package com.landray.kmss.third.ding.notify.service.spring;

import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyWorkrecord;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyWorkrecordService;

public class ThirdDingNotifyWorkrecordServiceImp extends ExtendDataServiceImp
		implements IThirdDingNotifyWorkrecordService {

	@Override
	public String delete(String recordId, String todoId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdRecordId = :recordId and fdNotifyId = :todoId");
		info.setParameter("recordId", recordId);
		info.setParameter("todoId", todoId);
		List<ThirdDingNotifyWorkrecord> records = this.findList(info);
		if (records == null || records.isEmpty()) {
			throw new Exception("找不到对应的record记录");
		}
		if (records.size() > 1) {
			throw new Exception("找到多条record记录，数据可能有问题，recordId=" + recordId
					+ "，todoId=" + todoId);
		}
		ThirdDingNotifyWorkrecord record = records.get(0);
		String fdId = record.getFdId();
		this.delete(record);
		return fdId;
	}

	@Override
	public ThirdDingNotifyWorkrecord findByRecordId(String recordId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdRecordId = :recordId");
		info.setParameter("recordId", recordId);
		List<ThirdDingNotifyWorkrecord> records = this.findList(info);
		if (records == null || records.isEmpty()) {
			return null;
		}
		if (records.size() > 1) {
			throw new Exception("找到多条record记录，数据可能有问题，recordId=" + recordId);
		}
		return records.get(0);
	}

}
