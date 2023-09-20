package com.landray.kmss.hr.config.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.hr.config.model.WhiteListConfig;
import com.landray.kmss.hr.config.util.DateFormat;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataEvent;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.util.StringUtil;

/**
 * 集体加班
 * 
 * @author 孙艳妮
 * @create 2022-10-06 14:41
 */
public class KmReviewMainEventByHrAttendJiaBan implements IExtendDataEvent {
	private IKmReviewMainService kmReviewMainService;
	private static final Log log = LogFactory.getLog(KmReviewMainEventByHrAttendJiaBan.class);

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

		KmReviewMain kmReviewMain = (KmReviewMain)iExtendDataModel;

		Map<String, Object> map = kmReviewMain.getExtendDataModelInfo().getModelData();
		StringBuffer sbError = new StringBuffer();
		// 加班开始时间
		Date fdStartTime=(Date)map.get("fd_start_time");

		//判断申请人是否是白名单人员
		String fdProposerId=(String) map.get("fd_proposer");//获取申请人
		Boolean isProposer=checkIsProposer(fdProposerId);
		if(isProposer){//是白名单的人员
			sbError.append(checkWhiteList(fdStartTime, sbError));
		}else{//非白名单的人员
			sbError.append(checkNotWhiteList(fdStartTime, sbError));
		}

		if (sbError.toString().length() > 0) {
			log.error(sbError.toString());
			throw new Exception(sbError.toString());
		}

	}
	
	/**
	 * 判断申请人是否在白名单
	 * @return
	 * @throws Exception 
	 */
	private boolean checkIsProposer(String fdProposerId) throws Exception{
		//获取白名单列表
		String whiteIds=new WhiteListConfig().getFdOvertimeWhiteId();
		if(StringUtil.isNotNull(whiteIds)){
			return whiteIds.contains(whiteIds);//如果包含返回true
		}
		return false;
	}
	/**
	 *   白名单人员校验
	 * 要在申请月份后一个月4号之前才能提交
	 * @param fdStartTime 加班申请开始时间
	 * @param ben_yue_m 本月月份
	 * @param shang_yue_m 上月月份
	 * @param sbError
	 * @return
	 */
	private String checkWhiteList(Date fdStartTime,StringBuffer sbError){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 申请时间下个月4日17:31
		long set_close = DateFormat.getLastNDay(fdStartTime, 5);
		if (System.currentTimeMillis() > set_close) {// 超了时间不允许提交
			sbError.append("最晚提交时间:" + sdf.format(set_close) + ",已超了限制时间不允许提交");
		}
		return sbError.toString();
	}
	
	/**
	 * 非白名单人员校验
	 *  且只能提交两个工作日内的数据
	 * @param fdStartTime 加班申请开始时间
	 * @param ben_yue_m 本月月份
	 * @param shang_yue_m 上月月份
	 * @param sbError 错误提示
	 * @return
	 */
	private String checkNotWhiteList(Date fdStartTime,StringBuffer sbError){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long set_close = DateFormat.getWorkDay(fdStartTime, 3);//获取加班开始时间两个工作日的时间

		if (System.currentTimeMillis() > set_close) {// 超了时间不允许提交
			sbError.append("最晚提交时间:" + sdf.format(set_close) + ",已超了限制时间不允许提交");
		}
		return sbError.toString();
	}
	
	

	@Override
	public void onUpdate(IExtendDataModel iExtendDataModel, ISysMetadataParser iSysMetadataParser) throws Exception {

		KmReviewMain kmReviewMain = (KmReviewMain)iExtendDataModel;

		Map<String, Object> map = kmReviewMain.getExtendDataModelInfo().getModelData();
		StringBuffer sbError = new StringBuffer();
		// 加班开始时间
		Date fdStartTime=(Date)map.get("fd_start_time");

		//判断申请人是否是白名单人员
		String fdProposerId=(String) map.get("fd_proposer");//获取申请人
		Boolean isProposer=checkIsProposer(fdProposerId);
		if(isProposer){//是白名单的人员
			sbError.append(checkWhiteList(fdStartTime, sbError));
		}else{//非白名单的人员
			sbError.append(checkNotWhiteList(fdStartTime, sbError));
		}

		if (sbError.toString().length() > 0) {
			log.error(sbError.toString());
			throw new Exception(sbError.toString());
		}

	}

	@Override
	public void onDelete(IExtendDataModel iExtendDataModel, ISysMetadataParser iSysMetadataParser) throws Exception {

	}
	
	
 	public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date currDay = sdf.parse("2022-12-28");
//        System.out.println(getWorkDay(new Date(), 2));
//        DateFormat.getLastNDay(currDay, 4);
        System.out.println( sdf2.format(DateFormat.getLastNDay(currDay, 5)));
    }
 	

 

}
