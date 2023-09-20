<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.alibaba.fastjson.JSONArray"%>
<%@ page import="com.alibaba.fastjson.JSONObject"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	if(ResourceUtil.isQuicklyEdit() && request.getAttribute("quicklyEditKeys") != null){
		JSONArray quicklyEditKeys = (JSONArray) request.getAttribute("quicklyEditKeys");
%>
		<script type="text/javascript">
			function reanderQuicklyEditButton(){
				var quicklyEdit = top.document.getElementById("quicklyEdit");
				if(quicklyEdit == null){
					if(top.document.body){
						//渲染快编元素
						quicklyEdit = top.document.createElement("div");
						quicklyEdit.id="quicklyEdit"
						quicklyEdit.innerText = "${lfn:message('sys-profile:sys.profile.i18n.multilang.shortname')}";
						quicklyEdit.style.width = "48px";
						quicklyEdit.style.height = "48px";
						quicklyEdit.style.lineHeight = "48px";
						quicklyEdit.style.borderRadius = "4px";
						quicklyEdit.style.right = "20px";
						quicklyEdit.style.bottom = "20px";
						quicklyEdit.style.position = "fixed";
						quicklyEdit.style.display = "block";
						quicklyEdit.style.backgroundColor = "#F56B6B";
						quicklyEdit.style.border = "1px solid #eee";
						quicklyEdit.style.textAlign = "center";
						quicklyEdit.style.zIndex = "999";
						quicklyEdit.style.color = "#fff";
						quicklyEdit.style.fontSize = "15px";
						quicklyEdit.style.fontWeight = "bold";
						quicklyEdit.style.cursor = "pointer";
						top.document.body.appendChild(quicklyEdit);
						addQuicklyEditEvent(quicklyEdit);
					}
					else{
						if(typeof $ === 'undefined'){
							if(typeof seajs != 'undefined'){
								use("lui/jquery",function($){
									$(document).ready(reanderQuicklyEditButton);
								});
							}
							else if(typeof window.addEventListener != 'undefined'){
								window.addEventListener("load", reanderQuicklyEditButton);
							}
							else if (typeof window.attachEvent != 'undefined') {  
				                window.attachEvent("onload", reanderQuicklyEditButton);  
				            }
							else{
								window.onload = reanderQuicklyEditButton;
							}
						}
						else{
							$(document).ready(reanderQuicklyEditButton);
						}
					}
				}
			}
			
			//添加click事件
			function addQuicklyEditEvent(quicklyEdit) {
				var clickFn = function(){
					if(top.quicklyEditDialog){
						return;
					}
					seajs.use("lui/dialog",function(dialog){
						top.quicklyEditDialog = dialog.iframe("/sys/profile/i18n/multi_module_message_list.jsp", "${lfn:message('sys-profile:sys.profile.i18n.multi.lang')}", function(data){
							delete top.quicklyEditDialog;
						}, {width: 1010, height: 600});
					});
				};
		        if(quicklyEdit.addEventListener) {
		          quicklyEdit.addEventListener("click",clickFn, false);
		        }
		        else {
		          quicklyEdit.attachEvent("onclick",clickFn);
		        }
		    }
			//注册当前doc到top
			function registryDoc2Top(){
				if(typeof top.messageKeyDocs === 'undefined'){
					top.messageKeyDocs = new Array();
				}
				var isRegistry = false;
				//意在维护最新的有效window数组
				var newMessageKeyDocs = new Array();
				for(var i in top.messageKeyDocs){
					//判断window对象是否已经失效
					if(top.messageKeyDocs[i].closed){
						continue;
					}
					newMessageKeyDocs.push(top.messageKeyDocs[i]);
					if(window === top.messageKeyDocs[i]){
						isRegistry = true;
					}
				}
				//如果当前window对象没有注册，则注册
				if(!isRegistry){
					newMessageKeyDocs.push(window);
				}
				top.messageKeyDocs = newMessageKeyDocs;
			}
			//拦截所有的ajax请求
			function ajaxGlobalInterceptor(){
				seajs.use("lui/jquery",function($){
					$.ajaxSetup({
						beforeSend: function(XMLHttpRequest){
							var requestURL = arguments[1].url;
							if(requestURL.indexOf(".jsp") > 0){
								var hostName = "<%=request.getServerName()%>";
								var contextPath = "<%=request.getContextPath()%>";
								if(contextPath != "/"){
									requestURL = requestURL.substring(requestURL.indexOf(contextPath) + contextPath.length);
									arguments[1].url = contextPath + "/sys/profile/i18n/quicklyMultiLangEditForward.jsp?isAjaxRequest=true&realViewName="+escape(requestURL);
								}
								else if(requestURL.indexOf(hostName) > 0){
									requestURL = (requestURL.split(hostName)[1]).substring(url.indexOf("/"));
									arguments[1].url = "/sys/profile/i18n/quicklyMultiLangEditForward.jsp?isAjaxRequest=true&realViewName="+escape(requestURL);
								}
							}
			            },
						dataFilter: function(data, type){
							if(data){							
								var messageKeysRegExp = new RegExp("<messageKeys>(.+)</messageKeys>");
								if(messageKeysRegExp.test(data)){
									loadMessageKeyJSONs(messageKeysRegExp.exec(data)[1]);
								}
								data = data.split("----^o^----")[0];
								if(typeof(type) === 'undefined'){
									var jsonObj = convertStrToJsonObj(data);
									if(jsonObj != null && typeof(jsonObj) === 'object'){
										return jsonObj;
									}
								}
							}
						   return data;
					   }
					});
				});
			}
			//获取多语言信息
			function convertStrToJsonObj(data){
				try{
					 if(typeof JSON === 'object'){
						 return JSON.parse(data);
					 }
					 else{
						 return eval(data);
					 }
				}
				catch(err)
				{
					return null;
				}
			}
			//加载多语言信息
			function loadMessageKeyJSONs(data){				
				 var messageKeyJSONs = convertStrToJsonObj(data);
				 if(messageKeyJSONs != null){
					if(window.messageKeys){
						seajs.use("lui/jquery",function($){
							for(var i=0; i < messageKeyJSONs.length; i++){
								var isIncluded = false;
								for(var j=0; j < window.messageKeys.length; j++){
									if(messageKeyJSONs[i].bundle === window.messageKeys[j].bundle &&
										messageKeyJSONs[i].key === window.messageKeys[j].key){
										isIncluded = true;
										break;
									}
								}
								if(!isIncluded){
									window.messageKeys.push(messageKeyJSONs[i]);								
								}
							}
						});
					}
					else{
						window.messageKeys = messageKeyJSONs;
					}
					registryDoc2Top();
				 }
			}
			//加载多语言message
			function quicklyMultiLangEditOnload(){
				ajaxGlobalInterceptor();
				loadMessageKeyJSONs('<%=quicklyEditKeys.toJSONString()%>');
			}
			reanderQuicklyEditButton();
		</script>
		<c:import url="/sys/profile/i18n/quicklyMultiLangEditHeader.jsp"></c:import>
<%	}%>