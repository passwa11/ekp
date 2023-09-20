<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,
	com.landray.kmss.util.*,
	com.landray.kmss.sys.organization.interfaces.*,
	com.landray.kmss.third.ding.oms.*,
	com.landray.kmss.third.ding.constant.DingConstant,
	com.landray.kmss.sys.organization.webservice.out.*,
	net.sf.json.JSONArray,
	net.sf.json.JSONObject
	" %>
<%!
	public List<JSONArray> getAllOrgByRootOrg() throws Exception {
		ISysSynchroGetOrgWebService sysSynchroGetOrgWebService = (ISysSynchroGetOrgWebService) SpringBeanUtil
					.getBean("sysSynchroGetOrgWebService");
		String lastUpdateTime = null;
		int preCount=1000;
		SysSynchroGetOrgInfoContext infoContext = new SysSynchroGetOrgInfoContext();
		infoContext
				.setReturnOrgType("[{\"type\":\"org\"},{\"type\":\"dept\"}]");
		infoContext.setCount(preCount);
		List<JSONArray> resultList = new ArrayList<JSONArray>();
		while (true) {
			infoContext.setBeginTimeStamp(lastUpdateTime);
			SysSynchroOrgResult orgResult = sysSynchroGetOrgWebService
					.getUpdatedElements(infoContext);
			if (orgResult.getReturnState() == 2) {
				String resultStr = orgResult.getMessage();
				if (StringUtil.isNotNull(resultStr)) {
					resultList.add((JSONArray) JSONArray.fromObject(resultStr));
					lastUpdateTime = orgResult.getTimeStamp();
				}
				if (orgResult.getCount() < preCount)
					break;
			}
		}
		return resultList;
	}

	public List<JSONArray> getAllPersonByRootOrg() throws Exception {
		ISysSynchroGetOrgWebService sysSynchroGetOrgWebService = (ISysSynchroGetOrgWebService) SpringBeanUtil
					.getBean("sysSynchroGetOrgWebService");
		String lastUpdateTime = null;
		int preCount=1000;
		SysSynchroGetOrgInfoContext infoContext = new SysSynchroGetOrgInfoContext();
		infoContext
				.setReturnOrgType("[{\"type\":\"person\"}]");
		infoContext.setCount(preCount);
		List<JSONArray> resultList = new ArrayList<JSONArray>();
		while (true) {
			infoContext.setBeginTimeStamp(lastUpdateTime);
			SysSynchroOrgResult orgResult = sysSynchroGetOrgWebService
					.getUpdatedElements(infoContext);
			if (orgResult.getReturnState() == 2) {
				String resultStr = orgResult.getMessage();
				if (StringUtil.isNotNull(resultStr)) {
					resultList.add((JSONArray) JSONArray.fromObject(resultStr));
					lastUpdateTime = orgResult.getTimeStamp();
				}
				if (orgResult.getCount() < preCount)
					break;
			}
		}
		return resultList;
	}

