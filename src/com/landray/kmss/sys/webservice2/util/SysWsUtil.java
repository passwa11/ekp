package com.landray.kmss.sys.webservice2.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSubTableProperty;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.webservice2.interfaces.ISysWsOrgService;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 辅助工具类
 * 
 * @author Jeff
 */
public class SysWsUtil {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysWsUtil.class);

	/**
	 * 简单的时间运算
	 * 
	 * @param calField
	 *            日历字段
	 * @param num
	 *            时间差值
	 * @return 时间结果
	 */
	public static Date getTime(int calField, int num) {
		Calendar cal = Calendar.getInstance();
		cal.add(calField, num);

		return cal.getTime();
	}

	/**
	 * 获取当前时间的下一天的零点
	 * 
	 * @return
	 */
	public static Date getNextDayStart() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	/**
	 * 从客户端访问路径中获取服务标识
	 */
	public static String getServiceBeanFromUrl(String url) {
		int index = url.lastIndexOf('/');

		return url.substring(index + 1);
	}

	/**
	 * 校验IP是否存在于指定的字符串中
	 */
	public static boolean isExistedIp(String ip, String allIpStr) {
		if (StringUtil.isNull(ip) || StringUtil.isNull(allIpStr)) {
			return true;
		}

		String[] arr = ip.split("\\.");
		StringBuilder sb = new StringBuilder();
		sb.append(arr[0]).append(".").append(arr[1]).append(".").append(arr[2])
				.append(".*");
		String p1 = sb.toString();

		sb = new StringBuilder();
		sb.append(arr[0]).append(".").append(arr[1]).append(".*.*");
		String p2 = sb.toString();

		if (allIpStr.contains(ip) || allIpStr.contains(p1)
				|| allIpStr.contains(p2)) {
			return true;
		}

		return false;
	}

	public static Map<String, Object> json2map(String jsonObjStr) {

		return json2map(jsonObjStr, null);
	}

	/**
	 * 把json字串转换成map对象 modify #4344 增加对组织架构类型json参数的支持 #曹映辉 2014.8.22
	 */
	public static Map<String, Object> json2map(String jsonObjStr,
			SysDictModel dict) {

		JSONObject jsonObject = JSONObject.fromObject(jsonObjStr);
		Map<String, Object> newMap = new HashMap<String, Object>();

		Set<Map.Entry<String, Object>> entrySet = jsonObject.entrySet();
		for (Map.Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();

			parseJsonValue(key, value, newMap, dict);
		}

		return newMap;
	}

	// modify #4344 增加对组织架构类型json参数的支持 #曹映辉 2014.8.22
	private static void parseJsonValue(String priKey, Object priValue,
			Map<String, Object> newMap, SysDictModel dict) {

		if (priValue instanceof String) {
			parseJsonStr(priKey, priValue, newMap);
		} else if(priValue instanceof Integer|| priValue instanceof Long) {
			parseJsonInt(priKey, priValue, newMap);
		}else if(priValue instanceof Double) {
			parseJsonBigDecimal(priKey, priValue, newMap);
		}else if (priValue instanceof JSONObject) {
			// #4344 增加对组织架构类型json参数的支持 #曹映辉 2014.8.22
			if (dict != null) {
				// SysDictCommonProperty prop =
				// dict.getPropertyMap().get(priKey);

				// #44170 流程启动接口，自定义表单明细表地址本不支持LoginName/PersonNo等传值
				SysDictCommonProperty prop = null;
				if (priKey.contains(".")) {
					SysDictCommonProperty commonProperty = dict.getPropertyMap().get(priKey.substring(0, priKey.indexOf(".")));
					if (commonProperty instanceof SysDictExtendSubTableProperty) {
						SysDictExtendSubTableProperty subTableProperty = (SysDictExtendSubTableProperty) commonProperty;
						prop = subTableProperty.getElementDictExtendModel().getPropertyMap().get(priKey.substring(priKey.indexOf(".") + 1));
					}
				} else {
					prop = dict.getPropertyMap().get(priKey);
				}

				if (SysOrgElement.class.getName().equals(prop.getType())
						||SysOrgPerson.class.getName().equals(prop.getType())) {
					ISysWsOrgService sysWsOrgService = (ISysWsOrgService) SpringBeanUtil
							.getBean("sysWsOrgService");
					try {
						SysOrgElement ele = sysWsOrgService
								.findSysOrgElement(priValue.toString());

						parseJsonStr(priKey, ele.getFdId(), newMap);
						return;
					} catch (Exception e) {
						logger.error("根据组织架构json " + priValue.toString()
								+ " 无法解析组织架构对象", e);
					}

				}
			}
			JSONObject jsonObj = (JSONObject) priValue;

			Set<Map.Entry<String, Object>> entrySet = jsonObj.entrySet();
			for (Map.Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				parseJsonValue(key, value, newMap, dict);
			}
		} else if (priValue instanceof JSONArray) {
			JSONArray jsonArr = (JSONArray) priValue;

			for (Object value : jsonArr) {
				parseJsonValue(priKey, value, newMap, dict);
			}
		} else {
			newMap.put(priKey, priValue); 
		}
	}

	private static void parseJsonStr(String priKey, Object priValue,
			Map<String, Object> newMap) {
		Date date = str2date((String) priValue);

		if (date != null) {
			priValue = date;
		}

		if (newMap.containsKey(priKey)) {
			Object value = newMap.get(priKey);

			if (value == null) {
				newMap.put(priKey, priValue);
			} else if (value instanceof List) {
				List valueList = (List) value;
				valueList.add(priValue);
				newMap.put(priKey, valueList);
			} else if (value instanceof String || value instanceof Date) {
				List valueList = new ArrayList();
				valueList.add(value);
				valueList.add(priValue);
				newMap.put(priKey, valueList);
			}
		} else {
			newMap.put(priKey, priValue);
		}
	}
	
	private static void parseJsonInt(String priKey, Object priValue,
			Map<String, Object> newMap) {

		if (newMap.containsKey(priKey)) {
			Object value = newMap.get(priKey);

			if (value == null) {
				newMap.put(priKey, priValue);
			} else if (value instanceof List) {
				List valueList = (List) value;
				valueList.add(priValue);
				newMap.put(priKey, valueList);
			} else if (value instanceof Integer||value instanceof Long) {
				List valueList = new ArrayList();
				valueList.add(value);
				valueList.add(priValue);
				newMap.put(priKey, valueList);
			}
		} else {
			newMap.put(priKey, priValue);
		}
	}
	
	private static void parseJsonBigDecimal(String priKey, Object priValue,
			Map<String, Object> newMap) {

		if (newMap.containsKey(priKey)) {
			Object value = newMap.get(priKey);

			if (value == null) {
				newMap.put(priKey, priValue);
			} else if (value instanceof List) {
				List valueList = (List) value;
				valueList.add(priValue);
				newMap.put(priKey, valueList);
			} else if (value instanceof Double) {
				List valueList = new ArrayList();
				valueList.add(value);
				valueList.add(priValue);
				newMap.put(priKey, valueList);
			}
		} else {
			newMap.put(priKey, priValue);
		}
	}
	

	/**
	 * 将指定格式的日期字串转换为日期对象
	 * 
	 * @param str
	 *            字符串
	 * @return 非日期类型字串返回空
	 */
	private static Date str2date(String str) {
		DateFormat df;
		Date date;

		// 初始化DateFormat
		if (StringUtil.isNull(str)) {
			return null;
		} else if (str.indexOf('-') >= 0) {
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (str.indexOf('/') >= 0) {
			df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else {
			return null;
		}

		// 解析时间字符串
		try {
			date = df.parse(str);
		} catch (ParseException e) {
			return null; // 非日期类型字串返回空
		}

		return date;
	}

	/**
	 * 获取URL的上下文路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getUrlPrefix(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String dns = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() != 80) {
            dns += ":" + request.getServerPort();
        }
		if (StringUtil.isNotNull(contextPath)) {
            return dns + contextPath;
        }
		return dns;
	}

	/**
	 * 压缩文件或目录
	 * 
	 * @param zipFileName
	 *            压缩文件名
	 * @param inputFile
	 *            源文件及目录
	 */
	public static void zip(String zipFileName, File inputFile) {
		try {
			org.apache.tools.zip.ZipOutputStream out = new org.apache.tools.zip.ZipOutputStream(
					new FileOutputStream(zipFileName));
			out.setEncoding("gbk");
			zip(out, inputFile, inputFile.getName());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 压缩文件或目录基础方法
	 * 
	 * @param out
	 *            输出流
	 * @param f
	 *            文件及目录
	 * @param base
	 *            基础路径，压缩路径的根路径
	 */
	public static void zip(org.apache.tools.zip.ZipOutputStream out, File f,
			String base) throws IOException, FileNotFoundException {
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			InputStream in = new BufferedInputStream(new FileInputStream(f));

			byte[] buffer = new byte[8192];
			int bytes_read;
			while ((bytes_read = in.read(buffer)) != -1) {
				out.write(buffer, 0, bytes_read);
			}

			in.close();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void downloadFile(HttpServletResponse response,
			String fileName) throws Exception {

		int pos = fileName.lastIndexOf(File.separator) + 1;
		String displayName = fileName.substring(pos);
		displayName = java.net.URLEncoder.encode(displayName, "UTF-8");

		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ displayName + "\"");
		// 以流的形式下载文件
		InputStream input = new BufferedInputStream(new FileInputStream(
				fileName));
		OutputStream output = new BufferedOutputStream(
				response.getOutputStream());
		int bytesRead = 0;
		byte[] buffer = new byte[1024];

		while ((bytesRead = input.read(buffer, 0, 1024)) != -1) {
			output.write(buffer, 0, bytesRead);
		}

		input.close();
		output.close();
	}

	/**
	 * 获取节点的文本内容
	 * 
	 * @param root
	 * @param nodeName
	 * @return
	 */
	public static String getTextContent(Element root, String nodeName) {
		String textContent = null;

		if (root != null) {
			NodeList nodes = root.getElementsByTagName(nodeName);

			if (nodes != null && nodes.getLength() > 0) {
				// textContent = nodes.item(0).getTextContent();
				Node n1 = nodes.item(0);
				if (n1 != null) {
					Node n2 = n1.getFirstChild();
					if (n2 != null && n2.getNodeType() == Node.TEXT_NODE) {
						textContent = n2.getNodeValue();
					}
				}
			}
		}

		return textContent;
	}

	/**
	 * 动态执行spring bean对象的方法
	 * 
	 * @param springBeanId
	 * @param methodName
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(String springBeanId, String methodName,
			Object[] args) throws Exception {
		Object service = SpringBeanUtil.getBean(springBeanId);
		Class serviceClass = service.getClass();
		Class[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		Method method = serviceClass.getMethod(methodName, argsClass);

		return method.invoke(service, args);
	}

	/**
	 * 使用消息摘要加密口令
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encryptPwd(String password) throws Exception {
		String cipherText = null;

		if (StringUtil.isNotNull(password)) {
			byte[] pBytes = password.getBytes("UTF8");
			cipherText = MD5Util.getMD5String(pBytes);
		}

		return cipherText;
	}
}
