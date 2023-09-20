<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.km.cogroup.model.GroupConfig"%>
<%@ page import="com.landray.kmss.km.cogroup.util.CogroupUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<style>
	.com_group_icon{
		background: url(${KMSS_Parameter_ContextPath}km/cogroup/resource/images/group2.png) no-repeat center;
		width: 48px;
		height: 48px;
		background-color: #fff;
		border-radius:4px;
		border: 1px solid #ccc !important;
	}
	.com_group_icon:hover {
		background-color: #dfdfdf;
	}
</style>
<%
	if(GroupConfig.newInstance().getCogroupEnabled().equals("true")){
%>
<%  request.setAttribute("loginName", UserUtil.getUser().getFdLoginName()); %>
<%  request.setAttribute("simpleModel", CogroupUtil.getModelSimpleClassName(request.getParameter("modelName"), request.getParameter("fdId"))); %>
<script type="text/javascript">
	seajs.use(['lui/jquery','lui/toolbar','lui/dialog'],function($, toolbar, dialog){
		
		LUI.ready(function(){
			var fdId = "${JsParam.fdId }";
			var modelName = "${JsParam.modelName }";
			createGroupIcon(fdId, modelName);
		});
		window.createGroupIcon = function(fdId, modelName){
			var isBitch = navigator.userAgent.indexOf("MSIE") > -1
			&& document.documentMode == null || document.documentMode <= 8;
			var isIE10D = navigator.userAgent.indexOf("MSIE") > -1
					&& document.documentMode == null || document.documentMode <= 9;
			
			if (isBitch) // 直接去除对ie8浏览器的支持，防止页面未响应
				return;
			var topObj = LUI('top');
			var btn = new toolbar.Button({
				styleClass:'com_group_icon',
				order : 12
			});
			btn.startup();
			topObj.addButton(btn);
			
			btn.element.on({
				click : function(evt) {
					checkGroup(fdId, function(res){
						if(res.gid != null){
							//群存在，加入群聊
							//window.location.href="landray.kk:${loginName}@group/join?gid="+res.gid;
							getGroupRecord(res.gid, res.state);
						}else{
							//群不存在
							console.log("群不存在");
							getCreateGroup(fdId, modelName);
						}
					});
				}
			});
		}
		
		//查询群聊是否存在
		window.checkGroup = function(fdId, callback){
			var bizId = "${simpleModel}" + "_" + fdId;
			var url = "${KMSS_Parameter_ContextPath}km/cogroup/km_cogroup/kmCogroup.do?method=getGroupInfoByBiz";
			$.ajax({
		        type: "post",
		        url:  url,
		        data: {"bizId":bizId, "bizType":"lbpm_global"},
		        async : true,
		        dataType: "json",
		        success: function (results ,textStatus, jqXHR)
		        {
		        	if(results != null){
						console.log(results);
						if(results.result == 0){
							callback && callback({
								gid : results.groupID,
								state : results.groupState
							});
						}else if(results.result == 621){
							callback && callback({
								gid : null
							});
						} else {
							dialog.failure(results.errorMsg);
						}
					}
		        },
		        error:function (XMLHttpRequest, textStatus, errorThrown) {      
		        	dialog.failure("查询群聊接口请求失败，请检查网络！");
		        }
		     });
		};
		
		//创建群聊
		window.getCreateGroup = function(fdId, modelName){
			var url ="${KMSS_Parameter_ContextPath}km/cogroup/km_cogroup/kmCogroup.do?method=getCreateGroupInfo";
			$.post(url,$.param({"fdId":fdId, "modelName":modelName},true),function(results){
				if(results != null){
					//console.log(results);
					var members = "";
					var userList = results.userList;
					if(null != userList){
						for ( var i = 0; i <userList.length; i++){
						    if(members == ""){
								members = userList[i];
							}else{
								members = members + "|" + userList[i];
							}
						}
					}
					var url = "landray.kk:${loginName}@group/new?groupType=biz&bizType=lbpm_global&bizTypeName="+encodeURIComponent(results.bizTypeName)+"&bizId="+
					encodeURIComponent(results.fdId)+"&groupName="+encodeURIComponent(results.groupName)+"&members="+encodeURIComponent(members)+"&bizUrl="+
					encodeURIComponent(results.url) + "&title="+encodeURIComponent(results.groupName) + "&user="+encodeURIComponent(results.descUser) + "&time="+encodeURIComponent(results.descTimePC);
					//console.log(members);
					console.log(url);
					window.location.href=url;
				}
			},'json');
		};
		
		//群存在，获取群消息记录url
		window.getGroupRecord = function(groupID, state){
			var url ="${KMSS_Parameter_ContextPath}km/cogroup/km_cogroup/kmCogroup.do?method=getGroupRecord";
			$.post(url,$.param({"groupID":groupID},true),function(results){
				console.log(results);
				if(results != null){
					if(results.result == 0){
						var pageUrl = results.pageUrl + "&ln=" + encodeURIComponent("${loginName}") + "&state=" + encodeURIComponent(state);
						console.log(pageUrl);
						dialog.iframe(pageUrl,"群聊",null,{
							width : 1010,
							height : 650
						});
						//window.location.href=pageUrl;
					}else {
						dialog.failure(results.errorMsg);
					}
				}
			},'json');
		};
		
	});
</script>
<%
	}
%>