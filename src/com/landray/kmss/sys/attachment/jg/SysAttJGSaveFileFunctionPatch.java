package com.landray.kmss.sys.attachment.jg;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;

import DBstep.iMsgServer2000;

/**
 * 加强金格控件保存文件类型
 * 
 * @author panyh
 *
 *         2020年9月23日 下午4:18:52
 */
public class SysAttJGSaveFileFunctionPatch extends SysAttJGSaveFileFunction {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttJGSaveFileFunctionPatch.class);

	@Override
	public void execute(RequestContext request, iMsgServer2000 MsgObj) throws Exception {
		byte[] mFileBody = MsgObj.ToDocument(MsgObj.MsgFileBody());// 将含有Office文件数据和批注数据的复合文件内容，流转为普通Office文件流
		MsgObj.MsgFileBody(mFileBody);
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
		} else if (".ppt".endsWith(fileType.toLowerCase()) || ".pptx".endsWith(fileType.toLowerCase())
				|| ".xls".endsWith(fileType.toLowerCase()) || ".pdf".endsWith(fileType.toLowerCase())){
			canSave = true;
		}

		if (canSave) {
			super.execute(request, MsgObj);
		} else {
			throw new RuntimeException("文件不合法或损坏，保存失败");
		}
	}
}
