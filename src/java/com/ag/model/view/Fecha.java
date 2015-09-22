/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author depazan
 */
public class Fecha {
    
    
    public Fecha() {
    }
    
    public Date getFecha(){
        return new Date();
    }
        
    public String getFechaFormato(Date fecha, String formato){
        SimpleDateFormat format= new SimpleDateFormat(formato); 
        return format.format(fecha);
    }
    
    public Date getFechaSistema(){
        SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
        String fecha=formato.format(new Date());
        Date fechaSistema = null;
        try {  
           fechaSistema=formato.parse(fecha);
        } catch (ParseException ex) {
            StackTraceElement[] stackTrace = ex.getStackTrace();
            System.out.println(stackTrace[0].toString() + " y "+ stackTrace[0].getClassName());
        }
        return fechaSistema;
    }
    
    public Date getFechaSistema(String formato){
        SimpleDateFormat format= new SimpleDateFormat(formato);
        String fecha=format.format(new Date());
        Date fechaSistema = null;
        try {  
           fechaSistema=format.parse(fecha);
        } catch (ParseException ex) {
            StackTraceElement[] stackTrace = ex.getStackTrace();
            System.out.println(stackTrace[0].toString() + " y "+ stackTrace[0].getClassName());
        }
        return fechaSistema;
    }
}
