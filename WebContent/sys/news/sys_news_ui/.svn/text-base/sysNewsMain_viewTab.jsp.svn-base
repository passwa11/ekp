<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.authorization.constant.ISysAuthConstant"%>
<template:replace name="content">
	<script>
		<c:if test="${'preview' eq param.show}">
		seajs.use(["lui/jquery", 'lui/parser'],function($, parser) {
			var btn = "${lfn:message('button.close') }";
			__delBtn = function (toolbar) {
				if(toolbar.buttons && toolbar.buttons.length > 0){
					var buttons = toolbar.buttons;
					for(var index = 0; index < buttons.length; index++){
						if(buttons[index].config && buttons[index].config.text != btn) {
							buttons.splice(index, 1);
							--index;
						}
					}
				}
			}
			parser.parse.on("afterStartup", function(instances, instancesArray) {
				if(instances.toolbar) {
					var toolbar = instances.toolbar;
					toolbar.on("redrawButton", function() {
						__delBtn(this);
					});
					__delBtn(toolbar);
				}
			});
		});
		</c:if>
		seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
			//删除事件
			window.deleteDoc = function(delUrl){
				Com_Delete_Get(delUrl, 'com.landray.kmss.sys.news.model.SysNewsMain');
				return;
			};

			
                     //图片压缩函数
			window.doZipImage=function(){
				$('.lui_form_content_frame').find('img').each(function(){
					this.style.cssText="";
					var pt;
					if(this.height && this.height!="" && this.width && this.width != "")
						pt = parseInt(this.height)/parseInt(this.width);//高宽比
					if(this.width>700){
						this.width = 700;
						if(pt)
							this.height = 700 * pt;
					}
				});
			};
			$(document).ready(function(){
				var obj = document.getElementById("JGWebOffice_editonline")||document.getElementById("JGWebOffice_${sysNewsMainForm.fdModelId}");
				if(obj){
					obj.setAttribute("height", "550px");
				}
			});
			
			window.setTop=function(isTop,fdId){
				var values = [];
				values.push(fdId);
				
			    if(values.length==0){
					dialog.alert('${lfn:message("page.noSelect")}');
					return;
			   	}
			    var days=null;
				if(isTop){
				dialog.iframe("/sys/news/sys_news_main/sysNewsMain_topday.jsp","${lfn:message('sys-news:sysNewsMain.fdTopTime')}",function (value){
                         days=value;
                     	if(days==null){
							return;
						}else{
										window.del_load = dialog.loading();
										$.ajax({
											url: Com_Parameter.ContextPath+'sys/news/sys_news_main/sysNewsMain.do?method=setTop&categoryId=${sysNewsMainForm.fdTemplateId}&fdId=${param.fdId}',
											type: 'POST',
											data:$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days},true),
											dataType: 'json',
											error: function(data){
												if(window.del_load!=null){
													window.del_load.hide(); 
												}
												dialog.failure('${lfn:message("return.optFailure")}');
											},
											success: topCallback
										});									
						}
						},{width:400,height : 200});
				}else{		
					    days=0;		
					    dialog.confirm("${lfn:message('sys-news:news.setTop.confirmCancel')}",function(value){
						if(value==true){
							window.del_load = dialog.loading();
							$.ajax({
								url: Com_Parameter.ContextPath+'sys/news/sys_news_main/sysNewsMain.do?method=setTop&categoryId=${sysNewsMainForm.fdTemplateId}&fdId=${param.fdId}',
								type: 'POST',
								data:$.param({"List_Selected":values,"fdIsTop":isTop,"fdDays":days},true),
								dataType: 'json',
								error: function(data){
									if(window.del_load!=null){
										window.del_load.hide(); 
									}
									dialog.failure('${lfn:message("return.optFailure")}');
								},
								success: topCallback
							});		
						}
					});					
				}
			};
			window.topCallback = function(data){
				if(window.del_load!=null)
					window.del_load.hide();
				if(data!=null && data.status==true){
					dialog.success('${lfn:message("return.optSuccess")}');
					window.location.reload();
				}else{
					dialog.failure('${lfn:message("return.optFailure")}');
					window.location.reload();
				}
			};
		});
		
		
		function setFrameSize(){
			var frame = document.getElementById("IFrame_Content");
			if(frame){//金格控件
				// 金格控件中图片居中兼容
				var ___imgs = frame.contentWindow.document.getElementsByTagName('img');
				for(var j=0;j<___imgs.length;j++){
					___imgs[j].style.display='block';
					___imgs[j].style.margin='0 auto';
				}
				//获取所有a元素
				var elems = frame.contentWindow.document.getElementsByTagName("a");
				for(var i = 0;i<elems.length;i++){
					elems[i].setAttribute("target","_top");
				}
					var IFrame_Content = document.getElementById("IFrame_Content");
					var chs = IFrame_Content.contentWindow.document.body.childNodes;
					var bh = 0;
					for(var i=0;i<chs.length;i++){
						var tbh = chs[i].offsetTop + chs[i].offsetHeight;
						if(tbh > bh){
							bh = tbh;
						}
					}
						//var tmpHeight = (IFrame_Content.contentWindow.document.body.scrollHeight + 10 );
					document.getElementById("contentDiv").style.height = bh+"px";
			 }else{//rtf输出文本
				 // xform输出默认设置最大宽度
				// doZipImage();
			 }
		};
		
		 seajs.use(['lui/jquery','lui/dialog'], function($, dialog){
			 window.yqq=function(){
				 var ajaxUrl = Com_Parameter.ContextPath+"sys/news/sys_news_out_sign/sysNewsOutSign.do?method=queryStatus&signId=${param.fdId}";
				 var ajaxUrl2 = Com_Parameter.ContextPath+"sys/news/sys_news_out_sign/sysNewsOutSign.do?method=validateOnce&signId=${param.fdId}";
					$.ajax({
						url : ajaxUrl,
						type : 'post',
						data : {},
						dataType : 'text',
						async : true,     
						error : function(){
							dialog.alert('请求出错');
						} ,   
						success : function(data) {
							if(data == "false"){
								$.ajax({
									url : ajaxUrl2,
									type : 'post',
									data : {},
									dataType : 'text',
									async : true,     
									error : function(){
										dialog.alert('请求出错');
									} ,   
									success:function(data){
										if(data == "true"){
											dialog.alert("新闻当前节点已经发送过易企签签署，签署事件未完成，请完成后再查看签署！！");
										}else{
											 var infoUrl = "/sys/news/sys_news_main/sysNewsMain.do?method=openYqqExtendInfo&signId=${param.fdId}";
											 var extendIframe=dialog.iframe(infoUrl,"签署平台签署",null,{width:1200, height:600, topWin : window, close: true});
										}
									}
								});
							}else{
								var extendIframe=dialog.iframe(data,"签署平台签署",null,{width:1200, height:600, topWin : window, close: true});
							}
						}
					}); 
				
			 }
			 <c:if test="${sysNewsMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.yqqSign =='true' && yqqFlag=='true' && sysNewsMainForm.fdSignEnable=='true'}">
			 Com_Parameter.event["submit"].push(function(){
					//操作类型为通过类型 ，才判断是否已经签署
				if(lbpm.globals.getCurrentOperation().operation && lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
					 var flag = true;
					 var url = Com_Parameter.ContextPath+"sys/news/sys_news_out_sign/sysNewsOutSign.do?method=queryFinish&signId=${param.fdId}";
					 $.ajax({
							url : url,
							type : 'post',
							data : {},
							dataType : 'text',
							async : false,     
							error : function(){
								dialog.alert('请求出错');
							} ,   
							success:function(data){
								if(data == "true"){
									flag = true;
								}else{
									dialog.alert("当前签署任务未完成，无法提交！！");
									flag = false;
								}
							}
						});
					 return flag;
				}else{
					return true;
				}
			 });
	 	</c:if>
		 });
   </script>
	<c:set var="sysDocBaseInfoForm" value="${sysNewsMainForm}" 	scope="request" />
	<c:set var="collapsed" value="false"></c:set>
	<c:if test="${sysNewsMainForm.docStatusFirstDigit>='3'}">
		<c:set var="collapsed" value="true"></c:set>
	</c:if>
	<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewContent.jsp" charEncoding="UTF-8">
 		 <c:param name="contentType" value="xform" />
 		 <c:param name="sysNewsMainForm_fdId" value="${sysNewsMainForm.fdId}" />
  	</c:import>
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-supportExpand="true" var-expand="${sysNewsMainForm.docStatus<30}" var-average='false' var-useMaxWidth='true'>
	  			<%@include file="/sys/news/sys_news_ui/sysNewsMain_viewTab_include.jsp"%>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
	  		<ui:tabpage expand="false" collapsed="${collapsed}">
				<%@include file="/sys/news/sys_news_ui/sysNewsMain_viewTab_include.jsp"%>
			</ui:tabpage>
			<!-- </form> -->
		</c:otherwise>
	</c:choose>
