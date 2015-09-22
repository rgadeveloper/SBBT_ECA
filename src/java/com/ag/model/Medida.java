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
@Table(name = "MEDIDA")
@NamedQueries({
    @NamedQuery(name = "Medida.findAll", query = "SELECT m FROM Medida m"),
    @NamedQuery(name = "Medida.findByUsuario", query = "SELECT m FROM Medida m WHERE m.usuario = :usuario"),
    @NamedQuery(name = "Medida.findByPrograma", query = "SELECT m FROM Medida m WHERE m.programa = :programa"),
    @NamedQuery(name = "Medida.findByFechaModif", query = "SELECT m FROM Medida m WHERE m.fechaModif = :fechaModif"),
    @NamedQuery(name = "Medida.findByIdComponente", query = "SELECT m FROM Medida m WHERE m.medidaPK.idComponente = :idComponente"),
    @NamedQuery(name = "Medida.findByIdTipoComponente", query = "SELECT m FROM Medida m WHERE m.medidaPK.idTipoComponente = :idTipoComponente"),
    @NamedQuery(name = "Medida.findByPeriodo", query = "SELECT m FROM Medida m WHERE m.medidaPK.periodo = :periodo"),
    @NamedQuery(name = "Medida.findByLecturaActual", query = "SELECT m FROM Medida m WHERE m.lecturaActual = :lecturaActual"),
    @NamedQuery(name = "Medida.findByConsumoFacturado", query = "SELECT m FROM Medida m WHERE m.consumoFacturado = :consumoFacturado"),
    @NamedQuery(name = "Medida.findByConsumoCalculado", query = "SELECT m FROM Medida m WHERE m.consumoCalculado = :consumoCalculado"),
    @NamedQuery(name = "Medida.findByDiasFacturados", query = "SELECT m FROM Medida m WHERE m.diasFacturados = :diasFacturados"),
    @NamedQuery(name = "Medida.findByFechaLecturaActual", query = "SELECT m FROM Medida m WHERE m.fechaLecturaActual = :fechaLecturaActual"),
    @NamedQuery(name = "Medida.findByObservacion", query = "SELECT m FROM Medida m WHERE m.observacion = :observacion"),
    @NamedQuery(name = "Medida.findByFechaFacturacion", query = "SELECT m FROM Medida m WHERE m.fechaFacturacion = :fechaFacturacion"),
    @NamedQuery(name = "Medida.findByConsumoRefacturado", query = "SELECT m FROM Medida m WHERE m.consumoRefacturado = :consumoRefacturado"),
    @NamedQuery(name = "Medida.findByConstanteLectura", query = "SELECT m FROM Medida m WHERE m.constanteLectura = :constanteLectura"),
    @NamedQuery(name = "Medida.findByFechaLecturaTeorica", query = "SELECT m FROM Medida m WHERE m.fechaLecturaTeorica = :fechaLecturaTeorica"),
    @NamedQuery(name = "Medida.findByConsumoAdicional", query = "SELECT m FROM Medida m WHERE m.consumoAdicional = :consumoAdicional"),
    @NamedQuery(name = "Medida.findByTipoConsumo", query = "SELECT m FROM Medida m WHERE m.tipoConsumo = :tipoConsumo"),
    @NamedQuery(name = "Medida.findByCiclo", query = "SELECT m FROM Medida m WHERE m.ciclo = :ciclo"),
    @NamedQuery(name = "Medida.findByTipoLecturaAnterior", query = "SELECT m FROM Medida m WHERE m.tipoLecturaAnterior = :tipoLecturaAnterior"),
    @NamedQuery(name = "Medida.findByLecturaAnterior", query = "SELECT m FROM Medida m WHERE m.lecturaAnterior = :lecturaAnterior"),
    @NamedQuery(name = "Medida.findByFechaLecturaAnterior", query = "SELECT m FROM Medida m WHERE m.fechaLecturaAnterior = :fechaLecturaAnterior"),
    @NamedQuery(name = "Medida.findByTipoAnomaliaLectAnterior", query = "SELECT m FROM Medida m WHERE m.tipoAnomaliaLectAnterior = :tipoAnomaliaLectAnterior"),
    @NamedQuery(name = "Medida.findByConsPromIndivTrim", query = "SELECT m FROM Medida m WHERE m.consPromIndivTrim = :consPromIndivTrim"),
    @NamedQuery(name = "Medida.findByConsPromIndivSem", query = "SELECT m FROM Medida m WHERE m.consPromIndivSem = :consPromIndivSem"),
    @NamedQuery(name = "Medida.findByConsPromEstrato", query = "SELECT m FROM Medida m WHERE m.consPromEstrato = :consPromEstrato"),
    @NamedQuery(name = "Medida.findByConsPromUso", query = "SELECT m FROM Medida m WHERE m.consPromUso = :consPromUso")})
