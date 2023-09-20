<%@ taglib prefix="if" uri="http://www.landray.com.cn/tags/kmss" %>
<%@ page import="com.landray.kmss.sys.appconfig.model.BaseAppconfigCache" %>
<%@ page import="org.apache.commons.collections.MapUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- 全系统搜索 -->
	<title>${lfn:message('sys-ftsearch-db:search.search.by.all')}</title>
	<%@ include file="/sys/ui/jsp/jshead.jsp"%>
	<script type="text/javascript" src="${KMSS_Parameter_ResPath}style/common/fileIcon/fileIcon.js"></script>
	<link href="styles/Mcommon.css" rel="stylesheet" type="text/css" />
	<link href="styles/reset.css" rel="stylesheet" type="text/css" />
	<link href="styles/Pcommon.css" rel="stylesheet" type="text/css" />
	<link href="styles/search3.css" rel="stylesheet" type="text/css" />
	<link href="styles/jquery-ui.min.css" rel="stylesheet" type="text/css" />
	<link href="styles/multiple-select.css" rel="stylesheet" type="text/css">
	
	<script type="text/javascript" src="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/scripts/shBrushBash.js"></script>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/styles/shCore.css"/>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/styles/shThemeDefault.css"/>
	
    
	<style type="text/css"> 
	.grid_s_r_all{
		width:48px;
		float:left;
	}
	.grid_s_cl_r .item1{
		margin-right: 15px;
		padding-top: 0px;
		padding-bottom: 0px;
		padding-left: 3px;
		margin-top: 5px;
	}
	.grid_s_cl_r .item2{
		margin-right: 15px;
		padding-top: 0px;
		padding-bottom: 0px;
		padding-left: 3px;
		margin-top: 5px;
	}
	.search_multiple_selection > a{
		display: block;
		position: relative;
	}
	.search_multiple_selection > a:hover{
		color: #F19703;
	}
	.search_multiple_selection .checkbox{
		width: 12px;
		height: 12px;
		background:url(styles/images/search_icon_check.png) no-repeat 50%;
		display: block;
		position: absolute;
		left: -12px;
		top:50%;
		margin-top: -6px;
	}
	.search_multiple_selection .checkbox.checked{
		background-image: url(styles/images/search_icon_checked.png);
	}
	.search_none {
	    background: url(styles/images/search_none.gif) no-repeat 23px 33px;
	    background-image: url(styles/images/search_none.gif);
	    background-position-x: 23px;
	    background-position-y: 33px;
	    background-size: initial;
	    background-repeat-x: no-repeat;
	    background-repeat-y: no-repeat;
	    background-attachment: initial;
	    background-origin: initial;
	    background-clip: initial;
	    background-color: initial;
	    border: 1px solid #d9d9d9;
	    padding: 29px 30px 23px 124px;
	    margin: 10px auto;
		margin-right: 12px;
	}
	.search_none li{
		padding: 5px 0;
		color: #000000;
	}
	#search_range_a {
	    background: url(styles/images/arrow_down.gif) no-repeat right center;
	    padding-right: 10px;
	}
	.categoryNode{
		background-image: url(${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/icon/s/icon-circle-arrow-right.png);
	   	background-repeat: no-repeat;
	    background-position: center;
	    display: inline-block;
	    vertical-align: middle;
	    width: 16px;
	    height: 16px;
	    line-height: 1px;
	}
	.categoryNodeParent{
		background-image: url(${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/images/criteria/parent_arrow.png);
	   	background-repeat: no-repeat;
	    background-position: center;
	    display: inline-block;
	    vertical-align: middle;
	    width: 16px;
	    height: 16px;
	    line-height: 1px;
    }
	</style>
</head>
<%-- 是否隐藏主搜索（MK搜索引入时需要隐藏主搜索） --%>
<c:set var="display" value=""/>
<c:if test="${'self' eq mktarget or 'self' eq param.mktarget}">
<c:set var="display" value="display: none;"/>
</c:if>
<if:ifModuleExist path="/third/hisearch">
<%
	Map<String, String> data =  BaseAppconfigCache.getCacheData("com.landray.kmss.third.hisearch.config.ThirdHiSearchConfig");
	boolean hisearchEnable = false;
	if(MapUtils.isNotEmpty(data)){
		hisearchEnable =  null != data.get("enable") && "true".equals(data.get("enable"))? true:false;
		boolean role_thirdhisearch_default = UserUtil.checkRole("ROLE_THIRDHISEARCH_DEFAULT");
		hisearchEnable = hisearchEnable && role_thirdhisearch_default;
		request.setAttribute("existHisearch",hisearchEnable);
		if(hisearchEnable){
			String hisearchGraphUrl = data.get("hiezServerUrl");
			if(StringUtils.isNotBlank(hisearchGraphUrl) && !hisearchGraphUrl.endsWith("/")){
				hisearchGraphUrl = hisearchGraphUrl+"/";
			}
			String kgApk = data.get("kgApk");
			String kgName = data.get("kgName");
			if(StringUtils.isNotBlank(hisearchGraphUrl)
					&& StringUtils.isNotBlank(kgApk)
					&& StringUtils.isNotBlank(kgName)){
				request.setAttribute("hisearchGraphUrl", hisearchGraphUrl+kgName+"/"+kgApk+"?kw=");
			}
		}
	}
%>
</if:ifModuleExist>
<body>
<!-- 打开搜索结果表单 -->
<form id="sysFtsearchReadLogForm" name="sysFtsearchReadLogForm"  action="<c:url value="/sys/ftsearch/expand/sys_ftsearch_read_log/sysFtsearchReadLog.do?method=save" />" method="post" target="_blank">
	<input id="fdDocSubject" name="fdDocSubject" type="hidden">
	<input id="fdModelName" name="fdModelName" type="hidden">
	<input id="fdCategory" name="fdCategory" type="hidden">
	<input id="fdUrl" name="fdUrl" type="hidden">
	<input id="fdSearchWord" name="fdSearchWord" type="hidden">
	<input id="fdHitPosition" name="fdHitPosition" type="hidden">
	<input id="fdModelId" name="fdModelId" type="hidden">
</form>
    <!-- 主搜索 Begin -->
    <div class="search_main_wrapper" style="${display}">
        <div class="search_main_box clrfix">
            <div class="search_main_content">
            	 <div class="search_bar">
		           	<input id="topKeyword" maxlength="300" class="input_txt" type="text" onkeydown="if (event.keyCode == 13 && this.value !='') commitSearchByTopKeywordInput();"/>
		                    <!-- 搜索 -->
		            <a id="input_btnTop" class="input_btn" href="javascript:void(0)" onclick="commitSearchByTopKeywordInput();">${lfn:message('sys-ftsearch-db:ftsearch.advanced.button.search')}</a>
		         </div>
			</div>
            <div class="search_main_linkToHome" onclick="location.href='${KMSS_Parameter_ContextPath}'">
           
            </div>
        </div>
    </div>
    <!-- 主搜索 End -->
    
    <c:if test="${JsParam._showTagContent != 'false'}">
	    <!-- 搜索主体 Begin -->
	    <div class="search_bar_tab_content">
				<div class="search_bar_tab">
			        	<label id="searchAll" onclick="change(this)" class="current">
			   		 		<p>
						 		<span>${lfn:message('sys-ftsearch:AllSearch')}</span>
						 	</p>
						</label>
						<label id="searchPeople" onclick="change(this)">
						 	<p>
						 		<span>${lfn:message('sys-ftsearch:PersonSearch')}</span>
						 	</p>
						</label>
						<label id="searchModel" onclick="change(this)">
						 	<p>
						 		<span>${lfn:message('sys-ftsearch:ModelSearch')}</span>
						 	</p>
						</label>
				</div>
	        <!-- 搜索数据 结束 -->
	    </div>
    </c:if>
	<div class="search_result">
		<div class="search_result_box">
			<!-- 搜索到约X项结果 开始-->
			<span>${lfn:message('sys-ftsearch-db:search.ftsearch.probably')}<em id="resultCount">0</em>${lfn:message('sys-ftsearch-db:search.ftsearch.itemResult')}</span>
			<!-- 用时X秒 -->
			<span>${lfn:message('sys-ftsearch-db:search.ftsearch.search.userTime')}<em id="searchUserTime">0</em>${lfn:message('sys-ftsearch-db:search.ftsearch.search.minute')}</span>
			<!-- 搜索到约X项结果 结束-->

			<!-- 右侧条件筛选 开始 -->
			<div class="search_operation" style="float:right;display:${JsParam._showOperation == 'false' ? 'none' : 'block'}">
				<div id="oper_time" onclick="showTimefilter();" class="show">${lfn:message('sys-ftsearch-db:search.ftsearch.time.range')}
					<div >
						<span class="filter_caret_down"></span>
					</div>
				</div>
				<div  id="timeFilter" class="search_aboutBox" style="display:none;">
					<ul>
						<li class="current" id="time_range_all" ><a href="javascript:void(0)" onclick="commitSearchByTimeScope('');">${lfn:message('sys-ftsearch-db:search.ftsearch.time.nolimit')}</a></li>
						<li id="time_range_day" ><a href="javascript:void(0)" onclick="commitSearchByTimeScope('day');">${lfn:message('sys-ftsearch-db:search.ftsearch.time.day')}</a></li>
						<li id="time_range_week" ><a href="javascript:void(0)" onclick="commitSearchByTimeScope('week');">${lfn:message('sys-ftsearch-db:search.ftsearch.time.week')}</a></li>
						<li id="time_range_month" ><a href="javascript:void(0)" onclick="commitSearchByTimeScope('month');">${lfn:message('sys-ftsearch-db:search.ftsearch.time.month')}</a></li>
						<li id="time_range_year" ><a href="javascript:void(0)" onclick="commitSearchByTimeScope('year');">${lfn:message('sys-ftsearch-db:search.ftsearch.time.year')}</a></li>
					</ul>
					<div class="time_cus_def">${lfn:message('sys-ftsearch-db:search.ftsearch.time.custom')}</div>
					<ul style="margin-top: 4px">
						<li><input type="text" id="startTime"
							   placeholder="${lfn:message('sys-ftsearch-db:search.ftsearch.time.startTime')}"
							   readonly="readonly"/></li>
						<li style="margin-top: 8px"><input type="text" id="endTime"
							   placeholder="${lfn:message('sys-ftsearch-db:search.ftsearch.time.endTime')}"
							   readonly="readonly"/></li>
						<li style="margin-top: 8px;border-top: 1px solid #eee;padding: 8px 9px"><input type="button" class="filterClose"
							   onclick="hideTimefilter();" value="${lfn:message('sys-ftsearch-db:search.confirm.button')}"/></li>
					</ul>
				</div>
				<div id="oper_time_close" class="select" onclick="hideTimefilter();" style ="display:none;">
					${lfn:message('sys-ftsearch-db:search.ftsearch.time.range')}
					<div >
						<span class="filter_caret_up"></span>
					</div>
				</div>
				<div id="oper_more" onclick="showSearchfilter();" class="show">
					${lfn:message('sys-ftsearch-db:search.ftsearch.moreCondition')}
					<div >
						<span class="filter_caret_down"></span>
					</div>
				</div>
				<div id="oper_more_close" class="select" onclick="hideSearchfilter();" style ="display:none;">${lfn:message('sys-ftsearch-db:search.ftsearch.moreCondition')}
					<div >
						<span class="filter_caret_up"></span>
					</div>
				</div>
				<div id="oper_adv" onclick="showAdvfilter();" class="show">${lfn:message('sys-ftsearch-db:ftsearch.advanced.button')}
					<div >
						<span class="filter_caret_down"></span>
					</div>
				</div>
				<div id="oper_adv_close" class="select" onclick="hideAdvfilter();" style ="display:none;">${lfn:message('sys-ftsearch-db:ftsearch.advanced.button')}
					<div >
						<span class="filter_caret_up"></span>
					</div>
				</div>
			</div>
			<!-- 右侧条件筛选 结束 -->
		</div>
	</div>
	<div class="search_m_body">
	<%--</c:if>--%>
        <div class="main_content">
            <!-- 左侧 搜索结果 开始 -->
			<c:if test="${existHisearch}">
			<div id="search_m_bodyL" style="width: 70%" class="search_m_bodyL" data-hisearch="${existHisearch}">
			</c:if>
			<c:if test="${!existHisearch}">
				<div id="search_m_bodyL" class="search_m_bodyL" style="width: 100%" data-hisearch="${existHisearch}">
			</c:if>
                    <!-- 搜索数据 开始 -->

                <!-- 筛选 开始 -->
				<div id="modelfilter" class="search_filter" style="display:none;">
					<div class="head_right" onclick = "hideSearchfilter();"></div>
					
					<div class="grid_box">
<!-- 
						<div class="grid_s_t clrfix">
							<div class="grid_s_title">已选条件：</div>
							<div class="grid_s_titleL">
								<div class="grid_s_tl"> </div>
								<span class="grid_search grid_search_size1">
				            		<input type="text" class="grid_search_t" value="请输入关键字" />
				            		
				        		</span>
							</div>
						</div>
-->
						
						<div class="grid_s_fl">
						
							<div class="grid_s_cl_body">
								<div style="width:70px;padding: 4px 0px 4px 10px;color: #5b5b5b; float:left;display: inline-block;">
									<!-- 搜索范围 -->
									<span style="color: #5b5b5b;display: inline-block;font-size: 14px;">${lfn:message('sys-ftsearch-db:search.ftsearch.search.range')}</span>
								</div>
								<div class="grid_s_cl_r">
									<div class="grid_s_cl_all">
										<a id="catagoryAll" class='item1' onclick="clickCatagoryAll()"> <span>${lfn:message('sys-ftsearch-db:search.ftsearch.filter.no')}</span></a>
									</div>
									<div id="entries_design_top" class="grid_s_cl_item search_multiple_selection">
										
									</div>
								</div>
								<div class="clr"> </div>
							</div>
							
							<div class="grid_s_cl_body">
								<div style="width:70px;padding: 4px 0px 4px 10px;color: #5b5b5b; float:left;display: inline-block;">
									<!-- 文档状态 -->
									<span style="color: #5b5b5b;display: inline-block;font-size: 14px;">${lfn:message('sys-ftsearch-db:search.ftsearch.field.docStatus')}</span>
								</div>
								<div class="grid_s_cl_r">
									<div class="grid_s_cl_all">
										<a id="doc_status_" class="item1" onclick="clickStatusAll()"><span>${lfn:message('sys-ftsearch-db:search.ftsearch.filter.no')}</span></a>
									</div>
									<div id="doc_status_top" class="grid_s_cl_item search_multiple_selection">
										<a id="doc_status_10" class="item1"><span class="checkbox"></span><span>${lfn:message('status.draft')}</span></a>
										<a id="doc_status_20" class="item1"><span class="checkbox"></span><span>${lfn:message('status.examine')}</span></a>
										<a id="doc_status_11" class="item1"><span class="checkbox"></span><span>${lfn:message('status.refuse')}</span></a>
										<a id="doc_status_30" class="item1"><span class="checkbox"></span><span>${lfn:message('status.publish')}</span></a>
										<a id="doc_status_40" class="item1"><span class="checkbox"></span><span>${lfn:message('status.expire')}</span></a>
										<a id="doc_status_00" class="item1"><span class="checkbox"></span><span>${lfn:message('status.discard')}</span></a>
								    </div>
								</div>
								<div class="clr"></div>
						 	</div>
							
							<div class="grid_s_cl_body">
								<div style="width:70px;padding: 4px 0px 4px 10px;color: #5b5b5b; float:left;display: inline-block;">
									<!-- 搜索域 -->
									<span style="color: #5b5b5b;display: inline-block;font-size: 14px;">${lfn:message('sys-ftsearch-db:search.ftsearch.search.fields')}</span>
								</div>
								<div class="grid_s_cl_r">
									<div class="grid_s_cl_all">
										<a id="search_field_" class="item1" onclick="clickFieldsAll()"><span>${lfn:message('sys-ftsearch-db:search.ftsearch.filter.no')}</span></a>
									</div>
									<div id="search_fields" class="grid_s_cl_item search_multiple_selection" >
										<!-- 标签 -->
										<a id="search_field_tag" class="item1"><span class="checkbox"></span><span>${lfn:message('sys-ftsearch-db:search.ftsearch.field.tag')}</span></a>
										<!-- 标题 -->
										<a id="search_field_title" class="item1"><span class="checkbox"></span><span>${lfn:message('sys-ftsearch-db:search.ftsearch.field.title')}</span></a>
										<!-- 正文 -->
										<a id="search_field_content" class="item1"><span class="checkbox"></span><span>${lfn:message('sys-ftsearch-db:search.ftsearch.field.content')}</span></a>
										<!-- 简介 -->
										<a id="search_field_fdDescription" class="item1"><span class="checkbox"></span><span>${lfn:message('sys-ftsearch-db:search.ftsearch.field.description')}</span></a>
										 <!-- 创建者 -->
										<a id="search_field_creator" class="item1"><span class="checkbox"></span><span>${lfn:message('sys-ftsearch-db:search.ftsearch.field.creator')}</span></a>
										<!-- 附件 -->
										<a id="search_field_attachment" class="item1" style="padding-right: 0px;margin-right: 0px;"><span class="checkbox"></span><span>${lfn:message('sys-ftsearch-db:search.ftsearch.field.attachment')}</span></a>
						                <select id="doc_file_type" style="width: 120px;height: 12px;" multiple="multiple" >
						                    <option value="">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.all')}</option>
						                    <option value="pdf">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.pdf')}</option>
						                    <option value="doc;docx">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.doc')}</option>
						                    <option value="xls;xlsx">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.xls')}</option>
						                    <option value="ppt;pptx">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.ppt')}</option>
						                    <option value="txt">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.txt')}</option>
						                    <option value="exe">${lfn:message('sys-ftsearch-db:search.ftsearch.docFileType.exe')}</option>
						                </select>						 
									</div>
								</div>
								<div class="clr"> </div>
							</div>
						 </div>
					</div>
				</div>
                <!-- 筛选 结束 -->
		                <div id="advFilter" class="fiterContent" style="display:none;">
		                    <div>
		                        <span class="rd_item">
		                        	<!-- 包含任意一个关键词 -->
									<label style="cursor: pointer;">
			                        	<input id="keyword_bond_or" type="radio" name="rdRelation" onclick="commitSearchByKeywordBond('or');" />
			                       		${lfn:message('sys-ftsearch-db:search.ftsearch.bond.or')}
			                        </label>
		                        </span>
		                        <span class="rd_item">
		                        	<!-- 包含全部的关键词 -->
			                        <label style="cursor: pointer;">
			                        	<input id="keyword_bond_and" type="radio" name="rdRelation" onclick="commitSearchByKeywordBond('and');"/>
			                        	${lfn:message('sys-ftsearch-db:search.ftsearch.bond.and')}
			                        </label>
		                        </span>
		            			<span class="rd_item">
		                        	<!-- 完全匹配关键词 -->
			                        <label style="cursor: pointer;">
			                        	<input id="keyword_bond_like" type="radio" name="rdRelation" onclick="commitSearchByKeywordBond('like');"/>
			                        	${lfn:message('sys-ftsearch-db:search.ftsearch.bond.like')}
			                        </label>
		                        </span>
		                        <div class="outKeyWord">
			                        <!-- 不包括以下关键词： -->
				                    <span class="title">${lfn:message('sys-ftsearch-db:search.ftsearch.outKeyword')}</span>
				                    <input id="out_keyword" class="input_txt" type="text" onkeydown="if (event.keyCode == 13) commitSearchByOutKeyword();" />
				                    <button id="out_keyword_button" class="filterClose" onclick="commitSearchByOutKeyword();">
				                    	<span>${lfn:message('sys-ftsearch-db:search.confirm.button')}</span>
				                    </button>
			                    </div>
		                    </div>
		                </div>
                <!-- 常用标签 Begin-->
                <div class="lui_common_list_box">
                    <div class="lui_common_list_4_headerL">
                        <div class="lui_common_list_4_headerR">
                            <div class="lui_common_list_4_headerC clrfix">
                                <ul class="search_tabSort">
                                	<!-- 按相关度排序 -->
                                    <li id="sort_by_relevance" class="on" onclick="commitSearchBySort('');"><span>${lfn:message('sys-ftsearch-db:search.ftsearch.sort.by.score')}</span></li>
                                    <!-- 按阅读量排序 -->
                                    <li id="sort_by_read" onclick="commitSearchBySort('readCount');"><span>${lfn:message('sys-ftsearch-db:search.ftsearch.sort.by.readCount')}</span></li>
                                    <!-- 按时间排序 -->
                                    <li id="sort_by_time" onclick="commitSearchBySort('time');">
                                    	<span>${lfn:message('sys-ftsearch-db:search.ftsearch.sort.by.time')}
                                    		<i id ="timeSort" style ="width:15px;height: 15px;"></i>
                                    	</span>
                                    	
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>

					<!-- 搜索纠正：你是不是想找 -->
	                <div id="search_corrected" style="color:#000;font-size:14px;font-weight:bold;text-align: left;padding:16px 0px 0px 0px;display:none;background-color: #fff">
	                	${lfn:message('sys-ftsearch-db:search.ftsearch.mean')}
	                	<span id="corrected_checkWord">
	                	</span>
	                    <!-- span>
	                    	${lfn:message('sys-ftsearch-db:search.ftsearch.checkResult')}
	                        <span id="show_checkWord" style="color: red"></span>
	                    </span>
	                    <span>
	                        ${lfn:message('sys-ftsearch-db:search.ftsearch.still')}
	                    </span>
	                    <a id="search_queryString" href="javascript:void(0);" style="color: red;font-size:14px;font-weight:bold;"></a-->
	                </div>
					<!-- 搜索纠正结束 -->

                    <!-- 人员名片 Starts -->
                    <div id="search_result_personCard">
                    
                    </div>
                    <!-- 人员名片 Ends --> 
                    
                    <div class="search_res_content">
                        <div class="lui_common_list_main_body">
                            <div class="lui_common_list_4_centerL">
                                <div class="lui_common_list_4_centerR">
                                    <div style="padding-bottom: 0px;">
                                    	<div id="maxLengthTipDiv" style="display:none;font-weight: bold;">${lfn:message('sys-ftsearch-db:ftsearch.max.length.of.search.word.info')} }</div>
                                        <!--数据内容 摘要视图 Begin-->
                                        <div id="searchResult" class="lui_common_view_summary_box">
                                            
                                        </div>
                                        
                                        <div id="search_none_div" style="display:none;">
						                    <ul class="search_none">
						                        <li style="font-size: 16px;">
						                            <h3 style="font-weight: bold;">
						                            	${lfn:message('sys-ftsearch-db:sysFtsearchDb.sorry')}
						                                <span id="search_none_span" style="color: red;word-wrap: break-word;"></span>
						                                ${lfn:message('sys-ftsearch-db:sysFtsearchDb.about')}
						                            </h3>
						                        </li>
						                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.advice')}</li>
						                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.checkWrong')}</li>
						                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.deleteSome')}</li>
						                    </ul>
                                        </div>
                                         <!-- 相关搜索 Begin -->
						                <div id="relevant_search" style="display: none;">
						                	<!-- 相关搜索 -->
						                    <div style="font-size: 18px;color: #333333;letter-spacing: 0;margin-bottom: 10px">${lfn:message('sys-ftsearch-db:search.ftsearch.relate.word')}</div>
						                    <ul id="relevantwordList">
						
						                    </ul>
						                </div>
						                <!-- 相关搜索 End -->
                                        <!--数据内容 摘要视图 End-->
                                        <!--分页控件3 Begin-->
                                        <div class="lui_common_pageNav_box">
                                            <div class="lui_common_pageNav_3_header_left">
                                                <div class="lui_common_pageNav_3_header_right">
                                                    <div class="lui_common_pageNav_3_header_centre"></div>
                                                </div>
                                            </div>
                                            <div class="lui_common_pageNav_3_content_left">
                                                <div class="lui_common_pageNav_3_content_right">
                                                    <div class="lui_common_pageNav_3_content_centre">
                                                        <!--内容样式 Begin-->
                                                        <ul class="lui_common_pageNav_3_contentBox clrfix">
                                                            <li id="page_pre_a_p" style="display:none; class="mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                        	<!-- 上一页 -->
                                                                            <a id="page_pre_a" style="display:none;margin-right: 10px;" href="javascript:void(0)" onclick="commitSearchByPrePage();">${lfn:message('sys-ftsearch-db:sysFtsearchDb.prePage')}</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <div id="page_first_a_p" style="display:none;" class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <a id="page_first_a" style="display:none;" href="javascript:void(0)" onclick="commitSearchByPageBtn();">1</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li id="page_left_span_p" style="display:none;" class="ml10 mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <span id="page_left_span" style="display:none;">...</span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <a id="page_1_a" style="display:none;" href="javascript:void(0)" onclick="commitSearchByPageBtn();">2</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <a id="page_2_a" style="display:none;" href="javascript:void(0)" onclick="commitSearchByPageBtn();">3</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <a id="page_3_a" style="display:none;" href="javascript:void(0)" onclick="commitSearchByPageBtn();">4</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <a id="page_4_a" style="display:none;" href="javascript:void(0)" onclick="commitSearchByPageBtn();">5</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li>
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <a id="page_5_a" style="display:none;" href="javascript:void(0)" onclick="commitSearchByPageBtn();">6</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li class="ml10 mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <span id="page_right_span" style="display:none;">...</span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li class="mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                            <a id="page_last_a" style="display:none;" href="javascript:void(0)" onclick="commitSearchByPageBtn();">7</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li class="mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                        	<!-- 下一页 -->
                                                                            <a id="page_next_a" style="display:none;" href="javascript:void(0)" onclick="commitSearchByNextPage();">${lfn:message('sys-ftsearch-db:sysFtsearchDb.nextPage')}</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li class="mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                        	<!-- 共X页 -->
																			${lfn:message('sys-ftsearch-db:search.ftsearch.total')}<span id="page_total_span">0</span>${lfn:message('sys-ftsearch-db:search.ftsearch.page')}
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li class="mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                        	<!-- 到X页 -->
                                                                            <span>
                                                                            	${lfn:message('sys-ftsearch-db:search.ftsearch.toCreateTime')}
                                                                            	<input id="page_select_input" class="pagination" type="text" onkeyup="commitSearchByPageSelectInput();"/>
                                                                            	${lfn:message('sys-ftsearch-db:search.ftsearch.page')}
                                                                            </span>
																		</div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li class="mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                        	<!-- 显示X条 -->
                                                                        	<span>
                                                                        		${lfn:message('sys-ftsearch-db:search.ftsearch.show')}
                                                                        		<input id="page_show_input" class="pagination" type="text" onkeyup="commitSearchByPageShowInput();" />
                                                                        		${lfn:message('sys-ftsearch-db:search.ftsearch.item')}
                                                                       		</span>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <li class="mr10">
                                                                <div class="lui_common_pageNav_3_c_itemL">
                                                                    <div class="lui_common_pageNav_3_c_itemR">
                                                                        <div class="lui_common_pageNav_3_c_itemC">
                                                                        	<a id="page_go_btn" class="btn" href="javascript:void(0)" title="Go" onclick="commitSearchByGo();">Go</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                        <!--内容样式 End-->
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="lui_common_pageNav_3_foot_left">
                                                <div class="lui_common_pageNav_3_foot_right">
                                                    <div class="lui_common_pageNav_3_foot_centre"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <!--分页控件3 End-->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--列表4 End-->
                </div>
                <!-- 搜索主体 End -->
            </div>
				<!-- 左侧 搜索结果 结束 -->
				<div id="search_box_right" style="width:28%;margin-left:2%;float:right;position: relative;display: ${existHisearch?'block': 'none'}" class="clrfix">
				<c:if test="${existHisearch}">
					<jsp:include page="/third/hisearch/third_hisearch_search/import/search.jsp"/>
				</c:if>
				<!-- 右侧 相关信息 开始 -->
				<div id="search_m_bodyR" class="search_m_bodyR" style = "display:none;">
					<!-- 关联人员 开始 -->
					<div style="font-size: 14px;color: #333333;line-height: 40px;border-bottom: 1px solid #E8E8E8;margin: 0 13px">${lfn:message('sys-ftsearch-db:search.ftsearch.relate.person')}</div>
					<div class="search_aboutPerson" style="margin-top: 2px">
						<img id="relevance_person_wait" src="styles/images/ajax.gif" style="display:none;" />
						<ul id="relevance_person" >

						</ul>
					</div>
					<!-- 关联人员 结束 -->
				</div>
				<!-- 右侧 相关信息 结束 -->
				</div>
        </div>
        <!-- 搜索筛选 End -->

		
        <c:if test="${JsParam._showBottomSearch != 'false' }">
	        <!-- 搜索条 Begin -->
	        <div class="search_module_bar">
	            <div class="innerbar">
	                <div class="search_bar">
	                    <input id="bottomKeyword" class="input_bottom" type="text" onkeydown="if (event.keyCode == 13 && this.value !='') commitSearchByBottomKeywordInput();" />
	                    <!-- 搜索 -->
	                    <a class="input_btn_bottom"  style="height:34px;" href="javascript:void(0)" onclick="commitSearchByBottomKeywordInput();">${lfn:message('sys-ftsearch-db:ftsearch.advanced.button.search')}</a>
	                </div>
	                <div class="btn_group">
	                	<!-- 在结果中搜索 -->
	                    <a class="btn" href="javascript:void(0)" onclick="commitSearchByResult();">${lfn:message('sys-ftsearch-db:search.ftsearch.search.on.result')}</a>
	                </div>
	            </div>
	        </div>
	        <!-- 搜索条 End -->
        </c:if>
        
        <c:if test="${JsParam._showSearchRange != 'false' }">
	        <!-- 搜索范围 开始 -->
	        <div class="search_rangeWrapper">
	            <h3 class="title">
	            	<!-- 搜索范围 -->
	            	<a id="search_range_a" style="cursor:pointer;" onclick="modelSelect();" >${lfn:message('sys-ftsearch-db:search.ftsearch.search.range')}</a>
	            </h3>
	            
	            <table id="search_range_table" style="border: 1px solid #cfcfcf;" width="100%">
	            	<tbody class="content">
	            		<tr>
	            			
		           			<td align="center" width="12%" class="content" style="font-size: 12px;font-weight: bold" >
			           			<c:if test="${empty sysNameList || sysNameList==null}">
			           				<label alt="selected">
			                			${lfn:message('sys-ftsearch-db:search.ftsearch.select')}：
			                		</label>
			                		<label style="cursor: pointer;display:none;" alt="Select All">
			                			<!-- 全选/全不选 -->
			                			<input id="model_select_all" onclick="commitSearchByModelNameAll();" style="margin-right: 3px; vertical-align: middle;" type="checkbox" />
			                			${lfn:message('sys-ftsearch-db:search.ftsearch.select')}：
			                		</label>
			           			</c:if>
				                <c:if test="${not empty sysNameList && sysNameList!=null}">
				                	<label alt="selected">
			                			${lfn:message('sys-ftsearch-db:search.ftsearch.local')}：
			                		</label>
			                		<label style="cursor: pointer;display:none;" alt="Select All">
			                			<input id="model_select_all" onclick="commitSearchByModelNameAll();" style="margin-right: 3px; vertical-align: middle;" type="checkbox" />${lfn:message('sys-ftsearch-db:search.ftsearch.local')}：
			                		</label>
				                </c:if>
		           			</td>
	            			
	            			<td align="left" width="88%" class="content" >
					            <ul id="model_view" class="content" style="display:block;">
									
					            </ul>
					            <ul id="model_select" class="content" style="display:none;">
					            	
					            </ul>
	            			</td>
	            		</tr>
	            		
			            <c:forEach items="${otherSysDesign}" var="sysDesigns" varStatus="status">
			            <c:set var="sysNamesBySelectAll" value="sysNamesBySelectAll_${status.index}"></c:set>
			            <tr style="border: 1px solid #cfcfcf;" >
			            	<td align="center" width="12%" class="content" style="font-size: 12px;font-weight: bold" >
			                    <c:forEach items="${sysNameList}" var="sysNames" varStatus="status2">
			                        <c:if test="${status.index==status2.index}">
			                        	<label alt="selected">
			                				 ${sysNames }：
			                			</label>
			                        	<label style="cursor: pointer;display:none;" alt="Select All">
				                            <input onclick="commitSearchByOtherModelNameAll('${sysNamesBySelectAll}');" style="margin-right: 3px; vertical-align: middle;" type="checkbox" />
				                            ${sysNames }：
			                            </label>
			                        </c:if>
			                    </c:forEach>
			            	</td>
			            	
			            	<td align="left" width="88%" class="content" >
								<ul id="${sysNamesBySelectAll}_view" class="content" style="display:block;">
				                    <c:forEach items="${sysDesigns}" var="sysDesign" varStatus="status">
				                        <c:if test="${sysDesign['flag']==true}">
				                            <li> ${sysDesign['title']}</li>
				                        </c:if> 
				                    </c:forEach>
					            </ul>
					            <ul id="${sysNamesBySelectAll}" class="content" style="display:none;">
				                    <c:forEach items="${sysDesigns}" var="sysDesign" varStatus="status">
				                        <li>
				                        	<label for="model_${sysDesign['modelName']}" style="cursor: pointer;">
				                        		<input alt="modelCheck" onclick="commitSearchByOutModel(${fn:length(otherSysDesign)});" style="margin-right: 3px; vertical-align: middle;" id="model_${sysDesign['modelName']}" type="checkbox" 
				                                    <c:if test="${sysDesign['flag']==true}">
				                                        checked
				                                    </c:if>
				                        		 />
				                        		${sysDesign['title']}
				                     		</label>
				                     	</li>
				                    </c:forEach>
					            </ul>
				            </td>
				        </tr>
			            </c:forEach>
	
	            	</tbody>
	            </table> 
	            
	        </div>
	        <!-- 搜索范围 结束 -->
        </c:if>
    </div>
    <!-- 搜索报错 -->
	<div id="EsErrorDiv" style="display:none;" id="moreErrInfo1"></div>
	<%@ include file="/sys/ftsearch/search3_js.jsp"%>
</body>
</html>
