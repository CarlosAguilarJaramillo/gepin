package me.carlosaguilar.gepin.model;
// Generated 7/04/2022 05:04:36 PM by Hibernate Tools 4.3.1



/**
 * RelProyectoLineaId generated by hbm2java
 */
public class RelProyectoLineaId  implements java.io.Serializable {


     private Integer idProyectoInvestigacion;
     private Integer idLineaInvestigacion;

    public RelProyectoLineaId() {
    }

    public RelProyectoLineaId(Integer idProyectoInvestigacion, Integer idLineaInvestigacion) {
       this.idProyectoInvestigacion = idProyectoInvestigacion;
       this.idLineaInvestigacion = idLineaInvestigacion;
    }
   
    public Integer getIdProyectoInvestigacion() {
        return this.idProyectoInvestigacion;
    }
    
    public void setIdProyectoInvestigacion(Integer idProyectoInvestigacion) {
        this.idProyectoInvestigacion = idProyectoInvestigacion;
    }
    public Integer getIdLineaInvestigacion() {
        return this.idLineaInvestigacion;
    }
    
    public void setIdLineaInvestigacion(Integer idLineaInvestigacion) {
        this.idLineaInvestigacion = idLineaInvestigacion;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RelProyectoLineaId) ) return false;
		 RelProyectoLineaId castOther = ( RelProyectoLineaId ) other; 
         
		 return ( (this.getIdProyectoInvestigacion()==castOther.getIdProyectoInvestigacion()) || ( this.getIdProyectoInvestigacion()!=null && castOther.getIdProyectoInvestigacion()!=null && this.getIdProyectoInvestigacion().equals(castOther.getIdProyectoInvestigacion()) ) )
 && ( (this.getIdLineaInvestigacion()==castOther.getIdLineaInvestigacion()) || ( this.getIdLineaInvestigacion()!=null && castOther.getIdLineaInvestigacion()!=null && this.getIdLineaInvestigacion().equals(castOther.getIdLineaInvestigacion()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIdProyectoInvestigacion() == null ? 0 : this.getIdProyectoInvestigacion().hashCode() );
         result = 37 * result + ( getIdLineaInvestigacion() == null ? 0 : this.getIdLineaInvestigacion().hashCode() );
         return result;
   }   


}

