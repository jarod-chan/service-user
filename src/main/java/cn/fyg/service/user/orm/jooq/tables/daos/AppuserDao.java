/**
 * This class is generated by jOOQ
 */
package cn.fyg.service.user.orm.jooq.tables.daos;


import cn.fyg.service.user.orm.jooq.tables.Appuser;
import cn.fyg.service.user.orm.jooq.tables.records.AppuserRecord;

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
public class AppuserDao extends DAOImpl<AppuserRecord, cn.fyg.service.user.orm.jooq.tables.pojos.Appuser, Long> {

	/**
	 * Create a new AppuserDao without any configuration
	 */
	public AppuserDao() {
		super(Appuser.APPUSER, cn.fyg.service.user.orm.jooq.tables.pojos.Appuser.class);
	}

	/**
	 * Create a new AppuserDao with an attached configuration
	 */
	public AppuserDao(Configuration configuration) {
		super(Appuser.APPUSER, cn.fyg.service.user.orm.jooq.tables.pojos.Appuser.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Long getId(cn.fyg.service.user.orm.jooq.tables.pojos.Appuser object) {
		return object.getId();
	}

	/**
	 * Fetch records that have <code>id IN (values)</code>
	 */
	public List<cn.fyg.service.user.orm.jooq.tables.pojos.Appuser> fetchById(Long... values) {
		return fetch(Appuser.APPUSER.ID, values);
	}

	/**
	 * Fetch a unique record that has <code>id = value</code>
	 */
	public cn.fyg.service.user.orm.jooq.tables.pojos.Appuser fetchOneById(Long value) {
		return fetchOne(Appuser.APPUSER.ID, value);
	}

	/**
	 * Fetch records that have <code>app_id IN (values)</code>
	 */
	public List<cn.fyg.service.user.orm.jooq.tables.pojos.Appuser> fetchByAppId(Long... values) {
		return fetch(Appuser.APPUSER.APP_ID, values);
	}

	/**
	 * Fetch records that have <code>fyid IN (values)</code>
	 */
	public List<cn.fyg.service.user.orm.jooq.tables.pojos.Appuser> fetchByFyid(Long... values) {
		return fetch(Appuser.APPUSER.FYID, values);
	}

	/**
	 * Fetch records that have <code>appinterid IN (values)</code>
	 */
	public List<cn.fyg.service.user.orm.jooq.tables.pojos.Appuser> fetchByAppinterid(String... values) {
		return fetch(Appuser.APPUSER.APPINTERID, values);
	}
}
