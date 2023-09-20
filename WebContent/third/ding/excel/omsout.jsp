<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@ page import="org.apache.poi.poifs.filesystem.POIFSFileSystem"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.util.HQLUtil"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@ page import="com.landray.kmss.sys.organization.service.ISysOrgElementService"%>
<%@ page import="com.landray.kmss.sys.organization.service.ISysOrgPostService"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgPost"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgPerson"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgDept"%>
<%@ page import="com.landray.kmss.sys.organization.service.ISysOrgDeptService"%>
<%@ page import="com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService"%>
<%@ page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<%! 

	//获取行
	private HSSFRow getRow(HSSFSheet sheet,int rowNumber){
		HSSFRow row = sheet.getRow(rowNumber);
		if(row == null){
			row = sheet.createRow(rowNumber);
		}
		return row;
	}
	
	//写行
	private void writeRow(SysOrgPerson person,HSSFRow row){
		Map<String,Object> options = new HashMap<String,Object>();
		options.put("isLeader", false);
		options.put("parentName", null);
		writeRow(person,row,options);
	}

	private void writeRow(SysOrgPerson person,HSSFRow row,Map<String,Object> options){
		boolean isLeader = (Boolean)options.get("isLeader");
		String parentName = (String) options.get("parentName");
		String postName = (String) options.get("postName");
		row.createCell(0).setCellValue(person.getFdLoginName());
		if(StringUtil.isNotNull(parentName)){
			row.createCell(1).setCellValue(parentName);
		}else{
			row.createCell(1).setCellValue(person.getFdParentsName("-"));
		}
		if(StringUtil.isNotNull(postName)){
			row.createCell(2).setCellValue(postName);
		}else{
			row.createCell(2).setCellValue("");
		}
		row.createCell(3).setCellValue(person.getFdName());
		row.createCell(4).setCellValue(person.getFdSex());
		row.createCell(5).setCellValue(person.getFdNo());
		row.createCell(6).setCellValue(isLeader ? "是" : "否");
		row.createCell(7).setCellValue(person.getFdMobileNo());
		row.createCell(8).setCellValue(person.getFdEmail());
		row.createCell(9).setCellValue("");
	}

	//获得not in查询语句
	private String buildLogicNotIN(String item, List valueList) {
		int n = (valueList.size() - 1) / 1000;
		StringBuffer rtnStr = new StringBuffer();
		Object obj = valueList.get(0);
		boolean isString = false;
		if (obj instanceof Character || obj instanceof String)
			isString = true;
		String tmpStr;
		for (int i = 0; i <= n; i++) {
			int size = i == n ? valueList.size() : (i + 1) * 1000;
			if (i > 0)
				rtnStr.append(" or ");
			rtnStr.append(item + " not  in (");
			if (isString) {
				StringBuffer tmpBuf = new StringBuffer();
				for (int j = i * 1000; j < size; j++) {
					tmpStr = valueList.get(j).toString().replaceAll("'", "''");
					tmpBuf.append(",'").append(tmpStr).append("'");
				}
				tmpStr = tmpBuf.substring(1);
			} else {
				tmpStr = valueList.subList(i * 1000, size).toString();
				tmpStr = tmpStr.substring(1, tmpStr.length() - 1);
			}
			rtnStr.append(tmpStr);
			rtnStr.append(")");
		}
		if (n > 0)
			return "(" + rtnStr.toString() + ")";
		else
			return rtnStr.toString();
	}
