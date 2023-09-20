<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.zone.util.SysZoneConfigUtil"%>
<%@ page import="net.sf.json.JSONObject"%>
<%@ page import="net.sf.json.JSONArray"%>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.sys.zone.interfaces.ICommunicate" %>
<%@ page import="com.landray.kmss.framework.service.plugin.Plugin" %>
<%@ page import="java.io.File" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
	<style>
		.lui_zone_card_item_ImDing a{
			background:none;
		}
		.lui_zone_card_item_ImDing a img {
			display: block !important;
			width: 16px;
		}
		
		.lui_zone_card_item_ImLding a{
			background:none;
		}
		
		.lui_zone_card_item_ImLding a img{
			display: block !important;
			width: 16px;
		}
	</style>

  <script>
      <%
    		JSONArray commmunicateList = SysZoneConfigUtil.getCommnicateList();
  	   %>
    	function onRender(arr){
        	if(arr == null || arr.length <= 0 ) {
				return;
            }
        	seajs.use(['sys/zone/resource/plugin/Communicate.js', 
        	        	'lui/jquery'
        	           <%
        	           	for(Object map: commmunicateList) {
        	           		JSONObject json = (JSONObject)map;
        	           		if(!"communicate".equals(json.get("showType"))) {
        	           			continue;
        	           		}
        	           		String src = ((String)json.get("js_src")).substring(1);
        	           		//其他服务的扩展
        	           		String key = (String)json.get("server");
        	           		String localKey = SysZoneConfigUtil.getCurrentServerGroupKey();
        	           		if(StringUtil.isNotNull(key) && !key.equals(localKey)) {
        	           			src = SysZoneConfigUtil.getServerUrl(key) +  "/" + src;
        	           		}
        	           		out.print(",'" + src + "#'");
        	           	}
      	        		%>
        	        ],
                	function(Communicate, $  
                			<%
            	           	for(Object map : commmunicateList) {
            	           		JSONObject json = (JSONObject)map;
            	           		if(!"communicate".equals(json.get("showType"))) {
            	           			continue;
            	           		}
            	           		out.print("," + ((String)json.get("js_class")) );
            	           	}
          	        		%>) {
	        	for(var i = 0; i < arr.length; i++){ 
	        		(function() {
	        			// 防止重复添加
                        $("#"+ arr[i].elementId + " .sys_zone_card_shortcut_list").remove();
		        		var link = new Communicate(arr[i]); 
		        		<%
		        			for(Object  map : commmunicateList) {
		        				JSONObject json = (JSONObject)map;
		        				if(!"communicate".equals(json.get("showType"))) {
	        	           			continue;
	        	           		}
		        				if(json.get("authUrl") != null) {
		        					if(!UserUtil.checkAuthentication((String)json.get("authUrl"), "GET")) {
		        						continue;
		        					}
		        				}
		        				//如果项目部署kk集成模块，则不显示 “发起沟通”隐藏，显示“发起KK聊天”
		        				boolean flag = false;
		        				if (json.get("unid").equals("collaborate") || json.get("unid").equals("KK")) {
		        					Boolean existImKK = SysZoneConfigUtil.moduleExist("/third/im/kk/");
		        					if(existImKK){
			        					for(Object kkobj : commmunicateList){
			        						JSONObject kkJson = (JSONObject)kkobj;
			        						if(kkJson.get("unid").equals("KK")){
			        							ICommunicate bean = (ICommunicate) SpringBeanUtil.getBean(kkJson.get("bean").toString());
			        							flag = bean.isEnable();
			        						}
			        					}
		        					}
		    					}
		        				//不显示“发起沟通”
		        				if(flag && json.get("unid").equals("collaborate")){
		        					continue;
		        				}
		        				//不显示“发起KK聊天”
		        				if(!flag && json.get("unid").equals("KK")){
		        					continue;
		        				}

		        				//其他服务的扩展
	        	           		String key = (String)json.get("server"); 
	        	           		String contextPathConfig = "";
	        	           		String path = null;
	        	           		String localKey = SysZoneConfigUtil.getCurrentServerGroupKey();
	        	           		if(StringUtil.isNotNull(key) && !key.equals(localKey)) { 
	        	           			path = SysZoneConfigUtil.getServerUrl(key);
	        	           			contextPathConfig = "{'contextPath':'" +  path +"'}";
	        	           		}
	        	           		String titleKey = (String)json.get("title");
	        	           		String title = "";
	        	           		if(StringUtil.isNotNull(titleKey)){
	        	           			title = ResourceUtil.getString(titleKey);
	        	           			if(StringUtil.isNull(title)){
	        	           				String[] titleKeyArr = titleKey.split(":");
	        	           				if(titleKeyArr.length > 1){
	        	           					title = ResourceUtil.getString(titleKeyArr[1], "sys-zone");
	        	           				}	
	        	           			}
	        	           		}
		        				out.println("link.push('" + (path == null ? "" : path) + (String)json.get("icon") + "',"
		        							+ "'lui_zone_card_item_"+(String)json.get("js_class") + "'," + "'" + title+ "',"
		        							+ "new " + (String)json.get("js_class")  +"(" + contextPathConfig +"));");
		        			} 
		        		
		        		
		        		%>
		        		link.show();
		        		
	        			var isDingEnabled = function(){
	        				if(arr.length==1 &&(arr[0].dingUserid=="" || arr[0].dingCropid=="")){
								return false;
							}
	        				return true;
	        			};
	        			
	        			var isLdingEnabled = function(){
	        				if(arr.length==1 && (arr[0].ldingUserid=="" || arr[0].dingCropid=="")){
								return false;
							}
	        				return true;
	        			}
	        			
	        			// 如果钉钉或者蓝桥开启，隐藏发起沟通
						if(isDingEnabled() || isLdingEnabled()){
							$(".lui_zone_card_item_ImCollaborate").hide();
						}
	        			
	        			if(!isDingEnabled()){
							$(".lui_zone_card_item_ImDing").hide();
						}
	        			
	        			if(!isLdingEnabled()){
							$(".lui_zone_card_item_ImLding").hide();
						}
						
			        })();  		
	        	}
        	});
    	}
 </script>