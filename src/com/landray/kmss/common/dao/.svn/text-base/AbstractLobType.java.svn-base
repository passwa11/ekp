package com.landray.kmss.common.dao;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.transaction.Status;
import javax.transaction.Synchronization;
import javax.transaction.TransactionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.usertype.UserType;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.landray.kmss.sys.config.loader.KmssHibernateLocalSessionFactoryBean;
import com.landray.kmss.util.SpringBeanUtil;

public abstract class AbstractLobType implements UserType {
	
	/**
	 * Order value for TransactionSynchronization objects that clean up LobCreators.
	 * Return SessionFactoryUtils.SESSION_SYNCHRONIZATION_ORDER - 100 to execute
	 * LobCreator cleanup before Hibernate Session and JDBC Connection cleanup, if any.
	 * @see org.springframework.orm.hibernate3.SessionFactoryUtils#SESSION_SYNCHRONIZATION_ORDER
	 */
	public static final int LOB_CREATOR_SYNCHRONIZATION_ORDER =
			SessionFactoryUtils.SESSION_SYNCHRONIZATION_ORDER - 100;


	protected final Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	protected  LobHandler lobHandler;

	private Transaction transaction;
	
	/**
	 * Constructor used by Hibernate: fetches config-time LobHandler and
	 * config-time JTA TransactionManager from LocalSessionFactoryBean.
	 * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean#getConfigTimeLobHandler
	 * @see org.springframework.orm.hibernate3.LocalSessionFactoryBean#getConfigTimeTransactionManager
	 */
	protected AbstractLobType() {
		// 下面这样写回有问题，oracleLobHandler，sessionFactory 还没初始化完
		//this((LobHandler) SpringBeanUtil.getBean("oracleLobHandler"),
				//((KmssHibernateLocalSessionFactoryBean) SpringBeanUtil.getBean("&sessionFactory")).getObject().getCurrentSession().getTransaction());
	}

	/**
	 * Constructor used for testing: takes an explicit LobHandler
	 * and an explicit JTA TransactionManager (can be <code>null</code>).
	 */
	protected AbstractLobType(LobHandler lobHandler, Transaction transaction) {
		this.lobHandler = lobHandler;
		this.transaction = transaction;
	}
	
	/**
	 * Template method to extract a value from the given result set.
	 * @param rs the ResultSet to extract from
	 * @param names the column names
	 * @param owner the containing entity
	 * @param lobHandler the LobHandler to use
	 * @return the extracted value
	 * @throws SQLException if thrown by JDBC methods
	 * @throws IOException if thrown by streaming methods
	 * @throws HibernateException in case of any other exceptions
	 */
	protected abstract Object nullSafeGetInternal(
			ResultSet rs, String[] names, Object owner, LobHandler lobHandler)
			throws SQLException, IOException, HibernateException;
	
	protected abstract Object nullSafeGetDefault(
			ResultSet rs, String[] names, Object owner)
			throws SQLException, IOException, HibernateException;

	/**
	 * Template method to set the given parameter value on the given statement.
	 * @param ps the PreparedStatement to set on
	 * @param index the statement parameter index
	 * @param value the value to set
	 * @param lobCreator the LobCreator to use
	 * @throws SQLException if thrown by JDBC methods
	 * @throws IOException if thrown by streaming methods
	 * @throws HibernateException in case of any other exceptions
	 */
	protected abstract void nullSafeSetInternal(
	    PreparedStatement ps, int index, Object value, LobCreator lobCreator)
			throws SQLException, IOException, HibernateException;
	
	protected abstract void nullSafeSetDefault(
		    PreparedStatement ps, int index, Object value)
				throws SQLException, IOException, HibernateException;
	
	 /**
	  * 返回UserType所映射字段的SQL类型（java.sql.Types) 返回类型为int[]，其中包含了映射个字段的SQL类型代码
	  * (UserType可以映射到一个或者多个字段)
	  */
	@Override
	public abstract  int[] sqlTypes();

	@Override
	public abstract Class returnedClass();
		

	
	/**
	  * 自定义数据类型的比对方法 此方法将用作脏数据检查，参数x、y分别为数据的两个副本
	  * 如果equals方法返回false,则Hibernate将认为数据发生变化,并将变化更新到数据库表中
	  */
	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		// return EqualsHelper.equals(x, y); Hibernate 这个类已经弃用

		if ( x == y ) {
			return true;
		}

		if ( x == null || y == null ) {
			// One is null, but the other is not (otherwise the `x == y` check would have passed).
			// null can never equal a non-null
			return false;
		}

