package com.landray.kmss.hr.staff.service.spring;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventAsyncCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.hr.staff.forms.HrStaffPayrollIssuanceForm;
import com.landray.kmss.hr.staff.model.HrStaffPayrollIssuance;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffSalaryInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPayrollIssuanceService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.service.IHrStaffSalaryInfoService;
import com.landray.kmss.hr.staff.util.ExcelParseCache;
import com.landray.kmss.hr.staff.util.HrStaffPersonUtil;
import com.landray.kmss.hr.staff.util.ResultBean;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyTodoProvider;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.sys.notify.service.ISysNotifyTodoService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.DESEncrypt;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.upload.FormFile;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;


public class HrStaffPayrollIssuanceServiceImp extends BaseServiceImp
		implements IHrStaffPayrollIssuanceService,IEventMulticasterAware  {

	private static final String EXCEPT_STRING = "¤§°±·×àáèéêìíòó÷ùúü";

	private static DecimalFormat formatter = new DecimalFormat(
			"####################");
	
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private IHrStaffPersonInfoService hrStaffPersonInfoService;
	private IHrStaffSalaryInfoService hrStaffSalaryInfoService;
	private ISysNotifyTodoService sysNotifyTodoService;
	private IEventMulticaster multicaster;
	private ThreadPoolTaskExecutor taskExecutor;
	private ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
		if(taskExecutor ==null) {
			taskExecutor =  (ThreadPoolTaskExecutor) SpringBeanUtil.getBean("staffTaskExecutor");
		}
		return taskExecutor;
	}
	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;
	}
	
	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}
	public void setHrStaffPersonInfoService(
			IHrStaffPersonInfoService hrStaffPersonInfoService) {
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
	}
	
	public void setHrStaffSalaryInfoService(
			IHrStaffSalaryInfoService hrStaffSalaryInfoService) {
		this.hrStaffSalaryInfoService = hrStaffSalaryInfoService;
	}

	@Override
	public HSSFWorkbook buildTempletWorkBook(String fdExportDeptId)
			throws Exception {
		return buildTempletWorkBook(getModelName(),
				getImportFields(),
				getFieldComments(), getItemNode(), fdExportDeptId);
	}

	public void setSysNotifyTodoService(
			ISysNotifyTodoService sysNotifyTodoService) {
		this.sysNotifyTodoService = sysNotifyTodoService;
	}
	
	/**
	 * 构建模板文件
	 * 
	 * @param modelName
	 * @param importFields
	 * @return
	 */
	public HSSFWorkbook buildTempletWorkBook(String modelName,
			String[] importFields, String[] fieldComments,
			List<String> itemNodes, String fdExportDeptId) throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(ResourceUtil
				.getString("hr-staff:hrStaff.payroll.sheet1.title"));
		sheet.setDefaultColumnWidth(25); // 设置宽度
		// sheet.protectSheet("123");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		// 第四步，创建单元格
		HSSFCell cell = row.createCell(0);
		// 单元格样式
		HSSFFont font1 = wb.createFont();
		font1.setBold(true); // 字体增粗
		// 定义必填字体效果
		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色
		
		// 备注
		HSSFPatriarch patr = sheet.createDrawingPatriarch();

		/********** 设置头部内容 **********/
		// 第一列为：姓名
		cell.setCellValue(ResourceUtil
				.getString("hr-staff:hrStaffPayrollIssuance.fdName"));
		HSSFCellStyle style = getStyle(wb);
		style.setFont(font2);
		cell.setCellStyle(style);
		cell.setCellComment(buildComment(patr, ResourceUtil
				.getString("hr-staff:hrStaff.import.templet.required")));

		HSSFCell cell1 = row.createCell(1);

		// 第二列为：所属组织名称全路径
		cell1.setCellValue(ResourceUtil
				.getString("hr-staff:hrStaffPayrollIssuance.fdFdOrg"));
		cell1.setCellStyle(style);
		cell1.setCellComment(buildComment(patr, ResourceUtil
				.getString("hr-staff:hrStaff.import.templet.required")));

		// 第三列为：登录账号
		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue(ResourceUtil
				.getString("hr-staff:hrStaffPersonInfo.fdLoginName"));
		cell2.setCellStyle(style);
		cell2.setCellComment(buildComment(patr, ResourceUtil
				.getString("hr-staff:hrStaff.import.templet.required")));

		// 第四列为：员工组织架构编号
		HSSFCell cell3 = row.createCell(3);
		cell3.setCellValue(
				ResourceUtil.getString("hr-staff:hrStaffPersonInfo.template.fdNo"));
		cell3.setCellStyle(style);

		// 第五列为：员工工号
		HSSFCell cell4 = row.createCell(4);
		cell4.setCellValue(
				ResourceUtil.getString("hr-staff:hrStaffPersonInfo.fdStaffNo"));
		cell4.setCellStyle(style);
		cell4.setCellComment(buildComment(patr, ResourceUtil
				.getString("hr-staff:hrStaff.import.templet.required")));
		// 由用户编辑的列
		Map<String, SysDictCommonProperty> map = SysDataDict.getInstance()
				.getModel(modelName).getPropertyMap();
		String val = null;
		StringBuffer comments = null;
		for (int i = 5; i <= importFields.length + 2; i++) {
			cell = row.createCell(i);
			int _i = i - 5;
			val = importFields[_i];
			SysDictCommonProperty property = map.get(val);
			style = getStyle(wb);
			
			comments = new StringBuffer();
			if (property.isNotNull()) {
				// 必填字段，字体设置为红色
				style.setFont(font2);
				comments.append(ResourceUtil
						.getString("hr-staff:hrStaff.import.templet.required"));
			} else {
				style.setFont(font1);
			}

			// 如果有描述，就增加
			if (fieldComments != null && fieldComments.length > _i) {
				if (StringUtil.isNotNull(comments.toString())
						&& StringUtil.isNotNull(fieldComments[_i])) {
					comments.append(", ");
				}
				comments.append(fieldComments[_i]);
			}

			if (StringUtil.isNotNull(comments.toString())) {
				cell.setCellComment(buildComment(patr, comments.toString()));
			}
			cell.setCellValue(ResourceUtil.getString(property.getMessageKey()));
			cell.setCellStyle(style);
		}

		List<String> status = new ArrayList<String>();
		status.add("trial");
		status.add("official");
		status.add("temporary");
		status.add("trialDelay");
		status.add("retire");
		status.add("practice");
		
		// 组织构架可能会有大量的人员数据，这里不能一次取所有，需要分页获取
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName("com.landray.kmss.hr.staff.model.HrStaffPersonInfo"); 
		hqlInfo.setWhereBlock("hrStaffPersonInfo.fdIsAvailable = :fdIsAvailable and hrStaffPersonInfo.fdStatus in (:status)");
		// 排序规则：层级长度，层级，排序号，名称
		hqlInfo.setOrderBy("length(hrStaffPersonInfo.fdHierarchyId), hrStaffPersonInfo.fdHierarchyId, hrStaffPersonInfo.fdOrder, hrStaffPersonInfo." + SysLangUtil.getLangFieldName("fdName"));
		
		hqlInfo.setParameter("fdIsAvailable", true);
		hqlInfo.setParameter("status", status);
		if (StringUtil.isNotNull(fdExportDeptId)) {
			hqlInfo.setJoinBlock("left join hrStaffPersonInfo.fdOrgPerson info");
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					" info.fdId = hrStaffPersonInfo.fdOrgPerson.fdId and info.fdHierarchyId like :fdExportDeptId");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdExportDeptId", "%" + fdExportDeptId + "%");
		}
		hqlInfo.setPageNo(1);
		hqlInfo.setRowSize(1000);
		Page page = hrStaffPersonInfoService.findPage(hqlInfo);
		List<HrStaffPersonInfo> list = page.getList();
		// 单元格样式
		HSSFCellStyle style2_1 = wb.createCellStyle();
		style2_1.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平居中
		style2_1.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		HSSFCellStyle style2_2 = wb.createCellStyle();
		style2_2.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		int index = 1; // 从第二行开始填写数据（第一行是标题）
		for (int i = 0; i < page.getTotal(); i++) {
			int pageNo = i + 1;
			if (pageNo == 1) {
				// 处理第一页的数据
				setSheet(sheet, index, list);
				index += list.size();
			} else {
				// 处理第二页以后的数据
				hqlInfo.setPageNo(pageNo);
				page = hrStaffPersonInfoService.findPage(hqlInfo);
				list = page.getList();
				setSheet(sheet, index, list);
				index += list.size();
			}
			
		}
		
		// 注意事项
		if (itemNodes != null && !itemNodes.isEmpty()) {
			HSSFSheet sheet2 = wb.createSheet(ResourceUtil
					.getString("hr-staff:hrStaff.payroll.sheet2.title"));
			sheet2.setColumnWidth(0, 35 * 80); // 第一列宽度
			sheet2.setColumnWidth(1, 35 * 600); // 第二列宽度
			HSSFRow row2 = null;
			HSSFCell cellSheet2 = null;
			style = getStyle(wb);
			style.setFont(font1);

			row2 = sheet2.createRow((int) 0);
			row2.setHeight((short) (20 * 20));
			cellSheet2 = row2.createCell(0);
			cellSheet2.setCellValue(ResourceUtil
					.getString("hr-staff:hrStaff.payroll.sheet2.serial"));
			cellSheet2.setCellStyle(style);

			cellSheet2 = row2.createCell(1);
			cellSheet2.setCellValue(ResourceUtil
					.getString("hr-staff:hrStaff.payroll.sheet2.item"));
			cellSheet2.setCellStyle(style);

			
			for (int i = 0; i < itemNodes.size(); i++) {
				row2 = sheet2.createRow((int) (i + 1));
				row2.setHeight((short) (20 * 20));
				// row.setHeight((short) (20 * 20));
				cellSheet2 = row2.createCell(0);
				cellSheet2.setCellValue(String.valueOf(i + 1));
				cellSheet2.setCellStyle(style2_1);

				cellSheet2 = row2.createCell(1);
				cellSheet2.setCellValue(itemNodes.get(i));
				cellSheet2.setCellStyle(style2_2);
			}
		}
		sheet.setColumnHidden(2, true);
		return wb;
	}

	private void setSheet(HSSFSheet sheet, int index, List<HrStaffPersonInfo> personList) {
		// 根据组织架构里的人员决定有多少行，并把读出组织架构中的数据读出
		HSSFRow row = null;
		for (HrStaffPersonInfo person : personList) {
			row = sheet.createRow(index++);
			HSSFCell celli1 = row.createCell(0);
			celli1.setCellValue(person.getFdName());
			HSSFCell celli2 = row.createCell(1);
			celli2.setCellValue(HrStaffPersonUtil.getFdOrgParentsName(person.getFdOrgParent()));
			HSSFCell celli3 = row.createCell(2);
			celli3.setCellValue(person.getFdLoginName());
			if(person.getFdOrgPerson()!=null) {
				HSSFCell celli4 = row.createCell(3);
				celli4.setCellValue(person.getFdOrgPerson().getFdNo());
			}
			HSSFCell celli5 = row.createCell(4);
			celli5.setCellValue(person.getFdStaffNo());
		}
	}

	
	/**
	 * 构建批注
	 * 
	 * @param patr
	 * @param value
	 * @return
	 */
	private static HSSFComment buildComment(HSSFPatriarch patr, String value) {
		// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
		HSSFClientAnchor anrhor = null;
		if (value.length() < 50) {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 2,
					4);
		} else if (value.length() > 150) {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3,
					7);
		} else {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3,
					5);
		}
		HSSFComment comment = patr.createComment(anrhor);
		comment.setString(new HSSFRichTextString(value));
		return comment;
	}
	
	
	public String[] getImportFields() {
		// 基本工资、岗位工资、交通补助、公积金、社保医保、备注
		return new String[] { "fdBasicWage", "fdPositionSalary",
				"fdTransAllowance", "fdHousingFund", "fdSocialInsuBasicMed",
				"fdRemark" };
	}

	private void buildEmailAndNotifyContent(final int colSize, final HSSFRow headRow, HSSFRow row, StringBuilder sb,
			JSONObject jsonContent) {
		HSSFCell cell;
		for (int j = 5; j <= colSize - 1; j++) {

			String headRowCell = ImportUtil.getCellValue(headRow.getCell(j));
			String fdRemarkCell = ResourceUtil.getString("hr-staff:hrStaffPayrollIssuance.fdRemark");

			if (!headRowCell.equals(fdRemarkCell)) {
				sb.append(headRowCell).append(":");
				if (row.getCell(j) == null) {
					sb.append("0.00");
					sb.append("\n");
				} else {
					cell = row.getCell(j);
					String val = "";
					switch (cell.getCellType()) {
					case NUMERIC:
						val = formatDouble(row.getCell(j).getNumericCellValue());
						break;
					default:
						val = cell.getRichStringCellValue().getString();
					}
					sb.append(val);
					sb.append("\n");

					if (jsonContent != null) {
						jsonContent.put(headRowCell, val);
					}
				}
			} else {
				sb.append(fdRemarkCell).append(":");
				String val = "";
				if (row.getCell(j) == null) {
					sb.append(val);
				} else {
					val = ImportUtil.getCellValue(row.getCell(j));
					sb.append(val);

					if (jsonContent != null) {
						jsonContent.put(headRowCell, val);
					}
				}
			}
		}
	}
	
	private HSSFSheet getFirstSheet(FormFile file) throws IOException, FileNotFoundException {
		String fileName = file.getFileName();
		if (!fileName.endsWith(".xls")) {
			throw new KmssRuntimeException(new KmssMessage("hr-staff:hrStaffPayrollIssuance.import.error.msg"));
		}
		POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		return sheet;
	}
	/**
	 * 给excel表中的员工通过发送工资单，异步方式 by 付昊 20210330
	 * 
	 * @param hrSalaryPayrollIssuance
	 * @param hrSalaryPayrollIssuanceForm
	 * @return
	 */
	public HrStaffPayrollIssuance sendPayrollByEmailAsync(HrStaffPayrollIssuance hrStaffPayrollIssuance,
			HrStaffPayrollIssuanceForm hrStaffPayrollIssuanceForm) throws Exception {
		FormFile file = hrStaffPayrollIssuanceForm.getFile();
		HSSFSheet sheet = getFirstSheet(file);

		int rowSize = sheet.getLastRowNum();
		final int colSize = sheet.getRow(0).getPhysicalNumberOfCells();
		final HSSFRow headRow = sheet.getRow(0);

		ExcelParseCache excelCache = ExcelParseCache.newCache(hrStaffPayrollIssuance.getFdId());
		String type = hrStaffPayrollIssuanceForm.getFdNotifyType();
		excelCache.setParam("type", type);
		for (int i = 0; i <= rowSize - 1; i++) {
			Map<String, Object> excelRow = new HashMap<>();
			HSSFRow row = sheet.getRow(i + 1);
			HSSFCell cell = null;
			cell = row.getCell(0);
			String userName = cell == null ? "" : getStringVal(cell);
			excelRow.put("userName", userName);
			cell = row.getCell(1);
			String deptName = cell == null ? "" : getStringVal(cell);
			excelRow.put("deptName", deptName);
			cell = row.getCell(2);
			String loginName = cell == null ? "" : getStringVal(cell);
			excelRow.put("loginName", loginName);
			cell = row.getCell(3);
			String fdNo = cell == null ? "" : getStringVal(cell);
			excelRow.put("fdNo", fdNo);
			cell = row.getCell(4);
			String fdStaffNo = cell == null ? "" : getStringVal(cell);
			excelRow.put("staffNo", fdStaffNo);

			StringBuilder sb = new StringBuilder();
			JSONObject jsonContent = null;
			if (type.contains("todo")) {
				jsonContent = new JSONObject();
				excelRow.put("jsonContent", jsonContent);
			}
			buildEmailAndNotifyContent(colSize, headRow, row, sb, jsonContent);
			excelRow.put("emailContent", sb);
			excelCache.addRow(excelRow);
		}
		
		if(!CollectionUtils.isEmpty(excelCache.getRows())) {
			hrStaffPayrollIssuance.setFdResultMseeage(ResourceUtil.getString("hr-salary:hrSalary.payroll.message.async"));
		}else {
			hrStaffPayrollIssuance.setFdResultMseeage(ResourceUtil.getString("hr-salary:hrSalary.payroll.message.emptyFile"));
		}
		return hrStaffPayrollIssuance;
	}

	private String getStringVal(HSSFCell cell){
		cell.setCellType(CellType.STRING);
		return ImportUtil.getCellValue(cell);
	}


	// 给excel表中的员工通过邮件发送工资单
	@Override
	public HrStaffPayrollIssuance sendPayrollByEmail(
			HrStaffPayrollIssuance hrStaffPayrollIssuance,
			HrStaffPayrollIssuanceForm hrStaffPayrollIssuanceForm)
			throws Exception {
		String type = hrStaffPayrollIssuanceForm.getFdNotifyType();
		boolean emailFlag = true;
		boolean todoFlag = true;
		// 首先获取excel表
		FormFile file = hrStaffPayrollIssuanceForm.getFile();
		String fileName = file.getFileName();
		if (!fileName.endsWith(".xls")) {
			throw new KmssRuntimeException(new KmssMessage(
					"hr-staff:hrStaffPayrollIssuance.import.error.msg"));
		}
		POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFSheet sheet = wb.getSheetAt(0);
		final int colSize = sheet.getRow(0).getPhysicalNumberOfCells();
		int rowSize = sheet.getLastRowNum();
		// 获取表中的人员作为发送对象
		List<String> staffEmail = new ArrayList<String>();
		ResultBean resultEmail = new ResultBean();
		ResultBean resultTodo = new ResultBean();
		StringBuffer rsTodo = new StringBuffer();
		StringBuffer rsEmail = new StringBuffer();
		rsEmail.append(ResourceUtil.getString("hr-staff:hrStaff.payroll.email.error"));
		rsEmail.append("\n");
		rsTodo.append(ResourceUtil.getString("hr-staff:hrStaff.payroll.todo.error"));
		rsTodo.append("\n");
		 for (int i = 0; i <= rowSize - 1; i++) {
			StringBuffer sb = new StringBuffer();
			List targets = new ArrayList();
			HSSFRow row = sheet.getRow(i + 1);
			HSSFRow headRow = sheet.getRow(0);
			HSSFCell cell = null;
			cell = row.getCell(2);

			// 目前登入名只能为字符串
			String loginName = cell == null?null:cell.getStringCellValue();
			// 获取组织架构编号的单元格
			HSSFCell cell2 = row.getCell(3);
			String fdNo = cell2==null?null:getCellValue(cell2);
			// 获取工号的单元格
			HSSFCell cell3 = row.getCell(4);
			String fdStaffNo = cell3==null?null:getCellValue(cell3);
			if((cell == null || StringUtil.isNull(loginName)) && (cell2 == null
					|| StringUtil.isNull(fdNo)) && (cell3 == null
					|| StringUtil.isNull(fdStaffNo))) {
				staffEmail.add("");
				resultTodo.addFailCount();
				rsTodo.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i+1));
				rsTodo.append(row.getCell(0));
				rsTodo.append("(").append(row.getCell(1)).append(")");
				rsTodo.append("\n");
				todoFlag = false;
				continue;
			}
			HQLInfo hqlInfo = new HQLInfo();
			StringBuilder sBuilder=new StringBuilder();
			sBuilder.append("1=1");
			if(StringUtil.isNotNull(loginName)) {
				sBuilder.append(" and hrStaffPersonInfo.fdOrgPerson.fdLoginName =:loginName ");
				hqlInfo.setParameter("loginName", loginName);
			}
			if(StringUtil.isNotNull(fdNo)) {
				sBuilder.append(" and hrStaffPersonInfo.fdOrgPerson.fdNo =:fdNo ");
				hqlInfo.setParameter("fdNo", fdNo);
			}
			if(StringUtil.isNotNull(fdStaffNo)) {
				sBuilder.append(" and hrStaffPersonInfo.fdStaffNo =:fdStaffNo ");
				hqlInfo.setParameter("fdStaffNo", fdStaffNo);
			}
			hqlInfo.setWhereBlock(sBuilder.toString());
			List personList = hrStaffPersonInfoService.findList(hqlInfo);
			// 过滤无效数据
			if (personList.isEmpty()) {
				staffEmail.add("");
				resultTodo.addFailCount();
				rsTodo.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i+1));
				rsTodo.append(row.getCell(0));
				rsTodo.append("(").append(row.getCell(1)).append(")");
				rsTodo.append("\n");
				todoFlag = false;
				continue;
			}
			HrStaffPersonInfo hrStaffPersonInfo = (HrStaffPersonInfo) personList.get(0);
			staffEmail.add(hrStaffPersonInfo.getFdOrgPerson().getFdEmail());
			targets.add(hrStaffPersonInfo.getFdOrgPerson());
			
			JSONObject jsonContent = null;
			if(type.contains("todo")){
				jsonContent = new JSONObject();
			}
			for (int j = 5; j <= colSize - 1; j++) {
				String headRowCell = ImportUtil
						.getCellValue(headRow.getCell(j));
				String fdRemarkCell = ResourceUtil
						.getString("hr-staff:hrStaffPayrollIssuance.fdRemark");
				if (!headRowCell.equals(fdRemarkCell)) {
					
					sb.append(headRowCell).append(":");
							
					if (row.getCell(j) == null) {
						sb.append("0.00");
						sb.append("\n");
					} else {
						cell = row.getCell(j);
						String val = "";
						switch (cell.getCellType()) {
						case NUMERIC:
							val = formatDouble(row.getCell(j).getNumericCellValue());
							break;
						default:
							val = cell.getRichStringCellValue().getString();
						}
						sb.append(val);
						sb.append("\n");
						
						if(jsonContent!=null){
							jsonContent.put(headRowCell, val);
						}
					}
				} else {
					sb.append(fdRemarkCell).append(":");
					String val = "";
					if (row.getCell(j) == null) {
						sb.append(val);
					} else {
						val = ImportUtil.getCellValue(row.getCell(j));
						sb.append(val);
					}
					if(jsonContent!=null){
						jsonContent.put(fdRemarkCell, val);
					}
				}
				
			}
			
				//发送待办
				// 获取上下文
				NotifyContext notifyContext = sysNotifyMainCoreService
						.getContext("hr-staff:hrStaffPayrollNotify.notify");
				
				// 设置发布通知人
				notifyContext.setNotifyTarget(targets);
				
				if(type.contains("todo")){
					String salaryId = IDGenerator.generateID();
					HashMap hashMap = new HashMap();
					hashMap.put(
							"hr-staff:hrStaffPayrollIssuance.fdMessageName",
							hrStaffPayrollIssuance.getFdMessageName());
				hashMap.put("hrStaffPayrollNotify.notify.content", hrStaffPayrollIssuance.getFdMessageName());
					
					notifyContext.setKey(hrStaffPersonInfo.getFdOrgPerson().getFdId());
					notifyContext.setNotifyType("todo");
					notifyContext.setFlag(SysNotifyConstant.NOTIFY_TODOTYPE_MANUAL);
					notifyContext.setLink("/hr/staff/hr_staff_payroll_issuance/hrStaffPayrollIssuance.do?method=viewSalary&fdId="+salaryId);
					sysNotifyMainCoreService.send(hrStaffPayrollIssuance,
							notifyContext, hashMap);
					resultTodo.addSuccessCount();
					
					DESEncrypt des = new DESEncrypt();
					if(jsonContent!=null){
						jsonContent.put("salaryTitle", hrStaffPayrollIssuance.getFdMessageName());
						String content = des.encryptString(jsonContent.toString());
						
						HrStaffSalaryInfo hrStaffSalaryInfo = new HrStaffSalaryInfo();
						hrStaffSalaryInfo.setFdId(salaryId);
						hrStaffSalaryInfo.setFdSalaryContent(content);
						hrStaffSalaryInfo.setFdPayrollIssuanceId(hrStaffPayrollIssuance.getFdId());
						hrStaffSalaryInfo.setFdPersonId(hrStaffPersonInfo.getFdOrgPerson().getFdId());
						hrStaffSalaryInfoService.add(hrStaffSalaryInfo);
					}
					
				}
				
				// 如果信息无误就发邮件
				if (!StringUtil.isNull(staffEmail.get(i))) {					
					if(type.contains("email")){
						notifyContext.setKey("payrollKey");
						sb.append("\n\n").append(ResourceUtil.getString(
								"hrStaffPayrollNotify.email.notify.subject", "hr-staff"));
						HashMap hashMap = new HashMap();
						hashMap.put(
								"hr-staff:hrStaffPayrollIssuance.fdMessageName",
								hrStaffPayrollIssuance.getFdMessageName());
						hashMap.put(
							"hrStaffPayrollNotify.notify.content",
								sb.toString());						
						notifyContext.setNotifyType("email");
						sysNotifyMainCoreService.send(hrStaffPayrollIssuance,
								notifyContext, hashMap);
					}

				}				
		}
		
		// 查询表中的信息是否错误，如果错误返回相应信息
		if (type.contains("email")) {
			for (int i = 1; i <= rowSize; i++) {
				if (staffEmail.size() >= i && StringUtil.isNull(staffEmail.get(i - 1))) {
					emailFlag = false;
					resultEmail.addFailCount();
					HSSFRow row = sheet.getRow(i);
					HSSFCell nameCell, orgCell;
					nameCell = row.getCell(0);
					orgCell = row.getCell(1);
					rsEmail.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i));
					rsEmail.append(nameCell);
					rsEmail.append("(").append(orgCell).append(")");
					rsEmail.append("\n");
				} else {
					resultEmail.addSuccessCount();
				}
			}
		}
		if (!emailFlag||!todoFlag) {
			if(!emailFlag&&!todoFlag) {
                hrStaffPayrollIssuance.setFdResultDetailMseeage(rsTodo.toString()+"\n"+rsEmail.toString());
            } else if(!todoFlag) {
                hrStaffPayrollIssuance.setFdResultDetailMseeage(rsTodo.toString());
            } else if(!emailFlag) {
                hrStaffPayrollIssuance.setFdResultDetailMseeage(rsEmail.toString());
            }
		} else {
			hrStaffPayrollIssuance.setFdResultDetailMseeage(ResourceUtil
					.getString("hr-staff:hrStaff.payroll.success"));
		}
		if(type.contains("email")&&type.contains("todo")){
			hrStaffPayrollIssuance.setFdResultMseeage(ResourceUtil.getString(
					"hrStaff.payroll.todo.result", "hr-staff", null, new Object[] {
							resultTodo.getSuccessCount(), resultTodo.getFailCount() })+"\n"+ResourceUtil.getString(
					"hrStaff.payroll.email.result", "hr-staff", null, new Object[] {
							resultEmail.getSuccessCount(), resultEmail.getFailCount() }));
			
		}else if(type.contains("todo")){
			hrStaffPayrollIssuance.setFdResultMseeage(ResourceUtil.getString(
					"hrStaff.payroll.todo.result", "hr-staff", null, new Object[] {
							resultTodo.getSuccessCount(), resultTodo.getFailCount() }));	
			
		}else if(type.contains("email")){
			hrStaffPayrollIssuance.setFdResultMseeage(ResourceUtil.getString(
					"hrStaff.payroll.email.result", "hr-staff", null, new Object[] {
							resultEmail.getSuccessCount(), resultEmail.getFailCount() }));			
		}
		return hrStaffPayrollIssuance;
		
	}
	
	@Override
	public JSONObject getSalaryContent(String fdId) throws Exception{
		HrStaffSalaryInfo hrStaffSalaryInfo = (HrStaffSalaryInfo) hrStaffSalaryInfoService.findByPrimaryKey(fdId);
		HrStaffPayrollIssuance hrStaffPayrollIssuance = (HrStaffPayrollIssuance) findByPrimaryKey(hrStaffSalaryInfo.getFdPayrollIssuanceId());
		if(hrStaffSalaryInfo.getFdPersonId().equals(UserUtil.getKMSSUser().getPerson().getFdId())){
			DESEncrypt des = new DESEncrypt();
			String content = des.decryptString(hrStaffSalaryInfo.getFdSalaryContent());
			JSONObject jsonObj= JSONObject.fromObject(content);
			Boolean isDone = false;
			if(StringUtil.isNotNull(content)){
				List<SysNotifyTodo> toDoList = (List<SysNotifyTodo>) sysNotifyTodoService
				.getCoreModels(hrStaffPayrollIssuance, hrStaffSalaryInfo.getFdPersonId());
				
				if(toDoList.size()>0){
					ISysOrgPersonService personService = (ISysOrgPersonService)SpringBeanUtil.getBean("sysOrgPersonService");
					SysOrgPerson person = (SysOrgPerson)personService.findByPrimaryKey(hrStaffSalaryInfo.getFdPersonId());
					List dones = toDoList.get(0).getHbmTodoTargets();
					if(!dones.contains(person)){
						isDone = true;
					}
				}
				jsonObj.put("isDone", isDone);	
				return jsonObj;
			}else {
                return new JSONObject();
            }
		}

		return null;
	}
	
	@Override
	public JSONObject updateSalaryTodoDone(String fdId) throws Exception {
		// TODO Auto-generated method stub
		JSONObject setTodoDone  = new JSONObject();
		HrStaffSalaryInfo hrStaffSalaryInfo = (HrStaffSalaryInfo) hrStaffSalaryInfoService.findByPrimaryKey(fdId);

		HrStaffPayrollIssuance hrStaffPayrollIssuance = (HrStaffPayrollIssuance) findByPrimaryKey(hrStaffSalaryInfo.getFdPayrollIssuanceId());
		
		if(!UserUtil.getKMSSUser().getPerson().getFdId().equals(hrStaffSalaryInfo.getFdPersonId())){
			setTodoDone.put("isSet", false);
			setTodoDone.put("errorCode", 1);
			return setTodoDone;
		}
		
		ISysOrgPersonService personService = (ISysOrgPersonService)SpringBeanUtil.getBean("sysOrgPersonService");
		SysOrgPerson person = (SysOrgPerson)personService.findByPrimaryKey(hrStaffSalaryInfo.getFdPersonId());
		
		ISysNotifyTodoProvider todoProvider = sysNotifyMainCoreService
				.getTodoProvider();
		
		if(hrStaffSalaryInfo!=null && hrStaffPayrollIssuance!=null && person!=null) {
            todoProvider.removePerson(hrStaffPayrollIssuance, person.getFdId(), person);
        }
		
		setTodoDone.put("isSet", true);
		return setTodoDone;
		
	}

	@Override
	public String add(IExtendForm form, RequestContext requestContext)
			throws Exception {
		HrStaffPayrollIssuanceForm hrStaffPayrollIssuanceForm = (HrStaffPayrollIssuanceForm) form;
		IBaseModel model = convertFormToModel(hrStaffPayrollIssuanceForm, null,
				requestContext);
		HrStaffPayrollIssuance hrStaffPayrollIssuance = (HrStaffPayrollIssuance) model;
		hrStaffPayrollIssuance = sendPayrollByEmailAsync(hrStaffPayrollIssuance,
				hrStaffPayrollIssuanceForm);		
		String resultMessage = hrStaffPayrollIssuance.getFdResultMseeage();
		String resultDetailMessage = hrStaffPayrollIssuance
				.getFdResultDetailMseeage();
		hrStaffPayrollIssuanceForm.setFdResultMseeage(resultMessage);
		hrStaffPayrollIssuanceForm.setFdResultDetailMseeage(resultDetailMessage);
		return super.add(hrStaffPayrollIssuanceForm, requestContext);
	}
	
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		String add = super.add(modelObj);
		//添加完之后启动发送线程 by 付昊 20210401
		syncOutAdd((HrStaffPayrollIssuance) modelObj);
		return add;
	}


	private void syncOutAdd(HrStaffPayrollIssuance hrStaffPayrollIssuance) {

		multicaster.attatchEvent(new EventOfTransactionCommit(hrStaffPayrollIssuance), new IEventAsyncCallBack() {
			@Override
			public void execute(ApplicationEvent event) throws Throwable {
				Object obj = event.getSource();
				if (obj instanceof HrStaffPayrollIssuance) {
					HrStaffPayrollIssuance hrStaffPayrollIssuance = (HrStaffPayrollIssuance) obj;
					getThreadPoolTaskExecutor().execute(new HrStaffPayrollIssuanceSendThread(hrStaffPayrollIssuance));
				}
			}
		});
	}
	
	public String[] getFieldComments() {
		return null;
	}
	
	/**
	 * 获取带背景和居中的样式
	 * 
	 * @param wb
	 * @return
	 */
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
	
	
	public static List<String> getItemNode() {
		List<String> item = new ArrayList<String>();
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.payroll.sheet2.item.node1"));
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.payroll.sheet2.item.node2"));
		item.add(ResourceUtil
				.getString("hr-staff:hrStaff.payroll.sheet2.item.node3"));
//		item.add(ResourceUtil
//				.getString("hr-staff:hrStaff.payroll.sheet2.item.node4"));
		return item;
	}
	
	/**
	 * 检查文件是否下载的模板
	 * 
	 * @param sheet
	 * @return
	 */
	private boolean checkFile(Sheet sheet) {
		String[] headers = getImportFields();
		// 正常来说，第一行是标题
		int cellNum = sheet.getRow(0).getLastCellNum();
		return cellNum == (headers.length + 1);
	}
	

	/**
	 * 把excel中的数据转化为默认保留两位小数
	 * 
	 * @param value
	 * @return
	 */
	public static String formatDouble(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.toString();
	}
	
	/**
	 * 获取Excel单元格的字符串值
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		if (cell == null) {
            return null;
        }
		String rtnStr;
		switch (cell.getCellType()) {
		case BOOLEAN:
			rtnStr = new Boolean(cell.getBooleanCellValue()).toString();
			break;
		case FORMULA: {
			rtnStr = formatter.format(cell.getNumericCellValue());
			break;
		}
		case NUMERIC: {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期、时间格式
				SimpleDateFormat sdf = null;
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				rtnStr = sdf.format(cell.getDateCellValue());
			} else {
				Double d = cell.getNumericCellValue();
				cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);
				rtnStr = cell.getRichStringCellValue().getString();
				cell.setCellValue(d);
				cell.setCellType(org.apache.poi.ss.usermodel.CellType.NUMERIC);
			}
			break;
		}
		case BLANK:
		case ERROR:
			rtnStr = "";
			break;
		default:
			rtnStr = cell.getRichStringCellValue().getString();
		}
		return formatString(rtnStr.trim());
	}

	/**
	 * 去除字符串中的无法辨认的字符
	 * 
	 * @param s
	 * @return
	 */
	private String formatString(String s) {
		StringBuffer rtnStr = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 128 && c <= 255 && EXCEPT_STRING.indexOf(c) == -1) {
                continue;
            }
			rtnStr.append(c);
		}
		return rtnStr.toString();
	}

}

