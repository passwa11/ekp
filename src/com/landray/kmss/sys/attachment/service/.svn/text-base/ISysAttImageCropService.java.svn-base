package com.landray.kmss.sys.attachment.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;

public interface ISysAttImageCropService extends IBaseService {

	public JSONArray addCrop(IExtendForm form) throws Exception;

	public void updateCancel(String fdAttId) throws Exception;

	public JSONArray findByKeys(String modelName, String modelId, String[] keys) throws Exception;

	public JSONObject obtainImageInfo(String fdAttId) throws Exception;
}
