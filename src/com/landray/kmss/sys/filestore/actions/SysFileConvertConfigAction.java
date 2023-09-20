package com.landray.kmss.sys.filestore.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.sys.config.design.SysCfgModule;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.filestore.forms.SysFileConvertConfigForm;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import edu.emory.mathcs.backport.java.util.Arrays;
import static com.landray.kmss.sys.filestore.constant.ConvertConstant.*;

public class SysFileConvertConfigAction extends ExtendAction {

	protected ISysFileConvertConfigService sysFileConvertConfigService;
  
	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysFileConvertConfigService == null) {
			sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil
					.getBean("sysFileConvertConfigService");
		}
		return sysFileConvertConfigService;
	}

	@Override
	protected void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
		super.changeFindPageHQLInfo(request, hqlInfo);
		ISysFileConvertConfigService configService = (ISysFileConvertConfigService) getServiceImp(request);
		String isConverterAspose = configService.findAppConfigValue("fdKey ='attconvert.converter.type.aspose'", "false");
		String isConverterYOZO = configService.findAppConfigValue("fdKey ='attconvert.converter.type.yozo'", "false");
		String where = hqlInfo.getWhereBlock();
		if (StringUtil.isNull(where)) {
			where = " 1=1 ";
		}
		CriteriaValue cv = new CriteriaValue(request);

		String[] ss = null;
		ss = cv.get("fdFileExtName");
		if (ss != null && ss.length > 0) {
			where += " and sysFileConvertConfig.fdFileExtName like :fdFileExtName ";
			hqlInfo.setParameter("fdFileExtName", "%" + ss[0] + "%");
		}

		ss = cv.get("fdModelName");
		if (ss != null && ss.length > 0) {
			where += " and sysFileConvertConfig.fdModelName like :fdModelName ";
			hqlInfo.setParameter("fdModelName", "%" + ss[0] + "%");
		}

		ss = cv.get("fdConverterKey");
		if (ss != null && ss.length > 0) {
			where += " and sysFileConvertConfig.fdConverterKey like :fdConverterKey ";
			hqlInfo.setParameter("fdConverterKey", "%" + ss[0] + "%");
		}
		
		String converter = "";
		if(isConverterAspose != null && "true".equals(isConverterAspose)) {
			converter += " sysFileConvertConfig.fdConverterType like :convertAspose or sysFileConvertConfig.fdConverterType is null or ";
			hqlInfo.setParameter("convertAspose", "aspose");
		}
		
		if(isConverterYOZO != null && "true".equals(isConverterYOZO)) {
			converter += "  sysFileConvertConfig.fdConverterType like :convertyozo or ";
			hqlInfo.setParameter("convertyozo", "yozo");
		}

		if(FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_WPS, false)) {
			converter += " sysFileConvertConfig.fdConverterType like :convertywps or ";
			hqlInfo.setParameter("convertywps", "wps");
		}

		if (FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_WPS_CENTER, false)) {
			converter += " sysFileConvertConfig.fdConverterType like :convertywpsCenter or ";
			hqlInfo.setParameter("convertywpsCenter", "wpsCenter");
		}

		if (FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_DIANJU, false)) {
			converter += " sysFileConvertConfig.fdConverterType like :convertyDianju or ";
			hqlInfo.setParameter("convertyDianju", "dianju");
		}

		if (FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_FOXIT, false)) {
			converter += " sysFileConvertConfig.fdConverterType like :convertyFoxit or ";
			hqlInfo.setParameter("convertyFoxit", "foxit");
		}
		
		
		if(FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_SHUKE, false)) {
			converter += " sysFileConvertConfig.fdConverterType like :convertyskofd or";
			hqlInfo.setParameter("convertyskofd", "skofd");
		}
		
		if(!"".equals(converter)) {
			converter = converter.substring(0, converter.lastIndexOf("or"));
			where += " and (" + converter + ")";
		}
		

		hqlInfo.setWhereBlock(where);
	}

	public ActionForward changeStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-changeStatus", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("List_Selected");
			String changeType = request.getParameter("changeType");
			((ISysFileConvertConfigService) getServiceImp(request)).changeConfigStatus(ids, changeType);
		} catch (Exception e) {
			messages.addError(e);
		}
		KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-changeStatus", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	@Override
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		handleForm(form);
		return super.save(mapping, form, request, response);
	}

	@Override
	public ActionForward saveadd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		handleForm(form);
		super.save(mapping, form, request, response);
		String urlPrefix = ResourceUtil.getKmssConfig("kmss.urlPrefix").get("kmss.urlPrefix");
		response.sendRedirect(urlPrefix+"/sys/filestore/sys_filestore/sysFileConvertConfig.do?method=add&addConfig=1");
		return null;
	}
	
	
	@Override
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String addConfig = request.getParameter("addConfig");
		if("1".equals(addConfig)){
			KmssReturnPage.getInstance(request).save(request);;
		}
	
		SysFileConvertConfigForm configForm = (SysFileConvertConfigForm) form;
		ISysFileConvertConfigService configService = (ISysFileConvertConfigService) getServiceImp(request);
		String isConverterAspose = configService.findAppConfigValue("fdKey ='attconvert.converter.type.aspose'", "false");
		String isConverterYOZO = configService.findAppConfigValue("fdKey ='attconvert.converter.type.yozo'", "false");
		String isConverterWPS = configService.findAppConfigValue("fdKey ='attconvert.converter.type.wps'", "false");
		String isConverterSKOFD = configService.findAppConfigValue("fdKey ='attconvert.converter.type.skofd'", "false");
		String isConverterWPSCenter = configService.findAppConfigValue("fdKey ='attconvert.converter.type.wpsCenter'", "false");
		String isConverterDianju = configService.findAppConfigValue("fdKey ='attconvert.converter.type.dianju'", "false");
		String isConverterFoxit = configService.findAppConfigValue("fdKey ='attconvert.converter.type.foxit'", "false");
		configForm.setConverter_aspose(isConverterAspose);
		configForm.setConverter_yozo(isConverterYOZO);
		configForm.setConverter_wps(isConverterWPS);
		configForm.setConverter_skofd(isConverterSKOFD);
		configForm.setConverter_wpsCenter(isConverterWPSCenter);
		configForm.setConverter_dianju(isConverterDianju);
		configForm.setConverter_foxit(isConverterFoxit);
		
		return super.add(mapping, form, request, response);
	}

	


	@Override
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SysFileConvertConfigForm configForm = (SysFileConvertConfigForm) form;
		ISysFileConvertConfigService configService = (ISysFileConvertConfigService) getServiceImp(request);
		String isConverterAspose = configService.findAppConfigValue("fdKey ='attconvert.converter.type.aspose'", "false");
		String isConverterYOZO = configService.findAppConfigValue("fdKey ='attconvert.converter.type.yozo'", "false");
		String isConverterWPS = configService.findAppConfigValue("fdKey ='attconvert.converter.type.wps'", "false");
		String isConverterSKOFD = configService.findAppConfigValue("fdKey ='attconvert.converter.type.skofd'", "false");
		String isConverterWPSCenter = configService.findAppConfigValue("fdKey ='attconvert.converter.type.wpsCenter'", "false");
		String isConverterDianju = configService.findAppConfigValue("fdKey ='attconvert.converter.type.dianju'", "false");
		String isConverterFoxit = configService.findAppConfigValue("fdKey ='attconvert.converter.type.foxit'", "false");
		configForm.setConverter_aspose(isConverterAspose);
		configForm.setConverter_yozo(isConverterYOZO);
		configForm.setConverter_wps(isConverterWPS);
		configForm.setConverter_skofd(isConverterSKOFD);
		configForm.setConverter_wpsCenter(isConverterWPSCenter);
		configForm.setConverter_dianju(isConverterDianju);
		configForm.setConverter_foxit(isConverterFoxit);
		String[] moduls = {"*.km.*", "*.kms.*", "*.sys.*"}; //模块信息
		List<String> listModuls = Arrays.asList(moduls);
		TimeCounter.logCurrentTime("Action-edit", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			loadActionForm(mapping, form, request, response);
			SysFileConvertConfigForm conForm = (SysFileConvertConfigForm) form;
			String fdModulName = conForm.getFdModelName();
			conForm.setFdFileExtNameId(conForm.getFdFileExtName());
			if(StringUtil.isNull(conForm.getFdConverterType()) && StringUtil.isNotNull(conForm.getFdConverterKey())
					&& !"image2thumbnail".equals(conForm.getFdConverterKey())) {
				conForm.setFdConverterType("aspose");
			}
			
			if(StringUtil.isNotNull(conForm.getFdHighFidelity()) && "1".equals(conForm.getFdHighFidelity())) {
				conForm.setFdHighFidelity("true");
			}
			else {
				conForm.setFdHighFidelity("false");
			}
			//附件所属模块：文字信息
			if(StringUtil.isNotNull(fdModulName)) {
				if("*".equals(fdModulName)) {  //不限模块
					conForm.setAllModuls("true");
					conForm.setFdModelName("");
				}
				else {
					String[] fdModulNames = fdModulName.split("、");
					String fdModul = "";
					for(String convertKey : fdModulNames) {
						String format = convertKey.replace("*","");
						format = format.replaceAll("[.]", "/");
						
						if(listModuls.contains(convertKey)) { //兼容历史数据--模块信息
							fdModul += getModul(convertKey)  + "、";
						}
						else {
							SysCfgModule moduleUrl  = SysConfigs.getInstance().getModule(format);
							if(moduleUrl != null) {
								String[] messageKeyss = moduleUrl.getMessageKey().split(":");
								fdModul += ResourceUtil.getString(messageKeyss[1], messageKeyss[0]) + "、";
							}
						}
					
						
					}
					
					if(!"".equals(fdModul)) {
						fdModul = fdModul.substring(0, fdModul.lastIndexOf("、"));
					}
					conForm.setFdModul(fdModul);
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

	public String getModul(String key) {
		Map<String, String> moduleMap = new HashMap<String, String>();
		moduleMap.put("*.km.*", ResourceUtil.getString("sysFileConvertConfig.fdModelType.kmModule","sys-filestore")); // KM模块
		moduleMap.put("*.kms.*", ResourceUtil.getString("sysFileConvertConfig.fdModelType.kmsModule","sys-filestore")); // KMS模块
		moduleMap.put("*.sys.*", ResourceUtil.getString("sysFileConvertConfig.fdModelType.systemModule","sys-filestore")); // 系统模块
       
		return moduleMap.get(key);
	}
	
	@Override
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		handleForm(form);
		return super.update(mapping, form, request, response);
	}

	private void handleForm(ActionForm form) {
		SysFileConvertConfigForm configForm = (SysFileConvertConfigForm) form;
		configForm.setFdStatus("1");
		String fdConverterKey = configForm.getFdConverterKey();
		String fdConverterType = configForm.getFdConverterType();
        String allModuls = configForm.getAllModuls(); 
		if("true".equals(allModuls)) {
			configForm.setFdModelName("*");
		}
		
		if("JPG".equals(fdConverterKey) || "WPS".equals(fdConverterType)) {
			configForm.setFdDispenser("local");
		}
		else {
			configForm.setFdDispenser("remote");
		}
		if(StringUtil.isNotNull(configForm.getFdHighFidelity()) && "true".equals(configForm.getFdHighFidelity())) {
			configForm.setFdHighFidelity("1");
		}
		else {
			configForm.setFdHighFidelity("0");
		}
		//configForm.setFdHighFidelity("0");
	}


	public ActionForward delConfigs(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-delConfigs", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String[] ids = request.getParameterValues("selected");
			((ISysFileConvertConfigService) getServiceImp(request)).deleteConfigs(ids);
		} catch (Exception e) {
			messages.addError(e);
		}
		TimeCounter.logCurrentTime("Action-delConfigs", false, getClass());
		return getActionForward("success", mapping, form, request, response);
	}
}
