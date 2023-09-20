package com.landray.kmss.hr.staff.service.spring;

import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.landray.kmss.hr.staff.util.*;
import com.landray.kmss.util.excel.ExcelOutput;
import com.landray.kmss.util.excel.ExcelOutputImp;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.ICheckUniqueBean;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.hr.function.HrFunctions;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationCompilingSum;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.organization.service.spring.HrOrganizationElementServiceImp;
import com.landray.kmss.hr.staff.event.HrStaffPersonInfoEvent;
import com.landray.kmss.hr.staff.forms.HrStaffPersonInfoForm;
import com.landray.kmss.hr.staff.model.HrStaffAlertWarningBirthday;
import com.landray.kmss.hr.staff.model.HrStaffAlertWarningTrial;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.hr.staff.model.HrStaffEntry;
import com.landray.kmss.hr.staff.model.HrStaffMoveRecord;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoLog;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffSyncLeaveConfig;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.report.HrCurrencyParams;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.service.IHrStaffEntryService;
import com.landray.kmss.hr.staff.service.IHrStaffMoveRecordService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBonusMalusService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceBriefService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceEducationService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceProjectService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceTrainingService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceWorkService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoLogService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoSettingNewService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.sys.authentication.user.KMSSUser;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.dict.SysDictExtendDynamicProperty;
import com.landray.kmss.sys.organization.event.SysOrgElementEffectivedEvent;
import com.landray.kmss.sys.organization.event.SysOrgElementInvalidatedEvent;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.organization.model.SysOrganizationConfig;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.organization.service.ISysOrgPostService;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.sys.organization.util.PasswordUtil;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.tag.forms.SysTagMainForm;
import com.landray.kmss.sys.tag.model.SysTagMain;
import com.landray.kmss.sys.tag.model.SysTagMainRelation;
import com.landray.kmss.sys.tag.service.ISysTagMainService;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.sys.zone.service.ISysZoneDocCountGetter;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.AutoHashMap;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.WorkBook;
import com.sunbor.web.tag.Page;
import com.sunbor.web.tag.enums.Type;
import com.sunbor.web.tag.enums.ValueLabel;
import com.landray.kmss.hr.function.HrFunctions;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 员工信息
 *
 * @author 潘永辉 2016-12-27
 */
public class HrStaffPersonInfoServiceImp extends HrOrganizationElementServiceImp
		implements IHrStaffPersonInfoService, ICheckUniqueBean, ApplicationListener {
	private static boolean locked = false;
	private SysQuartzJobContext jobContext = null;
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private IHrStaffEntryService hrStaffEntryService;
	private IHrStaffPersonInfoLogService hrStaffPersonInfoLogService;
	private ISysOrgPersonService sysOrgPersonService;
	private ISysOrgCoreService sysOrgCoreService;
	private IHrStaffTrackRecordService hrStaffTrackRecordService;
	private IHrStaffMoveRecordService hrStaffMoveRecordService;
	private IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService;
	private IHrOrganizationElementService hrOrganizationElementService;
	private IHrOrganizationRankService hrOrganizationRankService;
	private ISysOrgElementService sysOrgElementService;
    private IHrOrganizationPostService hrOrganizationPostService;
    private HrStaffPersonInfoCustomizeDataSource hrStaffPersonInfoCustomizeDataSource;
    private static SysOrgElement oldFdParent;
    private static SysOrgElement oldLeader;
    private static SysOrgElement newLeader;
    private static HrOrganizationRank oldFdOrgRank;
    private static SysOrgElement oldFdFirstLevelDepartment;
    private static SysOrgElement oldFdSecondLevelDepartment;
    private static SysOrgElement oldFdThirdLevelDepartment;
    private static List<SysOrgPost> oldFdOrgPost;
    private static List<SysOrgPost> OrgPosts1 = new ArrayList<SysOrgPost>();
    private static List<SysOrgPost> OrgPosts2 = new ArrayList<SysOrgPost>();
    private static Integer anquanjibie=0;
	public ISysOrgElementService getSysOrgElementService() {
		if (sysOrgElementService == null) {
			sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
		}
		return sysOrgElementService;
	}

	public void setSysOrgElementService(ISysOrgElementService sysOrgElementService) {
		this.sysOrgElementService = sysOrgElementService;
	}

	public void setHrStaffPersonInfoCustomizeDataSource(HrStaffPersonInfoCustomizeDataSource hrStaffPersonInfoCustomizeDataSource){
		this.hrStaffPersonInfoCustomizeDataSource = hrStaffPersonInfoCustomizeDataSource;
	}

	@Override
	public Page findPage(HQLInfo hqlInfo) throws Exception {
		Page page = getBaseDao().findPage(hqlInfo);

		List<HrStaffPersonInfo> list = page.getList();
		for (HrStaffPersonInfo hrStaffPersonInfo : list) {
			Date entryTime = hrStaffPersonInfo.getFdEntryTime();
			Date workTime = hrStaffPersonInfo.getFdWorkTime();
			if (entryTime != null) {
				int diffMonths = getDiffMonths(entryTime);
				int diffYear = diffMonths / 12;
				int diffMonth = diffMonths % 12;

				hrStaffPersonInfo.setFdEnterpriseAge(diffYear + "年" + diffMonth + '月');
			}
			if (workTime != null) {
				int diffMonths = getDiffMonths(workTime);
				int diffYear = diffMonths / 12;
				int diffMonth = diffMonths % 12;

				hrStaffPersonInfo.setFdStaffAge((diffYear + "年" + diffMonth + '月'));
			}
		}

		return page;
	}

	public int getDiffMonths(Date entryTime) {
		int diffMonths = 0;
		int year, month, day;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(entryTime);
		year = cal1.get(Calendar.YEAR);
		month = cal1.get(Calendar.MONTH) + 1;
		day = cal1.get(Calendar.DAY_OF_MONTH);
		Date now = new Date();
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(now);
		int nowYear = cal2.get(Calendar.YEAR);
		int nowMonth = cal2.get(Calendar.MONTH) + 1;
		int nowDay = cal2.get(Calendar.DAY_OF_MONTH);
		if (nowYear > year) {
			diffMonths = (nowYear - year) * 12 - month;
			if (nowDay >= day) {
				diffMonths += nowMonth;
			} else {
				diffMonths += nowMonth - 1;
			}
		} else if (nowYear == year) {
			if (nowMonth > month) {
				if (nowDay >= day) {
					diffMonths += nowMonth - month;
				} else {
					diffMonths += nowMonth - month - 1;
				}
			}
		}

		return diffMonths;
	}

	public IHrOrganizationRankService getHrOrganizationRankService() {
		if (hrOrganizationRankService == null) {
			hrOrganizationRankService = (IHrOrganizationRankService) SpringBeanUtil
					.getBean("hrOrganizationRankService");
		}
		return hrOrganizationRankService;
	}

	public IHrOrganizationElementService getHrOrganizationElementService() {
		if (hrOrganizationElementService == null) {
			hrOrganizationElementService = (IHrOrganizationElementService) SpringBeanUtil
					.getBean("hrOrganizationElementService");
		}
		return hrOrganizationElementService;
	}

	public void setHrStaffPersonExperienceContractService(
			IHrStaffPersonExperienceContractService hrStaffPersonExperienceContractService) {
		this.hrStaffPersonExperienceContractService = hrStaffPersonExperienceContractService;
	}

	private IHrStaffEntryService getHrStaffEntryServiceImp() {
		if (hrStaffEntryService == null) {
			hrStaffEntryService = (IHrStaffEntryService) SpringBeanUtil.getBean("hrStaffEntryService");
		}
		return hrStaffEntryService;
	}

	@Override
	public IHrStaffTrackRecordService getHrStaffTrackRecordService() {
		return hrStaffTrackRecordService;
	}
	
	public IHrStaffMoveRecordService getHrStaffMoveRecordService() {
		return hrStaffMoveRecordService;
	}
	public void setHrStaffTrackRecordService(IHrStaffTrackRecordService hrStaffTrackRecordService) {
		this.hrStaffTrackRecordService = hrStaffTrackRecordService;
	}

	public void setHrStaffMoveRecordService(IHrStaffMoveRecordService hrStaffMoveRecordService) {
		this.hrStaffMoveRecordService = hrStaffMoveRecordService;
	}

	public void setHrStaffPersonInfoLogService(IHrStaffPersonInfoLogService hrStaffPersonInfoLogService) {
		this.hrStaffPersonInfoLogService = hrStaffPersonInfoLogService;
	}

	public void setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;

	public IHrStaffEmolumentWelfareDetaliedService getHrStaffEmolumentWelfareDetaliedService() {
		if (hrStaffEmolumentWelfareDetaliedService == null) {
			hrStaffEmolumentWelfareDetaliedService = (IHrStaffEmolumentWelfareDetaliedService) SpringBeanUtil
					.getBean("hrStaffEmolumentWelfareDetaliedService");
		}
		return hrStaffEmolumentWelfareDetaliedService;
	}

	public ISysOrgPostService sysOrgPostService;

	public void setSysOrgPostService(ISysOrgPostService sysOrgPostService) {
		this.sysOrgPostService = sysOrgPostService;
	}

	private ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	private IHrStaffPersonInfoSettingNewService hrStaffPersonInfoSettingNewService;

	public IHrStaffPersonInfoSettingNewService getHrStaffPersonInfoSettingNewServiceImp() {
		if (hrStaffPersonInfoSettingNewService == null) {
			hrStaffPersonInfoSettingNewService = (IHrStaffPersonInfoSettingNewService) SpringBeanUtil
					.getBean("hrStaffPersonInfoSetNewService");
		}
		return hrStaffPersonInfoSettingNewService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) modelObj;
		String fdId = super.add(modelObj);
		// 添加任职记录
//		addTrackRecord(personInfo);
//		addContract(personInfo);
		/**
		 * 2021-07-16 屏蔽人员档案修改后记录到系统组织架构中 //添加系统日志
		 * if(personInfo.getRequestContext() !=null){
		 * addSysOrgMdifyLog(personInfo, personInfo.getRequestContext()); }else
		 * { addSysOrgMdifyLog(personInfo, new
		 * RequestContext(Plugin.currentRequest())); }
		 */
		return fdId;
	}
	private void addContract(HrStaffPersonInfo personInfo) throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		HrStaffPersonExperienceContract modelObj = null;
		modelObj = (HrStaffPersonExperienceContract) createContract(personInfo);

		hrStaffPersonExperienceContractService.add(modelObj);
//		if (null != personInfo.getFdParent() && "true".equals(syncSetting.getHrToEkpEnable())) {
//			modelObj = createContract(personInfo);
////			modelObj.setFdHrOrgDept(personInfo.getFdParent());
//			if (!ArrayUtil.isEmpty(personInfo.getFdPosts())) {
//				IBaseModel postModel = (IBaseModel) personInfo.getFdPosts().get(0);
//				HrOrganizationElement hrPost = this.findOrgById(postModel.getFdId());
////				modelObj.setFdHrOrgPost((HrOrganizationPost) hrPost);
//			}
//			hrStaffPersonExperienceContractService.add(modelObj);
//		} else if (personInfo.getFdOrgParent() != null && "true".equals(syncSetting.getHrToEkpEnable())) {
//			modelObj = (HrStaffPersonExperienceContract) createContract(personInfo);
////			modelObj.setFdHrOrgDept(this.findOrgById(personInfo.getFdOrgParent().getFdId()));
//			if (!ArrayUtil.isEmpty(personInfo.getFdOrgPosts())) {
//				HrOrganizationElement hrPost = null;
//				for (SysOrgElement post : personInfo.getFdOrgPosts()) {
//					hrPost = this.findOrgById(post.getFdId());
//				}
//				if (null != hrPost) {
////					modelObj.setFdHrOrgPost((HrOrganizationPost) hrPost);
//				}
//			}
//			hrStaffPersonExperienceContractService.add(modelObj);
//			// 人员添加部门、岗位
////			personInfo.setFdParent(modelObj.getFdHrOrgDept());
//			List fdPosts = new ArrayList<HrOrganizationPost>();
////			fdPosts.add(modelObj.getFdHrOrgPost());
//			personInfo.setFdPosts(fdPosts);
//		} else {
//			if (personInfo.getFdOrgParent() != null) {
//				modelObj = createContract(personInfo);
////				modelObj.setFdRatifyDept(personInfo.getFdOrgParent());
//				List posts = new ArrayList();
//				List<IBaseModel> fdPosts = personInfo.getFdPosts();
//				if (!ArrayUtil.isEmpty(fdPosts)) {
//					for (IBaseModel post : fdPosts) {
//						SysOrgElement element = sysOrgCoreService.findByPrimaryKey(post.getFdId(), null, true);
//						if (element != null) {
//							posts.add(element);
//						}
//					}
//				}
////				modelObj.setFdOrgPosts(posts);
//				hrStaffPersonExperienceContractService.add(modelObj);
//			}
//		}
	}
	private void addTrackRecord(HrStaffPersonInfo personInfo) throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		HrStaffTrackRecord modelObj = null;

		if (null != personInfo.getFdParent() && "true".equals(syncSetting.getHrToEkpEnable())) {
			modelObj = createTrackRecord(personInfo);
			modelObj.setFdHrOrgDept(personInfo.getFdParent());
			if (!ArrayUtil.isEmpty(personInfo.getFdPosts())&&personInfo.getFdPosts().get(0)!=null) {
				Object obj = personInfo.getFdPosts();
				IBaseModel postModel = (IBaseModel) personInfo.getFdPosts().get(0);
				HrOrganizationElement hrPost = this.findOrgById(postModel.getFdId());
				modelObj.setFdHrOrgPost((HrOrganizationPost) hrPost);
			}
			hrStaffTrackRecordService.add(modelObj);
		} else if (personInfo.getFdOrgParent() != null && "true".equals(syncSetting.getHrToEkpEnable())) {
			modelObj = createTrackRecord(personInfo);
			modelObj.setFdHrOrgDept(this.findOrgById(personInfo.getFdOrgParent().getFdId()));
			if (!ArrayUtil.isEmpty(personInfo.getFdOrgPosts())) {
				HrOrganizationElement hrPost = null;
				for (SysOrgElement post : personInfo.getFdOrgPosts()) {
					hrPost = this.findOrgById(post.getFdId());
				}
				if (null != hrPost) {
					modelObj.setFdHrOrgPost((HrOrganizationPost) hrPost);
				}
			}
			hrStaffTrackRecordService.add(modelObj);
			// 人员添加部门、岗位
			personInfo.setFdParent(modelObj.getFdHrOrgDept());
			List fdPosts = new ArrayList<HrOrganizationPost>();
			fdPosts.add(modelObj.getFdHrOrgPost());
			personInfo.setFdPosts(fdPosts);
		} else {
			if (personInfo.getFdOrgParent() != null) {
				modelObj = createTrackRecord(personInfo);
				modelObj.setFdRatifyDept(personInfo.getFdOrgParent());
				List posts = new ArrayList();
				List<IBaseModel> fdPosts = personInfo.getFdPosts();
				if (!ArrayUtil.isEmpty(fdPosts)) {
					for (IBaseModel post : fdPosts) {
						SysOrgElement element = sysOrgCoreService.findByPrimaryKey(post.getFdId(), null, true);
						if (element != null) {
							posts.add(element);
						}
					}
				}
				modelObj.setFdOrgPosts(posts);
				hrStaffTrackRecordService.add(modelObj);
			}
		}
	}
	private void addMoveRecord(HrStaffPersonInfo oldPersonInfo,HrStaffPersonInfo personInfo) throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		HrStaffMoveRecord modelObj = null;
		modelObj = createMoveRecord(oldPersonInfo,personInfo);
		hrStaffMoveRecordService.add1(modelObj);
//		if (null != personInfo.getFdParent() && "true".equals(syncSetting.getHrToEkpEnable())) {
//			modelObj = createMoveRecord(oldPersonInfo,personInfo);
////			modelObj.setFdHrOrgDept(personInfo.getFdParent());
////			if (!ArrayUtil.isEmpty(personInfo.getFdPosts())) {
////				IBaseModel postModel = (IBaseModel) personInfo.getFdPosts().get(0);
////				HrOrganizationElement hrPost = this.findOrgById(postModel.getFdId());
//////				modelObj.setFdHrOrgPost((HrOrganizationPost) hrPost);
////			}
//			hrStaffMoveRecordService.add1(modelObj);
//		} else if (personInfo.getFdOrgParent() != null && "true".equals(syncSetting.getHrToEkpEnable())) {
//			modelObj = createMoveRecord(oldPersonInfo,personInfo);
////			modelObj.setFdHrOrgDept(this.findOrgById(personInfo.getFdOrgParent().getFdId()));
////			if (!ArrayUtil.isEmpty(personInfo.getFdOrgPosts())) {
////				HrOrganizationElement hrPost = null;
////				for (SysOrgElement post : personInfo.getFdOrgPosts()) {
////					hrPost = this.findOrgById(post.getFdId());
////				}
////				if (null != hrPost) {
//////					modelObj.setFdHrOrgPost((HrOrganizationPost) hrPost);
////				}
////			}
//			hrStaffMoveRecordService.add1(modelObj);
//			// 人员添加部门、岗位
////			personInfo.setFdParent(modelObj.getFdHrOrgDept());
////			List fdPosts = new ArrayList<HrOrganizationPost>();
////			fdPosts.add(modelObj.getFdHrOrgPost());
////			personInfo.setFdPosts(fdPosts);
//		} else {
//			if (personInfo.getFdOrgParent() != null) {
//				modelObj = createMoveRecord(oldPersonInfo,personInfo);
////				modelObj.setFdRatifyDept(personInfo.getFdOrgParent());
////				List posts = new ArrayList();
////				List<IBaseModel> fdPosts = personInfo.getFdPosts();
////				if (!ArrayUtil.isEmpty(fdPosts)) {
////					for (IBaseModel post : fdPosts) {
////						SysOrgElement element = sysOrgCoreService.findByPrimaryKey(post.getFdId(), null, true);
////						if (element != null) {
////							posts.add(element);
////						}
////					}
////				}
////				modelObj.setFdOrgPosts(posts);
//				hrStaffMoveRecordService.add1(modelObj);
//			}
//		}
	}
	private HrStaffTrackRecord createTrackRecord(HrStaffPersonInfo personInfo) {
		HrStaffTrackRecord modelObj = new HrStaffTrackRecord();
		Date currDate = new Date();
		modelObj.setFdPersonInfo(personInfo);
		modelObj.setFdEntranceBeginDate(currDate);
		modelObj.setFdStaffingLevel(personInfo.getFdStaffingLevel());
		modelObj.setFdType("1");
		modelObj.setFdStatus("1");
		modelObj.setFdCreateTime(currDate);
		return modelObj;
	}
	private HrStaffMoveRecord createMoveRecord(HrStaffPersonInfo oldPersonInfo,HrStaffPersonInfo personInfo) {
		HrStaffMoveRecord modelObj = new HrStaffMoveRecord();
		modelObj.setFdMoveType("1");
		modelObj.setFdAfterDept(personInfo.getFdOrgParent());
		modelObj.setFdBeforeDept(oldFdParent);
		modelObj.setFdAfterFirstDeptName(personInfo.getFdFirstLevelDepartment()!=null?personInfo.getFdFirstLevelDepartment().getFdName():"");
		modelObj.setFdAfterLeader(newLeader);
		modelObj.setFdAfterPosts(OrgPosts1);
		modelObj.setFdAfterRank(personInfo.getFdOrgRank()!=null?personInfo.getFdOrgRank().getFdName():"");
		modelObj.setFdAfterSecondDeptName(personInfo.getFdSecondLevelDepartment()!=null?personInfo.getFdSecondLevelDepartment().getFdName():"");
		modelObj.setFdAfterThirdDeptName(personInfo.getFdThirdLevelDepartment()!=null?personInfo.getFdThirdLevelDepartment().getFdName():"");
		modelObj.setFdBeforeFirstDeptName(oldFdFirstLevelDepartment!=null?oldFdFirstLevelDepartment.getFdName():"");
		modelObj.setFdBeforeLeader(oldLeader);
		modelObj.setFdBeforePosts(OrgPosts2);
		modelObj.setFdBeforeRank(oldFdOrgRank!=null?oldFdOrgRank.getFdName():"");
		modelObj.setFdBeforeSecondDeptName(oldFdSecondLevelDepartment!=null?oldFdSecondLevelDepartment.getFdName():"");
		modelObj.setFdBeforeThirdDeptName(oldFdThirdLevelDepartment!=null?oldFdThirdLevelDepartment.getFdName():"");
		Date currDate = new Date();
		modelObj.setFdMoveDate(currDate);
		if(personInfo.getFdFirstLevelDepartment()!=null  && oldFdFirstLevelDepartment!=null && personInfo.getFdFirstLevelDepartment().getFdName().equals(oldFdFirstLevelDepartment.getFdName())){
			modelObj.setFdTransDept("0");
		} else {
			modelObj.setFdTransDept("1");
		}
		modelObj.setFdPersonInfo(personInfo);
		modelObj.setFdStaffName(personInfo.getFdName());
		modelObj.setFdStaffNumber(personInfo.getFdStaffNo());
		modelObj.setFdFlag("1");
		
		return modelObj;
	}
	private HrStaffPersonExperienceContract createContract(HrStaffPersonInfo personInfo) {
		HrStaffPersonExperienceContract modelObj = new HrStaffPersonExperienceContract();
		Date currDate = new Date();
		modelObj.setFdPersonInfo(personInfo);
		modelObj.setFdContractYear(personInfo.getFdContractYear());
		modelObj.setFdContractMonth(personInfo.getFdContractMonth());
		modelObj.setFdContType(personInfo.getFdContType());
		modelObj.setFdBeginDate(personInfo.getFdBeginDate());
		modelObj.setFdEndDate(personInfo.getFdEndDate());
		modelObj.setFdContractUnit(personInfo.getFdContractUnit());
		if(personInfo.getFdBeginDate()!=null && personInfo.getFdEndDate()!=null){
		modelObj.setFdContractYear(getDiffMonths1(personInfo.getFdBeginDate(),personInfo.getFdEndDate())/12);
		modelObj.setFdContractMonth(getDiffMonths1(personInfo.getFdBeginDate(),personInfo.getFdEndDate())%12);
		}
		return modelObj;
	}
	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		// 获取数据库旧数据
		HrStaffPersonInfo oldPersonInfo = (HrStaffPersonInfo) findByPrimaryKey(form.getFdId(), HrStaffPersonInfo.class,
				true);

		HrStaffPersonInfo newPersonInfo = (HrStaffPersonInfo) super.convertFormToModel(form, null, requestContext);
		
		HrStaffPersonInfoForm oldPersonInfoForm = null;
		if (oldPersonInfo != null) {
			oldFdFirstLevelDepartment=oldPersonInfo.getFdFirstLevelDepartment();
			oldFdSecondLevelDepartment=oldPersonInfo.getFdSecondLevelDepartment();
			oldFdThirdLevelDepartment=oldPersonInfo.getFdThirdLevelDepartment();
			OrgPosts2 = oldPersonInfo.getFdPosts();
//			for(SysOrgPost sysOrgPost : oldPersonInfo.getFdPosts())
//				OrgPosts2.add(sysOrgPost);
			oldFdOrgPost = oldPersonInfo.getFdPosts();
			oldFdOrgRank = oldPersonInfo.getFdOrgRank();
			oldLeader = oldPersonInfo.getFdReportLeader();
			oldFdParent=oldPersonInfo.getFdOrgParent();
			oldPersonInfoForm = (HrStaffPersonInfoForm) super.cloneModelToForm(null, oldPersonInfo, requestContext);
		}else{
			oldFdFirstLevelDepartment=newPersonInfo.getFdFirstLevelDepartment();
			oldFdSecondLevelDepartment=newPersonInfo.getFdSecondLevelDepartment();
			oldFdThirdLevelDepartment=newPersonInfo.getFdThirdLevelDepartment();
			OrgPosts2 = newPersonInfo.getFdPosts();
//			for(SysOrgPost sysOrgPost : oldPersonInfo.getFdPosts())
//				OrgPosts2.add(sysOrgPost);
			oldFdOrgPost = newPersonInfo.getFdPosts();
			oldFdOrgRank = newPersonInfo.getFdOrgRank();
			oldLeader = newPersonInfo.getFdReportLeader();
			oldFdParent=newPersonInfo.getFdOrgParent();
		}

		OrgPosts1 = newPersonInfo.getFdPosts();
		newLeader = newPersonInfo.getFdReportLeader();
		
