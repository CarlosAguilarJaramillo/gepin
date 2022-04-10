package me.carlosaguilar.gepin.model;
// Generated 7/04/2022 05:11:37 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * GrupoAcademico generated by hbm2java
 */
public class GrupoAcademico  implements java.io.Serializable {


     private int idGrupoAcademico;
     private String grupoAcademico;
     private Set relGrupoAcLineaInvs = new HashSet(0);
     private Set relCentroInvGrupoAcs = new HashSet(0);

    public GrupoAcademico() {
    }

	
    public GrupoAcademico(int idGrupoAcademico, String grupoAcademico) {
        this.idGrupoAcademico = idGrupoAcademico;
        this.grupoAcademico = grupoAcademico;
    }
    public GrupoAcademico(int idGrupoAcademico, String grupoAcademico, Set relGrupoAcLineaInvs, Set relCentroInvGrupoAcs) {
       this.idGrupoAcademico = idGrupoAcademico;
       this.grupoAcademico = grupoAcademico;
       this.relGrupoAcLineaInvs = relGrupoAcLineaInvs;
       this.relCentroInvGrupoAcs = relCentroInvGrupoAcs;
    }
   
    public int getIdGrupoAcademico() {
        return this.idGrupoAcademico;
    }
    
    public void setIdGrupoAcademico(int idGrupoAcademico) {
        this.idGrupoAcademico = idGrupoAcademico;
    }
    public String getGrupoAcademico() {
        return this.grupoAcademico;
    }
    
    public void setGrupoAcademico(String grupoAcademico) {
        this.grupoAcademico = grupoAcademico;
    }
    public Set getRelGrupoAcLineaInvs() {
        return this.relGrupoAcLineaInvs;
    }
    
    public void setRelGrupoAcLineaInvs(Set relGrupoAcLineaInvs) {
        this.relGrupoAcLineaInvs = relGrupoAcLineaInvs;
    }
    public Set getRelCentroInvGrupoAcs() {
        return this.relCentroInvGrupoAcs;
    }
    
    public void setRelCentroInvGrupoAcs(Set relCentroInvGrupoAcs) {
        this.relCentroInvGrupoAcs = relCentroInvGrupoAcs;
    }




}

