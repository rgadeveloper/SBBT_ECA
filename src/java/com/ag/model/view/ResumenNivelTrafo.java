/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

//import com.ag.model.*;

import com.ag.model.AtrComponente;
import com.ag.model.AtrComponenteMedida;
import com.ag.model.Balances;
import com.ag.model.Componente;
import com.ag.model.ComponenteMedida;
import com.ag.model.Medida;


/**
 *
 * @author arodriguezr
 */
public class ResumenNivelTrafo {
    private Balances balance;
    private ComponenteMedida componenteMedida;
    private Componente componente;
    private PadreHijo relacionGeo;
    private PadreHijo relacionElec;
    private String cantSumNoMedidos;
    private AtrComponente atrComponente;
    private AtrComponenteMedida atrComponenteMedida;
    private Medida medida;
    
    
    public ResumenNivelTrafo() {        
    }

    public ResumenNivelTrafo(Balances balance, ComponenteMedida componenteMedida, Componente componente, PadreHijo relacionGeo, PadreHijo relacionElec, String cantSumNoMedidos, AtrComponente atrComponente, AtrComponenteMedida atrComponenteMedida, Medida medida) {
        this.balance = balance;
        this.componenteMedida = componenteMedida;
        this.componente = componente;
        this.relacionGeo = relacionGeo;
        this.relacionElec = relacionElec;
        this.cantSumNoMedidos = cantSumNoMedidos;
        this.atrComponente = atrComponente;
        this.atrComponenteMedida = atrComponenteMedida;
        this.medida = medida;
    }    
    
    public AtrComponente getAtrComponente() {
        return atrComponente;
    }

    public void setAtrComponente(AtrComponente atrComponente) {
        this.atrComponente = atrComponente;
    }

    public AtrComponenteMedida getAtrComponenteMedida() {
        return atrComponenteMedida;
    }

    public void setAtrComponenteMedida(AtrComponenteMedida atrComponenteMedida) {
        this.atrComponenteMedida = atrComponenteMedida;
    }
    
    public Balances getBalance() {
        return balance;
    }

    public void setBalance(Balances balance) {
        this.balance = balance;
    }

    public String getCantSumNoMedidos() {
        return cantSumNoMedidos;
    }

    public void setCantSumNoMedidos(String cantSumNoMedidos) {
        this.cantSumNoMedidos = cantSumNoMedidos;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public ComponenteMedida getComponenteMedida() {
        return componenteMedida;
    }

    public void setComponenteMedida(ComponenteMedida componenteMedida) {
        this.componenteMedida = componenteMedida;
    }

    public Medida getMedida() {
        return medida;
    }

    public void setMedida(Medida medida) {
        this.medida = medida;
    }

    public PadreHijo getRelacionGeo() {
        return relacionGeo;
    }

    public void setRelacionGeo(PadreHijo relacionGeo) {
        this.relacionGeo = relacionGeo;
    }

    public PadreHijo getRelacionElec() {
        return relacionElec;
    }

    public void setRelacionElec(PadreHijo relacionElec) {
        this.relacionElec = relacionElec;
    }    
}
