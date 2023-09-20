package com.landray.kmss.third.wechat.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.ftsearch.config.LksConfig;
import com.landray.kmss.sys.ftsearch.config.LksField;
import com.landray.kmss.sys.ftsearch.db.model.SysFtsearchConfig;
import com.landray.kmss.sys.ftsearch.search.SearchContext;
import com.landray.kmss.third.wechat.forms.SearchParamForm;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

public class WeSearchUtil {

	public static final String LANG_ZH_CN = "zh-cn";

	public static final String LANG_EN_US = "en-us";

	/**
	 * 格式化搜索时间
	 * 
	 * @param date
	 * @param lang
	 */
	public static String formatDate(String date, String lang) {
		if (date == null) {
			return null;
		}
		String _date = date.substring(0, 10);
		if (LANG_ZH_CN.equals(lang)) {
			return _date.replaceAll("-", "");
		} else if (LANG_EN_US.equals(lang)) {
			_date = _date.replaceAll("/", "");
			_date = _date.substring(4) + _date.substring(0, 4);
			return _date;
		} else {
			return _date;
		}
	}

	public static void setParameters(Map<String, String> parameters,
			SearchParamForm searchParamForm, String lang) throws Exception {
		
		String queryString = searchParamForm.getQueryString();
		parameters.put("queryString", replaceQueryString(queryString));// 关键字
		String modelName = searchParamForm.getModelName();//request.getParameter("modelName");
		if(StringUtil.isNull(modelName)){
			SysFtsearchConfig sysFtsearchConfig = new SysFtsearchConfig();
			String ftsearchModelName = sysFtsearchConfig.getFtSearchModelName();
			if (ftsearchModelName == null || "".equals(ftsearchModelName)) {
				Map ftsearches = SysConfigs.getInstance().getFtSearchs();
				String tempModelName = "";
				for (Iterator iter = ftsearches.keySet().iterator(); iter
						.hasNext();) {
					tempModelName += iter.next().toString() + ";";
				}
				ftsearchModelName = tempModelName.substring(0, tempModelName
						.length() - 1);
			}
			parameters.put("modelName",
					convertToFullModelName(ftsearchModelName));
			parameters.put("modelNamex", convertToFullModelName(ftsearchModelName));
		}else{
			parameters.put("modelName", modelName);
			parameters.put("modelNamex", modelName);
		}
		
		String searchFields =searchParamForm.getSearchFields();// request.getParameter("searchFields");
		if(StringUtil.isNull(searchFields)){
			searchFields = "title;content;creator";
		}
		parameters.put("searchFields", createMultiFieldQueryParser(searchFields));
		
		// 过滤搜索
		String filterFields = searchParamForm.getFilterString();//request.getParameter("filterFields");
		String filterString = searchParamForm.getFilterString();//request.getParameter("filterString");
		parameters.put("filterFields", filterFields);
		parameters.put("filterString", filterString);
		
		// 分面参数信息
		String facetFields = searchParamForm.getFacetFields();//request.getParameter("facetFields");
		String facetString = searchParamForm.getFacetString();//request.getParameter("facetString");
		if(StringUtil.isNull(facetFields)){
			parameters.put("facetFields","modelName");
			parameters.put("facetString","");
		}else{
			parameters.put("facetFields", facetFields);
			parameters.put("facetString", facetString);
		}
		
		//排序字段
		String sortType = searchParamForm.getSortType();//request.getParameter("sortType");
		if (StringUtil.isNotNull(sortType)) {
			parameters.put("sortType", sortType);
		} else {
			parameters.put("sortType", "score");
		}

		//??
		String isSearchByButton = searchParamForm.getIsSearchByButton();//request.getParameter("isSearchByButton");
		if (StringUtil.isNotNull(isSearchByButton)) {
			parameters.put("isSearchByButton", isSearchByButton);
		} else {
			parameters.put("isSearchByButton", "true");
		}

		// 创建者
		String creator = searchParamForm.getCreator();//request.getParameter("creator");
		if (StringUtil.isNotNull(creator)) {
			parameters.put("creator", creator);
		}

		//??
		String status = searchParamForm.getStatus();//request.getParameter("status");
		if (StringUtil.isNotNull(status)) {
			parameters.put("status", status);
		}
	
		// 时间段
		String toCreateTime = "";
		String fromCreateTime = "";
		StringBuffer sb = new StringBuffer();
		String timeRange = searchParamForm.getTimeRange();//request.getParameter("timeRange");
		if (StringUtil.isNotNull(timeRange)) {
			sb = new StringBuffer();
			Calendar cal = Calendar.getInstance();
			toCreateTime = DateUtil.convertDateToString(new Date(),
					ResourceUtil.getString("date.format.date"));// 系统时间
			if ("day".equals(timeRange)) {
				cal.add(Calendar.DAY_OF_MONTH, -1);
			} else if ("week".equals(timeRange)) {
				cal.add(Calendar.WEEK_OF_MONTH, -1);
			} else if ("month".equals(timeRange)) {
				cal.add(Calendar.MONTH, -1);
			} else if ("year".equals(timeRange)) {
				cal.add(Calendar.YEAR, -1);
			}else if ("long".equals(timeRange)) {
				cal.add(Calendar.YEAR, -20);
			}
			fromCreateTime = DateUtil.convertDateToString(cal.getTime(),
					ResourceUtil.getString("date.format.date"));
			String type = ResourceUtil.getKmssConfigString("sys.ftsearch.config.engineType"); //获取引擎类型
			if("lucene".equals(type)){
				fromCreateTime = formatDate(fromCreateTime, lang);
				toCreateTime = formatDate(toCreateTime, lang);
			}
			sb.append("[");
			sb.append(fromCreateTime);
			sb.append(" TO ");
			sb.append(toCreateTime);
			sb.append("]");
		}
		if (sb.length() != 0) {
			parameters.put("createTime", sb.toString());
			parameters.put("fromCreateTime", fromCreateTime);
			parameters.put("toCreateTime", toCreateTime);
			parameters.put("dateArea", formatAutonomyDate(fromCreateTime) + " "
					+ formatAutonomyDate(toCreateTime));
		}
	}

