
package main.java.edu.ingsoft.sonrisas.perfectas.model;


public class Usuario {
    private String idUsuario;
    private String nombreDentista;
    private String nombreUsuario;
    private String contrasena;
    
    public Usuario(){
    }

    public Usuario(String idUsuario, String nombreDentista, String nombreUsuario, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombreDentista = nombreDentista;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreDentista() {
        return nombreDentista;
    }

    public void setNombreDentista(String nombreDentista) {
        this.nombreDentista = nombreDentista;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
    
    
}
