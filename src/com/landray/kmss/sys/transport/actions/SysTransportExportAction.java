package com.landray.kmss.sys.transport.actions;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictCommonPropertyComparator;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.profile.util.OrgImportExportUtil;
import com.landray.kmss.sys.transport.form.ConfigForm;
import com.landray.kmss.sys.transport.model.Config;
import com.landray.kmss.sys.transport.model.Property;
import com.landray.kmss.sys.transport.model.SysTransportExportConfig;
import com.landray.kmss.sys.transport.model.SysTransportExportProperty;
import com.landray.kmss.sys.transport.service.ISysListExportService;
import com.landray.kmss.sys.transport.service.ISysTransportExportService;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.WorkBook;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;



public class SysTransportExportAction extends ExtendAction {
	private ISysTransportExportService sysTransportExportService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return getService();
	}

	private ISysTransportExportService getService() {
		if (sysTransportExportService == null) {
            sysTransportExportService = (ISysTransportExportService) getBean("sysTransportExportService");
        }
		return sysTransportExportService;
	}

	protected ISysListExportService sysListExportService;

	protected ISysListExportService getSysListExportService(HttpServletRequest request) {
		if(sysListExportService == null){
			sysListExportService = (ISysListExportService)getBean("sysListExportService");
		}
		return sysListExportService;
	}
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

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
		Collection propertyCollection = dictModel.getPropertyMap().values();
		List options = new ArrayList();
		StringBuffer foreignKeyStringBuffer = new StringBuffer();
		Map foreignKeyPropertyOptionHtmlMap = new HashMap();
		for (Iterator iter = propertyCollection.iterator(); iter.hasNext();) {
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) iter
					.next();
			/* 只有可显示、非复合属性（SysDictListProperty、SysDictComplexProperty）才能导出 */
			if (isOptionProperty(commonProperty)) {
				options.add(commonProperty);
			}
		}
		Collections.sort(options, new SysDictCommonPropertyComparator());
		configForm.setOptionList(options);
		configForm.setForeignKeyString(foreignKeyStringBuffer.toString());
		configForm
				.setForeignKeyPropertyOptionHtmlMap(foreignKeyPropertyOptionHtmlMap);
		request.setAttribute("modelMessageKey", dictModel.getMessageKey());
		return form;
	}

	private boolean isOptionProperty(SysDictCommonProperty commonProperty) {
		if (commonProperty.isCanDisplay()
				&& StringUtil.isNull(commonProperty.getMessageKey())) {
            throw new IllegalArgumentException(commonProperty.getName()
                    + "的messageKey为空！");
        }
		return commonProperty.isCanDisplay();
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
		SysTransportExportConfig config;
		String fdId = request.getParameter("fdId");
		if (isSave) {
			config = new SysTransportExportConfig();
			// 为保证与新建页面fdId一致，这里需要获取页面中提交的fdId
			UserOperHelper.logAdd(getServiceImp(request).getModelName());
			if (StringUtil.isNotNull(fdId)) {
				config.setFdId(fdId);
			}
		} else {
		    UserOperHelper.logUpdate(getServiceImp(request).getModelName());
			config = (SysTransportExportConfig) getService().findByPrimaryKey(fdId);
		}
		//追加操作名称
        //UserOperHelper.appendEventType("-"+ResourceUtil.getString("table.sysTransportExportConfig", "sys-transport"));
        
		/* 设置一般属性 */
        IUserOper userOper = null;
		
		if (isSave) {
		    userOper = UserOperContentHelper.putAdd(config.getFdId(), configForm.getFdName(),SysTransportExportConfig.class.getName());
			config.setFdModelName(configForm.getFdModelName());
			config.setCreateTime(new Date());
			config.setCreator(UserUtil.getUser());
		}else{
            userOper = UserOperContentHelper.putUpdate(config.getFdId(), config.getFdName(),SysTransportExportConfig.class.getName());
        }
		UserOperContentHelper.putLog(isSave,userOper,"fdName",config.getFdName(),configForm.getFdName());
		UserOperContentHelper.putLog(isSave,userOper,"fdModelName",config.getFdModelName(),configForm.getFdModelName());
        config.setFdName(configForm.getFdName());
        
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
			SysTransportExportProperty property = new SysTransportExportProperty();
			property.setFdName(commonProperty.getName());
			property.setFdOrder(new Integer(i));
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

	@Override
	public ActionForward view(ActionMapping mapping, ActionForm form,
							  HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);

			/* 生成已选属性名称串 */
			String fdId = request.getParameter("fdId");
			SysTransportExportConfig config = (SysTransportExportConfig) getService()
					.findByPrimaryKey(fdId);
			SysDataDict dataDict = SysDataDict.getInstance();
			SysDictModel dictModel = dataDict.getModel(config.getFdModelName());
			request.setAttribute("modelMessageKey", dictModel.getMessageKey());
			Map propertyMap = dictModel.getPropertyMap();
			StringBuffer sb = new StringBuffer();
			for (Iterator iter = config.getPropertyList().iterator(); iter
					.hasNext();) {
				SysTransportExportProperty property = (SysTransportExportProperty) iter
						.next();
				SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap
						.get(property.getFdName());
				String propertyMessage = ResourceUtil.getString(commonProperty
						.getMessageKey(), request.getLocale());
				sb.append(propertyMessage).append("; ");
			}
			request.setAttribute("propertyNames", sb.toString());
		} catch (Exception e) {
			messages.addError(e);
		}

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
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);

			/* 获取域模型 */
			String fdId = request.getParameter("fdId");
			SysTransportExportConfig config = (SysTransportExportConfig) getService()
					.findByPrimaryKey(fdId);
			SysDataDict dataDict = SysDataDict.getInstance();
			SysDictModel dictModel = dataDict.getModel(config.getFdModelName());
			request.setAttribute("modelMessageKey", dictModel.getMessageKey());

			Collection selectedPropertyNameCollection = new HashSet();
			for (Iterator iter = config.getPropertyList().iterator(); iter
					.hasNext();) {
				SysTransportExportProperty property = (SysTransportExportProperty) iter
						.next();
				selectedPropertyNameCollection.add(property.getFdName());
			}

			Collection propertyCollection = dictModel.getPropertyMap().values();
			List options = new ArrayList();
			List selectedOptions = new ArrayList();
			StringBuffer foreignKeyStringBuffer = new StringBuffer();

			for (Iterator iter = propertyCollection.iterator(); iter.hasNext();) {
				SysDictCommonProperty commonProperty = (SysDictCommonProperty) iter
						.next();
				/*
				 * 只有非只读、可显示、非复合属性（SysDictListProperty、SysDictComplexProperty）才能被导入
				 */
				if (isOptionProperty(commonProperty)) {
					options.add(commonProperty);
					// 如果该属性为已选属性，则添加到selectedOptions中
					if (selectedPropertyNameCollection.contains(commonProperty
							.getName())) {
                        selectedOptions.add(commonProperty);
                    }
				}
			}
			ConfigForm configForm = (ConfigForm) form;
			Collections.sort(options, new SysDictCommonPropertyComparator());
			configForm.setOptionList(options);
			selectedOptions = sortSelectedOptions(config.getPropertyList(),
					selectedOptions);
			configForm.setPropertyList(selectedOptions);
			configForm.setForeignKeyString(foreignKeyStringBuffer.toString());
			request.setAttribute("modelMessageKey", dictModel.getMessageKey());
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("edit", mapping, form, request, response);
		}
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

	public ActionForward export(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			isIdMatchModelName(request, messages);
			if (!messages.hasError()) {
				String fdId = request.getParameter("fdId");
				WorkBook workbook = getService().buildWorkBook(fdId,
						request.getLocale());
				response.setContentType("multipart/form-data");
				response.setHeader("Content-Disposition", "attachment;fileName=" + SysTransportImportAction.encodeFileName(request, workbook.getFilename()));
				ExcelOutput output = new ExcelOutputImp();
				output.output(workbook, response);
				return null;
			}
		} catch (Exception e) {
			messages.addError(e);
		}

		if (messages.hasError()) {
			KmssReturnPage.getInstance(request).addMessages(messages).save(
					request);
			return getActionForward("failure", mapping, form, request, response);
		} else {
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	protected String getFindPageOrderBy(HttpServletRequest request,
										String curOrderBy) throws Exception {
		if (curOrderBy == null) {
            curOrderBy = "sysTransportExportConfig.fdId desc";
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
			whereBlock += " and sysTransportExportConfig.fdModelName= :fdModelName ";
			hqlInfo.setParameter("fdModelName", fdModelName);
		}
		hqlInfo.setWhereBlock(whereBlock);
		
		// 新UED查询
		CriteriaValue cv = new CriteriaValue(request);
		CriteriaUtil.buildHql(cv, hqlInfo, SysTransportExportConfig.class);
	}

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

	private List sortSelectedOptions(List propertyList, List selectedOptions) {
		List orderedSelectedOptions = new ArrayList();
		for (Iterator iter = propertyList.iterator(); iter.hasNext();) {
			Property property = (Property) iter.next();
			for (Iterator iter2 = selectedOptions.iterator(); iter2.hasNext();) {
				SysDictCommonProperty commonProperty = (SysDictCommonProperty) iter2
						.next();
				if (commonProperty.getName().equals(property.getFdName())) {
					orderedSelectedOptions.add(commonProperty);
					break;
				}
			}
		}
		return orderedSelectedOptions;
	}

	/**
	 * 明细表的下载模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deTableExportTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		getService().deTableDownloadTemplate(request, response, locale);
		return null;

	}

	/**
	 * 明细表导出 阅读状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void detailTableExportData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Locale locale = request.getLocale();
		getService().detailsTableExportData(request, response,
				ResourceUtil.getLocaleByUser());
	}

	/**
	 * 明细表导出 编辑状态
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void detailTableExportDataInEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		getService().detailsTableExportDataInEdit(request, response);
	}

	public ActionForward listExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String json = request.getParameter("json");
			 json = URLDecoder.decode(json, "UTF-8");
			String ths = request.getParameter("ths");
			// json = json.replace("\\\"", "&quot;");
			ths=URLDecoder.decode(ths,"UTF-8");
			String[] Headers=ths.split(",");
			JSONArray jsonData = (JSONArray) JSONValue.parse(json);
			List dataList = jsonData.subList(0, jsonData.size());
			String fdModelName = request.getParameter("fdModelName");
			String fileNameKey = request.getParameter("fileNameKey");
			if(UserOperHelper.allowLogOper("listExport", null)){
			    UserOperHelper.setModelNameAndModelDesc(fdModelName);
			    for(Object data : dataList){
	                List lvl1 = (List)((List)data).get(1);
	                List seq=(List) lvl1.get(0);
	                String seqValue=String.valueOf(seq.get(1));
	                List subject=(List) lvl1.get(1);
	                String subjectValue = String.valueOf(subject.get(1)); 
	                UserOperContentHelper.putFind(seqValue, subjectValue, fdModelName);
	            }
			}
			
			// 传入文件名称
			String key = "sys-transport:sysTransport.listDataExport";
			if (StringUtil.isNotNull(fileNameKey)) {
				String temp = ResourceUtil.getString(fileNameKey);
				if (StringUtil.isNotNull(temp)) {
					key = fileNameKey;
				}
			}
			String exportName = ResourceUtil
					.getString(key);
			
			HSSFWorkbook workbook = getSysListExportService(request).exportWorkBook(Headers, dataList);
	            response.setContentType("multipart/form-data");
	            response.setHeader("Content-Disposition", "attachment;fileName=" + OrgImportExportUtil.encodeFileName(request, exportName));
	            OutputStream out = (OutputStream)response.getOutputStream();
	            workbook.write(out);
	            return null;
		} catch (Exception e) {
			messages.addError((Throwable)e);
            if (messages.hasError()) {
                KmssReturnPage.getInstance(request).addMessages(messages).save(request);
                return this.getActionForward("failure", mapping, form, request, response);
            }
            KmssReturnPage.getInstance(request).addMessages(messages).addButton(0).save(request);
            return this.getActionForward("success", mapping, form, request, response);
        }
		
	}
}
