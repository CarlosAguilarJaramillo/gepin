package me.carlosaguilar.gepin.model;
// Generated 7/04/2022 05:11:37 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Perfil generated by hbm2java
 */
public class Perfil  implements java.io.Serializable {


     private int idPerfil;
     private String perfil;
     private Set usuarios = new HashSet(0);

    public Perfil() {
    }

	
    public Perfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }
    public Perfil(int idPerfil, String perfil, Set usuarios) {
       this.idPerfil = idPerfil;
       this.perfil = perfil;
       this.usuarios = usuarios;
    }
   
    public int getIdPerfil() {
        return this.idPerfil;
    }
    
    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }
    public String getPerfil() {
        return this.perfil;
    }
    
    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
    public Set getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }




}


