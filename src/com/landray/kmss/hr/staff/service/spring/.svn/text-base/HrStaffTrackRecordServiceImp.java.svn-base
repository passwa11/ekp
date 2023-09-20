package com.landray.kmss.hr.staff.service.spring;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationSyncSetting;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgConvert;
import com.landray.kmss.hr.staff.forms.HrStaffTrackRecordForm;
import com.landray.kmss.hr.staff.model.HrStaffEmolumentWelfareDetalied;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffEmolumentWelfareDetaliedService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.hr.staff.util.HrStaffDateUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.profile.service.ISysOrgImportService;
import com.landray.kmss.sys.quartz.interfaces.ISysQuartzCoreService;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzModelContext;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 任职记录
 * 
 * 
 */
public class HrStaffTrackRecordServiceImp extends HrStaffImportServiceImp
		implements IHrStaffTrackRecordService, SysOrgConstant {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(HrStaffTrackRecordServiceImp.class);

	private IHrStaffPersonInfoService hrStaffPersonService;
	private ISysOrgPersonService sysOrgPersonService;
	private ISysOrgImportService sysOrgImportService;


	public ISysOrgImportService getSysOrgImportService() {
		return sysOrgImportService;
	}

	public void
			setSysOrgImportService(ISysOrgImportService sysOrgImportService) {
		this.sysOrgImportService = sysOrgImportService;
	}

	public void
			setSysOrgPersonService(ISysOrgPersonService sysOrgPersonService) {
		this.sysOrgPersonService = sysOrgPersonService;
	}


	private IHrStaffPersonInfoService
			getHrStaffPersonInfoServiceImp() {
		if (hrStaffPersonService == null) {
			hrStaffPersonService = (IHrStaffPersonInfoService) SpringBeanUtil
					.getBean(
							"hrStaffPersonInfoService");
		}
		return hrStaffPersonService;
	}

	public IHrStaffPersonInfoService getHrStaffPersonService() {
		return hrStaffPersonService;
	}

	public void setHrStaffPersonService(
			IHrStaffPersonInfoService hrStaffPersonService) {
		this.hrStaffPersonService = hrStaffPersonService;
	}

	private IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService;

	public void setHrStaffEmolumentWelfareDetaliedService(
			IHrStaffEmolumentWelfareDetaliedService hrStaffEmolumentWelfareDetaliedService) {
		this.hrStaffEmolumentWelfareDetaliedService = hrStaffEmolumentWelfareDetaliedService;
	}

	private ISysQuartzCoreService sysQuartzCoreService;

	public void setSysQuartzCoreService(ISysQuartzCoreService sysQuartzCoreService) {
		this.sysQuartzCoreService = sysQuartzCoreService;
	}

	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		HrStaffTrackRecord model = (HrStaffTrackRecord) modelObj;
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		//判断是否有已经相同部门、岗位任职中的记录
		String fdDeptId = null;
		String fdPostId = null;
		if (null != model.getFdHrOrgDept() || "true".equals(syncSetting.getHrToEkpEnable())) {
			if (null == model.getFdHrOrgDept()) {
				if(null != model.getFdRatifyDept()){
					HrOrgConvert.setPropertyById(model, "fdRatifyDept", "fdHrOrgDept", model.getFdRatifyDept().getFdId());
				}
			}
			if(null == model.getFdHrOrgPost()){
				if(!ArrayUtil.isEmpty(model.getFdOrgPosts())){
					HrOrgConvert.setPropertyById(model, "fdOrgPosts", "fdHrOrgPost", model.getFdOrgPosts().get(0).getFdId() );
				}
			}
			if (null == model.getFdHrOrgDept()) {
				logger.warn("任职记录的部门不能为空！");
				return null;
			}
			fdDeptId = model.getFdHrOrgDept() != null ? model.getFdHrOrgDept().getFdId() : null;
			fdPostId = model.getFdHrOrgPost() != null ? model.getFdHrOrgPost().getFdId() : null;
		} else {
			if (null == model.getFdRatifyDept()) {
				logger.warn("任职记录的部门不能为空！");
				return null;
			}
			fdDeptId = model.getFdRatifyDept().getFdId();
			if (!ArrayUtil.isEmpty(model.getFdOrgPosts())) {
				SysOrgElement element = model.getFdOrgPosts().get(0);
				fdPostId = element.getFdId();
			}
		}
		if (StringUtil.isNull(model.getFdType())) {
			model.setFdType("1");//主岗
		}
		if (!checkUnique(null, model.getFdPersonInfo().getFdId(), fdDeptId, fdPostId,
				model.getFdStaffingLevel() == null ? null : model.getFdStaffingLevel().getFdId(),
				model.getFdType())) {
			return null;
		}
//		String fdId = super.add(model);
		if (null == model.getFdTransDate() || "2".equals(model.getFdType())) {
			model.setFdTransDate(model.getFdEntranceBeginDate());
		}
		 if (model.getFdTransDate().after(new Date())) {
			 //如果生效日期在当前时间之后，则生成定时任务
			 model.setFdStatus("3");
			 savePersonSchedulerJob(model);
			  List fdOrgPosts = new ArrayList();
			  if(null != model.getFdOrgPosts() && model.getFdOrgPosts().size()>0) {
			  for (SysOrgElement post : model.getFdOrgPosts()) {
			  IBaseModel element =
			  hrOrganizationElementService.findByPrimaryKey(post.getFdId(), null,
			  true);
			  if (null != element) {
			  fdOrgPosts.add(element);
			  }
			  }
			  }else {
			  fdOrgPosts = null;
			  }
			 model.setFdOrgPosts(fdOrgPosts);
			 return super.add(model);
		 }
		if(model.getFdEntranceEndDate()!=null && model.getFdEntranceEndDate().before(new Date())) {
			model.setFdStatus("2");//已结束
		}else {
			model.setFdStatus("1");//任职中
		}
		updatePerson(model);
		List fdOrgPosts = new ArrayList();
		if(null != model.getFdOrgPosts() && model.getFdOrgPosts().size()>0) {
			for (SysOrgElement post : model.getFdOrgPosts()) {
				IBaseModel element = hrOrganizationElementService.findByPrimaryKey(post.getFdId(), null, true);
				if (null != element) {
					fdOrgPosts.add(element);
				}
			}
		}else {
			fdOrgPosts = null;
		}
		model.setFdOrgPosts(fdOrgPosts);
		return super.add(model);
	}
	
	@Override
	public void update(IBaseModel modelObj) throws Exception {
		HrStaffTrackRecord model = (HrStaffTrackRecord) modelObj;
		//生效日期在当前时间之后，则生成定时任务，否则就直接修改
		model.getFdTransDate();

		super.update(modelObj);
		updatePerson(model);
	}

	@Override
	public String getTrackRecordByPerson(String fdId) throws Exception {
		List list = findList(
				"hrStaffTrackRecord.fdPersonInfo.fdId = '"
						+ fdId + "'",
				"");
		for (int i = 0; i < list.size(); i++) {
			HrStaffTrackRecord trackList = (HrStaffTrackRecord) list.get(i);
			if (trackList.getFdEntranceEndDate() == null) {
				return trackList.getFdId();
			}
		}
		return null;
	}

	@Override
	public HrStaffPersonInfo getPersonInfo(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"hrStaffPersonInfo.fdOrgPerson.fdId=:fdPersonId");
		hqlInfo.setParameter("fdPersonId", fdId);
		List<HrStaffPersonInfo> list = getHrStaffPersonInfoServiceImp()
				.findList(hqlInfo);
		if (list.size() == 1) {
			return (HrStaffPersonInfo) getHrStaffPersonInfoServiceImp()
					.findByPrimaryKey(list.get(0).getFdId());
		}
		return null;


	}

	@Override
	public String[] getImportFields() {
		List<String> importFields = new ArrayList<String>();
		importFields.add("fdEntranceBeginDate");
		importFields.add("fdEntranceEndDate");
		importFields.add("fdRatifyDept");
		importFields.add("fdOrgPosts");
		importFields.add("fdStaffingLevel");
		importFields.add("fdMemo");
		return importFields.toArray(new String[] {});
	}

	@Override
	public KmssMessage saveImportData(HrStaffTrackRecordForm trackRecordForm)
			throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		Workbook wb = null;
		Sheet sheet = null;
		InputStream inputStream = null;
		int count = 0;
		KmssMessages messages = null;
		StringBuffer errorMsg = new StringBuffer();
		try {
			inputStream = trackRecordForm.getFile().getInputStream();
			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(inputStream);
			sheet = wb.getSheetAt(0);

			// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.empty", "hr-staff"));
			}
			if (sheet.getRow(0).getLastCellNum() != 9) {
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.errFile", "hr-staff"));
			}

			// 从第二行开始取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HrStaffTrackRecord track = new HrStaffTrackRecord();
				messages = new KmssMessages();
				Row row = sheet.getRow(i);
				if (row == null) { // 跳过空行
					continue;
				}
				// 获取列数
				int cellNum = row.getLastCellNum();
				HrStaffPersonInfo personInfo = null;
				// 是否是新建
				boolean isNew = true;
				// 是否有账号或工号
				boolean hasLoginNameOrStaffNo = false;
				for (int j = 0; j < cellNum; j++) {
					String value = ImportUtil.getCellValue(row.getCell(j));
					String fdNo = null;
					if (j == 0) {
						fdNo = value;
						continue;
					}
					if (j == 1) {
						if (StringUtil.isNull((String) value)) {
                            continue;
                        }
						hasLoginNameOrStaffNo = true;
						personInfo = getHrStaffPersonInfoService()
								.findPersonInfoByLoginNameAndNo((String) value, fdNo);
						BeanUtils.setProperty(track, "fdPersonInfo",
								personInfo);
					}
					if (j == 2 && personInfo == null) { // 第二列为工号，如果通过登录账号没有找到人员，则需要再通过工号继续查找
						if (StringUtil.isNull((String) value)) {
                            continue;
                        }
						hasLoginNameOrStaffNo = true;
						personInfo = getHrStaffPersonInfoService()
								.findPersonInfoByStaffNo((String) value);
						BeanUtils.setProperty(track, "fdPersonInfo",
								personInfo);
					}

					if (!hasLoginNameOrStaffNo) {
						messages.addError(new KmssMessage(ResourceUtil
								.getString(
										"hrStaffTrackRecord.error.person")));
					} else {
						if (j == 3) {
							Date date = DateUtil.convertStringToDate(value,
									DateUtil.TYPE_DATE, null);
							if (date != null) {
								BeanUtils.setProperty(track, "fdEntranceBeginDate",
										date);
							} else {
								messages.addError(new KmssMessage(ResourceUtil
										.getString(
												"hr-staff:hrStaffTrackRecord.error.beiginDate")));
							}
						}
						if (j == 4) {
							Date date = DateUtil.convertStringToDate(value,
									DateUtil.TYPE_DATE, null);
							if (date != null) {
								BeanUtils.setProperty(track, "fdEntranceEndDate",
										date);
							} /*else {
							messages.addError(new KmssMessage(ResourceUtil
									.getString(
											"hr-staff:hrStaffTrackRecord.error.endDate")));
							}*/
						}
						if (j == 5) {
						/*IBaseModel baseModel = HrOrgConvert.setPropertyByName(track, "fdRatifyDept", "fdHrOrgDept",
								value);
						if (baseModel == null) {
							messages.addError(new KmssMessage(
									ResourceUtil.getString("hr-staff:hrStaffTrackRecord.error.dept.extis"), value));
						}*/
							if ("true".equals(syncSetting.getHrToEkpEnable())) {
								HrOrganizationElement hrOrgElement = hrOrganizationElementService.findOrgByName(value);
								if (null != hrOrgElement) {
									BeanUtils.setProperty(track, "fdHrOrgDept", hrOrgElement);
								} else {
									messages.addError(new KmssMessage(
											ResourceUtil.getString("hr-staff:hrStaffTrackRecord.error.dept"), value));
								}
							} else {
								SysOrgElement fdParent = sysOrgImportService.getSysOrgElementByName(value, ORG_TYPE_ORG,
										ORG_TYPE_DEPT);
								if (fdParent != null) {
									BeanUtils.setProperty(track, "fdRatifyDept", fdParent);
								} else {
									messages.addError(new KmssMessage(
											ResourceUtil.getString("hr-staff:hrStaffTrackRecord.error.dept"), value));
								}
							}
						}
						if (j == 6) {
						/*IBaseModel baseModel = HrOrgConvert.setPropertyByName(track, "fdOrgPosts", "fdHrOrgDept",
								value);
						if (baseModel == null) {
							messages.addError(new KmssMessage(
									ResourceUtil.getString("hr-staff:hrStaffTrackRecord.error.posts.extis"), value));
						}*/
							if ("true".equals(syncSetting.getHrToEkpEnable())) {
								HrOrganizationPost hrOrgPost = (HrOrganizationPost) hrOrganizationElementService
										.findOrgByName(value);
								if (null != hrOrgPost) {
									BeanUtils.setProperty(track, "fdHrOrgPost", hrOrgPost);
								} else {
									messages.addError(new KmssMessage(
											ResourceUtil.getString("hr-staff:hrStaffTrackRecord.error.posts"), value));
								}
							} else {
								List<SysOrgElement> posts = new ArrayList<SysOrgElement>();
								String[] postNames = value.split(";");
								StringBuffer error = new StringBuffer();
								for (String postName : postNames) {
									SysOrgElement _post = sysOrgImportService.getSysOrgElementByName(postName,
											ORG_TYPE_POST);
									if (_post != null) {
										posts.add(_post);
									} else {
										error.append(",").append(postName);
									}
								}
								if (error.length() > 0) {
									error.deleteCharAt(0);
									messages.addError(new KmssMessage(
											ResourceUtil.getString("hr-staff:hrStaffTrackRecord.error.posts"), value));
								} else {
									BeanUtils.setProperty(track, "fdOrgPosts", posts);
								}
							}
						}
						if (j == 7) {
							if (value != null) {
								SysOrganizationStaffingLevel organiza = sysOrgImportService
										.getSysOrganizationStaffingLevelByName(
												value);
								if (organiza != null) {
									BeanUtils.setProperty(track, "fdStaffingLevel",
											organiza);
								}
							}

						}
						if (j == 8) {
							if (value != null) {
								BeanUtils.setProperty(track, "fdMemo", value);
							}
						}
					}

				}
				// 如果有错误，就不进行导入
				if (!messages.hasError()) {
					this.add(track);
					count++;
				} else {
					errorMsg.append(ResourceUtil.getString(
							"hrStaff.import.error.num", "hr-staff", null, i));
					// 解析错误信息
					for (KmssMessage message : messages.getMessages()) {
						errorMsg.append(message.getMessageKey());
					}

					errorMsg.append("<br>");
				}
			}
			KmssMessage message = null;
			if (errorMsg.length() > 0) {
				errorMsg.insert(0, ResourceUtil.getString(
						"hrStaff.import.portion.failed", "hr-staff", null, count)
						+ "<br>");

				message = new KmssMessage(errorMsg.toString());
				message.setMessageType(KmssMessage.MESSAGE_ERROR);
			} else {
				message = new KmssMessage(ResourceUtil.getString(
						"hrStaff.import.success", "hr-staff", null, count));
				message.setMessageType(KmssMessage.MESSAGE_COMMON);
			}
			return message;
		} catch (Exception e) {
			throw new RuntimeException(ResourceUtil.getString(
					"hrStaff.import.error", "hr-staff"));
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		HrStaffTrackRecord hrStaffTrackRecord = (HrStaffTrackRecord) modelObj;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy("hrStaffTrackRecord.fdEntranceBeginDate desc");
		@SuppressWarnings("unchecked")
		List<HrStaffTrackRecord> hrStaffTrackRecords = findList(hqlInfo);
		HrStaffTrackRecord currTrackRecord = null;
		HrStaffTrackRecord beforeTrackRecord = null;
		for (int i = 0; i < hrStaffTrackRecords.size(); i++) {
			if (i == 0) {
				currTrackRecord = hrStaffTrackRecords.get(i);
			}
			if (i == 1) {
				beforeTrackRecord = hrStaffTrackRecords.get(i);
			}
		}
		// 任职记录和组织架构无关联时跳过更新操作
		if (null != beforeTrackRecord
				&& hrStaffTrackRecord.getFdId()
						.equals(currTrackRecord.getFdId())
				&& beforeTrackRecord.getFdOrgPerson() != null) {
			// 更新组织架构
			SysOrgPerson orgPerson = (SysOrgPerson) sysOrgPersonService
					.findByPrimaryKey(
							beforeTrackRecord.getFdOrgPerson().getFdId());
			orgPerson.setFdParent(beforeTrackRecord.getFdRatifyDept());
			orgPerson.setFdPosts(beforeTrackRecord.getFdOrgPosts());
			orgPerson.setFdAlterTime(new Date());
		}
		//删除定时任务
		sysQuartzCoreService.delete(modelObj, "hrStaffTrackRecordQuartzJob");

		super.delete(modelObj);
	}

	@Override
	public void addTransfer(HrStaffTrackRecord model) throws Exception {
		if (null != model.getFdTransSalary() && model.getFdTransSalary() > 0 && model.getFdPersonInfo() !=null) {
			HrStaffEmolumentWelfareDetalied modelObj = new HrStaffEmolumentWelfareDetalied();
			Double fdBeforeEmolument = hrStaffEmolumentWelfareDetaliedService.getSalaryByStaffId(model.getFdPersonInfo());
			double fdAdjustAmount = model.getFdTransSalary() - (null != fdBeforeEmolument ? fdBeforeEmolument : 0);
			modelObj.setFdPersonInfo(model.getFdPersonInfo());
			modelObj.setFdAdjustDate(new Date());
			if(fdBeforeEmolument!=null){
				modelObj.setFdBeforeEmolument(fdBeforeEmolument);
			}else{
				modelObj.setFdBeforeEmolument(0.0);
			}
			modelObj.setFdEffectTime(model.getFdTransDate());
			modelObj.setFdAdjustAmount(fdAdjustAmount);
			modelObj.setFdAfterEmolument(model.getFdTransSalary());
			modelObj.setFdSource("HrStaffConcern");
			if(model.getFdTransDate().after(new Date())){
				hrStaffEmolumentWelfareDetaliedService.setSalarySchedulerJob(model.getFdTransDate(), model.getFdPersonInfo(), modelObj.getFdId());
			}else{
				modelObj.setFdIsEffective(Boolean.TRUE);
				model.getFdPersonInfo().setFdSalary(model.getFdTransSalary());
			}
			hrStaffEmolumentWelfareDetaliedService.add(modelObj);
		}
		if (null != model.getFdRatifyDept()) {
			model.setFdSource("HrStaffConcern");
			this.add(model);
		}
	}

	private void updatePerson(HrStaffTrackRecord model) throws Exception {
		// 更新组织架构
		if ("1".equals(model.getFdType())) {
			HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
			//如果开启的是 EKP同步到HR档案
			if (null != model.getFdOrgPerson() && "true".equals(syncSetting.getEkpToHrEnable())) {
				SysOrgPerson orgPerson = (SysOrgPerson) sysOrgPersonService
						.findByPrimaryKey(model.getFdOrgPerson().getFdId());
				if (null != model.getFdRatifyDept()) {
					orgPerson.setFdParent(model.getFdRatifyDept());
				}
				if (null != model.getFdOrgPosts()) {
					orgPerson.setFdPosts(model.getFdOrgPosts());
				}
				orgPerson.setFdAlterTime(new Date());

				if (model.getFdStaffingLevel() != null) {
					orgPerson.setFdStaffingLevel(model.getFdStaffingLevel());
				}
				sysOrgPersonService.update(orgPerson);
			}
			//重写updateLastTrackRecord方法，新增参数model
			updateLastTrackRecordForTrack(model.getFdId(), model.getFdPersonInfo(), null,model);
			//更新人事组织架构的人员部门、岗位信息
			updateHrOrg(model);
		} else {
			//新建兼岗需要更新人员的最后更新时间，以便下次定时任务同步兼岗数据到ekp组织架构
			hrOrganizationElementService.update(model.getFdPersonInfo());
		}
	}

	private void updateHrOrg(HrStaffTrackRecord model) throws Exception {
		HrStaffPersonInfo personInfo = model.getFdPersonInfo();
		if (null != model.getFdRatifyDept()) {
			IBaseModel fdParent = hrOrganizationElementService
					.findByPrimaryKey(model.getFdRatifyDept().getFdId(), null, true);
			if (null != fdParent) {
				personInfo.setFdParent((HrOrganizationElement) fdParent);
			}
		} else {
			personInfo.setFdParent(model.getFdHrOrgDept());
		}
		List posts = new ArrayList();
		if (null != model.getFdOrgPosts()) {
			for (IBaseModel post : model.getFdOrgPosts()) {
				IBaseModel element = hrOrganizationElementService
						.findByPrimaryKey(post.getFdId(), null, true);
				if (null != element) {
					posts.add(element);
				}
			}
		} else {
			posts.add(model.getFdHrOrgPost());
		}
		personInfo.setFdPosts(posts);
		if (model.getFdStaffingLevel() != null) {
			personInfo.setFdStaffingLevel(model.getFdStaffingLevel());
			if (personInfo.getFdOrgPerson() != null) {
				personInfo.getFdOrgPerson().setFdStaffingLevel(model.getFdStaffingLevel());
			}
		}else {
			personInfo.setFdStaffingLevel(null);
			if (personInfo.getFdOrgPerson() != null) {
				personInfo.getFdOrgPerson().setFdStaffingLevel(null);
			}
		}
		getHrStaffPersonInfoServiceImp().updatePersonInfo(personInfo);
	}

	private void savePersonSchedulerJob(HrStaffTrackRecord record) throws Exception {
		Date fdTransDate = record.getFdTransDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fdTransDate);
		String cron = HrStaffDateUtil.getCronExpression(calendar);
		if (StringUtil.isNotNull(cron)) {
			SysQuartzModelContext quartzContext = new SysQuartzModelContext();
			quartzContext.setQuartzJobMethod("personSchedulerJob");
			quartzContext.setQuartzJobService("hrStaffTrackRecordService");
			quartzContext.setQuartzKey("hrStaffTrackRecordQuartzJob");
			quartzContext.setQuartzParameter(record.getFdId());
			quartzContext.setQuartzSubject(record.getFdPersonInfo().getFdName() + "的调动定时任务");
			quartzContext.setQuartzRequired(true);
			quartzContext.setQuartzCronExpression(cron);
			sysQuartzCoreService.saveScheduler(quartzContext, record);
		}
	}

	/**
	 * 修改人员的主岗任职记录 任职中 至 为结束
	 * @param personId
	 * @throws Exception
	 */
	private void updateRecordIsOver(String personId) throws Exception {
		HQLInfo hqlInfo=new HQLInfo();
		hqlInfo.setWhereBlock("fdType = :fdType and fdStatus = :fdStatus and fdOrgPerson.fdId =:personId");
		hqlInfo.setParameter("fdType","1");
		hqlInfo.setParameter("fdStatus","1");
		hqlInfo.setParameter("personId",personId);
		List<HrStaffTrackRecord> list = this.findList(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			for (HrStaffTrackRecord hrRecord:list  ) {
				hrRecord.setFdStatus("2");
				super.update(hrRecord);
			}
		}
	}

	@Override
	public void personSchedulerJob(SysQuartzJobContext context) throws Exception {
		HrStaffTrackRecord record = (HrStaffTrackRecord) findByPrimaryKey(context.getParameter(),
				HrStaffTrackRecord.class,
				true);
		if(record.getFdOrgPerson() !=null) {
			updateRecordIsOver(record.getFdOrgPerson().getFdId());
			record.setFdStatus("1");
			super.update(record);
			updatePerson(record);
		}
		sysQuartzCoreService.delete(record, "hrStaffTrackRecordQuartzJob");
	}
	
	@Override
	public void updateHrStaffTrackRecord(HrStaffTrackRecord model, String afterEffectTime) throws Exception {
		Date effectTime = DateUtil.convertStringToDate(afterEffectTime, null);
		if(effectTime.after(new Date())){//说明之前的定时任务没有执行，那么重新制定定时任务
			if (model.getFdTransDate().after(new Date())) {
				//如果生效日期在当前时间之后，则生成定时任务
				savePersonSchedulerJob(model);
			} else {
				updateRecordIsOver(model.getFdOrgPerson().getFdId());
				model.setFdStatus("1");
				updatePerson(model);
				sysQuartzCoreService.delete(model, "hrStaffTrackRecordQuartzJob");
			}
		}
		super.update(model);
	}

	@Override
	public void updateLastTrackRecord(String currId, HrStaffPersonInfo personInfo, Date beforeDate)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sbf = new StringBuffer(" 1=1 ");
		if (StringUtil.isNotNull(currId)) {
			sbf.append(" and hrStaffTrackRecord.fdId !=:fdId ");
			hqlInfo.setParameter("fdId", currId);
		}
		sbf.append(" and hrStaffTrackRecord.fdPersonInfo.fdId=:personInfo and hrStaffTrackRecord.fdType = :fdType and hrStaffTrackRecord.fdStatus = :fdStatus ");
		hqlInfo.setWhereBlock(sbf.toString());
		hqlInfo.setOrderBy("hrStaffTrackRecord.fdCreateTime ASC");
		hqlInfo.setParameter("personInfo", personInfo.getFdId());
		hqlInfo.setParameter("fdType","1");
		hqlInfo.setParameter("fdStatus","1");

		List<HrStaffTrackRecord> list = this.findList(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			HrStaffTrackRecord lastRecord = list.get(0);
			if (null != beforeDate) {
				lastRecord.setFdEntranceEndDate(beforeDate);
			} else {
				lastRecord.setFdEntranceEndDate(new Date());
			}
			lastRecord.setFdStatus("2");
			super.update(lastRecord);
		}
	}
	
	public void updateLastTrackRecordForTrack(String currId, HrStaffPersonInfo personInfo, Date beforeDate, HrStaffTrackRecord model)
			throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		StringBuffer sbf = new StringBuffer("1=1 ");
		if (StringUtil.isNotNull(currId)) {
			sbf.append("and hrStaffTrackRecord.fdId !=:fdId ");
			hqlInfo.setParameter("fdId", currId);
		}
		sbf.append(
				"and hrStaffTrackRecord.fdPersonInfo.fdId=:personInfo and hrStaffTrackRecord.fdType = '1' and hrStaffTrackRecord.fdStatus = '1'");
		hqlInfo.setWhereBlock(sbf.toString());
		hqlInfo.setOrderBy("hrStaffTrackRecord.fdCreateTime ASC");
		hqlInfo.setParameter("personInfo", personInfo.getFdId());
		List<HrStaffTrackRecord> list = this.findList(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			HrStaffTrackRecord lastRecord = list.get(0);
			if (null != beforeDate) {
				lastRecord.setFdEntranceEndDate(beforeDate);
			} else {
				lastRecord.setFdEntranceEndDate(new Date());
			}
			//重写updateLastTrackRecord方法:修改点：新的任职记录状态不是已结束，才会将上一段任职中的任职记录置为已结束 by 2020-11-12
			if(!"2".equals(model.getFdStatus())) {
				lastRecord.setFdStatus("2");
			}
			update(lastRecord);
		}
	}

	@Override
	public boolean checkUnique(String fdId, String fdPersonId, String fdDeptId, String fdPostId,
			String fdStaffingLevelId, String fdType)
			throws Exception {
		HrOrganizationSyncSetting syncSetting = new HrOrganizationSyncSetting();
		if (StringUtil.isNull(fdDeptId) && StringUtil.isNull(fdPostId)) {
			return true;
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(
				"com.landray.kmss.hr.staff.model.HrStaffTrackRecord");
		String table = hqlInfo.getModelTable();
		StringBuffer sbf = new StringBuffer("1=1");
		if (StringUtil.isNotNull(fdId)) {
			sbf.append(" and " + table + ".fdId !=:fdId");
			hqlInfo.setParameter("fdId", fdId);
		}
		if ("true".equals(syncSetting.getHrToEkpEnable())) {
			if (StringUtil.isNotNull(fdPostId)) {
				sbf.append(" and " + table + ".fdHrOrgPost.fdId=:postId");
				hqlInfo.setParameter("postId", fdPostId);
			}
			if (StringUtil.isNotNull(fdDeptId)) {
				sbf.append(" and " + table + ".fdHrOrgDept.fdId=:deptId");
				hqlInfo.setParameter("deptId", fdDeptId);
			}
		} else {
			if (StringUtil.isNotNull(fdDeptId)) {
				sbf.append(" and " + table + ".fdRatifyDept.fdId=:deptId");
				hqlInfo.setParameter("deptId", fdDeptId);
			}
			if (StringUtil.isNotNull(fdPostId)) {
				hqlInfo.setJoinBlock(
						"inner join " + table + ".fdOrgPosts posts");
				sbf.append(" and posts.fdId in(:postId)");
				hqlInfo.setParameter("postId", fdPostId);
			}
		}
		if (StringUtil.isNotNull(fdStaffingLevelId)) {
			sbf.append(" and " + table
					+ ".fdStaffingLevel.fdId=:fdStaffingLevelId");
			hqlInfo.setParameter("fdStaffingLevelId", fdStaffingLevelId);
		}
		sbf.append(" and " + table + ".fdPersonInfo.fdId=:personId and " + table
				+ ".fdType =:fdType and " + table + ".fdStatus = '1'");
		hqlInfo.setParameter("personId", fdPersonId);
		hqlInfo.setParameter("fdType", fdType);
		hqlInfo.setWhereBlock(sbf.toString());
		List<HrStaffTrackRecord> list = this.findList(hqlInfo);
		return list.size() < 1 ? true : false;

	}

}
