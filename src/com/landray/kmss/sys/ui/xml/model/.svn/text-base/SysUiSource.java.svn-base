package com.landray.kmss.sys.ui.xml.model;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.ForSystemType;
import com.landray.kmss.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class SysUiSource extends SysUiXmlBase {
	public SysUiSource() {

	}
	public SysUiSource(String id, String name, String format, SysUiCode body,
			String type, String attribute, String thumb, String help,
			String[] formats, String _for) {
		this(id, name, format, body, type, attribute, thumb, help, formats,
				_for, null, null, null, null, null, null);
	}

	public SysUiSource(String id, String name, String format, SysUiCode body,
			String type, String attribute, String thumb, String help,
			String[] formats, String _for, String forSystem, String varExtend,
			String portletId, String example, String anonymous, String dataFields) {
		this.fdId = id;
		this.fdName = name;
		this.fdFormat = format;
		this.fdType = type;
		this.fdBody = body;
		this.fdAttribute = attribute;
		this.fdHelp = help;
		this.fdThumb = thumb;
		this.fdFormats = formats;
		this.fdFor = _for;
		if (StringUtil.isNull(forSystem)) {
			forSystem = "ekp";
		}
		this.fdForSystem = ForSystemType.valueOf(forSystem);
		this.fdVarExtend = varExtend;
		if (StringUtil.isNotNull(this.fdVarExtend)) {
			SysUiSource source = SysUiPluginUtil
					.getSourceById(this.fdVarExtend);
			if (source != null) {
				// 这里是初始化，所以采用这种新增var方式
				getFdVars().addAll(source.getFdVars());
			}
		}
		this.fdPortletId = portletId;
		this.example = example;
		this.fdAnonymous = "true".equals(anonymous);
		this.dataFields = dataFields;
	}

	protected String[] fdFormats;
	protected String fdFormat;
	protected SysUiCode fdBody;
	protected String fdType;
	protected String fdFor;
	/**
	 * 是否支持匿名
	 */
	protected Boolean fdAnonymous;

	public Boolean getFdAnonymous() {
		return fdAnonymous;
	}

	public void setFdAnonymous(Boolean fdAnonymous) {
		this.fdAnonymous = fdAnonymous;
	}

	/**
	 * 所属系统
	 */
	protected ForSystemType fdForSystem;
	/**
	 * var参数继承自另一个数据源的ID
	 */
	protected String fdVarExtend;
	/**
	 * 所属门户部件ID
	 */
	protected String fdPortletId;
	/**
	 * 样例数据
	 */
	protected String example;
	/**
	 * 可配置的字段分组（用于MK接口），数据里的标题text和跳转链接href，不在其中，因为是必显项
	 *
	 * 转换后的样例：
	 * <pre>
	 * {
	 *   "base": {
	 *     "label": "基础信息",
	 *     "items": [
	 *       {"key": "image", "text": "图片"},
	 *       {"key": "desc", "text": "摘要"},
	 *       {"key": "statusInfo", "text": "状态"}
	 *     ]
	 *   },
	 *   "info": {
	 *     "label": "辅助信息",
	 *     "items": [
	 *       {"key": "created", "text": "创建时间", "type": "timestamp"},
	 *       {"key": "creator", "text": "作者", "type": "person"},
	 *       {"key": "cateName", "text": "分类", "type": "string"},
	 *       {"key": "pageView", "text": "浏览数", "type": "string"},
	 *       {"key": "review", "text": "评论数", "type": "string"}
	 *     ]
	 *   },
	 *   "tag": {
	 *     "label": "标签",
	 *     "items": [
	 *       {"key": "newIcon", "text": "最新", "type": "icon"},
	 *       {"key": "hotIcon", "text": "最热", "type": "icon"},
	 *       {"key": "icon", "text": "最热", "type": "icon"}
	 *     ]
	 *   }
	 * }
	 * </pre>
	 */
	protected String dataFields;

	public String getFdPortletId() {
		return fdPortletId;
	}

	public void setFdPortletId(String fdPortletId) {
		this.fdPortletId = fdPortletId;
	}
	public String getFdVarExtend() {
		return fdVarExtend;
	}

	public void setFdVarExtend(String fdVarExtend) {
		this.fdVarExtend = fdVarExtend;
	}
	public ForSystemType getFdForSystem() {
		return fdForSystem;
	}

	public void setFdForSystem(ForSystemType fdForSystem) {
		this.fdForSystem = fdForSystem;
	}

	/**
	 * 变量赋值的配置页面URL
	 */
	protected String fdAttribute;

	public String getFdFor() {
		return fdFor;
	}

	public void setFdFor(String fdFor) {
		this.fdFor = fdFor;
	}

	public String[] getFdFormats() {
		return fdFormats;
	}

	public void setFdFormats(String[] fdFormats) {
		this.fdFormats = fdFormats;
	}

	protected List<SysUiVar> fdVars;

	/**
	 * 这里是获取方法，新增var请使用{@code addVar}或{@code addAllVar}
	 * 
	 * @return
	 */
	public List<SysUiVar> getFdVars() {
		if (this.fdVars == null) {
			this.fdVars = new ArrayList<SysUiVar>();
		}
		return fdVars;
	}

	/**
	 * 新增var，相同key会覆盖原有var
	 * 
	 * @param var
	 */
	public void addVar(SysUiVar var) {
		if (this.fdVars == null) {
			this.fdVars = new ArrayList<SysUiVar>();
		}
		if (this.fdVars.contains(var)) {
			this.fdVars.remove(var);
		}
		this.fdVars.add(var);
	}

	public void addAllVar(List<SysUiVar> fdVars) {
		for (SysUiVar var : fdVars) {
			addVar(var);
		}
	}

	public void setFdVars(List<SysUiVar> fdVars) {
		this.fdVars = fdVars;
	}

	public String getFdAttribute() {
		return fdAttribute;
	}

	public void setFdAttribute(String fdAttribute) {
		this.fdAttribute = fdAttribute;
	}

	public String getFdFormat() {
		return fdFormat;
	}

	public void setFdFormat(String fdFormat) {
		this.fdFormat = fdFormat;
	}

	public SysUiCode getFdBody() {
		return fdBody;
	}

	public void setFdBody(SysUiCode fdBody) {
		this.fdBody = fdBody;
	}

	public String getFdType() {
		return fdType;
	}

	public void setFdType(String fdType) {
		this.fdType = fdType;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getDataFields() {
		return dataFields;
	}

	public void setDataFields(String dataFields) {
		this.dataFields = dataFields;
	}
}
