<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>

<script type="text/javascript">
    seajs.use("kms/knowledge/kms_knowledge_portlet_ui/index/style/portlet_index_my.css");
</script>
<div id="kms_knowledge_portlet_index_my_content_box">
    <ui:dataview id="kmsKnowledgePortletIndexMyDataview">
        <ui:render type="Template">
            var numtype = "${JsParam.numtype}";
            var opttype = "${JsParam.opttype}";
            console.log("numtype=>" + numtype);
            console.log("opttype=>" + opttype);

            // console.log("data=>", data);
            var __dataview = LUI("kmsKnowledgePortletIndexMyDataview");
            var timetype = Com_GetUrlParameter(__dataview.source.url, "timetype");;
            console.log("timetype=>" + timetype)

            var currentNumtype = "";
            if(timetype==null){
                var numArr = numtype.split(";");
                if(numArr && numArr.length>0){
                    currentNumtype = numArr[0];
                }
            } else if(timetype == "season"){
                currentNumtype = "fdSeason";
            }else if(timetype == "year"){
                currentNumtype = "fdYear";
            }else if(timetype == "total"){
                currentNumtype = "fdTotal";
            }else{
                currentNumtype = "fdMonth";
            }
            console.log("currentNumtype=>", currentNumtype);
            // console.log("timetype=>" + timetype)
            {$
            <div class="head">
                <div class="kn_title">${lfn:message('kms-knowledge:kmsKnowledge.index.mycount')}</div>
                <div class="kn_time_list">
                    <ul>
                        $}
                        if(numtype.indexOf('fdMonth')>-1){
                        {$
                        <li class="{%(currentNumtype=='fdMonth'?'current':'')%}" data-timetype="month" onclick="renderData(this)">
                            <div>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.month')}</div>
                        </li>
                        $}
                        }
                        if(numtype.indexOf('fdSeason')>-1){
                        {$
                        <li class="{%(currentNumtype=='fdSeason'?'current':'')%}" data-timetype="season" onclick="renderData(this)">
                            <div>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.season')}</div>
                        </li>
                        $}
                        }
                        if(numtype.indexOf('fdYear')>-1){
                        {$
                        <li class="{%(currentNumtype=='fdYear'?'current':'')%}" data-timetype="year" onclick="renderData(this)">
                            <div>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.year')}</div>
                        </li>
                        $}
                        }
                        if(numtype.indexOf('fdTotal')>-1){
                        {$
                        <li class="{%(currentNumtype=='fdTotal'?'current':'')%}" data-timetype="total" onclick="renderData(this)">
                            <div>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.total')}</div>
                        </li>
                        $}
                        }
                        {$
                    </ul>
                </div>
                <div class="clear"></div>
            </div>
            $}
            {$
            <div class="content">
                <ul>
                    $}
                    if(opttype.indexOf('fdUpload')>-1){
                    {$
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.upload')}</div>
                            <div class="count_num">{%data.uploadCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    $}
                    }
                    if(opttype.indexOf('fdRead')>-1){
                    {$
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.read')}</div>
                            <div class="count_num">{%data.readCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    $}
                    }
                    if(opttype.indexOf('fdIntroduce')>-1){
                    {$
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.introduce')}</div>
                            <div class="count_num">{%data.introCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    $}
                    }
                    if(opttype.indexOf('fdCollect')>-1){
                    {$
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.collect')}</div>
                            <div class="count_num">{%data.collectCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    $}
                    }
                    if(opttype.indexOf('fdComment')>-1){
                    {$
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.comment')}</div>
                            <div class="count_num">{%data.commentCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    $}
                    }
                    if(opttype.indexOf('fdCorrection')>-1){
                    {$
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.correction')}</div>
                            <div class="count_num">{%data.correctionCount%}</div>
                        </div>
                    </li>
                    $}
                    }
                    {$
                </ul>
            </div>
            $}
        </ui:render>
        <ui:source type="AjaxJson">
            {url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexMyData&opttype=${JsParam.opttype}&numtype=${JsParam.numtype}'}
        </ui:source>
    </ui:dataview>

    <script type="text/javascript">
        seajs.use([ 'lui/dialog' ], function(dialog) {
            window.renderData = function (obj){
                $(obj).addClass("current").siblings().removeClass("current");

                var timetype = $(obj).attr("data-timetype");
                var fdUrl = "/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexMyData&opttype=${JsParam.opttype}&numtype=${JsParam.numtype}";
                fdUrl = Com_SetUrlParameter(fdUrl, 'timetype', timetype);

                var loading = dialog.loading();

                var dataview = LUI("kmsKnowledgePortletIndexMyDataview");
                dataview.source.url = fdUrl;
                // dataview.params = this._buildParams('change');
                // dataview.refresh();
                dataview.source.get();

                loading.hide();

                // console.log("fdUrl=>",fdUrl);
            }
        });
    </script>

    <script type="text/javascript">
        domain.autoResize();
    </script>
</div>