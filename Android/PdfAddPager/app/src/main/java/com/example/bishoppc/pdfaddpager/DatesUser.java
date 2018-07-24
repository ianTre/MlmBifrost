package com.example.bishoppc.pdfaddpager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.lang.String;
import java.util.List;

public class DatesUser extends AppCompatActivity{
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;
    Button botonPrevisualizar;
    Button botonNeg;
    Button botonAff;

    EditText txtLeyenda;
    EditText txtNombre;
    EditText txtDni;
    TouchEventView touchEventView;
    String targetPdf;

    //Variable para la seleccion Intent
    private static final int PICKER = 1;
    //Declaramos los siguientes obejtos
    Button botonExaminar;
    EditText textExaminar;

    PDFView pdfView;

    private File pdfFile;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dates);

        botonExaminar = (Button) findViewById(R.id.btnExaminar);
        textExaminar = (EditText) findViewById(R.id.txtExaminar);

        botonAff = (Button)findViewById(R.id.buttonAff);
        botonNeg = (Button)findViewById(R.id.buttonNeg);
        botonPrevisualizar = (Button)findViewById(R.id.buttonPrev);

        txtNombre = (EditText)findViewById(R.id.txtNombre);
        txtDni = (EditText)findViewById(R.id.txtDni);
        txtLeyenda=(EditText)findViewById(R.id.txtLeyenda);

        touchEventView = (TouchEventView)findViewById(R.id.canvas);
        touchEventView.setFocusableInTouchMode(true);

        final CustomScrollView myScrollView = (CustomScrollView) findViewById(R.id.myScroll);
        final LinearLayout myLayout = (LinearLayout) findViewById(R.id.layout_main);

        final String textoTecnico = (String)getIntent().getExtras().get("textoTecnico");

        txtLeyenda.setText(textoTecnico.toString());

        botonAff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });

        touchEventView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    myScrollView.setEnableScrolling(true); // scrolling disabled
                    botonAff.requestFocus();
                }else{
                    myScrollView.setEnableScrolling(false); // scrolling enabled
                }

            }
        });

        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_DOWN == event.getAction())
                {
                    botonAff.requestFocus();
                    //enableAffButton();
                }

                return false;
            }
        });

        txtDni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableAffButton();
            }
        });

        txtNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                enableAffButton();
            }
        });

        botonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                botonAff.setEnabled(false);
                txtNombre.setText("");
                txtDni.setText("");
                touchEventView.clearCanvas();

            }
        });

        touchEventView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_DOWN == event.getAction())
                    enableAffButton();

                return false;
            }
        });

        botonPrevisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try
                {
                    previewPdf();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createPdfWrapper() throws FileNotFoundException,DocumentException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            CreatePDF();

            //Bundle extras = getIntent().getExtras();
            final File pathFile = (File)getIntent().getExtras().get("pathFile");

            //Archivos que deseo unir
            String[] srcs = {pathFile.toString(), "/storage/emulated/0/Download/carpetaJavaPruebas/pdf.pdf"};
            mergePdf(srcs);
        }
    }

    private void mergePdf(String[] srcs) {

        final File pathFile = (File)getIntent().getExtras().get("pathFile");
        String pathFileStr = pathFile.toString();
        String[] archFile = pathFileStr.split("/");
        String arch = archFile[archFile.length - 1].split("\\.")[0];

        try {
            // Create document object
            Document document = new Document();
            // Create pdf copy object to copy current document to the output mergedresult file
            String targetPdf = Environment.getExternalStorageDirectory().toString();

            pdfFile = new File(Environment.getExternalStorageDirectory().toString(),"/Download/carpetaJavaPruebas/" + arch + "+pdf.pdf");
            File filePath = new File(targetPdf, "/Download/carpetaJavaPruebas/" + arch + "+pdf.pdf");

            PdfCopy copy = new PdfCopy(document, new FileOutputStream(filePath));
            //Open the document
            document.open();
            PdfReader pr;
            int n;

            for (int i = 0; i < srcs.length; i++) {
                // Create pdf reader object to read each input pdf file
                pr = new PdfReader(srcs[i].toString());                     //recorre los pdf que hay en la cadena de String
                // Get the number of pages of the pdf file
                n = pr.getNumberOfPages();                                  //numero de hojas que tiene dicho pdf
                for (int page = 1; page <= n; page++)
                {
                    // Import all pages from the file to PdfCopy
                    copy.addPage(copy.getImportedPage(pr, page));           //añade a la variable copy all the pages
                }
            }

            //AGREGAR//////////////////////////////////////////////////////////////
            /*Esto refresca la interaccion entre Windows MTP y Android*/
            Uri uri = Uri.fromFile(filePath);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            sendBroadcast(intent);
            ///////////////////////////////////////////////////////////////////////

            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();

            document.close(); // close the document

        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /* PROYECTO ACTUAL*/
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void CreatePDF() {
        // create a new document

        PdfDocument document = new PdfDocument();

        /*
        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(100, 100, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(50, 50, 30, paint);
        //canvas.drawPath(touchEventView.mPath,touchEventView.mPaint);
        // finish the page
        document.finishPage(page);
        */

        // Create Page 2

        //PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(800, 1200, 1).create();
        //PdfDocument.Page page = document.startPage(pageInfo);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(800, 1200, 1).create(); //Se da el alto y ancho a la pagina
        PdfDocument.Page page = document.startPage(pageInfo);


        Canvas canvas = page.getCanvas();

        Paint paint = touchEventView.mPaint;
        Path path = touchEventView.mPath;
        paint.setColor(Color.BLUE);                           //Se le da color azul al lienzo del TouchEventView

        Date currentTime = Calendar.getInstance().getTime();

        String textoComienzo = "Dejo por sentado conformidad con el servicio brindado por el tecnico bashar al assad Y que en el dia de la Fecha recibí" +
                "la CPU funcionando y en óptimo estado. Sin más estoy a la espera de recibir 3 Mouse y 2 Teclados para concluir con lo" +
                "solicitado anteriormente";

        String textoNombre = "Nombre : " + txtNombre.getText();
        String textoDni = "Numero Dni : " + txtDni.getText();

        Paint paintToText = new Paint();

        paintToText.setStyle(Paint.Style.FILL);                                         //Se le da Formato texto, Dni y Nombre
        paintToText.setColor(Color.BLACK);
        paintToText.setTextSize(30);
        paintToText.setTypeface(Typeface.create("Arial",Typeface.NORMAL));



        TextPaint textPaint = new TextPaint();
        RectF rect = new RectF(50,50,800,200);
        StaticLayout sl = new StaticLayout(textoComienzo, textPaint, (int)rect.width(), Layout.Alignment.ALIGN_NORMAL,
                1, 1, false);

        canvas.save();
        canvas.translate(rect.left, rect.top);                          //Se le da el alto y ancho al objeto
        sl.draw(canvas);
        canvas.restore();

        canvas.drawText(textoDni,50,150 ,paintToText);
        canvas.drawText(textoNombre, 50 ,200 ,paintToText);
        canvas.drawText("Firma:", 50 ,250 ,paintToText);
        canvas.translate(0,300);                                //Se genera un espacio en el alto

        Matrix scaleMatrix = new Matrix();                              //Se le da una escala menor para que entre en el pdf//
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        scaleMatrix.setScale(0.6f, 0.6f,0,rectF.centerY());
        path.transform(scaleMatrix);

        canvas.drawPath(path,paint);                                    //Se dibuja el recorrido hecho con el lienzo al pdf //
        document.finishPage(page);


        // write the document content
        //String targetPdf = "/sdcard/test.pdf";
        targetPdf = Environment.getExternalStorageDirectory().toString();
        File filePath = new File(targetPdf, "/Download/carpetaJavaPruebas/"+"pdf.pdf"); //Pdf que se crea con la firma del touch


        try
        {
            document.writeTo(new FileOutputStream(filePath));

            //AGREGAR//////////////////////////////////////////////////////////////
            /*Esto refresca la interaccion entre Windows MTP y Android*/
            Uri uri = Uri.fromFile(filePath);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            sendBroadcast(intent);
            ///////////////////////////////////////////////////////////////////////

            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();

        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }

    private void previewPdf() throws FileNotFoundException{

        //Intent testIntent = new Intent(Intent.ACTION_VIEW);
        //testIntent.setType("application/pdf");

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        //Uri uri = Uri.fromFile(pdfFile);
        intent.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");

        PackageManager packageManager = getPackageManager();
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() > 0)
            startActivity(intent);
        else
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
    }

    private void enableAffButton()
    {
        String textoNombre=txtNombre.getText().toString();
        String textoDni=txtDni.getText().toString();
        boolean pintado=touchEventView.painted;

        if (textoNombre.equals("") || textoDni.equals("") || pintado == false)
            botonAff.setEnabled(false);
        else
            botonAff.setEnabled(true);

    }

    /*private void PickFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("file/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Instale un administrador de archivos"), PICKER);
    }*/

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case PICKER:
                if(resultCode == RESULT_OK)
                {
                    String FilePath = data.getData().getPath();
                    textExaminar.setText(FilePath);*/

                    /*try
                    {
                        pdfFile = new File(textExaminar.getText().toString());
                        //previewPdf(pdfFile);
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }*/
                //}
                //break;
        //}
    //}

    /*private StaticLayout measure(TextPaint textPaint, String text, Integer wrapWidth )
    {
        int boundedWidth = Integer.MAX_VALUE;
        if (wrapWidth != null && wrapWidth > 0 )
        {
            boundedWidth = wrapWidth;
        }
        StaticLayout layout = new StaticLayout( text, textPaint, boundedWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false );
        return layout;
    }*/

    //R.layout.activity_main
    /*private float getMaxLineWidth( StaticLayout layout )
    {
        float maxLine = 0.0f;
        int lineCount = layout.getLineCount();
        for( int i = 0; i < lineCount; i++ ) {
            if( layout.getLineWidth( 0 ) > maxLine )
            {
                maxLine = layout.getLineWidth( 0 );
            }
        }
        return maxLine;
    }*/

    /*void drawMultiLineText(String str, float x, float y, Paint paint, Canvas canvas){
        String[] lines = str.split("\n");
        float txtSize = -paint.ascent() + paint.descent();
        if(paint.getStyle() == Paint.Style.FILL_AND_STROKE || paint.getStyle() == Paint.Style.STROKE){
            txtSize += paint.getStrokeWidth();
        }
        float lineSpace= txtSize * 0.2f;

        for(int i=0; i < lines.length; ++i){
            canvas.drawText(lines[i],x,y + (txtSize + lineSpace) * i, paint);
        }
        canvas.translate(0,100);
    }*/

    /*private void previewPdf() {
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        File pdfFile = new File(targetPdf);

        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        }else{
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }*/
}
