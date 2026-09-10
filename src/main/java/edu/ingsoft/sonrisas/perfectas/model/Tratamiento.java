
package main.java.edu.ingsoft.sonrisas.perfectas.model;

import java.math.BigDecimal;

public class Tratamiento {
    
    private String codigoTratamiento;
    private String idUsuario;
    private String nombreTratamiento;
    private String descripcion;
    private BigDecimal costoEstandar;
    
    public Tratamiento(){
    }

    public Tratamiento(String codigoTratamiento, String idUsuario, String nombreTratamiento, String descripcion, BigDecimal costoEstandar) {
        this.codigoTratamiento = codigoTratamiento;
        this.idUsuario = idUsuario;
        this.nombreTratamiento = nombreTratamiento;
        this.descripcion = descripcion;
        this.costoEstandar = costoEstandar;
    }

    public String getCodigoTratamiento() {
        return codigoTratamiento;
    }

    public void setCodigoTratamiento(String codigoTratamiento) {
        this.codigoTratamiento = codigoTratamiento;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreTratamiento() {
        return nombreTratamiento;
    }

    public void setNombreTratamiento(String nombreTratamiento) {
        this.nombreTratamiento = nombreTratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCostoEstandar() {
        return costoEstandar;
    }

    public void setCostoEstandar(BigDecimal costoEstandar) {
        this.costoEstandar = costoEstandar;
    }
    
    
    
}
