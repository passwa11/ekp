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
	<link href="${KMSS_Parameter_ContextPath}sys/ftsearch/styles/McommonPerson.css" rel="stylesheet" type="text/css" />
	<link href="${KMSS_Parameter_ContextPath}sys/ftsearch/styles/resetPerson.css" rel="stylesheet" type="text/css" />
	<link href="${KMSS_Parameter_ContextPath}sys/ftsearch/styles/Pcommon.css" rel="stylesheet" type="text/css" />
	<link href="${KMSS_Parameter_ContextPath}sys/ftsearch/styles/searchPerson.css" rel="stylesheet" type="text/css" />
	<link href="${KMSS_Parameter_ContextPath}sys/ftsearch/styles/jquery-ui.min.css" rel="stylesheet" type="text/css" />
	<link href="${KMSS_Parameter_ContextPath}sys/ftsearch/styles/multiple-select.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/scripts/shBrushBash.js"></script>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/styles/shCore.css"/>
	<link type="text/css" rel="stylesheet" href="${KMSS_Parameter_StylePath}promptBox/syntaxhighlighter/styles/shThemeDefault.css"/>
    <link charset="utf-8" rel="stylesheet" href="<c:url value="/sys/ui/extend/theme/default/style/profile.css"/>">
	
    
	<style type="text/css"> 
	.lui_profile_listview_l_icon {
	    display: inline-block;
	    width: 50px;
	    height: 50px;
	    background-repeat: no-repeat;
	    background-position: center;
	}
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
		background:url(${KMSS_Parameter_ContextPath}sys/ftsearch/styles/images/search_icon_check.png) no-repeat 50%;
		display: block;
		position: absolute;
		left: -12px;
		top:50%;
		margin-top: -6px;
	}
	.search_multiple_selection .checkbox.checked{
		background-image: url(${KMSS_Parameter_ContextPath}sys/ftsearch/styles/images/search_icon_checked.png);
	}
	.search_none {
	    background: url(${KMSS_Parameter_ContextPath}sys/ftsearch/styles/images/search_none.gif) no-repeat 23px 33px;
	    background-image: url(${KMSS_Parameter_ContextPath}sys/ftsearch/styles/images/search_none.gif);
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
	}
	.search_none li{
		padding: 5px 0;
		color: #000000;
	}
	#search_range_a {
	    background: url(${KMSS_Parameter_ContextPath}sys/ftsearch/styles/images/arrow_down.gif) no-repeat right center;
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
	.search_m_body{
		padding: 0;
	}
	.model_content{
	}    
	</style>
</head>
<%-- 是否隐藏主搜索（MK搜索引入时需要隐藏主搜索） --%>
<c:set var="display" value=""/>
<c:if test="${'self' eq mktarget or 'self' eq param.mktarget}">
<c:set var="display" value="display: none;"/>
</c:if>
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
                    <a class="input_btn" id="input_btnTop" href="javascript:void(0)" onclick="commitSearchByTopKeywordInput();">${lfn:message('sys-ftsearch-db:ftsearch.advanced.button.search')}</a>
                </div>
  			</div>
  			<div class="search_main_linkToHome" onclick="location.href='${KMSS_Parameter_ContextPath}'">
           
            </div>
        </div>
    </div>
  
    <!-- 主搜索 End -->
	<div class="search_bar_tab_content">
		<div class="search_bar_tab">
			<label id="searchAll" onclick="change(this)">
				<p>
					<span>${lfn:message('sys-ftsearch:AllSearch')}</span>
				</p>
			</label> <label id="searchPeople" onclick="change(this)">
				<p>
					<span>${lfn:message('sys-ftsearch:PersonSearch')}</span>
				</p>
			</label> <label id="searchModel" onclick="change(this)" class="current">
				<p>
					<span>${lfn:message('sys-ftsearch:ModelSearch')}</span>
				</p>
			</label>
		</div>
	</div>
	<!-- 搜索主体 Begin -->
    <div class="main_content">
        <!-- 搜索数据 开始 -->
        <div class="search_result">
        	<!-- 搜索到约X项结果 -->
            <span>${lfn:message('sys-ftsearch-db:search.ftsearch.probably')}
		  				<em id="resultCount">0</em>
            ${lfn:message('sys-ftsearch-db:search.ftsearch.itemResult')}</span>
            <!-- 用时X秒 -->
			<span>${lfn:message('sys-ftsearch-db:search.ftsearch.search.userTime')}
		  				<em id="searchUserTime">0</em>
				${lfn:message('sys-ftsearch-db:search.ftsearch.search.minute')}
			</span>
        </div>
        <!-- 搜索数据 结束 -->
    </div>
    
    <div class="search_m_body">
        <div class="main_content model_content" style="border: 0;margin: 0;padding: 0">
            <!-- 右侧 相关信息 开始 -->
            <!-- 右侧 相关信息 结束 -->
            
            <!-- 左侧 搜索结果 开始 -->
                
                <!-- 常用标签 Begin-->
                <div class="lui_common_list_box">

					<!-- 搜索纠正：你是不是想找 -->

                    <!-- 模块 Starts -->
                     <div id="search_result_model">
                     
                     </div>
                    <!-- 模块 Ends --> 
                    <div class="search_res_content">
                        <div class="lui_common_list_main_body">
                            <div class="lui_common_list_4_centerL">
                                <div class="lui_common_list_4_centerR">
                                    <div style="padding-bottom: 0px;">
                                        <!--数据内容 摘要视图 Begin-->
                                        <div id="searchResult" class="lui_common_view_summary_box">
                                            
                                        </div>
                                        
                                         <div id="search_none_div_model" style="display:none;">
						                    <ul class="search_none">
						                        <li style="font-size: 16px;">
						                            <h3 style="font-weight: bold;">
						                            	${lfn:message('sys-ftsearch-db:sysFtsearchDb.sorry')}
						                                <span id="search_none_span_model" style="color: red"></span>
						                                ${lfn:message('sys-ftsearch-db:sysFtsearchDb.aboutModel')}
						                            </h3>
						                        </li>
						                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.advice')}</li>
						                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.checkWrong')}</li>
						                        <li>${lfn:message('sys-ftsearch-db:sysFtsearchDb.deleteSome')}</li>
						                    </ul>
                                        </div>
                                        
                                        <!--数据内容 摘要视图 End-->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--列表4 End-->
                </div>
                <!-- 搜索主体 End -->
            <!-- 左侧 搜索结果 结束 -->
        </div>
        <!-- 搜索筛选 End -->

        <!-- 搜索条 Begin -->
        <!-- 搜索条 End -->
        
    </div>
    
    <!-- 搜索报错 -->
	<div id="EsErrorDiv" style="display:none;" id="moreErrInfo1""></div>
</body>
<%@ include file="/sys/ftsearch/search3_js.jsp"%>
</html>
