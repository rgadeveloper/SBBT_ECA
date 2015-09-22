/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.view;

import com.ag.model.Color;
import com.ag.model.RangoClasificacion;
import com.ag.model.TipoComponente;
import com.ag.service.ConsecutivoManager;
import com.ag.service.RangoManager;
import com.ag.service.SpringContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;   



/**
 *
 * @author arodriguezr
 */

@ManagedBean
@SessionScoped
public class RangoView implements Serializable{
    
    private SpringContext context; 
    private RangoManager rangoManager;
    private List<RangoClasificacion> rangos;
    private List<Color> colores;
    private List<TipoComponente> tipoComponentes;    
    private RangoClasificacion rangoSelected;
    private Color colorSelected;
    private String colorHEX;
    private TipoComponente tipoComponentesSelected;
    private long codigoColor;
    private long idColor;
    private String codTipo;
    private String descripcion;
    private String descripcionColor;
    private BigDecimal idRango;
    private BigDecimal porcMinimo;
    private BigDecimal porcMaximo;   
      
    public RangoView() {
        context = SpringContext.getInstance();
        rangoManager= (RangoManager) context.getBean("RangoManager"); 
        setRangos(rangoManager.getRangosAll());
        setColores(rangoManager.getColoresAll());
        setTipoComponentes(rangoManager.getTiposComponentesAll());
        ClearCampos();
    }    
    
    public void saveColor(String usuario, String programa){
        try {
            short rojo, verde, azul;  
            rojo =Short.valueOf(colorHEX.substring(0, 2), 16);
            verde =Short.valueOf(colorHEX.substring(2, 4), 16);
            azul =Short.valueOf(colorHEX.substring(4, 6), 16);
            
            if (!rangoManager.existeColor(descripcionColor, rojo, verde, azul)) {
                ConsecutivoManager app = (ConsecutivoManager) context.getBean("ConsecutivoManager");
                String consecutivo = app.getConsecutivo("COLOR", "MIGRACION", "AUTOMATICO", "A", "0");
                idColor = Long.parseLong(consecutivo);
                rangoManager.saveColor(usuario, programa, idColor, descripcionColor, rojo, verde, azul);
                setColores(rangoManager.getColoresAll());
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "El Color o su Descripcion ya existe.", ""));
            } 
            idColor= 0;
            colorHEX=null;
            descripcionColor=null;   
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage
                 (null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                 "Ha ocurrido un error en el sistema al crear el color", "")); 
        }
    }    
       
    public void advertenciaCambioTipo() { 
        if (!codTipo.equals("no"))
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Este cambio puede causar error en el Sistema", ""));
    }
    
    public void editRango(String usuario, String programa){
        try {
            Short codTipoShort;
            if (codTipo.equals("no")) {
                codTipoShort = rangoSelected.getTipoComponente().getIdTipoComponente();
            } else {
                codTipoShort = Short.valueOf(codTipo);
            }
            
            if (!rangoManager.existeRango(Short.toString(codTipoShort), descripcion)) {
                rangoSelected.setUsuario(usuario);
                rangoSelected.setPrograma(programa);
                rangoManager.edit(rangoSelected, codTipo, codigoColor, descripcion);               
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Rango Editado Correctamente", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "El Rango ya existe.", ""));
            }
            ClearCampos();            
            
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el sistema al editar Rango", ""));
        } 
    }

    public void saveRango(String usuario, String programa) {
        try {
            if (!rangoManager.existeRango(codTipo, descripcion)) {
                ConsecutivoManager app = (ConsecutivoManager) context.getBean("ConsecutivoManager");
                String consecutivo = app.getConsecutivo("RANGO_CLASIFICACION", "MIGRACION", "AUTOMATICO", "A", "0");
                idRango = new BigDecimal(consecutivo);
                Short codTipoShort = Short.valueOf(codTipo);
                rangoManager.save(usuario, programa, idRango, porcMinimo, porcMaximo, descripcion, codTipoShort, codigoColor);

                //setColores(rangoManager.getColoresAll());
                //setTipoComponentes(rangoManager.getTiposComponentesAll());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Rango creado correctamente", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "El Rango ya existe.", ""));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ha ocurrido un error en el Sistema.", ""));
        } finally {
            ClearCampos();
            setRangos(rangoManager.getRangosAll());
        }
    }
    
    public void ClearCampos(){
        codigoColor=0;
        codTipo=null;
        idRango= null;
        porcMinimo=null;
        porcMaximo=null;
        descripcion=null;
    }
    
    public void msgExito(String msj){
     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"", msj));   
    }

    public RangoClasificacion getRangoSelected() {
        return rangoSelected;
    }

    public void setRangoSelected(RangoClasificacion rangoSelected) {
        this.rangoSelected = rangoSelected;
    }

    public List<RangoClasificacion> getRangos() {
        return rangos;
    }

    public void setRangos(List<RangoClasificacion> rangos) {
        this.rangos = rangos;
    }

    public List<Color> getColores() {
        return colores;
    }

    public void setColores(List<Color> colores) {
        this.colores = colores;
    }

    public List<TipoComponente> getTipoComponentes() {
        return tipoComponentes;
    }

    public void setTipoComponentes(List<TipoComponente> tipoComponentes) {
        this.tipoComponentes = tipoComponentes;
    }

    public Color getColorSelected() {
        return colorSelected;
    }

    public void setColorSelected(Color colorSelected) {
        this.colorSelected = colorSelected;
    }

    public TipoComponente getTipoComponentesSelected() {
        return tipoComponentesSelected;
    }

    public void setTipoComponentesSelected(TipoComponente tipoComponentesSelected) {
        this.tipoComponentesSelected = tipoComponentesSelected;
    }

    public long getCodigoColor() {
        return codigoColor;
    }

    public void setCodigoColor(long codigoColor) {
        this.codigoColor = codigoColor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getIdRango() {
        return idRango;
    }

    public void setIdRango(BigDecimal idRango) {
        this.idRango = idRango;
    }

    public BigDecimal getPorcMaximo() {
        return porcMaximo;
    }

    public void setPorcMaximo(BigDecimal porcMaximo) {
        this.porcMaximo = porcMaximo;
    }

    public BigDecimal getPorcMinimo() {
        return porcMinimo;
    }

    public void setPorcMinimo(BigDecimal porcMinimo) {
        this.porcMinimo = porcMinimo;
    }

    public String getCodTipo() {
        return codTipo;
    }

    public void setCodTipo(String codTipo) {
        this.codTipo = codTipo;
    }

    public String getColorHEX() {
        return colorHEX;
    }

    public void setColorHEX(String colorHEX) {
        this.colorHEX = colorHEX;
    }

    public long getIdColor() {
        return idColor;
    }

    public void setIdColor(long idColor) {
        this.idColor = idColor;
    }

    public String getDescripcionColor() {
        return descripcionColor;
    }

    public void setDescripcionColor(String descripcionColor) {
        this.descripcionColor = descripcionColor;
    }
    
}
