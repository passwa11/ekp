<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.km.cogroup.model.GroupConfig" %>
<%@ page import="com.landray.kmss.km.cogroup.util.CogroupUtil" %>
<%@ page import="java.io.File" %>
<%@ page import="com.landray.kmss.sys.appconfig.model.BaseAppConfig" %>
<%-- <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/top.css?s_cache=${MUI_Cache}"></link> --%>

<%  request.setAttribute("loginName", UserUtil.getUser().getFdLoginName()); %>

<%
	if(MobileUtil.isFromKK(new RequestContext(request)) && "true".equals(GroupConfig.newInstance().getCogroupEnabled())){
%>


<div class="muiCooperate" onclick="startGroup();">${lfn:message('km-cogroup:group.chat')}</div>


<%  request.setAttribute("simpleModel", CogroupUtil.getModelSimpleClassName(request.getParameter("modelName"), request.getParameter("fdId"))); %>
<script type="text/javascript">
	require(['dojo/topic', 'dojo/ready','dijit/registry',"mui/util", 'mui/device/adapter', 'dojo/query','dojo/dom-construct'],
			function(topic,ready,registry,util,adapter,query, domConstruct){
		
		/* $(function(){
			query("body").append($(".muiCooperate"));
		}); */
		
		ready(function(){
			var muiCooperate = query('.muiCooperate');
			if(muiCooperate && muiCooperate.length > 0){
				domConstruct.place(muiCooperate[0], document.body);
			}
		});
		
		
		function creategroup(results, modelName){
			var options = {};
			var desc = {};
			
			options.bizId = results.fdId;
			options.bizType = "lbpm_global";
			options.bizTypeName = results.bizTypeName;
			options.bizUrl = results.url;
			options.groupName = results.groupName;
			options.descUser = results.descUser;
			options.descTime = results.descTime;
			options.descTitle = results.groupName;
			options.creater = "${loginName}";
			options.users = results.userList;
			
			adapter.createGroup(options);
		}
		
		function showGroup(groupId){
			var options = {};
			options.groupId = groupId;
			adapter.showGroupMessage(options);
		}
		
		//发起群聊
		window.startGroup = function(){
			var fdId = "${JsParam.fdId }";
			var modelName = "${JsParam.modelName}";
			console.log(modelName);
			var options = {};
			options.bizType = "lbpm_global";
			options.bizId = "${simpleModel}" + "_" + fdId;
			adapter.checkGroup(options, function(res){
				var groupId = res.groupID;
				if(null != groupId && groupId != ""){
					//群存在
					showGroup(groupId);
				}else{
					//群不存在
					var url ="${KMSS_Parameter_ContextPath}km/cogroup/km_cogroup/kmCogroup.do?method=getCreateGroupInfo";
					$.post(url,$.param({"fdId":fdId, "modelName":modelName},true),function(results){
						if(results != null){
							creategroup(results, modelName);
						}
					},'json');
				}
			});
		
		};
		
	});
</script>

<%
	}
%>

