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
@FacesValidator("CURPValidator")
public class CURPValidator implements Validator{
    
    private static final String CURP_PATTERN = "^([A-Z&]|[a-z&]{1})([AEIOU]|[aeiou]{1})([A-Z&]|[a-z&]{1})([A-Z&]|[a-z&]{1})([0-9]{2})(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])([HM]|[hm]{1})([AS|as|BC|bc|BS|bs|CC|cc|CS|cs|CH|ch|CL|cl|CM|cm|DF|df|DG|dg|GT|gt|GR|gr|HG|hg|JC|jc|MC|mc|MN|mn|MS|ms|NT|nt|NL|nl|OC|oc|PL|pl|QT|qt|QR|qr|SP|sp|SL|sl|SR|sr|TC|tc|TS|ts|TL|tl|VZ|vz|YN|yn|ZS|zs|NE|ne]{2})([^A|a|E|e|I|i|O|o|U|u]{1})([^A|a|E|e|I|i|O|o|U|u]{1})([^A|a|E|e|I|i|O|o|U|u]{1})([A-Z&]|[a-z&]|[0-9&]{1})([0-9]{1})$";

    private Pattern pattern;
	private Matcher matcher;
 
	public CURPValidator(){
		  pattern = Pattern.compile(CURP_PATTERN);
	}
    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null){
        matcher = pattern.matcher(value.toString());
		if(!matcher.matches()){ 
			FacesMessage msg = new FacesMessage("Formato de CURP incorrecta.", 
						"CURP incorrecta.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                        if (!value.toString().toUpperCase().equals("S/D")){
			throw new ValidatorException(msg);
                        }
 
		}
        }
    }
}
 
	