		return x.equals( y );
	}

	
	/**
	 * This implementation returns the hashCode of the given objectz.
	 */
	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	 /**
	  * 从JDBC ResultSet读取数据,将其转换为自定义类型后返回 (此方法要求对可能出现null值进行处理)
	  * names中包含了当前自定义类型的映射字段名称
	  */
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner)
			throws HibernateException, SQLException {
		try {
			
			this.lobHandler = (LobHandler) SpringBeanUtil.getBean("oracleLobHandler");

			if (this.lobHandler == null || !(lobHandler instanceof OracleLobHandler && ((OracleLobHandler)lobHandler).isOracle())) {
				return nullSafeGetDefault(rs, names, owner);
			}

			return nullSafeGetInternal(rs, names, owner, this.lobHandler);
		}
		catch (IOException ex) {
			throw new HibernateException("I/O errors during LOB access", ex);
		}
	}
	
	
	/**
	  * 本方法将在Hibernate进行数据保存时被调用 我们可以通过PreparedStateme将自定义数据写入到对应的数据库表字段
	  * 
	  */
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		
		this.lobHandler = (LobHandler) SpringBeanUtil.getBean("oracleLobHandler");
		
		if (this.lobHandler == null || !(lobHandler instanceof OracleLobHandler && ((OracleLobHandler)lobHandler).isOracle())) {
			try {
				nullSafeSetDefault(st, index, value);
			} catch (IOException ex) {
				throw new HibernateException("I/O errors during LOB access", ex);
				
			}
			
		}else {

			LobCreator lobCreator = this.lobHandler.getLobCreator();
			try {
				nullSafeSetInternal(st, index, value, lobCreator);
			}
			catch (IOException ex) {
				throw new HibernateException("I/O errors during LOB access", ex);
			}
			
	
			if (TransactionSynchronizationManager.isSynchronizationActive()) {
				logger.debug("Registering Spring transaction synchronization for Hibernate LOB type");
				TransactionSynchronizationManager.registerSynchronization(
				    new SpringLobCreatorSynchronization(lobCreator));
			}
	
			else {
				
				this.transaction = ((KmssHibernateLocalSessionFactoryBean) SpringBeanUtil.getBean("&sessionFactory")).getObject().getCurrentSession().getTransaction();
				if (this.transaction != null) {
					try {
						TransactionStatus jtaStatus = transaction.getStatus();
						if (jtaStatus.equals(TransactionStatus.ACTIVE) || jtaStatus.equals(TransactionStatus.MARKED_ROLLBACK)) {
							logger.debug("Registering JTA transaction synchronization for Hibernate LOB type");
							this.transaction.registerSynchronization(
									new JtaLobCreatorSynchronization(lobCreator));
							return;
						}
					}
					catch (Exception ex) {
						throw new DataAccessResourceFailureException(
								"Could not register synchronization with JTA TransactionManager", ex);
					}
				}
				throw new IllegalStateException("Active Spring transaction synchronization or active " +
				    "JTA transaction with 'jtaTransactionManager' on LocalSessionFactoryBean required");
			}
		}
	}


	 /**
	  * 提供自定义类型的完全复制方法 本方法将用构造返回对象 当nullSafeGet方法调用之后，我们获得了自定义数据对象，在向用户返回自定义数据之前，
	  * deepCopy方法将被调用，它将根据自定义数据对象构造一个完全拷贝，并将此拷贝返回给用户
	  * 此时我们就得到了自定义数据对象的两个版本，第一个是从数据库读出的原始版本，其二是我们通过
	  * deepCopy方法构造的复制版本，原始的版本将有Hibernate维护，复制版由用户使用。原始版本用作
	  * 稍后的脏数据检查依据；Hibernate将在脏数据检查过程中将两个版本的数据进行对比（通过调用
	  * equals方法），如果数据发生了变化（equals方法返回false），则执行对应的持久化操作
	  * 
	  */
	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}
	
	/**
	  * 本类型实例是否可变
	  */
	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	/**
	 * Callback for resource cleanup at the end of a Spring transaction.
	 * Invokes LobCreator.close to clean up temporary LOBs that might have been created.
	 * @see org.springframework.jdbc.support.lob.LobCreator#close
	 */
	private static class SpringLobCreatorSynchronization extends TransactionSynchronizationAdapter {

		private final LobCreator lobCreator;

		private boolean beforeCompletionCalled = false;

		public SpringLobCreatorSynchronization(LobCreator lobCreator) {
			this.lobCreator = lobCreator;
		}

		@Override
		public int getOrder() {
			return LOB_CREATOR_SYNCHRONIZATION_ORDER;
		}

		@Override
		public void beforeCompletion() {
			// Close the LobCreator early if possible, to avoid issues with strict JTA
			// implementations that issue warnings when doing JDBC operations after
			// transaction completion.
			this.beforeCompletionCalled = true;
			this.lobCreator.close();
		}

		@Override
		public void afterCompletion(int status) {
			if (!this.beforeCompletionCalled) {
				// beforeCompletion not called before (probably because of flushing on commit
				// in HibernateTransactionManager, after the chain of beforeCompletion calls).
				// Close the LobCreator here.
				this.lobCreator.close();
			}
		}
	}


	/**
	 * Callback for resource cleanup at the end of a JTA transaction.
	 * Invokes LobCreator.close to clean up temporary LOBs that might have been created.
	 * @see org.springframework.jdbc.support.lob.LobCreator#close
	 */
	private static class JtaLobCreatorSynchronization implements Synchronization {

		private final LobCreator lobCreator;

		private boolean beforeCompletionCalled = false;

		public JtaLobCreatorSynchronization(LobCreator lobCreator) {
			this.lobCreator = lobCreator;
		}

		@Override
		public void beforeCompletion() {
			// Close the LobCreator early if possible, to avoid issues with strict JTA
			// implementations that issue warnings when doing JDBC operations after
			// transaction completion.
			this.beforeCompletionCalled = true;
			this.lobCreator.close();
		}

		@Override
		public void afterCompletion(int status) {
			if (!this.beforeCompletionCalled) {
				// beforeCompletion not called before (probably because of JTA rollback).
				// Close the LobCreator here.
				this.lobCreator.close();
			}
		}
	}

	
}
