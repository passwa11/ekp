package com.landray.kmss.sys.simplecategory.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 简单分类工具类
 *
 * @author 潘永辉
 * @date 2022/4/24 7:22 下午
 */
public class SysSimpleCategoryUtil {

	/**
	 * 构建分类树信息
	 *
	 * @param list 传入所有平级分类信息
	 * @return 返回包含层级结构的树形数据
	 */
	public static JSONArray buildCategoryTree(List<Object[]> list) {
		return buildCategoryTree(list, null, null, false);
	}

	/**
	 * 构建分类树信息
	 *
	 * @param list      传入所有平级分类信息
	 * @param baseUrl   基本路径
	 * @param autoFetch
	 * @param isCloud   是否MK调用
	 * @return 返回包含层级结构的树形数据
	 */
	public static JSONArray buildCategoryTree(List<Object[]> list, String baseUrl, Boolean autoFetch, boolean isCloud) {
		// 构建数据
		JSONArray array = new JSONArray();
		for (Object[] cate : list) {
			JSONObject row = new JSONObject();
			row.put("text", cate[0].toString());
			row.put("value", cate[1]); // fdId
			row.put("autoFetch", autoFetch);
			row.put("hierarchy", cate[2]); // 层级
			if (isCloud) {
				if (StringUtil.isNotNull(baseUrl)) {
					row.put("href", baseUrl + "#j_path=%2FdocCategory&docCategory=" + cate[1]);
				}
				row.put("target", "_blank");
			}
			row.put("parent", cate[4]); // 上级
			row.put("children", new JSONArray());
			array.add(row);
		}
		// 构建层级菜单
		return buildCategoryTree(array);
	}

	/**
	 * 构建分类树信息
	 *
	 * @param array 传入所有平级分类信息
	 * @return 返回包含层级结构的树形数据
	 */
	public static JSONArray buildCategoryTree(JSONArray array) {
		// 准备临时数据
		JSONObject temp = new JSONObject();
		Set<String> hierarchyIds = new HashSet<>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			temp.put(object.getString("value"), object);
			hierarchyIds.add(object.getString("hierarchy"));
		}
		// 构建层级关系
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			if (object.containsKey("parent")) {
				String pid = object.getString("parent");
				if (StringUtil.isNotNull(pid)) {
					JSONObject parent = temp.getJSONObject(pid);
					if (parent != null) {
						parent.getJSONArray("children").add(temp.getJSONObject(object.getString("value")));
					}
				}
			}
		}
		// 获取顶层菜单
		JSONArray data = new JSONArray();
		hierarchyIds = filterSubHierarchy(hierarchyIds);
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.getJSONObject(i);
			if (hierarchyIds.contains(object.getString("hierarchy"))) {
				data.add(object);
			}
		}
		return data;
	}

	/**
	 * 根据层级ID过滤子组织
	 *
	 * @param hids
	 * @return
	 */
	private static Set<String> filterSubHierarchy(Set<String> hids) {
		if (CollectionUtils.isEmpty(hids)) {
			return Collections.EMPTY_SET;
		}
		Set<String> resultTemp = new HashSet<String>();
		Set<String> addTemp = new HashSet<String>();
		Set<String> delTemp = new HashSet<String>();
		for (String hid1 : hids) {
			boolean add = true;
			for (String hid2 : resultTemp) {
				if (hid2.startsWith(hid1)) {
					add = false;
					delTemp.add(hid2);
					addTemp.add(hid1);
					continue;
				} else if (hid1.startsWith(hid2)) {
					add = false;
					break;
				}
			}
			if (add) {
				resultTemp.add(hid1);
			} else {
				resultTemp.removeAll(delTemp);
				resultTemp.addAll(addTemp);
				delTemp.clear();
				addTemp.clear();
			}
		}
		return resultTemp;
	}

}
