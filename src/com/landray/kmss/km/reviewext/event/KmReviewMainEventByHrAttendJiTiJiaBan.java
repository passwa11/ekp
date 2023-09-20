package com.landray.kmss.km.reviewext.event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataEvent;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.util.StringUtil;

/**
 * 集体加班、签卡、休假表单提交验证
 * 
 * @author 孙艳妮
 * @create 2022-10-06 14:41
 */
public class KmReviewMainEventByHrAttendJiTiJiaBan implements IExtendDataEvent {
	private IKmReviewMainService kmReviewMainService;
	private static final Log log = LogFactory.getLog(KmReviewMainEventByHrAttendJiTiJiaBan.class);

	public IKmReviewMainService getKmReviewMainService() {
		return kmReviewMainService;
	}

	public void setKmReviewMainService(IKmReviewMainService kmReviewMainService) {
		this.kmReviewMainService = kmReviewMainService;
	}

	@Override
	public void onInit(RequestContext requestContext, IExtendDataModel iExtendDataModel,
			ISysMetadataParser iSysMetadataParser) throws Exception {
	}

	@Override
	public void onAdd(IExtendDataModel iExtendDataModel, ISysMetadataParser iSysMetadataParser) throws Exception {
		StringBuffer sbError = new StringBuffer();
		sbError.append(validateSubmit(iExtendDataModel));

		if (sbError.toString().length() > 0) {
			log.error(sbError.toString());
			throw new Exception(sbError.toString());
		}
	}

	@Override
	public void onUpdate(IExtendDataModel iExtendDataModel, ISysMetadataParser iSysMetadataParser) throws Exception {
		StringBuffer sbError = new StringBuffer();
		sbError.append(validateSubmit(iExtendDataModel));
		if (sbError.toString().length() > 0) {
			log.error(sbError.toString());
			throw new Exception(sbError.toString());
		}
	}

	@Override
	public void onDelete(IExtendDataModel iExtendDataModel, ISysMetadataParser iSysMetadataParser) throws Exception {
	}

	public String validateSubmit(IExtendDataModel iExtendDataModel) {

		// 集体加班，集体签卡，集体请假，都遵循以下规则

		// 仅仅限于： 当月4日17:30之前可提交 上月1日至上月最后一日单据
		// 第一步：判断集体归属月，是上月 或当月

		// 如果上月，则判断服务器当前时间是否超过了当月4日17:31，
		// 若超过，则不允许提交；
		// 若无超过，则判断明细表加班日期是否都在上月1日-上月最后一日
		// 如果当月，则判断明细表是否加班日期都是当月的
		DateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		DateFormat sdf_y_m_d = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat sdf_y_m_d_h_m_s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sdf_ymd = new SimpleDateFormat("yyyyMMdd");
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例

		ca.setTime(new Date()); // 设置时间为当前时间
		int ben_yue_m = ca.get(Calendar.MONTH) + 1;

		StringBuffer sbError = new StringBuffer();

		// 上月
		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		int shang_yue_m = ca.get(Calendar.MONTH) + 1;

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = iExtendDataModel.getExtendDataModelInfo().getModelData();
			// 集体归属月
			int month_sel = Float.valueOf(map.get("fd_3b34b14dc7f032") + "").intValue();

			// 申请类型：1加班2签卡3假期
			int jiti_type = Float.valueOf(map.get("fd_3b397a437b3168") + "").intValue();

			Date start_date = null;
			Date end_date = null;

			String detail_table_id = "";

			if (jiti_type == 1) {
				// fd_3b142127f54434 加班明细表
				detail_table_id = "fd_3b142127f54434";
			} else if (jiti_type == 2) {
				// fd_3b395adbd90a90 签卡明细表
				detail_table_id = "fd_3b395adbd90a90";
			} else if (jiti_type == 3) {
				// fd_3b395add7a76e2 假期明细表
				detail_table_id = "fd_3b395add7a76e2";
			}
			if (month_sel == shang_yue_m) {
				ca.setTime(new Date()); // 设置时间为当前时间

				// 获取上个月1日
				ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
				ca.set(Calendar.DAY_OF_MONTH, 1);// 1日
				start_date = ca.getTime();
				System.out.println(sdf_y_m_d.format(start_date));

				// 获取上个月31日
				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
				end_date = ca.getTime();
				System.out.println(sdf_y_m_d.format(end_date));
				// 当月4日17:31
				ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH) + 1, 4, 17, 31, 0);
				long set_close = ca.getTimeInMillis();

