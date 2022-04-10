package me.carlosaguilar.gepin.bean;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.exception.BusinessException;
import static com.github.adminfaces.template.util.Assert.has;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.transaction.SystemException;
import me.carlosaguilar.gepin.infra.security.LogonMB;
import me.carlosaguilar.gepin.model.Perfil;
import me.carlosaguilar.gepin.model.Usuario;
import me.carlosaguilar.gepin.service.PerfilService;
import me.carlosaguilar.gepin.service.UsuarioService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

/**
 * Created by Carlos Aguilar on 25/06/2019.
 */
@Named
@ViewScoped
public class RegistroUsuarioMB implements Serializable {

    @Inject
    UsuarioService usuarioService;

    @Inject
    PerfilService perfilService;

    @Inject
    private ExternalContext externalContext;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AdminConfig adminConfig;

    @Inject
    private LogonMB logonMB;

    private Usuario usuario;
    private Usuario usuarioDuplicado;
    private String idUsuario;
    private List<SelectItem> itemsPerfil;
    private List<SelectItem> itemsEstatusUsuarios;
    private Integer idPerfil;
    private Integer idEstatus;
    private Boolean req;
    private String token;
    private boolean tokenValid;
    private String tokenMessageError;
    private List<SelectItem> itemsEdayo;
    private Integer idEdayo;
    private String email;

    @PostConstruct
    public void init() {
        req = false;
        usuario = new Usuario();
        usuarioDuplicado = new Usuario();
        //email = null; 

        //usuarioDuplicado= new 
//        bitacoraService = new BitacoraService();
//        usuarioServiceResetPassword = new UsuarioServiceResetPassword();
//        itemsEdayo = catEdayoService.getAllCatEdayoItems();
    }

