package com.landray.kmss.hr.organization.service.spring;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.model.HrOrganizationConPost;
import com.landray.kmss.hr.organization.model.HrOrganizationDept;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.service.IHrOrganizationConPostService;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.organization.util.HrOrganizationUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffTrackRecord;
import com.landray.kmss.hr.staff.service.IHrStaffTrackRecordService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrganizationStaffingLevel;
import com.landray.kmss.sys.organization.service.ISysOrganizationStaffingLevelService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class HrOrganizationConPostServiceImp extends ExtendDataServiceImp implements IHrOrganizationConPostService {


    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}
	
	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	private ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService;

	public void setSysOrganizationStaffingLevelService(
			ISysOrganizationStaffingLevelService sysOrganizationStaffingLevelService) {
		this.sysOrganizationStaffingLevelService = sysOrganizationStaffingLevelService;
	}

	private IHrStaffTrackRecordService hrStaffTrackRecordService;

	public void setHrStaffTrackRecordService(IHrStaffTrackRecordService hrStaffTrackRecordService) {
		this.hrStaffTrackRecordService = hrStaffTrackRecordService;
	}

	@Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrOrganizationConPost) {
            HrOrganizationConPost hrOrganizationConPost = (HrOrganizationConPost) model;
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        HrOrganizationConPost hrOrganizationConPost = new HrOrganizationConPost();
		hrOrganizationConPost.setFdStartTime(new Date());
        HrOrganizationUtil.initModelFromRequest(hrOrganizationConPost, requestContext);
        return hrOrganizationConPost;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrOrganizationConPost hrOrganizationConPost = (HrOrganizationConPost) model;
    }


	@Override
	public List<HrOrganizationConPost> findByFdPerson(HrStaffPersonInfo fdPerson) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrOrganizationConPost.fdPerson.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdPerson.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public List<HrOrganizationConPost> findByFdPost(HrOrganizationElement fdPost) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrOrganizationConPost.fdPost.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdPost.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
	public List<HrOrganizationConPost> findByFdDept(HrOrganizationDept fdDept) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("hrOrganizationConPost.fdDept.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdDept.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public List<HrOrganizationConPost> findByFdDuty(SysOrganizationStaffingLevel level) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrOrganizationConPost.fdStaffingLevel.fdId=:fdId");
		hqlInfo.setParameter("fdId", level.getFdId());
        return this.findList(hqlInfo);
    }

	@Override
	public WorkBook buildTemplateWorkbook(HttpServletRequest request) throws Exception {
		String[] baseColumns = new String[] { HrOrgUtil.getStr("hrOrganizationConPost.fdPerson"),
				HrOrgUtil.getStr("hrOrganizationConPost.fdStartTime"),
				HrOrgUtil.getStr("hrOrganizationConPost.fdEndTime"),
				HrOrgUtil.getStr("hrOrganizationConPost.fdDept"),
				HrOrgUtil.getStr("hrOrganizationConPost.fdPost"),
				HrOrgUtil.getStr("hrOrganizationConPost.fdDuty"), };
		// 必填的列的下标
		Integer[] notNullArr = new Integer[] { 0, 1, 3, 4 };
		List notNullList = Arrays.asList(notNullArr);
		String filename = HrOrgUtil.getStr("hrOrganizationConPost.import.templateFile");
		WorkBook wb = new WorkBook();
		wb.setLocale(request.getLocale());
		Sheet sheet = new Sheet();
		sheet.setTitle(filename);
		for (int i = 0; i < baseColumns.length; i++) {
			Column col = new Column();
			col.setTitle(baseColumns[i]);
			if (notNullList.contains(i)) {
				col.setRedFont(true);
			}
			sheet.addColumn(col);
		}
		List contentList = new ArrayList();
		// 样例数据
		Object[] objs = new Object[sheet.getColumnList().size()];
		objs[0] = "张子怡";
		objs[1] = "2019-12-12";
		objs[2] = "2020-12-12";
		objs[3] = "EKP产品部";
		objs[4] = "员工关系专员";
		objs[5] = "员工";
		contentList.add(objs);
		sheet.setContentList(contentList);
		wb.addSheet(sheet);
		wb.setFilename(filename);
		return wb;
	}

	@Override
	public JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception {
		Workbook wb = null;
		org.apache.poi.ss.usermodel.Sheet sheet = null;
		JSONObject result = new JSONObject();
		JSONArray titles = new JSONArray(); // 标题头
		JSONArray errorRows = new JSONArray(); // 每个错误行（包含错误行号，错误列号，行的错误信息）
		JSONArray otherErrors = new JSONArray(); // 其他错误
		int columnSize = 5;
		int successCount = 0, failCount = 0;
		try {
			wb = WorkbookFactory.create(inputStream); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);

			// 数据必须大于columnSize-1列，且不能少于2行
			if (sheet.getLastRowNum() < 1 || sheet.getRow(0).getLastCellNum() < columnSize - 1) {
				otherErrors.add(HrOrgUtil.getStr("hrOrganization.import.template.fileError"));
			} else {
				HrStaffTrackRecord record = null;
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
						if (StringUtil.isNotNull(HrOrgUtil.getCellValue(row.getCell(j)))) {
							break;
						}
					}
					if (j == columnSize) {
						continue;
					}
					//员工姓名
					String nameValue = HrOrgUtil.getCellValue(row.getCell(0));
					//兼职开始时间
					String fdStartTime = HrOrgUtil.getCellValue(row.getCell(1));
					//兼职结束时间
					String fdEndTime = HrOrgUtil.getCellValue(row.getCell(2));
					//部门
					String fdDeptValue = HrOrgUtil.getCellValue(row.getCell(3));
					//岗位
					String fdPostValue = HrOrgUtil.getCellValue(row.getCell(4));
					//职务
					String fdStaffingLevelValue = HrOrgUtil.getCellValue(row.getCell(5));

					record = new HrStaffTrackRecord();
					if (StringUtil.isNotNull(nameValue)) {
						HrOrganizationElement fdPerson = hrOrganizationElementService.findOrgByName(nameValue);
						if (null != fdPerson) {
							record.setFdType("2");
							record.setFdPersonInfo((HrStaffPersonInfo) fdPerson);
						} else {
							HrOrgUtil.addRowError(errorRow, rowIndex, 0,
									HrOrgUtil.getStr("hrOrganizationConPost.fdPerson.notnone"));
						}
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 0,
								HrOrgUtil.getStr("hrOrganizationConPost.fdPerson.notnull"));
					}

					if (StringUtil.isNotNull(fdStartTime)) {
						record.setFdEntranceBeginDate(DateUtil.convertStringToDate(fdStartTime));
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 1,
								HrOrgUtil.getStr("hrOrganizationConPost.fdStartTime.notnull"));
					}

					if (StringUtil.isNotNull(fdEndTime)) {
						DateUtil.getDateFormat(fdEndTime);
						Long now = new Date().getTime();
						Long endTimeStamp = new SimpleDateFormat("yyyy-MM-dd")
								.parse(fdEndTime).getTime();
						if ((endTimeStamp - now) > 0) {
							record.setFdEntranceEndDate(
									DateUtil.convertStringToDate(fdEndTime));
							if (StringUtil.isNotNull(fdStartTime)) {
								Long startTimeStamp = new SimpleDateFormat(
										"yyyy-MM-dd")
												.parse(fdStartTime).getTime();
								if ((endTimeStamp - startTimeStamp) < 0) {
									HrOrgUtil.addRowError(errorRow, rowIndex, 2,
											HrOrgUtil.getStr(
													"hrOrganizationConPost.fdendTime.gtNow"));
								}
							}
						} else {
							HrOrgUtil.addRowError(errorRow, rowIndex, 2,
									HrOrgUtil.getStr(
											"hrOrganizationConPost.fdendTime.gtStartTime"));
						}

					}
					if (StringUtil.isNotNull(fdDeptValue)) {
						HrOrganizationElement fdDept = hrOrganizationElementService.findOrgByName(fdDeptValue);
						if (null != fdDept) {
							record.setFdHrOrgDept(fdDept);
						} else {
							HrOrgUtil.addRowError(errorRow, rowIndex, 3,
									HrOrgUtil.getStr("hrOrganizationConPost.fdDept.notnull"));
						}
					}else{

							HrOrgUtil.addRowError(errorRow, rowIndex, 3,
									HrOrgUtil.getStr("hrOrganizationConPost.fdDept.notnull"));

					}

					if (StringUtil.isNotNull(fdPostValue)) {
						HrOrganizationElement fdPost = hrOrganizationElementService.findOrgByName(fdPostValue);
						if (null != fdPost) {
							if (hrStaffTrackRecordService.checkUnique(null,
									record.getFdPersonInfo() != null ? record.getFdPersonInfo().getFdId() : null,
									record.getFdHrOrgDept() != null ? record.getFdHrOrgDept().getFdId() : null,
									fdPost.getFdId(), null, "2")) {
								record.setFdHrOrgPost((HrOrganizationPost) fdPost);
							} else {
								HrOrgUtil.addRowError(errorRow, rowIndex, 4,
										HrOrgUtil.getStr("hrOrganizationConPost.fdPost.Unique"));
							}
						} else {
							HrOrgUtil.addRowError(errorRow, rowIndex, 4,
									HrOrgUtil.getStr("hrOrganizationConPost.fdPost.notnone"));
						}
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 4,
								HrOrgUtil.getStr("hrOrganizationConPost.fdPost.notnull"));
					}

					if (StringUtil.isNotNull(fdStaffingLevelValue)) {
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock("fdName = :fdName");
						hqlInfo.setParameter("fdName", fdStaffingLevelValue);
						List<SysOrganizationStaffingLevel> fdStaffingLevels = sysOrganizationStaffingLevelService.findList(hqlInfo);
						if(!ArrayUtil.isEmpty(fdStaffingLevels)){
							record.setFdStaffingLevel(fdStaffingLevels.get(0));
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
							hrStaffTrackRecordService.add(record);
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
					titles.add(HrOrgUtil.getStr("hrOrganization.import.lineNumber")); // 行号
					titles.add(HrOrgUtil.getStr("hrOrganizationConPost.fdPerson"));
					titles.add(HrOrgUtil.getStr("hrOrganizationConPost.fdStartTime"));
					titles.add(HrOrgUtil.getStr("hrOrganizationConPost.fdEndTime"));
					titles.add(HrOrgUtil.getStr("hrOrganizationConPost.fdDept"));
					titles.add(HrOrgUtil.getStr("hrOrganizationConPost.fdPost"));
					titles.add(HrOrgUtil.getStr("hrOrganization.import.errorDetails")); // 错误详情
					result.put("titles", titles);
					String importMsg = ResourceUtil.getString("hrOrganization.import.format.msg", "hr-organization", locale,
							new Object[] { successCount, failCount });
					result.put("importMsg", importMsg);
				} else { // 无错误
					String importMsg = ResourceUtil.getString("hrOrganization.import.format.msg.succ", "hr-organization",
							locale, new Object[] { successCount });
					result.put("importMsg", importMsg);
				}
			}
			result.put("otherErrors", otherErrors);
		} catch (IOException e) {
			otherErrors.add(e.getMessage());
		}finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}
		return result;
	}

	@Override
	public void updateFinishConPostByIds(String[] ids) throws Exception {
		HrStaffTrackRecord modelObj = null;
		for (int i = 0; i < ids.length; i++) {
			modelObj = (HrStaffTrackRecord) hrStaffTrackRecordService.findByPrimaryKey(ids[i]);
			modelObj.setFdStatus("2");
			modelObj.setFdEntranceEndDate(new Date());
			hrStaffTrackRecordService.update(modelObj);
		}
	}

}
