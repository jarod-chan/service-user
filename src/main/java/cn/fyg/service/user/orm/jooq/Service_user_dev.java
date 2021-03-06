/**
 * This class is generated by jOOQ
 */
package cn.fyg.service.user.orm.jooq;


import cn.fyg.service.user.orm.jooq.tables.App;
import cn.fyg.service.user.orm.jooq.tables.Appuser;
import cn.fyg.service.user.orm.jooq.tables.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Service_user_dev extends SchemaImpl {

	private static final long serialVersionUID = 518772429;

	/**
	 * The reference instance of <code>service-user-dev</code>
	 */
	public static final Service_user_dev SERVICE_USER_DEV = new Service_user_dev();

	/**
	 * No further instances allowed
	 */
	private Service_user_dev() {
		super("service-user-dev");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			App.APP,
			Appuser.APPUSER,
			User.USER);
	}
}
