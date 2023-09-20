<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view">
	<template:replace name="title">
		<bean:write	name="kmsMedalMainForm" property="fdName" />
	</template:replace>
	<template:replace name="head">
		<link type="text/css" rel="stylesheet" href="<c:url value="/kms/medal/resource/css/picview.css"/>" />
	</template:replace>
	<template:replace name="toolbar">
		<script>Com_IncludeFile('dialog.js');</script>
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
			<kmss:auth requestURL="/kms/medal/kms_medal_main/kmsMedalMain.do?method=edit&fdId=${param.fdId}" 
						requestMethod="GET">
				<ui:button text="${lfn:message('button.edit')}" 
							onclick="Com_OpenWindow('kmsMedalMain.do?method=edit&fdId=${param.fdId}','_self');" 
							order="2">
				</ui:button>
			</kmss:auth>
			<kmss:auth requestURL="/kms/medal/kms_medal_main/kmsMedalMain.do?method=delete&fdId=${param.fdId}" requestMethod="GET">
				<ui:button text="${lfn:message('button.delete')}" order="4"
							onclick="deleteDoc('${param.fdId}')">
				</ui:button> 
			</kmss:auth>
			<kmss:auth requestURL="/kms/medal/kms_medal_main/kmsMedalMain.do?method=downloadTemplate&categoryId=${kmsMedalMainForm.fdCategoryId}" requestMethod="GET">
				<ui:button text="${lfn:message('kms-medal:kmsMedalMain.temp.downLoad')}" order="1"
							onclick="window.open('${LUI_ContextPath }/kms/medal/kms_medal_main/kmsMedalMain.do?method=downloadTemplate&categoryId=${kmsMedalMainForm.fdCategoryId}')">
				</ui:button> 
			</kmss:auth>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" id="simplecategoryId">
			<ui:menu-item text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home" href="/" target="_self">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('kms-medal:module.kms.medal') }"
				href="/kms/medal/" target="_self">
			</ui:menu-item>
			<ui:menu-source autoFetch="false" target="_self"
				href="/kms/medal?#cri.q=fdCategory:!{value}">
				<ui:source type="AjaxJson">
					{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName=com.landray.kmss.kms.medal.model.KmsMedalCategory&categoryId=${kmsMedalMainForm.fdCategoryId}&currId=!{value}"} 
				</ui:source>
			</ui:menu-source>
		</ui:menu>
	</template:replace>
	<template:replace name="content">
		<div class='lui_form_title_frame'>
			<div class='lui_form_subject'>
				    <bean:write	name="kmsMedalMainForm" property="fdName" />
			</div>
			<div style="height: 15px;"></div>
			<c:if test="${not empty kmsMedalMainForm.fdIntroduction}">
				<div class='lui_form_summary_frame' style="margin-left:34px;margin-right:34px;">
					<c:out value="${kmsMedalMainForm.fdIntroduction }"/>
				</div>
			</c:if>
			<div style="height: 15px;"></div>
		</div>
		<div class="lui_form_content_frame">
			<table class="tb_simple" width=100% style="margin-left: auto;">
			<tr class="min-sty">
				<td width="80px;"style="padding-left:19px;">
					<bean:message bundle="kms-medal" key="kmsMedalMain.fdOwners"/>
				</td>
				<td style="padding-left: 20px;color: #0000ff;">
					<input type="hidden" name="fdOwnerIds">
					<input type="hidden" name="fdOwnerNames">
				<kmss:auth requestURL="/kms/medal/kms_medal_main/kmsMedalMain.do?method=importOwners&fdId=${param.fdId}" requestMethod="GET">	 
					<span style="cursor: pointer;" onclick="addOwners();">
						<bean:message bundle="kms-medal" key="kmsMedalMain.addOwners"/>
					</span>
					<form id="batchImportOwnerForm" style="display: inline;" target="batchImportOwnerframe" method="post" enctype="multipart/form-data" action="${LUI_ContextPath}/kms/medal/kms_upload_excel/kmsUploadExcel.do?medalId=${param.fdId}">
						<span style="margin-left: 20px;cursor: pointer;">
							<bean:message bundle="kms-medal" key="kmsMedalMain.excelImport"/>
						</span>
							
						<input accept="application/vnd.ms-excel" type="file" 
							name="excelFile" id="excelFile" class="file" onchange="uploadExcel();">
							
						<span style="color: red;">（${lfn:message('kms-medal:kmsMedalMain.support')}）</span>
					</form>
					<iframe name="batchImportOwnerframe" width="0" height="0" id="batchImportOwnerframe" src="about:blank" frameborder="0" marginwidth="0">
					</iframe>
					<table class="tb_simple info_table" id="info_table" width=95% style="margin:0px  auto;"   cellpadding="1" cellspacing="0" >
					</table>
				</kmss:auth>
				</td>
				<td align="right" style="padding-right:20px;">
					<input type="text" class="inputsgl" id="searchText">
					<ui:button text="${lfn:message('button.search')}"  onclick="searchOwners();"></ui:button>
					<ui:button text="${lfn:message('button.reset')}"  onclick="searchReset();"></ui:button>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<list:listview channel="medalOwners" id="medalOwnersList">
							<ui:source type="AjaxJson">
								{url:'/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=data&fdMedalId=${param.fdId}&rowsize=30&orderby=docHonoursTime&ordertype=down'}
							</ui:source>
						<list:gridTable name="gridtable" columnNum="10" >
							<list:row-template >	
								<%@include file="/kms/medal/extend/listview/grid-table-render.jsp" %>
							</list:row-template>
						</list:gridTable>
                    	<list:paging channel="medalOwners"></list:paging>
					</list:listview>
					<kmss:auth requestURL="/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=deleteByUserId&medalId=${kmsMedalMainForm.fdId}">
					<script>
					seajs.use(["lui/dialog", "lui/jquery", "lui/topic"], function(dialog, $, topic) {
						$("#medalOwnersList").on("click" , ".luiRemoveIcon", function() {
							var self = $(this);
							dialog.confirm("${lfn:message('kms-medal:kmsMedalOwner.delete.confirm')}" , function(flag) {
								if(!flag) {
									return;
								}
								var id = self.attr("data-id");
		                		if(id) {
		                				$.ajax({
		                					method : "post",
		                					url : "${LUI_ContextPath}/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=deleteByUserId",
		                					data : {
		                						userIds : id,
		                						medalId : "${kmsMedalMainForm.fdId}"
		                					},
		                					success : function() {
		                						dialog.success("${lfn:message('return.optSuccess')}");
		                						topic.channel("medalOwners").publish('list.refresh');
		                						window.location.href=window.location.href; 
											  	window.location.reload;
		                					}
		                				});
		                		}
							});
	            		});
					});
					</script>
					</kmss:auth>
				</td>
			</tr>
			</table>
		</div>
		<%-- 开启机制代码要配合修改后台Java代码 --%>
		<ui:tabpage expand="false">
			<!--授勋日志 -->
			<c:import url="/kms/medal/kms_medal_main/kmsMedalMain_loglist.jsp" charEncoding="UTF-8">
				<c:param name="fdMedalId" value="${kmsMedalMainForm.fdId}" />
			</c:import>	
		</ui:tabpage>
	</template:replace>
	<template:replace name="nav">
		<div style="min-width:200px;"></div>
		<ui:accordionpanel style="min-width:200px;"> 
			<ui:content title="${ lfn:message('kms-medal:module.kms.medal') }" toggle="true">
				<table class="tb_simple"  width="100%" >
					<tr><td style="text-align: center;">
					<c:if test="${not empty bigPicId}">
					<div class="inner">
                          <img src="<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${bigPicId}"/>">
                          <p class="txto" style="text-align: left;"><img src="<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${smallPicId}"/>">
                          <span title="${kmsMedalMainForm.fdName}" class="txt">${kmsMedalMainForm.fdName}</span>	</p>
                     </div>
					</c:if>
					</td></tr>
				  </table>				
			</ui:content>
			<ui:content title="${ lfn:message('kms-medal:kmsMedalMain.baseInfo') }" toggle="true">
				<ul class='lui_form_info'>
					<li>
						<bean:message bundle="kms-medal" key="kmsMedalMain.fdOwnerCount"/>：
						${ kmsMedalMainForm.fdOwnerCount }
					</li>				
					<li>
						<bean:message bundle="kms-medal" key="kmsMedalMain.fdValidTime"/>：
						<c:if test="${kmsMedalMainForm.fdValidTime ne null}">
							${fn:substring(kmsMedalMainForm.fdValidTime,0, 10)}
						</c:if>
						<c:if test="${kmsMedalMainForm.fdValidTime eq null}">
							${ lfn:message('kms-medal:kmsMedalMain.fdValidTime.forever') }
						</c:if>
					</li>			
					<li>
						<bean:message bundle="kms-medal" key="kmsMedalMain.docCreator"/>：
						<ui:person personId="${kmsMedalMainForm.docCreatorId}" personName="${kmsMedalMainForm.docCreatorName}">
						</ui:person>
					</li>
					<li>
						<bean:message bundle="kms-medal" key="kmsMedalMain.docCreateTime"/>：
						${ kmsMedalMainForm.docCreateTime }
					</li>
					<c:if test="${not empty kmsMedalMainForm.docAlterorId}">
						<li>
							<bean:message bundle="kms-medal" key="kmsMedalMain.docAlteror" />：
							<ui:person personId="${kmsMedalMainForm.docAlterorId}" personName="${kmsMedalMainForm.docAlterorName}">
							</ui:person>
						</li>
						<li>
							<bean:message bundle="kms-medal" key="kmsMedalMain.docAlterTime" />：
							${ kmsMedalMainForm.docAlterTime}
						</li>	
					</c:if>		
				</ul>			
			</ui:content>
			<ui:content title="${lfn:message('kms-medal:kmsMedalMain.fdCategory') }" toggle="true">
				<ul class="lui_form_info">
					<li>
						<bean:message bundle="kms-medal" key="kmsMedalMain.fdCategory"/> :
						<c:out value="${kmsMedalMainForm.fdCategoryName}" />
					</li>
				</ul>
			</ui:content>
		</ui:accordionpanel>
	<script>
	function deleteDoc(fdId){
		seajs.use(["lui/jquery", 'lui/dialog'],function($, dialog){
			dialog.confirm('<bean:message key="page.comfirmDelete"/>',function(isOk){
				if(isOk){
					var preUrl = "${LUI_ContextPath}/kms/medal/kms_medal_main/kmsMedalMain.do?";
					$.getJSON(preUrl + "method=validateDelete&fdIds=" + fdId,
						function(data) {
							if(data.isRela == true) {
								dialog.alert(data.errMsg);
							}  else {
								Com_OpenWindow(preUrl + "method=delete&fdId=" + fdId,'_self');
							}
					});
				}	
			});
		});
	}
	function addOwners(){
		//验证勋章的有效日期
		if(isExpired()) {
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
				 dialog.failure("勋章已经失效无法授勋");
			 });
			return;
		}
		Dialog_Address(true,'fdOwnerIds','fdOwnerNames',';',ORG_TYPE_PERSON, afterAddOwners);
	}
	function afterAddOwners(rntData){
		if(rntData == null){
			return;
		}
		var data = rntData.data;
		if(data == null || data.length <=0 ){
			return;
		}
		var ids = "";
		for(var i=0, len= data.length; i < len; i++){
			ids += ";" + data[i].id; 
		}
		if(ids.length > 0){
			ids = ids.substring(1);
		}
		$.ajax({
			url : '${LUI_ContextPath}/kms/medal/kms_medal_main/kmsMedalMain.do?method=addOwners',
			async : false, // 注意此处需要同步
			data: {"fdId": "${kmsMedalMainForm.fdId}", "fdOwnerIds": ids},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if(data.success == true){
					var url="";
					if(data.isNotAll){
					    url="/kms/medal/kms_medal_main/kmsMedalEditionMain_isNotAll_edit.jsp";
					}else{
						url="/kms/medal/kms_medal_main/kmsMedalEditionMain_isAll_edit.jsp";
					}
					
					if(data.ids!=""){
					  ids=data.ids+"";
						 seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
								dialog.iframe(url,
											  "${ lfn:message('kms-medal:kmsMedal.honours.inform') }",
											  null
											 /*  function() {
												   window.location.reload();
											  } */, 
											  {
												  "width" : 600,
												  "height" : 300,
												  "topWin": window,
												  buttons:[
															{
																name : "${lfn:message('button.save')}",
																value : true,
																focus : true,
																fn : function(value,_dialog) {
																	var _iframe = _dialog.content.iframeObj[0];
																	var _doc = _iframe.contentDocument;
																	var doc=$(_doc);
																	
																   //获取选择通方式
																	var fdNotifyType=doc.find("input[name='fdNotifyType']").val();
																	 if(fdNotifyType==""){
																		   _dialog.alert('<bean:message bundle="kms-medal" key="kmsMedal.select.notification"/>', function(){
																				return;
																			});
																		   return;
																	  }
																   
																   $.ajax({
																		  url:"${LUI_ContextPath}/kms/medal/kms_medal_main/kmsMedalMain.do?method=saveSendTodoFromResource",
																		  async:false,//注意要同步
																		  data:{"fdId": "${kmsMedalMainForm.fdId}","fdNotifyType": fdNotifyType, "fdOwnerIds": ids},
																		  type:"POST",
																		  dataType:"json",
																		  success :function(data){
																			  if(data.success == true){
																				  window.location.href=window.location.href; 
																				  window.location.reload;
																				  _dialog.hide(); 
																			  }
																		  }
																	  })
															
																}
															},
															
													     	{
																name : "${lfn:message('button.cancel')}",
																value : false,
																fn : function(value, _dialog) {
																	_dialog.hide();
																	window.location.href=window.location.href; 
																	window.location.reload;
																}
															}
															
															
														]
											  });
								
						});
						}else{
							seajs.use(["lui/dialog"], function(dialog){
								dialog.alert('<bean:message bundle="kms-medal" key="kmsMedalMain.haveHonoured"/>', function(){
									window.location.href=window.location.href; 
									window.location.reload;
								});
							});
						}
				}
			}
		});
	}
	function searchOwners(){
		seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic){
			var searchText = $.trim($("#searchText").val());
			if(searchText == null || searchText == ''){
				dialog.alert('<bean:message bundle="kms-medal" key="kmsMedalMain.keywords.required"/>');
				return;
			}
			if(searchText.length > 20){
				dialog.alert('请减少搜索关键字！');
				return;
			}
			
			 topic.channel("medalOwners").publish('criteria.changed' ,
                     { criterions: [{key:"_fdName" , value:[searchText]}]});
			
		});
	}
	function searchReset(){
		seajs.use(['lui/jquery','lui/topic'], function($, topic){
			$("#searchText").val('');
			topic.channel("medalOwners").publish('criteria.changed' ,
                { criterions: [{key:"_fdName" , value:[]}]});
		});
	}
		/*
		*file控件onchange事件触发
		*/
		function uploadExcel() {
			if(isExpired()) {
				seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic){
					 dialog.failure("勋章已经失效无法授勋");
				 });
				return;
			}
			var fileName = $("#excelFile").val();
			var ext = fileName.substr(fileName.lastIndexOf(".") + 1).toLowerCase();
			if(ext != "xls") {
				seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
					dialog.alert('上传文件类型不符合');
				});
				return;
			}
			$("#batchImportOwnerForm").submit();
		}
		/*
		*load框
		*/
		var load = null;
		function loadDialog() {
			seajs.use( [ 'sys/ui/js/dialog' ], function(dialog) {
				load = dialog.loading('excel导入中...');
			});
		}
		/*
		*展示校验结果
		*/
		function showValidateInfo(data) {
			var info_table = $("#info_table");
			info_table.empty();
			var size = data.length;
			var str="";
			if(size ==1) {
				/* info_table.append("<tr><td colspan='2' style='border:0;height:15px;'></td><tr><td</tr><tr><td width='15px;' style='text-align:center;font-size:18px;color:red;height:90px;line-height:90px;border:0;'>人员全部授勋成功！</td></tr>"); */
				str+="<tr><td colspan='2' style='text-align:center;font-size:18px;color:red;border:0;width:90px;padding-left:80px;'>人员全部授勋成功！</td></tr>";
			}
			for(var i = 0; i < size; i++) {
				if(data[i]['loginName'] != null) {
					var loginName_tr = "<tr><td</tr><tr><td width='15px;' style='text-align:left;'>没有需要授勋的人员</td>";
					loginName_tr += "<td width='100px;' style='text-align:left;'><span style='color:red;'>" + data[i]['loginName'] +"</td></tr>";
					//info_table.append(loginName_tr);
					str+=loginName_tr;
				}
				if(data[i]['sameInExcel'] != null) {
					var sameInExcel_tr = "<tr><td width='15px;' style='text-align:left;'>excel中相同的授勋人员</td>";
					sameInExcel_tr += "<td width='100px;' style='text-align:left;'><span style='color:red;'>" + data[i]['sameInExcel'] +"</td></tr>";
					//info_table.append(sameInExcel_tr);
					str+=sameInExcel_tr;
				}
				if(data[i]['isAvailable'] != null) {
					var isAvailable_tr = "<tr><td width='15px;' style='text-align:left;'>excel中无效的授勋人员</td>";
					isAvailable_tr += "<td width='100px;' style='text-align:left;'><span style='color:red;'>" + data[i]['isAvailable'] +"</td></tr>";
					//info_table.append(isAvailable_tr);
					str+=isAvailable_tr;
				}
				if(data[i]['sameInOwner'] != null) {
					var sameInOwner_tr = "<tr><td width='15px;' style='text-align:left;'>excel中已经被授勋的人员</td>";
					sameInOwner_tr += "<td width='100px;' style='text-align:left;'><span style='color:red;'>" + data[i]['sameInOwner'] +"</td></tr>";
					//info_table.append(sameInOwner_tr);
					str+=sameInOwner_tr;
				}
			}
			
		 
			//var str="<tr><td style='text-align:left;width:200px;'>是否发送通知给已经授勋人员</td><td><INPUT type='checkbox' name='checkbox' value='1'/>待办";
			 //   str+="<input type='checkbox' name='checkbox' value='2' />邮件</td></tr>";
			  //  info_table.append(str);
			    
			    
		
		 	 var arr1 = [  {
						//确定
						name : "确定",
						value : true,
						focus : true,
						fn : function(value, _dialog) {
							
							var _iframe = _dialog.content.iframeObj[0];
							var _doc = _iframe.contentDocument;
							var doc=$(_doc);
							
						   //获取选择通方式
							var fdNotifyType=doc.find("input[name='fdNotifyType']").val();
							 if(fdNotifyType==""){
								   _dialog.alert('<bean:message bundle="kms-medal" key="kmsMedal.select.notification"/>', function(){
										return;
									});
								   return;
							  }
							  $.ajax({
								  url:"${LUI_ContextPath}/kms/medal/kms_medal_main/kmsMedalMain.do?method=saveSendTodoFromResource",
								  async:false,//注意要同步
								  data:{"fdId": "${kmsMedalMainForm.fdId}","fdNotifyType":fdNotifyType, "fdOwnerIds": ids},
								  type:"POST",
								  dataType:"json",
								  success :function(data){
									  if(data.success == true){
										  window.location.href=window.location.href; 
										  window.location.reload; 
										  _dialog.hide(); 
									  }
								  }
							  })
						}
					},
					 {
						//取消
						name : "取消",
						value : true,
						focus : true,
						fn : function(value, _dialog) {
							_dialog.hide();
							window.location.href=window.location.href; 
							window.location.reload;
							  
						}
					} 
					]; 
			 
			 var arr2 = [  
					 {
						//取消
						name : "取消",
						value : true,
						focus : true,
						fn : function(value, _dialog) {
							_dialog.hide();
							window.location.href=window.location.href; 
							window.location.reload;
						}
					} 
					]; 
			
			 var ids="";
			 var arr3=[];
			 for(var i=0;i<data.length;i++){
					 ids=data[i].ids+"";
			 }
			 if(ids=="undefined"){
				 arr3=arr2;
			 }else{
				 arr3=arr1; 
			 } 
			 
			 htmlTmp=encodeURI(encodeURI(str));
			 var url="/kms/medal/kms_medal_main/kmsMedalEditionMain_isNotAll_uploadExcel.jsp?htmlTmp="+htmlTmp;
			 info_table.empty();
			 
			 seajs.use(['lui/jquery','lui/dialog'],function($,dialog) {
					dialog.iframe(url,
								  "${ lfn:message('kms-medal:kmsMedal.honours.inform') }",
								  null
								 /*  function() {
									   window.location.reload();
								  } */, 
								  {
									  "width" : 600,
									  "height" : 300,
									  "topWin": window,
									  buttons:arr3
								  });
					
			})
			 
		}
		
	    //加载选择事件
		function loadCheckbox(){
			seajs.use(["lui/dialog", "lui/jquery", "lui/topic"], function(dialog, $, topic) {
			         $("input[type=checkbox]").click(function(){
	
			        	var checked=$(this).is(":checked");
			        	if(checked==true){
			        		$(this).attr("checked","checked");
			        	}else{
			        		$(this).removeAttr("checked");
			        	}
			         });
			});
		}
		
		/*
		*校验勋章是否过期
		*/
		function isExpired() {
			var expiredDate = "${kmsMedalMainForm.fdValidTime}".substring(0, 10);
			expiredDate = expiredDate.replace(/\-/gi,"/");
			var nowDate = new Date();
			var nowDateStr = nowDate.getFullYear() + "-" + (nowDate.getMonth() + 1) + "-" + nowDate.getDate();
			nowDateStr = nowDateStr.replace(/\-/gi,"/");
			//当前时间
			var time1 = new Date(nowDateStr).getTime();
			//有效时间
			var time2 = new Date(expiredDate).getTime();
			if(time2 < time1) {
				//失效
				return true;
			}
			return false;
		}
	</script>
		</template:replace>	
</template:include>
