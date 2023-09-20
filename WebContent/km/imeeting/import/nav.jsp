<%@page import="com.landray.kmss.framework.service.plugin.Plugin"%>
<%@page import="com.landray.kmss.km.imeeting.synchro.*"%>
<%@page import="com.landray.kmss.framework.plugin.core.config.IExtension"%>
<%@page import="java.util.List,java.util.ArrayList"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	List<ImeetingSynchroPluginData> extensionList = SynchroPlugin.getExtensionList();
	List<Map<String,String>> extensions = new ArrayList<Map<String,String>>();
	for(int i=0;i<extensionList.size();i++){
		Map<String,String> map = new HashMap<String,String>();
		String name = extensionList.get(i).getName();
		String bindPageUrl = extensionList.get(i).getBindPageUrl();
		map.put("name",name);
		map.put("bindPageUrl",bindPageUrl);
		extensions.add(map);
	}
	request.setAttribute("extensions", extensions);
%>
<c:set var="key" value="${JsParam.key}"/>
<c:set var="criteria" value="${JsParam.criteria}"/>
<%--业务导航--%>
<ui:content title="业务导航" expand="${JsParam.key != 'calendar'?'false':'true' }" >
	<ul class='lui_list_nav_list'>
	<c:choose>
	<c:when test="${JsParam.panel!='kmImeetingPanel'}">
		 <li><a id="calendar_my" href="javascript:void(0)" onclick="openHref('${LUI_ContextPath}/km/imeeting');" title="${lfn:message('km-imeeting:kmImeeting.tree.calender') }"><i class="fontmui">&#xe705;</i>${lfn:message('km-imeeting:kmImeeting.tree.calender') }</a></li>
	</c:when>
	<c:otherwise>
		  <li><a id="calendar_my" href="javascript:void(0)" onclick="moduleAPI.kmImeeting.switchMenuItem(this,'${JsParam.panel}','myCalendar');" title="${lfn:message('km-imeeting:kmImeeting.tree.calender') }"><i class="fontmui">&#xe705;</i>${lfn:message('km-imeeting:kmImeeting.tree.calender') }</a></li>
	</c:otherwise>
	</c:choose>	 
		 <li><a id="calendar_plan" href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_paln.jsp');resetMenuNavStyle(this);" title="${lfn:message('km-imeeting:kmImeeting.tree.calender.plan') }"><i class="fontmui">&#xe705;</i>${lfn:message('km-imeeting:kmImeeting.tree.calender.plan') }</a></li>
	 	 <li><a id="meeting_fixed" href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_fixed.jsp?except=docStatus:00');resetMenuNavStyle(this);" title="${lfn:message('km-imeeting:kmImeeting.tree.query') }"><i class="fontmui">&#xe705;</i>${lfn:message('km-imeeting:kmImeeting.tree.meeting') }</a></li>
	 	 <li><a id="meeting_data" href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_data.jsp');resetMenuNavStyle(this);" title="${lfn:message('km-imeeting:kmImeeting.tree.data') }"><i class="fontmui">&#xe705;</i>${lfn:message('km-imeeting:kmImeeting.tree.data') }</a></li>
	 	 <li><a id="meeting_summary" href="javascript:void(0)" onclick="openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_summary.jsp?&except=docStatus:00');resetMenuNavStyle(this);" title="${lfn:message('km-imeeting:kmImeeting.tree.myHandleSummary') }"><i class="fontmui">&#xe705;</i>${lfn:message('km-imeeting:kmImeeting.tree.myHandleSummary') }</a></li>
	</ul>
