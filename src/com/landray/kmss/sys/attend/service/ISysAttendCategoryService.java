package com.landray.kmss.sys.attend.service;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.service.spring.SysAttendExchangeResult;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import net.sf.json.JSONArray;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 签到事项业务对象接口
 * 
 * @author 
 * @version 1.0 2017-05-24
 */
public interface ISysAttendCategoryService extends IBaseService {
	/**
	 * 获取用户考勤组
	 * 不过滤是否在排班、考勤时间范围
	 * @param element 人员
	 * @param date 日期
	 * @param isExc 是否过滤排除人员
	 * @return
	 * @throws Exception
	 */
	SysAttendCategory getCategoryInfo(SysOrgElement element,Date date,Boolean  isExc) throws Exception;
	/**
	 * 最晚正常上班打卡时间
	 * @param map 班次信息
	 * @param signTime 打卡时间(合并日期以后 直接增加分钟数，没合并日期获取结果后再合并。同理)
	 * @return 返回弹性以后的上班时间
	 */
	public Date getShouldOffWorkTime(Map map,Date signTime);
	/**
	 * 最晚正常上班打卡时间
	 * @param map 班次信息
	 * @param signTime 打卡时间(合并日期以后 直接增加分钟数，没合并日期获取结果后再合并。同理)
	 * @return 返回弹性以后的上班时间
	 */
	public Date getShouldOnWorkTime(Map map,Date signTime);
	/**
	 * 生成缺卡定时任务，非排班的缺卡记录产生
	 * @param category
	 * @throws Exception
	 */
	public void saveMissSignQuart(SysAttendCategory category,Date deleteQuartzDate) throws Exception;
	/**
	 * 获取打卡时间所在 排班班次范围所属日
	 *  @param workDate 打卡时间
	 * @param date 排班日期
	 * @param category 考勤组
	 * @param ele 人员
	 * @return 如果在打卡的范围内，则返回打卡时间
	 * @throws Exception
	 */
	Date getTimeAreaDateOfDate(Date workDate,Date date, SysAttendCategory category,SysOrgElement ele) throws Exception;

	/**
	 * 获取打卡时间所在 排班班次范围所属日
	 * @param workDate 打卡时间
	 * @param date 排班日期
	 * @param signTimeList 排班
	 * @return 如果在打卡的范围内，则返回打卡时间
	 * @throws Exception
	 */
	Date getTimeAreaDateOfDate(Date workDate,Date date, List<Map<String, Object>> signTimeList) throws Exception;

	/**
	 * 获取打卡时间所在 排班班次范围所属日
	 * @param workDate 打卡时间
	 * @param date 排班日期
	 * @param workConfig 具体的班次信息
	 * @return 如果在打卡的范围内，则返回打卡时间所在的日期。可以判断null的话，就是不在排班考勤范围内，
	 * @throws Exception
	 */
	Date getTimeAreaDateOfDate(Date workDate,Date date, Map<String, Object> workConfig) throws Exception ;

	/**
	 * 获取当前打卡时间点对应的上下班班次详情
	 * @param category 历史考勤组
	 * @param fdWorkTimeId 班次ID
	 * @param fdWorkType 班次类型0，1
	 * @param _signTime 打卡时间
	 * @return
	 * @throws Exception
	 */
	SysAttendCategoryWorktime getCurrentWorkTime(SysAttendCategory category,String fdWorkTimeId,String fdWorkType,String _signTime) throws Exception ;
		/**
         * 根据人员查找其排班的班次信息
         * @param element
         * @param date
         * @return 返回班次信息对应该天是按1天还是半天的时间统计
         * @throws Exception
         */
	Float getWorkTimeAreaTotalDay(SysOrgElement element, Date date) throws Exception;
	/**
	 * 清空人员排班缓存。
	 * 用于其他模块调用
	 * @throws Exception
	 */
	void clearSignTimesCache();
	/**
	 * 供排班管理获取用户考勤组 对应的排班情况。
	 * 不过滤排除人员
	 * @param ele
	 * @param date
	 * @param need 自然日 是否需要获取工时
	 * @return
	 * @throws Exception
	 */
	List getAttendSignTimesOfTime(SysOrgElement ele, Date date, boolean need)
			throws Exception;
	/**
	 * 获取用户考勤组
	 * 不过滤是否在排班、考勤时间范围
	 * @param element 人员
	 * @param date 日期
	 * @param isExc 是否过滤排除人员
	 * @return
	 * @throws Exception
	 */
	String getCategory(SysOrgElement element,Date date,Boolean  isExc) throws Exception;
	/**
	 * 获取用户考勤组
	 * 过滤排除人员
	 * @param element 人员
	 * @param date 日期
	 * @return
	 * @throws Exception
	 */
	String getCategory(SysOrgElement element,Date date) throws Exception;
	/**
	 * 保存考勤组信息到历史表
	 * @param category
	 * @throws Exception
	 */
	SysAttendHisCategory addHisCategory(SysAttendCategory category) throws Exception;
	/**
	 * 结束考勤组或签到组
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void updateCategoryOver(String id,String fdStatusFlag) throws Exception;

	/**
	 * 更新考勤组/签到组状态
	 * @param categoryId
	 * @param fdStatus
	 * @throws Exception
	 */
	public void updateStatus(String categoryId, int fdStatus) throws Exception;


