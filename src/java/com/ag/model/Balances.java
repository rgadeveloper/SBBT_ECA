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
@Table(name = "BALANCES")
@NamedQueries({
    @NamedQuery(name = "Balances.findAll", query = "SELECT b FROM Balances b"),
    @NamedQuery(name = "Balances.findByUsuario", query = "SELECT b FROM Balances b WHERE b.usuario = :usuario"),
    @NamedQuery(name = "Balances.findByPrograma", query = "SELECT b FROM Balances b WHERE b.programa = :programa"),
    @NamedQuery(name = "Balances.findByFechaModif", query = "SELECT b FROM Balances b WHERE b.fechaModif = :fechaModif"),
    @NamedQuery(name = "Balances.findByIdComponente", query = "SELECT b FROM Balances b WHERE b.balancesPK.idComponente = :idComponente"),
    @NamedQuery(name = "Balances.findByPeriodo", query = "SELECT b FROM Balances b WHERE b.balancesPK.periodo = :periodo"),
    @NamedQuery(name = "Balances.findByIdTipoComponente", query = "SELECT b FROM Balances b WHERE b.balancesPK.idTipoComponente = :idTipoComponente"),
    @NamedQuery(name = "Balances.findByFecha", query = "SELECT b FROM Balances b WHERE b.fecha = :fecha"),
    @NamedQuery(name = "Balances.findByBalEnergiaSum", query = "SELECT b FROM Balances b WHERE b.balEnergiaSum = :balEnergiaSum"),
    @NamedQuery(name = "Balances.findByPorcPerdidaMes", query = "SELECT b FROM Balances b WHERE b.porcPerdidaMes = :porcPerdidaMes"),
    @NamedQuery(name = "Balances.findByPorcPerdidaMovil", query = "SELECT b FROM Balances b WHERE b.porcPerdidaMovil = :porcPerdidaMovil"),
    @NamedQuery(name = "Balances.findByPorcAvance", query = "SELECT b FROM Balances b WHERE b.porcAvance = :porcAvance"),
    @NamedQuery(name = "Balances.findByCargabilidad", query = "SELECT b FROM Balances b WHERE b.cargabilidad = :cargabilidad"),
    @NamedQuery(name = "Balances.findByConsumoAdicional", query = "SELECT b FROM Balances b WHERE b.consumoAdicional = :consumoAdicional"),
    @NamedQuery(name = "Balances.findByConsumoSuministro", query = "SELECT b FROM Balances b WHERE b.consumoSuministro = :consumoSuministro"),
    @NamedQuery(name = "Balances.findByConsumoExtra", query = "SELECT b FROM Balances b WHERE b.consumoExtra = :consumoExtra"),
    @NamedQuery(name = "Balances.findByConsumoMacros", query = "SELECT b FROM Balances b WHERE b.consumoMacros = :consumoMacros"),
    @NamedQuery(name = "Balances.findByCantSuministrosTotal", query = "SELECT b FROM Balances b WHERE b.cantSuministrosTotal = :cantSuministrosTotal"),
    @NamedQuery(name = "Balances.findByCantSumFact", query = "SELECT b FROM Balances b WHERE b.cantSumFact = :cantSumFact"),
    @NamedQuery(name = "Balances.findByTotalMacros", query = "SELECT b FROM Balances b WHERE b.totalMacros = :totalMacros"),
    @NamedQuery(name = "Balances.findByTotalSinMacros", query = "SELECT b FROM Balances b WHERE b.totalSinMacros = :totalSinMacros"),
    @NamedQuery(name = "Balances.findByCierre", query = "SELECT b FROM Balances b WHERE b.cierre = :cierre"),
    @NamedQuery(name = "Balances.findByPorcAvaElaborado", query = "SELECT b FROM Balances b WHERE b.porcAvaElaborado = :porcAvaElaborado"),
    @NamedQuery(name = "Balances.findByPorcAvaCerrado", query = "SELECT b FROM Balances b WHERE b.porcAvaCerrado = :porcAvaCerrado"),
    @NamedQuery(name = "Balances.findByCantMacrosNoFunc", query = "SELECT b FROM Balances b WHERE b.cantMacrosNoFunc = :cantMacrosNoFunc"),
    @NamedQuery(name = "Balances.findByCantSuminSinBalance", query = "SELECT b FROM Balances b WHERE b.cantSuminSinBalance = :cantSuminSinBalance"),
    @NamedQuery(name = "Balances.findByTotalMacrosFunc", query = "SELECT b FROM Balances b WHERE b.totalMacrosFunc = :totalMacrosFunc"),
    @NamedQuery(name = "Balances.findByEnerEntManual", query = "SELECT b FROM Balances b WHERE b.enerEntManual = :enerEntManual"),
    @NamedQuery(name = "Balances.findByEnerSalManual", query = "SELECT b FROM Balances b WHERE b.enerSalManual = :enerSalManual"),
    @NamedQuery(name = "Balances.findByEnerPerdTotal", query = "SELECT b FROM Balances b WHERE b.enerPerdTotal = :enerPerdTotal")})
