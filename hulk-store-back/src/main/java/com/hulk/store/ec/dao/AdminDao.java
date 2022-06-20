package com.hulk.store.ec.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hulk.store.ec.base.BaseDao;


/**
 * Repositorio genérico
 * 
 * @author edwin
 *
 * @param <V> Clase genérica
 */
@Repository
public class AdminDao<V> extends BaseDao<V> {

	@PersistenceContext
	EntityManager em;

	/**
	 * Conexión a heredarse
	 */
	public EntityManager getEntityManager() {
		return em;
	}
}
