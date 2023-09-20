<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/zone/zone.tld"
	prefix="zone"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" 
	prefix="person"%>
<%@ page import="com.landray.kmss.sys.person.interfaces.PersonInfoServiceGetter" %>
<%@ page import="com.landray.kmss.sys.person.interfaces.PersonImageService" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="com.landray.kmss.common.dao.HQLInfo" %>
<%@ page import="com.landray.kmss.sys.attachment.model.SysAttMain" %>
<%@ page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>


<%@ page import="com.landray.kmss.sys.profile.util.ProfileMenuUtil" %>
<%@ page import="com.landray.kmss.sys.zone.model.SysZoneNavLink" %>


<template:include file="/sys/zone/sys_zone_personInfo/template_view/default/zonePersonTemplate.jsp" 
				  rightIcon="${sysOrgPerson.fdIsAvailable == false }" >
	<template:replace name="title">
		<%
			String fromPage =  request.getParameter("fromPage");
			if(StringUtil.isNotNull(fromPage)&&fromPage.equals("lecturer")){//从lecturer页面跳转
				if(ProfileMenuUtil.moduleExist("/kms/lecturer")){//有讲师模块
					request.setAttribute("fromZone",false);	
				}else{
					request.setAttribute("fromZone",true);	
				}
			
			}else{
				request.setAttribute("fromZone",true);	
			}
		%>
			<c:choose>
				<c:when test="${fromZone}">
					<bean:message bundle="sys-zone" key="sysZonePerson.personInfoTitle" />
				</c:when>
				<c:otherwise>
					<bean:message bundle="sys-zone" key="sysZonePerson.lecturerInfoTitle" />
				</c:otherwise>
			</c:choose>
	
	</template:replace>
	<template:replace name="head">
		<script>
			Com_IncludeFile("data.js|dialog.js|xform.js|jquery.js");
			window.fdPraiseTargetPerson = "${sysOrgPerson.fdId}";
		</script>
	</template:replace>
	<template:replace name="photo">
		<div class="sys-zone-card1">
			<!--头像-->
			<%
	   			String fromPage =  request.getParameter("fromPage");
	   			String imgUrl = new String();
	   			String timstap = System.currentTimeMillis()+"";
	   			request.setAttribute("timstap",timstap);
				if(StringUtil.isNotNull(fromPage)&&fromPage.equals("lecturer")){//从lecturer页面跳转
					if(ProfileMenuUtil.moduleExist("/kms/lecturer")){//有讲师模块
			   			String lecturerId =  (String)request.getAttribute("lecturerId");
						//System.out.println(lecturerId);

						imgUrl = request.getContextPath()+"/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=";
	
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock(" sysAttMain.fdModelName = :modelName and sysAttMain.fdModelId = :modelId and sysAttMain.fdKey = :key");
						hqlInfo.setParameter("modelName","com.landray.kmss.kms.lecturer.model.KmsLecturerMain");
						hqlInfo.setParameter("modelId",lecturerId);
						hqlInfo.setParameter("key","spic");
						ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil
							.getBean("sysAttMainService");
						List<SysAttMain> sysAttList = sysAttMainService.findList(hqlInfo);
						if(sysAttList!=null&&!sysAttList.isEmpty()){  //优先取附件中的图片
							SysAttMain sysAttMain = sysAttList.get(0);
							imgUrl += sysAttMain.getFdId();
							request.setAttribute("imgUrl",imgUrl);
							//System.out.println(imgUrl);
							request.setAttribute("isZoneImg",false);
							
						}else{ //没有附件图片
							request.setAttribute("isZoneImg",true);
						} 
					}
				}else{
					request.setAttribute("isZoneImg",true);
				}
			%>
			
			<c:choose>
				<c:when test="${isZoneImg}">
					<c:if test="${sysOrgPerson.fdId == KMSS_Parameter_CurrentUserId}">
						<%
							PersonImageService service = PersonInfoServiceGetter.getPersonImageService();
							String changeUrl = service.getHeadimageChangeUrl();
							String defaultUrl = "/sys/person/setting.do?setting=sys_zone_person_photo";
							if(StringUtil.isNull(changeUrl)) {
								changeUrl = defaultUrl;
							}else{
								//判断是否是url
								Pattern pattern = Pattern.compile("^(?:https|http?://)?[\\w\\.\\-]+(?:/|(?:/[\\w\\.\\-]+)*)?$",Pattern.CASE_INSENSITIVE);
								if(!pattern.matcher(changeUrl).matches() && !changeUrl.equals(defaultUrl)){
									 try{
										 File file = new File(changeUrl);
										 if(!file.exists()){//文件不存在
											changeUrl = defaultUrl;
										 }
									 }catch (Exception e) {
										 
									 }
								}
							}
							
		
						%>
						<a  title="${lfn:message('sys-zone:sysZonePersonInfo.changeMyPic')}" 
							target="_blank" 
							href="${ LUI_ContextPath }<%=changeUrl%>">
							<span class="personImg" style="
									background:url(<person:headimageUrl contextPath="true" personId="${sysZonePersonInfoForm.personId}" size="b"/>) 
									center center no-repeat;background-size: cover;"></span>
						</a>
					</c:if>
					<c:if test="${sysOrgPerson.fdId != KMSS_Parameter_CurrentUserId }">
					
						<%-- <span class="personImg" style="
									background:url(<person:headimageUrl contextPath="true" personId="${sysZonePersonInfoForm.personId}" size="b"/>) 
									center center no-repeat;background-size: cover;"></span> --%>
									 <span class="personImg" style="
									background:url(${ LUI_ContextPath }/sys/person/image.jsp?personId=${sysZonePersonInfoForm.personId}&size=b&timstap=${timstap}) 
									center center no-repeat;background-size: cover;"></span>
					</c:if>
				</c:when>
				<c:otherwise>
					<span class="personImg" style="
									background:url(${imgUrl }) 
									center center no-repeat;background-size: cover;"></span>
					
				</c:otherwise>
			</c:choose>

			<!-- 快捷列表 -->
			<c:if test="${sysOrgPerson.fdIsAvailable != false }">
			<div id="contactBar" class="staff_shortcut_list"  data-person-role="contact"
				 data-person-param="&fdId=${sysZonePersonInfoForm.personId}&fdName=${fn:escapeXml(sysZonePersonInfoForm.personName)}&fdLoginName=${ sysOrgPerson.fdLoginName}&fdEmail=${email}&fdMobileNo=${mobilPhone }">
			</div>
			<style>
				#contactBar .lui_zone_contact_item a img{
					max-width:17px !important;
				}
			</style>
			<%@ include file="/sys/zone/sys_zone_personInfo/sysZonePersonContact_include.jsp"%>
			<script>
				seajs.use(['lui/jquery'], function($) {
					var datas = [];
					$(function() {
						$.get("${ LUI_ContextPath }/sys/person/sys_person_zone/sysPersonZone.do?method=info&fdId=${JsParam.fdId}",function(res){
							$("[data-person-role='contact']").each(function() {
								var $this = $(this), personParam = $this.attr("data-person-param");
								datas.push({
									elementId : $this.attr("id"),
									personId: Com_GetUrlParameter(personParam, "fdId"),
									personName:Com_GetUrlParameter(personParam, "fdName"),
									loginName :Com_GetUrlParameter(personParam, "fdLoginName"),
									email:Com_GetUrlParameter(personParam, "fdEmail"),
									mobileNo:Com_GetUrlParameter(personParam, "fdMobileNo"),
									isSelf : ("${KMSS_Parameter_CurrentUserId}" == Com_GetUrlParameter(personParam, "fdId")),
									dingUserid: res.dingUserid,
									ldingUserid:res.ldingUserid,
									dingCropid: res.dingCropid
								});
							});
							
							onRender(datas);
						});
					});
				});
				
			</script> 
			</c:if>
			<c:if test="${sysOrgPerson.fdIsAvailable == false }">
				 	<div class="sys_zone_status_leave"></div>
			</c:if>
			<c:if test="${personResumeId != null }">
				<c:if test="${download}">
				<div style="    margin-top: 20px;">
					<div class="sys_zone_resumebook">
						<a  title="${lfn:message('sys-zone:sysZonePerson.resume.download') }"
							href="${LUI_ContextPath}/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${personResumeId }">
							<i class="lui_icon_s icon_download"></i>
							<span> 
								<bean:message bundle="sys-zone" key="sysZonePerson.resume" />
							</span>
						</a>
					</div>
				</div>
				</c:if>
			</c:if>	
		</div>
		</template:replace>
	
		<!--名字卡片-->
		 <template:replace name="infoCard">
		 	<c:import url="/sys/zone/import/sys_zone_info_card.jsp" charEncoding="UTF-8">
		 	</c:import>
		 </template:replace>
		 
		 		 <!--信息墙-->
		 <template:replace name="infoWall">
			<ul class="infoWall">
				<li class="sys_zone_btn_group">
						<c:if test="${sysOrgPerson.fdIsAvailable != false }">
						<div class="focus_person">
							<c:import url="/sys/fans/sys_fans_main/sysFansMain_view.jsp" charEncoding="UTF-8">
								<c:param name="userId" value="${sysZonePersonInfoForm.personId}"></c:param>
								<c:param name="attentModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"></c:param>
								<c:param name="fansModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"></c:param>
								<c:param name="isFollowPerson" value="true"></c:param>
								<c:param name="fdAttentionNum" value="${empty attentNum ? '0': attentNum}"></c:param>
								<c:param name="fdFansNum" value="${empty fansNum ? '0': attentNum}"></c:param>
								<c:param name="attentLocalId" value="sys_fans_attent_num"></c:param>
								<c:param name="fansLocalId" value="sys_fans_fans_num"></c:param>
								<c:param name="showFollowList" value="iframe_body"></c:param>
							</c:import>
						</div>
						</c:if>
						<%--积分  --%>
						<div class="integral_person">
							<zone:personinfo infoId="kmsIntegralPerson" 
									personId="${(empty param.fdId) ? (param.userId) : (param.fdId)}">
							</zone:personinfo>
						</div>
					</li>
					<%--勋章  --%>
					<li class="metal" >
						<zone:personinfo infoId="medalInfo" personId="${(empty param.fdId) ? (param.userId) : (param.fdId)  }">
						</zone:personinfo>
					</li>

					<!-- 关注信息 -->
					<li class="sys_zone_fansbox">
						<div class="sys_zone_fansitem" id="sys_fans_fans_num">
							<em data-role-fansnum="${sysZonePersonInfoForm.personId}">${empty fansNum ? '0' : fansNum }</em>
							<span>${lfn:message('sys-zone:sysZonePerson.fdFans') }</span>
						</div>
						<div class="sys_zone_fansitem" id="sys_fans_attent_num">
							<em data-role-follownum="${sysZonePersonInfoForm.personId}">${empty attentNum ? '0': attentNum}</em>
							<span>${lfn:message('sys-zone:sysZonePerson.fdAttention') }</span>
						</div>
					</li>
					<li class="sys_tag" >
						<em>${lfn:message('sys-zone:sysZonePerson.tags')}：</em>
						<c:import url="/sys/zone/import/sysTagMain_ui_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysZonePersonInfoForm" />
							<c:param name="fdKey" value="zonePersonInfoDoc" />
							<c:param name="modelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo" />
							<c:param name="fdQueryCondition"  value="${sysZonePersonInfoForm.fdId};${sysZonePersonInfoForm.personName}" />
							<c:param name="method" value="${ isSelf == true ? 'edit':'view' }"/>
						</c:import>
					</li>
			</ul>
		 </template:replace>
		<c:if test="${not  empty param.fromPage}">
			<c:if test="${not empty fdLecturerSignature}">
					<template:replace name="signature">
					<p class="fdSignature_table">
						<bean:message bundle="sys-zone" key="sysZonePersonInfo.fdSignature"/>：
					<span title="${fdLecturerSignature}">
						<c:out value="${fdLecturerSignature}"/>				
					</span>
					</p>
					</template:replace>	
			</c:if>
		</c:if>
		
			
		<%
			List<SysZoneNavLink> navLinks = (List<SysZoneNavLink>)request.getAttribute("navLinks");
			if(navLinks!=null&&!navLinks.isEmpty()){
				int navLinkSize = navLinks.size();
				request.setAttribute("navLinkSize",navLinkSize);
			}

			
		%>
		<template:replace name="navBar">
			<div class=nav_link >
				<div class='left_row' onclick="left_drag_nav();" >&lt;</div>
					<div class="nav_link_frame" >
						<div class="nav_link_body" >
							<ui:dataview>
								<ui:source type="Static">
									[<ui:trim>
										<c:forEach items="${navLinks}" var="link">
											{
												id: "${link.fdId }",
												text: "<c:out value="${link.fdName }" />",
												serverPath : "${link.serverPath}",
												target: "${link.fdTarget }",
												href : "${ link.fdUrl}",
												key : "${link.fdServerKey}"
											},
										</c:forEach>
										</ui:trim>]
								</ui:source>
								<ui:render type="Javascript">
									<%@ include file="/sys/zone/sys_zone_personInfo/template_view/default/sys_zone_nav_new_render.jsp"%>
								</ui:render>
							</ui:dataview>
						</div>
					</div>
				<div class='right_row'  onclick="right_drag_nav();" >&gt;</div>
			</div>
		</template:replace>
		<template:replace name="bodyL">
				<%@ include file="/sys/zone/sys_zone_personInfo/template_view/default/sysZonePersonInfo_view_js.jsp"%>
			
			<div id="iframeDiv" class="lui_zone_mbody_conetnt" >
				<iframe id="iframe_body" name="iframe_body" src="" value=""
					width=100%; height=100%; border="0" marginwidth="0" marginheight="0"
					frameborder="0" scrolling=yes> </iframe>
			</div>
			<script>
				$("#iframe_body").load(function() {
					setTimeout(function() {
						var iframeHeight = $("#iframe_body").contents().find("body").height();
						$("#iframe_body").height(iframeHeight + 50);
					}, 500);
				});
			</script>
		</template:replace>
		<template:replace name="bodyR">
		<!--团队关系 Starts-->
		<div class="lui_zone_relation_box" id="lui_zone_relation_box">
			<ui:tabpanel layout="sys.ui.tabpanel.default" id="zonePanel">
				<ui:content
					title="${lfn:message('sys-zone:sysZonePerson.leaders') }" style="width:100%">
					<ui:dataview>
						<ui:source type="AjaxJson">
							{url:"/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getTeam&orgId=${sysZonePersonInfoForm.fdId}&type=chain"}
						</ui:source>
						<ui:render type="Template">
							<%@ include file="/sys/zone/sys_zone_personInfo/template_view/default/sys_zone_chain_tmpl.jsp"%>
						</ui:render>
					</ui:dataview>
				</ui:content>
				<ui:content
					title="${lfn:message(lfn:concat('sys-zone:sysZonePerson.team.', zone_TA))}"
					style="width:100%">
					<ui:dataview>
						<ui:source type="AjaxJson">
							{url:"/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getTeam&orgId=${sysZonePersonInfoForm.fdId}&type=team"}
						</ui:source>
						<ui:render type="Template">
							<%@ include file="/sys/zone/sys_zone_personInfo/template_view/default/sys_zone_team_tmpl.jsp"%>
						</ui:render>
					</ui:dataview>
				</ui:content>
			</ui:tabpanel>
		</div>
		<!--团队关系 Ends-->
		
		<!--标签相似 Starts-->  
		<div class="lui_zone_staffYpage_similartag" id="lui_zone_staffYpage_similartag">
			<div style="float:right;">
				<list:paging channel="similarTags">
					<ui:layout type="Template">
						<%@ include file="/sys/zone/sys_zone_personInfo/template_view/default/sys_zone_tag_paging_tmpl.jsp"%>
					</ui:layout>		
				</list:paging>
			</div> 
			<ui:panel channel="similarTags" toggle="false">
				<ui:content
					title="${lfn:message('sys-zone:sysZonePerson.similarTags') }"> 
					<list:listview channel="similarTags">
						<list:gridTable name="gridtable" columnNum="1" gridHref=""
							layout="sys.ui.listview.gridtable" cfg-norecodeLayout="/sys/zone/resource/zoneNo_data.jsp"
							target="">
							<ui:source type="AjaxJson">
								{url:'/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getDatasByTags&personId=${sysZonePersonInfoForm.fdId}&rowsize=5'}
							</ui:source>
							<list:row-template>
							<%@ include file="/sys/zone/sys_zone_personInfo/template_view/default/sys_zone_tag_row_tmpl.jsp"%>
							</list:row-template>
							
						</list:gridTable>
						<ui:event event="load" >
						
							seajs.use('sys/fans/sys_fans_main/style/listView.css');
							var personIds = [];
							$("[data-fans-sign='sys_fans']").each(function() {
								var $this = $(this), personId = $this.attr("data-action-id");
								personIds.push(personId);
							});
							seajs.use(['sys/fans/resource/sys_fans'], function(follow){
								follow.changeFansStatus(personIds,".fans_follow_btn",
										{"extend" : "tag",
										 "caredText" : "${lfn:message('sys-zone:sysZonePerson.cared') }"  ,
										 "cancelText" :  "${lfn:message('sys-zone:sysZonePerson.cancelCared1') }",
										 "eachText" : "${lfn:message('sys-zone:sysZonePerson.follow.each') }",
										 "dialogEle" : ".lui_zone_staffYpage_similartag"
										 
										});
								follow.bindButton(".lui_zone_btn_tag", null, null, 
										{"extend" : "tag",
										 "caredText" : "${lfn:message('sys-zone:sysZonePerson.cared') }"  ,
										 "cancelText" :  "${lfn:message('sys-zone:sysZonePerson.cancelCared1') }",
										 "eachText" : "${lfn:message('sys-zone:sysZonePerson.follow.each') }"
										 
										});
							});
							 
							
						</ui:event>
					</list:listview>
				</ui:content>
			</ui:panel>
		</div> 
		<!--标签相似 End-->  
		
		<%-- 荣誉奖项 --%>
		<kmss:ifModuleExist path="/hr/staff/">
			<div class="lui_zone_person_experience" id="lui_zone_person_experience">
				<c:import url="/hr/staff/hr_staff_person_experience/import/bonusMalus_view4zone.jsp" charEncoding="UTF-8">
					<c:param name="personInfoId" value="${sysZonePersonInfoForm.personId}" />
				</c:import>
			</div>
		</kmss:ifModuleExist>

	<!--汇报关系与ta的团队-->
 	<c:import url="/sys/zone/sys_zone_personInfo/template_view/default/sysZonePersonInfoTop.jsp" charEncoding="UTF-8">
	</c:import>
	</template:replace>
	
</template:include>