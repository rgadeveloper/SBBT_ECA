/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import com.ag.model.Balances;

/**
 *
 * @author arodriguezr
 */
public class ResumenNivelZona {
    private Balances balance;
    private DataRangosBalance dataRangosBalance;
    
    public ResumenNivelZona() {        
    }

    public ResumenNivelZona(Balances balance, DataRangosBalance dataRangosBalance) {
        this.balance = balance;
        this.dataRangosBalance = dataRangosBalance;
    }

    public Balances getBalance() {
        return balance;
    }

    public void setBalance(Balances balance) {
        this.balance = balance;
    }

    public DataRangosBalance getDataRangosBalance() {
        return dataRangosBalance;
    }

    public void setDataRangosBalance(DataRangosBalance dataRangosBalance) {
        this.dataRangosBalance = dataRangosBalance;
    }
    
}