%>
<%
	
	if("true".equals(request.getParameter("downloading"))){
		
		if("true".equals(request.getParameter("withOutLock"))){
			application.setAttribute("__oms__out__excel__lock", new Boolean(false));
		}
		boolean lock = application.getAttribute("__oms__out__excel__lock") !=null ? (Boolean)application.getAttribute("__oms__out__excel__lock") : false;
		if(lock){
			out.write("已有下载任务,请耐心等候...");
			return;
		}else{
			application.setAttribute("__oms__out__excel__lock", new Boolean(true));
		}
		
		int nowRow = 3;
		int count = 100;//单次从数据库取数据最大数量
		
		//获取excel信息
		FileInputStream fs = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		try{
			fs = new FileInputStream(request.getRealPath("") + "/third/ding/excel/omsout.xls");
			POIFSFileSystem ps = new POIFSFileSystem(fs);
			wb = new HSSFWorkbook(ps);
			sheet=wb.getSheetAt(0);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(fs != null){
				fs.close();
			}
		}
		
		ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService)SpringBeanUtil.getBean("sysOrgCoreService");
		List<String> leaderIds = new ArrayList<String>();
		//从部门中取出所有领导
		ISysOrgDeptService sysOrgDeptService = (ISysOrgDeptService)SpringBeanUtil.getBean("sysOrgDeptService");
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgDept.fdIsAvailable = 1");
		List<SysOrgDept> depts = sysOrgDeptService.findList(hqlInfo);
		for(int i = 0;i < depts.size();i++){
			SysOrgDept dept = (SysOrgDept)depts.get(i);
			if(dept.getLeader(0) != null && !leaderIds.contains(dept.getLeader(0).getFdId())){
				List<SysOrgElement> leaderElems = new ArrayList<SysOrgElement>();
				leaderElems.add(dept.getLeader(0));
				List<SysOrgPerson> ls = sysOrgCoreService.expandToPerson(leaderElems);
				for(SysOrgPerson leader : ls){
					String parentName = StringUtil.linkString(dept.getFdParentsName("-"), "-", dept.getFdName());
					if(StringUtil.isNotNull(request.getParameter("company"))){
						parentName = StringUtil.linkString(request.getParameter("company"), "-", parentName);
					}
					
					Map<String,Object> options = new HashMap<String,Object>();
					options.put("isLeader", true);
					options.put("parentName", parentName);
					
					writeRow(leader, getRow(sheet, nowRow), options);
					nowRow++;
					leaderIds.add(dept.getLeader(0).getFdId());
				}
			}
		}
		//从岗位中取出所有领导
		ISysOrgPostService sysOrgPostService = (ISysOrgPostService)SpringBeanUtil.getBean("sysOrgPostService");
		hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("sysOrgPost.fdIsAvailable = 1");
		List<SysOrgPost> posts = sysOrgPostService.findList(hqlInfo);
		for(int i = 0;i < posts.size();i++){
			SysOrgPost post = posts.get(i);
			if(post.getLeader(0) != null){
				List<SysOrgElement> leaderElems = new ArrayList<SysOrgElement>();
				leaderElems.add(post.getLeader(0));
				List<SysOrgPerson> ls = sysOrgCoreService.expandToPerson(leaderElems);
				for(SysOrgPerson leader : ls){
					String parentName = post.getFdParentsName("-");
					if(StringUtil.isNotNull(request.getParameter("company"))){
						parentName = StringUtil.linkString(request.getParameter("company"), "-", parentName);
					}
					Map<String,Object> options = new HashMap<String,Object>();
					options.put("isLeader", true);
					options.put("parentName", parentName);
					options.put("postName", post.getFdName());
					writeRow(leader, getRow(sheet, nowRow), options);
					nowRow++;
					leaderIds.add(leader.getFdId());
				}
			}
		}
		
		ISysOrgElementService sysOrgElementService = (ISysOrgElementService)SpringBeanUtil.getBean("sysOrgElementService");
		hqlInfo = new HQLInfo();
		String whereblock = "(sysOrgElement.fdOrgType = 8 and sysOrgElement.fdIsAvailable = 1 and sysOrgElement.fdIsBusiness = 1)";
		whereblock = StringUtil.linkString(whereblock, " and ", buildLogicNotIN("sysOrgElement.fdId", leaderIds));
		hqlInfo.setWhereBlock(whereblock);
		List<SysOrgElement> elements = sysOrgElementService.findList(hqlInfo);
		
		//员工导入excel...
		for(SysOrgElement element : elements){
	   		SysOrgPerson person = (SysOrgPerson)sysOrgCoreService.format(element);
	    	HSSFRow row = sheet.getRow(nowRow);
	    	if(row == null){
	    		row = sheet.createRow(nowRow);
	    	}
	    	String parentName = person.getFdParentsName("-");
	    	if(StringUtil.isNotNull(request.getParameter("company"))){
				parentName = StringUtil.linkString(request.getParameter("company"), "-", parentName);
			}
	    	Map<String,Object> options = new HashMap<String,Object>();
			options.put("isLeader", false);
			options.put("parentName", parentName);
	    	
	    	writeRow(person, row , options);
	    	nowRow ++ ;
	    }
		
		//下载excel
		String filename = "omsout_" + new Date().getTime() + ".xls";
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
					+ filename);
		ServletOutputStream sos = response.getOutputStream();
		try {
			wb.write(sos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			application.setAttribute("__oms__out__excel__lock", new Boolean(false));
			sos.flush();
			sos.close();
			out.clear();
			out = pageContext.pushBody();  
		}
	}
%>
<c:if test="${empty param.downloading }">
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	</head>
	<body>
		<center>等待excel下载.....</center>
		<script type="text/javascript">
			var url = location.href;
			url = Com_SetUrlParameter(url,'downloading','true');
			location.href = url;
		</script>
	</body>
</html>
</c:if>