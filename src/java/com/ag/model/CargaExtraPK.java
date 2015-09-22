/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author larry.obispo
 */
@Embeddable
public class CargaExtraPK implements Serializable {

  @Basic(optional = false)
  @Column(name = "TIPO_CARGA")
  private String tipoCarga;
  @Basic(optional = false)
  @Column(name = "ID_COMPONENTE")
  private BigInteger idComponente;
  @Basic(optional = false)
  @Column(name = "PERIODO")
  private int periodo;

  public CargaExtraPK() {
  }

  public CargaExtraPK(String tipoCarga, BigInteger idComponente, int periodo) {
    this.tipoCarga = tipoCarga;
    this.idComponente = idComponente;
    this.periodo = periodo;
  }

  public String getTipoCarga() {
    return tipoCarga;
  }

  public void setTipoCarga(String tipoCarga) {
    this.tipoCarga = tipoCarga;
  }

  public BigInteger getIdComponente() {
    return idComponente;
  }

  public void setIdComponente(BigInteger idComponente) {
    this.idComponente = idComponente;
  }

  public int getPeriodo() {
    return periodo;
  }

  public void setPeriodo(int periodo) {
    this.periodo = periodo;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (tipoCarga != null ? tipoCarga.hashCode() : 0);
    hash += (idComponente != null ? idComponente.hashCode() : 0);
    hash += (int) periodo;
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof CargaExtraPK)) {
      return false;
    }
    CargaExtraPK other = (CargaExtraPK) object;
    if ((this.tipoCarga == null && other.tipoCarga != null) || (this.tipoCarga != null && !this.tipoCarga.equals(other.tipoCarga))) {
      return false;
    }
    if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
      return false;
    }
    if (this.periodo != other.periodo) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ag.model.CargaExtraPK[tipoCarga=" + tipoCarga + ", idComponente=" + idComponente + ", periodo=" + periodo + "]";
  }

}
