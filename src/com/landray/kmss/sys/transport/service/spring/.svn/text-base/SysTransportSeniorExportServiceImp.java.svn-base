package com.landray.kmss.sys.transport.service.spring;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.config.dict.SysDictAttachmentProperty;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSimpleProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.transport.dao.ISysTransportExportDao;
import com.landray.kmss.sys.transport.service.ISysTransportSeniorExportService;
import com.landray.kmss.sys.xform.base.service.controls.seniorDetailsTable.spring.SysFormDetailsTableMainServiceImp;
import com.landray.kmss.sys.xform.util.LangUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;
import net.sf.json.JSONArray;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class SysTransportSeniorExportServiceImp extends SysTransportExportServiceImp implements
		ISysTransportSeniorExportService {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private ISysTransportExportDao getDao() {
		return (ISysTransportExportDao) getBaseDao();
	}
	public SysFormDetailsTableMainServiceImp getSysFormDetailsTableMainService() {
		return (SysFormDetailsTableMainServiceImp) SpringBeanUtil.getBean("sysFormDetailsTableMainServiceTarget");
	}
	/*
	 * 明细表数据导出 阅读状态
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void detailsTableExportData(HttpServletRequest request,
									   HttpServletResponse response, Locale locale) throws Exception {
		locale = UserUtil.getKMSSUser().getLocale();
		// 导出所有字段的数据
		JSONArray fieldArray = JSONArray
				.fromObject(request.getParameter("field"));
		if (fieldArray.size() <= 0) {
			return;
		}
		// 创建WorkBook、Sheet
		WorkBook workbook = new WorkBook();
		Sheet sheet = new Sheet();
		// 设置提醒行 strat
		String tip = ResourceUtil.getString(
				"sys-transport:sysTransport.seniorExport.tip", request.getLocale());
		sheet.setTip(tip);
		sheet.setIfCreateSheetTipLine(true);
		workbook.setLocale(locale);
		List contentList = new ArrayList();// 内容list
		String title = "";
		// 业务模块的 modelId
		String modelId = request.getParameter("modelId");
		// 表单的 Id
		String formId = request.getParameter("formId");
		// 高级明细表的id
		String detailId = request.getParameter("detailId");
		// 数据总数
		String s_rowsize = request.getParameter("fdNum");
		// 导出数据开始
		String s_rowsizeStart = request.getParameter("fdNumStart");
		// 导出数据结束
		String s_rowsizeEnd = request.getParameter("fdNumEnd");
		//导出的列
		String fdColumns = request.getParameter("fdColumns");
		//选中的记录
		String s_checkIdValues = request.getParameter("checkIdValues");
		//是否保留rtf样式
		String fdKeepRtfStyle = request.getParameter("fdKeepRtfStyle");
		//哪种导出方式
		String fdExportType = request.getParameter("fdExportType");
		String modelName = request.getParameter("modelName");
		String filePath = request.getParameter("filePath");

		// 存放选择的fdId
		String[] arrIds = null;
		if (StringUtil.isNotNull(s_checkIdValues)) {
			arrIds = s_checkIdValues.split("[;\\|]");
		}
		Boolean isXform = false;
		if (StringUtil.isNotNull(request.getParameter("isXform"))
				&& "true".equalsIgnoreCase(request.getParameter("isXform"))) {
			isXform = true;
		}

		// 接收查询的记录
		List exportList = null;
		Map<String,Object> map = new HashMap<>();
		map.put("modelId",modelId);
		map.put("formId",formId);
		map.put("controlId",detailId);
		// 导出数据，有3种方式
		// 1.按选择的记录导出
		// 2.按起止时间导出
		// 3.导出全部（此方式导出，如果一次导出的数据量太大，会严重影响服务器性能，还可能引发耗尽内存而宕机）
		if ("3".equals(fdExportType)) {  // 如果用户选了记录，则优先按选择记录的方式导出
			exportList = getSysFormDetailsTableMainService().findListByIds(map,arrIds);
		} else if ("2".equals(fdExportType)) { //按范围导出记录
			int rowsizeStart = Integer.parseInt(s_rowsizeStart) - 1;
			int rowsizeEnd = Integer.parseInt(s_rowsizeEnd);
			if (rowsizeEnd < rowsizeStart) {
				throw new Exception("导出数据的“开始数”不能大于“终止数”！");
			}
			int max = rowsizeEnd - rowsizeStart;
			if (max > 5000) {
				logger.warn("导出的数量大于5000，为了避免服务器宕机，强制限制导出数量为5000");
				max = 5000;
			}
			map.put("maxSize",max);
			map.put("rowSizeStart",rowsizeStart);
			map.put("rowSizeEnd",rowsizeEnd);
			List selectList = getSysFormDetailsTableMainService().findListByRow(map);
			// 根据输入的首尾记录数进行截取数据
			if(selectList!=null && selectList.size()>0){
				exportList = selectList.subList(rowsizeStart,selectList.size());
			}else{
				exportList = selectList;
			}

		} else { //全部导出
			int rowsize = Integer.parseInt(s_rowsize);
			// 避免出现宕机，当导出的数量大于5000时，这里强制限制5000
			if (rowsize > 5000){
				rowsize = 5000;
			}
			map.put("maxSize",rowsize);
			exportList = getSysFormDetailsTableMainService().findAllList(map);
		}
		// 获取存储到数据库的明细表数据
		if (isXform) {
			// 明细表的Excel导出
			// 流程文档存储的数据
			//IExtendDataModel extendModel = (IExtendDataModel) exportList.get(0);
			// 获取当前流程文档的模板路径
			//String filePath = extendModel.getExtendFilePath();
			// 获取明细表的数字字典
			SysDictExtendSubTableProperty xformDetailTable = (SysDictExtendSubTableProperty) SysTransportTableUtil
					.getXformDetailTableProperty(filePath, detailId);
			Map nameToProperty = xformDetailTable.getElementDictExtendModel()
					.getPropertyMap();
			// 获取附件属性
			List attachInfo = xformDetailTable.getElementDictExtendModel()
					.getAttachmentPropertyList();
			// 标题行
			List titleList = parseFieldToId(fieldArray);
			// 增加一个控件类型的map，为尽量不更改原有代码，故做增加而不做更改titleList
			Map fieldMap = parseFieldToMap(fieldArray);
			// 过滤附件ID，附件的不用导出
			for (int i = 0; i < attachInfo.size(); i++) {
				SysDictAttachmentProperty dictAttachmentPropery = (SysDictAttachmentProperty) attachInfo
						.get(i);
				if (titleList.contains(dictAttachmentPropery.getName())) {
					titleList.remove(dictAttachmentPropery.getName());
				}
			}
			// 获取存储的数据
			//ExtendDataModelInfo dataInfo = extendModel.getExtendDataModelInfo();
			//Map modelData = dataInfo.getModelData();
			// 获取流程文档中明细表的数据
			//List detailTable = (ArrayList) modelData.get(detailId);
			String[] expControls = {"costCenter","expenseItem","project","vehicle","currency","wbs","innerOrder","area"};
			// 遍历明细表的每一行记录
			for (int i = 0; i < exportList.size(); i++) {
				Map<String, Object> dataMap = (HashMap) exportList.get(i);
				// 填充每行内容
				List fieldList = new ArrayList();
				for (int j = 0; j < titleList.size(); j++) {
					String fieldId = (String) titleList.get(j);
					String val = "";
					if (dataMap.containsKey(fieldId)) {
						String type = (String) fieldMap.get(fieldId);
						Object value = dataMap.get(fieldId);
						// 兼容动态控件
						if (type.indexOf("xform_relation_") > -1) {
							value = dataMap.get(fieldId + "_text");
						}
						//兼容费控控件
						if(Arrays.asList(expControls).contains(type)) {
							value = dataMap.get(fieldId + "_name");
						}
						// 兼容地址本多选
						if(value instanceof List){
							List listValue = (List)value;
							List<String> arrayList = new ArrayList<String>();
							String[] arrayStr;
							for(int l=0;l<listValue.size();l++){
								value =  (Object) listValue.get(l);
								String fieldValue = formatValue(fieldId,
										value,
										nameToProperty);
								arrayList.add(fieldValue);
							}
							if (arrayList.size()>0){
								val = arrayList.stream().collect(Collectors.joining(";"));
							}

						}else{
							val = formatValue(fieldId,
									value,
									nameToProperty);
						}

					}
					fieldList.add(val);
				}
				contentList.add(fieldList);
			}
			boolean isLangEnabled = SysLangUtil.isLangEnabled();
			Map langMap = new HashMap();
			if (isLangEnabled) {
				langMap = SysTransportTableUtil.getXformMultiLang(filePath);
			}
			// 设置标题
			for (int i = 0; i < titleList.size(); i++) {
				String fieldName = (String) titleList.get(i);
				Column col = new Column();
				if (nameToProperty.containsKey(fieldName)) {
					SysDictExtendProperty property = (SysDictExtendProperty) nameToProperty
							.get(fieldName);
					String colTitle = "";
					if (isLangEnabled && langMap != null
							&& !langMap.isEmpty()) {
						colTitle = LangUtil.getValueFromJSON(langMap,
								property.getName(), "label",
								locale.getLanguage());
					} else {
						colTitle = property.getLabel();
					}
					if (property.isNotNull()) {
						col.setRedFont(true);
					}
					col.setTitle(StringEscapeUtils.unescapeHtml(colTitle));
					if (property instanceof SysDictExtendSimpleProperty) {
						col.setType(property.getType()
								+ ((SysDictExtendSimpleProperty) property)
								.getScale());
						if ("Double".equals(property.getType())
								&& ((SysDictExtendSimpleProperty) property)
								.getScale() < 0) {
							col.setType(null);
						}
					} else {
						col.setType(property.getType());
					}
				} else {
					col.setTitle("特殊字段无法解析，请手动输入");
					col.setRedFont(true);
				}
				sheet.addColumn(col);
			}
			if (isLangEnabled && langMap != null
					&& !langMap.isEmpty()) {
				title = LangUtil.getValueFromJSON(langMap,
						xformDetailTable.getName(), "label",
						locale.getLanguage());
			} else {
				title = xformDetailTable.getLabel();
			}
			title += "_" + ResourceUtil.getString(
					"sys-transport:sysTransport.export.Exceldata",
					request.getLocale());
			// 获取当前文档的标题
			/*if (extendModel instanceof IDocSubjectModel) {
				title = ((IDocSubjectModel) extendModel).getDocSubject() + "_"
						+ title;
			}*/
			sheet.setTitle(title);
		} else {
			// 普通业务模块的Excel导出 未做验证，待完善 by zhugr 2017-05-17
			IBaseModel baseModel = (IBaseModel) exportList.get(0);
			Field[] fields = baseModel.getClass().getDeclaredFields();// 获取该model的所有属性
			Field.setAccessible(fields, true);// 这个是关键，只有设置了这个方法，才可以读取private的数值，当然也会有相应的风险
			for (Field field : fields) {
				Column col = new Column();
				col.setTitle(field.getName());
				String clz = field.getType().toString();
				col.setType(clz.substring(clz.lastIndexOf(".") + 1));
				sheet.addColumn(col);
			}

			for (Iterator iterator = exportList.iterator(); iterator.hasNext();) {
				IBaseModel ibModel = (IBaseModel) iterator.next();
				List fieldList = new ArrayList();
				for (Field field : fields) {
					fieldList.add(field.get(ibModel));
				}
				contentList.add(fieldList);
			}
			title = "test";
		}

		sheet.setContentList(contentList);
		sheet.setTitle(title);
		workbook.addSheet(sheet);
		// 增加样例页签 start
		Sheet sampleSheet = this.getSampleSheet();
		workbook.addSheet(sampleSheet);
		// end
		workbook.setFilename(sheet.getTitle());
		ExcelOutputImp output = new ExcelOutputImp();
		output.output(workbook, request, response);
	}
	/*
	 * 明细表模板下载
	 *
	 */
	@Override
	public void deTableDownloadTemplate(HttpServletRequest request,
										HttpServletResponse response, Locale locale) throws Exception {
		/* 创建WorkBook、Sheet */
		WorkBook workbook = new WorkBook();
		workbook.setLocale(locale);
		Sheet sheet = detailTableGetTitle(request);
		// 设置提醒行 strat
		String tip = ResourceUtil.getString(
				"sys-transport:sysTransport.seniorExport.tip", request.getLocale());
		sheet.setTip(tip);
		sheet.setIfCreateSheetTipLine(true);
		// end
		sheet.setContentList(new ArrayList<Object>());
		workbook.addSheet(sheet);
		// 增加样例页签 start
		Sheet sampleSheet = this.getSampleSheet();
		workbook.addSheet(sampleSheet);
		// end
		workbook.setFilename(sheet.getTitle());
		ExcelOutputImp output = new ExcelOutputImp();
		output.output(workbook, request, response);
//		String userAgent = request.getHeader("User-Agent");
//		// 非IE/Edge浏览器下面导出文件的名字需要用UTF-8转码，不然文件名乱码
//		// IE11的header已经去掉MSIE这个属性，需对ie11以上版本做个特殊判断
//		if (userAgent.contains("MSIE")
//				|| (userAgent.contains("rv") && userAgent.contains("Trident"))
//				|| userAgent.contains("Edge")) {
//			output.output(workbook, response);
//		} else {
//			output.output(workbook, response, "UTF-8");
//		}
	}

	@Override
	protected Sheet getSampleSheet() throws IOException {
		InputStream inputFile = null;
		Sheet sampleSheet = new Sheet();
		sampleSheet.setTitle(ResourceUtil.getString("sys-transport:sysTransport.exportTitle.sample"));
		org.apache.poi.ss.usermodel.Sheet sheet = null;
		Workbook wb = null;

		try {
			inputFile = new FileInputStream(new File(ConfigLocationsUtil
					.getWebContentPath()
					+ "/sys/xform/impt/help/detailTableExportSample.xlsx"));

			wb = WorkbookFactory.create(inputFile); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);
		} catch (Exception e) {
			if (inputFile != null) {
				inputFile.close();
				inputFile = null;
			}
			if (wb != null){
				IOUtils.closeQuietly(wb);
			}
			e.printStackTrace();
		} finally {
			if (inputFile != null) {
				inputFile.close();
				inputFile = null;
			}
			if (wb != null){
				IOUtils.closeQuietly(wb);
			}
		}
		if (sheet == null) {
			return sampleSheet;
		}
		// 设置提醒行
		String tip = ResourceUtil.getString("sys-transport:sysTransport.seniorExportDesc.desc");
		sampleSheet.setTip(tip);
		sampleSheet.setIfCreateSheetTipLine(true);
		List sampleList = new ArrayList();
		// 遍历sheet，把内容设置到sampleSheet里面
		// 标题行
		Row titleRow = sheet.getRow(0);
		for (int i = 0; i < titleRow.getLastCellNum(); i++) {
			Column col = new Column();
			col.setTitle(ImportUtil.getCellValue(titleRow.getCell(i)));
			sampleSheet.addColumn(col);
		}
		// 内容
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			List subSampleList = new ArrayList();
			for (int j = 0; j < row.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				String contentCell = "";
				// 处理日期、时间格式
				if (cell.getCellType() == org.apache.poi.ss.usermodel.CellType.NUMERIC
						&& org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					contentCell = getExactlyDateValue(cell);
				} else {
					contentCell = ImportUtil.getCellValue(cell);
				}
				subSampleList.add(contentCell);
			}
			sampleList.add(subSampleList);
		}
		sampleSheet.setContentList(sampleList);
		return sampleSheet;
	}
}
