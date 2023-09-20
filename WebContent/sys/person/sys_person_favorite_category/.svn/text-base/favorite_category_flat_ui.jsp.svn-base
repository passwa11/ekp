<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
    Com_IncludeFile("commonly_use.css", "${LUI_ContextPath}/sys/person/sys_person_favorite_category/resource/css/", "css", true);
    Com_IncludeFile("favorite_category_flat_ui.js", "${LUI_ContextPath}/sys/person/sys_person_favorite_category/resource/js/", "js", true);
</script>
<div id="favouriteCateDiv">
    <div data-lui-type="${LUI_ContextPath}/sys/ui/js/summary/summaryDataView!SortableSummaryDataView">
        <ui:source type="AjaxJson">
            {"url":"/sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=favoriteWithCate&modelName=${JsParam.modelName}&key=${JsParam.key }&getAddUrl=true"}
        </ui:source>
        <ui:render type="Template">
            <c:import url="/sys/person/sys_person_favorite_category/tmpl/commonly_use.jsp" charEncoding="UTF-8"></c:import>
        </ui:render>
        <script type="text/config">
            {
                id:"favouriteCate",
                addFavouriteFun:'setFavouriteCategory',
                openCreateFun:'openCreate',
                button:{
                    className:"lui_summary_item_btn_del",
                    click:window.delOneFavorite
                },
                sortable:{
                    stopCallback:function(){
                        window.moveUpdate();
                    }
                }
            }
        </script>
    </div>
</div>
<input type="hidden" name="fdCategoryIds" value="">
<input type="hidden" name="fdCategoryNames" value="">
<script>
    window.favorite_modelName = '${JsParam.modelName}';
    window.favorite_addUrl = '${addUrl}';
    window.favorite_isSimpleCategory = '${JsParam.isSimpleCategory}';
    window.favorite_cateType = '${JsParam.cateType}';
    window.favorite_contextPath = '${LUI_ContextPath}';
    window.favorite_key = '${JsParam.key}';
</script>

