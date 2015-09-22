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
@Table(name = "INT_LECTURAS")
@NamedQueries({
    @NamedQuery(name = "IntLecturas.findAll", query = "SELECT i FROM IntLecturas i"),
    @NamedQuery(name = "IntLecturas.findByIdCliente", query = "SELECT i FROM IntLecturas i WHERE i.intLecturasPK.idCliente = :idCliente"),
    @NamedQuery(name = "IntLecturas.findByFechaModif", query = "SELECT i FROM IntLecturas i WHERE i.fechaModif = :fechaModif"),
    @NamedQuery(name = "IntLecturas.findByIdTipoComponente", query = "SELECT i FROM IntLecturas i WHERE i.intLecturasPK.idTipoComponente = :idTipoComponente"),
    @NamedQuery(name = "IntLecturas.findByPeriodo", query = "SELECT i FROM IntLecturas i WHERE i.intLecturasPK.periodo = :periodo"),
    @NamedQuery(name = "IntLecturas.findByLecturaActual", query = "SELECT i FROM IntLecturas i WHERE i.lecturaActual = :lecturaActual"),
    @NamedQuery(name = "IntLecturas.findByConsumoCalculado", query = "SELECT i FROM IntLecturas i WHERE i.consumoCalculado = :consumoCalculado"),
    @NamedQuery(name = "IntLecturas.findByConsumoFacturado", query = "SELECT i FROM IntLecturas i WHERE i.consumoFacturado = :consumoFacturado"),
    @NamedQuery(name = "IntLecturas.findByDiasFacturados", query = "SELECT i FROM IntLecturas i WHERE i.diasFacturados = :diasFacturados"),
    @NamedQuery(name = "IntLecturas.findByFechaLecturaActual", query = "SELECT i FROM IntLecturas i WHERE i.fechaLecturaActual = :fechaLecturaActual"),
    @NamedQuery(name = "IntLecturas.findByTipoLectura", query = "SELECT i FROM IntLecturas i WHERE i.tipoLectura = :tipoLectura"),
    @NamedQuery(name = "IntLecturas.findByObservacion", query = "SELECT i FROM IntLecturas i WHERE i.observacion = :observacion"),
    @NamedQuery(name = "IntLecturas.findByTipoAnomalia", query = "SELECT i FROM IntLecturas i WHERE i.tipoAnomalia = :tipoAnomalia"),
    @NamedQuery(name = "IntLecturas.findByFechaFacturacion", query = "SELECT i FROM IntLecturas i WHERE i.fechaFacturacion = :fechaFacturacion"),
    @NamedQuery(name = "IntLecturas.findByConstanteLectura", query = "SELECT i FROM IntLecturas i WHERE i.constanteLectura = :constanteLectura"),
    @NamedQuery(name = "IntLecturas.findByConsumoAdicional", query = "SELECT i FROM IntLecturas i WHERE i.consumoAdicional = :consumoAdicional"),
    @NamedQuery(name = "IntLecturas.findByTipoConsumo", query = "SELECT i FROM IntLecturas i WHERE i.tipoConsumo = :tipoConsumo"),
    @NamedQuery(name = "IntLecturas.findByCiclo", query = "SELECT i FROM IntLecturas i WHERE i.ciclo = :ciclo"),
    @NamedQuery(name = "IntLecturas.findByTipoLecturaAnterior", query = "SELECT i FROM IntLecturas i WHERE i.tipoLecturaAnterior = :tipoLecturaAnterior"),
    @NamedQuery(name = "IntLecturas.findByLecturaAnterior", query = "SELECT i FROM IntLecturas i WHERE i.lecturaAnterior = :lecturaAnterior"),
    @NamedQuery(name = "IntLecturas.findByFechaLecturaAnterior", query = "SELECT i FROM IntLecturas i WHERE i.fechaLecturaAnterior = :fechaLecturaAnterior"),
    @NamedQuery(name = "IntLecturas.findByTipoAnomaliaLectAnterior", query = "SELECT i FROM IntLecturas i WHERE i.tipoAnomaliaLectAnterior = :tipoAnomaliaLectAnterior"),
    @NamedQuery(name = "IntLecturas.findByConsPromIndivTrim", query = "SELECT i FROM IntLecturas i WHERE i.consPromIndivTrim = :consPromIndivTrim"),
    @NamedQuery(name = "IntLecturas.findByConsPromIndivSem", query = "SELECT i FROM IntLecturas i WHERE i.consPromIndivSem = :consPromIndivSem"),
    @NamedQuery(name = "IntLecturas.findByConsPromEstrato", query = "SELECT i FROM IntLecturas i WHERE i.consPromEstrato = :consPromEstrato"),
    @NamedQuery(name = "IntLecturas.findByConsPromUso", query = "SELECT i FROM IntLecturas i WHERE i.consPromUso = :consPromUso"),
    @NamedQuery(name = "IntLecturas.findByDescProceso", query = "SELECT i FROM IntLecturas i WHERE i.descProceso = :descProceso"),
    @NamedQuery(name = "IntLecturas.findByProcesado", query = "SELECT i FROM IntLecturas i WHERE i.procesado = :procesado"),
    @NamedQuery(name = "IntLecturas.findByFechaProceso", query = "SELECT i FROM IntLecturas i WHERE i.fechaProceso = :fechaProceso")})
