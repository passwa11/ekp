﻿<%@page import="com.landray.kmss.util.DbUtils"%>
<%@page import="org.apache.commons.io.FilenameUtils"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttPicUtils"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttConstant"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil"%>
<%@page import="java.util.List,com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.metadata.interfaces.IExtendDataForm"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.util.IDGenerator"%>
<%@ page import="com.landray.kmss.sys.filestore.util.SysFileStoreUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<c:if test="${empty tiny && empty JsParam._data }">
	<mui:cache-file name="mui-attachment.js" cacheType="md5"/>
</c:if>
<c:set var="_fdKey" value="${JsParam.fdKey}" />
<c:set var="_templateId" value="${JsParam._templateId}" />
<c:set var="_contentId" value="${JsParam._contentId}" />
<c:if test="${JsParam.fdInDetail=='true'}">
	<%	//明细表下的处理

		if(StringUtil.isNull(request.getParameter("idx"))
				|| request.getParameter("idx").indexOf("!{index}")!=-1){
			return;
		}
		String dTableType=request.getParameter("dTableType");
		if("nonxform".equals(dTableType)){
			String formListAttribute=request.getParameter("formListAttribute");
			String formName=request.getParameter("formName");
			IExtendForm formxx=(IExtendForm)request.getAttribute(formName);
			List formList= (List)PropertyUtils.getProperty(formxx,formListAttribute);
			String key= request.getParameter("fdFiledName");
			String[] keys = key.split("\\.");
			IExtendForm listDetail=(IExtendForm)formList.get(Integer.parseInt(keys[1]));
			String key2=(String)PropertyUtils.getProperty(listDetail,keys[2]);
			if(StringUtil.isNull(key2)){
				key2="uid_"+IDGenerator.generateID();
			}
			pageContext.setAttribute("_fdKey", key2);
		}else{
			IExtendDataForm form = (IExtendDataForm)request.getAttribute(request.getParameter("formName"));
			String key= request.getParameter("fdFiledName");
			key = key.replace("extendDataFormInfo.value(","").replace(")","");
			String[] keys = key.split("\\.");
			Map<String,Object> map = form.getExtendDataFormInfo().getFormData();
			List list = (List)map.get(keys[0]);
			Map<String,Object> mapDetail=(Map<String,Object>)list.get(Integer.parseInt(keys[1]));
			pageContext.setAttribute("_fdKey", mapDetail.get(keys[2]));
		}
	%>
</c:if>
<c:if test="${JsParam.formName!=null && JsParam.formName!=''}">
	<c:set var="_formBean" value="${requestScope[JsParam.formName]}" />
	<c:set var="attForms" value="${_formBean.attachmentForms[_fdKey]}" />
</c:if>

<c:set var="_fdModelName" value="${JsParam.fdModelName}" />
<c:if test="${_fdModelName==null || _fdModelName == ''}">
	<c:if test="${_formBean!=null}">
		<c:set var="_fdModelName" value="${_formBean.modelClass.name}" />
	</c:if>
</c:if>

<c:set var="_fdModelId" value="${JsParam.fdModelId}" />
<c:if test="${_fdModelId==null || _fdModelId == ''}">
	<c:if test="${_formBean!=null}">
		<c:set var="_fdModelId" value="${_formBean.fdId}" />
	</c:if>
</c:if>

<c:set var="_fdName" value=""></c:set>
<c:if test="${JsParam.fdName!=null}">
	<c:set var="_fdName" value="${JsParam.fdName}"/>
</c:if>

<c:set var="_fdMulti" value="false" />
<c:if test="${JsParam.fdMulti!=null}">
	<c:set var="_fdMulti" value="${JsParam.fdMulti}" />
</c:if>

<%-- fdAttType: byte/pic--%>
<c:set var="_fdAttType" value="byte" />
<c:if test="${JsParam.fdAttType!=null}">
	<c:set var="_fdAttType" value="${JsParam.fdAttType}" />
</c:if>

<%-- fdViewType: normal/simple--%>
<c:set var="_fdViewType" value="normal"></c:set>
<c:if test="${JsParam.fdViewType!=null}">
	<c:set var="_fdViewType" value="${JsParam.fdViewType}" />
</c:if>
<c:if test="${attForms!=null}">
	<c:set var="sysAttMains" value="${attForms.attachments}" />
