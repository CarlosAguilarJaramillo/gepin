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
import me.carlosaguilar.gepin.model.LineaInvestigacion;
import me.carlosaguilar.gepin.model.RelInvestigadorLineaGrupo;
import me.carlosaguilar.gepin.service.CentroInvestigacionService;
import me.carlosaguilar.gepin.service.InvestigadorService;
import me.carlosaguilar.gepin.service.LineaInvestigacionService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.primefaces.model.DualListModel;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class RegistroInvestigadorMB implements Serializable {

    @Inject
    InvestigadorService investigadorService;
    
     @Inject
     LineaInvestigacionService lineaInvestigacionService;
     
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

    private Investigador investigador;
    
    private String idInvestigador;
//    private String selection;
//    private List<SelectItem> items;
//    
    private String[] selectedLineas;
    
    private List<SelectItem> itemsCentroInvestigacion;
    
//    private DualListModel<LineaInvestigacion> dualListLineaInvestigacion;
   private List<LineaInvestigacion> listTargetLineaInvestigacion;
   private List<LineaInvestigacion> listaLineaInvestigacion;
   
   
   private String idCentroInvestigacion;
       private List<SelectItem> itemsLineasInvestigacion;
       
  

    @PostConstruct
    public void init() {
       
        investigador = new Investigador();
        listaLineaInvestigacion = new ArrayList<>();
        listTargetLineaInvestigacion = new ArrayList();
        idCentroInvestigacion = "0";
        itemsCentroInvestigacion = new ArrayList<>();
        try {
            listaLineaInvestigacion = lineaInvestigacionService.getAllLineaInvestigacion();
//            dualListLineaInvestigacion = new DualListModel<>(listaLineaInvestigacion, listTargetLineaInvestigacion);
            //items = investigadorService.createMultiselectForInvestigador();
            
            itemsCentroInvestigacion = centroInvestigacionService.getItemsAllCentrosInvestigacion();
        } catch (SystemException ex) {
            Logger.getLogger(RegistroInvestigadorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

  
    public void postLoad() throws IOException {

        if (Faces.isAjaxRequest()) {
            
            return;
        }
        if (has(idInvestigador)) {
            try {
                investigador = investigadorService.getInvestigadorById(Integer.valueOf(idInvestigador));
                if (investigador == null) {
                    externalContext.getFlash().setKeepMessages(true);
                    addDetailMessage("El investigador seleccionada no existe.", FacesMessage.SEVERITY_ERROR);
                    externalContext.redirect("investigador.do");
                   
                    
                }else{
                    List<RelInvestigadorLineaGrupo> listaRelacion = new ArrayList<>(investigador.getRelInvestigadorLineaGrupos());
                     listTargetLineaInvestigacion = new ArrayList<>();
                    for (RelInvestigadorLineaGrupo ac: listaRelacion){
                       
                       // listTargetLineaInvestigacion.add(ac.getLineaInvestigacion());
                        
                    }
                    
//                    this.eliminaDuplicados();
                    
                    
                    
//                    dualListLineaInvestigacion = new DualListModel<>(listaLineaInvestigacion, listTargetLineaInvestigacion);
                }
                
            } catch (SystemException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
                
            }
        }
    }
    
  
    
    public void valueChangeCentroInvestigacion(AjaxBehaviorEvent event) {
        System.out.println("Entro");
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            idCentroInvestigacion = (String) event.getComponent().getAttributes().get("value");
            itemsLineasInvestigacion = investigadorService.createMultiselectLineaInvestigacionByIdCentroInvestigacion(Integer.valueOf(idCentroInvestigacion));
        } catch (SystemException ex) {
            Logger.getLogger(RegistroInvestigadorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
//    public void eliminaDuplicados(){
//        for(LineaInvestigacion linea : listTargetLineaInvestigacion){
//            for(LineaInvestigacion lineaAux : listaLineaInvestigacion){
//                if(lineaAux.getIdLineaInvestigacion()== linea.getIdLineaInvestigacion()){
//                    listaLineaInvestigacion.remove(lineaAux);
//                    break;
//                }
//        }
//        }
//    }

    /**
     * Este metodo permite guardar al investigador
     * 
     */
    public void guardarInvestigador()  {
        try {

          
List<String> listaLineaInvestigacionSelected = new ArrayList<>();
            for (int i = 0; i< selectedLineas.length; i++){
                listaLineaInvestigacionSelected.add(selectedLineas[i]);
            }
//              listTargetLineaInvestigacion = dualListLineaInvestigacion.getTarget();
                investigadorService.saveInvestigador(investigador, listaLineaInvestigacionSelected);
                
                addDetailMessage("El investigador " + investigador.getNombreInvestigador()
                        + " se ha guardado correctamente");

                investigador = new Investigador();
               

            

        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
    
   

    /**
     * Este metodo permite guardar un investigador, llamado al service 
     
     */
    public void actualizaInvestigador() {
        try {
            List<String> listaLineaInvestigacionSelected = new ArrayList<>();
            for (int i = 0; i< selectedLineas.length; i++){
                listaLineaInvestigacionSelected.add(selectedLineas[i]);
            }
            
            
//            listTargetLineaInvestigacion = dualListLineaInvestigacion.getTarget();
            investigadorService.updateInvestigador(investigador,listaLineaInvestigacionSelected);

            addDetailMessage("El investigador " + investigador.getNombreInvestigador()
                    + " se ha actualizado correctamente");

            investigador = new Investigador();
          
        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    
/**
 * Método que elimina a un investigador
 * @throws IOException 
 */
    public void remove() throws IOException {
        if (has(investigador) && has(investigador.getIdInvestigador())) {
            try {
                investigadorService.deleteInvestigador(investigador);
                addDetailMessage("el investigador " + investigador.getNombreInvestigador()
                        + " se ha eliminado correctamente");
                externalContext.getFlash().setKeepMessages(true);
               
                externalContext.redirect("investigador.do");

            } catch (BusinessException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    public boolean isNew() {

        return investigador == null || investigador.getIdInvestigador()== 0;
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

    public Investigador getInvestigador() {
        return investigador;
    }

    public void setInvestigador(Investigador investigador) {
        this.investigador = investigador;
    }

    public String getIdInvestigador() {
        return idInvestigador;
    }

    public void setIdInvestigador(String idInvestigador) {
        this.idInvestigador = idInvestigador;
    }

//    public DualListModel<LineaInvestigacion> getDualListLineaInvestigacion() {
//        return dualListLineaInvestigacion;
//    }
//
//    public void setDualListLineaInvestigacion(DualListModel<LineaInvestigacion> dualListLineaInvestigacion) {
//        this.dualListLineaInvestigacion = dualListLineaInvestigacion;
//    }

    public List<LineaInvestigacion> getListTargetLineaInvestigacion() {
        return listTargetLineaInvestigacion;
    }

    public void setListTargetLineaInvestigacion(List<LineaInvestigacion> listTargetLineaInvestigacion) {
        this.listTargetLineaInvestigacion = listTargetLineaInvestigacion;
    }

    public List<LineaInvestigacion> getListaLineaInvestigacion() {
        return listaLineaInvestigacion;
    }

    public void setListaLineaInvestigacion(List<LineaInvestigacion> listaLineaInvestigacion) {
        this.listaLineaInvestigacion = listaLineaInvestigacion;
    }

//    public List<SelectItem> getItems() {
//        return items;
//    }
//
//    public void setItems(List<SelectItem> items) {
//        this.items = items;
//    }
//
//    public String getSelection() {
//        return selection;
//    }
//
//    public void setSelection(String selection) {
//        this.selection = selection;
//    }
//
    public String[] getSelectedLineas() {
        return selectedLineas;
    }

    public void setSelectedLineas(String[] selectedLineas) {
        this.selectedLineas = selectedLineas;
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

 

}
