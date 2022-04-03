package me.carlosaguilar.gepin.bean;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.exception.BusinessException;
import static com.github.adminfaces.template.util.Assert.has;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.infra.security.LogonMB;
import me.carlosaguilar.gepin.model.CentroInvestigacion;
import me.carlosaguilar.gepin.service.CentroInvestigacionService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class RegistroCentroInvestigacionMB implements Serializable {

    @Inject
    CentroInvestigacionService centroInvestigacionService;

  

    @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AdminConfig adminConfig;

    @Inject
    private LogonMB logonMB;

    private CentroInvestigacion centroInvestigacion;
    
    private String idCentroInvestigacion;
   

    @PostConstruct
    public void init() {
       
        centroInvestigacion = new CentroInvestigacion();
        
    }

  
    public void postLoad() throws IOException {

        if (Faces.isAjaxRequest()) {
            
            return;
        }
        if (has(idCentroInvestigacion)) {
            try {
                centroInvestigacion = centroInvestigacionService.getCentroInvestigacionById(Integer.valueOf(idCentroInvestigacion));
                if (centroInvestigacion == null) {
                    externalContext.getFlash().setKeepMessages(true);
                    addDetailMessage("El centro de investigación seleccionado no existe.", FacesMessage.SEVERITY_ERROR);
                    externalContext.redirect("centroInvestigacion.do");
                }
                
            } catch (SystemException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
                
            }
        }
    }

    /**
     * Este metodo permite guardar el centro de investigacion
     * y guarda en la bitacora
     */
    public void guardarCentroInvestigacion()  {
        try {

          

              
                centroInvestigacionService.saveCentroInvestigacion(centroInvestigacion);

                addDetailMessage("El Centro de Investigación " + centroInvestigacion.getNombreCentroInvestigacion()
                        + " se ha guardado correctamente");

                centroInvestigacion = new CentroInvestigacion();
               

            

        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    /**
     * Este metodo permite guardar el usuario, llamado al service UsuarioService
     * y guarda en la bitacora
     */
    public void actualizaCentroInvestigacion() {
        try {

            
            centroInvestigacionService.updateCentroInvestigacion(centroInvestigacion);

            addDetailMessage("El Centro de Investigación " + centroInvestigacion.getNombreCentroInvestigacion()
                    + " se ha actualizado correctamente");

            centroInvestigacion = new CentroInvestigacion();
          
        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    
/**
 * Método que elimina a un usuario o usuarios seleccionados
 * @throws IOException 
 */
    public void remove() throws IOException {
        if (has(centroInvestigacion) && has(centroInvestigacion.getIdCentroInvestigacion())) {
            try {
                centroInvestigacionService.deleteCentroInvestigacion(centroInvestigacion);
                addDetailMessage("El centro de investigación " + centroInvestigacion.getNombreCentroInvestigacion()
                        + " se ha eliminado correctamente");
                externalContext.getFlash().setKeepMessages(true);
               
                externalContext.redirect("centroInvestigacion.do");

            } catch (BusinessException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    public boolean isNew() {

        return centroInvestigacion == null || centroInvestigacion.getIdCentroInvestigacion() == 0;
    }

    public ExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public CentroInvestigacion getCentroInvestigacion() {
        return centroInvestigacion;
    }

    public void setCentroInvestigacion(CentroInvestigacion centroInvestigacion) {
        this.centroInvestigacion = centroInvestigacion;
    }

    public String getIdCentroInvestigacion() {
        return idCentroInvestigacion;
    }

    public void setIdCentroInvestigacion(String idCentroInvestigacion) {
        this.idCentroInvestigacion = idCentroInvestigacion;
    }


}
