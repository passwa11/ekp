<%@page import="com.landray.kmss.sys.attachment.util.SysAttPicUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List,java.util.ArrayList,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<%@page import="com.landray.kmss.sys.ui.plugin.SysUiPluginUtil"%>

<link rel="preload" type="text/css" href="<%=request.getContextPath()+"/"+SysUiPluginUtil.getThemesFileByName(request,"module")%>"></link>

<c:if test="${empty param.fdModelId}">
	<c:set var="fdKey" value="${param.fdKey}" />
</c:if>
<c:if test="${not empty param.fdModelId}">
	<!-- fdUID的作用主要是 用于在同一个页面中同一份附件需要重复显示，在审批意见展示中有应用场景 -->
	<c:set var="fdKey" value="${param.fdKey}_${param.fdModelId}${param.fdUID}" />
</c:if>
<c:set var="_htmlKey" value="${lfn:escapeHtml(fdKey)}" />
<c:set var="_jsKey" value="${lfn:escapeJs(fdKey)}" />
<script>
	if(typeof Com_Parameter.__sysAttMainlocale__ == "undefined")
		Com_Parameter.__sysAttMainlocale__= "<%= UserUtil.getKMSSUser(request).getLocale().toString().toLowerCase().replace('_', '-') %>";
</script>
<script>Com_IncludeFile("swf_attachment.js","${KMSS_Parameter_ContextPath}sys/attachment/js/","js",true);</script>
<%	
	Object formBean = null;
	String fromBeanVar = "com.landray.kmss.web.taglib.FormBean";
	String formBeanName = request.getParameter("formBeanName");
	//记录旧的formBean,主要是避免当前界面中对于formBean的修改，影响后续的使用
	Object originFormBean = pageContext.getAttribute(fromBeanVar);
	if(StringUtil.isNotNull(formBeanName)){
		formBean = pageContext.findAttribute(formBeanName);
	}else{
		formBean = pageContext.findAttribute(fromBeanVar);
	}
	//设置使用的formBean对象
	pageContext.setAttribute(fromBeanVar, formBean);
	pageContext.setAttribute("_formBean", formBean);
%>
<c:set var="attForms" value="${_formBean.attachmentForms[param.fdKey]}" />
<c:set var="sysAttMains" value="${attForms.attachments}" />

