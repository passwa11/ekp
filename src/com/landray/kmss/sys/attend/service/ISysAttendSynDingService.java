package com.landray.kmss.sys.attend.service;

import com.landray.kmss.sys.attend.model.SysAttendImportLog;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISysAttendSynDingService extends IExtendDataService {
	/**
	 * @param context
	 * @throws Exception
	 *             获取钉钉的原始记录
	 */
	public void synchPersonClock(SysQuartzJobContext context) throws Exception;
	
	/**
	 * @param context
	 * @throws Exception
	 *             获取钉钉的最近3天原始记录
	 */
	public void synchPersonLastClock(SysQuartzJobContext context)
			throws Exception;

	public void synchPersonClock(Date fdStartTime, Date fdEndTime,
			Date fdSyncTime, SysQuartzJobContext context) throws Exception;
	
	/**
	 * @param context
	 * @throws Exception
	 *             获取企业微信的原始记录
	 */
	public void synchPersonWxClock(SysQuartzJobContext context) throws Exception;

	/**
	 * @param context
	 * @throws Exception
	 *             获取企业微信的最近3天原始记录
	 */
	public void synchPersonLastWxClock(SysQuartzJobContext context)
			throws Exception;

	/**
	 * 失败记录重新发送
	 * 
	 * @param fdStartTime
	 * @param fdEndTime
	 * @param fdUserIds
	 */
	public void reSynchPersonClock(Date fdStartTime, Date fdEndTime,
			String fdUserIds) throws Exception;

	/**
	 * 发送不合法考勤记录待阅
	 */
	public void sendInvalidRecordNotify(SysQuartzJobContext context)
			throws Exception;
	/**
	 * 导入原始记录
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public void addImportData(InputStream inputStream,HttpServletRequest request,SysAttendImportLog sysAttendImportLog) throws Exception;
	
	/**
	 * 导出原始记录
	 * @param requestContext
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook buildWorkBook(List list)
			throws Exception;
	
	/**
	 * 重新合并人员的有效记录 缺卡的人员
	 * @param dates
	 * @param elementIds
	 * @throws Exception
	 */
	public void recalMergeClock(List<Date> dates,List<String> elementIds) throws Exception;
	
	public void restat(SysQuartzJobContext context) throws Exception;

	/**
	 * 重新合并人员的有效记录
	 * 参数内人员的所有状态都统计
	 * @param dates
	 * @param elementIds
	 * @throws Exception
	 */
	public void recalMergeClockAllStatus(List<Date> dates, List<String> elementIds) throws Exception;

	/**
	 * 保存有效考勤记录，数据来源为：webservice、考勤excel导入、app数据同步
	 * 方法之所有写在这里是因为代码一直在这里，本次只是统一了入口，没有做代码迁移的必要
	 * @param userStatusData 用户考勤同步记录
	 * @param fdAppName 应用来源
	 * @param userList 用户列表
	 * @param date 考勤记录日期
	 * @param alterRecord 无效记录说明 eg.webservice接口同步置为无效打卡记录
	 * @return: void
	 * @author: wangjf
	 * @time: 2022/6/20 4:26 下午
	 */
	void addAttendMainBatch(Map<String, List<JSONObject>> userStatusData, String fdAppName, List userList, Date date, String alterRecord)throws Exception;
}
