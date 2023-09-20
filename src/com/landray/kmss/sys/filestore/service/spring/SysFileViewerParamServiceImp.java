package com.landray.kmss.sys.filestore.service.spring;

import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.model.SysFileViewerParam;
import com.landray.kmss.sys.filestore.service.ISysFileViewerParamService;
import com.landray.kmss.util.StringUtil;

@SuppressWarnings("unchecked")
public class SysFileViewerParamServiceImp extends BaseServiceImp implements ISysFileViewerParamService {

	@Override
	public void deleteQueueParams(SysFileConvertQueue queue, SysFileConvertClient queueClient) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		if (queueClient != null) {
			hqlInfo.setWhereBlock(
					"fdConverterKey = :converterKey and (fdFileId = :attFileId or fdAttMainId = :attMainId)");
			hqlInfo.setParameter("converterKey", queueClient.getConverterFullKey());
		} else {
			hqlInfo.setWhereBlock("fdFileId = :attFileId or fdAttMainId = :attMainId");
		}
		hqlInfo.setParameter("attFileId", queue.getFdFileId());
		hqlInfo.setParameter("attMainId", queue.getFdAttMainId());
		List<SysFileViewerParam> params = findList(hqlInfo);
		if (params != null && params.size() > 0) {
			for (SysFileViewerParam item : params) {
				delete(item);
			}
		}
	}

	@Override
	public void setViewerParam(Map<String, String> receiveInfos) throws Exception {
		String fileId = receiveInfos.get("fileId");
		String attMainId = receiveInfos.get("attMainId");
		String converterFullKey = receiveInfos.get("converterFullKey");
		String[] viewerKeys = null;
		String viewerParam = receiveInfos.get("viewerParam");
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "";
		if (StringUtil.isNotNull(fileId)) {
			whereBlock += " fdFileId=:fdFileId ";
		} else {
			whereBlock += " fdAttMainId=:fdAttMainId ";
		}
		hqlInfo.setWhereBlock(whereBlock);
		if (StringUtil.isNotNull(fileId)) {
			hqlInfo.setParameter("fdFileId", fileId);
		} else {
			hqlInfo.setParameter("fdAttMainId", attMainId);
		}
		hqlInfo.setGetCount(false);
		List<SysFileViewerParam> existParam = null;
		String errHintInfo = "other";
		try {
			viewerKeys = receiveInfos.get("viewerKey").split("-");
			existParam = findList(hqlInfo);
			if (existParam == null || existParam.size() == 0) {
				for (String viewerKey : viewerKeys) {
					SysFileViewerParam param = new SysFileViewerParam();
					param.setFdConverterKey(converterFullKey);
					param.setFdFileId(fileId);
					if (StringUtil.isNull(fileId)) {
						param.setFdAttMainId(attMainId);
					}
					param.setFdViewerKey(viewerKey);
					param.setFdParameterLong(viewerParam);
					param.setFdParameter("");
					try {
						add(param);
					} catch (Exception e) {
						errHintInfo = "setViewerParam-新增ViewerParam出错";
						throw e;
					}
				}
			} else {
				boolean update = false;
				for (SysFileViewerParam param : existParam) {
					if (canUpdateParam(param, viewerKeys)) {
						update = true;
						param.setFdConverterKey(converterFullKey);
						param.setFdParameterLong(viewerParam);
						param.setFdParameter("");
						try {
							update(param);
						} catch (Exception e) {
							errHintInfo = "setViewerParam-更新ViewerParam出错";
							throw e;
						}
					}
				}
				if (update == false) {
					for (String viewerKey : viewerKeys) {
						SysFileViewerParam param = new SysFileViewerParam();
						param.setFdConverterKey(converterFullKey);
						param.setFdFileId(fileId);
						if (StringUtil.isNull(fileId)) {
							param.setFdAttMainId(attMainId);
						}
						param.setFdViewerKey(viewerKey);
						param.setFdParameterLong(viewerParam);
						param.setFdParameter("");
						try {
							add(param);
						} catch (Exception e) {
							errHintInfo = "setViewerParam-新增ViewerParam出错";
							throw e;
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception(errHintInfo);
		}
	}

	private boolean canUpdateParam(SysFileViewerParam param, String[] viewerKeys) {
		boolean canUpdate = false;
		for (String viewerKey : viewerKeys) {
			if (param.getFdViewerKey().equals(viewerKey)) {
				canUpdate = true;
				break;
			}
		}
		return canUpdate;
	}
}
