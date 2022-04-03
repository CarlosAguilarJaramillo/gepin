/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.carlosaguilar.gepin.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *Esta clase permite crear un FacesValidator para validar la estructura correcta de un email
 * @author CarlosAguilar
 */
@FacesValidator("Emailvalidator")
public class EmailValidator implements Validator{
    
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_\\-\\.~]{2,}@[a-zA-Z0-9_\\-\\.~]{2,}\\.[a-zA-Z]{2,4}$";

    private Pattern pattern;
	private Matcher matcher;
 
	public EmailValidator(){
		  pattern = Pattern.compile(EMAIL_PATTERN);
	}
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null){
        matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){ 
			FacesMessage msg = new FacesMessage("Formato de EMAIL incorrecto.", 
						"EMAIL incorrecto.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        if (!value.toString().toUpperCase().equals("S/D")){
			throw new ValidatorException(msg);
                        }
 
		}
        }
    }
}
 
	