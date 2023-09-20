package com.landray.kmss.sys.filestore.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.filestore.util.FileStoreConvertUtil;
import com.landray.kmss.sys.filestore.util.SysFileStoreUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.filestore.forms.SysFileConvertGlobalConfigForm;
import com.landray.kmss.sys.filestore.model.SysFileConvertConfig;
import com.landray.kmss.sys.filestore.service.ISysFileConvertConfigService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.util.SpringBeanUtil;

import static com.landray.kmss.sys.filestore.constant.ConvertConstant.*;

public class SysFileConvertGlobalConfigAction extends ExtendAction {

	protected ISysFileConvertConfigService sysFileConvertConfigService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (sysFileConvertConfigService == null) {
			sysFileConvertConfigService = (ISysFileConvertConfigService) SpringBeanUtil
					.getBean("sysFileConvertConfigService");
		}
		return sysFileConvertConfigService;
	}

	public ActionForward saveGlobal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISysFileConvertConfigService configService = (ISysFileConvertConfigService) getServiceImp(request);
		SysFileConvertGlobalConfigForm globalConfigForm = configService.getGlobalConfigForm();
		SysFileConvertGlobalConfigForm getGlobalConfigForm = (SysFileConvertGlobalConfigForm) form;
		globalConfigForm.setAttConvertEnable(getGlobalConfigForm.getAttConvertEnable());
		globalConfigForm.setConverter_aspose(getGlobalConfigForm.getConverter_aspose());
		globalConfigForm.setConverter_skofd(getGlobalConfigForm.getConverter_skofd());
		globalConfigForm.setConverter_wps(getGlobalConfigForm.getConverter_wps());
		globalConfigForm.setConverter_yozo(getGlobalConfigForm.getConverter_yozo());
		globalConfigForm.setConverter_wps_center(getGlobalConfigForm.getConverter_wps_center());
		globalConfigForm.setConverter_dianju(getGlobalConfigForm.getConverter_dianju());
		globalConfigForm.setConverter_foxit(getGlobalConfigForm.getConverter_foxit());
		configService.clearCache();
		configService.saveGlobalInfos(globalConfigForm);
		return config(mapping, form, request, response);
	}

	/**
	 * 添加或删除配置WPS记录
	 * 
	 * @param configService
	 * @param isConvert 是添加还是删除
	 * @throws Exception
	 */
	public void createConvertConfig(ISysFileConvertConfigService configService, String convertType, boolean isConvert) throws Exception  {
		if(configService != null) {
			List<SysFileConvertConfig> sysFileConvertConfig = new ArrayList<SysFileConvertConfig>(10);
			if ("wps".equals(convertType) || "wpsCenter".equals(convertType)
					|| "dianju".equals(convertType) || "foxit".equals(convertType)) {
				sysFileConvertConfig = configService.getConfigsByTypeAndKey(convertType, new String[] {"toOFD","toPDF"});
			} else {
				sysFileConvertConfig = configService.getConfigsByTypeAndKey(convertType,  new String[] {"toOFD"});
			}

			 //添加记录
			 if(isConvert && sysFileConvertConfig.size() <= 0) {
				 SysFileConvertConfig config = new SysFileConvertConfig();
				 config.setFdId(UUID.randomUUID().toString());
				 config.setFdFileExtName("doc、docx、wps");
				 config.setFdModelName("*");
				 config.setFdConverterKey("toOFD");
				 config.setFdDispenser("remote");
				 config.setFdStatus("1");
				 config.setFdConverterType(convertType);
				 config.setFdHighFidelity("0");
				 config.setFdPicResolution("96");
				 config.setFdPicRectangle("A3");
				 configService.add(config);

				 if ("wps".equals(convertType)
						 || "wpsCenter".equals(convertType)
				         || "dianju".equals(convertType)
						 || "foxit".equals(convertType)) {
					 config = new SysFileConvertConfig();
					 config.setFdId(UUID.randomUUID().toString());
					 config.setFdFileExtName("doc、docx、wps");
					 config.setFdModelName("*");
					 config.setFdConverterKey("toPDF");
					 config.setFdDispenser("remote");
					 config.setFdStatus("1");
					 config.setFdConverterType(convertType);
					 config.setFdHighFidelity("0");
					 config.setFdPicResolution("96");
					 config.setFdPicRectangle("A3");
					 configService.add(config);

				 }

				 SysFileStoreUtil.updateConfigCache();

			 }
			 
			 //删除记录
			 if(!isConvert && sysFileConvertConfig.size() > 0) {
			 	for (SysFileConvertConfig sfcc : sysFileConvertConfig) {
					configService.delete(sfcc);
				}

				 SysFileStoreUtil.updateConfigCache();
			 }
		}
	}
	
	public ActionForward config(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISysFileConvertConfigService configService = (ISysFileConvertConfigService) getServiceImp(request);
		configService.enableDefaultConvertConfig();
		SysFileConvertGlobalConfigForm globalConfigForm = configService.getGlobalConfigForm();
		SysFileConvertGlobalConfigForm targetForm = (SysFileConvertGlobalConfigForm) form;
		copyGlobalConfigs(globalConfigForm, targetForm);
		request.setAttribute("hideNames", targetForm.getHideNames());
		//历史兼容处理--aspose
		if("false".equals(targetForm.getConverter_aspose())) {
			ISysFileConvertDataService convertDataService = (ISysFileConvertDataService) SpringBeanUtil
					.getBean("sysFileConvertDataService");
			Boolean converterClient = convertDataService.isConvertClientUpdate("Aspose");
			if(converterClient) {
				targetForm.setConverter_aspose("true");
				configService.saveGlobalInfos(targetForm);
			}
	
			
		}

		// 是否显示转换器
		new SetAttributeBuilder(request, globalConfigForm)
				.setWPSCenterAttribute().setWPSAttribute()
				.setDianjuAttribute().setFoxitAttribute().setShukeAttribute();
		configService.clearCache();
		return getActionForward("config", mapping, form, request, response);
	}


	/**
	 * 新版点击提交时保存：保存转换配置参数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveGlobalConvert(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISysFileConvertConfigService configService = (ISysFileConvertConfigService) getServiceImp(request);
		SysFileConvertGlobalConfigForm getGlobalConfigForm = (SysFileConvertGlobalConfigForm) form;
		SysFileConvertGlobalConfigForm globalConfigForm = configService.getGlobalConfigForm();
		globalConfigForm.setAttConvertOldSuccessUseHTML(getGlobalConfigForm.getAttConvertOldSuccessUseHTML());
		globalConfigForm.setDistributeThreadSleepTime(getGlobalConfigForm.getDistributeThreadSleepTime());
		globalConfigForm.setUnsignedTaskGetNum(getGlobalConfigForm.getUnsignedTaskGetNum());
		globalConfigForm.setLongTaskSize(getGlobalConfigForm.getLongTaskSize());
		configService.saveGlobalInfos(globalConfigForm);
		configService.clearCache();
		return getActionForward("success", mapping, form, request, response);
	}
	
	/**
	 * 转换服务配置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward convertServerConfig(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISysFileConvertConfigService configService = (ISysFileConvertConfigService) getServiceImp(request);
		configService.enableDefaultConvertConfig();
		SysFileConvertGlobalConfigForm globalConfigForm = configService.getGlobalConfigForm();
		SysFileConvertGlobalConfigForm targetForm = (SysFileConvertGlobalConfigForm) form;
		copyGlobalConfigs(globalConfigForm, targetForm);
		request.setAttribute("hideNames", targetForm.getHideNames());
		return getActionForward("convertServerConfig", mapping, form, request, response);
	}
	
	
	private void copyGlobalConfigs(SysFileConvertGlobalConfigForm globalConfigForm,
			SysFileConvertGlobalConfigForm targetForm) {
		targetForm.setAttConvertEnable(globalConfigForm.getAttConvertEnable());
		targetForm.setAttConvertOldSuccessUseHTML(globalConfigForm.getAttConvertOldSuccessUseHTML());
		targetForm.setAttConvertHighFidelityDoc(globalConfigForm.getAttConvertHighFidelityDoc());
		targetForm.setAttConvertHighFidelityDocx(globalConfigForm.getAttConvertHighFidelityDocx());
		targetForm.setAttConvertHighFidelityPpt(globalConfigForm.getAttConvertHighFidelityPpt());
		targetForm.setAttConvertHighFidelityPptx(globalConfigForm.getAttConvertHighFidelityPptx());
		targetForm.setAttConvertHighFidelityPdf(globalConfigForm.getAttConvertHighFidelityPdf());
		targetForm.setAttConvertHighFidelityWps(globalConfigForm.getAttConvertHighFidelityWps());
		targetForm.setHideNames(globalConfigForm.getHideNames());
		targetForm.setDistributeThreadSleepTime(globalConfigForm.getDistributeThreadSleepTime());
		targetForm.setUnsignedTaskGetNum(globalConfigForm.getUnsignedTaskGetNum());
		targetForm.setLongTaskSize(globalConfigForm.getLongTaskSize());
		targetForm.setConverter_aspose(globalConfigForm.getConverter_aspose());
		targetForm.setConverter_skofd(globalConfigForm.getConverter_skofd());
		targetForm.setConverter_wps(globalConfigForm.getConverter_wps());
		targetForm.setConverter_yozo(globalConfigForm.getConverter_yozo());
		targetForm.setConverter_wps_center(globalConfigForm.getConverter_wps_center());
		targetForm.setConverter_dianju(globalConfigForm.getConverter_dianju());
		targetForm.setConverter_foxit(globalConfigForm.getConverter_foxit());
	}

	/**
	 * 显示控件设置
	 */
   class SetAttributeBuilder {
	   HttpServletRequest request;
	   SysFileConvertGlobalConfigForm globalConfigForm;
		public SetAttributeBuilder(HttpServletRequest request,
								   SysFileConvertGlobalConfigForm globalConfigForm) {
			this.request = request;
			this.globalConfigForm = globalConfigForm;
		}

		/*显示WPS逻辑处理
		 *1.集成中开启WPS并且使用Linux，则配置页面显示
		 *2.勾选WPS，则自动添加数据，反之则删除
		 *3.关闭集成中的WPS，则添加的数据也要删除
		 */
		public SetAttributeBuilder setWPSAttribute() {

			request.setAttribute("convertWPS", false);
			request.setAttribute("convertWPSTip", false);

			if(FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_WPS, true)) {
				request.setAttribute("convertWPS", true);
				request.setAttribute("convertWPSTip", Boolean.valueOf(globalConfigForm.getConverter_wps()));
			}
			return this;
		}

		/*
		 * 显示WPS中台
		 */
	   public SetAttributeBuilder setWPSCenterAttribute() {
		   request.setAttribute("convertWPSCenter", false);
		   request.setAttribute("convertWPSCenterTip", false);

		   if(FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_WPS_CENTER, true)) {
			   request.setAttribute("convertWPSCenter", true);
			   request.setAttribute("convertWPSCenterTip", Boolean.valueOf(globalConfigForm.getConverter_wps_center()));
		   }
		   return this;
	   }

		/*
		 * 显示点聚
		 */
	   public SetAttributeBuilder setDianjuAttribute() {
		   request.setAttribute("convertDianju", false);
		   request.setAttribute("convertDianjuTip", false);

		   if(FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_DIANJU, true)) {
			   request.setAttribute("convertDianju", true);
			   request.setAttribute("convertDianjuTip", Boolean.valueOf(globalConfigForm.getConverter_dianju()));
		   }
		   return this;
	   }

		/*
		 * 显示福昕
		 */
	   public SetAttributeBuilder setFoxitAttribute() {
		   request.setAttribute("convertFoxit", false);
		   request.setAttribute("convertFoxitTip", false);

		   if(FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_FOXIT, true)) {
			   request.setAttribute("convertFoxit", true);
			   request.setAttribute("convertFoxitTip", Boolean.valueOf(globalConfigForm.getConverter_foxit()));
		   }
		   return this;
	   }

		/*显示数科转换
		 *1.数科转换需要启用，则配置页面才会显示数科转换
		 *2.勾选时，自动创建数据;反之，则删除
		 *3.数科转换关闭，则删除已经创建的数据
		 */
	   public SetAttributeBuilder setShukeAttribute() {
		   request.setAttribute("convertSuwell", false);
		   request.setAttribute("convertSuwellTip", false);

		   if(FileStoreConvertUtil.whetherExecute(THIRD_CONVERTER_SHUKE, true)) {
			   request.setAttribute("convertSuwell", true);
			   request.setAttribute("convertSuwellTip", Boolean.valueOf(globalConfigForm.getConverter_skofd()));
		   }

		   return this;
	   }

   }
}
