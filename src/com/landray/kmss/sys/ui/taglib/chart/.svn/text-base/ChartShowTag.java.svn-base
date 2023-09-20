package com.landray.kmss.sys.ui.taglib.chart;

import java.util.List;

import org.hibernate.query.NativeQuery;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ChartShowTag extends WidgetTag {
	private static final long serialVersionUID = -6787952146922998056L;

	private String chartType = "chart";

	protected String width;

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	protected String height;
	protected String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	protected String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected String module;

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Override
	protected String acquireString(String body) throws Exception {
		StringBuffer resultSB = new StringBuffer();
		String chartId = getChartId();
		if (StringUtil.isNotNull(chartId)) {
			resultSB
					.append("<div data-lui-type=\"lui/base!DataView\" style=\"display: none;\">\n");
			resultSB.append("\t<script type=\"text/config\">\n").append(
					"\t\t{\"format\": \"sys.ui.iframe\"}\n").append(
					"\t</script>\n");
			resultSB
					.append("\t<div data-lui-type=\"lui/data/source!Static\" style=\"display: none;\">\n");
			resultSB.append("\t\t<script type='text/code'>\n");
			if ("chart".equals(chartType)) {
				resultSB
						.append("\t\t\t{\"src\":\"/dbcenter/echarts/db_echarts_chart/dbEchartsChart.do?method=view&fdId="
								+ chartId
								+ "&showButton=0&LUIID=!{lui.element.id}\"}\n");
			}
			if ("chart-set".equals(chartType)) {
				resultSB
						.append("\t\t\t{\"src\":\"/dbcenter/echarts/db_echarts_chart_set/dbEchartsChartSet.do?method=view&fdId="
								+ chartId
								+ "&showButton=0&LUIID=!{lui.element.id}\"}\n");
			}
			resultSB.append("\t\t</script>\n").append("\t</div>\n");
			resultSB
					.append("\t<div data-lui-type=\"lui/view/render!Javascript\" style=\"display: none;\">\n");
			resultSB
					.append("\t\t<script type='text/code' xsrc='/sys/ui/extend/dataview/render/iframe.js?s_cache="
							+ pageContext.getRequest()
									.getAttribute("LUI_Cache") + "'>\n");
			resultSB.append("\t\t</script>\n").append("\t</div>\n").append(
					"</div>\n");
		}
		return resultSB.toString();
	}

	@SuppressWarnings("unchecked")
	private String getChartId() {
		String chartId = null;
		try {
			IBaseDao baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
			NativeQuery sqlQuery = baseDao.getHibernateSession().createNativeQuery(
					"select fd_id from db_echarts_chart where show_module='"
							+ module + "' and show_name='" + name + "'");
			List<String> result = sqlQuery.list();
			if (result != null && result.size() > 0) {
				chartId = result.get(0).toString();
				chartType = "chart";
			} else {
				sqlQuery = baseDao.getHibernateSession().createNativeQuery(
						"select fd_id from db_echarts_chart_set where show_module='"
								+ module + "' and show_name='" + name + "'");
				result = sqlQuery.list();
				if (result != null && result.size() > 0) {
					chartId = result.get(0).toString();
					chartType = "chart-set";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chartId;
	}

	@Override
    public void release() {
		this.width = null;
		this.height = null;
		this.className = null;
		this.module = null;
		this.name = null;
		super.release();
	}

}
