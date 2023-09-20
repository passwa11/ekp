package com.landray.kmss.sys.ui.taglib.list;

import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.profile.model.SysListShow;
import com.landray.kmss.sys.profile.model.SysListshowCategory;
import com.landray.kmss.sys.profile.service.ISysListShowService;
import com.landray.kmss.sys.profile.service.ISysListshowCategoryService;
import com.landray.kmss.sys.ui.taglib.api.ResponseCode;
import com.landray.kmss.sys.ui.taglib.api.ResponseConstant;
import com.landray.kmss.sys.ui.taglib.widget.BaseTag;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ColumnDatasTag extends BaseTag {

	protected String var = "__$var$__";
	// 数据信息
	protected JSONArray data;
	// 数据集合
	protected JSONArray jsonArray;

	protected JSON columns;

	// 子标签传递数据参数
	protected JSONArray cDatas;
	// 子标签传递列标题参数
	protected JSONArray cColumns;

	// 是否为移动端数据
	protected Boolean mobile = false;

	protected Iterator<?> it;

	protected String href;
	protected String target;
	
	protected String exceptColumns;

	/**
	 * 是否 j_dataType 为json 如果是 此Tag返回的数据结构发生变化为标准API格式:
	 * 
	 * { "errcode" : "0", "errmsg" : "ok", "data" : {}}
	 */
	private Boolean isJSONApiDataType = false;
	private static final String J_DATA_TYPE_JSON = "json";

	// 对应当前数据行的多个actions
	private JSONArray currentRowActions;
	// 所有行的所有actions[每个元素是一个currentRowActions]
	private JSONArray allRowActions;

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Boolean getMobile() {
		return mobile;
	}

	public void setMobile(Boolean mobile) {
		this.mobile = mobile;
	}

	public String getTarget() {
		if (target == null) {
            return "_blank";
        }
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public JSON getColumns() {
		if (columns == null) {
            columns = new JSONArray();
        }
		return columns;
	}

	public void setColumns(JSON column) {
		if (this.getColumns().isEmpty()) {
			((JSONArray) this.columns).addAll((JSONArray) column);
		}
	}

	public JSONArray getCColumns() {
		if (cColumns == null) {
			cColumns = new JSONArray();
		}
		return cColumns;
	}

	public void setCColumns(JSONArray columns) {
		cColumns = columns;
	}

	public void addCColumns(JSONObject column) {
		getCColumns().add(column);
	}

	public JSONArray getJsonArrayData() {
		if (data == null) {
			data = new JSONArray();
		}
		return data;
	}

	public void setData(JSONArray data) {
		this.data = data;
	}

	public void addJonsArrayData(JSONObject data) {
		getJsonArrayData().add(data);
	}

	public JSONArray getCDatas() {
		if (cDatas == null) {
			cDatas = new JSONArray();
		}
		return cDatas;
	}

	public void setCDatas(JSONArray datas) {
		cDatas = datas;
	}

	public void addCDatas(JSONObject datas) {
		getCDatas().add(datas);
	}

	public JSONArray getJsonArrayDatas() {
		if (jsonArray == null) {
			jsonArray = new JSONArray();
		}
		return jsonArray;
	}

	public void setJsonArrayDatas(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public void addDatas(Object data) {
		getJsonArrayDatas().add(data);
	}

	public Object getDatas() {
		return getJsonArrayDatas();
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var.trim();
	}

	protected String varIndex;

	protected Integer index;

	protected String modelName;

	protected List selectedFields;

	protected String page;

	protected String fields;
	
	protected boolean custom = true;

	public boolean isCustom() {
		return custom;
	}

	public void setCustom(boolean custom) {
		this.custom = custom;
	}

	public void setVarIndex(String varIndex) {
		this.varIndex = varIndex.trim();
	}

	private Iterator<?> itTemp;

	public void setList(Object list) {
		if(list==null){
			list=new ArrayList();
		}
		if (list instanceof Collection) {
			it = ((Collection<?>) list).iterator();
			//itTemp = ((Collection<?>) list).iterator();
		} else if (list instanceof Map) {
			it = ((Map<?, ?>) list).entrySet().iterator();
			//itTemp = ((Map<?, ?>) list).entrySet().iterator();
		} else if (list.getClass().isArray()) {
			List<Object> li = new ArrayList<Object>();
			int len = Array.getLength(li);
			for (int i = 0; i < len; i++) {
				li.add(Array.get(list, i));
			}
			it = li.iterator();
			//itTemp = li.iterator();
		}
		if(custom && !mobile){
		if ("java.util.ArrayList".equals(list.getClass().getName())) {
			ArrayList listTemp=(ArrayList)list;
			if(!listTemp.isEmpty()){
				modelName = listTemp.get(0).getClass().getName();
			}
			//modelName = itTemp.next().getClass().getName();
			/*if(modelName.indexOf("KmAssetApply")>-1){
				modelName="com.landray.kmss.km.asset.model.KmAssetApplyBase";
			}*/
		}
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			String cri = request.getQueryString();
			if(cri==null){
				cri = (String)request.getAttribute("javax.servlet.forward.query_string");
			}
			page = null;
			boolean isAll = false;
			boolean isTop = false;
			if (cri.indexOf("=") > -1) {
				String[] paras = cri.split("&");
				for (int i = 0; i < paras.length; i++) {
					String[] pairArr = paras[i].split("=");
					if (pairArr[0].indexOf("q.j_path") > -1) {
						page=pairArr[1].replace("%2F", "/");
					}
				}
			}
			if(page!=null){
			if(page.indexOf("abandomApply")>-1){
				modelName="com.landray.kmss.km.asset.model.KmAssetApplyBase";
			}
			}
			ISysListShowService listShowService = (ISysListShowService) SpringBeanUtil.getBean("sysListShowService");
			ISysListshowCategoryService sysListshowCategoryService = (ISysListshowCategoryService) SpringBeanUtil
					.getBean("sysListshowCategoryService");
			selectedFields = null;
			SysListshowCategory sysListshowCategory = null;
			if(page!=null){
				sysListshowCategory = sysListshowCategoryService.getCategory(modelName, page);
			}
			if (sysListshowCategory != null) {
				selectedFields = listShowService.getSelectedFields(sysListshowCategory.getFdId());
			}
			fields = null;
			boolean flag = false;
			if (selectedFields != null) {
				for (int i = 0; i < selectedFields.size(); i++) {
					if ("1".equals((String) ((SysListShow) selectedFields.get(i)).getFdStatus())) {
						flag = true;
						String field = (String) ((SysListShow) selectedFields.get(i)).getFdField();
						if (fields != null) {
							if (StringUtil.isNotNull(field) && field.toString().trim().length() > 0) {
								fields = fields + ";" + field;
							}
						} else {
							if (StringUtil.isNotNull(field) && field.toString().trim().length() > 0) {
								fields = field;
							}
						}
					}
				}
				if (!flag) {
						fields = getDefaultFields(page);
				}
			} else {
					fields = getDefaultFields(page);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
	
	private String getDefaultFields(String page) {
		String fields = "";
		IExtension[] extensions = Plugin.getExtensions(
				"com.landray.kmss.sys.listshow.listShowConfig",
				modelName, "listShowConfig");
		if (extensions != null && extensions.length > 0) {
			fields = Plugin
					.getParamValueString(extensions[0], "default");
		}
		for (IExtension extension : extensions) {
			String extensionPage = Plugin
					.getParamValueString(extension, "page");
			if (page != null && page.equals(extensionPage)) {
				fields = Plugin.getParamValueString(extension,
						"default");
				break;
			}
		}
		return fields;
	}

	@Override
	public void release() {
		it = null;
		var = "__$var$__";
		data = null;
		jsonArray = null;
		cDatas = null;
		cColumns = null;
		columns = null;
		varIndex = null;
		index = null;
		href = null;
		target = null;
		super.release();
	}

	@Override
	public int doStartTag() throws JspException {
		index = 0;
		initForJSONApi();
		if (it.hasNext()) {
			if (varIndex != null) {
				pageContext.setAttribute(varIndex, index);
			}
			pageContext.setAttribute(var, it.next());
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

	protected Object getValue(Object data, String property) throws Exception {
		Object value = null;
		if (((JSONObject) data).has("value")) {
			value = ((JSONObject) data).get("value");
		} else {
			int index = property.lastIndexOf(".");
			if (index > -1) {
				String _pre_property = property.substring(0, index);
				Object _pre_obj = PropertyUtils.getProperty(pageContext.getAttribute(var), _pre_property);
				if (_pre_obj != null) {
					String _last_property = property.substring(index + 1);
					value = PropertyUtils.getProperty(_pre_obj, _last_property);
				}
			} else {
				value = PropertyUtils.getProperty(pageContext.getAttribute(var), property);
			}
		}
		if (value != null && value instanceof Date) {
			value = DateUtil.convertDateToString((Date) value, DateUtil.TYPE_DATETIME,
					pageContext.getRequest().getLocale());
		}
		if (!"false".equals(String.valueOf(((JSONObject) data).get("escape"))) && value != null) {
			value = StringUtil.XMLEscape(value.toString());
		}
		return value;
	}

	protected void formatData() throws JspTagException {
		String ___href = null;
		if (href != null) {
            ___href = href;
        }
		if (custom && !mobile) {
			if (selectedFields != null) {
				for (int i = 0; i < selectedFields.size(); i++) {
					String fdMessageKey = (String) ((SysListShow) selectedFields.get(i)).getFdMessagekey();
					String fdField = (String) ((SysListShow) selectedFields.get(i)).getFdField();
					String fdFieldType = (String) ((SysListShow) selectedFields.get(i)).getFdFieldType();
					String fdWidth = (String) ((SysListShow) selectedFields.get(i)).getFdWidth();
					String enumType = (String) ((SysListShow) selectedFields.get(i)).getEnumType();
					Boolean flag = false;
					for (Object cData : cDatas) {
						Object _col = ((JSONObject) cData).get("col");
						Object _property = ((JSONObject) cData).get("property");
						String col = _col != null ? String.valueOf(_col) : String.valueOf(_property);
						String property = _property != null ? String.valueOf(_property) : String.valueOf(_col);
						if (col.equals(fdField) || property.equals(fdField)) {
							flag = true;
						}

					}
					if (!flag) {
						JSONObject DataConfig = new JSONObject();
						JSONObject ColumnConfig = new JSONObject();
						String bundle = null;
						String key = fdMessageKey;
						if(fdMessageKey.indexOf(":")>-1) {
							bundle =  fdMessageKey.split(":")[0];
							key = fdMessageKey.split(":")[1];
						}
						try {
							String Field = null;
							String displayProperty = null;
							if ("Model".equals(fdFieldType)) {
								if (fdField.indexOf(".") > -1) {
									Field = fdField.split("\\.")[0];
									displayProperty = fdField.split("\\.")[1];
								} else {
									Field = fdField;
								}

								if (PropertyUtils.isReadable(pageContext.getAttribute(var), Field)) {
									Object fieldValue = PropertyUtils.getProperty(pageContext.getAttribute(var), Field);
									if (fieldValue != null && !"".equals(fieldValue) && !"null".equals(fieldValue)) {
										String value = (String) PropertyUtils.getProperty(
												PropertyUtils.getProperty(pageContext.getAttribute(var), Field),
												displayProperty);
										if (value != null && !"".equals(value) && !"null".equals(value)) {
											DataConfig.element("value", value);
											DataConfig.element("col", fdField);
										} else {
											DataConfig.element("value", "<无>");
											DataConfig.element("col", fdField);
										}

									} else {
										DataConfig.element("value", "<无>");
										DataConfig.element("col", fdField);
										DataConfig.element("style", "min-width: 40px;");
									}
								} else {
									DataConfig.element("value", "<无>");
									DataConfig.element("col", fdField);
									DataConfig.element("style", "min-width: 40px;");
								}
								ColumnConfig.element("property", fdField);
							} else {
								if (PropertyUtils.isReadable(pageContext.getAttribute(var), fdField)) {
									Object fieldValue = PropertyUtils.getProperty(pageContext.getAttribute(var),
											fdField);
									if (fieldValue != null && !"".equals(fieldValue)) {
										if (StringUtil.isNotNull(enumType)) {
											HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
											String value = EnumerationTypeUtil.getColumnEnumsLabel(enumType,
													PropertyUtils.getProperty(pageContext.getAttribute(var), fdField)
															.toString(),
													request.getLocale());
											if (StringUtil.isNotNull(value)) {
												DataConfig.element("value", value);
											} else {
												String fieldValueS = null;
												if ("true".equals(fieldValue.toString())) {
													fieldValueS = "1";
												}
												if ("false".equals(fieldValue.toString())) {
													fieldValueS = "0";
												}
												String valueS = EnumerationTypeUtil.getColumnEnumsLabel(enumType,
														fieldValueS, request.getLocale());
												if (StringUtil.isNotNull(valueS)) {
													DataConfig.element("value", valueS);
												}
											}

										} else {
											Object obj = PropertyUtils.getProperty(pageContext.getAttribute(var),
													fdField);
											if (obj instanceof Date) {
												if ("Date".equalsIgnoreCase(fdFieldType)) {
													obj = DateUtil.convertDateToString((Date) obj,
															ResourceUtil.getString("date.format.date"));
												} else if ("DateTime".equalsIgnoreCase(fdFieldType)) {
													obj = DateUtil.convertDateToString((Date) obj,
															ResourceUtil.getString("date.format.datetime"));
												} else if ("Time".equalsIgnoreCase(fdFieldType)) {
													obj = DateUtil.convertDateToString((Date) obj,
															ResourceUtil.getString("date.format.time"));
												}
											}
											if (obj != null && !"null".equals(obj) && !"".equals(obj)) {
												DataConfig.element("value", obj);
											} else {
												DataConfig.element("value", "<无>");
											}

										}
										DataConfig.element("col", fdField);
									} else {
										DataConfig.element("value", "<无>");
										DataConfig.element("col", fdField);
										DataConfig.element("style", "min-width: 40px;");
									}
								} else {
									DataConfig.element("value", "<无>");
									DataConfig.element("col", fdField);
									DataConfig.element("style", "min-width: 40px;");
								}
								ColumnConfig.element("property", fdField);

							}
						} catch (Exception e) {
							logger.error("数据出错:", e);
							throw new JspTagException(e);
						}
						if (StringUtil.isNull(ResourceUtil.getString(key, bundle))) {
							ColumnConfig.element("title", "<无>");
						} else {
							ColumnConfig.element("title", ResourceUtil.getString(key, bundle));
						}
						if (StringUtil.isNotNull(fdWidth)) {
							ColumnConfig.element("headerStyle", "width:" + fdWidth);
						}
						this.addCDatas(DataConfig);
						this.addCColumns(ColumnConfig);
					}

				}
			}
		}
		JSONObject Config = new JSONObject();
		if (custom && !mobile) {
			Config.element("props", fields);
		}
		this.addCColumns(Config);
		for (Object cData : cDatas) {
			JSONObject column = new JSONObject();
			Object _col = ((JSONObject) cData).get("col");
			Object _property = ((JSONObject) cData).get("property");
			String col = _col != null ? String.valueOf(_col) : String.valueOf(_property);
			String property = _property != null ? String.valueOf(_property) : String.valueOf(_col);
			column.element("col", StringUtil.isNotNull(col) ? col : property);
			try {
				Object value = getValue(cData, property);
				column.element("value", value);
				Object style = ((JSONObject) cData).get("style");
				Object styleClass = ((JSONObject) cData).get("styleClass");
				if (selectedFields != null) {
					for (int j = 0; j < selectedFields.size(); j++) {
						String fdField = (String) ((SysListShow) selectedFields.get(j)).getFdField();
						String fdWidth = (String) ((SysListShow) selectedFields.get(j)).getFdWidth();
						if (StringUtil.isNotNull(fdWidth)) {
							if (col.equals(fdField) || property.equals(fdField)) {
								for (Object cColumn : cColumns) {
									Object _property2 = ((JSONObject) cColumn).get("property");
									Object _col2 = ((JSONObject) cColumn).get("col");
									String col2 = _col2 != null ? String.valueOf(_col2) : String.valueOf(_property2);
									String property2 = _property2 != null ? String.valueOf(_property2)
											: String.valueOf(_col2);
									if (col2.equals(fdField) || property2.equals(fdField)) {
										((JSONObject) cColumn).element("headerStyle", "width:" + fdWidth);
										((JSONObject) cColumn).element("headerClass", "");
									}
								}
							}

						}
					}
				}

				if (style != null) {
					column.element("style", ((JSONObject) cData).get("style"));
				}

				if (styleClass != null) {
					column.element("styleClass", ((JSONObject) cData).get("styleClass"));
				}
				if (___href != null) {
                    ___href = ___href.replace("!{" + column.getString("col") + "}", column.getString("value"));
                }
			} catch (Exception e) {
				logger.error("循环输出数据出错:", e);
				throw new JspTagException(e);
			}
			addJonsArrayData(column);
		}
		if (___href != null) {
			JSONObject hrefColumn = new JSONObject();
			hrefColumn.accumulate("col", "href");
			hrefColumn.accumulate("value", ___href);
			addJonsArrayData(hrefColumn);

			JSONObject targetColumn = new JSONObject();
			targetColumn.accumulate("col", "target");
			targetColumn.accumulate("value", this.getTarget());
			addJonsArrayData(targetColumn);
		}
		addDatas(getJsonArrayData());
		setColumns(getCColumns());
		// 如果为API调用，添加当前行的所有actions
		if (isJSONApiDataType) {
			allRowActions.add(currentRowActions);
		}

	}

	@Override
	public int doAfterBody() throws JspException {
		if (pageContext.getAttribute(var) != null) {
            this.formatData();
        }

		loopClear();
		if (it.hasNext()) {
			Object item = it.next();
			pageContext.setAttribute(var, item);
			index = index + 1;
			if (varIndex != null) {
				pageContext.setAttribute(varIndex, index);
			}
			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}
	}

	private void loopClear() {
		getCDatas().clear();
		getJsonArrayData().clear();
		getCColumns().clear();
		// 列表数据迭代解析内部标签时重置当前行的多个actions
		// 初始化为新数组对象，这样可以直接作为元素添加到所用行
		if (isJSONApiDataType) {
			currentRowActions = new JSONArray();
		}
	}

	@Override
	public int doEndTag() throws JspException {

		try {
			Tag parent = getParent();
			if (parent instanceof DataTag) {
                ((DataTag) parent).setDatas(bulidJsonDatas());
            }
		} catch (Exception e) {
			logger.error(e.toString());
			throw new JspTagException(e);
		}
		registerToParent();
		release();
		return EVAL_PAGE;
	}

	private JSONObject bulidJsonDatas() throws Exception {
		JSONObject json = new JSONObject();
		if (isJSONApiDataType) {
			json.element(ResponseConstant.KEY_CODE, ResponseCode.SUCCESS);
			json.element(ResponseConstant.KEY_MSG,
					ResponseConstant.EMTYP_MSG_STR);
			JSONObject dataObject = new JSONObject();
			dataObject.element("columns", getColumns());
			dataObject.element("datas", getDatas());
			dataObject.element("actions", allRowActions);
			json.element(ResponseConstant.KEY_DATA, dataObject);
		} else {
			json.element("columns", getColumns());
			json.element("datas", getDatas());
		}
		return json;
	}

	/**
	 * 为JSON API调用进行的初始化工作
	 */
	private void initForJSONApi() {
		String j_dataType = pageContext.getRequest().getParameter("j_dataType");
		if (J_DATA_TYPE_JSON.equals(j_dataType)) {
			isJSONApiDataType = true;
			currentRowActions = new JSONArray();
			allRowActions = new JSONArray();
		}
	}

	/**
	 * 为当前行添加一个操作Action
	 * 
	 * @param data
	 */
	public void addCurrentRowAction(JSONObject data) {
		if (isJSONApiDataType) {
			currentRowActions.add(data);
		}
	}

	public String getExceptColumns() {
		return exceptColumns;
	}

	public void setExceptColumns(String exceptColumns) {
		this.exceptColumns = exceptColumns;
	}

}
