package com.landray.kmss.common.dao;

import org.springframework.jdbc.support.lob.DefaultLobHandler;

public class OracleLobHandler extends DefaultLobHandler {
	private String hibernateDialect;

	public String getHibernateDialect() {
		return hibernateDialect;
	}

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}

	public boolean isOracle() {
		return hibernateDialect != null && ("org.hibernate.dialect.Oracle9Dialect".equals(hibernateDialect)
				|| "org.hibernate.dialect.Oracle9iDialect".equals(hibernateDialect));
	}
}
