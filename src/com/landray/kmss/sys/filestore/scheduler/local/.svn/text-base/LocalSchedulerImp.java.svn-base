package com.landray.kmss.sys.filestore.scheduler.local;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.scheduler.impl.AbstractQueueScheduler;
import com.landray.kmss.sys.filestore.scheduler.local.interfaces.ILocalScheduler;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.sys.filestore.util.ThumbnailUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LocalSchedulerImp extends AbstractQueueScheduler implements ILocalScheduler {

	private void distributeImageFile(SysFileConvertQueue convertQueue, String encryptionMode) {
		try {
			String filePath = dataService.getFilePath(convertQueue.getFdFileId(), convertQueue.getFdAttMainId());
			if (StringUtil.isNull(filePath)) {
				try {
					dataService.setLocalConvertQueue("taskInvalid", convertQueue.getFdId(), "");
				} catch (Exception e) {
					logDebug("设置队列为无效队列出错", e);
				}
			} else {
				dataService.setLocalConvertQueue("taskAssigned", convertQueue.getFdId(), "");
				if (StringUtil.isNotNull(filePath)) {
					imageCompress(convertQueue, filePath, encryptionMode);
				}
			}
		} catch (Throwable throwable) {
			logger.warn(throwable.getLocalizedMessage());
		}
	}

	private void imageCompress(SysFileConvertQueue imageTask, String filePath, String encryptionMode) {
		InputStream in = null;
		ByteArrayOutputStream out = null;
		String extParam = imageTask.getFdConverterParam();
		if (StringUtil.isNull(extParam)) {
			logger.warn("图片压缩参数为空");
			return;
		}
		try {
			JSONObject paramJson = JSONObject.fromObject(extParam);
			JSONArray thumbInfos = paramJson.getJSONArray("thumb");
			int infoLen = thumbInfos.size();
			dataService.setLocalConvertQueue("taskBegin", imageTask.getFdId(), "");
			ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
					.getBean("sysAttUploadService");
			SysAttFile attFile = sysAttUploadService.getFileByPath(filePath);
			ISysFileLocationProxyService sysFileLocationProxyService = SysFileLocationUtil
					.getProxyService(attFile.getFdAttLocation());
			String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
			ISysFileLocationProxyService writeFileLocationProxyService = SysFileLocationUtil.getProxyService();
			for (int i = 0; i < infoLen; i++) {
				JSONObject thumbInfo = JSONObject.fromObject(thumbInfos.get(i));
				String convertFileName = thumbInfo.getString("name");
				String convertFilePath = getImageConvertFile(imageTask, filePath, convertFileName);
				out = new ByteArrayOutputStream();
				in = sysFileLocationProxyService.readFile(filePath, pathPrefix);
				String width = thumbInfo.getString("w");
				String height = "";
				if (thumbInfo.containsKey("h")) {
					height = thumbInfo.getString("h");
				}
				String suffix = FilenameUtils.getExtension(imageTask.getFdFileName());
				if (StringUtil.isNull(height)) {
					height = "0";
				}
				if ("png".equals(suffix) || "gif".equals(suffix)) {
					ThumbnailUtil.getInstance().compressImage(in, out, Integer.valueOf(width).intValue(),
							Integer.valueOf(height).intValue(), true, imageTask.getFdFileName());
				} else {
					ThumbnailUtil.getInstance().compressImage(in, out, Integer.valueOf(width).intValue(),
							Integer.valueOf(height).intValue(), true);
				}
				writeFileLocationProxyService.writeFile(new ByteArrayInputStream(out.toByteArray()), convertFilePath);
			}
			dataService.setLocalConvertQueue("taskSuccess", imageTask.getFdId(), "");
		} catch (Throwable throwable) {
			try {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						logger.info("关闭文件输出流出错", e);
					}
				}
			} catch (Throwable thr) {
				//
			}
			logger.warn("压缩图片出错", throwable);
			try {
				dataService.setLocalConvertQueue("taskFailure", imageTask.getFdId(), throwable.getLocalizedMessage());
			} catch (Exception e) {
				logger.warn("设置图片队列为压缩失败出错", e);
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.info("关闭文件输出流出错", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.info("关闭文件输入流出错", e);
				}
			}
		}
	}

	private String getImageConvertFile(SysFileConvertQueue imageTask, String filePath, String fileName)
			throws Exception {
		String convertFilePath = filePath + "_convert/" + imageTask.getFdConverterKey() + "_" + fileName;
		return convertFilePath;
	}

	@Override
	protected String getThreadName() {
		return "LocalScheduler";
	}

	public void setDataService(ISysFileConvertDataService dataService) {
		this.dataService = dataService;
	}

	@Override
	protected void doDistributeConvertQueue(String encryptionMode) {
		TransactionStatus status = null;
		Exception throwException = null;
		boolean success = false;
		try {
			status = TransactionUtils.beginNewTransaction();
			List<SysFileConvertQueue> unsignedTasks = dataService.getUnsignedTasks("local", "image2thumbnail");
			for (SysFileConvertQueue deliveryTaskQueue : unsignedTasks) {
				distributeImageFile(deliveryTaskQueue, encryptionMode);
			}
			TransactionUtils.commit(status);
			success = true;
		} catch (Exception e) {
			success = false;
			throwException  = e;
//			if (status != null)
//				TransactionUtils.rollback(status);

			logger.error("文件存储加密线程执行出错", e);
		}finally {
			if (throwException != null && status != null) {
				TransactionUtils.rollback(status);
			}
		}
	}

}
