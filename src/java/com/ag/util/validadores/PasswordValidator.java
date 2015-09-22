/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.util.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator{
 
	private static final String PASSWORD_PATTERN = "(?=^.{8,}$)(?=.*\\d)(?=.*\\W+)(?![.\\n])(?=.*[a-zA-Z]).*$";
 
	private Pattern pattern;
	private Matcher matcher;
 
	public PasswordValidator(){
		  pattern = Pattern.compile(PASSWORD_PATTERN);
	}
 
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
            
            matcher = pattern.matcher(value.toString());
            if(!matcher.matches()){

                    FacesMessage msg = 
                            new FacesMessage("Error en Formato de Contraseña.", 
                                            "La contraseña debe contener: Minimo 8 caracteres, por lo menos un Digito, un Caracter Especial y una Letra.");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);

            } 		
 
	}
}