%>
<center>
<%
		String rootId = request.getParameter("rootId");
		String DING_OMS_ROOT_ORG_ID =DingConfig.newInstance().getDingOrgId();
		String DING_OMS_ROOT_FLAG =  DingConfig.newInstance().getDingOmsRootFlag();
		int DING_ROOT_DEPT_ID = 1;
		if(StringUtil.isNotNull(rootId)){
			DING_ROOT_DEPT_ID = Integer.parseInt(rootId);
		}
		
		Map<String,String> pidMap = new HashMap<String,String>();
		List<String> deptIds = new ArrayList<String>();
		List<JSONArray> allDepts = getAllOrgByRootOrg();
		for (JSONArray block : allDepts) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				deptIds.add(element.getString("id"));
				if (element.get("parent") != null) {
					pidMap.put(element.getString("id"),element.getString("parent"));
				}else{
					pidMap.put(element.getString("id"),"");
				}
			}
		}
		
		List<String> avaliableDeptIds = new ArrayList<String>();
		for(String deptId:deptIds){
			if (StringUtil.isNotNull(DING_OMS_ROOT_ORG_ID)) {
				String parentId = pidMap.get(deptId);
				while (StringUtil.isNotNull(parentId)) {
					if (parentId.equals(DING_OMS_ROOT_ORG_ID)) {
						avaliableDeptIds.add(deptId);
						break;
					}
					parentId = pidMap.get(parentId);
				}
			}else{
				avaliableDeptIds.add(deptId);
			}
		}
		if ("true".equals(DING_OMS_ROOT_FLAG)) {
			if(!avaliableDeptIds.contains(DING_OMS_ROOT_ORG_ID)){
				avaliableDeptIds.add(DING_OMS_ROOT_ORG_ID);
			}
		}

		List<JSONObject> avaliableDepts = new ArrayList<JSONObject>();
		for (JSONArray block : allDepts) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				if(avaliableDeptIds.contains(element.getString("id"))){
					avaliableDepts.add(element);
				}
			}
		}
		System.out.println(avaliableDeptIds);
		System.out.println(avaliableDeptIds.size());
		System.out.println(avaliableDepts.size());

		List<JSONObject> avaliablePersons = new ArrayList<JSONObject>();
		List<JSONArray> allPersons = getAllPersonByRootOrg();
		for (JSONArray block : allPersons) {
			for (int i = 0; i < block.size(); i++) {
				JSONObject element = block.getJSONObject(i);
				if (element.get("parent") != null) {
					if(avaliableDeptIds.contains(element.getString("parent"))){
						avaliablePersons.add(element);
					}
				}
			}
		}
		System.out.println(avaliablePersons.size());

		DingApiService dingApiService = new DingApiServiceImpl();
		
		Map<String,String> dingDeptMap = new HashMap<String,String>();
		List<String> dingPersonList = new ArrayList<String>();
		JSONObject retDept = dingApiService.departGet();
		if(retDept.getInt("errcode")==0){
			JSONArray depts = retDept.getJSONArray("department");
			for(int i=0;i<depts.size();i++){
				JSONObject dept = depts.getJSONObject(i);
				dingDeptMap.put(dept.getString("name"),""+dept.getInt("id"));
			}
		}

		JSONObject retUser = dingApiService.userList(DING_ROOT_DEPT_ID,true);
		if(retUser.getInt("errcode")==0){
			JSONArray users = retUser.getJSONArray("userlist");
			for(int i=0;i<users.size();i++){
				JSONObject user = users.getJSONObject(i);
				dingPersonList.add(user.getString("userid"));
			}
		}
	List<JSONObject> noInDingDept = new ArrayList<JSONObject>();
	List<JSONObject> noInDingPerson = new ArrayList<JSONObject>();

	if(avaliableDepts!=null){
		out.println("<table border=1>");
		out.println("<tr><td colspan='3'>能关联的部门</td><tr>");
		for(int i=0;i<avaliableDepts.size();i++){
			JSONObject dept = (JSONObject)avaliableDepts.get(i);
			if(dingDeptMap.keySet().contains(dept.getString("name"))){
				out.println("<tr>");
				out.println("<td>"+dept.getString("name")+"</td>");
				out.println("<td>"+dept.getString("id")+"</td>");
				out.println("<td>"+dingDeptMap.get(dept.getString("name"))+"</td>");
				out.print("</tr>");
			}else{
				noInDingDept.add(dept);
			}
		}
		out.println("</table>");
	}

	out.println("<br>");
	
	if(avaliablePersons!=null){
		out.println("<table border=1>");
		out.println("<tr><td colspan='3'>能关联的个人</td><tr>");
		for(int i=0;i<avaliablePersons.size();i++){
			JSONObject person = (JSONObject)avaliablePersons.get(i);
			if(dingPersonList.contains(person.getString("loginName"))){
				out.println("<tr>");
				out.println("<td>"+person.getString("name")+"</td>");
				out.println("<td>"+person.getString("id")+"</td>");
				out.println("<td>"+person.getString("loginName")+"</td>");
				out.print("</tr>");
			}else{
				noInDingPerson.add(person);
			}
		}
		out.println("</table>");
	}

	if(noInDingDept.isEmpty()){
		out.println("<br>");
		out.println("部门能全部关联");
		out.println("<br>");
	}else{
		out.println("<br>");
		out.println("<table border=1>");
		out.println("<tr><td colspan='2'>EKP还有不能关联的部门</td><tr>");
		for(int i=0;i<noInDingDept.size();i++){
			JSONObject dept = (JSONObject)noInDingDept.get(i);
			out.println("<tr>");
			out.println("<td>"+dept.getString("name")+"</td>");
			out.println("<td>"+dept.getString("id")+"</td>");
			out.print("</tr>");
		}
		out.println("</table>");
	}

	if(noInDingPerson.isEmpty()){
		out.println("<br>");
		out.println("个人能全部关联");
		out.println("<br>");
	}else{
		out.println("<br>");
		out.println("<table border=1>");
		out.println("<tr><td colspan='3'>EKP还有不能关联的个人</td><tr>");
		for(int i=0;i<noInDingPerson.size();i++){
			JSONObject person = (JSONObject)noInDingPerson.get(i);
			out.println("<tr>");
			out.println("<td>"+person.getString("name")+"</td>");
			out.println("<td>"+person.getString("id")+"</td>");
			out.println("<td>"+person.getString("loginName")+"</td>");
			out.print("</tr>");
		}
		out.println("</table>");
	}

%>
</center>