    /**
     * Valida si el idCatUsuario esta nulo o no
     *
     * @throws java.io.IOException
     */
    public void postLoad() throws IOException {

        try {
            //            listaEdayo = new ArrayList();
//            edayoTarget = new ArrayList();
//            listaEdayo = catEdayoService.getAllCatEdayo();
//
            itemsPerfil = perfilService.getItemsCatPerfil();
//            itemsEdayo = catEdayoService.getAllCatEdayoItems();
            itemsEstatusUsuarios = usuarioService.getItemStatusForUser();

            if (Faces.isAjaxRequest()) {

                return;
            }
            if (has(idUsuario)) {
                try {
                    usuario = usuarioService.getUserByEmail(idUsuario);
                    if (usuario == null) {
                        externalContext.getFlash().setKeepMessages(true);
                        addDetailMessage("El usuario seleccionado no existe.", FacesMessage.SEVERITY_ERROR);
                        externalContext.redirect("usuarios.do");
                    } else {
                        idPerfil = usuario.getPerfil().getIdPerfil();
                        idEstatus = usuario.getIdEstatus();
//                    idEdayo = usuario.getCatEdayo().getEdyEdayoId();
                        email = usuario.getIdUsuario();
                        req = false;
//                    listaEdayosActivos = relCatEdayoUsuarioService.getEdayoUsuarioActivos(usuario);
//                    edayoTarget = relCatEdayoUsuarioService.getInsCatEdayoUsuario(usuario);
//                    listaEdayoEliminar = listaEdayosActivos;
//                    eliminaCatEdayosSeleccionados();
                    }

                } catch (SystemException ex) {
                    addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);

                }
            } else {
                usuario = new Usuario();
                req = true;
            }
//        listEdayo = new DualListModel<CatEdayo>(listaEdayo, edayoTarget);
        } catch (SystemException ex) {
            Logger.getLogger(RegistroUsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este metodo permite guardar el usuario, llamado al service UsuarioService
     * y guarda en la bitacora
     */
    public void guardarUsuario() throws SystemException {
        try {

            usuarioDuplicado = usuarioService.getUserByEmail(email);
            if (usuarioDuplicado == null) {

                usuario.setIdUsuario(email);
                //quitamos los espacios en blanco
                trimUsuario();

                Perfil perfil = new Perfil();
                perfil.setIdPerfil(idPerfil);
                usuario.setPerfil(perfil);

                usuario.setIdEstatus(idEstatus);
                usuario.setFechaAlta(new Date());

////                CatEdayo catEdayo = new CatEdayo();
////                catEdayo.setEdyEdayoId(idEdayo);
////                usuario.setCatEdayo(catEdayo);
                usuarioService.saveUser(usuario);

                addDetailMessage("El usuario " + usuario.getIdUsuario()
                        + " se ha guardado correctamente");

                usuario = new Usuario();
                usuarioDuplicado = new Usuario();
                idPerfil = null;
                idEstatus = null;
                email = null;

            } else {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, " ", "Ya se encuentra Registrado el correo  " + usuarioDuplicado.getIdUsuario()));
                usuarioDuplicado = new Usuario();
                email = null;
            }

        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }

    /**
     * Este metodo permite guardar el usuario, llamado al service UsuarioService
     * y guarda en la bitacora
     */
    public void actualizarUsuario() {
        try {

            Perfil perfil = new Perfil();
            perfil.setIdPerfil(idPerfil);
            usuario.setPerfil(perfil);

            usuario.setIdEstatus(idEstatus);

////                
            trimUsuario();

            usuarioService.updateUser(usuario, req);

            addDetailMessage("El usuario " + usuario.getIdUsuario()
                    + " se ha actualizado correctamente");

            usuario = new Usuario();
            idPerfil = null;
            idEstatus = null;
            email = null;
            itemsEdayo = null;
            idEdayo = null;

        } catch (BusinessException ex) {
            throw new BusinessException(ex.getMessage());
        }
    }


    public void remove() throws IOException {
        if (has(usuario) && has(usuario.getIdUsuario())) {
            try {
                usuarioService.deleteUser(usuario);
                addDetailMessage("El usuario se ha eliminado correctamente");
                externalContext.getFlash().setKeepMessages(true);
               
                externalContext.redirect("usuarios.do");

            } catch (BusinessException ex) {
                addDetailMessage(ex.getLocalizedMessage(), FacesMessage.SEVERITY_ERROR);
            }
        }
    }
    public boolean isNew() {

        return usuario == null || usuario.getIdUsuario() == null || email == null;
    }

    public void trimUsuario() {
       
        String nombre = usuario.getNombre().toUpperCase().trim();
     
        usuario.setNombre(usuario.getNombre().toUpperCase().trim());
        usuario.setApellidoPaterno(usuario.getApellidoPaterno().toUpperCase().trim());
        usuario.setApellidoMaterno(usuario.getApellidoMaterno().toUpperCase().trim());
        usuario.setIdUsuario(usuario.getIdUsuario().trim());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<SelectItem> getItemsPerfil() {
        return itemsPerfil;
    }

    public void setItemsPerfil(List<SelectItem> itemsPerfil) {
        this.itemsPerfil = itemsPerfil;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public List<SelectItem> getItemsEstatusUsuarios() {
        return itemsEstatusUsuarios;
    }

    public void setItemsEstatusUsuarios(List<SelectItem> itemsEstatusUsuarios) {
        this.itemsEstatusUsuarios = itemsEstatusUsuarios;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Boolean getReq() {
        return req;
    }

    public void setReq(Boolean req) {
        this.req = req;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isTokenValid() {
        return tokenValid;
    }

    public void setTokenValid(boolean tokenValid) {
        this.tokenValid = tokenValid;
    }

    public String getTokenMessageError() {
        return tokenMessageError;
    }

    public void setTokenMessageError(String tokenMessageError) {
        this.tokenMessageError = tokenMessageError;
    }

    public List<SelectItem> getItemsEdayo() {
        return itemsEdayo;
    }

    public void setItemsEdayo(List<SelectItem> itemsEdayo) {
        this.itemsEdayo = itemsEdayo;
    }

    public Integer getIdEdayo() {
        return idEdayo;
    }

    public void setIdEdayo(Integer idEdayo) {
        this.idEdayo = idEdayo;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