public class Medida implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MedidaPK medidaPK;
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
    @Column(name = "LECTURA_ACTUAL")
    private Long lecturaActual;
    @Basic(optional = false)
    @Column(name = "CONSUMO_FACTURADO")
    private BigDecimal consumoFacturado;
    @Column(name = "CONSUMO_CALCULADO")
    private BigDecimal consumoCalculado;
    @Basic(optional = false)
    @Column(name = "DIAS_FACTURADOS")
    private short diasFacturados;
    @Basic(optional = false)
    @Column(name = "FECHA_LECTURA_ACTUAL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLecturaActual;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Basic(optional = false)
    @Column(name = "FECHA_FACTURACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFacturacion;
    @Column(name = "CONSUMO_REFACTURADO")
    private BigDecimal consumoRefacturado;
    @Column(name = "CONSTANTE_LECTURA")
    private Long constanteLectura;
    @Column(name = "FECHA_LECTURA_TEORICA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLecturaTeorica;
    @Column(name = "CONSUMO_ADICIONAL")
    private BigDecimal consumoAdicional;
    @Column(name = "TIPO_CONSUMO")
    private String tipoConsumo;
    @Basic(optional = false)
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
    @JoinColumn(name = "ID_TIPO_COMPONENTE", referencedColumnName = "ID_TIPO_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TipoComponente tipoComponente;
    @JoinColumn(name = "TIPO_ANOMALIA_LECT_ACTUAL", referencedColumnName = "TIPO")
    @ManyToOne
    private Tbltipo tbltipo;
    @JoinColumn(name = "TIPO_LECTURA_ACTUAL", referencedColumnName = "TIPO")
    @ManyToOne
    private Tbltipo tbltipo1;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componente componente;

    public Medida() {
    }

    public Medida(MedidaPK medidaPK) {
        this.medidaPK = medidaPK;
    }

    public Medida(MedidaPK medidaPK, String usuario, String programa, Date fechaModif, BigDecimal consumoFacturado, short diasFacturados, Date fechaLecturaActual, Date fechaFacturacion, String ciclo) {
        this.medidaPK = medidaPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.consumoFacturado = consumoFacturado;
        this.diasFacturados = diasFacturados;
        this.fechaLecturaActual = fechaLecturaActual;
        this.fechaFacturacion = fechaFacturacion;
        this.ciclo = ciclo;
    }

    public Medida(BigInteger idComponente, short idTipoComponente, int periodo) {
        this.medidaPK = new MedidaPK(idComponente, idTipoComponente, periodo);
    }

    public MedidaPK getMedidaPK() {
        return medidaPK;
    }

    public void setMedidaPK(MedidaPK medidaPK) {
        this.medidaPK = medidaPK;
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

    public Long getLecturaActual() {
        return lecturaActual;
    }

    public void setLecturaActual(Long lecturaActual) {
        this.lecturaActual = lecturaActual;
    }

    public BigDecimal getConsumoFacturado() {
        return consumoFacturado;
    }

    public void setConsumoFacturado(BigDecimal consumoFacturado) {
        this.consumoFacturado = consumoFacturado;
    }

    public BigDecimal getConsumoCalculado() {
        return consumoCalculado;
    }

    public void setConsumoCalculado(BigDecimal consumoCalculado) {
        this.consumoCalculado = consumoCalculado;
    }

    public short getDiasFacturados() {
        return diasFacturados;
    }

    public void setDiasFacturados(short diasFacturados) {
        this.diasFacturados = diasFacturados;
    }

    public Date getFechaLecturaActual() {
        return fechaLecturaActual;
    }

    public void setFechaLecturaActual(Date fechaLecturaActual) {
        this.fechaLecturaActual = fechaLecturaActual;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public BigDecimal getConsumoRefacturado() {
        return consumoRefacturado;
    }

    public void setConsumoRefacturado(BigDecimal consumoRefacturado) {
        this.consumoRefacturado = consumoRefacturado;
    }

    public Long getConstanteLectura() {
        return constanteLectura;
    }

    public void setConstanteLectura(Long constanteLectura) {
        this.constanteLectura = constanteLectura;
    }

    public Date getFechaLecturaTeorica() {
        return fechaLecturaTeorica;
    }

    public void setFechaLecturaTeorica(Date fechaLecturaTeorica) {
        this.fechaLecturaTeorica = fechaLecturaTeorica;
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

    public TipoComponente getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(TipoComponente tipoComponente) {
        this.tipoComponente = tipoComponente;
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

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medidaPK != null ? medidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medida)) {
            return false;
        }
        Medida other = (Medida) object;
        if ((this.medidaPK == null && other.medidaPK != null) || (this.medidaPK != null && !this.medidaPK.equals(other.medidaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Medida[medidaPK=" + medidaPK + "]";
    }

}
