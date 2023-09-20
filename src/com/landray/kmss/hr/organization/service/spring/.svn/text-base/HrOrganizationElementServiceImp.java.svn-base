package com.landray.kmss.hr.organization.service.spring;


import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.concurrent.KMSSCommonThreadUtil;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.forms.HrOrganizationElementForm;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationLog;
import com.landray.kmss.hr.organization.model.HrOrganizationOrg;
import com.landray.kmss.hr.organization.service.IHrOrganizationDeptService;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationLogService;
import com.landray.kmss.hr.organization.service.IHrOrganizationOrgService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.organization.util.HrOrganizationUtil;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.log.model.SysLogOrganization;
import com.landray.kmss.sys.log.service.ISysLogOrganizationService;
import com.landray.kmss.sys.log.util.UserAgentUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.exception.ExistChildrenException;
import com.landray.kmss.sys.organization.interfaces.SysOrgHQLUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.util.SysOrgUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.jdbc.support.JdbcUtils;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class HrOrganizationElementServiceImp extends ExtendDataServiceImp implements IHrOrganizationElementService, IXMLDataBean {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrOrganizationElementServiceImp.class);

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	public IHrStaffPersonInfoService getHrStaffPersonInfoService() {
		if (hrStaffPersonInfoService == null) {
			hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil.getBean("hrStaffPersonInfoService");
		}
		return hrStaffPersonInfoService;
	}

	private IHrOrganizationPostService hrOrganizationPostService;

	public IHrOrganizationPostService getHrOrganizationPostService() {
		if (hrOrganizationPostService == null) {
			hrOrganizationPostService = (IHrOrganizationPostService) SpringBeanUtil
					.getBean("hrOrganizationPostService");
		}
		return hrOrganizationPostService;
	}
	private IHrOrganizationLogService hrOrganizationLogService;

	public IBaseService getHrOrganizationLogService() {
		if (hrOrganizationLogService == null) {
			hrOrganizationLogService = (IHrOrganizationLogService) SpringBeanUtil.getBean("hrOrganizationLogService");
		}
		return hrOrganizationLogService;
	}

	private ISysOrgElementService sysOrgElementService;

	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	private ISysOrgPersonService sysOrgPersonService;

	public ISysOrgPersonService getSysOrgPersonService() {
		if (sysOrgPersonService == null) {
			sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
		}
		return sysOrgPersonService;
	}

	private IHrOrganizationOrgService hrOrganizationOrgService;

	public IHrOrganizationOrgService getHrOrganizationOrgService() {
		if (hrOrganizationOrgService == null) {
			hrOrganizationOrgService = (IHrOrganizationOrgService) SpringBeanUtil.getBean("hrOrganizationOrgService");
		}
		return hrOrganizationOrgService;
	}

	private IHrOrganizationDeptService hrOrganizationDeptService;

	public IHrOrganizationDeptService getHrOrganizationDeptService() {
		if (hrOrganizationDeptService == null) {
			hrOrganizationDeptService = (IHrOrganizationDeptService) SpringBeanUtil
					.getBean("hrOrganizationDeptService");
		}
		return hrOrganizationDeptService;
	}

	private IHrStaffTrackRecordService hrStaffTrackRecordService;

	public IHrStaffTrackRecordService getHrStaffTrackRecordService() {
		if (hrStaffTrackRecordService == null) {
			hrStaffTrackRecordService = (IHrStaffTrackRecordService) SpringBeanUtil
					.getBean("hrStaffTrackRecordService");
		}
		return hrStaffTrackRecordService;
	}

	private ISysLogOrganizationService sysLogOrganizationService;

	public ISysLogOrganizationService getSysLogOrganizationService() {
		if (sysLogOrganizationService == null) {
			sysLogOrganizationService = (ISysLogOrganizationService) SpringBeanUtil
					.getBean("sysLogOrganizationService");
		}
		return sysLogOrganizationService;
	}


	@Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrOrganizationElement) {
            HrOrganizationElement hrOrganizationElement = (HrOrganizationElement) model;
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrOrganizationElement hrOrganizationElement = new HrOrganizationElement();
        HrOrganizationUtil.initModelFromRequest(hrOrganizationElement, requestContext);
        return hrOrganizationElement;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrOrganizationElement hrOrganizationElement = (HrOrganizationElement) model;
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrOrganizationElement element = (HrOrganizationElement) modelObj;
		// 检查编号是否合法
		checkFdNo(element.getFdId(), element.getFdOrgType(), element.getFdNo());

		return super.add(modelObj);
	}

	@Override
	public String addOrg(IBaseModel modelObj) throws Exception {
		HrOrganizationElement elementNew = (HrOrganizationElement) modelObj;
		HrOrganizationElement elemOld = (HrOrganizationElement) findByPrimaryKey(modelObj.getFdId(),
				HrOrganizationElement.class, true);
		//添加日志
		addOrgModifyLog(elementNew, elemOld);
		return super.add(modelObj);
	}

	@Override
	public void updateOrg(IBaseModel modelObj) throws Exception {
		HrOrganizationElement elementNew = (HrOrganizationElement) modelObj;
		HrOrganizationElement elemOld = (HrOrganizationElement) findByPrimaryKey(modelObj.getFdId(),
				HrOrganizationElement.class, true);
		if (elemOld != null) {
			elemOld = cloneSysOrgElement(elemOld);
		}
		//添加日志
		addOrgModifyLog(elementNew, elemOld);
		super.update(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrOrganizationElement element = (HrOrganizationElement) modelObj;
		HrOrganizationElement elemOld = (HrOrganizationElement) findByPrimaryKey(modelObj.getFdId(),
				HrOrganizationElement.class, true);
		if (elemOld != null) {
			elemOld = cloneSysOrgElement(elemOld);
		}

		element.setFdAlterTime(new java.util.Date());
		// 检查编号是否合法
		checkFdNo(element.getFdId(), element.getFdOrgType(), element.getFdNo());
		//添加日志
		addOrgModifyLog(element, elemOld);
		super.update(modelObj);
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		HrOrganizationElement elem = (HrOrganizationElement) findByPrimaryKey(form.getFdId(),
				HrOrganizationElement.class, true);
		HrOrganizationElement elemOld = null;
		HrOrganizationElementForm formOld = null;
		if (elem != null) {
			elemOld = cloneSysOrgElement(elem);

			HrOrganizationElementForm elementForm = null;
			formOld = (HrOrganizationElementForm) super.convertModelToForm(elementForm, elem, requestContext);
		}
		HrOrganizationElement element = (HrOrganizationElement) super.convertFormToModel(form, model, requestContext);

		HrOrganizationElementForm formNew = (HrOrganizationElementForm) form;

		addOrgModifyLog(element, elemOld, requestContext, formOld, formNew);

		return super.convertFormToModel(form, model, requestContext);
	}

	/**
	 * <p>保存人事组织架构操作记录</p>
	 * @author sunj
	 * @throws Exception
	 */
	private void addOrgModifyLog(HrOrganizationElement element, HrOrganizationElement oldElement,
			RequestContext requestContext, HrOrganizationElementForm formOld, HrOrganizationElementForm formNew)
			throws Exception {
		String detailInfo = HrOrgUtil.getDetails(oldElement, element, formOld, formNew);
		String hrDemoDetail = requestContext.getParameter("fdorgDemo");
		if (StringUtil.isNotNull(hrDemoDetail)) {
			detailInfo = hrDemoDetail;
		}
		if (StringUtil.isNotNull(detailInfo)) {
			HrOrganizationLog log = HrOrgUtil.buildSysLog(requestContext);
			log.setFdDetails(log.getFdOperator() + detailInfo);// 设置详细信息
			log.setFdTargetId(element.getFdId());
			getHrOrganizationLogService().add(log);
		}
	}



	/*****
	 * 人事档案中变更员工信息在组织架构中增加操作日志
	 * 注意点 局部编辑的时候 formnew的某些元素是空 如果为空 不纳入比较
	 * 如果 "" 那么是手动去除这个比较的属性
	 * @param element
	 * @param requestContext
	 */
	public void addSysOrgMdifyLog (HrOrganizationElement element,
								   RequestContext requestContext ) throws Exception {
		SysLogOrganization log;
		if(requestContext ==null){
			log = new SysLogOrganization();
			log.setFdCreateTime(new java.util.Date());
			log.setFdBrowser(UserAgentUtil.getBrowser());
			log.setFdEquipment(UserAgentUtil.getOperatingSystem());
			log.setFdOperator(UserUtil.getKMSSUser().getUserName());
			log.setFdOperatorId(UserUtil.getKMSSUser().getUserId());
			log.setFdParaMethod("update");
		}else{
			log = SysOrgUtil.buildSysLog(requestContext);
		}

		if(element !=null && element instanceof  HrStaffPersonInfo) {
			//人员变更才记录日志
			HrStaffPersonInfo newElement = (HrStaffPersonInfo) element;
			/*if(newElement.getFdOrgPerson() ==null){
				//非系统用户 则不记录
				return;
			}*/
			if(!getSysOrgElementService().getBaseDao().isExist(SysOrgElement.class.getName(),newElement.getFdId())){
				//非系统用户 则不记录
				if(logger.isDebugEnabled()){
					logger.debug("用户在组织架构中不存在"+newElement.getFdName());
				}
				return;
			}

			Map<String, String>  map =getHrOldPersonInfo(newElement.getFdId());
			String details = "";
			StringBuffer sb = new StringBuffer();
			if(map ==null || map.size() ==0){
				//原数据不存在，则是新增
				log.setFdParaMethod("add");
				sb.append(ResourceUtil.getString("sysLogOaganization.operate.add", "sys-log"));
				sb.append(HrOrgUtil.getOrgTypeInfo(newElement));
				sb.append(newElement.getFdName());
			} else {
				//修改
				sb.append(ResourceUtil.getString("sysLogOaganization.operate.modify", "sys-log"));
				sb.append("【" + newElement.getFdName() + "】");
				sb.append(HrOrgUtil.getOrgTypeInfo(newElement));
				if (newElement.getFdOrgType().equals(HrOrgUtil.HR_TYPE_PERSON)) {
					HrStaffPersonInfo newhrstaff = (HrStaffPersonInfo) newElement;
					//登录名
					if(compareStr(newhrstaff.getFdLoginName(),map.get("fdLoginName"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdLoginName", "sys-organization"));
					}
					//密码
					if(compareStr(newhrstaff.getFdNewPassword(),map.get("fdPassword"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdPassword", "sys-organization"));
					}
					//邮箱
					if(compareStr(newhrstaff.getFdEmail(),map.get("fdEmail"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdEmail", "sys-organization"));
					}
					//电话号码
					if(compareStr(newhrstaff.getFdMobileNo(),map.get("fdMobileNo"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdMobileNo", "sys-organization"));
					}
					//工作电话
					if(compareStr(newhrstaff.getFdWorkPhone(),map.get("fdWorkPhone"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdWorkPhone", "sys-organization"));
					}
					//性别
					if(compareStr(newhrstaff.getFdSex(),map.get("fdSex"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdSex", "sys-organization"));
					}
					//所属部门
					if(compareStr((newhrstaff.getFdOrgParent() ==null || newhrstaff.getFdOrgParent().getFdId() ==null?null:newhrstaff.getFdOrgParent().getFdId()),map.get("fdOrgParentId"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdParent", "sys-organization"));
					}
					//职务
					if(compareStr((newhrstaff.getFdStaffingLevel() ==null
							|| newhrstaff.getFdStaffingLevel().getFdId() ==null?null:newhrstaff.getFdStaffingLevel().getFdId()),map.get("fdStaffingLevelId"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdStaffingLevel", "sys-organization"));
					}
					//所属岗位
					if(compareStr((newhrstaff.getFdOrgPosts() ==null || newhrstaff.getFdOrgPosts().size()==0
							|| newhrstaff.getFdOrgPosts().get(0) ==null?null: newhrstaff.getFdOrgPosts().get(0).getFdId()),map.get("fdPostIds"))){
						details = StringUtil.linkString(details, " 、", ResourceUtil.getString("sysOrgPerson.fdPosts", "sys-organization"));
					}

					// 如果是“置为无效”操作，需要记录原部门和原岗位

				}
			}
			if(StringUtil.isNotNull(details) || map.size() ==0){
				log.setFdDetails(log.getFdOperator() +sb.toString() +  details + "。");// 设置详细信息
				log.setFdTargetId(newElement.getFdId());
				getSysLogOrganizationService().add(log);
			}
		}
	}

	/*****
	 * @param newElement
	 * @param oldElement
	 * @throws Exception
	 */
	private void addOrgModifyLog(HrOrganizationElement newElement, HrOrganizationElement oldElement) throws Exception {

		HrOrganizationLog log = new HrOrganizationLog();
		log.setFdCreateTime(new java.util.Date());
		SysOrgPerson operator = UserUtil.getUser();
		log.setFdParaMethod("update");
		if (operator == null) {
			log.setFdOperator(ResourceUtil.getString("sysLogOaganization.system", "sys-log"));
			log.setFdOperatorId("");
		} else {
			log.setFdOperator(operator.getFdName());
			log.setFdOperatorId(operator.getFdId());
		}
		String details = log.getFdOperator() + HrOrgUtil.getDetails(newElement, oldElement);

		log.setFdDetails(details);// 设置详细信息
		log.setFdTargetId(newElement.getFdId());
		getHrOrganizationLogService().add(log);
	}

	/**
	 * 获取人事档案的信息
	 * 因为hibernate事务的原由，这里直接使用jdbc查询。
	 * @param fdId
	 * @return
	 */
	private Map getHrOldPersonInfo(String fdId){

		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT " +
					"  per.fd_work_phone as fdWorkPhone, " +
					"  per.fd_mobile_no as fdMobileNo, " +
					"  per.fd_email as fdEmail, " +
					"  per.fd_sex as fdSex, " +
					"  per.fd_login_name as fdLoginName, " +
					"  per.fd_staffing_level_id as fdStaffingLevelId, " +
					"  post.fd_postid as fdPostIds, " +
					"  sysp.fd_password fdPassword, " +
					"  per.fd_org_parent_id as fdOrgParentId " +
					" FROM " +
					"  hr_staff_person_info per " +
					"  LEFT JOIN hr_staff_person_post post ON per.fd_id =post.fd_personid " +
					"  LEFT JOIN sys_org_person sysp ON per.fd_id =sysp.fd_id " +
					"WHERE " +
					"  per.fd_id =? ");
			List paramList=new ArrayList<>();
			paramList.add(fdId);
			final Future<Map<String, Object>> futureRate =KMSSCommonThreadUtil.submit(new TaskPersonInfoThread(sql.toString(),paramList));
			Map<String, Object> result = futureRate.get();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String, String>> rtnList = new ArrayList<Map<String, String>>();
		// 检查与业务相关的数据
		if (logger.isDebugEnabled()) {
			logger.debug("检查与业务相关的数据");
		}
		String fdId = requestInfo.getParameter("fdId");
		try {
			checkBusines((HrOrganizationElement) findByPrimaryKey(fdId));
		} catch (Exception e) {
			Map<String, String> nodeMap = new HashMap<String, String>();
			nodeMap.put("msg", ResourceUtil.getMessage("{sys-organization:sysOrgElement.isBusiness.error.existChildren}"));
			rtnList.add(nodeMap);
		}
		return rtnList;
	}

	/**
	 * 机构/部门设置与业务相关为“否”时，需要校验所有子级
	 *
	 * @param orgElement
	 * @throws Exception
	 */
	private void checkBusines(HrOrganizationElement orgElement) throws Exception {
		// 取数据库中的状态，如果原来的状态为“与业务相关”，则需要取所有的子级
		String sql = "select fd_is_business from hr_org_element where fd_id = :id and fd_is_available = :fdIsAvailable";
		List<?> list = getBaseDao().getHibernateSession().createNativeQuery(sql).setString("id", orgElement.getFdId()).setParameter("fdIsAvailable", Boolean.TRUE).list();
		if (logger.isDebugEnabled()) {
			logger.debug("部门/机构原状态(sql)[" + sql + "]，params[" + orgElement.getFdId() + "]");
			String _status = "";
			if (list != null && !list.isEmpty()) {
				_status = list.get(0).toString();
			}
			logger.debug("部门/机构原状态：" + _status);
		}
		// 当部门（机构）设置业务相关为“否”时，需要校验所有子级部门及其下的人员、岗位 不能含有业务相关为“是”的
		if (list != null && !list.isEmpty()) {
			String _status = list.get(0).toString();
			if ("true".equals(_status) || "1".equals(_status)) {
				sql = "select count(*) from hr_org_element where fd_org_type in (1,2,4) and fd_is_business = :isBusiness and fd_hierarchy_id like :hierarchyId or (fd_parentid = :fdParentId and fd_org_type in (8) and fd_is_business = :isBusiness)";
				List<?> count = getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("isBusiness", Boolean.TRUE).setString("hierarchyId", orgElement.getFdHierarchyId() + "%").setParameter("fdParentId", orgElement.getFdId()).list();
				if (logger.isDebugEnabled()) {
					logger.debug("部门/机构包含有业务相关的子级(sql)[" + sql + "]，params[1," + orgElement.getFdHierarchyId() + "%]");
					logger.debug("部门/机构包含有业务相关的子级：" + count);
				}
				if (count != null && !count.isEmpty()) {
					if (Integer.valueOf(count.get(0).toString()) > 1) {
						throw new KmssException(new KmssMessage("sys-organization:sysOrgElement.isBusiness.error.existChildren"));
					}
				}
			}
		}
	}

	class TaskPersonInfoThread implements Callable<Map<String,Object>> {
		private String sql;
		private List paramList;
		public TaskPersonInfoThread(String sql,List paramList){
			this.sql= sql;
			this.paramList =paramList;
		}
		@Override
		public Map<String,Object> call() {
			return getMapByJdbc(sql, paramList);
		}
	}
	/*****
	 * 导入等其他情况没有elementform 组织架构信变更记录日志操作
	 * @param newElement
	 * @throws Exception
	 */
	@Override
	public void addSysOrgMdifyLog (HrOrganizationElement newElement) throws Exception {
		if(!(newElement instanceof HrStaffPersonInfo)){//非人员的增加不记录
			return;
		}
		/*this.addSysOrgMdifyLog(newElement,new RequestContext(Plugin.currentRequest()));*/

	}

	/**
	 * 比较两个值是否相等
	 * @param newVal
	 * @param oldVal
	 */
	public boolean compareStr(String newVal,String oldVal){
		//新对象的值是null,或者原值是null 则没有变化
		//新对象是null，原对象不是null. 有变化
		//新值不是null，原值是null 有变化
		//新对象不是null,原对象不是null,对比不一样就是有变化
		if(StringUtil.isNull(newVal) && StringUtil.isNull(oldVal )){
			return false;
		}
		if(StringUtil.isNull(newVal) && StringUtil.isNotNull(oldVal)){
			return true;
		}
		if(StringUtil.isNotNull(newVal)&& StringUtil.isNull(oldVal)){
			return true;
		}
		if(!newVal.equals(oldVal)){
			return true;
		}
		return false;
	}


	/**
	 * 根据SQL语句查询单个ID的值
	 * @param sql
	 * @return
	 */
	public Map<String, String> getMapByJdbc(String sql,List<Object> params) {
		Map<String, String> dataMap=new HashMap<String, String>();
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn =null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(sql.toString());
			if(params !=null) {
				int i=1;
				for (Object param : params) {
					if(param !=null) {
						if(param instanceof String) {
							ps.setString(i, String.valueOf(param));
							i++;
						}else if(param instanceof Integer) {
							ps.setInt(i, Integer.parseInt(param.toString()));
							i++;
						}else if(param instanceof java.util.Date) {
							ps.setTimestamp(i, new java.sql.Timestamp(((java.util.Date)param).getTime()));
							i++;
						}
					}
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ResultSetMetaData rsMeta=rs.getMetaData();
				int columnCount=rsMeta.getColumnCount();
				for (int i=1; i<=columnCount; i++) {
					String value = String.valueOf(rs.getObject(i));
					if ("null".equals(value)) {
						value =null;
					}
					dataMap.put(String.valueOf(rsMeta.getColumnLabel(i)),value );
				}
			}
		} catch (SQLException ex) {
			logger.error("查询组织架构的ID串失败：" + ex);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
		return dataMap;
	}

	/**
	 * 检查编号是否合法
	 *
	 * @throws Exception
	 */
	@Override
	public void checkFdNo(String fdId, Integer fdOrgType, String fdNo) throws Exception {
		// 检测编号是否重复
		/*if (StringUtil.isNull(fdNo)) {
			throw new Exception(ResourceUtil.getString("errors.required", null, null,
					ResourceUtil.getString("hr-organization:hrOrganizationElement.fdNo")));
		}*/
		if (StringUtil.isNotNull(fdNo)) {
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer sbf = new StringBuffer("1=1");
			if (StringUtil.isNotNull(fdId)) {
				sbf.append(" and fdId != :fdId ");
				hqlInfo.setParameter("fdId", fdId);
			}
			sbf.append(" and fdOrgType = :fdOrgType and fdNo = :fdNo");
			hqlInfo.setWhereBlock(sbf.toString());
			hqlInfo.setParameter("fdOrgType", fdOrgType);
			hqlInfo.setParameter("fdNo", fdNo);
			logger.debug("检查编号是否存在:fdId=" + fdId + ",fdOrgType=" + fdOrgType + ",fdNo=" + fdNo);
//			List list = this.findList(hqlInfo); //因为下面的方法注释了，此查询没有意义，我注释掉了.王京2020-11-23
			/*if ((list != null) && (list.size() > 0)) {
				throw new Exception(ResourceUtil.getString("hrOrganizationElement.error.fdNo.mustUnique.forImport",
						"hr-organization", null, fdNo));
			}*/
		}
	}

	@Override
	public void checkFdName(String fdId, Integer fdOrgType, String fdName) throws Exception {
		// 检测组织名是否重复
		if (StringUtil.isNull(fdName)) {
			throw new Exception(ResourceUtil.getString("errors.required", null, null,
					ResourceUtil.getString("hr-organization:hrOrganizationElement.fdName")));
		}
		if (StringUtil.isNotNull(fdName)) {
			HQLInfo hqlInfo = new HQLInfo();
			StringBuffer sbf = new StringBuffer("1=1");
			if (StringUtil.isNotNull(fdId)) {
				sbf.append(" and fdId != :fdId ");
				hqlInfo.setParameter("fdId", fdId);
			}
			sbf.append(" and fdOrgType = :fdOrgType and fdName = :fdName");
			hqlInfo.setWhereBlock(sbf.toString());
			hqlInfo.setParameter("fdOrgType", fdOrgType);
			hqlInfo.setParameter("fdName", fdName);
			logger.debug("检查名称是否存在:fdId=" + fdId + ",fdOrgType=" + fdOrgType + ",fdName=" + fdName);
			List list = this.findList(hqlInfo);
			if ((list != null) && (list.size() > 0)) {
				throw new Exception(ResourceUtil.getString("hrOrganizationElement.error.fdNo.mustUnique.forImport",
						"hr-organization", null, fdName));
			}
		}
	}


	private HrOrganizationElement cloneSysOrgElement(HrOrganizationElement elem) {
		HrOrganizationElement elemOld = null;
		try{
			// 这里不能使用浅克隆，因为对象属性会根据新model而发生变化，也不能使用深度克隆，因为会清除hibernate
			// session面报错，只好使用最原始的方法
			if (elem instanceof HrStaffPersonInfo) {
				elemOld = new HrStaffPersonInfo();
				HrStaffPersonInfo tempOld = (HrStaffPersonInfo) elemOld;
				HrStaffPersonInfo tempNew = (HrStaffPersonInfo) elem;
				tempOld.setFdSex(tempNew.getFdSex());
				tempOld.setFdMobileNo(tempNew.getFdMobileNo());
				tempOld.setFdEmail(tempNew.getFdEmail());
				tempOld.setFdLoginName(tempNew.getFdLoginName());
				tempOld.setFdWorkPhone(tempNew.getFdWorkPhone());
			} else {
				elemOld = new HrOrganizationElement();
			}
			//elemOld.setAuthElementAdmins(new ArrayList(elem.getAuthElementAdmins()));
			elemOld.setFdAlterTime(elem.getFdAlterTime());
			elemOld.setFdCreateTime(elem.getFdCreateTime());
			elemOld.setFdHierarchyId(elem.getFdHierarchyId());
			elemOld.setFdId(elem.getFdId());
			elemOld.setFdIsAbandon(elem.getFdIsAbandon());
			elemOld.setFdIsAvailable(elem.getFdIsAvailable());
			elemOld.setFdIsBusiness(elem.getFdIsBusiness());
			elemOld.setFdKeyword(elem.getFdKeyword());
			elemOld.setFdMemo(elem.getFdMemo());
			elemOld.setFdName(elem.getFdName());
			elemOld.setFdNamePinYin(elem.getFdNamePinYin());
			elemOld.setFdNo(elem.getFdNo());
			elemOld.setFdOrder(elem.getFdOrder());
			elemOld.setFdOrgType(elem.getFdOrgType());
			elemOld.setFdParent(elem.getFdParent());
			elemOld.setFdPersons(new ArrayList(elem.getFdPersons() == null ? new ArrayList() : elem.getFdPersons()));
			elemOld.setHbmChildren(new ArrayList(elem.getHbmChildren() == null ? new ArrayList() : elem.getHbmChildren() ));
			elemOld.setHbmParent(elem.getHbmParent());
			elemOld.setHbmParentOrg(elem.getHbmParentOrg());
			if(elem.getHbmPersons() !=null) {
				elemOld.setHbmPersons(new ArrayList(elem.getHbmPersons()));
			}
			elemOld.setHbmSuperLeader(elem.getHbmSuperLeader());
			if(elem.getHbmSuperLeaderChildren() !=null) {
				elemOld.setHbmSuperLeaderChildren(new ArrayList(elem.getHbmSuperLeaderChildren()));
			}
			elemOld.setHbmThisLeader(elem.getHbmThisLeader());
			if(elem.getHbmThisLeaderChildren() !=null) {
				elemOld.setHbmThisLeaderChildren(new ArrayList(elem.getHbmThisLeaderChildren()));
			}
			elemOld.setSysDictModel(elem.getSysDictModel());
			elemOld.setFdOrgEmail(elem.getFdOrgEmail());
		}catch (Exception e){
			e.printStackTrace();
		}
		return elemOld;
	}



	@Override
	public void initStaffPerson() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("执行人事档案数据迁移到人事组织架构........");
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("hrOrganizationElement.fdId");
		List orgElementList = this.findList(hqlInfo);
		findByStaffPersonInfo(orgElementList);
	}

	/**
	 * <p>查询人事档案数据</p>
	 * @author sunj
	 * @throws SQLException
	 */
	private void findByStaffPersonInfo(List orgElementList) throws SQLException {
		DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
		Connection conn = null;
		PreparedStatement selectSql = null, insertSql = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			selectSql = conn.prepareStatement(
					"select fd_id, fd_hierarchy_id, fd_name, fd_staff_no, fd_status from hr_staff_person_info");
			insertSql = conn.prepareStatement(
					"insert into hr_org_element (fd_id, fd_org_type, fd_hierarchy_id, fd_name, fd_no, fd_is_available, fd_is_abandon, fd_is_business, fd_create_time, fd_alter_time) VALUES (?,?,?,?,?,?,?,?,?,?)");
			rs = selectSql.executeQuery();
			String fdId = null, fdHierarchyId = null, fdName = null, fdStaffNo = null, fdStatus = null;
			while (rs.next()) {
				fdId = rs.getString(1);
				if (logger.isDebugEnabled()) {
					logger.debug("fdId=" + fdId);
				}
				fdHierarchyId = rs.getString(2);
				fdName = rs.getString(3);
				fdStaffNo = rs.getString(4);
				fdStatus = rs.getString(5);
				if (StringUtil.isNull(fdId)) {
					continue;
				}
				if (!ArrayUtil.isEmpty(orgElementList) && orgElementList.contains(fdId)) {
					continue;
				}

				insertSql.setString(1, fdId);
				insertSql.setInt(2, HrOrgConstant.HR_TYPE_PERSON);
				insertSql.setString(3, fdHierarchyId);
				insertSql.setString(4, fdName);
				insertSql.setString(5, fdStaffNo);
				if (StringUtil.isNotNull(fdStatus) && "leave".equals(fdStatus)) {
					insertSql.setBoolean(6, Boolean.FALSE);
				} else {
					insertSql.setBoolean(6, Boolean.TRUE);
				}
				insertSql.setBoolean(7, Boolean.FALSE);
				insertSql.setBoolean(8, Boolean.TRUE);
				Long currTime = System.currentTimeMillis();
				insertSql.setDate(9, new Date(currTime));
				insertSql.setDate(10, new Date(currTime));
				insertSql.addBatch();
			}
			insertSql.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("", e);
		} finally {
			rs.close();
			selectSql.close();
			insertSql.close();
			conn.close();
		}
	}


	private void updatePersonInfo() throws Exception {
		List<HrStaffPersonInfo> personInfos = getHrStaffPersonInfoService().findList(new HQLInfo());
		for (HrStaffPersonInfo personInfo : personInfos) {
			IBaseModel element = getSysOrgElementService().findByPrimaryKey(personInfo.getFdId(), null, true);
			if (null == element || StringUtil.isNull(element.getFdId())) {
				if (logger.isDebugEnabled()) {
					logger.debug("未找到该人员对应的EKP组织架构信息！");
				}
				continue;
			}
			HrOrgUtil.setHierarchy(personInfo, (SysOrgElement) element);
			this.update(personInfo);
		}
	}

	/*private void setHierarchy(HrOrganizationElement hrOrganizationElement, SysOrgElement element) throws Exception {
		hrOrganizationElement.setFdId(element.getFdId());
		hrOrganizationElement.setFdHierarchyId(element.getFdHierarchyId());
		if (null != element.getFdParent()) {
			hrOrganizationElement
					.setFdParent((HrOrganizationElement) this.findByPrimaryKey(element.getFdParent().getFdId()));
		}
		if (null != element.getHbmParentOrg()) {
			hrOrganizationElement.setHbmParentOrg(
					(HrOrganizationElement) this.findByPrimaryKey(element.getHbmParentOrg().getFdId()));
		}
		if (null != element.getHbmThisLeader()) {
			//本级领导
			hrOrganizationElement.setHbmThisLeader(
					(HrOrganizationElement) this.findByPrimaryKey(element.getHbmThisLeader().getFdId()));
		}
		if (null != element.getHbmSuperLeader()) {
			//上级领导
			hrOrganizationElement.setHbmSuperLeader(
					(HrOrganizationElement) this.findByPrimaryKey(element.getHbmSuperLeader().getFdId()));
		}
		//同步
		//最后更新时间
		hrOrganizationElement.setFdAlterTime(element.getFdAlterTime());
	}*/

	@Override
	public HrOrganizationElement findOrgByName(String fdName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrOrganizationElement.fdName = :fdName and hrOrganizationElement.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdName", fdName);
		hqlInfo.setParameter("fdIsAvailable", true);
		List<HrOrganizationElement> list = this.findList(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}


	@Override
	public void updateMergeOrg(String currOrgId, String newOrgId) throws Exception {
		HrOrganizationElement newElement = (HrOrganizationElement) this.findByPrimaryKey(newOrgId);
		/**
		 * 根据层级来合并
		 */
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdHierarchyId like :fdId and fdIsAvailable =:fdIsAvailable and fdOrgType = :fdOrgType");
		hqlInfo.setParameter("fdId", "%" + currOrgId + "%");
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setParameter("fdOrgType", HrOrgConstant.HR_TYPE_PERSON);
		List<HrStaffPersonInfo> persons = this.findList(hqlInfo);

		/**
		 * 根据父级部门来合并
		 */
		HQLInfo hqlInfo2 = new HQLInfo();
		hqlInfo2.setWhereBlock("hbmParent.fdId = :fdParentId and fdIsAvailable =:fdIsAvailable and fdOrgType = :fdOrgType");
		hqlInfo2.setParameter("fdParentId", currOrgId);
		hqlInfo2.setParameter("fdIsAvailable", true);
		hqlInfo2.setParameter("fdOrgType", HrOrgConstant.HR_TYPE_PERSON);
		List<HrStaffPersonInfo> persons2 = this.findList(hqlInfo2);
		Set<HrStaffPersonInfo> updateList =new HashSet<>();
		if(CollectionUtils.isNotEmpty(persons)){
			updateList.addAll(persons);
		}
		if(CollectionUtils.isNotEmpty(persons2)){
			updateList.addAll(persons2);
		}
		for (HrStaffPersonInfo personInfo : updateList) {
			personInfo.setFdParent(newElement);
			this.update(personInfo);
		}

		//合并兼岗数据
		updateTrackRecord(currOrgId, newOrgId);
	}

	private void updateTrackRecord(String currOrgId, String newOrgId) throws Exception {
		HQLInfo hql = new HQLInfo();
		hql.setWhereBlock("fdHrOrgDept.fdId=:fdParentId and fdType = '2' and fdStatus ='1'");
		hql.setParameter("fdParentId", currOrgId);
		List<HrStaffTrackRecord> records = getHrStaffTrackRecordService().findList(hql);
		if (!ArrayUtil.isEmpty(records)) {
			for (HrStaffTrackRecord record : records) {
				if (getHrStaffTrackRecordService().checkUnique(record.getFdId(), record.getFdPersonInfo().getFdId(),
						currOrgId, record.getFdHrOrgPost().getFdId(), null, "2")) {
					record.setFdHrOrgDept((HrOrganizationElement) this.findByPrimaryKey(newOrgId));
					getHrStaffTrackRecordService().update(record);
				} else {
					getHrStaffTrackRecordService().delete(record);
				}
			}
		}
	}

	@Override
	public boolean updateInvalidated(String fdId, RequestContext requestContext) throws Exception {
		HrOrganizationElement element = (HrOrganizationElement) this.findByPrimaryKey(fdId);
		if(null != element && null != element.getFdChildren() && element.getFdChildren().size()>0) {
			HrOrganizationElement elements = null;
			for (Object object : element.getFdChildren()) {
				if(null != object) {
					elements = (HrOrganizationElement) object;
					if(elements.getFdIsAvailable()) {
						throw new ExistChildrenException();
					}
				}
			}
		}

		List list = new ArrayList();
		if (null != element) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdHierarchyId like :fdHierarchyId and fdIsAvailable =:fdIsAvailable and fdId != :fdId");
			hqlInfo.setParameter("fdHierarchyId", "%" + fdId + "%");
			hqlInfo.setParameter("fdId", fdId);
			hqlInfo.setParameter("fdIsAvailable", true);
			list = this.findList(hqlInfo);
		}
		if (!ArrayUtil.isEmpty(list)) {
			return false;
		} else {
			HrOrganizationElement elemOld = null;
			HrOrganizationElementForm elementForm = null;
			HrOrganizationElementForm formOld = null;
			HrOrganizationElementForm formNew = null;

			if (element != null) {
				elemOld = cloneSysOrgElement(element);
				formOld = (HrOrganizationElementForm) super.convertModelToForm(elementForm, element, requestContext);

				element.setFdIsAvailable(new Boolean(false));
				formNew = (HrOrganizationElementForm) super.convertModelToForm(elementForm, element, requestContext);
			}

			// 增加组织架构操作日志
			if (requestContext.getRequest() != null) {
				addOrgModifyLog(element, elemOld, requestContext, formOld,
						formNew);
			}
			element.setFdPersons(null);
			element.setFdIsAvailable(false);
			element.setFdCompileNum(0);
			element.setFdIsLimitNum("false");
			element.setFdIsCompileOpen("false");
			this.update(element);
			return true;
		}
	}

	@Override
	public boolean updateValid(String fdId, RequestContext requestContext) throws Exception {
		HrOrganizationElement element = (HrOrganizationElement) this.findByPrimaryKey(fdId);
		List list = new ArrayList();
		if (null != element) {
			//添加日志
			HrOrganizationLog log = HrOrgUtil.buildSysLog(requestContext);
			String details = ResourceUtil.getString("hr.organization.log.changeEnabled.detail", "hr-organization",
					requestContext.getLocale(), new Object[] { log.getFdOperator(), element.getFdName() });

			log.setFdDetails(details);// 设置详细信息
			log.setFdTargetId(element.getFdId());
			getHrOrganizationLogService().add(log);

			element.setFdIsAvailable(true);
			this.update(element);
			return true;
		}
		return false;
	}

	@Override
	public JSONObject saveElementImportData(InputStream inputStream,
											Locale locale)
			throws Exception {
		Workbook wb = null;
		JSONArray otherErrors = new JSONArray();
		Sheet postSheet = null;
		Sheet staffSheet = null;
		Sheet orgSheet = null;
		try {
			wb = WorkbookFactory.create(inputStream);
			postSheet = wb.getSheetAt(3);
			staffSheet = wb.getSheetAt(5);
			orgSheet = wb.getSheetAt(1);

			boolean hasError = false;
			boolean noData = true;
			StringBuffer successTips = new StringBuffer();
			if (orgSheet.getPhysicalNumberOfRows() > 1) {
				JSONObject result = addImportData(wb, locale);
				noData = false;
				if (result.getInt("hasError") == 1) {
					hasError = true;
					result.put("importType", ResourceUtil
							.getString("hrOrganizationElement.import.type.org",
									"hr-organization"));
					return result;
				} else {
					successTips.append(
							"<div>组织导入：" + result.get("importMsg") + "</div>");
				}
			}
			// 有数据
			if (postSheet.getPhysicalNumberOfRows() > 1) {
				JSONObject postResult = getHrOrganizationPostService()
						.addImportData(postSheet, locale);
				noData = false;
				if (postResult.getInt("hasError") == 1) {
					hasError = true;
					postResult.put("importType", ResourceUtil
							.getString("hrOrganizationElement.import.type.post",
									"hr-organization"));
					return postResult;
				} else {
					successTips.append(
							"<div>岗位导入：" + postResult.get("importMsg") + "</div>");
				}

			}

			if (staffSheet.getPhysicalNumberOfRows() > 1) {
				HrStaffPersonInfoForm staffForm = new HrStaffPersonInfoForm();
				staffForm.setFdSource("com.landray.kmss.hr.organization");
				KmssMessage staffMessage = getHrStaffPersonInfoService()
						.saveImportData(staffSheet, staffForm);
				JSONObject staffresult = new JSONObject();
				noData = false;
				if (staffMessage.getMessageType() == KmssMessage.MESSAGE_ERROR) {
					staffresult.put("hasError", 1);
					hasError = true;
					String[] titleArr = { ResourceUtil
							.getString("hrOrganizationElement.import.orgBrief",
									"hr-organization") };
					staffresult.put("titles", titleArr);
					staffresult.put("otherErrors",
							new String[] { staffMessage.getMessageKey() });
					staffresult.put("errorRows", new String[] {});
					staffresult.put("importType", ResourceUtil
							.getString("hrOrganizationElement.import.type.staff",
									"hr-organization"));
					return staffresult;
				} else {
					successTips.append(
							"<div>人员导入：" + staffMessage.getMessageKey() + "</div>");
				}

			}
			if (noData) {
				// 没有数据时返回默认报错信息
				JSONObject otherResult = new JSONObject();
				otherResult.put("hasError", 1);
				otherResult.put("titles", new String[] { ResourceUtil
						.getString("hrOrganizationElement.import.orgBrief",
								"hr-organization") });

				otherResult.put("otherErrors", new String[] { ResourceUtil
						.getString("hrOrganizationElement.import.notnull",
								"hr-organization") });
				otherResult.put("errorRows", new String[] {});
				return otherResult;
			}
			if (!hasError) {
				JSONObject successResult = new JSONObject();
				successResult.put("hasError", 0);
				successResult.put("importMsg", successTips.toString());
				return successResult;
			}
		} catch (IOException e) {
			otherErrors.add(e.getMessage());
		}finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}
	@Override
	public JSONObject addImportData(Workbook wb, Locale locale)
			throws Exception {

		org.apache.poi.ss.usermodel.Sheet sheet = null;
		JSONObject result = new JSONObject();
		JSONArray titles = new JSONArray(); // 标题头
		JSONArray errorRows = new JSONArray(); // 每个错误行（包含错误行号，错误列号，行的错误信息）
		JSONArray otherErrors = new JSONArray(); // 其他错误
		int columnSize = 9;
		int successCount = 0, failCount = 0;
		sheet = wb.getSheetAt(1);
		// 数据必须大于columnSize-1列，且不能少于2行
		if (sheet.getLastRowNum() < 1
				|| sheet.getRow(0).getLastCellNum() < columnSize - 1) {
			otherErrors.add(HrOrgUtil
					.getStr("hrOrganization.import.template.fileError"));
		} else {
			HrOrganizationElement element = null;
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				int rowIndex = i + 1;
				JSONObject errorRow = new JSONObject();
				// 行不为空
				if (row == null) {
					continue;
				}
				// 每列都是空的行跳过
				int j = 0;
				for (; j < columnSize; j++) {
					if (StringUtil
							.isNotNull(HrOrgUtil.getCellValue(row.getCell(j)))) {
						break;
					}
				}
				if (j == columnSize) {
					continue;
				}
				// 组织编号
				String elementNO = HrOrgUtil.getCellValue(row.getCell(0));
				// 组织名称
				String elementName = HrOrgUtil.getCellValue(row.getCell(1));
				// 组织类型
				String elementOrgType = HrOrgUtil.getCellValue(row.getCell(2));
				// 上级组织名称
				String elementParentName = HrOrgUtil.getCellValue(row.getCell(3));
				// 组织负责人
				String elementLeader = HrOrgUtil.getCellValue(row.getCell(4));
				// 组织分管领导
				String elementBaranLeaader = HrOrgUtil.getCellValue(row.getCell(5));
				// 组织简称
				String elementNameAbbr = HrOrgUtil.getCellValue(row.getCell(6));
				// 排序号
				String elementOrder = HrOrgUtil.getCellValue(row.getCell(7));
				// 组织描述
				String elementMemo = HrOrgUtil.getCellValue(row.getCell(8));
				// 是否业务相关
				String fdIsBusiness = HrOrgUtil.getCellValue(row.getCell(9));

				if (StringUtil.isNotNull(elementOrgType)) {
					if ("机构".equals(elementOrgType)) {
						element = new HrOrganizationOrg();
					} else if ("部门".equals(elementOrgType)) {
						element = new HrOrganizationDept();
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 2,
								HrOrgUtil.getStr("hrOrganizationElement.fdOrgType.not.exist"));
					}
				} else {
					HrOrgUtil.addRowError(errorRow, rowIndex, 2,
							HrOrgUtil.getStr("hrOrganizationElement.fdOrgType.notnull"));
				}
				if (element != null) {
					if (StringUtil.isNotNull(elementNO)) {
						element.setFdNo(elementNO);
					}

					if (StringUtil.isNotNull(elementName)) {
						element.setFdName(elementName);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 1, HrOrgUtil
								.getStr("hrOrganizationElement.fdName.notnull"));
					}

					HrOrganizationElement organizationElement = this.findByOrgNoAndName(elementNO, elementName);
					if (null != organizationElement) {
						element = organizationElement;
					}

					if (StringUtil.isNotNull(elementParentName)) {
						HrOrganizationElement hrOrganizationElement = findOrgByName(
								elementParentName);
						if (null != hrOrganizationElement) {
							element.setFdParent(hrOrganizationElement);
						} else {
							HrOrgUtil.addRowError(errorRow, rowIndex, 3,
									HrOrgUtil.getStr(
											"hrOrganizationElement.fdParent.not.exist"));
						}
					}
					if (StringUtil.isNotNull(elementLeader)) {
						HrOrganizationElement hrOrganizationElement = findOrgByName(
								elementLeader);
						if (null != hrOrganizationElement) {
							element.setHbmThisLeader(hrOrganizationElement);
						} else {
							HrOrgUtil.addRowError(errorRow, rowIndex, 4,
									HrOrgUtil.getStr(
											"hrOrganizationElement.hbmThisLeader.not.exist"));
						}
					}
					if (StringUtil.isNotNull(elementBaranLeaader)) {
						HrOrganizationElement hrOrganizationElement = findOrgByName(
								elementLeader);
						if (null != hrOrganizationElement) {
							element.setFdBranLeader(hrOrganizationElement);
						} else {
							HrOrgUtil.addRowError(errorRow, rowIndex, 5,
									HrOrgUtil.getStr(
											"hrOrganizationElement.fdBranLeader.not.exist"));
						}
					}
					if (StringUtil.isNotNull(elementNameAbbr)) {
						element.setFdNameAbbr(elementNameAbbr);
					}
					if (StringUtil.isNotNull(elementOrder)) {
						element.setFdOrder(Integer.valueOf(elementOrder));
					}
					if (StringUtil.isNotNull(elementMemo)) {
						element.setFdMemo(elementMemo);
					}
					if (StringUtil.isNotNull(fdIsBusiness)) {
						element.setFdIsBusiness("否".equals(fdIsBusiness) ? Boolean.FALSE : Boolean.TRUE);
					}else{
						element.setFdIsBusiness(Boolean.TRUE);
					}
				}

				// 有错误
				if (errorRow.get("errRowNumber") != null) {
					// 当前行的内容
					JSONArray contents = new JSONArray();
					for (int k = 0; k < columnSize; k++) {
						String value = HrOrgUtil.getCellValue(row.getCell(k));
						contents.add(value);
					}
					errorRow.put("contents", contents);
					errorRows.add(errorRow);
					failCount++;
				} else {
					try {
						this.add(element);
						successCount++;
					} catch (Exception e) {
						e.printStackTrace();
						otherErrors.add(e.getMessage());
						failCount++;
					}
				}
			}
			int hasError = 0;
			if (otherErrors.size() > 0 || errorRows.size() > 0) {
				hasError = 1;
			}

			result.put("hasError", hasError);
			result.put("errorRows", errorRows);
			if (hasError == 1) { // 有错误
				titles.add(
						HrOrgUtil.getStr("hrOrganization.import.lineNumber")); // 行号

				titles.add(HrOrgUtil.getStr("hrOrganizationElement.fdNo"));
				titles.add(HrOrgUtil.getStr("hrOrganizationElement.fdName"));
				titles.add(HrOrgUtil.getStr("hrOrganizationElement.fdOrgType"));
				titles.add(HrOrgUtil.getStr("hrOrganizationElement.fdParent"));
				titles.add(HrOrgUtil
						.getStr("hrOrganizationElement.hbmThisLeader"));
				titles.add(
						HrOrgUtil.getStr("hrOrganizationElement.fdBranLeader"));
				titles.add(
						HrOrgUtil.getStr("hrOrganizationElement.fdNameAbbr"));
				titles.add(HrOrgUtil.getStr("hrOrganizationElement.fdOrder"));
				titles.add(HrOrgUtil.getStr("hrOrganizationElement.fdMemo"));

				titles.add(
						HrOrgUtil.getStr("hrOrganization.import.errorDetails")); // 错误详情
				result.put("titles", titles);
				String importMsg = ResourceUtil.getString(
						"hrOrganization.import.format.msg", "hr-organization",
						locale,
						new Object[] { successCount, failCount });
				result.put("importMsg", importMsg);
			} else { // 无错误
				String importMsg = ResourceUtil.getString(
						"hrOrganization.import.format.msg.succ",
						"hr-organization",
						locale, new Object[] { successCount });
				result.put("importMsg", importMsg);
			}
		}
		result.put("otherErrors", otherErrors);
		return result;
	}

	@Override
	public HSSFWorkbook buildTemplateWorkbook(HttpServletRequest request)
			throws Exception {
		// 组织编号， 组织名称，组织类型，上级组织名称，组织负责人，组织分管领导，组织简称，排序号，组织描述
		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			buildOrgBrief(wb);
			buildOrgSheet(wb);
			buildPostBrief(wb);
			getHrOrganizationPostService().buildPostSheet(wb);
			buildStaffBrief(wb);
			wb =HrStaffImportUtil.buildTempletWorkBook(wb,
					getHrStaffPersonInfoService().getModelName(),
					getHrStaffPersonInfoService().getImportFields(true), null,
					null, "hrOrg");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return wb;
	}

	private void buildOrgBrief(HSSFWorkbook wb) throws Exception {
		HSSFSheet sheet = wb.createSheet(ResourceUtil
				.getString(
						"hr-organization:hrOrganizationElement.import.orgBrief"));
		sheet.setDefaultColumnWidth(25); // 设置宽度
		CellRangeAddress region = new CellRangeAddress((short) 0, (short) 0, (short) 3, (short) 6);
		com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegionForRegion(sheet, region);
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		HSSFCell cellFirst = row.createCell(0);
		// 注意事项
		StringBuffer cont = new StringBuffer();
		cont.append(ResourceUtil.getString(
				"hr-organization:hrOrganizationElement.import.careful")
				+ "\r ");
		cont.append(ResourceUtil.getString(
				"hr-organization:hrOrganizationElement.import.org.care1")
				+ "\r ");
		cont.append(ResourceUtil.getString(
				"hr-organization:hrOrganizationElement.import.org.care2")
				+ "\r ");
		cont.append(ResourceUtil.getString(
				"hr-organization:hrOrganizationElement.import.org.care3")
				+ "\r ");
		cellFirst.setCellValue(cont.toString());
		// 属性说明表格
		HSSFRow row4 = sheet.createRow((int) 4);
		row4.createCell(0).setCellValue(ResourceUtil.getString(
				"hr-organization:hrOrganizationElement.import.thead.fdname"));
		row4.createCell(1).setCellValue(ResourceUtil.getString(
				"hr-organization:hrOrganizationElement.import.thead.brief"));
		for (int i = 0; i < getOrgField().length; i++) {
			HSSFRow propsRow = sheet.createRow((int) 5 + i);
			propsRow.createCell(0).setCellValue(getOrgField()[i]);
			if (i == 0 || i == 1) {
				propsRow.createCell(1).setCellValue(ResourceUtil.getString(
						"hr-organization:hrOrganizationElement.import.porps.reuqired"));
			} else {
				propsRow.createCell(1).setCellValue("");
			}
			if(i == 9){
				propsRow.createCell(1).setCellValue("默认为是，填写''是''或者''否''");
			}
		}
	}

	private String[] getOrgField() throws Exception {
		return new String[] {
				HrOrgUtil.getStr("hrOrganizationElement.fdNo"),
				HrOrgUtil.getStr("hrOrganizationElement.fdName"),
				HrOrgUtil.getStr("hrOrganizationElement.fdOrgType"),
				HrOrgUtil.getStr("hrOrganizationElement.fdParent"),
				HrOrgUtil.getStr("hrOrganizationElement.hbmThisLeader"),
				HrOrgUtil.getStr("hrOrganizationElement.fdBranLeader"),
				HrOrgUtil.getStr("hrOrganizationElement.fdNameAbbr"),
				HrOrgUtil.getStr("hrOrganizationElement.fdOrder"),
				HrOrgUtil.getStr("hrOrganizationElement.fdMemo"),
				HrOrgUtil.getStr("hrOrganizationElement.fdIsBusiness"),
		};
	}

	private void buildOrgSheet(HSSFWorkbook wb) throws Exception {
		String sheetName = HrOrgUtil
				.getStr("hrOrganizationElement.import.organization");
		HSSFSheet sheet = wb.createSheet(sheetName);
		String[] baseColumns = getOrgField();
		Integer[] notNullArr = new Integer[] { 1, 2 };
		List notNullList = Arrays.asList(notNullArr);
		sheet.setDefaultColumnWidth(25); // 设置宽度
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		HSSFCellStyle style = getStyle(wb);
		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色
		style.setFont(font2);
		HSSFCell cell = null;
		for (int i = 0; i < baseColumns.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(baseColumns[i]);
			if (notNullList.contains(i)) {
				cell.setCellStyle(style);
			} else {
				HSSFCellStyle style1 = getStyle(wb);
				cell.setCellStyle(style1);
			}
		}
	}

	private static HSSFCellStyle getStyle(HSSFWorkbook wb) {
		// 单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		// 背景色
		style.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index);

		return style;
	}

	private void buildStaffBrief(HSSFWorkbook wb) throws Exception {
		HSSFSheet sheet = wb.createSheet(ResourceUtil
				.getString(
						"hr-organization:hrOrganizationElement.import.staffBrief"));
		sheet.setColumnWidth(1, 60 * 256);
		sheet.setDefaultColumnWidth(25); // 设置宽度
		CellRangeAddress region = new CellRangeAddress((short) 0, (short) 0, (short) 3, (short) 6);
		com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegionForRegion(sheet, region);
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		HSSFCell cellFirst = row.createCell(0);
		StringBuffer careTips = new StringBuffer();
		careTips.append(ResourceUtil.getString(
				"hr-organization:hrOrganizationElement.import.careful")
				+ "\r ");
		careTips.append("1.以下表头红色为必填项，黑色为非必填项。" + "\r ");
		careTips.append("2、导入员工花名册会自动生成企业组织架构和员工信息。" + "\r ");
		cellFirst.setCellValue(careTips.toString());
		HSSFRow headRow = sheet.createRow((int) 4);
		HSSFCellStyle style = getStyle(wb);
		HSSFCell headOne = headRow.createCell(0);
		headOne.setCellValue("属性名称");
		headOne.setCellStyle(style);
		HSSFCell headTwo = headRow.createCell(1);
		headTwo.setCellValue("属性说明");
		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色
		headTwo.setCellStyle(style);
		String[] fields = getHrStaffPersonInfoService()
				.getImportFields(true);
		Map<String, SysDictCommonProperty> map = SysDataDict.getInstance()
				.getModel(getHrStaffPersonInfoService().getModelName())
				.getPropertyMap();
		for (int i = 0; i < fields.length; i++) {
			HSSFRow briefRow = sheet.createRow((int) i + 5);
			HSSFCell cellone = briefRow.createCell(0);

			SysDictCommonProperty property = map.get(fields[i]);
			cellone.setCellValue(
					ResourceUtil.getString(property.getMessageKey()));
			if (property.isNotNull()) {
				style = wb.createCellStyle();
				style.setFont(font2);
				cellone.setCellStyle(style);
			}
			HSSFCell cellTwo = briefRow.createCell(1);
			if (StringUtil
					.isNotNull((String) staffBriefProps().get(fields[i]))) {
				String res = (String) staffBriefProps().get(fields[i]);
				cellTwo.setCellValue(res);
			}
		}
	}

	private JSONObject staffBriefProps() throws Exception {
		JSONObject props = new JSONObject();
		props.accumulate("fdMobileNo", "11位手机号，系统根据手机号码、证件号码判断员工的唯一性");
		props.accumulate("fdStatus", "正式、试用、实习、离职、临时、试用延期、解聘、退休");
		props.accumulate("fdEntryTime", "格式如：2010/10/10");
		props.accumulate("fdTrialOperationPeriod", "纯数字如：1");
		props.accumulate("fdPositiveTime", "格式如：2010/10/10");
		props.accumulate("fdLeaveTime", "格式如：2010/10/10");
		props.accumulate("fdSex", "填男或女");
		props.accumulate("hbmParent", "填写上级组织名称");
		props.accumulate("fdOrgPosts", "填岗位名称,导入多个岗位的时候请用英文分号隔开");
		props.accumulate("fdStaffingLevel", "填写职务名称");
		props.accumulate("fdIsBusiness", "默认为是，填写''是''或者''否''");
		props.accumulate("fdCanLogin", "默认为是，填写''是''或者''否''");
		return props;
	}

	private void buildPostBrief(HSSFWorkbook wb) throws Exception {
		HSSFSheet sheet = wb.createSheet(ResourceUtil
				.getString(
						"hr-organization:hrOrganizationElement.import.postBrief"));
		sheet.setDefaultColumnWidth(25); // 设置宽度
		CellRangeAddress region = new CellRangeAddress((short) 0, (short) 0, (short) 3, (short) 6);
		com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegionForRegion(sheet, region);
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		HSSFCell cellFirst = row.createCell(0);
		StringBuffer careTips = new StringBuffer();
		careTips.append(ResourceUtil.getString(
				"hr-organization:hrOrganizationElement.import.careful")
				+ "\r ");
		careTips.append("1、岗位编码请勿重复。" + "\r ");
		careTips.append("2、默认职级、所属组织和所属岗位类别需要是系统内已经有的职级、组织和岗位类别" + "\r ");
		careTips.append("3、职级上限不能小于职级下限" + "\r ");
		careTips.append("4、是否业务相关，默认为是，填写''是''或者''否''" + "\r ");
		cellFirst.setCellValue(careTips.toString());

	}

	@Override
	public HrOrganizationElement findOrgById(String fdId) throws Exception {
		return (HrOrganizationElement) this.findByPrimaryKey(fdId, HrOrganizationElement.class, true);
	}

	@Override
	public List expandToPersonIds(List orgList) throws Exception {
		if (orgList == null || orgList.isEmpty()) {
			return new ArrayList();
		}
		Session session = this.getBaseDao().getHibernateSession();
		List hierarchyIds = new ArrayList();
		List postIds = new ArrayList();
		List personIds = new ArrayList();
		List results;
		String sql, whereBlock;

		for (int i = 0; i < orgList.size(); i++) {
			Object tmpOrg = orgList.get(i);
			HrOrganizationElement element = null;
			if (tmpOrg instanceof String) {
				element = (HrOrganizationElement) findByPrimaryKey((String) tmpOrg);
			} else {
				element = (HrOrganizationElement) orgList.get(i);
			}
			if (element != null) {
				switch (element.getFdOrgType().intValue()) {
				case HrOrgConstant.HR_TYPE_ORG:
				case HrOrgConstant.HR_TYPE_DEPT:
					hierarchyIds.add(element.getFdHierarchyId());
					break;
				case HrOrgConstant.HR_TYPE_POST:
					if (!postIds.contains(element.getFdId())) {
						postIds.add(element.getFdId());
					}
					break;
				case HrOrgConstant.HR_TYPE_PERSON:
					if (!personIds.contains(element.getFdId())) {
						personIds.add(element.getFdId());
					}
					break;
				}
			}
		}
		// 解释部门
		if (!hierarchyIds.isEmpty()) {
			hierarchyIds = SysOrgHQLUtil.formatHierarchyIdList(hierarchyIds);
			StringBuffer whereBf = new StringBuffer();
			for (int i = 0; i < hierarchyIds.size(); i++) {
				whereBf.append(" or fd_hierarchy_id like '").append(hierarchyIds.get(i))
						.append("%' and fd_is_available=1"); // 加上fd_is_available=1条件，以兼容有些数据迁移同步过程中层级id没置空的情况
			}
			whereBlock = "(" + whereBf.substring(4) + ")";
			sql = "select fd_id from hr_org_element where fd_org_type=" + HrOrgConstant.HR_TYPE_PERSON + " and "
					+ whereBlock;
			if (logger.isDebugEnabled()) {
				logger.debug("部门解释个人：" + sql);
			}
			addQueryResultToList(session, sql, personIds);
			sql = "select fd_personid from hr_org_post_person left join hr_org_element on fd_id=fd_postid where "
					+ whereBlock;
			if (logger.isDebugEnabled()) {
				logger.debug("部门－岗位解释个人：" + sql);
			}
			addQueryResultToList(session, sql, personIds);
		}
		// 解释岗位
		if (!postIds.isEmpty()) {
			whereBlock = HQLUtil.buildLogicIN("fd_postid", postIds);
			sql = "select fd_personid from hr_org_post_person where " + whereBlock;
			if (logger.isDebugEnabled()) {
				logger.debug("岗位解释：" + sql);
			}
			addQueryResultToList(session, sql, personIds);
		}
		return personIds;
	}

	private void addQueryResultToList(Session session, String sql, List list) {
		ArrayUtil.concatTwoList(session.createNativeQuery(sql).list(), list);
	}

	@Override
	public HrOrganizationElement findByOrgNoAndName(String fdNo, String fdName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sbf = new StringBuffer("1=1");
		if (StringUtil.isNotNull(fdNo)) {
			sbf.append(" and fdNo = :fdNo");
			hqlInfo.setParameter("fdNo", fdNo);
		}
		if (StringUtil.isNotNull(fdName)) {
			sbf.append(" and fdName = :fdName");
			hqlInfo.setParameter("fdName", fdName);
		}
		hqlInfo.setWhereBlock(sbf.toString());
		List<HrOrganizationElement> list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public IBaseModel findById(String fdId) throws Exception {
		IBaseModel baseModel = null;
		HrOrganizationElement element = (HrOrganizationElement) this.findByPrimaryKey(fdId, HrOrganizationElement.class, true);
		if (element != null) {
		switch (element.getFdOrgType()) {
		case HrOrgConstant.HR_TYPE_ORG:
			baseModel = getHrOrganizationOrgService().findByPrimaryKey(fdId);
			break;
		case HrOrgConstant.HR_TYPE_DEPT:
			baseModel = getHrOrganizationDeptService().findByPrimaryKey(fdId);
			break;
		case HrOrgConstant.HR_TYPE_POST:
			baseModel = getHrOrganizationPostService().findByPrimaryKey(fdId);
			break;
		case HrOrgConstant.HR_TYPE_PERSON:
			baseModel = getHrStaffPersonInfoService().findByPrimaryKey(fdId);
			break;
		default:
			baseModel = element;
			break;
		}
		}
		return baseModel;
	}
	/**
	 * 根据SQL语句查询单个ID的值
	 * @param sql
	 * @return
	 */
	@Override
	public List<String> getIdByJdbc(String sql,List<Object> params) {
		Connection conn = com.landray.kmss.sys.hibernate.spi.ConnectionWrapper.getInstance().getConnection(getBaseDao().openSession());
		List<String> fdIds=new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			if(params !=null) {
				int i=1;
				for (Object param : params) {
					if(param !=null) {
						if(param instanceof String) {
							ps.setString(i, String.valueOf(param));
						}else if(param instanceof Integer) {
							ps.setInt(i, Integer.parseInt(param.toString()));
						}else if(param instanceof java.util.Date) {
							ps.setTimestamp(i, new java.sql.Timestamp(((java.util.Date)param).getTime()));
						}else if(param instanceof Boolean) {
							ps.setBoolean(i, (Boolean)param);
						}
					}
					i++;
				}
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				fdIds.add(rs.getString(1));
			}
		} catch (SQLException ex) {
			logger.error("查询组织架构的ID串失败：" + ex);
		} finally {
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(ps);
			JdbcUtils.closeConnection(conn);
		}
		return fdIds;
	}
	/**
	 * 更新组织的类型
	 * @param fdId
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateHrOrgType(String fdId, Integer fdOrgType) throws Exception {
		try {
			//修改为对应的组织类型
			String sql = "update hr_org_element set fd_org_type=:orgType where fd_id=:fdId";
			getBaseDao().getHibernateSession().createSQLQuery(sql).setParameter("orgType",fdOrgType).setParameter("fdId", fdId).executeUpdate();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("更新人事组织架构-类型失败", e);
		}
		return true;
	}

	/**
	 * 离职用户
	 * 根据用户ID，设置组织架构中该人员是领导的数据设置为空
	 * @param userFdId
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateHrOrgLeader(String userFdId) throws Exception {

		//修改当前领导是自己的
		String sql = "update hr_org_element set fd_this_leaderid=null where fd_this_leaderid=:fdId ";
		NativeQuery thisLeaderNativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("fdId", userFdId);
		thisLeaderNativeQuery.addSynchronizedQuerySpace("hr_org_element");
		thisLeaderNativeQuery.executeUpdate();

		sql = "update hr_org_element set fd_bran_leader=null where fd_bran_leader=:fdId ";
		NativeQuery branLeaderNativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("fdId", userFdId);
		branLeaderNativeQuery.addSynchronizedQuerySpace("hr_org_element");
		branLeaderNativeQuery.executeUpdate();

		sql = "update hr_org_element set fd_super_leaderid=null where fd_super_leaderid=:fdId ";
		NativeQuery superLeaderNativeQuery = getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("fdId", userFdId);
		superLeaderNativeQuery.addSynchronizedQuerySpace("hr_org_element");
		superLeaderNativeQuery.executeUpdate();
	}
}
