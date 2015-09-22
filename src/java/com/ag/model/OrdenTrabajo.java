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
@Table(name = "ORDEN_TRABAJO")
@NamedQueries({
    @NamedQuery(name = "OrdenTrabajo.findAll", query = "SELECT o FROM OrdenTrabajo o"),
    @NamedQuery(name = "OrdenTrabajo.findByUsuario", query = "SELECT o FROM OrdenTrabajo o WHERE o.usuario = :usuario"),
    @NamedQuery(name = "OrdenTrabajo.findByPrograma", query = "SELECT o FROM OrdenTrabajo o WHERE o.programa = :programa"),
    @NamedQuery(name = "OrdenTrabajo.findByFecha", query = "SELECT o FROM OrdenTrabajo o WHERE o.fecha = :fecha"),
    @NamedQuery(name = "OrdenTrabajo.findByIdOrden", query = "SELECT o FROM OrdenTrabajo o WHERE o.idOrden = :idOrden"),
    @NamedQuery(name = "OrdenTrabajo.findByCodigoMovilAsig", query = "SELECT o FROM OrdenTrabajo o WHERE o.codigoMovilAsig = :codigoMovilAsig"),
    @NamedQuery(name = "OrdenTrabajo.findByEstado", query = "SELECT o FROM OrdenTrabajo o WHERE o.estado = :estado"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaAsignacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaAsignacion = :fechaAsignacion"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaActualizacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaActualizacion = :fechaActualizacion"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaEjecucion", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaEjecucion = :fechaEjecucion"),
    @NamedQuery(name = "OrdenTrabajo.findByObservacion", query = "SELECT o FROM OrdenTrabajo o WHERE o.observacion = :observacion"),
    @NamedQuery(name = "OrdenTrabajo.findByNroSello", query = "SELECT o FROM OrdenTrabajo o WHERE o.nroSello = :nroSello"),
    @NamedQuery(name = "OrdenTrabajo.findByTipoSello", query = "SELECT o FROM OrdenTrabajo o WHERE o.tipoSello = :tipoSello"),
    @NamedQuery(name = "OrdenTrabajo.findByColorSello", query = "SELECT o FROM OrdenTrabajo o WHERE o.colorSello = :colorSello"),
    @NamedQuery(name = "OrdenTrabajo.findByEstConexion", query = "SELECT o FROM OrdenTrabajo o WHERE o.estConexion = :estConexion"),
    @NamedQuery(name = "OrdenTrabajo.findByEstContinuidad", query = "SELECT o FROM OrdenTrabajo o WHERE o.estContinuidad = :estContinuidad"),
    @NamedQuery(name = "OrdenTrabajo.findByEnviadoLab", query = "SELECT o FROM OrdenTrabajo o WHERE o.enviadoLab = :enviadoLab"),
    @NamedQuery(name = "OrdenTrabajo.findByObsPreDiagnostico", query = "SELECT o FROM OrdenTrabajo o WHERE o.obsPreDiagnostico = :obsPreDiagnostico"),
    @NamedQuery(name = "OrdenTrabajo.findByIrregularidad", query = "SELECT o FROM OrdenTrabajo o WHERE o.irregularidad = :irregularidad"),
    @NamedQuery(name = "OrdenTrabajo.findByObsIrregularidad", query = "SELECT o FROM OrdenTrabajo o WHERE o.obsIrregularidad = :obsIrregularidad"),
    @NamedQuery(name = "OrdenTrabajo.findByInstAcometida", query = "SELECT o FROM OrdenTrabajo o WHERE o.instAcometida = :instAcometida"),
    @NamedQuery(name = "OrdenTrabajo.findByInstMedidor", query = "SELECT o FROM OrdenTrabajo o WHERE o.instMedidor = :instMedidor"),
    @NamedQuery(name = "OrdenTrabajo.findByInstCaja", query = "SELECT o FROM OrdenTrabajo o WHERE o.instCaja = :instCaja"),
    @NamedQuery(name = "OrdenTrabajo.findByRevMedidorLab", query = "SELECT o FROM OrdenTrabajo o WHERE o.revMedidorLab = :revMedidorLab"),
    @NamedQuery(name = "OrdenTrabajo.findByInstTablero", query = "SELECT o FROM OrdenTrabajo o WHERE o.instTablero = :instTablero"),
    @NamedQuery(name = "OrdenTrabajo.findBySuspension", query = "SELECT o FROM OrdenTrabajo o WHERE o.suspension = :suspension"),
    @NamedQuery(name = "OrdenTrabajo.findByInstPuestaTierra", query = "SELECT o FROM OrdenTrabajo o WHERE o.instPuestaTierra = :instPuestaTierra"),
    @NamedQuery(name = "OrdenTrabajo.findByAdecCarga", query = "SELECT o FROM OrdenTrabajo o WHERE o.adecCarga = :adecCarga"),
    @NamedQuery(name = "OrdenTrabajo.findByTipoAct", query = "SELECT o FROM OrdenTrabajo o WHERE o.tipoAct = :tipoAct"),
    @NamedQuery(name = "OrdenTrabajo.findByTerminal", query = "SELECT o FROM OrdenTrabajo o WHERE o.terminal = :terminal"),
    @NamedQuery(name = "OrdenTrabajo.findByLote", query = "SELECT o FROM OrdenTrabajo o WHERE o.lote = :lote"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaDescarga", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaDescarga = :fechaDescarga"),
    @NamedQuery(name = "OrdenTrabajo.findByFechaCarga", query = "SELECT o FROM OrdenTrabajo o WHERE o.fechaCarga = :fechaCarga"),
    @NamedQuery(name = "OrdenTrabajo.findByProcedencia", query = "SELECT o FROM OrdenTrabajo o WHERE o.procedencia = :procedencia"),
    @NamedQuery(name = "OrdenTrabajo.findByAntiguedad", query = "SELECT o FROM OrdenTrabajo o WHERE o.antiguedad = :antiguedad"),
    @NamedQuery(name = "OrdenTrabajo.findByApoyo", query = "SELECT o FROM OrdenTrabajo o WHERE o.apoyo = :apoyo"),
    @NamedQuery(name = "OrdenTrabajo.findByCapacidadTrafo", query = "SELECT o FROM OrdenTrabajo o WHERE o.capacidadTrafo = :capacidadTrafo"),
    @NamedQuery(name = "OrdenTrabajo.findByVinculo", query = "SELECT o FROM OrdenTrabajo o WHERE o.vinculo = :vinculo"),
    @NamedQuery(name = "OrdenTrabajo.findByNormalizado", query = "SELECT o FROM OrdenTrabajo o WHERE o.normalizado = :normalizado"),
    @NamedQuery(name = "OrdenTrabajo.findByProgramaOt", query = "SELECT o FROM OrdenTrabajo o WHERE o.programaOt = :programaOt"),
    @NamedQuery(name = "OrdenTrabajo.findByFecCierreOt", query = "SELECT o FROM OrdenTrabajo o WHERE o.fecCierreOt = :fecCierreOt"),
    @NamedQuery(name = "OrdenTrabajo.findByIdComercial", query = "SELECT o FROM OrdenTrabajo o WHERE o.idComercial = :idComercial"),
    @NamedQuery(name = "OrdenTrabajo.findByTipoComponente", query = "SELECT o FROM OrdenTrabajo o WHERE o.tipoComponente = :tipoComponente")})
