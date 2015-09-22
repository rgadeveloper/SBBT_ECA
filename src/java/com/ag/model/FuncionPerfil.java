/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ag.model;

import java.io.Serializable;
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
@Table(name = "FUNCION_PERFIL")
@NamedQueries({
    @NamedQuery(name = "FuncionPerfil.findAll", query = "SELECT f FROM FuncionPerfil f"),
    @NamedQuery(name = "FuncionPerfil.findByUsuario", query = "SELECT f FROM FuncionPerfil f WHERE f.usuario = :usuario"),
    @NamedQuery(name = "FuncionPerfil.findByPrograma", query = "SELECT f FROM FuncionPerfil f WHERE f.programa = :programa"),
    @NamedQuery(name = "FuncionPerfil.findByFechaModif", query = "SELECT f FROM FuncionPerfil f WHERE f.fechaModif = :fechaModif"),
    @NamedQuery(name = "FuncionPerfil.findByCodigoFuncion", query = "SELECT f FROM FuncionPerfil f WHERE f.funcionPerfilPK.codigoFuncion = :codigoFuncion"),
    @NamedQuery(name = "FuncionPerfil.findByCodigoPerfil", query = "SELECT f FROM FuncionPerfil f WHERE f.funcionPerfilPK.codigoPerfil = :codigoPerfil")})
public class FuncionPerfil implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FuncionPerfilPK funcionPerfilPK;
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
    @JoinColumn(name = "CODIGO_PERFIL", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Perfil perfil;
    @JoinColumn(name = "CODIGO_FUNCION", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Funcion funcion;

    public FuncionPerfil() {
    }

    public FuncionPerfil(FuncionPerfilPK funcionPerfilPK) {
        this.funcionPerfilPK = funcionPerfilPK;
    }

    public FuncionPerfil(FuncionPerfilPK funcionPerfilPK, String usuario, String programa, Date fechaModif) {
        this.funcionPerfilPK = funcionPerfilPK;
        this.usuario = usuario;
        this.programa = programa;
        this.fechaModif = fechaModif;
    }

    public FuncionPerfil(String codigoFuncion, String codigoPerfil) {
        this.funcionPerfilPK = new FuncionPerfilPK(codigoFuncion, codigoPerfil);
    }

    public FuncionPerfilPK getFuncionPerfilPK() {
        return funcionPerfilPK;
    }

    public void setFuncionPerfilPK(FuncionPerfilPK funcionPerfilPK) {
        this.funcionPerfilPK = funcionPerfilPK;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (funcionPerfilPK != null ? funcionPerfilPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuncionPerfil)) {
            return false;
        }
        FuncionPerfil other = (FuncionPerfil) object;
        if ((this.funcionPerfilPK == null && other.funcionPerfilPK != null) || (this.funcionPerfilPK != null && !this.funcionPerfilPK.equals(other.funcionPerfilPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ag.model.FuncionPerfil[funcionPerfilPK=" + funcionPerfilPK + "]";
    }

}
