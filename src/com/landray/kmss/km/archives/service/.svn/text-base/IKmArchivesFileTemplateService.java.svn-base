package com.landray.kmss.km.archives.service;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.module.core.enhance.IBeanEnhance;
import com.landray.kmss.common.service.IBaseCoreInnerService;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.sys.attachment.model.SysAttMain;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 归档信息
 * 
 * @author
 * @version 1.0 2014-06-17
 */
public interface IKmArchivesFileTemplateService extends IBaseCoreInnerService {
	/**
	 * 添加档案信息
	 * 
	 * @param mainModel
	 * @throws Exception
	 */
	public void addFileArchives(IBaseModel mainModel) throws Exception;

	/**
	 * 设置归档档案的属性（包括扩展属性）
	 * 
	 * @param kmArchivesMain
	 * @param fileTemplate
	 * @param mainModel
	 * @throws Exception
	 */
	public void setFileField(KmArchivesMain kmArchivesMain,
			KmArchivesFileTemplate fileTemplate,
			IBaseModel mainModel) throws Exception;

	/**
	 * 设置归档档案的属性（包括扩展属性）（传入多model）
	 * 
	 * @param kmArchivesMain
	 * @param fileTemplate
	 * @param mainModel
	 * @param modelMap
	 * @throws Exception
	 */
	public void setFileField(KmArchivesMain kmArchivesMain,
			KmArchivesFileTemplate fileTemplate, IBaseModel mainModel,
			Map<String, IBaseModel> modelMap) throws Exception;

	/**
	 * 将归档的页面当做附件绑定给档案
	 * 
	 * @param kmArchivesMain
	 * @param request
	 * @param url
	 * @param fileName
	 * @throws Exception
	 */
	public void setFilePrintPage(KmArchivesMain kmArchivesMain,
			HttpServletRequest request, String url, String fileName)
			throws Exception;
	
	public void setFilePrintPageZoom(KmArchivesMain kmArchivesMain,
			HttpServletRequest request, String url, String fileName,String zoom)
			throws Exception;

	public void setFilePrintPageZoom(KmArchivesMain kmArchivesMain,
			Map<String, String> params, String url, String fileName,String zoom)
			throws Exception;
	
	public void setFilePrintArchivesPage(KmArchivesMain kmArchivesMain, 
			String url, String fileName)
			throws Exception;
	
	public void setFilePrintArchivesPageZoom(KmArchivesMain kmArchivesMain, 
			String url, String fileName,String zoom)
			throws Exception;

	/**
	 * 主文档的所有附件绑定到档案中
	 * 
	 * @param kmArchivesMain
	 * @param mainModel
	 * @throws Exception
	 */
	public void setFileAttachement(KmArchivesMain kmArchivesMain,
			IBaseModel mainModel) throws Exception;

	/**
	 * 主文档的所有附件绑定到档案中（传入多model）
	 * 
	 * @param kmArchivesMain
	 * @param modelMap
	 * @throws Exception
	 */
	public void setFileAttachement(KmArchivesMain kmArchivesMain,
			Map<String, IBaseModel> modelMap) throws Exception;

	public Map<String, String> getOptions(String modelName, String type,
			String templateService, String templateId)
			throws Exception;

	/**
	 * 获得归档设置模板
	 * 
	 * @param moduleUrl
	 * @return
	 * @throws Exception
	 */
	public KmArchivesFileTemplate getFileTemplate(IBaseModel categoryMain,
			String key)
			throws Exception;

	/**
	 * 添加到待转pdf的临时附件区域，若为无需转PDF的格式，则跳过转换直接放到展示附件列表
	 * @param kmArchivesMain
	 * @param attMain
	 * @throws Exception
	 */
	void addToPDFSourceAtt(KmArchivesMain kmArchivesMain, SysAttMain attMain) throws Exception;
	
	
	/**
	 * 档案信息已经存在的情况下，追加部分文档
	 * 设置归档档案的属性（包括扩展属性）（传入多model）
	 * @param kmArchivesMain
	 * @param fileTemplate
	 * @param mainModel
	 * @param modelMap
	 * @throws Exception
	 */
	public void updateFileField(KmArchivesMain kmArchivesMain,
			KmArchivesFileTemplate fileTemplate, IBaseModel mainModel,
			Map<String, IBaseModel> modelMap) throws Exception;
	
	/**
	 * 档案信息已经存在的情况下，追加部分文档
	 * 将归档的页面当做附件绑定给档案
	 * @param kmArchivesMain
	 * @param request
	 * @param url
	 * @param fileName
	 * @throws Exception
	 */
	public void updateFilePrintPage(KmArchivesMain kmArchivesMain,
			HttpServletRequest request, String url, String fileName)
			throws Exception;
	
	/**
	 * 档案信息已经存在的情况下，追加部分文档
	 * 主文档的所有附件绑定到档案中（传入多model）
	 * @param kmArchivesMain
	 * @param modelMap
	 * @throws Exception
	 */
	public void updateFileAttachement(KmArchivesMain kmArchivesMain,
			Map<String, IBaseModel> modelMap) throws Exception;
	
	
	/**
	 * 档案信息已经存在的情况下，进行更新
	 * @param mainModel
	 * @throws Exception
	 */
	public void updateFileArchives(KmArchivesMain mainModel) throws Exception;
	
	/**
	 * 档案信息已经存在的情况下，设置自动归档概览页面
	 * @param kmArchivesMain
	 * @param url
	 * @param fileName
	 * @throws Exception
	 */
	public void updateFilePrintArchivesPage(KmArchivesMain kmArchivesMain, 
			String url, String fileName)
			throws Exception;

	/**
	 * 手动归档单个文档
	 * @param request 请求的Request
	 * @param mainModel 主文档model
	 * @param templateModel 主文档模板model
	 * @param fileTemplate KmArchivesFileTemplate
	 * @param filePrintPageUrl 归档页面的链接，用于生成html
	 */
	void addFileMainDoc(HttpServletRequest request, IBaseModel mainModel, IBaseModel templateModel,
							   IBeanEnhance<KmArchivesFileTemplate> fileTemplate, String filePrintPageUrl) throws Exception;

	/**
	 * 流程结束自动归档
	 * @param mainModel 主文档model
	 * @param templateModel 主文档模板model
	 * @param filePrintPageUrl 归档页面的链接，用于生成html
	 */
	void addAutoFileMainDoc(IBaseModel mainModel, IBaseModel templateModel, String filePrintPageUrl) throws Exception;

}
