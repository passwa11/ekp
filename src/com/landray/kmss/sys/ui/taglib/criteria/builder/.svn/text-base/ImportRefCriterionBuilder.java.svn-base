/**
 * 
 */
package com.landray.kmss.sys.ui.taglib.criteria.builder;

import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.ui.taglib.template.TargetUrlContentAcquirer;
import com.landray.kmss.sys.ui.taglib.template.TemplateContext;
import com.landray.kmss.sys.ui.taglib.widget.ref.AttributeUtil;

/**
 * @author 傅游翔
 * 
 */
public abstract class ImportRefCriterionBuilder extends
		AbstractCriterionBuilder {

	protected Class<?> getTypeClass(String type) {
		try {
			return ClassUtils.forName(type);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> getConfigMap(SysDictCommonProperty property,
			Map<String, Object> attrs) {

		if (attrs.containsKey("isTitle")) {
			// 自定义标题
			attrs.put("title", (String) attrs.get("isTitle"));
		} else {
			attrs.put("title", getTitle(property));
		}
		if(attrs.containsKey("mutilKey")){
			//多层级筛选
			attrs.put("key",attrs.get("mutilKey"));
		}else {
			attrs.put("key", property.getName());
		}
		return attrs;
	}

	protected Map<String, Object> getParamMap(SysDictCommonProperty property) {
		return null;
	}

	abstract protected String getRefId();

	@Override
    public String build(SysDictModel model, SysDictCommonProperty property,
                        PageContext pageContext, Map<String, Object> attrs)
			throws Exception {
		Map<String, Object> newAttrs = getConfigMap(property, attrs);
		Map<String, Object> params = getParamMap(property);
		return importRef(getRefId(), newAttrs, params, pageContext);
	}

	protected String importRef(String ref, Map<String, Object> attrs,
			Map<String, Object> params, PageContext pageContext)
			throws Exception {
		TemplateContext ctx = (new TemplateContext());
		ctx.bind(pageContext);
		AttributeUtil.set(pageContext, "criterionAttrs", attrs);
		AttributeUtil.set(pageContext, "varParams", params);
		try {
			ctx.asInclude();
			String url = getUrl(ref, pageContext);
			return new TargetUrlContentAcquirer(url, pageContext)
					.acquireString();
		} finally {
			ctx.release(pageContext);
			AttributeUtil.release(pageContext, "criterionAttrs");
			AttributeUtil.release(pageContext, "varParams");
		}
	}
}