				if (System.currentTimeMillis() > set_close) {// 超了时间不允许提交
					sbError.append("当前时间" + sdf_y_m_d_h_m_s.format(new Date()) + "，超了限制时间("
							+ sdf_y_m_d_h_m_s.format(ca.getTime()) + ")，不允许归属月是" + shang_yue_m + "月的单据");
				}

			} else if (month_sel == ben_yue_m) {
				ca.setTime(new Date()); // 设置时间为当前时间
				ca.set(Calendar.DAY_OF_MONTH, 1);// 当月1日
				start_date = ca.getTime();

				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));// 当月最后1日
				end_date = ca.getTime();
			} else {
				sbError.append("当前时间只能填归属月份是" + shang_yue_m + "月和" + ben_yue_m + "月的单据");
			}

			if (sbError.toString().length() == 0) {
				List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();

				childList = (List<Map<String, Object>>) map.get(detail_table_id);

				if (childList.size() > 0) {
					for (int i = 0; i < childList.size(); i++) {

						Map<String, Object> detailMap = childList.get(i);

						if (jiti_type == 1) {
							// fd_3b142127f54434 加班明细表ID
							// 开始加班日期 （年月日时分） fd_3b345093ff2fd4
							// 结束加班日期 （年月日时分） fd_3b1423c2db2074
							Date sel_start = null;
							Date sel_end = null;
							if (!StringUtil.isNull(detailMap.get("fd_3b345093ff2fd4") + "")) {

								sel_start = sdf.parse(detailMap.get("fd_3b345093ff2fd4") + "");
								sel_end = sdf.parse(detailMap.get("fd_3b1423c2db2074") + "");

								System.out.println(sdf_ymd.format(start_date));
								System.out.println(sdf_ymd.format(sel_start.getTime()));
								System.out.println(sdf_ymd.format(sel_end.getTime()));
								System.out.println(sdf_ymd.format(end_date));

								// 判断明细表加班日期的是否在归属月1日至归属月31日
								// 不在开始与结束日期范围内
								boolean is_guifan_1 = Integer.valueOf(sdf_ymd.format(start_date)) <= Integer.valueOf(sdf_ymd.format(sel_start.getTime()));
								boolean is_guifan_2 = Integer.valueOf(sdf_ymd.format(sel_start.getTime())) <= Integer.valueOf(sdf_ymd.format(end_date));
								
								if (!is_guifan_1 || !is_guifan_2) {
									sbError.append("明细表第" + (1 + i) + "行，加班日期不在" + month_sel + "月;");
								}

								// 开始日期与结束日期是否同一天
								boolean is_tong = sdf_y_m_d.format(sel_start).equals(sdf_y_m_d.format(sel_end));

								System.out.println(sdf_y_m_d.format(sel_start));
								System.out.println(sdf_y_m_d.format(sel_end));

								if (!is_tong) {// 如果不是同一天
									// 可以是开始日期+1=结束日期
									ca.setTime(sel_start); // 设置时间为当前时间
									ca.add(Calendar.DATE, 1);

									System.out.println(sdf_y_m_d.format(sel_end));
									System.out.println(sdf_y_m_d.format(ca.getTime()));
//									if (!sdf_y_m_d.format(sel_end).equals(sdf_y_m_d.format(ca.getTime()))) {
//										sbError.append("明细表第" + (1 + i) + "行，加班日期不可以超过2天;");
//									}
								}

							} else {
								sbError.append("明细表第" + (1 + i) + "行，加班日期不可以空;");
							}

						} else if (jiti_type == 2) {
							// fd_3b395adbd90a90 签卡明细表ID

							// 刷卡日期 （年月日） fd_3b395b9e7de34c
							Date sel = null;
							if (!StringUtil.isNull(detailMap.get("fd_3b395b9e7de34c") + "")) {
								sel = sdf.parse(detailMap.get("fd_3b395b9e7de34c") + "");

								// 判断明细表签卡日期的是否在归属月1日至归属月31日
								// 不在开始与结束日期范围内

								System.out.println(sdf_ymd.format(start_date));
								System.out.println(sdf_ymd.format(sel.getTime()));
								System.out.println(sdf_ymd.format(end_date));

								boolean is_guifan_1 = Integer.valueOf(sdf_ymd.format(start_date)) <= Integer.valueOf(sdf_ymd.format(sel.getTime()));
								boolean is_guifan_2 = Integer.valueOf(sdf_ymd.format(sel.getTime())) <= Integer.valueOf(sdf_ymd.format(end_date));
								
								if (!is_guifan_1 || !is_guifan_2) {
									sbError.append("明细表第" + (1 + i) + "行，签卡日期不在" + month_sel + "月;");
								}
							} else {
								sbError.append("明细表第" + (1 + i) + "签卡日期不可以空;");
							}

						} else if (jiti_type == 3) {
							// fd_3b395add7a76e2 假期明细表ID

							// 请假日期 （年月日） fd_3b395b9f706872
							Date sel = null;
							if (!StringUtil.isNull(detailMap.get("fd_3b395b9f706872") + "")) {
								sel = sdf.parse(detailMap.get("fd_3b395b9f706872") + "");
								// 判断明细表请假日期的是否在归属月1日至归属月31日
								// 不在开始与结束日期范围内

								System.out.println(sdf_ymd.format(start_date));
								System.out.println(sdf_y_m_d.format(sel.getTime()));
								System.out.println(sdf_ymd.format(end_date));

								boolean is_guifan_1 = Integer.valueOf(sdf_ymd.format(start_date)) <= Integer.valueOf(sdf_ymd.format(sel.getTime()));
								boolean is_guifan_2 = Integer.valueOf(sdf_ymd.format(sel.getTime())) <= Integer.valueOf(sdf_ymd.format(end_date));
								
								if (!is_guifan_1 || !is_guifan_2) {
									sbError.append("明细表第" + (1 + i) + "行，请假日期不在" + month_sel + "月;");
								}
							}
						} else {
							sbError.append("明细表第" + (1 + i) + "行，请假日期不可以空;");
						}

					}

				}

			}

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return sbError.toString();
	}

}
