/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.ui.taglib.criteria.builder.CriterionBuilder;
import com.landray.kmss.sys.ui.taglib.criteria.builder.CriterionBuilderFactory;
import com.landray.kmss.sys.ui.taglib.widget.WidgetTag;
import com.landray.kmss.sys.xform.base.model.BaseFormTemplate;
import com.landray.kmss.sys.xform.base.model.SysFormDbTable;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.oracle.StringUtil;

/**
 * @author 傅游翔
 * 
 */
@SuppressWarnings("serial")
public class AutoCriteriaTag extends WidgetTag {

	protected CriterionRegister register;

	protected String modelName;

	protected String title;

	protected String label; // 存储自定义表单的字段名

	protected boolean isXForm = false; // 是否是自定义表单

	public String getModelName() {
		return modelName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	protected String property;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	protected Boolean expand;
	
	protected Boolean multi;

	public Boolean getExpand() {
		return expand;
	}

	public void setExpand(Boolean expand) {
		this.expand = expand;
	}

	public Boolean getMulti() {
		return multi;
	}

	public void setMulti(Boolean multi) {
		this.multi = multi;
	}

	@Override
	public int doStartTag() throws JspException {
		register = (CriterionRegister) findAncestorWithClass(this,
				CriterionRegister.class);
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			SysDictModel model = getDictModel();
			if (null == model) {
				// 兼容自定义表单数据
				this.isXForm = true;
				String modelName2 = "com.landray.kmss.sys.xform.base.model.SysFormDbTable";
				SysFormDbTable sysFormDbTable = (SysFormDbTable) ((IBaseDao) SpringBeanUtil
						.getBean("KmssBaseDao"))
								.findByPrimaryKey(modelName, modelName2, false);
				String formType = sysFormDbTable.getFdFormType();
				String modelName3 = "";
				if ("common".equals(formType)) {
					modelName3 = "com.landray.kmss.sys.xform.base.model.SysFormCommonTemplate";
				} else {
					modelName3 = "com.landray.kmss.sys.xform.base.model.SysFormTemplate";
				}
				BaseFormTemplate baseFormTemplate = (BaseFormTemplate) ((IBaseDao) SpringBeanUtil
						.getBean("KmssBaseDao"))
								.findByPrimaryKey(sysFormDbTable.getFdFormId(),
										modelName3, false);
				String filePath = baseFormTemplate.getFdFormFileName();

				// 数据库表的所有字段
				SysDictExtendModel sysDictExtendModel = new SysDictExtendModel();
				DictLoadService dictLoadService = (DictLoadService) SpringBeanUtil
						.getBean("sysFormDictLoadService");
				if ("template"
						.equals(sysFormDbTable.getFdFormType())) {
					sysDictExtendModel = dictLoadService
							.loadTemplateExtDict(filePath);
				} else if ("common"
						.equals(sysFormDbTable.getFdFormType())) {
					sysDictExtendModel = dictLoadService
							.loadCommonTempExtDict(filePath);
				}

				Map<String, SysDictCommonProperty> propertyMap = sysDictExtendModel
						.getPropertyMap();
				Map m = sysDictExtendModel.getNameLabelMap();
				SysDictCommonProperty sysDictCommonProperty = propertyMap
						.get(property);
				this.label = ((SysDictExtendProperty) sysDictCommonProperty)
						.getLabel();

				model = sysDictExtendModel;
			}
			if (property != null && (property.length() > 0)
					&& !"*".equals(property)) {
				if(property.contains("|")){ //hbmParent|fdCreateTime
					String[] proArray = property.split("\\|");
					String pro = proArray[proArray.length-1];
					logger.debug("pro:"+pro);
					if(model.getPropertyMap().containsKey(pro)){
						buildProperty(model, model.getPropertyMap().get(pro));
					}
				}else{
					String[] props = property.split("[,;]");
					if (model != null) {
						for (String prop : props) {
							if(model.getPropertyMap().containsKey(prop)){
								buildProperty(model, model.getPropertyMap().get(prop));
							}
						}
					}
				}

			} else {
				for (SysDictCommonProperty prop : model.getPropertyList()) {
					if (register != null
							&& register.isRegsiterKey(prop.getName())) {
						continue;
					}
					if (!canCriteria(prop)) {
						continue;
					}
					buildProperty(model, prop);
				}
			}
			registerToParent();
		} catch (Exception e) {
			throw new JspTagException("自动生成筛选项出错:", e);
		}
		return EVAL_PAGE;
	}

	protected SysDictModel getDictModel() {
		return SysDataDict.getInstance().getModel(modelName);
	}

	protected void buildProperty(SysDictModel model,
			SysDictCommonProperty property) throws Exception {
		if (property == null) {
			throw new JspTagException("筛选属性不存在 property = " + getProperty());
		}
		CriterionBuilder builder = lookupCriterion(property);
		if (builder == null) {// 部分默认筛选器为实现，暂时屏蔽错误
			// throw new JspTagException("无此属性默认筛选器 property = "
			// + property.getName() + ", type = " + property.getType());
		}
		if (builder != null) {
			if (register != null) {
                register.regsiterKey(property.getName());
            }

			CriteriaTag criteria = (CriteriaTag) findAncestorWithClass(this,
					CriteriaTag.class);

			Map<String, Object> attrs = new HashMap<String, Object>();
			attrs.put("channel", criteria.getChannel());
			attrs.put("expand", this.getExpand());
			attrs.put("multi", this.getMulti());
			attrs.putAll(attributes);
			if (StringUtil.isNotNull(this.title)) {
				attrs.put("isTitle", this.title);
			} else {
				// 自定义表单的主题
				if (this.isXForm && StringUtil.isNotNull(this.label)) {
					attrs.put("isTitle", this.label);
				}
			}
			if(StringUtil.isNotNull(this.property)&&this.property.contains("|")){
				attrs.put("mutilKey", this.property.replace("|","."));
			}
			String html = builder.build(model, property, pageContext, attrs);
			if (!"false".equals(html)) {
				pageContext.getOut().append(html);
			}
		}
	}

	protected Map<String, Object> attributes = new HashMap<String, Object>();

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		if (value != null) {
			if (localName.startsWith("cfg-")) {
				value = value.toString().trim();
			}
			attributes.put(localName, value.toString());
		}
	}

	protected CriterionBuilder lookupCriterion(SysDictCommonProperty property) {
		return CriterionBuilderFactory.getBuilder(property);
	}

	protected boolean canCriteria(SysDictCommonProperty property) {
		return property.isCanSearch();
	}

	@Override
	public void release() {
		modelName = null;
		property = null;
		register = null;
		multi = null;
		expand = null;
		isXForm = false;
		label = null;
		title = null;
		super.release();
	}
}
