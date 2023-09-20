package com.landray.kmss.sys.zone.service;

import net.sf.json.JSONObject;

import com.landray.kmss.sys.zone.context.ImageContext;

/**
 * 头像接口：<br>
 * 对外开放的接口方法：获取图像的路径。
 * 
 * @author XuJieYang
 * 
 */
public interface ISysZoneImageService {

	/**
	 * 裁剪图片
	 * 
	 * @param attId
	 * @param startX
	 * @param satrtY
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	JSONObject updateCropImg(String attId, ImageContext imageContext)
			throws Exception;

	/**
	 * 裁剪图片
	 * 
	 * @param attId
	 * @param startX
	 * @param satrtY
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	ImageContext updateZoomImg(String attId, ImageContext imageContext)
			throws Exception;
	
	
	public void updateCancelImg(String attId, String path) throws Exception;
	
	public JSONObject updateCropImgById(String userId, String attId, ImageContext imageContext)
			throws Exception;
		
}
