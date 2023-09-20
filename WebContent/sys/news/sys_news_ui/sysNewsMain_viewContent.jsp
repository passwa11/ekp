<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<%@ page import="java.util.List"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConstant" %>
<%@ page import="com.landray.kmss.sys.attachment.model.SysAttConfig" %>
<%@ page import="java.util.Map" %>
<%
	Boolean _isWpsWebOffice = false;
	boolean existViewPath = JgWebOffice.isExistViewPath(request);
	String onlineToolType = SysAttConfigUtil.getOnlineToolType();
	Boolean _isWpsCloudEnable = false;
	Boolean _isWpsCenterEnable = false;
	Boolean _isFoxitEnable = false;
	String readOLConfig = SysAttConfigUtil.getReadOLConfig();
	if ("3".equals(onlineToolType) && "1".equals(readOLConfig) && !existViewPath) {
		//wps加载项+aspose，文件没有转换完成时，使用加载项
		_isWpsWebOffice = true;
	}
	if ("3".equals(readOLConfig)) {
		_isWpsCloudEnable = true;
	} else if ("5".equals(readOLConfig)) {
		_isWpsCenterEnable = true;
	} else if ("6".equals(readOLConfig)) {
		_isFoxitEnable = true;
	}
	pageContext.setAttribute("_wpsoaassist", "false");
	if (SysAttConstant.ATTCONFIG_ONLINETOOLTYPE_WPSWPSOAASSIST.equals(SysAttConfigUtil.getOnlineToolType())) {
		Map map = new SysAttConfig().getDataMap();
		if (map == null || map.isEmpty() || "0".equals(map.get("wpsoaassistEmbed"))) {
				pageContext.setAttribute("_wpsoaassist", "true");
		}
	}
	pageContext.setAttribute("_isWpsCloudEnable", _isWpsCloudEnable);
	pageContext.setAttribute("_isWpsWebOffice", _isWpsWebOffice);
	pageContext.setAttribute("_isWpsCenterEnable", _isWpsCenterEnable);
	pageContext.setAttribute("_isFoxitEnable", _isFoxitEnable);
%>
<script>
	Com_IncludeFile('view.css','${KMSS_Parameter_ContextPath}sys/news/resource/css/','css',true);
