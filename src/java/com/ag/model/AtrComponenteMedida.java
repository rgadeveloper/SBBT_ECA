/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author larry.obispo
 */
@Entity
@Table(name = "ATR_COMPONENTE_MEDIDA")
@NamedQueries({
    @NamedQuery(name = "AtrComponenteMedida.findAll", query = "SELECT a FROM AtrComponenteMedida a"),
    @NamedQuery(name = "AtrComponenteMedida.findByUsuario", query = "SELECT a FROM AtrComponenteMedida a WHERE a.usuario = :usuario"),
    @NamedQuery(name = "AtrComponenteMedida.findByPrograma", query = "SELECT a FROM AtrComponenteMedida a WHERE a.programa = :programa"),
    @NamedQuery(name = "AtrComponenteMedida.findByFechaModif", query = "SELECT a FROM AtrComponenteMedida a WHERE a.fechaModif = :fechaModif"),
    @NamedQuery(name = "AtrComponenteMedida.findByIdAtrComponente", query = "SELECT a FROM AtrComponenteMedida a WHERE a.atrComponenteMedidaPK.idAtrComponente = :idAtrComponente"),
    @NamedQuery(name = "AtrComponenteMedida.findByRelacionTc", query = "SELECT a FROM AtrComponenteMedida a WHERE a.relacionTc = :relacionTc"),
    @NamedQuery(name = "AtrComponenteMedida.findByRtp", query = "SELECT a FROM AtrComponenteMedida a WHERE a.rtp = :rtp"),
    @NamedQuery(name = "AtrComponenteMedida.findByConstante", query = "SELECT a FROM AtrComponenteMedida a WHERE a.constante = :constante"),
    @NamedQuery(name = "AtrComponenteMedida.findByCategoria", query = "SELECT a FROM AtrComponenteMedida a WHERE a.categoria = :categoria"),
    @NamedQuery(name = "AtrComponenteMedida.findByIdComponente", query = "SELECT a FROM AtrComponenteMedida a WHERE a.idComponente = :idComponente"),
    @NamedQuery(name = "AtrComponenteMedida.findByFechaAlta", query = "SELECT a FROM AtrComponenteMedida a WHERE a.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "AtrComponenteMedida.findByFechaBaja", query = "SELECT a FROM AtrComponenteMedida a WHERE a.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "AtrComponenteMedida.findByTipoRed", query = "SELECT a FROM AtrComponenteMedida a WHERE a.tipoRed = :tipoRed"),
    @NamedQuery(name = "AtrComponenteMedida.findByPeriodoIni", query = "SELECT a FROM AtrComponenteMedida a WHERE a.atrComponenteMedidaPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "AtrComponenteMedida.findByPeriodoFin", query = "SELECT a FROM AtrComponenteMedida a WHERE a.periodoFin = :periodoFin"),
    @NamedQuery(name = "AtrComponenteMedida.findBySellosTp", query = "SELECT a FROM AtrComponenteMedida a WHERE a.sellosTp = :sellosTp"),
    @NamedQuery(name = "AtrComponenteMedida.findBySellosTb", query = "SELECT a FROM AtrComponenteMedida a WHERE a.sellosTb = :sellosTb"),
    @NamedQuery(name = "AtrComponenteMedida.findByFechaInstalacion", query = "SELECT a FROM AtrComponenteMedida a WHERE a.fechaInstalacion = :fechaInstalacion"),
    @NamedQuery(name = "AtrComponenteMedida.findByFaseR", query = "SELECT a FROM AtrComponenteMedida a WHERE a.faseR = :faseR"),
    @NamedQuery(name = "AtrComponenteMedida.findByFaseT", query = "SELECT a FROM AtrComponenteMedida a WHERE a.faseT = :faseT"),
    @NamedQuery(name = "AtrComponenteMedida.findByFaseS", query = "SELECT a FROM AtrComponenteMedida a WHERE a.faseS = :faseS"),
    @NamedQuery(name = "AtrComponenteMedida.findByTensionRs", query = "SELECT a FROM AtrComponenteMedida a WHERE a.tensionRs = :tensionRs"),
    @NamedQuery(name = "AtrComponenteMedida.findByTensionSt", query = "SELECT a FROM AtrComponenteMedida a WHERE a.tensionSt = :tensionSt"),
    @NamedQuery(name = "AtrComponenteMedida.findByTensionTr", query = "SELECT a FROM AtrComponenteMedida a WHERE a.tensionTr = :tensionTr"),
    @NamedQuery(name = "AtrComponenteMedida.findByCodigoTrafo", query = "SELECT a FROM AtrComponenteMedida a WHERE a.codigoTrafo = :codigoTrafo")})
public class AtrComponenteMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AtrComponenteMedidaPK atrComponenteMedidaPK;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "PROGRAMA")
    private String programa;
    @Basic(optional = false)
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Column(name = "RELACION_TC")
    private String relacionTc;
    @Basic(optional = false)
    @Column(name = "RTP")
    private String rtp;
    @Basic(optional = false)
    @Column(name = "CONSTANTE")
    private long constante;
    @Basic(optional = false)
    @Column(name = "CATEGORIA")
    private String categoria;
    @Basic(optional = false)
    @Column(name = "ID_COMPONENTE")
    private BigInteger idComponente;
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Basic(optional = false)
    @Column(name = "TIPO_RED")
    private String tipoRed;
    @Basic(optional = false)
    @Column(name = "PERIODO_FIN")
    private long periodoFin;
    @Column(name = "SELLOS_TP")
    private Long sellosTp;
    @Column(name = "SELLOS_TB")
    private Long sellosTb;
    @Column(name = "FECHA_INSTALACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInstalacion;
    @Column(name = "FASE_R")
    private BigDecimal faseR;
    @Column(name = "FASE_T")
    private BigDecimal faseT;
    @Column(name = "FASE_S")
    private BigDecimal faseS;
    @Column(name = "TENSION_RS")
    private BigDecimal tensionRs;
    @Column(name = "TENSION_ST")
    private BigDecimal tensionSt;
    @Column(name = "TENSION_TR")
    private BigDecimal tensionTr;
    @Column(name = "CODIGO_TRAFO")
    private Long codigoTrafo;
    @JoinColumn(name = "ESTADO_INTERVENIDO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado;
    @JoinColumn(name = "ID_COMPONENTE_MEDIDA", referencedColumnName = "ID_COMPONENTE_MEDIDA")
    @ManyToOne(optional = false)
    private ComponenteMedida componenteMedida;

    public AtrComponenteMedida() {
    }

    public AtrComponenteMedida(AtrComponenteMedidaPK atrComponenteMedidaPK) {
        this.atrComponenteMedidaPK = atrComponenteMedidaPK;
    }

    public AtrComponenteMedida(AtrComponenteMedidaPK atrComponenteMedidaPK, String usuario, String programa, Date fechaModif, String rtp, long constante, String categoria, BigInteger idComponente, String tipoRed, long periodoFin) {
        this.atrComponenteMedidaPK = atrComponenteMedidaPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.rtp = rtp;
        this.constante = constante;
        this.categoria = categoria;
        this.idComponente = idComponente;
        this.tipoRed = tipoRed;
        this.periodoFin = periodoFin;
    }

    public AtrComponenteMedida(BigInteger idAtrComponente, int periodoIni) {
        this.atrComponenteMedidaPK = new AtrComponenteMedidaPK(idAtrComponente, periodoIni);
    }

    public AtrComponenteMedidaPK getAtrComponenteMedidaPK() {
        return atrComponenteMedidaPK;
    }

    public void setAtrComponenteMedidaPK(AtrComponenteMedidaPK atrComponenteMedidaPK) {
        this.atrComponenteMedidaPK = atrComponenteMedidaPK;
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

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public String getRelacionTc() {
        return relacionTc;
    }

    public void setRelacionTc(String relacionTc) {
        this.relacionTc = relacionTc;
    }

    public String getRtp() {
        return rtp;
    }

    public void setRtp(String rtp) {
        this.rtp = rtp;
    }

    public long getConstante() {
        return constante;
    }

    public void setConstante(long constante) {
        this.constante = constante;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigInteger getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigInteger idComponente) {
        this.idComponente = idComponente;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getTipoRed() {
        return tipoRed;
    }

    public void setTipoRed(String tipoRed) {
        this.tipoRed = tipoRed;
    }

    public long getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(long periodoFin) {
        this.periodoFin = periodoFin;
    }

    public Long getSellosTp() {
        return sellosTp;
    }

    public void setSellosTp(Long sellosTp) {
        this.sellosTp = sellosTp;
    }

    public Long getSellosTb() {
        return sellosTb;
    }

    public void setSellosTb(Long sellosTb) {
        this.sellosTb = sellosTb;
    }

    public Date getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(Date fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public BigDecimal getFaseR() {
        return faseR;
    }

    public void setFaseR(BigDecimal faseR) {
        this.faseR = faseR;
    }

    public BigDecimal getFaseT() {
        return faseT;
    }

    public void setFaseT(BigDecimal faseT) {
        this.faseT = faseT;
    }

    public BigDecimal getFaseS() {
        return faseS;
    }

    public void setFaseS(BigDecimal faseS) {
        this.faseS = faseS;
    }

    public BigDecimal getTensionRs() {
        return tensionRs;
    }

    public void setTensionRs(BigDecimal tensionRs) {
        this.tensionRs = tensionRs;
    }

    public BigDecimal getTensionSt() {
        return tensionSt;
    }

    public void setTensionSt(BigDecimal tensionSt) {
        this.tensionSt = tensionSt;
    }

    public BigDecimal getTensionTr() {
        return tensionTr;
    }

    public void setTensionTr(BigDecimal tensionTr) {
        this.tensionTr = tensionTr;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ComponenteMedida getComponenteMedida() {
        return componenteMedida;
    }

    public void setComponenteMedida(ComponenteMedida componenteMedida) {
        this.componenteMedida = componenteMedida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atrComponenteMedidaPK != null ? atrComponenteMedidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtrComponenteMedida)) {
            return false;
        }
        AtrComponenteMedida other = (AtrComponenteMedida) object;
        if ((this.atrComponenteMedidaPK == null && other.atrComponenteMedidaPK != null) || (this.atrComponenteMedidaPK != null && !this.atrComponenteMedidaPK.equals(other.atrComponenteMedidaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.AtrComponenteMedida[atrComponenteMedidaPK=" + atrComponenteMedidaPK + "]";
    }

    /**
     * @return the codigoTrafo
     */
    public Long getCodigoTrafo() {
        return codigoTrafo;
    }

    /**
     * @param codigoTrafo the codigoTrafo to set
     */
    public void setCodigoTrafo(Long codigoTrafo) {
        this.codigoTrafo = codigoTrafo;
    }

}