public class Balances implements Serializable {
    @Column(name = "CANT_MACROS_NO_FUNC")
    private Long cantMacrosNoFunc;
    @Column(name = "CANT_SUMIN_SIN_BALANCE")
    private Long cantSuminSinBalance;
    @Column(name = "ENER_ENT_MANUAL")
    private BigDecimal enerEntManual;
    @Column(name = "ENER_SAL_MANUAL")
    private BigDecimal enerSalManual;
    @Column(name = "ENER_PERD_TOTAL")
    private BigDecimal enerPerdTotal;
    @Column(name = "CANT_TRAFOS_TOTAL")
    private Long cantTrafosTotal;
    @Column(name = "CANT_TRAFOS_MACROS")
    private Long cantTrafosMacros;
    @Column(name = "CANT_SUM_MACROS")
    private Long cantSumMacros;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BalancesPK balancesPK;
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

    public Balances() {
    }

    public Balances(BalancesPK balancesPK) {
        this.balancesPK = balancesPK;
    }

    public Balances(BalancesPK balancesPK, String usuario, String programa, Date fechaModif, Date fecha) {
        this.balancesPK = balancesPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.fecha = fecha;
    }

    public Balances(BigInteger idComponente, int periodo, short idTipoComponente) {
        this.balancesPK = new BalancesPK(idComponente, periodo, idTipoComponente);
    }

    public BalancesPK getBalancesPK() {
        return balancesPK;
    }

    public void setBalancesPK(BalancesPK balancesPK) {
        this.balancesPK = balancesPK;
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
        hash += (balancesPK != null ? balancesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Balances)) {
            return false;
        }
        Balances other = (Balances) object;
        if ((this.balancesPK == null && other.balancesPK != null) || (this.balancesPK != null && !this.balancesPK.equals(other.balancesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Balances[balancesPK=" + balancesPK + "]";
    }

    public Long getCantMacrosNoFunc() {
        return cantMacrosNoFunc;
    }

    public void setCantMacrosNoFunc(Long cantMacrosNoFunc) {
        this.cantMacrosNoFunc = cantMacrosNoFunc;
    }

    public Long getCantSuminSinBalance() {
        return cantSuminSinBalance;
    }

    public void setCantSuminSinBalance(Long cantSuminSinBalance) {
        this.cantSuminSinBalance = cantSuminSinBalance;
    }

    public BigDecimal getEnerEntManual() {
        return enerEntManual;
    }

    public void setEnerEntManual(BigDecimal enerEntManual) {
        this.enerEntManual = enerEntManual;
    }

    public BigDecimal getEnerSalManual() {
        return enerSalManual;
    }

    public void setEnerSalManual(BigDecimal enerSalManual) {
        this.enerSalManual = enerSalManual;
    }

    public BigDecimal getEnerPerdTotal() {
        return enerPerdTotal;
    }

    public void setEnerPerdTotal(BigDecimal enerPerdTotal) {
        this.enerPerdTotal = enerPerdTotal;
    }

    public Long getCantTrafosTotal() {
        return cantTrafosTotal;
    }

    public void setCantTrafosTotal(Long cantTrafosTotal) {
        this.cantTrafosTotal = cantTrafosTotal;
    }

    public Long getCantTrafosMacros() {
        return cantTrafosMacros;
    }

    public void setCantTrafosMacros(Long cantTrafosMacros) {
        this.cantTrafosMacros = cantTrafosMacros;
    }

    public Long getCantSumMacros() {
        return cantSumMacros;
    }

    public void setCantSumMacros(Long cantSumMacros) {
        this.cantSumMacros = cantSumMacros;
    }

}
