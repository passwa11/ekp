package com.landray.kmss.sys.attend.service.spring;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.framework.spring.event.IEventMulticaster;
import com.landray.kmss.framework.spring.event.IEventMulticasterAware;
import com.landray.kmss.framework.spring.event.interfaces.IEventCallBack;
import com.landray.kmss.framework.spring.event.transaction.EventOfTransactionCommit;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.model.SysAttendCategoryTimesheet;
import com.landray.kmss.sys.attend.model.SysAttendCategoryWorktime;
import com.landray.kmss.sys.attend.model.SysAttendHisCategory;
import com.landray.kmss.sys.attend.model.SysAttendMain;
import com.landray.kmss.sys.attend.model.SysAttendMainExc;
import com.landray.kmss.sys.attend.model.SysAttendStat;
import com.landray.kmss.sys.attend.model.SysAttendStatDetail;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.service.ISysAttendMainExcService;
import com.landray.kmss.sys.attend.service.ISysAttendMainService;
import com.landray.kmss.sys.attend.service.ISysAttendStatDetailService;
import com.landray.kmss.sys.attend.service.ISysAttendStatJobService;
import com.landray.kmss.sys.attend.service.ISysAttendStatService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.attend.util.CategoryUtil;
import com.landray.kmss.sys.attend.util.DateTimeFormatUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.NumberUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员统计详情业务接口实现
 * 
 * @author
 * @version 1.0 2017-07-27
 */
