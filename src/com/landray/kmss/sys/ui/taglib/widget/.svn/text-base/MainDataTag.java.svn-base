package com.landray.kmss.sys.ui.taglib.widget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.sys.xform.maindata.util.MainDataUtil;
import com.landray.kmss.util.StringUtil;

public class MainDataTag extends BodyTagSupport {
	protected String mainDataId;

	public String getMainDataId() {
		return mainDataId;
	}

	public void setMainDataId(String mainDataId) {
		this.mainDataId = mainDataId;
	}

	public String getMainDataModelName() {
		return mainDataModelName;
	}

	public void setMainDataModelName(String mainDataModelName) {
		this.mainDataModelName = mainDataModelName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	protected String mainDataModelName;

	protected String title;

	protected Boolean layer = Boolean.TRUE;



	public Boolean getLayer() {
		return layer;
	}

	public void setLayer(Boolean layer) {
		this.layer = layer;
	}


	@Override
    public int doStartTag() throws JspException {
		try {
			String cxpath = ((HttpServletRequest) pageContext.getRequest())
					.getContextPath();
			StringBuffer sb = new StringBuffer();
			// List<Map<String, String>> list = this.parseToOrgList();
			if (title == null) {
				try {
					title = MainDataUtil.getMainDataTitle(mainDataId, mainDataModelName);
				} catch (Exception e) {
					if ("maindata not exist".equals(e.getMessage())) {
						sb.append(render(cxpath, mainDataId, mainDataModelName,
								"maindata not exist",
								layer != null ? layer.booleanValue() : false));

						pageContext.getOut().print(sb.toString());
						return SKIP_BODY;
					} else {
						throw e;
					}
				}
			}
			sb.append(render(cxpath, mainDataId, mainDataModelName, title,
						layer != null ? layer.booleanValue() : true));

			pageContext.getOut().print(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			throw new KmssRuntimeException(e);
		}
		return SKIP_BODY;
	}

	public static StringBuffer render(String cxpath,
			String mainDataId, String mainDataModelName, String title,
			boolean layer) {
		StringBuffer sb = new StringBuffer();


//		sb.append("<a class=\"com_author\" "
//				+ "href=\""
//				+ cxpath
//				+ "/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId="
//				+ tmpPerson.get("fdId") + "\" target=\"_blank\"");
		if (layer) {
			sb.append(
					"<a class=\"com_btn_link\" "
							+ " href=\"javascript:void(0) \"");
			sb.append(" ajax-href=\"" + cxpath
					+ "/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=cardInfo&fdId="
					+ mainDataId + "&modelName=" + mainDataModelName
					+ "\"");
			sb.append(
					" onmouseover=\"if(window.LUI && window.LUI.maindata)window.LUI.maindata(event,this);\"");
			sb.append(">");
			sb.append(title);
			sb.append("</a>");
		} else {
			sb.append(
					"<a href=\"" + cxpath
							+ "/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=show&fdId="
							+ mainDataId + "&modelName=" + mainDataModelName
							+ "\" " + "target=\"_blank\">" + title + "</a>");
		}

		return sb;
	}

	private List<Map<String, String>> parseToOrgList() throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		if (StringUtil.isNotNull(mainDataId)) {
			// if (mainDataId.indexOf(";") > -1) {
			// String[] mainDataIds = mainDataId.split(";");
			// String[] mainDataModelNames = null;
			// if (StringUtil.isNotNull(mainDataModelName)) {
			// mainDataModelNames = mainDataModelName.split(";");
			// }
			// for (int i = 0; i < mainDataIds.length; i++) {
			//
			// rtnList.add(parseToMap(mainDataIds[i],
			// mainDataModelNames == null ? null
			// : (mainDataModelNames[i] == null ? null
			// : mainDataModelNames[i])));
			// }
			//
			// } else {
				rtnList.add(parseToMap(mainDataId, mainDataModelName));
			// }

		}
		return rtnList;
	}

	private Map<String, String> parseToMap(String fdId, String fdModelName)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("fdId", fdId);
		if (StringUtil.isNotNull(fdModelName)) {
			map.put("fdName", fdModelName);
		}
		return map;
	}


	@Override
    public void setValue(String k, Object o) {
		super.setValue(k, o);
	}

}
