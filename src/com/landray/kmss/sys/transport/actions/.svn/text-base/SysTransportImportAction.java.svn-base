package com.landray.kmss.sys.transport.actions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.transport.service.extendsion.ISysTransportFilter;
import com.landray.kmss.sys.transport.util.PluginUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.exception.DataException;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictCommonPropertyComparator;
import com.landray.kmss.sys.config.dict.SysDictComplexProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.log.xml.SysLogOperXml;
import com.landray.kmss.sys.transport.form.ConfigForm;
import com.landray.kmss.sys.transport.form.SysTransportImportUploadForm;
import com.landray.kmss.sys.transport.model.Config;
import com.landray.kmss.sys.transport.model.SysTransportImportConfig;
import com.landray.kmss.sys.transport.model.SysTransportImportKey;
import com.landray.kmss.sys.transport.model.SysTransportImportProperty;
import com.landray.kmss.sys.transport.model.SysTransportPrimaryKeyProperty;
import com.landray.kmss.sys.transport.service.ISysTransportImportService;
import com.landray.kmss.sys.transport.service.ISysTransportNoticeProvider;
import com.landray.kmss.sys.transport.service.ISysTransportProvider;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.KmssEnumFormat;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;

import net.sf.json.JSONArray;

public class SysTransportImportAction extends ExtendAction {
	private ISysTransportImportService sysTransportImportService;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return getService();
	}

	private ISysTransportImportService getService() {
		if (sysTransportImportService == null) {
            sysTransportImportService = (ISysTransportImportService) getBean("sysTransportImportService");
        }
		return sysTransportImportService;
	}

	@Override
	protected ActionForm createNewForm(ActionMapping mapping, ActionForm form,
									   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String method = request.getParameter("method");
		ConfigForm configForm = (ConfigForm) form;
		configForm.setFdId(IDGenerator.generateID());
		String fdModelName;
		if ("add".equals(method)) {
			form.reset(mapping, request);
			fdModelName = request.getParameter("fdModelName");
		} else {
            fdModelName = configForm.getFdModelName();
        }
		if (StringUtil.isNull(fdModelName)) {
            throw new IllegalArgumentException("fdModelName参数不能为空！");
        }

		SysDataDict dataDict = SysDataDict.getInstance();
		if (dataDict == null) {
			logger.warn("无法获取SysDataDict实例！");
			return form;
		}
		SysDictModel dictModel = dataDict.getModel(fdModelName);
		if (dictModel == null) {
			logger.error("获取数据字典失败  fdModelName=" + fdModelName);
			throw new IllegalArgumentException("获取数据字典失败，请检查您的请求参数！");
		}
		Collection propertyCollection = dictModel.getPropertyMap().values();
		List options = new ArrayList();
		List selectedOptions = new ArrayList();
		List primaryKeyOptionList = new ArrayList(); // 主数据关键字可选项列表
		StringBuffer foreignKeyStringBuffer = new StringBuffer();
		StringBuffer notNullPropertyNameSb = new StringBuffer();
		Map foreignKeyPropertyOptionHtmlMap = new HashMap();
		Map<String, ISysTransportFilter>  filterMap =  PluginUtil.get(PluginUtil.SysTransPluginEnum.TRANSPORT_FILTER);
		Set<String> filterProps = null;
		if(MapUtils.isNotEmpty(filterMap)){
			ISysTransportFilter sysTransportFilter = filterMap.get(fdModelName);
			if(null != sysTransportFilter){
				filterProps = sysTransportFilter.getFilterProps(fdModelName);
			}
		}
		for (Iterator iter = propertyCollection.iterator(); iter.hasNext();) {
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) iter
					.next();
			// logger.debug("commonProperty.name = " +
			// commonProperty.getName());
			if(CollectionUtils.isNotEmpty(filterProps)){
				if(filterProps.contains(commonProperty.getName())){
					continue;
				}
			}
			if (isPrimaryKeyOption(commonProperty)) {
                primaryKeyOptionList.add(commonProperty);
            }
			/* 只有非只读、可显示、非复合属性（SysDictComplexProperty）才能被导入 */
			if (isOptionProperty(commonProperty)) {
				options.add(commonProperty);
				// 如果该属性同时还是必填的，则默认选择该属性
				if (commonProperty.isNotNull()) {
					selectedOptions.add(commonProperty);
					notNullPropertyNameSb.append(commonProperty.getName())
							.append(";");
				}
				// 判断是否为外键
				if (isForeignKeyProperty(commonProperty)) {
					// 将属性名加入到foreignKeyStringBuffer中
					foreignKeyStringBuffer.append(commonProperty.getName())
							.append(";");
					/* 查找该外键的属性列表 */
					SysDictModel foreignKeyModel = dataDict
							.getModel(commonProperty.getType());
					Collection foreignKeyPropertyCollection = foreignKeyModel
							.getPropertyMap().values();
					StringBuffer foreignKeyOptionHTMLStringBuffer = new StringBuffer();
					for (Iterator iterator = foreignKeyPropertyCollection
							.iterator(); iterator.hasNext();) {
						SysDictCommonProperty foreignKeyProperty = (SysDictCommonProperty) iterator
								.next();
						/* 只有非只读、可显示、SysDictSimpleProperty才能用于联合主键 */
						if (isForeignKeyPropertyOptions(foreignKeyProperty)) {
							foreignKeyOptionHTMLStringBuffer.append(
									"<br/><option value=\"").append(
									foreignKeyProperty.getName()).append("\">");
							if (!StringUtil.isNull(foreignKeyProperty
									.getMessageKey())) {
                                foreignKeyOptionHTMLStringBuffer
                                        .append(ResourceUtil
                                                .getString(foreignKeyProperty
                                                        .getMessageKey()));
                            }
							foreignKeyOptionHTMLStringBuffer
									.append("</option>");
							// 外键的父对象
						} else if (isForeignKeyProperty(foreignKeyProperty)
								&& "hbmParent".equals(
								foreignKeyProperty.getName())) {
							SysDictModel parentKeyModel = dataDict
									.getModel(foreignKeyProperty.getType());
							if (StringUtil.isNotNull(parentKeyModel
									.getDisplayProperty())) {
								foreignKeyOptionHTMLStringBuffer.append(
										"<br/><option value=\"").append(
										foreignKeyProperty.getName()).append(
										"\">");
								foreignKeyOptionHTMLStringBuffer
										.append(ResourceUtil
												.getString(foreignKeyProperty
														.getMessageKey()));
								foreignKeyOptionHTMLStringBuffer
										.append("</option>");
							}
						}
					}
					foreignKeyPropertyOptionHtmlMap.put(commonProperty
							.getName(), foreignKeyOptionHTMLStringBuffer
							.toString());
				}
			}
		}

		String realPath = ConfigLocationsUtil.getWebContentPath();
		logger.debug("realPath=" + realPath);
		SysDictCommonPropertyComparator comparator = new SysDictCommonPropertyComparator();
		Collections.sort(options, comparator);
		Collections.sort(primaryKeyOptionList, comparator);

		configForm.setOptionList(options);
		configForm.setPropertyList(selectedOptions);
		configForm.setForeignKeyString(foreignKeyStringBuffer.toString());
		configForm
				.setForeignKeyPropertyOptionHtmlMap(foreignKeyPropertyOptionHtmlMap);
		configForm.setPrimaryKeyOptionList(primaryKeyOptionList);
		request.setAttribute("modelMessageKey", dictModel.getMessageKey());
		request.setAttribute("notNullPropertyNames", notNullPropertyNameSb
				.toString());
		logger
				.debug("notNullPropertyNames="
						+ notNullPropertyNameSb.toString());
		/* 新增页面加入业务模块的扩展操作方法 */
		IBaseService baseService = (IBaseService) getBean(
				dictModel.getServiceBean());
		if (baseService != null
				&& baseService instanceof ISysTransportProvider) {
			ISysTransportProvider provider = (ISysTransportProvider) baseService;
			String extendPath = provider.getEditExtendPath();
			if (StringUtil.isNotNull(extendPath)) {
				request.setAttribute("extendPath", extendPath);
			}
		}

		return form;
	}

	/** 检查是否为主数据关键字的可选属性 */
	private boolean isPrimaryKeyOption(SysDictCommonProperty commonProperty) {
		if (commonProperty instanceof SysDictComplexProperty) {
            return commonProperty.isCanDisplay();
        }
		return !commonProperty.isReadOnly()
				&& StringUtil.isNotNull(commonProperty.getColumn())
				&& commonProperty.isCanDisplay()
				&& !"RTF".equals(commonProperty.getType())
				&& (!isForeignKeyProperty(commonProperty) || isForeignKeyProperty(commonProperty)
						&& commonProperty.isNotNull());
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			doSaveOrUpdate(form, request, messages, true);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return add(mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	private void doSaveOrUpdate(ActionForm form, HttpServletRequest request,
			KmssMessages messages, boolean isSave) throws Exception {
		ConfigForm configForm = (ConfigForm) form;
		SysTransportImportConfig config;
		String fdId = request.getParameter("fdId");
		if (isSave) {
		    UserOperHelper.logAdd(getServiceImp(request).getModelName());
			config = new SysTransportImportConfig();
			// 为保证与新建页面fdId一致，这里需要获取页面中提交的fdId
			if (StringUtil.isNotNull(fdId)) {
				config.setFdId(fdId);
			}
		} else {
		    UserOperHelper.logUpdate(getServiceImp(request).getModelName());
			config = (SysTransportImportConfig) getService().findByPrimaryKey(fdId);
		}
		//追加操作名称
		//UserOperHelper.appendEventType("-"+ResourceUtil.getString("table.sysTransportImportConfig", "sys-transport"));
		/* 设置一般属性 */
		IUserOper userOper = null;
		if (isSave) {
		    userOper = UserOperContentHelper.putAdd(config.getFdId(), config.getFdName(),SysTransportImportConfig.class.getName());
			config.setFdModelName(configForm.getFdModelName());
			UserOperContentHelper.putLog(isSave,userOper,"fdModelName",null,configForm.getFdModelName());
			config.setCreateTime(new Date());
			config.setCreator(UserUtil.getUser());
		}else{
		    userOper = UserOperContentHelper.putUpdate(config.getFdId(), config.getFdName(),SysTransportImportConfig.class.getName());
		}
		UserOperContentHelper.putLog(isSave,userOper,"fdName",config.getFdName(),configForm.getFdName());
		config.setFdName(configForm.getFdName());
		UserOperContentHelper.putLog(isSave,userOper,"fdImportType",config.getFdImportType(),configForm.getFdImportType());
		config.setFdImportType(configForm.getFdImportType());
		/* 设置主数据关键字列表 */
		List primaryKeyPropertyList = new ArrayList();
		;
		if (!StringUtil.isNull(configForm.getPrimaryKey1())) {
			SysTransportPrimaryKeyProperty primaryKeyProperty = new SysTransportPrimaryKeyProperty();
			primaryKeyProperty.setFdName(configForm.getPrimaryKey1());
			primaryKeyProperty.setConfig(config);
			primaryKeyPropertyList.add(primaryKeyProperty);
		}
		if (!StringUtil.isNull(configForm.getPrimaryKey2())) {
			SysTransportPrimaryKeyProperty primaryKeyProperty = new SysTransportPrimaryKeyProperty();
			primaryKeyProperty.setFdName(configForm.getPrimaryKey2());
			primaryKeyProperty.setConfig(config);
			primaryKeyPropertyList.add(primaryKeyProperty);
		}
		if (!StringUtil.isNull(configForm.getPrimaryKey3())) {
			SysTransportPrimaryKeyProperty primaryKeyProperty = new SysTransportPrimaryKeyProperty();
			primaryKeyProperty.setFdName(configForm.getPrimaryKey3());
			primaryKeyProperty.setConfig(config);
			primaryKeyPropertyList.add(primaryKeyProperty);
		}
		UserOperContentHelper.putLog(isSave,userOper,"primaryKeyPropertyList",config.getPrimaryKeyPropertyList(),primaryKeyPropertyList);
		config.setPrimaryKeyPropertyList(primaryKeyPropertyList);
		primaryKeyPropertyList = null;

		/* 设置已选属性列表 */
		List propertyList = new ArrayList();
		String[] selectedOptions = configForm.getSelectedPropertyNames().split(
				";");
		SysDataDict dataDict = SysDataDict.getInstance();
		SysDictModel dictModel = dataDict.getModel(configForm.getFdModelName());
		Map propertyMap = dictModel.getPropertyMap();
		for (int i = 0; i < selectedOptions.length; i++) {
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap
					.get(selectedOptions[i]);
			SysTransportImportProperty property = new SysTransportImportProperty();
			property.setFdName(commonProperty.getName());
			property.setFdOrder(new Integer(i));
			// 判断当前已选属性是否外键
			if (isForeignKeyProperty(commonProperty)) { // 是外键属性，则需要取外键属性的联合主键信息
				String[] keys = request.getParameterValues(commonProperty
						.getName());
				/* 如果该外键未选择属性，则提示出错 */
				if (StringUtil.isNull(keys[0])) {
					String propertyDisplayName = ResourceUtil
							.getString(commonProperty.getMessageKey(), request
									.getLocale());
					messages
							.addError(new KmssMessage(
									"sys-transport:sysTransport.error.foreignKeyProperty.empty",
									propertyDisplayName));
				}
				List keyList = new ArrayList();
				for (int j = 0; j < keys.length; j++) {
					if (StringUtil.isNull(keys[j])) {
                        continue;
                    }
					SysTransportImportKey key = new SysTransportImportKey(
							keys[j]);
					key.setProperty(property);
					keyList.add(key);
				}
				property.setKeyList(keyList);
			}
			property.setConfig(config);
			propertyList.add(property);
		}
		UserOperContentHelper.putLog(isSave,userOper,"propertyList",config.getPropertyList(),propertyList);
		config.setPropertyList(propertyList);

		if (!messages.hasError()) {
            if (isSave) {
                getService().add(config);
            } else {
                getService().update(config);
            }
        }
	}

    @Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form,
								 HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			doSaveOrUpdate(form, request, messages, true);
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).save(request);
		if (messages.hasError()) {
            return edit(mapping, form, request, response);
        } else {
            return add(mapping, form, request, response);
        }
	}

	private boolean isForeignKeyProperty(SysDictCommonProperty commonProperty) {
		return commonProperty.getType().startsWith("com.landray.kmss");
	}

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);

			/* 生成已选属性名称串 */
			String fdId = request.getParameter("fdId");
			SysTransportImportConfig config = (SysTransportImportConfig) getService()
					.findByPrimaryKey(fdId);
			SysDataDict dataDict = SysDataDict.getInstance();
			SysDictModel dictModel = dataDict.getModel(config.getFdModelName());
			request.setAttribute("modelMessageKey", dictModel.getMessageKey());
			Map propertyMap = dictModel.getPropertyMap();
			StringBuffer sb = new StringBuffer();
			Map foreignKeyMap = new HashMap();
			for (Iterator iter = config.getPropertyList().iterator(); iter
					.hasNext();) {
				SysTransportImportProperty property = (SysTransportImportProperty) iter
						.next();
				SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap
						.get(property.getFdName());
				if (commonProperty == null) {
					continue;
				}
				String propertyMessage = ResourceUtil.getString(
						commonProperty.getMessageKey(), request.getLocale());
				sb.append(propertyMessage).append("; ");

				/* 生成外键属性的属性映射表，key：外键属性名称（例如：部门）；value：属性名称列表（例如：名称 编号） */
				if (isForeignKeyProperty(commonProperty)) { // 是外键属性，则需要取外键属性的联合主键信息
					SysDictModel foreignModel = dataDict
							.getModel(commonProperty.getType());
					if(null != foreignModel) {
						Map foreignPropertyMap = foreignModel.getPropertyMap();
	
						StringBuffer foreignKeySb = new StringBuffer();
						for (Iterator iter2 = property.getKeyList().iterator(); iter2
								.hasNext();) {
							SysTransportImportKey key = (SysTransportImportKey) iter2
									.next();
							SysDictCommonProperty foreignCommonProperty = (SysDictCommonProperty) foreignPropertyMap
									.get(key.getFdName());
							String keyMessage = ResourceUtil.getString(
									foreignCommonProperty.getMessageKey(), request
											.getLocale());
							foreignKeySb.append(keyMessage).append("; ");
						}
						// key：外键属性名称（例如：部门）；value：属性名称列表（例如：名称 编号）
						foreignKeyMap.put(propertyMessage, foreignKeySb.toString());
					}
				}
			}
			request.setAttribute("propertyNames", sb.toString());
			request.setAttribute("foreignKeyMap", foreignKeyMap);

			/* 生成主数据关键字属性名称串 */
			sb.delete(0, sb.length() - 1);
			for (Iterator iter = config.getPrimaryKeyPropertyList().iterator(); iter
					.hasNext();) {
				SysTransportPrimaryKeyProperty property = (SysTransportPrimaryKeyProperty) iter
						.next();
				SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap
						.get(property.getFdName());
				sb.append(
						ResourceUtil.getString(commonProperty.getMessageKey(),
								request.getLocale())).append("; ");
			}
			request.setAttribute("primaryKeyNames", sb.toString());

			/* 生成其它提示信息 */
			IBaseService baseService = (IBaseService) getBean(
					dictModel.getServiceBean());
			if (baseService != null
					&& baseService instanceof ISysTransportNoticeProvider) {
				ISysTransportNoticeProvider provider = (ISysTransportNoticeProvider) baseService;
				String notice = provider.getViewPageNotice(config);
				if (StringUtil.isNotNull(notice)) {
					request.setAttribute("otherNotice", notice);
				}
			}

		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-view", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("view", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);

			/* 获取域模型 */
			String fdId = request.getParameter("fdId");
			SysTransportImportConfig config = (SysTransportImportConfig) getService()
					.findByPrimaryKey(fdId);
			SysDataDict dataDict = SysDataDict.getInstance();
			SysDictModel dictModel = dataDict.getModel(config.getFdModelName());
			request.setAttribute("modelMessageKey", dictModel.getMessageKey());

			Collection selectedPropertyNameCollection = new HashSet();
			/*
			 * 已选属性映射表 key： 属性名（例如：fdParent） value：SysTransportImportProperty对象
			 */
			Map selectedPropertyMap = new HashMap();
			for (Iterator iter = config.getPropertyList().iterator(); iter
					.hasNext();) {
				SysTransportImportProperty property = (SysTransportImportProperty) iter
						.next();
				selectedPropertyNameCollection.add(property.getFdName());
				selectedPropertyMap.put(property.getFdName(), property);
			}

			Collection propertyCollection = dictModel.getPropertyMap().values();
			Map propertyMap = dictModel.getPropertyMap();
			List options = new ArrayList();
			List selectedOptions = new ArrayList();
			List primaryKeyOptionList = new ArrayList(); // 主数据关键字可选项列表
			StringBuffer foreignKeyStringBuffer = new StringBuffer();
			StringBuffer notNullPropertyNameSb = new StringBuffer();

			/*
			 * 外键属性的可选option的Html代码 key：属性名（例如：fdParent）
			 * value：Html代码（例如：<br/><option value="fdNo">编号</option>）
			 */
			Map foreignKeyPropertyOptionHtmlMap = new HashMap();

			/*
			 * model中已选外键对应的select控件的html代码
			 */
			List selectedForeignKeyHtmlList = new ArrayList();

			for (Iterator iter = propertyCollection.iterator(); iter.hasNext();) {
				SysDictCommonProperty commonProperty = (SysDictCommonProperty) iter
						.next();
				if (isPrimaryKeyOption(commonProperty)) {
					primaryKeyOptionList.add(commonProperty);
				}
				/*
				 * 只有非只读、可显示、非复合属性（SysDictListProperty、SysDictComplexProperty）才能被导入
				 */
				if (isOptionProperty(commonProperty)) {
					options.add(commonProperty);
					if (commonProperty.isNotNull()) {
						notNullPropertyNameSb.append(commonProperty.getName())
								.append(";");
					}
					// 判断是否为外键
					if (isForeignKeyProperty(commonProperty)) {
						// 将属性名加入到foreignKeyStringBuffer中
						foreignKeyStringBuffer.append(commonProperty.getName())
								.append(";");
						/* 查找该外键的属性列表 */
						SysDictModel foreignKeyModel = dataDict
								.getModel(commonProperty.getType());
						if(null != foreignKeyModel) {
							Collection foreignKeyPropertyCollection = foreignKeyModel
									.getPropertyMap().values();
							StringBuffer foreignKeyOptionHTMLStringBuffer = new StringBuffer();
							for (Iterator iterator = foreignKeyPropertyCollection
									.iterator(); iterator.hasNext();) {
								SysDictCommonProperty foreignKeyProperty = (SysDictCommonProperty) iterator
										.next();
								/* 只有非只读、可显示、SysDictSimpleProperty才能用于联合主键 */
								if (isForeignKeyPropertyOptions(foreignKeyProperty)) {
									foreignKeyOptionHTMLStringBuffer.append(
											"<option value=\"").append(
											foreignKeyProperty.getName()).append(
											"\">");
									if (!StringUtil.isNull(foreignKeyProperty
											.getMessageKey())) {
                                        foreignKeyOptionHTMLStringBuffer
                                                .append(ResourceUtil
                                                        .getString(foreignKeyProperty
                                                                .getMessageKey()));
                                    }
									foreignKeyOptionHTMLStringBuffer
											.append("</option>");
								} else if (isForeignKeyProperty(foreignKeyProperty)
										&& "hbmParent".equals(
										foreignKeyProperty.getName())) {
									SysDictModel parentKeyModel = dataDict
											.getModel(foreignKeyProperty.getType());
									if (StringUtil.isNotNull(parentKeyModel
											.getDisplayProperty())) {
										foreignKeyOptionHTMLStringBuffer.append(
												"<br/><option value=\"").append(
												foreignKeyProperty.getName())
												.append("\">");
										foreignKeyOptionHTMLStringBuffer
												.append(ResourceUtil
														.getString(foreignKeyProperty
																.getMessageKey()));
										foreignKeyOptionHTMLStringBuffer
												.append("</option>");
									}
								}
							}
							foreignKeyPropertyOptionHtmlMap.put(commonProperty
									.getName(), foreignKeyOptionHTMLStringBuffer
									.toString());
	
							// 该外键是否已经被选择
							if (selectedPropertyNameCollection
									.contains(commonProperty.getName())) {
								StringBuffer selectedForeignKeyHtmlStringBuffer = new StringBuffer();
								SysTransportImportProperty property = (SysTransportImportProperty) selectedPropertyMap
										.get(commonProperty.getName());
								List keyList = property.getKeyList();
								foreignKeyOptionHTMLStringBuffer.insert(0,
										"<option value=\"\"></option>");
								String foreignKeyPropertyOptionHtmlString = foreignKeyOptionHTMLStringBuffer
										.toString();
								// 生成3个select的Html代码
								for (int i = 0; i < 3; i++) {
									StringBuffer oneSelectHtmlSb = new StringBuffer();
									SysTransportImportKey key = null;
									if (keyList.size() > i) {
                                        key = (SysTransportImportKey) keyList
                                                .get(i);
                                    }
									// <select name="propertyName">
									oneSelectHtmlSb.append("<select name=\"")
											.append(commonProperty.getName())
											.append("\">");
	
									/* 根据已选的属性名，查找相应的option，添加selected标记 */
									if (key != null) {
										String target = "<option value=\""
												+ key.getFdName() + "\">";
										String replaceTo = "<option value=\""
												+ key.getFdName() + "\" selected>";
										// <option value="xxx">xxx</option>...
										oneSelectHtmlSb
												.append(foreignKeyPropertyOptionHtmlString
														.replaceFirst(target,
																replaceTo));
									} else
										// <option value="xxx">xxx</option>...
                                    {
                                        oneSelectHtmlSb
                                                .append(foreignKeyPropertyOptionHtmlString);
                                    }
									// </select>
									oneSelectHtmlSb.append("</select>");
									if (i == 0) {
                                        oneSelectHtmlSb
                                                .append("<span class=\"txtstrong\">*</span>");
                                    }
									selectedForeignKeyHtmlStringBuffer
											.append(oneSelectHtmlSb);
								}
								String[] strArray = new String[] {
										commonProperty.getName(),
										commonProperty.getMessageKey(),
										selectedForeignKeyHtmlStringBuffer
												.toString() };
								selectedForeignKeyHtmlList.add(strArray);
							}
						}else{
							// 如果外键是null，很可能是没有对应模块，此时不应该显示属性
							iter.remove();
						}
					}
				}
			}

			/* 添加已选属性 */
			for (int i = 0, n = config.getPropertyList().size(); i < n; i++) {
				SysTransportImportProperty property = (SysTransportImportProperty) config
						.getPropertyList().get(i);
				SysDictCommonProperty __prop = (SysDictCommonProperty) propertyMap.get(property.getFdName());
				if (__prop == null) {
					// 如果该属性不存在，则在编辑页面显示“该属性不存在，请删除”的提示
					__prop = new SysDictCommonProperty();
					__prop.setName(property.getFdName());
					__prop.setMessageKey("sys-transport:sysTransport.property.non.existent");
				}
				selectedOptions.add(__prop);
			}

			ConfigForm configForm = (ConfigForm) form;
			String realPath = ConfigLocationsUtil.getWebContentPath();
			logger.debug("realPath=" + realPath);
			SysDictCommonPropertyComparator comparator = new SysDictCommonPropertyComparator();
			Collections.sort(options, comparator);
			Collections.sort(primaryKeyOptionList, comparator);
			configForm.setOptionList(options);
			configForm.setPropertyList(selectedOptions);
			configForm.setForeignKeyString(foreignKeyStringBuffer.toString());
			configForm.setPrimaryKeyOptionList(primaryKeyOptionList);
			configForm
					.setForeignKeyPropertyOptionHtmlMap(foreignKeyPropertyOptionHtmlMap);
			request.setAttribute("modelMessageKey", dictModel.getMessageKey());
			request.setAttribute("selectedForeignKeyHtmlList",
					selectedForeignKeyHtmlList);
			request.setAttribute("notNullPropertyNames", notNullPropertyNameSb
					.toString());

			/* 设置主数据关键字 */
			List primaryKeyPropertyList = config.getPrimaryKeyPropertyList();
			SysTransportPrimaryKeyProperty primaryKeyProperty;
			if (primaryKeyPropertyList.size() > 0) {
				primaryKeyProperty = (SysTransportPrimaryKeyProperty) primaryKeyPropertyList
						.get(0);
				configForm.setPrimaryKey1(primaryKeyProperty.getFdName());
			}
			if (primaryKeyPropertyList.size() > 1) {
				primaryKeyProperty = (SysTransportPrimaryKeyProperty) primaryKeyPropertyList
						.get(1);
				configForm.setPrimaryKey2(primaryKeyProperty.getFdName());
			}
			if (primaryKeyPropertyList.size() > 2) {
				primaryKeyProperty = (SysTransportPrimaryKeyProperty) primaryKeyPropertyList
						.get(2);
				configForm.setPrimaryKey3(primaryKeyProperty.getFdName());
			}

			/* 编辑页面加入业务模块的扩展操作方法 */
			IBaseService baseService = (IBaseService) getBean(
					dictModel.getServiceBean());
			if (baseService != null
					&& baseService instanceof ISysTransportProvider) {
				ISysTransportProvider provider = (ISysTransportProvider) baseService;
				String extendPath = provider.getEditExtendPath();
				if (StringUtil.isNotNull(extendPath)) {
					request.setAttribute("extendPath", extendPath);
				}
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		TimeCounter.logCurrentTime("Action-edit", false, getClass());
		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
	}

	/**
	 * @param foreignKeyProperty
	 * @return
	 */
	private boolean isForeignKeyPropertyOptions(
			SysDictCommonProperty foreignKeyProperty) {
		// 不可显示则返回false
		if (!foreignKeyProperty.isCanDisplay()) {
            return false;
        }
		// 是外键也返回false
		if (isForeignKeyProperty(foreignKeyProperty)) {
            return false;
        }
		// 若为复合属性，则必须为非计算属性
		if (foreignKeyProperty instanceof SysDictComplexProperty) {
			SysDictComplexProperty property = (SysDictComplexProperty) foreignKeyProperty;
			if (property.isCalculated()) {
                return false;
            }
		}
		// 对应的数据库字段不为空
		if (StringUtil.isNull(foreignKeyProperty.getColumn())) {
            return false;
        }
		// 排除 RTF
		if ("RTF".equals(foreignKeyProperty.getType())) {
            return false;
        }

		return true;
	}

	/**
	 * 判断属性是否为导入时可选属性
	 * 
	 * @param commonProperty
	 * @return
	 */
	private boolean isOptionProperty(SysDictCommonProperty commonProperty) {
		if (commonProperty.isCanDisplay()
				&& StringUtil.isNull(commonProperty.getMessageKey())) {
            throw new IllegalArgumentException(commonProperty.getName()
                    + "的messageKey为空！");
        }
		return !commonProperty.isReadOnly() && commonProperty.isCanDisplay()
				&& !(commonProperty instanceof SysDictComplexProperty);
	}

	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			doSaveOrUpdate(form, request, messages, false);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return edit(mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	public ActionForward downloadTemplet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();
		String fdId = request.getParameter("fdId");
		SysTransportImportConfig config = (SysTransportImportConfig) getService()
				.findByPrimaryKey(fdId);
		SysDataDict dataDict = SysDataDict.getInstance();
		SysDictModel dictModel = dataDict.getModel(config.getFdModelName());
		Map propertyMap = dictModel.getPropertyMap();

		WorkBook workbook = new WorkBook();
		workbook.setLocale(request.getLocale());
		// workbook.setBundle("");
		Sheet sheet = new Sheet();
		String title = ResourceUtil.getString(dictModel.getMessageKey(),
				request.getLocale());
		title += ResourceUtil.getString(
				"sys-transport:sysTransport.import.data", request.getLocale());
		sheet.setTitle(title);
		if(UserOperHelper.allowLogOper(SysLogOperXml.LOGPOINT_FIND, SysTransportImportConfig.class.getName())){
		    //作用于fdModelName
		    UserOperHelper.setModelNameAndModelDesc(config.getFdModelName());
		    //记录的应该是机制的model
		    UserOperContentHelper.putFind(config);
		}
		List propertyList = config.getPropertyList();
		Collection propertyNameCollection = new ArrayList();
		for (Iterator iter = propertyList.iterator(); iter.hasNext();) {
			SysTransportImportProperty property = (SysTransportImportProperty) iter
					.next();
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap
					.get(property.getFdName());
			if(null==commonProperty){
				messages.addError(new KmssMessage("sys-transport:sysTransport.property.non.unuse",property.getFdName()));
				KmssReturnPage.getInstance(request).addButton(KmssReturnPage.BUTTON_RETURN).addMessages(messages).save(request);
				return getActionForward("failure", mapping, form, request, response);
			}
			String msg = ResourceUtil.getString(commonProperty.getMessageKey(),
					request.getLocale());
			// 如果是外键，则需要将该外键的所有已选属性都生成一列，名称为："外键名称.外键属性名称"
			if (isForeignKeyProperty(commonProperty)) {
				SysDictModel foreignKeyModel = dataDict.getModel(commonProperty
						.getType());
				Map keyMap = foreignKeyModel.getPropertyMap();
				List keyList = property.getKeyList();
				for (Iterator iterator = keyList.iterator(); iterator.hasNext();) {
					SysTransportImportKey key = (SysTransportImportKey) iterator
							.next();
					SysDictCommonProperty commonProperty2 = (SysDictCommonProperty) keyMap
							.get(key.getFdName());
					Column col = new Column();
					col.setTitle(msg
							+ "."
							+ ResourceUtil.getString(commonProperty2
									.getMessageKey(), request.getLocale()));
					// 外键是必填项，则其所以已选属性都是必填项
					if (commonProperty.isNotNull()) {
                        col.setRedFont(true);
                    }
					sheet.addColumn(col);
					propertyNameCollection.add(commonProperty2.getName());
				}
			} else {
				Column col = new Column();
				col.setTitle(msg);
				if (commonProperty.isNotNull()) {
                    col.setRedFont(true);
                }
				if (StringUtil.isNotNull(commonProperty.getEnumType())) {
					KmssEnumFormat format = new KmssEnumFormat();
					format.setEnumType(commonProperty.getEnumType());
					col.setFormat(format);
				}
				sheet.addColumn(col);
				propertyNameCollection.add(commonProperty.getName());

				// 多语言字段
				if (SysLangUtil.isLangEnabled()
						&& commonProperty.isLangSupport()) {
					Map<String, String> langs = SysLangUtil.getSupportedLangs();
					for (String lang : langs.keySet()) {
						if (lang.equals(SysLangUtil.getOfficialLang())) {
							continue;
						}
						Column col_lang = new Column();
						col_lang.setTitle(msg + "(" + langs.get(lang) + ")");
						sheet.addColumn(col_lang);
					}
				}
			}
		}

		/* 检查主数据关键字是否已被添加，如果没有则需要补充 */
		List primaryKeyPropertyList = config.getPrimaryKeyPropertyList();
		for (int i = 0; i < primaryKeyPropertyList.size(); i++) {
			SysTransportPrimaryKeyProperty primaryKeyProperty = (SysTransportPrimaryKeyProperty) primaryKeyPropertyList
					.get(i);
			String propertyName = primaryKeyProperty.getFdName();
			if (propertyNameCollection.contains(propertyName)) {
                continue;
            }
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap
					.get(propertyName);
			if (isForeignKeyProperty(commonProperty)) {
                continue;
            }
			String msg = ResourceUtil.getString(commonProperty.getMessageKey(),
					request.getLocale());
			Column col = new Column();
			col.setTitle(msg);
			sheet.addColumn(col);
			propertyNameCollection.add(propertyName);
		}

		sheet.setContentList(new ArrayList());
		workbook.addSheet(sheet);
		title += ResourceUtil.getString("sys-transport:sysTransport.templet",
				request.getLocale());
		// logger.debug("the fucking title is:" + title);
		// title = "fuckingName";
		String reqBrowser = request.getHeader("User-Agent");
		// 火狐浏览器,会将空格截断
		if (reqBrowser.toLowerCase().indexOf("firefox") > 0) {
		title = title.replaceAll("\\s*", "");
		}
		workbook.setFilename(title);
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + encodeFileName(request, title));
		ExcelOutput output = new ExcelOutputImp();
		output.output(workbook, response);

		return null;
	}

	public static String encodeFileName(HttpServletRequest request, String oldFileName)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1 || userAgent.indexOf("EDGE") > -1) {// ie情况处理
			oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
			// 这里的编码后，空格会被解析成+，需要重新处理
			oldFileName = oldFileName.replace("+", "%20");
		} else {
			oldFileName = new String(oldFileName.getBytes("UTF-8"), "ISO8859-1");
		}
		return oldFileName;
	}

	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String errorResult = "null";
		KmssMessages messages = new KmssMessages();
		try {
			isIdMatchModelName(request, messages);
			SysTransportImportUploadForm dyForm = (SysTransportImportUploadForm) form;
			String fdId = request.getParameter("fdId");
			FormFile file = dyForm.getFile();
			if (messages.hasError()) {
                messages.concat(getService().importData(file.getInputStream(),
                        fdId, request.getLocale(), true));
            }
			if (!messages.hasError()) {
                messages.concat(getService().importData(file.getInputStream(),
                        fdId, request.getLocale(), false));
            }
		} catch (Exception e) {
			logger.error(e.toString());
			if (e instanceof DataException) {
				
				messages.addError(new KmssMessage(
						"sys-transport:sysTransport.import.dataTruncation")
						.setThrowable(e).setMessageType(
								KmssMessage.MESSAGE_ERROR));
			} else {
				messages.addError(e);
			}
		}

		// 保存导入详情
		KmssMessages errorMsg = new KmssMessages();
		errorMsg.addError(messages.getMessages().remove(0));
		if (messages.hasError()) {
			errorResult = getService().saveExcelErrorJson(messages, request);
		}
		response.setCharacterEncoding("UTF-8");
		String successResult = getService()
				.getMessageInfo(errorMsg.getMessages().get(0));
		request.setAttribute("errorResult", StringUtil.isNotNull(errorResult)?errorResult:"");
		request.setAttribute("successResult", StringUtil.isNotNull(successResult)?successResult:"");

		return getActionForward("uploadResult", mapping, form, request, response);

	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
										String curOrderBy) throws Exception {
		if (curOrderBy == null) {
            curOrderBy = "sysTransportImportConfig.fdId desc";
        }
		return curOrderBy;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request,
										 HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		String whereBlock = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(whereBlock)) {
			whereBlock = "1=1 ";
		}
		String fdModelName = request.getParameter("fdModelName");
		if (!StringUtil.isNull(fdModelName)) {
			whereBlock += " and sysTransportImportConfig.fdModelName = :fdModelName ";
			hqlInfo.setParameter("fdModelName", fdModelName);
		}
		// 导入类型
		String[] importTypes = request.getParameterValues("q.importType");
		if (importTypes != null && importTypes.length > 0) {
			whereBlock += " and sysTransportImportConfig.fdImportType in (:fdImportType) ";
			List<Integer> fdImportType = new ArrayList<Integer>();
			for (String importType : importTypes) {
				fdImportType.add(Integer.valueOf(importType));
			}
			hqlInfo.setParameter("fdImportType", fdImportType);
		}
		hqlInfo.setWhereBlock(whereBlock);
		
		// 新UED查询
		CriteriaUtil.buildHql(new CriteriaValue(request), hqlInfo, SysTransportImportConfig.class);
	}

	public ActionForward showUploadForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			isIdMatchModelName(request, messages);
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			// KmssReturnPage.getInstance(request).addMessages(messages).addButton(
			// KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("upload", mapping, form, request, response);
		}
	}

	/**
	 * @param request
	 * @param messages
	 * @throws Exception
	 */
	private void isIdMatchModelName(HttpServletRequest request,
			KmssMessages messages) throws Exception {
		/* 检查对应model的fdModelName属性是否与URL中的匹配，主要用于校验权限 */
		String fdId = request.getParameter("fdId");
		String fdModelName = request.getParameter("fdModelName");
		Config config = (Config) getService().findByPrimaryKey(fdId);
		if (!config.getFdModelName().equals(fdModelName)) {
            messages
                    .addError(new KmssMessage(
                            "sys-transport:sysTransport.error.fdIdMissmatchFdModelName",
                            request.getLocale()));
        }
	}

	/**
	 * 
	 * 跳转到明细表导入页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detailTableImport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		KmssMessages messages = new KmssMessages();

		/*try {
			isIdMatchModelName(request, messages);
		} catch (Exception e) {
			messages.addError(e);
		}*/

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("upload", mapping, form, request, response);
		}
	}

	/**
	 * 明细表导入，做校验处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void detailTableUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Locale locale = request.getLocale();
			SysTransportImportUploadForm dyForm = (SysTransportImportUploadForm) form;
			FormFile file = dyForm.getFile();
			getService().detailTableValidate(file, request, response, locale);
		} catch (Exception e) {
			logger.error("Excel导入异常!");
			List errorList = new ArrayList();
			errorList.add(StringUtil.XMLEscape(getTrace(e)));
			e.printStackTrace();
			response.getWriter().write(
					"<script>parent.changeImportStatus(\"uploadFailure\");parent.showResultTr();parent.changeImportStatus(\"uploadFailure\");parent.callback(\'Excel导入异常:"
							+ JSONArray.fromObject(errorList)
							+ "\');</script>");
		}
	}

	/**
	 * 获取异常信息
	 * 
	 * @param t
	 * @return
	 */
	public String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}

}