<%!
/*****************************************
功能：根据附件文件名后缀返回信息
参数：fileName 附件文件名
******************************************/
public String getIconNameByFileName(String fileName) {
	if(fileName==null || fileName=="" || fileName.lastIndexOf(".") == -1)
		return "documents.png";
	String fileExt = fileName.substring(fileName.lastIndexOf("."));
	if(fileExt!="")
		fileExt = fileExt.toLowerCase();
	if(".doc".equals(fileExt) || ".docx".equals(fileExt)){
		  return "word.png";
	}else if(".xls".equals(fileExt) || ".xlsx".equals(fileExt)){
		return "excel.png";
	}else if(".ppt".equals(fileExt) || ".pptx".equals(fileExt)){
		return "ppt.png";
	}else if(".rar".equals(fileExt) || ".zip".equals(fileExt) || ".tar".equals(fileExt) || ".gz".equals(fileExt)){
		return "zip.png";
	}else if(".pdf".equals(fileExt)){
		return "pdf.png";
	}else if(".txt".equals(fileExt)){
		return "text.png";
	}else if(".jpg".equals(fileExt) || ".jpeg".equals(fileExt)){
		return "jpg.png";
	}else if(".rm".equals(fileExt) || ".rmvb".equals(fileExt)){
		return "rmvb.png";
	}else if(".audio".equals(fileExt)){
		return "aud.png";
	}else if(".pst".equals(fileExt)){
		 return "outlook.png";
	}else{
		return fileExt.substring(1) + ".png";
	}
}
public String formatFileSize(double size){
	DecimalFormat decimalFormat = new DecimalFormat("0.##");
	String sizeVal = decimalFormat.format(size > (1024*1024) ? size / (1024*1024) : size > 1024 ? size / 1024 : size);
	String sizeUnit = (size > (1024*1024) ? "MB" : size > 1024 ? "KB" : "B");
	return sizeVal + sizeUnit;
}
%>
<%
	//以下代码用于附件不通过form读取的方式
	List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
	if(sysAttMains==null || sysAttMains.isEmpty()){
		try{
			String _modelName = request.getParameter("fdModelName");
			String _modelId = request.getParameter("fdModelId");
			if(StringUtil.isNotNull(_modelName) 
					&& StringUtil.isNotNull(_modelId)){
				String cacheKey = _modelName + "_" + _modelId;
				List cacheAtts = (List)request.getAttribute(cacheKey);
				if(cacheAtts!=null && !cacheAtts.isEmpty()){
					sysAttMains = cacheAtts;
				}else{
					String caheFlag = (String)request.getAttribute(cacheKey+"_flag");
					if(!"1".equals(caheFlag)){
						ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
						sysAttMains = ((ISysAttMainCoreInnerDao) sysAttMainService
								.getBaseDao()).findAttListByModel(_modelName,_modelId);
						request.setAttribute(cacheKey,sysAttMains);
						request.setAttribute(cacheKey+"_flag","1");
					}else{
						sysAttMains = new ArrayList();
					}
				}
				pageContext.setAttribute("sysAttMains",sysAttMains);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
%>
<%
	/*****************
	格式化显示信息
	******************/
	String fdAttType = request.getParameter("fdAttType");
	List<Map<String, Object>> showAtts = new ArrayList<Map<String, Object>>();
	if(sysAttMains != null && sysAttMains.size() > 0){
		Map<String, Object> attMaps;
		for(int i=0; i<sysAttMains.size(); i++){
			attMaps = new HashMap<String, Object>();
			SysAttMain sysAttMain = (SysAttMain)sysAttMains.get(i);
			attMaps.put("fdKey", sysAttMain.getFdKey());
			attMaps.put("type", getIconNameByFileName(sysAttMain.getFdFileName()));
			attMaps.put("fileName", sysAttMain.getFdFileName());
			attMaps.put("fileSize", formatFileSize(sysAttMain.getFdSize()));
			if("pic".equals(fdAttType)){
				String downLoadUrl = SysAttPicUtils.getPreviewUrl(request, sysAttMain.getFdId());
				downLoadUrl = request.getContextPath() + downLoadUrl;
				attMaps.put("downLoadUrl", downLoadUrl);
			}
			showAtts.add(attMaps);
		}
		pageContext.setAttribute("showAtts",showAtts);
	};
%>
<table style="border:none" width="100%" class="tb_noborder">
	<tbody>
	<c:if test="${param.fdAttType!='pic' }">
			<c:forEach items="${showAtts}" var="showAtt" varStatus="vstatus">
				<c:if test="${showAtt.fdKey==param.fdKey}">
					<tr class="upload_list_tr">
						<td class="upload_list_icon" width="32px">
							<img src="${LUI_ContextPath }/resource/style/common/fileIcon/${showAtt.type }" height="32"
								width="32" align="absmiddle"
								onerror="this.src='${LUI_ContextPath}/resource/style/common/fileIcon/documents.png'">
						</td>
						<td class="upload_list_filename_view">${showAtt.fileName}</td>
						<td class="upload_list_size" align="right">${showAtt.fileSize}</td>
					</tr>
				</c:if>
			</c:forEach>
		</c:if>
		<c:if test="${param.fdAttType=='pic' }">
			<tr>
				<td>
					<c:forEach var="showAtt" items="${showAtts}" varStatus="vsStatus">
						 <img style="width: 200px; height: 150px;" src="${showAtt.downLoadUrl}"/>
					</c:forEach>
				</td>
			</tr>
		</c:if>
	</tbody>
</table>
<%
	if (originFormBean != null) {
		pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean",
				originFormBean);
	}
%>