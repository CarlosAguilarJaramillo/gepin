package me.carlosaguilar.gepin.bean;

import com.github.adminfaces.template.exception.BusinessException;
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
import me.carlosaguilar.gepin.constantes.Constantes;
import me.carlosaguilar.gepin.model.Usuario;

import me.carlosaguilar.gepin.service.UsuarioService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class UsuarioMB implements Serializable {

    @Inject
    UsuarioService usuarioService;
    
      @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;
   
      
    private Usuario usuario;
    
    private Usuario usuarioInfo;
    private List<Usuario> listaUsuarios;
    private List<Usuario> selectedUsuarios;
    private boolean rendered;
    private Integer idEstatus;
   
    
    
    public UsuarioMB() {
    }

    @PostConstruct
    public void init() {

        try {
            rendered = false;
            usuarioInfo = new Usuario();
            selectedUsuarios = new ArrayList<>();
            usuario = new Usuario();
            listaUsuarios = new ArrayList<>();
            listaUsuarios = usuarioService.getAllUsers();
            
        } catch (SystemException ex) {
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
/**
 * Este método nos permite redirigirnos a la actualización de usuarios a través de su ID
 */
    public void editOutcome() {
        try {
            Map<String, String> params = externalContext.getRequestParameterMap();
            externalContext.redirect("registroUsuarios.do?idUsuario=" + params.get("idUsuario"));
            
            //Faces.redirect();
        } catch (IOException ex) {
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

   
/**
 * Muestra una tabla en la vista de los datos del usuario seleccionado
 */
    public void verUsuarioSeleccionado() {
        try {
            if (selectedUsuarios.size() > 1) {
                rendered = false;
                throw new SystemException("Solo se puede visualizar un usuario a la vez");

            } else {
                rendered = true;
                for (Usuario user : selectedUsuarios) {
                    idEstatus = user.getIdEstatus();
                    this.setUsuarioInfo(user);

                }
            }
        } catch (SystemException ex) {
            addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            usuarioInfo = new Usuario();
            rendered = false;
        }

    }

    
    

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioInfo() {
        return usuarioInfo;
    }

    public void setUsuarioInfo(Usuario usuarioInfo) {
        this.usuarioInfo = usuarioInfo;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Usuario> getSelectedUsuarios() {
        return selectedUsuarios;
    }

    public void setSelectedUsuarios(List<Usuario> selectedUsuarios) {
        this.selectedUsuarios = selectedUsuarios;
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }


    
  
}
