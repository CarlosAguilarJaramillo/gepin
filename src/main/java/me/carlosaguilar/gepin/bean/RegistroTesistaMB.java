package me.carlosaguilar.gepin.bean;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.exception.BusinessException;
import static com.github.adminfaces.template.util.Assert.has;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.infra.security.LogonMB;
import me.carlosaguilar.gepin.model.Investigador;
import me.carlosaguilar.gepin.model.ProyectosInvestigacion;
import me.carlosaguilar.gepin.model.Tesista;
import me.carlosaguilar.gepin.service.InvestigadorService;
import me.carlosaguilar.gepin.service.ProyectosInvestigacionService;
import me.carlosaguilar.gepin.service.TesistaService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class RegistroTesistaMB implements Serializable {

    @Inject
    TesistaService tesistaService;
    
     @Inject
     InvestigadorService investigadorService;
     
      @Inject
      ProyectosInvestigacionService proyectosInvestigacionService;

  

    @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AdminConfig adminConfig;

   
    
    @Inject
    private LogonMB logonMB;

    private Tesista tesista;
    
    private String idTesista;
    
    
    
   
   
    private List<SelectItem> itemsInvestigadores;
     private String idInvestigador;
    
       private Investigador investigador;
       
       
       private List<SelectItem> itemsProyectosInvestigacion;
     private String idProyectoInvestigacion;
    
       private ProyectosInvestigacion proyectosInvestigacion;
       

    @PostConstruct
    public void init() {
       
        tesista = new Tesista();
       
        
        idInvestigador = "0";
        itemsInvestigadores = new ArrayList<>();
        itemsProyectosInvestigacion = new ArrayList<>();
        
        try {
            
            itemsInvestigadores = investigadorService.getItemsAllInvestigadores();
            itemsProyectosInvestigacion = proyectosInvestigacionService.getItemsAllProyectosInvestigacion();
        } catch (SystemException ex) {
            Logger.getLogger(RegistroTesistaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  
    public void postLoad() throws IOException {

        if (Faces.isAjaxRequest()) {
            
            return;
        }
        if (has(idTesista)) {
            try {
                tesista = tesistaService.getTesistaById(Integer.valueOf(idTesista));
                if (tesista == null) {
                    externalContext.getFlash().setKeepMessages(true);
                    addDetailMessage("El tesista seleccionado no existe.", FacesMessage.SEVERITY_ERROR);
                    externalContext.redirect("tesista.do");
                   
                    
                }else{
                   
                }
                
            } catch (SystemException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
                
            }
        }
    }
    
    public void valueChangeInvestigador(AjaxBehaviorEvent event) throws SystemException {
        //  System.out.println("Entro");
        FacesContext context = FacesContext.getCurrentInstance();
        idInvestigador = (String) event.getComponent().getAttributes().get("value");
        investigador = investigadorService.getInvestigadorById(Integer.valueOf(idInvestigador));
        tesista.setInvestigador(investigador);
    }
    
    public void valueChangeProyecto(AjaxBehaviorEvent event) throws SystemException {
        //  System.out.println("Entro");
        FacesContext context = FacesContext.getCurrentInstance();
        idProyectoInvestigacion = (String) event.getComponent().getAttributes().get("value");
        proyectosInvestigacion = proyectosInvestigacionService.getProyectosInvestigacionByIdSinRelaciones(Integer.valueOf(idProyectoInvestigacion));
       tesista.setProyectosInvestigacion(proyectosInvestigacion);
    }
    
  

    public void guardarTesista()  {
        try {

          

            
              
                
                tesistaService.saveTesista(tesista);
                
                addDetailMessage("El tesisa " + tesista.getNombre() +" "+ tesista.getApellidoPaterno() + " " +tesista.getApellidoMaterno()
                        + " se ha guardado correctamente");

                tesista = new Tesista();
               

            

        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
    
   

    public void actualizaTesista() {
        try {
            
            
            
           
            tesistaService.updateTesista(tesista);

           addDetailMessage("El tesisa " + tesista.getNombre() +" "+ tesista.getApellidoPaterno() + " " +tesista.getApellidoMaterno()
                    + " se ha actualizado correctamente");

            tesista = new Tesista();
          
        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    
/**
 * Método que elimina a una linea de investigacion
 * @throws IOException 
 */
    public void remove() throws IOException {
        if (has(tesista) && has(tesista.getIdTesista())) {
            try {
                tesistaService.deleteTesista(tesista);
                addDetailMessage("El tesista " 
                        + " se ha eliminado correctamente");
                externalContext.getFlash().setKeepMessages(true);
               
                externalContext.redirect("tesista.do");

            } catch (BusinessException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    public boolean isNew() {

        return tesista == null || tesista.getIdTesista()== 0;
    }

    public ExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }

    public AdminConfig getAdminConfig() {
        return adminConfig;
    }

    public void setAdminConfig(AdminConfig adminConfig) {
        this.adminConfig = adminConfig;
    }

    public LogonMB getLogonMB() {
        return logonMB;
    }

    public void setLogonMB(LogonMB logonMB) {
        this.logonMB = logonMB;
    }

    public String getIdTesista() {
        return idTesista;
    }

    public void setIdTesista(String idTesista) {
        this.idTesista = idTesista;
    }

   

    public List<SelectItem> getItemsInvestigadores() {
        return itemsInvestigadores;
    }

    public void setItemsInvestigadores(List<SelectItem> itemsInvestigadores) {
        this.itemsInvestigadores = itemsInvestigadores;
    }

    public String getIdInvestigador() {
        return idInvestigador;
    }

    public void setIdInvestigador(String idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

    public Tesista getTesista() {
        return tesista;
    }

    public void setTesista(Tesista tesista) {
        this.tesista = tesista;
    }

    public Investigador getInvestigador() {
        return investigador;
    }

    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    public List<SelectItem> getItemsProyectosInvestigacion() {
        return itemsProyectosInvestigacion;
    }

    public void setItemsProyectosInvestigacion(List<SelectItem> itemsProyectosInvestigacion) {
        this.itemsProyectosInvestigacion = itemsProyectosInvestigacion;
    }

    public String getIdProyectoInvestigacion() {
        return idProyectoInvestigacion;
    }

    public void setIdProyectoInvestigacion(String idProyectoInvestigacion) {
        this.idProyectoInvestigacion = idProyectoInvestigacion;
    }

    public ProyectosInvestigacion getProyectosInvestigacion() {
        return proyectosInvestigacion;
    }

    public void setProyectosInvestigacion(ProyectosInvestigacion proyectosInvestigacion) {
        this.proyectosInvestigacion = proyectosInvestigacion;
    }

   
  
    

}
