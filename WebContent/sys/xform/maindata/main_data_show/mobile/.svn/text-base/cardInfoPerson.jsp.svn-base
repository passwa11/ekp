<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/template/dojoConfig.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mobile/js/dojo/dojo.js?s_cache=${MUI_Cache}"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mobile/js/dojox/mobile.js?s_cache=${MUI_Cache}"></script>
<link rel="Stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/mobile/css/mobilekeydata.css"/>" />
<link rel="Stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/mobile/css/ios7.css"/>" />
<link rel="Stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/mobile/icon/font-mui.css"/>" />
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
</head>
<script>
require([
 	"dojo/query",
 	"mui/util",
 	"dojo/domReady!" ], function(query, util) {

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
		//adapter.sendMsg(value);
		var object = {};
		object.method = 'sendMsg';
		object.para = value;
		parent.postMessage(JSON.stringify(object), "*");
	}
};
window.openChat = function(loginName){
	var options = {};
	options.loginName = loginName;
	var object = {};
	object.method = 'openChat';
	object.para = options;
	parent.postMessage(JSON.stringify(object), "*");
};

window.callApp = function(url){
	url = util.formatUrl(url,true);
	var options = {};
	options.url = url;
	options.code = "LABC-ROBOT";
	//adapter.callApp(options);
	//adapter.view(url,'_blank');
	var object = {};
	object.method = 'callApp';
	object.para = options;
	parent.postMessage(JSON.stringify(object), "*");
}
});
		</script>
		<script type="text/javascript">
			window.onload = function() {
				var h  = document.getElementById("mui_keydata_card_div").scrollHeight;        
				var object = {};
				object.method = 'height';
				object.para = h;
				parent.postMessage(JSON.stringify(object), "*");
			}    
		</script>

<div id="mui_keydata_card_div" class="mui_keydata_card" >
        <span class="mui_keydata_card_avatar"><img src="<c:url value='${icon}' />"></span>
        <div class="mui_keydata_card_content"  <c:if test="${auth==true }">onclick="callApp('${detailUrl2 }')"</c:if> >
          <span class="mui_keydata_card_tag" style="background-color:${color}">${mainDataType }</span>
          <h4 class="mui_keydata_card_title">${title }</h4>
          <c:if test="${auth==true }">
           <ul class="mui_keydata_card_list">
							<li>
								<span class="title"  title="岗位">岗位</span>
								<span class="txt"  title="${post }">${post}</span>
							</li>
							<li>
								<span class="title"  title="手机">手机</span>
								<span id="mobilPhone" class="txt"  title="手机">${mobilPhone}</span>
							</li>
							<li>
								<span class="title"  title="部门">部门</span>
								<span class="txt"  title="${dept }">${dept}</span>
							</li>
							<li>
								<span class="title"  title="邮箱">邮箱</span>
								<span id="mail" class="txt"  title="邮箱">${email}</span>
							</li>
          </ul>
          </c:if>
          
          <c:if test="${auth==false }">
          		<p class="lui_keydata_card_nodata">对不起，您没有阅读此内容的权限，请联系管理员</p>
          </c:if>
          
        </div>
        
        <c:if test="${auth==true && contactPrivate==false }">
          		<div class="mui_keydata_card_footer">
          			<c:if test="${!(empty mobilPhone)}">
          				<a href="javascript:void(0);" onclick="callPhone('${mobilPhone}');"><i class="mui mui-tel"></i>打电话</a>
          			</c:if>
          			<a href="javascript:void(0);"  onclick="openChat('${loginName}');"><i class="mui mui-handler_communicate"></i>发起沟通</a>
        		</div>
        </c:if>
          
</div>
      

