package com.landray.kmss.sys.ui.taglib.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.lang.StringUtils;
import com.landray.kmss.web.action.ActionForm;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.sys.config.util.ConvertFormDictToJson;
import com.landray.kmss.sys.ui.taglib.widget.BaseTag;

/**
 * 该类代表输出一个数据对象子项的多个字段
 * 
 *
 */
@SuppressWarnings("serial")
public class AutoPropertyTag extends BaseTag {

	private static final String DEFAULT_REF = "form";
	private String ref = DEFAULT_REF;

	private String include = null;
	private String exclude = null;
	
	private ActionForm form;

	@Override
	public void release() {
		ref = DEFAULT_REF;
		form = null;
		include = null;
		exclude = null;

		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		if (ref == null) {
			ref = DEFAULT_REF;
		}
		Object formObject = pageContext.getRequest().getAttribute(ref);
		if (formObject != null && formObject instanceof ActionForm) {
			form = (ActionForm)formObject;

			return SKIP_BODY;
		} else {
			return SKIP_BODY;
		}
	}


	@Override
	public int doEndTag() throws JspException {

		try {
			Tag parent = getParent();
			if (parent instanceof DataObjectTag && form != null) {
				List<String> includeProps = null;
				List<String> excludeProps = null;
				if (!StringUtils.isEmpty(include)) {
					String[] propArr = include.split(",");
					if (propArr != null) {
						includeProps = new ArrayList<String>(propArr.length);
						for (int i = 0; i < propArr.length; i++) {
							includeProps.add(propArr[i]);
						}
					}
				}
				if (!StringUtils.isEmpty(exclude)) {
					String[] propArr = exclude.split(",");
					if (propArr != null) {
						excludeProps = new ArrayList<String>(propArr.length);
						for (int i = 0; i < propArr.length; i++) {
							excludeProps.add(propArr[i]);
						}
					}
				}
				DataObjectTag responseDataObjectTag = (DataObjectTag) parent;

				ConvertFormDictToJson convert = new ConvertFormDictToJson();
				JSONObject jsonObject = convert.formDictToJsonObject(
						form, (HttpServletRequest) pageContext.getRequest(),
						includeProps,
						excludeProps);

				if (jsonObject != null) {
					JSONObject dataObject = jsonObject.getJSONObject("data");
					if (dataObject != null) {
						JSONObject baseObject = dataObject
								.getJSONObject(responseDataObjectTag.getName());
						responseDataObjectTag.putDataObject(baseObject);
					}

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public ActionForm getForm() {
		return form;
	}

	public String getInclude() {
		return include;
	}

	public void setInclude(String include) {
		this.include = include;
	}

	public String getExclude() {
		return exclude;
	}

	public void setExclude(String exclude) {
		this.exclude = exclude;
	}

}
