package com.landray.kmss.sys.attachment.jg;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.type.StandardBasicTypes;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.util.SysAttConfigUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import DBstep.iMsgServer2000;

public class SysAttJGSaveFileFunction extends AbstractSysAttachmentJGFunction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGSaveFileFunction.class);

	private void loggerInfo(SysAttMain sysAttMain, iMsgServer2000 MsgObj) {

		// 默认开启iWeboffice2009控件监控日志功能
		// boolean isLogJgEnabled = false;

		String logJgValue = ResourceUtil
				.getKmssConfigString("kmss.isLogJgEnabled");
		// if (logJgValue != null) {
		// isLogJgEnabled = "true".equals(logJgValue.trim());
		// }
		if (logger.isInfoEnabled()) {

			// logger.info("iWeboffice2009控件监控日志功能：" + (isLogJgEnabled ? "开" :
			// "关"));

			// if(isLogJgEnabled){
			String mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
			String mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得保存用户名称
			String mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
			String mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
			int mFileSize = MsgObj.MsgFileSize(); // 取得文档大小
			// String mFileDate=DbaObj.GetDateTime(); //取得文档时间
			// String mFileBody=MsgObj.MsgFileBody(); //取得文档内容
			logger.info("iWeboffice控件监控日志:"
					+ "1、【主文档ModelId】："
					+ MsgObj.GetMsgByName("_fdModelId")
					+ "；"// MsgObj.GetMsgByName("RECORDID")
					+ "2、【主文档ModelName】："
					+ MsgObj.GetMsgByName("_fdModelName")
					+ "；"
					+ "3、【附件fdId】："
					+ sysAttMain.getFdId()
					+ "；"
					+ "4、【文档名称】："
					+ sysAttMain.getFdFileName()
					+ "；"// mFileName
					+ "5、【文档大小】："
					+ mFileSize
					+ "字节；"
					+ "6、【用户名】："
					+ UserUtil.getUser().getFdName()
					+ "；"
					+ "7、【保存时间】："
					+ DateUtil.convertDateToString(new Date(),
							DateUtil.TYPE_DATETIME, UserUtil.getKMSSUser()
									.getLocale()));
			// }
		}
	}

	@Override
    public void execute(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		byte[] mFileBody = MsgObj.ToDocument(MsgObj.MsgFileBody());// 将含有Office文件数据和批注数据的复合文件内容，流转为普通Office文件流
		MsgObj.MsgFileBody(mFileBody);
		String mCommand = MsgObj.GetMsgByName("COMMAND");
		String fileType = MsgObj.GetMsgByName("FILETYPE");
		Boolean canSave = false;
		if (".doc".endsWith(fileType.toLowerCase()) || ".docx".endsWith(fileType.toLowerCase())
				|| ".xlsx".endsWith(fileType.toLowerCase())) {
			// 将流写入临时文件
			String fdSystemTempPath = FileUtil.getSystemTempPath();
			if (!fdSystemTempPath.endsWith("/")) {
				fdSystemTempPath = fdSystemTempPath + "/";
			}
			String tmpId = IDGenerator.generateID();
			String filePath = fdSystemTempPath + tmpId + "_tmp" + fileType;
			File tmpFile = new File(filePath);

			while (tmpFile.exists()) {
				tmpId = IDGenerator.generateID();
				filePath = fdSystemTempPath + tmpId + "_tmp" + fileType;
				tmpFile = new File(filePath);
			}

			File tmpPfile = tmpFile.getParentFile();
			if (!tmpPfile.exists()) {
				tmpPfile.mkdirs();
			}
			tmpFile.createNewFile();
			if (MsgObj.MsgFileSave(filePath)) {
				long startTime = System.currentTimeMillis(); // 获取开始时间
				String ret = MsgObj.CheckFileValid(filePath);
				long endTime = System.currentTimeMillis(); // 获取结束时间
				logger.info("poi程序运行时间： " + (endTime - startTime) + "ms");
				if ("ok".equals(ret)) {
					logger.info("POI检验文档成功!");
					canSave = true;
				} else if ("NoSheetPages".equals(ret)) {
					logger.info("Sheet页为0!");
				} else if ("FileEncryption".equals(ret)) {
					logger.info("加密文档解析异常!");
				} else if ("FileIsDamaged".equals(ret)) {
					logger.info("文件损坏!");
				} else if ("EmptyDocument".equals(ret)) {
					logger.info("空文档!保存");
					canSave = true;
				} else if ("DocumentNotWord".equals(ret)) {
					logger.info("文档类型不是word类型!");
				} else if ("DocumentNotExcel".equals(ret)) {
					logger.info("文档类型不是excel类型!");
				} else if ("FileEmpty".equals(ret)) {
					logger.info("文件流为空!");
				} else if ("TheFileFormatIsNotSupported".equals(ret)) {
					logger.info("文件类型不支持!");
				} else {
					logger.info("POI检验文档因未知原因失败!");
				}
				tmpFile.delete();
			}
		} else {
			canSave = true;
		}
		if (canSave) {
			if ("ORIGINALDRAFT".equals(mCommand) || "REVISIONDRAFT".equals(mCommand) || "CLEARDRAFT".equals(mCommand)) {
				// 正文保存底稿
				doSaveDraft(request, MsgObj);
				logger.info("底稿保存");
			} else {
				doSaveFile(request, MsgObj);
				logger.info("正文保存");
			}
		}
	}

	private void doSaveDraft(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		SysAttMain sysAttMain = new SysAttMain();
		String fdId = MsgObj.GetMsgByName("_fdId");
		String fdMmodelName = MsgObj.GetMsgByName("_modelName");
		String key = MsgObj.GetMsgByName("_key");
		sysAttMain.setFdId(fdId);
		sysAttMain.setFdKey(key);
		sysAttMain.setFdModelName(fdMmodelName);
		
		setSysAttMainNew(sysAttMain, MsgObj, key);
		getSysAttMainService().add(sysAttMain);

		// iWeboffice控件监控日志
		loggerInfo(sysAttMain, MsgObj);

		if (logger.isDebugEnabled()) {
			logger.debug("保存附件：fdId:" + sysAttMain.getFdId() + "; fdModelId:"
					+ MsgObj.GetMsgByName("_fdModelId") + "; fdModelName:"
					+ MsgObj.GetMsgByName("_fdModelName") + "; fdKey:"
					+ MsgObj.GetMsgByName("_fdKey"));
		}
		MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
		MsgObj.SetMsgByName("COMMAND", "NODRAFT");
		MsgObj.SetMsgByName("fd_fileId", sysAttMain.getFdId()); // 设置保存文档的Id
		String fileName = sysAttMain.getFdFileName().replaceAll(" ", "%20");
		MsgObj.SetMsgByName("fd_fileName", java.net.URLEncoder.encode(fileName, "UTF-8")); // 设置保存文档name
		MsgObj.MsgError(""); // 清除错误信息
		MsgObj.MsgFileClear(); // 清除文档内容
	}

	private void setSysAttMainNew(SysAttMain sysAttMain, iMsgServer2000 MsgObj,
			String key) throws Exception {
		String fileType = MsgObj.GetMsgByName("FILETYPE");
		String _fdFileName = MsgObj.GetMsgByName("_fdFileName");
		String userId =  MsgObj.GetMsgByName("_userId");
		String userName =  MsgObj.GetMsgByName("_userName");
		SysOrgPerson p = UserUtil.getUser();
		if(p.isAnonymous()){
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService)SpringBeanUtil.getBean("sysOrgPersonService");
			p = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(userId);
		}
		if(p != null){
			sysAttMain.setFdCreatorId(p.getFdId());
		}
		if (StringUtil.isNull(_fdFileName)) {
			StringBuffer fileName = new StringBuffer();
			if ("originalAtt".equals(key)) {
				// 由于清稿操作需要区分正文，故清稿附件文件名修改为 清稿_操作者_正文名.doc
				fileName.append(ResourceUtil.getString("sysAttMain.originalDraft", "sys-attachment"));
			}
			if ("revisionAtt".equals(key)) {
				// 由于清稿操作需要区分正文，故清稿附件文件名修改为 清稿_操作者_正文名.doc
				fileName.append(ResourceUtil.getString("sysAttMain.revisionDraft", "sys-attachment"));
			}
			if ("clearAtt".equals(key)) {
				// 由于清稿操作需要区分正文，故清稿附件文件名修改为 清稿_操作者_正文名.doc
				fileName.append(ResourceUtil.getString("sysAttMain.clearDraft", "sys-attachment"));
			}
			
			if ("redheadAtt".equals(key)) {
				fileName.append(ResourceUtil.getString("sysAttMain.redheadDraft", "sys-attachment"));
			}
			fileName.append("_");
			fileName.append(p.getFdName());
			fileName.append("_");
			String filename = MsgObj.GetMsgByName("FILENAME");
			String mainOnlineSimpleName = filename.substring(0, filename.lastIndexOf("."));
			fileName.append(java.net.URLDecoder.decode(mainOnlineSimpleName, "UTF-8"));
			fileName.append("_");
			fileName.append(DateUtil.convertDateToString(new Date(), DateUtil.PATTERN_DATE));
			if (StringUtil.isNotNull(fileName.toString()) && StringUtil.isNotNull(fileType)
					&& !fileName.toString().endsWith(fileType)) {
				fileName.append(fileType);
			}
			// 防止出现后缀名为.do的文件
			if (".do".equals(fileName.substring(fileName.length() - 3, fileName.length()).toLowerCase())
					&& "office".equals(MsgObj.GetMsgByName("_attType"))) {
				fileName.append("c");
			}
			_fdFileName = fileName.toString();
			// 替换文件夹中的特殊字符
			_fdFileName = _fdFileName.replaceAll("[/\\\\:*?|]", "-");
			_fdFileName = _fdFileName.replaceAll("[\"<>]", "'");
		}else {
			_fdFileName = java.net.URLDecoder.decode(_fdFileName, "UTF-8");
		}

		sysAttMain.setFdFileName(_fdFileName);
		sysAttMain.setFdAttType(MsgObj.GetMsgByName("_attType"));
		sysAttMain.setFdContentType(FileMimeTypeUtil.getContentType(_fdFileName));
		sysAttMain.setFdSize(Double.valueOf(MsgObj.MsgFileSize()));
		if (sysAttMain.getDocCreateTime() == null) {
			sysAttMain.setDocCreateTime(new Date());
		}
		sysAttMain.setInputStream(org.hibernate.engine.jdbc.NonContextualLobCreator.INSTANCE.createBlob(MsgObj.MsgFileBody())
				.getBinaryStream());
	}

	private void doSaveFile(RequestContext request, iMsgServer2000 MsgObj)
			throws Exception {
		String fdId = MsgObj.GetMsgByName("_fdId");
		String modelId = MsgObj.GetMsgByName("RECORDID");
		String modelName = MsgObj.GetMsgByName("_fdModelName");
		String key = MsgObj.GetMsgByName("_fdKey");
		String extParam =  MsgObj.GetMsgByName("EXTPARAM");
		String userId =  MsgObj.GetMsgByName("_userId");
		String userName =  MsgObj.GetMsgByName("_userName");
		//pdf签章
		if(StringUtils.isNotBlank(extParam) && "pdf2018Signature".equals(extParam)) {
			fdId = MsgObj.GetMsgByName("RECORDID");
			modelId =  MsgObj.GetMsgByName("_fdModelId");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("保存文档：fdId:" + fdId + "; fdModelId:" + modelId
					+ "; fdModelName:" + modelName + "; fdKey:" + key);
		}
		SysAttMain sysAttMain = getSysAttMain(fdId, modelId, modelName, key,true);
		SysOrgPerson p = UserUtil.getUser();
		if(p.isAnonymous()){
			ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService)SpringBeanUtil.getBean("sysOrgPersonService");
			p = (SysOrgPerson)sysOrgPersonService.findByPrimaryKey(userId);
		}
		if (sysAttMain == null) {
			sysAttMain = new SysAttMain();
			if(p != null){
				sysAttMain.setFdCreatorId(p.getFdId());
			}
			setSysAttMain(sysAttMain, MsgObj);
			getSysAttMainService().add(sysAttMain);
		} else {
			setSysAttMain(sysAttMain, MsgObj);
			getSysAttMainService().updateByUser(sysAttMain,p.getFdId());
			/*
			 * 移动端是WPS云文档,PC端为WPS加载项,则将附件推送到云端
			 * PC：金格，移动：WPS云文档 或PC：WPS加载项   移动：WPS云文档
			 * 或PC：：WPS云文档   移动：WPS云文档
			 */
			if((SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(SysAttConfigUtil.getOnlineToolType())
					&& "2".equals(SysAttConfigUtil.isReadJGForMobile()))
					|| (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())
					&& "2".equals(SysAttConfigUtil.isReadWPSForMobile()))
					|| (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSCLOUD.equals(SysAttConfigUtil.getOnlineToolType())
					&& "2".equals(SysAttConfigUtil.isReadWPSCloudForMobile()))
					&& StringUtil.isNotNull(sysAttMain.getFdKey()) && !"originalAtt".equals(sysAttMain.getFdKey()))
			{
				SysAttWpsCloudUtil.syncAttToAddByMainId(sysAttMain.getFdId());
			}
		}

		// iWeboffice控件监控日志
		loggerInfo(sysAttMain, MsgObj);

		MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
		MsgObj.SetMsgByName("_fdAttMainId", sysAttMain.getFdId()); // 设置保存文档的Id
		MsgObj.MsgError(""); // 清除错误信息
		MsgObj.MsgFileClear(); // 清除文档内容
	}

	private void setSysAttMain(SysAttMain sysAttMain, iMsgServer2000 MsgObj)
			throws Exception {
		String extParam =  MsgObj.GetMsgByName("EXTPARAM");
		//pdf签章
		if(StringUtils.isNotBlank(extParam) && "pdf2018Signature".equals(extParam)) {
			if (StringUtil.isNotNull(MsgObj.GetMsgByName("_fdModelId"))) {
				sysAttMain.setFdModelName(MsgObj.GetMsgByName("_fdModelId"));
			}
		}else {
			if (StringUtil.isNotNull(MsgObj.GetMsgByName("RECORDID"))) {
				sysAttMain.setFdModelId(MsgObj.GetMsgByName("RECORDID"));
			}
		}
		if (StringUtil.isNotNull(MsgObj.GetMsgByName("_fdModelName"))) {
			sysAttMain.setFdModelName(MsgObj.GetMsgByName("_fdModelName"));
		}
		if (StringUtil.isNotNull(MsgObj.GetMsgByName("_fdKey"))) {
			if (null == sysAttMain.getFdKey()) {
				sysAttMain.setFdKey(MsgObj.GetMsgByName("_fdKey"));
			}
		}
		String fileType = MsgObj.GetMsgByName("FILETYPE");
		String fileName = MsgObj.GetMsgByName("FILENAME");
		fileName = fileName.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
		fileName = fileName.replaceAll("\\+", "%2B");
		fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
		if (StringUtil.isNotNull(fileName) && StringUtil.isNotNull(fileType)
				&& !fileName.endsWith(fileType)) {
			fileName = fileName + fileType;
		}
		// 防止出现后缀名为.do的文件
		if (".do".equals(fileName.substring(fileName.length() - 3, fileName.length())
				.toLowerCase())
				&& "office".equals(MsgObj.GetMsgByName("_attType"))) {
			fileName = fileName + "c";
		}
		if (StringUtil.isNull(sysAttMain.getFdFileName())) {
			sysAttMain.setFdFileName(fileName);
		} else {
			// 合同正文只是需要修改文件扩展名，因为docx文件金格控件打开时，下载后的文档没有限定区域的控制了
			if ("mainOnline".equals(sysAttMain.getFdKey())
					&& "com.landray.kmss.km.agreement.model.KmAgreementApply".equals(sysAttMain.getFdModelName())) {
				int idx = sysAttMain.getFdFileName().lastIndexOf(".");
				String mainOnlineSimpleName = sysAttMain.getFdFileName().substring(0, idx);
				sysAttMain.setFdFileName(mainOnlineSimpleName + fileType);
			}
		}
		
		sysAttMain.setFdAttType(MsgObj.GetMsgByName("_attType"));
		sysAttMain.setFdContentType(FileMimeTypeUtil.getContentType(fileName));
		sysAttMain.setFdSize(Double.valueOf(MsgObj.MsgFileSize()));
		if (sysAttMain.getDocCreateTime() == null) {
			sysAttMain.setDocCreateTime(new Date());
		}
		sysAttMain.setInputStream(org.hibernate.engine.jdbc.NonContextualLobCreator.INSTANCE.createBlob(MsgObj.MsgFileBody()).getBinaryStream());
		// 非暂存操作，如果附件的当前在线编辑人字段不为空，则置为空，防止后面的人打开会提示附件正在被编辑
		if (!"true".equals(MsgObj.GetMsgByName("_saveDraft"))) {
			if (StringUtil.isNotNull(sysAttMain.getFdPersonId())
					|| sysAttMain.getFdLastOpenTime() != null) {
				sysAttMain.setFdPersonId(null);
				sysAttMain.setFdLastOpenTime(null);
			}
		}
	}
}
