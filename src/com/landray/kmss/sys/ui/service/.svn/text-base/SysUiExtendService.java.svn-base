package com.landray.kmss.sys.ui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.ui.forms.SysUiExtendForm;
import com.landray.kmss.sys.ui.model.SysUiConfig;
import com.landray.kmss.sys.ui.util.IniUtil;
import com.landray.kmss.sys.ui.util.ResourceCacheListener;
import com.landray.kmss.sys.ui.util.ThemeUtil;
import com.landray.kmss.sys.ui.util.ZipUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.web.upload.FormFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysUiExtendService implements IXMLDataBean {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	public void upload(SysUiExtendForm xform, RequestContext requestInfo) throws Exception {
		HttpServletRequest request = requestInfo.getRequest();
		// 检查主题包，并获取主题包信息
		JSONObject jsonInfo = null;
		FormFile file = xform.getFile();
		if (file != null) {
			jsonInfo = this.checkExtend(xform.getFile());
		} else if (StringUtil.isNotNull(xform.getFilePath())) {
			String fileName = xform.getFileName();
			String filePath = xform.getFilePath();
			InputStream is = null;
			try {
				is = new FileInputStream(filePath);
				jsonInfo = this.checkExtend(fileName, is);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		// 主题包ID
		String extendId = jsonInfo.getString("extendId");
		// 主题包名称
		String extendName = jsonInfo.getString("extendName");
		// 主题包临时存放目录文件夹名称
		String folderName = jsonInfo.getString("folderName");

		// 根据主题包ID判断主题包在系统中是否已经存在
		boolean isExists = this.isExistsExtend(extendId);
		if (isExists) {
			String nameText = " " + extendId + "（" + extendName + "）";
			String errorMessage = ResourceUtil.getString("ui.help.luiext.upload.exist.replace", "sys-ui", null,
					nameText);
			request.setAttribute("errorMessage", errorMessage);
			request.setAttribute("extendId", extendId);
			request.setAttribute("folderName", folderName);
		} else {
			// 保存主题包
			this.saveExtend(extendId, folderName);
			if (!ThemeUtil.isNotMerge) { // 判断是否开启了“样式调试模式” （ admin.do 基础配置--全局参数配置--样式调试模式 ）
				ThemeUtil.mergeAllTheme(request.getContextPath()); // 合并主题包（将每个扩展主题包与系统默认default主题包分别进行合并）
			}
			request.setAttribute("successMessage", ResourceUtil.getString("ui.help.luiext.upload.success", "sys-ui"));
		}

		request.setAttribute("themeIsExists", isExists);

		if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
			UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin") + "("
					+ ResourceUtil.getString("sys-ui:ui.extend.upload") + ")");
		}
	}

	/**
	 * 检测扩展主题包 （检查的主要内容：是否为空、文件格式、ui.ini文件是否存在、ui.ini文件中是否有定义主题包ID）
	 * 
	 * @param input
	 * @return 返回JSONObject对象，包含主题包ID和临时文件夹名称
	 *         例:{"extendId":"sky_blue","folderName":"16a43fc687dae13080c8a1244178efe8"}
	 * @throws Exception
	 */
	public JSONObject checkExtend(FormFile input) throws Exception {
		return checkExtend(input.getFileName(), input.getInputStream());
	}

	private JSONObject checkExtend(String fileName, InputStream is) throws Exception {
		JSONObject resultObj = new JSONObject();

		// 获取文件扩展名
		String ext = FilenameUtils.getExtension(fileName);

		// 为空时提示“请选择上传文件”
		if (StringUtil.isNull(ext)) {
			throw new Exception(ResourceUtil.getString("ui.help.luiext.selfile", "sys-ui"));
		}

		// 上传文件不是zip格式压缩包时提示“文件类型错误，只能是zip格式”
		if (!"zip".equals(ext)) {
			throw new Exception(ResourceUtil.getString("ui.help.luiext.upload.fileType", "sys-ui"));
		}

		// 获取临时目录，并在目录下新建一个zip文件
		String folderPath = System.getProperty("java.io.tmpdir");
		if (!folderPath.endsWith("/") && !folderPath.endsWith("\\")) {
			folderPath += "/";
		}
		// 获取主题包临时存放目录文件夹名称
		String folderName = IDGenerator.generateID();
		folderPath += folderName;
		File zipFile = new File(folderPath + ".zip");

		FileOutputStream output = null;
		try {
			output = new FileOutputStream(zipFile);
			IOUtils.copy(is, output);
			output.close();

			// 解压zip文件
			ZipUtil.unZip(zipFile, folderPath);

			// 检查ui.ini文件是否存在，如果不存在则提示“ui.ini文件不存在”
			File iniFile = new File(folderPath + "/ui.ini");
			if (!iniFile.exists()) {
				throw new Exception(ResourceUtil.getString("ui.help.luiext.upload.file.notExists", "sys-ui"));
			}

			// 检查ui.ini文件中是否有定义id，如果未定义则提示“ui.ini文件里面没有id的定义”
			Map<String, String> map = IniUtil.loadIniFile(iniFile);
			String extendId = map.get("id"); // 主题包ID
			String extendName = map.get("name"); // 主题包名称
			if (StringUtil.isNull(extendId)) {
				throw new Exception(ResourceUtil.getString("ui.help.luiext.upload.id.notExists", "sys-ui"));
			}

			//判断主题的id是否属于关键字
			SysUiConfig config = new SysUiConfig();
			if(StringUtil.isNotNull(config.getSysThemeKeys())){
				String[] sysThemeKeys = config.getSysThemeKeys().split(",");
				for(String key : sysThemeKeys){
					if(key.equals(extendId)){
						throw new Exception(ResourceUtil.getString("ui.help.luiext.upload.id.themeKeys", "sys-ui"));
					}
				}
			}

			// 这里的ID会拼接到后面解压的目录里，需要判断ID是否包含非法字符
			pathCheck(extendId);
			resultObj.put("extendId", extendId);
			resultObj.put("extendName", extendName);
			resultObj.put("folderName", folderName);
			if (map.containsKey("thumb")) {
				resultObj.put("thumbnail", map.get("thumb"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				zipFile.delete();
			} catch (Exception e2) {
			}
			try {
				output.close();
			} catch (Exception e2) {
			}
		}
		return resultObj;
	}

	/**
	 * 保存扩展主题包
	 * 
	 * @param extendId   主题包ID
	 * @param folderName 主题包临时存放目录文件夹名称
	 * @throws Exception
	 */
	public void saveExtend(String extendId, String folderName) throws Exception {
		String folderPath = System.getProperty("java.io.tmpdir");
		if (!folderPath.endsWith("/") && !folderPath.endsWith("\\")) {
			folderPath += "/";
		}
		folderPath += folderName;
		String zipName = extendId + ".zip";
		// 检查文件名是否有非法字符
		pathCheck(zipName);
		/** 2021.2.19 by wangl 非本地文件存储调用附件接口 start */
		SysFileLocationUtil.getFileService().writeOFolder(folderPath, XmlReaderContext.UIEXT, zipName,XmlReaderContext.UIEXT,null);
		/** 2021.2.19 by wangl 非本地文件存储调用附件接口 end */
		// 更新集群缓存信息
		if(logger.isDebugEnabled()){
			logger.debug(folderPath + " 扩展主题包已保存，发送集群通知");
		}
		ResourceCacheListener.updateResourceCache();
	}

	/**
	 * 替换主题包（替换的逻辑是先根据主题包ID进行删除，然后保存新上传的主题包）
	 * 
	 * @param request
	 */
	public boolean replaceExtend(HttpServletRequest request) throws Exception {
		String extendId = request.getParameter("extendId");
		String folderName = request.getParameter("folderName");
		boolean bool = this.deleteExtendDirectory(extendId, null); // 根据主题包ID删除主题包文件目录
		if (bool) {
			saveExtend(extendId, folderName); // 保存扩展主题包
		}
		return bool;
	}

	/**
	 * 根据主题包ID删除主题包文件目录
	 * 
	 * @param extendId 主题包ID
	 */
	public boolean deleteExtendDirectory(String extendId, String uiType) throws Exception {
		boolean bool = false; // 是否删除成功
		if (StringUtil.isNotNull(extendId)) {
			//删除oss信息和resource中的信息
			SysFileLocationUtil.getFileService().deleteFile(XmlReaderContext.UIEXT + "/" + extendId + ".zip");
			//说明是删除，否则的话可能是替换
			if(StringUtil.isNotNull(uiType)){
				Map<String,String> map = new HashMap<>();
				map.put("uiType", uiType);
				map.put("operate", "delete");
				map.put("extendId", extendId);
				ResourceCacheListener.updateResourceCache(map);
			}else{
				// 替换的情况下
				// 更新集群缓存信息
				ResourceCacheListener.updateResourceCache();
			}
			bool = true;
		}
		return bool;
	}

	/**
	 * 根据主题包ID判断主题包是否已经存在
	 * 
	 * @param extendId 主题包ID
	 * @return
	 */
	private boolean isExistsExtend(String extendId) {
		boolean bool = false;
		// 检查扩展包是否已经存在于系统中
		File extendFolder = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/" + XmlReaderContext.UIEXT + "/" + extendId);
		if (extendFolder.exists()) {
			bool = true;
		}
		return bool;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		TransactionStatus status = null;
		try {
			status = TransactionUtils.beginNewTransaction();
			String fileName = requestInfo.getParameter("fileName");
			String filePath = requestInfo.getParameter("filePath");
			if (logger.isInfoEnabled()) {
				logger.info("正在处理主题包模板：" + filePath);
			}
			SysUiExtendForm mainForm = new SysUiExtendForm();
//			mainForm.setFdSource(String.valueOf(LoginTemplateConstant.SOURCE_REUSE));
			mainForm.setFilePath(filePath);
			mainForm.setFileName(fileName);
			upload(mainForm, requestInfo);
			TransactionUtils.commit(status);
		} catch (Exception e) {
			TransactionUtils.rollback(status);
			logger.error("模板解析失败：", e);
			return faile(StringUtil.getStackTrace(e));
		}
		HttpServletRequest request = requestInfo.getRequest();
		Boolean themeIsExists = (Boolean) request.getAttribute("themeIsExists");
		String errorMessage = (String) request.getAttribute("errorMessage");
		String successMessage = (String) request.getAttribute("successMessage");
		String extendId = (String) request.getAttribute("extendId");
		String folderName = (String) request.getAttribute("folderName");

		JSONObject obj = new JSONObject();
		obj.put("themeIsExists", themeIsExists);
		obj.put("errorMessage", errorMessage);
		obj.put("successMessage", successMessage);
		obj.put("folderName", folderName);
		obj.put("extendId", extendId);
		return success(obj);
	}

	public List success(JSONObject obj) {
		JSONArray rtnData = new JSONArray();
		if (obj == null) {
			obj = new JSONObject();
		}
		obj.put("status", "2");
		rtnData.add(obj);
		return rtnData;
	}

	public List faile(String msg) {
		JSONArray rtnData = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("status", "3");
		obj.put("msg", msg);
		rtnData.add(obj);
		return rtnData;
	}

	/**
	 * 路径检查，过滤非法路径
	 *
	 * @param path
	 */
	private void pathCheck(String path) {
		// 路径不能包含./ 或 ../ 或 /
		if (path.contains("./") || path.contains(".\\") || path.contains("/") || path.contains("\\")) {
			throw new RuntimeException(ResourceUtil.getString("errors.invalid", null, null, "ID"));
		}
	}

}
