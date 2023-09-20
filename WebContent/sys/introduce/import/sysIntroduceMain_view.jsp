<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.doc.model.SysDocBaseInfo" %>
<%@ page import="com.landray.kmss.sys.doc.forms.SysDocBaseInfoForm" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.common.service.IBaseService"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.introduce.service.ISysIntroduceMainService"%>
<%@page import="com.landray.kmss.common.dao.HQLInfo"%>
<%@page import="java.util.*"%>
<div style="display: none;">
	<input type="hidden" id="sysIntroduce_param_docSubject_hidden" value="<c:out value='${param.docSubject}' />"/>
</div>
<c:set var="sysIntroduceForm" value="${requestScope[param.formName]}"/>

<c:if test="${sysIntroduceForm.introduceForm.fdIsShow=='true'}">
		<c:set var="intr_modelName" value="${sysIntroduceForm.introduceForm.fdModelName}"/>
		<c:set var="intr_modelId" value="${sysIntroduceForm.introduceForm.fdModelId}"/>
		<c:set var="order" value="${empty param.order ? '10' : param.order}"/>
		<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>

		<ui:content title="<div id='intr_label_title'  name='introviewnames'>${lfn:message('sys-introduce:sysIntroduceMain.tab.introduce.label')}${sysIntroduceForm.introduceForm.fdIntroduceCount}</div>"
			cfg-order="${order}" cfg-disable="${disable}">
			<script>
			Com_IncludeFile("form.js");
			var intr_lang = {
					'intr_star_2':'<bean:message key="sysIntroduceMain.fdIntroduceGrade.WorthLooking" bundle="sys-introduce" />',
					'intr_star_1':'<bean:message key="sysIntroduceMain.fdIntroduceGrade.KeyRecommendation" bundle="sys-introduce" />',
					'intr_star_0':'<bean:message key="sysIntroduceMain.fdIntroduceGrade.ForceRecommendation" bundle="sys-introduce" />',
					'intr_prompt_1':'<bean:message key="sysIntroduceMain.pda.alert1" bundle="sys-introduce"/>',
					'intr_prompt_2':'<bean:message key="sysIntroduceMain.pda.alert2" bundle="sys-introduce"/>',
					'intr_prompt_3':'<bean:message key="sysIntroduceMain.pda.alert3" bundle="sys-introduce"/>',
					'intr_prompt_sucess':'<bean:message key="sysIntroduceMain.save.msg.success" bundle="sys-introduce"/>',
					'intr_type_select':'<bean:message key="sysIntroduceMain.introduce.type.error.showMessage" bundle="sys-introduce"/>',
					'intr_select_person':'<bean:message key="sysIntroduceMain.fdIntroduceTo.error.showMessage" bundle="sys-introduce"/>',	
					'intr_repetition_message':'<bean:message key="sysIntroduceMain.fdIntroduceTo.repetition" bundle="sys-introduce"/>',	
					'intr_introfalse_message':'<bean:message key="sysIntroduceMain.error.introfalse" bundle="sys-introduce"/>',
					'intr_introcheckfalse_message':'<bean:message key="sysIntroduceMain.error.introcheckfalse" bundle="sys-introduce"/>',
					'intr_introduce_ensse_message':'<bean:message key="sysIntroduceMain.cancel.confirm" bundle="sys-introduce"/>',
					
					};
				seajs.use(['${KMSS_Parameter_ContextPath}sys/introduce/import/resource/intr.css']);
				Com_IncludeFile("intr.js","${KMSS_Parameter_ContextPath}sys/introduce/import/resource/","js",true);
			</script>
			<script>
				if(window.intr_opt==null){
					window.intr_opt = new IntroduceOpt("${intr_modelName}","${intr_modelId}","${JsParam.fdKey}",intr_lang);
				}
			</script>
			<ui:event event="show">
				intr_opt.onload();
			</ui:event>
			<ui:event topic="introduce.submit.success" args="info">
				intr_opt.refreshNum(info);
			</ui:event>
			
			<kmss:auth requestURL="/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=add&fdModelName=${intr_modelName}&fdModelId=${intr_modelId}" requestMethod="GET">
				<c:import url="/sys/introduce/import/sysIntroduceMain_view_include.jsp" charEncoding="UTF-8">
				    <c:param name="fdCateModelName" value="${param['fdCateModelName']}" />
				    <c:param name="fdCateModelId" value="${param['fdCateModelId']}"/>
					<c:param name="fdModelName" value="${intr_modelName}"></c:param>
					<c:param name="fdModelId" value="${intr_modelId}"></c:param>
					<c:param name="fdKey" value="${param['fdKey']}"></c:param>
					<c:param name="toEssence" value="${param['toEssence']}" />
					<c:param name="toNews" value="${param['toNews']}" />
					<c:param name="toPerson" value="${param['toPerson']}" />
					<c:param name="docSubject" value="${param['docSubject']}" />
					<c:param name="docCreatorName" value="${param['docCreatorName']}" />
				</c:import>
			</kmss:auth>
			<div id="intr_viewMain">
			<%ISysIntroduceMainService sysIntroduceMainService= (ISysIntroduceMainService)SpringBeanUtil.getBean("sysIntroduceMainService") ;
				HQLInfo info=new HQLInfo();
				String name=   (String)pageContext.getAttribute("intr_modelName");
				String id=   (String)pageContext.getAttribute("intr_modelId");
				String whereBlock="sysIntroduceMain.fdModelId=:id and sysIntroduceMain.fdModelName=:name and sysIntroduceMain.fdCancelFlag = :fdCancelFlag ";
				info.setWhereBlock(whereBlock);
				info.setParameter("id", id);
				info.setParameter("name", name);
				info.setParameter("fdCancelFlag", 1);
				Object obj=  sysIntroduceMainService.findFirstOne(info);
			      
			     if(obj != null)  { %>
			<ui:tabpanel>
			
			<ui:content title="${lfn:message('sys-introduce:sysIntroduceMain.introduce.title') }">
				<div id="introduceContent">
					<list:listview channel="intr_ch2">
						<ui:source type="AjaxJson">
							{"url":"/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=viewAll&forward=listUi&rowsize=10&fdModelId=${intr_modelId}&fdModelName=${intr_modelName}&rowsize=8&fdCancelFlag=0"}
						</ui:source>
						<list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="simple">
							<ui:layout type="Template">
								{$<div class="intr_record" data-lui-mark='table.content.inside'>
								</div>$}
							</ui:layout>
							<list:row-template>
								{$
									<div class="intr_record_msg"><div class="intr_record_content intr_title_color">{%row['fdIntroduceReason']%}</div>
									<div class="intr_summary">
										<span class="intr_evaler">{%row['fdIntroducer.fdName']%}</span>
										<span class="intr_summary_color">|&nbsp;{%row['fdIntroduceTime']%}</span>
										<span><ul class="intr_summary_star">$}
											for(var m=0;m<3;m++){
												var flag = 2- parseInt(row['fdIntroduceGrade']);
												var className = 'lui_icon_s_bad'
												if(m <= flag){
													className = 'lui_icon_s_good';
												}
												{$<li class='{%className%}'></li>$}
											}
										{$</ul></span>
										{%row['showCancelFlag']%}
										</div>
									<div class="intr_summary_detail intr_summary_color">
										<span>{%row['introduceType']%}</span>
										<span>{%row['introduceGoalNames'].replace(/;/gi,' , ')%}</span>
									</div>
									</div>
								$}
							</list:row-template>
						</list:rowTable>	
						<ui:event topic="list.loaded"> 
						
							$("#intr_viewMain").css({height:"auto"});
						</ui:event>
					</list:listview>
					<list:paging channel="intr_ch2" layout="sys.ui.paging.simple"></list:paging>
				</div>
				</ui:content>
				<kmss:auth
	               requestURL="/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=viewCancel&fdModelName=${param.fdModelName}">
				<ui:content title="${lfn:message('sys-introduce:sysIntroduceMain.cancel.button') }" >
				<div id="introduceContent" style="min-height:150px">
					<list:listview channel="intr_ch3">
						<ui:source type="AjaxJson">
							{"url":"/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=viewCancel&rowsize=10&fdModelId=${intr_modelId}&fdModelName=${intr_modelName}&rowsize=8"}
						</ui:source>
						<list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="simple">
							<ui:layout type="Template">
								{$<div class="intr_record" data-lui-mark='table.content.inside'>
								</div>$}
							</ui:layout>
							<list:row-template>
								{$
									<div class="intr_record_msg">
									<div class="intr_record_content intr_title_color">{%row['fdIntroduceReason']%}</div>
									<div class="intr_summary">
										<span class="intr_evaler">{%row['fdCancelBy.fdName']%}</span>
										<span class="intr_summary_color">|&nbsp;{%row['fdCancelTime']%}</span>
									
									</div>
									<div class="intr_summary_detail intr_summary_color">$}
										var fdIntroduceToEssence = row['fdIntroduceToEssence']=='true';
										var fdIntroduceToNews = row['fdIntroduceToNews']=='true';
										var fdIntroduceToPerson = row['fdIntroduceToPerson']=='true';
										if(fdIntroduceToEssence){
											{$<span><bean:message key="sysIntroduceMain.introduce.show.type.essence" bundle="sys-introduce" /></span>$}
										}if(fdIntroduceToNews){
											{$<span><bean:message key="sysIntroduceMain.introduce.show.type.news" bundle="sys-introduce" /></span>$}
										}if(fdIntroduceToPerson){
											{$ <span>{%row['introduceGoalNames'].replace(/;/gi,' , ')%}</span> $}
										}
									{$</div>
									</div>
								$}
							</list:row-template>
						</list:rowTable>	
						<ui:event topic="list.loaded" > 
						
							$("#intr_viewMain").css({height:"auto"});
						</ui:event>
					</list:listview>
					<list:paging channel="intr_ch3" layout="sys.ui.paging.simple"></list:paging>
				</div>
				</ui:content>
				</kmss:auth>
				</ui:tabpanel>
				<%} else{%>
				<div class="intr_title intr_title_color"><bean:message key="sysIntroduceMain.introduce.title" bundle="sys-introduce"/></div>
				<div id="introduceContent">
					<list:listview channel="intr_ch1">
						<ui:source type="AjaxJson">
							{"url":"/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=viewAll&forward=listUi&rowsize=10&fdModelId=${intr_modelId}&fdModelName=${intr_modelName}&rowsize=8"}
						</ui:source>
						<list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="simple">
							<ui:layout type="Template">
								{$<div class="intr_record" data-lui-mark='table.content.inside'>
								</div>$}
							</ui:layout>
							<list:row-template>
								{$
									<div class="intr_record_msg"><div class="intr_record_content intr_title_color">{%row['fdIntroduceReason']%}</div>
									<div class="intr_summary">
										<span class="intr_evaler">{%row['fdIntroducer.fdName']%}</span>
										<span class="intr_summary_color">|&nbsp;{%row['fdIntroduceTime']%}</span>
										<span><ul class="intr_summary_star">$}
											for(var m=0;m<3;m++){
												var flag = 2- parseInt(row['fdIntroduceGrade']);
												var className = 'lui_icon_s_bad'
												if(m <= flag){
													className = 'lui_icon_s_good';
												}
												{$<li class='{%className%}'></li>$}
											}
										{$</ul></span>
										{%row['showCancelFlag']%}
									</div>
									<div class="intr_summary_detail intr_summary_color">
										<span>{%row['introduceType']%}</span>
										<span>{%row['introduceGoalNames'].replace(/;/gi,' , ')%}</span>
									</div>
									</div>
								$}
							</list:row-template>
						</list:rowTable>	
						<ui:event topic="list.loaded"  > 
						
							$("#intr_viewMain").css({height:"auto"});
						</ui:event>
					</list:listview>
					<list:paging channel="intr_ch1" layout="sys.ui.paging.simple"></list:paging>
				</div>
				<%} %>
			</div>
		</ui:content>
		
		<script>
			seajs.use('lui/topic',function(topic){
				topic.channel('intr_ch1').subscribe('list.changed',function(evt){
				var num = evt.page.totalSize;
				var intro=document.getElementById('intr_label_title').innerText;
				var introcontent=document.getElementsByName("introviewnames");

				if(intro.indexOf("(")>0){
					var text=intro.substring(0,intro.indexOf("("));
					var introcontent=document.getElementsByName("introviewnames");
					for(var i=0;i<introcontent.length;i++){
						introcontent[i].innerHTml=text+"("+num+")";
						introcontent[i].innerText=text+"("+num+")";
					}
				}else{
					for(var i=0;i<introcontent.length;i++){
						introcontent[i].innerHTml=intro;
						introcontent[i].innerText=intro;
						}
				}
				});
			})
			seajs.use('lui/topic',function(topic){
				topic.channel('intr_ch2').subscribe('list.changed',function(evt){
				var num = evt.page.totalSize;
				var intro=document.getElementById('intr_label_title').innerText;
				var introcontent=document.getElementsByName("introviewnames");

				if(intro.indexOf("(")>0){
					var text=intro.substring(0,intro.indexOf("("));
					var introcontent=document.getElementsByName("introviewnames");
					for(var i=0;i<introcontent.length;i++){
						introcontent[i].innerHTml=text+"("+num+")";
						introcontent[i].innerText=text+"("+num+")";
					}
				}else{
					for(var i=0;i<introcontent.length;i++){
						introcontent[i].innerHTml=intro;
						introcontent[i].innerText=intro;
						}
				}
				});
			})
		</script>
