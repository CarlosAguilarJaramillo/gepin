package me.carlosaguilar.gepin.bean;

import java.io.IOException;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.transaction.SystemException;
import javax.transaction.Transactional;
import me.carlosaguilar.gepin.model.CentroInvestigacion;
import me.carlosaguilar.gepin.model.Usuario;
import me.carlosaguilar.gepin.service.CentroInvestigacionService;

import me.carlosaguilar.gepin.service.UsuarioService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class CentroInvestigacionMB implements Serializable {

    @Inject
    CentroInvestigacionService centroInvestigacionService;
    
      @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;
   
      
    private CentroInvestigacion centroInvestigacion;
    
    private CentroInvestigacion centroInvestigacionInfo;
    private List<CentroInvestigacion> listaCentrosInvestigacion;
    private List<CentroInvestigacion> selectedCentrosInvestigacion;
    private boolean rendered;
  
   
    
    
    public CentroInvestigacionMB() {
    }

    @PostConstruct
    public void init() {

        try {
            rendered = false;
            centroInvestigacionInfo = new CentroInvestigacion();
            selectedCentrosInvestigacion = new ArrayList<>();
            centroInvestigacion = new CentroInvestigacion();
            listaCentrosInvestigacion = new ArrayList<>();
            listaCentrosInvestigacion = centroInvestigacionService.getAllCentrosInvestigacion();
            
        } catch (SystemException ex) {
            Logger.getLogger(CentroInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Este método nos permite redirigirnos a la actualización de usuarios a través de su ID
 */
    public void editOutcome() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroCentroInvestigacion.do?idCentroInvestigacion=" + params.get("idCentroInvestigacion"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(CentroInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
      @Transactional
    public void eliminaCentrosInvestigacion(CentroInvestigacion centro) {
        if (centro!=null){
            centroInvestigacionService.deleteCentroInvestigacion(centro);
            listaCentrosInvestigacion.remove(centro);
           
        }
        addDetailMessage("Centro de investigación eliminado(s) satisfactoriamente");
        
    }

     public void editCentroInvestigacion() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroCentroInvestigacion.do?idCentroInvestigacion=" + params.get("idCentroInvestigacion"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(CentroInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
/**
 * Muestra una tabla en la vista de los datos del usuario seleccionado
     * @param ci
 */
    public void verCentroInvestigacionSeleccionado(CentroInvestigacion ci) {
        rendered = true;
        this.setCentroInvestigacionInfo(ci);

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

    public CentroInvestigacion getCentroInvestigacion() {
        return centroInvestigacion;
    }

    public void setCentroInvestigacion(CentroInvestigacion centroInvestigacion) {
        this.centroInvestigacion = centroInvestigacion;
    }

    public CentroInvestigacion getCentroInvestigacionInfo() {
        return centroInvestigacionInfo;
    }

    public void setCentroInvestigacionInfo(CentroInvestigacion centroInvestigacionInfo) {
        this.centroInvestigacionInfo = centroInvestigacionInfo;
    }

    public List<CentroInvestigacion> getListaCentrosInvestigacion() {
        return listaCentrosInvestigacion;
    }

    public void setListaCentrosInvestigacion(List<CentroInvestigacion> listaCentrosInvestigacion) {
        this.listaCentrosInvestigacion = listaCentrosInvestigacion;
    }

    public List<CentroInvestigacion> getSelectedCentrosInvestigacion() {
        return selectedCentrosInvestigacion;
    }

    public void setSelectedCentrosInvestigacion(List<CentroInvestigacion> selectedCentrosInvestigacion) {
        this.selectedCentrosInvestigacion = selectedCentrosInvestigacion;
    }

  
    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

   

    
  
}