	private static String formatAutonomyDate(String time) {
		String retTime = "";
		if (StringUtil.isNotNull(time)) {
			String year = time.substring(0, 4);
			String month = time.substring(4, 6);
			String day = time.substring(6, 8);
			retTime = day + "/" + month + "/" + year;
		}
		return retTime;
	}

	/**
	 * 将KmReviewMain转换为全路径com.landray.kmss.km.KmReviewMain
	 * 
	 * @param modelName
	 * @return
	 */
	private static String convertToFullModelName(String modelName) {
		String[] modelNames = modelName.split(";");
		String fullModelNames = "";
		Map ftsearches = SysConfigs.getInstance().getFtSearchs();
		for (int i = 0; i < modelNames.length; i++) {
			String resultModelName = modelNames[i];
			for (Iterator iter = ftsearches.keySet().iterator(); iter.hasNext();) {
				String ftModelName = iter.next().toString();
				String fullModelname = ftModelName;
				ftModelName = ftModelName.substring(ftModelName
						.lastIndexOf(".") + 1); // 将全路径转为具体model名称
				resultModelName = resultModelName.substring(resultModelName
						.lastIndexOf(".") + 1); // 将全路径转为具体model名称
				// 转换路径后再进行比较
				if (ftModelName.equals(resultModelName)) {
					if (i == modelNames.length - 1) {
						fullModelNames += fullModelname;
					} else {
						fullModelNames += fullModelname + ";";
					}
				}
			}
		}
		return fullModelNames;
	}

	/**
	 * 参数：String line传入需要处理的行字符串 ,全角半角转换
	 */
	private static String replaceQueryString(String line) {
		if (line == null) {
			return "";
		}
		// 创建一个HashMap用来存储全角字符和半角字符的对应关系
		// 每个entry中的key为全角字符，value为半角字符
		Map<String, String> map = new HashMap<String, String>();
		map.put("，", ",");
		map.put("。", ".");
		map.put("〈", "<");
		map.put("〉", ">");
		map.put("｜", "|");
		map.put("《", "<");
		map.put("》", ">");
		map.put("［", "[");
		map.put("］", "]");
		map.put("？", "?");
		map.put("＂", "\"");
		map.put("：", ":");
		map.put("﹑", ",");
		map.put("（", "(");
		map.put("）", ")");
		map.put("【", "[");
		map.put("】", "]");
		map.put("－", "-");
		map.put("￣", "~");
		map.put("！", "!");
		map.put("｀", "`");
		int length = line.length();

		for (int i = 0; i < length; i++) {
			// 每次截取一个字符进行判断
			String charat = line.substring(i, i + 1);
			if (map.get(charat) != null) {
				line = line.replace(charat, (String) map.get(charat));
			}
		}
		// 返回转换后的字符行
		return line;
	}

	/**
	 * 选择搜索
	 * 
	 * @param searchFieldsString
	 * @param xc
	 * @return
	 */
	public static String createMultiFieldQueryParser(String searchFieldsString) {
		String searchField = "";
		SearchContext sc = new SearchContext();
		LksConfig xc = sc.getLc();
		String[] searchFieldArray = searchFieldsString.split(";");
		List<String> searchFieldList = Arrays.asList(searchFieldArray);
		boolean isAllType = searchFieldList.size() == 0
				|| searchFieldList.size() == 5;
		isAllType = false;
		List<String> searchFields = new ArrayList<String>();
		List diaplayFields = xc.getDisplayFields();
		for (Iterator it = diaplayFields.iterator(); it.hasNext();) {
			LksField field = (LksField) it.next();
			if ((isAllType && field.getFieldType() == null)
					|| (field.getFieldType() != null
							&& "content".equals(field.getFieldType()) && searchFieldList
							.contains("content"))
					|| (field.getFieldType() != null
							&& "keyword".equals(field.getFieldType()) && searchFieldList
							.contains("tag"))
					|| (field.getFieldType() != null
							&& "attachment".equals(field.getFieldType()) && searchFieldList
							.contains("attachment"))
					|| (field.getFieldType() != null
							&& "title".equals(field.getFieldType()) && searchFieldList
							.contains("title"))
					|| (field.getFieldType() != null
							&& "creator".equals(field.getFieldType()) && searchFieldList
							.contains("creator"))) {
                if (field.getRole() == null || field.getRole().length() == 0
                        || UserUtil.getKMSSUser().isAdmin()) {
                    searchField += field.getName()+";";
                } else {
                    String[] roles = field.getRole().split(",");
                    boolean isrole = UserUtil.checkRoles(Arrays.asList(roles));
                    if (isrole) {
                        searchField += field.getName()+";";
                    }
                }
            }
		}
		if(StringUtil.isNotNull(searchField)){
			searchField = searchField.substring(0,searchField.length()-1);
		}
		return searchField;
	}

}
