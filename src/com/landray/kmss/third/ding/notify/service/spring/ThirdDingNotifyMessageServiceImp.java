package com.landray.kmss.third.ding.notify.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.third.ding.notify.model.ThirdDingNotifyMessage;
import com.landray.kmss.third.ding.notify.service.IThirdDingNotifyMessageService;

import java.util.List;

public class ThirdDingNotifyMessageServiceImp extends ExtendDataServiceImp
		implements IThirdDingNotifyMessageService {

	@Override
	public String delete(String dingTaskId, String todoId) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdDingTaskId = :dingTaskId and fdNotifyId = :todoId");
		info.setParameter("dingTaskId", dingTaskId);
		info.setParameter("todoId", todoId);
		List<ThirdDingNotifyMessage> records = this.findList(info);
		if (records == null || records.isEmpty()) {
			throw new Exception("找不到对应的record记录");
		}
		if (records.size() > 1) {
			throw new Exception("找到多条record记录，数据可能有问题，recordId=" + dingTaskId
					+ "，todoId=" + todoId);
		}
		ThirdDingNotifyMessage record = records.get(0);
		String fdId = record.getFdId();
		this.delete(record);
		return fdId;
	}

	@Override
	public ThirdDingNotifyMessage findByDingTaskId(String dingTaskId)
			throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdDingTaskId = :dingTaskId");
		info.setParameter("dingTaskId", dingTaskId);
		List<ThirdDingNotifyMessage> records = this.findList(info);
		if (records == null || records.isEmpty()) {
			return null;
		}
		if (records.size() > 1) {
			throw new Exception("找到多条record记录，数据可能有问题，recordId=" + dingTaskId);
		}
		return records.get(0);
	}

}
