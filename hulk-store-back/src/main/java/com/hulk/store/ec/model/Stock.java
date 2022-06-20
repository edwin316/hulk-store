// Generated with g9.

package com.hulk.store.ec.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
@Entity(name="stock")
public class Stock implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3710581850642595422L;
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="sto_id", unique=true, nullable=false)
    private int stoId;
    @Column(length=150)
    private String observacion;
    private int cantidad;
    @Column(name="cantidad_anterior")
    private int cantidadAnterior;
    @Column(name="cantidad_actual")
    private int cantidadActual;
    @Column(name="valor_unitario", precision=10, scale=2)
    private BigDecimal valorUnitario;
    @Column(name="fecha_entrada")
    private Date fechaEntrada;
    @Column(name="fecha_salida")
    private Date fechaSalida;
    private boolean estado;
    @ManyToOne(optional=false)
    @JoinColumn(name="pro_id", nullable=false)
    private Producto producto;

    /** Default constructor. */
    public Stock() {
        super();
    }

    /**
     * Access method for stoId.
     *
     * @return the current value of stoId
     */
    public int getStoId() {
        return stoId;
    }

    /**
     * Setter method for stoId.
     *
     * @param aStoId the new value for stoId
     */
    public void setStoId(int aStoId) {
        stoId = aStoId;
    }

    /**
     * Access method for observacion.
     *
     * @return the current value of observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * Setter method for observacion.
     *
     * @param aObservacion the new value for observacion
     */
    public void setObservacion(String aObservacion) {
        observacion = aObservacion;
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
     * Access method for cantidadAnterior.
     *
     * @return the current value of cantidadAnterior
     */
    public int getCantidadAnterior() {
        return cantidadAnterior;
    }

    /**
     * Setter method for cantidadAnterior.
     *
     * @param aCantidadAnterior the new value for cantidadAnterior
     */
    public void setCantidadAnterior(int aCantidadAnterior) {
        cantidadAnterior = aCantidadAnterior;
    }

    /**
     * Access method for cantidadActual.
     *
     * @return the current value of cantidadActual
     */
    public int getCantidadActual() {
        return cantidadActual;
    }

    /**
     * Setter method for cantidadActual.
     *
     * @param aCantidadActual the new value for cantidadActual
     */
    public void setCantidadActual(int aCantidadActual) {
        cantidadActual = aCantidadActual;
    }

    /**
     * Access method for valorUnitario.
     *
     * @return the current value of valorUnitario
     */
    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Setter method for valorUnitario.
     *
     * @param aValorUnitario the new value for valorUnitario
     */
    public void setValorUnitario(BigDecimal aValorUnitario) {
        valorUnitario = aValorUnitario;
    }

    /**
     * Access method for fechaEntrada.
     *
     * @return the current value of fechaEntrada
     */
    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    /**
     * Setter method for fechaEntrada.
     *
     * @param aFechaEntrada the new value for fechaEntrada
     */
    public void setFechaEntrada(Date aFechaEntrada) {
        fechaEntrada = aFechaEntrada;
    }

    /**
     * Access method for fechaSalida.
     *
     * @return the current value of fechaSalida
     */
    public Date getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Setter method for fechaSalida.
     *
     * @param aFechaSalida the new value for fechaSalida
     */
    public void setFechaSalida(Date aFechaSalida) {
        fechaSalida = aFechaSalida;
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
     * Compares the key for this instance with another Stock.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class Stock and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof Stock)) {
            return false;
        }
        Stock that = (Stock) other;
        if (this.getStoId() != that.getStoId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another Stock.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Stock)) return false;
        return this.equalKeys(other) && ((Stock)other).equalKeys(this);
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
        i = getStoId();
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
        StringBuffer sb = new StringBuffer("[Stock |");
        sb.append(" stoId=").append(getStoId());
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
        ret.put("stoId", Integer.valueOf(getStoId()));
        return ret;
    }

}
