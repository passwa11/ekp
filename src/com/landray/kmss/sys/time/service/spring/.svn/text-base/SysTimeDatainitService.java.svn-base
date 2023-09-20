package com.landray.kmss.sys.time.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.datainit.service.ISysDatainitSurroundInterceptor;
import com.landray.kmss.sys.datainit.service.spring.ProcessRuntime;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveRuleService;
import com.landray.kmss.util.StringUtil;

/**
 * @version:
 * @Description: 假期类型扩展点，对于相同的编号则取最大编号+1
 * @author: zby
 * @date: 2020年1月2日 上午10:52:33
 */
public class SysTimeDatainitService implements ISysDatainitSurroundInterceptor {

	private ISysTimeLeaveRuleService sysTimeLeaveRuleService;

	@Override
	public void beforeStoreModelData(IBaseModel model, Map<String, Object> data)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeRestoreModelData(IBaseModel model,
			Map<String, Object> data, Map<String, IBaseModel> cache,
			ProcessRuntime processRuntime) throws Exception {
		String modelName = (String) data.get("class");
		if (SysTimeLeaveRule.class.getName()
				.equals(modelName)) {
			String fdSerialNo = (String) data.get("fdSerialNo");
			if (StringUtil.isNull(fdSerialNo)) {
				return;
			}

			HQLInfo hqlInfo = new HQLInfo();
			List<SysTimeLeaveRule> leaveRuleList = sysTimeLeaveRuleService
					.findList(hqlInfo);
			Set<String> seriaNoList = new HashSet<>();
			if (leaveRuleList != null) {
				for (SysTimeLeaveRule rule : leaveRuleList) {
					// 已存在的跳过，只对新添加的做操作
					if (rule.getFdId().equals((String) data.get("fdId"))) {
						return;
					}
					seriaNoList.add(rule.getFdSerialNo());
				}
			}

			Set<Long> set = new HashSet<>();
			for (String value : seriaNoList) {
				set.add(Long.valueOf(value));
			}

			Long currentSn = Long.valueOf(fdSerialNo);
			// 假期编码相同,获取最大编号+1
			List<Long> sort = new ArrayList<>(set);
			Collections.sort(sort);
			if (set.contains(currentSn)) {
				fdSerialNo = String.valueOf(sort.get(sort.size() - 1) + 1);
			}

			data.put("fdSerialNo", fdSerialNo);
		}
	}

	public ISysTimeLeaveRuleService getSysTimeLeaveRuleService() {
		return sysTimeLeaveRuleService;
	}

	public void setSysTimeLeaveRuleService(
			ISysTimeLeaveRuleService sysTimeLeaveRuleService) {
		this.sysTimeLeaveRuleService = sysTimeLeaveRuleService;
	}

}
