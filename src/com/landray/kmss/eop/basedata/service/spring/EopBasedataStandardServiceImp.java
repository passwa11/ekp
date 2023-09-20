package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.forms.EopBasedataStandardForm;
import com.landray.kmss.eop.basedata.model.EopBasedataArea;
import com.landray.kmss.eop.basedata.model.EopBasedataBerth;
import com.landray.kmss.eop.basedata.model.EopBasedataCity;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCurrency;
import com.landray.kmss.eop.basedata.model.EopBasedataExchangeRate;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataLevel;
import com.landray.kmss.eop.basedata.model.EopBasedataStandard;
import com.landray.kmss.eop.basedata.model.EopBasedataStandardScheme;
import com.landray.kmss.eop.basedata.model.EopBasedataVehicle;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardSchemeService;
import com.landray.kmss.eop.basedata.service.IEopBasedataStandardService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataNumberUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.hibernate.query.Query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EopBasedataStandardServiceImp extends EopBasedataBusinessServiceImp implements IEopBasedataStandardService {

	private IEopBasedataStandardSchemeService eopBasedataStandardSchemeService;

	public IEopBasedataStandardSchemeService getEopBasedataStandardSchemeService() {
		if (eopBasedataStandardSchemeService == null) {
			eopBasedataStandardSchemeService = (IEopBasedataStandardSchemeService) SpringBeanUtil.getBean("eopBasedataStandardSchemeService");
		}
		return eopBasedataStandardSchemeService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataStandard) {
            EopBasedataStandard eopBasedataStandard = (EopBasedataStandard) model;
            eopBasedataStandard.setDocAlterTime(new Date());
            eopBasedataStandard.setDocAlteror(UserUtil.getUser());
        }
        return model; 
    }
    @Override
	public IExtendForm initFormSetting(IExtendForm form, RequestContext requestContext) throws Exception {
    	EopBasedataStandardForm mainForm = (EopBasedataStandardForm) super.initFormSetting(form, requestContext);
		return mainForm;
	}
    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataStandard eopBasedataStandard = new EopBasedataStandard();
        eopBasedataStandard.setFdIsAvailable(Boolean.valueOf("true"));
        eopBasedataStandard.setDocCreateTime(new Date());
        eopBasedataStandard.setDocAlterTime(new Date());
        eopBasedataStandard.setDocCreator(UserUtil.getUser());
        eopBasedataStandard.setDocAlteror(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataStandard, requestContext);
        return eopBasedataStandard;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataStandard eopBasedataStandard = (EopBasedataStandard) model;
    }

    @Override
    public List<EopBasedataStandard> findByFdLevel(EopBasedataLevel fdLevel) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandard.fdLevel.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdLevel.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataStandard> findByFdArea(EopBasedataArea fdArea) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandard.fdArea.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdArea.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataStandard> findByFdVehicle(EopBasedataVehicle fdVehicle) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandard.fdVehicle.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdVehicle.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataStandard> findByFdBerth(EopBasedataBerth fdBerth) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandard.fdBerth.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdBerth.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataStandard> findByFdItem(EopBasedataExpenseItem fdItem) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandard.fdItem.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdItem.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataStandard> findByFdCurrency(EopBasedataCurrency fdCurrency) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandard.fdCurrency.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCurrency.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataStandard> findByFdCompany(EopBasedataCompany fdCompany) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataStandard.fdCompanyList.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdCompany.getFdId());
        return this.findList(hqlInfo);
    }
	@Override
	public JSONObject getStandardData(JSONObject params) throws Exception {
		JSONObject rtn = new JSONObject();
		HQLInfo hInfo = new HQLInfo();
		String fdCompanyId = params.optString("fdCompanyId","");
		if(StringUtil.isNotNull(params.optString("fdCompanyId",""))){
			hInfo.setJoinBlock(" left join eopBasedataStandardScheme.fdCompanyList comp ");
			hInfo.setWhereBlock(StringUtil.linkString(hInfo.getWhereBlock(), " and ",
					" (comp.fdId=:fdCompanyId or comp is null) "));
			hInfo.setParameter("fdCompanyId", fdCompanyId);
		}
		String fdExpenseItemId = params.optString("fdExpenseItemId", "");
		if(StringUtil.isNotNull(fdExpenseItemId)){
			hInfo.setJoinBlock(hInfo.getJoinBlock() + " left join eopBasedataStandardScheme.fdItems item ");
			hInfo.setWhereBlock(StringUtil.linkString(hInfo.getWhereBlock(), " and ",
					" item.fdId=:fdExpenseItemId "));
			hInfo.setParameter("fdExpenseItemId", fdExpenseItemId);
		}
		hInfo.setWhereBlock(StringUtil.linkString(hInfo.getWhereBlock(), " and ",
				" eopBasedataStandardScheme.fdIsAvailable=:fdIsAvailable "));
		hInfo.setParameter("fdIsAvailable", true);
		List<EopBasedataStandardScheme> list = getEopBasedataStandardSchemeService().findList(hInfo);
		rtn.put("status", "0");//默认未配置标准
		if(!ArrayUtil.isEmpty(list)){
			EopBasedataStandardScheme scheme = list.get(0);
			String fdDim = scheme.getFdDimension();
			HQLInfo hqlInfo = new HQLInfo();
			StringBuilder where = new StringBuilder();
			where.append("eopBasedataStandard.fdIsAvailable=:fdIsAvailable and eopBasedataStandard.fdItem.fdId=:fdExpenseItemId");
			if(StringUtil.isNotNull(params.optString("fdCompanyId",""))) {
				hqlInfo.setJoinBlock(" left join eopBasedataStandard.fdCompanyList comp ");
				where.append(" and (comp.fdId=:fdCompanyId or comp.fdId is null)");
				hqlInfo.setParameter("fdCompanyId",params.optString("fdCompanyId",""));
			}
			hqlInfo.setParameter("fdIsAvailable",true);
			hqlInfo.setParameter("fdExpenseItemId",fdExpenseItemId);
			if(StringUtil.isNotNull(fdDim)){
				if(fdDim.contains("1")){//职级
					List<String> ids = null;
					String fdPersonId = params.containsKey("fdPersonId")?params.getString("fdPersonId"):"";
					//通过人员的职务配置查找对应的职级
					if(StringUtil.isNotNull(fdPersonId)){
						String hql = "select level.fdId from com.landray.kmss.eop.basedata.model.EopBasedataLevel level where level.fdPersonList.fdId=:fdPersonId and level.fdIsAvailable=:fdIsAvailable";
						Query query = getBaseDao().getHibernateSession().createQuery(hql);
						query.setParameter("fdIsAvailable", true);
						if(StringUtil.isNotNull(params.optString("fdCompanyId",""))) {
							hqlInfo.setJoinBlock(" left join eopBasedataStandard.fdCompanyList comp ");
							where.append(" and (comp.fdId=:fdCompanyId or comp.fdId is null)");
							hqlInfo.setParameter("fdCompanyId",params.optString("fdCompanyId",""));
						}
						query.setParameter("fdPersonId",fdPersonId);
						ids = query.list();
					}
					if(ArrayUtil.isEmpty(ids)){
						where.append(" and 1=2");
					}else{
						where.append(" and eopBasedataStandard.fdLevel.fdId in(:fdLevelId)");
						hqlInfo.setParameter("fdLevelId",ids);
					}
				}else{
					where.append(" and eopBasedataStandard.fdLevel is null");
				}
				if(fdDim.contains("2")){//地域
					String fdAreaId = params.optString("fdAreaId","");
					if(StringUtil.isNotNull(fdAreaId)) {
						EopBasedataCity c = (EopBasedataCity) findByPrimaryKey(fdAreaId, EopBasedataCity.class, true);
						if(c!=null) {
							where.append(" and eopBasedataStandard.fdArea.fdId=:fdAreaId");
							hqlInfo.setParameter("fdAreaId",c.getFdArea()!=null?c.getFdArea().getFdId():"");
						}else {
							where.append(" and 1=2");
						}
					}else {
						where.append(" and 1=2");
					}
				}else{
					where.append(" and eopBasedataStandard.fdArea is null");
				}
				if(fdDim.contains("6")){//城市
					String fdAreaId = params.optString("fdAreaId","");
					if(StringUtil.isNotNull(fdAreaId)) {
						where.append(" and eopBasedataStandard.fdCity.fdId=:fdCityId");
						hqlInfo.setParameter("fdCityId",fdAreaId);
					}else {
						where.append(" and 1=2");
					}
				}else{
					where.append(" and eopBasedataStandard.fdCity is null");
				}
				if(fdDim.contains("3")){//交通工具
					where.append(" and eopBasedataStandard.fdVehicle.fdId=:fdVehicleId");
					String fdBerthId = params.containsKey("fdBerthId")?params.getString("fdBerthId"):"";
					String fdVehicleId = "";
					EopBasedataBerth berth = (EopBasedataBerth) findByPrimaryKey(fdBerthId, EopBasedataBerth.class, true);
					if(berth!=null){
						fdVehicleId = berth.getFdVehicle().getFdId();
					}
					hqlInfo.setParameter("fdVehicleId",fdVehicleId);
				}else{
					where.append(" and eopBasedataStandard.fdVehicle is null");
				}
				if(fdDim.contains("4")){//人员
					where.append(" and eopBasedataStandard.fdPerson.fdId=:fdPersonId");
					hqlInfo.setParameter("fdPersonId",params.containsKey("fdPersonId")?params.getString("fdPersonId"):"");
				}else{
					where.append(" and eopBasedataStandard.fdPerson is null");
				}
				if(fdDim.contains("5")){//人员
					where.append(" and eopBasedataStandard.fdDept.fdId=:fdDeptId");
					hqlInfo.setParameter("fdDeptId",params.containsKey("fdDeptId")?params.getString("fdDeptId"):"");
				}else{
					where.append(" and eopBasedataStandard.fdDept is null");
				}
			}
			hqlInfo.setWhereBlock(where.toString());
			List<EopBasedataStandard> data = this.findList(hqlInfo);
			if(!ArrayUtil.isEmpty(data)){
				rtn.put("info",getStandardSubject(data,scheme));
				String status = null;
				if(EopBasedataConstant.FSSC_BASE_STANDARD_SCHEME_TARGET_TRAVEL.equals(scheme.getFdTarget())){//交通工具，检查舱位是否超标
					if(params.containsKey("fdBerthId")){
						String fdBerthId = params.getString("fdBerthId");
						EopBasedataBerth be = (EopBasedataBerth) findByPrimaryKey(fdBerthId, EopBasedataBerth.class, true);
						for(EopBasedataStandard st:data){
							//如果查到的标准数据对应的舱位优先级高于或等于当前选择的舱位，则认为是在标准内
							if(!"2".equals(status)&&st.getFdBerth()!=null&&Integer.valueOf(st.getFdBerth().getFdLevel())<=Integer.valueOf(be.getFdLevel())){
								status = "1";
							}else {
								status = "2";
							}
						}
					}
				}else{//金额，检查是否超标
					//标注是否和日期相关，例如按日标准以及每月一次的标准，在执行历史数据校验后若未超标，
					//仍然需要校验是否超了标准金额，防止无论填多少都返回超标的情况
					Boolean relDate = false;
					Double fdMoney = params.optDouble("fdMoney",0.0);
					Double fdStandardMoney = data.get(0).getFdMoney();
					//方案是按人数，则标准额度需要乘以人数
					if("1".equals(scheme.getFdType())&&params.containsKey("fdPersonNumber")){
						int fdPersonNumber = StringUtil.isNotNull(params.getString("fdPersonNumber"))?params.getInt("fdPersonNumber"):1;
						fdStandardMoney = EopBasedataNumberUtil.getMultiplication(fdPersonNumber, fdStandardMoney, 2);
						status=fdMoney>fdStandardMoney?"2":"1";
					}
					//如果方案是按天，则标准额度需要乘以天数
					if("2".equals(scheme.getFdType())){
						rtn.put("dayCount",true);
						rtn.put("fdSchemeId", scheme.getFdId());
						rtn.put("fdForbid", scheme.getFdForbid());
						//如果是报销，需要判断所选日期是否已报过
						String fdBeginDate = params.optString("fdBeginDate");
						String fdEndDate = params.optString("fdEndDate");
						String fdPersonId = params.optString("fdPersonId");
						if("expense".equals(params.get("model"))&&StringUtil.isNotNull(fdPersonId)&&StringUtil.isNotNull(fdEndDate)&&StringUtil.isNotNull(fdBeginDate)&&null!=SysConfigs.getInstance().getModule("/fssc/expense/")){
							status =isDateStandardUsed(fdExpenseItemId,params.getString("fdPersonId"),params.getString("fdBeginDate"),params.getString("fdEndDate"),params.optString("fdModelId"))?"2":"1";
							relDate = true;
						}
						int fdTravelDays = 1;
						try {
							fdTravelDays = params.getInt("fdTravelDays");
						} catch (Exception e) {
						}
						fdStandardMoney = EopBasedataNumberUtil.getMultiplication(fdTravelDays, fdStandardMoney, 2);
					}
					//如果方案是月次，需要查询当月是否已经报过这一类费用
					if("4".equals(scheme.getFdType())){
						rtn.put("monthCount",true);
						rtn.put("fdSchemeId", scheme.getFdId());
						rtn.put("fdForbid", scheme.getFdForbid());
						relDate = true;
						String fdDetailId = params.containsKey("fdDetailId")?params.getString("fdDetailId"):"";
						String fdHappenDate = params.containsKey("fdHappenDate")?params.getString("fdHappenDate"):"";
						String fdPersonId = params.containsKey("fdPersonId")?params.getString("fdPersonId"):"";
						//填写了人员和日期，需要校验该人当月是否报过
						if(StringUtil.isNotNull(fdHappenDate)&&StringUtil.isNotNull(fdPersonId)){
							Date happen = DateUtil.convertStringToDate(fdHappenDate, DateUtil.PATTERN_DATE);
							if("expense".equals(params.get("model"))&&null!=SysConfigs.getInstance().getModule("/fssc/expense/")){
								status =isExpenseMonthStandardUsed(fdPersonId, fdExpenseItemId, happen,fdDetailId)?"2":"1";
							}else if(null!=SysConfigs.getInstance().getModule("/fssc/fee/")){
								status =isFeeMonthStandardUsed(fdPersonId, fdExpenseItemId, happen,fdDetailId)?"2":"1";
							}
						}else{//没有填写人员或者日期，则默认不超标，后续要判断金额是否超了
							status = "1";
						}
					}
					//汇率转换，都转成公司本位币
					if(!relDate||"1".equals(status)||status==null){
						status="2";
						EopBasedataCompany comp = (EopBasedataCompany) findByPrimaryKey(params.getString("fdCompanyId"), EopBasedataCompany.class, true);
						Query query = getBaseDao().getHibernateSession().createQuery("select rate from "+EopBasedataExchangeRate.class.getName()+" rate left join rate.fdCompanyList comp where (comp.fdId=:fdCompanyId or comp is null) and rate.fdType=:fdType and rate.fdIsAvailable=:fdIsAvailable and rate.fdSourceCurrency.fdId=:fdSourceCurrencyId and rate.fdTargetCurrency.fdId=:fdTargetCurrencyId");
						//判断是否启用了实时汇率
						String value = EopBasedataFsscUtil.getSwitchValue("fdRateEnabled");
						if("true".equals(value)){
							query.setParameter("fdType", EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_CURRENT);
						}else{
							query.setParameter("fdType", EopBasedataConstant.FSSC_BASE_EXCHANGE_RATE_TYPE_COST);
						}
						query.setParameter("fdCompanyId", comp.getFdId());
						query.setParameter("fdIsAvailable", true);
						query.setParameter("fdSourceCurrencyId",params.getString("fdCurrencyId"));
						query.setParameter("fdTargetCurrencyId", comp.getFdAccountCurrency().getFdId());
						List<EopBasedataExchangeRate> rateList = query.list();
						Double rate = ArrayUtil.isEmpty(rateList)?1d:rateList.get(0).getFdRate();
						fdMoney = EopBasedataNumberUtil.getMultiplication(fdMoney, rate, 2);
						query.setParameter("fdSourceCurrencyId", data.get(0).getFdCurrency().getFdId());
						rateList = query.list();
						rate = ArrayUtil.isEmpty(rateList)?1d:rateList.get(0).getFdRate();
						fdStandardMoney = EopBasedataNumberUtil.getMultiplication(fdStandardMoney, rate, 2);
						if(fdMoney<=fdStandardMoney){
							status = "1";
						}
					}
				}
				//如果是超标且刚控，则不可以提交
				if("1".equals(scheme.getFdForbid())&&"2".equals(status)){
					rtn.put("submit", "0");
				}
				rtn.put("status", status);
			}else{
				rtn.put("info","[]");
			}
		}
		return rtn;
	}
	private String getStandardSubject(List<EopBasedataStandard> eopBasedataStandardList, EopBasedataStandardScheme scheme) throws Exception {
		JSONArray JsonArray = new JSONArray();
		for(EopBasedataStandard data:eopBasedataStandardList) {
			JSONObject jsonObject = new JSONObject();
			StringBuilder subject = new StringBuilder();
			jsonObject.put("fdStandardId", data.getFdId());
			if(data.getFdItem()!=null){
				subject.append(data.getFdItem().getFdName()).append("/");
				jsonObject.put("fdItemName", data.getFdItem().getFdName());
			}
			if(data.getFdPerson()!=null){
				subject.append(data.getFdPerson().getFdName()).append("/");
			}
			if(data.getFdLevel()!=null){
				subject.append(data.getFdLevel().getFdName()).append("/");
			}
			if(data.getFdArea()!=null){
				subject.append(data.getFdArea().getFdArea()).append("/");
			}
			if(data.getFdVehicle()!=null){
				subject.append(data.getFdVehicle().getFdName()).append("/");
			}
			if(data.getFdBerth()!=null){
				subject.append(data.getFdBerth().getFdName()).append("/");
			}
			//如果是按金额控
			if("1".equals(scheme.getFdTarget())){
				if(data.getFdCurrency()!=null){
					subject.append(data.getFdCurrency().getFdName()).append("/");
				}
				if(data.getFdMoney()!=null){
					subject.append(data.getFdMoney()).append("/");
					jsonObject.put("fdMoney", data.getFdMoney());
					subject.append(EnumerationTypeUtil.getColumnEnumsLabel("eop_basedata_standard_type",scheme.getFdType())).append("/");
					subject.append(EnumerationTypeUtil.getColumnEnumsLabel("eop_basedata_standard_forbid",scheme.getFdForbid()));
				}
			}
			jsonObject.put("subject", subject.toString());
			jsonObject.put("fdForbid", scheme.getFdForbid());
			jsonObject.put("fdSchemeId", scheme.getFdId());
			JsonArray.add(jsonObject);
		}
		
		return JsonArray.toString();
	}
	
	public Boolean isDateStandardUsed(String fdExpenseItemId,String fdPersonId,String fdBeginDate, String fdEndDate,String fdDetailId) throws Exception {
		Date begin = DateUtil.convertStringToDate(fdBeginDate, DateUtil.PATTERN_DATE);
		Date end = DateUtil.convertStringToDate(fdEndDate, DateUtil.PATTERN_DATE);
		Calendar begin_ = Calendar.getInstance();
		begin_.setTime(begin);
		begin_.set(Calendar.HOUR_OF_DAY, 0);
		begin_.set(Calendar.MINUTE, 0);
		begin_.set(Calendar.SECOND, 0);
		Calendar end_ = Calendar.getInstance();
		end_.setTime(end);
		end_.set(Calendar.HOUR_OF_DAY, 23);
		end_.set(Calendar.MINUTE, 59);
		end_.set(Calendar.SECOND, 59);
		StringBuilder hql = new StringBuilder();
		hql.append("select fdId from FsscExpenseDetail where fdExpenseItem.fdId=:fdExpenseItemId and docMain.fdId<>:fdId");
		hql.append(" and ((fdStartDate <= :begin and fdHappenDate>= :begin) or (fdStartDate <= :end and fdHappenDate>= :end)");
		hql.append(" or (fdStartDate >= :begin and fdStartDate<= :end) or (fdHappenDate >= :begin and fdHappenDate<= :end))");
		hql.append(" and fdRealUser.fdId=:fdPersonId and docMain.docStatus in('20','30') and docMain.docTemplate.fdIsTravelAlone=:fdIsTravelAlone ");
		Query query = getBaseDao().getHibernateSession().createQuery(hql.toString());
		query.setParameter("fdExpenseItemId", fdExpenseItemId);
		query.setParameter("fdPersonId", fdPersonId);
		query.setParameter("fdId", fdDetailId);
		query.setParameter("begin", begin_.getTime());
		query.setParameter("end", end_.getTime());
		query.setParameter("fdIsTravelAlone", Boolean.valueOf(false));
		if(!ArrayUtil.isEmpty(query.list())){
			return true;
		}
		hql = new StringBuilder();
		hql.append("select t.fdId from FsscExpenseTravelDetail t,FsscExpenseDetail d where d.docMain.fdId<>:fdId");
		hql.append(" and d.fdExpenseItem.fdId=:fdExpenseItemId and t.docMain.fdId=d.docMain.fdId and t.fdSubject=d.fdTravel");
		hql.append(" and d.fdRealUser.fdId=:fdPersonId and d.docMain.docStatus in('20','30') and d.docMain.docTemplate.fdIsTravelAlone=:fdIsTravelAlone ");
		hql.append(" and ((t.fdBeginDate <= :begin and t.fdEndDate>= :begin) or (t.fdBeginDate <= :end and t.fdEndDate>= :end)");
		hql.append(" or (t.fdBeginDate >= :begin and t.fdBeginDate<= :end) or (t.fdEndDate >= :begin and t.fdEndDate<= :end))");
		query = getBaseDao().getHibernateSession().createQuery(hql.toString());
		query.setParameter("fdExpenseItemId", fdExpenseItemId);
		query.setParameter("fdId", fdDetailId);
		query.setParameter("fdPersonId", fdPersonId);
		query.setParameter("begin", begin_.getTime());
		query.setParameter("end", end_.getTime());
		query.setParameter("fdIsTravelAlone", Boolean.valueOf(true));
		return !ArrayUtil.isEmpty(query.list());
	}
	
	public Boolean isExpenseMonthStandardUsed(String fdPersonId, String fdExpenseItemId, Date fdHappenDate,String fdDetailId) throws Exception {
		Calendar start = Calendar.getInstance();
		start.setTime(fdHappenDate);
		start.set(Calendar.DAY_OF_MONTH, 1);
		String hql = "from com.landray.kmss.fssc.expense.model.FsscExpenseDetail where fdHappenDate between :fdStartDate and :fdEndDate and fdRealUser.fdId=:fdPersonId and fdExpenseItem.fdId=:fdExpenseItemId and fdId<>:fdDetailId and (docMain.docStatus=:docStatus20 or docMain.docStatus=:docStatus30)";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdDetailId", fdDetailId);
		query.setParameter("fdPersonId",fdPersonId);
		query.setParameter("fdExpenseItemId",fdExpenseItemId);
		query.setParameter("docStatus20",SysDocConstant.DOC_STATUS_EXAMINE);
		query.setParameter("docStatus30",SysDocConstant.DOC_STATUS_PUBLISH);
		query.setParameter("fdStartDate",start.getTime());
		Calendar end = Calendar.getInstance();
		end.setTime(fdHappenDate);
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.add(Calendar.MONTH, 1);
		end.add(Calendar.DATE, -1);
		query.setParameter("fdEndDate", end.getTime());
		List<Object> details = query.list();
		return !ArrayUtil.isEmpty(details);
	}
	
	public Boolean isFeeMonthStandardUsed(String fdPersonId, String fdExpenseItemId, Date fdHappenDate,String fdDetailId) throws Exception {
		String hql = "from com.landray.kmss.fssc.fee.model.FsscFeeLedger where fdPersonId=:fdPersonId and fdExpenseItemId=:fdExpenseItemId and fdStartDate < :fdHappenDate and fdEndDate > :fdHappenDate";
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameter("fdExpenseItemId", fdExpenseItemId);
		query.setParameter("fdPersonId", fdPersonId);
		query.setParameter("fdHappenDate", fdHappenDate);
		return !ArrayUtil.isEmpty(query.list());
	}
	
}
