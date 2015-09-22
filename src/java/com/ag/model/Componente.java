package com.ag.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "COMPONENTE")
@NamedQueries({
    @NamedQuery(name = "Componente.findAll", query = "SELECT c FROM Componente c"),
    @NamedQuery(name = "Componente.findByUsuario", query = "SELECT c FROM Componente c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Componente.findByPrograma", query = "SELECT c FROM Componente c WHERE c.programa = :programa"),
    @NamedQuery(name = "Componente.findByFechaModif", query = "SELECT c FROM Componente c WHERE c.fechaModif = :fechaModif"),
    @NamedQuery(name = "Componente.findByIdComponente", query = "SELECT c FROM Componente c WHERE c.idComponente = :idComponente"),
    @NamedQuery(name = "Componente.findByNombre", query = "SELECT c FROM Componente c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Componente.findByDireccion", query = "SELECT c FROM Componente c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Componente.findByCoordX", query = "SELECT c FROM Componente c WHERE c.coordX = :coordX"),
    @NamedQuery(name = "Componente.findByCoordY", query = "SELECT c FROM Componente c WHERE c.coordY = :coordY"),
    @NamedQuery(name = "Componente.findByIdCliente", query = "SELECT c FROM Componente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Componente.findByTipoProvisional", query = "SELECT c FROM Componente c WHERE c.tipoProvisional = :tipoProvisional")})
public class Componente implements Serializable {
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
    @Column(name = "ID_COMPONENTE")
    private BigDecimal idComponente;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "COORD_X")
    private String coordX;
    @Column(name = "COORD_Y")
    private String coordY;
    @Column(name = "ID_CLIENTE")
    private String idCliente;
    @Basic(optional = false)
    @Column(name = "TIPO_PROVISIONAL")
    private String tipoProvisional;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<RelComponenteUbicacion> relComponenteUbicacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<Medida> medidaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<RelComponenteMedida> relComponenteMedidaList;
    @JoinColumn(name = "ID_ZONA", referencedColumnName = "ID_ZONA")
    @ManyToOne
    private ZonaGeografica zonaGeografica;
    @JoinColumn(name = "ID_TIPO_COMPONENTE", referencedColumnName = "ID_TIPO_COMPONENTE")
    @ManyToOne(optional = false)
    private TipoComponente tipoComponente;
    @JoinColumn(name = "TIPO_PROPIETARIO", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo;
    @JoinColumn(name = "LOCALIZACION", referencedColumnName = "TIPO")
    @ManyToOne(optional = false)
    private Tbltipo tbltipo1;
    @JoinColumn(name = "ESTADO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado;
    @JoinColumn(name = "ESTADO_SERVICIO", referencedColumnName = "ID_ESTADO")
    @ManyToOne(optional = false)
    private Estado estado1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<RelComponente> relComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente1")
    private List<RelComponente> relComponenteList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<CargaExtra> cargaExtraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<AtrComponente> atrComponenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<OrdenTrabajo> ordenTrabajoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "componente")
    private List<ComponenteCampania> componenteCampaniaList;

    public Componente() {
    }

    public Componente(BigDecimal idComponente) {
        this.idComponente = idComponente;
    }

    public Componente(BigDecimal idComponente, String usuario, String programa, Date fechaModif, String nombre, String direccion, String tipoProvisional) {
        this.idComponente = idComponente;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipoProvisional = tipoProvisional;
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

    public BigDecimal getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(BigDecimal idComponente) {
        this.idComponente = idComponente;
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

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getTipoProvisional() {
        return tipoProvisional;
    }

    public void setTipoProvisional(String tipoProvisional) {
        this.tipoProvisional = tipoProvisional;
    }

    public List<RelComponenteUbicacion> getRelComponenteUbicacionList() {
        return relComponenteUbicacionList;
    }

    public void setRelComponenteUbicacionList(List<RelComponenteUbicacion> relComponenteUbicacionList) {
        this.relComponenteUbicacionList = relComponenteUbicacionList;
    }

    public List<Medida> getMedidaList() {
        return medidaList;
    }

    public void setMedidaList(List<Medida> medidaList) {
        this.medidaList = medidaList;
    }

    public List<RelComponenteMedida> getRelComponenteMedidaList() {
        return relComponenteMedidaList;
    }

    public void setRelComponenteMedidaList(List<RelComponenteMedida> relComponenteMedidaList) {
        this.relComponenteMedidaList = relComponenteMedidaList;
    }

    public ZonaGeografica getZonaGeografica() {
        return zonaGeografica;
    }

    public void setZonaGeografica(ZonaGeografica zonaGeografica) {
        this.zonaGeografica = zonaGeografica;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Estado getEstado1() {
        return estado1;
    }

    public void setEstado1(Estado estado1) {
        this.estado1 = estado1;
    }

    public List<RelComponente> getRelComponenteList() {
        return relComponenteList;
    }

    public void setRelComponenteList(List<RelComponente> relComponenteList) {
        this.relComponenteList = relComponenteList;
    }

    public List<RelComponente> getRelComponenteList1() {
        return relComponenteList1;
    }

    public void setRelComponenteList1(List<RelComponente> relComponenteList1) {
        this.relComponenteList1 = relComponenteList1;
    }

    public List<CargaExtra> getCargaExtraList() {
        return cargaExtraList;
    }

    public void setCargaExtraList(List<CargaExtra> cargaExtraList) {
        this.cargaExtraList = cargaExtraList;
    }

    public List<AtrComponente> getAtrComponenteList() {
        return atrComponenteList;
    }

    public void setAtrComponenteList(List<AtrComponente> atrComponenteList) {
        this.atrComponenteList = atrComponenteList;
    }

    public List<OrdenTrabajo> getOrdenTrabajoList() {
        return ordenTrabajoList;
    }

    public void setOrdenTrabajoList(List<OrdenTrabajo> ordenTrabajoList) {
        this.ordenTrabajoList = ordenTrabajoList;
    }

    public List<ComponenteCampania> getComponenteCampaniaList() {
        return componenteCampaniaList;
    }

    public void setComponenteCampaniaList(List<ComponenteCampania> componenteCampaniaList) {
        this.componenteCampaniaList = componenteCampaniaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Componente)) {
            return false;
        }
        Componente other = (Componente) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.Componente[idComponente=" + idComponente + "]";
    }

}
