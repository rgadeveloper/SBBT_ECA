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
@Table(name = "CEO_DATOS_OS")
@NamedQueries({
    @NamedQuery(name = "CeoDatosOs.findAll", query = "SELECT c FROM CeoDatosOs c"),
    @NamedQuery(name = "CeoDatosOs.findByOrden", query = "SELECT c FROM CeoDatosOs c WHERE c.orden = :orden"),
    @NamedQuery(name = "CeoDatosOs.findByProducto", query = "SELECT c FROM CeoDatosOs c WHERE c.producto = :producto"),
    @NamedQuery(name = "CeoDatosOs.findByEstadoOrden", query = "SELECT c FROM CeoDatosOs c WHERE c.estadoOrden = :estadoOrden"),
    @NamedQuery(name = "CeoDatosOs.findByFechaAsignacion", query = "SELECT c FROM CeoDatosOs c WHERE c.fechaAsignacion = :fechaAsignacion"),
    @NamedQuery(name = "CeoDatosOs.findByFechaEjecucion", query = "SELECT c FROM CeoDatosOs c WHERE c.fechaEjecucion = :fechaEjecucion"),
    @NamedQuery(name = "CeoDatosOs.findByFechaLegalizacion", query = "SELECT c FROM CeoDatosOs c WHERE c.fechaLegalizacion = :fechaLegalizacion"),
    @NamedQuery(name = "CeoDatosOs.findByTpoAct", query = "SELECT c FROM CeoDatosOs c WHERE c.tpoAct = :tpoAct"),
    @NamedQuery(name = "CeoDatosOs.findByTerminal", query = "SELECT c FROM CeoDatosOs c WHERE c.terminal = :terminal"),
    @NamedQuery(name = "CeoDatosOs.findByLote", query = "SELECT c FROM CeoDatosOs c WHERE c.lote = :lote"),
    @NamedQuery(name = "CeoDatosOs.findByFechaDesc", query = "SELECT c FROM CeoDatosOs c WHERE c.fechaDesc = :fechaDesc"),
    @NamedQuery(name = "CeoDatosOs.findByFecCarg", query = "SELECT c FROM CeoDatosOs c WHERE c.fecCarg = :fecCarg"),
    @NamedQuery(name = "CeoDatosOs.findByNroCamp", query = "SELECT c FROM CeoDatosOs c WHERE c.nroCamp = :nroCamp"),
    @NamedQuery(name = "CeoDatosOs.findByDescCamp", query = "SELECT c FROM CeoDatosOs c WHERE c.descCamp = :descCamp"),
    @NamedQuery(name = "CeoDatosOs.findByProce", query = "SELECT c FROM CeoDatosOs c WHERE c.proce = :proce"),
    @NamedQuery(name = "CeoDatosOs.findByPrograma", query = "SELECT c FROM CeoDatosOs c WHERE c.programa = :programa"),
    @NamedQuery(name = "CeoDatosOs.findByAntiguedad", query = "SELECT c FROM CeoDatosOs c WHERE c.antiguedad = :antiguedad"),
    @NamedQuery(name = "CeoDatosOs.findByUnidoper", query = "SELECT c FROM CeoDatosOs c WHERE c.unidoper = :unidoper"),
    @NamedQuery(name = "CeoDatosOs.findByIrreg", query = "SELECT c FROM CeoDatosOs c WHERE c.irreg = :irreg"),
    @NamedQuery(name = "CeoDatosOs.findByTpoIrreg", query = "SELECT c FROM CeoDatosOs c WHERE c.tpoIrreg = :tpoIrreg"),
    @NamedQuery(name = "CeoDatosOs.findByObsIrreg", query = "SELECT c FROM CeoDatosOs c WHERE c.obsIrreg = :obsIrreg"),
    @NamedQuery(name = "CeoDatosOs.findByVacio", query = "SELECT c FROM CeoDatosOs c WHERE c.vacio = :vacio"),
    @NamedQuery(name = "CeoDatosOs.findByArranque", query = "SELECT c FROM CeoDatosOs c WHERE c.arranque = :arranque"),
    @NamedQuery(name = "CeoDatosOs.findByConexion", query = "SELECT c FROM CeoDatosOs c WHERE c.conexion = :conexion"),
    @NamedQuery(name = "CeoDatosOs.findByContin", query = "SELECT c FROM CeoDatosOs c WHERE c.contin = :contin"),
    @NamedQuery(name = "CeoDatosOs.findByEnvioLab", query = "SELECT c FROM CeoDatosOs c WHERE c.envioLab = :envioLab"),
    @NamedQuery(name = "CeoDatosOs.findByObsPdiag", query = "SELECT c FROM CeoDatosOs c WHERE c.obsPdiag = :obsPdiag"),
    @NamedQuery(name = "CeoDatosOs.findByApoyo", query = "SELECT c FROM CeoDatosOs c WHERE c.apoyo = :apoyo"),
    @NamedQuery(name = "CeoDatosOs.findByCapKva", query = "SELECT c FROM CeoDatosOs c WHERE c.capKva = :capKva"),
    @NamedQuery(name = "CeoDatosOs.findByTransf", query = "SELECT c FROM CeoDatosOs c WHERE c.transf = :transf"),
    @NamedQuery(name = "CeoDatosOs.findByNroSello", query = "SELECT c FROM CeoDatosOs c WHERE c.nroSello = :nroSello"),
    @NamedQuery(name = "CeoDatosOs.findByTipSello", query = "SELECT c FROM CeoDatosOs c WHERE c.tipSello = :tipSello"),
    @NamedQuery(name = "CeoDatosOs.findByColSell", query = "SELECT c FROM CeoDatosOs c WHERE c.colSell = :colSell"),
    @NamedQuery(name = "CeoDatosOs.findByEstSello", query = "SELECT c FROM CeoDatosOs c WHERE c.estSello = :estSello"),
    @NamedQuery(name = "CeoDatosOs.findByPrbaReg", query = "SELECT c FROM CeoDatosOs c WHERE c.prbaReg = :prbaReg"),
    @NamedQuery(name = "CeoDatosOs.findByIcAcomt", query = "SELECT c FROM CeoDatosOs c WHERE c.icAcomt = :icAcomt"),
    @NamedQuery(name = "CeoDatosOs.findByIcMddor", query = "SELECT c FROM CeoDatosOs c WHERE c.icMddor = :icMddor"),
    @NamedQuery(name = "CeoDatosOs.findByIcCaja", query = "SELECT c FROM CeoDatosOs c WHERE c.icCaja = :icCaja"),
    @NamedQuery(name = "CeoDatosOs.findByRmLabor", query = "SELECT c FROM CeoDatosOs c WHERE c.rmLabor = :rmLabor"),
    @NamedQuery(name = "CeoDatosOs.findByInsttabl", query = "SELECT c FROM CeoDatosOs c WHERE c.insttabl = :insttabl"),
    @NamedQuery(name = "CeoDatosOs.findBySuspension", query = "SELECT c FROM CeoDatosOs c WHERE c.suspension = :suspension"),
    @NamedQuery(name = "CeoDatosOs.findByIcPt", query = "SELECT c FROM CeoDatosOs c WHERE c.icPt = :icPt"),
    @NamedQuery(name = "CeoDatosOs.findByAdecCarga", query = "SELECT c FROM CeoDatosOs c WHERE c.adecCarga = :adecCarga"),
    @NamedQuery(name = "CeoDatosOs.findByVinculo", query = "SELECT c FROM CeoDatosOs c WHERE c.vinculo = :vinculo"),
    @NamedQuery(name = "CeoDatosOs.findByObservacionOrden", query = "SELECT c FROM CeoDatosOs c WHERE c.observacionOrden = :observacionOrden"),
    @NamedQuery(name = "CeoDatosOs.findByProductoMacro", query = "SELECT c FROM CeoDatosOs c WHERE c.productoMacro = :productoMacro"),
    @NamedQuery(name = "CeoDatosOs.findByTipoOrden", query = "SELECT c FROM CeoDatosOs c WHERE c.tipoOrden = :tipoOrden"),
    @NamedQuery(name = "CeoDatosOs.findByFechCierreOt", query = "SELECT c FROM CeoDatosOs c WHERE c.fechCierreOt = :fechCierreOt"),
    @NamedQuery(name = "CeoDatosOs.findByFechActOt", query = "SELECT c FROM CeoDatosOs c WHERE c.fechActOt = :fechActOt"),
    @NamedQuery(name = "CeoDatosOs.findByComentario", query = "SELECT c FROM CeoDatosOs c WHERE c.comentario = :comentario"),
    @NamedQuery(name = "CeoDatosOs.findByNormal", query = "SELECT c FROM CeoDatosOs c WHERE c.normal = :normal"),
    @NamedQuery(name = "CeoDatosOs.findByProcesado", query = "SELECT c FROM CeoDatosOs c WHERE c.procesado = :procesado"),
    @NamedQuery(name = "CeoDatosOs.findByFechaProceso", query = "SELECT c FROM CeoDatosOs c WHERE c.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "CeoDatosOs.findByDescProceso", query = "SELECT c FROM CeoDatosOs c WHERE c.descProceso = :descProceso"),
    @NamedQuery(name = "CeoDatosOs.findByMacro", query = "SELECT c FROM CeoDatosOs c WHERE c.macro = :macro")})
public class CeoDatosOs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private BigDecimal orden;
    @Column(name = "PRODUCTO")
    private String producto;
    @Column(name = "ESTADO_ORDEN")
    private String estadoOrden;
    @Column(name = "FECHA_ASIGNACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAsignacion;
    @Column(name = "FECHA_EJECUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEjecucion;
    @Column(name = "FECHA_LEGALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLegalizacion;
    @Column(name = "TPO_ACT")
    private String tpoAct;
    @Column(name = "TERMINAL")
    private String terminal;
    @Column(name = "LOTE")
    private String lote;
    @Column(name = "FECHA_DESC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesc;
    @Column(name = "FEC_CARG")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecCarg;
    @Column(name = "NRO_CAMP")
    private BigInteger nroCamp;
    @Column(name = "DESC_CAMP")
    private String descCamp;
    @Column(name = "PROCE")
    private String proce;
    @Column(name = "PROGRAMA")
    private String programa;
    @Column(name = "ANTIGUEDAD")
    private BigInteger antiguedad;
    @Column(name = "UNIDOPER")
    private String unidoper;
    @Column(name = "IRREG")
    private String irreg;
    @Column(name = "TPO_IRREG")
    private String tpoIrreg;
    @Column(name = "OBS_IRREG")
    private String obsIrreg;
    @Column(name = "VACIO")
    private String vacio;
    @Column(name = "ARRANQUE")
    private String arranque;
    @Column(name = "CONEXION")
    private String conexion;
    @Column(name = "CONTIN")
    private String contin;
    @Column(name = "ENVIO_LAB")
    private String envioLab;
    @Column(name = "OBS_PDIAG")
    private String obsPdiag;
    @Column(name = "APOYO")
    private String apoyo;
    @Column(name = "CAP_KVA")
    private String capKva;
    @Column(name = "TRANSF")
    private String transf;
    @Column(name = "NRO_SELLO")
    private String nroSello;
    @Column(name = "TIP_SELLO")
    private String tipSello;
    @Column(name = "COL_SELL")
    private String colSell;
    @Column(name = "EST_SELLO")
    private String estSello;
    @Column(name = "PRBA_REG")
    private String prbaReg;
    @Column(name = "IC_ACOMT")
    private String icAcomt;
    @Column(name = "IC_MDDOR")
    private String icMddor;
    @Column(name = "IC_CAJA")
    private String icCaja;
    @Column(name = "RM_LABOR")
    private String rmLabor;
    @Column(name = "INSTTABL")
    private String insttabl;
    @Column(name = "SUSPENSION")
    private String suspension;
    @Column(name = "IC_PT")
    private String icPt;
    @Column(name = "ADEC_CARGA")
    private String adecCarga;
    @Column(name = "VINCULO")
    private String vinculo;
    @Column(name = "OBSERVACION_ORDEN")
    private String observacionOrden;
    @Column(name = "PRODUCTO_MACRO")
    private String productoMacro;
    @Column(name = "TIPO_ORDEN")
    private String tipoOrden;
    @Column(name = "FECH_CIERRE_OT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechCierreOt;
    @Column(name = "FECH_ACT_OT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechActOt;
    @Column(name = "COMENTARIO")
    private String comentario;
    @Column(name = "NORMAL")
    private String normal;
    @Column(name = "PROCESADO")
    private Short procesado;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Column(name = "DESC_PROCESO")
    private String descProceso;
    @Column(name = "MACRO")
    private String macro;

    public CeoDatosOs() {
    }

    public CeoDatosOs(BigDecimal orden) {
        this.orden = orden;
    }

    public BigDecimal getOrden() {
        return orden;
    }

    public void setOrden(BigDecimal orden) {
        this.orden = orden;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(Date fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Date getFechaLegalizacion() {
        return fechaLegalizacion;
    }

    public void setFechaLegalizacion(Date fechaLegalizacion) {
        this.fechaLegalizacion = fechaLegalizacion;
    }

    public String getTpoAct() {
        return tpoAct;
    }

    public void setTpoAct(String tpoAct) {
        this.tpoAct = tpoAct;
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

    public Date getFechaDesc() {
        return fechaDesc;
    }

    public void setFechaDesc(Date fechaDesc) {
        this.fechaDesc = fechaDesc;
    }

    public Date getFecCarg() {
        return fecCarg;
    }

    public void setFecCarg(Date fecCarg) {
        this.fecCarg = fecCarg;
    }

    public BigInteger getNroCamp() {
        return nroCamp;
    }

    public void setNroCamp(BigInteger nroCamp) {
        this.nroCamp = nroCamp;
    }

    public String getDescCamp() {
        return descCamp;
    }

    public void setDescCamp(String descCamp) {
        this.descCamp = descCamp;
    }

    public String getProce() {
        return proce;
    }

    public void setProce(String proce) {
        this.proce = proce;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public BigInteger getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(BigInteger antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getUnidoper() {
        return unidoper;
    }

    public void setUnidoper(String unidoper) {
        this.unidoper = unidoper;
    }

    public String getIrreg() {
        return irreg;
    }

    public void setIrreg(String irreg) {
        this.irreg = irreg;
    }

    public String getTpoIrreg() {
        return tpoIrreg;
    }

    public void setTpoIrreg(String tpoIrreg) {
        this.tpoIrreg = tpoIrreg;
    }

    public String getObsIrreg() {
        return obsIrreg;
    }

    public void setObsIrreg(String obsIrreg) {
        this.obsIrreg = obsIrreg;
    }

    public String getVacio() {
        return vacio;
    }

    public void setVacio(String vacio) {
        this.vacio = vacio;
    }

    public String getArranque() {
        return arranque;
    }

    public void setArranque(String arranque) {
        this.arranque = arranque;
    }

    public String getConexion() {
        return conexion;
    }

    public void setConexion(String conexion) {
        this.conexion = conexion;
    }

    public String getContin() {
        return contin;
    }

    public void setContin(String contin) {
        this.contin = contin;
    }

    public String getEnvioLab() {
        return envioLab;
    }

    public void setEnvioLab(String envioLab) {
        this.envioLab = envioLab;
    }

    public String getObsPdiag() {
        return obsPdiag;
    }

    public void setObsPdiag(String obsPdiag) {
        this.obsPdiag = obsPdiag;
    }

    public String getApoyo() {
        return apoyo;
    }

    public void setApoyo(String apoyo) {
        this.apoyo = apoyo;
    }

    public String getCapKva() {
        return capKva;
    }

    public void setCapKva(String capKva) {
        this.capKva = capKva;
    }

    public String getTransf() {
        return transf;
    }

    public void setTransf(String transf) {
        this.transf = transf;
    }

    public String getNroSello() {
        return nroSello;
    }

    public void setNroSello(String nroSello) {
        this.nroSello = nroSello;
    }

    public String getTipSello() {
        return tipSello;
    }

    public void setTipSello(String tipSello) {
        this.tipSello = tipSello;
    }

    public String getColSell() {
        return colSell;
    }

    public void setColSell(String colSell) {
        this.colSell = colSell;
    }

    public String getEstSello() {
        return estSello;
    }

    public void setEstSello(String estSello) {
        this.estSello = estSello;
    }

    public String getPrbaReg() {
        return prbaReg;
    }

    public void setPrbaReg(String prbaReg) {
        this.prbaReg = prbaReg;
    }

    public String getIcAcomt() {
        return icAcomt;
    }

    public void setIcAcomt(String icAcomt) {
        this.icAcomt = icAcomt;
    }

    public String getIcMddor() {
        return icMddor;
    }

    public void setIcMddor(String icMddor) {
        this.icMddor = icMddor;
    }

    public String getIcCaja() {
        return icCaja;
    }

    public void setIcCaja(String icCaja) {
        this.icCaja = icCaja;
    }

    public String getRmLabor() {
        return rmLabor;
    }

    public void setRmLabor(String rmLabor) {
        this.rmLabor = rmLabor;
    }

    public String getInsttabl() {
        return insttabl;
    }

    public void setInsttabl(String insttabl) {
        this.insttabl = insttabl;
    }

    public String getSuspension() {
        return suspension;
    }

    public void setSuspension(String suspension) {
        this.suspension = suspension;
    }

    public String getIcPt() {
        return icPt;
    }

    public void setIcPt(String icPt) {
        this.icPt = icPt;
    }

    public String getAdecCarga() {
        return adecCarga;
    }

    public void setAdecCarga(String adecCarga) {
        this.adecCarga = adecCarga;
    }

    public String getVinculo() {
        return vinculo;
    }

    public void setVinculo(String vinculo) {
        this.vinculo = vinculo;
    }

    public String getObservacionOrden() {
        return observacionOrden;
    }

    public void setObservacionOrden(String observacionOrden) {
        this.observacionOrden = observacionOrden;
    }

    public String getProductoMacro() {
        return productoMacro;
    }

    public void setProductoMacro(String productoMacro) {
        this.productoMacro = productoMacro;
    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public Date getFechCierreOt() {
        return fechCierreOt;
    }

    public void setFechCierreOt(Date fechCierreOt) {
        this.fechCierreOt = fechCierreOt;
    }

    public Date getFechActOt() {
        return fechActOt;
    }

    public void setFechActOt(Date fechActOt) {
        this.fechActOt = fechActOt;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
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

    public String getMacro() {
        return macro;
    }

    public void setMacro(String macro) {
        this.macro = macro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orden != null ? orden.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CeoDatosOs)) {
            return false;
        }
        CeoDatosOs other = (CeoDatosOs) object;
        if ((this.orden == null && other.orden != null) || (this.orden != null && !this.orden.equals(other.orden))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.CeoDatosOs[orden=" + orden + "]";
    }

}
