/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model.view;

import java.math.BigDecimal;

/**
 *
 * @author Ultimate
 */
public class Perdidas {
    private BigDecimal RANK;
    private BigDecimal PERIODO;
    private BigDecimal RANGO_BALANCE;
    private String RANGOS;
    private String TIPOPCI;
    private BigDecimal PERDIDAS_KW_MES;
    private BigDecimal CANTIDAD;

    public Perdidas() {
    }
       
     public BigDecimal getRANK() {
            return RANK;
        }

        public void setRANK(BigDecimal RANK) {
            this.RANK = RANK;
        }

        public BigDecimal getPERIODO() {
            return PERIODO;
        }

        public void setPERIODO(BigDecimal PERIODO) {
            this.PERIODO = PERIODO;
        }

        public BigDecimal getRANGO_BALANCE() {
            return RANGO_BALANCE;
        }

        public void setRANGO_BALANCE(BigDecimal RANGO_BALANCE) {
            this.RANGO_BALANCE = RANGO_BALANCE;
        }

        public String getRANGOS() {
            return RANGOS;
        }

        public void setRANGOS(String RANGOS) {
            this.RANGOS = RANGOS;
        }

        public String getTIPOPCI() {
            return TIPOPCI;
        }

        public void setTIPOPCI(String TIPOPCI) {
            this.TIPOPCI = TIPOPCI;
        }

        public BigDecimal getPERDIDAS_KW_MES() {
            return PERDIDAS_KW_MES;
        }

        public void setPERDIDAS_KW_MES(BigDecimal PERDIDAS_KW_MES) {
            this.PERDIDAS_KW_MES = PERDIDAS_KW_MES;
        }

        public BigDecimal getCANTIDAD() {
            return CANTIDAD;
        }

        public void setCANTIDAD(BigDecimal CANTIDAD) {
            this.CANTIDAD = CANTIDAD;
        }
}
