package com.landray.kmss.common.rest.convertor;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dto.PageBO;
import com.landray.kmss.common.dto.PageVO;
import com.landray.kmss.common.rest.util.PluginUtil;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.profile.model.SysListShow;
import com.landray.kmss.sys.profile.model.SysListshowCategory;
import com.landray.kmss.sys.profile.service.ISysListShowService;
import com.landray.kmss.sys.profile.service.ISysListshowCategoryService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class PageVOConvertor {
	private final Log log = LogFactory.getLog(PageVOConvertor.class);
	private final HttpServletRequest request;
	private final Page queryPage;
	private final String modelName;
	private final String methodName;
	//除列表配置的字段外，额外需要添加的字段，用于摘要切换及其它逻辑字段等
	private Map<String, PageBO> extraFields;

	public PageVOConvertor(HttpServletRequest request, Page queryPage, String modelName, String methodName) {
		this.request = request;
		this.queryPage = queryPage;
		this.modelName = modelName;
		this.methodName = methodName;
		this.extraFields = Collections.emptyMap();
	}

	public PageVOConvertor(HttpServletRequest request, Page queryPage, String modelName, List<PageBO> extraListFields) {
		this.request = request;
		this.queryPage = queryPage;
		this.modelName = modelName;
		this.methodName = "";
		this.extraFields = convertPageBO(extraListFields);
	}

	ISysListShowService listShowService;

	private ISysListShowService getListShowService() {
		if (listShowService == null) {
			listShowService = (ISysListShowService) SpringBeanUtil.getBean("sysListShowService");
		}
		return listShowService;
	}

	ISysListshowCategoryService sysListshowCategoryService;

	public ISysListshowCategoryService getSysListshowCategoryService() {
		if (sysListshowCategoryService == null) {
			sysListshowCategoryService = (ISysListshowCategoryService) SpringBeanUtil.getBean("sysListshowCategoryService");
		}
		return sysListshowCategoryService;
	}

	@SuppressWarnings("unchecked")
	public PageVO convert() {
		PageVO pageVO = new PageVO();
		String pagePath = getPagePath();
		// 获取显示列配置
		Fields fields = findConfigFields(pagePath);

		// 列表页显示字段
		String props = extractListProps(fields.getSelectedFields(), pagePath);
		pageVO.setProps(props);

		// 表头信息
		Set<PageVO.ColumnInfo> columnInfoSet = buildColumnInfos();
		pageVO.getColumns().addAll(columnInfoSet);

		// 行数据信息
		List<List<PageVO.SigleFild>> allRowDatas = buildAllRowDatas(pageVO.getColumns(), queryPage.getList());
		pageVO.setDatas(allRowDatas);

		// 分页信息
		pageVO.setPaging(queryPage.getPageno(), queryPage.getRowsize(), queryPage.getTotalrows());
		return pageVO;
	}

	/**
	 * 构建前端页面显示的所有行的数据信息
	 *
	 * @param columnsInfo 表头信息
	 * @param pageList    从数据库查询得到的结果集
	 * @return 构建结果
	 */
	private List<List<PageVO.SigleFild>> buildAllRowDatas(List<PageVO.ColumnInfo> columnsInfo, List<Object> pageList) {
		List<List<PageVO.SigleFild>> allRowDatas = new ArrayList<>();
		if (!ArrayUtil.isEmpty(pageList)) {
			for (Object bean : pageList) {
				List<PageVO.SigleFild> rowDatas = new ArrayList<>();
                for (PageVO.ColumnInfo column : columnsInfo) {
                    Object value = getObjectValue(bean,column);
                    rowDatas.add(new PageVO.SigleFild(column.getProperty(), value));
                }
				allRowDatas.add(rowDatas);
			}
		}
		return allRowDatas;
	}

    /**
     * 获取实体对象的实际值
     *
     * @param bean    实体对象
     * @param columnInfo 字段信息
     * @return 实体对象的实际值
     */
	private Object getObjectValue(Object bean, PageVO.ColumnInfo columnInfo) {
        IPropertyConvertor convertor = columnInfo.getPropertyConvertor();
        Object value = bean;
        PropertyConvertorContext convertorContext = new PropertyConvertorContext();
        convertorContext.setColumnInfo(columnInfo);
        convertorContext.setValue(bean);
        convertorContext.setRequestContext(new RequestContext(request));
        convertorContext.setConvertorProps(columnInfo.getConvertorProps());
        return convertor.convert(value,convertorContext);
    }

	/**
	 * 获取实体对象的字段值
	 *
	 * @param bean  实体对象
	 * @param field 字段名
	 * @return 字段值
	 */
	private Object getProperty(Object bean, String field) {
		try {
			return PropertyUtils.getProperty(bean, field);
		} catch (Exception e) {
			log.error("获取字段值失败", e);
		}
		return null;
	}

	/**
	 * 获取去重的表头信息，包含基本、列表及扩展的所有表头信息
	 *
	 * @return
	 */
	private Set<PageVO.ColumnInfo> buildColumnInfos() {
		Set<PageVO.ColumnInfo> columnInfoSet = new LinkedHashSet<>();
        Map<String, Map<String, Object>> fieldMap = PluginUtil.getFields(modelName, methodName);
        if (!fieldMap.isEmpty()) {
            fieldMap.forEach((k,v)-> {
                PageVO.ColumnInfo columnInfo = buildColumnInfo(v);
                columnInfoSet.add(columnInfo);
            });
        }
        return columnInfoSet;
	}

    /**
     * 根据字段的数据字典读取并格式化最终输出的列信息
     *
     * @param field   列表字段信息
     * @return
     */
    private PageVO.ColumnInfo buildColumnInfo(Map<String,Object> field) {
        PageVO.ColumnInfo columnInfo;
        //属性名
        String property = (String) field.get(PluginUtil.PARAM_PROPERTY);
        //标题名
        String title = (String) field.get(PluginUtil.PARAM_TITLE);
        //转换器
        IPropertyConvertor convertor = (IPropertyConvertor) field.get(PluginUtil.PARAM_CONVERTOR);
        //转换器属性
        String convertorProps = (String) field.get(PluginUtil.PARAM_CONVERTOR_PROPS);
        columnInfo = new PageVO.ColumnInfo(title, property,convertor);
        if (StringUtil.isNotNull(convertorProps)) {
            columnInfo.setConvertorProps(convertorProps);
        }
        //字段其它属性
        Map props = (Map)field.get(PluginUtil.PARAM_PROPS);
        if (props != null && props.containsKey("type")) {
            String type = (String)props.get("type");
            columnInfo.setType(type);
        }
        return columnInfo;
    }

	/**
	 * 获取显示列配置
	 */
	@SuppressWarnings("unchecked")
	private Fields findConfigFields(String pagePath) {
		List<SysListShow> selectedFields = null;
		List<SysListShow> unselectedFields = null;
		try {
			SysListshowCategory sysListshowCategory = null;
			if (pagePath != null) {
				sysListshowCategory = getSysListshowCategoryService().getCategory(modelName, pagePath);
			}
			if (sysListshowCategory != null) {
				selectedFields = getListShowService().getSelectedFields(sysListshowCategory.getFdId());
				unselectedFields = getListShowService().getUnselectedFields(sysListshowCategory.getFdId());
			}
		} catch (Exception e) {
			log.error("获取列表显示列配置信息时发生错误", e);
		}
		return new Fields(selectedFields, unselectedFields);
	}

	/**
	 * 获取页面路由路径，如/listCategory
	 */
	private String getPagePath() {
		String page = request.getParameter("q.j_path");
		if (page != null) {
            page = page.replace("%2F", "/");
        }
		return page;
	}

	/**
	 * 提取列表显示列
	 */
	private String extractListProps(List<SysListShow> selectedFields, String page) {
		StringBuilder fields = new StringBuilder();
		boolean flag = false;
		if (selectedFields != null) {
			for (SysListShow selectedField : selectedFields) {
				if ("1".equals(selectedField.getFdStatus())) {
					flag = true;
					String field = selectedField.getFdField();
					if (StringUtil.isNotNull(field)) {
						if (StringUtil.isNotNull(field) && field.trim().length() > 0) {
							fields.append(field).append(";");
						}
					} else {
						if (StringUtil.isNotNull(field) && field.trim().length() > 0) {
							fields = new StringBuilder(field);
						}
					}
				}
			}
			if (flag) {
				return fields.toString();
			}
		}
		//默认显示列
		fields = new StringBuilder(getDefaultFields(page));
		//没有实现自定义列表字段扩展点
		if (fields.length() < 1) {
            fields = new StringBuilder(PluginUtil.getProps(modelName,methodName));
		}
		return fields.toString();
	}

	/**
	 * 获取默认显示列
	 */
	@SuppressWarnings("all")
	private String getDefaultFields(String page) {
		String fields = "";
		IExtension[] extensions = Plugin.getExtensions("com.landray.kmss.sys.listshow.listShowConfig", modelName, "listShowConfig");
		if (extensions != null && extensions.length > 0) {
			fields = Plugin.getParamValueString(extensions[0], "default");
			for (IExtension extension : extensions) {
				String extensionPage = Plugin.getParamValueString(extension, "page");
				if (page != null && page.equals(extensionPage)) {
					fields = Plugin.getParamValueString(extension, "default");
					break;
				}
			}
		}
		return fields;
	}

	/**
	 * List<PageBO>转Map<String, PageBO>，以propertyName为Map的key
	 */
	private Map<String, PageBO> convertPageBO(List<PageBO> extraListFields) {
		Map<String, PageBO> extraFields = new LinkedHashMap<>();
		int i = 0;
		for (PageBO extraField : extraListFields) {
		    if (extraField.getQueryListObjectArrayIndex() == null) {
                extraField.setQueryListObjectArrayIndex(i++);
            }
			extraFields.put(extraField.getProperty(), extraField);
		}
		return extraFields;
	}

    /**
     * 用于添加带转换器的pageBO
     * @param pageBOs
     */
	public void addPageBOs(List<PageBO> pageBOs) {
	    if (!ArrayUtil.isEmpty(pageBOs)) {
	        for (PageBO pageBO : pageBOs) {
                String property = pageBO.getProperty();
                if (this.extraFields == null) {
                    this.extraFields = Collections.emptyMap();
                }
                this.extraFields.put(property,pageBO);
            }
        }
    }

	/**
	 * List<String>转Map<String, PageBO>，以propertyName为Map的key
	 *
	 * @param extraStringFields
	 */
	public void setExtraFieldsArray(List<String> extraStringFields) {
		Map<String, PageBO> extraFields = new LinkedHashMap<>();
		for (String extraField : extraStringFields) {
			extraFields.put(extraField, new PageBO(extraField));
		}
		this.extraFields = extraFields;
	}

	/**
	 * 配置的字段信息
	 */
	private static class Fields {
		private final List<SysListShow> selectedFields;
		private final List<SysListShow> unselectedFields;

		public Fields(List<SysListShow> selectedFields, List<SysListShow> unselectedFields) {
			this.selectedFields = selectedFields;
			this.unselectedFields = unselectedFields;
		}

		public List<SysListShow> getSelectedFields() {
			return selectedFields;
		}

		public List<SysListShow> getUnselectedFields() {
			return unselectedFields;
		}
	}
}
