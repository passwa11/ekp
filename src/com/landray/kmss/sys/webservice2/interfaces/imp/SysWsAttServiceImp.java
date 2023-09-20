package com.landray.kmss.sys.webservice2.interfaces.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.DataHandler;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.ImageCompressUtils;
import com.landray.kmss.sys.webservice2.forms.AttachmentForm;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsAttService;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SysWsAttServiceImp extends BaseServiceImp implements
		ISysWsAttService {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysWsAttServiceImp.class);

	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	/**
	 * 保存多个附件
	 */
	@Override
	public List<String> save(List<AttachmentForm> attForms, String modelId,
							 String modelName) throws Exception {

		List<String> fdIds = new ArrayList<String>();
		for (AttachmentForm attForm : attForms) {
			String fdId = save(attForm, modelId, modelName);
			fdIds.add(fdId);
		}

		return fdIds;
	}
	
	@Override
	public void deleteByIds(String[] ids) throws Exception {
		sysAttMainService.delete(ids);
	}
	

	/**
	 * 保存单个附件
	 */
	@Override
	public String save(AttachmentForm attForm, String modelId, String modelName)
			throws Exception {

		SysAttMain sysAttMain = new SysAttMain();
		DataHandler fdAttachment = attForm.getFdAttachment();
		InputStream fdData = fdAttachment.getInputStream();

		if (fdData != null) {
			BeanUtils.copyProperties(sysAttMain, attForm);
			sysAttMain.setDocCreateTime(new Date());
			sysAttMain.setFdModelId(modelId);
			sysAttMain.setFdModelName(modelName);
			if (StringUtil.isNull(sysAttMain.getFdKey())) {
				sysAttMain.setFdKey("fdAttachment");
			}

			sysAttMain.setFdSize(new Double(fdData.available()));
			String fileName = sysAttMain.getFdFileName();
			String exeName = fileName.substring(fileName.lastIndexOf(".") + 1);
			if(ImageCompressUtils.isImageType(exeName)){
				sysAttMain.setFdAttType(SysAttBase.ATTACHMENT_TYPE_PIC);
			}
			String contentType = FileMimeTypeUtil
			.getContentType(fileName);
			sysAttMain.setFdContentType(contentType);
			// 附件内容的字节流
			sysAttMain.setInputStream(fdData);

			if (logger.isDebugEnabled()) {
                logger.debug("save files:" + fileName + " complete!");
            }
		}

		return sysAttMainService.add(sysAttMain); // 调用服务保存附件
	}

	/**
	 * 校验附件的大小，包括单个附件的长度和文档所有附件的长度
	 */
	@Override
	public void validateAttSize(List<AttachmentForm> attForms) throws Exception {
		if (attForms == null || attForms.isEmpty()) {
			return;
		}
		// 获取系统的附件最大长度配置
		String singleExpr = ResourceUtil
				.getKmssConfigString("sys.att.smallMaxSize");
		long singleMaxSize = parseFileSizeWithUnit(singleExpr);

		if (singleMaxSize <= 0) {
			singleMaxSize = 100 * 1024 * 1024;
			logger.debug("附件大小使用默认值：" + singleMaxSize);
		}
		String totalExpr = ResourceUtil
				.getKmssConfigString("sys.att.totalMaxSize");
		long totalMaxSize = parseFileSizeWithUnit(totalExpr);

		// 如果配置为空，则给予单附件100M，总大小1000M的默认值
		if (totalMaxSize <= 0) {
			totalMaxSize = 1000 * 1024 * 1024;
			logger.debug("附件总大小使用默认值：" + totalMaxSize);
		}
		int totalAttSize = 0; // 附件的总大小
		for (AttachmentForm attForm : attForms) {
			DataHandler fdAttachment = attForm.getFdAttachment();
			InputStream fdData = fdAttachment.getInputStream();
			int attSize = fdData.available();
			if (attSize > singleMaxSize) {
				String message = new StringBuilder().append("附件 ")
						.append(attForm.getFdFileName())
						.append(" 的文件长度超过限制，单个附件的最大长度为: ").append(singleExpr)
						.toString();

				throw new Exception(message);
			}
			totalAttSize = totalAttSize + attSize;
		}
		if (totalAttSize > totalMaxSize) {
			String message = new StringBuilder()
					.append("附件的长度超过限制，单个文档所有附件的最大长度: ").append(singleExpr)
					.toString();
			throw new Exception(message);
		}
	}

	/**
	 * 解析带文件长度单位的字串表达式
	 * 
	 * @param fileSizeExpr 文件长度的表达式，支持‘g’，‘m’，‘kb’等单位，不区分大小写
	 * @return 文件长度的数值，以字节为单位
	 */
	private long parseFileSizeWithUnit(String fileSizeExpr) throws Exception {
		long size = 0; // 文件大小
		if (StringUtil.isNotNull(fileSizeExpr)) {
			try {
				fileSizeExpr = fileSizeExpr.toLowerCase();
				if(fileSizeExpr.indexOf("g")>-1){
					fileSizeExpr = fileSizeExpr.replace("g", "");
					size = Long.parseLong(fileSizeExpr) * 1024 * 1024 * 1024;
				}else if(fileSizeExpr.indexOf("m")>-1){
					fileSizeExpr =fileSizeExpr.replace("m", "");
					size = Long.parseLong(fileSizeExpr) * 1024 * 1024;
				}else if(fileSizeExpr.indexOf("kb")>-1){
					fileSizeExpr =fileSizeExpr.replace("kb", "");
					size = Long.parseLong(fileSizeExpr) * 1024;
				}else{
					size = Long.parseLong(fileSizeExpr) * 1024 * 1024;
				}
				if(size < 0){
					logger.error("解析得到的附件长度为: " + size);
				}
			} catch (NumberFormatException e) {
				logger.error("解析附件长度配置信息时发生异常，请联系系统管理员！" + e.getMessage());
			}
		}
		return size;
	}

}
