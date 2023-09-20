<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script>
    Com_IncludeFile('echarts4.2.1.js','${LUI_ContextPath}/sys/ui/js/chart/echarts/','js',true);
</script>
<!-- 流程统计 -->
<div class="lui_review_statist_echarts">
    <ui:tabpanel id="echartsPanel">
        <ui:content title="${ lfn:message('km-review:kmReview.nav.create.my') }">
            <img id="img1" src="${LUI_ContextPath}/resource/style/common/images/loading.gif" ></img>
            <div id="process_datas" class="lui_review_echarts_table"></div>
        </ui:content>
        <ui:content title="${ lfn:message('km-review:kmReview.nav.resolve.my') }">
            <img id="img2" src="${LUI_ContextPath}/resource/style/common/images/loading.gif" ></img>
            <div id="process_data" class="lui_review_echarts_table"></div>
        </ui:content>
    </ui:tabpanel>
</div>