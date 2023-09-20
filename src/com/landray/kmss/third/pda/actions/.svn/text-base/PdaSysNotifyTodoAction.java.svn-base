package com.landray.kmss.third.pda.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.notify.actions.SysNotifyTodoAction;
import com.landray.kmss.third.pda.model.PdaRowsPerPageConfig;
import com.landray.kmss.third.pda.service.IPdaDataShowService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;


/**
 * 待办筛选
 * @author linxiuxian
 *
 */
public class PdaSysNotifyTodoAction extends SysNotifyTodoAction {

	
	private void chgHqlInfo(HQLInfo hqlInfo, String hbmTab) throws Exception {
		String whereStr = hqlInfo.getWhereBlock();
		// 模块前缀
		Set<String> moduleSet = ((IPdaDataShowService) SpringBeanUtil
				.getBean("pdaDataShowService")).getSupportPdaModulesPrefix();
		List<String> list = new ArrayList<String>();
		// 模块前缀匹配格式
		for (String str : moduleSet) {
			list.add("/" + str.trim() + "/");
		}
		String[] extendArr = (new PdaRowsPerPageConfig()).getFdExtendsUrl();
		if (extendArr != null) {
			for (String url : extendArr) {
				list.add(url);
			}
		}

		if (!list.isEmpty()) {
			StringBuffer sb = new StringBuffer();
			extendArr = new String[] {};
			extendArr = list.toArray(extendArr);

			for (int i = 0; i < extendArr.length; i++) {
				if (StringUtil.isNull(sb.toString())) {
					sb.append(hbmTab + ".fdLink like :extendurl" + i);
				} else {
					sb.append(" or " + hbmTab + ".fdLink like :extendurl" + i);
				}
				if (extendArr[i].startsWith("/")) {
					hqlInfo.setParameter("extendurl" + i, extendArr[i] + "%");
				} else {
					hqlInfo.setParameter("extendurl" + i, "%" + extendArr[i]
							+ "%");
				}
			}
			if (StringUtil.isNotNull(whereStr)) {
				whereStr = "(" + whereStr + ") and " + "(" + sb.toString()
						+ ")";
			} else {
				whereStr = sb.toString();
			}
		}
		hqlInfo.setWhereBlock(whereStr);
	}

	@Override
	protected void chgHqlInfo(HQLInfo hqlInfo,boolean isShowFinish)throws Exception {
		super.chgHqlInfo(hqlInfo,isShowFinish);
		chgHqlInfo(hqlInfo,isShowFinish ? "sysNotifyTodoDoneInfo.todo":"sysNotifyTodo");
	}
}
