<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*,
	com.landray.kmss.util.*,
	com.landray.kmss.sys.organization.interfaces.*,
	com.landray.kmss.sys.organization.service.*,
	com.landray.kmss.sys.organization.model.*,
	com.landray.kmss.third.ding.oms.*,
	com.landray.kmss.third.ding.constant.DingConstant,
	net.sf.json.JSONArray,
	net.sf.json.JSONObject
	" %>

<center>
<%
String type = request.getParameter("type");
if("andrawu".equals(type)){

		List<String> notDeleteNameList = new ArrayList<String>();
		notDeleteNameList.add("钉钉管理员");
		notDeleteNameList.add("运营管理中心");
		notDeleteNameList.add("品牌经营中心");
		notDeleteNameList.add("产品研发中心");
		notDeleteNameList.add("总裁办公室");
		notDeleteNameList.add("后勤保障中心");
		notDeleteNameList.add("运营客户服务部");
		notDeleteNameList.add("研究院");
		notDeleteNameList.add("KMS方案部");
		notDeleteNameList.add("平台架构部");
		notDeleteNameList.add("质量管理部");
		notDeleteNameList.add("EKP产品部");
		notDeleteNameList.add("UED部门");
		notDeleteNameList.add("云应用产品部");
		notDeleteNameList.add("人力行政部");

		String rootId = request.getParameter("rootId");
		String DING_OMS_ROOT_ORG_ID =DingConfig.newInstance().getDingOrgId();
		String DING_OMS_ROOT_FLAG =  DingConfig.newInstance().getDingOmsRootFlag();
		int DING_ROOT_DEPT_ID = 1;
		if(StringUtil.isNotNull(rootId)){
			DING_ROOT_DEPT_ID = Integer.parseInt(rootId);
		}

		DingApiService dingApiService = new DingApiServiceImpl();
		List<Integer> allIdList = new ArrayList<Integer>();
		List<Integer> allParentIdList = new ArrayList<Integer>();
		List<Integer> notDeleteIdList = new ArrayList<Integer>();
		notDeleteIdList.add(1);
		notDeleteIdList.add(-1);

		Map<Integer,JSONObject> dingDeptMap = new HashMap<Integer,JSONObject>();
		JSONObject retDept = dingApiService.departGet();
		if(retDept.getInt("errcode")==0){
			JSONArray depts = retDept.getJSONArray("department");
			for(int i=0;i<depts.size();i++){
				JSONObject dept = depts.getJSONObject(i);
				allIdList.add(dept.getInt("id"));
				dingDeptMap.put(dept.getInt("id"),dept);

				//out.print(dept+"<br>");

				if(dept.get("parentid")!=null){
					int parentId = dept.getInt("parentid");

					if(!allParentIdList.contains(parentId)){
						allParentIdList.add(parentId);
					}

				}

				if(notDeleteNameList.contains(dept.getString("name"))){
					notDeleteIdList.add(dept.getInt("id"));
				}

			}
		}
		
		out.print("notDeleteIdList: " +notDeleteIdList+"<br>");
		out.print("allParentIdList: " +allParentIdList+"<br>");
		
		/*
		List<String> deleteUserId = new ArrayList<String>();
		for(Integer deptId:allIdList){
			if(!notDeleteIdList.contains(deptId)){
				JSONObject retUser = dingApiService.userList(deptId, false);
				if (retUser.getInt("errcode") == 0) {
					JSONArray users = retUser.getJSONArray("userlist");
					for (int i = 0; i < users.size(); i++) {
						JSONObject user = users.getJSONObject(i);
						//out.print(user.get("userid")+"="+user.get("name")+";");
						deleteUserId.add(user.getString("userid"));
					}
				}
			}
		}
		out.print("deleteUserId: " +deleteUserId+"<br>");
		
		for(String userid:deleteUserId){
			String info = "";
			info+="删除 " +userid;
			info+= ", "+dingApiService.userDelete(userid);
			System.out.println(info);
			out.print(info+"<br>");
		}
		*/

		
		for(Integer deptId:allIdList){
			if(!allParentIdList.contains(deptId)){
				String info = "";
				info+="删除 " +dingDeptMap.get(deptId);
				info+= ", "+dingApiService.departDelete(""+deptId);
				System.out.println(info);
				out.print(info+"<br>");

			}
		}
	
}else{
%>

<form method="post">
<input type="password" name="type">
<input type="submit" value="提交">
</form>
<%
}
	
%>
</center>
