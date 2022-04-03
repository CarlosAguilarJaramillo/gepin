package me.carlosaguilar.gepin.infra.security;

import org.omnifaces.util.Faces;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Specializes;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.template.util.Assert.has;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import me.carlosaguilar.gepin.constantes.Constantes;

import me.carlosaguilar.gepin.service.UsuarioService;

@RequestScoped
@Specializes
public class LogoutMB extends com.github.adminfaces.template.security.LogoutMB implements Serializable {
 
/**
 * Redireccion al inicio del sistema y borra la sesion del usuario
 * @throws IOException 
 */
  
        
    @Override
    public void doLogout() throws IOException {
      
        if(has(Faces.getRequestCookie("admin-email"))) {
            Faces.removeResponseCookie("admin-email",null);
            Faces.removeResponseCookie("admin-pass",null);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//       
        
        super.doLogout();
          
    }
    
    
   
    
}