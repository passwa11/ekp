<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<style>
    /*禁用*/
 	.hr_nav_list .hr_nav_list_noimport .hr_nav_icon,
	.hr_nav_list li:hover .hr_nav_list_noimport .hr_nav_icon {
	    background-color: #eeeeee;
	    color: #999999;
	    cursor: not-allowed;
	}
	
	.hr_nav_list .hr_nav_list_noimport .hr_nav_title,
	.hr_nav_list li:hover .hr_nav_list_noimport .hr_nav_title {
	    color: #999999;
	    cursor: not-allowed;
	}
	

</style>
<div>
    <div class="hr_nav_container lui_hr_main_content" style="padding-left:0px;padding-right:0px;">
        <div class="hr_nav_head">
            <span class="hr_nav_title">${lfn:message('hr-staff:porlet.module.nav')}</span>
            <!-- 搜索 -->
            <div class="hr_nav_search">
                <input name="hr_nav_search_name" type="text" placeholder="${lfn:message('hr-staff:porlet.employee.search.help')}"> 
                <span onclick="hrNavSearch()">${lfn:message('hr-staff:button.search')}</span>
            </div>
        </div>
        <div class="hr_nav_content">
            <ul class="hr_nav_list">
				<!--recruit -->
                <li>
                    <c:if test="${nav.recruit}">
                        <a href="${LUI_ContextPath}/hr/recruit/" target="_blank">
                    </c:if>
                    <c:if test="${!nav.recruit}">
                        <a href="javascript:;" target="_blank" class="hr_nav_list_noimport">
                    </c:if>
                    <span class="hr_nav_icon icon_nav_1"></span>
                    <span class="hr_nav_title">${lfn:message('hr-staff:porlet.module.nav.recruit')}</span>
                    </a>
                </li>
                <!-- ratify -->
                <li>
                    <c:if test="${nav.ratify}">
                        <a href="${LUI_ContextPath}/hr/ratify/" target="_blank">
                    </c:if>
                    <c:if test="${!nav.ratify}">
                        <a href="javascript:;" target="_blank" class="hr_nav_list_noimport">
                    </c:if>
                    <span class="hr_nav_icon icon_nav_2"></span>
                    <span class="hr_nav_title">${lfn:message('hr-staff:porlet.module.nav.ratify')}</span>
                    </a>
                </li>
                  <!-- staff -->
                <li>
                    <c:if test="${nav.staff}">
                        <a href="${LUI_ContextPath}/hr/staff/" target="_blank">
                    </c:if>
                    <c:if test="${!nav.staff}">
                        <a href="javascript:;" target="_blank" class="hr_nav_list_noimport">
                    </c:if>
                    <span class="hr_nav_icon icon_nav_3"></span>
                    <span class="hr_nav_title">${lfn:message('hr-staff:porlet.module.nav.staff')}</span>
                    </a>
                </li>
                <!-- px -->
                <li>
                    <c:if test="${nav.exam}">
                        <a href="${LUI_ContextPath}/kms/exam" target="_blank">
                    </c:if>
                    <c:if test="${!nav.exam}">
                        <a href="javascript:;" target="_blank" class="hr_nav_list_noimport">
                    </c:if>
                    <span class="hr_nav_icon icon_nav_4"></span>
                    <span class="hr_nav_title">${lfn:message('hr-staff:porlet.module.nav.exam')}</span>
                    </a>
                </li>
                  <!-- kq -->
                <li>
                    <c:if test="${nav.attend}"> 
                    	<a href="${LUI_ContextPath}/sys/attend/index.jsp?j_module=true" target="_blank">
                    </c:if>
                    <c:if test="${!nav.attend}">
                        <a href="javascript:;" target="_blank" class="hr_nav_list_noimport">
                    </c:if>
                    <span class="hr_nav_icon icon_nav_5"></span>
                    <span class="hr_nav_title">${lfn:message('hr-staff:porlet.module.nav.attend')}</span>
                    </a>
                </li>
                <!-- okr -->
                <li>
                    <c:if test="${nav.okr}">
                        <a href="${LUI_ContextPath}/hr/okr/" target="_blank">
                    </c:if>
                    <c:if test="${!nav.okr}">
                        <a href="javascript:;" target="_blank" class="hr_nav_list_noimport">
                    </c:if>
                    <span class="hr_nav_icon icon_nav_6"></span>
                    <span class="hr_nav_title">${lfn:message('hr-staff:porlet.module.nav.okr')}</span>
                    </a>
                </li>
                <!-- xc -->
                <li>
                    <c:if test="${nav.salary}">
                    	<a href="${LUI_ContextPath}/hr/salary/" target="_blank">
                    </c:if>
                    <c:if test="${!nav.salary}">
                        <a href="javascript:;" target="_blank" class="hr_nav_list_noimport">
                    </c:if>
                    <span class="hr_nav_icon icon_nav_7"></span>
                    <span class="hr_nav_title">${lfn:message('hr-staff:porlet.module.nav.xc')}</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>
<script>
function hrNavSearch(){
	var v= $("input[name='hr_nav_search_name']").val();
	var url = "${LUI_ContextPath}/hr/staff/#j_path=/informationIn&cri.q=_fdKey:"+v
	window.open(url);
	}
</script>