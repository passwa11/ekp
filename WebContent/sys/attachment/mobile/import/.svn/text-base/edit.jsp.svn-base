<%@page import="com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil"%>
<%@page import="com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationDirectService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" href="${LUI_ContextPath}/sys/attachment/mobile/css/edit.css">
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.metadata.interfaces.IExtendDataForm"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.util.DbUtils"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttPicUtils"%>
<%@page import="java.util.List"%>
<%@ page import="com.landray.kmss.sys.authentication.user.KMSSUser" %>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.lang.String" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.appconfig.service.ISysAppConfigService"%>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConstant"%>
<%
	//获取钉钉集成开关钉盘参数
	ISysAppConfigService sysAppConfigService = (ISysAppConfigService) SpringBeanUtil.getBean("sysAppConfigService");
	Map orgMap = sysAppConfigService.findByKey("com.landray.kmss.third.ding.model.DingConfig");
	if (orgMap != null) {
		if(orgMap.containsKey("dingEnabled")){
			request.setAttribute("dingEnabled", orgMap.get("dingEnabled"));
		}else{
			request.setAttribute("dingEnabled", false);
		}
		if(orgMap.containsKey("cspaceEnable")){
			request.setAttribute("cspaceEnable", orgMap.get("cspaceEnable"));
		}else{
			request.setAttribute("cspaceEnable", false);
		}
		if(orgMap.containsKey("dingCorpid")){
			request.setAttribute("dingCorpid", orgMap.get("dingCorpid"));
		}else{
			request.setAttribute("dingCorpid", "");
		}
	}else{
		request.setAttribute("dingEnabled", false);
		request.setAttribute("cspaceEnable", false);
		request.setAttribute("dingCorpid", "");
	}
%>

<c:if test="${empty tiny && empty JsParam._data }">
	<mui:cache-file name="mui-attachment.js" cacheType="md5"/>
</c:if>
<c:choose>
	<c:when test="${param.dTableType=='nonxform'}">
	</c:when>
	<c:otherwise>
		<c:set var="_fdKey" value="${param.fdKey}"/>
	</c:otherwise>
</c:choose>
<% 
KMSSUser user = UserUtil.getKMSSUser();
pageContext.setAttribute("currUserId", user.getUserId()); 
pageContext.setAttribute("isAdmin", user.isAdmin()); 
%>
<c:if test="${param.fdInDetail=='true'}">
	<%
		String dTableType=request.getParameter("dTableType");
		if("nonxform".equals(dTableType)){
			String idx=request.getParameter("idx");
			if(idx.indexOf("!{index}")==-1){
				String key=request.getParameter("fdKey");
				String fieldName=key.split("\\.")[1];
				String formListAttribute=request.getParameter("formListAttribute");
				String formName=request.getParameter("formName");
				IExtendForm formxx=(IExtendForm)request.getAttribute(formName);
				List formList= (List)PropertyUtils.getProperty(formxx,formListAttribute);
				IExtendForm listDetail=(IExtendForm)formList.get(Integer.parseInt(idx));
				String key2=(String)PropertyUtils.getProperty(listDetail,fieldName);
				if(StringUtil.isNull(fieldName)){
					key2="uid_"+IDGenerator.generateID();
				}
				pageContext.setAttribute("_fdKey", key2);
			}
		}else{
			IExtendDataForm form = (IExtendDataForm)request.getAttribute(request.getParameter("formName"));
			String key= request.getParameter("fdFiledName");
			key = key.replace("extendDataFormInfo.value(","").replace(")","");
			String[] keys = key.split("\\.");
			if("!{index}".equals(keys[1])){
				if("true".equals(String.valueOf(request.getAttribute("dingEnabled"))) && "true".equals(String.valueOf(request.getAttribute("cspaceEnable"))) ){
					pageContext.setAttribute("_fdKey", "att_"+IDGenerator.generateID());
				}else
					pageContext.setAttribute("_fdKey", "");
			}else{
			   	Map<String,Object> map = form.getExtendDataFormInfo().getFormData();
			    List list = (List)map.get(keys[0]);
			    if(!list.isEmpty()){
				    Map<String,Object> mapDetail=(Map<String,Object>)list.get(Integer.parseInt(keys[1]));
				    pageContext.setAttribute("_fdKey", mapDetail.get(keys[2]));
			    }else{
			    	 pageContext.setAttribute("_fdKey", "");
			    }
			}
		}
	%>
	
