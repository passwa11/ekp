package com.landray.kmss.sys.portal.taglib;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.SysUiConfigUtil;
import com.landray.kmss.sys.ui.xml.model.SysUiTemplate;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.taglib.editor.IEditorBox;
import com.landray.kmss.web.taglib.xform.TagUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DesignerTag extends SimpleTagSupport {

	private String scene;
	private String ref;
	private String style;
	private String property;

	public String getScene() {
		return scene;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getRef() {
		if (this.ref == null) {
			ref = "template.default";
		}
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	public void doTag() throws JspException {
		try {
			PageContext ctx = (PageContext) getJspContext();
			Object value = TagUtils.getFieldValue((HttpServletRequest) ctx.getRequest(), this.getProperty());
			// 隐藏域
			StringBuilder sb1 = new StringBuilder();
			sb1.append("<textarea id=\"").append(this.getProperty()).append("\" name=\"").append(this.getProperty()).append("\" style=\"width:100%;display:none;\">");
			sb1.append(StringUtil.isNull((String) value) ? "" : StringUtil.XMLEscape((String) value));
			sb1.append("</textarea>\r\n");
			ctx.getOut().print(sb1.toString());

			// 脚本
			StringBuilder sb = new StringBuilder();
			sb.append("\tseajs.use([\"sys/portal/designer/js/Designer\"], function(Designer) { \r\n");
			sb.append("\t\tvar config  = { \r\n");
			sb.append("\t\t\t\"element\":\"").append(this.getProperty()).append("\", \r\n");
			sb.append("\t\t\t\"contextPath\":\"").append(((HttpServletRequest) ctx.getRequest()).getContextPath()).append("\", \r\n");
			sb.append("\t\t\t\"ref\":\"").append(this.getRef()).append("\", \r\n");
			if (style != null) {
				sb.append("\t\t\t\"style\":\"").append(this.getStyle()).append("\", \r\n");
			}
			if (scene != null) {
				sb.append("\t\t\t\"scene\":\"").append(this.getScene()).append("\", \r\n");
			}
			
			//页面默认宽度
            String pageWidth = SysUiConfigUtil.getFdWidth();
		    if(StringUtil.isNotNull(pageWidth)){
				sb.append("\t\t\t\"pageWidth\":\"").append(pageWidth).append("\", \r\n");
			}
		    
		    // 页面可选模板列表
			List<SysUiTemplate> templateList = new ArrayList<SysUiTemplate>(SysUiPluginUtil.getTemplates().values());
			JSONArray jsonArray = new JSONArray();
			if(templateList!=null&&templateList.size()>0){
				for(SysUiTemplate template : templateList){
					String temp = (";" + template.getFdFor() + ";");
					// 匿名匹配判断 @author 吴进 by 20191110
					if (StringUtil.isNull(template.getFdFor())
							|| temp.indexOf((";portal;")) != -1
							|| temp.indexOf((";anonymous;")) != -1) {
						JSONObject jsonObj = new JSONObject();
						jsonObj.put("ref", template.getFdId());
						jsonObj.put("refName", template.getFdName());
						jsonArray.add(jsonObj);	
					}
				}
			}
			sb.append("\t\t\t\"pageTemplateList\":$.parseJSON(unescape(\"").append(StringUtil.escape(jsonArray.toString())).append("\")) \r\n");
	
			sb.append("\t\t}; \r\n");
			sb.append("\t\tLDESIGNER[\"").append(this.getProperty()).append("\"]=(new Designer(config).start());\r\n");
			sb.append("\t});\r\n");
			
			String outScript = sb.toString();
			IEditorBox ebox = (IEditorBox) findAncestorWithClass(this,IEditorBox.class);
			if (ebox != null) {
				outScript = ebox.startEditor(outScript);
			} else {
				outScript = "<script>\r\n" + outScript + "</script>";
			}
			ctx.getOut().print(outScript);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}
}
