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

@FacesValidator("codigoUsuarioValidator")
public class CodigoUsuarioValidator implements Validator{
 
	private static final String ALFABETICO_PATTERN = "^[A-Za-z0-9]+$";
 
	private Pattern pattern;
	private Matcher matcher;
 
	public CodigoUsuarioValidator(){
		  pattern = Pattern.compile(ALFABETICO_PATTERN);
	}
 
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
            
            matcher = pattern.matcher(value.toString());
            if(!matcher.matches()){

                    FacesMessage msg = 
                            new FacesMessage("Error en Codigo de Usuario", 
                                            "Sólo Valores Alfanumericos");
                    msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                    throw new ValidatorException(msg);

            } 		
 
	}
}
