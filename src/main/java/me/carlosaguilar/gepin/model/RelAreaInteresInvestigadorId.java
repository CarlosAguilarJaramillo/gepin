package me.carlosaguilar.gepin.model;
// Generated 7/04/2022 05:11:37 PM by Hibernate Tools 4.3.1



/**
 * RelAreaInteresInvestigadorId generated by hbm2java
 */
public class RelAreaInteresInvestigadorId  implements java.io.Serializable {


     private int idAreaInteres;
     private int idInvestigador;
     private int anio;

    public RelAreaInteresInvestigadorId() {
    }

    public RelAreaInteresInvestigadorId(int idAreaInteres, int idInvestigador, int anio) {
       this.idAreaInteres = idAreaInteres;
       this.idInvestigador = idInvestigador;
       this.anio = anio;
    }
   
    public int getIdAreaInteres() {
        return this.idAreaInteres;
    }
    
    public void setIdAreaInteres(int idAreaInteres) {
        this.idAreaInteres = idAreaInteres;
    }
    public int getIdInvestigador() {
        return this.idInvestigador;
    }
    
    public void setIdInvestigador(int idInvestigador) {
        this.idInvestigador = idInvestigador;
    }
    public int getAnio() {
        return this.anio;
    }
    
    public void setAnio(int anio) {
        this.anio = anio;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof RelAreaInteresInvestigadorId) ) return false;
		 RelAreaInteresInvestigadorId castOther = ( RelAreaInteresInvestigadorId ) other; 
         
		 return (this.getIdAreaInteres()==castOther.getIdAreaInteres())
 && (this.getIdInvestigador()==castOther.getIdInvestigador())
 && (this.getAnio()==castOther.getAnio());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdAreaInteres();
         result = 37 * result + this.getIdInvestigador();
         result = 37 * result + this.getAnio();
         return result;
   }   


}

