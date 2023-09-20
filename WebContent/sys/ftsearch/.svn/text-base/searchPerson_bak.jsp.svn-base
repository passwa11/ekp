<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.zone.model.SysZonePrivateConfig" %>
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
	<%@ include file="/sys/zone/sys_zone_personInfo/sysZonePersonContact_include.jsp"%>
    
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
	    margin: 10px auto;
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
    .staff_sex_m{
    background: url(${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/images/zone/boy.png);
    background-repeat: no-repeat;
	width: 16px;
	height: 16px;
    }
    .staff_sex_f{
    background: url(${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/images/zone/girl.png);
    background-repeat: no-repeat;
	width: 16px;
	height: 16px;
    }
	</style>
</head>
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
    <div class="search_main_wrapper">
        <div class="search_main_box clrfix">
            <div class="search_main_logo"></div>
            <div class="search_main_content">
			 	
                <div class="search_bar">
		            <div class="search_bar_tab">
		            	<label id="searchAll" onclick="change(this)" >
					 			<p>
					 				<span>${lfn:message('sys-ftsearch:AllSearch')}</span>
					 			</p>
					 	</label>
					 	 <label id="searchPeople" onclick="change(this)"  class="current">
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
                    <input id="topKeyword" maxlength="300" class="input_txt" type="text" onkeydown="if (event.keyCode == 13 && this.value !='') commitSearchByTopKeywordInput();"/>
                    <!-- 搜索 -->
                    <a class="input_btn" id="input_btnTop" href="javascript:void(0)" onclick="commitSearchByTopKeywordInput();">${lfn:message('sys-ftsearch-db:ftsearch.advanced.button.search')}</a>
                
                </div>

            </div>
            <div class="search_main_linkToHome">
            	<!-- 首页 -->
                <a href="${KMSS_Parameter_ContextPath}">${lfn:message('sys-ftsearch-db:search.ftsearch.home')}</a>
            </div>
        </div>
    </div>
    <!-- 主搜索 End -->
    
    <!-- 搜索主体 Begin -->
    <div class="main_content">
        <!-- 搜索数据 开始 -->
        <div class="search_result">
        	<!-- 搜索到约X项结果 -->
            <span>${lfn:message('sys-ftsearch-db:search.ftsearch.probably')}
	            <c:choose>  
		   			<c:when test="${count>0}"> 
		   				<em id="resultCount">${count}</em>  
		  			</c:when>  
		  			<c:otherwise>
		  				<em id="resultCount">0</em>
		  			</c:otherwise>  
				</c:choose>  
            ${lfn:message('sys-ftsearch-db:search.ftsearch.itemResult')}</span>
            <!-- 用时X秒 -->
			<span>${lfn:message('sys-ftsearch-db:search.ftsearch.search.userTime')}
				 <c:choose>  
		   			<c:when test="${time>0}"> 
		   				<em id="searchUserTime">${time}</em>  
		  			</c:when>  
		  			<c:otherwise>
		  				<em id="searchUserTime">0</em>
		  			</c:otherwise>  
				</c:choose>  
				${lfn:message('sys-ftsearch-db:search.ftsearch.search.minute')}
			</span>
        </div>
        <!-- 搜索数据 结束 -->
    </div>
    
    <div class="search_m_body search_person_body">
        <div class="main_content">
            <div class="search_person_wrap">
                    <!-- 人员名片 Starts -->
					<!-- 员工卡片 Starts -->
					<c:forEach var="sysOrgPerson" items="${queryPage.list}">
					<div class="lui_hr_staff_pop_frame sys_zone_card_frame"  id="sys_zone_card_frame">
	<ui:dataview>
		<ui:source type="AjaxJson">
			{url:"/sys/person/sys_person_zone/sysPersonZone.do?method=info&fdId=${sysOrgPerson.fdId}"}
		</ui:source>
		<ui:render type="Template">
		if(data.fdIsAvailable != false){
			{$
				<div <%=new SysZonePrivateConfig().hideQrCode()?"style='display: none;'":"" %> class="staff_dropbox_toggle" data-url="{%data.fdName%}#{%data.fdPostName%}#{%data.fdMobileNo%}#{%data.fdEmail%}#{%data.fdId%}#{%data.fdDeptName%}#{%data.fdOrgName%}#{%data.fdWorkPhone%}"  onmouseenter="showQrCode(this);" onmouseleave="hideQrCode(this);">
					<div class="iconbox">
						<span class="icon_QRcode"></span>
						<span class="icon_PC"></span>
					</div>
				</div>
			 $}
		 }
		
			{$
				<div class="sys_zone_card_content sys_zone_card_detail_info">
					<div class="sys_zone_card_photo">
						<div class="card_photo">
			 $}
		 
				if(data.isFullPath == true) {
		            {$
		            <a onclick="openElement('${KMSS_Parameter_ContextPath}sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=${sysOrgPerson.fdId}')" href="#">
		                <img src="{%data.imgUrl%}">
		             </a>   
		            $}
		            }else{
		            {$
		            <a onclick="openElement('${KMSS_Parameter_ContextPath}sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=${sysOrgPerson.fdId}')" href="#">
		                <img src="${LUI_ContextPath}{%data.imgUrl%}">
		             </a>   
		            $}
	            }
	            if(data.fdSex && data.fdSex != ""){
	             {$
						<span class="staff_sex sex_{%data.fdSex%}">
						<i class="staff_sex_{%data.fdSex%} lui_icon_s" title="{%data.fdSexText%}"></i>
						</span>
				 $}
	          	}else{  
	             {$
						<span class="staff_sex sex_M">
						<i class="staff_sex_M lui_icon_s" title="{%data.fdSexText%}"></i>
						</span>
				 $}
				}			
				 {$			
					</div>
					<h3 class="staff_name"><a onclick="openElement('${KMSS_Parameter_ContextPath}sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=${sysOrgPerson.fdId}')" href="" title="${sysOrgPerson.fdName}">${sysOrgPerson.fdName}</a> </h3>
				 $}
		             {$ <div data-lui-mark="follow_btn">$}
		             {$ 					
		             						<c:if test="${map[sysOrgPerson.fdId] == '0'}">
												<a class="sys_zone_btn_focus icon_focusAdd" attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" is-follow-person="true" fans-action-type="unfollowed" fans-action-id="${sysOrgPerson.fdId}" href="javascript:void(0);"onclick="_layer_zone_follow_action(this);"><span><i></i><span><bean:message bundle='sys-zone' key='sysZonePerson.cared1'/></span></span> </a>
											</c:if>
											<c:if test="${map[sysOrgPerson.fdId] == '1'}">
												<a class="sys_zone_btn_focus icon_focused">  <span><i></i><span><bean:message bundle='sys-zone' key='sysZonePerson.cancelCared1'/></span></span><em attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"   fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"   is-follow-person="true" fans-action-type="followed" fans-action-id="${sysOrgPerson.fdId}"  href="javascript:void(0);" onclick="_layer_zone_follow_action(this);"><bean:message bundle='sys-zone' key='sysZonePerson.cancelCared'/></em></a>
											</c:if>
											<c:if test="${map[sysOrgPerson.fdId] == '2'}">
												<a class="sys_zone_btn_focus icon_unfocus" attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"   fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" is-follow-person="true" fans-action-type="unfollowed" fans-action-id="${sysOrgPerson.fdId}"  href="javascript:void(0);" onclick="_layer_zone_follow_action(this);"> <span><i></i><span><bean:message bundle='sys-zone' key='sysZonePerson.cared1'/></span></span></a>
											</c:if>
											<c:if test="${map[sysOrgPerson.fdId] == '3'}">
												<a class="sys_zone_btn_focus icon_eachFocus"><span><i></i><span><bean:message bundle='sys-zone' key='sysZonePerson.follow.each'/></span></span><em attent-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo"  fans-model-name="com.landray.kmss.sys.zone.model.SysZonePersonInfo" is-follow-person="true" fans-action-type="followed" fans-action-id="${sysOrgPerson.fdId}" href="javascript:void(0);" onclick="_layer_zone_follow_action(this);"><bean:message bundle='sys-zone' key='sysZonePerson.cancelCared'/></em></a>
											</c:if>
											<c:if test="${map[sysOrgPerson.fdId] == '9'}">
											</c:if>	
			          $}
	          		 {$</div>$}
			 {$</div>$}
			{$	
				<div class="sys_zone_card_info">
					<ul class="sys_zone_card_info_list">
			$}
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.dept')}</em>	
			$}			
				if(data.fdIsAvailable != false && (data.isDepInfoPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdDeptName%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
							
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.mobilePhone')}</em>	
			$}			
				if(data.fdIsAvailable != false && (data.isContactPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdMobileNo%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
			
						{$
			<li>
				<em>${lfn:message('sys-organization:sysOrgPerson.fdShortNo')}</em>	
			$}
			if(data.fdIsAvailable != false && (data.isContactPrivate != true)){
			{$			
						<span>
							<c:out value="{%data.fdShortNo%}"></c:out>
						</span> 
			$}	
			}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}				
			{$
			</li>
			$}
			
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.email')}</em>	
			$}			
				if(data.fdIsAvailable != false && (data.isContactPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdEmail%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
			{$
			<li>
				<em>${lfn:message('sys-zone:sysZonePerson.post')}</em>	
			$}			
				if(data.fdIsAvailable != false && (data.isDepInfoPrivate != true)){
					{$
						<span>
							<c:out value="{%data.fdPostName%}"></c:out>
						</span>
					$}
				}else{
					{$
						<span class = "sys_zone_card_status_undisclosed">
							[ ${lfn:message('sys-zone:sysZonePerson.undisclosed')} ]
						</span>
					$}
				}	
								
			{$
			</li>
			$}
			
			
			{$
					</ul>
					<a target="_blank"  href="${LUI_ContextPath }/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=${sysOrgPerson.fdId}"  class="sys_zone_card_more" >${lfn:message('sys-zone:sysZonePerson.more')}</a>
				</div>
			$}	
			{$</div>$}
			{$
				<div class="sys_zone_card_code" id="${sysOrgPerson.fdId}">
					<div id="personQrCode_${sysOrgPerson.fdId}" class="sys_zone_card_code_img">
					</div>
					<p>${lfn:message('sys-zone:sysZonePerson.saveto.addressBook')}</p>
				</div>
			$}	
			{$
				<div class="sys_zone_card_footer">
			$}	
			if(data.fdIsAvailable == false) {
			   {$
			   	<p class="tips">${lfn:message('sys-zone:sysZonePerson.dimission')}</p>
			   $}	
			}else{
			
				{$
					<div id="contactBar_${sysOrgPerson.fdId}"  data-person-role="contact"
						class="contactBar"
						 data-person-param="&fdId=${sysOrgPerson.fdId}&fdName={%data.fdName%}&fdLoginName={%data.fdLoginName%}&fdEmail={%data.fdEmail%}&fdMobileNo={%data.fdMobileNo%}">	
					</div>
				$}	
			}	
			{$
				</div>
			$}
		</ui:render>
		<ui:event event="load">
			setTimeout(function(){
				var datas = [];
				var personParam = $("#contactBar_${sysOrgPerson.fdId}").attr("data-person-param");
				datas.push({
					elementId :$("#contactBar_${sysOrgPerson.fdId}").attr("id"),
					personId: Com_GetUrlParameter(personParam, "fdId"),
					personName:Com_GetUrlParameter(personParam, "fdName"),
					loginName :Com_GetUrlParameter(personParam, "fdLoginName"),
					email:Com_GetUrlParameter(personParam, "fdEmail"),
					mobileNo:Com_GetUrlParameter(personParam, "fdMobileNo"),
					isSelf : ("${KMSS_Parameter_CurrentUserId}" == Com_GetUrlParameter(personParam, "fdId")),
					isCard:true
				});
				onRender(datas); 
			},100);
		</ui:event>
	</ui:dataview>
</div>
					</c:forEach>
					</div>
					</div>
					
                     <!-- 人员名片 Ends --> 
                      <list:paging pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }"  currentPage="${queryPage.pageno }" id="pageChange"></list:paging>
                    <div class="search_res_content">
                        <div class="lui_common_list_main_body">
                            <div class="lui_common_list_4_centerL">
                                <div class="lui_common_list_4_centerR">
                                    <div style="padding-bottom: 0px;">
                                        <!--数据内容 摘要视图 Begin-->
                                        <div id="searchResult" class="lui_common_view_summary_box">
                                            
                                        </div>
                                        
                                        
                                         <div id="search_none_div_people" style="display:none;">
						                    <ul class="search_none">
						                        <li style="font-size: 16px;">
						                            <h3 style="font-weight: bold;">
						                            	${lfn:message('sys-ftsearch-db:sysFtsearchDb.sorry')}
						                                <span id="search_none_span_people" style="color: red"></span>
						                                ${lfn:message('sys-ftsearch-db:sysFtsearchDb.aboutPerson')}
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
                <!-- 搜索主体 End -->
            <!-- 左侧 搜索结果 结束 -->
        <!-- 搜索筛选 End -->

        <!-- 搜索条 Begin -->
        <!-- 搜索条 End -->
        
    </div>
    
    <!-- 搜索报错 -->
	<div id="EsErrorDiv" style="display:none;" id="moreErrInfo1""></div>
</body>
<%@ include file="/sys/ftsearch/search3_js.jsp"%>
</html>
