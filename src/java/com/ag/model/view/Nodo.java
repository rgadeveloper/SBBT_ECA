
package com.ag.model.view;

import java.util.List;
import javax.persistence.OneToMany;
import org.primefaces.model.TreeNode;

/**
 *
 * @author 85154220
 */

public class Nodo {    
    private String codigo;
    private String nombre;
    private String tipo;
    private String nombreTipo;    
    private String color;
    private String perdidas;
    private String coordenadaX;
    private String coordenadaY;
    private String numMacroTot;
    private String numMacrosFuncionando;
    private String numSuministrosFact;
    private String localizacion = "";
    private boolean selected = false;
    private String tipoUso;
    private String nombreColor;
    private TreeNode nodoVisual;
    
    @OneToMany
    private List<Nodo> hijos;

    public Nodo() {
    }
    
    public Nodo (String codigo,String nombre,String tipo,String color,String perdidas){
        this.codigo   = codigo;
        this.nombre   = nombre;
        this.tipo     = tipo;
        this.color    = color;
        this.perdidas = perdidas;
    }
    
    public Nodo (String codigo,String nombre,String tipo,String color,String perdidas,String coordenadaX,String coordenadaY){
        this.codigo      = codigo;
        this.nombre      = nombre;
        this.tipo        = tipo;
        this.color       = color;
        this.perdidas    = perdidas;
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
    }
    
    public Nodo(String codigo){
        this.codigo = codigo;
    }

    /**
     * @return the Codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param Codigo the Codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the Nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the Nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String toString(){
        return nombre +": " + getPerdidas() +" %";
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the perdidas
     */
    public String getPerdidas() {
        return perdidas;
    }

    /**
     * @param perdidas the perdidas to set
     */
    public void setPerdidas(String perdidas) {
        this.perdidas = perdidas;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @return the hijos
     */
    public List<Nodo> getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(List<Nodo> hijos) {
        this.hijos = hijos;
    }

    /**
     * @return the coordenadaX
     */
    public String getCoordenadaX() {
        return coordenadaX;
    }

    /**
     * @param coordenadaX the coordenadaX to set
     */
    public void setCoordenadaX(String coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    /**
     * @return the coordenadaY
     */
    public String getCoordenadaY() {
        return coordenadaY;
    }

    /**
     * @param coordenadaY the coordenadaY to set
     */
    public void setCoordenadaY(String coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    /**
     * @return the nombreTipo
     */
    public String getNombreTipo() {
        return nombreTipo;
    }

    /**
     * @param nombreTipo the nombreTipo to set
     */
    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    /**
     * @return the numMacroTot
     */
    public String getNumMacroTot() {
        return numMacroTot;
    }

    /**
     * @param numMacroTot the numMacroTot to set
     */
    public void setNumMacroTot(String numMacroTot) {
        this.numMacroTot = numMacroTot;
    }

    /**
     * @return the numMacrosFuncionando
     */
    public String getNumMacrosFuncionando() {
        return numMacrosFuncionando;
    }

    /**
     * @param numMacrosFuncionando the numMacrosFuncionando to set
     */
    public void setNumMacrosFuncionando(String numMacrosFuncionando) {
        this.numMacrosFuncionando = numMacrosFuncionando;
    }

    /**
     * @return the localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * @param localizacion the localizacion to set
     */
    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    /**
     * @return the numSuministrosFact
     */
    public String getNumSuministrosFact() {
        return numSuministrosFact;
    }

    /**
     * @param numSuministrosFact the numSuministrosFact to set
     */
    public void setNumSuministrosFact(String numSuministrosFact) {
        this.numSuministrosFact = numSuministrosFact;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }

    /**
     * @return the nodoVisual
     */
    public TreeNode getNodoVisual() {
        return nodoVisual;
    }

    /**
     * @param nodoVisual the nodoVisual to set
     */
    public void setNodoVisual(TreeNode nodoVisual) {
        this.nodoVisual = nodoVisual;
    }
    
}
