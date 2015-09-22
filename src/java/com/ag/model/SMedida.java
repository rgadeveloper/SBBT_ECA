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
@Table(name = "S_MEDIDA")
@NamedQueries({
    @NamedQuery(name = "SMedida.findAll", query = "SELECT s FROM SMedida s"),
    @NamedQuery(name = "SMedida.findByUsuario", query = "SELECT s FROM SMedida s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SMedida.findByPrograma", query = "SELECT s FROM SMedida s WHERE s.programa = :programa"),
    @NamedQuery(name = "SMedida.findByFechaModif", query = "SELECT s FROM SMedida s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SMedida.findByIdComponente", query = "SELECT s FROM SMedida s WHERE s.sMedidaPK.idComponente = :idComponente"),
    @NamedQuery(name = "SMedida.findByIdTipoComponente", query = "SELECT s FROM SMedida s WHERE s.sMedidaPK.idTipoComponente = :idTipoComponente"),
    @NamedQuery(name = "SMedida.findByPeriodo", query = "SELECT s FROM SMedida s WHERE s.sMedidaPK.periodo = :periodo"),
    @NamedQuery(name = "SMedida.findByLecturaActual", query = "SELECT s FROM SMedida s WHERE s.lecturaActual = :lecturaActual"),
    @NamedQuery(name = "SMedida.findByConsumoFacturado", query = "SELECT s FROM SMedida s WHERE s.consumoFacturado = :consumoFacturado"),
    @NamedQuery(name = "SMedida.findByConsumoCalculado", query = "SELECT s FROM SMedida s WHERE s.consumoCalculado = :consumoCalculado"),
    @NamedQuery(name = "SMedida.findByDiasFacturados", query = "SELECT s FROM SMedida s WHERE s.diasFacturados = :diasFacturados"),
    @NamedQuery(name = "SMedida.findByFechaLecturaActual", query = "SELECT s FROM SMedida s WHERE s.fechaLecturaActual = :fechaLecturaActual"),
    @NamedQuery(name = "SMedida.findByObservacion", query = "SELECT s FROM SMedida s WHERE s.observacion = :observacion"),
    @NamedQuery(name = "SMedida.findByFechaFacturacion", query = "SELECT s FROM SMedida s WHERE s.fechaFacturacion = :fechaFacturacion"),
    @NamedQuery(name = "SMedida.findByConsumoRefacturado", query = "SELECT s FROM SMedida s WHERE s.consumoRefacturado = :consumoRefacturado"),
    @NamedQuery(name = "SMedida.findByConstanteLectura", query = "SELECT s FROM SMedida s WHERE s.constanteLectura = :constanteLectura"),
    @NamedQuery(name = "SMedida.findByFechaLecturaTeorica", query = "SELECT s FROM SMedida s WHERE s.fechaLecturaTeorica = :fechaLecturaTeorica"),
    @NamedQuery(name = "SMedida.findByConsumoAdicional", query = "SELECT s FROM SMedida s WHERE s.consumoAdicional = :consumoAdicional"),
    @NamedQuery(name = "SMedida.findByTipoConsumo", query = "SELECT s FROM SMedida s WHERE s.tipoConsumo = :tipoConsumo"),
    @NamedQuery(name = "SMedida.findByCiclo", query = "SELECT s FROM SMedida s WHERE s.ciclo = :ciclo"),
    @NamedQuery(name = "SMedida.findByTipoLecturaAnterior", query = "SELECT s FROM SMedida s WHERE s.tipoLecturaAnterior = :tipoLecturaAnterior"),
    @NamedQuery(name = "SMedida.findByLecturaAnterior", query = "SELECT s FROM SMedida s WHERE s.lecturaAnterior = :lecturaAnterior"),
    @NamedQuery(name = "SMedida.findByFechaLecturaAnterior", query = "SELECT s FROM SMedida s WHERE s.fechaLecturaAnterior = :fechaLecturaAnterior"),
    @NamedQuery(name = "SMedida.findByTipoAnomaliaLectAnterior", query = "SELECT s FROM SMedida s WHERE s.tipoAnomaliaLectAnterior = :tipoAnomaliaLectAnterior"),
    @NamedQuery(name = "SMedida.findByConsPromIndivTrim", query = "SELECT s FROM SMedida s WHERE s.consPromIndivTrim = :consPromIndivTrim"),
    @NamedQuery(name = "SMedida.findByConsPromIndivSem", query = "SELECT s FROM SMedida s WHERE s.consPromIndivSem = :consPromIndivSem"),
    @NamedQuery(name = "SMedida.findByConsPromEstrato", query = "SELECT s FROM SMedida s WHERE s.consPromEstrato = :consPromEstrato"),
    @NamedQuery(name = "SMedida.findByConsPromUso", query = "SELECT s FROM SMedida s WHERE s.consPromUso = :consPromUso")})
public class SMedida implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SMedidaPK sMedidaPK;
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
    private SComponente sComponente;

    public SMedida() {
    }

    public SMedida(SMedidaPK sMedidaPK) {
        this.sMedidaPK = sMedidaPK;
    }

    public SMedida(SMedidaPK sMedidaPK, String usuario, String programa, Date fechaModif, BigDecimal consumoFacturado, short diasFacturados, Date fechaLecturaActual, Date fechaFacturacion, String ciclo) {
        this.sMedidaPK = sMedidaPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.consumoFacturado = consumoFacturado;
        this.diasFacturados = diasFacturados;
        this.fechaLecturaActual = fechaLecturaActual;
        this.fechaFacturacion = fechaFacturacion;
        this.ciclo = ciclo;
    }

    public SMedida(BigInteger idComponente, short idTipoComponente, int periodo) {
        this.sMedidaPK = new SMedidaPK(idComponente, idTipoComponente, periodo);
    }

    public SMedidaPK getSMedidaPK() {
        return sMedidaPK;
    }

    public void setSMedidaPK(SMedidaPK sMedidaPK) {
        this.sMedidaPK = sMedidaPK;
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

    public SComponente getSComponente() {
        return sComponente;
    }

    public void setSComponente(SComponente sComponente) {
        this.sComponente = sComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sMedidaPK != null ? sMedidaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SMedida)) {
            return false;
        }
        SMedida other = (SMedida) object;
        if ((this.sMedidaPK == null && other.sMedidaPK != null) || (this.sMedidaPK != null && !this.sMedidaPK.equals(other.sMedidaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SMedida[sMedidaPK=" + sMedidaPK + "]";
    }

}
