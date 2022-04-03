package me.carlosaguilar.gepin.infra.security;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.exception.BusinessException;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import me.carlosaguilar.gepin.model.Usuario;
import me.carlosaguilar.gepin.service.UsuarioService;
import static me.carlosaguilar.gepin.util.Utils.addDetailMessage;


@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    @Inject
    private AdminConfig adminConfig;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ExternalContext externalContext;

    //private String password;
    //private String email;
    private boolean remember;
    private Usuario usuario;

    private UsuarioService usuarioService;

  
  
    @PostConstruct
    public void init() {
        usuario = new Usuario();
        usuarioService = new UsuarioService();
   

    }

    /**
     * Inicia sesión a través de los datos ingresados desde el formulario del
     * login
     */
    public void login(){
//        System.err.println("Continue auht NAME ===== "+continueAuthentication().name());
//        System.err.println("Continue auht  ===== "+continueAuthentication().toString());
       // Thread.sleep(30000);
        //Faces.redirect(adminConfig.getIndexPage());
     
        switch (continueAuthentication()) {
               case SEND_CONTINUE:
                   facesContext.responseComplete();
                   usuario = usuarioService.obtenUsuariologin(usuario);
                                           
                break;

            case SEND_FAILURE:
                usuario.setPassword("");
                externalContext.getFlash().setKeepMessages(true);
                                
                break;
                
            case SUCCESS:
                externalContext.getFlash().setKeepMessages(true);
                addDetailMessage("Iniciaste sesión correctamente como " + usuario.getIdUsuario() + "");
                
                 {
                    try {
                        usuario = usuarioService.obtenUsuariologin(usuario);
                         externalContext.getSessionMap().put("userSession", usuario);

                    } catch (BusinessException ex) {
                         Logger.getLogger(LogonMB.class.getName()).log(Level.SEVERE, null, ex);
                       
                    }
                }
                 
                 
                 

                    if (remember) {
                        storeCookieCredentials(usuario.getIdUsuario(), usuario.getPassword());
                    }
                   
                        Faces.redirect(adminConfig.getIndexPage());
                       
                    
             
                     
                 
                     
                
                break;

            case NOT_DONE:
                usuario.setPassword("");
                externalContext.getFlash().setKeepMessages(true);
            break;    

        }
       
    }
    
   

    /**
     * Guarda el email y pass en session cokkies
     *
     * @param email
     * @param password
     */
    private void storeCookieCredentials(final String email, final String password) {
        Faces.addResponseCookie("admin-email", email, 900);
        Faces.addResponseCookie("admin-pass", password, 900);
    }

    /**
     * Autentifica al usuario logead
     *
     * @return
     */
    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate((HttpServletRequest) externalContext.getRequest(),
                (HttpServletResponse) externalContext.getResponse(),
                AuthenticationParameters.withParams().rememberMe(remember)
                        .credential(new UsernamePasswordCredential(this.getUsuario().getIdUsuario(), this.getUsuario().getPassword())));
    }

    @Override
    public boolean isLoggedIn() {
        return securityContext.getCallerPrincipal() != null;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getCurrentUser() {
        return securityContext.getCallerPrincipal() != null ? securityContext.getCallerPrincipal().getName() : "";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

   


    
}
