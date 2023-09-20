<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.net.URL"%>
<%@ page import="
	java.util.Iterator,
	com.landray.kmss.sys.ftsearch.config.LksConfigBuilder,
	com.landray.kmss.sys.ftsearch.config.LksConfig,
	com.landray.kmss.util.ResourceUtil,
	com.landray.kmss.util.StringUtil,
	com.landray.kmss.sys.config.dict.SysDataDict" %>
<%
	String com_url=request.getServletPath();
	boolean isSearchPath = (com_url.equals("/sys/ftsearch/search_ui.jsp")||com_url.equals("/sys/ftsearch/result_ui.jsp"));
	pageContext.setAttribute("__isSearchPath__",isSearchPath);
	String serverURL = ResourceUtil.getKmssConfigString("kmss.ftsearch.aloneSearch.url");
	request.setAttribute("IndependentSearch",StringUtil.isNotNull(serverURL));
	String modelName = "com.landray.kmss.third.intell.model.IntellConfig";
	if(com.landray.kmss.util.ModelUtil.isExisted(modelName)){
		Class clz = Class.forName(modelName);
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
<div id="lui_single_menu_header_search_include" class="lui_single_menu_header_search_include">
	<div class="lui_single_menu_header_search_input <c:if test="${ itEnabled=='true' }"> lui_single_menu_header_search_intell</c:if> ">
		<div class="lui_single_menu_header_search_keyword_l">
			<div class="lui_single_menu_header_search_keyword_r">
				<div class="lui_single_menu_header_search_keyword_c">
					<i class="lui_single_menu_header_icon lui_icon_l_cancel lui_icon_l_fork" onclick="hideHeadSearch()"></i>
					<div class="lui_single_menu_header_search_dropdown_menu">
						<span class="lui_single_menu_header_search_dropdown_menu_toggle">全部</span>
					</div>
					<c:if test="${ itEnabled=='true' }">
						<i class="icon_robot"></i>
					</c:if>
					<input type="text" class="lui_single_menu_header_search_keyword_input" placeholder="${lfn:message('sys-ftsearch-db:search.ftsearch.textDefaultValue')}" name="SEARCH_KEYWORD" onkeydown="if (event.keyCode == 13 && this.value !='') __portal_search__full();"/ >
					<i class="lui_single_menu_header_icon lui_icon_l_icon_search" onclick="__portal_search__full()"></i>
				</div>
			</div>
		</div>

	</div>
	<!-- 用于计算占位 -->
	<div class="lui_single_menu_header_search_input_placeholder lui_single_menu_header-active <c:if test="${ itEnabled=='true' }"> lui_single_menu_header_search_intell</c:if> ">
	</div>
	<div class="lui_single_menu_header_search_btn search_btn_icon" onclick="showHeadSearch()">
	<i class="lui_single_menu_header_icon lui_icon_l_icon_search"></i>
</div>
<!-- 展开按钮 -->
<div class="lui_single_menu_header_search_btn" id="_SEARCH_MODEL_" style="display: none" >
	<div class="lui_single_menu_header_search_model_l">
		<div class="lui_single_menu_header_search_model_r">
			<div class="lui_single_menu_header_search_model_c">
				<input type="button" value="${lfn:message('sys-ftsearch-db:search.ftsearch.local.model')}" onclick="__portal_search__()"/>
			</div>
		</div>
	</div>
</div>
</div>

<script>
	var newLUI="true";
	var separator = ${IndependentSearch} ? "&" : "#";

	function showHeadSearch(){
		$(".lui_single_menu_header_search_input").addClass('lui_single_menu_header-active');
		$(".lui_single_menu_header_search_btn.search_btn_icon").hide();
	}

	function hideHeadSearch(){
		$(".lui_single_menu_header_search_input").removeClass('lui_single_menu_header-active');
		$(".lui_single_menu_header_search_btn.search_btn_icon").show();
	}

	if("${__isSearchPath__}" == "true"){
		document.getElementById("lui_single_menu_header_search_include").style.display='none';
	}else{
		document.getElementById("lui_single_menu_header_search_include").style.display='block';
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

	/**
	 * 选项搜索下拉渲染
	 * @return
	 */
	function renderSearchOption(){
		var $lui_single_menu_header_search_dropdown_menu_list = $("<div class='lui_single_menu_header_search_dropdown_menu_list'>");
		//$lui_single_menu_header_search_dropdown_menu_list.html(`
		//					<ul>
		//						<li><a href="javascript:;"><label><input name="search" type="radio" id="searchAll" value="searchAll" checked="checked"/>全部</label></a></li>
		//						<li><a href="javascript:;"><label><input name="search" type="radio" id="searchPeople" value="searchPeople"/>人员</label></a></li>
		//						<li><a href="javascript:;"><label><input name="search" type="radio" id="searchApp" value="searchModel"/>应用</label></a></li>
		//					</ul>
		//				`);
		var menu_top =$(".lui_single_menu_header_search_dropdown_menu").offset().top;
		var menu_left =$(".lui_single_menu_header_search_dropdown_menu").offset().left;
		var menu_height =$(".lui_single_menu_header_search_dropdown_menu").height();
		$lui_single_menu_header_search_dropdown_menu_list.appendTo($("body"))
		$lui_single_menu_header_search_dropdown_menu_list.css({"display":"none","top":menu_top+menu_height,"left":menu_left})

		var htmlsearch = '<ul>'
								+ '<li>'
								+ '<a href="javascript:;"><label><input name="search" type="radio" id="searchAll" value="searchAll" checked="checked"/>全部</label></a>'
								+ '</li>'
								+ '<li>'
								+ '<a href="javascript:;"><label><input name="search" type="radio" id="searchPeople" value="searchPeople"/>人员</label></a>'
								+ '</li>'
								+ '<li>'
								+ '<a href="javascript:;"><label><input name="search" type="radio" id="searchApp" value="searchModel"/>应用</label></a>'
								+ '</li>'
								+ '</ul>';
								
						LUI.$(".lui_single_menu_header_search_dropdown_menu_list").html(htmlsearch);

	}

	// 选项搜索定位
	function postionSearchOption(){
		var menu_top =$(".lui_single_menu_header_search_dropdown_menu").offset().top;
		var menu_left =$(".lui_single_menu_header_search_dropdown_menu").offset().left;
		var menu_height =$(".lui_single_menu_header_search_dropdown_menu").height();
		$(".lui_single_menu_header_search_dropdown_menu_list").css({"top":menu_top+menu_height,"left":menu_left});
	}

	LUI.ready(function(){

		renderSearchOption()
		//$("#searchCategory").hide();
		//if(SYS_SEARCH_MODEL_NAME != null){
		//	LUI.$("#_SEARCH_MODEL_").show();
		//}
		var itEnabled = "${itEnabled}";
		if(itEnabled=='true'){
			$('.lui_single_menu_header_search_intell .icon_robot').click(function(){
				Com_OpenWindow("${itUrl}",'_blank');
			});
		}
		// 标识是否移开区域
		var searchFlag = false,searchListFlag = false;
		//$(".lui_single_menu_header_search_dropdown_menu").on("mouseenter",".lui_single_menu_header_search_dropdown_menu_toggle",function(e){
			$(".lui_single_menu_header_search_dropdown_menu").on("click",".lui_single_menu_header_search_dropdown_menu_toggle",function(e){
			$(".lui_single_menu_header_search_dropdown_menu_list").slideDown(200);
			var searchFlag = true;
			postionSearchOption()
			$(document).one('click', function () {
				$('.lui_single_menu_header_search_dropdown_menu_list').slideUp(200);
			});
			e.stopPropagation();
		})
		$('.lui_single_menu_header_search_dropdown_menu_list').mouseenter(function(){
			searchListFlag = true
		})
		$('.lui_single_menu_header_search_dropdown_menu_list').mouseleave(function(){
			searchListFlag = false
			if(!searchFlag){
				$('.lui_single_menu_header_search_dropdown_menu_list').slideUp(200);
			}
		})
		$(".lui_single_menu_header_search_dropdown_menu").on("mouseleave",".lui_single_menu_header_search_dropdown_menu_toggle",function(){
			searchFlag = false
			if(searchListFlag){
				$('.lui_single_menu_header_search_dropdown_menu_list').slideUp(200);
			}
		})
		$(".lui_single_menu_header_search_dropdown_menu_list").on("click","li",function(){
			var $this = $(this);
			var i = $this.index();
			$('.lui_single_menu_header_search_dropdown_menu_toggle').text($this.text()).attr('title', $this.text());
			// $(this).addClass('active');
		})
	});
</script>