
<%@page import="net.sf.json.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.xform.base.service.controls.auditshow.ISysXformAuditshowService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@page import="com.landray.kmss.sys.xform.base.model.controls.auditshow.SysXformAuditshow"%>
<%@page import="com.landray.kmss.sys.lbpmservice.taglib.auditnote.AuditNoteStyle"%>

<%
//获取样式扩展点
IExtension[] extensions = Plugin.getExtensions(
		"com.landray.kmss.sys.xform.jsp.auditnote.viewstyle", "*");

List<Map<String,String>> list = new ArrayList<Map<String,String>>();
if(extensions !=null){
	for(int i=0;i<extensions.length;i++){
		Map<String,String> mapElement=new HashMap<String,String>();
		
		Object viewName =Plugin.getParamValue(extensions[i],"viewName");
		//获取字符串BeanId
		Object viewValue =Plugin.getParamValueString(extensions[i],"viewValue");
		Object order =Plugin.getParamValue(extensions[i],"order");
		Object previewPictureURL =Plugin.getParamValue(extensions[i],"previewPictureURL");
		
		
		mapElement.put("viewName",null==viewName?"":ResourceUtil.getString((String)viewName,request.getLocale()));
		mapElement.put("viewValue",null==viewValue?"":(String)viewValue);
		//确保没有输入序号的扩展排到最后
		mapElement.put("order",null==order?"100":(String)order);
		mapElement.put("previewPictureURL",null==previewPictureURL?"":(String)previewPictureURL);
		
		if(null!=viewValue){
			AuditNoteStyle auditNoteStyle = (AuditNoteStyle) SpringBeanUtil
					.getBean(viewValue.toString());
			if(viewValue.equals("auditNoteStyleDefaultShowOnlyHandler")){
				mapElement.put("styleHtml","<br/><br/><br/>"+auditNoteStyle.getStyleHTML());
			}else{
				mapElement.put("styleHtml",auditNoteStyle.getStyleHTML());
			}
		}
		list.add(mapElement);
	}
}

//自定义样式
ISysXformAuditshowService sysXformAuditshowService = (ISysXformAuditshowService) SpringBeanUtil.getBean("sysXformAuditshowService");
HQLInfo hqlInfo = new HQLInfo();
hqlInfo.setWhereBlock("fdIsenabled=1");
try {
	List<SysXformAuditshow> l = (List<SysXformAuditshow>) (sysXformAuditshowService.findList(hqlInfo));
	for(SysXformAuditshow auditshow:l){
		Map<String,String> mapElement2=new HashMap<String,String>();
		
		mapElement2.put("viewName",null==auditshow.getFdName()?"":auditshow.getFdName());
		mapElement2.put("viewValue",null==auditshow.getFdId()?"":"Custom-"+auditshow.getFdId());
		//确保没有输入序号的扩展排到最后
		mapElement2.put("order",null==auditshow.getFdOrder()?"100":auditshow.getFdOrder()+"");
		mapElement2.put("previewPictureURL","style/img/auditshow/custom.png");
		mapElement2.put("styleHtml",auditshow.getFdContent());
		list.add(mapElement2);
	}
} catch (Exception e) {
	e.printStackTrace();
}

//排序
for(int i=0;i<list.size();i++){
	Map<String,String> mapI=list.get(i);
	float mapIOrder=Float.valueOf(mapI.get("order"));
	for(int j=i;j<list.size();j++){
		Map<String,String> mapJ=list.get(j);
		float mapJOrder=Float.valueOf(mapJ.get("order"));
		if(mapJOrder<mapIOrder){
			Map<String,String> mapTemp=mapI;
			mapI=mapJ;
			mapIOrder=mapJOrder;//更新order
			mapJ=mapTemp;
			list.set(i,mapI);
			list.set(j,mapJ);//不跳出，一直到找出最小的放在前面
		}
	}
}
//构建JSON数组，相应给客户端
out.print(JSONArray.fromObject(list));
%>