//		for(SysOrgPost sysOrgPost : newPersonInfo.getFdOrgPosts())
//			OrgPosts1.add(sysOrgPost);
		// 构建日志信息
		HrStaffPersonInfoForm newPersonInfoForm = (HrStaffPersonInfoForm) form;
		// 组织架构人员
		SysOrgPerson fdOrgPerson = newPersonInfo.getFdOrgPerson();
		String fdLoginName = newPersonInfoForm.getFdLoginName();
		String fdNewPassword = newPersonInfoForm.getFdNewPassword();
		if (fdOrgPerson == null && StringUtil.isNotNull(fdLoginName) && StringUtil.isNotNull(fdNewPassword)) {
			String fdName = newPersonInfoForm.getFdName();
			String fdMobileNo = newPersonInfoForm.getFdMobileNo();
			SysOrgPerson person = new SysOrgPerson();
			person.setFdId(form.getFdId());
			person.setFdName(fdName);
			person.setFdLoginName(fdLoginName);
			person.setFdNewPassword(fdNewPassword);
			person.setFdMobileNo(fdMobileNo);
			person.setFdEmail(newPersonInfoForm.getFdEmail());
			person.setFdWorkPhone(newPersonInfoForm.getFdWorkPhone());
			person.setFdIsBusiness(Boolean.parseBoolean(newPersonInfoForm.getFdIsBusiness()));
			person.setFdCanLogin(newPersonInfoForm.getFdCanLogin());
			if (null != newPersonInfo.getFdOrgParent()) {
				person.setFdParent(newPersonInfo.getFdOrgParent());
			} else {
				String fdParentId = (null != newPersonInfo.getFdParent()) ? newPersonInfo.getFdParent().getFdId()
						: null;
				if (StringUtil.isNotNull(fdParentId)) {
					SysOrgElement fdParent = sysOrgCoreService.findByPrimaryKey(fdParentId, null, true);
					if (null != fdParent) {
						person.setFdParent(fdParent);
					}
				}
			}

			person.setFdSex(newPersonInfo.getFdSex());
			List<SysOrgPost> fdPosts = new ArrayList<SysOrgPost>();
			if (!ArrayUtil.isEmpty(newPersonInfo.getFdOrgPosts()) && newPersonInfo.getFdOrgPosts().size() > 0) {
				fdPosts.addAll(newPersonInfo.getFdOrgPosts());
				person.setFdPosts(fdPosts);
			} else {
				if (StringUtil.isNotNull(newPersonInfoForm.getFdPostIds())) {
					fdPosts.clear();
					String[] fdIds = newPersonInfoForm.getFdPostIds().split(";");
					for (int i = 0; i < fdIds.length; i++) {
						IBaseModel element = sysOrgPostService.findByPrimaryKey(fdIds[i], null, true);
						if (null != element) {
							fdPosts.add((SysOrgPost) element);
						}
					}
					if (!ArrayUtil.isEmpty(fdPosts)) {
						person.setFdPosts(fdPosts);
					}
				}
			}
			String fdNo = person.getFdNo();
			boolean noRequired = new SysOrganizationConfig().isNoRequired();
			if (StringUtil.isNull(fdNo) && noRequired) {
				String fdStaffNo = newPersonInfo.getFdStaffNo();
				fdNo = StringUtil.isNotNull(fdStaffNo) ? fdStaffNo
						: String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
				person.setFdNo(fdNo);
			}
			if (StringUtil.isNotNull(newPersonInfoForm.getFdStaffingLevelId())) {
				person.setFdStaffingLevel((SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
						.findByPrimaryKey(newPersonInfoForm.getFdStaffingLevelId()));
			}
			sysOrgPersonService.add(person);
			newPersonInfo.setFdOrgPerson(person);
		}
		if (fdOrgPerson != null) {
			SysOrgPerson orgPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(fdOrgPerson.getFdId());
			String newFdName = newPersonInfoForm.getFdName();
			if (StringUtil.isNotNull(newFdName)) {
				orgPerson.setFdName(newFdName);
			}
			String newFdSex = newPersonInfoForm.getFdSex();
			if (StringUtil.isNotNull(newFdSex)) {
				orgPerson.setFdSex(newFdSex);
			}
			List<SysOrgPost> fdPosts = orgPerson.getFdPosts();
			// if (fdPosts.size() > 0) {
			if (StringUtil.isNotNull(newPersonInfoForm.getFdOrgPostIds())) {
				fdPosts.clear();
				List<SysOrgPost> fdOrgPosts = sysOrgCoreService
						.findByPrimaryKeys(newPersonInfoForm.getFdOrgPostIds().split(";"));
				fdPosts.addAll(fdOrgPosts);
				orgPerson.setFdPosts(fdPosts);
			}
			// }
			// 职务处理
			if (StringUtil.isNotNull(newPersonInfoForm.getFdStaffingLevelId())) {
				SysOrganizationStaffingLevel fdStaffingLevel = (SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
						.findByPrimaryKey(newPersonInfoForm.getFdStaffingLevelId());
				orgPerson.setFdStaffingLevel(fdStaffingLevel);
			} else {
				orgPerson.setFdStaffingLevel(null);
			}
			if (StringUtil.isNotNull(fdLoginName)) {
				orgPerson.setFdLoginName(fdLoginName);
			}
			// 联系信息
			String newFdMobileNo = newPersonInfoForm.getFdMobileNo();
			if (StringUtil.isNotNull(newFdMobileNo)) {
				orgPerson.setFdMobileNo(newFdMobileNo);
			}
			String newFdEmail = newPersonInfoForm.getFdEmail();
			if (StringUtil.isNotNull(newFdEmail)) {
				orgPerson.setFdEmail(newFdEmail);
			}
			String newFdWorkPhone = newPersonInfoForm.getFdWorkPhone();
			if (StringUtil.isNotNull(newFdWorkPhone)) {
				orgPerson.setFdWorkPhone(newFdWorkPhone);
			}

			String newFdOrgParentId = newPersonInfoForm.getFdOrgParentId();
			if (StringUtil.isNotNull(newFdOrgParentId)) {
				SysOrgElement fdParent = sysOrgCoreService.findByPrimaryKey(newFdOrgParentId, null, true);
				orgPerson.setFdParent(fdParent);
			} else {
				String fdParentId = newPersonInfoForm.getFdParentId();
				orgPerson.setFdParent(sysOrgCoreService.findByPrimaryKey(fdParentId, null, true));
			}

			String newFdOrgParentOrgId = newPersonInfoForm.getFdOrgParentOrgId();
			if (StringUtil.isNotNull(newFdOrgParentOrgId)) {
				SysOrgElement fdParentOrg = sysOrgCoreService.findByPrimaryKey(newFdOrgParentOrgId);
				orgPerson.setHbmParentOrg(fdParentOrg);
			}
			orgPerson.setFdIsBusiness(Boolean.parseBoolean(newPersonInfoForm.getFdIsBusiness()));
			orgPerson.setFdCanLogin(newPersonInfoForm.getFdCanLogin());
			sysOrgPersonService.update(orgPerson);
		}
		if(newPersonInfo.getFdOrgParent()!=oldFdParent || (oldFdOrgRank!=newPersonInfo.getFdOrgRank()) ||(!getDiffrent(OrgPosts1,OrgPosts2)||oldLeader!=newLeader))
			addMoveRecord(oldPersonInfo,newPersonInfo);
		buildPersonInfoLog(oldPersonInfoForm, newPersonInfoForm, requestContext);
		updatePersonLeave(newPersonInfoForm);
		return newPersonInfo;
	}

	/**
	 * <p>
	 * 修改人事档案员工状态修改组织架构账号状态
	 * </p>
	 *
	 * @throws Exception
	 * @author sunj
	 */
	private void updatePersonLeave(HrStaffPersonInfoForm personInfoForm) throws Exception {
		SysOrgPerson person = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(personInfoForm.getFdOrgPersonId());
		if ("1".equals(personInfoForm.getFdAccountFlag())) {
			person.setFdIsAvailable(false);
			sysOrgPersonService.update(person);
		}
		if ("2".equals(personInfoForm.getFdAccountFlag())) {
			person.setFdIsAvailable(true);
			if (StringUtil.isNotNull(personInfoForm.getFdParentId())) {
				SysOrgElement orgElement = (SysOrgElement) sysOrgCoreService
						.findByPrimaryKey(personInfoForm.getFdParentId());
				person.setFdParent(orgElement);
			}
			if (StringUtil.isNotNull(personInfoForm.getFdPostIds())) {
				List posts = new ArrayList();
				String[] fdPosts = personInfoForm.getFdPostIds().split(";");
				for (String postId : fdPosts) {
					IBaseModel element = sysOrgCoreService.findByPrimaryKey(postId, null, true);
					if (null != element) {
						posts.add(element);
					}
				}
				person.setFdPosts(posts);
			}
			sysOrgPersonService.update(person);
		}
	}

	private void addSysOrgPerson() {

	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) modelObj;
		// 只能删除手动增加的数据
		// if (personInfo.getFdOrgPerson() == null) {{//edit by huyh
		// 手动以及无效的组织均能删除
		// 删除关联
		Session session = getBaseDao().getHibernateSession();
		// 日志

		session.createNativeQuery("delete from hr_staff_person_log_target where fd_personid = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		// 个人经历
		session.createNativeQuery("delete from hr_staff_person_exp_bm where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		session.createNativeQuery("delete from hr_staff_person_exp_brief where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		session.createNativeQuery("delete from hr_staff_person_exp_cont where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		session.createNativeQuery("delete from hr_staff_person_exp_educ where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		session.createNativeQuery("delete from hr_staff_person_exp_pro where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		session.createNativeQuery("delete from hr_staff_person_exp_qual where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		session.createNativeQuery("delete from hr_staff_person_exp_trai where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		session.createNativeQuery("delete from hr_staff_person_exp_work where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();
		session.createNativeQuery("delete from hr_staff_emolument_welfare where fd_person_info_id = ?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();

		session.createNativeQuery("delete from hr_staff_person_family where fd_person_info_id=?")
				.setParameter(0, personInfo.getFdId()).executeUpdate();

		List trackRecordList = hrStaffTrackRecordService
				.findList("hrStaffTrackRecord.fdPersonInfo.fdId = '" + personInfo.getFdId() + "'", "");
		for (java.util.Iterator itera = trackRecordList.iterator(); itera.hasNext();) {
			hrStaffTrackRecordService.delete((HrStaffTrackRecord) itera.next());
		}
		super.delete(personInfo);
		// 删除日志
		hrStaffPersonInfoLogService.savePersonInfoLog("delete", "删除“" + personInfo.getFdName() + "”的员工信息。");
		// }
	}

	private void buildPersonInfoLog(HrStaffPersonInfoForm oldForm, HrStaffPersonInfoForm newForm,
			RequestContext requestContext) throws Exception {
		String fdParaMethod = requestContext.getParameter("method");
		String fdDetails = null;
		if ("save".equalsIgnoreCase(fdParaMethod)) {
			fdDetails = "新增员工“" + newForm.getFdName() + "”。";
		} else {
			fdDetails = "修改了“" + oldForm.getFdName() + "”的员工信息：" + HrStaffPersonUtil.compare(oldForm, newForm);
		}
		HrStaffPersonInfo info = (HrStaffPersonInfo) findByPrimaryKey(newForm.getFdId());
		HrStaffPersonInfoLog log = hrStaffPersonInfoLogService.buildPersonInfoLog(fdParaMethod, fdDetails);
		if (info != null) {
			log.getFdTargets().add(info);
		}
		hrStaffPersonInfoLogService.add(log);
	}

	@Override
	public HSSFWorkbook buildTempletWorkBook(HttpServletRequest request) throws Exception {
		String fdResource = request.getParameter("fdResource");
		List<String> itemNodes = HrStaffImportUtil.getItemNode();
		// “员工状态”说明
		Type type = EnumerationTypeUtil.newInstance().getColumnEnums().findType("hrStaffPersonInfo_fdStatus");
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < type.getValueLabels().size(); i++) {
			ValueLabel valueLabel = (ValueLabel) type.getValueLabels().get(i);
			// 显示值
			String _bundle = valueLabel.getBundle();
			if (StringUtil.isNull(_bundle)) {
				_bundle = type.getBundle();
			}
			String label = ResourceUtil.getString(valueLabel.getLabelKey(), _bundle);
			buf.append(label).append(";");
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		itemNodes.add(
				ResourceUtil.getString("hr-staff:hrStaff.import.sheet2.item.hrStaffPerson.node1", null, null, buf));
		// “个人设置”类别说明
		String[] settingFields = { "fdStaffType", "fdNation", "fdPoliticalLandscape", "fdHighestEducation",
				"fdHighestDegree", "fdMaritalStatus", "fdHealth" };
		buf = new StringBuffer();
		for (String field : settingFields) {
			buf.append(ResourceUtil.getString("hr-staff:hrStaffPersonInfo." + field)).append(";");
		}
		if (buf.length() > 0) {
			buf.deleteCharAt(buf.length() - 1);
		}
		itemNodes.add(
				ResourceUtil.getString("hr-staff:hrStaff.import.sheet2.item.hrStaffPerson.node2", null, null, buf));
		itemNodes.add("当系统账号不为空的时候，组织编号为必填");
		itemNodes.add(ResourceUtil.getString("hr-staff:hrStaff.import.sheet2.item.node6"));
		if (StringUtil.isNotNull(fdResource)) {
			return HrStaffImportUtil.buildTempletWorkBook(getModelName(), getImportFields(true), null, itemNodes);
		} else {
			return HrStaffImportUtil.buildTempletWorkBook(getModelName(), getImportFields(false), null, itemNodes);
		}
	}

	@Override
	public KmssMessage saveImportData(HrStaffPersonInfoForm personInfoForm) throws Exception {
		Workbook wb = null;
		InputStream inputStream = null;
		try {
			inputStream = personInfoForm.getFile().getInputStream();
			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(inputStream);
			Sheet sheet = wb.getSheetAt(0);
			return saveImportData(sheet, personInfoForm);
		} catch (Exception e) {
			logger.error("hrStaffPersonInfoServiceImp.saveImportData.error", e);
			throw new RuntimeException(ResourceUtil.getString("hrStaff.import.error", "hr-staff"));

		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}
	}

	private AtomicInteger count = null;
	private Set<HrStaffPersonInfo> targets = null;
	private StringBuffer errorMsg = null;

	@Override
	public KmssMessage saveImportData(Sheet sheet, HrStaffPersonInfoForm personInfoForm) throws Exception {
		errorMsg = new StringBuffer();
		targets = new HashSet<HrStaffPersonInfo>();
		count = new AtomicInteger(0);
		// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
		int rowNum = sheet.getLastRowNum();
		if (rowNum < 1) {
			throw new RuntimeException(ResourceUtil.getString("hrStaff.import.empty", "hr-staff"));
		}
		boolean isOrg = StringUtil.isNotNull(personInfoForm.getFdSource())
				&& "com.landray.kmss.hr.organization".equals(personInfoForm.getFdSource());
		String[] importFields = getImportFields(isOrg);

		// 检查文件是否是下载的模板文件
		if (!checkFile(sheet, importFields)) {
			throw new RuntimeException(ResourceUtil.getString("hrStaff.import.errFile", "hr-staff"));
		}

		// 从第二行开始取数据
		List<Row> allRow = new ArrayList<Row>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row == null) { // 跳过空行
				continue;
			}
			allRow.add(row);
		}
		int rowsize = 1000;
		int allCount = allRow.size() % rowsize == 0 ? allRow.size() / rowsize : allRow.size() / rowsize + 1;
		CountDownLatch countDownLatch = new CountDownLatch(allCount);

		List<Row> tempRow = null;
		for (int i = 0; i < allCount; i++) {
			if (allRow.size() > rowsize * (i + 1)) {
				tempRow = allRow.subList(rowsize * i, rowsize * (i + 1));
			} else {
				tempRow = allRow.subList(rowsize * i, allRow.size());
			}
			personInfoForm.setRequestContext(new RequestContext(Plugin.currentRequest()));
			taskExecutor.execute(new ImportRunner(tempRow, personInfoForm, countDownLatch));
		}

		try {
			countDownLatch.await(3, TimeUnit.HOURS);
		} catch (InterruptedException exc) {
			logger.error("hrStaffPersonInfoServiceImp.saveImportData.countDownLatch.error", exc);
		}
		KmssMessage message = null;
		if (errorMsg.length() > 0) {
			errorMsg.insert(0,
					ResourceUtil.getString("hrStaff.import.portion.failed", "hr-staff", null, count) + "<br>");
			message = new KmssMessage(errorMsg.toString());
			message.setMessageType(KmssMessage.MESSAGE_ERROR);
		} else {
			message = new KmssMessage(ResourceUtil.getString("hrStaff.import.success", "hr-staff", null, count));
			message.setMessageType(KmssMessage.MESSAGE_COMMON);
		}
		// 记录导入日志
		buildLog(targets);
		return message;
	}

	class ImportRunner implements Runnable {
		private final List<Row> rows;

		private HrStaffPersonInfoForm personInfoForm;
		CountDownLatch countDownLatch;

		public ImportRunner(List<Row> rows, HrStaffPersonInfoForm personInfoForm, CountDownLatch countDownLatch) {
			this.rows = rows;
			this.personInfoForm = personInfoForm;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			logger.debug("启动线程：" + Thread.currentThread().getName());
			try {
				saveImportData(rows, personInfoForm);
			} catch (Exception e) {
				logger.error("hrStaffPersonInfoServiceImp.ImportRunner.run.error", e);
			} finally {
				countDownLatch.countDown();
				logger.debug("线程" + Thread.currentThread().getName() + "执行完成!");

			}
		}
	}
	  private List <SysOrgElement> findSysOrgElementByName(String name,String id) throws Exception {
	        HQLInfo info = new HQLInfo();
	        info.setWhereBlock("fdName=:fdName and fdOrgType=:fdOrgType and fdHierarchyId like :id");
	        info.setParameter("fdName",name);
	        info.setParameter("id","%"+id+"%");
	        info.setParameter("fdOrgType",SysOrgConstant.ORG_TYPE_DEPT);
	        List<SysOrgElement> sysOrgElements = getSysOrgElementService().findList(info);
          
	        return sysOrgElements;
	    }
	  private List <SysOrgElement> findSysOrgElementByName1(String name) throws Exception {
	        HQLInfo info = new HQLInfo();
	        info.setWhereBlock("fdName=:fdName and fdOrgType=:fdOrgType");
	        info.setParameter("fdName",name);
	        info.setParameter("fdOrgType",SysOrgConstant.ORG_TYPE_DEPT);
	        List<SysOrgElement> sysOrgElements = getSysOrgElementService().findList(info);
        
	        return sysOrgElements;
	    }
	  private Boolean findSysOrgElementById(String id1,String Id) throws Exception {
	        HQLInfo info = new HQLInfo();
	        
	        info.setWhereBlock("fdId=:id1 and fdHierarchyId like : Id");
	        info.setParameter("Id","%"+Id+"%");
	        info.setParameter("id1",id1);
	        info.setParameter("fdOrgType",SysOrgConstant.ORG_TYPE_DEPT);
	        List<SysOrgElement> sysOrgElements = getSysOrgElementService().findList(info);
        
	        if(sysOrgElements.size()==0)
	        return false;
	        else
	        	return true;
	    }
	private void saveImportData(List<Row> rows, HrStaffPersonInfoForm personInfoForm) throws Exception {
		Map<String, SysDictCommonProperty> map = SysDataDict.getInstance().getModel(getModelName()).getPropertyMap();
		boolean isOrg = StringUtil.isNotNull(personInfoForm.getFdSource())
				&& "com.landray.kmss.hr.organization".equals(personInfoForm.getFdSource());
		String[] importFields = getImportFields(isOrg);
		// 需要跳过前面几个列
		int skipColumn = 3;
		TransactionStatus status = null;
		Map<String, Integer> fdNoHaveMap = new HashMap<String, Integer>();
		
		SysOrgElement fdFirstLevelDepartment = null;
		SysOrgElement fdSecondLevelDepartment = null;
		SysOrgElement fdThirdLevelDepartment = null; 
		for (int i = 0; i < rows.size(); i++) {
			KmssMessages messages = new KmssMessages();
			try {
				Row row = rows.get(i);
				if (row == null) { // 跳过空行
					continue;
				}
				status = TransactionUtils.beginNewTransaction();

				// 非空判断
				HrStaffImportUtil.validateNotNullProperties(map, importFields, row, messages);

				// 获取列数
				int cellNum = row.getLastCellNum();
				// 是否是新建
				boolean isNew = false;
				// 组织机构人员
				SysOrgPerson orgPerson = null;
				HrStaffPersonInfo personInfo = null;
				// 姓名
				String fdName = ImportUtil.getCellValue(row.getCell(3));
				String fdLoginName = ImportUtil.getCellValue(row.getCell(1));
				String fdNo = ImportUtil.getCellValue(row.getCell(0));
				SysOrgPerson person = null;
				HrStaffTrackRecord newTrack = new HrStaffTrackRecord();
				// 有系统账号没有人事档案
				boolean sysOrgNoStaff = false;
				// 是否有关联的系统组织架构人员
				boolean hasOrgPerson = false;
				List<HrStaffPersonExperienceContract> newContractList = new ArrayList<HrStaffPersonExperienceContract>();
				HrStaffPersonExperienceContract newContract = new HrStaffPersonExperienceContract();

				SysOrgElement fdOrgParent = null;
				for (int j = 0; j < cellNum; j++) {
					Object value = null;
					/*
					 * if (j == 0) { fdNo =
					 * ImportUtil.getCellValue(row.getCell(j)); }
					 */
					if (j == 1) { // 第一列为登录账号，需要校验此账号是否存在
						value = HrStaffImportUtil.getCellValue(row.getCell(j));
						if (StringUtil.isNull((String) value)) {
							messages.addError(new KmssMessage(
									ResourceUtil.getString("hrStaff.import.error.notNull", "hr-staff", null,
											ResourceUtil.getString("hrStaffPersonInfo.fdLoginName", "hr-staff"))));
							continue;
						}
						List<SysOrgPerson> list = findByLoginName((String) value, fdNo);
						if (!list.isEmpty() && list.size() > 1) {
							messages.addError(new KmssMessage(ResourceUtil
									.getString("hrStaff.import.error.fdLoginName0", "hr-staff", null, value)));
						} else {
							// 系统账号跟行组合
							String key = String.format("%s_LoginName", value);
							Integer haveData = fdNoHaveMap.get(key);
							if (haveData == null) {
								fdNoHaveMap.put(key, i);
							} else if (haveData != i) {
								// 不是同一行，存在重复，提示错误
								messages.addError(new KmssMessage(ResourceUtil
										.getString("hrStaff.import.error.fdLoginName1", "hr-staff", null, value)));
								continue;
							}

							orgPerson = findLoginName(list);
							if (orgPerson != null) {
								person = orgPerson;
								person.setFdName(fdName);
								if (StringUtil.isNotNull(fdNo)) {
									person.setFdNo(fdNo);
								}
								person.setFdLoginName((String) value);
							}

							// 如果通过账号找到了人员，这里需要设置关联
							personInfo = findPersonInfoByLoginNameAndNo((String) value, fdNo);
							if (personInfo != null && orgPerson != null) {
								BeanUtils.setProperty(personInfo, "fdOrgPerson", orgPerson);
							} else {
								if (personInfo == null && orgPerson != null) {
									sysOrgNoStaff = true;
								}
							}
						}
					} else if (j == 2) {
						// 第二列为工号，如果通过登录账号没有找到人员，则需要再通过工号继续查找
						value = HrStaffImportUtil.getCellValue(row.getCell(j));
						if (StringUtil.isNull((String) value)) {
							messages.addError(new KmssMessage(ResourceUtil.getString("hrStaff.import.error.notNull",
									"hr-staff", null, ResourceUtil.getString("hrStaffEntry.fdStaffNo", "hr-staff"))));
							continue;
						}
						String key = String.format("%s_FdNo", value);
						Integer haveData = fdNoHaveMap.get(key);
						if (haveData == null) {
							fdNoHaveMap.put(key, i);
						} else if (haveData != i) {
							// 不是同一行，存在重复，提示错误
							messages.addError(new KmssMessage(ResourceUtil.getString("hrStaff.import.error.fdNoRepeat",
									"hr-staff", null, value)));
							continue;
						}
						// 如果通过账号没有找到人员，则需要通过工号查找
						if (personInfo == null) {
							personInfo = findPersonInfoByStaffNo((String) value);
						}
						// 如果还没有找到，那就是新增了
						if (personInfo == null) {
							personInfo = new HrStaffPersonInfo();
							isNew = true;
						} else {
							SysOrgPerson personSysOrg = personInfo.getFdOrgPerson();
							if (personSysOrg != null) {
								hasOrgPerson = true;
								if (person != null) {
									if (!person.getFdLoginName().equals(personSysOrg.getFdLoginName())) {
										messages.addError(new KmssMessage(ResourceUtil
												.getString("hrStaffPersonInfo.fdLoginName.notmatch", "hr-staff")));
									}
								} else {
									person = personSysOrg;
									person.setFdName(fdName);
									if (StringUtil.isNotNull(fdNo)) {
										person.setFdNo(fdNo);
									}
									person.setFdLoginName(fdLoginName);
								}
							} else {
								hasOrgPerson = false;
							}
						}

						// 设置工号，但是在设置前，要判断工号是否存在
						String result = checkStaffNoUnique(personInfo.getFdId(), (String) value);
//						if (StringUtil.isNotNull(result)) {
//							// 工号已在存在
//							messages.addError(new KmssMessage(ResourceUtil.getString(
//									"hrStaffPersonInfo.staffNo.unique.err", "hr-staff", null, new Object[] { value })));
//						} else {
							BeanUtils.setProperty(personInfo, "fdStaffNo", value);
//						}
						if(person==null){
							person = new SysOrgPerson();	
							person.setFdLoginName(fdLoginName);
							person.setFdNo(fdNo);
							person.setFdName(fdName);
						}
					}
					if (j > 2) { // 第三列以后才是导入数据部分
						int cellIndex = j - skipColumn;
						if (cellIndex > importFields.length - 1) {
							break;
						}

						if (personInfo == null) {
							personInfo = new HrStaffPersonInfo();
							isNew = true;
						}
						String fdField = importFields[cellIndex];
						SysDictCommonProperty property = map.get(fdField);
						String fdFieldLabel = ResourceUtil.getString(property.getMessageKey());
						try {
							value = ImportUtil.getCellValue(row.getCell(j), property, null);
							if (value != null) {
								if ("fdTrialOperationPeriod".equals(fdField)) {
									Pattern pattern = Pattern.compile("[0-9]*");
									if (!pattern.matcher(value.toString()).matches()) {
										messages.addError(new KmssMessage("[试用期限]应为数字类型!"));
									}
								}
								// 校验手机号码
								else if ("fdMobileNo".equals(fdField)) {
									value = ImportUtil.getCellValue(row.getCell(j), property, null);
									// // 国际号码
									// if (((String) value).startsWith("+")) {
									// String regex =
									// "^(\\+\\d{1,5})(\\d{6,11})";
									// if (((String) value).startsWith("+86")) {
									// regex = "^(\\+86)(\\d{11})";
									// }
									// Pattern pattern = Pattern.compile(regex);
									// if
									// (!pattern.matcher(value.toString()).matches())
									// {
									// messages.addError(new
									// KmssMessage("[手机号码]格式不正确!"));
									// }
									// } else {
									// // 国内号码
									// String regex_inner = "(\\d{11})";
									// Pattern pattern_inner =
									// Pattern.compile(regex_inner);
									// if
									// (!pattern_inner.matcher(value.toString()).matches())
									// {
									// messages.addError(new
									// KmssMessage("[手机号码]格式不正确!"));
									// }
									// }
									if (((String) value).indexOf("-") != -1) {
										// 判断是否有+
										if (((String) value).startsWith("+")) {
											String regex = "^(\\+\\d{1,5})(\\-)(\\d{6,11})";
											// +86是国内手机号，其他为+XX是不国内手机号
											if (((String) value).startsWith("+86")) {
												regex = "^(\\+86-1)(\\d{10})";
											}
											Pattern pattern = Pattern.compile(regex);
											if (!pattern.matcher(value.toString()).matches()) {
												messages.addError(new KmssMessage("[手机号码]格式不正确!"));
											}
										} else {
											String regex = "^(\\d{1,5})(\\-)(\\d{6,11})";
											// +86是国内手机号，其他为+XX是不国内手机号
											if (((String) value).startsWith("86")) {
												regex = "^(86-1)(\\d{10})";
											}
											Pattern pattern = Pattern.compile(regex);
											if (!pattern.matcher(value.toString()).matches()) {
												messages.addError(new KmssMessage("[手机号码]格式不正确!"));
											}
										}
									} else {
										if (((String) value).startsWith("+")) {
											String regex = "^(\\+\\d{1,5})(\\d{6,11})";
											// +86是国内手机号，其他为+XX是不国内手机号
											if (((String) value).startsWith("+86")) {
												regex = "^(\\+861)(\\d{10})";
											}
											Pattern pattern = Pattern.compile(regex);
											if (!pattern.matcher(value.toString()).matches()) {
												messages.addError(new KmssMessage("[手机号码]格式不正确!"));
											}
										} else {
											// 既没有+也没有-的手机号默认为国内手机号
											String regex = "^(1)(\\d{10})";
											if (((String) value).startsWith("86")) {
												regex = "^(861)(\\d{10})";
											}
											Pattern pattern = Pattern.compile(regex);
											if (!pattern.matcher(value.toString()).matches()) {
												messages.addError(new KmssMessage("[手机号码]格式不正确!"));
											}
										}
									}
								}
								// 根据身份证号自动填充出生日期
								if ("fdIdCard".equals(fdField)) {
									String sexcode = "";
									String birth = "";
									boolean flag = false;
									if (value.toString().length() == 15) {
										sexcode = value.toString().substring(14, 15);
										birth = "19" + value.toString().substring(6, 8) + "-"
												+ value.toString().substring(8, 10) + "-"
												+ value.toString().substring(10, 12);
										flag = true;
									}
									if (value.toString().length() == 18) {
										sexcode = value.toString().substring(16, 17);
										birth = value.toString().substring(6, 10) + "-"
												+ value.toString().substring(10, 12) + "-"
												+ value.toString().substring(12, 14);
										flag = true;
									}
									if (flag) {
										boolean date = HrStaffDateUtil.isDate(birth);
										if (date) {
											if (personInfo.getFdDateOfBirth() == null) {
												personInfo.setFdDateOfBirth(DateUtil.convertStringToDate(birth));
											}
										}
										// 偶数为女性，奇数为男性
										if (Integer.parseInt(sexcode) % 1 == 0) {
											personInfo.setFdSex("F");
										} else {
											personInfo.setFdSex("M");
										}
									}
								}
								if ("fdStatus".equals(fdField)) {
									if("在职人员".equals(value.toString()))
										value="onpost";
									else if("试用人员".equals(value.toString()))
										value="trial";
									else if("正式人员".equals(value.toString()))
										value="official";
									else if("返聘人员".equals(value.toString()))
										value="rehireAfterRetirement";
									else if("黑名单".equals(value.toString()))
										value="blacklist";
									else if("离职人员".equals(value.toString()))
										value="leave";
									else
										messages.addError(new KmssMessage(
												ResourceUtil.getString("hrStaff.import.error.notContain1", "hr-staff", null,
														value)));
								}
								if ("fdFirstLevelDepartment".equals(fdField)) {
									value = findSysOrgElementByName1(value.toString()).get(0);
									fdFirstLevelDepartment=(SysOrgElement) value;
									personInfo.setHbmParent((HrOrganizationElement) getHrOrganizationElementService().findByPrimaryKey(fdFirstLevelDepartment.getFdId()));;
									SysOrgElement thisLeader = fdFirstLevelDepartment.getHbmThisLeader();
									personInfo.setFdDepartmentHead(thisLeader);
									personInfo.setFdHeadOfFirstLevelDepartment(thisLeader);
									person.setFdParent((SysOrgElement)fdFirstLevelDepartment);
									}
								if ("fdSecondLevelDepartment".equals(fdField)) {
									if(value!=null)
									value = findSysOrgElementByName(value.toString(),fdFirstLevelDepartment.getFdId()).get(0);
									fdSecondLevelDepartment=(SysOrgElement) value;
									if(value==null)
										messages.addError(new KmssMessage(
												ResourceUtil.getString("hrStaff.import.error.notContain", "hr-staff", null,
														ResourceUtil.getString("hrStaffPersonInfo.fdSecondLevelDepartment", "hr-staff"))));
									
									else{
										personInfo.setHbmParent((HrOrganizationElement) getHrOrganizationElementService().findByPrimaryKey(fdSecondLevelDepartment.getFdId()));;
										SysOrgElement thisLeader = fdSecondLevelDepartment.getHbmThisLeader();
										personInfo.setFdDepartmentHead(thisLeader);
										person.setFdParent(fdSecondLevelDepartment);
									}
									}
								if ("fdThirdLevelDepartment".equals(fdField)) {
									if(value!=null)
									value = findSysOrgElementByName(value.toString(),fdSecondLevelDepartment.getFdId()).get(0);
									fdThirdLevelDepartment=(SysOrgElement) value;
									if(value==null)
										messages.addError(new KmssMessage(
												ResourceUtil.getString("hrStaff.import.error.notContain", "hr-staff", null,
														ResourceUtil.getString("hrStaffPersonInfo.fdThirdLevelDepartment", "hr-staff"))));
									else{
									personInfo.setHbmParent((HrOrganizationElement) getHrOrganizationElementService().findByPrimaryKey(fdThirdLevelDepartment.getFdId()));;
									SysOrgElement thisLeader = fdThirdLevelDepartment.getHbmThisLeader();
									personInfo.setFdDepartmentHead(thisLeader);
									person.setFdParent(fdThirdLevelDepartment);
									
								}
									}if ("fdIdCard".equals(fdField)) {
										if(value!=null)
											personInfo.setFdIdCard(value.toString());
										
									}if ("anquanjibie".equals(fdField)) {
										if(value!=null);
										anquanjibie=(Integer) value;

										person.getCustomPropMap().put("anquanjibie", anquanjibie);
//											person.setAnquanjibie(Integer.parseInt(value.toString()));
										
									}if ("fdPostalAddress".equals(fdField)) {
										if(value!=null)
											personInfo.setFdPostalAddress(value.toString());
										
									}if ("fdHomeAddress".equals(fdField)) {
										if(value!=null)
											personInfo.setFdHomeAddress(value.toString());
										
									}
								String dept="";
								if ("fdOrgParent".equals(fdField)) {
									if(fdThirdLevelDepartment!=null)
										dept=fdThirdLevelDepartment.getFdId();
								else if (fdSecondLevelDepartment!=null)
										dept=fdSecondLevelDepartment.getFdId();
							else 
								dept=fdFirstLevelDepartment.getFdId();
						}
						
								if (HrStaffImportUtil.checkValue(property, value, messages)) {
									if (property instanceof SysDictExtendDynamicProperty) {
										PropertyUtils.setProperty(personInfo,
												"customPropMap(" + property.getName() + ")", value);
									} else {
										// 设置系统组织为空，重新更新
										personInfo.setFdOrgPerson(null);

										buildHrOrgPersonInfo(fdField, value, personInfo, newTrack,newContractList,newContract, person, messages, dept);
									}
								}
							}
							if (!messages.hasError()) {
								// 添加日志信息
								if (UserOperHelper.allowLogOper("fileUpload", getModelName())) {
									UserOperContentHelper.putAdd(personInfo, fdField);
								}
							}
						} catch (Exception e) {
							logger.error("hrStaffPersonInfoServiceImp.saveImportData.hrStaff.import.error.other", e);
							messages.addError(new KmssMessage(ResourceUtil.getString("hrStaff.import.error.other",
									"hr-staff", null, new Object[] { fdFieldLabel, e.getMessage() })));
							// throw new Exception();
						}
					}
				}
				if (StringUtil.isNotNull(personInfoForm.getFdParentId())) {
					personInfo.setFdParent(super.findOrgById(personInfoForm.getFdParentId()));
					if (person != null) {
						person.setFdParent(sysOrgCoreService.findByPrimaryKey(personInfoForm.getFdParentId()));
					}

				}
				if (StringUtil.isNotNull(fdNo)) {
					if (null != person) {
						sysOrgPersonService.checkFdNo(person.getFdId(), person.getFdOrgType(), fdNo);

					}
				}
				// 如果有错误，就不进行导入
				if (!messages.hasError()) {
					if (null != person) {

						person.setFdHiredate(personInfo.getFdEntryTime());
						// personInfo.setFdId(person.getFdId());
						String staffStatus = personInfo.getFdStatus();
						if ("leave".equals(staffStatus) || "retire".equals(staffStatus)
								|| "dismissal".equals(staffStatus)) {
							person.setFdIsAvailable(false);
						} else {
							person.setFdIsAvailable(true);
						}
						if (StringUtil.isNotNull(fdNo)) {
							person.setFdNo(fdNo);
						}
						person.setFdNewPassword("123456");// 初始密码为1
						setOrgPersonProps(person, personInfo);
						person.setFdEmail(personInfo.getFdEmail());
						sysOrgPersonService.update(person);
						personInfo.setFdOrgPerson(person);
					} else {
						if (StringUtil.isNotNull(fdLoginName)) {
							// 来源是人事组织架构，则新增登录账号
							if (null == person) {
								person = new SysOrgPerson();
							}
							person.setFdName(personInfo.getFdName());
							if (StringUtil.isNotNull(fdNo)) {
								person.setFdNo(fdNo);
							}
							person.setFdHiredate(personInfo.getFdEntryTime());
							person.setFdLoginName(fdLoginName);
							person.setFdNewPassword("1");// 初始密码为1
							setOrgPersonProps(person, personInfo);
							person.setFdId(personInfo.getFdId());
							person.setFdParent(personInfo.getFdOrgParent());
							try {
								if (isNew) {
									sysOrgPersonService.add(person);
								} else {
									sysOrgPersonService.update(person);
								}
								personInfo.setFdOrgPerson(person);

							} catch (Exception e) {
								logger.error("hrStaffPersonInfoServiceImp.saveImportData.error", e);
								messages.addError(new KmssMessage(e.getMessage()));
							}

						}
					}
					if (null != personInfo && null == personInfo.getFdTimeOfEnterprise()) {
						personInfo.setFdTimeOfEnterprise(personInfo.getFdEntryTime());
					}
					personInfo.setRequestContext(personInfoForm.getRequestContext());
//					personInfo.setFdOrgPerson(person);
					if (isNew || sysOrgNoStaff) {
						if (sysOrgNoStaff) {
							// personInfo.setFdId(person.getFdId());
						}
						if (null != person) {
							personInfo.setFdId(person.getFdId());
						}
						this.add(personInfo);
						addContract(personInfo);
					} else {
						// addTrackRecord(newTrack, personInfo);
						this.update(personInfo);
					}
					try {
						PreparedStatement update = null;
						Connection conn = null;
						DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
						conn = dataSource.getConnection();
						conn.setAutoCommit(false);
						update = conn.prepareStatement(
								"update sys_org_person set anquanjibie=? "
										+ "where fd_id =?");
						update.setInt(1, anquanjibie);
						update.setString(2, person.getFdId());
						update.execute();
						conn.commit();
//						getSysAttendMainService().update(sysAttendMain);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					getHrOrganizationElementService().update(personInfo);
					count.incrementAndGet();
					targets.add(personInfo);
				} else {
					// errorMsg.append("<p class=error>");
					errorMsg.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i + 1));
					// 解析错误信息
					for (KmssMessage message : messages.getMessages()) {
						errorMsg.append(message.getMessageKey());
					}
					errorMsg.append("<br>");
				}
				TransactionUtils.getTransactionManager().commit(status);

			} catch (Exception e) {
				logger.error("hrStaffPersonInfoServiceImp.saveImportData.hrStaff.import.error.num", e);
				if (status != null) {
					TransactionUtils.getTransactionManager().rollback(status);
				}
				// errorMsg.append("<p class=error>");
				errorMsg.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i + 1));
				errorMsg.append(e.getMessage());
				errorMsg.append("<br>");
				messages.addError(new KmssMessage(e.getMessage()));
			}

		}
	}
	public int getDiffMonths1(Date begin,Date end){
		int diffMonths=0;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(begin);
		int year  = cal1.get(Calendar.YEAR);
		int month = cal1.get(Calendar.MONTH) + 1;
		int day = cal1.get(Calendar.DAY_OF_MONTH);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		int endYear  = cal2.get(Calendar.YEAR);
		int endMonth = cal2.get(Calendar.MONTH) + 1;
		int endDay = cal2.get(Calendar.DAY_OF_MONTH);
		if(endDay>=day){
			diffMonths=(endYear-year)*12+endMonth-month;
		}else{
			diffMonths=(endYear-year)*12+endMonth-month-1;
		}
		
		return diffMonths;
	}
	private void buildHrOrgPersonInfo(String fdField, Object value, HrStaffPersonInfo personInfo,
			HrStaffTrackRecord newTrack,List<HrStaffPersonExperienceContract> newContractList,HrStaffPersonExperienceContract newContract, SysOrgPerson person, KmssMessages messages, String dept) throws Exception {
		boolean isrun = true;

		if ("hbmParent".equals(fdField)) {
			HrOrganizationElement parentDept = HrStaffImportUtil.findDeptByPath(value.toString());
			if (parentDept != null) {
				if (parentDept.getFdOrgType() != HrOrgConstant.HR_TYPE_DEPT
						&& parentDept.getFdOrgType() != HrOrgConstant.HR_TYPE_ORG) {
					// 人事档案上级组织必须为部门
					messages.addError(new KmssMessage("人事档案上级组织必须为组织！"));
				}
			}

			// 来源人事组织架构
			String eleParentId = null;
			if (personInfo.getFdParent() != null) {
				eleParentId = personInfo.getFdParent().getFdId();
			}

			// 来源组织架构
			String staffParentId = null;
			if (personInfo.getFdOrgParent() != null) {
				staffParentId = personInfo.getFdOrgParent().getFdId();
			}

			if ("fdLeaveTime".equals(fdField)) {
				if ("leave".equals(personInfo.getFdStatus())) {
					PropertyUtils.setProperty(personInfo, "fdLeaveTime", value);
				}
			}
			if ("fdLeaveReason".equals(fdField)) {
				if ("leave".equals(personInfo.getFdStatus())) {
					PropertyUtils.setProperty(personInfo, "fdLeaveReason", value);
				}
			}
			if (parentDept != null) {
				if (parentDept.getFdId() != eleParentId && parentDept.getFdId() != staffParentId) {
					// newTrack.setFdOrgPerson(person);
					newTrack.setFdHrOrgDept(parentDept);
					// getHrStaffTrackRecordService().add(modelObj);
				}
			} else {
				messages.addError(new KmssMessage("上级组织不存在！"));
			}
			personInfo.setFdParent(parentDept);
			isrun = false;
		}
		if ("fdOrgPosts".equals(fdField)) {
			String sql = "select fd_id from sys_org_element where fd_name='" + value + "' and fd_parentid='"+person.getFdParent().getFdId()+"'";
			List listFdId = HrCurrencyParams.getValueBySql(sql, "fd_id");
			String fdId = (String) listFdId.get(0);
			List list = new ArrayList();
			 HrOrganizationPost post = (HrOrganizationPost) getHrOrganizationPostService().findByPrimaryKey(fdId, null, true);
             list.add(post);
			personInfo.setFdOrgPosts(list);
			personInfo.setHbmPosts(list);
			List<SysOrgPost> fdPosts = new ArrayList<SysOrgPost>();
				SysOrgPost orgPost = (SysOrgPost) sysOrgPostService.findByPrimaryKey(fdId);
				if (null != orgPost) {
					fdPosts.add(orgPost);
				}
			person.setFdPosts(fdPosts);
			isrun = false;
		}
		if ("hbmPosts".equals(fdField)) {
			List posts = getHrOrganizationPostService().findPostsByName((String) value);
			List elemPostList = personInfo.getFdOrgPosts();
			List staffPostList = personInfo.getFdOrgPosts();
			if (elemPostList != null && staffPostList != null && posts != null) {
				if (!ArrayUtil.isListSame(elemPostList, posts) && !ArrayUtil.isListSame(staffPostList, posts)) {
					newTrack.setFdHrOrgPost((HrOrganizationPost) posts.get(0));
				}
			}
			// 处理导入时无效岗位导致的岗位丢失和岗位重复问题：对Excel导入进来的岗位进行过滤，如果不是Excel导入部门下的岗位，则自动过滤掉
			// by徐斌 -2020-08-06
			List<HrOrganizationPost> hrPosts = new ArrayList<HrOrganizationPost>();
			if (null != posts && posts.size() > 0) {
				for (Object object : posts) {
					if (null == object) {
						continue;
					}
					HrOrganizationPost post = (HrOrganizationPost) object;

					if (null == personInfo.getFdParent() || null == post.getFdParent()) {
						continue;
					}
					if (personInfo.getFdParent().getFdId().equals(post.getFdParent().getFdId())) {
						hrPosts.add(post);
					}
				}
			}
			if (null != hrPosts && hrPosts.size() > 0) {
				personInfo.setFdPosts(hrPosts);
			}
			isrun = false;
		}
		if ("fdContType".equals(fdField)) {
			personInfo.setFdContType(value.toString());
			isrun = false;
		}

		if ("fdBeginDate".equals(fdField)) {
			personInfo.setFdBeginDate((Date)value);
			isrun = false;
		}
		if ("fdEndDate".equals(fdField)) {
			personInfo.setFdEndDate((Date)value);
			isrun = false;
		}
	
		if ("fdContractUnit".equals(fdField)) {
			personInfo.setFdContractUnit(value.toString());

			isrun = false;
		}
		if ("fdStaffingLevel".equals(fdField)) {
			SysOrganizationStaffingLevel staffLevel = sysOrganizationStaffingLevelService
					.findStaffLevelByName((String) value);
			newTrack.setFdStaffingLevel(staffLevel);
			personInfo.setFdStaffingLevel(staffLevel);
			isrun = false;
		}
		if ("fdReportLeader".equals(fdField)) {
			String sql = "select fd_id from sys_org_element where fd_name='" + value + "' ";
			List listFdId = HrCurrencyParams.getValueBySql(sql, "fd_id");
			String fdId = (String) listFdId.get(0);
			SysOrgElement sysOrgElement = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdId);
			personInfo.setFdReportLeader(sysOrgElement);
			isrun = false;
		}
		if ("fdOrgParent".equals(fdField)) {
			String sql = "select fd_id from sys_org_element where fd_name='" + value + "' and fd_hierarchy_id like  '%"+dept+"%'";
			List listFdId = HrCurrencyParams.getValueBySql(sql, "fd_id");
			String fdId = (String) listFdId.get(0);
			SysOrgElement sysOrgElement = (SysOrgElement) sysOrgElementService.findByPrimaryKey(fdId);
			personInfo.setFdOrgParent(sysOrgElement);
			
			List<SysOrgElement> allParent = sysOrgElement.getAllParent(true);
			if(person!=null)
			person.setFdParent(sysOrgElement);
//			if(allParent.size()!=1)
//			for (int i=0;i<=allParent.size() - 2; i++) {
//				SysOrgElement sysOrgElement1 = allParent.get(i);
//				if(i==allParent.size() - 2)
//					personInfo.setFdFirstLevelDepartment(sysOrgElement1);
//				if(i==allParent.size() - 3)
//					personInfo.setFdSecondLevelDepartment(sysOrgElement1);
//			}
//			if(allParent.size()==3)
//				personInfo.setFdThirdLevelDepartment(sysOrgElement);
//			if(allParent.size()==2)
//				personInfo.setFdSecondLevelDepartment(sysOrgElement);
			SysOrgElement sysOrgElement2 = null;
			if(allParent.size()!=1)
			sysOrgElement2 = allParent.get(allParent.size() - 2);
			else sysOrgElement2 = sysOrgElement;
			JSONObject object2 = new JSONObject();
			SysOrgElement sysOrgElement3 = (SysOrgElement) getSysOrgElementService()
					.findByPrimaryKey(
							sysOrgElement2.getHbmThisLeader().getFdId());
			
			isrun = false;
		}
		if ("fdOrgRank".equals(fdField)) {
			HrOrganizationRank rank = getHrOrganizationRankService()
					.findRankByName(value.toString());
			personInfo.setFdOrgRank(rank);
			isrun = false;
		}
		if ("fdOtherContact".equals(fdField)) {
			personInfo.setFdOtherContact(NumberUtil.roundDecimal(value, "###"));
			isrun = false;
		}
		
		 if ("fdSex".equals(fdField) && StringUtil.isNotNull((String) value))
		 {
		 if ("F".equals(value.toString())) {
		 personInfo.setFdSex("F");
		 } else {
		 personInfo.setFdSex("M");
		 }
		 isrun = false;
		 }
		if ("fdStatus".equals(fdField)) {
			personInfo.setFdStatus(value.toString());
			isrun = false;
		}
		if ("fdMobileNo".equals(fdField)) {
			String result = this.checkMobileNoUnique(personInfo.getFdId(), value.toString());
			if (StringUtil.isNotNull(result)) {
				messages.addError(new KmssMessage("	[手机号码]不能重复！"));
			} else {
				personInfo.setFdMobileNo(value.toString());
				// person.setFdMobileNo(value.toString());
			}
		}
		if ("fdHighestDegree".equals(fdField) || "fdHighestEducation".equals(fdField) || "fdHealth".equals(fdField)
				|| "fdNation".equals(fdField) || "fdPoliticalLandscape".equals(fdField)
				|| "fdMaritalStatus".equals(fdField) || "fdWorkAddress".equals(fdField)
				|| "fdNatureWork".equals(fdField) || "fdStaffType".equals(fdField) || "fdLeaveReason".equals(fdField)) {
			// 最高学位、最高学历、健康情况、名族、政治面貌、工作地点、工作性质、婚姻情况
			if (null != value) {
				HQLInfo hqlInfo = new HQLInfo();
				hqlInfo.setWhereBlock("fdType = :fdType and fdName = :fdName");
				hqlInfo.setParameter("fdType", fdField.toString());
				hqlInfo.setParameter("fdName", value.toString());
				List<HrStaffPersonInfoSettingNew> list = getHrStaffPersonInfoSettingNewServiceImp().findList(hqlInfo);
				if (ArrayUtil.isEmpty(list)) {
					/*
					 * value = list.get(0).getFdId(); }else{
					 */
					messages.addError(
							new KmssMessage(ResourceUtil.getString("hr-staff:hrStaffPersonInfo." + fdField) + "不存在！"));
				}
			}
		}

		if (isrun) {
			BeanUtils.setProperty(personInfo, fdField, value);
		}
//		if ("fdTimeOfEnterprise".equals(fdField) || "fdWorkTime".equals(fdField)) {
//			if (personInfo.getFdWorkTime() != null && personInfo.getFdTimeOfEnterprise() != null
//					&& personInfo.getFdWorkTime().after(personInfo.getFdTimeOfEnterprise())) {
//				messages.addError(
//						new KmssMessage(ResourceUtil.getString("hrStaffPersonInfo.workTime.err.plus", "hr-staff")));
//			}
//		}
	}

	private void setOrgPersonProps(SysOrgPerson person, HrStaffPersonInfo staff) throws Exception {

		if (StringUtil.isNotNull(staff.getFdSex())) {
			person.setFdSex(staff.getFdSex());
		}
		if (StringUtil.isNotNull(staff.getFdMobileNo())) {
			person.setFdMobileNo(staff.getFdMobileNo());
		}
		if (staff.getFdStaffingLevel() != null) {
			person.setFdStaffingLevel(staff.getFdStaffingLevel());
		}
		if (StringUtil.isNotNull(staff.getFdWorkPhone())) {
			person.setFdWorkPhone(staff.getFdWorkPhone());
		}
		if (staff.getFdStaffingLevel() != null) {
			person.setFdStaffingLevel(staff.getFdStaffingLevel());
		}
//		if (staff.getFdParent() != null) {
//		 if (parent != null) { person.setFdParent(parent); } } 
//		String postId =
//		 * ((HrOrganizationElement) staff.getFdPosts().get(0)) .getFdId();
//		 * ArrayList postlist = new ArrayList(); SysOrgPost post = (SysOrgPost)
//		 * sysOrgPostService. findByPrimaryKey(postId); if (post != null) {
//		 * postlist.add(post); person.setFdPosts(postlist); }
//		 */
		person.setFdAlterTime(new Date());
	}

	private void addTrackRecord(HrStaffTrackRecord newTrack, HrStaffPersonInfo personInfo) throws Exception {

		if (newTrack != null) {
			Date now = new Date();
			if (newTrack.getFdHrOrgDept() == null) {
				newTrack.setFdHrOrgDept(personInfo.getFdParent());
			}
			if (newTrack.getFdHrOrgPost() == null) {
				newTrack.setFdHrOrgPost((HrOrganizationPost) personInfo.getFdPosts().get(0));
			}
			newTrack.setFdEntranceBeginDate(now);
			newTrack.setFdEntranceEndDate(now);
			newTrack.setFdType("1");
			newTrack.setFdTransDate(now);
			newTrack.setFdPersonInfo(personInfo);
			getHrStaffTrackRecordService().add(newTrack);
		}
	}

	/**
	 * 记录导入日志
	 */
	private void buildLog(Set<HrStaffPersonInfo> targets) throws Exception {
		HrStaffPersonInfoLog log = hrStaffPersonInfoLogService.buildPersonInfoLog("import",
				"导入 " + targets.size() + "位员工的“员工信息”。");
		if (CollectionUtils.isNotEmpty(targets)) {
			log.getFdTargets().addAll(targets);
		}
		hrStaffPersonInfoLogService.add(log);
	}

	private boolean checkFile(Sheet sheet, String[] importFields) {
		// 正常来说，第一行是标题
		int cellNum = sheet.getRow(0).getLastCellNum();
		return cellNum == (importFields.length + 3);
	}

	private List<SysOrgPerson> findByLoginName(String fdLoginName, String fdNo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		// 因为可能需要编辑无效人员，所以这里不能过滤“无效人员”
		String whereBlock = "fdLoginName = :fdLoginName";
		if (StringUtil.isNotNull(fdNo)) {
			whereBlock += " and fdNo = :fdNo";
			hqlInfo.setParameter("fdNo", fdNo);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdLoginName", fdLoginName);
		List<SysOrgPerson> list = sysOrgPersonService.findList(hqlInfo);
		return list;
	}

	private SysOrgPerson findLoginName(List<SysOrgPerson> list) throws Exception {
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public HrStaffPersonInfo findPersonInfoByLoginName(String fdLoginName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdOrgPerson.fdLoginName = :fdLoginName");
		hqlInfo.setParameter("fdLoginName", fdLoginName);
		List<HrStaffPersonInfo> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public HrStaffPersonInfo findPersonInfoByLoginNameAndNo(String fdLoginName, String fdNo) throws Exception {
		if (StringUtil.isNotNull(fdNo)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock("fdOrgPerson.fdLoginName = :fdLoginName and fdOrgPerson.fdNo = :fdNo");
			hqlInfo.setParameter("fdLoginName", fdLoginName);
			hqlInfo.setParameter("fdNo", fdNo);
			List<HrStaffPersonInfo> list = findList(hqlInfo);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			} else {
				return null;
			}
		} else {
			return findPersonInfoByLoginName(fdLoginName);
		}
	}

	@Override
	public HrStaffPersonInfo findPersonInfoByStaffNo(String staffNo) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrStaffPersonInfo.fdStaffNo = :fdStaffNo");
		hqlInfo.setParameter("fdStaffNo", staffNo);

		List<HrStaffPersonInfo> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public HrStaffPersonInfo findByOrgPersonId(String fdOrgPersonId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdId = :fdId");
		hqlInfo.setParameter("fdId", fdOrgPersonId);

		List<HrStaffPersonInfo> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<HrStaffPersonInfo> findByPost(String fdOrgPostId) throws Exception {
		String sql = "select fd_personid from sys_org_post_person where fd_postid = :fdOrgPostId ";
		List list = getBaseDao().getHibernateSession().createNativeQuery(sql).setParameter("fdOrgPostId", fdOrgPostId)
				.list();
		List hrStaffPersonInfos = null;

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				hrStaffPersonInfos = new ArrayList<HrStaffPersonInfo>();
				hrStaffPersonInfos.add(findByOrgPersonId(list.get(i).toString()));
			}
		}
		return hrStaffPersonInfos;

	}

	/**
	 * 试用期人员列表查询
	 * 
	 * @param searchType
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<HrStaffPersonInfo> findByTrial(String searchType) throws Exception {
		HQLInfo hqlInfo = findByTrialHqlInfo(searchType, new Date());
		if (hqlInfo == null) {
			return null;
		}
		List<HrStaffPersonInfo> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 根据周期查询对应的生日人列表、分页查询
	 * 
	 * @param searchDateType
	 *            周期类型
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page findByTrialPage(String searchDateType, Date beginDate, int rowSize, int pageNo) throws Exception {
		HQLInfo hqlInfo = findByTrialHqlInfo(searchDateType, beginDate);
		if (hqlInfo == null) {
			return null;
		}
		hqlInfo.setPageNo(pageNo);
		hqlInfo.setRowSize(rowSize);
		hqlInfo.setOrderBy("hrStaffPersonInfo.fdTrialExpirationTime");

		StringBuffer whereBlock = new StringBuffer(hqlInfo.getWhereBlock());
		HrStaffAlertWarningTrial warningBirthday = new HrStaffAlertWarningTrial();
		if ("true".equals(warningBirthday.getCerifyAuthorization())) {
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo);
			hqlInfo.setWhereBlock(whereBlock.toString());
		} else {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		}
		return findPage(hqlInfo);
	}

	private HQLInfo findByTrialHqlInfo(String searchType, Date beginDate) {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffPersonInfo.fdTrialExpirationTime >= :fdEndDateQ and hrStaffPersonInfo.fdTrialExpirationTime <= :fdEndDateH  and hrStaffPersonInfo.fdStatus in ( :trial,:trialDelay )");
		if ("week".equals(searchType)) {
			hqlInfo.setParameter("fdEndDateQ", beginDate == null ? HrStaffDateUtil.getTimesWeekmorning()
					: DateUtil.removeTime(beginDate).getTime());
			hqlInfo.setParameter("fdEndDateH", HrStaffDateUtil.getTimesWeeknight());
		} else if ("month".equals(searchType)) {
			hqlInfo.setParameter("fdEndDateQ", beginDate == null ? HrStaffDateUtil.getTimesMonthmorning()
					: DateUtil.removeTime(beginDate).getTime());
			hqlInfo.setParameter("fdEndDateH", HrStaffDateUtil.getTimesMonthnight());

		} else if ("twoMonth".equals(searchType)) {
			hqlInfo.setParameter("fdEndDateQ", beginDate == null ? HrStaffDateUtil.getTimesMonthmorning()
					: DateUtil.removeTime(beginDate).getTime());
			hqlInfo.setParameter("fdEndDateH", HrStaffDateUtil.getTimeLastMonthLast());
		} else if ("quarter".equals(searchType)) {
			hqlInfo.setParameter("fdEndDateQ", beginDate == null ? HrStaffDateUtil.getFirstDayOfQuarter()
					: DateUtil.removeTime(beginDate).getTime());
			hqlInfo.setParameter("fdEndDateH", HrStaffDateUtil.getLastDayOfQuarter());
		}
		hqlInfo.setParameter("trial", "trial");
		hqlInfo.setParameter("trialDelay", "trialDelay");

		return hqlInfo;
	}

	/**
	 * 根据周期查询对应的生日人列表
	 * 
	 * @param searchDateType
	 *            周期类型
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<HrStaffPersonInfo> findByBirthday(String searchDateType) throws Exception {
		HQLInfo hqlInfo = findByBirthdayHql(searchDateType, null);
		if (hqlInfo == null) {
			return null;
		}
		List<HrStaffPersonInfo> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 根据周期查询对应的生日人列表、分页查询
	 * 
	 * @param searchDateType
	 *            周期类型
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page findByBirthdayPage(String searchDateType, Date beginDate, int rowSize, int pageNo) throws Exception {
		HQLInfo hqlInfo = findByBirthdayHql(searchDateType, beginDate);
		if (hqlInfo == null) {
			return null;
		}
		hqlInfo.setPageNo(pageNo);
		hqlInfo.setRowSize(rowSize);
		hqlInfo.setOrderBy("hrStaffPersonInfo.fdBirthdayOfYear");
		StringBuffer whereBlock = new StringBuffer(hqlInfo.getWhereBlock());
		HrStaffAlertWarningBirthday warningBirthday = new HrStaffAlertWarningBirthday();
		if ("true".equals(warningBirthday.getCerifyAuthorization())) {
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo);
			hqlInfo.setWhereBlock(whereBlock.toString());
		} else {
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		}
		return findPage(hqlInfo);
	}

	/**
	 * 查询生日范围的HQL语句
	 * 
	 * @param searchDateType
	 * @return
	 */
	private HQLInfo findByBirthdayHql(String searchDateType, Date beginDate) {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffPersonInfo.fdBirthdayOfYear >= :fdEndDateQ and hrStaffPersonInfo.fdBirthdayOfYear <= :fdEndDateH and hrStaffPersonInfo.fdStatus in ( :trial,:official,:temporary,:trialDelay,:retire )");
		if ("week".equals(searchDateType)) {
			// 如果开始时间没有指定，则按照规则取默认值
			if (beginDate == null) {
				hqlInfo.setParameter("fdEndDateQ",
						HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getTimesWeekmorning()));
			} else {
				hqlInfo.setParameter("fdEndDateQ", HrStaffDateUtil.dateToFdBirthdayOfYear(beginDate));
			}
			hqlInfo.setParameter("fdEndDateH",
					HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getTimesWeeknight()));
		} else if ("month".equals(searchDateType)) {
			// 如果开始时间没有指定，则按照规则取默认值
			if (beginDate == null) {
				hqlInfo.setParameter("fdEndDateQ",
						HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getTimesMonthmorning()));
			} else {
				hqlInfo.setParameter("fdEndDateQ", HrStaffDateUtil.dateToFdBirthdayOfYear(beginDate));
			}
			hqlInfo.setParameter("fdEndDateH",
					HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getTimesMonthnight()));

		} else if ("twoMonth".equals(searchDateType)) {
			// 如果开始时间没有指定，则按照规则取默认值
			if (beginDate == null) {
				hqlInfo.setParameter("fdEndDateQ",
						HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getTimesMonthmorning()));
			} else {
				hqlInfo.setParameter("fdEndDateQ", HrStaffDateUtil.dateToFdBirthdayOfYear(beginDate));
			}
			hqlInfo.setParameter("fdEndDateH",
					HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getTimeLastMonthLast()));
		} else if ("quarter".equals(searchDateType)) {
			// 如果开始时间没有指定，则按照规则取默认值
			if (beginDate == null) {
				hqlInfo.setParameter("fdEndDateQ",
						HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getFirstDayOfQuarter()));
			} else {
				hqlInfo.setParameter("fdEndDateQ", HrStaffDateUtil.dateToFdBirthdayOfYear(beginDate));
			}

			hqlInfo.setParameter("fdEndDateH",
					HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getLastDayOfQuarter()));
		} else {
			return null;
		}
		hqlInfo.setParameter("trial", "trial");
		hqlInfo.setParameter("official", "official");
		hqlInfo.setParameter("temporary", "temporary");
		hqlInfo.setParameter("trialDelay", "trialDelay");
		hqlInfo.setParameter("retire", "retire");
		return hqlInfo;
	}

	@Override
	public List<HrStaffPersonInfo> findByBirthdayToday() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdBirthdayOfYear = :fdEndDate ");
		hqlInfo.setParameter("fdEndDate", HrStaffDateUtil.dateToFdBirthdayOfYear(HrStaffDateUtil.getToday()));

		List<HrStaffPersonInfo> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}

	@Override
	public Page obtainPersons(HQLInfo hqlInfo, String parentId, String fdSearchName) throws Exception {
		String where = "sysOrgPerson.fdIsAvailable= :fdIsAvailable and "
				+ "(sysOrgPerson.fdIsAbandon = false or sysOrgPerson.fdIsAbandon is null) and sysOrgPerson.fdIsBusiness = :fdIsBusiness";
		if (StringUtil.isNotNull(parentId)) {
			SysOrgElement element = sysOrgCoreService.findByPrimaryKey(parentId);
			where += " and sysOrgPerson.fdHierarchyId like '" + element.getFdHierarchyId() + "%'";
		}
		if (StringUtil.isNotNull(fdSearchName)) {
			StringBuffer sb = new StringBuffer();
			sb.append(" and (sysOrgPerson.fdName like :searchName")
					.append(" or sysOrgPerson.fdNamePinYin like :searchName")
					.append(" or sysOrgPerson.fdLoginName like :searchName")
					.append(" or sysOrgPerson.fdEmail like :searchName")
					.append(" or sysOrgPerson.fdMobileNo like :searchName)");
			where += sb.toString();
			hqlInfo.setParameter("searchName", "%" + fdSearchName + "%");
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setParameter("fdIsAvailable", Boolean.TRUE);
		hqlInfo.setParameter("fdIsBusiness", Boolean.TRUE);
		return sysOrgPersonService.findPage(hqlInfo);
	}

	public static final String DOCCOUNT_EXTENSION_POINT_ID = "com.landray.kmss.sys.zone.doccount";
	private static Boolean extensionInitFlag = false;
	private static IExtension[] extensions;

	private static void initExtensions() {
		IExtensionPoint point = Plugin.getExtensionPoint(DOCCOUNT_EXTENSION_POINT_ID);
		extensions = point.getExtensions();
		extensionInitFlag = true;
	}

	@Override
	public JSONArray getDocCount(String fdPersonId) throws Exception {
		if (!extensionInitFlag) {
			initExtensions();
		}
		JSONArray array = new JSONArray();
		for (IExtension extension : extensions) {
			if ("doccount".equals(extension.getAttribute("name"))) {
				String unid = Plugin.getParamValueString(extension, "unid");
				String order = Plugin.getParamValueString(extension, "order");
				String title = Plugin.getParamValueString(extension, "title");
				String link = Plugin.getParamValueString(extension, "link");
				String bean = Plugin.getParamValueString(extension, "bean");
				ISysZoneDocCountGetter docCountGetter = (ISysZoneDocCountGetter) SpringBeanUtil.getBean(bean);
				JSONObject obj = new JSONObject();
				obj.put("uuid", unid);
				obj.put("order", order);
				obj.put("title", title);
				obj.put("link", link);
				obj.put("num", docCountGetter.getDocNum(fdPersonId));
				obj.put("isSelf", fdPersonId.equals(UserUtil.getUser().getFdId()) ? true : false);
				array.add(obj);
			}
		}
		return array;
	}

	private IHrStaffPersonExperienceBriefService hrStaffPersonExperienceBriefService;
	private IHrStaffPersonExperienceProjectService hrStaffPersonExperienceProjectService;
	private IHrStaffPersonExperienceEducationService hrStaffPersonExperienceEducationService;
	private IHrStaffPersonExperienceTrainingService hrStaffPersonExperienceTrainingService;
	private IHrStaffPersonExperienceWorkService hrStaffPersonExperienceWorkService;
	private IHrStaffPersonExperienceBonusMalusService hrStaffPersonExperienceBonusMalusService;

	public void setHrStaffPersonExperienceBriefService(
			IHrStaffPersonExperienceBriefService hrStaffPersonExperienceBriefService) {
		this.hrStaffPersonExperienceBriefService = hrStaffPersonExperienceBriefService;
	}

	public void setHrStaffPersonExperienceProjectService(
			IHrStaffPersonExperienceProjectService hrStaffPersonExperienceProjectService) {
		this.hrStaffPersonExperienceProjectService = hrStaffPersonExperienceProjectService;
	}

	public void setHrStaffPersonExperienceEducationService(
			IHrStaffPersonExperienceEducationService hrStaffPersonExperienceEducationService) {
		this.hrStaffPersonExperienceEducationService = hrStaffPersonExperienceEducationService;
	}

	public void setHrStaffPersonExperienceTrainingService(
			IHrStaffPersonExperienceTrainingService hrStaffPersonExperienceTrainingService) {
		this.hrStaffPersonExperienceTrainingService = hrStaffPersonExperienceTrainingService;
	}

	public void setHrStaffPersonExperienceWorkService(
			IHrStaffPersonExperienceWorkService hrStaffPersonExperienceWorkService) {
		this.hrStaffPersonExperienceWorkService = hrStaffPersonExperienceWorkService;
	}

	public void setHrStaffPersonExperienceBonusMalusService(
			IHrStaffPersonExperienceBonusMalusService hrStaffPersonExperienceBonusMalusService) {
		this.hrStaffPersonExperienceBonusMalusService = hrStaffPersonExperienceBonusMalusService;
	}

	@Override
	public String dataIntrgrity(String fdPersonId) throws Exception {
		int blockCount = 0; // 启用的个数
		int count = 0; // 填写了数据的个数
		String percent = "0";
		List<Long> list = new ArrayList<Long>();
		HQLInfo hql = new HQLInfo();
		if (!HrStaffPrivateUtil.isBriefPrivate(fdPersonId)) {
			blockCount++;
			hql.setSelectBlock(" count(hrStaffPersonExperienceBrief.fdId)");
			hql.setFromBlock(
					"com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBrief hrStaffPersonExperienceBrief");
			hql.setWhereBlock(" hrStaffPersonExperienceBrief.fdPersonInfo.fdId=:fdUserId");
			hql.setParameter("fdUserId", UserUtil.getUser().getFdId());
			list = hrStaffPersonExperienceBriefService.getBaseDao().findValue(hql);
			if (list.get(0) > 0) {
				count++;
			}

		}
		if (!HrStaffPrivateUtil.isEducationPrivate(fdPersonId)) {
			blockCount++;
			hql.setSelectBlock(" count(hrStaffPersonExperienceEducation.fdId)");
			hql.setFromBlock(
					"com.landray.kmss.hr.staff.model.HrStaffPersonExperienceEducation hrStaffPersonExperienceEducation");
			hql.setWhereBlock(" hrStaffPersonExperienceEducation.fdPersonInfo.fdId=:fdUserId");
			hql.setParameter("fdUserId", UserUtil.getUser().getFdId());
			list = hrStaffPersonExperienceEducationService.getBaseDao().findValue(hql);
			if (list.get(0) > 0) {
				count++;
			}
		}
		if (!HrStaffPrivateUtil.isProjectPrivate(fdPersonId)) {
			blockCount++;
			hql.setSelectBlock(" count(hrStaffPersonExperienceProject.fdId)");
			hql.setFromBlock(
					"com.landray.kmss.hr.staff.model.HrStaffPersonExperienceProject hrStaffPersonExperienceProject");
			hql.setWhereBlock(" hrStaffPersonExperienceProject.fdPersonInfo.fdId=:fdUserId");
			hql.setParameter("fdUserId", UserUtil.getUser().getFdId());
			list = hrStaffPersonExperienceProjectService.getBaseDao().findValue(hql);
			if (list.get(0) > 0) {
				count++;
			}
		}
		if (!HrStaffPrivateUtil.isTrainingPrivate(fdPersonId)) {
			blockCount++;
			hql.setSelectBlock(" count(hrStaffPersonExperienceTraining.fdId)");
			hql.setFromBlock(
					"com.landray.kmss.hr.staff.model.HrStaffPersonExperienceTraining hrStaffPersonExperienceTraining");
			hql.setWhereBlock(" hrStaffPersonExperienceTraining.fdPersonInfo.fdId=:fdUserId");
			hql.setParameter("fdUserId", UserUtil.getUser().getFdId());
			list = hrStaffPersonExperienceTrainingService.getBaseDao().findValue(hql);
			if (list.get(0) > 0) {
				count++;
			}
		}
		if (!HrStaffPrivateUtil.isWorkPrivate(fdPersonId)) {
			blockCount++;
			hql.setSelectBlock(" count(hrStaffPersonExperienceWork.fdId)");
			hql.setFromBlock("com.landray.kmss.hr.staff.model.HrStaffPersonExperienceWork hrStaffPersonExperienceWork");
			hql.setWhereBlock(" hrStaffPersonExperienceWork.fdPersonInfo.fdId=:fdUserId");
			hql.setParameter("fdUserId", UserUtil.getUser().getFdId());
			list = hrStaffPersonExperienceWorkService.getBaseDao().findValue(hql);
			if (list.get(0) > 0) {
				count++;
			}
		}
		if (!HrStaffPrivateUtil.isBonusPrivate(fdPersonId)) {
			blockCount++;
			hql.setSelectBlock(" count(hrStaffPersonExperienceBonusMalus.fdId)");
			hql.setFromBlock(
					"com.landray.kmss.hr.staff.model.HrStaffPersonExperienceBonusMalus hrStaffPersonExperienceBonusMalus");
			hql.setWhereBlock(" hrStaffPersonExperienceBonusMalus.fdPersonInfo.fdId=:fdUserId");
			hql.setParameter("fdUserId", UserUtil.getUser().getFdId());
			list = hrStaffPersonExperienceBonusMalusService.getBaseDao().findValue(hql);
			if (list.get(0) > 0) {
				count++;
			}
		}
		if (count > 0 && blockCount > 0) {
			percent = Math.round((100 * count) / blockCount) + "";
		}
		return percent;
	}

	@Override
	public String checkUnique(RequestContext requestInfo) throws Exception {
		String fdId = requestInfo.getParameter("fdId");
		String mobileNo = requestInfo.getParameter("mobileNo");
		String staffNo = requestInfo.getParameter("staffNo");
		String result = "";
		if (StringUtil.isNotNull(mobileNo)) {
			result = checkMobileNoUnique(fdId, mobileNo);
		}
		if (StringUtil.isNotNull(staffNo)) {
			result = checkStaffNoUnique(fdId, staffNo);
		}
		String loginName = requestInfo.getParameter("loginName");
		String checkType = requestInfo.getParameter("checkType");
		if (StringUtil.isNotNull(loginName)) {
			result = checkLoginName(fdId, loginName, checkType);
		}
		return result;
	}

	private String checkLoginName(String fdId, String loginName, String checkType) throws Exception {
		String result = "";
		if (StringUtil.isNull(loginName)) {
			return result;
		}
		// HrRatifyEntry entry = (HrRatifyEntry) findByPrimaryKey(fdId,
		// HrRatifyEntry.class, true);
		// if ((entry != null) && (loginName.equals(entry.getFdLoginName()))) {
		// // 编辑用户并且登录名没有改动过则 无需校验无效部分是否重名
		// return result;
		// }
		HQLInfo hqlInfo = new HQLInfo();
		String hql = " sysOrgPerson.fdLoginName=:fdLoginName and ";
		if ("unique".equals(checkType)) {
			hql = hql + " sysOrgPerson.fdIsAvailable=" + HibernateUtil.toBooleanValueString(true); // 1
																									// 表示有效的登录名
		} else {
			// 检测无效部分是否重名
			hql = hql + " sysOrgPerson.fdIsAvailable=" + HibernateUtil.toBooleanValueString(false);// 0
																									// 表示无效的登录名
		}
		hqlInfo.setWhereBlock(hql);
		hqlInfo.setParameter("fdLoginName", loginName);
		List<SysOrgPerson> lists = getSysOrgPersonService().findList(hqlInfo);

		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result += lists.get(0).getFdLoginName();
		}
		return result;
	}

	private String checkStaffNoUnique(String fdId, String staffNo) throws Exception {
		String result = "";
		if (StringUtil.isNull(staffNo)) {
			return result;
		}
		HQLInfo hqlInfo = new HQLInfo();
		// 检查人事档案中有没有相同的工号
		hqlInfo.setWhereBlock(" hrStaffPersonInfo.fdStaffNo = :fdStaffNo and hrStaffPersonInfo.fdId != :fdId");
		hqlInfo.setParameter("fdStaffNo", staffNo);
		hqlInfo.setParameter("fdId", fdId);
		List<HrStaffPersonInfo> lists = findList(hqlInfo);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result = lists.get(0).getFdStaffNo();
		}
		return result;
	}

	private String checkMobileNoUnique(String fdId, String mobileNo) throws Exception {
		String result = "";
		if (StringUtil.isNull(mobileNo)) {
			return result;
		}
		// 如果是+86开头的手机号，保存到数据库时强制去掉+86前缀
		if (mobileNo.startsWith("+86")) {
			mobileNo = mobileNo.substring(3);
		}
		if (mobileNo.startsWith("x")) {
			mobileNo = mobileNo.replace("x", "+");
		}
		HQLInfo hqlInfo = new HQLInfo();
		// 检查组织机构有没有相同的手机号
		hqlInfo.setWhereBlock(
				" sysOrgPerson.fdMobileNo = :fdMobileNo and sysOrgPerson.fdId != :fdId and sysOrgPerson.fdIsAvailable ="
						+ HibernateUtil.toBooleanValueString(true));
		hqlInfo.setParameter("fdMobileNo", mobileNo);
		hqlInfo.setParameter("fdId", fdId);
		List<SysOrgPerson> lists = sysOrgPersonService.findList(hqlInfo);
		if ((lists != null) && (!lists.isEmpty()) && (lists.size() > 0)) {
			result += lists.get(0).getFdLoginName();
		}

		// 检查人事档案有没有相同的手机号
		if (StringUtil.isNull(result)) {
			hqlInfo = new HQLInfo();
			hqlInfo.setParameter("fdMobileNo", mobileNo);
			hqlInfo.setParameter("fdId", fdId);
			hqlInfo.setWhereBlock(" hrStaffPersonInfo.fdMobileNo = :fdMobileNo and hrStaffPersonInfo.fdId != :fdId");
			List<HrStaffPersonInfo> lists2 = findList(hqlInfo);
			if ((lists2 != null) && (!lists2.isEmpty()) && (lists2.size() > 0)) {
				result += lists2.get(0).getFdName();
			}
		}
		// 检查hrStaffEntry 中有没有相同的手机号
		if (StringUtil.isNull(result)) {
			hqlInfo = new HQLInfo();
			hqlInfo.setParameter("fdMobileNo", mobileNo);
			hqlInfo.setParameter("fdId", fdId);
			hqlInfo.setWhereBlock(" hrStaffEntry.fdMobileNo = :fdMobileNo and hrStaffEntry.fdOrgPerson.fdId != :fdId");
			List<HrStaffEntry> lists3 = getHrStaffEntryServiceImp().findList(hqlInfo);
			if ((lists3 != null) && (!lists3.isEmpty()) && (lists3.size() > 0)) {
				result += lists3.get(0).getFdName();
			}
		}

		return result;
	}

	public List<String[]> modelToArrayList(List<HrStaffPersonInfo> useList) throws Exception {
		List<String[]> returnList = new ArrayList<String[]>();
		if (ArrayUtil.isEmpty(useList)) {
			String[] listdata = new String[9];
			for (int i = 0; i < listdata.length; i++) {
				listdata[i] = "";
			}
			returnList.add(listdata);
			return returnList;
		}
		List<HrStaffPersonInfoSettingNew> hrStaffPersonInfoSettingNewList = getHrStaffPersonInfoSettingNewServiceImp()
				.findList("", "");
		Map<String, HrStaffPersonInfoSettingNew> hrStaffPersonInfoSettingNewMap = new HashMap<String, HrStaffPersonInfoSettingNew>();
		if (null != hrStaffPersonInfoSettingNewList && !hrStaffPersonInfoSettingNewList.isEmpty()) {
			for (HrStaffPersonInfoSettingNew hrStaffPersonInfoSettingNew : hrStaffPersonInfoSettingNewList) {
				hrStaffPersonInfoSettingNewMap.put(hrStaffPersonInfoSettingNew.getFdId(), hrStaffPersonInfoSettingNew);
			}
		}
		for (int i = 0; i < useList.size(); i++) {
			HrStaffPersonInfo hrStaffPersonInfo = useList.get(i);
			returnList.add(buildExcelDate(hrStaffPersonInfo, hrStaffPersonInfoSettingNewMap));
		}
		return returnList;
	}

	private String[] buildExcelDate(HrStaffPersonInfo hrStaffPersonInfo,
			Map<String, HrStaffPersonInfoSettingNew> hrStaffPersonInfoSettingNewMap) throws Exception {

		String[] listdata = new String[47];
		String fdNo = hrStaffPersonInfo.getFdOrgPerson() == null ? "" : hrStaffPersonInfo.getFdOrgPerson().getFdNo();
		String fdLoginName = hrStaffPersonInfo.getFdLoginName();
		String fdStaffNo = hrStaffPersonInfo.getFdStaffNo();// 员工号
		String fdName = hrStaffPersonInfo.getFdName();// 姓名
		String fdNameUsedBefore = hrStaffPersonInfo.getFdNameUsedBefore();
		String fdIdCard = hrStaffPersonInfo.getFdIdCard();// 身份证号
		// String fdOrgParent = hrStaffPersonInfo.getFdOrgParentsName();
		String fdOrgParent = hrStaffPersonInfo.getFdParentsName();// 所在部门取人事组织架构
		// 如果开启了ekp同步人事 取ekp的组织架构
		HrOrganizationSyncSetting hrorganizationsyncsetting = new HrOrganizationSyncSetting();
		if ("true".equals(hrorganizationsyncsetting.getEkpToHrEnable())) {
			fdOrgParent = hrStaffPersonInfo.getFdOrgParentsName();
		}
		String fdStaffingLevel = hrStaffPersonInfo.getFdStaffingLevel() == null ? ""
				: hrStaffPersonInfo.getFdStaffingLevel().getFdName();
		String fdOrgPosts = "";
		List<HrOrganizationElement> posts = hrStaffPersonInfo.getFdPosts();
		for (HrOrganizationElement element : posts) {
			fdOrgPosts += element.getFdName() + ";";
		}
		if ("true".equals(hrorganizationsyncsetting.getEkpToHrEnable())) {
			List<SysOrgPost> list = hrStaffPersonInfo.getFdOrgPosts();
			fdOrgPosts = "";
			if (list != null) {
				for (SysOrgPost sysOrgPost : list) {
					fdOrgPosts += sysOrgPost.getFdName() + ";";
				}
			}
		}
		String fdOrgRank = hrStaffPersonInfo.getFdOrgRank() == null ? "" : hrStaffPersonInfo.getFdOrgRank().getFdName();
		String fdMobileNo = hrStaffPersonInfo.getFdMobileNo();// 手机号码
		String fdTimeOfEnterprise = DateUtil.convertDateToString(hrStaffPersonInfo.getFdTimeOfEnterprise(),
				DateUtil.PATTERN_DATE);
		String fdNatureWork = hrStaffPersonInfo.getFdNatureWork();// 工作性质
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdNatureWork)) {
			fdNatureWork = hrStaffPersonInfoSettingNewMap.get(fdNatureWork) == null ? fdNatureWork
					: hrStaffPersonInfoSettingNewMap.get(fdNatureWork).getFdName();
		}
		String fdWorkAddress = hrStaffPersonInfo.getFdWorkAddress();// 工作地点
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdWorkAddress)) {
			fdWorkAddress = hrStaffPersonInfoSettingNewMap.get(fdWorkAddress) == null ? fdWorkAddress
					: hrStaffPersonInfoSettingNewMap.get(fdWorkAddress).getFdName();
		}
		String sex = hrStaffPersonInfo.getFdSex();
		String fdSex = "";// 性别
		if (StringUtil.isNotNull(sex) && "F".equals(sex)) {
			fdSex = ResourceUtil.getString("hrStaff.overview.report.staffSex.F", "hr.staff");
		} else {
			fdSex = ResourceUtil.getString("hrStaff.overview.report.staffSex.M", "hr.staff");
		}
		String personStatus = hrStaffPersonInfo.getFdStatus();
		String fdStatus = getPersonStatus(personStatus);
		String fdStaffType = hrStaffPersonInfo.getFdStaffType();// 人员类别
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdStaffType)) {
			fdStaffType = hrStaffPersonInfoSettingNewMap.get(fdStaffType) == null ? fdStaffType
					: hrStaffPersonInfoSettingNewMap.get(fdStaffType).getFdName();
		}
		String fdEntryTime = DateUtil.convertDateToString(hrStaffPersonInfo.getFdEntryTime(), DateUtil.PATTERN_DATE);
		String fdEmploymentPeriod = hrStaffPersonInfo.getFdEmploymentPeriod() == null ? ""
				: hrStaffPersonInfo.getFdEmploymentPeriod().toString();
		String fdProbationPeriod = hrStaffPersonInfo.getFdProbationPeriod();
		String fdPositiveTime = DateUtil.convertDateToString(hrStaffPersonInfo.getFdPositiveTime(),
				DateUtil.PATTERN_DATE);
		String fdLeaveTime = DateUtil.convertDateToString(hrStaffPersonInfo.getFdLeaveTime(), DateUtil.PATTERN_DATE);
		String fdLeaveReason = hrStaffPersonInfo.getFdLeaveReason();// 离职原因
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdLeaveReason)) {
			fdLeaveReason = hrStaffPersonInfoSettingNewMap.get(fdLeaveReason) == null ? fdLeaveReason
					: hrStaffPersonInfoSettingNewMap.get(fdLeaveReason).getFdName();
		}

		String fdDateOfBirth = DateUtil.convertDateToString(hrStaffPersonInfo.getFdDateOfBirth(),
				DateUtil.PATTERN_DATE);// 出生日期
		String fdMaritalStatus = hrStaffPersonInfo.getFdMaritalStatus();// 婚姻情况
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdMaritalStatus)) {
			fdMaritalStatus = hrStaffPersonInfoSettingNewMap.get(fdMaritalStatus) == null ? fdLeaveReason
					: hrStaffPersonInfoSettingNewMap.get(fdMaritalStatus).getFdName();
		}
		String fdDateOfGroup = DateUtil.convertDateToString(hrStaffPersonInfo.getFdDateOfGroup(),
				DateUtil.PATTERN_DATE);
		String fdDateOfParty = DateUtil.convertDateToString(hrStaffPersonInfo.getFdDateOfParty(),
				DateUtil.PATTERN_DATE);
		String fdHighestEducation = hrStaffPersonInfo.getFdHighestEducation();// 最高学历
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdHighestEducation)) {
			fdHighestEducation = hrStaffPersonInfoSettingNewMap.get(fdHighestEducation) == null ? fdHighestEducation
					: hrStaffPersonInfoSettingNewMap.get(fdHighestEducation).getFdName();
		}
		String fdHighestDegree = hrStaffPersonInfo.getFdHighestDegree();// 最高学位
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdHighestDegree)) {
			fdHighestDegree = hrStaffPersonInfoSettingNewMap.get(fdHighestDegree) == null ? fdHighestDegree
					: hrStaffPersonInfoSettingNewMap.get(fdHighestDegree).getFdName();
		}
		String fdNation = hrStaffPersonInfo.getFdNation();// 民族
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdNation)) {
			fdNation = hrStaffPersonInfoSettingNewMap.get(fdNation) == null ? fdNation
					: hrStaffPersonInfoSettingNewMap.get(fdNation).getFdName();
		}
		String fdHealth = hrStaffPersonInfo.getFdHealth();// 健康情况
		if (null != hrStaffPersonInfoSettingNewMap && hrStaffPersonInfoSettingNewMap.containsKey(fdHealth)) {
			fdHealth = hrStaffPersonInfoSettingNewMap.get(fdHealth) == null ? fdHealth
					: hrStaffPersonInfoSettingNewMap.get(fdHealth).getFdName();
		}
		String fdStature = hrStaffPersonInfo.getFdStature() == null ? "" : hrStaffPersonInfo.getFdStature().toString();
		String fdWeight = hrStaffPersonInfo.getFdWeight() == null ? "" : hrStaffPersonInfo.getFdWeight().toString();
		String fdPoliticalLandscape = hrStaffPersonInfo.getFdPoliticalLandscape();// 政治面貌
		if (null != hrStaffPersonInfoSettingNewMap
				&& hrStaffPersonInfoSettingNewMap.containsKey(fdPoliticalLandscape)) {
			fdPoliticalLandscape = hrStaffPersonInfoSettingNewMap.get(fdPoliticalLandscape) == null
					? fdPoliticalLandscape : hrStaffPersonInfoSettingNewMap.get(fdPoliticalLandscape).getFdName();
		}
		String fdNativePlace = hrStaffPersonInfo.getFdNativePlace();
		String fdAccountProperties = hrStaffPersonInfo.getFdAccountProperties();
		String fdHomeplace = hrStaffPersonInfo.getFdHomeplace();
		String fdRegisteredResidence = hrStaffPersonInfo.getFdRegisteredResidence();
		String fdResidencePoliceStation = hrStaffPersonInfo.getFdResidencePoliceStation();
		String fdWorkTime = DateUtil.convertDateToString(hrStaffPersonInfo.getFdWorkTime(), DateUtil.PATTERN_DATE);
		String fdWorkPhone = hrStaffPersonInfo.getFdWorkPhone();// 工作电话
		String fdOfficeLocation = hrStaffPersonInfo.getFdOfficeLocation();// 办公地点
		String fdLivingPlace = hrStaffPersonInfo.getFdLivingPlace();// 现居地
		String fdEmergencyContact = hrStaffPersonInfo.getFdEmergencyContact();// 紧急联系人
		String fdEmergencyContactPhone = hrStaffPersonInfo.getFdEmergencyContactPhone();// 紧急联系人电话
		String fdOtherContact = hrStaffPersonInfo.getFdOtherContact();// 其他联系方式

		listdata[0] = hrStaffPersonInfo.getFdId();
		listdata[1] = fdNo;
		listdata[2] = fdLoginName;
		listdata[3] = fdStaffNo;
		listdata[4] = fdName;
		listdata[5] = fdNameUsedBefore;
		listdata[6] = fdIdCard;
		listdata[7] = fdOrgParent;
		listdata[8] = fdStaffingLevel;
		listdata[9] = fdOrgPosts;
		listdata[10] = fdOrgRank;
		listdata[11] = fdMobileNo;
		listdata[12] = fdTimeOfEnterprise;
		listdata[13] = fdNatureWork;
		listdata[14] = fdWorkAddress;
		listdata[15] = fdSex;
		listdata[16] = fdStatus;
		listdata[17] = fdStaffType;
		listdata[18] = fdEntryTime;
		listdata[19] = fdEmploymentPeriod;
		listdata[20] = fdProbationPeriod;
		listdata[21] = fdPositiveTime;
		listdata[22] = fdLeaveTime;
		listdata[23] = fdLeaveReason;
		listdata[24] = fdDateOfBirth;
		listdata[25] = fdMaritalStatus;
		listdata[26] = fdDateOfGroup;
		listdata[27] = fdDateOfParty;
		listdata[28] = fdHighestEducation;
		listdata[29] = fdHighestDegree;
		listdata[30] = fdNation;
		listdata[31] = fdHealth;
		listdata[32] = fdStature;
		listdata[33] = fdWeight;
		listdata[34] = fdPoliticalLandscape;
		listdata[35] = fdNativePlace;
		listdata[36] = fdAccountProperties;
		listdata[37] = fdHomeplace;
		listdata[38] = fdRegisteredResidence;
		listdata[39] = fdResidencePoliceStation;
		listdata[40] = fdWorkTime;
		listdata[41] = fdWorkPhone;
		listdata[42] = fdOfficeLocation;
		listdata[43] = fdLivingPlace;
		listdata[44] = fdEmergencyContact;
		listdata[45] = fdEmergencyContactPhone;
		listdata[46] = fdOtherContact;

		return listdata;
	}

	private String getPersonStatus(String personStatus) {
		String fdStatus = "";
		switch (personStatus) {
		case "dismissal":
			fdStatus = ResourceUtil.getString("hrStaffPersonInfo.fdStatus.dismissal", "hr.staff");
			break;
		case "leave":
			fdStatus = ResourceUtil.getString("hrStaffPersonInfo.fdStatus.leave", "hr.staff");
			break;
		case "retire":
			fdStatus = ResourceUtil.getString("hrStaffPersonInfo.fdStatus.retire", "hr.staff");
			break;
		case "trial":
			fdStatus = ResourceUtil.getString("hrStaffPersonInfo.fdStatus.trial", "hr.staff");
			break;
		case "official":
			fdStatus = ResourceUtil.getString("hrStaffPersonInfo.fdStatus.official", "hr.staff");
			break;
		case "temporary":
			fdStatus = ResourceUtil.getString("hrStaffPersonInfo.fdStatus.temporary", "hr.staff");
			break;
		case "trialDelay":
			fdStatus = ResourceUtil.getString("hrStaffPersonInfo.fdStatus.trialDelay", "hr.staff");
			break;
		case "practice":
			fdStatus = ResourceUtil.getString("hrStaffPersonInfo.fdStatus.practice", "hr.staff");
			break;
		default:
			fdStatus = "";
			break;
		}
		return fdStatus;
	}

	public String[] getExportTitles() {
		String[] titles = { "hrStaffPersonInfo.template.fdNo", "hrStaffPersonInfo.fdLoginName",
				"hrStaffPersonInfo.fdStaffNo", "hrStaffPersonInfo.fdName", "hrStaffPersonInfo.fdNameUsedBefore",
				"hrStaffPersonInfo.fdIdCard", "hrStaffPersonInfo.fdOrgParent", "hrStaffPersonInfo.fdStaffingLevel",
				"hrStaffPersonInfo.fdOrgPosts", "hrStaffPersonInfo.fdOrgRank", "hrStaffPersonInfo.fdMobileNo",
				"hrStaffPersonInfo.fdTimeOfEnterprise", "hrStaffPersonInfo.fdNatureWork",
				"hrStaffPersonInfo.fdWorkAddress", "hrStaffPersonInfo.fdSex", "hrStaffPersonInfo.fdStatus",
				"hrStaffPersonInfo.fdStaffType", "hrStaffPersonInfo.fdEntryTime",
				"hrStaffPersonInfo.fdEmploymentPeriod", "hrStaffPersonInfo.fdProbationPeriod",
				"hrStaffPersonInfo.fdPositiveTime", "hrStaffPersonInfo.fdLeaveTime", "hrStaffPersonInfo.fdLeaveReason",
				"hrStaffPersonInfo.fdDateOfBirth", "hrStaffPersonInfo.fdMaritalStatus",
				"hrStaffPersonInfo.fdDateOfGroup", "hrStaffPersonInfo.fdDateOfParty",
				"hrStaffPersonInfo.fdHighestEducation", "hrStaffPersonInfo.fdHighestDegree",
				"hrStaffPersonInfo.fdNation", "hrStaffPersonInfo.fdHealth", "hrStaffPersonInfo.fdStature",
				"hrStaffPersonInfo.fdWeight", "hrStaffPersonInfo.fdPoliticalLandscape",
				"hrStaffPersonInfo.fdNativePlace", "hrStaffPersonInfo.fdAccountProperties",
				"hrStaffPersonInfo.fdHomeplace", "hrStaffPersonInfo.fdRegisteredResidence",
				"hrStaffPersonInfo.fdResidencePoliceStation", "hrStaffPersonInfo.fdWorkTime",
				"hrStaffPersonInfo.fdWorkPhone", "hrStaffPersonInfo.fdOfficeLocation",
				"hrStaffPersonInfo.fdLivingPlace", "hrStaffPersonInfo.fdEmergencyContact",
				"hrStaffPersonInfo.fdEmergencyContactPhone", "hrStaffPersonInfo.fdOtherContact", };
		for (int i = 0; i < titles.length; i++) {
			titles[i] = ResourceUtil.getString(titles[i], "hr.staff");
		}
		return titles;
	}

	/**
	 * 根据自定义列表获取表头
	 * 
	 * @param fdShowCol
	 * @return
	 */
	private List getExportTitles(String fdShowCol) {
		String[] s = fdShowCol.split(";");
		List title = new ArrayList();
		title.add("序号");
		Map map = hrStaffPersonInfoCustomizeDataSource.getOptions();
		for (int i = 0; i < s.length; i++) {
			if(map.containsKey(s[i])){
				title.add(map.get(s[i]));
			}
		}
		return title;
	}

	/**
	 *
	 * @param hrStaffPersonInfoList
	 * @param fdShowCol
	 * @return
	 */
	private List<String[]> getModelDataList(List<HrStaffPersonInfo> hrStaffPersonInfoList, String fdShowCol) {
		int length = fdShowCol.split(";").length;
		List<String[]> modelList = new ArrayList<>();
		for (int i = 0; i < hrStaffPersonInfoList.size(); i++) {
			HrStaffPersonInfo hrStaffPersonInfo = hrStaffPersonInfoList.get(i);
			String[] s = new String[length + 1];
			s[0] = String.valueOf(i + 1);
			Calendar calendar = Calendar.getInstance();
			for (int j = 0; j < length; j++) {
				String field = fdShowCol.split(";")[j];
				if (field.equals("fdPostalAddress"))
					s[j + 1] = ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdPostalAddressProvinceName")
							+ ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdPostalAddressCityName")
							+ ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdPostalAddressAreaName")
							+ ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdPostalAddress");
				else if (field.equals("fdHomeAddress"))
					s[j + 1] = ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdHomeAddressProvinceName")
							+ ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdHomeAddressCityName")
							+ ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdHomeAddressAreaName")
							+ ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdHomeAddress");

				else if (field.equals("fdOfficeArea"))
					s[j + 1] = ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdOfficeAreaProvinceName")
							+ ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdOfficeAreaCityName")
							+ ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdOfficeAreaAreaName");
				else if (field.equals("fdAge")) {
					if (ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdDateOfBirth").isEmpty())
						s[j + 1] = "";
					else
						s[j + 1] = String.valueOf(calendar.get(Calendar.YEAR) - Integer.parseInt(
								ExportUtil.getFieldValueByName(hrStaffPersonInfo, "fdDateOfBirth").split("-")[0]));
				}
				else if(field.equals("fdOrgRank")){
					if(hrStaffPersonInfo.getFdOrgRank()!=null)
						s[j + 1] = hrStaffPersonInfo.getFdOrgRank().getFdName();
					else 
						s[j + 1] = "";
				}else if(field.equals("fdStatus")){
					if("trial".equals(ExportUtil.getFieldValueByName(hrStaffPersonInfo, field)))
						s[j + 1] = "试用人员";
					else if("onpost".equals(ExportUtil.getFieldValueByName(hrStaffPersonInfo, field)))
						s[j + 1] = "在职人员";
					else if("official".equals(ExportUtil.getFieldValueByName(hrStaffPersonInfo, field)))
						s[j + 1] = "正式人员";
					else if("rehireAfterRetirement".equals(ExportUtil.getFieldValueByName(hrStaffPersonInfo, field)))
						s[j + 1] = "返聘人员";
					else if("blacklist".equals(ExportUtil.getFieldValueByName(hrStaffPersonInfo, field)))
						s[j + 1] = "黑名单";
					else if("leave".equals(ExportUtil.getFieldValueByName(hrStaffPersonInfo, field)))
						s[j + 1] = "离职人员";
				}
				else if(field.equals("fdStaffingLevel")){
					if(hrStaffPersonInfo.getFdStaffingLevel()!=null)
						s[j + 1] = hrStaffPersonInfo.getFdStaffingLevel().getFdName();
					else 
						s[j + 1] = "";
				}
				else
					s[j + 1] = ExportUtil.getFieldValueByName(hrStaffPersonInfo, field);
			}
			logger.info("获取人员信息id：{},name:{},导出数据:{}", hrStaffPersonInfo.getFdId(), hrStaffPersonInfo.getFdName(), s);
			modelList.add(s);
		}
		return modelList;
	}

	@Override
	public void exportPersonList(HttpServletRequest request, HttpServletResponse response,
			List<HrStaffPersonInfo> rtnList, String fdShowCols) {
		try {
			List<String[]> modelList = this.getModelDataList(rtnList, fdShowCols);
			String fileName = ResourceUtil.getString("hrStaffPersonInfo.exportFileName", "hr.staff");
			// 调用通用导出方法
			WorkBook workbook = new WorkBook();
			String sheetTitle = "员工花名册";
			com.landray.kmss.util.excel.Sheet sheet = new com.landray.kmss.util.excel.Sheet();
			sheet.setTitle(sheetTitle);
			List<String> columnList = this.getExportTitles(fdShowCols);
			// 设置表头
			for (String str : columnList) {
				Column col = new Column();
				col.setTitle(str);
				sheet.addColumn(col);
			}
			// 设置内容行
			sheet.setContentList(modelList);
			workbook.addSheet(sheet);
			workbook.setFilename(fileName);
			ExcelOutput output = new ExcelOutputImp();
			output.output(workbook, response);
		} catch (Exception e) {
			logger.error("hrStaffPersonInfoServiceImp.exportList.error", e);
		}
	}

	/**
	 * 导出Excel或Csv文件<br/>
	 * 自动保存导出日志
	 */
	@SuppressWarnings("unused")
	public void exportDecode(HttpServletResponse response, String[] headTitles, List<String[]> modelList,
			String fileName, String postfix, String fdModelName, String queryCondition) throws Exception {
		String exportIds = "";
		if (BdExportConstant.FIX_CSV.equals(postfix)) {// 导出CSV
		} else {// 导出Excel
			ExcelExportUtil excel = new ExcelExportUtil(response, fileName, headTitles,
					modelList, BdExportConstant.FIX_EXCEL_03.equals(postfix)
							? ExcelExportUtil.PostfixEnum.ENUM_POSTFIX_03 : ExcelExportUtil.PostfixEnum.ENUM_POSTFIX_07,
					true);
			excel.export();
			exportIds = excel.getExportIds();// 导出记录ID
		}
	}

	/**
	 * 导出所有任务
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void exportList(HttpServletRequest request, HttpServletResponse response, List<HrStaffPersonInfo> rtnList) {
		try {

			List<String[]> modelList = this.modelToArrayList(rtnList);
			String fileName = ResourceUtil.getString("hrStaffPersonInfo.exportFileName", "hr.staff");
			// fileName = URLEncoder.encode(fileName, "UTF-8");
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			// 类名 用于
			String className = HrStaffPersonInfo.class.getName();
			// 调用通用导出方法
			this.exportDecode(response, this.getExportTitles(), modelList, fileName, BdExportConstant.FIX_EXCEL_07,
					className, null);
		} catch (Exception e) {
			logger.error("hrStaffPersonInfoServiceImp.exportList.error", e);
		}
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		try {
			if (event == null || event.getSource() == null) {
				return;
			}
			if (event instanceof SysOrgElementInvalidatedEvent || event instanceof SysOrgElementEffectivedEvent
					|| event instanceof HrStaffPersonInfoEvent) {
				HrStaffPersonInfo hrStaffPersonInfo = null;
				if (event instanceof SysOrgElementInvalidatedEvent) {
					SysOrgElement element = ((SysOrgElementInvalidatedEvent) event).getSysOrgElement();
					hrStaffPersonInfo = (HrStaffPersonInfo) this.findByPrimaryKey(element.getFdId(), null, true);
				}
				taskExecutor.execute(new SysTaskJobRunner(event, this, hrStaffPersonInfo));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class SysTaskJobRunner implements Runnable {
		private HrStaffPersonInfo hrStaffPersonInfo;
		private ApplicationEvent event;
		private HrStaffPersonInfoServiceImp service;

		public SysTaskJobRunner(ApplicationEvent event, HrStaffPersonInfoServiceImp service,
				HrStaffPersonInfo hrStaffPersonInfo) {
			this.event = event;
			this.service = service;
			this.hrStaffPersonInfo = hrStaffPersonInfo;
		}

		@Override
		public void run() {
			try {
				TransactionStatus status = null;
				try {
					if (event instanceof SysOrgElementInvalidatedEvent) {
						SysOrgElement element = ((SysOrgElementInvalidatedEvent) event).getSysOrgElement();
						if (HrStaffEventCache.getInstance().exist(element.getFdId(), "SysOrgElementInvalidatedEvent")) {
							return;
						}
						status = TransactionUtils.beginNewTransaction();
						if (element.getFdOrgType().intValue() == 8) {
							if (null != hrStaffPersonInfo) {
								// 人事档案中存在当前人员情况下：
								String fdStatus = hrStaffPersonInfo.getFdStatus();
								Date fdLeaveTime = hrStaffPersonInfo.getFdLeaveTime();
								// 如果当前状态不是离职，退休，解聘 更新状态为离职
								if (!"leave".equals(fdStatus) && !"dismissal".equals(fdStatus)
										&& !"retire".equals(fdStatus)) {
									fdStatus = "leave";
								}
								// 如果人事档案中当前人员是非离职状态要置为离职状态，或者离职时间为空，则离职时间新取当前时间，否则使用原离职时间
								if ("leave".equals(fdStatus) && null == fdLeaveTime) {
									fdLeaveTime = new Date();
								}
								// 组织架构置为无效，设置员工状态为“离职”
								NativeQuery personInfoNativeQuery = getBaseDao().getHibernateSession()
										.createNativeQuery(
												"update hr_staff_person_info set fd_status = ?,fd_leave_time = ? where fd_id = ?")
										.setParameter(0, fdStatus).setParameter(1, fdLeaveTime)
										.setParameter(2, element.getFdId());
								personInfoNativeQuery.addSynchronizedQuerySpace("hr_staff_person_info");
								personInfoNativeQuery.executeUpdate();

								// 修改任职截至时间为空的任职记录 为结束状态以及更新任职结束日期
								NativeQuery trackRecordNativeQuery = getBaseDao().getHibernateSession()
										.createNativeQuery(
												"update hr_staff_track_record set fd_status = ?,fd_entrance_endDate =? where fd_person_info_id = ? and fd_entrance_endDate is null ")
										.setParameter(0, "2").setParameter(1, fdLeaveTime)
										.setParameter(2, element.getFdId());
								trackRecordNativeQuery.addSynchronizedQuerySpace("hr_staff_track_record");
								trackRecordNativeQuery.executeUpdate();

							}
						}
						TransactionUtils.commit(status);
					} else if (event instanceof SysOrgElementEffectivedEvent) {
						SysOrgElement element = ((SysOrgElementEffectivedEvent) event).getSysOrgElement();
						if (HrStaffEventCache.getInstance().exist(element.getFdId(), "SysOrgElementEffectivedEvent")) {
							return;
						}
						status = TransactionUtils.beginNewTransaction();
						if (element.getFdOrgType().intValue() == 8) {
							// 组织架构置为有效，设置员工状态为“正式”
							NativeQuery personInfoNativeQuery = getBaseDao().getHibernateSession()
									.createNativeQuery(
											"update hr_staff_person_info set fd_status = 'official',fd_leave_time = null where fd_org_person_id = ? and fd_status in('dismissal','leave','retire') ")
									.setParameter(0, element.getFdId());
							personInfoNativeQuery.addSynchronizedQuerySpace("hr_staff_person_info");
							personInfoNativeQuery.executeUpdate();
						}
						TransactionUtils.commit(status);
					} else if (event instanceof HrStaffPersonInfoEvent) {
						HrStaffPersonInfoEvent hrStaffPersonInfoEvent = ((HrStaffPersonInfoEvent) event);
						HrStaffPersonInfo hrStaffPersonInfo = hrStaffPersonInfoEvent.getHrStaffPersonInfo();
						if (HrStaffEventCache.getInstance().exist(hrStaffPersonInfo.getFdId(),
								"HrStaffPersonInfoEvent")) {
							return;
						}
						status = TransactionUtils.beginNewTransaction();
						setPersonInfoOrg(hrStaffPersonInfo);
						TransactionUtils.commit(status);
					}
				} catch (Exception e) {
					logger.error("hrStaffPersonInfoServiceImp.updateDataToTask.更新任务信息出错", e);
					if (status != null) {
						TransactionUtils.rollback(status);
					}
				}
			} catch (Exception e) {
				logger.error("hrStaffPersonInfoServiceImp.SysTaskJobRunner- error", e);
			}
		}
	}

	/**
	 * <p>
	 * 设置当前人员部门、岗位信息
	 * </p>
	 *
	 * @throws Exception
	 */
	private void setPersonInfoOrg(HrStaffPersonInfo personInfo) throws Exception {
		if (null != personInfo.getFdOrgParent()) {
			personInfo.setFdParent(this.findOrgById(personInfo.getFdOrgParent().getFdId()));
		}
		if (!ArrayUtil.isEmpty(personInfo.getFdOrgPosts())) {
			List hrPosts = new ArrayList();
			for (SysOrgElement post : personInfo.getFdOrgPosts()) {
				HrOrganizationElement hrPost = this.findOrgById(post.getFdId());
				if (null != hrPost) {
					hrPosts.add(hrPost);
				}
			}
			personInfo.setFdPosts(hrPosts);
		}
	}

	/**
	 * 考虑到动态增加自定义字段，这里每次取值时都会重新处理
	 *
	 * @return
	 */
	@Override
	public String[] getImportFields(boolean isOrg) {
		// 导入的字段，模板通过员工账号作为唯一标识。
		// 字段包含 个人信息 和 联系信息：
		// 姓名、状态、出生日期、身份证号码、参加工作时间、到本单位时间、
		// 试用到期时间、用工期限、人员类别、曾用名、民族、
		// 政治面貌、入团日期、入党日期、最高学历、最高学位、
		// 婚姻状况、健康状况、身高、体重、现居地、
		// 籍贯、出生地、户口性质、户口所在地、户口所在派出所、
		// 办公地点、办公电话、紧急联系人、紧急联系人电话、其他联系方式；
		List<String> importFields = new ArrayList<String>();
		importFields.add("fdName");
		importFields.add("fdSex");
		importFields.add("fdDateOfBirth");
		importFields.add("fdNation");
		importFields.add("fdHighestEducation");
		importFields.add("fdMaritalStatus");
		importFields.add("fdNativePlace");
		importFields.add("fdWorkTime");
		importFields.add("fdFirstLevelDepartment");
		importFields.add("fdSecondLevelDepartment");
		importFields.add("fdThirdLevelDepartment");
		importFields.add("fdOrgPosts");
		importFields.add("fdAffiliatedCompany");
		importFields.add("fdOrgRank");
		importFields.add("fdStaffingLevel");
		importFields.add("fdReportLeader");
		importFields.add("fdStaffType");
		importFields.add("fdEntryTime");
		importFields.add("fdProposedEmploymentConfirmationDate");
		importFields.add("fdTrialExpirationTime");
		importFields.add("fdPositiveTime");
		importFields.add("fdProbationPeriod");
		importFields.add("fdResignationDate");
		importFields.add("fdStatus");
		importFields.add("fdContType");
		importFields.add("fdBeginDate");
		importFields.add("fdEndDate");
		importFields.add("fdContractUnit");
		importFields.add("fdCategory");
		 importFields.add("fdTimeCardNo");
		// if (isOrg) {
		 importFields.add("fdEmail");
		 importFields.add("fdMobileNo");
		 importFields.add("fdIdCard");
		 importFields.add("anquanjibie");
		 importFields.add("fdPostalAddress");
		 importFields.add("fdHomeAddress");
		// importFields.add("fdStaffingLevel");
		// importFields.add("hbmPosts");
		// importFields.add("fdOrgRank");
		// }
		// importFields.add("fdTimeOfEnterprise");
		//
		// importFields.add("fdNatureWork");
		// importFields.add("fdWorkAddress");
		// importFields.add("fdEmploymentPeriod");
		// 转正日期
		// 离职日期
		// importFields.add("fdLeaveTime");
		// 离职原因
		// importFields.add("fdLeaveReason");
		// importFields.add("fdDateOfBirth");
		// importFields.add("fdDateOfGroup");
		// importFields.add("fdDateOfParty");
		// importFields.add("fdHighestDegree");
		// importFields.add("fdHealth");
		// importFields.add("fdStature");
		// importFields.add("fdWeight");
		// importFields.add("fdPoliticalLandscape");
		// importFields.add("fdAccountProperties");
		// importFields.add("fdHomeplace");
		// importFields.add("fdRegisteredResidence");
		// importFields.add("fdResidencePoliceStation");
		// importFields.add("fdWorkPhone");
		// importFields.add("fdOfficeLocation");
		// importFields.add("fdLivingPlace");
		// importFields.add("fdEmergencyContact");
		// importFields.add("fdEmergencyContactPhone");
		// importFields.add("fdOtherContact");
		// importFields.add("fdIsBusiness");
		// importFields.add("fdCanLogin");

//		if (!isOrg) {
//			SysDictModel dictModel = SysDataDict.getInstance().getModel(HrStaffPersonInfo.class.getName());
//			List<SysDictCommonProperty> propertyList = dictModel.getPropertyList();
//			List<String> tempList = new ArrayList<String>();
//			for (SysDictCommonProperty prop : propertyList) {
//				// 导入模板增加自定义的字段
//				if (prop instanceof SysDictExtendDynamicProperty) {
//					tempList.add(prop.getName());
//				}
//			}
//			if (!tempList.isEmpty()) {
//				importFields.addAll(tempList);
//			}
//		}

		return importFields.toArray(new String[] {});
	}

	@Override
	public void exportMap(HttpServletRequest request, HttpServletResponse response,
			Map<String, List<HrStaffPersonInfo>> rtnMap) {
		try {
			List<String[]> modelList = new ArrayList<String[]>();
			List<String> keyList = new ArrayList<String>(rtnMap.keySet());
			Collections.sort(keyList);
			for (String key : keyList) {
				modelList.addAll(this.modelToArrayList(rtnMap.get(key)));
			}
			String fileName = ResourceUtil.getString("hrStaffPersonInfo.exportFileName", "hr.staff");
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			// 类名 用于
			String className = HrStaffPersonInfo.class.getName();
			// 调用通用导出方法
			this.exportDecode(response, this.getExportTitles(), modelList, fileName, BdExportConstant.FIX_EXCEL_07,
					className, null);
		} catch (Exception e) {
			logger.error("hrStaffPersonInfoServiceImp.exportMap- error", e);
		}
	}

	@Override
	public void updatePersonInfo(HrStaffPersonInfoForm personInfoForm, HttpServletRequest request) throws Exception {
		String fdId = personInfoForm.getFdId();
		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) findByPrimaryKey(fdId, HrStaffPersonInfo.class, true);
		
//		for(SysOrgPost sysOrgPost : oldPersonInfo.getFdPosts())
//			OrgPosts2.add(sysOrgPost);
		oldFdOrgPost = personInfo.getFdPosts();
		oldFdOrgRank = personInfo.getFdOrgRank();
		oldLeader = personInfo.getFdReportLeader();
		OrgPosts2 = personInfo.getFdPosts();
		oldFdParent=personInfo.getFdOrgParent();
		oldFdFirstLevelDepartment=personInfo.getFdFirstLevelDepartment();
		oldFdSecondLevelDepartment=personInfo.getFdSecondLevelDepartment();
		oldFdThirdLevelDepartment=personInfo.getFdThirdLevelDepartment();
		OrgPosts2 = personInfo.getFdPosts();
//		for(SysOrgPost sysOrgPost : oldPersonInfo.getFdPosts())
//			OrgPosts2.add(sysOrgPost);
		oldFdOrgPost = personInfo.getFdPosts();
		oldFdOrgRank = personInfo.getFdOrgRank();
		oldLeader = personInfo.getFdReportLeader();
		oldFdParent=personInfo.getFdOrgParent();
		

		HrStaffPersonInfoForm oldPersonInfoForm = (HrStaffPersonInfoForm) super.convertModelToForm(null, personInfo,
				new RequestContext(request));
		String type = request.getParameter("type");

		// 保存自定义信息
		if (!org.springframework.util.CollectionUtils.isEmpty(oldPersonInfoForm.getCustomPropMap())
				&& !"basic".equals(type)) {
			// 只有修改基本信息的时候才存在自定义属性，其他情况不存在自定义属性的修改
			personInfoForm.setCustomPropMap(oldPersonInfoForm.getCustomPropMap());
		}

		HrStaffPersonInfo newPersonInfo = null;
		newPersonInfo = (HrStaffPersonInfo) super.convertFormToModel(personInfoForm, newPersonInfo,
				new RequestContext(request));

		OrgPosts1 = newPersonInfo.getFdPosts();
		newLeader = newPersonInfo.getFdReportLeader();
		newLeader = newPersonInfo.getFdReportLeader();
		OrgPosts1 = newPersonInfo.getFdPosts();
		if ("status".equals(type)) {
			oldPersonInfoForm.setFdAccountFlag(personInfoForm.getFdAccountFlag());
			this.updatePersonLeave(oldPersonInfoForm);
		}
		String fdOrgPersonId = personInfoForm.getFdOrgPersonId();
		// 添加部门、岗位
		if (null != newPersonInfo.getFdParent()) {
			personInfo.setFdParent(newPersonInfo.getFdParent());
		}
		if (!ArrayUtil.isEmpty(newPersonInfo.getFdPosts())) {
			personInfo.setFdPosts(newPersonInfo.getFdPosts());
		}
		if (null != newPersonInfo.getFdOrgRank()) {
			personInfo.setFdOrgRank(newPersonInfo.getFdOrgRank());
		}
		SysOrgPerson fdOrgPerson = null;
		if (StringUtil.isNotNull(fdOrgPersonId)) {
			fdOrgPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(fdOrgPersonId);
			String fdNo = fdOrgPerson.getFdNo();
			boolean noRequired = new SysOrganizationConfig().isNoRequired();
			if (StringUtil.isNull(fdNo) && noRequired) {
				String fdStaffNo = personInfo.getFdStaffNo();
				fdNo = StringUtil.isNotNull(fdStaffNo) ? fdStaffNo
						: String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
				fdOrgPerson.setFdNo(fdNo);
			}
		}
		if ("basic".equals(type)) {
			// 基本信息的修改以前端传过来为准
			personInfo.setFdName(newPersonInfo.getFdName());
			personInfo.setFdSex(personInfoForm.getFdSex());
			personInfo.setFdDateOfParty(newPersonInfo.getFdDateOfParty());
			personInfo.setFdWeight(newPersonInfo.getFdWeight());
			personInfo.setFdStature(newPersonInfo.getFdStature());
			personInfo.setFdIdCard(newPersonInfo.getFdIdCard());
			personInfo.setFdDateOfGroup(newPersonInfo.getFdDateOfGroup());
			personInfo.setFdTimeOfEnterprise(newPersonInfo.getFdTimeOfEnterprise());
			personInfo.setFdWorkTime(newPersonInfo.getFdWorkTime());
			personInfo.setFdNameUsedBefore(newPersonInfo.getFdNameUsedBefore());
			personInfo.setFdDateOfBirth(newPersonInfo.getFdDateOfBirth());
			personInfo.setFdMaritalStatus(newPersonInfo.getFdMaritalStatus());
			personInfo.setFdNation(newPersonInfo.getFdNation());
			personInfo.setFdPoliticalLandscape(newPersonInfo.getFdPoliticalLandscape());
			personInfo.setFdNativePlace(newPersonInfo.getFdNativePlace());
			personInfo.setFdLivingPlace(newPersonInfo.getFdLivingPlace());
			personInfo.setFdHomeplace(newPersonInfo.getFdHomeplace());
			personInfo.setFdAccountProperties(newPersonInfo.getFdAccountProperties());
			personInfo.setFdRegisteredResidence(newPersonInfo.getFdRegisteredResidence());
			personInfo.setFdResidencePoliceStation(newPersonInfo.getFdResidencePoliceStation());
			personInfo.setFdHighestEducation(newPersonInfo.getFdHighestEducation());
			personInfo.setFdHighestDegree(newPersonInfo.getFdHighestDegree());
			personInfo.setFdHealth(newPersonInfo.getFdHealth());
			if (fdOrgPerson != null) {
				fdOrgPerson.setFdName(newPersonInfo.getFdName());
				fdOrgPerson.setFdSex(personInfoForm.getFdSex());
				sysOrgPersonService.update(fdOrgPerson);
			}
			// 覆盖附件对象
			AutoHashMap map = personInfo.getAttachmentForms();
			map.clear();
			map.putAll(personInfoForm.getAttachmentForms());
		} else if ("offical".equals(type)) {
			personInfo.setFdStaffNo(personInfoForm.getFdStaffNo());
			personInfo.setFdLoginName(personInfoForm.getFdLoginName());
			personInfo.setFdWorkAddress(newPersonInfo.getFdWorkAddress());
//			personInfo.setFdReportLeader(newPersonInfo.getFdReportLeader());
			personInfo.setFdHrReportLeader(newPersonInfo.getFdHrReportLeader());
			if (fdOrgPerson == null) {
				personInfo.setFdOrgParent(newPersonInfo.getFdOrgParent());
				personInfo.setFdStaffingLevel(newPersonInfo.getFdStaffingLevel());
				List<SysOrgPost> fdOrgPosts = personInfo.getFdOrgPosts();
				if (!ArrayUtil.isEmpty(fdOrgPosts)) {
					fdOrgPosts.clear();
					fdOrgPosts.addAll(newPersonInfo.getFdOrgPosts());
					personInfo.setFdOrgPosts(fdOrgPosts);
				}
				fdOrgPerson = new SysOrgPerson();
				fdOrgPerson.setFdId(personInfo.getFdId());
				fdOrgPerson.setFdPassword(PasswordUtil.desEncrypt(personInfoForm.getFdNewPassword()));
				fdOrgPerson.setFdName(oldPersonInfoForm.getFdName());
				fdOrgPerson.setFdSex(oldPersonInfoForm.getFdSex());
				fdOrgPerson.setFdMobileNo(oldPersonInfoForm.getFdMobileNo());
				fdOrgPerson.setFdEmail(oldPersonInfoForm.getFdEmail());
			}
			List<SysOrgPost> fdPosts = new ArrayList<SysOrgPost>();
			if (StringUtils.isBlank(personInfoForm.getFdOrgPostIds())) {
				fdPosts.addAll(newPersonInfo.getFdOrgPosts());
			} else {
				SysOrgPost orgPost = (SysOrgPost) sysOrgPostService.findByPrimaryKey(personInfoForm.getFdOrgPostIds());
				if (null != orgPost) {
					fdPosts.add(orgPost);
				}
			}
			fdOrgPerson.setFdPosts(fdPosts);
			personInfo.setHbmPosts(fdPosts);
			// 部门
			if (StringUtils.isBlank(personInfoForm.getFdOrgParentId())) {
				if (!StringUtils.isBlank(personInfoForm.getFdParentId())) {
					SysOrgElement sysdept = sysOrgCoreService.findByPrimaryKey(personInfoForm.getFdParentId(), null,
							true);
					if (null != sysdept) {
						fdOrgPerson.setFdParent(sysdept);
						fdOrgPerson.setHbmParentOrg(sysdept.getFdParentOrg());
					}
				} else {
					fdOrgPerson.setFdParent(newPersonInfo.getFdOrgParent());
					fdOrgPerson.setHbmParentOrg(newPersonInfo.getFdOrgParentOrg());
				}
			} else {
				SysOrgElement sysdept = sysOrgCoreService.findByPrimaryKey(personInfoForm.getFdOrgParentId(), null,
						true);
				if (null != sysdept) {
					fdOrgPerson.setFdParent(sysdept);
					fdOrgPerson.setHbmParentOrg(sysdept.getFdParentOrg());
				}
			}
			if (StringUtils.isBlank(personInfoForm.getFdStaffingLevelId())) {
				fdOrgPerson.setFdStaffingLevel(null);
			} else {
				SysOrganizationStaffingLevel staffingLevel = (SysOrganizationStaffingLevel) sysOrganizationStaffingLevelService
						.findByPrimaryKey(personInfoForm.getFdStaffingLevelId(), null, true);
				if (null != staffingLevel) {
					fdOrgPerson.setFdStaffingLevel(staffingLevel);
				}
			}
			fdOrgPerson.setFdLoginName(personInfoForm.getFdLoginName());
			fdOrgPerson.setFdIsBusiness(Boolean.parseBoolean(personInfoForm.getFdIsBusiness()));
			fdOrgPerson.setFdCanLogin(personInfoForm.getFdCanLogin());
			sysOrgPersonService.update(fdOrgPerson);

			personInfo.setFdOrgPerson(fdOrgPerson);
		} else if ("status".equals(type)) {
			// 员工状态
			personInfo.setFdStatus(newPersonInfo.getFdStatus());
			// 入职时间
			personInfo.setFdEntryTime(newPersonInfo.getFdEntryTime());
			// 试用期限(月)
			personInfo.setFdTrialOperationPeriod(newPersonInfo.getFdTrialOperationPeriod());
			// 转正时间
			personInfo.setFdPositiveTime(newPersonInfo.getFdPositiveTime());
			// 人员类别
			personInfo.setFdStaffType(newPersonInfo.getFdStaffType());
			// 工作性质
			personInfo.setFdNatureWork(newPersonInfo.getFdNatureWork());
			// 用工期限（年）
			personInfo.setFdEmploymentPeriod(newPersonInfo.getFdEmploymentPeriod());
			// 试用到期时间
			personInfo.setFdTrialExpirationTime(newPersonInfo.getFdTrialExpirationTime());
			// 实际离职日期
			personInfo.setFdLeaveTime(newPersonInfo.getFdLeaveTime());
		} else if ("leave".equals(type)) {
			personInfo.setFdLeaveTime(newPersonInfo.getFdLeaveTime());
			personInfo.setFdLeaveApplyDate(newPersonInfo.getFdLeaveApplyDate());
			personInfo.setFdLeaveSalaryEndDate(newPersonInfo.getFdLeaveSalaryEndDate());
			personInfo.setFdLeaveReason(newPersonInfo.getFdLeaveReason());
			personInfo.setFdLeaveRemark(newPersonInfo.getFdLeaveRemark());
			personInfo.setFdNextCompany(newPersonInfo.getFdNextCompany());
		} else if ("connect".equals(type)) {
			personInfo.setFdOfficeLocation(personInfoForm.getFdOfficeLocation());
			if (fdOrgPerson == null) {
				personInfo.setFdMobileNo(personInfoForm.getFdMobileNo());
				personInfo.setFdEmail(personInfoForm.getFdEmail());
				personInfo.setFdWorkPhone(personInfoForm.getFdWorkPhone());
			} else {
				fdOrgPerson.setFdMobileNo(personInfoForm.getFdMobileNo());
				fdOrgPerson.setFdEmail(personInfoForm.getFdEmail());
				fdOrgPerson.setFdWorkPhone(personInfoForm.getFdWorkPhone());
				sysOrgPersonService.update(fdOrgPerson);
			}
			personInfo.setFdEmergencyContact(personInfoForm.getFdEmergencyContact());
			personInfo.setFdEmergencyContactPhone(personInfoForm.getFdEmergencyContactPhone());
			personInfo.setFdOtherContact(personInfoForm.getFdOtherContact());
		}

		if(newPersonInfo.getFdOrgParent()!=oldFdParent || (oldFdOrgRank!=newPersonInfo.getFdOrgRank()) ||(!getDiffrent(OrgPosts1,OrgPosts2)||oldLeader!=newLeader))
			addMoveRecord(personInfo,newPersonInfo);
		HrStaffPersonInfoForm newPersonInfoForm = (HrStaffPersonInfoForm) convertModelToForm(personInfoForm, personInfo,
				new RequestContext(request));
		buildPersonInfoLog(oldPersonInfoForm, newPersonInfoForm, new RequestContext(request));
		update(personInfo);
		if (StringUtil.isNull(oldPersonInfoForm.getFdParentId())
				|| StringUtil.isNull(oldPersonInfoForm.getFdOrgParentId())) {
			addTrackRecord(personInfo);
		}
	}
	private static boolean getDiffrent(List list1, List list2) {


		if (list1.size() != list2.size()) {


		return false;

		}

		for (Object str : list1) {

		if (!list2.contains(str)) {


		return false;

		}

		}


		return true;


		}
	@Override
	public void update(IBaseModel modelObj) throws Exception {

		HrStaffPersonInfo personInfo = (HrStaffPersonInfo) modelObj;
		String fdStatus = personInfo.getFdStatus();
		if ("dismissal".equals(fdStatus) || "leave".equals(fdStatus) || "retire".equals(fdStatus)) {
			if (null == personInfo.getFdLeaveTime()) {
				personInfo.setFdLeaveTime(new Date());
			}
			personInfo.setFdIsAvailable(false);
			// 将人员的最后一个任职记录添加任职结束时间，并将任职状态改为已结束
			hrStaffTrackRecordService.updateLastTrackRecord(null, personInfo, personInfo.getFdLeaveTime());
			// 将人员所在组织架构中是组织者，组织领导的值清空
			getHrOrganizationElementService().updateHrOrgLeader(personInfo.getFdId());
		} else {
			HrStaffPersonInfo oldPersonInfo = (HrStaffPersonInfo) findByPrimaryKey(personInfo.getFdId(), HrStaffPersonInfo.class,
					true);
//			System.out.println(oldPersonInfo.getFdFirstLevelDepartment().getFdName());
			addTrackRecord(personInfo);
			
			// 非离职状态将离职日期清空。（清空系统组织表和HR表）
			if (personInfo.getFdOrgPerson() != null && personInfo.getFdOrgPerson().getFdLeaveDate() != null) {
				updateLevelTime(personInfo.getFdOrgPerson());
			}
			// 不是已离职也不是待离职
			if (!("1".equals(personInfo.getFdLeaveStatus()) || "2".equals(personInfo.getFdLeaveStatus()))) {
				personInfo.setFdLeaveTime(null);
				personInfo.setFdActualLeaveTime(null);
			}
			personInfo.setFdIsAvailable(true);
		}
		super.update(modelObj);
		/**
		 * 2021-07-07 by 王京 修改人员手机号码， 并且如果人员在系统组织架构中存在，这里没有关联的情况下，关联系统组织架构数据
		 */
		updateMobileNo(personInfo);
		if (personInfo.getFdOrgParent()!=null) {
			String updateSql = "update sys_org_element set fd_parentid =:fdParentId where fd_id =:fdId ";
			NativeQuery nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(updateSql)
					.setParameter("fdParentId", personInfo.getFdOrgParent().getFdId()).setParameter("fdId", personInfo.getFdId());
//			nativeQuery.addSynchronizedQuerySpace("sys_org_person");
			nativeQuery.executeUpdate();
			String updateSql1 = "update hr_org_element set fd_parentid =:fdParentId where fd_id =:fdId ";
			NativeQuery nativeQuery1= getBaseDao().getHibernateSession().createNativeQuery(updateSql1)
					.setParameter("fdParentId", personInfo.getFdOrgParent().getFdId()).setParameter("fdId", personInfo.getFdId());
//			nativeQuery.addSynchronizedQuerySpace("sys_org_person");
			nativeQuery1.executeUpdate();
		}
		/**
		 * 2021-07-16 屏蔽人员档案修改后记录到系统组织架构中 //添加系统日志
		 * if(personInfo.getRequestContext() !=null){
		 * addSysOrgMdifyLog(personInfo, personInfo.getRequestContext()); }else
		 * { addSysOrgMdifyLog(personInfo, new
		 * RequestContext(Plugin.currentRequest())); }
		 */
	}

	/**
	 * 人事档案修改手机号时，人员确认信息也对应修改手机号码。 主要是因为验证手机号码用了此手机号码作唯一验证
	 *
	 * @param info
	 * @throws Exception
	 */
	private void updateMobileNo(HrStaffPersonInfo info) throws Exception {
		String mobileNo = info.getFdMobileNo();
		if (StringUtil.isNull(mobileNo)) {
			return;
		}
		HrStaffEntry hrStaffEntry = info.getFdStaffEntry();
		if (hrStaffEntry != null) {
			hrStaffEntry.setFdName(info.getFdName());
			hrStaffEntry.setFdStaffNo(info.getFdStaffNo());
			hrStaffEntry.setFdPlanEntryDept(info.getFdOrgParent());
			// 更新岗位信息
			List posts = new ArrayList();
			List<IBaseModel> fdPosts = info.getFdPosts();
			if (!ArrayUtil.isEmpty(fdPosts)) {
				for (IBaseModel post : fdPosts) {
					SysOrgElement element = sysOrgCoreService.findByPrimaryKey(post.getFdId(), null, true);
					if (element != null) {
						posts.add(element);
					}
				}
			}
			if (!ArrayUtil.isEmpty(posts)) {
				hrStaffEntry.setFdOrgPosts(posts);
			}
			hrStaffEntry.setFdMobileNo(mobileNo);
			hrStaffEntry.setFdEmail(info.getFdEmail());
		}

		SysOrgPerson orgPerson = info.getFdOrgPerson();
		if (orgPerson == null) {
			SysOrgPerson orgPersonTemp = (SysOrgPerson) getSysOrgPersonService().findByPrimaryKey(info.getFdId(),
					SysOrgPerson.class, true);
			if (orgPersonTemp != null && !mobileNo.equals(orgPersonTemp.getFdMobileNo())) {
				String updateSql = "update sys_org_person set fd_mobile_no =:mobileNo where fd_id =:fdId ";
				NativeQuery nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(updateSql)
						.setParameter("mobileNo", mobileNo).setParameter("fdId", orgPersonTemp.getFdId());
				nativeQuery.addSynchronizedQuerySpace("sys_org_person");
				nativeQuery.executeUpdate();
				orgPersonTemp.setFdMobileNo(mobileNo);
				info.setFdOrgPerson(orgPersonTemp);
			}
		}
		// 如果人事流程中有数据也需要进行更新
		// HrStaffPersonUtil.updateRatifyMobile(info.getFdId(), mobileNo);
	}

	/**
	 * 清空系统组织架构中人员的离职日期信息
	 *
	 * @param fdOrgPerson
	 * @throws Exception
	 */
	private void updateLevelTime(SysOrgPerson fdOrgPerson) throws Exception {
		if (fdOrgPerson != null) {
			String updateSql = "update sys_org_person set fd_leave_date =null where fd_id =:fdId ";
			NativeQuery nativeQuery = getBaseDao().getHibernateSession().createNativeQuery(updateSql)
					.setParameter("fdId", fdOrgPerson.getFdId());
			nativeQuery.addSynchronizedQuerySpace("sys_org_person");
			nativeQuery.executeUpdate();

		}
	}

	@Override
	public void updatePersonInfo(HrStaffPersonInfo personInfo) throws Exception {
		super.update(personInfo);
	}

	@Override
	public HrStaffPersonInfo updateGetPersonInfo(String fdOrgPersonId) throws Exception {
		HrStaffPersonInfo modelObj = findByOrgPersonId(fdOrgPersonId);
		if (null == modelObj) {
			synchronized (this) {
				if (null == modelObj) {
					SysOrgPerson fdOrgPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(fdOrgPersonId,
							SysOrgPerson.class, true);
					modelObj = new HrStaffPersonInfo();
					modelObj.setFdId(fdOrgPersonId);
					modelObj.setFdOrgPerson(fdOrgPerson);
					modelObj.setFdStatus("official");
					modelObj.setFdHierarchyId(fdOrgPerson.getFdHierarchyId());
					modelObj.setFdName(fdOrgPerson.getFdName());
					modelObj.setFdLoginName(fdOrgPerson.getFdLoginName());
					add(modelObj);
					modelObj = (HrStaffPersonInfo) this.findByPrimaryKey(fdOrgPersonId, HrStaffPersonInfo.class, true);
				}
			}
		}
		return modelObj;
	}

	@Override
	public HrStaffPersonInfo findByStaffEntryId(String fdStaffEntryId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrStaffPersonInfo.fdStaffEntry.fdId = :fdStaffEntryId");
		hqlInfo.setParameter("fdStaffEntryId", fdStaffEntryId);

		List<HrStaffPersonInfo> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public JSONArray getStaffMobileIndex() throws Exception {
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		StringBuffer whereBlock = null;
		// 在职员工
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer(
				"hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')");
		hqlInfo.setWhereBlock(
				HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo).toString());
		List<Long> onPost = findValue(hqlInfo);
		obj = new JSONObject();
		obj.put("name", "onPost");
		obj.put("value", onPost.get(0));
		array.add(obj);
		// 全职员工
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer("hrStaffPersonInfo.fdStatus in ('official','temporary','trial','trialDelay')");
		hqlInfo.setWhereBlock(
				HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo).toString());
		List<Long> onFull = findValue(hqlInfo);
		obj = new JSONObject();
		obj.put("name", "onFull");
		obj.put("value", onFull.get(0));
		array.add(obj);
		// 正式员工
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer("hrStaffPersonInfo.fdStatus in('official','temporary')");
		hqlInfo.setWhereBlock(
				HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo).toString());
		List<Long> onFormal = findValue(hqlInfo);
		obj = new JSONObject();
		obj.put("name", "onFormal");
		obj.put("value", onFormal.get(0));
		array.add(obj);
		// 试用员工
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer("hrStaffPersonInfo.fdStatus in('trial','trialDelay')");
		hqlInfo.setWhereBlock(
				HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo).toString());
		List<Long> onTrial = findValue(hqlInfo);
		obj = new JSONObject();
		obj.put("name", "onTrial");
		obj.put("value", onTrial.get(0));
		array.add(obj);
		// 兼职员工
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer(
				"hrStaffPersonInfo.fdNatureWork like:fdNatureWork and hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')");
		hqlInfo.setWhereBlock(
				HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo).toString());
		hqlInfo.setParameter("fdNatureWork", "%兼职%");
		List<Long> onPart = findValue(hqlInfo);
		obj = new JSONObject();
		obj.put("name", "onPart");
		obj.put("value", onPart.get(0));
		array.add(obj);
		// 实习员工
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer("hrStaffPersonInfo.fdStatus=:fdStatus");
		hqlInfo.setWhereBlock(
				HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo).toString());
		hqlInfo.setParameter("fdStatus", "practice");
		List<Long> onPractive = findValue(hqlInfo);
		obj = new JSONObject();
		obj.put("name", "onPractive");
		obj.put("value", onPractive.get(0));
		array.add(obj);
		// 本月入职
		Date beginDay = DateUtil.getBeginDayOfMonth();
		Date endDay = DateUtil.getEndDayOfMonth();
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer(
				"hrStaffPersonInfo.fdEntryTime >=:beginDay and hrStaffPersonInfo.fdEntryTime <=:endDay");
		hqlInfo.setWhereBlock(
				HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo).toString());
		hqlInfo.setParameter("beginDay", beginDay);
		hqlInfo.setParameter("endDay", endDay);
		List<Long> onEntry = findValue(hqlInfo);
		obj = new JSONObject();
		obj.put("name", "onEntry");
		obj.put("value", onEntry.get(0));
		array.add(obj);
		// 本月离职
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer(
				"hrStaffPersonInfo.fdLeaveTime >=:beginDay and hrStaffPersonInfo.fdLeaveTime <=:endDay");
		hqlInfo.setWhereBlock(
				HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonInfo", hqlInfo).toString());
		hqlInfo.setParameter("beginDay", beginDay);
		hqlInfo.setParameter("endDay", endDay);
		List<Long> onLeave = findValue(hqlInfo);
		obj = new JSONObject();
		obj.put("name", "onLeave");
		obj.put("value", onLeave.get(0));
		array.add(obj);
		return array;
	}

	@Override
	public JSONObject getPersonStat(String parentId) throws Exception {
		JSONObject stat = new JSONObject();
		// 统计待入职人数
		Long entryNum = getHrStaffEntryServiceImp().getCountByDept(parentId);
		stat.accumulate("entry", entryNum != null ? entryNum : 0);
		// 统计离职人数
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer whereBlock = null;
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer(
				" hrStaffPersonInfo.hbmParent.fdId =:fdParentId and hrStaffPersonInfo.fdLeaveStatus=:leaveStatus and hrStaffPersonInfo.fdIsAvailable = :fdIsAvailable");
		hqlInfo.setParameter("fdParentId", parentId);
		hqlInfo.setParameter("leaveStatus", "1");
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setWhereBlock(whereBlock.toString());
		List<Long> leave = findValue(hqlInfo);
		stat.accumulate("leave", leave.get(0));
		// 统计在职人数
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = new StringBuffer(
				"hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')");
		whereBlock.append(
				" and (hrStaffPersonInfo.hbmParent.fdId=:fdParentId or hrStaffPersonInfo.fdHierarchyId like :fdHierarchyId)");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdParentId", parentId);
		hqlInfo.setParameter("fdHierarchyId", "%x" + parentId + "x%");
		List<Long> onAllPost = findValue(hqlInfo);
		stat.accumulate("onAllPost", onAllPost.get(0));
		// 统计部门编制人数
		HrOrganizationCompilingSum hrOrganizationCompilingSum = new HrOrganizationCompilingSum();
		List list = new ArrayList();
		if ("true".equals(hrOrganizationCompilingSum.getCompilationOfficial())) {
			list.add("official");
		}
		if ("true".equals(hrOrganizationCompilingSum.getCompilationTrial())) {
			list.add("trial");
		}
		if ("true".equals(hrOrganizationCompilingSum.getCompilationTrialDelay())) {
			list.add("trialDelay");
		}
		if ("true".equals(hrOrganizationCompilingSum.getCompilationPractice())) {
			list.add("practice");
		}
		if ("true".equals(hrOrganizationCompilingSum.getCompilationTemporary())) {
			list.add("temporary");
		}
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock.append(" and " + HQLUtil.buildLogicIN("hrStaffPersonInfo.fdStatus", list));
		whereBlock.append(
				" and (hrStaffPersonInfo.hbmParent.fdId=:fdParentId or hrStaffPersonInfo.fdHierarchyId like :fdHierarchyId)");
		hqlInfo.setWhereBlock(whereBlock.toString());
		hqlInfo.setParameter("fdParentId", parentId);
		hqlInfo.setParameter("fdHierarchyId", "%x" + parentId + "x%");
		List<Long> onCompiling = findValue(hqlInfo);
		stat.accumulate("onCompiling", onCompiling.get(0));

		// 统计兼岗人员
		List trackRecords = hrStaffTrackRecordService
				.findList("fdHrOrgDept.fdId = '" + parentId + "' and fdType = '2' and fdStatus = '1'", null);
		stat.accumulate("allPerson", onAllPost.get(0) + trackRecords.size());
		return stat;
	}

	/**
	 * <p>
	 * 定时任务，试用期到期自动转正
	 * </p>
	 *
	 * @throws Exception
	 * @author sunj
	 */
	@Override
	public void updatePersonInfPositive() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffPersonInfo.fdStatus in('trial','trialDelay','practice') and hrStaffPersonInfo.fdPositiveTime <= :currDate");
		hqlInfo.setParameter("currDate", new Date());
		List<HrStaffPersonInfo> personInfos = findList(hqlInfo);
		for (HrStaffPersonInfo info : personInfos) {
			info.setFdStatus("official");
			this.update(info);
		}
	}

	@Override
	public IExtendForm convertModelToForm(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {

		HrStaffPersonInfoForm personInfoForm = (HrStaffPersonInfoForm) super.convertModelToForm(form, model,
				requestContext);
		// 因为要与员工黄页的“标签”进行统一，所以这里需要另外获取标签数据
		personInfoForm.setSysTagMainForm(findTagByModelId(personInfoForm.getFdId(), requestContext.getRequest()));

		if (StringUtil.isNotNull(personInfoForm.getFdNation())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdNation",
					personInfoForm.getFdNation());
			if (null != settingNew) {
				personInfoForm.setFdNation(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdPoliticalLandscape())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
					.getByType("fdPoliticalLandscape", personInfoForm.getFdPoliticalLandscape());
			if (null != settingNew) {
				personInfoForm.setFdPoliticalLandscape(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdHighestEducation())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
					.getByType("fdHighestEducation", personInfoForm.getFdHighestEducation());
			if (null != settingNew) {
				personInfoForm.setFdHighestEducation(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdHighestDegree())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
					.getByType("fdHighestDegree", personInfoForm.getFdHighestDegree());
			if (null != settingNew) {
				personInfoForm.setFdHighestDegree(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdMaritalStatus())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
					.getByType("fdMaritalStatus", personInfoForm.getFdMaritalStatus());
			if (null != settingNew) {
				personInfoForm.setFdMaritalStatus(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdHealth())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdHealth",
					personInfoForm.getFdHealth());
			if (null != settingNew) {
				personInfoForm.setFdHealth(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdStaffType())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp().getByType("fdStaffType",
					personInfoForm.getFdStaffType());
			if (null != settingNew) {
				personInfoForm.setFdStaffType(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdNatureWork())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
					.getByType("fdNatureWork", personInfoForm.getFdNatureWork());
			if (null != settingNew) {
				personInfoForm.setFdNatureWork(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdWorkAddress())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
					.getByType("fdWorkAddress", personInfoForm.getFdWorkAddress());
			if (null != settingNew) {
				personInfoForm.setFdWorkAddress(settingNew.getFdName());
			}
		}
		if (StringUtil.isNotNull(personInfoForm.getFdLeaveReason())) {
			HrStaffPersonInfoSettingNew settingNew = getHrStaffPersonInfoSettingNewServiceImp()
					.getByType("fdLeaveReason", personInfoForm.getFdLeaveReason());
			if (null != settingNew) {
				personInfoForm.setFdLeaveReason(settingNew.getFdName());
			}
		}
		return personInfoForm;
	}

	private ISysTagMainService sysTagMainService;

	public ISysTagMainService getSysTagMainService() {
		return sysTagMainService;
	}

	public void setSysTagMainService(ISysTagMainService sysTagMainService) {
		this.sysTagMainService = sysTagMainService;
	}

	/**
	 * 根据modelID获取标签（view页面）
	 *
	 * @param modelId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private SysTagMainForm findTagByModelId(String modelId, HttpServletRequest request) throws Exception {
		SysTagMainForm tagForm = null;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysTagMain.fdModelId = :fdModelId and sysTagMain.fdModelName = :fdModelName");
		hqlInfo.setOrderBy("sysTagMain.docAlterTime desc");
		hqlInfo.setParameter("fdModelId", modelId);
		hqlInfo.setParameter("fdModelName", "com.landray.kmss.sys.zone.model.SysZonePersonInfo");
		List<SysTagMain> list = getSysTagMainService().findPage(hqlInfo).getList();
		if (!list.isEmpty()) {
			SysTagMain sysTagMain = list.get(0);
			tagForm = (SysTagMainForm) getSysTagMainService().convertModelToForm(null, sysTagMain,
					new RequestContext(request));
			String name = "";
			List<SysTagMainRelation> relationList = sysTagMain.getSysTagMainRelationList();
			if (relationList != null && !relationList.isEmpty()) {
				for (SysTagMainRelation sysTagMainRelation : relationList) {
					if (!"true".equals(String.valueOf(sysTagMainRelation.getDocIsDelete()))) {
						name = name + sysTagMainRelation.getFdTagName() + ";";
					}
				}
				int l = name.length() - 1 < 0 ? 0 : name.length() - 1;
				name = name.substring(0, l);
			}
			tagForm.setFdTagNames(name);
		}
		return tagForm;
	}

	private String buildWhereBlock(String whereBlock, String[] ids, HQLInfo hqlInfo) {
		if (ids != null && ids.length > 0) {
			int length = ids.length;
			whereBlock += " and (";
			for (int i = 0; i < length; i++) {
				if (i != (length - 1)) {
					whereBlock += "hrStaffPersonInfo.fdHierarchyId like:id" + i + " or ";
				} else {
					whereBlock += "hrStaffPersonInfo.fdHierarchyId like:id" + i;
				}
				hqlInfo.setParameter("id" + i, "%" + ids[i] + "%");
			}
			whereBlock += ")";
		}
		return whereBlock;
	}

	@Override
	public JSONArray getStaffMobileStat(String[] ids) throws Exception {
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		// 当前在职
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		String whereBlock = "hrStaffPersonInfo.fdStatus in ('trial','official','temporary','trialDelay','practice')";
		whereBlock = buildWhereBlock(whereBlock, ids, hqlInfo);
		hqlInfo.setWhereBlock(HrStaffAuthorityUtil
				.builtWhereBlock(new StringBuffer(whereBlock), "hrStaffPersonInfo", hqlInfo).toString());
		List<Long> onPost = findValue(hqlInfo);
		Long onPostValue = onPost.get(0);
		obj = new JSONObject();
		obj.put("name", "current");
		obj.put("value", onPostValue);
		array.add(obj);
		// 本月初在职=当前在职+本月离职-本月入职
		// 本月离职
		Date beginDay = DateUtil.getBeginDayOfMonth();
		Date endDay = DateUtil.getEndDayOfMonth();
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = "hrStaffPersonInfo.fdLeaveTime >=:beginDay and hrStaffPersonInfo.fdLeaveTime <=:endDay";
		whereBlock = buildWhereBlock(whereBlock, ids, hqlInfo);
		hqlInfo.setWhereBlock(HrStaffAuthorityUtil
				.builtWhereBlock(new StringBuffer(whereBlock), "hrStaffPersonInfo", hqlInfo).toString());
		hqlInfo.setParameter("beginDay", beginDay);
		hqlInfo.setParameter("endDay", endDay);
		List<Long> onLeave = findValue(hqlInfo);
		Long onLeaveValue = onLeave.get(0);
		// 本月入职
		hqlInfo = new HQLInfo();
		hqlInfo.setGettingCount(true);
		whereBlock = "hrStaffPersonInfo.fdEntryTime >=:beginDay and hrStaffPersonInfo.fdEntryTime <=:endDay";
		whereBlock = buildWhereBlock(whereBlock, ids, hqlInfo);
		hqlInfo.setWhereBlock(HrStaffAuthorityUtil
				.builtWhereBlock(new StringBuffer(whereBlock), "hrStaffPersonInfo", hqlInfo).toString());
		hqlInfo.setParameter("beginDay", beginDay);
		hqlInfo.setParameter("endDay", endDay);
		List<Long> onEntry = findValue(hqlInfo);
		Long onEntryValue = onEntry.get(0);
		Long beginOnPostValue = onPostValue + onLeaveValue - onEntryValue;
		obj = new JSONObject();
		obj.put("name", "early");
		obj.put("value", beginOnPostValue);
		array.add(obj);
		return array;
	}

	@Override
	public WorkBook exportContract(HttpServletRequest request) throws Exception {
		String[] baseColumns = null;
		String fdSignType = request.getParameter("fdSignType");
		if ("0".equals(fdSignType)) {
			baseColumns = new String[] { getStr("hrStaffPersonInfo.fdName"), // 姓名
					getStr("hrStaffPersonInfo.fdStaffNo"), // 工号
					getStr("hrStaffPersonInfo.fdOrgParent"), // 所在部门
					getStr("hrStaffPersonInfo.fdStatus"), // 员工状态
					getStr("hrStaffPersonInfo.contract.sign.status"), // 合同签订情况
					getStr("hrStaffPersonInfo.contract.sign.history") // 历史签订情况
			};
		} else {
			baseColumns = new String[] { getStr("hrStaffPersonInfo.fdName"), // 姓名
					getStr("hrStaffPersonInfo.fdStaffNo"), // 工号
					getStr("hrStaffPersonInfo.fdOrgParent"), // 所在部门
					getStr("hrStaffPersonInfo.fdStatus"), // 员工状态
					getStr("hrStaffPersonInfo.contract.sign.status"), // 合同签订情况
					getStr("hrStaffPersonExperience.contract.fdContType"), // 合同类型
					getStr("hrStaffPersonExperience.contract.fdSignType"), // 签订标识
					getStr("hrStaffPersonExperience.contract.fdContStatus"), // 合同状态
					getStr("hrStaffPersonInfo.contract.time"), // 合同时间
					getStr("hrStaffPersonInfo.contract.sign.time") // 合同签订时间
			};
		}
		WorkBook wb = new WorkBook();
		String filename = "人事合同信息";
		wb.setLocale(request.getLocale());
		com.landray.kmss.util.excel.Sheet sheet = new com.landray.kmss.util.excel.Sheet();
		sheet.setTitle(filename);
		for (int i = 0; i < baseColumns.length; i++) {
			Column col = new Column();
			col.setTitle(baseColumns[i]);
			sheet.addColumn(col);
		}
		List contentList = new ArrayList();
		Object[] objs = null;
		String ids = request.getParameter("ids");
		if (StringUtil.isNotNull(ids)) {
			String[] arrIds = ids.split(";");
			List<HrStaffPersonInfo> personList = findByPrimaryKeys(arrIds);
			for (HrStaffPersonInfo person : personList) {
				objs = new Object[sheet.getColumnList().size()];
				objs[0] = person.getFdName();
				objs[1] = person.getFdStaffNo();
				objs[2] = person.getFdOrgParentsName();
				objs[3] = ResourceUtil.getString("hrStaffPersonInfo.fdStatus." + person.getFdStatus(), "hr-staff");
				if ("0".equals(fdSignType)) {
					objs[4] = "未签订";
					List<HrStaffPersonExperienceContract> contractList = hrStaffPersonExperienceContractService
							.findContractListByPersonId(person.getFdId());
					if (contractList.size() > 0) {
						StringBuffer sb = new StringBuffer();
						for (HrStaffPersonExperienceContract contract : contractList) {
							String status = "";
							String fdContStatus = contract.getFdContStatus();
							if (StringUtil.isNotNull(fdContStatus)) {
								status = ResourceUtil.getString(
										"hrStaffPersonExperience.contract.fdContStatus." + fdContStatus, "hr-staff");
							} else {
								status = ResourceUtil.getString("hrStaffPersonExperience.contract.fdContStatus.1",
										"hr-staff");
							}
							sb.append(contract.getFdName() + " " + status + " \n");
						}
						objs[5] = sb.toString();
					} else {
						objs[5] = " - ";
					}
				} else {
					objs[4] = "已签订";
					HrStaffPersonExperienceContract contract = hrStaffPersonExperienceContractService
							.findContractByPersonId(person.getFdId());
					objs[5] = ResourceUtil.getString(
							"hrStaffPersonExperience.contract.fdContType." + contract.getFdContType(), "hr-staff");
					objs[6] = ResourceUtil.getString(
							"hrStaffPersonExperience.contract.fdSignType." + contract.getFdSignType(), "hr-staff");
					objs[7] = ResourceUtil.getString(
							"hrStaffPersonExperience.contract.fdContStatus." + contract.getFdContStatus(), "hr-staff");
					String endDate = "";
					if (Boolean.TRUE.equals(contract.getFdIsLongtermContract())) {
						endDate = ResourceUtil
								.getString("hr-staff:hrStaffPersonExperience.contract.fdIsLongtermContract.1");
					} else {
						endDate = DateUtil.convertDateToString(contract.getFdEndDate(), DateUtil.TYPE_DATE,
								request.getLocale());
					}
					objs[8] = DateUtil.convertDateToString(contract.getFdBeginDate(), DateUtil.TYPE_DATE,
							request.getLocale()) + " ~ " + endDate;
					objs[9] = DateUtil.convertDateToString(contract.getFdCreateTime(), DateUtil.TYPE_DATE,
							request.getLocale());
				}
				contentList.add(objs);
			}
		}
		sheet.setContentList(contentList);
		wb.addSheet(sheet);
		wb.setFilename(filename);
		return wb;
	}

	private String getStr(String key) {
		return ResourceUtil.getString(key, "hr-staff");
	}

	@Override
	public void setSalarySchedulerJob(SysQuartzJobContext context) {
		try {
			String parameter = context.getParameter();
			String[] strArr = parameter.split(";");
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) findByPrimaryKey(strArr[0]);
			HrStaffEmolumentWelfareDetalied detail = (HrStaffEmolumentWelfareDetalied) getHrStaffEmolumentWelfareDetaliedService()
					.findByPrimaryKey(strArr[1]);
			hrStaffPersonInfo.setFdSalary(detail.getFdAfterEmolument());
			// update(hrStaffPersonInfo);

			super.update(hrStaffPersonInfo);

			/** 设置明细生效 */
			detail.setFdIsEffective(Boolean.TRUE);
			getHrStaffEmolumentWelfareDetaliedService().update(detail);

		} catch (Exception e) {
			logger.error("设置员工薪资操作出错", e);
		}
	}

	@Override
	public Map<String, String> getPersonNum(String fdId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 在职人数
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join hrStaffPersonInfo.hbmPosts fdPost");
		hqlInfo.setWhereBlock("hrStaffPersonInfo.fdStatus in ('official', 'trial', 'practice', 'trialDelay') and ("
				+ HQLUtil.buildLogicIN("fdPost.fdId", Arrays.asList(fdId)) + ")");
		hqlInfo.setSelectBlock("count(*)");
		List list = this.findList(hqlInfo);
		map.put("onpost", ArrayUtil.isEmpty(list) ? "0" : list.get(0).toString());
		// 待入职
		hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join hrStaffEntry.fdOrgPosts fdPost");
		hqlInfo.setWhereBlock(HQLUtil.buildLogicIN("fdPost.fdId", Arrays.asList(fdId)));
		hqlInfo.setSelectBlock("count(*)");
		list = getHrStaffEntryServiceImp().findList(hqlInfo);
		map.put("waitentry", ArrayUtil.isEmpty(list) ? "0" : list.get(0).toString());
		// 待离职
		hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock("left join hrStaffPersonInfo.hbmPosts fdPost");
		hqlInfo.setWhereBlock("hrStaffPersonInfo.fdLeaveStatus = '1' and ("
				+ HQLUtil.buildLogicIN("fdPost.fdId", Arrays.asList(fdId)) + ")");
		hqlInfo.setSelectBlock("count(*)");
		list = this.findList(hqlInfo);
		map.put("waitleave", ArrayUtil.isEmpty(list) ? "0" : list.get(0).toString());
		map.put("totalNum", map.get("onpost") + map.get("waitentry") + map.get("waitleave"));
		return map;
	}

	@Override
	public Page findPersonList(HQLInfo hqlInfo, HttpServletRequest request) throws Exception {
		Page page = Page.getEmptyPage();
		Map<String, Object> map = null;
		List<HrStaffTrackRecord> records = new ArrayList<HrStaffTrackRecord>();
		List<HrStaffPersonInfo> persons = new ArrayList<HrStaffPersonInfo>();
		List list = new ArrayList();
		String queryPerson = request.getParameter("queryPerson");
		String fdType = request.getParameter("q.fdType");
		if (StringUtil.isNull(fdType) || "1".equals(fdType)) {
			String order = hqlInfo.getOrderBy();
			if (StringUtil.isNotNull(order)) {
				order += ", hrStaffPersonInfo.fdCreateTime desc";
			} else {
				order = "hrStaffPersonInfo.fdCreateTime desc";
			}
			hqlInfo.setOrderBy(order);
			persons = this.findList(hqlInfo);
		}
		if (StringUtil.isNull(fdType) || "2".equals(fdType)) {
			// 查询兼岗数据
			if (StringUtil.isNotNull(queryPerson)) {
				HQLInfo hql = new HQLInfo();
				StringBuffer whereBlock = new StringBuffer("1=1");
				String fdName = request.getParameter("q.fdName");
				if (StringUtil.isNotNull(fdName)) {
					whereBlock.append(" and hrStaffTrackRecord.fdPersonInfo.fdName like:fdName");
					hql.setParameter("fdName", "%" + fdName + "%");
				}

				String[] _fdStatus = request.getParameterValues("q._fdStatus");
				if (_fdStatus != null && _fdStatus.length > 0) {
					List<String> fdStatus = new ArrayList<String>();
					boolean isNull = false;
					for (String _fdStatu : _fdStatus) {
						if ("official".equals(_fdStatu)) {
							isNull = true;
						}
						fdStatus.add(_fdStatu);
					}
					whereBlock.append(" and (hrStaffTrackRecord.fdPersonInfo.fdStatus in (:fdStatus)");
					if (isNull) {
						whereBlock.append(" or hrStaffTrackRecord.fdPersonInfo.fdStatus is null");
					}
					whereBlock.append(")");
					hql.setParameter("fdStatus", fdStatus);
				}
				whereBlock.append(
						" and hrStaffTrackRecord.fdHrOrgDept.fdId=:fdParentId and hrStaffTrackRecord.fdType = '2' and hrStaffTrackRecord.fdStatus ='1'");
				hql.setWhereBlock(whereBlock.toString());
				hql.setParameter("fdParentId", queryPerson);
				records = hrStaffTrackRecordService.findList(hql);
			}
		}

		if (!ArrayUtil.isEmpty(persons) || !ArrayUtil.isEmpty(records)) {
			page.setRowsize(hqlInfo.getRowSize());
			page.setPageno(hqlInfo.getPageNo());
			page.setTotalrows(persons.size() + records.size());
			page.setOrderby(hqlInfo.getOrderBy());
			page.excecute();
			for (HrStaffPersonInfo personInfo : persons) {
				map = new HashMap<String, Object>();
				map.put("fdPerson", personInfo);
				map.put("fdType", "1");
				map.put("modelName", "com.landray.kmss.hr.staff.mode.HrStaffPersonInfo");
				map.put("fdOrder", personInfo.getFdOrder());
				map.put("fdId", personInfo.getFdId());
				map.put("fdOrgPost",
						ArrayUtil.isEmpty(personInfo.getFdPosts()) ? null : personInfo.getFdPosts().get(0));
				list.add(map);
			}
			for (HrStaffTrackRecord record : records) {
				map = new HashMap<String, Object>();
				map.put("fdId", record.getFdPersonInfo().getFdId());
				map.put("fdPerson", record.getFdPersonInfo());
				map.put("fdType", "2");
				map.put("modelName", "com.landray.kmss.hr.staff.mode.HrStaffTrackRecord");
				map.put("fdOrder", record.getFdOrder());
				map.put("fdOrgPost", record.getFdHrOrgPost());
				list.add(map);
			}
			int endIndex = list.size() > page.getStart() + page.getRowsize() ? page.getStart() + page.getRowsize()
					: list.size();
			list = list.subList(page.getStart(), endIndex);
			page.setList(list);
		}
		return page;
	}

	boolean lockUpdateSyncHireDate = false;

	@Override
	public void updateSyncHireDate() throws Exception {
		String temp = null;
		if (lockUpdateSyncHireDate) {
			temp = "存在运行中的 在职日期同步，当前任务中断...";
			logger.error(temp);
			return;
		}
		lockUpdateSyncHireDate = true;
		try {
			temp = "==========开始同步在职日期===============";
			logger.debug(temp);
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("hrStaffPersonInfo.fdOrgPerson.fdId,hrStaffPersonInfo.fdEntryTime");
			hqlInfo.setWhereBlock(
					"hrStaffPersonInfo.fdOrgPerson is not null and hrStaffPersonInfo.fdEntryTime is not null");

			// List<HrStaffPersonInfo> personInfoList = findList(hqlInfo);
			int rowsize = 100;
			int pageno = 1;
			hqlInfo.setOrderBy("hrStaffPersonInfo.fdId desc");
			hqlInfo.setPageNo(pageno);
			hqlInfo.setRowSize(rowsize);
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			Page personPage = findPage(hqlInfo);
			int totalPage = personPage.getTotal();
			int totalRows = personPage.getTotalrows();
			for (int i = 0; i < totalPage; i++) {
				int currentPage = pageno + i;
				// 从第二页开始
				if (i > 0) {
					hqlInfo.setPageNo(currentPage);
					hqlInfo.setRowSize(rowsize);
					personPage = findPage(hqlInfo);
				}
				List<Object> personInfoList = personPage.getList();
				if (CollectionUtils.isNotEmpty(personInfoList)) {
					temp = "==========同步在职日期第%s页,%s条===============";
					logger.debug(String.format(temp, currentPage, personInfoList.size()));
					CountDownLatch latch = new CountDownLatch(personInfoList.size());
					for (Object personInfo : personInfoList) {
						taskExecutor.execute(new SyncHireDate(personInfo, sysOrgPersonService, latch));
					}
					latch.await();
				}
			}
			temp = "==========同步在职日期结束===============总同步条数:%s";
			logger.debug(String.format(temp, totalRows));
		} finally {
			lockUpdateSyncHireDate = false;
		}
	}

	class SyncHireDate implements Runnable {

		private Object personInfo;
		CountDownLatch latch;
		private ISysOrgPersonService service;

		public SyncHireDate(Object personInfo, ISysOrgPersonService service, CountDownLatch latch) {
			this.personInfo = personInfo;
			this.service = service;
			this.latch = latch;
		}

		@Override
		public void run() {
			// 单条为一个事务处理
			TransactionStatus status = null;
			try {
				if (personInfo != null) {
					Object[] objArr = (Object[]) personInfo;
					status = TransactionUtils.beginNewTransaction();
					// 因为这两个条件再查询语句中都判断不为空。所以这里强转类型
					String fdOrgPersonId = (String) objArr[0];
					Date fdEntryTime = (Date) objArr[1];
					SysOrgPerson fdOrgPerson = (SysOrgPerson) sysOrgPersonService.findByPrimaryKey(fdOrgPersonId, null,
							true);
					if (fdOrgPerson.getFdHiredate() == null || !fdOrgPerson.getFdHiredate().equals(fdEntryTime)) {
						fdOrgPerson.setFdHiredate(fdEntryTime);
						fdOrgPerson.setFdAlterTime(new Date());
						service.update(fdOrgPerson);
					}
					TransactionUtils.commit(status);
				}
			} catch (Exception e) {
				logger.error("hrStaffPersonInfoServiceImp.SyncHireDate error :", e);
				if (status != null) {
					TransactionUtils.rollback(status);
				}
			} finally {
				this.latch.countDown();
			}
		}
	}

	/**
	 * 同步离职日期
	 */
	@Override
	public void updateSyncLeaveDate(SysQuartzJobContext context) throws Exception {
		String temp = null;
		if (locked) {
			temp = "存在运行中的人事组织架构到EKP组织架构同步任务，当前任务中断...";
			logger.error(temp);
			context.logError(temp);
			return;
		}
		locked = true;
		this.jobContext = context;
		try {
			temp = "==========开始同步人事组织离职日期到EKP组织架构===============";
			logger.debug(temp);
			context.logMessage(temp);

			HrStaffSyncLeaveConfig hrStaffSyncLeaveConfig = new HrStaffSyncLeaveConfig();

			long alltime = System.currentTimeMillis();
			// 获取人事组织架构需要同步的数据
			long caltime = System.currentTimeMillis();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setJoinBlock(" inner join hrStaffPersonInfo.fdOrgPerson ");
			hqlInfo.setWhereBlock(
					"hrStaffPersonInfo.fdOrgPerson is not null and hrStaffPersonInfo.fdLeaveTime is not null and hrStaffPersonInfo.fdAlterTime >=:fdLeaveTime");
			hqlInfo.setParameter("fdLeaveTime", DateUtil
					.convertStringToDate(hrStaffSyncLeaveConfig.getStaffLastSyncLeaveDate(), "yyyy-MM-dd HH:mm:ss"));
			List<HrStaffPersonInfo> personInfoList = findList(hqlInfo);

			temp = "获取人事组织离职人员数据耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
			logger.debug(temp);
			context.logMessage(temp);
			caltime = System.currentTimeMillis();
			if (personInfoList != null && !personInfoList.isEmpty()) {
				syncLeaveDate(personInfoList);
			}
			temp = "更新离职人员离职日期耗时(秒)：" + (System.currentTimeMillis() - caltime) / 1000;
			logger.debug(temp);
			context.logMessage(temp);
			temp = "整个任务总耗时(秒)：" + (System.currentTimeMillis() - alltime) / 1000;
			logger.debug(temp);
			// 保存时间
			hrStaffSyncLeaveConfig
					.setStaffLastSyncLeaveDate(DateUtil.convertDateToString(new Date(alltime), "yyyy-MM-dd HH:mm:ss"));
			hrStaffSyncLeaveConfig.save();
			context.logMessage(temp);
		} catch (Exception ex) {
			logger.error("hrStaffPersonInfoServiceImp.updateSyncLeaveDate.error", ex);
			if (context != null) {
				context.logError(ex);
			}
		} finally {
			locked = false;
		}
	}

	private void syncLeaveDate(List<HrStaffPersonInfo> elements) throws Exception {
		String logInfo = null;
		int rowsize = 300;
		int count = elements.size() % rowsize == 0 ? elements.size() / rowsize : elements.size() / rowsize + 1;

		logInfo = "本次同步总数据:" + elements.size() + "条,将执行" + count + "次分批同步,每次" + rowsize + "条";
		logger.debug(logInfo);
		jobContext.logMessage(logInfo);

		CountDownLatch countDownLatch = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			List<HrStaffPersonInfo> tempPersons = null;
			logger.debug("执行同步第" + (i + 1) + "批");
			if (elements.size() > rowsize * (i + 1)) {
				tempPersons = elements.subList(rowsize * i, rowsize * (i + 1));
			} else {
				tempPersons = elements.subList(rowsize * i, elements.size());
			}
			taskExecutor.execute(new SysUpdateLeaveDateRunner(tempPersons, countDownLatch));
		}
		try {
			countDownLatch.await(30, TimeUnit.MINUTES);
		} catch (InterruptedException exc) {
			logger.error("hrStaffPersonInfoServiceImp.syncLeaveDate.error", exc);
		}
	}

	class SysUpdateLeaveDateRunner implements Runnable {
		private final List<HrStaffPersonInfo> element;
		CountDownLatch countDownLatch;

		public SysUpdateLeaveDateRunner(List<HrStaffPersonInfo> element, CountDownLatch countDownLatch) {
			this.element = element;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			logger.debug("启动线程：" + Thread.currentThread().getName());
			TransactionStatus status = null;
			try {
				status = TransactionUtils.beginNewTransaction();

				List<String> collect = element.stream().map(item -> item.getFdId()).collect(Collectors.toList());
				HQLInfo hqlInfo = new HQLInfo();
				String where = "sysOrgPerson.fdId in (:fdIds)";
				hqlInfo.setWhereBlock(where);
				hqlInfo.setParameter("fdIds", collect);
				List<SysOrgPerson> sysOrgPersonList = sysOrgPersonService.findList(hqlInfo);
				Map<String, SysOrgPerson> sysOrgPersonMaps = sysOrgPersonList.stream()
						.collect(Collectors.toMap(SysOrgPerson::getFdId, item -> item));
				int count = 0;
				for (HrStaffPersonInfo personInfo : element) {
					if (personInfo.getFdOrgPerson() != null
							&& StringUtil.isNotNull(personInfo.getFdOrgPerson().getFdId())) {
						SysOrgPerson sysOrgPerson = sysOrgPersonMaps.get(personInfo.getFdOrgPerson().getFdId());
						if (sysOrgPerson != null
								&& !personInfo.getFdLeaveTime().equals(sysOrgPerson.getFdLeaveDate())) {
							// sysOrgPerson.setFdLeaveDate(personInfo.getFdLeaveTime());
							// sysOrgPerson.setFdAlterTime(new Date());
							// 采用指定参数更新
							String hql = "update SysOrgPerson sop set sop.fdLeaveDate=:fdLeaveDate,sop.fdAlterTime=:fdAlterTime where sop.fdId=:fdId";
							Query query = sysOrgPersonService.getBaseDao().getSession().createQuery(hql);
							query.setParameter("fdLeaveDate", personInfo.getFdLeaveTime());
							query.setParameter("fdAlterTime", new Date());
							query.setParameter("fdId", sysOrgPerson.getFdId());
							query.executeUpdate();
							// sysOrgPersonService.update(sysOrgPerson);
							count++;
							// 50个刷新session一次，避免占用内存,50的来源是根据hibernate配置得到的
							if (count % 50 == 0) {
								sysOrgPersonService.flushHibernateSession();
								sysOrgPersonService.clearHibernateSession();
							}
						}
					}
				}
				TransactionUtils.getTransactionManager().commit(status);
			} catch (Exception e) {
				logger.error("hrStaffPersonInfoServiceImp.SysUpdateLeaveDateRunner.run.error", e);
				TransactionUtils.getTransactionManager().rollback(status);

			} finally {
				countDownLatch.countDown();
				logger.debug("线程" + Thread.currentThread().getName() + "执行完成!");
			}
		}
	}

	@Override
	public JSONObject findWorkDaysByPersonInfo() throws Exception {
		KMSSUser user = UserUtil.getKMSSUser();
		JSONObject obj = new JSONObject();
		if (null == user) {
			return obj;
		}
		Date fdEntryTime = null;
		Long dayNumberOne = 0L;
		try {
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) this.findByPrimaryKey(user.getUserId());
			if (null != hrStaffPersonInfo) {
				fdEntryTime = ((null == hrStaffPersonInfo.getFdEntryTime()) ? hrStaffPersonInfo.getFdTimeOfEnterprise()
						: hrStaffPersonInfo.getFdEntryTime());
				if (null != fdEntryTime) {
					Date nowDate = new Date();
					long days = 24L * 60L * 60L * 1000L;
					// 入职当天和当前天数都算进来，所以需+1
					dayNumberOne = ((nowDate.getTime() + 1000 - fdEntryTime.getTime()) / days) + 1;
				}
				obj.accumulate("userId", hrStaffPersonInfo.getFdId());
				obj.accumulate("days", dayNumberOne);
				obj.accumulate("userName", hrStaffPersonInfo.getFdName());
				String fdParentsName = "";
				if (null != hrStaffPersonInfo.getFdParent()) {
					fdParentsName = hrStaffPersonInfo.getFdParent().getFdName();
				}
				obj.accumulate("dept", fdParentsName);

				String org = "";
				if (null != hrStaffPersonInfo.getFdParentOrg()) {
					org = hrStaffPersonInfo.getFdParentOrg().getFdName();
				}
				obj.accumulate("org", org);

				StringBuffer sb = new StringBuffer("");
				if (null != hrStaffPersonInfo.getFdPosts() && hrStaffPersonInfo.getFdPosts().size() > 0) {
					List<HrOrganizationElement> fdPosts = hrStaffPersonInfo.getFdPosts();
					for (HrOrganizationElement element : fdPosts) {
						if (StringUtil.isNotNull(sb.toString())) {
							sb.append(",");
						}
						if (null != element) {
							sb.append(element.getFdName());
						}
					}
				}
				obj.accumulate("post", sb.toString());

				String sex = "";
				if ("F".equals(hrStaffPersonInfo.getFdSex())) {
					sex = "女";
				}
				if ("M".equals(hrStaffPersonInfo.getFdSex())) {
					sex = "男";
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date fdDateOfBirth = hrStaffPersonInfo.getFdDateOfBirth();
				obj.accumulate("sex", sex);
				obj.accumulate("sexCode", hrStaffPersonInfo.getFdSex());
				obj.accumulate("entryTime", (null == fdEntryTime ? "" : sdf.format(fdEntryTime)));
				obj.accumulate("fdDateOfBirth", ((null == fdDateOfBirth) ? "" : sdf.format(fdDateOfBirth)));

			}
		} catch (Exception e) {
			logger.error("hrStaffPersonInfoServiceImp.findWorkDaysByPersonInfo.error", e);
			dayNumberOne = 0L;
		}
		return obj;
	}

}