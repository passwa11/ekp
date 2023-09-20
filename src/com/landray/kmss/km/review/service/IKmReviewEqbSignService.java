package com.landray.kmss.km.review.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.elec.device.client.ElecChannelResponseMessage;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.sys.attachment.model.SysAttMain;

public interface IKmReviewEqbSignService {
	/**
	 * 
	 * @param kmReviewMain
	 * @param signers 签署方信息
	 * @param signFiles 签署文件
	 * @return
	 * @throws Exception
	 */
	public ElecChannelResponseMessage<?> sendEqb(KmReviewMain kmReviewMain, List<JSONObject> signers, List<SysAttMain> signFiles)
			throws Exception;
	
}