public class SysAttendStatDetailServiceImp extends BaseServiceImp
		implements ISysAttendStatDetailService, IEventMulticasterAware {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttendStatDetailServiceImp.class);
	private ISysAttendCategoryService sysAttendCategoryService;

	private ISysAttendMainService sysAttendMainService;

	private ISysAttendMainExcService sysAttendMainExcService;

	private ISysAttendStatJobService sysAttendStatJobService;
	private ISysAttendStatService sysAttendStatService;

	private IEventMulticaster multicaster;
	private ISysNotifyMainCoreService sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil
			.getBean("sysNotifyMainCoreService");
	@Override
	public void setEventMulticaster(IEventMulticaster multicaster) {
		this.multicaster = multicaster;

	}

	public void setSysAttendCategoryService(
			ISysAttendCategoryService sysAttendCategoryService) {
		this.sysAttendCategoryService = sysAttendCategoryService;
	}

	public void
			setSysAttendMainService(
					ISysAttendMainService sysAttendMainService) {
		this.sysAttendMainService = sysAttendMainService;
	}

	public void setSysAttendMainExcService(
			ISysAttendMainExcService sysAttendMainExcService) {
		this.sysAttendMainExcService = sysAttendMainExcService;
	}

	public void setSysAttendStatJobService(
			ISysAttendStatJobService sysAttendStatJobService) {
		this.sysAttendStatJobService = sysAttendStatJobService;
	}

	/**
	 * 班次信息表头
	 * 
	 * @param sheet
	 * @param titleRow
	 * @param infos
	 */
	public void buildMergeHeader(HSSFSheet sheet, HSSFRow titleRow,
			List<Map<String, Object>> infos) {
		if (sheet == null || titleRow == null || infos == null
				|| infos.isEmpty()) {
			return;
		}
		int headRowIdx = titleRow.getRowNum();
		int headColStart = titleRow.getFirstCellNum();
		int headColEnd = titleRow.getLastCellNum();
		HSSFRow tmpRow = sheet.createRow(headRowIdx + 1);
		tmpRow.setHeight((short) 400);
		List<Integer> notMergeColIdx = new ArrayList<Integer>();
		for (Map<String, Object> map : infos) {
			if (map != null) {
				Integer colStart = (Integer) map.get("colStart");
				Integer colEnd = (Integer) map.get("colEnd");
				if (colStart != null && colEnd != null) {
					for (int i = colStart; i <= colEnd; i++) {
						HSSFCell cell1 = titleRow.getCell(i);
						HSSFCell cell2 = tmpRow.createCell(i);
						if (cell1 != null && cell2 != null) {
							cell2.setCellStyle(cell1.getCellStyle());
							cell2.setCellValue(cell1.getStringCellValue());
						}
						notMergeColIdx.add(i);
					}
					String title = (String) map.get("title");
					if (StringUtil.isNotNull(title)) {
						HSSFCell wtCell = titleRow.getCell(colStart);
						wtCell.setCellValue(title);
					}
					com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, 
							new CellRangeAddress(0, 0, colStart, colEnd));
				}
			}
		}
		for (int k = headColStart; k <= headColEnd; k++) {
			if (!notMergeColIdx.isEmpty() && notMergeColIdx.contains(k)) {
				continue;
			}
			com.landray.kmss.util.poi.SheetAddMergedRegionUtils.addMergedRegion(sheet, new CellRangeAddress(0, 1, k, k));
		}
	}


	private void statRender(HSSFCell[] contentcells, int cellBenginIdx,
			SysAttendStat stat) {
		String hourTxt = ResourceUtil.getString(
				"sys-attend:sysAttendStatDetail.totalTime.hour");
		String minTxt = ResourceUtil.getString(
				"sys-attend:sysAttendStatDetail.totalTime.minute");
		// 工时
		String timeStr = "";
		if (stat.getFdTotalTime() == null
				|| stat.getFdTotalTime().longValue() == 0) {
			timeStr = "0" + hourTxt;
		} else {
			long totalTime = stat.getFdTotalTime().longValue();
			long hour = totalTime / 60;
			long minute = totalTime % 60;
			if (minute == 0) {
				timeStr = hour + hourTxt;
			} else if (minute > 0 && hour == 0) {
				timeStr = minute + minTxt;
			} else if (minute > 0 && hour > 0) {
				timeStr = hour + hourTxt
						+ minute + minTxt;
			}
		}
		contentcells[cellBenginIdx].setCellValue(String.format("%.2f", stat.getFdTotalTime().longValue()/60.0));

		// 加班工时
		String overTimeStr = "";
		if (stat.getFdOverTime() == null || stat.getFdOverTime().longValue() == 0) {
			overTimeStr = "0" + hourTxt;
		} else {
			long overTime = stat.getFdOverTime().longValue();
			long hour = overTime / 60;
			long minute = overTime % 60;
			if (minute == 0) {
				overTimeStr = hour + hourTxt;
			} else if (minute > 0 && hour == 0) {
				overTimeStr = minute + minTxt;
			} else if (minute > 0 && hour > 0) {
				overTimeStr = hour + hourTxt + minute + minTxt;
			}
		}
		contentcells[cellBenginIdx + 1].setCellValue(String.format("%.2f", stat.getFdOverTime().longValue()/60.0));
		// 外出工时
		contentcells[cellBenginIdx + 2].setCellValue(String.format("%.2f", stat.getFdOutgoingTime()));
//		contentcells[cellBenginIdx + 2].setCellValue(SysTimeUtil.formatHourTimeStr(stat.getFdOutgoingTime()));
		// 迟到小时
		Integer lateTime = stat.getFdLateTime();
		int hour = 0, minute = 0;
		if (lateTime != null && lateTime != 0) {
			hour = lateTime / 60;
			minute = lateTime % 60;
		}
		String txt = "";
		if (hour == 0 && minute == 0) {
			txt = hour + "";
		}
		if (hour > 0 && minute == 0) {
			txt = hour + hourTxt;
		}
		if (hour == 0 && minute > 0) {
			txt = minute + minTxt;
		}
		if (hour > 0 && minute > 0) {
			txt = hour + hourTxt + minute + minTxt;
		}
		contentcells[cellBenginIdx + 3].setCellValue(stat.getFdLateTime());
		// 早退小时
		Integer leftTime = stat.getFdLeftTime();
		hour = minute = 0;
		if (leftTime != null && leftTime != 0) {
			hour = leftTime / 60;
			minute = leftTime % 60;
		}
		if (hour == 0 && minute == 0) {
			txt = hour + "";
		}
		if (hour > 0 && minute == 0) {
			txt = hour + hourTxt;
		}
		if (hour == 0 && minute > 0) {
			txt = minute + minTxt;
		}
		if (hour > 0 && minute > 0) {
			txt = hour + hourTxt + minute + minTxt;
		}
		contentcells[cellBenginIdx + 4].setCellValue(stat.getFdLeftTime());
		// 旷工天数
		Float fdAbsentDays = stat.getFdAbsentDays() == null ? 0
				: stat.getFdAbsentDays();
		contentcells[cellBenginIdx + 5]
				.setCellValue(String.format("%.2f",fdAbsentDays));
		// 请假天数
		Float fdOffDays = stat.getFdOffDays() == null ? 0 : stat.getFdOffDays();
		Float fdOffTime = stat.getFdOffTimeHour() == null ? 0f : stat.getFdOffTimeHour();
//		if(stat.getFdWorkTime() !=null && stat.getFdWorkTime() > 0){
//			//如果非新的数据，则使用原来的逻辑处理。否则只按小时
//			fdOffDays =0F;
//		}
		String offText = SysTimeUtil.formatLeaveTimeStr(stat.getFdWorkTime(),fdOffDays, fdOffDays);
		contentcells[cellBenginIdx + 6].setCellValue(stat.getFdPersonalLeaveDays());
//				contentcells[cellBenginIdx + 7].setCellValue(String.format("%.2f",fdOffDays+fdOffDays/stat.getFdWorkTime()));
		contentcells[cellBenginIdx + 7].setCellValue(String.format("%.2f",stat.getFdOffTimeHour()==null ? 0F : stat.getFdOffTimeHour()));
		// 出差天数
		Float tripDays = stat.getFdTripDays() == null ? 0
				: stat.getFdTripDays();
		contentcells[cellBenginIdx + 8]
				.setCellValue(String.format("%.2f",tripDays));
	}


	@Override
	public HSSFWorkbook buildWorkBook(List list, int maxWorkTimeCount,Map<String, List<List<JSONObject>>>  worksMap ) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(0,
				ResourceUtil.getString("table.sysAttendStatDetail",
						"sys-attend"));
		sheet.createFreezePane(0, 2);

		// 最多显示5个班次
		maxWorkTimeCount = maxWorkTimeCount > 5 ? 5 : maxWorkTimeCount;

		int colIndex = 0;
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 5000);
		sheet.setColumnWidth(colIndex++, 5000);
		sheet.setColumnWidth(colIndex++, 5000);
		sheet.setColumnWidth(colIndex++, 5000);
		sheet.setColumnWidth(colIndex++, 5000);
		//新增字段
		sheet.setColumnWidth(colIndex++, 5000);
		sheet.setColumnWidth(colIndex++, 5000);
		sheet.setColumnWidth(colIndex++, 5000);
		sheet.setColumnWidth(colIndex++, 5000);
		sheet.setColumnWidth(colIndex++, 3000);
		sheet.setColumnWidth(colIndex++, 3000);
		sheet.setColumnWidth(colIndex++, 3000);
		sheet.setColumnWidth(colIndex++, 3000);
		sheet.setColumnWidth(colIndex++, 3000);
		sheet.setColumnWidth(colIndex++, 3000);
		// 一个班次占4列
		for (int j = 0; j < maxWorkTimeCount * 6; j++) {
			sheet.setColumnWidth(colIndex++, 3000);
		}
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		sheet.setColumnWidth(colIndex++, 4000);
		
		int colNum = colIndex;

		int rowIndex = 0;
		/* 标题行 */
		HSSFRow titlerow = sheet.createRow(rowIndex++);
		titlerow.setHeight((short) 400);
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		titleCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		titleCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		font.setBold(true);
		titleCellStyle.setFont(font);
		HSSFCell[] titleCells = new HSSFCell[colNum];
		for (int i = 0; i < titleCells.length; i++) {
			titleCells[i] = titlerow.createCell(i);
			titleCells[i].setCellStyle(titleCellStyle);
		}

		int titleIndex = 0;
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.docCreator"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString(
						"sys-attend:sysAttendStatDetail.docCreator.loginName"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.docCreator.fdNo"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.docCreator.fdDept"));
		
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdCategoryName"));
		
		//报表新增字段
				titleCells[titleIndex++].setCellValue(ResourceUtil
						.getString("sys-attend:sysAttendStatDetail.fdFirstLevelDepartmentName"));
				titleCells[titleIndex++].setCellValue(ResourceUtil
						.getString("sys-attend:sysAttendStatDetail.fdSecondLevelDepartmentName"));
				titleCells[titleIndex++].setCellValue(ResourceUtil
						.getString("sys-attend:sysAttendStatDetail.fdThirdLevelDepartmentName"));
		
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdDate"));
		
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdDateType"));
		
		//报表新增字段
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdRestTime"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdStandWorkTime"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdMonthLateNum"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdMonthForgerNum"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdMonthLateMinNum"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
						.getString("sys-attend:sysAttendStatDetail.fdDelayedTime"));
		titleCells[titleIndex++].setCellValue(ResourceUtil
						.getString("sys-attend:sysAttendStatDetail.fdAttendResult"));

		for (int k = 0; k < maxWorkTimeCount; k++) {
			// 上班
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatDetail.startTime"));
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.fdWorkType.onwork"));
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatDetail.docStatus"));
//			titleCells[titleIndex++].setCellValue(ResourceUtil
//					.getString("sys-attend:sysAttendStatDetail.fdState"));
			// 下班
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatDetail.endTime"));
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendMain.fdWorkType.offwork"));
			titleCells[titleIndex++].setCellValue(ResourceUtil
					.getString("sys-attend:sysAttendStatDetail.docStatus2"));
//			titleCells[titleIndex++].setCellValue(ResourceUtil
//					.getString("sys-attend:sysAttendStatDetail.fdState2"));
		}

		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdTotalTime")
				.replace("<br/>", ""));

		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdOverTime")
				.replace("<br/>", ""));

		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdOutgoingTime")
				.replace("<br/>", ""));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdLateTime")
				.replace("<br/>", ""));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdLeftTime")
				.replace("<br/>", ""));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdAbsentDays")
				.replace("<br/>", ""));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdPersonalLeaveDays")
				.replace("<br/>", ""));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdOffDays")
				.replace("<br/>", ""));
		titleCells[titleIndex++].setCellValue(ResourceUtil
				.getString("sys-attend:sysAttendStatDetail.fdTripDays")
				.replace("<br/>", ""));

		// 班次表头
		List<Map<String, Object>> workTimeInfos = new ArrayList<Map<String, Object>>();
		for (int y = 0; y < maxWorkTimeCount; y++) {
			Map<String, Object> info = new HashMap<String, Object>();
			info.put("colStart", 7+10 + 6 * y);
			info.put("colEnd", 12+10 + 6 * y);
			String title = "";
			switch (y) {
			case 0:
				title = "sys-attend:sysAttendStatDetail.workTime.first";
				break;
			case 1:
				title = "sys-attend:sysAttendStatDetail.workTime.second";
				break;
			case 2:
				title = "sys-attend:sysAttendStatDetail.workTime.third";
				break;
			case 3:
				title = "sys-attend:sysAttendStatDetail.workTime.forth";
				break;
			case 4:
				title = "sys-attend:sysAttendStatDetail.workTime.fifth";
				break;
			default:
				break;
			}
			info.put("title", ResourceUtil.getString(title));
			workTimeInfos.add(info);
		}
		buildMergeHeader(sheet, titlerow, workTimeInfos);
		rowIndex++;

		/* 内容行 */
		HSSFCellStyle contentCellStyle = workbook.createCellStyle();
		contentCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		contentCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		contentCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));
		//红色
		HSSFCellStyle excCellStyle = workbook.createCellStyle();
		excCellStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		excCellStyle.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		excCellStyle.setDataFormat(workbook.createDataFormat().getFormat("@"));
		HSSFFont excFont = workbook.createFont();
		excFont.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index);
		excFont.setBold(true);
		excCellStyle.setFont(excFont);
		
		//蓝色
		HSSFCellStyle excStyleBule = workbook.createCellStyle();
		excStyleBule.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		excStyleBule.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		excStyleBule.setDataFormat(workbook.createDataFormat().getFormat("@"));
		HSSFFont excFontBule = workbook.createFont();
		excFontBule.setColor(org.apache.poi.ss.usermodel.IndexedColors.BLUE.index);
		excFontBule.setBold(true);
		excStyleBule.setFont(excFontBule);
		
		//黑色
		HSSFCellStyle excStyleBlack = workbook.createCellStyle();
		excStyleBlack.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
		excStyleBlack.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);
		excStyleBlack.setDataFormat(workbook.createDataFormat().getFormat("@"));
		HSSFFont excFontBlack = workbook.createFont();
		excFontBlack.setColor(org.apache.poi.ss.usermodel.IndexedColors.BLACK.index);
		excStyleBlack.setFont(excFontBlack);
		DateTimeFormatUtil datef = new DateTimeFormatUtil();
		// 考勤组信息
		//Map<String, Object> cateMap = sysAttendCategoryService.getCategoryMap();

		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				HSSFRow contentrow = sheet.createRow(rowIndex++);
				contentrow.setHeight((short) 400);
				HSSFCell[] contentcells = new HSSFCell[colNum];
				for (int j = 0; j < contentcells.length; j++) {
					contentcells[j] = contentrow.createCell(j);
					contentcells[j].setCellStyle(contentCellStyle);
					contentcells[j].setCellType(CellType.STRING);
				}
				SysAttendStat stat = (SysAttendStat) list.get(i);

				int contentIndex = 0;
				//每次都获取考勤组、考勤组获取是内存中获取
				SysAttendCategory category =CategoryUtil.getCategoryById(stat.getFdCategoryId());
				boolean isOutsdReview = category != null
						&& Integer.valueOf(1)
								.equals(category.getFdOsdReviewType());

				contentcells[contentIndex++]
						.setCellValue(stat.getDocCreator().getFdIsAvailable()
								? stat.getDocCreator().getFdName()
								: (stat.getDocCreator().getFdName()
										+ ResourceUtil
												.getString(
														"sys-attend:sysAttendStatDetail.alreadyQuit")));
				contentcells[contentIndex++]
						.setCellValue(
								stat.getDocCreator() == null
										? ""
										: stat.getDocCreator()
												.getFdLoginName());
				contentcells[contentIndex++]
						.setCellValue(
								stat.getDocCreator().getFdNo() == null
										? ""
										: stat.getDocCreator().getFdNo()
												);
				contentcells[contentIndex++]
						.setCellValue(
								stat.getDocCreator().getFdParent() == null
										? ""
										: stat.getDocCreator().getFdParent()
												.getFdName());
				contentcells[contentIndex++].setCellValue(
						category != null ? category.getFdName() : "");
				
				//新增报表字段
				contentcells[contentIndex++].setCellValue(stat.getFdFirstLevelDepartmentName());
				contentcells[contentIndex++].setCellValue(stat.getFdSecondLevelDepartmentName());
				contentcells[contentIndex++].setCellValue(stat.getFdThirdLevelDepartmentName());
				
				contentcells[contentIndex++].setCellValue(DateUtil
						.convertDateToString(stat.getFdDate(), "yyyy-MM-dd")
						+ " " + datef.getDateTime(stat.getFdDate(), "E"));
				
				contentcells[contentIndex++].setCellValue(EnumerationTypeUtil
						.getColumnEnumsLabel("sysAttendMain_fdDateType",
								String.valueOf(stat.getFdDateType())));
				//新增报表字段
				contentcells[contentIndex++].setCellValue(stat.getFdRestTime()!=null?stat.getFdRestTime():0);
				BigDecimal b = new BigDecimal(stat.getFdStandWorkTime());;
				float standWorkTime = stat.getFdStandWorkTime()!=null?b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue():0.0F;
				contentcells[contentIndex++].setCellValue(""+standWorkTime);
				contentcells[contentIndex++].setCellValue(stat.getFdMonthLateNum()!=null?stat.getFdMonthLateNum():0);
				contentcells[contentIndex++].setCellValue(stat.getFdMonthForgerNum()!=null?stat.getFdMonthForgerNum():0);
				contentcells[contentIndex++].setCellValue(stat.getFdMonthLateMinNum()!=null?stat.getFdMonthLateMinNum():0);
				contentcells[contentIndex++].setCellValue(stat.getFdDelayedTime()!=null?stat.getFdDelayedTime():0);
				contentcells[contentIndex++].setCellValue(stat.getFdAttendResult() != null && stat.getFdAttendResult() != 1 ? "正常" : "异常");
				

				for (int x = 0; x < maxWorkTimeCount; x++) {
					if(worksMap==null){
						continue;
					}
					List workTimeList = (List) worksMap.get(stat.getFdId());
					JSONObject work1 = new JSONObject();// 上班
					JSONObject work2 = new JSONObject();// 下班
					if (workTimeList != null && !workTimeList.isEmpty()) {
						if (x < workTimeList.size()) {
							List workTime = (List) workTimeList.get(x);
							work1 = (JSONObject) workTime.get(0);
							if (workTime.size() > 1) {
								work2 = (JSONObject) workTime.get(1);
							}
						}
					}
					
					int workIndex = contentIndex + x * 6;
					int first=0,second=0,first1=0,second1=0;

					SysAttendCategory sysAttendCategory = CategoryUtil.getCategoryById(stat.getFdCategoryId());
					// 上班
					if (work1 != null && !work1.isEmpty()) {
						Date signTime = new Date(work1.getLong("signTime"));
						Integer fdStatus = work1.getInt("fdStatus");
						Integer fdState = work1.getInt("fdState");
						Boolean fdOutside = work1.getBoolean("fdOutside");
						String fdOffTypeText = work1.getString("fdOffTypeText");

						boolean isAcrossSign = false;
						if (work1.get("fdIsAcross") != null) {
							isAcrossSign = Boolean.valueOf(
									work1.get("fdIsAcross").toString());
						}
						String signStr = DateUtil.convertDateToString(signTime,
								"HH:mm");
						// 跨天打卡加上 次日标识
						if (isAcrossSign) {
							signStr = signStr + "("
									+ ResourceUtil.getString(
											"sys-attend:sysAttendMain.fdIsAcross.nextday")
									+ ")";
						}
						// 打卡时间
						String fdSignTime1 = fdStatus != null && (fdStatus != 0
								|| Integer.valueOf(2).equals(fdState))
										? signStr : "";
						contentcells[workIndex++].setCellValue(stat.getStartTime());
						contentcells[workIndex++].setCellValue(fdSignTime1);
						// 打卡状态
						String fdStatusStr = "";
						HSSFCell statusCell = contentcells[workIndex++];
						if (fdStatus != null) {
							statusCell.setCellStyle(excStyleBlack);
							if (fdStatus == 1 && Boolean.TRUE.equals(fdOutside)) {
								fdStatusStr = ResourceUtil.getString(
										"sys-attend:sysAttendMain.outside");
								statusCell.setCellStyle(excStyleBule);
							} else if (fdStatus.intValue() == 0
									|| fdStatus.intValue() == 2
									|| fdStatus.intValue() == 3) {
								if (Integer.valueOf(2).equals(fdState)) {
									fdStatusStr = ResourceUtil
											.getString(
													"sys-attend:sysAttendMain.fdStatus.ok");
								} else {
									statusCell.setCellStyle(excCellStyle);
									fdStatusStr = EnumerationTypeUtil
											.getColumnEnumsLabel(
													"sysAttendMain_fdStatus",
													String.valueOf(fdStatus));
									if (fdOutside) {
										String fdOutsideKey = ResourceUtil.getString("sys-attend:sysAttendMain.outside");
										fdStatusStr=fdStatusStr+"("+fdOutsideKey+")";
									}
								}
							} else {
								fdStatusStr = EnumerationTypeUtil
										.getColumnEnumsLabel(
												"sysAttendMain_fdStatus",
												String.valueOf(fdStatus));
							}
							if (fdStatus == 5
									&& StringUtil.isNotNull(fdOffTypeText)) {
								fdStatusStr = fdStatusStr + "(" + fdOffTypeText
										+ ")";
								statusCell.setCellStyle(excStyleBule);
							}
							if(Integer.valueOf(4).equals(sysAttendCategory.getFdShiftType())){
								if(fdStatusStr.equals("正常"))
									first=1;
							if(fdStatusStr.equals("缺卡"))
								first=2;
							}
							statusCell.setCellValue(fdStatusStr);
							// 处理情况
//							String fdStateStr = "";
//							if (fdState == null && fdStatus != null
//									&& (fdStatus == 0 || fdStatus == 2
//											|| fdStatus == 3)) {
//								fdStateStr = ResourceUtil
//										.getString(
//												"sys-attend:sysAttendMain.fdState.undo");
//							} else if (fdStatus == 4 || fdStatus == 5 || fdStatus == 6 ) {
//								statusCell.setCellStyle(excStyleBule);
//								// do nothing
//							} else {
//								if(!(fdStatus ==1 && (fdState ==null || fdState==0))) {
//									fdStateStr = EnumerationTypeUtil
//											.getColumnEnumsLabel(
//													"sysAttendMain_fdState",
//													String.valueOf(fdState));
//								}
//							}
//							contentcells[workIndex++].setCellValue(fdStateStr);
						}
					}
					// 下班
					if (work2 != null && !work2.isEmpty()) {
						Date signTime = new Date(work2.getLong("signTime"));
						Integer fdStatus = work2.getInt("fdStatus");
						Integer fdState = work2.getInt("fdState");
						Boolean fdOutside = work2.getBoolean("fdOutside");
						String fdOffTypeText = work2.getString("fdOffTypeText");
						boolean isAcrossSign = false;
						if (work2.get("fdIsAcross") != null) {
							isAcrossSign = Boolean.valueOf(
									work2.get("fdIsAcross").toString());
						}
						String signStr = DateUtil.convertDateToString(signTime,
								"HH:mm");
						// 跨天打卡加上 次日标识
						if (isAcrossSign) {
							signStr = signStr + "("
									+ ResourceUtil.getString(
											"sys-attend:sysAttendMain.fdIsAcross.nextday")
									+ ")";
						}
						// 打卡时间
						String fdSignTime1 = fdStatus != null && (fdStatus != 0
								|| Integer.valueOf(2).equals(fdState))
										? signStr : "";
						contentcells[workIndex++].setCellValue(stat.getEndTime());
						contentcells[workIndex++].setCellValue(fdSignTime1);
						// 打卡状态
						String fdStatusStr = "";
						HSSFCell statusCell = contentcells[workIndex++];
						if (fdStatus != null) {
							statusCell.setCellStyle(excStyleBlack);
							if (fdStatus == 1 && Boolean.TRUE.equals(fdOutside) ) {
								fdStatusStr = ResourceUtil.getString(
										"sys-attend:sysAttendMain.outside");
								statusCell.setCellStyle(excStyleBule);
							} else if (fdStatus.intValue() == 0
									|| fdStatus.intValue() == 2
									|| fdStatus.intValue() == 3) {
								if (Integer.valueOf(2).equals(fdState)) {
									fdStatusStr = ResourceUtil
											.getString(
													"sys-attend:sysAttendMain.fdStatus.ok");
								} else {
									statusCell.setCellStyle(excCellStyle);
									fdStatusStr = EnumerationTypeUtil
											.getColumnEnumsLabel(
													"sysAttendMain_fdStatus",
													String.valueOf(fdStatus));
									if (fdOutside) {
										String fdOutsideKey = ResourceUtil.getString("sys-attend:sysAttendMain.outside");
										fdStatusStr=fdStatusStr+"("+fdOutsideKey+")";
									}
								}
							} else {
								fdStatusStr = EnumerationTypeUtil
										.getColumnEnumsLabel(
												"sysAttendMain_fdStatus",
												String.valueOf(fdStatus));
							}
							if (fdStatus == 5
									&& StringUtil.isNotNull(fdOffTypeText)) {
								fdStatusStr = fdStatusStr + "(" + fdOffTypeText
										+ ")";
								statusCell.setCellStyle(excStyleBule);
							}
							statusCell.setCellValue(fdStatusStr);
							if(Integer.valueOf(4).equals(sysAttendCategory.getFdShiftType())){
								if(fdStatusStr.equals("正常")&&first==2){
									int temp = workIndex-4;
									contentcells[temp].setCellStyle(excStyleBlack);
									contentcells[temp].setCellValue("正常");
								}
							if(fdStatusStr.equals("缺卡")&&first==1){
								statusCell.setCellStyle(excStyleBlack);
								statusCell.setCellValue("正常");
							}
							}
							// 处理情况
//							String fdStateStr = "";
//							if (fdState == null && fdStatus != null
//									&& (fdStatus == 0 || fdStatus == 2
//											|| fdStatus == 3)) {
//								fdStateStr = ResourceUtil
//										.getString(
//												"sys-attend:sysAttendMain.fdState.undo");
//							} else if (fdStatus == 4 || fdStatus == 5 || fdStatus == 6) {
//								statusCell.setCellStyle(excStyleBule);
//							} else {
//								if(!(fdStatus ==1 && (fdState ==null || fdState==0))) {
//									fdStateStr = EnumerationTypeUtil
//											.getColumnEnumsLabel(
//													"sysAttendMain_fdState",
//													String.valueOf(fdState));
//								}
//							}
//							contentcells[workIndex++].setCellValue(fdStateStr);
						}
					}
				}
				contentIndex = contentIndex + maxWorkTimeCount * 6;

				// 统计
				statRender(contentcells, contentIndex, stat);
			}
		}
		return workbook;
	}

	@Override
	public JSONArray renderAttendRecord(List list, int fdType) {
		JSONArray result = new JSONArray();
		if (list.isEmpty()) {
			return result;
		}
		for (int i = 0; i < list.size(); i++) {
			SysAttendStatDetail record = (SysAttendStatDetail) list.get(i);
			setAttendData(record.getFdSignTime(), record.getDocStatus(), fdType,
					record.getFdOutside(),
					result);
			setAttendData(record.getFdSignTime2(), record.getDocStatus2(),
					fdType, record.getFdOutside2(), result);
			setAttendData(record.getFdSignTime3(), record.getDocStatus3(),
					fdType, record.getFdOutside3(), result);
			setAttendData(record.getFdSignTime4(), record.getDocStatus4(),
					fdType, record.getFdOutside4(), result);
		}
		Collections.sort(result, new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				Long arg1 = o1.getLong("signTime");
				Long arg2 = o2.getLong("signTime");
				return arg1.compareTo(arg2);
			}

		});
		return result;
	}

	private void setAttendData(Date fdSignTime, Integer docStatus, int fdType,
			Boolean fdOutside, JSONArray result) {

		if (docStatus != null && docStatus.intValue() == fdType
				&& fdType != 4) {
			JSONObject json = new JSONObject();
			json.put("signTime", fdSignTime.getTime());
			json.put("docStatus", docStatus.intValue());
			result.add(json);
		} else if (fdOutside != null && fdOutside.booleanValue()
				&& fdType == 4) {
			JSONObject json = new JSONObject();
			json.put("signTime", fdSignTime.getTime());
			json.put("fdOutside", fdOutside.booleanValue());
			result.add(json);
		}
	}

	/*
	 * @param fdStatus 废弃
	 */
	@Override
	public void updateStatus(String fdId, String fdStatus) throws Exception {
		final SysAttendStat statDetail = (SysAttendStat) sysAttendStatService
				.findByPrimaryKey(fdId);
		if (statDetail == null) {
			throw new NoRecordException();
		}
		HQLInfo hqlInfo = new HQLInfo();
		HQLInfo hqlInfoTwo = new HQLInfo();
		StringBuffer whereBlock = new StringBuffer("1=1");
		if (statDetail.getDocCreator() != null) {
			whereBlock.append(" and sysAttendMain.docCreator.fdId=:docCretorId");
			hqlInfo.setParameter("docCretorId",statDetail.getDocCreator().getFdId());
			hqlInfoTwo.setParameter("docCretorId",statDetail.getDocCreator().getFdId());
		} else {
			throw new NoRecordException();
		}
		if (StringUtil.isNotNull(statDetail.getFdCategoryId())) {
			whereBlock.append(" and sysAttendMain.fdHisCategory.fdId=:fdCategoryId");
			hqlInfo.setParameter("fdCategoryId",statDetail.getFdCategoryId());
			hqlInfoTwo.setParameter("fdCategoryId",statDetail.getFdCategoryId());
		} else {
			throw new NoRecordException();
		}
		whereBlock.append(" and (sysAttendMain.docStatus=0 or sysAttendMain.docStatus is null)");
		StringBuffer selectOne=new StringBuffer();
		StringBuffer selectTwo=new StringBuffer();
		if (statDetail.getFdDate() != null) {
			selectOne.append(whereBlock);
			selectOne.append(" and sysAttendMain.docCreateTime>=:startTime and sysAttendMain.docCreateTime<:endTime and (sysAttendMain.fdIsAcross is null or sysAttendMain.fdIsAcross=:fdIsAcross0) ");
			selectTwo.append(whereBlock);
			selectTwo.append(" and sysAttendMain.docCreateTime>=:nextStart and sysAttendMain.docCreateTime<:nextEnd and sysAttendMain.fdIsAcross=:fdIsAcross1 ");
			Date startTime = AttendUtil.getDate(statDetail.getFdDate(), 0);
			Date endTime = AttendUtil.getDate(statDetail.getFdDate(), 1);
			Date nextEnd = AttendUtil.getDate(statDetail.getFdDate(), 2);
			hqlInfo.setParameter("fdIsAcross0", false);
			hqlInfo.setParameter("startTime", startTime);
			hqlInfo.setParameter("endTime", endTime);
			hqlInfo.setWhereBlock(selectOne.toString());

			hqlInfoTwo.setWhereBlock(selectTwo.toString());
			hqlInfoTwo.setParameter("fdIsAcross1", true);
			hqlInfoTwo.setParameter("nextStart", endTime);
			hqlInfoTwo.setParameter("nextEnd", nextEnd);
		} else {
			throw new NoRecordException();
		}
		List mainListOne = sysAttendMainService.findList(hqlInfo);
		List mainListTwo = sysAttendMainService.findList(hqlInfoTwo);
		List mainList =new ArrayList();
		if(CollectionUtils.isNotEmpty(mainListOne)){
			mainList.addAll(mainListOne);
		}
		if(CollectionUtils.isNotEmpty(mainListTwo)){
			mainList.addAll(mainListTwo);
		}
		if (mainList != null && !mainList.isEmpty()) {
			for (Object obj : mainList) {
				SysAttendMain main = (SysAttendMain) obj;
				SysAttendCategory category = CategoryUtil.getFdCategoryInfo(main);
				if ((main.getFdStatus() == 0 || main.getFdStatus() == 2
						|| main.getFdStatus() == 3
						|| Integer.valueOf(1)
								.equals(category.getFdOsdReviewType())
								&& Boolean.TRUE.equals(main.getFdOutside())
								&& main.getFdStatus() == 1)
						&& (main.getFdState() == null
								|| main.getFdState() != 2)) {
					Integer oldStatus = main.getFdStatus();
					Date oldCreateTime = main.getDocCreateTime();
					Boolean oldOutside = main.getFdOutside();
					HQLInfo excHqlInfo = new HQLInfo();
					excHqlInfo.setWhereBlock(
							"sysAttendMainExc.fdAttendMain.fdId=:fdAttendMainId");
					excHqlInfo.setParameter("fdAttendMainId", main.getFdId());
					List excList = sysAttendMainExcService
							.findList(excHqlInfo);
					boolean isToOk = false;
					if (excList != null && !excList.isEmpty()) {
						// 已提异常单
						for (int k = 0; k < excList.size(); k++) {
							SysAttendMainExc exc = (SysAttendMainExc) excList
									.get(k);
							try {
								// 特权通过
								sysAttendMainExcService
										.passByAdmin(exc.getFdId());
								main.setFdState(2);
							} catch (Exception e) {
								// 特权通过失败
								main.setFdState(3);//标记处理未通过
								isToOk = true;
							}
						}
					} else {
						// 未提异常单
						main.setFdStatus(main.getFdStatus());
						main.setFdState(2);//标记已处理
						isToOk = true;
					}
					if (isToOk) {
						//用户打卡时间恢复为上下班时间
						if (Integer.valueOf(0).equals(oldStatus)
								|| Integer.valueOf(2).equals(oldStatus)
								|| Integer.valueOf(3).equals(oldStatus)) {
							List signList = sysAttendCategoryService
									.getAttendSignTimes(category,
											statDetail.getFdDate(),
											statDetail.getDocCreator());
							SysAttendCategoryWorktime workTime = this.sysAttendCategoryService
									.getWorkTimeByRecord(signList,
											main.getDocCreateTime(),
											main.getFdWorkType());
							if (workTime != null) {
								Date _signTime = workTime.getFdStartTime();
								Date fdDate = statDetail.getFdDate();
								if (Integer.valueOf(1)
										.equals(main.getFdWorkType())) {
									_signTime = workTime.getFdEndTime();
									Integer overTimeType = workTime
											.getFdOverTimeType();
									// 跨天排班打卡时间要加一天
									if (Integer.valueOf(2)
											.equals(overTimeType)) {
										fdDate = AttendUtil.addDate(fdDate, 1);
									}
								}
								main.setDocCreateTime(AttendUtil.joinYMDandHMS(
										fdDate, _signTime));
								logger.warn(
										"用户考勤异常状态置为正常操作通过!打卡时间调整为:"
												+ oldCreateTime
												+ "--->"
												+ main.getDocCreateTime()
												+ ";userName:"
												+ main.getDocCreator());
							}
						}
					}
					//保存打卡记录信息
					main.setFdAppName(ResourceUtil.getString("sysAttendMain.fdAppName.ekp","sys-attend"));

					main.setFdOutside(false);
					main.setDocAlteror(UserUtil.getUser());
					main.setDocAlterTime(new Date());
					main.setFdAlterRecord(ResourceUtil.getString(
							"sysAttendMain.fdAlterRecord.content", "sys-attend")
							.replace("%status1%",
									oldStatus == 1
											&& Boolean.TRUE.equals(oldOutside)
													? ResourceUtil.getString(
															"sysAttendMain.outside",
															"sys-attend")
													: EnumerationTypeUtil
															.getColumnEnumsLabel(
																	"sysAttendMain_fdStatus",
																	oldStatus
																			+ ""))
							.replace("%status2%",
									ResourceUtil.getString(
											"sysAttendMain.fdStatus.ok",
											"sys-attend")));
					sysAttendMainService.getBaseDao().update(main);
					// 删除待办
					sysNotifyMainCoreService.getTodoProvider().clearTodoPersons(
							main, "sendUnSignNotify", null, null);
					// 添加日志信息
					if (UserOperHelper.allowLogOper("updateStatus",
							getModelName())) {

						UserOperContentHelper.putUpdate(main)
								.putSimple("fdStatus", oldStatus,
										main.getFdStatus())
								.putSimple("fdOutside", oldOutside,
										main.getFdOutside())
								.putSimple("docAlteror", null,
										main.getDocAlteror())
								.putSimple("docAlterTime", null,
										main.getDocAlterTime())
								.putSimple("fdAlterRecord", null,
										main.getFdAlterRecord());
					}
				}
			}
			multicaster.attatchEvent(
					new EventOfTransactionCommit(StringUtils.EMPTY),
					new IEventCallBack() {
						@Override
						public void execute(ApplicationEvent event)
								throws Throwable {
							// 重新统计用户数据
							sysAttendStatJobService.stat(
									statDetail.getDocCreator(),
									statDetail.getFdDate(),null);
						}
					});

		}
	}

	public void
			setSysAttendStatService(
					ISysAttendStatService sysAttendStatService) {
		this.sysAttendStatService = sysAttendStatService;
	}

	/**
	 * 获取用户打卡记录
	 *
	 * @param statList
	 * @return
	 * @throws Exception
	 */
	private Map getUserAttendMainDetail(List statList) throws Exception {
		Map<String, List<SysAttendMain>> userMainMap = new HashMap<String, List<SysAttendMain>>();

		Date startDate = null, endDate = null;
		List<String> userList = new ArrayList<String>();
		for (int i = 0; i < statList.size(); i++) {
			SysAttendStat stat = (SysAttendStat) statList.get(i);
			userList.add(stat.getDocCreator().getFdId());
			if (stat.getFdDate() != null && startDate == null) {
				startDate = stat.getFdDate();
			}
			if (stat.getFdDate() != null && endDate == null) {
				endDate = stat.getFdDate();
			}
			if (stat.getFdDate() != null && stat.getFdDate().before(startDate)) {
				startDate = stat.getFdDate();
			}
			if (stat.getFdDate() != null && !stat.getFdDate().before(endDate)) {
				endDate = stat.getFdDate();
			}
		}
		return sysAttendMainService.findList(userList, startDate, endDate);
	}

	private List getUserAttendMainList(List<SysAttendMain> userMainList,Date statDate) {
		List<SysAttendMain> mainList = new ArrayList<SysAttendMain>();
		for (SysAttendMain main : userMainList) {
			Date createTime = AttendUtil.getDate(main.getDocCreateTime(), 0);
			if (Boolean.TRUE.equals(main.getFdIsAcross())) {
				createTime = AttendUtil.getDate(main.getDocCreateTime(), -1);
			}
			if (AttendUtil.getDate(statDate, 0).compareTo(createTime) == 0) {
				mainList.add(main);
			}
		}
		return mainList;
	}
	private JSONArray getWorkTime(SysAttendCategory category, Date date) {
        JSONArray works = new JSONArray();
        List<SysAttendCategoryWorktime> workTimes = null;

        if ((Integer.valueOf(0).equals(category.getFdShiftType()) || Integer.valueOf(3).equals(category.getFdShiftType()))
                && Integer.valueOf(1).equals(category.getFdSameWorkTime())) {
            List<SysAttendCategoryTimesheet> tSheets = category.getFdTimeSheets();
            if (tSheets != null && !tSheets.isEmpty()) {
                for (SysAttendCategoryTimesheet tSheet : tSheets) {
                    if (StringUtil.isNotNull(tSheet.getFdWeek()) && tSheet.getFdWeek().indexOf(AttendUtil.getWeek(date) + "") > -1) {
                        workTimes = tSheet.getAvailWorkTime();
                        break;
                    }
                }
            }
        } else {
            workTimes = category.getAvailWorkTime();
        }

        if (workTimes == null || workTimes.isEmpty()) {
            if(category !=null && category.getFdShiftType() == 3 ){
                List<SysAttendCategoryWorktime> fdWorkTime = category.getFdWorkTime();
                if(fdWorkTime!=null && fdWorkTime.size()>0){
                    SysAttendCategoryWorktime workTime = fdWorkTime.get(0);
                    JSONObject json = new JSONObject();
                    json.put("fdWorkId", workTime.getFdId());
                    json.put("fdWorkType", 0);
                    if(workTime.getFdStartTime() != null){
                        json.put("signTime", workTime.getFdStartTime().getTime());
                    }else{
                        json.put("signTime", (1000 * 60 * 60 * 9));
                    }
                    works.add(json);
                    json = new JSONObject();
                    json.put("fdWorkId", workTime.getFdId());
                    json.put("fdWorkType", 1);
                    if(workTime.getFdEndTime()!=null){
                        json.put("signTime", workTime.getFdEndTime().getTime());
                    }else{
                        json.put("signTime", (1000 * 60 * 60 * 18));
                    }
                    works.add(json);
                }
            }

            return works;
        }
        if(category.getFdShiftType() == 4 && workTimes.size() > 0){
            SysAttendCategoryWorktime workTime = workTimes.get(0);
            JSONObject json = new JSONObject();
            json.put("fdWorkId", workTime.getFdId());
            json.put("fdWorkType", 0);
            if(workTime.getFdStartTime() != null){
                json.put("signTime", workTime.getFdStartTime().getTime());
            }else{
                json.put("signTime", 3600000L);
            }
            works.add(json);
            json = new JSONObject();
            json.put("fdWorkId", workTime.getFdId());
            json.put("fdWorkType", 1);
            if(workTime.getFdEndTime()!=null){
                json.put("signTime", workTime.getFdEndTime().getTime());
            }else{
                json.put("signTime", 36000000L);
            }
            works.add(json);
            //3600000
            //36000000
        }else{
            for (SysAttendCategoryWorktime workTime : workTimes) {
                JSONObject json = new JSONObject();
                json.put("fdWorkId", workTime.getFdId());
                json.put("fdWorkType", 0);
                json.put("signTime", workTime.getFdStartTime().getTime());
                works.add(json);
                json = new JSONObject();
                json.put("fdWorkId", workTime.getFdId());
                json.put("fdWorkType", 1);
                json.put("signTime", workTime.getFdEndTime().getTime());
                works.add(json);
            }
        }

        return works;
    }
	@Override
	public Map<String, JSONArray> getRecordsMap(List list)
			throws Exception {
		Map<String, JSONArray> resultMap = new HashMap<String, JSONArray>();
		if (list == null || list.isEmpty()) {
			return resultMap;
		}
		Map<String, List<SysAttendMain>> userMainMap = getUserAttendMainDetail(
				list);
		for (int i = 0; i < list.size(); i++) {
			SysAttendStat stat = (SysAttendStat) list.get(i);
			//重新设置考勤组
			SysAttendHisCategory hisCategory=CategoryUtil.getHisCategoryById(stat.getFdCategoryId());
			SysAttendCategory sysAttendCategory = null;
			if(hisCategory!=null)
			sysAttendCategory = (SysAttendCategory) this.sysAttendCategoryService.findByPrimaryKey(hisCategory.getFdCategoryId());
			stat.setShiftType(sysAttendCategory.getFdShiftType()!=null?sysAttendCategory.getFdShiftType():14);
			stat.setFdCategoryName(hisCategory !=null?hisCategory.getFdName():"");
			//考勤组
			JSONArray records = new JSONArray();
			// 用户所有打卡记录
			List<SysAttendMain> allMainList = userMainMap.get(stat.getDocCreator().getFdId());
			if (allMainList == null || allMainList.isEmpty()) {
				resultMap.put(stat.getFdId(), new JSONArray());
				continue;
			}
			// 某天打卡记录
			List<SysAttendMain> dateMainList = getUserAttendMainList(allMainList, stat.getFdDate());
			if (dateMainList == null || dateMainList.isEmpty()) {
				resultMap.put(stat.getFdId(), new JSONArray());
				continue;
			}
			Date startTime = null;
			Date endTime = null;
			Date baseStartTime = null;
			Date baseEndTime = null;
			List<SysAttendMain> mainList = dateMainList;
			if (mainList != null && !mainList.isEmpty()) {
				for (int k = 0; k < mainList.size(); k++) {
					SysAttendMain main = mainList.get(k);
					JSONArray works = getWorkTime(CategoryUtil.getFdCategoryInfo(main), stat.getFdDate());
					 JSONObject json1 = (JSONObject) works.get(1);
			            String fdWorkId = json1.getString("fdWorkId");
					JSONObject json = new JSONObject();
					json.accumulate("signTime",
							main.getDocCreateTime().getTime());
					json.accumulate("fdId", main.getFdId());
					json.accumulate("fdWorkId",fdWorkId);
//					json.accumulate("fdWorkId",main.getWorkTime() == null ? main.getFdWorkKey(): main.getWorkTime().getFdId());
					json.accumulate("fdWorkType", main.getFdWorkType());
					if(sysAttendCategory != null && 4 == sysAttendCategory.getFdShiftType() && ( 2 == main.getFdStatus() || 3 == main.getFdStatus())){
						json.accumulate("fdStatus", 1);
					}else if(sysAttendCategory != null && 3 == sysAttendCategory.getFdShiftType() && 2 == main.getFdStatus()) {
						json.accumulate("fdStatus", 1);
					}else{
						json.accumulate("fdStatus", main.getFdStatus());
					}


					json.accumulate("fdState",main.getFdState() == null ? 0 : main.getFdState());
					json.accumulate("fdOutside", main.getFdOutside() == null ? false : main.getFdOutside());
					json.accumulate("fdOffType", main.getFdOffType());
					json.accumulate("fdOffTypeText", AttendUtil.getLeaveTypeText(main.getFdOffType()));
					json.accumulate("fdIsAcross", main.getFdIsAcross());
					records.add(json);
					if(main.getFdWorkType()==1&&main.getFdStatus()!=5&&main.getFdStatus()!=4&&main.getFdStatus()!=0){
						endTime=main.getDocCreateTime();
						baseEndTime=main.getFdBaseWorkTime();
					}

					if(main.getFdWorkType()==0&&main.getFdStatus()!=5&&main.getFdStatus()!=4&&main.getFdStatus()!=0){
						startTime=main.getDocCreateTime();
								baseStartTime=main.getFdBaseWorkTime();
					}
				}

				// 当天的用户数据渲染
				if (AttendUtil.getDate(stat.getFdDate(), 0).equals(AttendUtil.getDate(new Date(), 0))) {
					SysAttendMain main = mainList.get(0);
					JSONArray works = getWorkTime(CategoryUtil.getFdCategoryInfo(main), stat.getFdDate());
					 JSONObject json1 = (JSONObject) works.get(1);
			            String fdWorkId = json1.getString("fdWorkId");
					List<Map<String, Object>> signTimeList = this.sysAttendCategoryService
							.getAttendSignTimes(CategoryUtil.getFdCategoryInfo(main),stat.getFdDate(), stat.getDocCreator());
					this.sysAttendCategoryService.doWorkTimesRender(signTimeList, mainList);
					for (Map<String, Object> m : signTimeList) {
						String fdWorkTimeId = (String) m.get("fdWorkTimeId");
						Integer fdWorkType = (Integer) m.get("fdWorkType");
						for (SysAttendMain record : mainList) {
							if (sysAttendCategoryService.isSameWorkTime(m,
									 record.getWorkTime()==null ? "":record.getWorkTime().getFdId(),
									record.getFdWorkType(),
									record.getFdWorkKey())) {
								m.put("fdSigned", true);
							}
						}
					}
					// 当天缺卡数据
					for (Map<String, Object> m : signTimeList) {
						if (!m.containsKey("fdSigned")) {
							JSONObject json = new JSONObject();
							json.accumulate("signTime",
									AttendUtil
											.joinYMDandHMS(new Date(),
													(Date) m.get("signTime"))
											.getTime());
							json.accumulate("fdId", "");
//							json.accumulate("fdWorkId",
//									(String) m.get("fdWorkTimeId"));
							json.accumulate("fdWorkId",
											fdWorkId);
							json.accumulate("fdWorkType",
									(Integer) m.get("fdWorkType"));
							json.accumulate("fdStatus", 0);
							json.accumulate("fdState", 0);
							json.accumulate("fdOutside", false);
							json.accumulate("fdOffType", null);
							json.accumulate("fdOffTypeText", "");
							records.add(json);
						}
					}
				}

				Collections.sort(records, new Comparator<JSONObject>() {
					@Override
					public int compare(JSONObject o1, JSONObject o2) {
						Long arg1 = o1.getLong("signTime");
						Long arg2 = o2.getLong("signTime");
						return arg1.compareTo(arg2);
					}
				});
				resultMap.put(stat.getFdId(), records);
			} else {
				resultMap.put(stat.getFdId(), new JSONArray());
			}
			if(sysAttendCategory!=null&&endTime!=null&&baseEndTime!=null&&startTime!=null&&baseStartTime!=null){
			if(sysAttendCategory.getFdShiftType()==0||sysAttendCategory.getFdShiftType()==1)
				if(sysAttendCategory.getFdIsFlex()==true&&sysAttendCategory.getFdFlexTime()!=null){
					if(startTime!=null&&baseStartTime!=null&&startTime.getTime()<=baseStartTime.getTime())
						if(endTime!=null&&baseEndTime!=null&&((int)(endTime.getTime()-baseEndTime.getTime())>0))
						stat.setFdDelayedTime((int)(endTime.getTime()-baseEndTime.getTime())/(1000*60));
					if(startTime!=null&&baseStartTime!=null&&startTime.getTime()<=(baseStartTime.getTime()+sysAttendCategory.getFdFlexTime()*1000*60)){
						if(endTime!=null&&baseEndTime!=null&&(endTime.getTime()-startTime.getTime()+sysAttendCategory.getFdRestStartTime().getTime()-sysAttendCategory.getFdRestEndTime().getTime())>stat.getFdWorkTime()*60*1000*60)
						stat.setFdDelayedTime((int)(endTime.getTime()-startTime.getTime()+sysAttendCategory.getFdRestStartTime().getTime()-sysAttendCategory.getFdRestEndTime().getTime()-stat.getFdWorkTime()*60*1000*60)/(1000*60));
					}
					if(startTime!=null&&baseStartTime!=null&&startTime.getTime()>(baseStartTime.getTime()+sysAttendCategory.getFdFlexTime()*1000*60)){
//						if(endTime!=null&&baseEndTime!=null&&(endTime.getTime()-baseEndTime.getTime())>stat.getFdWorkTime()*60*1000*60)
							if((int)(endTime.getTime()-baseEndTime.getTime()-sysAttendCategory.getFdFlexTime()*1000*60)>0)
						stat.setFdDelayedTime((int)(endTime.getTime()-baseEndTime.getTime()-sysAttendCategory.getFdFlexTime()*1000*60)/(1000*60));
					}
					if(startTime!=null&&baseStartTime!=null&&startTime.getTime()<baseStartTime.getTime())
						if((int)(endTime.getTime()-baseEndTime.getTime())>0)
						stat.setFdDelayedTime((int)(endTime.getTime()-baseEndTime.getTime())/(1000*60));
				}else{
					if(endTime!=null&&baseEndTime!=null)
						if((int)(endTime.getTime()-baseEndTime.getTime())>0)
					stat.setFdDelayedTime((int)(endTime.getTime()-baseEndTime.getTime())/(1000*60));
				}
			if(sysAttendCategory.getFdShiftType()==3)
				if((int)(endTime.getTime()-startTime.getTime()-8*60*60*1000)>0)
				stat.setFdDelayedTime((int)(endTime.getTime()-startTime.getTime()-8*60*60*1000)/(1000*60));
			if(endTime!=null){
			String str[] = new SimpleDateFormat("HH:mm:ss").format(endTime).toString().split(":");
			if(sysAttendCategory.getFdShiftType()==4)
				if(Integer.parseInt(str[0])-18>=0)
				stat.setFdDelayedTime((int)((Integer.parseInt(str[0])-18)*60+Integer.parseInt(str[1])));
			}
			}
		}
		return resultMap;
	}

	@Override
	public Map<String,Object> formatStatDetail(List list)
			throws Exception {
		// 详细打卡记录
		Map<String, JSONArray> map = getRecordsMap(list);
		Map<String, List<List<JSONObject>>> userWorksMap = new HashMap<String, List<List<JSONObject>>>();
		// 班次与对应打卡记录
		for (String key : map.keySet()) {
			// 用户班次与打卡记录
			List<List<JSONObject>> workList = new ArrayList<List<JSONObject>>();
			// 班次与对应打卡记录
			Map<String, List<JSONObject>> workMap = new HashMap<String, List<JSONObject>>();
			// 用户打卡记录
			JSONArray records = map.get(key);
			for (int i = 0; i < records.size(); i++) {
				JSONObject json = (JSONObject) records.get(i);
				String fdWorkId = (String) json.get("fdWorkId");
				if (!workMap.containsKey(fdWorkId)) {
					workMap.put(fdWorkId, new ArrayList<JSONObject>());
				}
				List<JSONObject> recordList = workMap.get(fdWorkId);
				recordList.add(json);
			}
			// 转换为班次打卡对象
			for (String workId : workMap.keySet()) {
				workList.add(workMap.get(workId));
			}
			Collections.sort(workList, new Comparator<List<JSONObject>>() {
				@Override
				public int compare(List<JSONObject> arg0,
						List<JSONObject> arg1) {
					JSONObject json0 = arg0.get(0);
					JSONObject json1 = arg1.get(0);
					Long signTime0 = (Long) json0.get("signTime");
					Long signTime1 = (Long) json1.get("signTime");
					return signTime0.compareTo(signTime1);
				}
			});
			userWorksMap.put(key, workList);
		}
		//#TODO 因为异步调用的关系.request改成map存储值返回
		Map<String,Object> resultMap=new HashMap<>();
		// 用户班次与打卡详细信息
		resultMap.put("worksMap",userWorksMap);
//		resultMap.put("recordsMap",map);
		resultMap.put("list",list);
		// 用户班次与打卡详细信息，{statId -> [[record,..],[record,..]]}
//		request.setAttribute("worksMap", userWorksMap);

		// {statId -> [record,...]}
//		request.setAttribute("recordsMap", map);
		// request.setAttribute("statDetail", statMap);
		return resultMap;
	}

}