</script>
<c:choose>
	<c:when test="${param.contentType eq 'xform'}">
		<%-- <ui:content title="${ lfn:message('km-archives:py.JiBenXinXi') }" toggle="false"> --%>
		<!--标题-->
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				<c:if test="${sysNewsMainForm.fdIsTop==true}">
					<i class="lui_article_status_top_border lui_article_status_top"><bean:message key="news.fdIsTop.true" bundle="sys-news"/></i>
				</c:if>
				<bean:write name="sysNewsMainForm" property="docSubject" />
			</div>
			<div class='lui_form_baseinfo'>
				<!--发布日期-->
				<c:if test="${ not empty sysNewsMainForm.docPublishTime }">
					<span><bean:write name="sysNewsMainForm"
							property="docPublishTime" /></span>
				</c:if>
				<!--作者-->
				<bean:message bundle="sys-news" key="sysNewsMain.publisher" />
				：
				<c:if test="${sysNewsMainForm.fdIsWriter==false}">
					<span><ui:person personId="${sysNewsMainForm.fdAuthorId}"
							personName="${sysNewsMainForm.fdAuthorName}" layer="true"></ui:person></span>
				</c:if>
				<c:if test="${sysNewsMainForm.fdIsWriter==true}">
					<c:out value="${sysNewsMainForm.fdWriter}"></c:out>
				</c:if>
				<c:if test="${sysNewsMainForm.fdIsWriter==false}">
				<span style="margin-left: 6px;">
					<bean:message bundle="sys-news" key="sysNewsMain.publishUnit" />
					：
					<c:out value="${sysNewsMainForm.fdDepartmentName}" />
				</span>
				</c:if>
				<%--新闻来源 --%>
				<c:if test="${not empty sysNewsMainForm.fdNewsSource}">
					<bean:message bundle="sys-news" key="sysNewsMain.fdNewsSourceOnly" />：<bean:write
						name="sysNewsMainForm" property="fdNewsSource" />
				</c:if>
			</div>
		</div>
		<!-- 摘要 -->
		<c:if test="${ not empty sysNewsMainForm.fdDescription }">
			<div class="lui_form_summary_frame" style="text-indent: 0;">
				<font color="#003048">
					<xform:textarea property="fdDescription"  showStatus="view" style="width:95%" />
				</font>
			</div>
		</c:if>
		<div class="lui_form_content_frame clearfloat"
			id="lui_form_content_frame">
			<!--内容-->
			<div style="min-height: 150px;" id="contentDiv">
				<c:choose>
					<c:when test="${not empty sysNewsMainForm.fdIsLink}">
						<!--发布机制链接-->
						<bean:message bundle="sys-news" key="SysNewsMain.linkNews" />
						<a href='<c:url value="${sysNewsMainForm.fdLinkUrl}"/>'
							class="com_subject" />
						<c:out value="${sysNewsMainForm.docContent}" />
						</a>
					</c:when>
					<c:otherwise>
						<c:if test="${sysNewsMainForm.fdContentType=='rtf'}">
							<xform:rtf property="docContent"></xform:rtf>
						</c:if>
						<c:if test="${sysNewsMainForm.fdContentType=='word'}">
							<c:choose>
								<c:when test="${_isWpsCloudEnable}">
									<script>
										seajs.use(['lui/topic' ], function(topic) {
											var officeIframeh = "560";
											if($('.content').length > 0){
												var contentH = $('.content').height();
												officeIframeh = contentH-70;
											}
											 topic.subscribe('/sys/attachment/wpsCloud/loaded', function(obj) {
												if(obj){
													obj.iframe.style.height=(officeIframeh)+"px"
												}
											});
										});
									</script>
									<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_view.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
										<c:param name="formBeanName" value="sysNewsMainForm" />
									</c:import>
								</c:when>
								<c:when test="${_isWpsCenterEnable}">
									<script>
										seajs.use(['lui/topic' ], function(topic) {
											var officeIframeh = "650";
											if($('.content').length > 0){
												var contentH = $('.content').height();
												officeIframeh = contentH-70;
											}
											topic.subscribe('/sys/attachment/wpsCenter/loaded', function(obj) {
												if(obj){
													obj.iframe.style.height=(officeIframeh)+"px"
												}
											});
										});
									</script>
									<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_view.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
										<c:param name="formBeanName" value="sysNewsMainForm" />
									</c:import>
								</c:when>
								<c:when test="${_isWpsWebOffice}">
								  <div>
										<%
											//以下代码用于附件不通过form读取的方式
										List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
											if(sysAttMains==null || sysAttMains.isEmpty()){
												try{
													String _modelId = request.getParameter("sysNewsMainForm_fdId");

													if(StringUtil.isNotNull(_modelId)){
														ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
														sysAttMains = sysAttMainService.findByModelKey("com.landray.kmss.sys.news.model.SysNewsMain",_modelId,"editonline");
													}
													if(sysAttMains!=null && !sysAttMains.isEmpty()){
														pageContext.setAttribute("sysAttMains",sysAttMains);
													}
												}catch(Exception e){
													e.printStackTrace();
												}
											}
										%>
										<c:forEach items="${sysAttMains}" var="sysAttMain"	varStatus="vstatus">
											<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
											<c:set var="fdAttMainId" value="${sysAttMain.fdId}" scope="request"/>
										</c:forEach>
										<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_view.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="editonline" />
												<c:param name="fdMulti" value="false" />
												<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
												<c:param name="formBeanName" value="sysNewsMainForm" />
												<c:param name="fdTemplateModelName" value="com.landray.kmss.sys.news.model.SysNewsTemplate" />
												<c:param name="fdTemplateKey" value="editonline" />
												<c:param name="templateBeanName" value="sysNewsTemplateForm" />
												<c:param name="showDelete" value="false" />
												<c:param name="wpsExtAppModel" value="sysNews" />
												<c:param name="canRead" value="true" />
												<c:param name="addToPreview" value="false" />
												<c:param  name="hideTips"  value="true"/>
												<c:param  name="hideReplace"  value="true"/>
												<c:param  name="canEdit"  value="false"/>
                                                <c:param name="canPrint" value="false" />
												<c:param  name="canChangeName"  value="false"/>
									</c:import>
									</div>
								</c:when>
								<c:otherwise>
									<%
										if (com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()) {
									%>
									<div id="missiveButtonDiv"
										style="text-align: right; padding-bottom: 5px">&nbsp;</div>
									<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
										<c:param name="formBeanName" value="sysNewsMainForm" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isExpand" value="true" />
										<c:param name="showToolBar" value="false" />
										<c:param name="isAtt" value="false" />
										<c:param name="showChangeView" value="true" />
									</c:import>
									<%
										} else {
									%>
										${sysNewsMainForm.fdHtmlContent}
									<%
										}
									%>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:forEach items="${sysNewsMainForm.attachmentForms['newsMain'].attachments}" var="sysAttMain"	varStatus="vstatus">
									<c:set var="attId" value="${sysAttMain.fdId}"/>
									<c:set var="fdAttMainId" value="${sysAttMain.fdId}" scope="request"/>
						</c:forEach>
						<!-- pdf格式 -->
						<c:if test="${sysNewsMainForm.fdContentType=='att' && fn:length(sysNewsMainForm.attachmentForms['newsMain'].attachments)>0}">
				    	 <%
						   //取fdAttMainId的值判断附件是否已经转换
						   if(JgWebOffice.isExistViewPath(request)){ 
						  %>
						  <div class="lui_form_content_frame">
						  		<div id="downloadDiv" style="text-align: right;;padding-bottom:5px">&nbsp;
									<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attId}" requestMethod="GET">
										 <a href="javascript:void(0);" class="attdownloadcontent"
											onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attId}&downloadType=manual');">
										       <bean:message bundle="sys-news" key="sysNewsMain.att.dowload" />
										 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									 </kmss:auth>
								</div>
                                <!-- #135859  经过排查由于1200高度引起，删去后显示正常-->
						  		<iframe id="pdfFrame"  src="<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attId}&inner=yes&dj_ifr=true&isAtt=false&showChangeView=true"/>"  width="100%"  style="min-height:565px;/* height:1200px */"  frameborder="0">
								</iframe>
						   </div>
							<% }else if("true".equals(pageContext.getAttribute("_wpsoaassist"))){%>
							<%if(_isFoxitEnable) {%>
							<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="newsMain" />
								<c:param name="fdAttType" value="office" />
								<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="formBeanName" value="sysNewsMainForm" />
								<c:param name="buttonDiv" value="missiveButtonDiv" />
								<c:param name="isExpand" value="true" />
								<c:param name="showToolBar" value="false" />
								<c:param name="isAtt" value="false" />
								<c:param name="showChangeView" value="true" />
							</c:import>
							<%} else {%>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysNewsMainForm" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
								<c:param name="fdKey" value="newsMain" />
								<c:param name="fdMulti" value="false" />
								<c:param  name="canEdit"  value="false"/>
								<c:param name="canPrint" value="false" />
								<c:param name="canRead" value="true" />
								<c:param name="addToPreview" value="false" />
								<c:param  name="hideTips"  value="true"/>
								<c:param  name="hideReplace"  value="true"/>
								<c:param  name="canChangeName"  value="false"/>
								<c:param name="showDelete" value="false" />
							</c:import>

							<c:if test="${isOpenAspose eq true && isImage eq false}">
								<div id="converTip" class="sys_news_loading">文件转换中，请稍后...</div>
							</c:if>
							<%}%>

						  <%
						   }else{
						  %>
						  		<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
                                    <c:param name="fdKey" value="newsMain" />
                                    <c:param name="fdAttType" value="office" />
                                    <c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
                                    <c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
                                    <c:param name="formBeanName" value="sysNewsMainForm" />
                                    <c:param name="buttonDiv" value="missiveButtonDiv" />
                                    <c:param name="isExpand" value="true" />
                                    <c:param name="showToolBar" value="false" />
									<c:param name="isAtt" value="false" />
									<c:param name="showChangeView" value="true" />
									<c:param name="attHeight" value="550px" />
                                </c:import>
						  <%
						   }
						  %>
						</c:if>
					</c:otherwise>
				</c:choose>
			</div>
			<!--附件-->
			<c:if
				test="${not empty sysNewsMainForm.autoHashMap['fdAttachment'].attachments}">
				<div class="lui_form_spacing"></div>
				<div>
					<div class="lui_form_subhead">
						<img
							src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
						${ lfn:message('sys-news:tip.news.download.attachment') }(${fn:length(sysNewsMainForm.attachmentForms['fdAttachment'].attachments)})
					</div>
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="formBeanName" value="sysNewsMainForm" />
						<c:param name="fdKey" value="fdAttachment" />
						<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
					</c:import>
				</div>
			</c:if>
			<br>
			<!-- 盖章文件 -->
			 	<c:if test="${sysNewsMainForm.fdSignEnable}">
				 	<c:if test="${not empty sysNewsMainForm.autoHashMap['yqqSigned'].attachments}">
				 		<div class="lui_form_spacing"></div>
					 	<div>
					 		<div class="lui_form_subhead">
								<img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
								${ lfn:message('sys-news:sysNewsSummary.yqqSignFile') }(${fn:length(sysNewsMainForm.attachmentForms['yqqSigned'].attachments)})
							</div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
								charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysNewsMainForm" />
								<c:param name="fdKey" value="yqqSigned" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
						</div>
				 	</c:if>
			 		<c:if test="${not empty sysNewsMainForm.autoHashMap['fdSignFile'].attachments}">
			 			<div class="lui_form_spacing"></div>
					 	<div>
					 		<div class="lui_form_subhead">
								<img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
								${ lfn:message('sys-news:sysNewsSummary.fdSignFile') }(${fn:length(sysNewsMainForm.attachmentForms['fdSignFile'].attachments)})
							</div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
								charEncoding="UTF-8">
								<c:param name="formBeanName" value="sysNewsMainForm" />
								<c:param name="fdKey" value="fdSignFile" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
							</c:import>
		
						</div>
			 		</c:if>
			 	</c:if>
			
			<c:if test="${param.approveModel ne 'right'}">
				<div style="padding-left: 5px">
					<!-- 标签机制 -->
					<c:import url="/sys/tag/import/sysTagMain_view.jsp" charEncoding="utf-8">
						<c:param name="formName" value="sysNewsMainForm" />
						<c:param name="useTab" value="false"></c:param>
					</c:import>
				</div>
			</c:if>
		</div>
		<%-- </ui:content> --%>
	</c:when>
	<c:when test="${ param.contentType eq 'share' }">
		<!-- 分享机制 -->
		<kmss:ifModuleExist path="/third/ywork/">
			<c:import url="/third/ywork/ywork_share/yworkDoc_share.jsp"
				charEncoding="UTF-8">
				<c:param name="modelId" value="${sysNewsMainForm.fdId}" />
				<c:param name="modelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
				<c:param name="templateId" value="${ sysNewsMainForm.fdTemplateId}" />
				<c:param name="allPath" value="${ sysNewsMainForm.fdTemplateName}" />
				<c:param name="readRecord" value="true" />
				<c:param name="shareRecord" value="true" />
			</c:import>
		</kmss:ifModuleExist>
	</c:when>
	<c:when test="${ param.contentType eq 'info' }">
		<ui:content title="${lfn:message('sys-news:sysNewsMain.baseInfo')}" toggle="false">
			<ul class='lui_form_info'>
				<li><bean:message bundle="sys-news" key="sysNewsMain.fdAuthorId" />：
				<ui:person personId="${sysNewsMainForm.fdCreatorId}" personName="${sysNewsMainForm.fdCreatorName}" layer="true"></ui:person></li>
				<li><bean:message bundle="sys-news" key="sysNewsMain.fdDepartmentId" />：<bean:write	 name="sysDocBaseInfoForm" property="fdDepartmentName" /></li>
				<%if(ISysAuthConstant.IS_AREA_ENABLED) { %> 
					<li><bean:message bundle="sys-authorization" key="sysAuthArea.authArea" />：${ sysNewsMainForm.authAreaName }</li>
				<%} %>
				<li><bean:message bundle="sys-news" key="sysNewsPublishMain.fdImportance" />：<sunbor:enumsShow	value="${sysNewsMainForm.fdImportance}"	enumsType="sysNewsMain_fdImportance" /></li>
				<li><bean:message bundle="sys-news" key="sysNewsPublishMain.docStatus" />：<sunbor:enumsShow	value="${sysNewsMainForm.docStatus}"	enumsType="news_status" /></li>
			  	<li>${lfn:message("sys-news:sysNewsMain.fdMainPicture") }：</li>
			  <c:if test="${hasImage eq true}">  
			    <li>
				    <c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						<c:param name="fdKey" value="Attachment" />
						<c:param name="fdMulti" value="false" />
						<c:param name="fdAttType" value="pic" />
						<c:param name="fdImgHtmlProperty" value="width=120" />
						<c:param name="fdModelId" value="${sysNewsMainForm.fdId }" />
						<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
						<%-- 图片设定大小 --%>
						<c:param name="picWidth" value="258" />
						<c:param name="picHeight" value="192" />
						<c:param name="proportion" value="false" />
						<c:param name="fdLayoutType" value="pic"/>
						<c:param name="fdPicContentWidth" value="258"/>
						<c:param name="fdPicContentHeight" value="192"/>
						<c:param name="fdViewType" value="pic_single"/>
						<c:param name="fdShowMsg">true</c:param>
					</c:import>
			    </li>
			</c:if>
			</ul>
		</ui:content>
	</c:when>
</c:choose>