// Generated with g9.

package com.hulk.store.ec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="usuario")
public class Usuario implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8138682366084579933L;
	@JsonIgnore
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="usu_id", unique=true, nullable=false)
    private int usuId;
	@JsonProperty("Nombre completo")
    @Column(nullable=false, length=50)
    private String nombre;
	@JsonProperty("usuario")
    @Column(nullable=false, length=50)
    private String login;
	@JsonIgnore
    @Column(nullable=false, length=50)
    private String clave;
	@JsonIgnore
    @Column(nullable=false)
    private Boolean estado;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_EC")
    private LocalDate fecha;
	@JsonIgnore
    @ManyToOne(optional=false)
    @JoinColumn(name="tipo_usuario", nullable=false)
    private Categoria categoria;

    /** Default constructor. */
    public Usuario() {
        super();
    }

    /**
     * Access method for usuId.
     *
     * @return the current value of usuId
     */
    public int getUsuId() {
        return usuId;
    }

    /**
     * Setter method for usuId.
     *
     * @param aUsuId the new value for usuId
     */
    public void setUsuId(int aUsuId) {
        usuId = aUsuId;
    }

    /**
     * Access method for nombre.
     *
     * @return the current value of nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter method for nombre.
     *
     * @param aNombre the new value for nombre
     */
    public void setNombre(String aNombre) {
        nombre = aNombre;
    }

    /**
     * Access method for login.
     *
     * @return the current value of login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter method for login.
     *
     * @param aLogin the new value for login
     */
    public void setLogin(String aLogin) {
        login = aLogin;
    }

    /**
     * Access method for clave.
     *
     * @return the current value of clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * Setter method for clave.
     *
     * @param aClave the new value for clave
     */
    public void setClave(String aClave) {
        clave = aClave;
    }

    /**
     * Access method for estado.
     *
     * @return true if and only if estado is currently true
     */
    public boolean getEstado() {
        return estado;
    }

    /**
     * Setter method for estado.
     *
     * @param aEstado the new value for estado
     */
    public void setEstado(boolean aEstado) {
        estado = aEstado;
    }

    /**
     * Access method for fecha.
     *
     * @return the current value of fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * Setter method for fecha.
     *
     * @param aFecha the new value for fecha
     */
    public void setFecha(LocalDate aFecha) {
        fecha = aFecha;
    }

    /**
     * Access method for categoria.
     *
     * @return the current value of categoria
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Setter method for categoria.
     *
     * @param aCategoria the new value for categoria
     */
    public void setCategoria(Categoria aCategoria) {
        categoria = aCategoria;
    }

    /**
     * Compares the key for this instance with another Usuario.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Usuario and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Usuario)) {
            return false;
        }
        Usuario that = (Usuario) other;
        if (this.getUsuId() != that.getUsuId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Usuario.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Usuario)) return false;
        return this.equalKeys(other) && ((Usuario)other).equalKeys(this);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
    @Override
    public int hashCode() {
        int i;
        int result = 17;
        i = getUsuId();
        result = 37*result + i;
        return result;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Usuario |");
        sb.append(" usuId=").append(getUsuId());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("usuId", Integer.valueOf(getUsuId()));
        return ret;
    }

}