</template:replace>

<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<c:choose>
				<c:when test="${sysNewsMainForm.docStatus>='30' || sysNewsMainForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 基本信息-->
						<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewBaseInfo.jsp" charEncoding="UTF-8"></c:import>
						<c:if test="${not empty requestScope['sysNewsMainForm'].sysTagMainForm.fdTagNames}" >
							<ui:content title="${lfn:message('sys-tag:sysTagTags.title') }" titleicon="lui-fm-icon-2">
								<div style="padding-left: 5px">
									<!-- 标签机制 -->
									<c:import url="/sys/tag/import/sysTagMain_view.jsp" charEncoding="utf-8">
										<c:param name="formName" value="sysNewsMainForm" />
										<c:param name="useTab" value="false" />
										<%-- <c:param name="showTitle" value="false" /> --%>
									</c:import>
								</div>
							</ui:content>
						</c:if>
						<!-- 关联配置 -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysNewsMainForm" />
							<c:param name="approveType" value="right" />
						</c:import>
					</ui:accordionpanel>
				</c:when>
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
	               		<%--流程机制 --%>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysNewsMainForm" />
							<c:param name="fdKey" value="newsMainDoc" />
		                    <c:param name="approveType" value="right" />
							<c:param name="approvePosition" value="right" />
	               		</c:import>
	               		<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysNewsMainForm" />
							<c:param name="fdModelId" value="${sysNewsMainForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.sys.news.model.SysNewsMain" />
						</c:import>
						<!-- 关联配置 -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="sysNewsMainForm" />
							<c:param name="approveType" value="right" />
							<c:param name="needTitle" value="true" />
						</c:import>
						<!-- 基本信息-->
						<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewBaseInfo.jsp" charEncoding="UTF-8"></c:import>
						<c:if test="${not empty requestScope['sysNewsMainForm'].sysTagMainForm.fdTagNames}" >
						<ui:content title="${lfn:message('sys-tag:sysTagTags.title') }" titleicon="lui-fm-icon-5">
							<div style="padding-left: 5px">
								<!-- 标签机制 -->
								<c:import url="/sys/tag/import/sysTagMain_view.jsp" charEncoding="utf-8">
									<c:param name="formName" value="sysNewsMainForm" />
									<c:param name="useTab" value="false"></c:param>
								</c:import>
							</div>
						</ui:content>
						</c:if>
					</ui:tabpanel>
					
				</c:otherwise>
			</c:choose>
			
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
			<c:if test="${sysNewsMainForm.docStatus ne 30 && sysNewsMainForm.docStatus ne 40}">
	  			<div style="min-width:200px;"></div>
				<ui:accordionpanel style="min-width:200px;"> 
					<c:import url="/sys/news/sys_news_ui/sysNewsMain_viewContent.jsp" charEncoding="UTF-8">
	 		 			<c:param name="contentType" value="info" />
	 		 			<c:param name="sysNewsMainForm_fdId" value="${sysNewsMainForm.fdId}" />
	  				</c:import>
				</ui:accordionpanel>
			</c:if>
			<%--关联机制 --%>
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="sysNewsMainForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>