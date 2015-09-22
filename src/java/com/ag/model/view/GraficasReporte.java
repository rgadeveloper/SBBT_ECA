/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

/**
 *
 * @author arodriguezr
 */
public class GraficasReporte {
    private long funcionan;
    private long nofuncionan;   
    private long bajo;
    private long medio;
    private long critico;
    private long alto;
    private long inconsistentePositivo;
    private long inconsistenteNegativo;
    private long negativo;
    private long sinbalance;
    
    public GraficasReporte() {        
    }

    public GraficasReporte(long funcionan, long nofuncionan, long bajo, long medio, long critico, long inconsistentePositivo, long inconsistenteNegativo, long negativo, long sinbalance) {
        this.funcionan = funcionan;
        this.nofuncionan = nofuncionan;
        this.bajo = bajo;
        this.medio = medio;
        this.critico = critico;
        this.inconsistentePositivo = inconsistentePositivo;
        this.inconsistenteNegativo = inconsistenteNegativo;
        this.negativo = negativo;
        this.sinbalance = sinbalance;
    }

    public long getBajo() {
        return bajo;
    }

    public void setBajo(long bajo) {
        this.bajo = bajo;
    }

    public long getCritico() {
        return critico;
    }

    public void setCritico(long critico) {
        this.critico = critico;
    }

    public long getFuncionan() {
        return funcionan;
    }

    public void setFuncionan(long funcionan) {
        this.funcionan = funcionan;
    }

    public long getInconsistentePositivo() {
        return inconsistentePositivo;
    }

    public void setInconsistentePositivo(long inconsistentePositivo) {
        this.inconsistentePositivo = inconsistentePositivo;
    }
    
    public long getInconsistenteNegativo() {
        return inconsistenteNegativo;
    }

    public void setInconsistenteNegativo(long inconsistenteNegativo) {
        this.inconsistenteNegativo = inconsistenteNegativo;
    }

    public long getMedio() {
        return medio;
    }

    public void setMedio(long medio) {
        this.medio = medio;
    }

    public long getNegativo() {
        return negativo;
    }

    public void setNegativo(long negativo) {
        this.negativo = negativo;
    }

    public long getNofuncionan() {
        return nofuncionan;
    }

    public void setNofuncionan(long nofuncionan) {
        this.nofuncionan = nofuncionan;
    }

    public long getSinbalance() {
        return sinbalance;
    }

    public void setSinbalance(long sinbalance) {
        this.sinbalance = sinbalance;
    }

    /**
     * @return the alto
     */
    public long getAlto() {
        return alto;
    }

    /**
     * @param alto the alto to set
     */
    public void setAlto(long alto) {
        this.alto = alto;
    }
    
}
