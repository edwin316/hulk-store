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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hulk.store.ec.util.AES;

@Entity(name = "producto")
public class Producto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3632139602923371885L;
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pro_id", unique = true, nullable = false)
	private Integer proId;
	@JsonProperty("producto")
	@Column(nullable = false, length = 50)
	private String nombre;
	@Column(length = 150)
	@JsonProperty("informacion")
	private String descripcion;
	@JsonProperty("icono")
	@Column(length = 100)
	private String icon;
	private int cantidad;
	@Column(name = "valor_unitario", precision = 10, scale = 2)
	private Double valorUnitario;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "es_EC")
	private LocalDate fecha;
	private Boolean estado;
	@JsonIgnore
	@JoinColumn(name = "cat_id", referencedColumnName = "cat_id")
	@ManyToOne
	private Categoria categoria;
	@JsonIgnore
	@OneToMany(mappedBy = "producto")
	private Set<Stock> stock;
	@JsonIgnore
	@OneToMany(mappedBy = "producto")
	private Set<Transaccion> transaccion;
	@JsonProperty("codigo")
	@Transient
	private String ids;

	/** Default constructor. */
	public Producto() {
		super();
	}
	
	public Producto(Integer proId) {
		this.proId = proId;
	}

	/**
	 * Access method for proId.
	 *
	 * @return the current value of proId
	 */
	public Integer getProId() {
		return proId;
	}

	/**
	 * Setter method for proId.
	 *
	 * @param aProId the new value for proId
	 */
	public void setProId(Integer aProId) {
		proId = aProId;
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
	 * Access method for icon.
	 *
	 * @return the current value of icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Setter method for icon.
	 *
	 * @param aIcon the new value for icon
	 */
	public void setIcon(String aIcon) {
		icon = aIcon;
	}

	/**
	 * Access method for cantidad.
	 *
	 * @return the current value of cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Setter method for cantidad.
	 *
	 * @param aCantidad the new value for cantidad
	 */
	public void setCantidad(int aCantidad) {
		cantidad = aCantidad;
	}

	/**
	 * Access method for valorUnitario.
	 *
	 * @return the current value of valorUnitario
	 */
	public Double getValorUnitario() {
		return valorUnitario;
	}

	/**
	 * Setter method for valorUnitario.
	 *
	 * @param aValorUnitario the new value for valorUnitario
	 */
	public void setValorUnitario(Double aValorUnitario) {
		valorUnitario = aValorUnitario;
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
	 * Access method for stock.
	 *
	 * @return the current value of stock
	 */
	public Set<Stock> getStock() {
		return stock;
	}

	/**
	 * Setter method for stock.
	 *
	 * @param aStock the new value for stock
	 */
	public void setStock(Set<Stock> aStock) {
		stock = aStock;
	}

	/**
	 * Access method for transaccion.
	 *
	 * @return the current value of transaccion
	 */
	public Set<Transaccion> getTransaccion() {
		return transaccion;
	}

	/**
	 * Setter method for transaccion.
	 *
	 * @param aTransaccion the new value for transaccion
	 */
	public void setTransaccion(Set<Transaccion> aTransaccion) {
		transaccion = aTransaccion;
	}

	/**
	 * Compares the key for this instance with another Producto.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class Producto and the key
	 *         objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Producto)) {
			return false;
		}
		Producto that = (Producto) other;
		if (this.getProId() != that.getProId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another Producto.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Producto))
			return false;
		return this.equalKeys(other) && ((Producto) other).equalKeys(this);
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
		i = getProId();
		result = 37 * result + i;
		return result;
	}

	/**
	 * Returns a debug-friendly String representation of this instance.
	 *
	 * @return String representation of this instance
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("[Producto |");
		sb.append(" proId=").append(getProId());
		sb.append("]");
		return sb.toString();
	}

	public String getIds() {
		if(this.getProId() != null) {
			ids = AES.encrypt(this.getProId().toString(), AES.semm);
		}
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
