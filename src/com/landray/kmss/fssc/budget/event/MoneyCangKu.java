package com.landray.kmss.fssc.budget.event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.xform.base.service.controls.relation.RelationParamsField;
import com.landray.kmss.sys.xform.base.service.controls.relation.SysFormRelationAdapta;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MoneyCangKu extends SysFormRelationAdapta {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MoneyCangKu.class);


	/**
	 * @param key和扩展点中sourceUUID匹配
	 * @searchs 事件控件搜索条件的值
	 * @ins 传入参数的值 RelationParamsField中fieldIdForm对应表单的ID
	 *      fieldValueForm对应该表单字段的值
	 * 
	 */
	@Override
	protected List<List<RelationParamsField>> getData(String key,
			List<RelationParamsField> searchs, List<RelationParamsField> ins) {

		for (RelationParamsField in : ins) {
			String name = in.getUuId();
			logger.info("入参key=>" + name);
			String value = in.getFieldValueForm();
			logger.info("入参值=>" + value);
		}

		return queryList();
	}

	@Override
	protected File getTemplateFile(String key) {
		return new File(ConfigLocationsUtil
				.getWebContentPath()
				+ "/fssc/budget/template/cangku.xml");
	}
	public List<List<RelationParamsField>> queryList() {
		List<List<RelationParamsField>> rows = new ArrayList<List<RelationParamsField>>();

		try {
			String jsonStr=MoneyDataServer.getCangKuData();
			JSONObject json=JSONObject.fromObject(jsonStr);
			JSONArray array=(JSONArray) json.get("data");
			//{"code":0,"data":[{"skt27.skf393":1,"skt27.skf394":"深圳仓储部","skt27.skf4520":"4747","skt27.skf398":""}]}
			for (Object jo : array) {
				JSONObject jobj = (JSONObject) jo;
				// ID
				String fdCangKuId = jobj.getString("skt27.skf393");
				// 名称
				String fdCangKuName = jobj.getString("skt27.skf394");

				List<RelationParamsField> columns = new ArrayList<RelationParamsField>();
				columns.add(
						new RelationParamsField("fdCangKuId", "",
								fdCangKuId));
				columns.add(
						new RelationParamsField("fdCangKuName", "",
								fdCangKuName));

				rows.add(columns);
			}
		} catch (Exception e) {
			logger.error("公式加载错误", e);
			return null;
		}
		return rows;
	}

}
