/**
 * This class is generated by jOOQ
 */
package cn.fyg.service.user.orm.jooq.tables.daos;


import cn.fyg.service.user.orm.jooq.tables.App;
import cn.fyg.service.user.orm.jooq.tables.records.AppRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.6.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AppDao extends DAOImpl<AppRecord, cn.fyg.service.user.orm.jooq.tables.pojos.App, Long> {

	/**
	 * Create a new AppDao without any configuration
	 */
	public AppDao() {
		super(App.APP, cn.fyg.service.user.orm.jooq.tables.pojos.App.class);
	}

	/**
	 * Create a new AppDao with an attached configuration
	 */
	public AppDao(Configuration configuration) {
		super(App.APP, cn.fyg.service.user.orm.jooq.tables.pojos.App.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Long getId(cn.fyg.service.user.orm.jooq.tables.pojos.App object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<cn.fyg.service.user.orm.jooq.tables.pojos.App> fetchById(Long... values) {
		return fetch(App.APP.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public cn.fyg.service.user.orm.jooq.tables.pojos.App fetchOneById(Long value) {
		return fetchOne(App.APP.ID, value);
	}

	/**
	 * Fetch records that have <code>name IN (values)</code>
	 */
	public List<cn.fyg.service.user.orm.jooq.tables.pojos.App> fetchByName(String... values) {
		return fetch(App.APP.NAME, values);
	}

	/**
	 * Fetch records that have <code>remark IN (values)</code>
	 */
	public List<cn.fyg.service.user.orm.jooq.tables.pojos.App> fetchByRemark(String... values) {
		return fetch(App.APP.REMARK, values);
	}

	/**
	 * Fetch records that have <code>url IN (values)</code>
	 */
	public List<cn.fyg.service.user.orm.jooq.tables.pojos.App> fetchByUrl(String... values) {
		return fetch(App.APP.URL, values);
	}
}
