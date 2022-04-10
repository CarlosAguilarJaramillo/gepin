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
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.infra.security.LogonMB;
import me.carlosaguilar.gepin.model.CentroInvestigacion;
import me.carlosaguilar.gepin.model.GrupoAcademico;
import me.carlosaguilar.gepin.model.RelCentroInvGrupoAc;
import me.carlosaguilar.gepin.service.CentroInvestigacionService;
import me.carlosaguilar.gepin.service.GrupoAcademicoService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.primefaces.model.DualListModel;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class RegistroGrupoAcademicoMB implements Serializable {

    @Inject
    GrupoAcademicoService grupoAcademicoService;
    
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

    private GrupoAcademico grupoAcademico;
    
    private String idGrupoAcademico;
    
    
    
    private DualListModel<CentroInvestigacion> dualListCentrosInvestigacion;
   private List<CentroInvestigacion> listTargetCentrosInvestigacion;
   private List<CentroInvestigacion> listaCentrosInvestigacion;

    @PostConstruct
    public void init() {
       
        grupoAcademico = new GrupoAcademico();
        listaCentrosInvestigacion = new ArrayList<>();
        listTargetCentrosInvestigacion = new ArrayList();
        try {
            listaCentrosInvestigacion = centroInvestigacionService.getAllCentrosInvestigacion();
            dualListCentrosInvestigacion = new DualListModel<>(listaCentrosInvestigacion, listTargetCentrosInvestigacion);
        } catch (SystemException ex) {
            Logger.getLogger(RegistroGrupoAcademicoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  
    public void postLoad() throws IOException {

        if (Faces.isAjaxRequest()) {
            
            return;
        }
        if (has(idGrupoAcademico)) {
            try {
                grupoAcademico = grupoAcademicoService.getGrupoAcademicoById(Integer.valueOf(idGrupoAcademico));
                if (grupoAcademico == null) {
                    externalContext.getFlash().setKeepMessages(true);
                    addDetailMessage("El grupo académico seleccionado no existe.", FacesMessage.SEVERITY_ERROR);
                    externalContext.redirect("grupoAcademico.do");
                   
                    
                }else{
                    List<RelCentroInvGrupoAc> listaRelacion = new ArrayList<>(grupoAcademico.getRelCentroInvGrupoAcs());
                     listTargetCentrosInvestigacion = new ArrayList<>();
                    for (RelCentroInvGrupoAc ac: listaRelacion){
                       
                        listTargetCentrosInvestigacion.add(ac.getCentroInvestigacion());
                        
                    }
                    
                    this.eliminaDuplicados();
                    
                    
                    
                    dualListCentrosInvestigacion = new DualListModel<>(listaCentrosInvestigacion, listTargetCentrosInvestigacion);
                }
                
            } catch (SystemException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
                
            }
        }
    }
    
    public void eliminaDuplicados(){
        for(CentroInvestigacion cen : listTargetCentrosInvestigacion){
            for(CentroInvestigacion cenAux : listaCentrosInvestigacion){
                if(cenAux.getIdCentroInvestigacion() == cen.getIdCentroInvestigacion()){
                    listaCentrosInvestigacion.remove(cenAux);
                    break;
                }
        }
        }
    }

    /**
     * Este metodo permite guardar el grupo académico
     * 
     */
    public void guardarGrupoAcademico()  {
        try {

          

              listTargetCentrosInvestigacion = dualListCentrosInvestigacion.getTarget();
                grupoAcademicoService.saveGrupoAcademico(grupoAcademico, listTargetCentrosInvestigacion);
                
                addDetailMessage("El grupo académico " + grupoAcademico.getGrupoAcademico()
                        + " se ha guardado correctamente");

                grupoAcademico = new GrupoAcademico();
               

            

        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
    
   

    /**
     * Este metodo permite guardar el grupo academico, llamado al service 
     * y guarda en la bitacora
     */
    public void actualizaGrupoAcademico() {
        try {

            listTargetCentrosInvestigacion = dualListCentrosInvestigacion.getTarget();
            grupoAcademicoService.updateGrupoAcademico(grupoAcademico,listTargetCentrosInvestigacion);

            addDetailMessage("El grupo académico " + grupoAcademico.getGrupoAcademico()
                    + " se ha actualizado correctamente");

            grupoAcademico = new GrupoAcademico();
          
        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    
/**
 * Método que elimina a un grupo academido
 * @throws IOException 
 */
    public void remove() throws IOException {
        if (has(grupoAcademico) && has(grupoAcademico.getIdGrupoAcademico())) {
            try {
                grupoAcademicoService.deleteGrupoAcademico(grupoAcademico);
                addDetailMessage("El grupo académico " + grupoAcademico.getGrupoAcademico()
                        + " se ha eliminado correctamente");
                externalContext.getFlash().setKeepMessages(true);
               
                externalContext.redirect("grupoAcademico.do");

            } catch (BusinessException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    public boolean isNew() {

        return grupoAcademico == null || grupoAcademico.getIdGrupoAcademico()== 0;
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

    public GrupoAcademico getGrupoAcademico() {
        return grupoAcademico;
    }

    public void setGrupoAcademico(GrupoAcademico grupoAcademico) {
        this.grupoAcademico = grupoAcademico;
    }

    public String getIdGrupoAcademico() {
        return idGrupoAcademico;
    }

    public void setIdGrupoAcademico(String idGrupoAcademico) {
        this.idGrupoAcademico = idGrupoAcademico;
    }

    public DualListModel<CentroInvestigacion> getDualListCentrosInvestigacion() {
        return dualListCentrosInvestigacion;
    }

    public void setDualListCentrosInvestigacion(DualListModel<CentroInvestigacion> dualListCentrosInvestigacion) {
        this.dualListCentrosInvestigacion = dualListCentrosInvestigacion;
    }

    public List<CentroInvestigacion> getListaCentrosInvestigacion() {
        return listaCentrosInvestigacion;
    }

    public void setListaCentrosInvestigacion(List<CentroInvestigacion> listaCentrosInvestigacion) {
        this.listaCentrosInvestigacion = listaCentrosInvestigacion;
    }

  

    public List<CentroInvestigacion> getListTargetCentrosInvestigacion() {
        return listTargetCentrosInvestigacion;
    }

    public void setListTargetCentrosInvestigacion(List<CentroInvestigacion> listTargetCentrosInvestigacion) {
        this.listTargetCentrosInvestigacion = listTargetCentrosInvestigacion;
    }

    

}
