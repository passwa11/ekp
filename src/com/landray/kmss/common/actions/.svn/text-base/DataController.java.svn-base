package com.landray.kmss.common.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.sys.category.model.SysCategoryMain;
import com.landray.kmss.sys.category.service.ISysCategoryMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.RestResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/data/sys-common")
public class DataController {
	private ISysCategoryMainService sysCategoryMainService;

	protected ISysCategoryMainService getSysCategoryMainService() {
		if (sysCategoryMainService == null) {
			sysCategoryMainService = (ISysCategoryMainService) SpringBeanUtil
					.getBean("sysCategoryMainService");
		}
		return sysCategoryMainService;
	}

	@RequestMapping(value = "datajson", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<JSONArray> datajson(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s_bean = request.getParameter("s_bean");
		JSONArray array = new JSONArray();
		JSONArray jsonArray = null;
		try {
			Assert.notNull(s_bean, "参数s_bean不能为空！");
			RequestContext requestInfo = new RequestContext(request, true);
			String[] beanList = s_bean.split(";");
			IXMLDataBean treeBean;
			List result = null;
			HashMap nodeMap;
			Object node;
			Object[] nodeList;
			Iterator attr;
			for (int i = 0; i < beanList.length; i++) {
				treeBean = (IXMLDataBean) SpringBeanUtil.getBean(beanList[i]);
				result = treeBean.getDataList(requestInfo);
				if (result != null) {
					jsonArray = new JSONArray();
					for (Iterator iterator = result.iterator(); iterator
							.hasNext();) {
						node = iterator.next();
						if (node instanceof HashMap) {
							Map<String, Object> parseObj = (Map<String, Object>) node;
							JSONObject json = new JSONObject();
							for (String key1 : parseObj.keySet()) {
								Object value1 = parseObj.get(key1);
								// 添加为null的对象，jackson解析时会报错
								if (value1 != null) {
                                    json.accumulate(key1, value1);
                                }
							}
						jsonArray.add(json);
						} else if (node instanceof Object[]) {
							nodeList = (Object[]) node;
							JSONObject json = new JSONObject();
							for (int k = 0; k < nodeList.length; k++) {
								if (nodeList[k] != null) {
									String key2 = "key" + k;
									Object value2 = nodeList[k];
									// 添加为null的对象，jackson解析时会报错
									if (value2 != null) {
                                        json.accumulate(key2, value2);
                                    }
								}
							}
							jsonArray.add(json);
						} else {
							if (node != null) {
								JSONObject json = new JSONObject();
								String key3 = "key0";
								Object value3 = node;
								json.accumulate(key3, value3);
								jsonArray.add(json);
							}
						}
					}
					array.add(jsonArray);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		// 避免出现数组里面包数组的情况
		if (array.size() == 1) {
			return RestResponse.ok(jsonArray);
		}
		return RestResponse.ok(array);
	}

	@RequestMapping(value = "dataxml", produces = {
			KmssMediaTypes.APPLICATION_XML_UTF8 })
	@ResponseBody
	public RestResponse<JSONArray> dataxml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s_bean = request.getParameter("s_bean");
		JSONArray sarray = new JSONArray();
		try {
			Assert.notNull(s_bean, "参数s_bean不能为空！");
			List nodes = null;
			RequestContext requestInfo = new RequestContext(request, true);
			String[] beanList = s_bean.split(";");
			IXMLDataBean treeBean;
			HashMap nodeMap;
			Object node, value;
			Object[] nodeList;
			Iterator attr;
			String key;
			int i, j, k;
			for (i = 0; i < beanList.length; i++) {
				treeBean = (IXMLDataBean) SpringBeanUtil.getBean(beanList[i]);
				nodes = treeBean.getDataList(requestInfo);
				if (nodes != null) {
					for (j = 0; j < nodes.size(); j++) {
						node = nodes.get(j);
						JSONObject json = new JSONObject();
						if (node instanceof HashMap) {
							nodeMap = (HashMap) node;
							for (attr = nodeMap.keySet().iterator(); attr
									.hasNext();) {
								key = attr.next().toString();
								value = nodeMap.get(key);
								if (value == null) {
                                    continue;
                                }
								json.put(key, value);
							}
						} else if (node instanceof Object[]) {
							nodeList = (Object[]) node;
							for (k = 0; k < nodeList.length; k++) {
                                if (nodeList[k] != null) {
                                    json.put("key" + k, nodeList[k]);
                                }
                            }
						} else {
							if (node != null) {
								json.put("key0", node);
							}
						}
						sarray.add(json);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(sarray);
	}

	@RequestMapping(value = "treexml", produces = {
			KmssMediaTypes.APPLICATION_XML_UTF8 })
	@ResponseBody
	public RestResponse<JSONArray> treexml(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String s_bean = request.getParameter("s_bean");
		JSONArray sarray = new JSONArray();
		try {
			Assert.notNull(s_bean, "参数s_bean不能为空！");
			List nodes = null;
			RequestContext requestInfo = new RequestContext(request, true);
			String[] beanList = s_bean.split(";");
			IXMLDataBean treeBean;
			HashMap nodeMap;
			Object node, value;
			Object[] nodeList;
			String[] keyList = new String[] { "text", "value", "title",
					"nodeType", "beanName", "isAutoFetch", "href", "target",
					"winStyle" };
			Iterator attr;
			String key;
			int i, j, k;
			for (i = 0; i < beanList.length; i++) {
				treeBean = (IXMLDataBean) SpringBeanUtil.getBean(beanList[i]);
				nodes = treeBean.getDataList(requestInfo);
				if (nodes != null) {
					for (j = 0; j < nodes.size(); j++) {
						node = nodes.get(j);
						JSONObject data = new JSONObject();
						if (node instanceof HashMap) {
							nodeMap = (HashMap) node;
							for (attr = nodeMap.keySet().iterator(); attr
									.hasNext();) {
								key = attr.next().toString();
								value = nodeMap.get(key);
								if (value == null) {
                                    continue;
                                }
								data.put(key, value);
							}
						} else {
							nodeList = (Object[]) node;
							for (k = 0; k < nodeList.length
									&& k < keyList.length; k++) {
                                if (nodeList[k] != null
                                        && !"".equals(nodeList[k].toString())) {
                                    data.put(keyList[k], nodeList[k]);
                                }
                            }
						}
						sarray.add(data);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(sarray);
	}

	/**
	 * MK Paas查数据源配置项详情接口，全局分类的情况
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "categoryDetails", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<JSONArray> categoryDetails(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray array = new JSONArray();
		try {
			String[] fdIds = request.getParameterValues("fdId");
			String modelName = request.getParameter("model");
			Assert.notNull(fdIds, "参数fdId不能为空！");
			Assert.notNull(modelName, "参数modelName不能为空！");
			SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
			if (dict != null) {
				JSONObject obj = null;
				for (String fdId : fdIds) {
					boolean isCategory = getSysCategoryMainService()
							.getBaseDao()
							.isExist(getSysCategoryMainService().getModelName(),
									fdId);
					// 是分类
					if (isCategory) {
						obj = new JSONObject();
						SysCategoryMain category = (SysCategoryMain) getSysCategoryMainService()
								.findByPrimaryKey(fdId);
						obj.put("fdId", fdId);
						String nameKey = StringUtil
								.isNotNull(dict.getDisplayProperty())
										? dict.getDisplayProperty() : "fdName";
						obj.put(nameKey, category.getFdName());
						array.add(obj);
					} else {
						// 是模板
						obj = commonGetDetails(fdId, modelName);
						if (obj != null) {
							array.add(obj);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(array);
	}

	private JSONObject commonGetDetails(String fdId, String modelName)
			throws Exception {
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		if (dict != null) {
			JSONObject obj = new JSONObject();
			IBaseService service = (IBaseService) SpringBeanUtil
					.getBean(dict.getServiceBean());
			IBaseModel model = service.findByPrimaryKey(fdId);
			if (model != null) {
				obj.put("fdId", fdId);
				String nameKey = StringUtil
						.isNotNull(dict.getDisplayProperty())
								? dict.getDisplayProperty() : "fdName";
				obj.put(nameKey,
						getName(model, dict.getDisplayProperty()));
				return obj;
			}
		}
		return null;
	}

	/**
	 * MK Paas查数据源配置项详情接口，一般情况
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "details", produces = {
			KmssMediaTypes.APPLICATION_JSON_UTF8 })
	@ResponseBody
	public RestResponse<JSONArray> details(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray array = new JSONArray();
		try {
			String[] fdIds = request.getParameterValues("fdId");
			String modelName = request.getParameter("model");
			Assert.notNull(fdIds, "参数fdId不能为空！");
			Assert.notNull(modelName, "参数modelName不能为空！");
			JSONObject obj = null;
			for (String fdId : fdIds) {
				obj = commonGetDetails(fdId, modelName);
				if (obj != null) {
					array.add(obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RestResponse.error(RestResponse.ERROR_CODE_500,
					e.getMessage());
		}
		return RestResponse.ok(array);
	}

	private String getName(IBaseModel model, String displayProp) {
		String fdName = "fdName";
		String fdSubject = "docSubject";
		String name = null;
		if (StringUtil.isNotNull(displayProp)) {
			if (PropertyUtils.isReadable(model, displayProp)) {
				try {
					name = (String) PropertyUtils.getProperty(model,
							displayProp);
					return name;
				} catch (Exception e) {
				}
			}
		}
		if (PropertyUtils.isReadable(model, fdName)) {
			try {
				name = (String) PropertyUtils.getProperty(model, fdName);
				return name;
			} catch (Exception e) {
			}
		}
		if (PropertyUtils.isReadable(model, fdSubject)) {
			try {
				name = (String) PropertyUtils.getProperty(model, fdSubject);
			} catch (Exception e) {
			}
		}
		return name;
	}
}
