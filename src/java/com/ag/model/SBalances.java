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
@Table(name = "S_BALANCES")
@NamedQueries({
    @NamedQuery(name = "SBalances.findAll", query = "SELECT s FROM SBalances s"),
    @NamedQuery(name = "SBalances.findByUsuario", query = "SELECT s FROM SBalances s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SBalances.findByPrograma", query = "SELECT s FROM SBalances s WHERE s.programa = :programa"),
    @NamedQuery(name = "SBalances.findByFechaModif", query = "SELECT s FROM SBalances s WHERE s.fechaModif = :fechaModif"),
    @NamedQuery(name = "SBalances.findByIdComponente", query = "SELECT s FROM SBalances s WHERE s.sBalancesPK.idComponente = :idComponente"),
    @NamedQuery(name = "SBalances.findByPeriodo", query = "SELECT s FROM SBalances s WHERE s.sBalancesPK.periodo = :periodo"),
    @NamedQuery(name = "SBalances.findByIdTipoComponente", query = "SELECT s FROM SBalances s WHERE s.sBalancesPK.idTipoComponente = :idTipoComponente"),
    @NamedQuery(name = "SBalances.findByFecha", query = "SELECT s FROM SBalances s WHERE s.fecha = :fecha"),
    @NamedQuery(name = "SBalances.findByBalEnergiaSum", query = "SELECT s FROM SBalances s WHERE s.balEnergiaSum = :balEnergiaSum"),
    @NamedQuery(name = "SBalances.findByPorcPerdidaMes", query = "SELECT s FROM SBalances s WHERE s.porcPerdidaMes = :porcPerdidaMes"),
    @NamedQuery(name = "SBalances.findByPorcPerdidaMovil", query = "SELECT s FROM SBalances s WHERE s.porcPerdidaMovil = :porcPerdidaMovil"),
    @NamedQuery(name = "SBalances.findByPorcAvance", query = "SELECT s FROM SBalances s WHERE s.porcAvance = :porcAvance"),
    @NamedQuery(name = "SBalances.findByCargabilidad", query = "SELECT s FROM SBalances s WHERE s.cargabilidad = :cargabilidad"),
    @NamedQuery(name = "SBalances.findByConsumoAdicional", query = "SELECT s FROM SBalances s WHERE s.consumoAdicional = :consumoAdicional"),
    @NamedQuery(name = "SBalances.findByConsumoSuministro", query = "SELECT s FROM SBalances s WHERE s.consumoSuministro = :consumoSuministro"),
    @NamedQuery(name = "SBalances.findByConsumoExtra", query = "SELECT s FROM SBalances s WHERE s.consumoExtra = :consumoExtra"),
    @NamedQuery(name = "SBalances.findByConsumoMacros", query = "SELECT s FROM SBalances s WHERE s.consumoMacros = :consumoMacros"),
    @NamedQuery(name = "SBalances.findByCantSuministrosTotal", query = "SELECT s FROM SBalances s WHERE s.cantSuministrosTotal = :cantSuministrosTotal"),
    @NamedQuery(name = "SBalances.findByCantSumFact", query = "SELECT s FROM SBalances s WHERE s.cantSumFact = :cantSumFact"),
    @NamedQuery(name = "SBalances.findByTotalMacros", query = "SELECT s FROM SBalances s WHERE s.totalMacros = :totalMacros"),
    @NamedQuery(name = "SBalances.findByTotalSinMacros", query = "SELECT s FROM SBalances s WHERE s.totalSinMacros = :totalSinMacros"),
    @NamedQuery(name = "SBalances.findByCierre", query = "SELECT s FROM SBalances s WHERE s.cierre = :cierre"),
    @NamedQuery(name = "SBalances.findByPorcAvaElaborado", query = "SELECT s FROM SBalances s WHERE s.porcAvaElaborado = :porcAvaElaborado"),
    @NamedQuery(name = "SBalances.findByPorcAvaCerrado", query = "SELECT s FROM SBalances s WHERE s.porcAvaCerrado = :porcAvaCerrado"),
    @NamedQuery(name = "SBalances.findByCantMacrosNoFunc", query = "SELECT s FROM SBalances s WHERE s.cantMacrosNoFunc = :cantMacrosNoFunc"),
    @NamedQuery(name = "SBalances.findByCantSuminSinBalance", query = "SELECT s FROM SBalances s WHERE s.cantSuminSinBalance = :cantSuminSinBalance"),
    @NamedQuery(name = "SBalances.findByTotalMacrosFunc", query = "SELECT s FROM SBalances s WHERE s.totalMacrosFunc = :totalMacrosFunc"),
    @NamedQuery(name = "SBalances.findByIdSimulacion", query = "SELECT s FROM SBalances s WHERE s.sBalancesPK.idSimulacion = :idSimulacion")})
