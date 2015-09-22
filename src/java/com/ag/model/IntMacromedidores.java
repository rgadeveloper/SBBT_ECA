/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "INT_MACROMEDIDORES")
@NamedQueries({
    @NamedQuery(name = "IntMacromedidores.findAll", query = "SELECT i FROM IntMacromedidores i"),
    @NamedQuery(name = "IntMacromedidores.findByIdCliente", query = "SELECT i FROM IntMacromedidores i WHERE i.intMacromedidoresPK.idCliente = :idCliente"),
    @NamedQuery(name = "IntMacromedidores.findByFechaModif", query = "SELECT i FROM IntMacromedidores i WHERE i.fechaModif = :fechaModif"),
    @NamedQuery(name = "IntMacromedidores.findByNombre", query = "SELECT i FROM IntMacromedidores i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IntMacromedidores.findByDireccion", query = "SELECT i FROM IntMacromedidores i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "IntMacromedidores.findByNumeroRuedas", query = "SELECT i FROM IntMacromedidores i WHERE i.numeroRuedas = :numeroRuedas"),
    @NamedQuery(name = "IntMacromedidores.findByTipoMedidor", query = "SELECT i FROM IntMacromedidores i WHERE i.tipoMedidor = :tipoMedidor"),
    @NamedQuery(name = "IntMacromedidores.findByLecturaInicial", query = "SELECT i FROM IntMacromedidores i WHERE i.lecturaInicial = :lecturaInicial"),
    @NamedQuery(name = "IntMacromedidores.findByLecturaFinal", query = "SELECT i FROM IntMacromedidores i WHERE i.lecturaFinal = :lecturaFinal"),
    @NamedQuery(name = "IntMacromedidores.findByRelacionTc", query = "SELECT i FROM IntMacromedidores i WHERE i.relacionTc = :relacionTc"),
    @NamedQuery(name = "IntMacromedidores.findByRtp", query = "SELECT i FROM IntMacromedidores i WHERE i.rtp = :rtp"),
    @NamedQuery(name = "IntMacromedidores.findByConstante", query = "SELECT i FROM IntMacromedidores i WHERE i.constante = :constante"),
    @NamedQuery(name = "IntMacromedidores.findByFechaAlta", query = "SELECT i FROM IntMacromedidores i WHERE i.fechaAlta = :fechaAlta"),
    @NamedQuery(name = "IntMacromedidores.findByFechaBaja", query = "SELECT i FROM IntMacromedidores i WHERE i.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "IntMacromedidores.findByTipoRed", query = "SELECT i FROM IntMacromedidores i WHERE i.tipoRed = :tipoRed"),
    @NamedQuery(name = "IntMacromedidores.findByEstadoIntervenido", query = "SELECT i FROM IntMacromedidores i WHERE i.estadoIntervenido = :estadoIntervenido"),
    @NamedQuery(name = "IntMacromedidores.findBySellosTp", query = "SELECT i FROM IntMacromedidores i WHERE i.sellosTp = :sellosTp"),
    @NamedQuery(name = "IntMacromedidores.findBySellosTb", query = "SELECT i FROM IntMacromedidores i WHERE i.sellosTb = :sellosTb"),
    @NamedQuery(name = "IntMacromedidores.findByFechaInstalacion", query = "SELECT i FROM IntMacromedidores i WHERE i.fechaInstalacion = :fechaInstalacion"),
    @NamedQuery(name = "IntMacromedidores.findByFaseR", query = "SELECT i FROM IntMacromedidores i WHERE i.faseR = :faseR"),
    @NamedQuery(name = "IntMacromedidores.findByFaseT", query = "SELECT i FROM IntMacromedidores i WHERE i.faseT = :faseT"),
    @NamedQuery(name = "IntMacromedidores.findByFaseS", query = "SELECT i FROM IntMacromedidores i WHERE i.faseS = :faseS"),
    @NamedQuery(name = "IntMacromedidores.findByTensionRs", query = "SELECT i FROM IntMacromedidores i WHERE i.tensionRs = :tensionRs"),
    @NamedQuery(name = "IntMacromedidores.findByTensionSt", query = "SELECT i FROM IntMacromedidores i WHERE i.tensionSt = :tensionSt"),
    @NamedQuery(name = "IntMacromedidores.findByTensionTr", query = "SELECT i FROM IntMacromedidores i WHERE i.tensionTr = :tensionTr"),
    @NamedQuery(name = "IntMacromedidores.findByProcesado", query = "SELECT i FROM IntMacromedidores i WHERE i.procesado = :procesado"),
    @NamedQuery(name = "IntMacromedidores.findByFechaProceso", query = "SELECT i FROM IntMacromedidores i WHERE i.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "IntMacromedidores.findByDescProceso", query = "SELECT i FROM IntMacromedidores i WHERE i.descProceso = :descProceso"),
    @NamedQuery(name = "IntMacromedidores.findByCategoria", query = "SELECT i FROM IntMacromedidores i WHERE i.categoria = :categoria"),
    @NamedQuery(name = "IntMacromedidores.findByPeriodo", query = "SELECT i FROM IntMacromedidores i WHERE i.intMacromedidoresPK.periodo = :periodo")})
public class IntMacromedidores implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntMacromedidoresPK intMacromedidoresPK;
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "NUMERO_RUEDAS")
    private Long numeroRuedas;
    @Column(name = "TIPO_MEDIDOR")
    private String tipoMedidor;
    @Column(name = "LECTURA_INICIAL")
    private Long lecturaInicial;
    @Column(name = "LECTURA_FINAL")
    private Long lecturaFinal;
    @Column(name = "RELACION_TC")
    private String relacionTc;
    @Column(name = "RTP")
    private String rtp;
    @Column(name = "CONSTANTE")
    private Long constante;
    @Column(name = "FECHA_ALTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Column(name = "TIPO_RED")
    private String tipoRed;
    @Column(name = "ESTADO_INTERVENIDO")
    private String estadoIntervenido;
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
    @Column(name = "PROCESADO")
    private Short procesado;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Column(name = "DESC_PROCESO")
    private String descProceso;
    @Column(name = "CATEGORIA")
    private String categoria;

    public IntMacromedidores() {
    }

    public IntMacromedidores(IntMacromedidoresPK intMacromedidoresPK) {
        this.intMacromedidoresPK = intMacromedidoresPK;
    }

    public IntMacromedidores(String idCliente, int periodo) {
        this.intMacromedidoresPK = new IntMacromedidoresPK(idCliente, periodo);
    }

    public IntMacromedidoresPK getIntMacromedidoresPK() {
        return intMacromedidoresPK;
    }

    public void setIntMacromedidoresPK(IntMacromedidoresPK intMacromedidoresPK) {
        this.intMacromedidoresPK = intMacromedidoresPK;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getNumeroRuedas() {
        return numeroRuedas;
    }

    public void setNumeroRuedas(Long numeroRuedas) {
        this.numeroRuedas = numeroRuedas;
    }

    public String getTipoMedidor() {
        return tipoMedidor;
    }

    public void setTipoMedidor(String tipoMedidor) {
        this.tipoMedidor = tipoMedidor;
    }

    public Long getLecturaInicial() {
        return lecturaInicial;
    }

    public void setLecturaInicial(Long lecturaInicial) {
        this.lecturaInicial = lecturaInicial;
    }

    public Long getLecturaFinal() {
        return lecturaFinal;
    }

    public void setLecturaFinal(Long lecturaFinal) {
        this.lecturaFinal = lecturaFinal;
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

    public String getEstadoIntervenido() {
        return estadoIntervenido;
    }

    public void setEstadoIntervenido(String estadoIntervenido) {
        this.estadoIntervenido = estadoIntervenido;
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

    public Short getProcesado() {
        return procesado;
    }

    public void setProcesado(Short procesado) {
        this.procesado = procesado;
    }

    public Date getFechaProceso() {
        return fechaProceso;
    }

    public void setFechaProceso(Date fechaProceso) {
        this.fechaProceso = fechaProceso;
    }

    public String getDescProceso() {
        return descProceso;
    }

    public void setDescProceso(String descProceso) {
        this.descProceso = descProceso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intMacromedidoresPK != null ? intMacromedidoresPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntMacromedidores)) {
            return false;
        }
        IntMacromedidores other = (IntMacromedidores) object;
        if ((this.intMacromedidoresPK == null && other.intMacromedidoresPK != null) || (this.intMacromedidoresPK != null && !this.intMacromedidoresPK.equals(other.intMacromedidoresPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntMacromedidores[intMacromedidoresPK=" + intMacromedidoresPK + "]";
    }

}