</c:if>

<c:if test="${param.formName!=null && param.formName!=''}">
 	<c:set var="_formBean" value="${requestScope[param.formName]}"/>
 	<c:set var="attForms" value="${_formBean.attachmentForms[_fdKey]}" />
 </c:if>
 
 <c:set var="_fdModelName" value="${param.fdModelName}"/>
 <c:if test="${_fdModelName==null || _fdModelName == ''}">
 	<c:if test="${_formBean!=null}">
 		<c:set var="_fdModelName" value="${_formBean.modelClass.name}"/>
 	</c:if>
 </c:if>
 
 <c:set var="_fdModelId" value="${param.fdModelId}"/>
 <c:if test="${_fdModelId==null || _fdModelId == ''}">
 	<c:if test="${_formBean!=null}">
 		<c:set var="_fdModelId" value="${_formBean.fdId}"/>
 	</c:if>
 </c:if>

<c:set var="_fdMulti" value="true"/>
<c:if test="${param.fdMulti!=null}">
	<c:set var="_fdMulti" value="${param.fdMulti}"/>
</c:if>

<%-- fdAttType: byte/pic--%>
<c:set var="_fdAttType" value="byte"/>
<c:if test="${param.fdAttType!=null}">
	<c:set var="_fdAttType" value="${param.fdAttType}"/>
</c:if>

<c:set var="_fdRequired" value="false"></c:set>
<c:if test="${param.fdRequired==true || param.fdRequired=='true'}">
	<c:set var="_fdRequired" value="true"/>
</c:if>

<%-- fdViewType: normal/simple--%>
<c:set var="_fdViewType" value="normal"></c:set>
<c:if test="${param.fdViewType!=null}">
	<c:set var="_fdViewType" value="${param.fdViewType}"/>
</c:if>

<c:set var="_extParam" value=""></c:set>
<c:if test="${param.extParam!=null}">
	<c:set var="_extParam" value="${param.extParam}"/>
</c:if>

<c:set var="_fdName" value=""></c:set>
<c:if test="${param.fdName!=null}">
	<c:set var="_fdName" value="${param.fdName}"/>
</c:if>

<c:set var="_subject" value="${ lfn:message('sys-attachment:mui.sysAttMain.button.upload') }"></c:set>
<c:if test="${param.fdAttType!=null&&param.fdAttType=='pic'}">
	<c:set var="_subject" value="${ lfn:message('sys-attachment:sysAttMain.button.img.upload') }"/>
</c:if>
<c:if test="${param.label!=null}">
	<c:set var="_subject" value="${param.label}"/>
</c:if>
<c:set var="_orient" value="none"></c:set>
<c:if test="${param.orient!=null}">
	<c:set var="_orient" value="${param.orient}"/>
</c:if>

<c:set var="_customSubject" value=""></c:set>
<c:if test="${param.customSubject!=null}">
	<c:set var="_customSubject" value="${param.customSubject}"/>
</c:if>

<c:set var="_customWarnTipMsg" value=""></c:set>
<c:if test="${param.customWarnTipMsg!=null}">
	<c:set var="_customWarnTipMsg" value="${param.customWarnTipMsg}"/>
</c:if>

<%--附件类型控制 --%>
<c:set var="_enabledFileType" value=""></c:set>
<c:if test="${param.enabledFileType!=null}">
	<c:set var="_enabledFileType" value="${param.enabledFileType}"/>
</c:if>

<c:set var="_capture" value=""></c:set>
<c:if test="${param.capture!=null}">
	<c:set var="_capture" value="${param.capture}"/>
</c:if>