</ui:content>
<%--会议统计--%>
<ui:content title="${ lfn:message('km-imeeting:kmImeeting.tree.stat') }" expand="${JsParam.key != 'stat'?'false':'true' }" >
	<ui:menu layout="sys.ui.menu.ver.default">
		<%--部门会议统计--%>
		<ui:menu-popup text="${ lfn:message('km-imeeting:kmImeeting.tree.stat.dept')}" align="right-top" borderWidth="2" icon="&#xe705;">
			<div style="width: 500px;">
				<ui:dataview>
					<ui:source type="Static">
						[{
							"text":"${lfn:message('km-imeeting:kmImeetingStat.stat.thrught')}",
							"children":[{	
									"text":"${lfn:message('km-imeeting:kmImeetingStat.dept.stat')}",		
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=dept.stat');",
									"target": "_self"
								},{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.dept.statMon')}",		
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=dept.statMon')",
									"target": "_self"
							}]
						}]
					</ui:source>
					<ui:render ref="sys.ui.treeMenu2.cate"></ui:render>
				</ui:dataview>
			</div>
		</ui:menu-popup>
		<%--个人会议统计--%>
		<ui:menu-popup text="${ lfn:message('km-imeeting:kmImeeting.tree.stat.person')}" align="right-top" borderWidth="2" icon="&#xe705;">
			<div style="width: 500px;">
				<ui:dataview>
					<ui:source type="Static">
						[{
							"text":"${lfn:message('km-imeeting:kmImeetingStat.stat.thrught')}",
							"children":[{	
									"text":"${lfn:message('km-imeeting:kmImeetingStat.person.stat')}",		
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=person.stat')",
									"target": "_self"
								},{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.person.statMon')}",		
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=person.statMon')",
									"target": "_self"
							}]
						}]
					</ui:source>
					<ui:render ref="sys.ui.treeMenu2.cate" ></ui:render>
				</ui:dataview>
			</div>
		</ui:menu-popup>
		<%--会议室资源统计--%>
		<ui:menu-popup text="${ lfn:message('km-imeeting:kmImeeting.tree.stat.res')}" align="right-top" borderWidth="2" icon="&#xe705;">
			<div style="width: 500px;">
				<ui:dataview>
					<ui:source type="Static">
						[{
							"text":"${lfn:message('km-imeeting:kmImeetingStat.stat.thrught')}",
							"children":[{	
									"text":"${lfn:message('km-imeeting:kmImeetingStat.resource.stat')}",		
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=resource.stat')",
									"target": "_self"
								},{
									"text":"${lfn:message('km-imeeting:kmImeetingStat.resource.statMon')}",		
									"href":"javascript:openSearch('${LUI_ContextPath}/km/imeeting/import/kmImeeting_stat.jsp?stat_key=resource.statMon')",
									"target": "_self"
							}]
						}]
					</ui:source>
					<ui:render ref="sys.ui.treeMenu2.cate"></ui:render>
				</ui:dataview>
			</div>
		</ui:menu-popup>
	</ui:menu>
</ui:content>

<script>
	seajs.use([
	   	'lui/jquery', 
	   	'lui/util/str', 
	   	'lui/dialog',
	   	'lui/topic'
	   	], function($, strutil, dialog, topic){
		window.openSearch=function(url){
			LUI.pageOpen(url,'_rIframe');
		}
		window.openHref=function(href){
			window.location.href=href;
		}
		
		window.moduleAPI = window.moduleAPI || {};
		
		window.moduleAPI.kmImeeting = {
			//切换菜单栏
			switchMenuItem : function(obj, panel,menuType, evt){
				 // hideFrame();
				 //openQuery();
				 LUI.pageHide("_rIframe");
				resetMenuNavStyle(obj);
				var tabpanel = LUI(panel);
				switch(menuType){
					case 'myCalendar' :  
					tabpanel.setSelectedIndex(0);
					tabpanel.props(0,{
						visible : true
					});
					topic.publish('spa.change.add', {
						target : obj
					});
					break;
					case 'other' :  
						tabpanel.setSelectedIndex(0);
						tabpanel.props(0,{
							visible : true
						});
						topic.publish('spa.change.add', {
							target : obj
						});
						break;	
				}
			}
		};   	
		window.setUrl=function(key,mykey,type){
			//重刷页面
			if(key!="${key}"){
				//会议安排
				if(key == 'imeeting'){
					if(type ==''){
			        	openUrl('km_imeeting_main','');
				    }else{
		    			openUrl('km_imeeting_main','cri.q='+mykey+':'+type);
				    }
			     }
			     //会议纪要
			     if(key == 'summary'){
			    	 if(type ==''){
			    		openUrl('km_imeeting_summary','');
				    }else{
			    	 	openUrl('km_imeeting_summary','cri.q='+mykey+':'+type);
				    }
			     }
			}else{
				//不需要冲刷页面
				openQuery();
				if(type==''){
					LUI('${criteria}').clearValue();
				}else{
					 LUI('${criteria}').setValue(mykey, type);
				}
			}
		};
		window.openUrl = function(prefix,hash){
		    var srcUrl = "${LUI_ContextPath}/km/imeeting/";
			srcUrl = srcUrl+ prefix+"/index.jsp";
			if(hash!=""){
				srcUrl+="#"+hash;
		    }
			window.open(srcUrl,"_self"); 
		};
		LUI.ready(function(){
	 		  // 初始化左则菜单样式
      	  setTimeout("initMenuNav('${JsParam.key}')", 300);
		});
		// 左则样式
		window.initMenuNav = function(fdKey) {
			var mydoc ;
			if(fdKey == "imeeting"){
				mydoc = getValueByHash("mymeeting");
			}
			if(fdKey == "summary"){
			    mydoc = getValueByHash("mysummary");
			}
			if(mydoc != "") {
				resetMenuNavStyle($("#"+fdKey+"_" + mydoc));
			} else {
				resetMenuNavStyle($("#"+fdKey+"_allFlow")); 
			}
			if(fdKey == "calendar"){
				resetMenuNavStyle($("#"+fdKey+"_" + "my"));
			}
			if(fdKey == "uploadAtt"){
				resetMenuNavStyle($("#"+fdKey));
			}
		 }
	});
</script>