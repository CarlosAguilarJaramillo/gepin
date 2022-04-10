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
import me.carlosaguilar.gepin.model.RelProyectoInvestigador;
import me.carlosaguilar.gepin.service.CentroInvestigacionService;
import me.carlosaguilar.gepin.service.InvestigadorService;
import me.carlosaguilar.gepin.service.ProyectosInvestigacionService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.primefaces.model.DualListModel;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class RegistroProyectosInvestigacionMB implements Serializable {

    @Inject
    ProyectosInvestigacionService proyectosInvestigacionService;
    
     @Inject
     InvestigadorService investigadorService;

  

    @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AdminConfig adminConfig;

    @Inject
     CentroInvestigacionService centroInvestigacionService;
    
    @Inject
    private LogonMB logonMB;

    private ProyectosInvestigacion proyectosInvestigacion;
    
    private String idProyectoInvestigacion;
    
    
    
    private DualListModel<Investigador> dualListInvestigadores;
   private List<Investigador> listTargetInvestigadores;
   private List<Investigador> listaInvestigadores;
   
    private List<SelectItem> itemsCentroInvestigacion;
     private String idCentroInvestigacion;
     private String idLineaInvestigacion;
       private List<SelectItem> itemsLineasInvestigacion;
       
       

    @PostConstruct
    public void init() {
       
        proyectosInvestigacion = new ProyectosInvestigacion();
        listaInvestigadores = new ArrayList<>();
        listTargetInvestigadores= new ArrayList();
        idCentroInvestigacion = "0";
        itemsCentroInvestigacion = new ArrayList<>();
        try {
            listaInvestigadores = investigadorService.getAllInvestigador();
            dualListInvestigadores = new DualListModel<>(listaInvestigadores, listTargetInvestigadores);
            itemsCentroInvestigacion = centroInvestigacionService.getItemsAllCentrosInvestigacion();
        } catch (SystemException ex) {
            Logger.getLogger(RegistroProyectosInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  
    public void postLoad() throws IOException {

        if (Faces.isAjaxRequest()) {
            
            return;
        }
        if (has(idProyectoInvestigacion)) {
            try {
                proyectosInvestigacion = proyectosInvestigacionService.getProyectosInvestigacionById(Integer.valueOf(idProyectoInvestigacion));
                if (proyectosInvestigacion == null) {
                    externalContext.getFlash().setKeepMessages(true);
                    addDetailMessage("El proyecto seleccionado no existe.", FacesMessage.SEVERITY_ERROR);
                    externalContext.redirect("proyectosInvestigacion.do");
                   
                    
                }else{
                    List<RelProyectoInvestigador> listaRelacion = new ArrayList<>(proyectosInvestigacion.getRelProyectoInvestigadors());
                     listTargetInvestigadores = new ArrayList<>();
                    for (RelProyectoInvestigador ac: listaRelacion){
                       
                        listTargetInvestigadores.add(ac.getInvestigador());
                        
                    }
                    
                    this.eliminaDuplicados();
                    
                    
                    
                    dualListInvestigadores = new DualListModel<>(listaInvestigadores, listTargetInvestigadores);
                }
                
            } catch (SystemException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
                
            }
        }
    }
    
    public void valueChangeCentroInvestigacion(AjaxBehaviorEvent event) {
      //  System.out.println("Entro");
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            idCentroInvestigacion = (String) event.getComponent().getAttributes().get("value");
            itemsLineasInvestigacion = investigadorService.createMultiselectLineaInvestigacionByIdCentroInvestigacion(Integer.valueOf(idCentroInvestigacion));
        } catch (SystemException ex) {
            Logger.getLogger(RegistroInvestigadorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    public void valueChangeLineaInvestigacion(AjaxBehaviorEvent event) {
      //  System.out.println("Entro");
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            idLineaInvestigacion = (String) event.getComponent().getAttributes().get("value");
            itemsLineasInvestigacion = investigadorService.createMultiselectLineaInvestigacionByIdCentroInvestigacion(Integer.valueOf(idCentroInvestigacion));
        } catch (SystemException ex) {
            Logger.getLogger(RegistroInvestigadorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    public void eliminaDuplicados(){
        for(Investigador inv : listTargetInvestigadores){
            for(Investigador invAux : listaInvestigadores){
                if(invAux.getIdInvestigador()== inv.getIdInvestigador()){
                    listaInvestigadores.remove(invAux);
                    break;
                }
        }
        }
    }

    /**
     * Este metodo permite guardar la linea de investigacion
     * 
     */
    public void guardarProyectoInvestigacion()  {
        try {

          

              listTargetInvestigadores = dualListInvestigadores.getTarget();
              
            
                proyectosInvestigacionService.saveProyectosInvestigacion(proyectosInvestigacion, listTargetInvestigadores, Integer.valueOf(idLineaInvestigacion));
                
                addDetailMessage("El proyecto " + proyectosInvestigacion.getTemaProyecto()
                        + " se ha guardado correctamente");

                proyectosInvestigacion = new ProyectosInvestigacion();
               

            

        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
    
   

    /**
     * Este metodo permite guardar la linea de investigacion, llamado al service 
     
     */
    public void actualizaProyectoInvestigacion() {
        try {
            
             
            
            
            
            
            
            listTargetInvestigadores = dualListInvestigadores.getTarget();
            proyectosInvestigacionService.updateProyectoInvestigacion(proyectosInvestigacion,listTargetInvestigadores, Integer.valueOf(idLineaInvestigacion));

            addDetailMessage("El proyecto " + proyectosInvestigacion.getTemaProyecto()
                    + " se ha actualizado correctamente");

            proyectosInvestigacion = new ProyectosInvestigacion();
          
        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    
/**
 * Método que elimina a una linea de investigacion
 * @throws IOException 
 */
    public void remove() throws IOException {
        if (has(proyectosInvestigacion) && has(proyectosInvestigacion.getIdProyectoInvestigacion())) {
            try {
                proyectosInvestigacionService.deleteProyectosInvestigacion(proyectosInvestigacion);
                addDetailMessage("El proyecto " + proyectosInvestigacion.getLineaInvestigacion()
                        + " se ha eliminado correctamente");
                externalContext.getFlash().setKeepMessages(true);
               
                externalContext.redirect("proyectosInvestigacion.do");

            } catch (BusinessException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    public boolean isNew() {

        return proyectosInvestigacion == null || proyectosInvestigacion.getIdProyectoInvestigacion()== 0;
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

    public String getIdProyectoInvestigacion() {
        return idProyectoInvestigacion;
    }

    public void setIdProyectoInvestigacion(String idProyectoInvestigacion) {
        this.idProyectoInvestigacion = idProyectoInvestigacion;
    }

    public DualListModel<Investigador> getDualListInvestigadores() {
        return dualListInvestigadores;
    }

    public void setDualListInvestigadores(DualListModel<Investigador> dualListInvestigadores) {
        this.dualListInvestigadores = dualListInvestigadores;
    }

    public List<Investigador> getListTargetInvestigadores() {
        return listTargetInvestigadores;
    }

    public void setListTargetInvestigadores(List<Investigador> listTargetInvestigadores) {
        this.listTargetInvestigadores = listTargetInvestigadores;
    }

    public List<Investigador> getListaInvestigadores() {
        return listaInvestigadores;
    }

    public void setListaInvestigadores(List<Investigador> listaInvestigadores) {
        this.listaInvestigadores = listaInvestigadores;
    }

    public ProyectosInvestigacion getProyectosInvestigacion() {
        return proyectosInvestigacion;
    }

    public void setProyectosInvestigacion(ProyectosInvestigacion proyectosInvestigacion) {
        this.proyectosInvestigacion = proyectosInvestigacion;
    }

    public List<SelectItem> getItemsCentroInvestigacion() {
        return itemsCentroInvestigacion;
    }

    public void setItemsCentroInvestigacion(List<SelectItem> itemsCentroInvestigacion) {
        this.itemsCentroInvestigacion = itemsCentroInvestigacion;
    }

    public String getIdCentroInvestigacion() {
        return idCentroInvestigacion;
    }

    public void setIdCentroInvestigacion(String idCentroInvestigacion) {
        this.idCentroInvestigacion = idCentroInvestigacion;
    }

    public List<SelectItem> getItemsLineasInvestigacion() {
        return itemsLineasInvestigacion;
    }

    public void setItemsLineasInvestigacion(List<SelectItem> itemsLineasInvestigacion) {
        this.itemsLineasInvestigacion = itemsLineasInvestigacion;
    }

    public String getIdLineaInvestigacion() {
        return idLineaInvestigacion;
    }

    public void setIdLineaInvestigacion(String idLineaInvestigacion) {
        this.idLineaInvestigacion = idLineaInvestigacion;
    }

  
    

}