</c:if>
<%
	//以下代码用于附件不通过form读取的方式
	List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
	pageContext.setAttribute("_downLoadNoRight",new com.landray.kmss.third.pda.model.PdaRowsPerPageConfig().getFdAttDownload());
	if(sysAttMains==null || sysAttMains.isEmpty()){
		try{
			String _modelName = request.getParameter("fdModelName");
			String _modelId = request.getParameter("fdModelId");
			String _key = request.getParameter("fdKey");
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
				//附件个数
				int attCount  = 0;
				for(int i=0;i<sysAttMains.size();i++){
					SysAttMain s = (SysAttMain)sysAttMains.get(i);
					if(_key.equals(s.getFdKey())){
						attCount++;
					}
				}
				pageContext.setAttribute("attCount",attCount);
				pageContext.setAttribute("sysAttMains",sysAttMains);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}else{
		//附件个数
		pageContext.setAttribute("attCount",sysAttMains.size());
	}
	Map<String,Boolean> convertedResult = SysAttViewerUtil.getConvertedResult(sysAttMains);
	pageContext.setAttribute("convertedResult",convertedResult);
	pageContext.setAttribute("wpsWebOfficeEnable", SysAttWpsWebOfficeUtil.isEnable());
	pageContext.setAttribute("wpsCenterWebOfficeEnable", SysAttWpsCenterUtil.isWPSCenterEnableMobile(true));
	pageContext.setAttribute("wpsCloudOfficeEnable", SysAttWpsCloudUtil.isEnable());
	pageContext.setAttribute("wpsCloudOfficeEnableEdit", SysAttWpsCloudUtil.isEnable(true));
	pageContext.setAttribute("wpsCloudOfficeEnableMobileEdit", SysAttWpsCloudUtil.isEnableMobile(true));
	pageContext.setAttribute("wpsPreviewIsLinux", SysAttWpsCloudUtil.checkWpsPreviewIsLinux());
	pageContext.setAttribute("wpsPreviewIsWindows", SysAttWpsCloudUtil.checkWpsPreviewIsWindows());
	pageContext.setAttribute("readOLConfig", SysAttConfigUtil.getReadOLConfig());
%>
<div class="muiAttachments muiAttachments${_fdViewType}">
	<c:if test="${_fdViewType=='normal' }">
		<c:if test="${attCount > 0}">
			<div data-dojo-type="sys/attachment/mobile/js/AttachmentViewListItem"
				 data-dojo-props="name:'<bean:message bundle="sys-attachment" key="mui.sysAttMain.label" />：<bean:message bundle="sys-attachment" key="mui.sysAttMain.total" /> ${attCount}<bean:message bundle="sys-attachment" key="mui.sysAttMain.pieces" />',viewType:'${_fdViewType}',icon:'mui mui-attach'"
				 class="muiAttTitle"></div>
		</c:if>
	</c:if>
	<%-- 查看状态，即使不含任何附件，也需要存在组件，跟其他组件保持一致 by zhugr 2018-07-02 --%>
	<div data-dojo-type="sys/attachment/mobile/js/AttachmentList"
		 data-dojo-props="name:'${_fdName}',fdKey:'${_fdKey}',fdModelName:'${_fdModelName}',fdModelId:'${_fdModelId}',viewType:'${_fdViewType}',editMode:'view', _templateId:'${_templateId}',_contentId:'${_contentId}'">
		<c:forEach var="sysAttMain" items="${sysAttMains}" varStatus="vsStatus">
			<c:if test="${sysAttMain.fdKey==_fdKey}">
				<%
					SysAttMain sysAttMain = (SysAttMain) pageContext.getAttribute("sysAttMain");
					String escapeFileName = StringEscapeUtils.escapeJavaScript(sysAttMain.getFdFileName());
					pageContext.setAttribute("escapeFileName", escapeFileName);
					// 根据客户端状况构建预览图片的url zhanlei
					String viewPicHref = "/third/pda/attdownload.jsp?open=1";
					String extensionName = FilenameUtils.getExtension(sysAttMain.getFdFileName());
					if(MobileUtil.getClientType(request) >= 6
							&& ("pic".equals(sysAttMain.getFdAttType()) || SysAttPicUtils.isImageType(extensionName))  ){
						Object timestampObj = pageContext.getAttribute("timestampStr");
						Object generateObj = pageContext.getAttribute("generateStr");
						String timestampStr = null;
						String generateStr = null;
						//#103498 修改说明：如果有多张图片，就会有多次请求。因此请求的时间戳和加密参数需要改变。否则，则无权访问。
						if (timestampObj == null) {
							//long timestamp = DbUtils.getDbTimeMillis();
							timestampStr = String.valueOf(DbUtils.getDbTimeMillis());
							generateStr = SysAttPicUtils.generate(timestampStr + sysAttMain.getFdId());
							pageContext.setAttribute("timestampStr", timestampStr);
							pageContext.setAttribute("generateStr", generateStr);
						} else {
							timestampStr = String.valueOf(DbUtils.getDbTimeMillis());
							generateStr = SysAttPicUtils.generate(timestampStr + sysAttMain.getFdId());
						}
						viewPicHref = "/resource/pic/attachment.do?method=view";
						viewPicHref += "&t=" + timestampStr;
						viewPicHref += "&k=" + generateStr;
					}
					pageContext.setAttribute("viewPicHref", viewPicHref);
				%>
				<c:if test="${_fdAttType=='pic'}">
					<div
							data-dojo-type="sys/attachment/mobile/js/AttachmentViewPicItem"
							data-dojo-props="name:'${escapeFileName}',
								size:'${sysAttMain.fdSize}',
								viewPicHref : '${viewPicHref}',
								hidePicName:'${JsParam.hidePicName}',
								mobilePicDisplaythumb:'${JsParam.mobilePicDisplaythumb}',
								fdId:'${sysAttMain.fdId}'">
					</div>
				</c:if>

				<c:if test="${_fdAttType!='pic'}">
					<c:set value="false" var="canDownload"></c:set>
					<c:set value="false" var="downLoadNoRight"></c:set>
					<%--下载权限  --%>
					<kmss:auth
							requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMain.fdId}"
							requestMethod="GET">
						<c:set value="true" var="canDownload"></c:set>
					</kmss:auth>
					<c:set value="false" var="canRead"></c:set>
					<%--查看权限  --%>
					<kmss:auth
							requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${sysAttMain.fdId}"
							requestMethod="GET">
						<c:set value="true" var="canRead"></c:set>
					</kmss:auth>
					<c:set  var="canEdit" value="false"></c:set>
					<%--编辑权限  --%>
					<kmss:auth
							requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=edit&v=true&fdId=${sysAttMain.fdId}"
							requestMethod="GET">
						<c:set  var="canEdit" value="true"></c:set>
					</kmss:auth>
					<%--当开启了公文的附件选项中的编辑附件权限  --%>
					<c:if test="${JsParam.imissiveCanEdit==true}">
						<c:set  var="canEdit" value="true"></c:set>
					</c:if>

					<c:if test="${canDownload != true }">
						<c:if test="${_downLoadNoRight==true || _downLoadNoRight=='true'}">
							<c:set value="true" var="downLoadNoRight"></c:set>
							<c:set value="true" var="canDownload"></c:set>
						</c:if>
					</c:if>
					<%
						Map<String,Boolean> cr = (Map<String,Boolean>) pageContext.getAttribute("convertedResult");
						Boolean hasViewer = Boolean.FALSE;
						String crKey = StringUtil.isNotNull(sysAttMain.getFdFileId()) ? sysAttMain.getFdFileId() : sysAttMain.getFdId();
						Boolean attCr = cr.get(crKey);
						if (attCr != null && attCr.equals(Boolean.TRUE)
								&& sysAttMain.getFdContentType().indexOf("video") < 0
								|| sysAttMain.getFdFileName().indexOf("mp4") >= 0
								|| sysAttMain.getFdFileName().indexOf("m4v") >= 0
								|| sysAttMain.getFdFileName().indexOf("flv") >= 0
								|| sysAttMain.getFdFileName().indexOf("f4v") >= 0
								|| sysAttMain.getFdFileName().indexOf("ogg") >= 0
								|| sysAttMain.getFdFileName().indexOf("3gp") >= 0
								|| sysAttMain.getFdFileName().indexOf("avi") >= 0
								|| sysAttMain.getFdFileName().indexOf("wmv") >= 0
								|| sysAttMain.getFdFileName().indexOf("asx") >= 0
								|| sysAttMain.getFdFileName().indexOf("asf") >= 0
								|| sysAttMain.getFdFileName().indexOf("mpg") >= 0
								|| sysAttMain.getFdFileName().indexOf("mov") >= 0
								|| sysAttMain.getFdFileName().indexOf("rm") >= 0
								|| sysAttMain.getFdFileName().indexOf("rmvb") >= 0
								|| sysAttMain.getFdFileName().indexOf("wmv9") >= 0
								|| sysAttMain.getFdFileName().indexOf("wrf") >= 0
								|| sysAttMain.getFdContentType().indexOf("audio") >= 0) {
							hasViewer = Boolean.TRUE;
						}

						String pcPreviewType=SysAttConfigUtil.getOnlineToolType();
						boolean attConvertEnable = SysFileStoreUtil.isAttConvertEnable();
						if(attConvertEnable&&SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_JG.equals(pcPreviewType)&&SysAttConstant.MOBILE_NONE.equals(SysAttConfigUtil.getMobileOnlineToolType())){
							hasViewer = Boolean.TRUE;
						}

						pageContext.setAttribute("hasViewer", hasViewer);
					%>
					<div
							data-dojo-type="sys/attachment/mobile/js/AttachmentViewListItem"
							data-dojo-props="name:'${escapeFileName}',
								size:'${sysAttMain.fdSize}',
								viewPicHref : '${viewPicHref }',
								fdId:'${sysAttMain.fdId}',
								type:'${sysAttMain.fdContentType}',
								hasViewer:${hasViewer},
								canEdit:${canEdit},
								canDownload:${canDownload},
								downLoadNoRight:${downLoadNoRight},
								canRead:${canRead},
								wpsWebOfficeEnable:${wpsWebOfficeEnable},
								wpsCloudOfficeEnable:${wpsCloudOfficeEnable},
								wpsCenterWebOfficeEnable:${wpsCenterWebOfficeEnable},
								wpsCloudOfficeEnableEdit:${wpsCloudOfficeEnableEdit},
								wpsCloudOfficeEnableMobileEdit:${wpsCloudOfficeEnableMobileEdit},
								wpsPreviewIsLinux:${wpsPreviewIsLinux},
								wpsPreviewIsWindows:${wpsPreviewIsWindows},
								readOLConfig:${readOLConfig}">
					</div>
				</c:if>
			</c:if>
		</c:forEach>
	</div>
</div>
