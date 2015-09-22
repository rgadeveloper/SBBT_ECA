package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RGA
 */
@Entity
@Table(name = "ENERGIA_TOTAL_AREAS")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "EnergiaTotalAreas.findAll", query = "SELECT e FROM EnergiaTotalAreas e"),
  @NamedQuery(name = "EnergiaTotalAreas.findByUsuario", query = "SELECT e FROM EnergiaTotalAreas e WHERE e.usuario = :usuario"),
  @NamedQuery(name = "EnergiaTotalAreas.findByPrograma", query = "SELECT e FROM EnergiaTotalAreas e WHERE e.programa = :programa"),
  @NamedQuery(name = "EnergiaTotalAreas.findByPeriodo", query = "SELECT e FROM EnergiaTotalAreas e WHERE e.energiaTotalAreasPK.periodo = :periodo"),
  @NamedQuery(name = "EnergiaTotalAreas.findByEnergiaTotal", query = "SELECT e FROM EnergiaTotalAreas e WHERE e.energiaTotalAreasPK.energiaTotal = :energiaTotal"),
  @NamedQuery(name = "EnergiaTotalAreas.findByFechaModif", query = "SELECT e FROM EnergiaTotalAreas e WHERE e.energiaTotalAreasPK.fechaModif = :fechaModif")})
public class EnergiaTotalAreas implements Serializable {

  private static final long serialVersionUID = 1L;
  @EmbeddedId
  protected EnergiaTotalAreasPK energiaTotalAreasPK;
  @Basic(optional = false)
  @Column(name = "USUARIO")
  @NotNull
  @Size(min = 1, max = 15)
  private String usuario;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 30)
  @Column(name = "PROGRAMA")
  private String programa;
  @Basic(optional = false)
  @Column(name = "ENERGIA_ENTRADA")
  private BigDecimal energiaEntrada;
  @Basic(optional = false)
  @Column(name = "ENERGIA_SALIDA")
  private BigDecimal energiaSalida;
  @Basic(optional = false)
  @Column(name = "PORCENTAJE_PERDIDAS")
  private BigDecimal porcentajePerdidas;
  
  public EnergiaTotalAreas() {
  }

  public EnergiaTotalAreas(EnergiaTotalAreasPK energiaTotalAreasPK) {
    this.energiaTotalAreasPK = energiaTotalAreasPK;
  }

  public EnergiaTotalAreas(EnergiaTotalAreasPK energiaTotalAreasPK, String usuario, String programas) {
    this.energiaTotalAreasPK = energiaTotalAreasPK;
    this.usuario = usuario;
    this.programa = programas;
  }

  public EnergiaTotalAreas(EnergiaTotalAreasPK energiaTotalAreasPK, String usuario, String programa, BigDecimal energiaEntrada, BigDecimal energiaSalida, BigDecimal porcentajePerdidas) {
    this.energiaTotalAreasPK = energiaTotalAreasPK;
    this.usuario = usuario;
    this.programa = programa;
    this.energiaEntrada = energiaEntrada;
    this.energiaSalida = energiaSalida;
    this.porcentajePerdidas = porcentajePerdidas;
  }
  
  public EnergiaTotalAreas(int periodo, BigDecimal energiaTotal, long fechaModif) {
    this.energiaTotalAreasPK = new EnergiaTotalAreasPK(periodo, energiaTotal, fechaModif);
  }

  public EnergiaTotalAreasPK getEnergiaTotalAreasPK() {
    return energiaTotalAreasPK;
  }

  public void setEnergiaTotalAreasPK(EnergiaTotalAreasPK energiaTotalAreasPK) {
    this.energiaTotalAreasPK = energiaTotalAreasPK;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getPrograma() {
    return programa;
  }

  public void setPrograma(String programa) {
    this.programa = programa;
  }

  public BigDecimal getEnergiaEntrada() {
    return energiaEntrada;
  }

  public void setEnergiaEntrada(BigDecimal energiaEntrada) {
    this.energiaEntrada = energiaEntrada;
  }

  public BigDecimal getEnergiaSalida() {
    return energiaSalida;
  }

  public void setEnergiaSalida(BigDecimal energiaSalida) {
    this.energiaSalida = energiaSalida;
  }

  public BigDecimal getPorcentajePerdidas() {
    return porcentajePerdidas;
  }

  public void setPorcentajePerdidas(BigDecimal porcentajePerdidas) {
    this.porcentajePerdidas = porcentajePerdidas;
  }
  
  @Override
  public int hashCode() {
    int hash = 0;
    hash += (energiaTotalAreasPK != null ? energiaTotalAreasPK.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EnergiaTotalAreas)) {
      return false;
    }
    EnergiaTotalAreas other = (EnergiaTotalAreas) object;
    if ((this.energiaTotalAreasPK == null && other.energiaTotalAreasPK != null) || (this.energiaTotalAreasPK != null && !this.energiaTotalAreasPK.equals(other.energiaTotalAreasPK))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.ag.model.EnergiaTotalAreas{" + "energiaTotalAreasPK=" + energiaTotalAreasPK + '}';
  }
}
