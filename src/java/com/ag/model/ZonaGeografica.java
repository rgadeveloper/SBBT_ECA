package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author larry.obispo
 */
@Entity
@Table(name = "ZONA_GEOGRAFICA")
@NamedQueries({
    @NamedQuery(name = "ZonaGeografica.findAll", query = "SELECT z FROM ZonaGeografica z"),
    @NamedQuery(name = "ZonaGeografica.findByUsuario", query = "SELECT z FROM ZonaGeografica z WHERE z.usuario = :usuario"),
    @NamedQuery(name = "ZonaGeografica.findByPrograma", query = "SELECT z FROM ZonaGeografica z WHERE z.programa = :programa"),
    @NamedQuery(name = "ZonaGeografica.findByFechaModif", query = "SELECT z FROM ZonaGeografica z WHERE z.fechaModif = :fechaModif"),
    @NamedQuery(name = "ZonaGeografica.findByIdZona", query = "SELECT z FROM ZonaGeografica z WHERE z.idZona = :idZona"),
    @NamedQuery(name = "ZonaGeografica.findByNombre", query = "SELECT z FROM ZonaGeografica z WHERE z.nombre = :nombre"),
    @NamedQuery(name = "ZonaGeografica.findByIdPadre", query = "SELECT z FROM ZonaGeografica z WHERE z.idPadre = :idPadre"),
    @NamedQuery(name = "ZonaGeografica.findByEstado", query = "SELECT z FROM ZonaGeografica z WHERE z.estado = :estado"),
    @NamedQuery(name = "ZonaGeografica.findByZonaEmpresa", query = "SELECT z FROM ZonaGeografica z WHERE z.zonaEmpresa = :zonaEmpresa"),
    @NamedQuery(name = "ZonaGeografica.findByCoordX", query = "SELECT z FROM ZonaGeografica z WHERE z.coordX = :coordX"),
    @NamedQuery(name = "ZonaGeografica.findByCoordY", query = "SELECT z FROM ZonaGeografica z WHERE z.coordY = :coordY"),
    @NamedQuery(name = "ZonaGeografica.findByIdComercial", query = "SELECT z FROM ZonaGeografica z WHERE z.idComercial = :idComercial"),
    @NamedQuery(name = "ZonaGeografica.findByNombreComercial", query = "SELECT z FROM ZonaGeografica z WHERE z.nombreComercial = :nombreComercial")})
public class ZonaGeografica implements Serializable {
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
    @Column(name = "ID_ZONA")
    private BigDecimal idZona;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ID_PADRE")
    private BigInteger idPadre;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "ZONA_EMPRESA")
    private String zonaEmpresa;
    @Column(name = "COORD_X")
    private String coordX;
    @Column(name = "COORD_Y")
    private String coordY;
    @Column(name = "ID_COMERCIAL")
    private String idComercial;
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    @OneToMany(mappedBy = "zonaGeografica")
    private List<SComponente> sComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zonaGeografica")
    private List<RelComponenteUbicacion> relComponenteUbicacionList;
    @OneToMany(mappedBy = "zonaGeografica")
    private List<Componente> componenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zonaGeografica")
    private List<SHistRelComponenteUbica> sHistRelComponenteUbicaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zonaGeografica")
    private List<SRelComponenteUbicacion> sRelComponenteUbicacionList;
    @JoinColumn(name = "ID_TIPO_COMPONENTE", referencedColumnName = "ID_TIPO_COMPONENTE")
    @ManyToOne(optional = false)
    private TipoComponente tipoComponente;

    public ZonaGeografica() {
    }

    public ZonaGeografica(BigDecimal idZona) {
        this.idZona = idZona;
    }

    public ZonaGeografica(BigDecimal idZona, String usuario, String programa, Date fechaModif, String nombre, String estado) {
        this.idZona = idZona;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
        this.estado = estado;
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

    public BigDecimal getIdZona() {
        return idZona;
    }

    public void setIdZona(BigDecimal idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigInteger getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(BigInteger idPadre) {
        this.idPadre = idPadre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getZonaEmpresa() {
        return zonaEmpresa;
    }

    public void setZonaEmpresa(String zonaEmpresa) {
        this.zonaEmpresa = zonaEmpresa;
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

    public String getIdComercial() {
        return idComercial;
    }

    public void setIdComercial(String idComercial) {
        this.idComercial = idComercial;
    }

    public List<SComponente> getSComponenteList() {
        return sComponenteList;
    }

    public void setSComponenteList(List<SComponente> sComponenteList) {
        this.sComponenteList = sComponenteList;
    }

    public List<RelComponenteUbicacion> getRelComponenteUbicacionList() {
        return relComponenteUbicacionList;
    }

    public void setRelComponenteUbicacionList(List<RelComponenteUbicacion> relComponenteUbicacionList) {
        this.relComponenteUbicacionList = relComponenteUbicacionList;
    }

    public List<Componente> getComponenteList() {
        return componenteList;
    }

    public void setComponenteList(List<Componente> componenteList) {
        this.componenteList = componenteList;
    }

    public List<SHistRelComponenteUbica> getSHistRelComponenteUbicaList() {
        return sHistRelComponenteUbicaList;
    }

    public void setSHistRelComponenteUbicaList(List<SHistRelComponenteUbica> sHistRelComponenteUbicaList) {
        this.sHistRelComponenteUbicaList = sHistRelComponenteUbicaList;
    }

    public List<SRelComponenteUbicacion> getSRelComponenteUbicacionList() {
        return sRelComponenteUbicacionList;
    }

    public void setSRelComponenteUbicacionList(List<SRelComponenteUbicacion> sRelComponenteUbicacionList) {
        this.sRelComponenteUbicacionList = sRelComponenteUbicacionList;
    }

    public TipoComponente getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(TipoComponente tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZona != null ? idZona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ZonaGeografica)) {
            return false;
        }
        ZonaGeografica other = (ZonaGeografica) object;
        if ((this.idZona == null && other.idZona != null) || (this.idZona != null && !this.idZona.equals(other.idZona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.ZonaGeografica[idZona=" + idZona + "]";
    }

    /**
     * @return the nombreComercial
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * @param nombreComercial the nombreComercial to set
     */
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

}
