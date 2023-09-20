package com.landray.kmss.third.ding.adapta;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.xform.base.service.controls.relation.RelationParamsField;
import com.landray.kmss.sys.xform.base.service.controls.relation.SysFormRelationAdapta;
import com.landray.kmss.third.ding.oms.DingApiService;
import com.landray.kmss.third.ding.service.IOmsRelationService;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchduleDataAdapta extends SysFormRelationAdapta {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SchduleDataAdapta.class);

	static final String IS_REST = "Y";

	private static DingApiService dingApiService = DingUtils
			.getDingApiService();

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

		String opUserId = UserUtil.getUser().getFdId();
		String checkUserId = null;
		Date workDate = null;

		for (RelationParamsField in : ins) {
			String name = in.getUuId();
			logger.info("入参key=>" + name);
			String value = in.getFieldValueForm();
			logger.info("入参值=>" + value);

			if ("fdCheckUserId".equals(name)) {
				checkUserId = value;
			} else if ("fdDateTime".equals(name)) {
				workDate = DateUtil.convertStringToDate(value);
			}
		}

		// 调用排班接口组装结果
		return queryScheduleList(opUserId, checkUserId, workDate);
	}

	@Override
	protected File getTemplateFile(String key) {
		return new File(ConfigLocationsUtil
				.getWebContentPath()
				+ "/third/ding/template/schdule.xml");
	}

	/**
	 * 查询排班列表
	 * 
	 * @param opUserId
	 *            操作人id
	 * @param checkUserId
	 *            补卡人id
	 * @param fdDateTime
	 *            班次日期
	 * @return
	 * @throws Exception
	 */
	public List<List<RelationParamsField>> queryScheduleList(
			String opUserId, String checkUserId,
			Date fdDateTime) {
		List<List<RelationParamsField>> rows = new ArrayList<List<RelationParamsField>>();

		try {
			JSONObject params = new JSONObject();
			params.put("op_user_id", getDingUserIdByEkpUserId(opUserId)==null?getDingUserIdByEkpUserId(checkUserId):getDingUserIdByEkpUserId(opUserId));
			params.put("user_id", getDingUserIdByEkpUserId(checkUserId));
			params.put("date_time", fdDateTime.getTime());
			logger.warn("查询排班列表params=>" + JSONUtils.valueToString(params));
			JSONObject result = dingApiService.scheduleByDay(params, null);
			logger.warn("result=>" + JSONUtils.valueToString(result));

			if (result != null && result.getInt("errcode") == 0) {
				logger.info("钉钉查询排班操作成功");

				// 没有排班信息，不返回字段
				if (!result.containsKey("result")) {
					logger.warn("没有排班信息，不返回字段");
					return rows;
				} else {
					JSONArray ja = result.getJSONArray("result");
					logger.info("排班结果=>" + JSONUtils.valueToString(ja));

					for (Object jo : ja) {
						JSONObject jobj = (JSONObject) jo;

						// 跳过休息时间
						if (IS_REST.equals(jobj.getString("is_rest"))) {
							continue;
						}

						// 排班ID
						String class_id = jobj.getString("id");
						// 打卡时间
						String check_date_time = jobj
								.getString("check_date_time");

						List<RelationParamsField> columns = new ArrayList<RelationParamsField>();
						columns.add(
								new RelationParamsField("fdPunchId", "",
										class_id + "_"
												+ DateUtil
														.convertStringToDate(
																check_date_time)
														.getTime()));
						columns.add(
								new RelationParamsField("fdPunchCheckTime", "",
										check_date_time));

						rows.add(columns);
					}
				}
			} else {
				logger.warn("查询排班钉钉返回错误");
			}
		} catch (Exception e) {
			logger.error("公式加载错误", e);
			return null;
		}

		logger.info(
				"转换后的排班结果数=>" + rows.size());
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
