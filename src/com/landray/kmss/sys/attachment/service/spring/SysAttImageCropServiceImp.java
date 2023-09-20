package com.landray.kmss.sys.attachment.service.spring;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import com.landray.kmss.util.ClassUtils;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.attachment.forms.SysAttImageCropForm;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttImageCropService;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.ImageCropUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SysAttImageCropServiceImp extends BaseServiceImp implements
		ISysAttImageCropService {
	
	protected ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	@Override
	public JSONArray addCrop(IExtendForm form) throws Exception {
		SysAttImageCropForm cropForm = (SysAttImageCropForm) form;

		String fdModelName = cropForm.getFdModelName();
		if ("com.landray.kmss.sys.zone.model.SysZonePersonInfo".equals(fdModelName)) {
			// 当前用户只能修改自己的头像
			if (!cropForm.getFdModelId().equals(UserUtil.getKMSSUser().getUserId())
					&& !UserUtil.getKMSSUser().isAdmin()) {
				return null;
			}
		}

		String fdAttId = cropForm.getFdCropId();
		SysAttMain att = getAtt(fdAttId);
		JSONArray obj = null;
		if (null != att) {
			obj = cropAndCompress(cropForm, att);
			sysAttMainService.delete(fdAttId);
			if(UserOperHelper.allowLogOper("addCrop", SysAttMain.class.getName())){
				UserOperContentHelper.putDelete(fdAttId, att.getFdFileName(),SysAttMain.class.getName());
			}
		}
		return obj;
	}

	private JSONArray cropAndCompress(SysAttImageCropForm cropForm, SysAttMain attMain)
			throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		// 裁剪
		ImageCropUtil.cropImage(attMain.getInputStream(), out, cropForm.getFdStartX(), cropForm.getFdStartY(), cropForm
				.getFdCropWidth(), cropForm.getFdCropHeight(), FilenameUtils.getExtension(attMain.getFdFileName()));
		// 压缩（对应尺寸）
		return compressImage(cropForm, attMain, out);
	}

	private JSONArray compressImage(SysAttImageCropForm cropForm,
			SysAttMain attMain, ByteArrayOutputStream out)
			throws Exception {
		Map<String, Integer[]> map = getCompressWHMap(cropForm, attMain.getFdKey());
		String fileName = attMain.getFdFileName();
		byte[] bytes = out.toByteArray();
		JSONArray array = new JSONArray();
		String dUrl = null;
		for (Entry<String, Integer[]> entry : map.entrySet()) {

			String fdModelName = cropForm.getFdModelName();
			if ("com.landray.kmss.sys.zone.model.SysZonePersonInfo".equals(fdModelName)) {
                delLastCrop(cropForm, entry.getKey());
            }

			dUrl = compressByKey(cropForm, entry, bytes, fileName);
			JSONObject json = new JSONObject();
			json.put("key" , entry.getKey());
			json.put("value", dUrl.substring(dUrl.indexOf("fdId=") + 5));
			array.add(json);
			if(UserOperHelper.allowLogOper("addCrop", SysAttMain.class.getName())){
				UserOperContentHelper.putAdd(dUrl.substring(dUrl.indexOf("fdId=") + 5), fileName,SysAttMain.class.getName());
			}
		}
		return array;
	}

	@SuppressWarnings("unchecked")
	private void delLastCrop(SysAttImageCropForm cropForm, String fdKey) throws Exception {
		List<SysAttMain> attMains = (List<SysAttMain>) sysAttMainService.findByModelKey(cropForm.getFdModelName(),
				cropForm.getFdModelId(), fdKey);
		for (SysAttMain sysAttMain : attMains) {
			sysAttMainService.delete(sysAttMain);
		}
	}

	/**
	 * 压缩 <br>
	 * Closing a ByteArrayOutputStream/ByteArrayInputStream has no effect
	 * 
	 * @param cropForm
	 * @param entry
	 * @param bytes
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private String compressByKey(SysAttImageCropForm cropForm,
			Entry<String, Integer[]> entry, byte[] bytes,
			String fileName) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageCropUtil.compress(new ByteArrayInputStream(bytes), out, entry.getValue()[0], entry.getValue()[1], fileName);
		IBaseModel model = (IBaseModel) com.landray.kmss.util.ClassUtils.forName(cropForm.getFdModelName()).newInstance();
		model.setFdId(cropForm.getFdModelId());
		return sysAttMainService.addAttachment(model, entry.getKey(), out.toByteArray(), fileName, "pic");
	}
	
	private Map<String, Integer[]> getCompressWHMap(SysAttImageCropForm cropForm, String fdKey) {
		Map<String, Integer[]> map = new HashMap<String, Integer[]>();
		String fdCropKeys = cropForm.getFdCropKeys();
		String[] cropKeys = ImageCropUtil.CROP_KEYS;
		boolean isAll = StringUtil.isNull(fdCropKeys);
		if (isAll || fdCropKeys.contains(cropKeys[0])) {
			map.put(fdKey + cropKeys[0], cropForm.getBigWH());
		}
		if (isAll || fdCropKeys.contains(cropKeys[1])) {
			map.put(fdKey + cropKeys[1], cropForm.getMediumWH());
		}
		if (isAll || fdCropKeys.contains(cropKeys[2])) {
			map.put(fdKey + cropKeys[2], cropForm.getSmallWH());
		}
		return map;
	}

	@Override
	public void updateCancel(String fdAttId) throws Exception {
		sysAttMainService.delete(fdAttId);
	}
	
	@Override
	public JSONArray findByKeys(String modelName, String modelId, String[] keys) throws Exception {
		JSONArray obj = new JSONArray();
		for (int i = 0; i < keys.length; i++) {
			buildData(modelName, modelId, keys[i], obj);
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	private void buildData(String modelName, String modelId, String key, JSONArray arr) throws Exception {
		List<SysAttMain> list = sysAttMainService.findByModelKey(modelName, modelId, key);
		if (!list.isEmpty()) {
			JSONObject obj = new JSONObject();
			obj.accumulate("key", key);
			obj.accumulate("value", list.get(0).getFdId());
			arr.add(obj);
		}
	}

	@Override
	public JSONObject obtainImageInfo(String fdAttId) throws Exception {
		JSONObject json = new JSONObject();
		SysAttMain att = getAtt(fdAttId);
		if (null != att) {
			BufferedImage sourceImg = ImageIO.read(att.getInputStream());
			json.accumulate("width", sourceImg.getWidth());
			json.accumulate("height", sourceImg.getHeight());
		}
		return json;
	}

	@SuppressWarnings("unchecked")
	private SysAttMain getAtt(String fdAttId) throws Exception {
		List<SysAttMain> list = sysAttMainService.findModelsByIds(new String[] { fdAttId });
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
