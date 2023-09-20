package com.landray.kmss.third.ding.ws;

/**
 * WebService客户端
 * 
 */
public class WebServiceClient {

	/**
	 * 主方法
	 * 
	 * @throws Exception
	 */
	/*public static void main(String[] args) throws Exception {
		WebServiceConfig cfg = WebServiceConfig.getInstance();
	
		Object service = callService(cfg.getAddress(), cfg.getServiceClass());
		// 请在此处添加业务代码
		WebServiceClient client= new WebServiceClient();
		client.addFlow("","chenl");
	}
	
	public void addFlow(String pid,String auth) throws Exception{
		WebServiceConfig cfg = WebServiceConfig.getInstance();
		Object service = callService(cfg.getAddress(), cfg.getServiceClass());
		IKmReviewWebserviceService service2 = (IKmReviewWebserviceService) service;
		service2.addReview(createForm(pid,auth));
		System.out.println("流程启动完毕=============");
	}
	
	
	public void addDingFlow(KmReviewMain main, String auth) throws Exception{
		DingApiService dingService = DingUtils.getDingApiService();
		Map map = main.getExtendDataModelInfo().getModelData();
		ThirdDingTalkClient client = new ThirdDingTalkClient(DingConstant.DING_PREFIX + "/topapi/processinstance/create");
		OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
		request.setProcessCode("PROC-FFYJRKOV-OCRYFGHMUXW4P2NNWYBI3-7DR9I7MJ-1");
		request.setOriginatorUserId("manager1896");
		request.setDeptId(43909110l);
		request.setApprovers("manager1896");
		//request.setCcList("userid1,userid2");
		//request.setCcPosition("START_FINISH");
		List<FormComponentValueVo> formComponentValues = new ArrayList<FormComponentValueVo>();
		"ext_value": "{\"compressedValue\":\"1f8b0800000000000000a551cd4ac340107e973987b269d234c9ad58a405ab50d393781892ad5ddcee86fd514ac9c1938fe3d127127c0c6743688b78f3f8fdec7cdfcc1ea146597b898eaf74c3a14c2268b843216f8475503e1ca1f1069dd06aa9e67880928d92e039910bed0d3d1b3176c9ae84f28e5b28e39cfd92ee79ad55430acb83b0e5e8bc09d62360dbcac3b5d1fb4aeca94b3c49b23ccfc8c682b3572b7da115d36cd06a89d6de6250600603a6a41069fb35b86a66b5d19610f9099de71471311ee65887c65df87afc479bee310261d73cdc688bd2f2085eb8b1944605e211832e821ddaab50034a673c195a896a498b8fa7c934cd9334ceb3085eb5799ed3f587f993341d9a0421049f2e3949fbd47f7fc799df2841f56171b759d3c95a6f77153e11f1fdf1f9f5f60edd0f9d3d8f5d1d020000\",\"extension\":\"{\\\"tag\\\":\\\"事假\\\"}\",\"unit\":\"HOUR\",\"pushTag\":\"请假\",\"isNaturalDay\":false,\"detailList\":[{\"classInfo\":{\"hasClass\":true,\"name\":\"A\",\"sections\":[{\"endAcross\":0,\"startTime\":1536886800000,\"endTime\":1536919200000,\"startAcross\":0}]},\"workDate\":1536854400000,\"isRest\":false,\"workTimeMinutes\":540,\"approveInfo\":{\"fromAcross\":0,\"toAcross\":0,\"fromTime\":1536886800000,\"durationInDay\":0.33,\"durationInHour\":3,\"toTime\":1536897600000}}],\"durationInDay\":0.33,\"isModifiable\":false,\"durationInHour\":3}",
		"name": "[\"开始时间\",\"结束时间\"]",
		"value": "[\"2018-09-14 09:00\",\"2018-09-14 12:00\",3,\"hour\",\"事假\",\"请假类型\"]"
		}
		FormComponentValueVo vo = new FormComponentValueVo();
		List temp = new ArrayList();
		temp.add("开始时间");
		temp.add("结束时间");
		vo.setName(temp.toString());
		temp = new ArrayList();
		temp.add("2018-09-14 09:00");
		temp.add("2018-09-14 12:00");
		temp.add(3);
		temp.add("hour");
		temp.add("事假");
		temp.add("请假类型");
		vo.setValue(temp.toString());
	//		vo.setValue("[\"2018-09-14 09:00\",\"2018-09-14 12:00\",3,\"hour\",\"事假\",\"请假类型\"]");
		vo.setExtValue("{\"compressedValue\":\"1f8b0800000000000000a551cd4ac340107e973987b269d234c9ad58a405ab50d393781892ad5ddcee86fd514ac9c1938fe3d127127c0c6743688b78f3f8fdec7cdfcc1ea146597b898eaf74c3a14c2268b843216f8475503e1ca1f1069dd06aa9e67880928d92e039910bed0d3d1b3176c9ae84f28e5b28e39cfd92ee79ad55430acb83b0e5e8bc09d62360dbcac3b5d1fb4aeca94b3c49b23ccfc8c682b3572b7da115d36cd06a89d6de6250600603a6a41069fb35b86a66b5d19610f9099de71471311ee65887c65df87afc479bee310261d73cdc688bd2f2085eb8b1944605e211832e821ddaab50034a673c195a896a498b8fa7c934cd9334ceb3085eb5799ed3f587f993341d9a0421049f2e3949fbd47f7fc799df2841f56171b759d3c95a6f77153e11f1fdf1f9f5f60edd0f9d3d8f5d1d020000\",\"extension\":\"{\\\"tag\\\":\\\"事假\\\"}\",\"unit\":\"HOUR\",\"pushTag\":\"请假\",\"isNaturalDay\":false,\"detailList\":[{\"classInfo\":{\"hasClass\":true,\"name\":\"A\",\"sections\":[{\"endAcross\":0,\"startTime\":1536886800000,\"endTime\":1536919200000,\"startAcross\":0}]},\"workDate\":1536854400000,\"isRest\":false,\"workTimeMinutes\":540,\"approveInfo\":{\"fromAcross\":0,\"toAcross\":0,\"fromTime\":1536886800000,\"durationInDay\":0.33,\"durationInHour\":3,\"toTime\":1536897600000}}],\"durationInDay\":0.33,\"isModifiable\":false,\"durationInHour\":3}");
		formComponentValues.add(vo);
		vo = new FormComponentValueVo();
		vo.setName("开始时间");
		vo.setValue("2018-09-14 09:00");
		formComponentValues.add(vo);
		vo = new FormComponentValueVo();
		vo.setName("结束时间");
		vo.setValue("2018-09-14 12:00");
		formComponentValues.add(vo);
		request.setFormComponentValues(formComponentValues);
		System.out.println("请求钉钉创建流程参数：" + BeanUtils.describe(request));
		OapiProcessinstanceCreateResponse response = client.execute(request,dingService.getAccessToken());
		System.out.println("创建钉钉流程返回值：" + response.getBody());
	}
	
	*//**
		* 创建文档及流程数据
		*/
	/*
	private KmReviewParamterForm createForm(String pid,String auth) throws Exception {
	KmReviewParamterForm form = new KmReviewParamterForm();
	// 文档模板id
	form.setFdTemplateId("165e60630425ba27d09207e445c88dcb");
	// 文档标题
	form.setDocSubject("钉钉补卡申请流程集成测试");
	// 流程表单
	JSONObject jsonv = new JSONObject();
	jsonv.put("fd_369c8046989f3a", pid);
	
	DingApiService dingService = DingUtils.getDingApiService();
	ThirdDingTalkClient client = new ThirdDingTalkClient(DingConstant.DING_PREFIX + "/topapi/processinstance/get");
	OapiProcessinstanceGetRequest request = new OapiProcessinstanceGetRequest();
	request.setProcessInstanceId(pid);
	OapiProcessinstanceGetResponse response = client.execute(request,dingService.getAccessToken());
	JSONObject json = JSONObject.fromObject(response.getBody());
	System.out.println("获取审批单信息:"+json.toString());
	JSONArray ja = json.getJSONObject("process_instance").getJSONArray("form_component_values");
	System.out.println("表单内容:"+ja.toString());
	JSONObject jotemp = null;
	for(int i=0;i<ja.size();i++){
		jotemp = ja.getJSONObject(i);
		if(!jotemp.containsKey("name"))
			continue;
		if("缺卡原因".equals(jotemp.getString("name"))){
			jsonv.put("result", jotemp.getString("value"));
		}else if("repairCheckTime".equals(jotemp.getString("name"))){
			jsonv.put("time", JSONObject.fromObject(jotemp.getString("ext_value")).getString("planText"));
		}
	}
	// 流程发起人
	if("manager1896".equals(auth))
		auth = "chenl";
	form.setDocCreator("{\"LoginName\": \""+auth+"\"}");
	form.setFormValues(jsonv.toString());
	return form;
	}
	
	*//**
		* 调用服务，生成客户端的服务代理
		* 
		* @param address WebService的URL
		* @param serviceClass 服务接口全名
		* @return 服务代理对象
		* @throws Exception
		*//*
		public static Object callService(String address, Class serviceClass)
			throws Exception {
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		
		// 记录入站消息
		factory.getInInterceptors().add(new LoggingInInterceptor());
		
		// 记录出站消息
		factory.getOutInterceptors().add(new LoggingOutInterceptor());
		
		// 添加消息头验证信息。如果服务端要求验证用户密码，请加入此段代码
		// factory.getOutInterceptors().add(new AddSoapHeader());
		
		factory.setServiceClass(serviceClass);
		factory.setAddress(address);
		
		// 使用MTOM编码处理消息。如果需要在消息中传输文档附件等二进制内容，请加入此段代码
		// Map<String, Object> props = new HashMap<String, Object>();
		// props.put("mtom-enabled", Boolean.TRUE);
		// factory.setProperties(props);		
		 
		 // 创建服务代理并返回
		return factory.create();
		}*/

}