<!-- 钉钉群聊 -->
<%
boolean dingExist = CogroupUtil.moduleExist("/third/ding/");
boolean lDingExist = CogroupUtil.moduleExist("/third/lding/");
if(dingExist || lDingExist){
	if(MobileUtil.getClientType(new RequestContext(request)) == 11 && "true".equals(GroupConfig.newInstance().getDingCogroupEnable())){
		String url = "third/ding";
		if (lDingExist) {
			BaseAppConfig ldingConfig = (BaseAppConfig) Class
					.forName("com.landray.kmss.third.lding.model.LdingConfig").newInstance();
			if ("true".equals(ldingConfig.getDataMap().get("ldingEnabled"))) {
				url = "third/lding";
			}
		}
		request.setAttribute("prefix", url);
%>

	<div class="muiCooperate" onclick="dingStartGroup();">${lfn:message('km-cogroup:group.chat.ding')}</div>
	
	<script type="text/javascript">
	require(['dojo/topic', 'dojo/ready','dijit/registry',"mui/util", 'mui/device/adapter', 'dojo/query', "dojo/request","mui/dialog/Tip",'dojo/dom-construct'],
			function(topic,ready,registry,util,adapter,query, request, Tip, domConstruct){
		
		/* $(function(){
			query("body").append($(".muiCooperate"));
		}); */
		
		ready(function(){
			var muiCooperate = query('.muiCooperate');
			if(muiCooperate && muiCooperate.length > 0){
				domConstruct.place(muiCooperate[0], document.body);
			}
		});



		//发起群聊
		window.dingStartGroup = function(){
			var fdId = "${JsParam.fdId }";
			var modelName = "${JsParam.modelName}";
			var url = "${KMSS_Parameter_ContextPath}${prefix}/third_ding_group/thirdDingGroup.do?method=checkGroup";
			var data =  { "fdId" : fdId, "modelName" : modelName };
			//调用后台接口，检查钉钉群是否存在,如果存在,检查当前用户是否在群中，如果不在，则添加
			request.post(url, {
				data : data,
				handleAs : 'json',
				sync: true
			}).then(function(data){
				//alert(JSON.stringify(data));
				var groupId = data.groupID;
				var corpId = data.corpId;
				if(null != groupId && groupId != ""){
					//群存在
					var options = {};
					options.corpId = corpId;
					options.chatId = groupId;
					if(null != corpId && "" != corpId){
						adapter.openGroup(options);
					}else{
						Tip.fail({
							text : 'corpId不能为空!'
						});
					}
				}else{
					//群不存在
					getCreateInfo(fdId, modelName)
				}
			},function(data){
			});
		
		};
		
		function getCreateInfo(fdId, modelName){
			var url ="${KMSS_Parameter_ContextPath}${prefix}/third_ding_group/thirdDingGroup.do?method=getCreateInfo";
			var data = { "fdId" : fdId, "modelName" : modelName };
			request.post(url, {
				data : data,
				handleAs : 'json',
				sync: true
			}).then(function(data){
				if(data != null){
					createDingGroup(data);
				}
			},function(data){
			});
		}
		
		function createDingGroup(results){
			var options = {};
			options.title = results.groupName;
			options.corpId = results.corpId;
			options.pickedUsers = results.userList;
			options.appId = results.appId;
			adapter.complexPicker(options, function(res){
				var users = res.users;
				var userId = "";
				if(null != users && users != ""){
					for(var i in users){
						if(null != users[i].emplId && users[i].emplId != "" && users[i].emplId != 'undefined'){
							if(userId == "")
								userId = users[i].emplId;
							else
								userId = userId + "," + users[i].emplId;
						}
					}
					createGroup(userId);
				}
			});
		}
		
		
		//保存钉钉群id
		function createGroup(userId){
			var fdId = "${JsParam.fdId }";
			var modelName = "${JsParam.modelName}";
			var url = "${KMSS_Parameter_ContextPath}${prefix}/third_ding_group/thirdDingGroup.do?method=createGroup";
			var data = { "userIds" : userId, "fdId" : fdId, "modelName" : modelName };
			request.post(url, {
				data : data,
				handleAs : 'json',
				sync: true
			}).then(function(data){
				if(null != data){
					if(null != data.chatId){
						//打开钉钉群聊
						var options = {};
						options.corpId = data.corpId;
						options.chatId = data.chatId;
						adapter.openGroup(options);
					}else{
						Tip.fail({
							text : data.msg
						});
					}
				}
			},function(data){
			});
		}
	});
	</script>
<%
	}
	}
%>

