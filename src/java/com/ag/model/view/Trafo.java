/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import com.ag.model.Balances;
import com.ag.model.Componente;

/**
 *
 * @author arodriguezr
 */
public class Trafo {
    private Componente trafo;   
    private Balances balance;    

    public Trafo() {
    }

    public Trafo(Componente trafo, Balances balance) {
        this.trafo = trafo;
        this.balance = balance;
    }

    public Componente getTrafo() {
        return trafo;
    }

    public void setTrafo(Componente trafo) {
        this.trafo = trafo;
    }

    public Balances getBalance() {
        return balance;
    }

    public void setBalance(Balances balance) {
        this.balance = balance;
    }
    
}
