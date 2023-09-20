/**
 * 配置样例
	<bean
		id="nativeJdbcExtractor"
		class="org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor" />
	
	<bean
		id="oracleLobHandler"
		class="com.landray.kmss.common.dao.OracleLobHandler"
		lazy-init="true">

		<property name="nativeJdbcExtractor">

			<ref bean="nativeJdbcExtractor" />

		</property>
		<property name="hibernateDialect">

			<value>${hibernate.dialect}</value>

		</property>

	</bean>
	
	<bean
		id="sessionFactory"
		class="com.landray.kmss.sys.config.loader.KmssHibernateLocalSessionFactoryBean">
		<property name="hibernateProperties">
			<props>
				<prop key="hibernate.dialect">
					${hibernate.dialect}
				</prop>
			</props>
		</property>
		<property name="dataSource">
			<ref bean="dataSource" />
		</property>
		
		<property name="lobHandler">

			<ref bean="oracleLobHandler" />

		</property>
		 
	</bean>
 */
package com.landray.kmss.common.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.transaction.TransactionManager;

import org.apache.poi.util.IOUtils;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
/**
 * 
 * @author Administrator
 *
 */
public class ClobStringType extends AbstractLobType implements UserType {
	public ClobStringType() {
		super();
	}

	/**
	 * Constructor used for testing: takes an explicit LobHandler
	 * and an explicit JTA TransactionManager (can be <code>null</code>).
	 */
	protected ClobStringType(LobHandler lobHandler, Transaction transaction) {
		super(lobHandler, transaction);
	}

	@Override
    public int[] sqlTypes() {
		return new int[] {Types.CLOB};
	}

	@Override
    public Class returnedClass() {
		return String.class;
	}

	@Override
    protected Object nullSafeGetInternal(
			ResultSet rs, String[] names, Object owner, LobHandler lobHandler)
			throws SQLException {
		try {
			return lobHandler.getClobAsString(rs, names[0]);
		} catch (Exception e) {
			// 这里隐藏了一个坑，子类继承父类（类似于SysOrgPerson继承SysOrgElement），
			// 刚好父类有大字段（Clob），hibernate查询父类时走到这里，会将父类大字段的别名去子表中取数据，
			// 因为子表没有这个字段，所以会出现类似异常：Column 'XXXX_XXXX_' not found.
			// 如果抛出这个异常，将会导致程序出错而无法执行
			// 这里做一个简单粗爆的处理，如果出现找不到字段的异常，不抛异常，只返回NULL
			return null;
		}
	}

	@Override
    protected void nullSafeSetInternal(
			PreparedStatement ps, int index, Object value, LobCreator lobCreator)
			throws SQLException {
		lobCreator.setClobAsString(ps, index, (String) value);
	}

	@Override
    protected Object nullSafeGetDefault(ResultSet rs, String[] names, Object owner) throws SQLException, IOException, HibernateException {
		try {
			Object value = get(rs,names[0]);
			if ( value == null || rs.wasNull() ) {
				return null;
			}
			else {

				return value;
			}
		}
		catch ( RuntimeException re ) {
			throw re;
		}
		catch ( SQLException se ) {
			throw se;
		}

	}

	@Override
    protected void nullSafeSetDefault(PreparedStatement ps, int index, Object value) throws SQLException, IOException, HibernateException {
		try {
			if ( value == null ) {

				ps.setNull( index, sqlType() );
			}
			else {

				set( ps, value, index );
			}
		}
		catch ( RuntimeException re ) {
			throw re;
		}
		catch ( SQLException se ) {
			throw se;
		}
		
	}
	public int sqlType() {
		return Types.CLOB; //or Types.LONGVARCHAR?
	}
	public void set(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		String str = (String) value;
		st.setCharacterStream( index, new StringReader(str), str.length() );
	}

	public Object get(ResultSet rs, String name) throws HibernateException, SQLException {

			// Retrieve the value of the designated column in the current row of this
			// ResultSet object as a java.io.Reader object
			Reader charReader = null;
			try {
				charReader = rs.getCharacterStream(name);
			} catch (Exception e) {
				// 这里隐藏了一个坑，子类继承父类（类似于SysOrgPerson继承SysOrgElement），
				// 刚好父类有大字段（Clob），hibernate查询父类时走到这里，会将父类大字段的别名去子表中取数据，
				// 因为子表没有这个字段，所以会出现类似异常：Column 'XXXX_XXXX_' not found.
				// 如果抛出这个异常，将会导致程序出错而无法执行
				// 这里做一个简单粗爆的处理，如果出现找不到字段的异常，不抛异常，只返回NULL
			}

			// if the corresponding SQL value is NULL, the reader we got is NULL as well
			if (charReader==null) {
				return null;
			}

			// Fetch Reader content up to the end - and put characters in a StringBuffer
			StringBuffer sb = new StringBuffer();
			try {
				char[] buffer = new char[2048];
				while (true) {
					int amountRead = charReader.read(buffer, 0, buffer.length);
					if ( amountRead == -1 ) {
						break;
					}
					sb.append(buffer, 0, amountRead);
				}
			}
			catch (IOException ioe) {
				throw new HibernateException( "IOException occurred reading text", ioe );
			}
			finally {
				IOUtils.closeQuietly(charReader);
			}

			// Return StringBuffer content as a large String
			return sb.toString();
	}
}
