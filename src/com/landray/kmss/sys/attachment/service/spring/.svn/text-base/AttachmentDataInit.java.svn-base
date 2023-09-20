package com.landray.kmss.sys.attachment.service.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.sys.datainit.service.ISysDatainitMainService;
import com.landray.kmss.sys.datainit.service.ISysDatainitProcessService;
import com.landray.kmss.sys.datainit.service.ISysDatainitSurroundInterceptor;
import com.landray.kmss.sys.datainit.service.spring.ProcessRuntime;
import com.landray.kmss.sys.datainit.service.spring.SysDatainitProcessServiceImp;
import com.landray.kmss.sys.datainit.service.spring.SysDatainitProcessServiceImp.ImportContext;
import com.landray.kmss.sys.datainit.util.SysDatainitContext;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttCatalog;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.xform.base.model.SysFormCommonTemplate;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class AttachmentDataInit implements ISysDatainitSurroundInterceptor {
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(ISysDatainitSurroundInterceptor.class);
	/**
	 * ${LUI_ContextPath}
	 */
	private static String LUI_CONTEXTPATH = "${LUI_ContextPath}";
	protected ISysAttMainCoreInnerService sysAttMainService;

	public ISysAttMainCoreInnerService getSysAttMainService() {
		if (sysAttMainService == null) {
			sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
					.getBean("sysAttMainService");
		}
		return sysAttMainService;
	}

	protected ISysAttUploadService sysAttUploadService;

	public ISysAttUploadService getSysAttUploadService() {
		if (sysAttUploadService == null) {
			sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
					.getBean("sysAttUploadService");
		}
		return sysAttUploadService;
	}

	protected ISysDatainitProcessService sysDatainitProcessService;

	public ISysDatainitProcessService getSysDatainitProcessService() {
		if (sysDatainitProcessService == null) {
			sysDatainitProcessService = (ISysDatainitProcessService) SpringBeanUtil
					.getBean("sysDatainitProcessService");
		}
		return sysDatainitProcessService;
	}

	private IBaseDao baseDao = null;

	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	// RTF域初始化处理
	private void sysAttRtfDataInit(String modelId, String modelName,
			List<Map<String, Object>> sysAttRtfDatas,
			List<Map<String, Object>> sysAttRtfDatasTemp,
			List<Map<String, Object>> sysAttFileDatas,
			ProcessRuntime processRuntime) throws Exception {
		if (!ArrayUtil.isEmpty(sysAttRtfDatas)) {
			// 有未插入的值，则更新到数据库里
			for (Map<String, Object> map : sysAttRtfDatas) {
				map.put("fdModelId", null);// RTF里此字段不插值
				map.put("fdModelName", null);// RTF里此字段不插值
				Map<String, IBaseModel> modelCache = new HashMap<String, IBaseModel>();
				ImportContext importContext = new SysDatainitProcessServiceImp().new ImportContext(
						map, modelCache, processRuntime);
				getSysDatainitProcessService().restoreModelData(importContext);
				IBaseModel baseModel = importContext.getModel();
				logger.debug("RTF数据导入初始化：有未插入的值，做数据库新增或更新处理："
						+ baseModel.getFdId());
				saveOrUpdateByAttRtf(baseModel, processRuntime.isUpdate());
			}
		}
		// 数据库存在值，则看对应文件及附件是否存在
		for (Map<String, Object> map : sysAttRtfDatas) {
			String attFileId = (String) map.get("fdFileId");
			String fdAttLocation = (String) map.get("fdAttLocation");
			if (StringUtil.isNotNull(fdAttLocation)) {
				if ("db".equals(fdAttLocation)) {
					logger.debug("RTF数据导入初始化：存储类型为db，不做附件迁移处理：" + attFileId);
					continue;
				}
			}
			sysAttFileInit(attFileId, modelId, modelName, sysAttFileDatas,
					processRuntime);
		}
	}

	private void saveOrUpdateByAttRtf(IBaseModel baseModel, boolean isUpdate)
			throws Exception {
		String fdId = baseModel.getFdId();
		IBaseModel attmodel = baseDao.findByPrimaryKey(fdId,
				SysAttRtfData.class, true);
		if (attmodel == null) {
			if (baseModel != null) {
				baseDao.add(baseModel);
				logger.debug("RTF数据导入初始化：新增成功：" + baseModel.getFdId());
			}
		}
		baseDao.flushHibernateSession();
	}

	private void saveOrUpdateByAttMain(IBaseModel baseModel, boolean isUpdate)
			throws Exception {
		if (baseModel != null) {
			IBaseModel model = baseDao.findByPrimaryKey(baseModel.getFdId(),
					baseModel.getClass(), true);
			if (model == null) {
				baseDao.add(baseModel);
				baseDao.flushHibernateSession();
				logger.debug("附件数据导入初始化：新增成功：" + baseModel.getFdId());
			} else {
				logger.debug("附件数据导入初始化：数据已存在：" + baseModel.getFdId());
			}
		}
	}

	// 文件拷贝，从初始化目录将文件拷贝到当前文件存档路径
	private boolean fileCopy(String currentFilePath, String initFilePath,
			SysAttFile attFile) throws Exception {
		if (attFile != null) {
			ISysFileLocationProxyService service = SysFileLocationUtil.getProxyService();
			if (!service.doesFileExist(currentFilePath)) {
				// 文件不存在，从解压后的初始数据里将文件拷贝到数据库对应目录文件里
				if (new File(initFilePath).exists()) {
					// 附件导入，使用加密流
					service.writeFile(FileUtil.getInputStream(initFilePath), currentFilePath);
					return true;
				}
			}
		}
		return false;
	}

	// 附件初始化处理
	@SuppressWarnings("unchecked")
	private void sysAttMainInit(String modelId, String modelName,
			List<Map<String, Object>> sysAttMainDatas,
			List<Map<String, Object>> sysAttMainDatasTemp,
			List<Map<String, Object>> sysAttFileDatas,
			ProcessRuntime processRuntime) throws Exception {
		List<SysAttMain> cSysAttMainList = null;
		for (Map<String, Object> map : sysAttMainDatas) {
			if (ArrayUtil.isEmpty(cSysAttMainList)) {
				cSysAttMainList = getSysAttMainService().findByModelKey(
						(String) map.get("fdModelName"),
						(String) map.get("fdModelId"),
						(String) map.get("fdKey"));
			}
		}
		if (!ArrayUtil.isEmpty(cSysAttMainList)) {
			for (SysAttMain cSysAttMain : cSysAttMainList) {
				getSysAttMainService().getBaseDao().delete(cSysAttMain);
			}
		}

		if (!ArrayUtil.isEmpty(sysAttMainDatas)) {
			// 有未插入的值，则更新到数据库里
			for (Map<String, Object> map : sysAttMainDatas) {
				Map<String, IBaseModel> modelCache = new HashMap<String, IBaseModel>();
				ImportContext importContext = new SysDatainitProcessServiceImp().new ImportContext(
						map, modelCache, processRuntime);
				getSysDatainitProcessService().restoreModelData(importContext);
				IBaseModel baseModel = importContext.getModel();
				logger.debug("附件数据导入初始化：有未插入的值，做数据库新增或更新处理："
						+ baseModel.getFdId());
				saveOrUpdateByAttMain(baseModel, processRuntime.isUpdate());
			}
		}
		// 数据库存在值，则看对应附件是否存在
		for (Map<String, Object> map : sysAttMainDatas) {

			String attFileId = (String) map.get("fdFileId");
			String fdAttLocation = (String) map.get("fdAttLocation");
			if (StringUtil.isNotNull(fdAttLocation)) {
				if ("db".equals(fdAttLocation)) {
					logger.debug("附件数据导入初始化：存储类型为db，不做附件迁移处理：" + attFileId);
					continue;
				}
			}
			sysAttFileInit(attFileId, modelId, modelName, sysAttFileDatas,
					processRuntime);
		}
	}

	// 导入
	@Override
	@SuppressWarnings("unchecked")
	public void beforeRestoreModelData(IBaseModel model,
			Map<String, Object> data, Map<String, IBaseModel> cache,
			ProcessRuntime processRuntime) throws Exception {
		List<Map<String, Object>> sysAttMainDatas = (List<Map<String, Object>>) data
				.get("sysAttMain_extension");
		List<Map<String, Object>> sysAttRtfDataDatas = (List<Map<String, Object>>) data
				.get("sysAttRtfData_extension");
		List<Map<String, Object>> sysAttFileDatas = (List<Map<String, Object>>) data
				.get("sysAttFile_extension");
		String modelId = "";
		String modelName = "";
		if (!ArrayUtil.isEmpty(sysAttRtfDataDatas)) {
			modelId = (String) sysAttRtfDataDatas.get(0).get("fdModelId");
			modelName = (String) sysAttRtfDataDatas.get(0).get("fdModelName");
			sysAttRtfDataInit(modelId, modelName, sysAttRtfDataDatas, null,
					sysAttFileDatas, processRuntime);
		}
		if (!ArrayUtil.isEmpty(sysAttMainDatas)) {
			modelId = (String) sysAttMainDatas.get(0).get("fdModelId");
			modelName = (String) sysAttMainDatas.get(0).get("fdModelName");
			sysAttMainInit(modelId, modelName, sysAttMainDatas, null,
					sysAttFileDatas, processRuntime);
		}

		if (StringUtil.isNull(modelName)) {
			modelName = (String) data.get("class");
		}
		// 获取DNS服务器，遍历data，将值替换
		if (model == null) {
			model = (IBaseModel) com.landray.kmss.util.ClassUtils.forName(modelName).newInstance();
		}
		model.setFdId((String) data.get("fdId"));
		SysDictModel dictModel = SysDataDict.getInstance().getModel(
				ModelUtil.getModelClassName(model));
		List<SysDictCommonProperty> properties = dictModel.getPropertyList();
		String currentContext = SysDatainitContext.requestContextPath;
		for (int i = 0; i < properties.size(); i++) {
			SysDictCommonProperty property = properties.get(i);
			if (!"RTF".equals(property.getType())) {
				continue;
			}
			String propertyName = property.getName();
			if (StringUtil.isNull(propertyName)
					|| StringUtil.isNull(property.getColumn())) {
                continue;
            }
			if (property instanceof SysDictSimpleProperty) {
				// 从data里获取RTF对应的扩展字段，并替换
				String docContent = (String) data.get(propertyName
						+ "_picpath_extension");
				if (StringUtil.isNull(docContent)) {
					continue;
				}
				if (docContent.indexOf(LUI_CONTEXTPATH) > 0) {
					docContent = docContent.replace(LUI_CONTEXTPATH,
							currentContext);
					logger.debug("导入：扩展字段：" + propertyName
							+ "_picpath_extension");
					data.put(propertyName, docContent);
				}
			}
		}
	}

	private void sysAttFileInit(String attFileId, String modelId,
			String modelName, List<Map<String, Object>> sysAttFileDatas,
			ProcessRuntime processRuntime) throws Exception {
		SysAttFile attFile = getSysAttUploadService().getFileById(attFileId);
		if (attFile == null) {
			if (!ArrayUtil.isEmpty(sysAttFileDatas)) {
				// 文件不存在,插入值，并将文件拷贝到对应目录里
				for (Map<String, Object> attFileMap : sysAttFileDatas) {
					if (attFileId.equals(attFileMap.get("fdId"))) {
						Map<String, IBaseModel> modelCache = new HashMap<String, IBaseModel>();
						ImportContext importContext = new SysDatainitProcessServiceImp().new ImportContext(
								attFileMap, modelCache, processRuntime);
						getSysDatainitProcessService().restoreModelData(
								importContext);
						IBaseModel baseModel = importContext.getModel();
						String fdId = baseModel.getFdId();
						IBaseModel attmodel = baseDao.findByPrimaryKey(fdId,
								attFileMap.get("class"), true);
						if (attmodel == null) {
							if (baseModel != null) {
								baseDao.add(baseModel);
							}
						}
						baseDao.getHibernateTemplate().flush();
					}
				}
			}
			attFile = getSysAttUploadService().getFileById(attFileId);
		}
		if (attFile != null) {
			String currentFilePath = formatPath(attFile.getFdCata(), attFile
					.getFdFilePath());
			
			ISysFileLocationProxyService service = SysFileLocationUtil.getProxyService();
			if (!service.doesFileExist(currentFilePath)){
				// 文件不存在，从解压后的初始数据里将文件拷贝到数据库对应目录文件里
				String initFilePath = getInitPath(modelName)
						+ attFile.getFdId() + "_" + modelId + "_"
						+ modelName.substring(modelName.lastIndexOf(".") + 1);
				fileCopy(currentFilePath, initFilePath, attFile);
			}
		}
	}

	// 导出扩展处理
	@Override
	@SuppressWarnings("unchecked")
	public void beforeStoreModelData(IBaseModel model, Map<String, Object> data)
			throws Exception {
		String modelId = model.getFdId();
		String modelName = ModelUtil.getModelClassName(model);
		List<Map<String, Object>> sysAttMaintDatas = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sysAttRtfDataDatas = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sysAttFileDatas = new ArrayList<Map<String, Object>>();
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("fdModelName=:modelName and fdModelId=:modelId");
		hql.setOrderBy("fdOrder");
		hql.setParameter("modelName", modelName);
		hql.setParameter("modelId", modelId);
		// 附件
		List<SysAttMain> sysAttMainList = getSysAttMainService().findList(hql);
		// RTF域
		List<SysAttRtfData> sysAttRtfDataList = new ArrayList<SysAttRtfData>();
		String[] rtfIds = getDownLoadIdsByRTF(model);
		if (rtfIds != null) {
			for (String rtfId : rtfIds) {
				SysAttRtfData sysAttRtfData = getSysAttMainService()
						.findRtfDataByPrimaryKey(rtfId);
				if (sysAttRtfData != null) {
					sysAttRtfDataList.add(sysAttRtfData);
				}
			}
		}
		if (!ArrayUtil.isEmpty(sysAttMainList)) {
			for (SysAttMain sysAttMain : sysAttMainList) {
				SysAttFile attFile = getSysAttUploadService().getFileById(
						sysAttMain.getFdFileId());
				if ("db".equals(attFile.getFdAttLocation())) {// 存储于数据库的附件及RTF不做导出导入处理
					logger.debug("附件数据导出：存储类型为db，不做附件迁移处理："
							+ sysAttMain.getFdId());
					continue;
				}
				
				if (attFile != null) {
					Map<String, Object> cData = new HashMap<String, Object>();
					cData.put("fdId", attFile.getFdId());
					cData.put("class", ModelUtil.getModelClassName(attFile));
					getSysDatainitProcessService().storeModelData(attFile,
							cData);
					sysAttFileDatas.add(cData);
					// 将文件拷贝到导出目录里
//					String from = formatPath(attFile.getFdCata(), attFile
//							.getFdFilePath());
					String from = attFile.getFdFilePath();
					// 文件路径
					String filePath = "_"
							+ modelName
									.substring(modelName.lastIndexOf(".") + 1);
					filePath = getInitPath(modelName) + attFile.getFdId() + "_"
							+ modelId + filePath;
					if (SysFileLocationUtil.getProxyService(attFile.getFdAttLocation())
							.doesFileExist(from,formatCataPath(attFile.getFdCata()))) {
						// 附件导出，使用解密流
						FileUtil.createFile(getSysAttMainService()
								.getInputStream(sysAttMain),
								filePath);
						// FileUtil.copy(from, filePath);
					}
					cData = new HashMap<String, Object>();
					cData.put("fdId", sysAttMain.getFdId());
					cData.put("class", ModelUtil.getModelClassName(sysAttMain));
					getSysDatainitProcessService().storeModelData(sysAttMain,
							cData);
					sysAttMaintDatas.add(cData);
				}
			}
		}
		if (!ArrayUtil.isEmpty(sysAttRtfDataList)) {
			for (SysAttRtfData sysAttRtfData : sysAttRtfDataList) {
				SysAttFile attFile = getSysAttUploadService().getFileById(
						sysAttRtfData.getFdFileId());
				if ("db".equals(attFile.getFdAttLocation())) {// 存储于数据库的附件及RTF不做导出导入处理
					logger.debug("附件数据导出：存储类型为db，不做附件迁移处理："
							+ sysAttRtfData.getFdId());
					continue;
				}
				if (attFile != null) {
					Map<String, Object> cData = new HashMap<String, Object>();
					cData.put("fdId", attFile.getFdId());
					cData.put("class", ModelUtil.getModelClassName(attFile));
					getSysDatainitProcessService().storeModelData(attFile,
							cData);
					sysAttFileDatas.add(cData);
					// 将文件拷贝到导出目录里
//					String from = formatPath(attFile.getFdCata(), attFile
//							.getFdFilePath());
					String from = attFile.getFdFilePath();
					
					// 文件路径
					String filePath = "_"
							+ modelName
									.substring(modelName.lastIndexOf(".") + 1);
					filePath = getInitPath(modelName) + attFile.getFdId() + "_"
							+ modelId + filePath;

					ISysFileLocationProxyService service = SysFileLocationUtil
							.getProxyService();
					if (service.doesFileExist(from)) {
						// 附件导出，使用解密流
						FileUtil.createFile(getSysAttMainService()
								.getInputStreamByFile(attFile.getFdId()),
								filePath);
						// FileUtil.copy(from, filePath);
					}
					cData = new HashMap<String, Object>();
					cData.put("fdId", sysAttRtfData.getFdId());
					cData.put("class", ModelUtil
							.getModelClassName(sysAttRtfData));
					cData.put("fdModelId", modelId);
					cData.put("fdModelName", modelName);
					getSysDatainitProcessService().storeModelData(
							sysAttRtfData, cData);
					sysAttRtfDataDatas.add(cData);
				}
			}
		}
		// 处理RTF图片路径
		repleceRTFPicPath(model, data);
		// 导出扩展字段
		data.put("sysAttMain_extension", sysAttMaintDatas);
		data.put("sysAttRtfData_extension", sysAttRtfDataDatas);
		data.put("sysAttFile_extension", sysAttFileDatas);
	}

	// 根据模块获取默认的模块数据初始保存路径
	public String getInitPath(String modelName) {
		String[] arr = modelName.split("\\.");
		int index = findModelIndex(arr);
		String tempPath;
		File temp;
		if (index < 0) {
			tempPath = ISysDatainitMainService.INIT_PATH
					+ ISysDatainitMainService.CONF_PATH + "/" + arr[3] + "/"
					+ arr[4] + "/initdata/";

			temp = new File(tempPath);
		} else {
			tempPath = ISysDatainitMainService.INIT_PATH
					+ ISysDatainitMainService.CONF_PATH;

			for (int i = 3; i < index; i++) {
				tempPath += "/" + arr[i];
			}
			tempPath += "/initdata/";
			temp = new File(tempPath);
		}
		if (!temp.exists()) {
			temp.mkdirs();
		}
		return tempPath;
	}

	/**
	 * 支持多层model 只要class 里面到model 层为终止点
	 * 
	 * @param attr
	 * @return
	 */
	public int findModelIndex(String[] attr) {
		for (int i = 0; i < attr.length; i++) {
			if ("model".equalsIgnoreCase(attr[i])) {
				return i;
			}
		}
		return -1;
	}

	public String[] getDownLoadIdsByRTF(IBaseModel model) throws Exception {
		SysDictModel dictModel = SysDataDict.getInstance().getModel(
				ModelUtil.getModelClassName(model));
		if (dictModel == null) {
			return null;
		}
		List<SysDictCommonProperty> properties = dictModel.getPropertyList();
		String docContent = "";
		List<String> rtfIds = new ArrayList<String>();
		for (int i = 0; i < properties.size(); i++) {
			SysDictCommonProperty property = properties.get(i);
			if (!"RTF".equals(property.getType())) {
				continue;
			}
			String propertyName = property.getName();
			if (StringUtil.isNull(propertyName)
					|| StringUtil.isNull(property.getColumn())) {
                continue;
            }
			Object value = PropertyUtils.getProperty(model, propertyName);
			if (value == null) {
                continue;
            }
			if (property instanceof SysDictSimpleProperty) {
				docContent = value.toString();
				docContent = docContent.replace("\r\n", " ");
				Pattern p = Pattern
						.compile(
								"/resource/fckeditor/editor/filemanager/download\\?fdId=([a-z0-9])*",
								Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(docContent);
				while (m.find()) {
					String id = m.group();
					id = id
							.replace(
									"/resource/fckeditor/editor/filemanager/download?fdId=",
									"");
					id = id.replace("\"", "");
					rtfIds.add(id);
				}
			}
		}
		return rtfIds.toArray(new String[rtfIds.size()]);
	}

	public void repleceRTFPicPath(IBaseModel model, Map<String, Object> data)
			throws Exception {
		SysDictModel dictModel = SysDataDict.getInstance().getModel(
				ModelUtil.getModelClassName(model));
		List<SysDictCommonProperty> properties = dictModel.getPropertyList();
		String currentContext = SysDatainitContext.requestContextPath;
		String currentDNS = ResourceUtil.getKmssConfigString("kmss.urlPrefix")
				+ "";
		for (int i = 0; i < properties.size(); i++) {
			SysDictCommonProperty property = properties.get(i);
			if (!"RTF".equals(property.getType())) {
				continue;
			}
			String propertyName = property.getName();
			if (StringUtil.isNull(propertyName)
					|| StringUtil.isNull(property.getColumn())) {
                continue;
            }
			Object value = PropertyUtils.getProperty(model, propertyName);
			if (value == null) {
                continue;
            }
			if (property instanceof SysDictSimpleProperty) {
				String docContent = value.toString();
				String tempDocContent = value.toString();
				if ((model instanceof SysFormTemplate || model instanceof SysFormCommonTemplate)
						&& "fdDesignerHtml".equals(propertyName)) {
					// 对表单的fdDesignerHtml做特殊处理
					docContent = docContent.replace("&amp;quot;", "&quot;");
				}
				docContent = docContent.replace("&quot;", "\"");
				Pattern p = Pattern.compile("(href=|src=)\"(.*?)\"",
						Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(docContent);
				String editor = "/resource/fckeditor/editor/filemanager/";
				String uiext = "/ui-ext/";
				boolean isAdd = false;
				while (m.find()) {
					String path = m.group();
					boolean ishref = false;
					boolean isCompareDNS = false;// 是否需要比对DNS，只有附件需要判断DNS，其它链接无需判断DNS服务器.
					boolean isReplaceStart = false;// href链接有可能不是以/开头，这里用来判断是否需要替换/
					if (path.indexOf("href") > -1) {
						ishref = true;
					}
					path = path.replace("href=\"", "");
					path = path.replace("src=\"", "");
					path = path.replace("\"", "");
					String txt = "";
					if (path.indexOf(editor) > 0) {
						txt = editor;
						isCompareDNS = true;
					} else if (path.indexOf(uiext) > 0) {
						txt = uiext;
					} else if (ishref) {
						// href没有固定后缀区分，这里截取上下文根之后的为后缀，用来做后续判断处理。
						String prex = "";
						if (path.startsWith("http")) {
							prex = path.startsWith("https") ? "https://" : "http://";
							String temp = path.substring(prex.length());
							String[] str = temp.split("/");
							if (str.length > 1) {
								prex += str[0] + "/" + str[1];
							}
						} else {
							if (!path.startsWith("/")) {
								path = "/" + path;
								isReplaceStart = true;
							}
							if (!path.startsWith(currentContext)) {
								continue;
							}
							prex = path.substring(0, path.indexOf("/") + 1);
							String temp = path.substring(prex.length());
							try {
								prex = prex
										+ temp.substring(0, temp.indexOf("/"));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						txt = path.substring(prex.length());
					} else {
						continue;
					}
					if (StringUtil.isNotNull(txt)) {
						String prexPath = path.substring(0, path.indexOf(txt));
						// 判断DNS
						if (isCompareDNS && prexPath.startsWith("http")
								&& !prexPath.equals(currentDNS)) {
							continue;
						} else {
							String picContextPath = prexPath.substring(prexPath
									.lastIndexOf("/"));
							// 判断上下文
							if (!picContextPath.equals(currentContext)) {
								continue;
							}
						}
						// 已处理的不再处理
						if (prexPath.equals(LUI_CONTEXTPATH)) {
							continue;
						}
						String endPath = path.substring(path.indexOf(txt));
						String newPath = LUI_CONTEXTPATH + endPath;
						if (isReplaceStart && path.startsWith("/")) {
							path = path.substring(1);
						}
						docContent = docContent.replace(path, newPath);
						tempDocContent = tempDocContent.replace(path, newPath);
						isAdd = true;
					}
				}
				tempDocContent = replaceBackgroundUrl(tempDocContent,
						currentContext);
				if (isAdd) {
					// 可能一个表里不止一个RTF字段，这里名字动态处理。
					data.put(propertyName + "_picpath_extension",
							tempDocContent);
					logger.debug("导出：添加扩展字段：" + propertyName
							+ "_picpath_extension");
				}
			}
		}
	}

	/**
	 * 将与当前上下文路径对应的样式背景图片URL替换成上下文${LUI_ContextPath}
	 * 
	 * @param rtfContent
	 * @param contextPath
	 * @return
	 */
	public String replaceBackgroundUrl(String rtfContent, String contextPath) {
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("url\\(([^\\)]+)\\)");
		Matcher matcher = pattern.matcher(rtfContent);
		while (matcher.find()) {
			String str = matcher.group(1);
			if (str.startsWith(contextPath)) {
				str = "url(" + LUI_CONTEXTPATH
						+ str.substring(contextPath.length()) + ")";
			} else if (str.startsWith("http")) {
				String prex = "http://";
				String temp = str.substring(prex.length());
				String[] strs = temp.split("/");
				prex += strs[0] + "/" + strs[1];
				str = "url(" + LUI_CONTEXTPATH + str.substring(prex.length())
						+ ")";
			} else {
				continue;
			}

			str = str.replaceAll("\\\\", "\\\\\\\\");
			if (str.indexOf("$") > -1) {
				str = str.replaceAll("\\$", "\\\\\\$");
			}
			matcher.appendReplacement(sb, str);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public static void main(String[] args) {
		// String text =
		// "&lt;p&gt;&lt;a href=&quot;http://localhost:8080/ekp_j/km/doc/km_doc_knowledge/kmDocKnowledge.do?method=view&amp;amp;fdId=144da1d1e8f6fec1f964df74b94b03b6&quot; target=&quot;_blank&quot;&gt;&lt;img alt=&quot;&quot; src=&quot;http://localhost:8080/ekp_j/ui-ext/initpackage/images/V11_Quick_Start.jpg&quot; src=&quot;http://localhost:8080/ekp/ui-ext/initpackage/images/V11_Quick_Start.jpg&quot; src=&quot;/ekp_j/ui-ext/initpackage/images/V11_Quick_Start.jpg&quot;style=&quot;width: 240px; height: 88px;&quot; /&gt;&lt;/a&gt;&lt;/p&gt;&#13;  src=&quot;http://localhost:8080/ekp_j/resource/fckeditor/editor/filemanager/abc.jpg&quot;";
		// String text =
		// "&lt;p&gt;&lt;a href=&quot;http://localhost:8080/ekp_j/km/doc/km_doc_knowledge/kmDocKnowledge.do?method=view&amp;amp;fdId=144da1d1e8f6fec1f964df74b94b03b6&quot; target=&quot;_blank&quot;&gt;&lt;img alt=&quot;&quot; src=&quot;http://localhost:8080/ekp_j/ui-ext/initpackage/images/V11_Quick_Start.jpg&quot; src=&quot;http://localhost:8080/ekp/ui-ext/initpackage/images/V11_Quick_Start.jpg&quot; src=&quot;/ekp_j/ui-ext/initpackage/images/V11_Quick_Start.jpg&quot;style=&quot;width: 240px; height: 88px;&quot; /&gt;&lt;/a&gt;&lt;/p&gt;&#13;  src=&quot;http://localhost:8080/ekp_j/resource/fckeditor/editor/filemanager/abc.jpg&quot;";
		String text = "&lt;p&gt;&lt;a href=&quot;http://localhost:8080/ekp_j/km/doc/km_doc_knowledge/kmDocKnowledge.do?method=view&amp;amp;fdId=144da1d1e8f6fec1f964df74b94b03b6&quot; target=&quot;_blank&quot;&gt;&lt;img alt=&quot;&quot; src=&quot;http://localhost:8080/ekp_j/ui-ext/initpackage/images/V11_Quick_Start.jpg&quot; style=&quot;width: 240px; height: 88px&quot; /&gt;&lt;/a&gt;&lt;/p&gt;&#13;"
				+ "&#13;"
				+ "&lt;div style=&quot;width: 250px; background: url(/ekp_j/ui-ext/initpackage/images/info_banner.jpg); height: 100px&quot;&gt;&amp;nbsp;&lt;/div&gt;&#13;"
				+ "href=&quot;http://localhost:8080/ekp_j/resource/fckeditor/editor/filemanager/aaa.jpg&quot; "
				+ "src=&quot;http://localhost:8080/ekp_j/resource/fckeditor/editor/filemanager/aaa.jpg&quot;";
		String docContent = text;
		String tempDocContent = text;
		String currentDNS = "http://localhost:8080/ekp_j";
		String currentContext = "/ekp_j";
		docContent = docContent.replace("&quot;", "\"");
		Pattern p = Pattern.compile("(href=|src=)\"(.*?)\"",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(docContent);
		String editor = "/resource/fckeditor/editor/filemanager/";
		String uiext = "/ui-ext/";
		while (m.find()) {
			String path = m.group();
			boolean ishref = false;
			boolean isCompareDNS = false;// 是否需要比对DNS，只有附件需要判断DNS，其它链接无需判断DNS服务器.
			boolean isReplaceStart = false;// href链接有可能不是以/开头，这里用来判断是否需要替换/
			if (path.indexOf("href") > -1) {
				ishref = true;
			}
			path = path.replace("src=\"", "");
			path = path.replace("href=\"", "");
			path = path.replace("\"", "");
			String txt = "";
			if (path.indexOf(editor) > 0) {
				txt = editor;
				isCompareDNS = true;
			} else if (path.indexOf(uiext) > 0) {
				txt = uiext;
			} else if (ishref) {
				// href没有固定后缀区分，这里截取上下文根之后的为后缀，用来做后续判断处理。
				String prex = "";
				if (path.startsWith("http")) {
					prex = "http://";
					String temp = path.substring(prex.length());
					String[] str = temp.split("/");
					prex += str[0] + "/" + str[1];
				} else {
					if (!path.startsWith("/")) {
						path = "/" + path;
						isReplaceStart = true;
					}
					prex = "/";
					String temp = path.substring(prex.length());
					prex = prex + temp.substring(0, temp.indexOf("/"));
				}
				txt = path.substring(prex.length());
			} else {
				continue;
			}
			if (StringUtil.isNotNull(txt)) {
				String prexPath = path.substring(0, path.indexOf(txt));
				// 判断DNS
				if (isCompareDNS && prexPath.startsWith("http")
						&& !prexPath.equals(currentDNS)) {
					continue;
				} else {
					String picContextPath = prexPath.substring(prexPath
							.lastIndexOf("/"));
					// 判断上下文
					if (!picContextPath.equals(currentContext)) {
						continue;
					}
				}
				// 已处理的不再处理
				if (prexPath.equals(LUI_CONTEXTPATH)) {
					continue;
				}
				String endPath = path.substring(path.indexOf(txt));
				String newPath = LUI_CONTEXTPATH + endPath;
				if (isReplaceStart && path.startsWith("/")) {
					path = path.substring(1);
				}
				docContent = docContent.replace(path, newPath);
				tempDocContent = tempDocContent.replace(path, newPath);
			}
		}
		AttachmentDataInit att = new AttachmentDataInit();
		tempDocContent = att.replaceBackgroundUrl(tempDocContent,
				currentContext);
		logger.info(tempDocContent);
	}
	
	private String formatPath(SysAttCatalog catalog,String relativePath) {
		return formatCataPath(catalog) + relativePath;
	}
	
	private String formatCataPath(SysAttCatalog catalog) {
		String cfgPath = null;
		if (catalog == null) {
			cfgPath = "";
		} else {
			cfgPath = catalog.getFdPath();
		}
		if (StringUtil.isNotNull(cfgPath)) {
			cfgPath = cfgPath.replace("\\", "/");
		}
		if (cfgPath.endsWith("/")) {
            cfgPath = cfgPath.substring(0, cfgPath.length() - 1);
        }
		return cfgPath;
	}
}
