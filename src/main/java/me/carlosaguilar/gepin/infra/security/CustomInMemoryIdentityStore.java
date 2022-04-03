package mx.gob.edomex.icati.siicati.infra.security;

import com.github.adminfaces.template.exception.BusinessException;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import me.carlosaguilar.gepin.constantes.Constantes;
import me.carlosaguilar.gepin.dao.UsuarioDAO;
import me.carlosaguilar.gepin.model.Usuario;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.omnifaces.util.Messages;

@ApplicationScoped
public class CustomInMemoryIdentityStore implements IdentityStore {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
   

    /**
     * Valida si los datos ingresados desde el fomulario son válidos
     *
     * @param credential
     * @return
     */
    @Override
    public CredentialValidationResult validate(Credential credential) {

        try {
            UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

            Usuario usuario = new Usuario();
            usuario.setIdUsuario(login.getCaller());
            usuario.setPassword(login.getPasswordAsString());
            usuario = this.obtenUsuariologin(usuario);

            if (usuario != null) {
                return new CredentialValidationResult(usuario.getIdUsuario(), new HashSet<>(Arrays.asList(usuario.getPerfil().getPerfil())));
            } else {
                return CredentialValidationResult.INVALID_RESULT;
            }
         } catch (BusinessException ex) {
            Messages.addError(null, ex.getDetail());
            //throw new BusinessException(ex.getDetail());
            return CredentialValidationResult.INVALID_RESULT;
        }
       
       

    }

    /**
     * Obtiene los datos del usuario a traves del email ingresado desde el
     * formulario de login
     *
     * @param usuario
     * @return
     */
    public Usuario obtenUsuariologin(Usuario usuario) {

        
        usuarioDAO = new UsuarioDAO();
        Usuario usuariotemp = new Usuario();
        usuariotemp = usuarioDAO.getUserByEmail(usuario.getIdUsuario());
        
        if (usuariotemp != null) {

            
            if (usuariotemp.getIdEstatus()==(Constantes.ESTATUS_USUARIO_INACTIVO)){
                throw new BusinessException("El usuario "+usuariotemp.getIdUsuario()+" \"se encuentra inhabilitado\"");
            }
            
           
            if ((usuario.getPassword().equals(desencriptarPassword(usuariotemp.getPassword()))) && (usuariotemp.getIdEstatus() == Constantes.ESTATUS_USUARIO_ACTIVO)) {
                
                                       
              

                return usuariotemp;

            } else {

                    
                   
                    throw new BusinessException("El usuario o contraseña son incorrectos");

                

            }
        } else {
            
            throw new BusinessException("El usuario o contraseña son incorrectos");
        }

    }

    /**
     * Metodo que nos permite encriptar el password usando BASE64
     *
     * @param pass
     * @return
     */
    public String encriptarPassword(String pass) {
        String passEncriptado = "";
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(Constantes.KEY_PASSWORD_ACTIVE);
        passEncriptado = s.encrypt(pass);
        String passDe = desencriptarPassword(passEncriptado);

        return passEncriptado;

    }

    /**
     * Metodo que permite desencriptar el password
     *
     * @param passEncriptado
     * @return
     */
    public String desencriptarPassword(String passEncriptado) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(Constantes.KEY_PASSWORD_ACTIVE);
        String devuelve = "";
        try {
            devuelve = s.decrypt(passEncriptado);
        } catch (Exception e) {
        }
        return devuelve;

    }
    
    


}
