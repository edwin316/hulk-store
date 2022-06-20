// Generated with g9.

package com.hulk.store.ec.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity(name="categoria")
public class Categoria implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5843181849348452777L;
	@JsonIgnore
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="cat_id", unique=true, nullable=false)
    private Integer catId;
	@JsonProperty("catalogo")
    @Column(nullable=false, length=50)
    private String nombre;
    @JsonIgnore
    @Column(length=150)
    private String descripcion;
    @Column(nullable=false, length=20)
    @JsonIgnore
    private String nemonico;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_EC")
    private LocalDate fecha;
    @JsonIgnore
    @OneToMany(mappedBy="categoria")
    private Set<Categoria> categoriaM;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="padre_id")
    private Categoria categoria;
    @JsonIgnore
    @OneToMany(mappedBy="categoria")
    private Set<Producto> producto;
    @JsonIgnore
    @OneToMany(mappedBy="categoria")
    private Set<Usuario> usuario;

    /** Default constructor. */
    public Categoria() {
        super();
    }

    /**
     * Access method for catId.
     *
     * @return the current value of catId
     */
    public Integer getCatId() {
        return catId;
    }

    /**
     * Setter method for catId.
     *
     * @param aCatId the new value for catId
     */
    public void setCatId(Integer aCatId) {
        catId = aCatId;
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
     * Access method for descripcion.
     *
     * @return the current value of descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Setter method for descripcion.
     *
     * @param aDescripcion the new value for descripcion
     */
    public void setDescripcion(String aDescripcion) {
        descripcion = aDescripcion;
    }

    /**
     * Access method for nemonico.
     *
     * @return the current value of nemonico
     */
    public String getNemonico() {
        return nemonico;
    }

    /**
     * Setter method for nemonico.
     *
     * @param aNemonico the new value for nemonico
     */
    public void setNemonico(String aNemonico) {
        nemonico = aNemonico;
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
     * Access method for categoriaM.
     *
     * @return the current value of categoriaM
     */
    public Set<Categoria> getCategoriaM() {
        return categoriaM;
    }

    /**
     * Setter method for categoriaM.
     *
     * @param aCategoriaM the new value for categoriaM
     */
    public void setCategoriaM(Set<Categoria> aCategoriaM) {
        categoriaM = aCategoriaM;
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
     * Access method for producto.
     *
     * @return the current value of producto
     */
    public Set<Producto> getProducto() {
        return producto;
    }

    /**
     * Setter method for producto.
     *
     * @param aProducto the new value for producto
     */
    public void setProducto(Set<Producto> aProducto) {
        producto = aProducto;
    }

    /**
     * Access method for usuario.
     *
     * @return the current value of usuario
     */
    public Set<Usuario> getUsuario() {
        return usuario;
    }

    /**
     * Setter method for usuario.
     *
     * @param aUsuario the new value for usuario
     */
    public void setUsuario(Set<Usuario> aUsuario) {
        usuario = aUsuario;
    }

    /**
     * Compares the key for this instance with another Categoria.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Categoria and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Categoria)) {
            return false;
        }
        Categoria that = (Categoria) other;
        if (this.getCatId() != that.getCatId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Categoria.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Categoria)) return false;
        return this.equalKeys(other) && ((Categoria)other).equalKeys(this);
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[Categoria |");
        sb.append(" catId=").append(getCatId());
        sb.append("]");
        return sb.toString();
    }

}
