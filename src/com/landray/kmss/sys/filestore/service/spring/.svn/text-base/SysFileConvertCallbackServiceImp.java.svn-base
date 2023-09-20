package com.landray.kmss.sys.filestore.service.spring;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.hibernate.ObjectNotFoundException;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.service.ISysFileConvertCallbackService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.util.FileMimeTypeUtil;

public class SysFileConvertCallbackServiceImp extends BaseServiceImp implements ISysFileConvertCallbackService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysFileConvertCallbackServiceImp.class);

	private ISysFileConvertQueueService sysFileConvertQueueService;
	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysFileConvertQueueService(ISysFileConvertQueueService sysFileConvertQueueService) {
		this.sysFileConvertQueueService = sysFileConvertQueueService;
	}

	public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	@Override
	public void setSuccessCallback(Map<String, String> receiveInfos) throws Exception {
		SysAttMain sysAttMain = this.findSysAttByReceiveInfos(receiveInfos);
		if(null!=sysAttMain){
			if (sysAttMain.getFdModelName().contains("km.archives")) {
				saveArchivesFile(receiveInfos);
			}else if(sysAttMain.getFdModelName().contains("kms.multidoc")){
				saveMultidocFile(receiveInfos);
			}
		}
	}
	
	private SysAttMain findSysAttByReceiveInfos(Map<String, String> receiveInfos) throws Exception{
		String converterType = "Aspose";
		String converterKey = "toPDF";
		String converterFullKey = receiveInfos.get("converterFullKey");
		if (converterFullKey == null || !(converterKey + "-" + converterType).equals(converterFullKey)) {
			logger.debug("converterFullKey：" + converterFullKey + " 跳过后续逻辑");
			return null;
		}
		String queueId = receiveInfos.get("queueId");
		SysAttMain sysAttMain = null;
		if (!sysFileConvertQueueService.isExist(queueId)) {
			throw new IllegalArgumentException("未找到队列：" + queueId + " 跳过后续逻辑");
		}
		SysFileConvertQueue queue = (SysFileConvertQueue) sysFileConvertQueueService.findByPrimaryKey(queueId);
		try {
			sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(queue.getFdAttMainId());
		} catch (ObjectNotFoundException e) {
			try {
				//移除队列
				sysFileConvertQueueService.delete(queueId);
			} catch (Exception eq1) {
				logger.info("移除队列出错", eq1);
			}
			return null;
		}
		return sysAttMain;
	}
	

	/**
	 * 归档异步附件转换后回调
	 * @param receiveInfos
	 * @throws Exception
	 */
	private void saveArchivesFile(Map<String, String> receiveInfos) throws Exception {
		String converterType = "Aspose";
		String converterKey = "toPDF";
		String converterFullKey = receiveInfos.get("converterFullKey");
		if (converterFullKey == null || !(converterKey + "-" + converterType).equals(converterFullKey)) {
			logger.debug("converterFullKey：" + converterFullKey + " 跳过后续逻辑");
			return;
		}
		String queueId = receiveInfos.get("queueId");
		if (!sysFileConvertQueueService.isExist(queueId)) {
			throw new IllegalArgumentException("未找到队列：" + queueId + " 跳过后续逻辑");
		}
		SysFileConvertQueue queue = (SysFileConvertQueue) sysFileConvertQueueService.findByPrimaryKey(queueId);
		SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(queue.getFdAttMainId());
		saveArchivesAtt(converterType, converterKey, sysAttMain);
		
	}
	
	@Override
	public void saveMultidocFile(Map<String, String> receiveInfos) throws Exception {
		String attMainId = receiveInfos.get("attMainId");
		if (null != attMainId) {
			String converterType = "Aspose";
			String converterKey = "toPDF";
			String converterFullKey = receiveInfos.get("converterFullKey");
			if (converterFullKey == null || !(converterKey + "-" + converterType).equals(converterFullKey)) {
				logger.debug("converterFullKey：" + converterFullKey + " 跳过后续逻辑");
				return;
			}
			String queueId = receiveInfos.get("queueId");
			if (!sysFileConvertQueueService.isExist(queueId)) {
				throw new IllegalArgumentException("未找到队列：" + queueId + " 跳过后续逻辑");
			}
			SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(attMainId);
			// 校验转换后的PDF是否存在
			String pdfFileName = converterKey + "-" + converterType + "_pdf";
			String pdfFilePath = SysAttViewerUtil.getConvertFilePath(sysAttMain, pdfFileName);
			String filePath = SysAttViewerUtil.getAttFilePath(sysAttMain);
			if (pdfFilePath == null || pdfFilePath.equals(filePath)) {
				// 使回调失败以再次重试
				logger.error(filePath + "转换后的PDF文件[" + pdfFileName + "]未找到");
				throw new FileNotFoundException(filePath + "转换后的PDF文件[" + pdfFileName + "]未找到");
			}
			// 读取转换PDF后的文件流
			InputStream inputStream = SysAttViewerUtil.getConvertFileInputStream(sysAttMain, pdfFileName);
			String fileName = sysAttMain.getFdFileName();
			int fdOrder=1;
			try {
				// PDF作为新附件添加到沉淀文件中
				if (fileName.contains(".")) {
					String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					if ("html".equals(extName)) {
						fdOrder=0;
						fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
					} else {
						// 保留原格式信息
						fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + extName + ".pdf";
					}
				}
				byte[] byteArray = IOUtils.toByteArray(inputStream);
				SysAttMain syspdf = new SysAttMain();
				syspdf.setDocCreateTime(new Date());
				syspdf.setFdFileName(fileName);
				syspdf.setFdSize(Double.valueOf(byteArray.length));
				syspdf.setFdFileName(fileName);
				syspdf.setDocCreateTime(new Date());
				String contentType = FileMimeTypeUtil.getContentType(fileName);
				syspdf.setFdContentType(contentType);
				syspdf.setInputStream(new ByteArrayInputStream(byteArray));
				syspdf.setFdKey("attachment");
				syspdf.setFdModelId(sysAttMain.getFdModelId());
				syspdf.setFdModelName("com.landray.kmss.kms.multidoc.model.KmsMultidocKnowledge");
				syspdf.setFdAttType("byte");
				syspdf.setFdOrder(fdOrder);
				syspdf.setAddQueue(false);
				sysAttMainService.add(syspdf);
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
	}

	private void saveArchivesAtt(String converterType, String converterKey, SysAttMain sysAttMain) throws Exception {
		//所有有此临时文件的主modelId
		HQLInfo hql = new HQLInfo();
		String where = " fdFileId = :fdFileId and fdKey=:fdTemp and fdId not in ("
				+ " select fdId from SysAttMain att where att.fdFileId = :fdFileId and att.fdKey=:fdAtt" + ")";
		hql.setParameter("fdFileId", sysAttMain.getFdFileId());
		hql.setParameter("fdTemp", "attArchivesMain_temp");
		hql.setParameter("fdAtt", "attArchivesMain");
		hql.setWhereBlock(where);
		hql.setSelectBlock(" fdModelId ");
		List<String> ids = sysAttMainService.findValue(hql);
		addPDFAtt(converterType, converterKey, sysAttMain, ids);
	}

	private void addPDFAtt(String converterType, String converterKey, SysAttMain sysAttMain, List<String> modelIds)
			throws Exception {
		if (sysAttMain == null) {
			return;
		}
		//校验转换后的PDF是否存在
		String pdfFileName = converterKey + "-" + converterType + "_pdf";
		String pdfFilePath = SysAttViewerUtil.getConvertFilePath(sysAttMain, pdfFileName);
		String filePath = SysAttViewerUtil.getAttFilePath(sysAttMain);
		if (pdfFilePath == null || pdfFilePath.equals(filePath)) {
			//使回调失败以再次重试
			logger.error(filePath + "转换后的PDF文件[" + pdfFileName + "]未找到");
			throw new FileNotFoundException(filePath + "转换后的PDF文件[" + pdfFileName + "]未找到");
		}
		//读取转换PDF后的文件流
		InputStream inputStream = SysAttViewerUtil.getConvertFileInputStream(sysAttMain, pdfFileName);
		String fileName = sysAttMain.getFdFileName();

		try {
			// PDF作为新附件添加到归档文件中
			if (fileName.contains(".")) {
				String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				if ("html".equals(extName)) {
					fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
				} else {
					// 保留原格式信息
					fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + extName + ".pdf";
				}
			}
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			for (String id : modelIds) {
				sysAttMainService.addAttachment(id, "com.landray.kmss.km.archives.model.KmArchivesMain",
						"attArchivesMain", byteArray, fileName, "byte");
			}
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

}
