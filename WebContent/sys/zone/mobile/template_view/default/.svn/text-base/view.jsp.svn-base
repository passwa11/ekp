<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page language="java"  import="com.landray.kmss.sys.zone.forms.SysZonePersonInfoForm" %>
<%@ page language="java"  import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.sys.zone.util.SysZoneConfigUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page language="java" import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" import="com.landray.kmss.common.dao.HQLInfo"%>
<%@ page language="java"  import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page language="java" import="com.landray.kmss.common.service.IBaseService"%>
<%@ page language="java" import="java.util.List"%>
<%@page language="java" import="com.landray.kmss.util.StringUtil" %>
<%@page language="java" import="com.landray.kmss.sys.profile.util.ProfileMenuUtil" %>

<%@page language="java" import="com.landray.kmss.framework.plugin.core.config.IExtension" %>
<%@page language="java" import="com.landray.kmss.framework.plugin.core.config.IExtensionPoint" %>
<%@page language="java" import="com.landray.kmss.framework.service.plugin.Plugin" %>
<%@page language="java" import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService" %>
<%@page language="java" import="com.landray.kmss.sys.attachment.model.SysAttMain" %>
<%@page language="java" import="com.landray.kmss.sys.zone.util.SysZonePrivateUtil" %>

<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" 

	prefix="person"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		<c:out value="${lfn:message('sys-zone:sysZonePerson.personInfoTitle')}"/> 
		- <c:out value="${sysZonePersonInfoForm.personName}"/>
	</template:replace>
	<template:replace name="head">
		<mui:min-file name="mui-zone.js"/>
		<mui:min-file name="mui-zone-view.css"/>
		<script>
			require(['dojox/mobile/TransitionEvent',
				    'dojo/topic',"dojo/dom",
			     	"dojo/query","dijit/registry",
			     	"dojo/dom-geometry","mui/device/adapter",
			     	"mui/util",
			     	"dojo/domReady!" ], function(TransitionEvent, topic, 
			     			dom, query, registry, domGeometry, adapter, util) {
				window.backToDocView =  function(obj) {
					var opts = {
							transition : 'slide',
							moveTo:'docView',
							transitionDir : -1
						};
					new TransitionEvent(obj, opts).dispatch();
					topic.publish("/mui/list/pushDomHide",this);
				};
		
				window.fansMoreView = function(viewId, obj) {
					topic.publish("/sys/zone/onSlide", this, {
						moreViewId : viewId,
						target : obj
					});
				};
				topic.subscribe('/mui/list/noData',function(){
					var h = dom.byId('docView').offsetHeight - dom.byId('nav_view').offsetTop -30;
					query('.muiListNoData').style({
						'line-height' : h + 'px',
						'height' : h + 'px'
					});
				});
				window.callPhone = function(value){
					if(value){
						//adapter.callPhone(value, false); kk接口经常无法调起拨打电话界面
						location.href="tel:"+value;
					}
				};
				window.sendEmail = function(value){
					if(value){
						location.href="mailto:" + value;
					}
				};
				window.sendSms = function(value){
					if(value){
						adapter.sendMsg(value);
					}
				};
				window.toEva = function() {
					location.href = util.formatUrl("/sys/evaluation/mobile/index.jsp?modelName=com.landray.kmss.sys.zone.model.SysZonePersonInfo&modelId=${sysZonePersonInfoForm.fdId}&isNotify=no")
				};
				
				topic.subscribe("/mui/zone/followRelationChange",function(data){
                   
					debugger;
				});				
			});
		</script>
	</template:replace>
	<template:replace name="content">
	<div data-dojo-type="mui/view/DocScrollableView" id="docView"
		data-dojo-mixins="mui/list/_ViewPushAppendMixin">
		<div  class="mui_zone_perinfo" id="zone_perinfo">
			<c:if test="${KMSS_Parameter_CurrentUserId !=  sysZonePersonInfoForm.fdId}">
			<div>
				<div  style="width: 38%;">
				         <span data-dojo-type="dojox/mobile/Button" data-dojo-mixins="sys/zone/mobile/js/_FollowButtonMixin,sys/zone/mobile/js/_FollowButtonDetailViewMixin"
				         	   data-dojo-props="orgId1:'${KMSS_Parameter_CurrentUserId}',
				         			userId:'${sysZonePersonInfoForm.fdId}',
				         			attentModelName:'com.landray.kmss.sys.zone.model.SysZonePersonInfo',
				         			isDelayStatus:false,
				        			fansModelName:'com.landray.kmss.sys.zone.model.SysZonePersonInfo',
				        			isFollowPerson:'true'">
				         </span>
				</div>
			</div>
			</c:if>
			<div class="mui_zone_per_content">
				<div class="mui_zone_img_content">
					<div>
						<%
				   			String fromPage =  request.getParameter("fromPage");
				   			String imgUrl = new String();
				
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
								<span style="
									background:url(<person:headimageUrl contextPath="true" personId="${sysZonePersonInfoForm.personId}" size="b"/>) 
									center center no-repeat;background-size: cover;"></span>
							
							</c:when>
							<c:otherwise>
								<span style="
									background:url(${imgUrl }) 
									center center no-repeat;background-size: cover;"></span>
							
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="mui_zone_info_content">
			         <h2>
			         	<c:out value="${sysZonePersonInfoForm.personName}"/>
			         </h2>
					<%
						SysZonePersonInfoForm sysZonePersonInfoForm= (SysZonePersonInfoForm)request.getAttribute("sysZonePersonInfoForm");
						if(sysZonePersonInfoForm!=null){
							request.setAttribute("isDepInfoPrivate", SysZonePrivateUtil.isDepInfoPrivate(sysZonePersonInfoForm.getFdId()));
						}
					%>
					<c:choose>
						<c:when test='${isDepInfoPrivate}'>
							<p class="lui_info_tip">[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]</p>
						</c:when>
						<c:otherwise>
							<p class="lui_info_tip"><c:out value="${sysZonePersonInfoForm.dep}"/></p>
						</c:otherwise>
					</c:choose>
			    </div>
			</div>
			
			<kmss:ifModuleExist path="/kms/medal/">
				<!-- 勋章入口 Starts -->
				<div class="mui_zone_medal">
					<div class="mui_zone_item_medal">
						<c:import url="/kms/medal/mobile/import/view.jsp" charEncoding="UTF-8">
								<c:param name="fdModelId" value="${sysZonePersonInfoForm.fdId }"/>
								<c:param name="fdModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
								<c:param name="fdMedalNum" value="${empty medalNum ? '0': medalNum}"/>
								<c:param name="muiIcon" value="mui_zone_icon_medal"/>
								<c:param name="zone_TA_text" value="${zone_TA_text}"/>
						</c:import>
					</div>
				</div>
				<!-- 勋章入口 Ends -->
			</kmss:ifModuleExist>
			
			<div class="mui_zone_relation">
				<div class="mui_zone_item">
					<c:import url="/sys/fans/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="fdModelId" value="${sysZonePersonInfoForm.fdId }"/>
						<c:param name="attentModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
						<c:param name="fansModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
						<c:param name="muiIcon" value="mui-heart"/>
						<c:param name="type" value="attention"/>
						<c:param name="fdMemberNum" value="${empty attentNum ? '0': attentNum}"/>
					</c:import>
					<c:import url="/sys/fans/mobile/import/view.jsp" charEncoding="UTF-8">
						<c:param name="fdModelId" value="${sysZonePersonInfoForm.fdId }"/>
						<c:param name="attentModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
						<c:param name="fansModelName" value="com.landray.kmss.sys.zone.model.SysZonePersonInfo"/>
						<c:param name="muiIcon" value="mui-depart"/>
						<c:param name="type" value="fans"/>
						<c:param name="fdMemberNum" value="${empty fansNum ? '0': fansNum}"/>
					</c:import>
                </div>
				<c:if test="${KMSS_Parameter_CurrentUserId !=  sysZonePersonInfoForm.fdId}">
					<div class="itemOpearte">
						<c:if test="${not empty mobilPhone }">
	                    	<span><a  class="mui mui-tel mui_zone_replace" href="javascript:void(0)" onclick="callPhone('${mobilPhone }')"></a></span>
	                    </c:if>
	                    <c:if test="${not empty  email}">
	                    	<span><a  class="mui mui-mail mui_zone_replace" href="javascript:void(0)" onclick="sendEmail('${email }')"></a></span>
	                    </c:if>
	                    <c:if test="${not empty mobilPhone}">
	                    	<span><a class="mui mui-msg mui_zone_replace" href="javascript:void(0)" onclick="sendSms('${mobilPhone }')"></a></span>
               			</c:if>
               		</div>
				</c:if>
			</div>
		</div>
		<div data-dojo-type="mui/fixed/Fixed">
			<div data-dojo-type="mui/fixed/FixedItem">
				
				<div class="muiHeader" style="margin:0;">
					<div
						data-dojo-type="mui/nav/NavBarStore" 
						data-dojo-props="defaultUrl:'/sys/zone/mobile/nav.jsp?zone_TA_text=${zone_TA_text}&fdId=${param.fdId}'">
					</div>
					<!--
					<div style="display:table-cell;" class="mui_more_zone_button"
							data-dojo-type="sys/zone/mobile/js/more/MoreButtonBar"> 
					</div>
					<div class="mui_zone_search_bar"
						data-dojo-type="mui/search/SearchButtonBar"
						data-dojo-props="modelName:'com.landray.kmss.sys.zone.model.SysZonePersonInfo'">
					</div>
				--></div>
				<div  class="mui_zone_cri" id="mui_zone_cri">
					<span class="mui_zone_selected_text"></span><span class="knowledge_num"></span>
					<span class="mui_zone_cri_btn "><span class="mui mui-filter"></span><bean:message bundle="sys-zone" key="sysZonePerson.4m.criteria"/></span>
				</div>
			</div>
		</div>
		
		<%--mui/list/NavSwapScrollableView  --%>
		<div data-dojo-type="sys/zone/mobile/js/NavSwapView" id="nav_view"
			 data-dojo-props="userId:'${sysZonePersonInfoForm.fdId}',
			 				  isMultiServer:'<%=SysZoneConfigUtil.isMultiServer()%>',
			 				  currentServerKey:'<%=SysZoneConfigUtil.getCurrentServerGroupKey() %>'">
			 <div data-dojo-type="sys/zone/mobile/js/BaseInfoView" class="gray">
				<section class="mui_zone_info_section">
					<h2 class="mui_section_head"><bean:message bundle="sys-zone" key="sysZonePersonInfo.basicInfo" /></h2>
					<div class="mui_section_content">
						<div class="mui_section_catalog">
						 	<div class="mui_zone_eva_box">
							 	<div class="mui_zone_eva_score">
							 		<div data-dojo-type="mui/rating/Rating"
							 			data-dojo-props="value:'${sysZonePersonInfoForm.evaluationForm.fdEvaluateScore}'">
							 		</div>
						 		</div>
						 		<span class="mui_zone_ratingScore">
						 			${sysZonePersonInfoForm.evaluationForm.fdEvaluateScore}</span>
						 		<a class="mui_zone_eva_count" 
						 			style="cursor: pointer;"
						 			onclick="toEva();"
						 			href="javascript:;">
						 			${(empty  sysZonePersonInfoForm.docEvalCount) ? 0 : (sysZonePersonInfoForm.docEvalCount)}<bean:message bundle="sys-zone" key="sysZonePerson.4m.person"/><bean:message bundle="sys-zone" key="sysZonePerson.4m.evaluate"/>
						 			<span class="mui-forward mui mui_zone_eva_forward"></span>
						 		</a>
					 		</div>
						</div>
						<div class="mui_section_catalog">
							 <span class="mui_zone_section_title"><bean:message bundle="sys-zone" key="sysZonePerson.sign"/> </span>
							 <p class="mui_zone_section_content"><c:out value="${sysZonePersonInfoForm.fdSignature}"/></p>
						</div>
						<div class="mui_section_catalog">
							 <span class="mui_zone_section_title"><bean:message bundle="sys-zone" key="sysZonePerson.tags"/> </span>
							 <div class="mui_zone_section_content">
							 	<c:import url="/sys/tag/mobile/import/view.jsp"
									charEncoding="UTF-8">
									<c:param name="formName" value="sysZonePersonInfoForm" />
								</c:import>
							 </div>
						</div>
						<div class="mui_section_catalog" >
							 <span class="mui_zone_section_title"><bean:message bundle="sys-zone"  key="sysZonePerson.mobilePhone"/> 
							 </span>
							 <div class="mui_zone_section_content">
							 	<em><c:out value="${mobilPhone}"/></em>
							 </div>
						</div>
						<div class="mui_section_catalog" >
							 <span class="mui_zone_section_title"><bean:message bundle="sys-zone"  key="sysZonePerson.workPhone"/> 
							 </span>
							 <div class="mui_zone_section_content">
							 	<em><c:out value="${fdCompanyPhone}"/></em>
							 </div>
						</div>
						<div class="mui_section_catalog" >
							 <span class="mui_zone_section_title"><bean:message bundle="sys-zone" key="sysZonePerson.email"/> 
							 </span>
							 <div class="mui_zone_section_content">
							 	<em><c:out value="${email}"/></em>
							 </div>
						</div>
						<div class="mui_section_catalog" >
							 <span class="mui_zone_section_title"><bean:message bundle="sys-zone" key="sysZonePerson.post"/> 
							 </span>
							 <div class="mui_zone_section_content">
							 	<em><c:out value="${postNames}"/></em>
							 </div>
						</div>
					</div>
				</section>
				<%
					Boolean isLecturer = false;
					if(ProfileMenuUtil.moduleExist("/kms/lecturer")){//有讲师模块
						String fdId = request.getParameter("fdId");
	
						HQLInfo hqlInfo = new HQLInfo();
						hqlInfo.setWhereBlock("kmsLecturerMain.fdPerson.fdId = :userId");
						hqlInfo.setParameter("userId",fdId);
						IBaseService kmsLecturerMainService = (IBaseService) SpringBeanUtil
							.getBean("kmsLecturerMainService");
						List  lecturerMainList =  kmsLecturerMainService.findList(hqlInfo);
			        	
						if(lecturerMainList!=null&&!lecturerMainList.isEmpty()){
							IExtensionPoint point = Plugin
								.getExtensionPoint("com.landray.kmss.kms.lecturer");
							IExtension[] extensions = point.getExtensions();
							for (IExtension extension : extensions) {
								if ("lecturerInfo".equals(extension.getAttribute("name"))) {
									String jspUrl = Plugin.getParamValueString(extension,
											"jspUrl.4m");
									//System.out.println(jspUrl);
									request.setAttribute("LectUrl",jspUrl);

								}
							}
							
							isLecturer = true;
							
						}
					}
					request.setAttribute("isLecturer",isLecturer);
				%>
				<c:if test="${isLecturer==true}">
					<c:import url="${LectUrl}" charEncoding="UTF-8">
						<c:param name="userId" value="${param.fdId}" />
					</c:import>
				</c:if>
				<div class="___muiDocContent">
					<c:forEach items="${personDatas}" var="data">
						<section class="mui_zone_info_section">
							<h2 class="mui_section_head"><c:out value="${data.fdName}"/></h2>
							<div class="mui_section_catalog">
								<div class="mui_zone_section_content mui_zone_section_content_etcInfo">
									<em>${data.docContent}</em>
								</div>
							</div>
						</section>
					</c:forEach>
				</div>
				<script>
						require(
								[ 'mui/rtf/RtfResizeUtil', 'dojo/query' ],
								function(RtfResizeUtil, query) {
									new RtfResizeUtil(
											{
												containerNode : query('.___muiDocContent')[0],
												channel : 'zoneView'
											});
								})
				</script>
			</div>
		    <ul 
		    	data-dojo-type="sys/zone/mobile/js/JsonStoreZoneList" 
		    	data-dojo-mixins="sys/zone/mobile/js/list/TextItemListMixin,mui/list/_ListNoDataMixin" >
			</ul>
		</div>
		
			<%--提问  --%>
		<c:if test="${KMSS_Parameter_CurrentUserId !=  sysZonePersonInfoForm.fdId}">
				      <%				
			    					 JSONArray commmunicateList = SysZoneConfigUtil.getCommnicateList();
				      				 SysZonePersonInfoForm zoneForm = (SysZonePersonInfoForm)request.getAttribute("sysZonePersonInfoForm");
								     for(Object map: commmunicateList) { 
								    	 JSONObject json = (JSONObject)map;
								    	 if(!"communicate_mobile".equals(json.get("showType"))) {
				        	           			continue;
				        	           	 }
								    	 if("ask_mobile".equals(json.get("unid"))) {
								    		 String path = "";
								    		 String key = (String)json.get("server"); 
								    		 String localKey = SysZoneConfigUtil.getCurrentServerGroupKey();
								    		 if(StringUtil.isNotNull(key) && !key.equals(localKey)) { 
					        	           			path = SysZoneConfigUtil.getServerUrl(key);
					        	           	 } 
								    		 String askHref = path + json.get("href");
								    		 if(StringUtil.isNotNull(askHref)) {
								    			 askHref =  askHref.replaceAll("\\!\\{personId\\}",
								    					 zoneForm.getFdId()); 
								    		 }
								 %>
								 			<kmss:auth requestURL="<%=askHref%>" requestMethod="GET"> 
								 				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props="fill:'grid'">
										 			<li data-dojo-type="mui/tabbar/TabBarButton"
										 				class="muiBtnSubmit" data-dojo-props='colSize:2,href:"<%=askHref%>"'>
										 				<bean:message bundle="sys-zone" key="sysZonePerson.4m.ask" />
										 			</li>
												</ul>
								 			</kmss:auth>
								 		
								 <%   		 
								    	 }
								     }
	  	  				  		%>
       		 </c:if>
			
	</div>
	</template:replace>
</template:include>

