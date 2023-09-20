<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple">
    <%-- 右侧内容区域 --%>
    <template:replace name="body">
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/views/business/res/calendar.css?s_cache=${LUI_Cache}"/>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/relation/business/res/resPanel.css?s_cache=${LUI_Cache}"/>
        <%--var CRITERIA_UPDATE = cbase.CRITERIA_UPDATE;
            var CRITERIA_CHANGED = cbase.CRITERIA_CHANGED;
            var CRITERIA_SPA_CHANGED = cbase.CRITERIA_SPA_CHANGED;
            var CRITERION_CHANGED = cbase.CRITERION_CHANGED;
            var CRITERION_HASH_CHANGE = cbase.CRITERION_HASH_CHANGE;--%>
        <!-- 筛选器 -->
        <%@ include file="/sys/modeling/base/views/business/show/criteria.jsp" %>


        <div data-lui-type="sys/modeling/base/views/business/show/resPanel!ResPanel" style="display:none;"
             id="resPanelContent">
            <ui:source type="AjaxJson">
                { url : '/sys/modeling/main/business.do?method=indexData&modelId=${param.modelId}&businessId=${param.businessId}&type=resPanel'}
            </ui:source>
            <div data-lui-type="lui/view/render!Template">
                <script type="text/config">
                {src : '/sys/modeling/base/views/business/show/resPanelRender.html#'}

                </script>
            </div>
            <ui:event event="load" args="evt">
                if ($(window).width() < $(".res_calendar_header").width() ) {
                $(".res_calendar_rescontent").addClass("res_calendar_box_shadow");
                }else{
                $(".res_calendar_rescontent").removeClass("res_calendar_box_shadow");
                }

             var rebacktoday =  $.find(".rebacktoday");
                if(rebacktoday.length>0){
                var days =  $(".res_today").find("div").html();
                if (!isNaN(days)){
                var itemWidth =  $(".res_today").width();
                $('.res_calendar_rightContent').scrollLeft(itemWidth*days);
                }else {
                return;
                }
                }
            </ui:event>
        </div>
<script>
    // seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
    //     var criterions = null;
    //
    //     //改变搜索条件时，动态往查询参数里增加自定义参数
    //     topic.subscribe('criteria.changed', function (evt) {
    //         alert('criteria.changed')
    //         criterions = evt.criterions;
    //         if (evt && evt.criterions && evt.criterions.length > 0) {
    //             for (var i in criterions) {
    //                 //部门：包含子节点
    //                 let isCludeSubDeptName = 't1_' + criterions[i].key;
    //                 let t1 = $('input[name="' + isCludeSubDeptName + '"]');
    //                 if (t1.length > 0 && t1[0].checked && t1.attr('listview-creteria-type') === 'dept') {
    //                     evt.criterions.push({"key": isCludeSubDeptName, "value": [$(t1).val()]});
    //                     break;
    //                 }
    //             }
    //         }
    //         //未选部门时，勾选框隐藏
    //         $('input[listview-creteria-type="dept"]').each(function () {
    //             let isCheckDept = false;
    //             let deptName = $(this).attr('name').substring("t1_".length);
    //             for (var i in criterions) {
    //                 if(deptName === criterions[i].key) {
    //                     isCheckDept = true;
    //                 }
    //             }
    //             if(isCheckDept) {
    //                 $(this).closest('div').show();
    //             } else {
    //                 $(this)[0].checked = false;
    //                 $(this).closest('div').hide();
    //             }
    //         });
    //     });
    //
    //     $(function () {
    //         //为"部门：包含子节点"勾选框绑定改变时触发criteria条件改变事件
    //         $('input[listview-creteria-type="dept"]').on("change", function () {
    //             if (!criterions || criterions.length === 0)
    //                 return;
    //             //通过控件改变事件生成的criterions不包含自定义push进去的数据，此处删除后由subscribe去判断是否push
    //             var delIndex = -1;
    //             for (var i in criterions) {
    //                 let key = criterions[i].key;
    //                 if (key == $(this).attr('name')) {
    //                     delIndex = i;
    //                     break;
    //                 }
    //             }
    //             if (delIndex !== -1)
    //                 criterions.splice(delIndex, 1);
    //             topic.publish('criteria.changed', {criterions: criterions});
    //         });
    //     });
    // });
    //
    // function criterionIsIncludeSubDivInit(canMulti) {
    //     // 重新计算 包含子节点 复选框样式
    //     var $criterion = $('.criterion-is-include-sub');
    //     $criterion.css('margin-top', ($criterion.closest('.criterion').height() - 19) / 2);
    //     // 离右侧距离;
    //     if (canMulti === 'true')
    //         $criterion.css('margin-right', $criterion.closest('.criterion').find('.criterion-options').outerWidth());
    // }
</script>

    </template:replace>
</template:include>