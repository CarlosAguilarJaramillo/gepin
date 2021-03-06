package me.carlosaguilar.gepin.model;
// Generated 7/04/2022 05:11:37 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * CentroInvestigacion generated by hbm2java
 */
public class CentroInvestigacion  implements java.io.Serializable {


     private int idCentroInvestigacion;
     private String nombreCentroInvestigacion;
     private String nombreDirector;
     private String telefono;
     private Set usuarios = new HashSet(0);
     private Set relCentroInvGrupoAcs = new HashSet(0);

    public CentroInvestigacion() {
    }

	
    public CentroInvestigacion(int idCentroInvestigacion, String nombreCentroInvestigacion) {
        this.idCentroInvestigacion = idCentroInvestigacion;
        this.nombreCentroInvestigacion = nombreCentroInvestigacion;
    }
    public CentroInvestigacion(int idCentroInvestigacion, String nombreCentroInvestigacion, String nombreDirector, String telefono, Set usuarios, Set relCentroInvGrupoAcs) {
       this.idCentroInvestigacion = idCentroInvestigacion;
       this.nombreCentroInvestigacion = nombreCentroInvestigacion;
       this.nombreDirector = nombreDirector;
       this.telefono = telefono;
       this.usuarios = usuarios;
       this.relCentroInvGrupoAcs = relCentroInvGrupoAcs;
    }
   
    public int getIdCentroInvestigacion() {
        return this.idCentroInvestigacion;
    }
    
    public void setIdCentroInvestigacion(int idCentroInvestigacion) {
        this.idCentroInvestigacion = idCentroInvestigacion;
    }
    public String getNombreCentroInvestigacion() {
        return this.nombreCentroInvestigacion;
    }
    
    public void setNombreCentroInvestigacion(String nombreCentroInvestigacion) {
        this.nombreCentroInvestigacion = nombreCentroInvestigacion;
    }
    public String getNombreDirector() {
        return this.nombreDirector;
    }
    
    public void setNombreDirector(String nombreDirector) {
        this.nombreDirector = nombreDirector;
    }
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Set getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Set usuarios) {
        this.usuarios = usuarios;
    }
    public Set getRelCentroInvGrupoAcs() {
        return this.relCentroInvGrupoAcs;
    }
    
    public void setRelCentroInvGrupoAcs(Set relCentroInvGrupoAcs) {
        this.relCentroInvGrupoAcs = relCentroInvGrupoAcs;
    }




}


