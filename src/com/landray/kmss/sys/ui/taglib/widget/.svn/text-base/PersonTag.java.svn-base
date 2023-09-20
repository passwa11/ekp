package com.landray.kmss.sys.ui.taglib.widget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.landray.kmss.sys.lbpm.engine.support.def.XMLUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class PersonTag extends BodyTagSupport {
	
	protected static final Logger logger = org.slf4j.LoggerFactory.getLogger(PersonTag.class);
	
	protected String personName;
	protected String loginName;
	protected String personId;
	protected Boolean layer = Boolean.TRUE;
	protected Object bean;

	private ISysOrgCoreService sysOrgCoreService = null;

	private ISysOrgCoreService getSysOrgCoreService() {
		if (sysOrgCoreService == null) {
            sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
                    .getBean("sysOrgCoreService");
        }
		return sysOrgCoreService;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public Boolean getLayer() {
		return layer;
	}

	public void setLayer(Boolean layer) {
		this.layer = layer;
	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			String cxpath = ((HttpServletRequest) pageContext.getRequest())
					.getContextPath();
			StringBuffer sb = new StringBuffer();
			List<Map<String, String>> list = this.parseToOrgList();

			for (int i = 0; i < list.size(); i++) {
				Map<String, String> tmpPerson = list.get(i);
				if (list.size() > 1 && i != list.size() - 1) {
					tmpPerson.put("fdName", tmpPerson.get("fdName") + ";");
				}
				sb.append(render(cxpath, tmpPerson,
						layer != null ? layer.booleanValue() : true));
			}
			pageContext.getOut().print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new KmssRuntimeException(e);
		}
		return SKIP_BODY;
	}

	public static StringBuffer render(String cxpath,
			Map<String, String> tmpPerson, boolean layer) {
		StringBuffer sb = new StringBuffer();
		sb.append("<a class=\"com_author\" "+ " href=\"javascript:void(0) \"");
//		sb.append("<a class=\"com_author\" "
//				+ "href=\""
//				+ cxpath
//				+ "/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId="
//				+ tmpPerson.get("fdId") + "\" target=\"_blank\"");
		if (layer) {
			sb.append(" ajax-href=\""+ cxpath+ "/sys/zone/resource/zoneInfo.jsp?fdId="+ tmpPerson.get("fdId") + "\"");
			
			
			sb.append(" onmouseover=\"if(window.LUI && window.LUI.person)window.LUI.person(event,this);\"");
		}
		sb.append(">");
		// 生僻字需要转回来，否则乱码
		sb.append(XMLUtils
				.unescapeRareWord(org.apache.commons.lang.StringEscapeUtils
						.escapeHtml(tmpPerson.get("fdName"))));
		sb.append("</a>");
		return sb;
	}

	private List<Map<String, String>> parseToOrgList() throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		if (StringUtil.isNotNull(personId)) {
			if (personId.indexOf(";") > -1) {
				String[] persons = personId.split(";");
				String[] names = null;
				if (StringUtil.isNotNull(personName)) {
					names = personName.split(";");
				}
				for (int i = 0; i < persons.length; i++) {
					rtnList.add(parseToMap(persons[i], names == null ? null
							: (names[i] == null ? null : names[i]), loginName));
				}

			} else {
				rtnList.add(parseToMap(personId, personName, loginName));
			}
		} else if (bean != null) {
			Object tmpBean = null;
			if (bean instanceof String) {
				String beanCfg = (String) bean;
				if (beanCfg.indexOf(";") > -1) {
					String[] beans = beanCfg.split(";");
					for (int i = 0; i < beans.length; i++) {
						rtnList.add(parseToMap(pageContext
								.getAttribute(beans[i])));
					}
				} else {
					tmpBean = pageContext.getAttribute(beanCfg);
				}
			} else {
				tmpBean = bean;
			}
			if (tmpBean != null) {
				if (tmpBean instanceof Object[]) {
					Object[] beans = (Object[]) tmpBean;
					for (int i = 0; i < beans.length; i++) {
						rtnList.add(parseToMap(beans[i]));
					}
				} else if (tmpBean instanceof List) {
					List<?> beans = (List<?>) tmpBean;
					for (int i = 0; i < beans.size(); i++) {
						rtnList.add(parseToMap(beans.get(i)));
					}
				} else if (tmpBean instanceof Map) {
					Map beans = (Map) tmpBean;
					for (Iterator iterator = beans.values().iterator(); iterator
							.hasNext();) {
						Object tmpObject = (Object) iterator.next();
						rtnList.add(parseToMap(tmpObject));
					}
				} else {
					rtnList.add(parseToMap(tmpBean));
				}
			}
		}
		return rtnList;
	}

	// 
	private Map<String, String> parseToMap(String fdId, String fdName,
			String fdLoginName) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fdId", fdId);
		if (StringUtil.isNotNull(fdName)) {
			map.put("fdName", fdName);
			map.put("fdLoginName", fdLoginName);
		} else {
			try {
				SysOrgElement element =
						getSysOrgCoreService().findByPrimaryKey(personId);
				map.put("fdName",
						(String) PropertyUtils.getProperty(element, "fdName"));
			} catch (Exception e) {
				map.put("fdName", "");
				logger.error("获取人员名称报错：", e);
			}
			
			/*
			 * map.put("fdLoginName", (String)
			 * PropertyUtils.getProperty(element, "fdLoginName"));
			 */
		}
		return map;
	}

	private Map<String, String> parseToMap(Object beanObj) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		if (beanObj != null) {
			map.put("fdId", (String) PropertyUtils.getProperty(beanObj, "fdId"));
			map.put("fdName",
					(String) PropertyUtils.getProperty(beanObj, "fdName"));
			map.put("fdLoginName",
					(String) PropertyUtils.getProperty(beanObj, "fdLoginName"));
		}
		return map;
	}

	@Override
	public void setValue(String k, Object o) {
		super.setValue(k, o);
	}

}
