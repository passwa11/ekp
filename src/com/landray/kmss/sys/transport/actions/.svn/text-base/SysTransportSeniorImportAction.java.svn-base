package com.landray.kmss.sys.transport.actions;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.config.dict.SysDictAttachmentProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.transport.form.SysTransportImportUploadForm;
import com.landray.kmss.sys.transport.service.ISysTransportSeniorImportService;
import com.landray.kmss.sys.xform.base.service.controls.seniorDetailsTable.SysFormDetailsTableUtil;
import com.landray.kmss.sys.xform.base.service.controls.seniorDetailsTable.form.SysFormDetailsTableDataForm;
import com.landray.kmss.sys.xform.base.service.controls.seniorDetailsTable.form.SysFormDetailsTableMainForm;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import com.landray.kmss.web.util.RequestUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class SysTransportSeniorImportAction extends SysTransportImportAction {
	private ISysTransportSeniorImportService sysTransportSeniorImportService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String IMPORTING_STATUS = "importing";
	private final String FINISHED_STATUS = "finished";

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return getService();
	}
	private ISysTransportSeniorImportService getService() {
		if (sysTransportSeniorImportService == null){
			sysTransportSeniorImportService = (ISysTransportSeniorImportService) SpringBeanUtil.getBean("sysTransportSeniorImportService");
		}
		return sysTransportSeniorImportService;
	}

	/**
	 * 高级明细表导入，做校验处理
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void detailTableUpload(ActionMapping mapping,
								  ActionForm form, HttpServletRequest request,
								  HttpServletResponse response) throws Exception {
		try {
			Locale locale = request.getLocale();
			SysTransportImportUploadForm dyForm = (SysTransportImportUploadForm) form;
			FormFile file = dyForm.getFile();
			this.getService().detailTableValidate(file, request, response, locale);
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
	 * 高级明细表更新导入的数据
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void updateImportData(ActionMapping mapping,
								 ActionForm form, HttpServletRequest request,
								 HttpServletResponse response) throws Exception{
		Map<String,Object> map = new HashMap<>();
		Boolean isUpdate = false;
		ActionForm instance = null;
		instance = (ActionForm) BeanUtils.instantiateClass(SysFormDetailsTableDataForm.class);
		//instance.reset(mapping, request);
		RequestUtils.populate(instance, request);
		// 先更新数据
		SysFormDetailsTableDataForm detailsTableDataForm = (SysFormDetailsTableDataForm) instance;
		List<SysFormDetailsTableMainForm> detailsTableMainForms = buildDetailsTableMainForm(detailsTableDataForm);
		RequestContext requestContext = new RequestContext(request);
		if (!ArrayUtil.isEmpty(detailsTableMainForms)) {
			for (int i = 0, length = detailsTableMainForms.size(); i < length; i++) {
				SysFormDetailsTableMainForm detailsTableMainForm = (SysFormDetailsTableMainForm)detailsTableMainForms.get(i);
				this.getService().updateImportData(detailsTableMainForm, requestContext);
			}
		}
		String keyCache = (String)request.getParameter("keyCache");
		String maxLimitedNum = (String)request.getParameter("maxLimitedNum");
		String importStatus = (String)request.getSession().getAttribute(keyCache);

		// 判断是否还需要继续渲染
		if(IMPORTING_STATUS.equals(importStatus)){
		   // 判断缓存中数据是否已经渲染完成
			List renderPageList = this.getService().getExcelRenderPageList(keyCache,maxLimitedNum);
			if(renderPageList!=null && renderPageList.size()>0){
				map.put("renderList",renderPageList);
				map.put("importStatus",IMPORTING_STATUS);

			}else{
				// 数据已渲染完成，1.清除缓存，2.更新session中key的状态，3.调高级明细表的action渲染第一页数据
				Boolean isClear = this.getService().clearRenderCache(keyCache);
				importStatus = FINISHED_STATUS;
				request.getSession().setAttribute(keyCache,FINISHED_STATUS);
				map.put("importStatus",FINISHED_STATUS);

			}
		}else if(FINISHED_STATUS.equals(importStatus)){
			map.put("importStatus",FINISHED_STATUS);
		}
		map.put("keyCache",keyCache);
		JSONObject json = JSONObject.fromObject(map);
		response.getWriter().write(json.toString());
	}

	private List<SysFormDetailsTableMainForm> buildDetailsTableMainForm(SysFormDetailsTableDataForm detailsTableDataForm) throws Exception {
		Map<String, Object> formData = detailsTableDataForm.getExtendDataFormInfo().getFormData();
		String fdControlId = detailsTableDataForm.getFdControlId();
		String fdExtendFilePath = detailsTableDataForm.getFdExtendFilePath();
		DictLoadService dictLoadService = (DictLoadService)SpringBeanUtil.getBean("sysDictLoader");
		SysDictExtendModel dictExtendModel = dictLoadService.loadDictByFileName(fdExtendFilePath);
		Map<String, SysDictAttachmentProperty> subTableAttDict = SysFormDetailsTableUtil.getSubTableAttDict(dictExtendModel, fdControlId);
		List detailsData = (List) formData.get(fdControlId);
		AutoHashMap attachmentForms = detailsTableDataForm.getAttachmentForms();
		if (ArrayUtil.isEmpty(detailsData)) {
			detailsData = new ArrayList();
		}
		int len = detailsData.size();
		List<SysFormDetailsTableMainForm> detailsTableMainForms = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			Map rowData = (Map)detailsData.get(i);
			List<Object> oneRowData = new ArrayList<>();
			oneRowData.add(rowData);
			SysFormDetailsTableMainForm detailsTableMainForm = new SysFormDetailsTableMainForm();
			detailsTableDataForm.copyBaseInfo(detailsTableMainForm);
			Map<String, Object> rowFormData = detailsTableMainForm.getExtendDataFormInfo().getFormData();
			rowFormData.put(fdControlId, oneRowData);

			subTableAttDict.entrySet().stream().forEach(entry -> {
				String key = entry.getKey();
				String uid = (String) rowData.get(key);
				Object attForm = attachmentForms.get(uid);
				detailsTableMainForm.getAttachmentForms().put(uid, attForm);
			});
			detailsTableMainForms.add(detailsTableMainForm);
		}
		return detailsTableMainForms;
	}



}
