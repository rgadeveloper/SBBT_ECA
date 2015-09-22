/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;


import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("currencyFormat")
public class CurrencyFormat implements Converter {

@Override
public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
    return null;
}

@Override
public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    if (value == null) {
        return null;
    } else {
        if (value.toString().trim().equals("")) {
            return null;
        }
        try {
            Locale l=new Locale("es_CO");
            NumberFormat format = NumberFormat.getInstance(l);
            format.setMinimumFractionDigits(2);
            return format.format(new BigDecimal(value.toString()));

        } catch (Exception exception) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Number"));
        }
    }
}

}
