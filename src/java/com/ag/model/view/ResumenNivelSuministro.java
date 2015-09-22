/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import com.ag.model.AtrComponente;
import com.ag.model.Componente;
import com.ag.model.Medida;
import java.util.List;

/**
 *
 * @author arodriguezr
 */
public class ResumenNivelSuministro {
    
    private Componente componente;
    private AtrComponente atrComponente;
    private List<Medida> listMedida;
    
    public ResumenNivelSuministro() {        
    }

    public ResumenNivelSuministro(Componente componente, AtrComponente atrComponente, List<Medida> listMedida) {
        this.componente = componente;
        this.atrComponente = atrComponente;
        this.listMedida = listMedida;
    }    
    
    public AtrComponente getAtrComponente() {
        return atrComponente;
    }

    public void setAtrComponente(AtrComponente atrComponente) {
        this.atrComponente = atrComponente;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public List<Medida> getListMedida() {
        return listMedida;
    }

    public void setListMedida(List<Medida> listMedida) {
        this.listMedida = listMedida;
    }   
    
}
