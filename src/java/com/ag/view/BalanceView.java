package com.ag.view;


import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped

public class BalanceView implements Serializable {
     private List infoBalance;

    /**
     * @return the infoBalance
     */
    public List getInfoBalance() {
        return infoBalance;
    }

    /**
     * @param infoBalance the infoBalance to set
     */
    public void setInfoBalance(List infoBalance) {
        this.infoBalance = infoBalance;
    }

}
