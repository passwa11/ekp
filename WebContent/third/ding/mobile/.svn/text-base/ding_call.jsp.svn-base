<%@ page import="com.landray.kmss.third.ding.model.DingConfig" %>
<%@ page import="com.landray.kmss.third.ding.util.DingUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  request.setAttribute("corpId",DingUtil.getCorpId()); %>
<script>
	require(['dojo/topic',
	         'dojo/ready',
	         'dijit/registry',
	         'dojo/request',
	         'dojo/query',
	         'dojo/dom-style',
	         'dojo/dom',
	         'dojo/dom-construct',
	         'mui/dialog/BarTip',
	         'mui/dialog/Tip',
	         "dojo/dom-class",
	         'mui/device/adapter',
	         "mui/device/device"
	         ],function(topic,ready,registry,request,query,domStyle,dom,domConstruct,BarTip,Tip,domClass,adapter,device){
		
		ready(function(){
			if(device.getClientType() == 11){//钉钉客户端 
				//var fdTemplateId = '${kmImeetingMainForm.fdTemplateId}';
				var dingCallButton = dom.byId('dingCallButton');
				if(dingCallButton != null){
					domStyle.set(dingCallButton, 'display', 'block');
					if(dom.byId('tabBar')){
						domStyle.set(dom.byId('tabBar'),'display', 'flex');
					}
					var pNode = dingCallButton.parentNode;
					 if(pNode && "mui/tabbar/TabBar" ==  pNode.getAttribute("data-dojo-type")){
						domStyle.set(pNode,'display', 'flex');
					}
				}
			}
		});
		
		window.callPhone = function(fdAttendPersonIds, fdHostId){
			
			var url = "${KMSS_Parameter_ContextPath}third/ding/user.do?method=getDingUserId";
			var data = { "fdId" : fdAttendPersonIds + ";" + fdHostId };
			request.post(url, {
				data : data,
				handleAs : 'json',
				sync: true
			}).then(function(data){
				if(null != data ){
					if(null != data.dingUserId && data.dingUserId != ''){
						adapter.call(data.dingUserId, '${corpId}');
					}else{
						Tip.fail({
							text : '钉钉中配置人员为空，无法发起电话会议！'
						});
					}
				}
			},function(data){
			});
		};
		
	});
</script>