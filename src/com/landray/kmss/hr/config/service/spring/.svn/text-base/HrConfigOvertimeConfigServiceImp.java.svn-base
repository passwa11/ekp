package com.landray.kmss.hr.config.service.spring;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.config.model.HrConfigOvertimeConfig;
import com.landray.kmss.hr.config.model.WhiteListConfig;
import com.landray.kmss.hr.config.service.IHrConfigOvertimeConfigService;
import com.landray.kmss.hr.config.util.DateFormat;
import com.landray.kmss.hr.config.util.HrConfigUtil;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.time.model.SysTimeHoliday;
import com.landray.kmss.sys.time.model.SysTimeHolidayDetail;
import com.landray.kmss.sys.time.service.ISysTimeHolidayDetailService;
import com.landray.kmss.sys.time.service.ISysTimeHolidayService;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONObject;

/**
  * 加班规则配置 服务实现
  */
public class HrConfigOvertimeConfigServiceImp extends ExtendDataServiceImp implements IHrConfigOvertimeConfigService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrConfigOvertimeConfig) {
            HrConfigOvertimeConfig hrConfigOvertimeConfig = (HrConfigOvertimeConfig) model;
        }
        return model;
    }

    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrConfigOvertimeConfig hrConfigOvertimeConfig = new HrConfigOvertimeConfig();
        hrConfigOvertimeConfig.setFdIsAvailable(Boolean.valueOf("true"));
        hrConfigOvertimeConfig.setDocCreateTime(new Date());
        hrConfigOvertimeConfig.setFdWorkTime(Integer.valueOf("8"));
        hrConfigOvertimeConfig.setDocCreator(UserUtil.getUser());
        HrConfigUtil.initModelFromRequest(hrConfigOvertimeConfig, requestContext);
        return hrConfigOvertimeConfig;
    }

    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrConfigOvertimeConfig hrConfigOvertimeConfig = (HrConfigOvertimeConfig) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	@Override
	public HrConfigOvertimeConfig getDataByParams(String fdPersonId, String fdRankName, String fdOvertimeType) throws Exception {
		HQLInfo hqlInfoTS=new HQLInfo();//特殊人员规则
		String whereTS=" 1=1 ";
		HQLInfo hqlInfoPT=new HQLInfo();//普通人员规则
		String wherePT=" 1=1 ";
		String joinStr="";
		if(StringUtil.isNotNull(fdPersonId)){
			whereTS+=" and hrConfigOvertimeConfig.fdOrg.fdId=:fdPersonId";
			hqlInfoTS.setParameter("fdPersonId", fdPersonId);
			joinStr +=" left join hrConfigOvertimeConfig.fdOrg fdOrg";
			wherePT+=" and (fdOrg.fdId is null or fdOrg.fdId ='')";
		}
		
		if(StringUtil.isNotNull(fdRankName)){
			joinStr +=" left join hrConfigOvertimeConfig.fdRank fdRank";
			wherePT+=" and fdRank.fdName=:fdRankName";
			hqlInfoPT.setParameter("fdRankName", fdRankName);
		}
		
		if(StringUtil.isNotNull(fdOvertimeType)){
			whereTS+=" and hrConfigOvertimeConfig.fdOvertimeType like :fdOvertimeType";
			wherePT+=" and hrConfigOvertimeConfig.fdOvertimeType like :fdOvertimeType";
			hqlInfoTS.setParameter("fdOvertimeType", "%"+fdOvertimeType+"%");
			hqlInfoPT.setParameter("fdOvertimeType", "%"+fdOvertimeType+"%");
		}
		hqlInfoTS.setWhereBlock(whereTS);
		hqlInfoPT.setWhereBlock(wherePT);
		List<HrConfigOvertimeConfig> listTS=this.findList(hqlInfoTS);
		if(listTS!=null&&listTS.size()>0){
			return listTS.get(0);
		}
		hqlInfoPT.setJoinBlock(joinStr);
		List<HrConfigOvertimeConfig> listPt=this.findList(hqlInfoPT);

		if(listPt!=null&&listPt.size()>0){
			return listPt.get(0);
		}
		
		return null;
	}
	 private ISysAttendCategoryService sysAttendCategoryService;

	    public ISysAttendCategoryService getSysAttendCategoryService() {
	        if (sysAttendCategoryService == null) {
	        	sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
	        }
	        return sysAttendCategoryService;
	    }
	    
	    private ISysOrgElementService sysOrgElementService;

	    public ISysOrgElementService getSysOrgElementService() {
	        if (sysOrgElementService == null) {
	        	sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
	        }
	        return sysOrgElementService;
	    }
	    
	    private ISysTimeHolidayDetailService sysTimeHolidayDetailService;

	    public ISysTimeHolidayDetailService getSysTimeHolidayDetailService() {
	        if (sysTimeHolidayDetailService == null) {
	        	sysTimeHolidayDetailService = (ISysTimeHolidayDetailService) SpringBeanUtil.getBean("sysTimeHolidayDetailService");
	        }
	        return sysTimeHolidayDetailService;
	    }
	    
	    private ISysTimeHolidayService sysTimeHolidayService;

	    public ISysTimeHolidayService getSysTimeHolidayService() {
	        if (sysTimeHolidayService == null) {
	        	sysTimeHolidayService = (ISysTimeHolidayService) SpringBeanUtil.getBean("sysTimeHolidayService");
	        }
	        return sysTimeHolidayService;
	    }
	    
	    
	    
	@Override
	public JSONObject getOvertimeType(String fdPersonId, String fdStartTime) throws Exception  {
		String type="1";//1：工作日，2：周末；3：节假日
		JSONObject rtn = new JSONObject();
		rtn.put("errMsg", "");
		if(StringUtil.isNull(fdPersonId)||StringUtil.isNull(fdStartTime)){
			rtn.put("errMsg", "请检查参数是否完整。");
			return rtn;
		}
//		SysOrgElement element;
//		try {
//			element = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdPersonId);
//		} catch (Exception e) {
//			rtn.put("errMsg", "用户id:"+fdPersonId+",没找到用户!");
//			return rtn;
//		}
		Date date=DateUtil.convertStringToDate(fdStartTime, "yyyy-MM-dd hh:mm");//申请开始时间
//		SysAttendCategory sysAttendCategory=null;//所属考勤组
//		try {
//			 sysAttendCategory=getSysAttendCategoryService().getCategoryInfo(element,date,true);
//			 if(sysAttendCategory==null){
//				 rtn.put("errMsg", "用户:"+element.getFdName()+",在"+fdStartTime+"没找到对应考勤组");
//					return rtn;
//			 }
//		} catch (Exception e) {
//			rtn.put("errMsg", "用户:"+element.getFdName()+",在"+fdStartTime+"没找到对应考勤组");
//			return rtn;
//		}
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock(" fdName =:fdStartDateStr");
		hqlInfo.setParameter("fdStartDateStr", "海格物流法定假期");
//		SysTimeHoliday fdHoliday=sysAttendCategory.getFdHoliday();//获取节假日设置
		List<SysTimeHoliday> fdHolidays=getSysTimeHolidayService().findList(hqlInfo);
		SysTimeHoliday fdHoliday=null;
		if(fdHolidays!=null&&fdHolidays.size()>0){
			fdHoliday=fdHolidays.get(0);
		}
		if(fdHoliday!=null){//说明有节假日设置
			List<SysTimeHolidayDetail> sysTimeHolidayDetails=getSysTimeHolidayDetailService().getDatasByMainId(fdHoliday.getFdId());
			if(sysTimeHolidayDetails!=null&&sysTimeHolidayDetails.size()>0){
				for (SysTimeHolidayDetail sysTimeHolidayDetail : sysTimeHolidayDetails) {
					
					String fdPatchHolidayDay=sysTimeHolidayDetail.getFdPatchHolidayDay();//补假时间
					if(StringUtil.isNotNull(fdPatchHolidayDay)){//2022-01-29,2022-01-30
						String[] fdPatchHolidayDayArr=fdPatchHolidayDay.split(",");
						String fdStartDate=fdStartTime.substring(0, 10);//加班申请日期，去掉时间。
						for (String onePatchDay : fdPatchHolidayDayArr) {
							if(onePatchDay.equals(fdStartDate)){//如果申请加班日期是补假日期，那么直接设置为周末
								rtn.put("type", "2");
								return rtn;
							}
						}
					}
					String fdPatchDay=sysTimeHolidayDetail.getFdPatchDay();//补班时间
					if(StringUtil.isNotNull(fdPatchDay)){//2022-01-29,2022-01-30
						String[] fdPatchDayArr=fdPatchDay.split(",");
						String fdStartDate=fdStartTime.substring(0, 10);//加班申请日期，去掉时间。
						for (String onePatchDay : fdPatchDayArr) {
							if(onePatchDay.equals(fdStartDate)){//如果申请加班日期是补班日期，那么直接设置为工作日
								rtn.put("type", "1");
								return rtn;
							}
						}
					}
					Date fdStartDayXJ=sysTimeHolidayDetail.getFdStartDay();//休假开始日期
					Date fdEndDayXJ=sysTimeHolidayDetail.getFdEndDay();//休假结束日期
					Date dateDay=DateUtil.convertStringToDate(fdStartTime, "yyyy-MM-dd");//只需要精确到日
					if(fdStartDayXJ.getTime()<=dateDay.getTime()&&dateDay.getTime()<=fdEndDayXJ.getTime()){
						//如果申请时间在休假范围内标识是休假
						rtn.put("type", "3");
						return rtn;
					}
				}
			}
		}
		SysOrgElement sysOrgElement = (SysOrgElement) getSysOrgElementService().findByPrimaryKey(fdPersonId);
//		List signList = getSysAttendCategoryService().getAttendSignTimes(sysOrgElement, date);
//		if(signList==null||signList.size()==0){
//			rtn.put("type", "2");
//			return rtn;
//		}
		if(DateFormat.isWeekend(date)){//周末
			rtn.put("type", "2");
			return rtn;
		}
		rtn.put("type", type);
		return rtn;
	}

	@Override
	public JSONObject canSubmit(String fdPersonId, String fdStartTime,String fdEndTime) throws Exception {
		JSONObject rtn = new JSONObject();
		rtn.put("errMsg", "");
		if(StringUtil.isNull(fdPersonId)||StringUtil.isNull(fdStartTime)||StringUtil.isNull(fdEndTime)){
			rtn.put("errMsg", "请检查参数是否完整。");
			return rtn;
		}
		Date startDate=DateUtil.convertStringToDate(fdStartTime, "yyyy-MM-dd HH:mm");//申请开始时间
		Date endDate=DateUtil.convertStringToDate(fdEndTime, "yyyy-MM-dd HH:mm");//申请結束时间
		if(endDate.getTime()-startDate.getTime()<(1000L*60*60)){//如果申请时间少于一小时不能提交
			rtn.put("errMsg", "申请时间不满一个小时不能提交");
			return rtn;
		}
		

		StringBuffer sbError = new StringBuffer();

		//判断申请人是否是白名单人员
		boolean isProposer=checkIsProposer(fdPersonId);
		if(isProposer){//是白名单的人员
			checkWhiteList(startDate, sbError);
		}else{//非白名单的人员
			checkNotWhiteList(startDate, sbError);
		}
		rtn.put("errMsg", sbError.toString());
		return rtn;

	}
	
	
	@Override
	public JSONObject canSubmitBatch(Date startDate) throws Exception {
		JSONObject rtn = new JSONObject();
		rtn.put("errMsg", "");
		if(startDate==null){
			rtn.put("errMsg", "请检查归属月参数。");
			return rtn;
		}
		StringBuffer sbError = new StringBuffer();
		checkWhiteList(startDate, sbError);
		rtn.put("errMsg", sbError.toString());
		return rtn;

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
			return whiteIds.contains(fdProposerId);//如果包含返回true
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 申请时间下个月4日17:31
		long set_close = DateFormat.getLastNDay(fdStartTime, 114);
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long set_close = DateFormat.getWorkDay(fdStartTime, 113);//获取加班开始时间两个工作日的时间

		if (System.currentTimeMillis() > set_close) {// 超了时间不允许提交
			sbError.append("最晚提交时间:" + sdf.format(set_close) + ",已超了限制时间不允许提交");
		}
		return sbError.toString();
	}

	@Override
	public JSONObject getValueByRank(String fdRankValue,String fdTemplateId,String first,String url) throws Exception {
		JSONObject result=new JSONObject();
		result.put("err", "");
		Properties proer = null;
		try {
			try {
				proer = FileUtil.getProperties(url);
			} catch (Exception e) {
				proer = FileUtil.getProperties("/lc-config.properties");
			}
			for (int i = 1; i <= 6; i++) {
				String key=fdTemplateId+"."+i;
				String ranks=proer.getProperty(key);
				if(StringUtil.isNotNull(ranks)){
					ranks = new String(ranks.getBytes("ISO-8859-1"), "utf-8");//设置编码格式中文乱码
				}
				if(StringUtil.isNotNull(ranks)){
					if(ranks.contains(fdRankValue)){
						result.put("type", i);
						return result;
					}
					if(StringUtil.isNotNull(first)&&ranks.contains(first)){
						result.put("type", i);
						return result;
					}
				}
			}
			result.put("err", "未找到配置的类型,请检查配置:【fdTemplateId="+fdTemplateId+"】【fdRankValue="+fdRankValue+"】!");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return result;
	}

	@Override
	public JSONObject checkInvoice(String fdInvoice) throws Exception {
		JSONObject result=new JSONObject();
		result.put("err", "");
		//查询发票
		String sql=" select fd_dianZiFaPiaoHaoMa from ekp_fapiao_number a "
				+ " LEFT JOIN km_review_main b on a.fd_id=b.fd_id "
				+ " LEFT JOIN fssc_expense_main c on a.fd_id=c.fd_id "
				+ " WHERE (b.doc_status in('20','30') or c.doc_status in('20','30')) "
				+ " and fd_dianZiFaPiaoHaoMa like '%"+fdInvoice+"%'";
		List list=this.getBaseDao().getSession().createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			result.put("err", "发票号码："+fdInvoice+"已存在！");
		}
		return result;
	}
	public static void main(String[] args) {
//		String url = this.getClass().getClassLoader().getResource("lc-config.properties").getPath().replace("%20", " ");
		System.out.println(System.getProperty("user.dir"));
		
	}
}
