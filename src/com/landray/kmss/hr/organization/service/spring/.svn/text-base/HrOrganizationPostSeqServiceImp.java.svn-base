package com.landray.kmss.hr.organization.service.spring;

import java.io.IOException;
import java.io.InputStream;
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
import com.landray.kmss.hr.organization.model.HrOrganizationPostSeq;
import com.landray.kmss.hr.organization.service.IHrOrganizationPostSeqService;
import com.landray.kmss.hr.organization.util.HrOrgUtil;
import com.landray.kmss.hr.organization.util.HrOrganizationUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HrOrganizationPostSeqServiceImp extends ExtendDataServiceImp implements IHrOrganizationPostSeqService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	@Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof HrOrganizationPostSeq) {
            HrOrganizationPostSeq hrOrganizationPost1Seq = (HrOrganizationPostSeq) model;
            hrOrganizationPost1Seq.setDocAlterTime(new Date());
            hrOrganizationPost1Seq.setDocAlteror(UserUtil.getUser());
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		HrOrganizationPostSeq hrOrganizationPostSeq = new HrOrganizationPostSeq();
		hrOrganizationPostSeq.setDocCreateTime(new Date());
		hrOrganizationPostSeq.setDocAlterTime(new Date());
		hrOrganizationPostSeq.setDocCreator(UserUtil.getUser());
		hrOrganizationPostSeq.setDocAlteror(UserUtil.getUser());
		HrOrganizationUtil.initModelFromRequest(hrOrganizationPostSeq, requestContext);
		return hrOrganizationPostSeq;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        HrOrganizationPostSeq hrOrganizationPost1Seq = (HrOrganizationPostSeq) model;
    }

	@Override
	public WorkBook buildTemplateWorkbook(HttpServletRequest request) throws Exception {
		String[] baseColumns = new String[] { HrOrgUtil.getStr("hrOrganizationPostSeq.fdName"),
				HrOrgUtil.getStr("hrOrganizationPostSeq.fdDesc"), };
		// 必填的列的下标
		Integer[] notNullArr = new Integer[] { 0 };
		List notNullList = Arrays.asList(notNullArr);
		String filename = HrOrgUtil.getStr("hrOrganizationPostSeq.import.templateFile");
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
		objs[0] = "技术序列";
		objs[1] = "描述";
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
		int columnSize = 2;
		int successCount = 0, failCount = 0;
		try {
			wb = WorkbookFactory.create(inputStream); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);

			// 数据必须大于columnSize-1列，且不能少于2行
			if (sheet.getLastRowNum() < 1 || sheet.getRow(0).getLastCellNum() < columnSize - 1) {
				otherErrors.add(HrOrgUtil.getStr("hrOrganization.import.template.fileError"));
			} else {
				HrOrganizationPostSeq postSeq = null;
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
					//职级
					String nameValue = HrOrgUtil.getCellValue(row.getCell(0));
					//所属职等
					String descValue = HrOrgUtil.getCellValue(row.getCell(1));

					postSeq = new HrOrganizationPostSeq();
					if (StringUtil.isNotNull(nameValue)) {
						boolean flag = checkNameUnique(nameValue, null);
						if (flag) {
							postSeq.setFdName(nameValue);
						} else {
							HrOrgUtil.addRowError(errorRow, rowIndex, 0,
									HrOrgUtil.getStr("hrOrganizationPostSeq.fdName.Unique"));
						}
					} else {
						HrOrgUtil.addRowError(errorRow, rowIndex, 0,
								HrOrgUtil.getStr("hrOrganizationPostSeq.fdName.notnull"));
					}
					if (StringUtil.isNotNull(descValue)) {
						postSeq.setFdDesc(descValue);
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
							this.add(postSeq);
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
					titles.add(HrOrgUtil.getStr("hrOrganizationPostSeq.fdName"));
					titles.add(HrOrgUtil.getStr("hrOrganizationPostSeq.fdDesc"));
					titles.add(HrOrgUtil.getStr("hrOrganization.import.errorDetails")); // 错误详情
					result.put("titles", titles);
					String importMsg = ResourceUtil.getString("hrOrganization.import.format.msg", "hr-organization", locale,
							new Object[]{successCount, failCount});
					result.put("importMsg", importMsg);
				} else { // 无错误
					String importMsg = ResourceUtil.getString("hrOrganization.import.format.msg.succ", "hr-organization",
							locale, new Object[]{successCount});
					result.put("importMsg", importMsg);
				}
			}
			result.put("otherErrors", otherErrors);
		} catch (IOException e) {
			otherErrors.add(e.getMessage());
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}
		return result;
	}


	@Override
	public HrOrganizationPostSeq findByName(String fdName) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrOrganizationPostSeq.fdName = :fdName");
		hqlInfo.setParameter("fdName", fdName);
		List<HrOrganizationPostSeq> list = this.findList(hqlInfo);
		return ArrayUtil.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public boolean checkNameUnique(String fdName, String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String where = "fdName = :fdName";
		if(StringUtil.isNotNull(fdId)){
			where += " and fdId !=:fdId";
			hqlInfo.setParameter("fdId", fdId);
		}
		hqlInfo.setWhereBlock(where);
		hqlInfo.setSelectBlock("fdName");
		hqlInfo.setParameter("fdName", fdName);
		List<String> list = this.findList(hqlInfo);
		if (!ArrayUtil.isEmpty(list)) {
			return false;
		}
		return true;
	}
}