</c:if>
<c:set var="form" value="${requestScope[param.formName]}" />
<% 
Object form1 = pageContext.getAttribute("form");
if(form1 instanceof SysDocBaseInfoForm){
SysDocBaseInfoForm form=(SysDocBaseInfoForm)form1;
String fdModelName=form.getModelClass().getName();

String  fdModelId =form.getFdId(); 
request.setAttribute("fdModelId", fdModelId);
request.setAttribute("fdModelName", fdModelName);
SysDictModel dictModel = SysDataDict.getInstance().getModel(
		fdModelName);
String serviceInfo=dictModel.getServiceBean();
IBaseService baseService=(IBaseService) SpringBeanUtil.getBean(serviceInfo);
SysDocBaseInfo baseModel=(SysDocBaseInfo)baseService.findByPrimaryKey(fdModelId,null, true);
request.setAttribute("baseModel", baseModel);
 

%>  
	<script>
	function introduce_cancelIntroduce(){
		 
		var values = [];
		var fdIframeInfo="${lfn:message('sys-introduce:sysIntroduceMain.cancel.confirm')}";
		LUI.$("input[name='List_Selected']:checked").each(function(){
				values.push(LUI.$(this).val());
			});
		var fdModelId="${fdModelId}";
		if(typeof(fdModelId)!="undefined"&&fdModelId!="") {
			values.push(fdModelId);
			fdIframeInfo="${lfn:message('sys-introduce:sysIntroduceMain.cancel.thisOne')}";
		}
		if(values.length>0) {
				seajs.use(['lui/dialog','lui/topic'],function(dialog,topic){
					dialog.confirm(fdIframeInfo,function(val,dia){
						if(val){
							var str=LUI.$("#sysIntroduce_param_docSubject_hidden").val();
							str=encodeURIComponent(str);
							window.del_load = dialog.loading();
							var xurl = "<c:url value="/sys/introduce/sys_introduce_main/sysIntroduceMain.do?method=cancelIntro&fdModelName=${fdModelName}&fdModelId=" />"+fdModelId+"&docSubject="+str;
							var xdata = {};
							LUI.$.post(xurl,LUI.$.param({"List_Selected":values},true),function(json){
								if(window.del_load!=null)
									window.del_load.hide();
								//topic.publish("list.refresh");
								if(json.status){
									dialog.success("${lfn:message('return.optSuccess')}");
									window.location.reload();
								}else{
									dialog.failure("${lfn:message('return.optFailure')}");									
								}
							},'json');
						}
					});
				});
		}else{
			seajs.use(['lui/dialog'],function(dialog){
				dialog.alert("${lfn:message('page.noSelect')}");
			});
		}
	}
</script>
<%} %>