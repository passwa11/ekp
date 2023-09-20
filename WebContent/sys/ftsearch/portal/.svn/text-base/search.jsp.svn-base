<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ClassUtils"%>
<%@ page import="
	com.landray.kmss.util.ResourceUtil,
	com.landray.kmss.util.StringUtil,
	java.net.URL" %>
<%
	String com_url=request.getServletPath();
  	boolean isSearchPath = (com_url.equals("/sys/ftsearch/search_ui.jsp")||com_url.equals("/sys/ftsearch/result_ui.jsp"));
  	pageContext.setAttribute("__isSearchPath__",isSearchPath);
	String serverURL = ResourceUtil.getKmssConfigString("kmss.ftsearch.aloneSearch.url");
	request.setAttribute("IndependentSearch",StringUtil.isNotNull(serverURL));
	String modelName = "com.landray.kmss.third.intell.model.IntellConfig";
	if(com.landray.kmss.util.ModelUtil.isExisted(modelName)){
		Class clz = ClassUtils.forName(modelName);
		Object instance = clz.newInstance();
		String itEnabled = (String)clz.getMethod("getItEnabled", null).invoke(instance, null);		
		request.setAttribute("itEnabled",itEnabled);
		if("true".equals(itEnabled)||"1".equals(itEnabled)){
			String itUrl = (String)clz.getMethod("getItUrl", null).invoke(instance, null);
			String searchUrl = "/labc-search/#/?q=";
					try{
						String tmpUrl =	(String)clz.getMethod("getSearchUrl", null).invoke(instance, null);
							if(StringUtil.isNotNull(tmpUrl)){
								searchUrl = tmpUrl;
							}
					
					}catch(Exception e){
						
					}
			if(StringUtil.isNotNull(itUrl)){
				request.setAttribute("itUrl",StringUtil.formatUrl(itUrl));
				String searchEnabled =  (String)clz.getMethod("getSearchEnabled", null).invoke(instance, null);
				if("true".equals(searchEnabled)||"1".equals(searchEnabled)){
					URL url = new URL(itUrl);
					String host = ("https".equalsIgnoreCase(url.getProtocol())?"https://":"http://")+url.getHost()+(url.getPort()==-1||url.getPort()==80||url.getPort()==443?"":":"+url.getPort());
					if(host != ""){
						host =host+searchUrl;
					}
					request.setAttribute("__searchHost__", host);
				}
			}
		}
	}
