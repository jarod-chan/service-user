/**
 * This class is generated by jOOQ
 */
package cn.fyg.service.user.orm.jooq.tables;


import cn.fyg.service.user.orm.jooq.Keys;
import cn.fyg.service.user.orm.jooq.Service_user_dev;
import cn.fyg.service.user.orm.jooq.tables.records.UserRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class User extends TableImpl<UserRecord> {

	private static final long serialVersionUID = -1567674869;

	/**
	 * The reference instance of <code>service-user-dev.user</code>
	 */
	public static final User USER = new User();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<UserRecord> getRecordType() {
		return UserRecord.class;
	}

	/**
	 * The column <code>service-user-dev.user.fyid</code>.
	 */
	public final TableField<UserRecord, Long> FYID = createField("fyid", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>service-user-dev.user.uname</code>.
	 */
	public final TableField<UserRecord, String> UNAME = createField("uname", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false), this, "");

	/**
	 * The column <code>service-user-dev.user.uemail</code>.
	 */
	public final TableField<UserRecord, String> UEMAIL = createField("uemail", org.jooq.impl.SQLDataType.VARCHAR.length(256), this, "");

	/**
	 * The column <code>service-user-dev.user.uphone</code>.
	 */
	public final TableField<UserRecord, String> UPHONE = createField("uphone", org.jooq.impl.SQLDataType.VARCHAR.length(256), this, "");

	/**
	 * The column <code>service-user-dev.user.password</code>.
	 */
	public final TableField<UserRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false), this, "");

	/**
	 * The column <code>service-user-dev.user.realname</code>.
	 */
	public final TableField<UserRecord, String> REALNAME = createField("realname", org.jooq.impl.SQLDataType.VARCHAR.length(256), this, "");

	/**
	 * The column <code>service-user-dev.user.state</code>.
	 */
	public final TableField<UserRecord, String> STATE = createField("state", org.jooq.impl.SQLDataType.VARCHAR.length(20), this, "");

	/**
	 * The column <code>service-user-dev.user.createtime</code>.
	 */
	public final TableField<UserRecord, Long> CREATETIME = createField("createtime", org.jooq.impl.SQLDataType.BIGINT, this, "");

	/**
	 * Create a <code>service-user-dev.user</code> table reference
	 */
	public User() {
		this("user", null);
	}

	/**
	 * Create an aliased <code>service-user-dev.user</code> table reference
	 */
	public User(String alias) {
		this(alias, USER);
	}

	private User(String alias, Table<UserRecord> aliased) {
		this(alias, aliased, null);
	}

	private User(String alias, Table<UserRecord> aliased, Field<?>[] parameters) {
		super(alias, Service_user_dev.SERVICE_USER_DEV, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<UserRecord, Long> getIdentity() {
		return Keys.IDENTITY_USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<UserRecord> getPrimaryKey() {
		return Keys.KEY_USER_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<UserRecord>> getKeys() {
		return Arrays.<UniqueKey<UserRecord>>asList(Keys.KEY_USER_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User as(String alias) {
		return new User(alias, this);
	}

	/**
	 * Rename this table
	 */
	public User rename(String name) {
		return new User(name, null);
	}
}
