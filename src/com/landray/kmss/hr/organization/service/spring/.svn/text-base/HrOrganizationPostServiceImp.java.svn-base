package com.landray.kmss.hr.organization.service.spring;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.organization.constant.HrOrgConstant;
import com.landray.kmss.hr.organization.model.HrOrganizationElement;
import com.landray.kmss.hr.organization.model.HrOrganizationGrade;
import com.landray.kmss.hr.organization.model.HrOrganizationPost;
import com.landray.kmss.hr.organization.model.HrOrganizationPostSeq;
import com.landray.kmss.hr.organization.model.HrOrganizationRank;
import com.landray.kmss.hr.organization.service.IHrOrganizationElementService;
import com.landray.kmss.hr.organization.service.IHrOrganizationGradeService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostSeqService;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostService;
import com.landray.kmss.hr.organization.service.IHrOrganizationRankService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.organization.util.HrOrganizationUtil;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationPostServiceImp extends HrOrganizationElementServiceImp
		implements IHrOrganizationPostService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;
    
    @Override
	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

	private IHrStaffPersonInfoService hrStaffPersonInfoService;

	public void setHrStaffPersonInfoService(IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}

	private IHrOrganizationElementService hrOrganizationElementService;

	public void setHrOrganizationElementService(IHrOrganizationElementService hrOrganizationElementService) {
		this.hrOrganizationElementService = hrOrganizationElementService;
	}

	private IHrOrganizationPostSeqService hrOrganizationPostSeqService;

	public void setHrOrganizationPostSeqService(IHrOrganizationPostSeqService hrOrganizationPostSeqService) {
		this.hrOrganizationPostSeqService = hrOrganizationPostSeqService;
	}

	private IHrOrganizationRankService hrOrganizationRankService;

	public void setHrOrganizationRankService(IHrOrganizationRankService hrOrganizationRankService) {
		this.hrOrganizationRankService = hrOrganizationRankService;
	}

	private IHrOrganizationGradeService hrOrganizationGradeService;

	public void setHrOrganizationGradeService(IHrOrganizationGradeService hrOrganizationGradeService) {
		this.hrOrganizationGradeService = hrOrganizationGradeService;
	}

	@Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		HrOrganizationPost hrOrganizationPost = (HrOrganizationPost) model;
	}

	@Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		HrOrganizationPost hrOrganizationPost = new HrOrganizationPost();
		HrOrganizationUtil.initModelFromRequest(hrOrganizationPost, requestContext);
		return hrOrganizationPost;
	}

	@Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof HrOrganizationPost) {
			HrOrganizationPost hrOrganizationPost = (HrOrganizationPost) model;
		}
		return model;
	}

	@Override
	public List<HrStaffPersonInfo> getPostPersons(String fdId) throws Exception {

		return null;
	}
	
	@Override
	public JSONObject addImportData(InputStream inputStream, Locale locale) throws Exception {
		org.apache.poi.ss.usermodel.Sheet sheet = null;
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(inputStream); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);
			return addImportData(sheet, locale);
		} catch (IOException e) {
			//otherErrors.add(e.getMessage());
		}finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}
		return null;
	}
	
	
	@Override
	public JSONObject addImportData(org.apache.poi.ss.usermodel.Sheet sheet,
									Locale locale) throws Exception {
		//Workbook wb = null;
		JSONObject result = new JSONObject();
		JSONArray titles = new JSONArray(); // 标题头
		JSONArray errorRows = new JSONArray(); // 每个错误行（包含错误行号，错误列号，行的错误信息）
		JSONArray otherErrors = new JSONArray(); // 其他错误
		int columnSize = 9;
		int successCount = 0, failCount = 0;
	/*	try {
			wb = WorkbookFactory.create(inputStream); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);
		} catch (IOException e) {
			otherErrors.add(e.getMessage());
		} catch (InvalidFormatException e) {
			otherErrors.add(e.getMessage());
		}*/
		// 数据必须大于columnSize-1列，且不能少于2行
		if (sheet.getLastRowNum() < 1 || sheet.getRow(0).getLastCellNum() < columnSize - 1) {
			otherErrors.add(HrOrgUtil.getStr("hrOrganization.import.template.file.not.null"));
		} else {
			HrOrganizationPost post = null;
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
				//岗位名称
				String postNameValue = HrOrgUtil.getCellValue(row.getCell(0));
				//岗位编码
				String postCodeValue = HrOrgUtil.getCellValue(row.getCell(1));
				//所属组织
				String orgNameValue = HrOrgUtil.getCellValue(row.getCell(2));
				//岗位序列
				String postSeqValue = HrOrgUtil.getCellValue(row.getCell(3));
				//岗位领导
				String postLeaderValue = HrOrgUtil.getCellValue(row.getCell(4));

				String gradeMixValue = HrOrgUtil.getCellValue(row.getCell(5));
				String gradeMaxValue = HrOrgUtil.getCellValue(row.getCell(6));
				//职级下限
				String rankMixValue = HrOrgUtil.getCellValue(row.getCell(7));
				//职级上限
				String rankMaxValue = HrOrgUtil.getCellValue(row.getCell(8));
				//关键岗位
				String fdIsKeyValue = HrOrgUtil.getCellValue(row.getCell(9));
				//涉密岗位
				String fdIsSecretValue = HrOrgUtil.getCellValue(row.getCell(10));
				//是否业务相关
				String fdIsBusiness = HrOrgUtil.getCellValue(row.getCell(11));

				post = new HrOrganizationPost();
				if (StringUtil.isNotNull(postNameValue)) {
					if (checkNameUnique(postNameValue)) {
						post.setFdName(postNameValue);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 0,
								HrOrgUtil.getStr("hrOrganizationPost.fdName.Unique"));
					}
				} else {
					HrOrgUtil.addRowError(errorRow, rowIndex, 0, HrOrgUtil.getStr("hrOrganizationPost.fdName.notnull"));
				}
				if (StringUtil.isNotNull(postCodeValue)) {
					try {
						super.checkFdNo(null, post.getFdOrgType(), postCodeValue);
					} catch (Exception e) {
						HrOrgUtil.addRowError(errorRow, rowIndex, 1,
								HrOrgUtil.getStr("hrOrganizationPost.fdCode.Unique"));
					}
					post.setFdNo(postCodeValue);
				} else {
					HrOrgUtil.addRowError(errorRow, rowIndex, 1, HrOrgUtil.getStr("hrOrganizationPost.fdCode.notnull"));
				}

				HrOrganizationElement organizationElement = this.findByOrgNoAndName(postCodeValue, postNameValue);
				if (null != organizationElement) {
					post = (HrOrganizationPost) organizationElement;
				}

				if (StringUtil.isNotNull(orgNameValue)) {
					HrOrganizationElement hrOrganizationElement = hrOrganizationElementService.findOrgByName(orgNameValue);
					if (null != hrOrganizationElement) {
						post.setFdParent(hrOrganizationElement);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 6,
								HrOrgUtil.getStr("hrOrganizationPost.org.not.exist"));
					}
				}
				if (StringUtil.isNotNull(postSeqValue)) {
					HrOrganizationPostSeq postSeq = hrOrganizationPostSeqService.findByName(postSeqValue);
					if (null != postSeq) {
						post.setFdPostSeq(postSeq);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 7, HrOrgUtil.getStr("hrOrganizationPost.seq.exist"));
					}
				}
				if (StringUtil.isNotNull(postLeaderValue)) {
					HrOrganizationElement hrOrganizationElement = hrOrganizationElementService.findOrgByName(postLeaderValue);
					if (null != hrOrganizationElement) {
						post.setHbmThisLeader(hrOrganizationElement);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 8,
								HrOrgUtil.getStr("hrOrganizationPost.leader.exist"));
					}
				}
				if (StringUtil.isNotNull(rankMixValue)) {
					HrOrganizationRank rank = hrOrganizationRankService.findByName(rankMixValue);
					if (null != rank) {
						post.setFdRankMix(rank);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 4,
								HrOrgUtil.getStr("hrOrganizationPost.rank.scope.mix.exist"));
					}
				}
				if (StringUtil.isNotNull(rankMaxValue)) {
					HrOrganizationRank rank = hrOrganizationRankService.findByName(rankMaxValue);
					if (null != rank) {
						post.setFdRankMax(rank);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 5,
								HrOrgUtil.getStr("hrOrganizationPost.rank.scope.max.exist"));
					}
				}
				if (StringUtil.isNotNull(gradeMixValue)) {
					HrOrganizationGrade grade = hrOrganizationGradeService.getHrOrgGradeByName(gradeMixValue);
					if (null != grade) {
						post.setFdGradeMix(grade);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 2,
								HrOrgUtil.getStr("hrOrganizationPost.grade.scope.mix.exist"));
					}
				}
				if (StringUtil.isNotNull(gradeMaxValue)) {
					HrOrganizationGrade grade = hrOrganizationGradeService.getHrOrgGradeByName(gradeMaxValue);
					if (null != grade) {
						post.setFdGradeMax(grade);
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 3,
								HrOrgUtil.getStr("hrOrganizationPost.grade.scope.max.exist"));
					}
				}
				if (StringUtil.isNotNull(fdIsKeyValue)) {
					post.setFdIsKey("是".equals(fdIsKeyValue) ? "true" : "false");
				}
				if (StringUtil.isNotNull(fdIsSecretValue)) {
					post.setFdIsSecret("是".equals(fdIsSecretValue) ? "true" : "false");
				}
				if (StringUtil.isNotNull(fdIsBusiness)) {
					post.setFdIsBusiness("否".equals(fdIsBusiness) ? Boolean.FALSE : Boolean.TRUE);
				}else{
					post.setFdIsBusiness(Boolean.TRUE);
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
						this.add(post);
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
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.fdName"));
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.fdCode"));
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.org"));
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.seq"));
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.leader"));
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.rank.scope.mix"));
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.rank.scope.max"));
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.is.key"));
				titles.add(HrOrgUtil.getStr("hrOrganizationPost.is.secret"));
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
		return result;
	}

	@Override
	public HSSFWorkbook buildTemplateWorkbook(HttpServletRequest request)
			throws Exception {
		/*
		 * String[] baseColumns = new String[] {
		 * HrOrgUtil.getStr("hrOrganizationPost.fdName"),
		 * HrOrgUtil.getStr("hrOrganizationPost.fdCode"),
		 * HrOrgUtil.getStr("hrOrganizationPost.org"),
		 * HrOrgUtil.getStr("hrOrganizationPost.seq"),
		 * HrOrgUtil.getStr("hrOrganizationPost.leader"),
		 * HrOrgUtil.getStr("hrOrganizationPost.rank.scope.mix"),
		 * HrOrgUtil.getStr("hrOrganizationPost.rank.scope.max"),
		 * HrOrgUtil.getStr("hrOrganizationPost.is.key"),
		 * HrOrgUtil.getStr("hrOrganizationPost.is.secret"), }; // 必填的列的下标
		 * Integer[] notNullArr = new Integer[] { 0 }; List notNullList =
		 * Arrays.asList(notNullArr); String filename =
		 * HrOrgUtil.getStr("hrOrganizationPost.import.templateFile"); WorkBook
		 * wb = new WorkBook(); wb.setLocale(request.getLocale()); Sheet sheet =
		 * new Sheet(); sheet.setTitle(filename); for (int i = 0; i <
		 * baseColumns.length; i++) { Column col = new Column();
		 * col.setTitle(baseColumns[i]); if (notNullList.contains(i))
		 * col.setRedFont(true); sheet.addColumn(col); } List contentList = new
		 * ArrayList(); // 样例数据 Object[] objs = new
		 * Object[sheet.getColumnList().size()]; objs[0] = "Java开发工程师"; objs[1]
		 * = "000"; objs[2] = "研发中心"; objs[3] = "研发类"; objs[4] = "张子怡"; objs[5]
		 * = "P2"; objs[6] = "P3"; objs[7] = "true"; objs[8] = "false";
		 * contentList.add(objs); sheet.setContentList(contentList);
		 * wb.addSheet(sheet); wb.setFilename(filename);
		 */
		HSSFWorkbook wb = new HSSFWorkbook();
		buildPostSheet(wb);
		return wb;
	}

	@Override
	public List findPostsByName(String fdName)
			throws Exception {
		String[] postName = fdName.split(";");
		List<String> postNameList = ArrayUtil.convertArrayToList(postName);

		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "hrOrganizationPost.fdIsAvailable = :fdIsAvailable";
		if (!postNameList.isEmpty()) {
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					HQLUtil.buildLogicIN(
							"hrOrganizationPost.fdName",
							postNameList));
		}
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setWhereBlock(whereBlock);
		List<HrOrganizationElement> list = this.findList(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			return list;
		}
		return null;
	}

	@Override
	public void buildPostSheet(HSSFWorkbook wb) throws Exception {
		String sheetName = HrOrgUtil
				.getStr("hrOrganizationPost.import.templateFile");
		HSSFSheet sheet = wb.createSheet(sheetName);
		String[] baseColumns = new String[] {
				HrOrgUtil.getStr("hrOrganizationPost.fdName"),
				HrOrgUtil.getStr("hrOrganizationPost.fdCode"),
				HrOrgUtil.getStr("hrOrganizationPost.org"),
				HrOrgUtil.getStr("hrOrganizationPost.seq"),
				HrOrgUtil.getStr("hrOrganizationPost.leader"),
				HrOrgUtil.getStr("hrOrganizationPost.grade.scope.mix"),
				HrOrgUtil.getStr("hrOrganizationPost.grade.scope.max"),
				HrOrgUtil.getStr("hrOrganizationPost.rank.scope.mix"),
				HrOrgUtil.getStr("hrOrganizationPost.rank.scope.max"),
				HrOrgUtil.getStr("hrOrganizationPost.is.key"),
				HrOrgUtil.getStr("hrOrganizationPost.is.secret"),
				HrOrgUtil.getStr("hrOrganizationElement.fdIsBusiness"),
		};
		sheet.setDefaultColumnWidth(25); // 设置宽度
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		Integer[] notNullArr = new Integer[] { 0 };
		List notNullList = Arrays.asList(notNullArr);
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
	@Override
	public List<HrOrganizationPost> getPostsByOrgId(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hbmParent.fdId =:fdId and fdOrgType = :fdOrgType");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setParameter("fdOrgType", HrOrgConstant.HR_TYPE_POST);
		return this.findList(hqlInfo);
	}

	@Override
	public List<HrOrganizationPost> getPostsByRankId(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdRankMix.fdId =:fdId or fdRankMax.fdId = :fdId");
		hqlInfo.setParameter("fdId", fdId);
		return hrOrganizationElementService.findList(hqlInfo);
	}

	@Override
	public boolean getPostAndPersonByRankId(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdRankMix.fdId =:fdId or fdRankMax.fdId = :fdId");
		hqlInfo.setParameter("fdId", fdId);
		List list = hrOrganizationElementService.findList(hqlInfo);
		if (ArrayUtil.isEmpty(list)) {
			HQLInfo staffHql = new HQLInfo();
			staffHql.setWhereBlock("hrStaffPersonInfo.fdOrgRank.fdId =:fdId");
			staffHql.setParameter("fdId", fdId);
			IHrStaffPersonInfoService hrStaffPersonInfoService = (IHrStaffPersonInfoService) SpringBeanUtil
					.getBean("hrStaffPersonInfoService");
			List staffList = hrStaffPersonInfoService.findList(staffHql);
			if (!ArrayUtil.isEmpty(staffList)) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	@Override
	public List<HrOrganizationPost> getPostById(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdPostSeq.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdId);
		return this.findList(hqlInfo);
	}

	public boolean checkNameUnique(String fdName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdName = :fdName");
		hqlInfo.setParameter("fdName", fdName);
		List list = this.findList(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			return false;
		}
		return true;
	}

}