%>
	<div id="lui_portal_header_search_include" class="lui_portal_header_search_include"  onmouseover="showCategory()" onmouseout="hideCategory()">
		<div class="lui_portal_header_search_input <c:if test="${ itEnabled=='true' }"> lui_portal_header_search_intell</c:if> ">
			<div class="lui_portal_header_search_keyword_l">
				<div class="lui_portal_header_search_keyword_r">
					<div class="lui_portal_header_search_keyword_c">
						<c:if test="${ itEnabled=='true' }">
							<i class="icon_robot"></i>
						</c:if>
						<input type="text" placeholder="${lfn:message('sys-ftsearch-db:search.ftsearch.textDefaultValue')}" name="SEARCH_KEYWORD" onkeydown="if (event.keyCode == 13 && this.value !='') __portal_search__full();"/ >
					</div>
				</div>
			</div>
			     <div id="searchCategory" class="searchCategory"  onmouseover="showCategory()" onmouseout="hideCategory()">
            <div id="searchAll" class="search_item" >
			 				<label>
			 					<span class="search_radio">
					 				<input name="search" type="radio" id="searchAll" value="searchAll" checked="checked"/>	
					 				<i class="item_radio"></i>
				 				</span>
				 				<span class="search_text">${lfn:message('sys-ftsearch:AllSearch')}</span>
			 				</label>
			 	</div>
			 	 <div id="searchPeople" class="search_item" >
				 			<label>
				 				<span class="search_radio">
					 				<input name="search" type="radio" id="searchPeople" value="searchPeople"/>
					 				<i class="item_radio"></i>
				 				</span>
				 				<span class="search_text">${lfn:message('sys-ftsearch:PersonSearch')}</span>
				 			</label>
			 	</div>
			 	 <div id="searchModel" class="search_item" >
			 			<label>
			 				<span class="search_radio">
				 				<input name="search" type="radio" id="searchApp" value="searchModel"/>
				 				<i class="item_radio"></i>
			 				</span>
			 				<span class="search_text">${lfn:message('sys-ftsearch:ModelSearch')}</span>
			 			</label>
			 	</div>
	</div> 
		</div>
		<div class="lui_portal_header_search_btn search_btn_icon">
	        <div class="lui_portal_header_search_full_l">
				<div class="lui_portal_header_search_full_r">
					<div class="lui_portal_header_search_full_c">
						<input type="button" value="${lfn:message('sys-ftsearch-db:search.ftsearch.all.system')}" onclick="__portal_search__full()"/>
					</div>
				</div>
			</div>
			
		</div>
		<div class="lui_portal_header_search_btn" id="_SEARCH_MODEL_" style="display: none" >
			<div class="lui_portal_header_search_model_l">
				<div class="lui_portal_header_search_model_r">
					<div class="lui_portal_header_search_model_c">
						<input type="button" value="${lfn:message('sys-ftsearch-db:search.ftsearch.local.model')}" onclick="__portal_search__()"/>
					</div>
				</div>
			</div>
		</div>    
    </div>

	<script>
		var newLUI="true";
		var separator = ${IndependentSearch} ? "&" : "#";
		
		if("${__isSearchPath__}" == "true"){
	 		document.getElementById("lui_portal_header_search_include").style.display='none';
		}else{ 
			document.getElementById("lui_portal_header_search_include").style.display='block'; 
		}	
		function __portal_search__(){
			var keyField = document.getElementsByName("SEARCH_KEYWORD")[0];
			if(keyField.value==""){
				keyField.focus();
				return;
			}else{
				if(typeof(DB_SEARCH) == "undefined"){
					var searchHost = "${__searchHost__}"
					if(searchHost!=""){
						var url = '${__searchHost__}';
					}else{
						var url = Com_Parameter.ContextPath+"sys/ftsearch/searchBuilder.do?method=search&queryString=";
					}
					url = url + encodeURIComponent(keyField.value);
					url = url + "&newLUI=" + newLUI;
					url = url + "&modelName=" + encodeURIComponent(SYS_SEARCH_MODEL_NAME);
					url = url + "&seq=" + Math.random();
					window.open(url,"_blank");
				}else{//重定向为数据库搜索
					db_search(keyField.value);
				}
			}
		}
		function __portal_search__full(){
			 var searchFlag = $("input[name=search]:checked").val();
			var keyField = document.getElementsByName("SEARCH_KEYWORD")[0];
			if(keyField.value==""){
				keyField.focus();
				return;
			}else{
				if("searchAll" == searchFlag){
				var searchHost = "${__searchHost__}"
				if(searchHost!=""){
					var url = '${__searchHost__}';
				}else{
					var url = Com_Parameter.ContextPath+"sys/ftsearch/searchBuilder.do?method=search&queryString=";
				}
				url = url + encodeURIComponent(keyField.value);
				url = url + "&newLUI=" + newLUI;
				url = url + "&seq=" + Math.random();
				url = url + "&searchAll=true";
				window.open(url,"_blank");
				}
				if("searchPeople" == searchFlag){
					if (keyField.value == null || trim(keyField.value) == "") {
						seajs.use('lui/dialog', function (dialog) {
							// 请输入内容
							dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
								$("#topKeyword").focus();
							});
						});
					}else{
						var url = Com_Parameter.ContextPath + 'sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=getPersons';
							url = url + "&fdSearchName="+encodeURIComponent(trim(keyField.value))+"&searchPeople=true";
							window.open(url,"_blank");
							}

					
				}
				if("searchModel" == searchFlag){
					if (keyField.value == null || trim(keyField.value) == "") {
						seajs.use('lui/dialog', function (dialog) {
							// 请输入内容
							dialog.alert("${lfn:message('sys-ftsearch-db:ftsearch.select.queryString')}", function () {
								$("#topKeyword").focus();
							});
						});
					}else{
						var url = Com_Parameter.ContextPath + 'sys/common/searchModel.jsp?query='+encodeURIComponent(trim(keyField.value));
						url=url+"&searchModel=true";
						window.open(url,"_blank");
					}
				}
				
			} 
		}
		
		function showCategory(){
			
			if($('#_SEARCH_MODEL_').css('display')=="none"){
				$("#searchCategory").show();
			}
		}
		function hideCategory(){
			if($('#_SEARCH_MODEL_').css('display')=="none"){
			$("#searchCategory").hide();
			}
		}
		
		
		function trim(str){ //删除左右两端的空格
		　　     return str.replace(/(^\s*)|(\s*$)/g, "");
		　　 }
		
		var SYS_SEARCH_MODEL_NAME;
   		LUI.ready(function(){
   			$("#searchCategory").hide();
   			if(SYS_SEARCH_MODEL_NAME != null){
   				LUI.$("#_SEARCH_MODEL_").show();
   			}
   			var itEnabled = "${itEnabled}";
   			if(itEnabled=='true'){
   				$('.lui_portal_header_search_intell .icon_robot').click(function(){
   					Com_OpenWindow("${itUrl}",'_blank');
   	   			});
   			}
   			
   		});
   		
   		

   		
   		
	</script>