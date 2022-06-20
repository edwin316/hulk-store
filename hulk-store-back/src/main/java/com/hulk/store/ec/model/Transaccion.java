// Generated with g9.

package com.hulk.store.ec.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity(name="transaccion")
public class Transaccion implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4886902597779823721L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="tra_id", unique=true, nullable=false)
    private int traId;
    private int cantidad;
    @Column(name="valor_total", precision=10, scale=2)
    private BigDecimal valorTotal;
    private LocalDate fecha;
    @ManyToOne(optional=false)
    @JoinColumn(name="pro_id", nullable=false)
    private Producto producto;

    /** Default constructor. */
    public Transaccion() {
        super();
    }

    /**
     * Access method for traId.
     *
     * @return the current value of traId
     */
    public int getTraId() {
        return traId;
    }

    /**
     * Setter method for traId.
     *
     * @param aTraId the new value for traId
     */
    public void setTraId(int aTraId) {
        traId = aTraId;
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
     * Access method for valorTotal.
     *
     * @return the current value of valorTotal
     */
    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    /**
     * Setter method for valorTotal.
     *
     * @param aValorTotal the new value for valorTotal
     */
    public void setValorTotal(BigDecimal aValorTotal) {
        valorTotal = aValorTotal;
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
     * Access method for producto.
     *
     * @return the current value of producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Setter method for producto.
     *
     * @param aProducto the new value for producto
     */
    public void setProducto(Producto aProducto) {
        producto = aProducto;
    }

    /**
     * Compares the key for this instance with another Transaccion.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Transaccion and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Transaccion)) {
            return false;
        }
        Transaccion that = (Transaccion) other;
        if (this.getTraId() != that.getTraId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Transaccion.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Transaccion)) return false;
        return this.equalKeys(other) && ((Transaccion)other).equalKeys(this);
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
        i = getTraId();
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
        StringBuffer sb = new StringBuffer("[Transaccion |");
        sb.append(" traId=").append(getTraId());
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
        ret.put("traId", Integer.valueOf(getTraId()));
        return ret;
    }

}
