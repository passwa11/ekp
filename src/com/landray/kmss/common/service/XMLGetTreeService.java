package com.landray.kmss.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 用于构造树的XML的通用Service
 * 
 * @author 叶中奇
 */
public class XMLGetTreeService
		implements IXMLDataBean, ApplicationContextAware {
	ApplicationContext applicationContext;

	@Override
    public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
    public List getDataList(RequestContext xmlContext) throws Exception {
		String para = xmlContext.getParameter("service");
		IBaseService baseService = (IBaseService) applicationContext
				.getBean(para);
		String tableName = baseService.getModelName();

		String localeCountry = null;
		SysDictModel dictModel = null;
		if (SysLangUtil.isLangEnabled()) {
			dictModel = SysDataDict.getInstance()
					.getModel(tableName);
			if (dictModel != null && dictModel.isLangSupport()) {
				localeCountry = SysLangUtil.getCurrentLocaleCountry();
			}
		}

		tableName = ModelUtil.getModelTableName(tableName) + ".";
		para = xmlContext.getParameter("parent");
		String whereBlock = tableName + para;
		para = xmlContext.getParameter("fdId");
		if (StringUtil.isNull(para)) {
			whereBlock += "=null";
		} else {
			whereBlock += ".fdId='" + para + "'";
		}
		para = xmlContext.getParameter("item");
		String[] itemList = para.split(":");
		String selectBlock = "";
		String selectBlock_lang = "";
		List<Integer> langSupportFieldPoss = new ArrayList<Integer>();
		for (int i = 0; i < itemList.length; i++) {
			selectBlock += "," + tableName + itemList[i];
			if (StringUtil.isNotNull(localeCountry)
					&& dictModel.isLangSupport(itemList[i])) {
				selectBlock_lang += "," + tableName + itemList[i]
						+ localeCountry;
				langSupportFieldPoss.add(i);
			}
		}
		selectBlock = (selectBlock + selectBlock_lang).substring(1);
		para = xmlContext.getParameter("orderby");
		String orderBy = null;
		if (!StringUtil.isNull(para)) {
			String[] orderbyList = para.split(":");
			orderBy = "";
			for (int i = 0; i < orderbyList.length; i++) {
				orderBy += "," + tableName + orderbyList[i];
			}
			orderBy = orderBy.substring(1);
		}
		List result = baseService.findValue(selectBlock, whereBlock, orderBy);
		if (!langSupportFieldPoss.isEmpty()) {
			for (int i = 0; i < result.size(); i++) {
				Object[] row = (Object[]) result.get(i);
				for (int pos : langSupportFieldPoss) {
					String value = (String) row[pos + itemList.length];
					if (StringUtil.isNotNull(value)) {
						row[pos] = value;
					}
				}
			}
		}
		return result;
	}

}
