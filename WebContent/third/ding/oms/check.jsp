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
		String rootId = request.getParameter("rootId");
		String DING_OMS_ROOT_ORG_ID =DingConfig.newInstance()().getDingOrgId();
		String DING_OMS_ROOT_FLAG = DingConfig.newInstance().getDingOmsRootFlag();
		int DING_ROOT_DEPT_ID = 1;
		if(StringUtil.isNotNull(rootId)){
			DING_ROOT_DEPT_ID = Integer.parseInt(rootId);
		}
		List<SysOrgElement> rootDeptChildren = new ArrayList<SysOrgElement>();
		List<SysOrgPerson> rootPersonChildren = new ArrayList<SysOrgPerson>();

		ISysOrgCoreService	sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
					.getBean("sysOrgCoreService");
		ISysOrgElementService	sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
					.getBean("sysOrgElementService");
		ISysOrgPersonService	sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
					.getBean("sysOrgPersonService");

		DingApiService dingApiService = new DingApiServiceImpl();

		List<SysOrgElement> allOrgInRootOrg = new ArrayList<SysOrgElement>();
		List allOrgChildren = sysOrgElementService.findList(
				"(fdOrgType=1) and fdIsAvailable=1", null);
		for (int i = 0; i < allOrgChildren.size(); i++) {
			SysOrgElement org = (SysOrgElement) allOrgChildren.get(i);
			if (StringUtil.isNotNull(DING_OMS_ROOT_ORG_ID)) {
				SysOrgElement parent = org.getFdParent();
				while (parent != null) {
					if (parent.getFdId().equals(DING_OMS_ROOT_ORG_ID)) {
						allOrgInRootOrg.add(org);
						break;
					}
					parent = parent.getFdParent();
				}
			}else{
				allOrgInRootOrg.add(org);
			}
		}

		if (StringUtil.isNotNull(DING_OMS_ROOT_ORG_ID)) {
			SysOrgElement rootOrg = sysOrgCoreService
					.findByPrimaryKey(DING_OMS_ROOT_ORG_ID);

			if(!allOrgInRootOrg.contains(rootOrg)){
				allOrgInRootOrg.add(rootOrg);
			}

			for(SysOrgElement org :allOrgInRootOrg){
				rootDeptChildren.addAll(sysOrgCoreService.findAllChildren(org,
						SysOrgElement.ORG_TYPE_ORGORDEPT));
				rootPersonChildren.addAll(sysOrgCoreService.findAllChildren(org,
								SysOrgElement.ORG_TYPE_PERSON));
			}

		}else{
			rootDeptChildren = sysOrgElementService.findList("(fdOrgType=1 or fdOrgType=2) and fdIsAvailable=1",
					null);
			rootPersonChildren = sysOrgPersonService.findList("fdIsAvailable=1",null);
		}
		
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
	List<SysOrgElement> noInDingDept = new ArrayList<SysOrgElement>();
	List<SysOrgPerson> noInDingPerson = new ArrayList<SysOrgPerson>();

	if(rootDeptChildren!=null){
		out.println("<table border=1>");
		out.println("<tr><td colspan='3'>能关联的部门</td><tr>");
		for(int i=0;i<rootDeptChildren.size();i++){
			SysOrgElement dept = (SysOrgElement)rootDeptChildren.get(i);
			if(dingDeptMap.keySet().contains(dept.getFdName())){
				out.println("<tr>");
				out.println("<td>"+dept.getFdName()+"</td>");
				out.println("<td>"+dept.getFdId()+"</td>");
				out.println("<td>"+dingDeptMap.get(dept.getFdName())+"</td>");
				out.print("</tr>");
			}else{
				noInDingDept.add(dept);
			}
		}
		out.println("</table>");
	}

	out.println("<br>");
	
	if(rootPersonChildren!=null){
		out.println("<table border=1>");
		out.println("<tr><td colspan='3'>能关联的个人</td><tr>");
		for(int i=0;i<rootPersonChildren.size();i++){
			SysOrgPerson person = (SysOrgPerson)rootPersonChildren.get(i);
			if(dingPersonList.contains(person.getFdLoginName())){
				out.println("<tr>");
				out.println("<td>"+person.getFdName()+"</td>");
				out.println("<td>"+person.getFdId()+"</td>");
				out.println("<td>"+person.getFdLoginName()+"</td>");
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
			SysOrgElement dept = (SysOrgElement)noInDingDept.get(i);
			out.println("<tr>");
			out.println("<td>"+dept.getFdName()+"</td>");
			out.println("<td>"+dept.getFdId()+"</td>");
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
			SysOrgPerson person = (SysOrgPerson)noInDingPerson.get(i);
			out.println("<tr>");
			out.println("<td>"+person.getFdName()+"</td>");
			out.println("<td>"+person.getFdId()+"</td>");
			out.println("<td>"+person.getFdLoginName()+"</td>");
			out.print("</tr>");
		}
		out.println("</table>");
	}

%>
</center>
