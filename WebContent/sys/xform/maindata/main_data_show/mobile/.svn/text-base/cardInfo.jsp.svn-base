<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/template/dojoConfig.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mobile/js/dojo/dojo.js?s_cache=${MUI_Cache}"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/mobile/js/dojox/mobile.js?s_cache=${MUI_Cache}"></script>
<link rel="Stylesheet" href="<c:url value="/sys/xform/maindata/main_data_show/mobile/css/mobilekeydata.css"/>" />


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
<div id="mui_keydata_card_div" class="mui_keydata_card" <c:if test="${auth==true }">onclick="callApp('${detailUrl2 }')"</c:if>  >
        <span class="mui_keydata_card_avatar"><img src="<c:url value='${icon}' />"></span>
        <div class="mui_keydata_card_content">
          <span class="mui_keydata_card_tag" style="background-color:${color}">${mainDataType }</span>
          <h4 class="mui_keydata_card_title">${title }</h4>
          <c:if test="${auth==true }">
           <ul class="mui_keydata_card_list">
          	  <c:forEach items="${showFieldsList }" var="field" varStatus="varStatus">
							<li>
								<span class="title"  title="${field[0] }">${field[0] }</span>
								<span class="txt"  title="${field[1] }">${field[1] }</span>
							</li>
				</c:forEach>
          </ul>
          </c:if>
          
          <c:if test="${auth==false }">
          		<p class="lui_keydata_card_nodata">对不起，您没有阅读此内容的权限，请联系管理员</p>
          </c:if>
          
        </div>
</div>
      

