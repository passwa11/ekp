package com.landray.kmss.third.ding.service;

import java.io.InputStream;

import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import net.sf.json.JSONObject;

public interface IThirdDingCspaceService extends IExtendDataService {

	public void uploadToDingSpace(String fileName, InputStream in, String fdId);

	public void downloadFromDing(JSONObject jsonObject);
}
