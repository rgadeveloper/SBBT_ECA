/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;
import javax.persistence.Temporal;

/**
 *
 * @author RGA
 */
@Embeddable
public class EnergiaTotalAreasPK implements Serializable {

  @Basic(optional = false)
  @Column(name = "PERIODO")
  private int periodo;
  @Basic(optional = false)
  @Column(name = "ENERGIA_TOTAL")
  private BigDecimal energiaTotal;
  @Basic(optional = false)
  @Column(name = "FECHA_MODIF")
  //@Temporal(javax.persistence.TemporalType.DATE)
  private long fechaModif;

  public EnergiaTotalAreasPK() {
  }
  
  EnergiaTotalAreasPK(int periodo, BigDecimal energiaTotal, long fechaModif) {
    this.periodo = periodo;
    this.energiaTotal = energiaTotal;
    this.fechaModif = fechaModif;
  }

  public int getPeriodo() {
    return periodo;
  }

  public void setPeriodo(int periodo) {
    this.periodo = periodo;
  }

  public BigDecimal getEnergiaTotal() {
    return energiaTotal;
  }

  public void setEnergiaTotal(BigDecimal energiaTotal) {
    this.energiaTotal = energiaTotal;
  }
  
  public long getFechaModif() {
    return fechaModif;
  }

  public void setFechaModif(long fechaModif) {
    this.fechaModif = fechaModif;
  }
  
  @Override
  public int hashCode() {
    int hash = 0;
    hash += (int) periodo;
    hash += (energiaTotal != null ? energiaTotal.hashCode() : 0);
    //hash += (fechaModif != new long("0") ? fechaModif.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof EnergiaTotalAreasPK)) {
      return false;
    }
    EnergiaTotalAreasPK other = (EnergiaTotalAreasPK) object;
    if (this.periodo != other.periodo) {
      return false;
    }
    
    if (this.energiaTotal != other.energiaTotal && (this.energiaTotal == null || !this.energiaTotal.equals(other.energiaTotal))) {
      return false;
    }
    
//    if (this.fechaModif != other.fechaModif && (this.fechaModif == null || !this.fechaModif.equals(other.fechaModif))) {
//      return false;
//    }
    return true;
  }

  @Override
  public String toString() {
    return "EnergiaTotalAreasPK{" + "periodo=" + periodo + ", energiaTotal=" + energiaTotal + ", fechaModif=" + fechaModif + '}';
  }
  
}