<!-- 企业微信群聊 -->
<%
boolean wxWorkExist = CogroupUtil.moduleExist("/third/weixin/");
if(wxWorkExist){
	if(MobileUtil.getClientType(new RequestContext(request)) == 12 && "true".equals(GroupConfig.newInstance().getWxWorkCogroupEnable())){
		String corpGroupIntegrateEnable = (String)CogroupUtil.getWeixinConfig().get("corpGroupIntegrateEnable");
		// 下游用户不能使用群聊功能
		if(!"true".equals(corpGroupIntegrateEnable) || UserUtil.getUser().getFdIsExternal()==null || UserUtil.getUser().getFdIsExternal()==false){
			String url = "third/wexin";
			request.setAttribute("prefix", url);
%>
	<div class="muiCooperate" onclick="wxStartGroup();">群聊</div>
	<script type="text/javascript">
	require(['dojo/topic', 'dojo/ready','dijit/registry',"mui/util", 'mui/device/adapter', 'dojo/query', "dojo/request","mui/dialog/Tip",'dojo/dom-construct',"lib/weixin/jweixin-1.0.0"],
			function(topic,ready,registry,util,adapter,query, request, Tip, domConstruct,wx){
		
		ready(function(){
			var muiCooperate = query('.muiCooperate');
			if(muiCooperate && muiCooperate.length > 0){
				domConstruct.place(muiCooperate[0], document.body);
			}
		});

		//发起群聊
		window.wxStartGroup = function(){
			var fdId = "${JsParam.fdId }";
			var modelName = "${JsParam.modelName}";
			var url = "${KMSS_Parameter_ContextPath}third/weixin/work/third_weixin_work_group/thirdWeixinWorkGroup.do?method=checkGroup";
			var data =  { "fdId" : fdId, "modelName" : modelName };
			//调用后台接口，检查微信群是否存在,如果存在,检查当前用户是否在群中，如果不在，则添加
			request.post(url, {
				data : data,
				handleAs : 'json',
				sync: true
			}).then(function(data){
				console.log("检查情况",data);
				var groupId = data.groupID;
				var corpId = data.corpId;
				if(null != groupId && groupId != ""){
					//群存在
					var options = {};
					options.corpId = corpId;
					options.chatId = groupId;
					if(null != corpId && "" != corpId){
						//wxOpenGroup(options);
						wxOpenGroup(options);
					}else{
						Tip.fail({
							text : 'corpId不能为空!'
						});
					}
				}else{
					//群不存在
					getWxCreateInfo(fdId, modelName);
				}
			},function(data){
			});
		
		};
		
		function getWxCreateInfo(fdId, modelName){
			var url ="${KMSS_Parameter_ContextPath}third/weixin/work/third_weixin_work_group/thirdWeixinWorkGroup.do?method=getCreateInfo";
			var data = { "fdId" : fdId, "modelName" : modelName };
			request.post(url, {
				data : data,
				handleAs : 'json',
				sync: true
			}).then(function(data){
				if(data != null){
					if(data.errorMsg){
						alert(data.errorMsg);
					}else{
						createWxGroup(data);
					}
				}
			},function(data){
			});
		}
		
		function createWxGroup(results){
			var options = {};
			options.title = results.groupName;
			options.corpId = results.corpId;
			options.pickedUsers = results.userList;
			options.appId = results.appId;
			console.log("准备调起通讯录：",options);
			adapter.selectEnterpriseContact(options, function(res){
				
				var users = res.users; // 已选的成员列表
				console.log(users);
				var userId = "";
				if(null != users && users != ""){
					 for (var i = 0; i < users.length; i++)
                     {
                             console.log(users[i]);
                             if(userId == "")
 								userId = users[i].id;
 							 else
 								userId = userId + "," + users[i].id;
                     }
					createAndSaveWxGroup(userId);
				}
			});
		}
		
		//保存企业微信群id
		function createAndSaveWxGroup(userId){
			var fdId = "${JsParam.fdId }";
			var modelName = "${JsParam.modelName}";
			var url = "${KMSS_Parameter_ContextPath}third/weixin/work/third_weixin_work_group/thirdWeixinWorkGroup.do?method=createGroup";
			var data = { "userIds" : userId, "fdId" : fdId, "modelName" : modelName };
			request.post(url, {
				data : data,
				handleAs : 'json',
				sync: true
			}).then(function(data){
				if(null != data){
					if(null != data.chatId){
						var options = {};
						options.corpId = data.corpId;
						options.chatId = data.chatId;
						wxOpenGroup(options);
					}else{
						Tip.fail({
							text : data.msg
						});
					}
				}
			},function(data){
			});
		};

		window.wxOpenGroup = function(options){
		   // alert("wxOpenGroup");
			var jsApiList =  ['openEnterpriseChat','selectEnterpriseContact','agentConfig','openExistedChatWithMsg'];
			url = '${KMSS_Parameter_ContextPath}third/wxwork/jsapi/wxJsapi.do?method=jsapiSignature&type=agent_config',
			option = { data : { url : location.href }, handleAs : 'json' };
			//后端获取签名信息
			request.post(url ,option).response.then(function(rtn){
				var signInfo = rtn.data;
				if(signInfo && signInfo.appId){
					console.log("signInfo",signInfo);
					wx.config({
						appId : signInfo.appId,
						timestamp : signInfo.timestamp,
						nonceStr : signInfo.noncestr,
						signature : signInfo.signature,
						jsApiList : jsApiList
					});
					wx.ready(function(){
						wx.agentConfig({
						    corpid: signInfo.corpid, // 必填，企业微信的corpid，必须与当前登录的企业一致
						    agentid: signInfo.agentid, // 必填，企业微信的应用id （e.g. 1000247）
						    timestamp: signInfo.timestamp, // 必填，生成签名的时间戳
						    nonceStr: signInfo.noncestr, // 必填，生成签名的随机串
						    signature: signInfo.signature,// 必填，签名，见附录-JS-SDK使用权限签名算法
						    jsApiList: jsApiList, //必填，传入需要使用的接口名称
						    success: function(res) {
						        // 回调
						    	wx.invoke("openExistedChatWithMsg", {
						                chatId: options.chatId,
								        },function(res){
								                if (res.err_msg == "openExistedChatWithMsg:ok")
								                {
								                }
								        }
								);
						    },
						    fail: function(res) {
						    	alert('agentConfig失败');
						    	console.log(res);
						    }
						});
					});
					
				}else{
					console.log('signInfo appId empty(wxwork)');
				}
		});
		}
	});
	</script>
	
<%
	}
	}
	}
%>

