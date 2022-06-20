package com.hulk.store.ec.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.hulk.store.ec.dto.SelectItem;
import com.hulk.store.ec.enums.CondicionEnum;
import com.hulk.store.ec.util.AES;

/**
 * Genérico consumo base de datos
 * 
 * @author edwin
 *
 * @param <V>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseDao<V> {

	private Logger log = LoggerFactory.getLogger(BaseDao.class);
	// Mapeo de columnas trasient en su correspondiente columna de base de datos
	private Map<String, String> coltrasient;
	@Value( "${datainfo.key}" )
	private String semilla;

	protected abstract EntityManager getEntityManager();

	/**
	 * Inserta objetos en la base de datos.
	 *
	 * @param entidad Entidad
	 */
	@Transactional
	public void insertar(V entidad) {
		this.getEntityManager().persist(entidad);
	}

	/**
	 * Actualiza objetos en la base de datos.
	 *
	 * @param entidad Entidad
	 * @return Entidad actualizada
	 */
	public V actualizar(V entidad) {
		return this.getEntityManager().merge(entidad);
	}

	/**
	 * Elimina registros
	 * 
	 * @param clazz
	 * @param where
	 * @return
	 */
	@Transactional(rollbackOn = Exception.class)
	public Boolean eliminarRegistros(Class<V> clazz, Object[][] where) {
		try {
			CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
			CriteriaDelete<V> delete = cb.createCriteriaDelete(clazz);
			Root<V> from = delete.from(clazz);
			List<Predicate> predicates = new ArrayList<>();
			Map<ParameterExpression, Object> params = new HashMap<ParameterExpression, Object>();
			this.condiciones(predicates, cb, from, null, where, params);
			delete.where(predicates.toArray(new Predicate[] {}));

			Query query = getEntityManager().createQuery(delete);
			// Sets
			for (Map.Entry<ParameterExpression, Object> param : params.entrySet()) {
				ParameterExpression<Object> key = param.getKey();
				Object value = param.getValue();
				query.setParameter(key, value);
			}

			return query.executeUpdate() > 0;
		} catch (Exception e) {
			log.error("Error al eliminar ", e);
			return false;
		}
	}

	/**
	 * Actualización por campos
	 * @param clazz
	 * @param where
	 * @param valor
	 * @return
	 */
	@Transactional
	public boolean actualizarCampos(Class<V> clazz, Object[][] where, Map<String, Object> valor) {
		try {
			CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
			CriteriaUpdate<V> update = cb.createCriteriaUpdate(clazz);
			Root<V> from = update.from(clazz);

			valor.entrySet().forEach((entry) -> {
				update.set(entry.getKey(), entry.getValue());
			});

			List<Predicate> predicates = new ArrayList<>();
			Map<ParameterExpression, Object> params = new HashMap<ParameterExpression, Object>();
			this.condiciones(predicates, cb, from, null, where, params);

			update.where(predicates.toArray(new Predicate[] {}));
			Query query = getEntityManager().createQuery(update);
			// Sets
			for (Map.Entry<ParameterExpression, Object> param : params.entrySet()) {
				ParameterExpression<Object> key = param.getKey();
				Object value = param.getValue();
				query.setParameter(key, value);
			}

			return query.executeUpdate() > 0;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	/**
	 * Retorna el valor de una determinada columna de la tabla correspondiente
	 *
	 * @param clazz
	 * @param field
	 * @param filter
	 * @return
	 */
	@Transactional
	public Object campoConsulta(Class<V> clazz, String field, Object[][] filtro) {
		try {
			CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Object> root = cb.createQuery(Object.class);
			Root<V> from = root.from(clazz);
			List<Predicate> predicates = new ArrayList<>();
			Map<ParameterExpression, Object> params = new HashMap<ParameterExpression, Object>();
			this.condiciones(predicates, cb, from, null, filtro, params);
			root.select(from.get(field)).where(predicates.toArray(new Predicate[] {}));
			TypedQuery<Object> jpaQuery = this.getEntityManager().createQuery(root);
			jpaQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
			jpaQuery.setMaxResults(1);
			// Sets
			for (Map.Entry<ParameterExpression, Object> param : params.entrySet()) {
				ParameterExpression<Object> key = param.getKey();
				Object value = param.getValue();
				jpaQuery.setParameter(key, value);
			}

			return jpaQuery.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * single record on constraints
	 * 
	 * @param clazz
	 * @param filtro
	 * @param cols
	 * @return V Entity
	 */
	@Transactional
	public V registroEntidad(Class<V> clazz, Object[][] filtro, String cols) {
		try {
			CriteriaBuilder cb = (CriteriaBuilder) getEntityManager().getCriteriaBuilder();
			CriteriaQuery<V> q = cb.createQuery(clazz);
			Root<V> lista = q.from(clazz);
			Map<ParameterExpression, Object> params = new HashMap<ParameterExpression, Object>();
			List<Predicate> predicates = new ArrayList<>();
			this.condiciones(predicates, cb, lista, null, filtro, params);
			// Select field1, field2
			q.select(lista);
			if (cols != null) {
				String[] fields = cols.contains(",") ? cols.split(",") : new String[] { cols };
				Selection<V>[] sfields = new Selection[fields.length];
				for (int i = 0; i < fields.length; i++) {
					String[] sfld = fields[i].contains(".") ? fields[i].split("\\.") : new String[] { fields[i] };
					int indx = fields[i].contains("lf-") ? 2 : 1;
					Path<V> exp = lista.get(sfld[0].trim());
					for (int j = indx; j < sfld.length; j++) {
						exp = exp.get(sfld[j].trim());
					}
					sfields[i] = exp;
				}
				q.select(cb.construct(clazz, sfields));
			}
			// Constraints
			q.where(predicates.toArray(new Predicate[] {}));
			// Query init
			TypedQuery<V> jpaQuery = getEntityManager().createQuery(q)
					.setHint("javax.persistence.cache.storeMode", "REFRESH").setFirstResult(0).setMaxResults(1);
			// Sets
			for (Map.Entry<ParameterExpression, Object> param : params.entrySet()) {
				ParameterExpression<Object> key = param.getKey();
				Object value = param.getValue();
				jpaQuery.setParameter(key, value);
			}
			// Result
			return jpaQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Listado Genérico
	 * 
	 * @param clazz      Generic class
	 * @param first      Primer elemento
	 * @param pageSize   tolta registros por página
	 * @param sortField  columna ordanación
	 * @param asc        Ordenación ASC o DESC
	 * @param filters    Filtro ingresado desde los Datatable
	 * @param filtro     Filtro en tiempo de ejecución.
	 * @param cols       Lista columnas select, ej.: col1,col2
	 * @param leftFields Fields left join
	 * @param subCol     filed subcolumn
	 * @return
	 */
	@Transactional
	public List<V> listaRegistro(Class<V> clazz, int first, int pageSize, String sortField, boolean asc,
			Map<String, Object> filters, Object[][] filtro, String cols) {
		try {
			CriteriaBuilder cb = (CriteriaBuilder) getEntityManager().getCriteriaBuilder();
			CriteriaQuery<V> q = cb.createQuery(clazz);
			Map<ParameterExpression, Object> params = new HashMap<ParameterExpression, Object>();
			Root<V> lista = q.from(clazz);
			List<Predicate> predicates = new ArrayList<>();
			this.condiciones(predicates, cb, lista, filters, filtro, params);
			// Sort
			if (sortField != null) {
				String[] fields = sortField.contains(".") ? sortField.split("\\.") : new String[] { sortField };
				Path exp = lista.get(fields[0]);
				for (int i = 1; i < fields.length; i++) {
					exp = exp.get(fields[i]);
				}
				q.orderBy(asc ? cb.asc(exp) : cb.desc(exp));
			}
			// select *from
			q.select(lista);
			// select field1, field2, ...
			if (cols != null && !cols.isEmpty()) {
				String[] fields = cols.contains(",") ? cols.split(",") : new String[] { cols };
				Selection[] sfields = new Selection[fields.length];
				Path<?> exp;
				for (int i = 0; i < fields.length; i++) {
					String[] sfld = fields[i].contains(".") ? fields[i].split("\\.") : new String[] { fields[i] };
					exp = lista.get(sfld[0].trim());
					for (int j = 1; j < sfld.length; j++) {
						exp = exp.get(sfld[j].trim());
					}

					sfields[i] = exp;
				}
				q.select(cb.construct(clazz, sfields));
			}
			// Constraints
			q.where(predicates.toArray(new Predicate[] {}));
			// Query init
			TypedQuery<V> jpaQuery = getEntityManager().createQuery(q)
					.setHint("javax.persistence.cache.storeMode", "REFRESH").setFirstResult(first * pageSize)
					.setMaxResults(pageSize);
			// Sets
			for (Map.Entry<ParameterExpression, Object> param : params.entrySet()) {
				ParameterExpression<Object> key = param.getKey();
				Object value = param.getValue();
				jpaQuery.setParameter(key, value);
			}
			// Result
			return (List<V>) jpaQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	/**
	 * Total listado
	 * 
	 * @param filter Filtro ingresado desde los Datatable
	 * @param filtro Filtro en tiempo de ejecución.
	 * @param clazz  Generic class
	 * @return Long
	 */
	@Transactional
	public Long contarRegistros(Map<String, Object> filter, Object[][] filtro, Class<V> clazz) {
		try {
			// Objects EclipseLink
			CriteriaBuilder cb = (CriteriaBuilder) getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Long> root = cb.createQuery(Long.class);
			Root<V> from = root.from(clazz);
			Map<ParameterExpression, Object> params = new HashMap<ParameterExpression, Object>();
			// Constraints
			List<Predicate> predicates = new ArrayList<>();
			this.condiciones(predicates, cb, from, filter, filtro, params);
			// Init Query
			root.select(cb.count(from)).where(predicates.toArray(new Predicate[] {}));
			TypedQuery<Long> jpaQuery = getEntityManager().createQuery(root);
			jpaQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
			// Params, Xss protection attacks
			params.entrySet().forEach((param) -> {
				ParameterExpression key = param.getKey();
				Object value = param.getValue();
				jpaQuery.setParameter(key.getName(), value);
			});
			// Result
			return jpaQuery.getSingleResult();
		} catch (Exception e) {
			log.error("Error", e);
			return 0L;
		}
	}

	/**
	 * Búsqueda Genérica por Id
	 * 
	 * @param id
	 * @param clazz
	 * @return
	 */
	@Transactional
	public V entidadPorId(Integer id, Integer ids, Class<V> clazz) {
		try {
			return getEntityManager().find(clazz, id == null ? ids : id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Búsqueda de entidad por nombre de consulta (NamedQuery)
	 * 
	 * @param name  NamedQuery
	 * @param param Params (where)
	 * @return Entity
	 */
	@Transactional
	public V entityByName(String name, Map<String, Object> param) {
		try {
			Query query = getEntityManager().createNamedQuery(name);
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			return (V) query.setHint("javax.persistence.cache.storeMode", "REFRESH").setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Lista de entidades por NamedQuery
	 * 
	 * @param name  NamedQuery
	 * @param param Params
	 * @return List Entity
	 */
	@Transactional
	public List<V> listaPorNombre(String name, Map<String, Object> param) {
		try {
			Query query = getEntityManager().createNamedQuery(name);
			for (Map.Entry<String, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			return query.setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();
		} catch (Exception e) {
			log.error("Error ", e);
			return null;
		}
	}

	/**
	 * Ejecuta una función nativa
	 * 
	 * @param name  namedQuery
	 * @param param Param Query
	 * @return Boolean
	 */
	@Transactional
	public Object funcionSqlNativa(String name, Map<Integer, Object> param) {
		try {
			TypedQuery<Object> query = getEntityManager().createNamedQuery(name, Object.class);
			for (Map.Entry<Integer, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			query.setMaxResults(1);

			return (Object) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error("Función uno ", e);
			return null;
		}
	}

	/**
	 * Funnción nativa sql, devuelve una solo entidad
	 * 
	 * @param name
	 * @param param
	 * @return
	 */
	@Transactional
	public V funcionSqlNativaRegistro(String name, Map<Integer, Object> param) {
		try {
			TypedQuery<Object> query = getEntityManager().createNamedQuery(name, Object.class);
			for (Map.Entry<Integer, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			query.setMaxResults(1);

			return (V) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * Consulta por función nativa, devuelve listado
	 * 
	 * @param name
	 * @param param
	 * @return
	 */
	@Transactional
	public List<V> funcionSqlNativaLista(String name, Map<Integer, Object> param) {
		try {
			TypedQuery<Object> query = getEntityManager().createNamedQuery(name, Object.class);
			for (Map.Entry<Integer, Object> entry : param.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}

			return (List<V>) query.getResultList();
		} catch (Exception e) {
			log.error("Función nativa ", e);
			return null;
		}
	}

	/**
	 * Listado para combos
	 * 
	 * @param filter Condiciones
	 * @param clazz  Entity
	 * @param value  Valor
	 * @param label  Etiqueta
	 * @param asc    ordenación
	 * @return Items
	 */
	@Transactional
	public List<SelectItem> listaCombo(Object[][] filter, Class<V> clazz, String value, String label, boolean asc) {
		// Objeto eclipselink de consultas
		CriteriaBuilder cb = (CriteriaBuilder) getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tuple> q = cb.createTupleQuery();
		Root<V> from = q.from(clazz);
		List<Predicate> predicates = new ArrayList<>();
		Map<ParameterExpression, Object> params = new HashMap<ParameterExpression, Object>();
		// Genero condiciones
		this.condiciones(predicates, cb, from, null, filter, params);
		// Ordenaciones y etiqueta
		Path exp;
		String[] fields = label.contains(",") ? label.split(",") : new String[] { label };
		Expression<?>[] sfields = new Expression[(fields.length + fields.length) - 1];
		Expression<String> xpr = cb.literal(" - ");
		
		int fieldsCount = 0;
		//intercala separador ' - '
		for (int i = 0; i < sfields.length; i++) {
			if (i % 2 == 0 && fieldsCount < fields.length ){
				String[] info = fields[fieldsCount].contains(".") ? fields[fieldsCount].split("\\.")
						: new String[] { fields[fieldsCount] };
				exp = from.get(info[0]);
				for (int j = 1; j < info.length; j++) {
					exp = exp.get(info[j]);
				}
				sfields[i] = exp;
				fieldsCount++;
			} else {
				sfields[i] = xpr;
			}
		}

		// Valores
		String[] info = value.contains(".") ? value.split("\\.") : new String[] { value };
		exp = from.get(info[0]);
		for (int j = 1; j < info.length; j++) {
			exp = exp.get(info[j]);
		}
		q.select(cb.tuple(cb.function("concat", String.class, sfields), exp));
		// Generar el constructor en la entity correspondiente: select val, label from
		// ...
		q.where(predicates.toArray(new Predicate[] {}));
		// creo objeto consulta
		TypedQuery<Tuple> jpaQuery = getEntityManager().createQuery(q);
		// Asigno valores
		params.entrySet().forEach((param) -> {
			ParameterExpression<Object> key = param.getKey();
			Object val = param.getValue();
			jpaQuery.setParameter(key, val);
		});
		// Genero la consulta
		List<Tuple> lista = (List<Tuple>) jpaQuery.setHint("javax.persistence.cache.storeMode", "REFRESH")
				.getResultList();
		List<SelectItem> items = new ArrayList<>();
		lista.forEach((tuple) -> {
			items.add(new SelectItem(tuple.get(0).toString(), AES.encrypt(tuple.get(1).toString(), this.semilla)));
		});

		return items;
	}

	/**
	 * Condiciones genéricas
	 * 
	 * @param predicates condiciones generales
	 * @param cb         Objeto consulta
	 * @param lista      lista campos
	 * @param filters    Filtro ingresado desde los Datatable
	 * @param filtros    Filtro en tiempo de ejecución.
	 * @param params     Parámetros o valores de ingreso
	 */
	private void condiciones(List<Predicate> predicates, CriteriaBuilder cb, Root<V> lista, Map<String, Object> filters,
			Object[][] filtros, Map<ParameterExpression, Object> params) {
		ParameterExpression param;
		if (filtros != null) {
			for (Object[] fltr : filtros) {

				String[] fields = fltr[1].toString().contains(".") ? fltr[1].toString().split("\\.")
						: new String[] { fltr[1].toString() };

				Path exp = lista.get(fields[0]);
				for (int i = 1; i < fields.length; i++) {
					exp = exp.get(fields[i]);
				}
				param = cb.parameter(Object.class, fltr.length > 3 ? fltr[3].toString() : fields[fields.length - 1]);
				switch ((CondicionEnum) fltr[0]) {
				case EQUAL:
					predicates.add(cb.equal(exp, param));
					params.put(param, fltr[2]);
					break;
				case NOT_EQUAL:
					predicates.add(cb.notEqual(exp, param));
					params.put(param, fltr[2]);
					break;
				case NOT_NULL:
					predicates.add(exp.isNotNull());
					break;
				case IS_NULL:
					predicates.add(exp.isNull());
					break;
				case LIKE:
					predicates.add(cb.like(cb.upper(exp), param));
					params.put(param, "%" + fltr[2].toString().toUpperCase() + "%");
					break;
				case YEAR: // Fecha por año
					predicates.add(cb.equal(cb.function("TO_CHAR", String.class, exp, cb.literal("'YYYY'")), fltr[2]));
					// params.put(param, entry.getValue());
					break;
				case IN:
					predicates.add(exp.in(param));
					params.put(param, fltr[2]);
					break;
				// Caso padre is null or child is equal value
				case OR_NULL_NOT_EQUAL:
					predicates.add(cb.or(exp.isNull(), cb.notEqual(exp, param)));
					params.put(param, fltr[2]);
					break;
				case EQUAL_OR_ZERO:
					ParameterExpression paramz = cb.parameter(Object.class, "zero");
					predicates.add(cb.or(cb.equal(exp, paramz), cb.equal(exp, param)));
					params.put(param, fltr[2]);
					params.put(paramz, 0);
					break;
				default:
					break;
				}
			}
		}
		// filtros
		if (filters != null) {
			for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
				try {
					String column = it.next();
					Object value = filters.get(column);
					String[] fields = column.contains(".") ? column.split("\\.") : new String[] { column };

					Path exp = lista.get(fields[0]);
					for (int i = 1; i < fields.length; i++) {
						exp = exp.get(fields[i]);
					}
					param = cb.parameter(Object.class, fields[fields.length - 1]);
					switch (exp.getJavaType().getName()) {
					case "java.lang.String":
						predicates.add(cb.like(cb.upper(exp), param));
						params.put(param, "%" + value.toString().toUpperCase() + "%");
						break;
					case "java.lang.Date":
						predicates.add(
								cb.equal(cb.function("to_char", String.class, exp, cb.literal("'DD/MM/YYYY'")), param));
						params.put(param, value.toString());
						break;
					case "java.lang.Integer":
						predicates.add(cb.equal(exp, param));
						params.put(param, Integer.parseInt(value.toString()));
						break;
					case "java.lang.Long":
						predicates.add(cb.equal(exp, param));
						params.put(param, Long.parseLong(value.toString()));
						break;
					default:
						break;
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	public Map<String, String> getColtrasient() {
		return coltrasient;
	}

	public void setColtrasient(Map<String, String> coltrasient) {
		this.coltrasient = coltrasient;
	}
}
