<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/ui.tld" prefix="ui"%>
<%@page import="java.util.*"%>
<%@page import="java.lang.Object"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<!-- 极速模式下出现$未定义的情况，这里手动引用jquery -->
<script>Com_IncludeFile("jquery.js");</script>

<link charset="utf-8" rel="stylesheet"
	href="${LUI_ContextPath}/sys/ui/extend/theme/default/style/iconnav.css" />

 <div class="lui_list_create_frame">
    <!-- 图标式卡片 Starts -->
  		<div class="lui_loperation_wraper status_collapse">
			<div class="lui_loperation_navLeft">
				<!-- 展开收起 Starts-->
				<div class="lui_loperation_nav_opt" title="展开"><i class="status_icon"></i></div>
				<!-- 展开收起 Ends -->
				<!-- 菜单-->
				<ul>
					<% 
					Object _varParams = request.getAttribute("varParams");

					if (_varParams != null) {
						Map<String, Object> varParams = (Map) _varParams;
						Map<String, Object> sortMap = new TreeMap<String, Object>(new Comparator<String>() {
					        
					        public int compare(String o1, String o2) {
					        	 return Integer.parseInt(o1) - Integer.parseInt(o2);
					        }
					    });
						  
						sortMap.putAll(varParams);
						
						for(Map.Entry<String, Object> entry : sortMap.entrySet()) {
							String value = (String)entry.getValue();
					%>
						<ui:dataview format="sys.ui.nav.simple.icon">
							<%=value %>
							<ui:render ref="sys.ui.nav.simple.icon"/>
						</ui:dataview>
					<% 
						}
					}
					%>
				</ul>
			</div>
		</div>
 </div>
 
 		<script>
			$(function() {

				
				//二级菜单的展开和收起
			/*	$('.lui_loperation_productBar_list .nav_header').click(function() {
					var $obj = $(this).parent();
					if($obj.hasClass("status_collapse")) {
						$obj.removeClass("status_collapse").addClass('status_spread');
					} else {
						$obj.removeClass("status_spread").addClass('status_collapse');
					}
				})*/

				//导航的收展
				//二级菜单项的展开和收起
				
				$('.lui_loperation_nav_opt').click(function() {
					var $obj = $(this).parents('.lui_loperation_wraper');
					if($obj.hasClass("status_collapse")) {
						$("this").attr('title', '展开')
						$obj.removeClass("status_collapse").addClass('status_spread');
						$(".lui_list_left_sidebar_innerframe").addClass('status_expand_innerframe');
						$(".lui_list_left_sidebar_frame ").addClass('status_expand_innerframe_left');
						$(".lui_list_left_sidebar_td ").removeClass('lui_list_left_sidebar_frame');
						
						$(".lui_list_body_frame").css("margin-left","130px");
						
					 	seajs.use(['lui/topic'], function(topic) {
							topic.publish("navExpand");
						});	
						var mainiframe =  $("#mainIframe")[0] ?  $("#mainIframe")[0].contentWindow : null;
				          if(mainiframe) {
				            LUI.fire({
				               type : "topic",
				               name : "navExpand"
				             }, mainiframe);
				          }
					 	

					} else {
						$("this").attr('title', '收起')
						$obj.removeClass("status_spread").addClass('status_collapse');
						$(".lui_list_left_sidebar_innerframe").removeClass('status_expand_innerframe');
						$(".lui_list_body_frame").removeClass('status_expand_innerframe');
						$(".lui_list_body_frame").css("margin-left","50px");
						$(".lui_list_left_sidebar_frame ").removeClass('status_expand_innerframe_left');
						$(".lui_list_left_sidebar_td ").addClass('lui_list_left_sidebar_frame');
						
					 	seajs.use(['lui/topic'], function(topic) {
							topic.publish("navExpand");
						});	
						var mainiframe =  $("#mainIframe")[0] ?  $("#mainIframe")[0].contentWindow : null;
				          if(mainiframe) {
				            LUI.fire({
				               type : "topic",
				               name : "navExpand"
				             }, mainiframe);
				          }

					}
					

					
				})

				//二级菜单整体的展开与收起
				$('.lui_loperation_productBar_opt').click(function() {
					var $obj = $(this).parents('.lui_loperation_productBar');
					if($obj.hasClass("status_collapse")) {
						$("this").attr('title', '展开')
						$obj.removeClass("status_collapse").addClass('status_spread');
					} else {
						$("this").attr('title', '收起')
						$obj.removeClass("status_spread").addClass('status_collapse');
					}
				})
			})
		</script>
  

