package com.landray.kmss.third.ding.provider;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.request.OapiProcessDeleteRequest;
import com.dingtalk.api.request.OapiProcessDeleteRequest.DeleteProcessRequest;
import com.dingtalk.api.request.OapiProcessSaveRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordCreateRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordCreateRequest.FormComponentValueVo;
import com.dingtalk.api.request.OapiProcessWorkrecordCreateRequest.SaveFakeProcessInstanceRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest.SaveTaskRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest.UpdateTaskRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordUpdateRequest;
import com.dingtalk.api.request.OapiProcessWorkrecordUpdateRequest.UpdateProcessInstanceRequest;
import com.dingtalk.api.response.OapiProcessDeleteResponse;
import com.dingtalk.api.response.OapiProcessSaveResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordCreateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskCreateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordTaskUpdateResponse;
import com.dingtalk.api.response.OapiProcessWorkrecordUpdateResponse;
import com.google.api.client.util.Lists;
import com.landray.kmss.sys.notify.model.SysNotifyTodo;
import com.landray.kmss.third.ding.constant.DingConstant;
import com.landray.kmss.third.ding.model.DingConfig;
import com.landray.kmss.third.ding.model.ThirdDingDtemplate;
import com.landray.kmss.third.ding.model.ThirdDingDtemplateXform;
import com.landray.kmss.third.ding.model.ThirdDingTemplateDetail;
import com.landray.kmss.third.ding.model.ThirdDingTemplateXDetail;
import com.landray.kmss.third.ding.service.IThirdDingCategoryXformService;
import com.landray.kmss.third.ding.util.DingHttpClientUtil;
import com.landray.kmss.third.ding.util.DingUtil;
import com.landray.kmss.third.ding.util.DingUtils;
import com.landray.kmss.third.ding.util.ThirdDingTalkClient;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.taobao.api.ApiException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class DingNotifyUtil {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DingNotifyUtil.class);

	private IThirdDingCategoryXformService thirdDingCategoryXformService;

	public IThirdDingCategoryXformService getThirdDingCategoryXformService() {
		if (thirdDingCategoryXformService == null) {
			thirdDingCategoryXformService = (IThirdDingCategoryXformService) SpringBeanUtil
					.getBean("thirdDingCategoryXformService");
		}
		return thirdDingCategoryXformService;
	}

	public void setThirdDingCategoryXformService(
			IThirdDingCategoryXformService thirdDingCategoryXformService) {
		this.thirdDingCategoryXformService = thirdDingCategoryXformService;
	}

	public static String getNameByLang(String name, String fdLang) {
		Locale locale = null;
		if (StringUtil.isNotNull(fdLang)
				&& fdLang.contains("-")) {
			locale = new Locale(fdLang.split("-")[0],
					fdLang.split("-")[1]);
		}
		if ("标题".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.fdSubject", "sys-notify",
					locale);
		} else if ("创建者".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.docCreator", "sys-notify",
					locale);
		} else if ("创建时间".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.fdCreateTime", "sys-notify",
					locale);
		}
		return name;
	}

	private static String getId(){
		String uid = UUID.randomUUID().toString();
		uid = uid.substring(uid.length()-8, uid.length());
		return uid;
	}
	
	//创建模板,返回模板code
	public static OapiProcessSaveResponse createTemplate(String access_token,
			Long agentId, String name, String description, Boolean fakeMode,
			String pcode, String fdLang) throws ApiException {
		String url = DingConstant.DING_PREFIX + "/topapi/process/save"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);

        OapiProcessSaveRequest request = new OapiProcessSaveRequest();
        OapiProcessSaveRequest.SaveProcessRequest saveProcessRequest = new OapiProcessSaveRequest.SaveProcessRequest();
        //审批模板名称
        saveProcessRequest.setName(name);
        //审批模板描述
        saveProcessRequest.setDescription(description);
        //企业微应用标识(企业方式调用，agentid都不用传)
        saveProcessRequest.setAgentid(agentId);//企业方式调用，agentid都不用传
        //审批模板的唯一码。在本接口中，如不传，表示新建一个模板。如传值了，表示更新所传值对应的审批模板
        if(StringUtil.isNotNull(pcode)) {
            saveProcessRequest.setProcessCode(pcode);
        }
        //是否允许表单在审批管理后台可编辑。true表示不带流程
        saveProcessRequest.setDisableFormEdit(true);
        //如果需要创建不带流程的模板，该参数传true
        saveProcessRequest.setFakeMode(fakeMode);
        // 注意，每种表单组件，对应的componentName是固定的，参照一下示例代码
        List<OapiProcessSaveRequest.FormComponentVo> formComponentList = Lists.newArrayList();

        // 单行文本框
        OapiProcessSaveRequest.FormComponentVo title = new OapiProcessSaveRequest.FormComponentVo();
        title.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo titleDetail = new OapiProcessSaveRequest.FormComponentPropVo();
		titleDetail.setLabel(DingNotifyUtil.getNameByLang("标题", fdLang));
        titleDetail.setId("TextField-"+getId());
        titleDetail.setPlaceholder("请输入");
        titleDetail.setRequired(false);
        title.setProps(titleDetail);
        formComponentList.add(title);
        
        OapiProcessSaveRequest.FormComponentVo creator = new OapiProcessSaveRequest.FormComponentVo();
        creator.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo creatorDetail = new OapiProcessSaveRequest.FormComponentPropVo();
		creatorDetail.setLabel(DingNotifyUtil.getNameByLang("创建者", fdLang));
        creatorDetail.setId("TextField-"+getId());
        creatorDetail.setPlaceholder("请输入");
        creatorDetail.setRequired(false);
        creator.setProps(creatorDetail);
        formComponentList.add(creator);
        
        OapiProcessSaveRequest.FormComponentVo createTime = new OapiProcessSaveRequest.FormComponentVo();
        createTime.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo createTimeDetail = new OapiProcessSaveRequest.FormComponentPropVo();
		createTimeDetail.setLabel(DingNotifyUtil.getNameByLang("创建时间", fdLang));
        createTimeDetail.setId("TextField-"+getId());
        createTimeDetail.setPlaceholder("请输入");
        createTimeDetail.setRequired(false);
        createTime.setProps(createTimeDetail);
        formComponentList.add(createTime);
       
        // 多行文本框
       /* OapiProcessSaveRequest.FormComponentVo multipleInput = new OapiProcessSaveRequest.FormComponentVo();
        multipleInput.setComponentName("TextareaField");
        OapiProcessSaveRequest.FormComponentPropVo multipleInputProp = new OapiProcessSaveRequest.FormComponentPropVo();
        multipleInputProp.setRequired(false);
        multipleInputProp.setLabel("多行输入框");
        multipleInputProp.setPlaceholder("请输入");
        multipleInputProp.setId("TextareaFieldId");
        multipleInput.setProps(multipleInputProp);
        formComponentList.add(multipleInput);*/

        // 金额组件
       /* OapiProcessSaveRequest.FormComponentVo moneyComponent = new OapiProcessSaveRequest.FormComponentVo();
        moneyComponent.setComponentName("MoneyField");
        OapiProcessSaveRequest.FormComponentPropVo moneyComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        moneyComponentProp.setRequired(false);
        moneyComponentProp.setLabel("金额（元）大写");
        moneyComponentProp.setPlaceholder("请输入");
        moneyComponentProp.setId("MoneyFieldId");
        moneyComponentProp.setNotUpper("1"); // 是否禁用大写
        moneyComponent.setProps(moneyComponentProp);
        formComponentList.add(moneyComponent);*/

       // 数字输入框
       /* OapiProcessSaveRequest.FormComponentVo numberComponent = new OapiProcessSaveRequest.FormComponentVo();
        numberComponent.setComponentName("NumberField");
        OapiProcessSaveRequest.FormComponentPropVo numberComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        numberComponentProp.setRequired(true);
        numberComponentProp.setLabel("数字输入框带单位");
        numberComponentProp.setPlaceholder("请输入");
        numberComponentProp.setId("NumberFieldId");
        numberComponentProp.setUnit("元");
        numberComponent.setProps(numberComponentProp);
        formComponentList.add(numberComponent);*/

        // 计算公式
        /*OapiProcessSaveRequest.FormComponentVo calculateComponent = new OapiProcessSaveRequest.FormComponentVo();
        calculateComponent.setComponentName("CalculateField");
        OapiProcessSaveRequest.FormComponentPropVo calculateComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        calculateComponentProp.setRequired(true);
        calculateComponentProp.setLabel("计算公式");
        calculateComponentProp.setPlaceholder("自动计算数值");
        calculateComponentProp.setId("CalculateFieldId");
        calculateComponent.setProps(calculateComponentProp);
        formComponentList.add(calculateComponent);*/

        // 单选框
        /*OapiProcessSaveRequest.FormComponentVo selectComponent = new OapiProcessSaveRequest.FormComponentVo();
        selectComponent.setComponentName("DDSelectField");
        OapiProcessSaveRequest.FormComponentPropVo selectComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        selectComponentProp.setRequired(false);
        selectComponentProp.setLabel("单选框");
        selectComponentProp.setPlaceholder("请输入");
        selectComponentProp.setId("DDSelectFieldId");
        selectComponentProp.setOptions(Arrays.asList("a", "b", "c")); // 选项最多200项，每项最多50个字
        selectComponent.setProps(selectComponentProp);
        formComponentList.add(selectComponent);*/

        // 多选框
        /*OapiProcessSaveRequest.FormComponentVo multiSelectComponent = new OapiProcessSaveRequest.FormComponentVo();
        multiSelectComponent.setComponentName("DDMultiSelectField");
        OapiProcessSaveRequest.FormComponentPropVo multiSelectComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        multiSelectComponentProp.setRequired(false);
        multiSelectComponentProp.setLabel("多选框");
        multiSelectComponentProp.setPlaceholder("请输入");
        multiSelectComponentProp.setId("DDMultiSelectFieldId");
        multiSelectComponentProp.setOptions(Arrays.asList("a", "b", "c"));
        multiSelectComponent.setProps(multiSelectComponentProp);
        formComponentList.add(multiSelectComponent);*/

        // 日期
        /*OapiProcessSaveRequest.FormComponentVo dateComponent = new OapiProcessSaveRequest.FormComponentVo();
        dateComponent.setComponentName("DDDateField");
        OapiProcessSaveRequest.FormComponentPropVo dateComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        dateComponentProp.setRequired(true);
        dateComponentProp.setLabel("日期");
        dateComponentProp.setPlaceholder("请选择");
        dateComponentProp.setUnit("小时"); // 小时或天
        dateComponentProp.setId("DDDateFieldId");
        dateComponent.setProps(dateComponentProp);
        formComponentList.add(dateComponent);*/

        // 日期区间
        /*OapiProcessSaveRequest.FormComponentVo dateRangeComponent = new OapiProcessSaveRequest.FormComponentVo();
        dateRangeComponent.setComponentName("DDDateRangeField");
        OapiProcessSaveRequest.FormComponentPropVo dateRangeComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        dateRangeComponentProp.setRequired(true);
        dateRangeComponentProp.setLabel(JSON.toJSONString(Arrays.asList("a", "b")));
        dateRangeComponentProp.setPlaceholder("请选择");
        dateRangeComponentProp.setUnit("小时"); // 小时或天
        dateRangeComponentProp.setId("DDDateRangeFieldId");
        dateRangeComponent.setProps(dateRangeComponentProp);
        formComponentList.add(dateRangeComponent);*/

        // 关联组件
        /*OapiProcessSaveRequest.FormComponentVo relateComponent = new OapiProcessSaveRequest.FormComponentVo();
        relateComponent.setComponentName("RelateField");
        OapiProcessSaveRequest.FormComponentPropVo relateComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        relateComponentProp.setRequired(true);
        relateComponentProp.setLabel("关联审批单");
        relateComponentProp.setPlaceholder("请选择");
        relateComponentProp.setId("RelateFieldId");
        relateComponentProp.setNotPrint("1");
        relateComponent.setProps(relateComponentProp);
        formComponentList.add(relateComponent);*/

        // 图片
       /* OapiProcessSaveRequest.FormComponentVo photoComponent = new OapiProcessSaveRequest.FormComponentVo();
        photoComponent.setComponentName("DDPhotoField");
        OapiProcessSaveRequest.FormComponentPropVo photoComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        photoComponentProp.setRequired(true);
        photoComponentProp.setLabel("图片");
        photoComponentProp.setId("DDPhotoFieldId");
        photoComponent.setProps(photoComponentProp);
        formComponentList.add(photoComponent);*/

        // 附件
       /* OapiProcessSaveRequest.FormComponentVo attachmentComponent = new OapiProcessSaveRequest.FormComponentVo();
        attachmentComponent.setComponentName("DDAttachment");
        OapiProcessSaveRequest.FormComponentPropVo attachmentComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        attachmentComponentProp.setRequired(true);
        attachmentComponentProp.setLabel("附件");
        attachmentComponentProp.setId("DDAttachmentId");
        attachmentComponent.setProps(attachmentComponentProp);
        formComponentList.add(attachmentComponent);*/

        // 内部联系人
       /* OapiProcessSaveRequest.FormComponentVo innerContactComponent = new OapiProcessSaveRequest.FormComponentVo();
        innerContactComponent.setComponentName("InnerContactField");
        OapiProcessSaveRequest.FormComponentPropVo innerContactComponentProp = new OapiProcessSaveRequest.FormComponentPropVo();
        innerContactComponentProp.setRequired(true);
        innerContactComponentProp.setLabel("联系人多选");
        innerContactComponentProp.setChoice(1L); // 是否支持多选 "1" or "0"
        innerContactComponentProp.setId("InnerContactFieldId");
        innerContactComponent.setProps(innerContactComponentProp);
        formComponentList.add(innerContactComponent);*/

        // 明细组件
        //OapiProcessSaveRequest.FormComponentVo formComponentVo = new OapiProcessSaveRequest.FormComponentVo();
        // 设置组件名称
        //formComponentVo.setComponentName("TableField");

        // 设置组件属性
        /*OapiProcessSaveRequest.FormComponentPropVo prop = new OapiProcessSaveRequest.FormComponentPropVo();
        prop.setActionName("增加明细");
        prop.setLabel("明细");
        prop.setId("TableFieldId");*/

        // 明细里需要计算的组件列表
       /* List<OapiProcessSaveRequest.FormComponentStatVo> statFieldList = Lists.newArrayList();
        OapiProcessSaveRequest.FormComponentStatVo statField = new OapiProcessSaveRequest.FormComponentStatVo();
        statField.setId("NumberFieldDetailId");
        statField.setUpper(false);
        statFieldList.add(statField);
        prop.setStatField(statFieldList);*/

        // 明细组件的子组件
        /*List<OapiProcessSaveRequest.FormComponentVo2> children = Lists.newArrayList();
        OapiProcessSaveRequest.FormComponentVo2 form1 = new OapiProcessSaveRequest.FormComponentVo2();
        form1.setComponentName("TextField");
        OapiProcessSaveRequest.FormComponentPropVo prop1 = new OapiProcessSaveRequest.FormComponentPropVo();
        prop1.setPlaceholder("请输入");
        prop1.setLabel("单行输入框");
        prop1.setId("TextFieldDetailId");
        form1.setProps(prop1);

        OapiProcessSaveRequest.FormComponentVo2 form2 = new OapiProcessSaveRequest.FormComponentVo2();
        form2.setComponentName("NumberField");
        OapiProcessSaveRequest.FormComponentPropVo prop2 = new OapiProcessSaveRequest.FormComponentPropVo();
        prop2.setPlaceholder("请输入数字");
        prop2.setLabel("数字输入框");
        prop2.setId("NumberFieldDetailId");
        form2.setProps(prop2);

        children.add(form1);
        children.add(form2);

        formComponentVo.setChildren(children);
        formComponentVo.setProps(prop);
        formComponentList.add(formComponentVo);*/

        saveProcessRequest.setFormComponentList(formComponentList);
        request.setSaveProcessRequest(saveProcessRequest);

        OapiProcessSaveResponse response = client.execute(request, access_token);
       return response;
	}
	
	// 流程高级审批 ：创建模板,返回模板code
	public static JSONObject createTemplateXform(
			String access_token,
			Long agentId, String name, String description, Boolean fakeMode,
			String pcode, String fdLang, List<String> titleList,
			JSONObject save_config)
			throws ApiException, UnsupportedEncodingException {
		logger.debug("-----------------流程高级审批:创建/更新流程模板------------------");

		String apiPre = "";
		if ("true".equals(DingConfig.newInstance().getAttendanceDebug())) {
			apiPre = "https://pre-oapi.dingtalk.com";
		} else {
			apiPre = DingConstant.DING_PREFIX;
		}
		String url = apiPre + "/topapi/process/save"
				+ "?access_token=" + access_token
				+ DingUtil.getDingAppKeyByEKPUserId("&", null);
		logger.debug("钉钉接口url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		JSONArray form_component_list = buildFormComponentList(titleList,
				save_config.getString("type"));

		JSONObject param = new JSONObject();
		if (StringUtil.isNotNull(pcode)) {
			param.put("process_code", pcode);
		}
		param.put("agentid", agentId);
		param.put("description", description);
		param.put("fake_mode", true);
		param.put("form_component_list", form_component_list);
		param.put("name", name);
		param.put("process_config", save_config.getJSONObject("save_config")
				.getJSONObject("process_config"));
		param.put("create_instance_pc_url",
				save_config.getJSONObject("save_config")
						.getString("create_instance_pc_url"));
		param.put("create_instance_mobile_url",
				save_config.getJSONObject("save_config")
						.getString("create_instance_mobile_url"));
		param.put("dir_id",
				save_config.getJSONObject("save_config").getString("dir_id"));
		if (save_config.containsKey("origin_dir_id")) {
			param.put("origin_dir_id", save_config.getString("origin_dir_id"));
		}

		param.put("icon",
				save_config.getJSONObject("save_config").getString("icon"));

		JSONObject request_param = new JSONObject();
		request_param.put("saveProcessRequest", param);

		return DingHttpClientUtil.httpPost(url, request_param, null,
				JSONObject.class);
	}

	private static JSONArray buildFormComponentList(List<String> titleList,String type) {
		JSONArray form_component_list = new JSONArray();
		logger.warn("-----------type:" + type);
		if ("attendance".equals(type)) {
			JSONObject attendance = new JSONObject();
			JSONArray children = new JSONArray();

			JSONObject type_component = new JSONObject();
			type_component.put("component_name", "DDSelectField");
			JSONObject prop = new JSONObject();
			prop.put("id", "DDSelectField-" + getId());
			prop.put("label", "请假类型");
			prop.put("biz_alias", "type");
			prop.put("options", "");
			prop.put("placeholder", "请选择");
			prop.put("required", true);
			type_component.put("props", prop);
			children.add(type_component);

			JSONObject startTime_component = new JSONObject();
			startTime_component.put("component_name", "DDDateField");
			JSONObject startTime_prop = new JSONObject();
			startTime_prop.put("id", "DDDateField-" + getId());
			startTime_prop.put("label", "开始时间");
			startTime_prop.put("placeholder", "请选择");
			startTime_prop.put("biz_alias", "startTime");
			startTime_prop.put("unit", "小时");
			startTime_prop.put("required", true);
			startTime_component.put("props", startTime_prop);
			children.add(startTime_component);

			JSONObject finishTime_component = new JSONObject();
			finishTime_component.put("component_name", "DDDateField");
			JSONObject finishTime_prop = new JSONObject();
			finishTime_prop.put("id", "DDDateField-" + getId());
			finishTime_prop.put("label", "结束时间");
			finishTime_prop.put("placeholder", "请选择");
			finishTime_prop.put("biz_alias", "finishTime");
			finishTime_prop.put("unit", "小时");
			finishTime_prop.put("required", true);
			finishTime_component.put("props", finishTime_prop);
			children.add(finishTime_component);

			JSONObject duration_component = new JSONObject();
			duration_component.put("component_name", "NumberField");
			JSONObject duration_prop = new JSONObject();
			duration_prop.put("id", "NumberField-" + getId());
			duration_prop.put("label", "时长");
			duration_prop.put("placeholder", "请输入时长");
			duration_prop.put("biz_alias", "duration");
			duration_prop.put("required", true);
			duration_component.put("props", duration_prop);
			children.add(duration_component);

			JSONObject reason_component = new JSONObject();
			reason_component.put("component_name", "TextField");
			JSONObject reason_prop = new JSONObject();
			reason_prop.put("id", "TextField-" + getId());
			reason_prop.put("label", "请假原因");
			reason_prop.put("placeholder", "请输入原因");
			reason_prop.put("biz_alias", "reason");
			reason_prop.put("required", false);
			reason_component.put("props", reason_prop);
			children.add(reason_component);

			attendance.put("children", children);
			attendance.put("component_name", "DDBizSuite");
			JSONObject props = new JSONObject();
			props.put("id", "DDBizSuite-" + getId());
			props.put("label", "");
			props.put("biz_type", "attendance.leave");
			props.put("biz_alias", "leave");
			attendance.put("props", props);
			form_component_list.add(attendance);
		}else if("batchLeave".equals(type)){
			//批量请假 ，调整一下请假套件的顺序
			JSONObject attendance = new JSONObject();
			JSONArray children = new JSONArray();

			JSONObject type_component = new JSONObject();
			type_component.put("component_name", "DDSelectField");
			JSONObject prop = new JSONObject();
			prop.put("id", "DDSelectField-" + getId());
			prop.put("label", "请假类型");
			prop.put("biz_alias", "type");
			prop.put("options", "");
			prop.put("placeholder", "请选择");
			prop.put("required", true);
			type_component.put("props", prop);
			children.add(type_component);

			JSONObject toalSize_component = new JSONObject();
			toalSize_component.put("component_name", "TextField");
			JSONObject total_prop = new JSONObject();
			total_prop.put("id", "TextField-" + getId());
			total_prop.put("label", "总时长");
			total_prop.put("placeholder", "总时长");
			total_prop.put("biz_alias", "totalDuration");
			total_prop.put("required", true);
			toalSize_component.put("props", total_prop);
			children.add(toalSize_component);

			JSONObject reason_component = new JSONObject();
			reason_component.put("component_name", "TextField");
			JSONObject reason_prop = new JSONObject();
			reason_prop.put("id", "TextField-" + getId());
			reason_prop.put("label", "请假原因");
			reason_prop.put("placeholder", "请输入原因");
			reason_prop.put("biz_alias", "reason");
			reason_prop.put("required", true);
			reason_component.put("props", reason_prop);
			children.add(reason_component);

			JSONObject duration_component = new JSONObject();
			duration_component.put("component_name", "NumberField");
			JSONObject duration_prop = new JSONObject();
			duration_prop.put("id", "NumberField-" + getId());
			duration_prop.put("label", "时长");
			duration_prop.put("placeholder", "请输入时长");
			duration_prop.put("biz_alias", "duration");
			duration_prop.put("required", true);
			duration_component.put("props", duration_prop);
			children.add(duration_component);


			JSONObject startTime_component = new JSONObject();
			startTime_component.put("component_name", "DDDateField");
			JSONObject startTime_prop = new JSONObject();
			startTime_prop.put("id", "DDDateField-" + getId());
			startTime_prop.put("label", "开始时间");
			startTime_prop.put("placeholder", "请选择");
			startTime_prop.put("biz_alias", "startTime");
			startTime_prop.put("required", true);
			startTime_prop.put("unit", "天");
			startTime_component.put("props", startTime_prop);
			children.add(startTime_component);

			JSONObject finishTime_component = new JSONObject();
			finishTime_component.put("component_name", "DDDateField");
			JSONObject finishTime_prop = new JSONObject();
			finishTime_prop.put("id", "DDDateField-" + getId());
			finishTime_prop.put("label", "结束时间");
			finishTime_prop.put("placeholder", "请选择");
			finishTime_prop.put("biz_alias", "finishTime");
			finishTime_prop.put("required", true);
			finishTime_prop.put("unit", "天");
			finishTime_component.put("props", finishTime_prop);
			children.add(finishTime_component);

			attendance.put("children", children);
			attendance.put("component_name", "DDBizSuite");
			JSONObject props = new JSONObject();
			props.put("id", "DDBizSuite-" + getId());
			props.put("label", "");
			props.put("biz_type", "attendance.leave");
			props.put("biz_alias", "leave");
			attendance.put("props", props);
			form_component_list.add(attendance);

		} else if ("batchReplacement".equals(type)) {
			// 批量补卡
			JSONObject attendance = new JSONObject();
			JSONArray children = new JSONArray();

			JSONObject userCheckTime_component = new JSONObject();
			userCheckTime_component.put("component_name", "DDDateField");
			JSONObject userCheckTime_prop = new JSONObject();
			userCheckTime_prop.put("id", "DDDateField-" + getId());
			userCheckTime_prop.put("label", "补卡时间");
			userCheckTime_prop.put("placeholder", "请选择时间");
			userCheckTime_prop.put("biz_alias", "userCheckTime");
			userCheckTime_prop.put("required", true);
			userCheckTime_prop.put("unit", "小时");
			userCheckTime_component.put("props", userCheckTime_prop);
			children.add(userCheckTime_component);

			JSONObject reason_component = new JSONObject();
			reason_component.put("component_name", "TextField");
			JSONObject reason_prop = new JSONObject();
			reason_prop.put("id", "TextField-" + getId());
			reason_prop.put("label", "补卡原因");
			reason_prop.put("placeholder", "请输入原因");
			reason_prop.put("biz_alias", "reason");
			reason_prop.put("required", true);
			reason_component.put("props", reason_prop);
			children.add(reason_component);

			attendance.put("children", children);
			attendance.put("component_name", "DDBizSuite");
			JSONObject props = new JSONObject();
			props.put("id", "DDBizSuite-" + getId());
			props.put("label", "");
			props.put("biz_type", "attendance.supply");
			props.put("biz_alias", "supply");
			attendance.put("props", props);
			form_component_list.add(attendance);

		} else if ("destroyLeave".equals(type) || "batchCancel".equals(type)) {
			// 单行文本框
			JSONObject title_component = new JSONObject();
			title_component.put("component_name", "TextField");
			JSONObject prop = new JSONObject();
			prop.put("id", "TextField-" + getId());
			prop.put("label", "请假单");
			prop.put("placeholder", "请输入");
			prop.put("required", false);
			title_component.put("props", prop);
			form_component_list.add(title_component);

			JSONObject creator_component = new JSONObject();
			creator_component.put("component_name", "TextField");
			JSONObject creator = new JSONObject();
			creator.put("id", "TextField-" + getId());
			creator.put("label", "销假时长");
			creator.put("placeholder", "请输入");
			creator.put("required", false);
			creator_component.put("props", creator);
			form_component_list.add(creator_component);

			JSONObject createTime_component = new JSONObject();
			createTime_component.put("component_name", "TextField");
			JSONObject createTime = new JSONObject();
			createTime.put("id", "TextField-" + getId());
			createTime.put("label", "剩余请假时长");
			createTime.put("placeholder", "请输入");
			createTime.put("required", false);
			createTime_component.put("props", createTime);
			form_component_list.add(createTime_component);
		} else if ("batchChange".equals(type)) {
			// 批量换班
			JSONObject attendance = new JSONObject();
			JSONArray children = new JSONArray();
			// 申请人
			JSONObject applicant_component = new JSONObject();
			applicant_component.put("component_name", "InnerContactField");
			JSONObject applicant_prop = new JSONObject();
			applicant_prop.put("id", "InnerContactField-" + getId());
			applicant_prop.put("label", "申请人");
			applicant_prop.put("placeholder", "换班申请人");
			applicant_prop.put("biz_alias", "applicant");
			applicant_prop.put("required", true);
			applicant_component.put("props", applicant_prop);
			children.add(applicant_component);

			// 替班人
			JSONObject relief_component = new JSONObject();
			relief_component.put("component_name", "InnerContactField");
			JSONObject relief_prop = new JSONObject();
			relief_prop.put("id", "InnerContactField-" + getId());
			relief_prop.put("label", "替班人");
			relief_prop.put("placeholder", "请选择");
			relief_prop.put("biz_alias", "relief");
			relief_prop.put("required", true);
			relief_component.put("props", relief_prop);
			children.add(relief_component);

			// 换班日期
			JSONObject relieveDate_component = new JSONObject();
			relieveDate_component.put("component_name", "DDDateField");
			JSONObject relieveDate_prop = new JSONObject();
			relieveDate_prop.put("id", "DDDateField-" + getId());
			relieveDate_prop.put("label", "换班日期");
			relieveDate_prop.put("placeholder", "请选择时间");
			relieveDate_prop.put("biz_alias", "relieveDate");
			relieveDate_prop.put("unit", "天");
			relieveDate_prop.put("required", true);
			relieveDate_component.put("props", relieveDate_prop);
			children.add(relieveDate_component);

			// 还班日期
			JSONObject backDate_component = new JSONObject();
			backDate_component.put("component_name", "DDDateField");
			JSONObject backDate_prop = new JSONObject();
			backDate_prop.put("id", "DDDateField-" + getId());
			backDate_prop.put("label", "还班日期");
			backDate_prop.put("placeholder", "请选择时间");
			backDate_prop.put("biz_alias", "backDate");
			backDate_prop.put("unit", "天");
			backDate_prop.put("required", false);
			backDate_component.put("props", backDate_prop);
			children.add(backDate_component);

			// 换班说明
			JSONObject relieveInfo_component = new JSONObject();
			relieveInfo_component.put("component_name", "TextNote");
			JSONObject relieveInfo_prop = new JSONObject();
			relieveInfo_prop.put("id", "TextNote-" + getId());
			relieveInfo_prop.put("label", "换班说明");
			relieveInfo_prop.put("placeholder", "");
			relieveInfo_prop.put("biz_alias", "relieveInfo");
			relieveInfo_prop.put("required", false);
			relieveInfo_component.put("props", relieveInfo_prop);
			children.add(relieveInfo_component);

			// 还班说明
			JSONObject backInfo_component = new JSONObject();
			backInfo_component.put("component_name", "TextNote");
			JSONObject backInfo_prop = new JSONObject();
			backInfo_prop.put("id", "TextNote-" + getId());
			backInfo_prop.put("label", "还班说明");
			backInfo_prop.put("placeholder", "");
			backInfo_prop.put("biz_alias", "backInfo");
			backInfo_prop.put("required", false);
			backInfo_component.put("props", backInfo_prop);
			children.add(backInfo_component);

			attendance.put("children", children);
			attendance.put("component_name", "DDBizSuite");
			JSONObject props = new JSONObject();
			props.put("id", "DDBizSuite-" + getId());
			props.put("label", "");
			props.put("biz_type", "attendance.relieve");
			props.put("biz_alias", "relieve");
			attendance.put("props", props);
			form_component_list.add(attendance);

		} else if ("batchWorkOverTime".equals(type)) {
			// 批量加班
			JSONObject attendance = new JSONObject();
			JSONArray children = new JSONArray();
			// 加班人
			JSONObject partner_component = new JSONObject();
			partner_component.put("component_name", "InnerContactField");
			JSONObject partner_prop = new JSONObject();
			partner_prop.put("id", "InnerContactField-" + getId());
			partner_prop.put("label", "加班人");
			partner_prop.put("placeholder", "请选择");
			partner_prop.put("biz_alias", "partner");
			partner_prop.put("choice", "1");
			partner_prop.put("required", true);
			partner_component.put("props", partner_prop);
			children.add(partner_component);

			// 开始时间
			JSONObject startTime_component = new JSONObject();
			startTime_component.put("component_name", "DDDateField");
			JSONObject startTime_prop = new JSONObject();
			startTime_prop.put("id", "DDDateField-" + getId());
			startTime_prop.put("label", "开始时间");
			startTime_prop.put("placeholder", "请选择时间");
			startTime_prop.put("biz_alias", "startTime");
			startTime_prop.put("required", true);
			startTime_prop.put("unit", "小时");
			startTime_component.put("props", startTime_prop);
			children.add(startTime_component);

			// 结束时间
			JSONObject finishTime_component = new JSONObject();
			finishTime_component.put("component_name", "DDDateField");
			JSONObject finishTime_prop = new JSONObject();
			finishTime_prop.put("id", "DDDateField-" + getId());
			finishTime_prop.put("label", "结束时间");
			finishTime_prop.put("placeholder", "请选择时间");
			finishTime_prop.put("biz_alias", "finishTime");
			finishTime_prop.put("required", true);
			finishTime_prop.put("unit", "小时");
			finishTime_component.put("props", finishTime_prop);
			children.add(finishTime_component);

			// 时长
			JSONObject duration_component = new JSONObject();
			duration_component.put("component_name", "NumberField");
			JSONObject duration_prop = new JSONObject();
			duration_prop.put("id", "NumberField-" + getId());
			duration_prop.put("label", "时长");
			duration_prop.put("placeholder", "请输入时长");
			duration_prop.put("biz_alias", "duration");
			duration_prop.put("required", true);
			duration_component.put("props", duration_prop);
			children.add(duration_component);

			// 加班补偿
			JSONObject compensation_component = new JSONObject();
			compensation_component.put("component_name", "DDSelectField");
			JSONObject compensation_prop = new JSONObject();
			compensation_prop.put("id", "DDSelectField-" + getId());
			compensation_prop.put("label", "加班补偿");
			compensation_prop.put("placeholder", "请选择");
			compensation_prop.put("disabled", false);
			compensation_prop.put("hidden", true);
			compensation_prop.put("biz_alias", "compensation");
			compensation_component.put("props", compensation_prop);
			children.add(compensation_component);

			attendance.put("children", children);
			attendance.put("component_name", "DDBizSuite");
			JSONObject props = new JSONObject();
			props.put("id", "DDBizSuite-" + getId());
			props.put("label", "加班");
			props.put("unit", "hour");
			JSONObject child_field_visible = new JSONObject();
			child_field_visible.put("partner", true);
			child_field_visible.put("type", false);
			props.put("child_field_visible", child_field_visible);

			JSONObject submit_validator = new JSONObject();
			submit_validator.put("unit", "day");
			submit_validator.put("enable", false);
			//props.put("submit_validator", submit_validator);

			props.put("biz_type", "attendance.batchovertime");
			props.put("biz_alias", "overtime");
			attendance.put("props", props);
			form_component_list.add(attendance);

		} else if (titleList != null && titleList.size() > 0) {
			// 没有设置type时
			for (int i = 0; i < titleList.size(); i++) {
				JSONObject component = new JSONObject();
				component.put("component_name", "TextField");
				JSONObject prop = new JSONObject();
				prop.put("id", "TextField-" + getId());
				prop.put("label", titleList.get(i));
				prop.put("placeholder", "请输入");
				prop.put("required", false);
				component.put("props", prop);
				form_component_list.add(component);
			}

		} else {
			// 单行文本框
			JSONObject title_component = new JSONObject();
			title_component.put("component_name", "TextField");
			JSONObject prop = new JSONObject();
			prop.put("id", "TextField-" + getId());
			prop.put("label", "标题");
			prop.put("placeholder", "请输入");
			prop.put("required", false);
			title_component.put("props", prop);
			form_component_list.add(title_component);

			JSONObject creator_component = new JSONObject();
			creator_component.put("component_name", "TextField");
			JSONObject creator = new JSONObject();
			creator.put("id", "TextField-" + getId());
			creator.put("label", "创建者");
			creator.put("placeholder", "请输入");
			creator.put("required", false);
			creator_component.put("props", creator);
			form_component_list.add(creator_component);

			JSONObject createTime_component = new JSONObject();
			createTime_component.put("component_name", "TextField");
			JSONObject createTime = new JSONObject();
			createTime.put("id", "TextField-" + getId());
			createTime.put("label", "创建时间");
			createTime.put("placeholder", "请输入");
			createTime.put("required", false);
			createTime_component.put("props", createTime);
			form_component_list.add(createTime_component);

		}
		return form_component_list;
	}


	// 模板可阅读者
	public static JSONObject procvisibleSave(
			String access_token,
			Long agentId, String processCode, JSONObject save_config)
			throws ApiException, UnsupportedEncodingException {
		logger.debug("-----------------流程高级审批:创建/更新流程模板------------------");
		String apiPre = "";
		if ("true".equals(DingConfig.newInstance().getAttendanceDebug())) {
			apiPre = "https://pre-oapi.dingtalk.com";
		} else {
			apiPre = DingConstant.DING_PREFIX;
		}
		String url = apiPre + "/topapi/process/procvisible/save?access_token="
				+ access_token
				+ DingUtil.getDingAppKeyByEKPUserId("&",
						save_config.containsKey("ekpUserId")
								? save_config.getString("ekpUserId") : null);
		logger.debug("钉钉接口url:" + url);

		JSONObject param = new JSONObject();
		param.put("agentid", agentId);
		param.put("visible_list", save_config.getJSONArray("visible_list"));
		param.put("process_code", processCode);
		param.put("userid", save_config.getString("userid"));

		JSONObject request_param = new JSONObject();
		request_param.put("request", param);

		return DingHttpClientUtil.httpPost(url, request_param, null,
				JSONObject.class);
	}

	// 钉钉套件:创建实例,返回实例ID
	public static JSONObject
			createXformDistance_batch(
					String access_token, String originatorUserId,
					ThirdDingDtemplateXform template, String subject,
					List<ThirdDingTemplateXDetail> details, JSONObject param)
					throws Exception {
		logger.debug(
				"---------------------高级审批：【补卡、换班等】创建实例-------------------------：");
		String url = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/create?access_token="
				+ DingUtils.dingApiService.getAccessToken();
		logger.debug("【补卡、换班等】钉钉创建实例接口：" + url);

		JSONObject requestJSON = new JSONObject();
		requestJSON.put("agentid", Long.valueOf(template.getFdAgentId()));
		requestJSON.put("process_code", template.getFdProcessCode());
		requestJSON.put("originator_user_id", originatorUserId);
		if (param.containsKey("title")) {
			requestJSON.put("title", param.getString("title"));
		} else {
			requestJSON.put("title", subject);
		}
		requestJSON.put("url", param.getString("url"));
		// 套件标识
		String type = param.getString("type");
		logger.warn("type:" + type);
		if ("batchReplacement".equals(type)) {
			requestJSON.put("custom_data", param.getString("extend_value"));
		}

		JSONArray form_component_values = new JSONArray();
		// 构建form_component_values
		buildFormComponentValues(form_component_values, param, type);

		requestJSON.put("form_component_values", form_component_values);
		JSONObject request = new JSONObject();
		request.put("request", requestJSON);
		logger.warn("【" + type + "】request:" + request);
		return DingHttpClientUtil.httpPost(url, request, null,
				JSONObject.class);
	}

	private static void buildFormComponentValues(
			JSONArray form_component_values, JSONObject param, String type) {

		if ("batchReplacement".equals(type)) {
			JSONObject userCheckTime = new JSONObject();
			userCheckTime.put("name", "补卡时间");
			userCheckTime.put("value", param.getString("补卡时间"));
			userCheckTime.put("extValue", param.getString("extend_value"));
			userCheckTime.put("bizAlias", "userCheckTime");
			form_component_values.add(userCheckTime);

			JSONObject reason = new JSONObject();
			reason.put("name", "补卡原因");
			reason.put("value", param.getString("补卡原因"));
			form_component_values.add(reason);
		} else if ("batchChange".equals(type)) {
			// 批量换班
			JSONObject applicant = new JSONObject();
			applicant.put("name", "申请人");
			applicant.put("value", param.getString("申请人"));
			applicant.put("extValue", param.getString("applicat_extValue"));
			applicant.put("bizAlias", "applicant");
			form_component_values.add(applicant);

			JSONObject relief = new JSONObject();
			relief.put("name", "替班人");
			relief.put("value", param.getString("替班人"));
			relief.put("extValue", param.getString("relief_extValue"));
			relief.put("bizAlias", "relief");
			form_component_values.add(relief);

			JSONObject relieveDate = new JSONObject();
			relieveDate.put("name", "换班日期");
			relieveDate.put("value", param.getString("换班日期"));
			relieveDate.put("bizAlias", "relieveDate");
			form_component_values.add(relieveDate);

			if (param.containsKey("还班日期")) {

				JSONObject backDate = new JSONObject();
				backDate.put("name", "还班日期");
				backDate.put("value", param.getString("还班日期"));
				backDate.put("bizAlias", "backDate");
				form_component_values.add(backDate);

				JSONObject backInfo = new JSONObject();
				backInfo.put("name", "还班说明");
				backInfo.put("value", param.getString("还班说明"));
				backInfo.put("bizAlias", "backInfo");
				form_component_values.add(backInfo);

			}

			if (param.containsKey("换班说明")) {
				JSONObject relieveInfo = new JSONObject();
				relieveInfo.put("name", "换班说明");
				relieveInfo.put("value", param.getString("换班说明"));
				relieveInfo.put("bizAlias", "relieveInfo");
				form_component_values.add(relieveInfo);
			} else {
				logger.error("换班说明为空");
			}

		} else if ("batchWorkOverTime".equals(type)) {
			// 批量加班
			JSONObject applicant = new JSONObject();
			applicant.put("name", "加班人");
			applicant.put("value", param.getString("加班人"));
			applicant.put("extValue", param.getString("partner_extValue"));
			applicant.put("bizAlias", "partner");
			form_component_values.add(applicant);

			// 开始时间
			JSONObject relieveDate = new JSONObject();
			relieveDate.put("name", "开始时间");
			relieveDate.put("value", param.getString("开始时间"));
			relieveDate.put("bizAlias", "startTime");
			form_component_values.add(relieveDate);

			// 结束时间
			JSONObject endTime = new JSONObject();
			endTime.put("name", "结束时间");
			endTime.put("value", param.getString("结束时间"));
			endTime.put("bizAlias", "finishTime");
			form_component_values.add(endTime);

			// 时长
			JSONObject duration = new JSONObject();
			duration.put("name", "时长");
			duration.put("value", Double.parseDouble(param.getString("时长")));
			duration.put("extValue", param.getString("duration_extValue"));
			duration.put("bizAlias", "duration");
			form_component_values.add(duration);

			// 补偿方式
			String fdCompensation = param.getString("fd_compensation");
			logger.info("加班 补偿方式fdCompensation："+fdCompensation);
			String val = (StringUtil.isNotNull(fdCompensation) && "charge".equals(fdCompensation))? "加班费":"转调休";
			logger.info("val："+val);
			param.put("加班补偿",fdCompensation); //用于保存钉钉套件实例记录回写，目前钉钉这个功能只能转调休

			JSONObject compensation = new JSONObject();
			compensation.put("name", "加班补偿");
			compensation.put("value", val);
			compensation.put("extValue", fdCompensation);
			compensation.put("bizAlias", "compensation");
			form_component_values.add(compensation);

			// 加班
			JSONObject overtime = new JSONObject();
			overtime.put("name", "加班");
			overtime.put("value", form_component_values.toString());
			overtime.put("extValue", "");
			overtime.put("bizAlias", "overtime");
			form_component_values.add(overtime);

		}

	}

	public static OapiProcessSaveResponse createTemplateXform_copy(
			String access_token,
			Long agentId, String name, String description, Boolean fakeMode,
			String pcode, String fdLang, List<String> titleList)
			throws ApiException {
		logger.debug("-----------------流程高级审批:创建流程模板------------------");
		String url = DingConstant.DING_PREFIX + "/topapi/process/save"
				+ DingUtil.getDingAppKeyByEKPUserId("?", null);
		logger.debug("钉钉接口url:" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);


		OapiProcessSaveRequest request = new OapiProcessSaveRequest();
		OapiProcessSaveRequest.SaveProcessRequest saveProcessRequest = new OapiProcessSaveRequest.SaveProcessRequest();
		// 审批模板名称
		saveProcessRequest.setName(name);
		// 审批模板描述
		saveProcessRequest.setDescription(description);
		// 企业微应用标识(企业方式调用，agentid都不用传)
		saveProcessRequest.setAgentid(agentId);// 企业方式调用，agentid都不用传
		// 审批模板的唯一码。在本接口中，如不传，表示新建一个模板。如传值了，表示更新所传值对应的审批模板
		if (StringUtil.isNotNull(pcode)) {
            saveProcessRequest.setProcessCode(pcode);
        }
		// 是否允许表单在审批管理后台可编辑。true表示不带流程
		saveProcessRequest.setDisableFormEdit(true);
		// 如果需要创建不带流程的模板，该参数传true
		saveProcessRequest.setFakeMode(fakeMode);
		// 注意，每种表单组件，对应的componentName是固定的，参照一下示例代码
		List<OapiProcessSaveRequest.FormComponentVo> formComponentList = Lists
				.newArrayList();

		if (titleList != null && titleList.size() > 0) {
			for (int i = 0; i < titleList.size(); i++) {
				OapiProcessSaveRequest.FormComponentVo title = new OapiProcessSaveRequest.FormComponentVo();
				title.setComponentName("TextField");
				OapiProcessSaveRequest.FormComponentPropVo titleDetail = new OapiProcessSaveRequest.FormComponentPropVo();
				titleDetail.setLabel(titleList.get(i));
				titleDetail.setId("TextField-" + getId());
				titleDetail.setPlaceholder("请输入");
				titleDetail.setRequired(false);
				title.setProps(titleDetail);
				formComponentList.add(title);
			}

		} else {
			// 单行文本框
			OapiProcessSaveRequest.FormComponentVo title = new OapiProcessSaveRequest.FormComponentVo();
			title.setComponentName("TextField");
			OapiProcessSaveRequest.FormComponentPropVo titleDetail = new OapiProcessSaveRequest.FormComponentPropVo();
			titleDetail.setLabel(DingNotifyUtil.getNameByLang("标题", fdLang));
			titleDetail.setId("TextField-" + getId());
			titleDetail.setPlaceholder("请输入");
			titleDetail.setRequired(false);
			title.setProps(titleDetail);
			formComponentList.add(title);

			OapiProcessSaveRequest.FormComponentVo creator = new OapiProcessSaveRequest.FormComponentVo();
			creator.setComponentName("TextField");
			OapiProcessSaveRequest.FormComponentPropVo creatorDetail = new OapiProcessSaveRequest.FormComponentPropVo();
			creatorDetail.setLabel(DingNotifyUtil.getNameByLang("创建者", fdLang));
			creatorDetail.setId("TextField-" + getId());
			creatorDetail.setPlaceholder("请输入");
			creatorDetail.setRequired(false);
			creator.setProps(creatorDetail);
			formComponentList.add(creator);

			OapiProcessSaveRequest.FormComponentVo createTime = new OapiProcessSaveRequest.FormComponentVo();
			createTime.setComponentName("TextField");
			OapiProcessSaveRequest.FormComponentPropVo createTimeDetail = new OapiProcessSaveRequest.FormComponentPropVo();
			createTimeDetail
					.setLabel(DingNotifyUtil.getNameByLang("创建时间", fdLang));
			createTimeDetail.setId("TextField-" + getId());
			createTimeDetail.setPlaceholder("请输入");
			createTimeDetail.setRequired(false);
			createTime.setProps(createTimeDetail);
			formComponentList.add(createTime);
		}

		saveProcessRequest.setFormComponentList(formComponentList);
		request.setSaveProcessRequest(saveProcessRequest);

		OapiProcessSaveResponse response = client.execute(request,
				access_token);
		return response;
	}
	private static String getNameByLang(String name, Locale locale) {
		if ("标题".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.fdSubject", "sys-notify",
					locale);
		} else if ("创建者".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.docCreator", "sys-notify",
					locale);
		} else if ("创建时间".equals(name)) {
			return ResourceUtil.getStringValue(
					"sysNotifyTodo.fdCreateTime", "sys-notify",
					locale);
		}
		return name;
	}

	// 删除模板
	public static OapiProcessDeleteResponse deleteTemplate(String corpId,
			String processCode, String access_token) throws ApiException {

		String url = DingConstant.DING_PREFIX + "/topapi/process/delete"
				+ DingUtil.getDingAppKey("?", corpId);
		logger.debug("钉钉删除模板接口：" + url);
		ThirdDingTalkClient client = new ThirdDingTalkClient(url);
		OapiProcessDeleteRequest req = new OapiProcessDeleteRequest();
		DeleteProcessRequest obj1 = new DeleteProcessRequest();
		// obj1.setAgentid(123456L);
		obj1.setProcessCode(processCode);
		req.setRequest(obj1);
		return client.execute(req, access_token);
	}

	//创建实例,返回实例ID
	public static OapiProcessWorkrecordCreateResponse createExample(
			String access_token, String originatorUserId, SysNotifyTodo todo,
			ThirdDingDtemplate template, String subject,
			List<ThirdDingTemplateDetail> details) throws ApiException {
		logger.debug("---------------------创建实例-------------------------："
				+ todo.getFdSubject());
		logger.debug("template.getFdLang()  ："
				+ template.getFdLang());
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/create"
				+ DingUtil.getDingAppKeyByEKPUserId("?",
						todo.getDocCreator() == null ? null
								: todo.getDocCreator().getFdId());
		logger.debug("钉钉接口：" + dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		OapiProcessWorkrecordCreateRequest req = new OapiProcessWorkrecordCreateRequest();
		SaveFakeProcessInstanceRequest obj1 = new SaveFakeProcessInstanceRequest();
		//应用id
		obj1.setAgentid(template.getFdAgentId());
		//审批模板唯一码
		obj1.setProcessCode(template.getFdProcessCode());
		//审批发起人
		obj1.setOriginatorUserId(originatorUserId);
		List<FormComponentValueVo> list3 = new ArrayList<FormComponentValueVo>();
		if(template!=null&&details!=null){
			FormComponentValueVo vo = null;
			if(template.getFdType()){
				Locale local = null;
				if (StringUtil.isNotNull(todo.getFdLang())
						&& todo.getFdLang().contains("-")) {
					local = new Locale(todo.getFdLang().split("-")[0],
							todo.getFdLang().split("-")[1]);
				}
				for (ThirdDingTemplateDetail detail : details) {
					vo = new FormComponentValueVo();
					vo.setName(getNameByLang(detail.getFdName(), local));
					if("标题".equals(detail.getFdName())){
						vo.setValue(todo.getFdSubject());
						String level = null;
						switch (todo.getFdLevel()) {
						case 1:
							level = "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.1", "sys-notify", local)+"】";
							break;
						case 2:
							level = "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.2", "sys-notify", local)+"】";
							break;
						default:
							// level =
							// "【"+ResourceUtil.getStringValue("sysNotifyTodo.level.taglib.3",
							// "sys-notify", local)+"】";
							level = "";
							break;
						}
						String suspend = "";
						if (todo.getFdType() == 3) {
							suspend = "【"
									+ ResourceUtil.getStringValue(
											"sysNotifyTodo.type.suspend",
											"sys-notify", local)
									+ "】";
						}
						if(StringUtil.isNotNull(subject)) {
                            obj1.setTitle(suspend + level + subject);
                        }
					}else if("创建者".equals(detail.getFdName())){
						vo.setValue(todo.getDocCreatorName());
					}else if("创建时间".equals(detail.getFdName())){
						if(todo.getFdCreateTime()==null){
							vo.setValue("");
						}else{
							vo.setValue(DateUtil.convertDateToString(todo.getFdCreateTime(), "yyyy-MM-dd HH:mm"));
						}
					}
					list3.add(vo);
				}
			}else{
				//非通用流程模板
				for(ThirdDingTemplateDetail detail:details){
					vo = new FormComponentValueVo();
					vo.setName(detail.getFdName());
					vo.setValue("");
					list3.add(vo);
				}
			}
		}
		obj1.setFormComponentValues(list3);
		//实例跳转链接
		String domainName = DingConfig.newInstance().getDingDomain();
		String viewUrl = DingUtil.getPcViewUrl(todo);
		// if(StringUtil.isNotNull(todo.getFdId())){
		// viewUrl =
		// "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId="
		// + todo.getFdId() + "&oauth=ekp&dingOut=true";
		// if (StringUtils.isNotEmpty(domainName)) {
		// viewUrl = domainName + viewUrl;
		// } else {
		// viewUrl = StringUtil.formatUrl(viewUrl);
		// }
		// }else{
		// if(StringUtil.isNull(domainName))
		// domainName = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
		// if(domainName.endsWith("/"))
		// domainName = domainName.substring(0, domainName.length()-1);
		// viewUrl = StringUtil.formatUrl(todo.getFdLink(), domainName);
		// if(viewUrl.indexOf("?")==-1){
		// viewUrl = viewUrl + "?oauth=ekp&dingOut=true";
		// }else{
		// viewUrl = viewUrl + "&oauth=ekp&dingOut=true";
		// }
		// }
		obj1.setUrl(viewUrl);
		req.setRequest(obj1);
		OapiProcessWorkrecordCreateResponse rsp = client.execute(req, access_token);
		return rsp;
	}
	
	// 钉钉套件:创建实例,返回实例ID
	public static OapiProcessWorkrecordCreateResponse createXformDistance(
			String access_token, String originatorUserId,
			ThirdDingDtemplateXform template, String subject,
			List<ThirdDingTemplateXDetail> details, JSONObject param)
			throws ApiException {
		logger.debug(
				"---------------------高级审批：创建实例-------------------------：");
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/create"
				+ DingUtil.getDingAppKeyByEKPUserId("?",
						(param != null && param.containsKey("ekpUserId"))
								? param.getString("ekpUserId") : null);
		logger.debug("钉钉创建实例接口：" + dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		OapiProcessWorkrecordCreateRequest req = new OapiProcessWorkrecordCreateRequest();
		SaveFakeProcessInstanceRequest obj1 = new SaveFakeProcessInstanceRequest();
		// 应用id
		obj1.setAgentid(Long.valueOf(template.getFdAgentId()));
		// 审批模板唯一码
		obj1.setProcessCode(template.getFdProcessCode());
		// 审批发起人
		obj1.setOriginatorUserId(originatorUserId);
		// 标题
		if (param.containsKey("title")) {
			obj1.setTitle(param.getString("title"));
		} else {
			obj1.setTitle(subject);
		}

		List<FormComponentValueVo> list3 = new ArrayList<FormComponentValueVo>();
		String local = template.getFdLang();
		if (template != null && details != null) {
			FormComponentValueVo vo = null;
			if ("common".equals(template.getFdFlow())
					|| StringUtil.isNull(template.getFdFlow())) {
				// 通用流程模板： 非套件
				for (ThirdDingTemplateXDetail detail : details) {
					vo = new FormComponentValueVo();
					vo.setName(detail.getFdName());
					if ("标题".equals(detail.getFdName())) {
						vo.setValue(subject);
					} else if ("创建者".equals(detail.getFdName())) {
						if (!param.containsKey("creater")) {
							vo.setValue("");
						} else {
							vo.setValue(param.getString("creater"));
						}
					} else if ("创建时间".equals(detail.getFdName())) {
						if (!param.containsKey("createTime")) {
							vo.setValue("");
						} else {
							vo.setValue(param.getString("createTime"));
						}
					}
					list3.add(vo);
				}
			} else if ("attendance".equals(template.getFdFlow())
					|| "batchLeave".equals(template.getFdFlow())) {
				// 请假
				for (ThirdDingTemplateXDetail detail : details) {
					vo = new FormComponentValueVo();
					vo.setName(detail.getFdName());
					if (StringUtil.isNotNull(detail.getFdName())
							&& param.containsKey(detail.getFdName())) {
						vo.setValue(param.getString(detail.getFdName()));
					} else {
						logger.warn("字段为空：" + detail.getFdName());
						vo.setValue("");
					}
					if ("时长".equals(detail.getFdName())) {
						vo.setExtValue(param.getString("extend_value"));
					}
					if ("请假类型".equals(detail.getFdName())) {
						vo.setExtValue(param.getString("type_extend_value"));
					}
					list3.add(vo);
				}

			} else {
				// 套件模板
				logger.warn("param:" + param);
				for (ThirdDingTemplateXDetail detail : details) {
					vo = new FormComponentValueVo();
					vo.setName(detail.getFdName());
					if (StringUtil.isNotNull(detail.getFdName())
							&& param.containsKey(detail.getFdName())) {
						vo.setValue(param.getString(detail.getFdName()));
					} else {
						vo.setValue("");
					}
					list3.add(vo);
				}
			}
		}
		obj1.setFormComponentValues(list3);
		// 实例跳转链接
		obj1.setUrl(param.getString("url"));
		req.setRequest(obj1);

		logger.warn("----req:" + JSON.toJSONString(req));
		OapiProcessWorkrecordCreateResponse rsp = client.execute(req,
				access_token);
		if (rsp != null){
			logger.warn("创建实例结果："+rsp.getBody());
			rsp.setMsg(JSON.toJSONString(req));
		}else{
			logger.warn("--------------创建实例结果为null-----------");
		}

		return rsp;
	}

	//创建任务,返回任务ID
	public static OapiProcessWorkrecordTaskCreateResponse createTask(
			OapiProcessWorkrecordTaskCreateRequest req, String access_token,
			String processInstanceId, String userid, String url, Long agentId,
			String ekpUserId) throws ApiException {

		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/task/create"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		logger.debug(
				"【钉钉接口】创建任务 ekpUserId： " + ekpUserId + "  dingUrl:" + dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		SaveTaskRequest obj1 = new SaveTaskRequest();
		//应用id
		obj1.setAgentid(agentId);
		//实例id
		obj1.setProcessInstanceId(processInstanceId);
		//节点id
		//obj1.setActivityId("activity-zzz");
		List<com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest.TaskTopVo> list3 = new ArrayList<com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest.TaskTopVo>();
		com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest.TaskTopVo obj4 = new com.dingtalk.api.request.OapiProcessWorkrecordTaskCreateRequest.TaskTopVo();
		//用户id
		obj4.setUserid(userid);
		//跳转url
		logger.debug("-------待办调整url:-------" + url);
		url = url + DingUtil.getDingAppKeyByEKPUserId("&", ekpUserId);
		logger.debug("-------待办调整url后:-------" + url);
		obj4.setUrl(url);		 
		list3.add(obj4);
		obj1.setTasks(list3);
		req.setRequest(obj1);
		OapiProcessWorkrecordTaskCreateResponse rsp = client.execute(req, access_token);
		return rsp;
	}
	
	//更新任务，更新任务ID
	public static  OapiProcessWorkrecordTaskUpdateResponse updateTask(OapiProcessWorkrecordTaskUpdateRequest req, String access_token,String pid,Long taskid, Long agentId,String ekpUserId) throws ApiException{
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/task/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);
		logger.debug(
				"【钉钉接口】更新任务 ekpUserId： " + ekpUserId + "  dingUrl:" + dingUrl);

		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		UpdateTaskRequest obj1 = new UpdateTaskRequest();
		//应用id,isv必须传递
		obj1.setAgentid(agentId);
		//实例id
		obj1.setProcessInstanceId(pid);
		List<com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest.TaskTopVo> list3 = new ArrayList<com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest.TaskTopVo>();
		com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest.TaskTopVo obj4 = new com.dingtalk.api.request.OapiProcessWorkrecordTaskUpdateRequest.TaskTopVo();
		list3.add(obj4);
		//任务id
		obj4.setTaskId(taskid);
		//任务状态，分为CANCELED和COMPLETED
		obj4.setStatus("COMPLETED");
		//任务结果，分为agree和refuse。当status为CANCELED时，不需要传result
		obj4.setResult("agree");
		obj1.setTasks(list3);
		req.setRequest(obj1);
		OapiProcessWorkrecordTaskUpdateResponse rsp = client.execute(req, access_token);
		return rsp;
	}
	
	//同步实例状态，更新实例下所有任务
	public static OapiProcessWorkrecordUpdateResponse updateInstanceState(
			String access_token, String pid, Long agentId, String ekpUserId,
			boolean isAgree)
			throws ApiException {
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);

		logger.debug("【钉钉接口】同步实例状态，更新实例下所有任务  ekpUserId： " + ekpUserId
				+ "   dingUrl:" + dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		OapiProcessWorkrecordUpdateRequest req = new OapiProcessWorkrecordUpdateRequest();
		UpdateProcessInstanceRequest obj1 = new UpdateProcessInstanceRequest();
		//应用id
		obj1.setAgentid(agentId);
		//实例id
		obj1.setProcessInstanceId(pid);
		//实例状态，分为COMPLETED, TERMINATED
		obj1.setStatus("COMPLETED");
		//实例结果, 如果实例状态是COMPLETED，需要设置result，分为agree和refuse
		if (isAgree) {
			obj1.setResult("agree");
		} else {
			obj1.setResult("refuse");
		}

		req.setRequest(obj1);
		OapiProcessWorkrecordUpdateResponse rsp = client.execute(req, access_token);
		return rsp;
	} 

	// 更新实例状态为终止，更新实例下所有任务
	public static OapiProcessWorkrecordUpdateResponse
			updateInstanceState_TERMINATED(
					String access_token, String pid, Long agentId,
					String ekpUserId)
					throws ApiException {
		String dingUrl = DingConstant.DING_PREFIX
				+ "/topapi/process/workrecord/update"
				+ DingUtil.getDingAppKeyByEKPUserId("?", ekpUserId);

		logger.debug("【钉钉接口】同步实例状态，更新实例下所有任务  ekpUserId： " + ekpUserId
				+ "   dingUrl:" + dingUrl);
		ThirdDingTalkClient client = new ThirdDingTalkClient(dingUrl);
		OapiProcessWorkrecordUpdateRequest req = new OapiProcessWorkrecordUpdateRequest();
		UpdateProcessInstanceRequest obj1 = new UpdateProcessInstanceRequest();
		// 应用id
		obj1.setAgentid(agentId);
		// 实例id
		obj1.setProcessInstanceId(pid);
		// 实例状态，分为COMPLETED, TERMINATED
		obj1.setStatus("TERMINATED");
		// 实例结果, 如果实例状态是COMPLETED，需要设置result，分为agree和refuse
		obj1.setResult("refuse");

		req.setRequest(obj1);
		OapiProcessWorkrecordUpdateResponse rsp = client.execute(req,
				access_token);
		return rsp;
	}
}