	/**
	 * 过滤用户某天的签到事项
	 * 
	 * @param list
	 *            考勤组或签到组集合
	 * @param date
	 *            考勤日期
	 * @param filterTarget
	 *            是否过滤例外人员(针对签到组生效)
	 * @param orgEle
	 *            当前考勤用户(排班制生效)
	 * @return
	 * @throws Exception
	 */
	public com.alibaba.fastjson.JSONArray filterAttendCategory(List<SysAttendCategory> list,
			Date date, boolean filterTarget, SysOrgElement orgEle)
			throws Exception;

	/**
	 * 获取冲突组织架构
	 */
	public JSONArray findConflictElement(String elementIds, String exceptCategoryId) throws Exception;

	/**
	 * 获取当天当前用户的考勤组和签到组
	 * 
	 * @return
	 * @throws Exception
	 */
	public com.alibaba.fastjson.JSONArray getAttendCategorys(HttpServletRequest request)
			throws Exception;

	/**
	 * 获取当前用户所属考勤组
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getAttendCategory(Date date) throws Exception;

	/**
	 * 获取当前某个用户所属考勤组
	 * 
	 * @param orgEle
	 * @return
	 * @throws Exception
	 */
	public String getAttendCategory(SysOrgElement orgEle) throws Exception;

	/**
	 * 获取当前某天某用户的考勤组,返回为空表示不用打卡
	 * 
	 * @param orgEle
	 *            用户
	 * @param date
	 *            考勤日期
	 * @return
	 */
	public String getAttendCategory(SysOrgElement orgEle, Date date)
			throws Exception;
	
	/**
	 * 获取指定用户某天的打卡时间点(可用于排班制),为空则不需要打卡
	 *
	 * @param date
	 * @param ele
	 * @return
	 * @throws Exception
	 */
	public List getAttendSignTimes(SysOrgElement ele, Date date)
			throws Exception;

	/**
	 * 根据考勤组,日期判断是否需要考勤打卡(只适用于固定班制)
	 * 
	 * @param category
	 *            考勤组
	 * @param date
	 *            日期
	 * @return
	 */
	public Boolean isAttendNeeded(SysAttendCategory category, Date date)
			throws Exception;
	/**
	 * 是否需要打卡
	 * @param cate 考勤组
	 * @param date 日期
	 * @param fdStartTime 开始时间
	 * @param fdEndTime 结束时间
	 * @param orgEle 人员
	 * @return
	 * @throws Exception
	 */
	public boolean isNeedSign(SysAttendCategory cate,Date date,long fdStartTime,long fdEndTime,SysOrgElement orgEle) throws Exception;
	/**
	 * 发送上下班提醒
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void sendWorkRemind(SysQuartzJobContext context) throws Exception;

	/**
	 * 发送签到提醒
	 * 
	 * @param context
	 * @throws Exception
	 */
	public void sendCustomRemind(SysQuartzJobContext context) throws Exception;

	/**
	 * 获取签到扩展
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getSignExtend() throws Exception;

	/**
	 * 根据考勤组判断某天是否法定节假日(不支持排班制)
	 * 
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public boolean isHoliday(String categoryId, Date date) throws Exception;

	/**
	 * 根据考勤组判断某天是否法定节假日补班(不支持排班制)
	 * 
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public boolean isHolidayPatchDay(String categoryId, Date date)
			throws Exception;

	/**
	 * 获取某个用户负责的签到组列表
	 * 
	 * @param orgEle
	 * @return
	 * @throws Exception
	 */
	public List findSignCategorysByLeader(SysOrgElement orgEle)
			throws Exception;


	/**
	 * 判断是否节假日(支持排班制)
	 * 
	 * @param categoryId
	 *            排班时,该值允许为空
	 * @param date
	 * @param orgEle
	 * @param isTimeArea
	 *            是否排班(true表示排班)
	 * @return
	 * @throws Exception
	 */
	public boolean isHoliday(String categoryId, Date date, SysOrgElement orgEle,
			boolean isTimeArea) throws Exception;

