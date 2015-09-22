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
@Table(name = "S_ATR_COMPONENTE_MEDIDA")
@NamedQueries({
    @NamedQuery(name = "SAtrComponenteMedida.findAll", query = "SELECT s FROM SAtrComponenteMedida s"),
    @NamedQuery(name = "SAtrComponenteMedida.findByUsuario", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SAtrComponenteMedida.findByPrograma", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.programa = :programa"),
    @NamedQuery(name = "SAtrComponenteMedida.findByFechaModif", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SAtrComponenteMedida.findByIdAtrComponente", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.sAtrComponenteMedidaPK.idAtrComponente = :idAtrComponente"),
    @NamedQuery(name = "SAtrComponenteMedida.findByRelacionTc", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.relacionTc = :relacionTc"),
    @NamedQuery(name = "SAtrComponenteMedida.findByRtp", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.rtp = :rtp"),
    @NamedQuery(name = "SAtrComponenteMedida.findByConstante", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.constante = :constante"),
    @NamedQuery(name = "SAtrComponenteMedida.findByCategoria", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.categoria = :categoria"),
    @NamedQuery(name = "SAtrComponenteMedida.findByIdComponente", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.idComponente = :idComponente"),
    @NamedQuery(name = "SAtrComponenteMedida.findByFechaAlta", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "SAtrComponenteMedida.findByFechaBaja", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "SAtrComponenteMedida.findByTipoRed", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.tipoRed = :tipoRed"),
    @NamedQuery(name = "SAtrComponenteMedida.findByPeriodoIni", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.sAtrComponenteMedidaPK.periodoIni = :periodoIni"),
    @NamedQuery(name = "SAtrComponenteMedida.findByPeriodoFin", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.periodoFin = :periodoFin"),
    @NamedQuery(name = "SAtrComponenteMedida.findBySellosTp", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.sellosTp = :sellosTp"),
    @NamedQuery(name = "SAtrComponenteMedida.findBySellosTb", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.sellosTb = :sellosTb"),
    @NamedQuery(name = "SAtrComponenteMedida.findByFechaInstalacion", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.fechaInstalacion = :fechaInstalacion"),
    @NamedQuery(name = "SAtrComponenteMedida.findByFaseR", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.faseR = :faseR"),
    @NamedQuery(name = "SAtrComponenteMedida.findByFaseT", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.faseT = :faseT"),
    @NamedQuery(name = "SAtrComponenteMedida.findByFaseS", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.faseS = :faseS"),
    @NamedQuery(name = "SAtrComponenteMedida.findByTensionRs", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.tensionRs = :tensionRs"),
    @NamedQuery(name = "SAtrComponenteMedida.findByTensionSt", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.tensionSt = :tensionSt"),
    @NamedQuery(name = "SAtrComponenteMedida.findByTensionTr", query = "SELECT s FROM SAtrComponenteMedida s WHERE s.tensionTr = :tensionTr")})
public class SAtrComponenteMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SAtrComponenteMedidaPK sAtrComponenteMedidaPK;
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
    @Column(name = "RTP")
    private String rtp;
    @Column(name = "CONSTANTE")
    private Long constante;
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
    @JoinColumn(name = "ID_COMPONENTE_MEDIDA", referencedColumnName = "ID_COMPONENTE_MEDIDA")
    @ManyToOne(optional = false)
    private SComponenteMedida sComponenteMedida;
    @JoinColumn(name = "ESTADO_INTERVENIDO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado;

    public SAtrComponenteMedida() {
    }

    public SAtrComponenteMedida(SAtrComponenteMedidaPK sAtrComponenteMedidaPK) {
        this.sAtrComponenteMedidaPK = sAtrComponenteMedidaPK;
    }

    public SAtrComponenteMedida(SAtrComponenteMedidaPK sAtrComponenteMedidaPK, String usuario, String programa, Date fechaModif, String categoria, BigInteger idComponente, String tipoRed, long periodoFin) {
        this.sAtrComponenteMedidaPK = sAtrComponenteMedidaPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.categoria = categoria;
        this.idComponente = idComponente;
        this.tipoRed = tipoRed;
        this.periodoFin = periodoFin;
    }

    public SAtrComponenteMedida(BigInteger idAtrComponente, int periodoIni) {
        this.sAtrComponenteMedidaPK = new SAtrComponenteMedidaPK(idAtrComponente, periodoIni);
    }

    public SAtrComponenteMedidaPK getSAtrComponenteMedidaPK() {
        return sAtrComponenteMedidaPK;
    }

    public void setSAtrComponenteMedidaPK(SAtrComponenteMedidaPK sAtrComponenteMedidaPK) {
        this.sAtrComponenteMedidaPK = sAtrComponenteMedidaPK;
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

    public Long getConstante() {
        return constante;
    }

    public void setConstante(Long constante) {
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

    public SComponenteMedida getSComponenteMedida() {
        return sComponenteMedida;
    }

    public void setSComponenteMedida(SComponenteMedida sComponenteMedida) {
        this.sComponenteMedida = sComponenteMedida;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sAtrComponenteMedidaPK != null ? sAtrComponenteMedidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SAtrComponenteMedida)) {
            return false;
        }
        SAtrComponenteMedida other = (SAtrComponenteMedida) object;
        if ((this.sAtrComponenteMedidaPK == null && other.sAtrComponenteMedidaPK != null) || (this.sAtrComponenteMedidaPK != null && !this.sAtrComponenteMedidaPK.equals(other.sAtrComponenteMedidaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SAtrComponenteMedida[sAtrComponenteMedidaPK=" + sAtrComponenteMedidaPK + "]";
    }

}
