<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<%--标签选择组件--%>
<div data-dojo-type="mui/listcategory/SwapCategoryList"
     data-dojo-mixins="km/calendar/mobile/resource/js/label/LabelCategoryListMixin"
     data-dojo-props="isMul:true,dataUrl:'/km/calendar/km_calendar_label/kmCalendarLabel.do?method=getLabelSelectList',key:'0',selType:'0',authType:'03',confirm:true">
    <div data-dojo-type="mui/listcategory/ListCategoryPath"
         data-dojo-props="key:'0',height:'4rem',modelName:'null',titleNode:'${lfn:message("km-calendar:mui.kmCalendar.label")}'">
    </div>
</div>

<%--底部确认/取消按钮--%>
<div data-dojo-type="mui/listcategory/ListCategorySelection"
     data-dojo-mixins="km/calendar/mobile/resource/js/label/LabelCategorySelectionMixin"
     data-dojo-props="key:'0',curIds:'',curNames:'',isMul:true,beforeSelectCateHistoryId:'listener'"
     fixed="bottom">
</div>