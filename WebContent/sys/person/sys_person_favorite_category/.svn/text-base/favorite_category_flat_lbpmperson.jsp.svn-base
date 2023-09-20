<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
    Com_IncludeFile("commonly_use.css", "${LUI_ContextPath}/sys/person/sys_person_favorite_category/resource/css/", "css", true);
    Com_IncludeFile("favorite_category_flat_ui.js", "${LUI_ContextPath}/sys/person/sys_person_favorite_category/resource/js/", "js", true);
</script>
<div id="favouriteCateDiv">
    <div data-lui-type="${LUI_ContextPath}/sys/ui/js/summary/summaryDataView!SimpleSummaryDataView">
        <ui:source type="AjaxJson">
            {"url":"/sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=favoriteWithCate&getAddUrl=true&authAll=true"}
        </ui:source>
        <ui:render type="Template">
            <c:import url="/sys/person/sys_person_favorite_category/tmpl/commonly_use_lbpmperson.jsp" charEncoding="UTF-8"></c:import>
        </ui:render>
        <script type="text/config">
            {
                id:"favouriteCate",
                button:{
                    className:"lui_summary_item_btn_del",
                    click:window.delOneFavorite
                }
            }
        </script>
    </div>
</div>
<input type="hidden" name="fdCategoryIds" value="">
<input type="hidden" name="fdCategoryNames" value="">
<script>
    window.favorite_contextPath = '${LUI_ContextPath}';
</script>

