package me.carlosaguilar.gepin.bean;

import java.io.IOException;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import me.carlosaguilar.gepin.model.Investigador;
import me.carlosaguilar.gepin.service.InvestigadorService;

import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class InvestigadorMB implements Serializable {

    @Inject
    InvestigadorService investigadorService;
    
      @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;
   
      
    private Investigador investigador;
    
    private Investigador investigadorInfo;
    private List<Investigador> listainvestigador;
    
    private boolean rendered;
  
   
    
    
    public InvestigadorMB() {
    }

    @PostConstruct
    public void init() {

        try {
            rendered = false;
            investigadorInfo = new Investigador();
            
            investigador = new Investigador();
            listainvestigador = new ArrayList<>();
            listainvestigador = investigadorService.getAllInvestigador();
        } catch (SystemException ex) {
            Logger.getLogger(InvestigadorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Este método nos permite redirigirnos a la actualización de un investigador a través de su ID
 */
    public void editOutcome() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroInvestigador.do?idInvestigador=" + params.get("idInvestigador"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(InvestigadorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
      @Transactional
    public void eliminaInvestigador(Investigador inv) {
        if (inv!=null){
            investigadorService.deleteInvestigador(inv);
            listainvestigador.remove(inv);
           
        }
        addDetailMessage("Investigador eliminado satisfactoriamente");
        
    }

     public void editInvestigador() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroInvestigador.do?idInvestigador=" + params.get("idInvestigador"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(InvestigadorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
/**
 * Muestra una tabla en la vista de los datos de los investgadores 
     * @param inv
 */
    public void verInvestigadorSeleccionado(Investigador inv) {
        rendered = true;
        this.setInvestigadorInfo(inv);

    }

    
    


    public ExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

   
  
    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public Investigador getInvestigador() {
        return investigador;
    }

    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    public Investigador getInvestigadorInfo() {
        return investigadorInfo;
    }

    public void setInvestigadorInfo(Investigador investigadorInfo) {
        this.investigadorInfo = investigadorInfo;
    }

    public List<Investigador> getListainvestigador() {
        return listainvestigador;
    }

    public void setListainvestigador(List<Investigador> listainvestigador) {
        this.listainvestigador = listainvestigador;
    }

    

    
  
}
