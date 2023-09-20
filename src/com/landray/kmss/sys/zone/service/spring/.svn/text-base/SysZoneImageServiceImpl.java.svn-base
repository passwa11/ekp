package com.landray.kmss.sys.zone.service.spring;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.zone.constant.SysZoneConstant;
import com.landray.kmss.sys.zone.context.ImageContext;
import com.landray.kmss.sys.zone.model.SysZonePersonInfo;
import com.landray.kmss.sys.zone.service.ISysZoneImageService;
import com.landray.kmss.sys.zone.service.ISysZonePersonInfoService;
import com.landray.kmss.sys.zone.util.ImageUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

public class SysZoneImageServiceImpl extends BaseServiceImp implements
		ISysZoneImageService {
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(SysZoneImageServiceImpl.class);
	
	protected ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	protected ISysZonePersonInfoService sysZonePersonInfoService;

	public void setSysZonePersonInfoService(
			ISysZonePersonInfoService sysZonePersonInfoService) {
		this.sysZonePersonInfoService = sysZonePersonInfoService;
	}
	
	@Override
	public JSONObject updateCropImg(String attId, ImageContext imageContext)
			throws Exception {
		String curUserId = UserUtil.getUser().getFdId();
		sysZonePersonInfoService.updateGetPerson(curUserId);
		
		JSONObject json = updateImg(curUserId, attId, imageContext);
		return json;
	}
	
	private JSONObject updateImg(String userId, String attId, ImageContext imageContext)
			throws Exception {
		SysAttMain attMain = (SysAttMain) this.sysAttMainService
				.findByPrimaryKey(attId, null, true);
		if (attMain == null) {
			return null;
		}
		// 源文件名
		String fileName = attMain.getFdFileName();
		// 源文件流输出到文件路径
		String filePath = imageContext.getZoomPath();
		if(StringUtil.isNull(filePath)){
			filePath = buildFileAbsolutePath(attId, fileName, this.sysAttMainService.getFile(attMain.getFdId()));
		}
		String cutFilePath = getCutImagePath(imageContext, fileName, filePath, attMain);
		try {
			JSONObject json = getAfterSaveData(fileName, filePath, cutFilePath, userId, attMain);
			// 删除原来的附件
			this.sysAttMainService.delete(attId);
			return json;
		} catch (Exception e) {
			LOGGER.error("头像裁剪失败！",e);
			throw e;
		}
	}
	
	@Override
	public JSONObject updateCropImgById(String userId, String attId, ImageContext imageContext)
			throws Exception {
		if(StringUtil.isNull(userId)){
			return null;
		}
		sysZonePersonInfoService.updateGetPerson(userId);
		JSONObject jsonObj = updateImg(userId, attId, imageContext);
		return jsonObj;
	}
	
	
	/**
	 * 取消裁剪
	 */
	@Override
	public void updateCancelImg(String attId, String path) throws Exception{
		try {
			if(StringUtil.isNotNull(attId)) {
                this.sysAttMainService.delete(attId);
            }
			
		}catch (Exception e) {
			throw e;
		}
	}
	private JSONObject getAfterSaveData(String fileName, String filePath,
			String cutFilePath, String userId,SysAttMain attMain) throws Exception {
		JSONObject json = new JSONObject();
		json.accumulate(
				"bigPicAttId",
				addAttByKey(filePath, fileName, cutFilePath,
						SysZoneConstant.BIG_PHOTO_KEY, 120, userId, attMain));
		json.accumulate(
				"middlePicAttId",
				addAttByKey(filePath, fileName, cutFilePath,
						SysZoneConstant.MEDIUM_PHOTO_KEY, 60, userId, attMain));
		json.accumulate(
				"smallPicAttId",
				addAttByKey(filePath, fileName, cutFilePath,
						SysZoneConstant.SMALL_PHOTO_KEY, 30, userId, attMain));
		return json;
	}

	private String getCutImagePath(ImageContext imageContext, String fileName,
			String filePath,SysAttMain attMain) throws Exception {
		// 裁剪后的文件路径
		String cutFilePath = filePath.substring(0,
				filePath.lastIndexOf('/') + 1) + IDGenerator.generateID();
		SysAttFile attFile = sysAttMainService.getFile(attMain.getFdId());
		ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil
				.getProxyService(attFile.getFdAttLocation());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		InputStream in = sysFileLocationService.readFile(filePath,pathPrefix);
		ByteArrayOutputStream cutFile = new ByteArrayOutputStream();
		// 生成了一个裁剪后的图片
		ImageUtil.cutImage(in, cutFile, imageContext.getStartX(),
				imageContext.getStartY(), imageContext.getWidth(),
				imageContext.getHeight(), FilenameUtils.getExtension(fileName));
		sysFileLocationService.writeFile(cutFile.toByteArray(), cutFilePath);
		
		return cutFilePath;
	}
	
	private String addAttByKey(String filePath, String fileName,
			String cutFilePath, String fdKey, int size, String userId,SysAttMain attMain) throws Exception {
		// 最终压缩后的文件路径
		String destFilePath = filePath.substring(0,
				filePath.lastIndexOf('/') + 1) + IDGenerator.generateID();
		ByteArrayOutputStream dest = new ByteArrayOutputStream();
		SysAttFile attFile = sysAttMainService.getFile(attMain.getFdId());
		ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil
				.getProxyService(attFile.getFdAttLocation());
		String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
		InputStream src = sysFileLocationService.readFile(cutFilePath,pathPrefix);
		// 对应尺寸的压缩
		ImageUtil.zoomImageRule(src, dest, size, size,fileName, fdKey);
		sysFileLocationService.writeFile(dest.toByteArray(), destFilePath);
		
		String sizeFileName = FilenameUtils.getBaseName(fileName) + "_" + size
				+ "_" + size + "_crop." + FilenameUtils.getExtension(fileName);
		return addAttAfterDel(fdKey, destFilePath, sizeFileName, userId, attFile);
	}

	/**
	 * 
	 * 将文件流输出到临时目录文件中
	 *
	 * @param attId
	 * @param fileName
	 * @param fromType
	 * @return
	 * @throws Exception
	 */
	private String buildFileAbsolutePath(String attId, String fileName, SysAttFile attFile)
			throws Exception {
//		String path = ResourceUtil.getKmssConfigString("kmss.resource.path")
		String path = "/zone/" + attId;
		String filePath = path + "/" + IDGenerator.generateID() + "."
				+ FilenameUtils.getExtension(fileName);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		sysAttMainService.findData(attId, out);
		SysFileLocationUtil.getProxyService(attFile.getFdAttLocation()).writeFile(out.toByteArray(), filePath);
		return filePath;
	}
	
	/**
	 * 
	 * 保存附件
	 *
	 * @param fdKey
	 * @param filePath
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	private String addAttAfterDel(String fdKey, String filePath, String fileName, String personId
			,SysAttFile attFile) throws Exception {
		InputStream is = null;
		try {
			@SuppressWarnings("unchecked")
			List<SysAttMain> attMains = sysAttMainService.findByModelKey(
					SysZoneConstant.MODEL_NAME, personId, fdKey);
			for (SysAttMain sysAttMain : attMains) {
				sysAttMainService.delete(sysAttMain);
			}
			String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
			is = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation()).readFile(filePath,pathPrefix);
			SysZonePersonInfo personInfo = new SysZonePersonInfo();
			personInfo.setFdId(personId);
			String downUrl = this.sysAttMainService.addAttachment(personInfo,
					fdKey, IOUtils.toByteArray(is), fileName, "byte");
			return downUrl.substring(downUrl.lastIndexOf("=") + 1);
		} catch (Exception e) {
			LOGGER.error("保存附件失败", e);
			throw e;
		} finally {
			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					LOGGER.error("关闭流错误", e);
				}
			}
		}
	}

	private String zoomImage(SysAttMain attMain, ImageContext imageContext) throws Exception{
		// 压缩图片来源
		String srcFile = null;
		try {
			SysAttFile attFile = sysAttMainService.getFile(attMain.getFdId());
			srcFile = buildFileAbsolutePath(attMain.getFdId(), attMain.getFdFileName(), attFile); 
			// 压缩的目标路径
			String zoomImgPath = buildZoomFilePath(attMain);
			ISysFileLocationProxyService sysFileLocationService = SysFileLocationUtil
					.getProxyService(attFile.getFdAttLocation());
			String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
			InputStream src = sysFileLocationService.readFile(srcFile,pathPrefix);
			ByteArrayOutputStream zoomImg = new ByteArrayOutputStream();
			ImageUtil.zoomImageRule(src, zoomImg,
					imageContext.getWidth(), imageContext.getHeight(),
					attMain.getFdFileName(), "zoom");
			sysFileLocationService.writeFile(zoomImg.toByteArray(), zoomImgPath);
			return zoomImgPath;
		} catch (Exception e) {
			LOGGER.error("压缩图片失败", e);
			throw e;
		}
	}

	@Override
	public ImageContext updateZoomImg(String attId, ImageContext imageContext)
			throws Exception {
		// 查找附件
		SysAttMain attMain = (SysAttMain) this.sysAttMainService
				.findByPrimaryKey(attId);
		// 压缩图片
		String zoomImgPath = this.zoomImage(attMain, imageContext);
		// 获取压缩后图片的大小
		int[] info = this.getZoomImgeInfo(zoomImgPath,attMain);		
		// 更新图片流
		InputStream is = null;
		try {
			SysAttFile attFile = sysAttMainService.getFile(attId);
			String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
			is = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation()).readFile(zoomImgPath,pathPrefix);
			// 更新现在的附件(sys_att_main)记录
			attMain.setFdFileName(zoomImgPath.substring(
					zoomImgPath.lastIndexOf('/') + 1, zoomImgPath.length()));
			attMain.setInputStream(is);
			this.sysAttMainService.update(attMain);
			imageContext = new ImageContext();
			imageContext.setWidth(info[0]);
			imageContext.setHeight(info[1]);
			imageContext.setZoomPath(zoomImgPath);
			imageContext.getAttMains().add(attMain);
			return imageContext;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	private String buildZoomFilePath(SysAttMain attMain) {
//		String zoomImgPath = ResourceUtil.getKmssConfigString("kmss.resource.path")
		String zoomImgPath = "/zone/" + attMain.getFdId() + "/" + IDGenerator.generateID()
				+ "." + FilenameUtils.getExtension(attMain.getFdFileName());
		return zoomImgPath;
	}

	/**
	 * 获取压缩后图片的大小
	 * 
	 * @param zoomImagePath
	 * @param realWidth
	 * @param realHeight
	 * @throws Exception 
	 */
	private int[] getZoomImgeInfo(String zoomImagePath,SysAttMain attMain) throws Exception {
		BufferedImage sourceImg;
		try {
			SysAttFile attFile = sysAttMainService.getFile(attMain.getFdId());
			String pathPrefix = attFile.getFdCata() == null ? null : attFile.getFdCata().getFdPath();
			InputStream in = SysFileLocationUtil.getProxyService(attFile.getFdAttLocation())
					.readFile(zoomImagePath,pathPrefix);
			sourceImg = ImageIO.read(in);
			return new int[] { sourceImg.getWidth(), sourceImg.getHeight() };
		} catch (IOException e) {
			LOGGER.error("获取压缩后图片的大小异常", e);
			throw e;
		}
	}
}
