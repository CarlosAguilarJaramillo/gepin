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
import me.carlosaguilar.gepin.model.ProyectosInvestigacion;
import me.carlosaguilar.gepin.service.InvestigadorService;
import me.carlosaguilar.gepin.service.ProyectosInvestigacionService;

import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class ProyectoInvestigacionMB implements Serializable {

    @Inject
    ProyectosInvestigacionService proyectosInvestigacionService;
    
      @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;
   
      
    private ProyectosInvestigacion proyectosInvestigacion;
    
    
    private List<ProyectosInvestigacion> listaProyectosInvestigacions;
    
    private boolean rendered;
  
   
    
    
    public ProyectoInvestigacionMB() {
    }

    @PostConstruct
    public void init() {

        try {
            rendered = false;
            
            
            proyectosInvestigacion = new ProyectosInvestigacion();
            listaProyectosInvestigacions = new ArrayList<>();
            listaProyectosInvestigacions = proyectosInvestigacionService.getAllProyectosInvestigacion();
        } catch (SystemException ex) {
            Logger.getLogger(ProyectoInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Este método nos permite redirigirnos a la actualización de un investigador a través de su ID
 */
    public void editOutcome() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroProyectosInvestigacion.do?idProyectoInvestigacion=" + params.get("idProyectoInvestigacion"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(ProyectoInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
      @Transactional
    public void eliminaProyectoInvestigacion(ProyectosInvestigacion proyectosInvestigacion) {
        if (proyectosInvestigacion!=null){
            proyectosInvestigacionService.deleteProyectosInvestigacion(proyectosInvestigacion);
            listaProyectosInvestigacions.remove(proyectosInvestigacion);
           
        }
        addDetailMessage("Proyecto eliminado satisfactoriamente");
        
    }

     public void editProyectoInvestigacion() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroProyectosInvestigacion.do?idProyectoInvestigacion=" + params.get("idProyectoInvestigacion"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(ProyectoInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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

    public List<ProyectosInvestigacion> getListaProyectosInvestigacions() {
        return listaProyectosInvestigacions;
    }

    public void setListaProyectosInvestigacions(List<ProyectosInvestigacion> listaProyectosInvestigacions) {
        this.listaProyectosInvestigacions = listaProyectosInvestigacions;
    }

    

    
  
}
