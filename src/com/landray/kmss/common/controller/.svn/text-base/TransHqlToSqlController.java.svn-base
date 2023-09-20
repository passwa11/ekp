package com.landray.kmss.common.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.landray.kmss.sys.hibernate.spi.HQLConverTool;
import com.landray.kmss.sys.hibernate.spi.KmssSessionFactoryProxy;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.web.RestResponse;

@RestController
@RequestMapping(value = "/trans")
public class TransHqlToSqlController {

	@RequestMapping(value = "/to_sql", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public RestResponse<List> trans(HttpServletRequest request, String hql) {
		HQLConverTool util = HQLConverTool.instance();
		List list = null;
		String sql = "";
		if (StringUtils.isNotBlank(hql)) {
			hql = util.genSecondHql(hql);
			KmssSessionFactoryProxy kmssSessionFactoryProxy = (KmssSessionFactoryProxy) SpringBeanUtil
					.getApplicationContext().getBean("sessionFactory");
			// 得到Query转换器实现类
			QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(hql, hql, Collections.EMPTY_MAP,
					kmssSessionFactoryProxy);
			queryTranslator.compile(Collections.EMPTY_MAP, false);
			// 得到sql
			sql = queryTranslator.getSQLString();
		}

		return RestResponse.ok(list, "HQL做兼容转换后：["+hql+"]，转换出的SQL:[" + sql+"]");
	}
}
