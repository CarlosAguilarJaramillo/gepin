package me.carlosaguilar.gepin.util;

import org.omnifaces.util.Messages;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import me.carlosaguilar.gepin.constantes.Constantes;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Created by rmpestano on 07/02/17.
 */
@ApplicationScoped
public class Utils implements Serializable {

    @PostConstruct
    public void init() {

    }

    public static void addDetailMessage(String message) {
        addDetailMessage(message, null);
    }

    public static void addDetailMessage(String message, FacesMessage.Severity severity) {

        FacesMessage facesMessage = Messages.create("").detail(message).get();
        if (severity != null && severity != FacesMessage.SEVERITY_INFO) {
            facesMessage.setSeverity(severity);
        }
        Messages.add(null, facesMessage);
    }

   
    

    /**
     * Metodo que nos permite encriptar el password usando BASE64
     *
     * @param pass
     * @return
     */
    public static String encriptarPassword(String pass) {
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
    public static String desencriptarPassword(String passEncriptado) {
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
