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
import me.carlosaguilar.gepin.model.GrupoAcademico;
import me.carlosaguilar.gepin.model.LineaInvestigacion;
import me.carlosaguilar.gepin.service.GrupoAcademicoService;
import me.carlosaguilar.gepin.service.LineaInvestigacionService;

import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class LineaInvestigacionMB implements Serializable {

    @Inject
    LineaInvestigacionService lineaInvestigacionService;
    
      @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;
   
      
    private LineaInvestigacion lineaInvestigacion;
    
    private LineaInvestigacion lineaInvestigacionInfo;
    private List<LineaInvestigacion> listaLineaInvestigacion;
    
    private boolean rendered;
  
   
    
    
    public LineaInvestigacionMB() {
    }

    @PostConstruct
    public void init() {

        try {
            rendered = false;
            lineaInvestigacionInfo = new LineaInvestigacion();
            
            lineaInvestigacion = new LineaInvestigacion();
            listaLineaInvestigacion = new ArrayList<>();
            listaLineaInvestigacion = lineaInvestigacionService.getAllLineaInvestigacion();
            
        } catch (SystemException ex) {
            Logger.getLogger(LineaInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Este método nos permite redirigirnos a la actualización de una linea de investigacion a través de su ID
 */
    public void editOutcome() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroLineaInvestigacion.do?idLineaInvestigacion=" + params.get("idLineaInvestigacion"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(LineaInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
      @Transactional
    public void eliminaLineaInvestigacion(LineaInvestigacion linea) {
        if (linea!=null){
            lineaInvestigacionService.deleteLineaInvestigacion(linea);
            listaLineaInvestigacion.remove(linea);
           
        }
        addDetailMessage("Línea de investigación eliminada satisfactoriamente");
        
    }

     public void editLineaInvestigacion() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroLineaInvestigacion.do?idLineaInvestigacion=" + params.get("idLineaInvestigacion"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(LineaInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
/**
 * Muestra una tabla en la vista de los datos de la lineaInvestigacion seleccionada
     * @param li
 */
    public void verLineaInvestigacionSeleccionada(LineaInvestigacion li) {
        rendered = true;
        this.setLineaInvestigacionInfo(li);

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

    public LineaInvestigacion getLineaInvestigacion() {
        return lineaInvestigacion;
    }

    public void setLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }

    public LineaInvestigacion getLineaInvestigacionInfo() {
        return lineaInvestigacionInfo;
    }

    public void setLineaInvestigacionInfo(LineaInvestigacion lineaInvestigacionInfo) {
        this.lineaInvestigacionInfo = lineaInvestigacionInfo;
    }

    public List<LineaInvestigacion> getListaLineaInvestigacion() {
        return listaLineaInvestigacion;
    }

    public void setListaLineaInvestigacion(List<LineaInvestigacion> listaLineaInvestigacion) {
        this.listaLineaInvestigacion = listaLineaInvestigacion;
    }

   
   

    
  
}
