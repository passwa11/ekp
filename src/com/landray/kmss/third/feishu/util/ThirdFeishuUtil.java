package com.landray.kmss.third.feishu.util;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.convertor.FormConvertor_Common;
import com.landray.kmss.common.convertor.IFormToModelConvertor;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.dao.HQLWrapper;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IBaseTemplateModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant.AuthCheck;
import com.landray.kmss.constant.SysAuthConstant.CheckType;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.config.dict.*;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.simplecategory.model.ISysSimpleCategoryModel;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.*;
import com.landray.kmss.web.upload.FormFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ThirdFeishuUtil{

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ThirdFeishuUtil.class);

	public static void initModelFromRequest(IBaseModel model,
			RequestContext request) throws Exception {
		if(request.getParameterMap()==null){
			return;
		}
		String modelName = ModelUtil.getModelClassName(model);
		SysDataDict dataDict = SysDataDict.getInstance();
		Map<String, SysDictCommonProperty> propMap = dataDict
				.getModel(modelName).getPropertyMap();
		for (Entry<String, String[]> entry : request.getParameterMap()
				.entrySet()) {
			// key以i.开头
			String key = entry.getKey();
			if (key.length() < 3 || !key.startsWith("i.")) {
				continue;
			}
			String[] values = entry.getValue();
			if (values == null || values.length == 0
					|| StringUtil.isNull(values[0])) {
				continue;
			}
			// 属性可写
			String propName = key.substring(2);
			if (!PropertyUtils.isWriteable(model, propName)) {
				continue;
			}
			// 数据字典为简单类型或对象类型
			SysDictCommonProperty prop = propMap.get(propName);
			if (prop == null || prop.isReadOnly() || !prop.isCanDisplay()) {
				continue;
			}
			if (prop instanceof SysDictSimpleProperty) {
				BeanUtils.copyProperty(model, propName, values[0]);
			} else if (prop instanceof SysDictModelProperty) {
				SysDictModel dictModel = dataDict.getModel(prop.getType());
				if (dictModel == null) {
					continue;
				}
				// 获取数据
				IBaseService service = null;
				String beanName = dictModel.getServiceBean();
				if (StringUtil.isNotNull(beanName)) {
					service = (IBaseService) SpringBeanUtil.getBean(beanName);
				}
				if (service == null) {
					continue;
				}
				IBaseModel value = service.findByPrimaryKey(values[0]);
				BeanUtils.copyProperty(model, propName, value);
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static String buildCriteria(String serviceBean, String selectBlock,
			String whereBlock, String orderBy) throws Exception {
		// 查询
		IBaseService service = (IBaseService) SpringBeanUtil
				.getBean(serviceBean);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock(selectBlock);
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setOrderBy(orderBy);
		hqlInfo.setCheckParam(CheckType.AuthCheck, AuthCheck.SYS_EDITOR);
		List rtnList = service.findValue(hqlInfo);
		JSONArray result = new JSONArray();
		for (Object rtnVal : rtnList) {
			if (rtnVal == null) {
				continue;
			}
			String optValue = null;
			String optText = null;
			if (rtnVal instanceof Object[]) {
				Object[] rtnVals = (Object[]) rtnVal;
				if (rtnVals.length == 0 || rtnVals[0] == null) {
					continue;
				}
				optValue = rtnVals[0].toString();
				if (rtnVals.length > 1 && rtnVals[1] != null) {
					optText = rtnVals[1].toString();
				} else {
					optText = optValue;
				}
			} else {
				optValue = rtnVal.toString();
				optText = optValue;
			}
			JSONObject json = new JSONObject();
			json.put("value", optValue);
			json.put("text", optText);
			result.add(json);
		}
		return result.toString();
	}
	
	@SuppressWarnings("rawtypes")
    public static final void convert(IBaseService baseService, IExtendForm $baseForm, 
            IBaseModel $baseModel,String[] props, RequestContext requestContext) throws Exception{
        if(props==null || props.length<1){
            return;
        }
        ConvertorContext context = new ConvertorContext();
        context.setBaseService(baseService);
        context.setSObject($baseForm);
        context.setTObject($baseModel);
        context.setObjMap(new HashMap());
        context.setRequestContext(requestContext);
        Map propertyMap = $baseForm.getToModelPropertyMap().getPropertyMap();
        for(String prop:props){
            if(prop==null||prop.trim().length()<1){
                continue;
            }
            IFormToModelConvertor convertor = (IFormToModelConvertor)propertyMap.get(prop);
            if(convertor==null){
                convertor = new FormConvertor_Common(prop);
            }
            context.setSPropertyName(prop);
            convertor.excute(context);
        }
    }
    
    /**
	 * 
	 * @param value(#now or #dayslater;1 or #hourslater or #minuteslater)
	 * @return
	 */
	public static final Date getDateByValue(String value) {
		if(value!=null&&!"#now".equals(value)){
			int num = Integer.valueOf(value.split(";")[1]);
			if(value.startsWith("#dayslater")){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DATE, num);
				return calendar.getTime();
			} else if (value.startsWith("#hourslater")) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.HOUR, num);
				return calendar.getTime();
			} else if (value.startsWith("#minuteslater")) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.MINUTE, num);
				return calendar.getTime();
			}
		}
		return new Date();
	}
	
	/**
	 * 
	 * @param value("#dayslater;1")
	 * @param dateType(date
	 *            or time or dateTime)
	 * @return
	 */
	public static final Date getDateByValue(String value, String dateType) {
		Date date = getDateByValue(value);
		if ("time".equals(dateType)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.YEAR, 1970);
			calendar.set(Calendar.MONTH, 00);
			calendar.set(Calendar.DATE, 01);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.MILLISECOND, 000);
			return calendar.getTime();
		} else if ("date".equals(dateType)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 00);
			calendar.set(Calendar.MINUTE, 00);
			calendar.set(Calendar.SECOND, 00);
			calendar.set(Calendar.MILLISECOND, 000);
			return calendar.getTime();
		} else {
			return date;
		}
	}
	
		/**
	 * 
	 * @param value("#dayslater;1")
	 * @param dateType(date
	 *            or time or dateTime)
	 * @return
	 */
	public static final String getDateStrByValue(String value,
			String dateType) {
		Date date = getDateByValue(value, dateType);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 
	 * @param hqlInfo
	 * @param paramList("key":"eq.docCreateTime","value":"#dayslater;1","dateType":"dateTime")
	 * @param clazz
	 */
	public static void buildHqlInfoDateType(
			HQLInfo hqlInfo, List<Map<String, String>> paramList,
			Class<?> clazz) {
		if (paramList != null) {
			String modelName = clazz.getName();
			String shortName = ModelUtil.getModelTableName(modelName);
			int i = 0;
			for (Map<String, String> paramMap : paramList) {
				String keyInfo = paramMap.get("key");
				String[] names = keyInfo.split("\\.");
				String opt = names[0];
				String fieldName = names[1];
				String valueInfo = paramMap.get("value");
				String dateType = paramMap.get("dateType");
				if ("isnull".equals(opt)) {
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(),
									" and ",
									shortName + "." + fieldName
											+ "is null"));
				} else if ("nnull".equals(opt)) {
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(),
									" and ",
									shortName + "." + fieldName
											+ "is not null"));
				} else if ("eq".equals(opt)) {
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(),
									" and ",
									shortName + "." + fieldName + "=:"
											+ fieldName
											+ String.valueOf(i)));
					hqlInfo.setParameter(fieldName
							+ String.valueOf(i),
							getDateByValue(valueInfo, dateType));
				} else if ("ne".equals(opt)) {
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(),
									" and ",
									shortName + "." + fieldName + "!=:"
											+ fieldName
											+ String.valueOf(i)));
					hqlInfo.setParameter(
							fieldName
									+ String.valueOf(i),
							getDateByValue(valueInfo, dateType));
				} else if ("lt".equals(opt)) {
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(),
									" and ", shortName + "." + fieldName + "<:"
											+ fieldName + String.valueOf(i)));
					hqlInfo.setParameter(fieldName + String.valueOf(i),
							getDateByValue(valueInfo, dateType));
				} else if ("lte".equals(opt)) {
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(),
									" and ", shortName + "." + fieldName + "<=:"
											+ fieldName + String.valueOf(i)));
					hqlInfo.setParameter(fieldName + String.valueOf(i),
							getDateByValue(valueInfo, dateType));
				} else if ("gt".equals(opt)) {
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(),
									" and ", shortName + "." + fieldName + ">:"
											+ fieldName + String.valueOf(i)));
					hqlInfo.setParameter(fieldName + String.valueOf(i),
							getDateByValue(valueInfo, dateType));
				} else if ("gte".equals(opt)) {
					hqlInfo.setWhereBlock(
							StringUtil.linkString(hqlInfo.getWhereBlock(),
									" and ", shortName + "." + fieldName + ">=:"
											+ fieldName + String.valueOf(i)));
					hqlInfo.setParameter(fieldName + String.valueOf(i),
							getDateByValue(valueInfo, dateType));
				}
				i++;

			}
		}
	}
	
		public static void buildHqlInfoDate(HQLInfo hqlInfo,
			HttpServletRequest request, Class<?> clazz) {
		String[] dateInfo = request.getParameterValues("dateInfo");
		List<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
		if (dateInfo != null) {
			for (String info : dateInfo) {
				String[] infos = info.split(";");
				Map map = new HashMap();
				if (3 == infos.length) {
					map.put("key", infos[0]);
					map.put("value", "#" + infos[1]);
					map.put("dateType", infos[2]);
				} else {
					map.put("key", infos[0]);
					map.put("value", "#" + infos[1] + ";" + infos[2]);
					map.put("dateType", infos[3]);
				}
				paramList.add(map);
			}
			buildHqlInfoDateType(hqlInfo, paramList,
					clazz);
		}
	}
	
	public static void buildHqlInfoModel(HQLInfo hqlInfo,
			HttpServletRequest request)
			throws Exception {
		String[] modelInfo = request.getParameterValues("modelInfo");
		if (modelInfo != null) {
			for (String info : modelInfo) {
				String[] infos = info.split(":");
				if (infos != null) {
					String[] key = infos[0].split(".");
					String opt = key[0];
					String property = key[1];
					String values = infos[1];
					buildHqlInfoModelType(property, property, hqlInfo, opt,
							values);
				}
			}
		}

	}

	public static void buildHqlInfoModelType(String shortName,
			String key, HQLInfo hqlInfo, String opt, String value)
			throws Exception {

		String optKey = "";
		if ("isnull".equals(opt)) {
			optKey = "is null";
		} else if ("nnull".equals(opt)) {
			optKey = "is not null";
		} else if ("eq".equals(opt)) {
			optKey = "=";
		} else if ("ne".equals(opt)) {
			optKey = "!=";
		} else if ("in".equals(opt)) {
			optKey = "in";
		} else if ("nin".equals(opt)) {
			optKey = "not in";
		}
		
		if("currentPerson".equals(value)){
			if (UserUtil.getKMSSUser().getUserId() != null) {
				value = UserUtil.getKMSSUser().getUserId();
			} else {
				value = "";
			}

		}
		if("currentPost".equals(value)){
			if(UserUtil.getKMSSUser().getPostIds()!=null){
				value = StringUtils.join(UserUtil.getKMSSUser().getPostIds(),
						";");
			} else {
				value = "";
			}
		}
		if("currentDept".equals(value)){
			if (UserUtil.getKMSSUser().getDeptId() != null) {
				value = UserUtil.getKMSSUser().getDeptId();
			} else {
				value = "";
			}

		}
		if("currentOrg".equals(value)){
			if(UserUtil.getUser().getFdParentOrg()!=null){
				value = UserUtil.getUser().getFdParentOrg().getFdId();
				}else{
					value="";
				}
		}
		

		String whereBlock = hqlInfo.getWhereBlock();
		String propKey = shortName.contains(".fdId") ? shortName
				: (shortName + ".fdId");
		if (StringUtil.isNotNull(whereBlock)) {
			whereBlock = whereBlock + " and ";
		}
		if ("is null".equals(optKey) || "is not null".equals(optKey)) {
			whereBlock += propKey + " " + optKey;
		} else if ("in".equals(optKey) || "not in".equals(optKey)) {
			if (value.contains(";")) {
				List valueList = null;
				HQLWrapper hqlW = null;
				valueList = Arrays.asList(value.split(";"));
				if ("in".equals(optKey)) {
					hqlW = HQLUtil.buildPreparedLogicIN(propKey,
							null, valueList);
				} else {
					hqlW = _buildPreparedLogicNotIN(propKey,
							null, valueList);
				}
				whereBlock += hqlW.getHql();
				hqlInfo.setParameter(hqlW.getParameterList());
			} else {
				// 单值
				optKey = "in".equals(optKey) ? "=" : "!=";
				whereBlock += propKey + " " + optKey + " :" + key;
				hqlInfo.setParameter(key, value);
			}
		} else {
			whereBlock += propKey + " " + optKey + " :" + key;
			hqlInfo.setParameter(key, value);
		}
		hqlInfo.setWhereBlock(whereBlock);
	}

	private static HQLWrapper _buildPreparedLogicNotIN(String item,
			String alias,
			List<?> valueList) {
		String param = alias;
		if (StringUtil.isNull(param)) {
			param = Integer.toHexString(System.identityHashCode(item));
		}
		List<?> valueCopy = new ArrayList<Object>(valueList);
		HQLWrapper hqlWrapper = new HQLWrapper();
		int n = (valueCopy.size() - 1) / 1000;
		StringBuffer whereBlockTmp = new StringBuffer();
		List<?> tmpList;
		for (int k = 0; k <= n; k++) {
			int size = k == n ? valueCopy.size() : (k + 1) * 1000;
			if (k > 0) {
				whereBlockTmp.append(" and ");
			}
			String para = "kmss_in_" + param + "_" + k;
			whereBlockTmp.append(item + " not in (:" + para + ")");
			tmpList = valueCopy.subList(k * 1000, size);
			HQLParameter hqlParameter = new HQLParameter(para, tmpList);
			hqlWrapper.setParameter(hqlParameter);
		}
		if (n > 0) {
			hqlWrapper.setHql("(" + whereBlockTmp.toString() + ")");
		} else {
			hqlWrapper.setHql(whereBlockTmp.toString());
		}
		return hqlWrapper;
	}
	
		/**
	 * 文件名编码，同步附件机制
	 * 
	 * @param request
	 * @param oldFileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeFileName(HttpServletRequest request,
			String oldFileName)
			throws UnsupportedEncodingException {
		String userAgent = request.getHeader("User-Agent").toUpperCase();
		if (userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("TRIDENT") > -1
				|| userAgent.indexOf("EDGE") > -1) {// ie情况处理
			oldFileName = URLEncoder.encode(oldFileName, "UTF-8");
			// 这里的编码后，空格会被解析成+，需要重新处理
			oldFileName = oldFileName.replace("+", "%20");
		} else {
			oldFileName = new String(oldFileName.getBytes("UTF-8"),
					"ISO8859-1");
		}
		return oldFileName;
	}
	
	public static org.json.simple.JSONArray importData(FormFile file, org.json.simple.JSONArray jsArray)
			throws Exception {
		Workbook wb = null;
		Sheet sheet = null;
		try {
			try {
				// 抽象类创建Workbook，适合excel 2003和2007以上
				wb = WorkbookFactory.create(file.getInputStream());
				sheet = wb.getSheetAt(0);
			} catch (Exception e) {
				throw new RuntimeException("上传文件格式错误！系统预期的文件为Excel文件格式");
			}
			// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				return null;
			}
			String ths = "";

			Row rowFirst = sheet.getRow(0);
			int cellNumFirst = rowFirst.getLastCellNum();
			for (int k = 0; k < cellNumFirst; k++) {
				String valueFirst = ImportUtil.getCellValue(rowFirst.getCell(k));
				ths = ths + "," + valueFirst;
			}
			JSONObject job = new JSONObject();
			job.element("ths", ths.substring(1, ths.length()));
			jsArray.add(job);
			// 从第二行开始取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) { // 跳过空行
					continue;
				}
				// 获取列数
				String values = "";

				for (int j = 0; j < cellNumFirst; j++) {
					String value = "";
					try {
						value = ImportUtil.getCellValue(row.getCell(j));
						if (value != null) {
							values = values + ";" + value;
						} else {
							values = values + ";" + "";
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				job = new JSONObject();
				job.element("value",
						values.substring(1, values.length()));
				jsArray.add(job);
			}
			return jsArray;
		}catch (Exception e){
			throw e;
		}finally {
			if(wb!=null){
				wb.close();
			}
		}
	}

	public static HSSFWorkbook buildWorkBook(String[] Headers)
			throws Exception {
		final HSSFWorkbook wb = new HSSFWorkbook();
		final HSSFSheet sheet1 = wb.createSheet("列表数据导出");
		buildTemplate(wb, sheet1, Headers);
		return wb;
	}

	public static HSSFWorkbook exportWorkBook(String[] Headers, List list)
			throws Exception {
		HSSFRow row = null;
		int rowCount = 1;
		final HSSFWorkbook workbook = buildWorkBook(Headers);
		final HSSFSheet sheet1 = workbook.getSheetAt(0);
		if (list != null) {
			for (Object data : list) {
				row = sheet1.createRow(rowCount++);
				List dataLast = (List) data;
				List value = (List) dataLast.get(1);
				buileOneNcCustomer(row, value);
			}
		}
		return workbook;
	}

	private static HSSFCellStyle getCellStyle(final HSSFWorkbook wb,
			final boolean isCenter) {
		final HSSFCellStyle style = wb.createCellStyle();
		if (isCenter) {
			style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 2));
			style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
		}
		style.setWrapText(true);
		style.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
		style.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
		style.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
		style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
		return style;
	}

	private static void setBackgroundColor(final HSSFCellStyle style,
			final boolean isMain) {
		style.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.valueOf((short) 1));
		if (isMain) {
			style.setFillForegroundColor((short) 44);
		} else {
			style.setFillForegroundColor((short) 22);
		}
	}

	private static HSSFFont getTitleFont(final HSSFWorkbook wb,
			final boolean isRed) {
		final HSSFFont font = wb.createFont();
		font.setBold(true);
		if (isRed) {
			font.setColor((short) 10);
		}
		return font;
	}

	private static void buildTemplate(final HSSFWorkbook wb,
			final HSSFSheet sheet,
			String[] Headers) {
		HSSFRow row = null;
		HSSFCell cell = null;
		HSSFCellStyle style = null;
		sheet.setDefaultColumnWidth(20);
		row = sheet.createRow(0);
		int cellIndex = 0;
		for (int i = 0; i < Headers.length; ++i) {
			cell = row.createCell(cellIndex++);
			style = getCellStyle(wb, true);
			setBackgroundColor(style, true);
			style.setFont(getTitleFont(wb, false));
			cell.setCellStyle(style);
			cell.setCellValue(Headers[i]);
		}
		final HSSFPalette palette = wb.getCustomPalette();
		palette.setColorAtIndex((short) 44, (byte) 91, (byte) (-101),
				(byte) (-43));
		palette.setColorAtIndex((short) 22, (byte) (-39), (byte) (-39),
				(byte) (-39));
	}

	private static void buileOneNcCustomer(HSSFRow row, List values)
			throws Exception {
		HSSFCell cell = null;
		int cellIndex = 0;
		for (Object value : values) {
			List valueLast = (List) value;
			cell = row.createCell(cellIndex++);
			cell.setCellValue(valueLast.get(1).toString());
		}
	}

	/**
	 *  判断是否是其它分类
	 * @param templateModelName
	 * @return
	 */
	public static boolean isOtherCategory(String templateModelName){
		if(isSimpleCategory(templateModelName)){
			return false;
		}
		if(isGlobalCategory(templateModelName)){
			return false;
		}
		try {
			if(StringUtils.isNotBlank(templateModelName)){
				Class<?> clazz = ClassUtils.forName(templateModelName);
				if(SysCategoryMain.class.isAssignableFrom(clazz)){
					return false;
				}
			}
		} catch (ClassNotFoundException e) {
		}
		return true;
	}

	/**
	 * 判断是否是简单分类
	 *
	 * @param templateModelName
	 * @return
	 */
	public static boolean isSimpleCategory(String templateModelName) {
		try {
			if(StringUtils.isNotBlank(templateModelName)){
				Class<?> clazz = ClassUtils.forName(templateModelName);
				return (ISysSimpleCategoryModel.class.isAssignableFrom(clazz));
			}
		} catch (ClassNotFoundException e) {
		}
		return false;
	}

	/**
	 * 判断是否为全局分类
	 * @param templateModelName
	 * @return
	 */
	public static boolean isGlobalCategory(String templateModelName){
		try {
			if(StringUtils.isNotBlank(templateModelName)){
				Class<?> clazz = ClassUtils.forName(templateModelName);
				return (IBaseTemplateModel.class.isAssignableFrom(clazz));
			}
		} catch (ClassNotFoundException e) {
		}
		return false;
	}

	/**
	 * 根据属性类型与数据字典查询对应属性名
	 * @param model
	 * @return
	 */
	public static String getFieldNameByType(SysDictModel model,String fieldType){
		String fdName = null;
		List<SysDictCommonProperty> list = model.getPropertyList();
		for(SysDictCommonProperty bean : list){
			if(fieldType.equals(bean.getType())){
				fdName = bean.getName();
			}
		}
		return fdName;
	}

	/**
	 * 获取全局分类的层级名称
	 * @param sysCategoryMain
	 * @return
	 */
	public static String getHierarchyCategoryNames(SysCategoryMain sysCategoryMain) {
		String hierarchyCategoryNames = sysCategoryMain.getFdName();
		SysCategoryMain parentCategory = (SysCategoryMain) sysCategoryMain.getHbmParent();
		while (parentCategory != null) {
			hierarchyCategoryNames = parentCategory.getFdName() + "/" + hierarchyCategoryNames;
			parentCategory = (SysCategoryMain) parentCategory.getHbmParent();
		}
		return hierarchyCategoryNames;
	}

	/**
	 * 获取简单分类的层级名称
	 * @param sysSimpleCategoryModel
	 * @return
	 */
	public static String getHierarchyCategoryNames(ISysSimpleCategoryModel sysSimpleCategoryModel) {
		String hierarchyCategoryNames = sysSimpleCategoryModel.getFdName();
		ISysSimpleCategoryModel parentCategory = (ISysSimpleCategoryModel) sysSimpleCategoryModel.getHbmParent();
		while (parentCategory != null) {
			hierarchyCategoryNames = parentCategory.getFdName() + "/" + hierarchyCategoryNames;
			parentCategory = (ISysSimpleCategoryModel) parentCategory.getHbmParent();
		}
		return hierarchyCategoryNames;
	}

	/**
	 * 判断值是否为同步
	 * @param synWay
	 * @return
	 */
	public static boolean isSyn(String synWay){
		return "syn".equals(synWay);
	}
	/**
	 * 判断值是否为不同步
	 * @param synWay
	 * @return
	 */
	public static boolean isNoSyn(String synWay){
		return "noSyn".equals(synWay);
	}

	/**
	 * 判断值是否为仅新增同步
	 * @param synWay
	 * @return
	 */
	public static boolean isAddSyn(String synWay){
		return "addSyn".equals(synWay);
	}

	/**
	 * 设置同步属性
	 * @param o
	 * @param key
	 * @param value
	 * @param synWay
	 * @param isNew
	 */
	public static void setSynProperty(JSONObject o, String key, Object value, String synWay, boolean isNew){
		if(isSyn(synWay)
			|| (isNew && isAddSyn(synWay))){
			o.put(key, value);
		}
	}

	/**
	 * 获取同步字段值
	 * @param synWay
	 * @param synField
	 * @param element
	 * @param isNew
	 * @return
	 * @throws Exception
	 */
	public static Object getSynValue(String synWay, String synField, SysOrgPerson element, boolean isNew) throws Exception {
		if(isSyn(synWay)
				|| (isNew && isAddSyn(synWay))){
			return getPersonProperty(synField, element);
		}
		return null;
	}

	/**
	 * 根据字段获取人员的值，该值范围：SysOrgPerson和自定义
	 * @param fieldName
	 * @param element
	 * @return
	 * @throws Exception
	 */
	public static Object getPersonProperty(String fieldName, SysOrgPerson element) throws Exception{
		if (StringUtil.isNull(fieldName)) {
			return null;
		}

		Map<String, Object> customMap = element.getCustomPropMap();
		Object propertyVal = null;
		if (customMap != null && customMap.containsKey(fieldName)) {
			propertyVal =  customMap.get(fieldName);
		}
		else if (PropertyUtils.isReadable(element, fieldName)) {
			propertyVal = PropertyUtils.getProperty(element, fieldName);
		}
		if(propertyVal != null){
			return convertPropertyVal2BasicType(propertyVal);
		}
		return propertyVal;
	}

	/**
	 * 转化属性类型为基本数据类型
	 * @param propertyVal
	 * @return
	 */
	private static Object convertPropertyVal2BasicType(Object propertyVal) {
		if(propertyVal instanceof IBaseModel){
			return getModelDisplayNameValue((IBaseModel) propertyVal);
		}
		else if(propertyVal instanceof List){
			List<String> list = ((List<?>) propertyVal).stream().filter(ele->(ele instanceof IBaseModel)).map(ele->{
				return getModelDisplayNameValue((IBaseModel) ele);
			}).filter(p->p != null).map(p->p.toString()).collect(Collectors.toList());
			return String.join(";", list);
		}
		else if(propertyVal instanceof Date){
			return ((Date) propertyVal).getTime() / 1000L;
		}
		return propertyVal;
	}

	/**
	 * 获取主文档显示字段值
	 * @param mainModel
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private static Object getModelDisplayNameValue(IBaseModel mainModel){
			String displayName = getTemplateDisplayName(mainModel.getClass().getName());
			if(StringUtil.isNotNull(displayName)){
				try{
					return PropertyUtils.getProperty(mainModel, displayName);
				}
				catch(Exception e){
					logger.error("", e);
				}
			}
		return null;
	}

	/**
	 * 获取模版类数据字典显示名称
	 * @param templateName
	 * @return
	 */
	public static String getTemplateDisplayName(String templateName) {
		SysDictModel dict = SysDataDict.getInstance().getModel(templateName);
		if (dict == null) {
			return null;
		}
		// 获取各个模板中显示的字段名
		String dispField = dict.getDisplayProperty();
		try {
			HibernateUtil.getColumnName(com.landray.kmss.util.ClassUtils.forName(templateName),
					dispField);
		} catch (Exception e) {
			try {
				dispField = "fdName";
				HibernateUtil.getColumnName(com.landray.kmss.util.ClassUtils.forName(templateName),
						dispField);
			} catch (Exception e2) {
				try {
					dispField = "docSubject";
					HibernateUtil.getColumnName(
							com.landray.kmss.util.ClassUtils.forName(templateName), dispField);
				} catch (Exception e3) {
					return null;
				}
			}
		}
		return dispField;
	}
}
