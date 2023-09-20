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
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataEvent;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.util.StringUtil;

/**
 * 集体假期
 * 
 * @author 孙艳妮
 * @create 2022-10-06 14:41
 */
public class KmReviewMainEventByHrAttendJiTiJiaQi implements IExtendDataEvent {
	private IKmReviewMainService kmReviewMainService;
	private static final Log log = LogFactory.getLog(KmReviewMainEventByHrAttendJiTiJiaQi.class);

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
		validateSubmit(iExtendDataModel);
	}

	@Override
	public void onUpdate(IExtendDataModel iExtendDataModel, ISysMetadataParser iSysMetadataParser) throws Exception {
		validateSubmit(iExtendDataModel);
	}

	@Override
	public void onDelete(IExtendDataModel iExtendDataModel, ISysMetadataParser iSysMetadataParser) throws Exception {
	}

	
	public void validateSubmit(IExtendDataModel iExtendDataModel) {

		// 仅仅限于： 当月4日17:30之前可提交 上月1日至上月最后一日单据
		// 第一步：判断集体归属月，是上月 或当月

		// 如果上月，则判断判断当前时间是否超过了当月4日17:31，
		//          若超过，则不允许提交；
		//          若无超过，则判断明细表加班日期是否都在上月1日-上月最后一日
		// 如果当月，则判断明细表是否加班日期都是当月的
		DateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);		
		Calendar ca = Calendar.getInstance();// 得到一个Calendar的实例
		
		ca.setTime(new Date()); // 设置时间为当前时间
		int ben_yue_m = ca.get(Calendar.MONTH)+1;

		StringBuffer sbError = new StringBuffer();
		
		// 上月
		ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
		int shang_yue_m = ca.get(Calendar.MONTH);

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = iExtendDataModel.getExtendDataModelInfo().getModelData();
			// 集体归属月
			int month_sel = Float.valueOf(map.get("fd_3b34b14dc7f032")+"").intValue();
					
			Date start_date = null;
			Date end_date = null;

			if (month_sel == shang_yue_m) {
				ca.setTime(new Date()); // 设置时间为当前时间

				ca.set(Calendar.MONTH, ca.get(Calendar.MONTH) - 1);
				ca.set(Calendar.DAY_OF_MONTH, 1);// 1日
				start_date = ca.getTime();

				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));
				end_date = ca.getTime();

				// 当月4日17:31
				ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), 4, 17, 31, 0);
				long set_close = ca.getTimeInMillis();

				if (System.currentTimeMillis() > set_close) {// 超了时间不允许提交
					sbError.append("当前时间" + sdf.format(new Date()) + "超了限制时间不允许提交");
				}

			} else if (month_sel == ben_yue_m) {
				ca.setTime(new Date()); // 设置时间为当前时间
				ca.set(Calendar.DAY_OF_MONTH, 1);// 当月1日
				start_date = ca.getTime();

				ca.set(Calendar.DATE, ca.getActualMaximum(Calendar.DATE));// 当月最后1日
				end_date = ca.getTime();
			} else {
				sbError.append("集体归属月数据不合规则，请重新填写");
			}

			if (sbError.toString().length() == 0) {
				List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();
				// fd_3b142127f54434 明细表ID
				childList = (List<Map<String, Object>>) map.get("fd_3b142127f54434");

				if (childList.size() > 0) {
					for (int i = 0; i < childList.size(); i++) {
						
						Map<String, Object> detailMap = childList.get(i);
						// 加班日期 fd_3b345093ff2fd4
						Date sel = null;
						if(!StringUtil.isNull(detailMap.get("fd_3b345093ff2fd4")+"")){
							sel = sdf.parse(detailMap.get("fd_3b345093ff2fd4") + "");
							
							if(!HrCurrencyParams.isEffectiveDate(sel, start_date, end_date)){//不在开始与结束日期范围内
								sbError.append("明细表" + (1+i) + "行，加班日期不在" + sdf.format(start_date) + "与" + sdf.format(end_date) + "范围内");
							}
						} else {
							sbError.append("明细表" + (1+i) + "加班日期不可以空");
						}
					}

				}
			}

			if (sbError.toString().length() > 0) {
				log.error(sbError.toString());
				throw new Exception(sbError.toString());
			}
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}
}