public class SBalances implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SBalancesPK sBalancesPK;
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
    @Basic(optional = false)
    @Column(name = "FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "BAL_ENERGIA_SUM")
    private BigDecimal balEnergiaSum;
    @Column(name = "PORC_PERDIDA_MES")
    private BigDecimal porcPerdidaMes;
    @Column(name = "PORC_PERDIDA_MOVIL")
    private BigDecimal porcPerdidaMovil;
    @Column(name = "PORC_AVANCE")
    private BigDecimal porcAvance;
    @Column(name = "CARGABILIDAD")
    private BigDecimal cargabilidad;
    @Column(name = "CONSUMO_ADICIONAL")
    private BigDecimal consumoAdicional;
    @Column(name = "CONSUMO_SUMINISTRO")
    private BigDecimal consumoSuministro;
    @Column(name = "CONSUMO_EXTRA")
    private BigDecimal consumoExtra;
    @Column(name = "CONSUMO_MACROS")
    private BigDecimal consumoMacros;
    @Column(name = "CANT_SUMINISTROS_TOTAL")
    private Long cantSuministrosTotal;
    @Column(name = "CANT_SUM_FACT")
    private Long cantSumFact;
    @Column(name = "TOTAL_MACROS")
    private Long totalMacros;
    @Column(name = "TOTAL_SIN_MACROS")
    private Long totalSinMacros;
    @Column(name = "CIERRE")
    private String cierre;
    @Column(name = "PORC_AVA_ELABORADO")
    private BigDecimal porcAvaElaborado;
    @Column(name = "PORC_AVA_CERRADO")
    private BigDecimal porcAvaCerrado;
    @Column(name = "CANT_MACROS_NO_FUNC")
    private Integer cantMacrosNoFunc;
    @Column(name = "CANT_SUMIN_SIN_BALANCE")
    private Integer cantSuminSinBalance;
    @Column(name = "TOTAL_MACROS_FUNC")
    private BigDecimal totalMacrosFunc;
    @JoinColumn(name = "MOT_NO_BALANCE", referencedColumnName = "TIPO")
    @ManyToOne
    private Tbltipo tbltipo;
    @JoinColumn(name = "RANGO_BALANCE", referencedColumnName = "ID_RANGO")
    @ManyToOne
    private RangoClasificacion rangoClasificacion;
    @JoinColumn(name = "ESTADO_BALANCE", referencedColumnName = "ID_ESTADO")
    @ManyToOne
    private Estado estado;

    public SBalances() {
    }

    public SBalances(SBalancesPK sBalancesPK) {
        this.sBalancesPK = sBalancesPK;
    }

    public SBalances(SBalancesPK sBalancesPK, String usuario, String programa, Date fechaModif, Date fecha) {
        this.sBalancesPK = sBalancesPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.fecha = fecha;
    }

    public SBalances(BigInteger idComponente, int periodo, short idTipoComponente, long idSimulacion) {
        this.sBalancesPK = new SBalancesPK(idComponente, periodo, idTipoComponente, idSimulacion);
    }

    public SBalancesPK getSBalancesPK() {
        return sBalancesPK;
    }

    public void setSBalancesPK(SBalancesPK sBalancesPK) {
        this.sBalancesPK = sBalancesPK;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getBalEnergiaSum() {
        return balEnergiaSum;
    }

    public void setBalEnergiaSum(BigDecimal balEnergiaSum) {
        this.balEnergiaSum = balEnergiaSum;
    }

    public BigDecimal getPorcPerdidaMes() {
        return porcPerdidaMes;
    }

    public void setPorcPerdidaMes(BigDecimal porcPerdidaMes) {
        this.porcPerdidaMes = porcPerdidaMes;
    }

    public BigDecimal getPorcPerdidaMovil() {
        return porcPerdidaMovil;
    }

    public void setPorcPerdidaMovil(BigDecimal porcPerdidaMovil) {
        this.porcPerdidaMovil = porcPerdidaMovil;
    }

    public BigDecimal getPorcAvance() {
        return porcAvance;
    }

    public void setPorcAvance(BigDecimal porcAvance) {
        this.porcAvance = porcAvance;
    }

    public BigDecimal getCargabilidad() {
        return cargabilidad;
    }

    public void setCargabilidad(BigDecimal cargabilidad) {
        this.cargabilidad = cargabilidad;
    }

    public BigDecimal getConsumoAdicional() {
        return consumoAdicional;
    }

    public void setConsumoAdicional(BigDecimal consumoAdicional) {
        this.consumoAdicional = consumoAdicional;
    }

    public BigDecimal getConsumoSuministro() {
        return consumoSuministro;
    }

    public void setConsumoSuministro(BigDecimal consumoSuministro) {
        this.consumoSuministro = consumoSuministro;
    }

    public BigDecimal getConsumoExtra() {
        return consumoExtra;
    }

    public void setConsumoExtra(BigDecimal consumoExtra) {
        this.consumoExtra = consumoExtra;
    }

    public BigDecimal getConsumoMacros() {
        return consumoMacros;
    }

    public void setConsumoMacros(BigDecimal consumoMacros) {
        this.consumoMacros = consumoMacros;
    }

    public Long getCantSuministrosTotal() {
        return cantSuministrosTotal;
    }

    public void setCantSuministrosTotal(Long cantSuministrosTotal) {
        this.cantSuministrosTotal = cantSuministrosTotal;
    }

    public Long getCantSumFact() {
        return cantSumFact;
    }

    public void setCantSumFact(Long cantSumFact) {
        this.cantSumFact = cantSumFact;
    }

    public Long getTotalMacros() {
        return totalMacros;
    }

    public void setTotalMacros(Long totalMacros) {
        this.totalMacros = totalMacros;
    }

    public Long getTotalSinMacros() {
        return totalSinMacros;
    }

    public void setTotalSinMacros(Long totalSinMacros) {
        this.totalSinMacros = totalSinMacros;
    }

    public String getCierre() {
        return cierre;
    }

    public void setCierre(String cierre) {
        this.cierre = cierre;
    }

    public BigDecimal getPorcAvaElaborado() {
        return porcAvaElaborado;
    }

    public void setPorcAvaElaborado(BigDecimal porcAvaElaborado) {
        this.porcAvaElaborado = porcAvaElaborado;
    }

    public BigDecimal getPorcAvaCerrado() {
        return porcAvaCerrado;
    }

    public void setPorcAvaCerrado(BigDecimal porcAvaCerrado) {
        this.porcAvaCerrado = porcAvaCerrado;
    }

    public Integer getCantMacrosNoFunc() {
        return cantMacrosNoFunc;
    }

    public void setCantMacrosNoFunc(Integer cantMacrosNoFunc) {
        this.cantMacrosNoFunc = cantMacrosNoFunc;
    }

    public Integer getCantSuminSinBalance() {
        return cantSuminSinBalance;
    }

    public void setCantSuminSinBalance(Integer cantSuminSinBalance) {
        this.cantSuminSinBalance = cantSuminSinBalance;
    }

    public BigDecimal getTotalMacrosFunc() {
        return totalMacrosFunc;
    }

    public void setTotalMacrosFunc(BigDecimal totalMacrosFunc) {
        this.totalMacrosFunc = totalMacrosFunc;
    }

    public Tbltipo getTbltipo() {
        return tbltipo;
    }

    public void setTbltipo(Tbltipo tbltipo) {
        this.tbltipo = tbltipo;
    }

    public RangoClasificacion getRangoClasificacion() {
        return rangoClasificacion;
    }

    public void setRangoClasificacion(RangoClasificacion rangoClasificacion) {
        this.rangoClasificacion = rangoClasificacion;
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
        hash += (sBalancesPK != null ? sBalancesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SBalances)) {
            return false;
        }
        SBalances other = (SBalances) object;
        if ((this.sBalancesPK == null && other.sBalancesPK != null) || (this.sBalancesPK != null && !this.sBalancesPK.equals(other.sBalancesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.SBalances[sBalancesPK=" + sBalancesPK + "]";
    }

}
