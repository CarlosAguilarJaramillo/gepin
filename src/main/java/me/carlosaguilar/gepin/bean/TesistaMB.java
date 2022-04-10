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
import me.carlosaguilar.gepin.model.Tesista;
import me.carlosaguilar.gepin.service.InvestigadorService;
import me.carlosaguilar.gepin.service.ProyectosInvestigacionService;
import me.carlosaguilar.gepin.service.TesistaService;

import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class TesistaMB implements Serializable {

    @Inject
    TesistaService tesistaService;
    
      @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;
   
      
    private Tesista tesista;
    
    
    private List<Tesista> listaTesistas;
    
    private boolean rendered;
  
   
    
    
    public TesistaMB() {
    }

    @PostConstruct
    public void init() {

        try {
            rendered = false;
            
            
            tesista = new Tesista();
            listaTesistas = new ArrayList<>();
            listaTesistas = tesistaService.getAllTesistas();
        } catch (SystemException ex) {
            Logger.getLogger(TesistaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Este método nos permite redirigirnos a la actualización de un investigador a través de su ID
 */
    public void editOutcome() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroTesista.do?idTesista=" + params.get("idTesista"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(TesistaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
      @Transactional
    public void eliminaTesista(Tesista tesista) {
        if (tesista!=null){
            tesistaService.deleteTesista(tesista);
            listaTesistas.remove(tesista);
           
        }
        addDetailMessage("Tesista eliminado satisfactoriamente");
        
    }

     public void editProyectoInvestigacion() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroTesista.do?idTesista=" + params.get("idTesista"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(TesistaMB.class.getName()).log(Level.SEVERE, null, ex);
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

    public Tesista getTesista() {
        return tesista;
    }

    public void setTesista(Tesista tesista) {
        this.tesista = tesista;
    }

    public List<Tesista> getListaTesistas() {
        return listaTesistas;
    }

    public void setListaTesistas(List<Tesista> listaTesistas) {
        this.listaTesistas = listaTesistas;
    }


    
  
}
