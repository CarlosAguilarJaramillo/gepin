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
import me.carlosaguilar.gepin.model.LineaInvestigacion;
import me.carlosaguilar.gepin.model.RelCentroInvGrupoAc;
import me.carlosaguilar.gepin.model.RelGrupoAcLineaInv;
import me.carlosaguilar.gepin.service.CentroInvestigacionService;
import me.carlosaguilar.gepin.service.GrupoAcademicoService;
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
public class RegistroLineaInvestigacionMB implements Serializable {

    @Inject
    LineaInvestigacionService lineaInvestigacionService;
    
     @Inject
     GrupoAcademicoService grupoAcademicoService;

  

    @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AdminConfig adminConfig;

    @Inject
    private LogonMB logonMB;

    private LineaInvestigacion lineaInvestigacion;
    
    private String idLineaInvestigacion;
    
    
    
    private DualListModel<GrupoAcademico> dualListGrupoAcademico;
   private List<GrupoAcademico> listTargetGrupoAcademico;
   private List<GrupoAcademico> listaGrupoAcademico;

    @PostConstruct
    public void init() {
       
        lineaInvestigacion = new LineaInvestigacion();
        listaGrupoAcademico = new ArrayList<>();
        listTargetGrupoAcademico = new ArrayList();
        try {
            listaGrupoAcademico = grupoAcademicoService.getAllGruposAcademicos();
            dualListGrupoAcademico = new DualListModel<>(listaGrupoAcademico, listTargetGrupoAcademico);
        } catch (SystemException ex) {
            Logger.getLogger(RegistroLineaInvestigacionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  
    public void postLoad() throws IOException {

        if (Faces.isAjaxRequest()) {
            
            return;
        }
        if (has(idLineaInvestigacion)) {
            try {
                lineaInvestigacion = lineaInvestigacionService.getLineaInvestogacionById(Integer.valueOf(idLineaInvestigacion));
                if (lineaInvestigacion == null) {
                    externalContext.getFlash().setKeepMessages(true);
                    addDetailMessage("La línea de investigación seleccionada no existe.", FacesMessage.SEVERITY_ERROR);
                    externalContext.redirect("grupoAcademico.do");
                   
                    
                }else{
                    List<RelGrupoAcLineaInv> listaRelacion = new ArrayList<>(lineaInvestigacion.getRelGrupoAcLineaInvs());
                     listTargetGrupoAcademico = new ArrayList<>();
                    for (RelGrupoAcLineaInv ac: listaRelacion){
                       
                        listTargetGrupoAcademico.add(ac.getGrupoAcademico());
                        
                    }
                    
                    this.eliminaDuplicados();
                    
                    
                    
                    dualListGrupoAcademico = new DualListModel<>(listaGrupoAcademico, listTargetGrupoAcademico);
                }
                
            } catch (SystemException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
                
            }
        }
    }
    
    public void eliminaDuplicados(){
        for(GrupoAcademico grupo : listTargetGrupoAcademico){
            for(GrupoAcademico grupoAux : listaGrupoAcademico){
                if(grupoAux.getIdGrupoAcademico()== grupo.getIdGrupoAcademico()){
                    listaGrupoAcademico.remove(grupoAux);
                    break;
                }
        }
        }
    }

    /**
     * Este metodo permite guardar la linea de investigacion
     * 
     */
    public void guardarListaInvestigacion()  {
        try {

          

              listTargetGrupoAcademico = dualListGrupoAcademico.getTarget();
                lineaInvestigacionService.saveLineaInvestigacion(lineaInvestigacion, listTargetGrupoAcademico);
                
                addDetailMessage("La línea de investigación " + lineaInvestigacion.getLineaInvestigacion()
                        + " se ha guardado correctamente");

                lineaInvestigacion = new LineaInvestigacion();
               

            

        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }
    
   

    /**
     * Este metodo permite guardar la linea de investigacion, llamado al service 
     
     */
    public void actualizaLineaInvestigacion() {
        try {

            listTargetGrupoAcademico = dualListGrupoAcademico.getTarget();
            lineaInvestigacionService.updateLineaInvestigacion(lineaInvestigacion,listTargetGrupoAcademico);

            addDetailMessage("La línea de investigación " + lineaInvestigacion.getLineaInvestigacion()
                    + " se ha actualizado correctamente");

            lineaInvestigacion = new LineaInvestigacion();
          
        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    
/**
 * Método que elimina a una linea de investigacion
 * @throws IOException 
 */
    public void remove() throws IOException {
        if (has(lineaInvestigacion) && has(lineaInvestigacion.getIdLineaInvestigacion())) {
            try {
                lineaInvestigacionService.deleteLineaInvestigacion(lineaInvestigacion);
                addDetailMessage("La línea de investigación " + lineaInvestigacion.getLineaInvestigacion()
                        + " se ha eliminado correctamente");
                externalContext.getFlash().setKeepMessages(true);
               
                externalContext.redirect("lineaInvestigacion.do");

            } catch (BusinessException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    public boolean isNew() {

        return lineaInvestigacion == null || lineaInvestigacion.getIdLineaInvestigacion()== 0;
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

    public LineaInvestigacion getLineaInvestigacion() {
        return lineaInvestigacion;
    }

    public void setLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }

    public String getIdLineaInvestigacion() {
        return idLineaInvestigacion;
    }

    public void setIdLineaInvestigacion(String idLineaInvestigacion) {
        this.idLineaInvestigacion = idLineaInvestigacion;
    }

    public DualListModel<GrupoAcademico> getDualListGrupoAcademico() {
        return dualListGrupoAcademico;
    }

    public void setDualListGrupoAcademico(DualListModel<GrupoAcademico> dualListGrupoAcademico) {
        this.dualListGrupoAcademico = dualListGrupoAcademico;
    }

    public List<GrupoAcademico> getListTargetGrupoAcademico() {
        return listTargetGrupoAcademico;
    }

    public void setListTargetGrupoAcademico(List<GrupoAcademico> listTargetGrupoAcademico) {
        this.listTargetGrupoAcademico = listTargetGrupoAcademico;
    }

    public List<GrupoAcademico> getListaGrupoAcademico() {
        return listaGrupoAcademico;
    }

    public void setListaGrupoAcademico(List<GrupoAcademico> listaGrupoAcademico) {
        this.listaGrupoAcademico = listaGrupoAcademico;
    }

   
    

}
