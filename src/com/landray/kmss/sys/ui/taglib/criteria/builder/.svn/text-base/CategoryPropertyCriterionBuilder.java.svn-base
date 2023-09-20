/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.hibernate.Session;

import com.landray.kmss.sys.category.service.ISysCategoryPropertyService;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictModelProperty;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * @author 傅游翔
 * 
 */
public class CategoryPropertyCriterionBuilder extends ImportRefCriterionBuilder
		implements CriterionBuilder {

	@Override
    protected String getRefId() {
		return "criterion.sys.property";
	}

	@Override
    public boolean support(SysDictCommonProperty property) {
		if (property instanceof SysDictListProperty
				|| property instanceof SysDictModelProperty) {
			String type = property.getType();
			if ("com.landray.kmss.sys.category.model.SysCategoryProperty"
					.equals(type)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String build(SysDictModel model, SysDictCommonProperty property,
			PageContext pageContext, Map<String, Object> attrs)
			throws Exception {
		ISysCategoryPropertyService sysCategoryPropertyService = ((ISysCategoryPropertyService) SpringBeanUtil
				.getBean("sysCategoryPropertyService"));
		Session session = sysCategoryPropertyService.getBaseDao()
				.getHibernateSession();
		// 查询需要同步的数据
		String selectSql = "select fd_name, fd_id from sys_category_property where fd_parent_id is null";
		List<String> list = session.createNativeQuery(selectSql).list();
		if (list.size() > 0) {

			Map<String, Object> newAttrs = getConfigMap(property, attrs);
			Map<String, Object> params = getParamMap(property);
			return importRef(getRefId(), newAttrs, params, pageContext);

		} else {
			return "false";
		}
	}

}
