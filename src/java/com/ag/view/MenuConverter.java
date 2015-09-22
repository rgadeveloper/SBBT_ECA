/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.Menu;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
 
@FacesConverter("menuConverter")
public class MenuConverter implements Converter {
 
    //private static final Logger LOG = LoggerFactory.getLogger(MenuConverter.class);
 
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        //LOG.trace("String value: {}", value);
 
        return getObjectFromUIPickListComponent(component,value);
    }
 
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string;
        //LOG.trace("Object value: {}", object);
        if(object == null){
            string="";
        }else{
            try{
                string = String.valueOf(((Menu)object).getCodigo());
            }catch(ClassCastException cce){
                throw new ConverterException();
            }
        }
        return string;
    }
 
    @SuppressWarnings("unchecked")
    private Menu getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Menu> dualList;
        try{
            dualList = (DualListModel<Menu>) ((PickList)component).getValue();
            Menu menu = getObjectFromList(dualList.getSource(),Integer.valueOf(value));
            if(menu==null){
                menu = getObjectFromList(dualList.getTarget(),Integer.valueOf(value));
            }
             
            return menu;
        }catch(ClassCastException cce){
            throw new ConverterException();
        }catch(NumberFormatException nfe){
            throw new ConverterException();
        }
    }
 
    private Menu getObjectFromList(final List<?> list, final Integer identifier) {
        for(final Object object:list){
            final Menu menu = (Menu) object;
            if(menu.getCodigo().equals(identifier)){
                return menu;
            }
        }
        return null;
    }
}
