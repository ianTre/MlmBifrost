package com.example.bishoppc.pdfaddpager.entidades;

import java.io.Serializable;

public class Operacion implements Serializable
{
    private Integer id;
    private String fecha;
    private String legajoCli;
    private String nomUsu;
    private String leyenda;
    private String nomYApeCli;
    private String dniCli;
    private String pathArch;
    private int firmado;

    public Operacion(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLegajoCli() {
        return legajoCli;
    }

    public void setLegajoCli(String legajoCli) { this.legajoCli = legajoCli; }

    public String getNomUsu() {
        return nomUsu;
    }

    public void setNomUsu(String nomUsu) {
        this.nomUsu = nomUsu;
    }

    public String getLeyenda() { return leyenda; }

    public void setLeyenda(String leyenda) { this.leyenda = leyenda; }

    public String getNomYApeCli() {
        return nomYApeCli;
    }

    public void setNomYApeCli(String nomYApeCli) {
        this.nomYApeCli = nomYApeCli;
    }

    public String getDniCli() {
        return dniCli;
    }

    public void setDniCli(String dniCli) {
        this.dniCli = dniCli;
    }

    public String getPathArch() {
        return pathArch;
    }

    public void setPathArch(String pathArch) {
        this.pathArch = pathArch;
    }

    public int getFirmado() {
        return firmado;
    }

    public void setFirmado(int firmado) {
        this.firmado = firmado;
    }
}