	/**
	 * 获取某个用户负责的考勤组列表或签到组列表
	 * 
	 * @param orgEle
	 * @param fdType
	 *            1:考勤组,2:签到组
	 * @return
	 * @throws Exception
	 */
	public List findCategorysByLeader(SysOrgElement orgEle, int fdType)
			throws Exception;

	/**
	 * 根据fdAppId获取所有的签到组
	 * 
	 * @param fdAppId
	 * @return
	 * @throws Exception
	 */
	public List findCategorysByAppId(String fdAppId) throws Exception;

	/**
	 * 通过可能是可阅读者/可编辑者的人员id，获取他所在的考勤组/签到组
	 * 
	 * @param authId
	 * @return
	 * @throws Exception
	 */
	public List findCateIdsByAuthId(List<String> authId, Integer fdType)
			throws Exception;

	/**
	 * 通过可能是可编辑者的人员id，获取他所在的考勤组/签到组
	 * 
	 * @param authId
	 * @param fdType
	 * @return
	 * @throws Exception
	 */
	public List findCateIdsByEditorId(String authId, Integer fdType)
			throws Exception;

	/**
	 * 通过可能是可阅读者的人员id，获取他所在的考勤组/签到组
	 * 
	 * @param readerId
	 * @param fdType
	 * @return
	 * @throws Exception
	 */
	public List findCateIdsByReaderId(String readerId, Integer fdType)
			throws Exception;

	/**
	 * 获取考勤组Map，fdId-->model
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, SysAttendCategory> getCategoryMap() throws Exception;

	/**
	 * 获取某天考勤组打卡时间点(不适用于排班)
	 * 
	 * @param category
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List getAttendSignTimes(SysAttendCategory category, Date date)
			throws Exception;

	/**
	 * 获取指定用户某天的打卡时间点(可用于排班制)
	 * 
	 * @param category
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List getAttendSignTimes(SysAttendCategory category, Date date,
			SysOrgElement ele) throws Exception;

	/**
	 * 获取指定用户某天的打卡时间点,若当天没有排班则获取最近的排班信息
	 * 
	 * @param category
	 * @param date
	 * @param ele
	 * @param needed
	 *            排班制时生效(true时,若当天没有排班则获取最近的排班信息)
	 * @return
	 * @throws Exception
	 */
	public List getAttendSignTimes(SysAttendCategory category,
			Date date, SysOrgElement ele, boolean needed) throws Exception;

	/**
	 * 自然日和工作日获取排班信息
	 * 
	 * @param ele
	 * @param date
	 * @param need
	 * @return
	 * @throws Exception
	 */
	public List getAttendSignTimes(SysOrgElement ele, Date date, boolean need)
			throws Exception;

	/**
	 * 最早正常下班打卡时间
	 * 
	 * @param pSignTime
	 *            同个班次上班用户打卡时间
	 * @param map
	 *            班次相关信息
	 * @return 时间点
	 * @throws Exception
	 */
	public int getShouldOffWorkTime(Date pSignTime, Map map);

	/**
	 * 最晚正常上班打卡时间
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int getShouldOnWorkTime(Map map);

	/**
	 * 重新计算考勤组当日打卡记录（包括已从考勤组剔除的用户）
	 * @param categoryId
	 * @param orgIds
	 * @param alterPerson
	 * @throws Exception
	 */
	public void updateAttendMainRecord(String categoryId, List<String> orgIds,SysOrgPerson alterPerson)
			throws Exception;

	/**
	 * 获取某个考勤组的节假日和补班日，用于PC考勤日历
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public JSONArray getHolidayPatchDay(String categoryId) throws Exception;
	/**
	 * 获取所有考勤组的所有有效考勤用户id列表
	 * 指定日期
	 * @param date
	 * @return
	 * @throws Exception
	 */
	List<String>  getAttendPersonIds(Date date) throws Exception;

	/**
	 * 获取考勤组的用户ID列表
	 * @param categoryId 考勤组
	 * @param date 日期
	 * @return
	 * @throws Exception
	 */
	List<String>  getAttendPersonIds(String categoryId,Date date) throws Exception;

	/**
	 * 获取考勤组所有有效考勤用户对象列表
	 * @param categoryId
	 * @param date 指定日期
	 * @return
	 * @throws Exception
	 */
	List<SysOrgElement>  getAttendPersons(String categoryId,Date date) throws Exception;

