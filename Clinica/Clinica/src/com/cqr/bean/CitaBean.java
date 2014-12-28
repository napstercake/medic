/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cqr.bean;

/**
 *
 * @author ricardogonzales
 */
public class CitaBean {
    
    private Integer codigo;
    private Integer codigopaciente;
    private String nombrepaciente;
    private String estado;
    private String hora;
    private String fecha;
    private String notas;

    public Integer getCodigo() {
        return codigo;
    }
    
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }   
    
    public String getNombrepaciente() {
        return nombrepaciente;
    }

    public void setNombrepaciente(String nombrepaciente) {
        this.nombrepaciente = nombrepaciente;
    }

    public Integer getCodigopaciente() {
        return codigopaciente;
    }

    public void setCodigopaciente(Integer codigopaciente) {
        this.codigopaciente = codigopaciente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
         
}