public class OrdenTrabajo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "PROGRAMA")
    private String programa;
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ORDEN")
    private BigDecimal idOrden;
    @Column(name = "CODIGO_MOVIL_ASIG")
    private String codigoMovilAsig;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_ASIGNACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;
    @Column(name = "FECHA_ACTUALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Column(name = "FECHA_EJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "NRO_SELLO")
    private String nroSello;
    @Column(name = "TIPO_SELLO")
    private String tipoSello;
    @Column(name = "COLOR_SELLO")
    private String colorSello;
    @Column(name = "EST_CONEXION")
    private String estConexion;
    @Column(name = "EST_CONTINUIDAD")
    private String estContinuidad;
    @Column(name = "ENVIADO_LAB")
    private String enviadoLab;
    @Column(name = "OBS_PRE_DIAGNOSTICO")
    private String obsPreDiagnostico;
    @Column(name = "IRREGULARIDAD")
    private String irregularidad;
    @Column(name = "OBS_IRREGULARIDAD")
    private String obsIrregularidad;
    @Column(name = "INST_ACOMETIDA")
    private String instAcometida;
    @Column(name = "INST_MEDIDOR")
    private String instMedidor;
    @Column(name = "INST_CAJA")
    private String instCaja;
    @Column(name = "REV_MEDIDOR_LAB")
    private String revMedidorLab;
    @Column(name = "INST_TABLERO")
    private String instTablero;
    @Column(name = "SUSPENSION")
    private String suspension;
    @Column(name = "INST_PUESTA_TIERRA")
    private String instPuestaTierra;
    @Column(name = "ADEC_CARGA")
    private String adecCarga;
    @Column(name = "TIPO_ACT")
    private String tipoAct;
    @Column(name = "TERMINAL")
    private String terminal;
    @Column(name = "LOTE")
    private String lote;
    @Column(name = "FECHA_DESCARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDescarga;
    @Column(name = "FECHA_CARGA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCarga;
    @Column(name = "PROCEDENCIA")
    private String procedencia;
    @Column(name = "ANTIGUEDAD")
    private BigInteger antiguedad;
    @Column(name = "APOYO")
    private String apoyo;
    @Column(name = "CAPACIDAD_TRAFO")
    private BigDecimal capacidadTrafo;
    @Column(name = "VINCULO")
    private String vinculo;
    @Column(name = "NORMALIZADO")
    private String normalizado;
    @Column(name = "PROGRAMA_OT")
    private String programaOt;
    @Column(name = "FEC_CIERRE_OT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecCierreOt;
    @Basic(optional = false)
    @Column(name = "ID_COMERCIAL")
    private String idComercial;
    @Basic(optional = false)
    @Column(name = "TIPO_COMPONENTE")
    private short tipoComponente;
    @JoinColumn(name = "TIPO_IRREGULARIDAD", referencedColumnName = "TIPO")
    @ManyToOne
    private Tbltipo tbltipo;
    @JoinColumn(name = "TIPO_ORDEN", referencedColumnName = "TIPO")
    @ManyToOne
    private Tbltipo tbltipo1;
    @JoinColumn(name = "TIPO_TRABAJO_REALIZADO", referencedColumnName = "TIPO")
    @ManyToOne
    private Tbltipo tbltipo2;
    @JoinColumn(name = "EST_ARRANQUE", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado1;
    @JoinColumn(name = "EST_SELLO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado2;
    @JoinColumn(name = "EST_VACIO", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado3;
    @JoinColumn(name = "EST_PRB_REG", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado4;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE")
    @ManyToOne(optional = false)
    private Componente componente;
    @JoinColumn(name = "ID_CAMPANIA", referencedColumnName = "ID_CAMPANIA")
    @ManyToOne(optional = false)
    private Campania campania;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(BigDecimal idOrden) {
        this.idOrden = idOrden;
    }

    public OrdenTrabajo(BigDecimal idOrden, String usuario, String programa, Date fecha, String idComercial, short tipoComponente) {
        this.idOrden = idOrden;
        this.usuario = usuario;
        this.programa = programa;
        this.fecha = fecha;
        this.idComercial = idComercial;
        this.tipoComponente = tipoComponente;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(BigDecimal idOrden) {
        this.idOrden = idOrden;
    }

    public String getCodigoMovilAsig() {
        return codigoMovilAsig;
    }

    public void setCodigoMovilAsig(String codigoMovilAsig) {
        this.codigoMovilAsig = codigoMovilAsig;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNroSello() {
        return nroSello;
    }

    public void setNroSello(String nroSello) {
        this.nroSello = nroSello;
    }

    public String getTipoSello() {
        return tipoSello;
    }

    public void setTipoSello(String tipoSello) {
        this.tipoSello = tipoSello;
    }

    public String getColorSello() {
        return colorSello;
    }

    public void setColorSello(String colorSello) {
        this.colorSello = colorSello;
    }

    public String getEstConexion() {
        return estConexion;
    }

    public void setEstConexion(String estConexion) {
        this.estConexion = estConexion;
    }

    public String getEstContinuidad() {
        return estContinuidad;
    }

    public void setEstContinuidad(String estContinuidad) {
        this.estContinuidad = estContinuidad;
    }

    public String getEnviadoLab() {
        return enviadoLab;
    }

    public void setEnviadoLab(String enviadoLab) {
        this.enviadoLab = enviadoLab;
    }

    public String getObsPreDiagnostico() {
        return obsPreDiagnostico;
    }

    public void setObsPreDiagnostico(String obsPreDiagnostico) {
        this.obsPreDiagnostico = obsPreDiagnostico;
    }

    public String getIrregularidad() {
        return irregularidad;
    }

    public void setIrregularidad(String irregularidad) {
        this.irregularidad = irregularidad;
    }

    public String getObsIrregularidad() {
        return obsIrregularidad;
    }

    public void setObsIrregularidad(String obsIrregularidad) {
        this.obsIrregularidad = obsIrregularidad;
    }

    public String getInstAcometida() {
        return instAcometida;
    }

    public void setInstAcometida(String instAcometida) {
        this.instAcometida = instAcometida;
    }

    public String getInstMedidor() {
        return instMedidor;
    }

    public void setInstMedidor(String instMedidor) {
        this.instMedidor = instMedidor;
    }

    public String getInstCaja() {
        return instCaja;
    }

    public void setInstCaja(String instCaja) {
        this.instCaja = instCaja;
    }

    public String getRevMedidorLab() {
        return revMedidorLab;
    }

    public void setRevMedidorLab(String revMedidorLab) {
        this.revMedidorLab = revMedidorLab;
    }

    public String getInstTablero() {
        return instTablero;
    }

    public void setInstTablero(String instTablero) {
        this.instTablero = instTablero;
    }

    public String getSuspension() {
        return suspension;
    }

    public void setSuspension(String suspension) {
        this.suspension = suspension;
    }

    public String getInstPuestaTierra() {
        return instPuestaTierra;
    }

    public void setInstPuestaTierra(String instPuestaTierra) {
        this.instPuestaTierra = instPuestaTierra;
    }

    public String getAdecCarga() {
        return adecCarga;
    }

    public void setAdecCarga(String adecCarga) {
        this.adecCarga = adecCarga;
    }

    public String getTipoAct() {
        return tipoAct;
    }

    public void setTipoAct(String tipoAct) {
        this.tipoAct = tipoAct;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaDescarga() {
        return fechaDescarga;
    }

    public void setFechaDescarga(Date fechaDescarga) {
        this.fechaDescarga = fechaDescarga;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public BigInteger getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(BigInteger antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getApoyo() {
        return apoyo;
    }

    public void setApoyo(String apoyo) {
        this.apoyo = apoyo;
    }

    public BigDecimal getCapacidadTrafo() {
        return capacidadTrafo;
    }

    public void setCapacidadTrafo(BigDecimal capacidadTrafo) {
        this.capacidadTrafo = capacidadTrafo;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public String getNormalizado() {
        return normalizado;
    }

    public void setNormalizado(String normalizado) {
        this.normalizado = normalizado;
    }

    public String getProgramaOt() {
        return programaOt;
    }

    public void setProgramaOt(String programaOt) {
        this.programaOt = programaOt;
    }

    public Date getFecCierreOt() {
        return fecCierreOt;
    }

    public void setFecCierreOt(Date fecCierreOt) {
        this.fecCierreOt = fecCierreOt;
    }

    public String getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(String idComercial) {
        this.idComercial = idComercial;
    }

    public short getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(short tipoComponente) {
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

    public Tbltipo getTbltipo2() {
        return tbltipo2;
    }

    public void setTbltipo2(Tbltipo tbltipo2) {
        this.tbltipo2 = tbltipo2;
    }

    public Estado getEstado1() {
        return estado1;
    }

    public void setEstado1(Estado estado1) {
        this.estado1 = estado1;
    }

    public Estado getEstado2() {
        return estado2;
    }

    public void setEstado2(Estado estado2) {
        this.estado2 = estado2;
    }

    public Estado getEstado3() {
        return estado3;
    }

    public void setEstado3(Estado estado3) {
        this.estado3 = estado3;
    }

    public Estado getEstado4() {
        return estado4;
    }

    public void setEstado4(Estado estado4) {
        this.estado4 = estado4;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public Campania getCampania() {
        return campania;
    }

    public void setCampania(Campania campania) {
        this.campania = campania;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrden != null ? idOrden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenTrabajo)) {
            return false;
        }
        OrdenTrabajo other = (OrdenTrabajo) object;
        if ((this.idOrden == null && other.idOrden != null) || (this.idOrden != null && !this.idOrden.equals(other.idOrden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.OrdenTrabajo[idOrden=" + idOrden + "]";
    }

}
