/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import com.ag.model.ComponenteCampania;
import com.ag.model.Tbltipo;
import java.util.List;

/**
 *
 * @author arodriguezr
 */
public class ResultCampania {
    private String idCampania;
    private String nombre;
    private String periodo;
    private Tbltipo tipo;    
    private double cantMacrosTotal;
    private double cantMacrosRevisados;
    private double cantMacrosNormales;
    private double cantMacrosIrregularidad;
    private double cantMacrosNormalizados;
    private double porcEfectividadMacros;
    private boolean mostrarValoresMacros;
    private double cantClientes;
    private double cantClientesRevisados;
    private double cantClientesNormales;
    private double cantClientesIrregularidad;
    private double cantClientesNormalizados;
    private double porcEfectividadClientes; 
    private boolean mostrarValoresClientes;
    private List<ComponenteCampania> componenetesAsociados;

    public ResultCampania() {
        this.mostrarValoresClientes=false;
        this.mostrarValoresMacros=false;
    }

    public ResultCampania(String idCampania, String nombre, String periodo, Tbltipo tipo) {
        this.idCampania = idCampania;
        this.nombre = nombre;
        this.periodo = periodo;
        this.tipo = tipo;
        this.mostrarValoresClientes=false;
        this.mostrarValoresMacros=false;
    }

    public double getCantClientes() {
        return cantClientes;
    }

    public void setCantClientes(double cantClientes) {
        this.cantClientes = cantClientes;
    }

    public double getCantClientesIrregularidad() {
        return cantClientesIrregularidad;
    }

    public void setCantClientesIrregularidad(double cantClientesIrregularidad) {
        this.cantClientesIrregularidad = cantClientesIrregularidad;
    }

    public double getCantClientesNormales() {
        return cantClientesNormales;
    }

    public void setCantClientesNormales(double cantClientesNormales) {
        this.cantClientesNormales = cantClientesNormales;
    }

    public double getCantClientesNormalizados() {
        return cantClientesNormalizados;
    }

    public void setCantClientesNormalizados(double cantClientesNormalizados) {
        this.cantClientesNormalizados = cantClientesNormalizados;
    }

    public double getCantClientesRevisados() {
        return cantClientesRevisados;
    }

    public void setCantClientesRevisados(double cantClientesRevisados) {
        this.cantClientesRevisados = cantClientesRevisados;
    }

    public double getCantMacrosIrregularidad() {
        return cantMacrosIrregularidad;
    }

    public void setCantMacrosIrregularidad(double cantMacrosIrregularidad) {
        this.cantMacrosIrregularidad = cantMacrosIrregularidad;
    }

    public double getCantMacrosNormales() {
        return cantMacrosNormales;
    }

    public void setCantMacrosNormales(double cantMacrosNormales) {
        this.cantMacrosNormales = cantMacrosNormales;
    }

    public double getCantMacrosNormalizados() {
        return cantMacrosNormalizados;
    }

    public void setCantMacrosNormalizados(double cantMacrosNormalizados) {
        this.cantMacrosNormalizados = cantMacrosNormalizados;
    }

    public double getCantMacrosRevisados() {
        return cantMacrosRevisados;
    }

    public void setCantMacrosRevisados(double cantMacrosRevisados) {
        this.cantMacrosRevisados = cantMacrosRevisados;
    }

    public double getCantMacrosTotal() {
        return cantMacrosTotal;
    }

    public void setCantMacrosTotal(double cantMacrosTotal) {
        this.cantMacrosTotal = cantMacrosTotal;
    }

    public List<ComponenteCampania> getComponenetesAsociados() {
        return componenetesAsociados;
    }

    public void setComponenetesAsociados(List<ComponenteCampania> componenetesAsociados) {
        this.componenetesAsociados = componenetesAsociados;
    }

    public String getIdCampania() {
        return idCampania;
    }

    public void setIdCampania(String idCampania) {
        this.idCampania = idCampania;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getPorcEfectividadClientes() {
        return porcEfectividadClientes;
    }

    public void setPorcEfectividadClientes(double porcEfectividadClientes) {
        this.porcEfectividadClientes = porcEfectividadClientes;
    }

    public double getPorcEfectividadMacros() {
        return porcEfectividadMacros;
    }

    public void setPorcEfectividadMacros(double porcEfectividadMacros) {
        this.porcEfectividadMacros = porcEfectividadMacros;
    }

    public Tbltipo getTipo() {
        return tipo;
    }

    public void setTipo(Tbltipo tipo) {
        this.tipo = tipo;
    }

    public boolean isMostrarValoresClientes() {
        return mostrarValoresClientes;
    }

    public void setMostrarValoresClientes(boolean mostrarValoresClientes) {
        this.mostrarValoresClientes = mostrarValoresClientes;
    }

    public boolean isMostrarValoresMacros() {
        return mostrarValoresMacros;
    }

    public void setMostrarValoresMacros(boolean mostrarValoresMacros) {
        this.mostrarValoresMacros = mostrarValoresMacros;
    }
}
