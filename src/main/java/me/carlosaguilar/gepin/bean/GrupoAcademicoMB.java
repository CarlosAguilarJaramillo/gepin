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
import me.carlosaguilar.gepin.service.GrupoAcademicoService;

import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class GrupoAcademicoMB implements Serializable {

    @Inject
    GrupoAcademicoService grupoAcademicoService;
    
      @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;
   
      
    private GrupoAcademico grupoAcademico;
    
    private GrupoAcademico grupoAcademicoInfo;
    private List<GrupoAcademico> listaGruposAcademicos;
    
    private boolean rendered;
  
   
    
    
    public GrupoAcademicoMB() {
    }

    @PostConstruct
    public void init() {

        try {
            rendered = false;
            grupoAcademicoInfo = new GrupoAcademico();
            
            grupoAcademico = new GrupoAcademico();
            listaGruposAcademicos = new ArrayList<>();
            listaGruposAcademicos = grupoAcademicoService.getAllGruposAcademicos();
            
        } catch (SystemException ex) {
            Logger.getLogger(GrupoAcademicoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Este método nos permite redirigirnos a la actualización de un grupo academico a través de su ID
 */
    public void editOutcome() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroGrupoAcademico.do?idGrupoAcademico=" + params.get("idGrupoAcademico"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(GrupoAcademicoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
      @Transactional
    public void eliminaGrupoAcademico(GrupoAcademico grupo) {
        if (grupo!=null){
            grupoAcademicoService.deleteGrupoAcademico(grupo);
            listaGruposAcademicos.remove(grupo);
           
        }
        addDetailMessage("Grupo académico eliminado satisfactoriamente");
        
    }

     public void editGrupoAcademico() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroGrupoAcademico.do?idGrupoAcademico=" + params.get("idGrupoAcademico"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(GrupoAcademicoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
/**
 * Muestra una tabla en la vista de los datos del grupo académico seleccionado
     * @param grupoAc
 */
    public void verGrupoAcademicoSeleccionado(GrupoAcademico grupoAc) {
        rendered = true;
        this.setGrupoAcademicoInfo(grupoAc);

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

    public GrupoAcademico getGrupoAcademico() {
        return grupoAcademico;
    }

    public void setGrupoAcademico(GrupoAcademico grupoAcademico) {
        this.grupoAcademico = grupoAcademico;
    }

    public GrupoAcademico getGrupoAcademicoInfo() {
        return grupoAcademicoInfo;
    }

    public void setGrupoAcademicoInfo(GrupoAcademico grupoAcademicoInfo) {
        this.grupoAcademicoInfo = grupoAcademicoInfo;
    }

    public List<GrupoAcademico> getListaGruposAcademicos() {
        return listaGruposAcademicos;
    }

    public void setListaGruposAcademicos(List<GrupoAcademico> listaGruposAcademicos) {
        this.listaGruposAcademicos = listaGruposAcademicos;
    }

   

    
  
}