public class IntLecturas implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntLecturasPK intLecturasPK;
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Column(name = "LECTURA_ACTUAL")
    private Long lecturaActual;
    @Column(name = "CONSUMO_CALCULADO")
    private BigDecimal consumoCalculado;
    @Column(name = "CONSUMO_FACTURADO")
    private BigDecimal consumoFacturado;
    @Column(name = "DIAS_FACTURADOS")
    private Short diasFacturados;
    @Column(name = "FECHA_LECTURA_ACTUAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLecturaActual;
    @Column(name = "TIPO_LECTURA")
    private String tipoLectura;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "TIPO_ANOMALIA")
    private String tipoAnomalia;
    @Column(name = "FECHA_FACTURACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFacturacion;
    @Column(name = "CONSTANTE_LECTURA")
    private Long constanteLectura;
    @Column(name = "CONSUMO_ADICIONAL")
    private BigDecimal consumoAdicional;
    @Column(name = "TIPO_CONSUMO")
    private String tipoConsumo;
    @Column(name = "CICLO")
    private String ciclo;
    @Column(name = "TIPO_LECTURA_ANTERIOR")
    private String tipoLecturaAnterior;
    @Column(name = "LECTURA_ANTERIOR")
    private Long lecturaAnterior;
    @Column(name = "FECHA_LECTURA_ANTERIOR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLecturaAnterior;
    @Column(name = "TIPO_ANOMALIA_LECT_ANTERIOR")
    private String tipoAnomaliaLectAnterior;
    @Column(name = "CONS_PROM_INDIV_TRIM")
    private BigDecimal consPromIndivTrim;
    @Column(name = "CONS_PROM_INDIV_SEM")
    private BigDecimal consPromIndivSem;
    @Column(name = "CONS_PROM_ESTRATO")
    private BigDecimal consPromEstrato;
    @Column(name = "CONS_PROM_USO")
    private BigDecimal consPromUso;
    @Column(name = "DESC_PROCESO")
    private String descProceso;
    @Column(name = "PROCESADO")
    private Short procesado;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;

    public IntLecturas() {
    }

    public IntLecturas(IntLecturasPK intLecturasPK) {
        this.intLecturasPK = intLecturasPK;
    }

    public IntLecturas(String idCliente, short idTipoComponente, int periodo) {
        this.intLecturasPK = new IntLecturasPK(idCliente, idTipoComponente, periodo);
    }

    public IntLecturasPK getIntLecturasPK() {
        return intLecturasPK;
    }

    public void setIntLecturasPK(IntLecturasPK intLecturasPK) {
        this.intLecturasPK = intLecturasPK;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public Long getLecturaActual() {
        return lecturaActual;
    }

    public void setLecturaActual(Long lecturaActual) {
        this.lecturaActual = lecturaActual;
    }

    public BigDecimal getConsumoCalculado() {
        return consumoCalculado;
    }

    public void setConsumoCalculado(BigDecimal consumoCalculado) {
        this.consumoCalculado = consumoCalculado;
    }

    public BigDecimal getConsumoFacturado() {
        return consumoFacturado;
    }

    public void setConsumoFacturado(BigDecimal consumoFacturado) {
        this.consumoFacturado = consumoFacturado;
    }

    public Short getDiasFacturados() {
        return diasFacturados;
    }

    public void setDiasFacturados(Short diasFacturados) {
        this.diasFacturados = diasFacturados;
    }

    public Date getFechaLecturaActual() {
        return fechaLecturaActual;
    }

    public void setFechaLecturaActual(Date fechaLecturaActual) {
        this.fechaLecturaActual = fechaLecturaActual;
    }

    public String getTipoLectura() {
        return tipoLectura;
    }

    public void setTipoLectura(String tipoLectura) {
        this.tipoLectura = tipoLectura;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getTipoAnomalia() {
        return tipoAnomalia;
    }

    public void setTipoAnomalia(String tipoAnomalia) {
        this.tipoAnomalia = tipoAnomalia;
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public Long getConstanteLectura() {
        return constanteLectura;
    }

    public void setConstanteLectura(Long constanteLectura) {
        this.constanteLectura = constanteLectura;
    }

    public BigDecimal getConsumoAdicional() {
        return consumoAdicional;
    }

    public void setConsumoAdicional(BigDecimal consumoAdicional) {
        this.consumoAdicional = consumoAdicional;
    }

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getTipoLecturaAnterior() {
        return tipoLecturaAnterior;
    }

    public void setTipoLecturaAnterior(String tipoLecturaAnterior) {
        this.tipoLecturaAnterior = tipoLecturaAnterior;
    }

    public Long getLecturaAnterior() {
        return lecturaAnterior;
    }

    public void setLecturaAnterior(Long lecturaAnterior) {
        this.lecturaAnterior = lecturaAnterior;
    }

    public Date getFechaLecturaAnterior() {
        return fechaLecturaAnterior;
    }

    public void setFechaLecturaAnterior(Date fechaLecturaAnterior) {
        this.fechaLecturaAnterior = fechaLecturaAnterior;
    }

    public String getTipoAnomaliaLectAnterior() {
        return tipoAnomaliaLectAnterior;
    }

    public void setTipoAnomaliaLectAnterior(String tipoAnomaliaLectAnterior) {
        this.tipoAnomaliaLectAnterior = tipoAnomaliaLectAnterior;
    }

    public BigDecimal getConsPromIndivTrim() {
        return consPromIndivTrim;
    }

    public void setConsPromIndivTrim(BigDecimal consPromIndivTrim) {
        this.consPromIndivTrim = consPromIndivTrim;
    }

    public BigDecimal getConsPromIndivSem() {
        return consPromIndivSem;
    }

    public void setConsPromIndivSem(BigDecimal consPromIndivSem) {
        this.consPromIndivSem = consPromIndivSem;
    }

    public BigDecimal getConsPromEstrato() {
        return consPromEstrato;
    }

    public void setConsPromEstrato(BigDecimal consPromEstrato) {
        this.consPromEstrato = consPromEstrato;
    }

    public BigDecimal getConsPromUso() {
        return consPromUso;
    }

    public void setConsPromUso(BigDecimal consPromUso) {
        this.consPromUso = consPromUso;
    }

    public String getDescProceso() {
        return descProceso;
    }

    public void setDescProceso(String descProceso) {
        this.descProceso = descProceso;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intLecturasPK != null ? intLecturasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntLecturas)) {
            return false;
        }
        IntLecturas other = (IntLecturas) object;
        if ((this.intLecturasPK == null && other.intLecturasPK != null) || (this.intLecturasPK != null && !this.intLecturasPK.equals(other.intLecturasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntLecturas[intLecturasPK=" + intLecturasPK + "]";
    }

}
