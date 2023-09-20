package com.landray.kmss.sys.transport.service.spring;

import com.google.api.client.util.ArrayMap;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IDocSubjectModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictAttachmentProperty;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.metadata.dict.SysDictExtendElementProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSimpleProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.transport.dao.ISysTransportExportDao;
import com.landray.kmss.sys.transport.model.SysTransportExportConfig;
import com.landray.kmss.sys.transport.model.SysTransportExportProperty;
import com.landray.kmss.sys.transport.service.ISysTransportExportService;
import com.landray.kmss.sys.xform.util.LangUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.ExcelOutputImp;
import com.landray.kmss.util.excel.KmssEnumFormat;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SysTransportExportServiceImp extends BaseServiceImp implements
		ISysTransportExportService {
	private ISysTransportExportDao getDao() {
		return (ISysTransportExportDao) getBaseDao();
	}

	@Override
	public WorkBook buildWorkBook(String fdId, Locale locale) throws Exception {
		return buildWorkBook(fdId, locale, null);
	}

	@Override
	public WorkBook buildWorkBook(String fdId, Locale locale, List modelList)
			throws Exception {
		// 取得导出配置model
		SysTransportExportConfig config = (SysTransportExportConfig) findByPrimaryKey(fdId);

		/* 取得数据字典 */
		SysDataDict dataDict = SysDataDict.getInstance();
		SysDictModel dictModel = dataDict.getModel(config.getFdModelName());
		Map propertyMap = dictModel.getPropertyMap();

		/* 创建WorkBook、Sheet */
		WorkBook workbook = new WorkBook();
		workbook.setLocale(locale);
		Sheet sheet = new Sheet();
		String title = ResourceUtil
				.getString(dictModel.getMessageKey(), locale);
		title += ResourceUtil.getString(
				"sys-transport:sysTransport.export.data", locale);
		sheet.setTitle(title);

		/* 创建列标题 */
		List propertyList = config.getPropertyList();
		for (Iterator iter = propertyList.iterator(); iter.hasNext();) {
			SysTransportExportProperty property = (SysTransportExportProperty) iter
					.next();
			SysDictCommonProperty commonProperty = (SysDictCommonProperty) propertyMap
					.get(property.getFdName());
			Column col = new Column();
			col.setTitle(ResourceUtil.getString(commonProperty.getMessageKey(),
					locale));
			// 设置列类型
			if (commonProperty instanceof SysDictExtendSimpleProperty) {
				col.setType(commonProperty.getType()
						+ ((SysDictExtendSimpleProperty) commonProperty)
								.getScale());
			} else {
				col.setType(commonProperty.getType());
			}
			sheet.addColumn(col);
		}

		/* 创建表数据（数据库中所有记录） */
		List contentList = new ArrayList();
		String modelName = null;
		if (modelList == null){
		    modelList = getDao().getAllByModelName(dictModel.getModelName());
		    modelName = dictModel.getModelName();
		}else{
		    if(!modelList.isEmpty()){
		        modelName = ModelUtil.getModelClassName(modelList.get(0));
		    }
		}
		boolean allowLogOper = UserOperHelper.allowLogOper("export", modelName);
		if(allowLogOper){
		    UserOperHelper.setModelNameAndModelDesc(modelName);
		    UserOperHelper.logFindAll(modelList, modelName);
		}
		
		for (Iterator iter = modelList.iterator(); iter.hasNext();) {
			Object model = (Object) iter.next();
			if(logger.isDebugEnabled()){
			    logger.debug("model.getClass()=" + model.getClass().getName());
			}
			Object[] values = new Object[propertyList.size()];
			for (int i = 0; i < propertyList.size(); i++) {
				SysTransportExportProperty property = (SysTransportExportProperty) propertyList
						.get(i);
				String propertyName = property.getFdName();
				values[i] = ModelUtil.getModelPropertyString(model,
						propertyName, ";", locale);
			}
			contentList.add(values);
		}

		sheet.setContentList(contentList);
		workbook.addSheet(sheet);
		workbook.setFilename(title);

		return workbook;
	}

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

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
				"sys-transport:sysTransport.export.tip", request.getLocale());
		sheet.setTip(tip);
		sheet.setIfCreateSheetTipLine(true);
		// end
		sheet.setContentList(new ArrayList<Object>());
		workbook.addSheet(sheet);
		// 增加样例页签 start
		Sheet sampleSheet = getSampleSheet();
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
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputFile);
		}
		if (sheet == null) {
			return sampleSheet;
		}
		// 设置提醒行
		String tip = ResourceUtil.getString("sys-transport:sysTransport.exportDesc.desc");
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

	/**
	 * 获取在Excel输入的显示值，而不是时间戳
	 * 
	 * @param cell
	 * @return
	 */
	protected String getExactlyDateValue(Cell cell) {
		String cellString = "";
		// Excel单元格的类型
		short dataFormat = cell.getCellStyle().getDataFormat();
		SimpleDateFormat sdf = null;
		// 统一格式化为最长的，后面根据格式类型截取
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		cellString = sdf.format(cell.getDateCellValue());
		// cellString 为 2016-07-25 00:00
		if (dataFormat == HSSFDataFormat.getBuiltinFormat("h:mm")) {
			cellString = cellString.substring(11,16);
		} else if (dataFormat == HSSFDataFormat.getBuiltinFormat("m/d/yy")) {
			cellString = cellString.substring(0, 10);
		}
		return cellString;
	}

	/**
	 *返回带有明细表标题的sheet
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Sheet detailTableGetTitle(HttpServletRequest request)
			throws Exception {
		/* 创建Sheet */
		Sheet sheet = new Sheet();
		Locale locale = ResourceUtil.getLocaleByUser();
		String propertyName = request.getParameter("propertyName");
		String modelName = request.getParameter("modelName");
		String isXform = request.getParameter("isXform");
		SysDataDict dataDict = SysDataDict.getInstance();
		String[] propertyNameArray = propertyName.split(",");// 把字符串解析成字符串数组
		String title = "";
		// 普通业务模块和有表单的业务模块的处理方法不一样
		Map<Integer, String[]> contentSelectMap = new HashMap();
		if (StringUtil.isNotNull(isXform)) {
			if ("undefined".equalsIgnoreCase(isXform)
					|| "false".equalsIgnoreCase(isXform)) {
				SysDictModel dictModel = dataDict.getModel(modelName);
				title = ResourceUtil.getString(dictModel.getMessageKey(),
						request.getLocale());
				title += ResourceUtil.getString(
						"sys-transport:sysTransport.import.data", request
								.getLocale());
				// 得到明细表字段属性
				SysDictCommonProperty detailTable = getCommonDetailTableProperty(
						dictModel, request);
				List<SysDictCommonProperty> propertyList = new ArrayList<SysDictCommonProperty>();
				// 根据页面的字段从数据字典取得对应的属性
				propertyList = SysTransportTableUtil.getDictPropertyByName(
						detailTable, propertyNameArray, false, null, locale);
				// 把对应的属性值取出来，取得messageKey设到表格里面
				for (Iterator<SysDictCommonProperty> iterator = propertyList
						.iterator(); iterator.hasNext();) {
					SysDictCommonProperty property = (SysDictCommonProperty) iterator
							.next();
					Column col = new Column();
					col.setTitle(ResourceUtil.getString(property
							.getMessageKey(), locale));
					// 如果必填，字体标红
					if (property.isNotNull()) {
                        col.setRedFont(true);
                    }
					// 如果属性是枚举类，则单元格是枚举型
					if (StringUtil.isNotNull(property.getEnumType())) {
						KmssEnumFormat format = new KmssEnumFormat();
						format.setEnumType(property.getEnumType());
						col.setFormat(format);
					}
					sheet.addColumn(col);
				}
			} else if ("true".equalsIgnoreCase(isXform)) {
				boolean isLangEnabled = SysLangUtil.isLangEnabled();
				Map langMap = new HashMap();
				if (isLangEnabled) {
					langMap = SysTransportTableUtil
							.getXformMultiLang(modelName);
				}
				// 获取表单明细表字段属性
				String itemName = request.getParameter("itemName");// 明细表字段
				SysDictExtendSubTableProperty xformDetailTable = (SysDictExtendSubTableProperty) SysTransportTableUtil
						.getXformDetailTableProperty(modelName, itemName);
				title = ResourceUtil.getString(
						"sys-transport:sysTransport.xformExportTemplet",
						request.getLocale())
						+ "_";
				if (isLangEnabled && langMap != null
						&& !langMap.isEmpty()) {
					title += LangUtil.getValueFromJSON(langMap,
							xformDetailTable.getName(), "label",
							locale.getLanguage());
				} else {
					title += xformDetailTable.getLabel();
				}
				// 获取表单明细表里面的属性数据字典
				List<SysDictExtendProperty> propertyList = SysTransportTableUtil
						.getXformPropertyByName(xformDetailTable,
								ArrayUtil.convertArrayToList(propertyNameArray),
								false, null, locale);
				int i = 0;
				for (Iterator<SysDictExtendProperty> iterator = propertyList
						.iterator(); iterator.hasNext();) {
					SysDictExtendProperty property = (SysDictExtendProperty) iterator
							.next();
					if ("select".equals(property.getBusinessType())) {
						String values = property.getEnumValues();
						if (StringUtils.isNotBlank(values)) {
							String[] lines = values.split(";");
							String[] contentList = new String[lines.length];
							for (int v = 0; v < lines.length; v++) {
								if (lines[v].contains("|")) {// 获取显示值，如果区分不了就获取所有
									contentList[v] = (lines[v].substring(0, lines[v].indexOf("|")));
								} else {
									contentList[v] = (lines[v]);
								}
							}
							if (contentList.length > 0) {
								contentSelectMap.put(i, contentList);
							}
						}
					}
					Column col = new Column();
					String colTitle = "";
					if (isLangEnabled && langMap != null
							&& !langMap.isEmpty()) {
						colTitle = LangUtil.getValueFromJSON(langMap,
								property.getName(), "label",
								locale.getLanguage());
					} else {
						colTitle = property.getLabel();
					}
					col.setTitle(StringEscapeUtils.unescapeHtml(colTitle));
					// 如果必填，字体标红
					if (property.isNotNull()) {
                        col.setRedFont(true);
                    }
					sheet.addColumn(col);
					i++;
				}
			}
		}
		sheet.setSelectContentMap(contentSelectMap);
		sheet.setTitle(title);
		return sheet;
	}


	/**
	 * 获取普通业务模块的明细表字段属性
	 * 
	 * @param dictModel
	 * @param request
	 * @return
	 */
	private SysDictCommonProperty getCommonDetailTableProperty(
			SysDictModel dictModel, HttpServletRequest request) {
		SysDictCommonProperty dictCommonProperty = new SysDictCommonProperty();
		String modelName = request.getParameter("modelName");
		String itemName = request.getParameter("itemName");// 明细表字段
		Map propertyMap = dictModel.getPropertyMap();
		itemName = SysTransportTableUtil.transportFormNameToModelName(
				modelName, itemName);
		dictCommonProperty = (SysDictCommonProperty) propertyMap.get(itemName);
		if (dictCommonProperty != null) {
			return dictCommonProperty;
		} else {
			// 找不到该属性
			return null;
		}

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
				"sys-transport:sysTransport.export.tip", request.getLocale());
		sheet.setTip(tip);
		sheet.setIfCreateSheetTipLine(true);
		workbook.setLocale(locale);
		List contentList = new ArrayList();// 内容list
		String title = "";

		String modelName = request.getParameter("modelName");
		String itemName = request.getParameter("itemName");// 明细表字段
		String fdId = request.getParameter("fdId");
		Boolean isXform = false;
		if (StringUtil.isNotNull(request.getParameter("isXform"))
				&& "true".equalsIgnoreCase(request.getParameter("isXform"))) {
			isXform = true;
		}
		// 读取后台数据库的记录
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(modelName);
		String whereBlock = hqlInfo.getModelTable() + ".fdId=:fdId";
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdId", fdId);
		List itemList = findList(hqlInfo);
		// 获取存储到数据库的明细表数据
		if (isXform) {
			// 明细表的Excel导出
			// 流程文档存储的数据
			IExtendDataModel extendModel = (IExtendDataModel) itemList.get(0);
			// 获取当前流程文档的模板路径
			String filePath = extendModel.getExtendFilePath();
			// 获取明细表的数字字典
			SysDictExtendSubTableProperty xformDetailTable = (SysDictExtendSubTableProperty) SysTransportTableUtil
					.getXformDetailTableProperty(filePath, itemName);
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
			ExtendDataModelInfo dataInfo = extendModel.getExtendDataModelInfo();
			Map modelData = dataInfo.getModelData();
			// 获取流程文档中明细表的数据
			List detailTable = (ArrayList) modelData.get(itemName);
			String[] expControls = {"costCenter","expenseItem","project","vehicle","currency","wbs","innerOrder","area"};
			// 遍历明细表的每一行记录
			for (int i = 0; i < detailTable.size(); i++) {
				Map<String, Object> dataMap = (HashMap) detailTable.get(i);
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
						//兼容业务关联控件
						if("placeholder".equals(type) && dataMap.containsKey(fieldId+"_text")){
							value = dataMap.get(fieldId + "_text");
						}
						//兼容费控控件
						if(Arrays.asList(expControls).contains(type)) {
							value = dataMap.get(fieldId + "_name");
						}
						val = formatValue(fieldId,
								value,
								nameToProperty);
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
			if (extendModel instanceof IDocSubjectModel) {
				title = ((IDocSubjectModel) extendModel).getDocSubject() + "_"
						+ title;
			}
			sheet.setTitle(title);
		} else {
			// 普通业务模块的Excel导出 未做验证，待完善 by zhugr 2017-05-17
			IBaseModel baseModel = (IBaseModel) itemList.get(0);
			Field[] fields = baseModel.getClass().getDeclaredFields();// 获取该model的所有属性
			Field.setAccessible(fields, true);// 这个是关键，只有设置了这个方法，才可以读取private的数值，当然也会有相应的风险
			for (Field field : fields) {
				Column col = new Column();
				col.setTitle(field.getName());
				String clz = field.getType().toString();
				col.setType(clz.substring(clz.lastIndexOf(".") + 1));
				sheet.addColumn(col);
			}

			for (Iterator iterator = itemList.iterator(); iterator.hasNext();) {
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
		Sheet sampleSheet = getSampleSheet();
		workbook.addSheet(sampleSheet);
		// end
		workbook.setFilename(sheet.getTitle());
		ExcelOutputImp output = new ExcelOutputImp();
		output.output(workbook, request, response);
//		String userAgent = request.getHeader("User-Agent");
//		// 非IE/Edge浏览器下面导出文件的名字需要用UTF-8转码，不然文件名乱码
//		// IE11的header已经去掉MSIE这个属性，需对ie11以上版本做个特殊判断
//		if (userAgent.contains("MSIE")
//				|| (userAgent.contains("rv")
//						&& userAgent.contains("Trident"))
//				|| userAgent.contains("Edge")) {
//			output.output(workbook, response);
//		} else {
//			output.output(workbook, response, "UTF-8");
//		}
	}

	protected Map parseFieldToMap(JSONArray fieldArray) {
		Map resultMap = new ArrayMap();
		for (int i = 0; i < fieldArray.size(); i++) {
			JSONObject fieldJSON = JSONObject.fromObject(fieldArray.get(i));
			resultMap.put(fieldJSON.get("fieldId"),
					fieldJSON.get("fieldType"));
		}
		return resultMap;
	}
	//时间维度值
	protected Map parseDateFieldToMap(JSONArray fieldArray) {
		Map resultMap = new ArrayMap();
		for (int i = 0; i < fieldArray.size(); i++) {
			JSONObject fieldJSON = JSONObject.fromObject(fieldArray.get(i));
			if(fieldJSON.containsKey("dimensionType")){
				resultMap.put(fieldJSON.get("fieldId"),
						fieldJSON.get("dimensionType"));
			}
		}
		return resultMap;
	}

	protected List parseFieldToId(JSONArray fieldArray) {
		List resultList = new ArrayList();
		for (int i = 0; i < fieldArray.size(); i++) {
			JSONObject fieldJSON = JSONObject.fromObject(fieldArray.get(i));
			resultList.add(fieldJSON.get("fieldId"));
		}
		return resultList;
	}

	/**
	 * 格式化从数据库取出的值
	 * 
	 * @param object
	 * 
	 * @param entry
	 * @param nameToProperty
	 * @return
	 */
	protected String formatValue(String fieldId, Object val, Map nameToProperty) {
		String result = "";
		if (nameToProperty.containsKey(fieldId) && val != null) {
			SysDictExtendProperty extendProperty = (SysDictExtendProperty) nameToProperty
					.get(fieldId);
			// 判断类型，来获取正确的值
			// 判断是否是单选或多选
			if (extendProperty.isEnum()) {
				String enumValues = extendProperty.getEnumValues();
				String[] enumArray = enumValues.split(";");
				String[] valArray = val.toString().split(";");
				List valList = new ArrayList();
				// 去除值里面的重复数据，复选框有可能会有重复值（不知原因）
				for (int x = 0; x < valArray.length; x++) {
					if (valList.contains(valArray[x])) {
						continue;
					}
					valList.add(valArray[x]);
				}
				boolean isNum = false;
				if (extendProperty.isNumber()) {
					isNum = true;
				}
				// 遍历值
				for (int j = 0; j < valList.size(); j++) {
					// 遍历获取显示值
					for (int i = 0; i < enumArray.length; i++) {
						String enumValue = enumArray[i];
						String[] enumValueArray = enumValue.split("\\|");
						// 如果是数字类型，则格式化字符串
						if (isNum) {
							BigDecimal bg = new BigDecimal(enumValueArray[1]);
							DecimalFormat df = new DecimalFormat(
									"###0.0###########");
							enumValueArray[1] = df.format(bg);
						}
						// 如果实际值相等，则赋予显示值
						if (enumValueArray[1].equals(valList.get(j))) {
							result += enumValueArray[0] + ";";
							break;
						}
					}
				}
				if (result.length() > 0) {
					result = result.substring(0, result.length() - 1);
				}
			}else if(extendProperty.isDateTime()){
				//是否是时间日期类型
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						ResourceUtil.getString("date.format.datetime"));
				result = dateFormat.format((Date) val);
				String dateType = extendProperty.getType();
				String dimension = extendProperty.getDimension();
				if ("Date".equalsIgnoreCase(dateType)) {
					result = result.substring(0, 10);
					//以下是添加日期维度判断，进行截取日期值 by xwuh #166106
					if("yearMonth".equalsIgnoreCase(dimension)){
						Date date = DateUtil.convertStringToDate( result,ResourceUtil.getString("date.format.date"));
						result = DateUtil.convertDateToString(date,ResourceUtil.getString("date.format.yearMonth"));
					}
					if("year".equalsIgnoreCase(dimension)){
						Date date = DateUtil.convertStringToDate(result,ResourceUtil.getString("date.format.date"));
						result = DateUtil.convertDateToString(date,ResourceUtil.getString("date.format.year"));
					}
				} else if ("Time".equalsIgnoreCase(dateType)) {
					result = result.substring(11);
				}
			} else if (extendProperty instanceof SysDictExtendElementProperty) {
				if(val instanceof SysOrgElement){
					SysOrgElement sysOrgElement = (SysOrgElement) val;
					result = sysOrgElement.getFdName();
				}else{
					// 判断是否是对象类型，例如地址本
					Map person = (Map) val;
					if (person.containsKey("name")) {
						result = (String) person.get("name");
					}
				}
			} else if (extendProperty.isNumber()) {
				// 数字类型需要处理小数位
				String formatInt = "#####0";
				String formatDigit = ".###";
				String templateDigit = "0000000000000000000";
				if (extendProperty instanceof SysDictExtendSimpleProperty) {
					int scale = ((SysDictExtendSimpleProperty) extendProperty)
							.getScale();
					// 千分位在数据字典没有体现，无法判断
					// 小数位的补零处理
					if (scale > 0) {
						formatDigit = "." + templateDigit.substring(0, scale);
					}
				}
				BigDecimal bg = new BigDecimal(val.toString());
				DecimalFormat df = new DecimalFormat(formatInt + formatDigit);
				result = df.format(bg);
			} else {
				result = val.toString();
			}
		}
		return result;
	}

	/*
	 * 明细表Excel导出 编辑状态
	 */
	@Override
	public void detailsTableExportDataInEdit(HttpServletRequest request,
											 HttpServletResponse response) throws Exception {
		Locale locale = UserUtil.getKMSSUser().getLocale();
		// 创建WorkBook、Sheet
		WorkBook workbook = new WorkBook();

		Sheet sheet = new Sheet();
		workbook.setLocale(locale);
		List contentsList = new ArrayList();// 内容list
		String title = "";
		// 获取需要导出的所有数据
		String export = request.getParameter("export");
		JSONObject obj = JSONObject.fromObject(export);
		String itemName = request.getParameter("itemName");
		String fileName = request.getParameter("fileName");
		String isXform = request.getParameter("isXform");
		if (StringUtil.isNotNull(isXform) && "true".equalsIgnoreCase(isXform)) {
			boolean isLangEnabled = SysLangUtil.isLangEnabled();
			Map langMap = new HashMap();
			if (isLangEnabled) {
				langMap = SysTransportTableUtil.getXformMultiLang(fileName);
			}
			// 设置提醒行 strat
			String tip = ResourceUtil.getString(
					"sys-transport:sysTransport.export.tip",
					request.getLocale());
			sheet.setTip(tip);
			sheet.setIfCreateSheetTipLine(true);
			SysDictExtendSubTableProperty dictExtendSubTableProperty = (SysDictExtendSubTableProperty) SysTransportTableUtil
					.getXformDetailTableProperty(fileName, itemName);
			if (isLangEnabled && langMap != null
					&& !langMap.isEmpty()) {
				title = LangUtil.getValueFromJSON(langMap,
						dictExtendSubTableProperty.getName(), "label",
						locale.getLanguage());
			} else {
				title = dictExtendSubTableProperty.getLabel();
			}
			title += "_" + ResourceUtil.getString(
					"sys-transport:sysTransport.export.Exceldata",
					request.getLocale());
			sheet.setTitle(title);
			// 设置标题
			JSONArray datas = obj.getJSONArray("data");
			JSONArray ids = obj.getJSONArray("ids");
			Map propertyMap = dictExtendSubTableProperty
					.getElementDictExtendModel()
					.getPropertyMap();
			for (int i = 0; i < ids.size(); i++) {
				Column col = new Column();
				String type = "";
				if (propertyMap.containsKey(ids.getString(i))) {
					SysDictExtendProperty sysDictExtendProperty = ((SysDictExtendProperty) propertyMap
							.get(ids.getString(i)));
					String colTitle = "";
					if (isLangEnabled && langMap != null
							&& !langMap.isEmpty()) {
						colTitle = LangUtil.getValueFromJSON(langMap,
								sysDictExtendProperty.getName(), "label",
								locale.getLanguage());
					} else {
						colTitle = sysDictExtendProperty.getLabel();
					}
					col.setTitle(StringEscapeUtils.unescapeHtml(colTitle));
					if (sysDictExtendProperty.isNotNull()) {
						col.setRedFont(true);
					}
					if (sysDictExtendProperty instanceof SysDictExtendSimpleProperty) {
						type = sysDictExtendProperty.getType()
								+ ((SysDictExtendSimpleProperty) sysDictExtendProperty)
										.getScale();
						if ("Double".equals(sysDictExtendProperty.getType())
								&& ((SysDictExtendSimpleProperty) sysDictExtendProperty)
										.getScale() < 0) {
							type = null;
						}
					} else {
						type = sysDictExtendProperty.getType();
					}
					col.setType(type);
					sheet.addColumn(col);
				} else {
//					col.setTitle("特殊字段无法解析，请手动输入");
//					type = "String";
					logger.info("特殊字段无法解析，请手动输入");
				}
			}
			// 遍历行数据
			for (int i = 0; i < datas.size(); i++) {
				JSONArray data = datas.getJSONArray(i);
				List contentList = new ArrayList();
				// 遍历列数据
				for (int j = 0; j < data.size(); j++) {
					JSONObject dataObject = data.getJSONObject(j);
					
					if (propertyMap.containsKey(ids.getString(j))) {
						SysDictExtendProperty sysDictExtendProperty = ((SysDictExtendProperty) propertyMap
								.get(dataObject.get("id")));
						if("DateTime".equals(sysDictExtendProperty.getType())){
							String datetimeStr=dataObject.getString("value");
							if(StringUtil.isNotNull(datetimeStr)) {
								try {
									//获取各语言日期时间格式
									String dateTimePattern = ResourceUtil.getString("date.format.datetime", ResourceUtil.getLocaleByUser());
									SimpleDateFormat sdf = new SimpleDateFormat(dateTimePattern);
									Date dateTimeTemp = sdf.parse(datetimeStr);
									SimpleDateFormat dateFormat = new SimpleDateFormat(
											"yyyy-MM-dd HH:mm");
									contentList.add(dateFormat.format(dateTimeTemp));
								}catch (Exception e) {
									contentList.add(datetimeStr);
								}
							}
						}else {
							contentList.add(dataObject.getString("value"));
						}
					}
					
				}
				contentsList.add(contentList);
			}
		}
		sheet.setContentList(contentsList);
		sheet.setTitle(title);
		workbook.addSheet(sheet);
		// 增加样例页签 start
		Sheet sampleSheet = getSampleSheet();
		workbook.addSheet(sampleSheet);
		// end
		workbook.setFilename(sheet.getTitle());
		ExcelOutputImp output = new ExcelOutputImp();
		output.output(workbook, request, response);
//		String userAgent = request.getHeader("User-Agent");
//		// 非IE/Edge浏览器下面导出文件的名字需要用UTF-8转码，不然文件名乱码
//		// IE11的header已经去掉MSIE这个属性，需对ie11以上版本做个特殊判断
//		if (userAgent.contains("MSIE")
//				|| (userAgent.contains("rv")
//						&& userAgent.contains("Trident"))
//				|| userAgent.contains("Edge")) {
//			output.output(workbook, response);
//		} else {
//			output.output(workbook, response, "UTF-8");
//		}
	}

}
