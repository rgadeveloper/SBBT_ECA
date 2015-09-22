/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "ATR_COMPONENTE")
@NamedQueries({
    @NamedQuery(name = "AtrComponente.findAll", query = "SELECT a FROM AtrComponente a"),
    @NamedQuery(name = "AtrComponente.findByUsuario", query = "SELECT a FROM AtrComponente a WHERE a.usuario = :usuario"),
    @NamedQuery(name = "AtrComponente.findByPrograma", query = "SELECT a FROM AtrComponente a WHERE a.programa = :programa"),
    @NamedQuery(name = "AtrComponente.findByFechaModif", query = "SELECT a FROM AtrComponente a WHERE a.fechaModif = :fechaModif"),
    @NamedQuery(name = "AtrComponente.findByIdAtrComponente", query = "SELECT a FROM AtrComponente a WHERE a.idAtrComponente = :idAtrComponente"),
    @NamedQuery(name = "AtrComponente.findByCargaInstalada", query = "SELECT a FROM AtrComponente a WHERE a.cargaInstalada = :cargaInstalada"),
    @NamedQuery(name = "AtrComponente.findByPotencia", query = "SELECT a FROM AtrComponente a WHERE a.potencia = :potencia"),
    @NamedQuery(name = "AtrComponente.findByFechaInstalacion", query = "SELECT a FROM AtrComponente a WHERE a.fechaInstalacion = :fechaInstalacion"),
    @NamedQuery(name = "AtrComponente.findByFechaBaja", query = "SELECT a FROM AtrComponente a WHERE a.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "AtrComponente.findByTipoConsumo", query = "SELECT a FROM AtrComponente a WHERE a.tipoConsumo = :tipoConsumo"),
    @NamedQuery(name = "AtrComponente.findByEstrato", query = "SELECT a FROM AtrComponente a WHERE a.estrato = :estrato"),
    @NamedQuery(name = "AtrComponente.findBySerie", query = "SELECT a FROM AtrComponente a WHERE a.serie = :serie"),
    @NamedQuery(name = "AtrComponente.findByRamal", query = "SELECT a FROM AtrComponente a WHERE a.ramal = :ramal"),
    @NamedQuery(name = "AtrComponente.findByConsumoPromedio", query = "SELECT a FROM AtrComponente a WHERE a.consumoPromedio = :consumoPromedio"),
    @NamedQuery(name = "AtrComponente.findByPeriodoIni", query = "SELECT a FROM AtrComponente a WHERE a.periodoIni = :periodoIni"),
    @NamedQuery(name = "AtrComponente.findByPeriodoFin", query = "SELECT a FROM AtrComponente a WHERE a.periodoFin = :periodoFin"),
    @NamedQuery(name = "AtrComponente.findByApoyo", query = "SELECT a FROM AtrComponente a WHERE a.apoyo = :apoyo"),
    @NamedQuery(name = "AtrComponente.findByFases", query = "SELECT a FROM AtrComponente a WHERE a.fases = :fases"),
    @NamedQuery(name = "AtrComponente.findByMedidor", query = "SELECT a FROM AtrComponente a WHERE a.medidor = :medidor"),
    @NamedQuery(name = "AtrComponente.findByRuta", query = "SELECT a FROM AtrComponente a WHERE a.ruta = :ruta"),
    @NamedQuery(name = "AtrComponente.findByContrato", query = "SELECT a FROM AtrComponente a WHERE a.contrato = :contrato"),
    @NamedQuery(name = "AtrComponente.findByNif", query = "SELECT a FROM AtrComponente a WHERE a.nif = :nif"),
    @NamedQuery(name = "AtrComponente.findByAolFin", query = "SELECT a FROM AtrComponente a WHERE a.aolFin = :aolFin")})
public class AtrComponente implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ATR_COMPONENTE")
    private BigDecimal idAtrComponente;
    @Column(name = "CARGA_INSTALADA")
    private BigDecimal cargaInstalada;
    @Column(name = "POTENCIA")
    private BigDecimal potencia;
    @Column(name = "FECHA_INSTALACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInstalacion;
    @Basic(optional = false)
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Basic(optional = false)
    @Column(name = "TIPO_CONSUMO")
    private String tipoConsumo;
    @Column(name = "ESTRATO")
    private Short estrato;
    @Column(name = "SERIE")
    private String serie;
    @Basic(optional = false)
    @Column(name = "RAMAL")
    private String ramal;
    @Column(name = "CONSUMO_PROMEDIO")
    private Long consumoPromedio;
    @Basic(optional = false)
    @Column(name = "PERIODO_INI")
    private int periodoIni;
    @Basic(optional = false)
    @Column(name = "PERIODO_FIN")
    private int periodoFin;
    @Column(name = "APOYO")
    private String apoyo;
    @Column(name = "FASES")
    private Short fases;
    @Column(name = "MEDIDOR")
    private String medidor;
    @Column(name = "RUTA")
    private String ruta;
    @Column(name = "CONTRATO")
    private Long contrato;
    @Column(name = "NIF")
    private Long nif;
    @Column(name = "AOL_FIN")
    private Long aolFin;
    @JoinColumn(name = "TIPO_CATEGORIA", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;
    @JoinColumn(name = "TIPO_USO", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo1;
    @JoinColumn(name = "TIPO_CONEXION", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo2;
    @JoinColumn(name = "TIPO_MEDIDOR", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo3;
    @JoinColumn(name = "TIPO_SUMINISTRO", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo4;
    @JoinColumn(name = "TIPO_RED", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo5;
    @JoinColumn(name = "TIPO_MARCA", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo6;
    @JoinColumn(name = "TIPO_TENSION", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo7;
    @JoinColumn(name = "ID_ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado;
    @JoinColumn(name = "ESTADO_INTERVENCION", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado1;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE")
    @ManyToOne(optional = false)
    private Componente componente;
    @JoinColumn(name = "CICLO", referencedColumnName = "ID_CICLO")
    @ManyToOne(optional = false)
    private Ciclo ciclo;

    public AtrComponente() {
    }

    public AtrComponente(BigDecimal idAtrComponente) {
        this.idAtrComponente = idAtrComponente;
    }

    public AtrComponente(BigDecimal idAtrComponente, String usuario, String programa, Date fechaModif, Date fechaBaja, String tipoConsumo, String ramal, int periodoIni, int periodoFin) {
        this.idAtrComponente = idAtrComponente;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.fechaBaja = fechaBaja;
        this.tipoConsumo = tipoConsumo;
        this.ramal = ramal;
        this.periodoIni = periodoIni;
        this.periodoFin = periodoFin;
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

    public BigDecimal getIdAtrComponente() {
        return idAtrComponente;
    }

    public void setIdAtrComponente(BigDecimal idAtrComponente) {
        this.idAtrComponente = idAtrComponente;
    }

    public BigDecimal getCargaInstalada() {
        return cargaInstalada;
    }

    public void setCargaInstalada(BigDecimal cargaInstalada) {
        this.cargaInstalada = cargaInstalada;
    }

    public BigDecimal getPotencia() {
        return potencia;
    }

    public void setPotencia(BigDecimal potencia) {
        this.potencia = potencia;
    }

    public Date getFechaInstalacion() {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(Date fechaInstalacion) {
        this.fechaInstalacion = fechaInstalacion;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public Short getEstrato() {
        return estrato;
    }

    public void setEstrato(Short estrato) {
        this.estrato = estrato;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public Long getConsumoPromedio() {
        return consumoPromedio;
    }

    public void setConsumoPromedio(Long consumoPromedio) {
        this.consumoPromedio = consumoPromedio;
    }

    public int getPeriodoIni() {
        return periodoIni;
    }

    public void setPeriodoIni(int periodoIni) {
        this.periodoIni = periodoIni;
    }

    public int getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(int periodoFin) {
        this.periodoFin = periodoFin;
    }

    public String getApoyo() {
        return apoyo;
    }

    public void setApoyo(String apoyo) {
        this.apoyo = apoyo;
    }

    public Short getFases() {
        return fases;
    }

    public void setFases(Short fases) {
        this.fases = fases;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    public Tbltipo getTbltipo1() {
        return tbltipo1;
    }

    public void setTbltipo1(Tbltipo tbltipo1) {
        this.tbltipo1 = tbltipo1;
    }

    public Tbltipo getTbltipo2() {
        return tbltipo2;
    }

    public void setTbltipo2(Tbltipo tbltipo2) {
        this.tbltipo2 = tbltipo2;
    }

    public Tbltipo getTbltipo3() {
        return tbltipo3;
    }

    public void setTbltipo3(Tbltipo tbltipo3) {
        this.tbltipo3 = tbltipo3;
    }

    public Tbltipo getTbltipo4() {
        return tbltipo4;
    }

    public void setTbltipo4(Tbltipo tbltipo4) {
        this.tbltipo4 = tbltipo4;
    }

    public Tbltipo getTbltipo5() {
        return tbltipo5;
    }

    public void setTbltipo5(Tbltipo tbltipo5) {
        this.tbltipo5 = tbltipo5;
    }

    public Tbltipo getTbltipo6() {
        return tbltipo6;
    }

    public void setTbltipo6(Tbltipo tbltipo6) {
        this.tbltipo6 = tbltipo6;
    }

    public Tbltipo getTbltipo7() {
        return tbltipo7;
    }

    public void setTbltipo7(Tbltipo tbltipo7) {
        this.tbltipo7 = tbltipo7;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Estado getEstado1() {
        return estado1;
    }

    public void setEstado1(Estado estado1) {
        this.estado1 = estado1;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public Ciclo getCiclo() {
        return ciclo;
    }

    public void setCiclo(Ciclo ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAtrComponente != null ? idAtrComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AtrComponente)) {
            return false;
        }
        AtrComponente other = (AtrComponente) object;
        if ((this.idAtrComponente == null && other.idAtrComponente != null) || (this.idAtrComponente != null && !this.idAtrComponente.equals(other.idAtrComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.AtrComponente[idAtrComponente=" + idAtrComponente + "]";
    }

    /**
     * @return the contrato
     */
    public Long getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(Long contrato) {
        this.contrato = contrato;
    }

    /**
     * @return the nif
     */
    public Long getNif() {
        return nif;
    }

    /**
     * @param nif the nif to set
     */
    public void setNif(Long nif) {
        this.nif = nif;
    }

    /**
     * @return the aolFin
     */
    public Long getAolFin() {
        return aolFin;
    }

    /**
     * @param aolFin the aolFin to set
     */
    public void setAolFin(Long aolFin) {
        this.aolFin = aolFin;
    }

}