	/**
	 * 根据原始考勤ID 获取对应日期的所有考勤人员
	 * @param categoryIds 原始考勤组
	 * @param beginDate 开始日期
	 * @return
	 * @throws Exception
	 */
	List<String> getAttendPersonIds(List<String> categoryIds,Date beginDate,boolean isOld) throws Exception;
	/**
	 * 根据打卡记录,只获取用户当前对应班次的上班/下班打卡时间
	 * 
	 * @param signList
	 *            用户某天的班次信息
	 * @param signedTime
	 *            打卡时间
	 * @param fdWorkType
	 *            上下班类型
	 * @return
	 * @throws Exception
	 */
	public SysAttendCategoryWorktime getWorkTimeByRecord(List signList,
			Date signedTime, Integer fdWorkType) throws Exception;

	/**
	 * 获取考勤组某个班次(排班制时返回null)
	 * 
	 * @param category
	 * @param fdWorkId
	 * @return
	 * @throws Exception
	 */
	public SysAttendCategoryWorktime getWorkTime(SysAttendCategory category,
			String fdWorkId) throws Exception;

	/**
	 * 获取考勤组或指定用户某天的班制列表(排班时,只有班次时间有效,班次id无意义)
	 * 
	 * @param category
	 * @param date
	 * @param element(排班制时,该值必填)
	 * @return
	 * @throws Exception
	 */
	public List<SysAttendCategoryWorktime> getWorktimes(
			SysAttendCategory category, Date date, SysOrgElement element)
			throws Exception;
	
	/**
	 * 获取排班制的班次信息
	 * 
	 * @param element
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<SysAttendCategoryWorktime> getWorkTimeOfTimeArea(
			SysOrgElement element, Date date) throws Exception;

	/**
	 * 排班制获取用户班次信息渲染 作用是为了保证同个班次fdWorkId/fdWorkKey相同
	 * 
	 * @param workTimesList
	 *            用户某天的打卡班次信息
	 * @param recordList
	 *            用户打卡记录
	 * @throws Exception
	 */
	public void doWorkTimesRender(List<Map<String, Object>> workTimesList,
			List<?> recordList) throws Exception;

	/**
	 * 判断是否同个班次
	 * 
	 * @param workTimeMap
	 *            班次信息
	 * @param fdWorkId
	 *            打卡记录中的班次id
	 * @param fdWorkType
	 * @param fdWorkKey
	 * @return
	 * @throws Exception
	 */
	public boolean isSameWorkTime(Map<String, Object> workTimeMap,
			String fdWorkId, Integer fdWorkType, String fdWorkKey)
			throws Exception;

	/**
	 * 是否可以查看这个签到组的统计数据
	 * 
	 * @param category
	 * @return
	 */
	public Boolean isStatSignReader(SysAttendCategory category);

	/**
	 * 判断当前用户所属考勤组是否只允许钉钉打卡方式
	 * 
	 * @return
	 */
	public boolean isOnlyDingAttend();

	/**
	 * 获取所有有效的排班制考勤组列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List findCategorysByTimeArea() throws Exception;

	/**
	 * 判断某个时间点是否跨天打卡(时间点大于考勤日期且小于最晚打卡时间)
	 * 
	 * @param date
	 *            某个时间点
	 * @param person
	 * @param category
	 * @return
	 * @throws Exception
	 */
	public boolean isAcrossDay(Date date, SysOrgElement person,
			SysAttendCategory category) throws Exception;

	/**
	 * 指定人员在有效的排班制考勤组中并返回人员列表
	 * @param areaMembers 用户ID
	 * @param cateIds 考勤组列表
	 * @param date 日期
	 * @return
	 * @throws Exception
	 */
	public List getTimeAreaAttendPersonIds(List<SysOrgElement> areaMembers,
			List<String> cateIds,Date date)
			throws Exception;
	
	/**
	 * 校验是否可以换班
	 * @param applyPerson 申请人
	 * @param exchangePerson 替换人
	 * @param exchangeDate 申请换班日期
	 * @param returnDate 还班日期
	 * @return
	 * @throws Exception
	 */
	public SysAttendExchangeResult validatorCanExchangeWorkTime(SysOrgElement applyPerson,SysOrgElement exchangePerson,Date exchangeDate,Date returnDate) throws Exception;
	
	/**
	 * 初始化线程变量
	 */
	public void initThreadLocalConfig();
	
	/**
	 * 释放线程变量
	 */
	public void releaseThreadLocalConfig();

	/**
	 * 判断是否是补假
	 * @param categoryId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public boolean isPatchHolidayDay(String categoryId, Date date) throws Exception;
}
