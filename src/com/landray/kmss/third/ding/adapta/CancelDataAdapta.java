package com.landray.kmss.third.ding.adapta;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.xform.base.service.controls.relation.RelationParamsField;
import com.landray.kmss.sys.xform.base.service.controls.relation.SysFormRelationAdapta;
import com.landray.kmss.third.ding.model.ThirdDingLeavelog;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.service.IThirdDingLeavelogService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.oracle.StringUtil;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CancelDataAdapta extends SysFormRelationAdapta {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CancelDataAdapta.class);

	static final String IS_REST = "Y";

	private IThirdDingLeavelogService thirdDingLeavelogService;

	public void setThirdDingLeavelogService(
			IThirdDingLeavelogService thirdDingLeavelogService) {
		this.thirdDingLeavelogService = thirdDingLeavelogService;
	}

	/**
	 * @param key和扩展点中sourceUUID匹配
	 * @searchs 事件控件搜索条件的值
	 * @ins 传入参数的值 RelationParamsField中fieldIdForm对应表单的ID
	 *      fieldValueForm对应该表单字段的值
	 * 
	 */
	@Override
	protected List<List<RelationParamsField>> getData(String key,
			List<RelationParamsField> searchs, List<RelationParamsField> ins) {
		// 1 表示加班；2 表示出差或外出 ; 3 表示请假
		String fdCancelUserId = null;
		String fdCancelType = "3";
		String fdTagName = "请假";

		for (RelationParamsField in : ins) {
			String name = in.getUuId();
			logger.info("入参key=>" + name);
			String value = in.getFieldValueForm();
			logger.info("入参值=>" + value);
			if ("fdCancelUserId".equals(name)) {
				fdCancelUserId = value;
			}
		}

		// 查询leaveLog数据
		return queryCancelList(fdCancelUserId, fdCancelType, fdTagName);
	}

	@Override
	protected File getTemplateFile(String key) {
		return new File(ConfigLocationsUtil
				.getWebContentPath()
				+ "/third/ding/template/cancel.xml");
	}


	public List<List<RelationParamsField>> queryCancelList(
			String fdCancelUserId, String fdCancelType, String fdTagName) {
		List<List<RelationParamsField>> rows = new ArrayList<List<RelationParamsField>>();
		if (StringUtil.isNull(fdCancelUserId)) {
			logger.warn("请求获取考勤信息的用户id为空！");
			return rows;
		}

		if (StringUtil.isNull(fdTagName)) {
			return rows;
		} else {
			fdTagName = fdTagName.trim();
		}
		logger.debug("fdCancelUserId:" + fdCancelUserId);
		logger.debug("fdCancelType:" + fdCancelType);
		logger.debug("fdTagName:" + fdTagName);
		try {
			String ding_userId = getDingUserIdByEkpUserId(fdCancelUserId);
			if (StringUtil.isNull(ding_userId)) {
				logger.warn("根据ekp人员(" + fdCancelUserId
						+ ")无法找到对应的关系，先确认人员对照表信息正常映射！");
				return rows;
			}
			logger.debug("user_id:" + getDingUserIdByEkpUserId(fdCancelUserId));

			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"fdEkpUserid =:fdEkpUserid and fdBizType =:fdBizType and fdIstrue='1' and fdTagName=:fdTagName and (fdIsDingSuit is NULL or fdIsDingSuit ='0')");
			hqlInfo.setParameter("fdEkpUserid", fdCancelUserId);
			hqlInfo.setParameter("fdBizType", Integer.valueOf(fdCancelType));
			hqlInfo.setParameter("fdTagName", fdTagName);
			List<ThirdDingLeavelog> LeaveList = thirdDingLeavelogService
					.findList(hqlInfo);

			logger.debug("=======LeaveList size()=======" + LeaveList.size()
					+ " fdEkpUserid:" + fdCancelUserId);

			if (LeaveList.size() > 0) {
				List<RelationParamsField> columns = new ArrayList<RelationParamsField>();
				Set<String> record = new HashSet<String>();
				for (int i = 0; i < LeaveList.size(); i++) {
					if (record.contains(LeaveList.get(i).getFdApproveId())) {
						logger.debug(
								"已有记录：" + LeaveList.get(i).getFdApproveId());
						continue;
					} else {
						record.add(LeaveList.get(i).getFdApproveId());
					}
					columns.add(
							new RelationParamsField("fdSubject", "",
									LeaveList.get(i).getDocSubject()));
					columns.add(
							new RelationParamsField("fdApproveId", "",
									LeaveList.get(i).getFdApproveId()));

				}
				rows.add(columns);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return rows;
	}

	/**
	 * 根据ekUserId查找钉钉userid
	 *
	 * @param ekpUserId
	 * @return
	 * @throws Exception
	 */
	private String getDingUserIdByEkpUserId(String ekpUserId)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("fdAppPkId");
		hqlInfo.setWhereBlock("fdEkpId=:fdEkpId");
		hqlInfo.setParameter("fdEkpId", ekpUserId);
		IOmsRelationService omsRelationService = (IOmsRelationService) SpringBeanUtil
				.getBean("omsRelationService");
		return (String) omsRelationService.findFirstOne(hqlInfo);
	}
}
