<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page import="com.landray.kmss.util.*,com.landray.kmss.third.ding.constant.DingConstant" %>
<%
	String domain = DingConfig.newInstance().getDingDomain();
	String redirctUrl = "/sys/notify/sys_notify_todo/sysNotifyTodo.do?method=view&fdId=";
	if(StringUtil.isNotNull(domain)){
		redirctUrl=domain+redirctUrl;
	}else{
		redirctUrl= StringUtil.formatUrl(redirctUrl);
	}

	String apiType = DingConfig.newInstance().getNotifyApiType();
	boolean canReSend = false;
	if("WR".equals(apiType) || "TODO".equals(apiType) ){
		canReSend = true;
	}
	String apiText = "<font color='red'>不支持重推</font>";
	if("WR".equals(apiType)){
		apiText = "待办V1.0";
	}else if ("TODO".equals(apiType)){
		apiText = "待办V2.0";
	}
%>
<style type="text/css">
		.tb_simple .inputsgl {
    height: 25px;
    border: 0px;
    border: 0px solid #dfdfdf; 
}
.tb_simple .inputselectsgl{
	    width: 30% !important;
}
</style>		
		
<template:include ref="config.profile.edit">
    <template:replace name="content">
         <table class="tb_normal" width="90%">
        	<tr>
        			<td class="td_normal_title" width="15%" style="text-align: right;"">请选择人员：</td>
								<td>
									<div id="_xform_fdOrgAdminIds" _xform_type="text">
                        				<div class="inputselectsgl" style="width:51%;">
                        				<input type="hidden" style="width:100px" name="ekpUserId" readonly="true" /> 
                        				<xform:text property="ekpUserName" showStatus="edit" htmlElementProperties="readOnly='true'" style=" height: 24px; width: 98%;"  subject=""></xform:text>
                        				</div>
                       					<a href="#" onclick="Dialog_Address(true, 'ekpUserId', 'ekpUserName', null, ORG_TYPE_PERSON);">
										<bean:message key="dialog.selectOrg" /></a>
										<%-- <ui:button text="清理" onclick="cleaningNotify();" style="vertical-align: top;margin-left: 60px;"></ui:button>	 --%>
										<ui:button text="查询" onclick="queryNotify();" style="vertical-align: top;margin-left: 20px;"></ui:button>
										<ui:button text="一键清理和推送" onclick="cleanAndResend();" style="vertical-align: top;margin-left: 0px;"></ui:button>
							</div>
								</td>
        	</tr>
		</table>

		<div style="margin-left: 30px">
			<br>
			<strong style="color: red">对比结果仅供参考，请以实际的数据为准！接口权限等会影响对比结果，请谨慎操作 !!!</strong>
		</div>
		
		<table id="Label_Tabel" width=95% style="margin: 30px">
		    <tr LKS_LabelName="已推送的待办">
		      <td>
		        <table class="tb_normal" width=100% id="hadSend" style="text-align: center;">
		           <tr>
		              <td>待办标题</td>
		              <td>待办ID</td>
		              <td>钉钉待办ID</td>
		              <td>待办人员</td>
		              <td>接口方式</td>
		              <td>钉钉状态</td>
		           </tr>
		        </table>
		      </td>
		    </tr>
		    <tr LKS_LabelName="未推送的待办">
		      <td>
		        <table class="tb_normal" width=100% id="notSend" style="text-align: center;">
		           <tr>
		              <td>待办标题</td>
		              <td>待办ID</td>
		              <td>待办人员</td>
		              <td>接口方式</td>
		              <td>失败次数</td>
		           </tr>
		        </table>
		      </td>
		    </tr>
		    <tr LKS_LabelName="已处理但未消失的待办">
		      <td>
		        <table class="tb_normal" width=100% id="updateFail" style="text-align: center;">
		           <tr>
		              <td><input type="checkbox" onclick="updateFailCheckAll()" id="updateFailCheckAll"/></td>
		              <td>待办标题</td>
		              <td>待办ID</td>
		              <td>钉钉待办ID</td>
		              <td>待办人员</td>
		              <td>接口方式</td>
		              <td>钉钉状态</td>
		           </tr>
		        </table>
		      </td>
		    </tr>
		    <tr LKS_LabelName="匹配异常的数据">
		      <td>
		        <table class="tb_normal" width=100% id="checkError" style="text-align: center;">
		           <tr>
		              <td>待办标题</td>
		              <td>待办人员</td>
		              <td>接口方式</td>
		              <td>URL</td>
		           </tr>
		        </table>
		      </td>
		    </tr>
		</table>		       
        <script type="text/javascript">
        	Com_IncludeFile("dialog.js");
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                modelName: 'com.landray.kmss.third.ding.model.ThirdDingNotify',
                templateName: '',
                basePath: '/third/ding/third_ding_notify/thirdDingNotify.do',
                canDelete: '${canDelete}',
                mode: '',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
            seajs.use(['lui/jquery','lui/dialog'],function($, dialog){
				function queryNotify(){
					var ekpUserId = $("input[name='ekpUserId']").val();
					if(ekpUserId == "" || ekpUserId == null){
						dialog.alert("请选择人员！");
						return;
					}
					var d = dialog.loading();
             		var url ="${LUI_ContextPath}/third/ding/third_ding_notify/thirdDingNotify.do?method=queryNotify&userId="+ekpUserId;       			
             		$.ajax({
						url : url,
						type : 'post',
						async : true,
						dataType : "json",
						success : function(data) {
							if(data.success){
								//已推送
								if(data.hadSendArray){
									console.log(data.hadSendArray);
									$("#hadSend").empty();
									$("#hadSend").append('<tr style="font-weight: bold;"><td>序号</td><td>待办标题</td><td>待办ID</td><td>钉钉待办ID</td><td>待办人员</td><td>接口方式</td><td>钉钉状态</td></tr>');
									//角标，出现两个id一样的，待处理
									$("table[id='Label_Tabel'] #Label_Tabel_Label_Btn_2").each(function(){
										$(this).attr("value",'已推送的待办('+data.hadSendArray.length+')');
									});
									for(var i=0;i<data.hadSendArray.length;i++){
										$("#hadSend").append('<tr><td>'+(i+1)+'</td><td>'+'<a target="_blank" href="<%=redirctUrl%>'+data.hadSendArray[i].notifyFdId+'">'+data.hadSendArray[i].title+'</a>'+'</td><td>'+data.hadSendArray[i].notifyFdId+'</td><td>'+data.hadSendArray[i].dingNotifyId+'</td><td>'+data.hadSendArray[i].user+'</td><td>'+data.hadSendArray[i].type+'</td><td>'+data.hadSendArray[i].status+'</td></tr>');
									}
								}
								//未推送的待办
								if(data.notSendArray){
									console.log(data.notSendArray);
									$("#notSend").empty();
									$("#notSend").append('<tr><td colspan="6" style="text-align:right;"><div style="margin-right:20px"><font style="color:red">提醒: 待办重推必须选择待办任务接口V1.0或者V2.0才能使用。原来使用工作流接口推送失败的待办，也将改用待办任务接口推送。</font><input type="button" value="批量推送"  onclick="reSend()"/></div></td></tr>');
									$("#notSend").append("<tr style='font-weight: bold;'><td><input type='checkbox' onclick='notSendCheckAll()' id='notSendCheckAll'/></td><td>序号</td><td>待办标题</td><td>待办ID</td><td>待办人员</td><td>接口方式</td></tr>");
									//角标，出现两个id一样的，待处理
									$("#Label_Tabel_Label_Btn_3").each(function(){
										$(this).attr("value",'未推送的待办('+data.notSendArray.length+')');
									});
									for(var i=0;i<data.notSendArray.length;i++){
										$("#notSend").append("<tr><td><input type='checkbox' name='notSendCheck'/><input type='hidden' name='notSendData' value='"+JSON.stringify(data.notSendArray[i]).toString()+"'/></td><td>"+(i+1)+"</td><td>"+"<a target='_blank' href='<%=redirctUrl%>"+data.notSendArray[i].notifyFdId+"'>"+data.notSendArray[i].title+"</a></td><td>"+data.notSendArray[i].notifyFdId+"</td><td>"+data.notSendArray[i].user+"</td><td><%=apiText%></td></tr>");
									}
								}
								
								//处理了但是待办不消失的问题
								if(data.updateFailArray){
									console.log(data.updateFailArray);
									$("#updateFail").empty();
									$("#updateFail").append('<tr><td colspan="8" style="text-align:right;"><input type="button" value="批量清理" style="margin-right:20px" onclick="cleaningFail()"/></td></tr>');
									$("#updateFail").append("<tr style='font-weight: bold;'><td><input type='checkbox' onclick='updateFailCheckAll()' id='updateFailCheckAll'/></td><td>序号</td><td>待办标题</td><td>待办ID</td><td>钉钉待办ID</td><td>待办人员</td><td>接口方式</td><td>钉钉状态</td></tr>");
									//角标，出现两个id一样的，待处理
									$("#Label_Tabel_Label_Btn_4").each(function(){
										$(this).attr("value",'已处理但未消失的待办('+data.updateFailArray.length+')');
									});
									for(var i=0;i<data.updateFailArray.length;i++){
										$("#updateFail").append("<tr><td><input type='checkbox' name='updateFailCheck'/><input type='hidden' name='failData' value='"+JSON.stringify(data.updateFailArray[i]).toString()+"'/></td><td>"+(i+1)+"</td><td>"+"<a target='_blank' href='<%=redirctUrl%>"+data.updateFailArray[i].notifyFdId+"'>"+data.updateFailArray[i].title+"</a>"+"</td><td>"+data.updateFailArray[i].notifyFdId+"</td><td>"+data.updateFailArray[i].dingNotifyId+"</td><td>"+data.updateFailArray[i].user+"</td><td>"+data.updateFailArray[i].type+"</td><td>"+data.updateFailArray[i].status+"</td></tr>");
									}
								}
								
								//异常数据
								if(data.checkErrorArray){
									console.log(data.checkErrorArray);
									$("#checkError").empty();
									$("#checkError").append('<tr style="font-weight: bold;"><td>序号</td><td>待办标题</td><td>待办人员</td><td>接口方式</td><td>URL/待办ID</td><td>异常类型</td></tr>');
									//角标，出现两个id一样的，待处理
									$("#Label_Tabel_Label_Btn_5").each(function(){
										$(this).attr("value",'匹配异常的数据('+data.checkErrorArray.length+')');
									});
									for(var i=0;i<data.checkErrorArray.length;i++){
										var urlMsg = data.checkErrorArray[i].url;
										if(data.checkErrorArray[i].taskId){
											urlMsg = data.checkErrorArray[i].taskId;
										}
										$("#checkError").append('<tr><td>'+(i+1)+'</td><td>'+data.checkErrorArray[i].title+'</td><td>'+data.checkErrorArray[i].user+'</td><td>'+data.checkErrorArray[i].type+'</td><td>'+urlMsg+'</td><td>'+data.checkErrorArray[i].errorType+'</td></tr>');
									}
								}
							}else{
								dialog.alert(data.message);
							}
							d.hide();
						} ,
						error : function(req) {
							console.log(req);
							d.hide();
						}
					});
				}
				
				function updateFailCheckAll(){
				   var allFlag = $("#updateFailCheckAll").is(':checked');
				   if(allFlag){
					   $("input[name='updateFailCheck']").prop("checked",true); 
				   }else{
					   $("input[name='updateFailCheck']").prop("checked",false);
				   }
				}
				
				function cleaningFail(){
					var datas=[];
					var length = $('input:checkbox[name=updateFailCheck]:checked').length;
					if(length==0){
						dialog.alert("请选择需要清理的待办记录！");
						return;
					}
					$('input:checkbox[name=updateFailCheck]:checked').each(function (i) {
		                var data = $(this).closest("td").find("input[name='failData']")[0].value;
		                datas.push(JSON.parse(data));
		            });
					var _datas = {};
					_datas.targets = JSON.stringify(datas);
					console.log(_datas);
					var d = dialog.loading();
				    var url ="${LUI_ContextPath}/third/ding/third_ding_notify/thirdDingNotify.do?method=cleaningFail";
				    $.ajax({
						url : url,
						type : 'post',
						async : true,
						dataType : "json",
						data: _datas,
						success : function(data) {
							dialog.alert(data.message);
							d.hide();
							queryNotify();
						} ,
						error : function(req) {
							dialog.alert("清理失败！！！");
							console.log(req);
							d.hide();
						}
					});
				}
				
				//批量推送
				function notSendCheckAll(){
				   var allFlag = $("#notSendCheckAll").is(':checked');
				   if(allFlag){
					   $("input[name='notSendCheck']").prop("checked",true); 
				   }else{
					   $("input[name='notSendCheck']").prop("checked",false);
				   }
				}
				
				function reSend(){
					var canReSend = <%=canReSend%>;
					if(!canReSend){
						dialog.alert("待办接口请选择(新接口)待办任务接口！");
						return;
					}
					var datas=[];
					var length = $('input:checkbox[name=notSendCheck]:checked').length;
					if(length==0){
						dialog.alert("请选择重发的待办记录！");
						return;
					}
					$('input:checkbox[name=notSendCheck]:checked').each(function (i) {
		                var data = $(this).closest("td").find("input[name='notSendData']")[0].value;
		                datas.push(JSON.parse(data));
		            });
					var _datas = {};
					_datas.targets = JSON.stringify(datas);
					console.log(_datas);
					var d = dialog.loading();
				    var url ="${LUI_ContextPath}/third/ding/third_ding_notify/thirdDingNotify.do?method=reSend";
				    $.ajax({
						url : url,
						type : 'post',
						async : true,
						dataType : "json",
						data: _datas,
						success : function(data) {
							if(data.success){
								dialog.alert("处理成功");
							}else{
								dialog.alert("重发失败："+data.message);
							}
							d.hide();
							queryNotify();
						} ,
						error : function(req) {
							dialog.alert("重发失败！！！");
							console.log(req);
							d.hide();
						}
					});
				}
				
				//一键清理和重发
				function cleanAndResend(){
					var ekpUserId = $("input[name='ekpUserId']").val();
					if(ekpUserId == "" || ekpUserId == null){
						dialog.alert("请选择人员！");
						return;
					}
					var dismissTips = '即将清除该人员待办以及待办重发，是否继续？';
					var canReSend = <%=canReSend%>;
					if(!canReSend){
						dismissTips = '未选择待办任务接口(新接口)，此操作仅仅清除该人员的待办，是否继续？';
					}
                    dialog.confirm(dismissTips, function(ok) {
                        if(ok == true) {
                        	var d = dialog.loading();
                     		var url ="${LUI_ContextPath}/third/ding/third_ding_notify/thirdDingNotify.do?method=cleanAndResend&userId="+ekpUserId;        			
                     		$.ajax({
        						url : url,
        						type : 'post',
        						async : true,
        						dataType : "json",
        						success : function(data) {
        							if(data.message){
										dialog.alert(data.message);
        							}
        							d.hide();
        							queryNotify();
        						} ,
        						error : function(req) {
        							console.log(req);
        							d.hide();
        						}
        					});
                 	    }
                	}); 
					
					
					
				}
				
				window.cleanAndResend=cleanAndResend;
				window.notSendCheckAll=notSendCheckAll;
				window.reSend=reSend;
				window.cleaningFail=cleaningFail;
				window.updateFailCheckAll=updateFailCheckAll;
				window.queryNotify=queryNotify;
			});  
        </script>
    </template:replace>
</template:include>