<%-- 对齐方式 --%>
<c:set var="align" value="left"></c:set>
<c:if test="${param.align!=null}">
	<c:set var="align" value="${param.align}"/>
</c:if>

<c:set var="_supportCustom" value="false"/>
<c:if test="${param.align!=null}">
	<c:set var="_supportCustom" value="${param.supportCustom}"/>
</c:if>

<c:set var="_fdDisabledFileType" value=""/>
<% 
	if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.fileLimitType"))){
		pageContext.setAttribute("_fileLimitType",ResourceUtil.getKmssConfigString("sys.att.fileLimitType"));
		pageContext.setAttribute("_fdDisabledFileType",ResourceUtil.getKmssConfigString("sys.att.disabledFileType"));
	}else{
		pageContext.setAttribute("_fileLimitType","1");
		pageContext.setAttribute("_fdDisabledFileType",SysAttConstant.DISABLED_FILE_TYPE);
	}
    if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"))){
	pageContext.setAttribute("_fdSmallMaxSize",ResourceUtil.getKmssConfigString("sys.att.smallMaxSize"));
	}  
	  if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.imageMaxSize"))){
			pageContext.setAttribute("_fdImageMaxSize",ResourceUtil.getKmssConfigString("sys.att.imageMaxSize"));
    }
%>

	<%
		JSONObject jsonObj = new JSONObject();
		jsonObj.accumulate("fdKey",pageContext.getAttribute("_fdKey"));
		jsonObj.accumulate("name",pageContext.getAttribute("_fdName"));
		jsonObj.accumulate("fdFiledName",request.getParameter("fdFiledName"));
		jsonObj.accumulate("fdModelName",pageContext.getAttribute("_fdModelName"));
		jsonObj.accumulate("fdModelId",pageContext.getAttribute("_fdModelId"));
		jsonObj.accumulate("viewType",pageContext.getAttribute("_fdViewType"));
		jsonObj.accumulate("required","true".equals(pageContext.getAttribute("_fdRequired")));
		jsonObj.accumulate("editMode","edit");
		jsonObj.accumulate("extParam",pageContext.getAttribute("_extParam"));
		jsonObj.accumulate("fdAttType",pageContext.getAttribute("_fdAttType"));
		jsonObj.accumulate("fileLimitType",pageContext.getAttribute("_fileLimitType"));	
		jsonObj.accumulate("fdDisabledFileType",pageContext.getAttribute("_fdDisabledFileType"));
		jsonObj.accumulate("fdSmallMaxSize",pageContext.getAttribute("_fdSmallMaxSize"));
		jsonObj.accumulate("fdImageMaxSize",pageContext.getAttribute("_fdImageMaxSize"));
		jsonObj.accumulate("fdMulti","true".equals(pageContext.getAttribute("_fdMulti")));
		jsonObj.accumulate("subject",pageContext.getAttribute("_subject"));
		jsonObj.accumulate("orient",pageContext.getAttribute("_orient"));
		jsonObj.accumulate("capture",pageContext.getAttribute("_capture"));
		jsonObj.accumulate("align",pageContext.getAttribute("align"));
		jsonObj.accumulate("customWarnTipMsg",pageContext.getAttribute("_customWarnTipMsg"));
		jsonObj.accumulate("enabledFileType",pageContext.getAttribute("_enabledFileType"));
		String jsonProp = jsonObj.toString();
		pageContext.setAttribute("jsonProp",StringUtil.XMLEscape(jsonProp.substring(1,jsonProp.length()-1)));
		pageContext.setAttribute("required","true".equals(pageContext.getAttribute("_fdRequired")));
		pageContext.setAttribute("_fdAttType",pageContext.getAttribute("_fdAttType"));
	%>
	<div
		data-dojo-type="sys/attachment/mobile/js/AttachmentList"  <c:if test="${not empty param.widgitId }">id="${param.widgitId}"</c:if>
		data-dojo-props="${ jsonProp }">
		<div data-dojo-type="sys/attachment/mobile/js/AttachmentEvent"
			 data-dojo-props="hidePicName:'${param.hidePicName}',isResizeListView:${JsParam.isResizeListView == 'false' ? false : true}">
		</div>
		<c:forEach var="sysAttMain" items="${attForms.attachments}" varStatus="vsStatus">
			<c:set var="downLoadUrl" value="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMain.fdId}" />
			<c:if test="${_downLoadNoRight==true || _downLoadNoRight=='true'}">
				<c:set var="downLoadUrl" value="/third/pda/attdownload.jsp?fdId=${sysAttMain.fdId}" />
			</c:if>
			<%
				SysAttMain sysAtt = (SysAttMain) pageContext.getAttribute("sysAttMain");
				String escapeFileName = StringEscapeUtils.escapeJavaScript(sysAtt.getFdFileName());
				pageContext.setAttribute("escapeFileName", escapeFileName);
			%>
		<%
			SysAttMain sysAttMain = (SysAttMain) pageContext
						.getAttribute("sysAttMain");
				Boolean hasViewer = Boolean.FALSE;
				String viewPicHref = "/third/pda/attdownload.jsp?open=1";
				if (SysAttViewerUtil.isConverted(sysAttMain)
						&& sysAttMain.getFdContentType().indexOf("video") < 0
						|| sysAttMain.getFdContentType().indexOf("mp4") >= 0
						|| sysAttMain.getFdContentType().indexOf("m4v") >= 0
						|| sysAttMain.getFdContentType().indexOf("audio") >= 0) {
					hasViewer = Boolean.TRUE;
				}
				String extensionName = FilenameUtils
						.getExtension(sysAttMain.getFdFileName());
				if (MobileUtil.getClientType(request) >= 6
						&& ("pic".equals(sysAttMain.getFdAttType())
								|| SysAttPicUtils.isImageType(extensionName))) {
					long timestamp = DbUtils.getDbTimeMillis();
					viewPicHref = "/resource/pic/attachment.do?method=view";
					viewPicHref += "&t=" + String.valueOf(timestamp);
					viewPicHref += "&k=" + SysAttPicUtils.generate(
							String.valueOf(timestamp) + sysAttMain.getFdId());
				}
				pageContext.setAttribute("viewPicHref", viewPicHref);
				pageContext.setAttribute("hasViewer", hasViewer);
		%>
		
			<div data-dojo-type="sys/attachment/mobile/js/AttachmentEditListItem" 
				data-dojo-props="name:'${escapeFileName}',
					size:'${sysAttMain.fdSize}',
					href:'${downLoadUrl}',
					thumb:'/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId=${sysAttMain.fdId}',
					viewPicHref : '${viewPicHref }',
					hasViewer:${hasViewer},
					fdId:'${sysAttMain.fdId}',
					hidePicName:'${param.hidePicName}',
					showDeleteIcon:'${   param.allCanNotDelete eq 'true' ? isAdmin  :  ( param.otherCanNotDelete eq 'true' ? (sysAttMain.fdCreatorId eq currUserId || isAdmin) : true )  }',
					type:'${sysAttMain.fdContentType}'">
			</div>
		</c:forEach>
		
		<div data-dojo-type="sys/attachment/mobile/js/AttachmentOptListItem"
			data-dojo-props="_fdAttType:'${_fdAttType}',customSubject:'${_customSubject }',capture:'${_capture}',orient:'${_orient}',align:'${align}',supportCustom:'${_supportCustom}'">
		</div>

		<c:if test="${_fdAttType=='byte'}">
			<div class="muiFormItemDing" onclick="uploadFileToEkp('${ jsonProp }');">
				<img src="${KMSS_Parameter_ContextPath}sys/attachment/mobile/css/dingpan.svg" class="muiFormItemDingSpan">
			</div>
		</c:if>

		<%-- 兼容form.js--%>
		<c:if test="${param.fdInDetail=='true'}">
			<xform:text showStatus="noShow"  property="${param.fdFiledName}" value="${_fdKey}"/>
		</c:if>
	</div>
