package com.example.bishoppc.pdfaddpager.entidad;

import java.util.Date;

public class Operaciones
{
    private Integer id;
    private Date fecha;
    private String nomUsu;
    private Integer legajoCli;
    private String nomYApeCli;
    private Integer dniCli;
    private String pathArch;
    private Boolean firmado;

    public Operaciones(Integer id, Date fecha, String nomUsu, Integer legajoCli, String nomYApeCli, Integer dniCli, String pathArch, Boolean firmado) {
        this.id = id;
        this.fecha = fecha;
        this.nomUsu = nomUsu;
        this.legajoCli = legajoCli;
        this.nomYApeCli = nomYApeCli;
        this.dniCli = dniCli;
        this.pathArch = pathArch;
        this.firmado = firmado;
    }

    public Operaciones(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNomUsu() {
        return nomUsu;
    }

    public void setNomUsu(String nomUsu) {
        this.nomUsu = nomUsu;
    }

    public Integer getLegajoCli() {
        return legajoCli;
    }

    public void setLegajoCli(Integer legajoCli) {
        this.legajoCli = legajoCli;
    }

    public String getNomYApeCli() {
        return nomYApeCli;
    }

    public void setNomYApeCli(String nomYApeCli) {
        this.nomYApeCli = nomYApeCli;
    }

    public Integer getDniCli() {
        return dniCli;
    }

    public void setDniCli(Integer dniCli) {
        this.dniCli = dniCli;
    }

    public String getPathArch() {
        return pathArch;
    }

    public void setPathArch(String pathArch) {
        this.pathArch = pathArch;
    }

    public Boolean getFirmado() {
        return firmado;
    }

    public void setFirmado(Boolean firmado) {
        this.firmado = firmado;
    }
}
