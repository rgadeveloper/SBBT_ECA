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
@Table(name = "INT_SUM_TRAFO")
@NamedQueries({
    @NamedQuery(name = "IntSumTrafo.findAll", query = "SELECT i FROM IntSumTrafo i"),
    @NamedQuery(name = "IntSumTrafo.findByIdCliente", query = "SELECT i FROM IntSumTrafo i WHERE i.intSumTrafoPK.idCliente = :idCliente"),
    @NamedQuery(name = "IntSumTrafo.findByFechaModif", query = "SELECT i FROM IntSumTrafo i WHERE i.fechaModif = :fechaModif"),
    @NamedQuery(name = "IntSumTrafo.findByNombre", query = "SELECT i FROM IntSumTrafo i WHERE i.nombre = :nombre"),
    @NamedQuery(name = "IntSumTrafo.findByTipoComponente", query = "SELECT i FROM IntSumTrafo i WHERE i.intSumTrafoPK.tipoComponente = :tipoComponente"),
    @NamedQuery(name = "IntSumTrafo.findByTipoPropietario", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoPropietario = :tipoPropietario"),
    @NamedQuery(name = "IntSumTrafo.findByTipoProvisional", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoProvisional = :tipoProvisional"),
    @NamedQuery(name = "IntSumTrafo.findByEstadoServicio", query = "SELECT i FROM IntSumTrafo i WHERE i.estadoServicio = :estadoServicio"),
    @NamedQuery(name = "IntSumTrafo.findByDireccion", query = "SELECT i FROM IntSumTrafo i WHERE i.direccion = :direccion"),
    @NamedQuery(name = "IntSumTrafo.findByCoordX", query = "SELECT i FROM IntSumTrafo i WHERE i.coordX = :coordX"),
    @NamedQuery(name = "IntSumTrafo.findByCoordY", query = "SELECT i FROM IntSumTrafo i WHERE i.coordY = :coordY"),
    @NamedQuery(name = "IntSumTrafo.findByLocalizacion", query = "SELECT i FROM IntSumTrafo i WHERE i.localizacion = :localizacion"),
    @NamedQuery(name = "IntSumTrafo.findByCargaInstalada", query = "SELECT i FROM IntSumTrafo i WHERE i.cargaInstalada = :cargaInstalada"),
    @NamedQuery(name = "IntSumTrafo.findByPotencia", query = "SELECT i FROM IntSumTrafo i WHERE i.potencia = :potencia"),
    @NamedQuery(name = "IntSumTrafo.findByTipoConsumo", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoConsumo = :tipoConsumo"),
    @NamedQuery(name = "IntSumTrafo.findByTipoMedidor", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoMedidor = :tipoMedidor"),
    @NamedQuery(name = "IntSumTrafo.findByTipoConexion", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoConexion = :tipoConexion"),
    @NamedQuery(name = "IntSumTrafo.findByFechaInstalacion", query = "SELECT i FROM IntSumTrafo i WHERE i.fechaInstalacion = :fechaInstalacion"),
    @NamedQuery(name = "IntSumTrafo.findByFechaBaja", query = "SELECT i FROM IntSumTrafo i WHERE i.fechaBaja = :fechaBaja"),
    @NamedQuery(name = "IntSumTrafo.findByTipoRed", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoRed = :tipoRed"),
    @NamedQuery(name = "IntSumTrafo.findByTipoUso", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoUso = :tipoUso"),
    @NamedQuery(name = "IntSumTrafo.findByTipoTension", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoTension = :tipoTension"),
    @NamedQuery(name = "IntSumTrafo.findByProcesado", query = "SELECT i FROM IntSumTrafo i WHERE i.procesado = :procesado"),
    @NamedQuery(name = "IntSumTrafo.findByFechaProceso", query = "SELECT i FROM IntSumTrafo i WHERE i.fechaProceso = :fechaProceso"),
    @NamedQuery(name = "IntSumTrafo.findByDescProceso", query = "SELECT i FROM IntSumTrafo i WHERE i.descProceso = :descProceso"),
    @NamedQuery(name = "IntSumTrafo.findByTipoCategoria", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoCategoria = :tipoCategoria"),
    @NamedQuery(name = "IntSumTrafo.findByRuta", query = "SELECT i FROM IntSumTrafo i WHERE i.ruta = :ruta"),
    @NamedQuery(name = "IntSumTrafo.findByMedidor", query = "SELECT i FROM IntSumTrafo i WHERE i.medidor = :medidor"),
    @NamedQuery(name = "IntSumTrafo.findByFases", query = "SELECT i FROM IntSumTrafo i WHERE i.fases = :fases"),
    @NamedQuery(name = "IntSumTrafo.findByApoyo", query = "SELECT i FROM IntSumTrafo i WHERE i.apoyo = :apoyo"),
    @NamedQuery(name = "IntSumTrafo.findByEstadoIntervencion", query = "SELECT i FROM IntSumTrafo i WHERE i.estadoIntervencion = :estadoIntervencion"),
    @NamedQuery(name = "IntSumTrafo.findByCiclo", query = "SELECT i FROM IntSumTrafo i WHERE i.ciclo = :ciclo"),
    @NamedQuery(name = "IntSumTrafo.findByConsumoPromedio", query = "SELECT i FROM IntSumTrafo i WHERE i.consumoPromedio = :consumoPromedio"),
    @NamedQuery(name = "IntSumTrafo.findByRamal", query = "SELECT i FROM IntSumTrafo i WHERE i.ramal = :ramal"),
    @NamedQuery(name = "IntSumTrafo.findBySerie", query = "SELECT i FROM IntSumTrafo i WHERE i.serie = :serie"),
    @NamedQuery(name = "IntSumTrafo.findByEstrato", query = "SELECT i FROM IntSumTrafo i WHERE i.estrato = :estrato"),
    @NamedQuery(name = "IntSumTrafo.findByTipoSuministro", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoSuministro = :tipoSuministro"),
    @NamedQuery(name = "IntSumTrafo.findByTipoMarca", query = "SELECT i FROM IntSumTrafo i WHERE i.tipoMarca = :tipoMarca"),
    @NamedQuery(name = "IntSumTrafo.findByIdCircuito", query = "SELECT i FROM IntSumTrafo i WHERE i.idCircuito = :idCircuito"),
    @NamedQuery(name = "IntSumTrafo.findByIdBarrio", query = "SELECT i FROM IntSumTrafo i WHERE i.idBarrio = :idBarrio"),
    @NamedQuery(name = "IntSumTrafo.findByClaveGeo", query = "SELECT i FROM IntSumTrafo i WHERE i.claveGeo = :claveGeo"),
    @NamedQuery(name = "IntSumTrafo.findByClaveEle", query = "SELECT i FROM IntSumTrafo i WHERE i.claveEle = :claveEle"),
    @NamedQuery(name = "IntSumTrafo.findByPeriodo", query = "SELECT i FROM IntSumTrafo i WHERE i.intSumTrafoPK.periodo = :periodo")})
public class IntSumTrafo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IntSumTrafoPK intSumTrafoPK;
    @Column(name = "FECHA_MODIF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "TIPO_PROPIETARIO")
    private String tipoPropietario;
    @Column(name = "TIPO_PROVISIONAL")
    private String tipoProvisional;
    @Column(name = "ESTADO_SERVICIO")
    private String estadoServicio;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "COORD_X")
    private String coordX;
    @Column(name = "COORD_Y")
    private String coordY;
    @Column(name = "LOCALIZACION")
    private String localizacion;
    @Column(name = "CARGA_INSTALADA")
    private BigDecimal cargaInstalada;
    @Column(name = "POTENCIA")
    private BigDecimal potencia;
    @Column(name = "TIPO_CONSUMO")
    private String tipoConsumo;
    @Column(name = "TIPO_MEDIDOR")
    private String tipoMedidor;
    @Column(name = "TIPO_CONEXION")
    private String tipoConexion;
    @Column(name = "FECHA_INSTALACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInstalacion;
    @Column(name = "FECHA_BAJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    @Column(name = "TIPO_RED")
    private String tipoRed;
    @Column(name = "TIPO_USO")
    private String tipoUso;
    @Column(name = "TIPO_TENSION")
    private String tipoTension;
    @Column(name = "PROCESADO")
    private Short procesado;
    @Column(name = "FECHA_PROCESO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProceso;
    @Column(name = "DESC_PROCESO")
    private String descProceso;
    @Column(name = "TIPO_CATEGORIA")
    private String tipoCategoria;
    @Column(name = "RUTA")
    private String ruta;
    @Column(name = "MEDIDOR")
    private String medidor;
    @Column(name = "FASES")
    private Short fases;
    @Column(name = "APOYO")
    private String apoyo;
    @Column(name = "ESTADO_INTERVENCION")
    private String estadoIntervencion;
    @Column(name = "CICLO")
    private String ciclo;
    @Column(name = "CONSUMO_PROMEDIO")
    private Long consumoPromedio;
    @Column(name = "RAMAL")
    private String ramal;
    @Column(name = "SERIE")
    private String serie;
    @Column(name = "ESTRATO")
    private Short estrato;
    @Column(name = "TIPO_SUMINISTRO")
    private String tipoSuministro;
    @Column(name = "TIPO_MARCA")
    private String tipoMarca;
    @Column(name = "ID_CIRCUITO")
    private String idCircuito;
    @Column(name = "ID_BARRIO")
    private String idBarrio;
    @Column(name = "CLAVE_GEO")
    private String claveGeo;
    @Column(name = "CLAVE_ELE")
    private String claveEle;

    public IntSumTrafo() {
    }

    public IntSumTrafo(IntSumTrafoPK intSumTrafoPK) {
        this.intSumTrafoPK = intSumTrafoPK;
    }

    public IntSumTrafo(String idCliente, short tipoComponente, int periodo) {
        this.intSumTrafoPK = new IntSumTrafoPK(idCliente, tipoComponente, periodo);
    }

    public IntSumTrafoPK getIntSumTrafoPK() {
        return intSumTrafoPK;
    }

    public void setIntSumTrafoPK(IntSumTrafoPK intSumTrafoPK) {
        this.intSumTrafoPK = intSumTrafoPK;
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

    public String getTipoPropietario() {
        return tipoPropietario;
    }

    public void setTipoPropietario(String tipoPropietario) {
        this.tipoPropietario = tipoPropietario;
    }

    public String getTipoProvisional() {
        return tipoProvisional;
    }

    public void setTipoProvisional(String tipoProvisional) {
        this.tipoProvisional = tipoProvisional;
    }

    public String getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(String estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCoordX() {
        return coordX;
    }

    public void setCoordX(String coordX) {
        this.coordX = coordX;
    }

    public String getCoordY() {
        return coordY;
    }

    public void setCoordY(String coordY) {
        this.coordY = coordY;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
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

    public String getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(String tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public String getTipoMedidor() {
        return tipoMedidor;
    }

    public void setTipoMedidor(String tipoMedidor) {
        this.tipoMedidor = tipoMedidor;
    }

    public String getTipoConexion() {
        return tipoConexion;
    }

    public void setTipoConexion(String tipoConexion) {
        this.tipoConexion = tipoConexion;
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

    public String getTipoRed() {
        return tipoRed;
    }

    public void setTipoRed(String tipoRed) {
        this.tipoRed = tipoRed;
    }

    public String getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(String tipoUso) {
        this.tipoUso = tipoUso;
    }

    public String getTipoTension() {
        return tipoTension;
    }

    public void setTipoTension(String tipoTension) {
        this.tipoTension = tipoTension;
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

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public Short getFases() {
        return fases;
    }

    public void setFases(Short fases) {
        this.fases = fases;
    }

    public String getApoyo() {
        return apoyo;
    }

    public void setApoyo(String apoyo) {
        this.apoyo = apoyo;
    }

    public String getEstadoIntervencion() {
        return estadoIntervencion;
    }

    public void setEstadoIntervencion(String estadoIntervencion) {
        this.estadoIntervencion = estadoIntervencion;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Long getConsumoPromedio() {
        return consumoPromedio;
    }

    public void setConsumoPromedio(Long consumoPromedio) {
        this.consumoPromedio = consumoPromedio;
    }

    public String getRamal() {
        return ramal;
    }

    public void setRamal(String ramal) {
        this.ramal = ramal;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Short getEstrato() {
        return estrato;
    }

    public void setEstrato(Short estrato) {
        this.estrato = estrato;
    }

    public String getTipoSuministro() {
        return tipoSuministro;
    }

    public void setTipoSuministro(String tipoSuministro) {
        this.tipoSuministro = tipoSuministro;
    }

    public String getTipoMarca() {
        return tipoMarca;
    }

    public void setTipoMarca(String tipoMarca) {
        this.tipoMarca = tipoMarca;
    }

    public String getIdCircuito() {
        return idCircuito;
    }

    public void setIdCircuito(String idCircuito) {
        this.idCircuito = idCircuito;
    }

    public String getIdBarrio() {
        return idBarrio;
    }

    public void setIdBarrio(String idBarrio) {
        this.idBarrio = idBarrio;
    }

    public String getClaveGeo() {
        return claveGeo;
    }

    public void setClaveGeo(String claveGeo) {
        this.claveGeo = claveGeo;
    }

    public String getClaveEle() {
        return claveEle;
    }

    public void setClaveEle(String claveEle) {
        this.claveEle = claveEle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (intSumTrafoPK != null ? intSumTrafoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IntSumTrafo)) {
            return false;
        }
        IntSumTrafo other = (IntSumTrafo) object;
        if ((this.intSumTrafoPK == null && other.intSumTrafoPK != null) || (this.intSumTrafoPK != null && !this.intSumTrafoPK.equals(other.intSumTrafoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.IntSumTrafo[intSumTrafoPK=" + intSumTrafoPK + "]";
    }

}