<%
		ISysFileLocationDirectService directService =
				SysFileLocationUtil.getDirectService();
		request.setAttribute("methodName", directService.getMethodName());
		request.setAttribute("uploadUrl", directService.getUploadUrl(request.getHeader("User-Agent")));
		request.setAttribute("isSupportDirect", directService.isSupportDirect(request.getHeader("User-Agent")));
		request.setAttribute("fileVal", directService.getFileVal());
	%>
<script>
	var attachmentConfig = {
		<%-- 上传路径--%>
		uploadurl: '${uploadUrl}',
		<%-- 上传方法名--%>
		methodName: '${methodName}',
		<%-- 是否支持直连模式--%>
		isSupportDirect: ${isSupportDirect},
		<%-- 文件key--%>
		fileVal: '${fileVal}'|| null
	}
	<%-- 控制钉盘附件上传图标是否展示--%>

	function renderDingIcon(devType) {
		var dingEnabled = '${dingEnabled}';//是否开启钉钉集成
		var cspaceEnable = '${cspaceEnable}';//是否开启钉盘
		if("true"==cspaceEnable && "true"==dingEnabled && 11 == devType){
			$(".muiFormItemDing").css("display","inline-block");
		}
	}

	require([ 'dojo/ready', 'mui/device/adapter', 'dojo/query', "dojo/request","mui/dialog/Tip","mui/device/device",'dojo/topic'],
			function(ready,adapter,query, request, Tip,device,topic){
				window.uploadFileToEkp = function(data){
					var prop =  eval("({"+data+"})");
					var options = {
						corpId:'${dingCorpid}',
						fdKey:prop.fdKey,
						fdModelId:prop.fdModelId,
						fdModelName:prop.fdModelName,
						fdAttType:prop.fdAttType
					};
					var result = adapter.downloadFromDing(options,prop);
				}

				window.onloading =function() {
					renderDingIcon(device.getClientType());
				}

				try{
					$(function() {
						onloading();
					});
				}catch (e) {
					require([ 'dojo/ready', 'mui/device/adapter', 'dojo/query', "dojo/request","mui/dialog/Tip","mui/device/device"],
							function(ready,adapter,query, request, Tip,device){
								window.initOnload = function(){
									renderDingIcon(device.getClientType());
								}
								Com_AddEventListener(window, "load", initOnload);
							});
				}

			})



	require([ 'dojo/ready', 'dojo/query', "mui/dialog/Tip", 'dojo/topic',"mui/util"],
			function(ready, query, Tip, topic,util){
				topic.subscribe('/third/ding/status/'+'${_fdKey}',function(val) {
					var isSuccess = false;
					var isBreak = false;

					var timestamp = (new Date()).getTime();
					$.ajaxSettings.async = false;
					while (true) {

						$.post(val.url, {
							'fdId': val.fdId,
							'key': 'ding',
							't': timestamp,
							'type': '1'
						}, function (result) {
							if (result.status == "0") {
								isSuccess = true;
								isBreak = true;
								val.tip.hide();
							} else if (result.status == "-1") {
								isBreak = true;
								val.tip.hide();
								Tip.fail({
									text: result.msg.errmsg
								});
							}
						});

						if (isBreak) {
							break;
						}

						val.obj.sleep(3000);

					}
					$.ajaxSettings.async = true;

					if (isSuccess) {
						val.attachment.startUploadFile({
							filePath: "dingUpload",
							status: "2",
							size: val.size,
							name: val.name,
							_fdAttId: val._fdAttId,
							isDing: true
						});
					}
				});


				topic.subscribe('/third/ding/del/'+'${_fdKey}',function(val) {
						if(val._srcObj.isDing){
							var delUrl = util.formatUrl("/sys/attachment/sys_att_main/sysAttMain.do?method=delFileRelation",true);
							$.post(delUrl, {
								'fdId': val._srcObj._fdAttId
							}, function (result) {
								if (result.flag == true) {
									val._this.removeChild(val._widget);
									val._widget.destroy();
									topic.publish("/mui/list/resize", val._this);
								} else {
									Tip.fail({
										text: "删除失败"
									});
								}
							});
						}

				});
			})


</script>
