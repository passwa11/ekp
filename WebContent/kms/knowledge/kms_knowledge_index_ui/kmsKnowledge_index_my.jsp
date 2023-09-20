<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script src="${LUI_ContextPath}/kms/knowledge/kms_knowledge_index_ui/js/knowledgeIndexUtil.js"></script>
<div id="kms_knowledge_index_my_content_box">
    <div class="head">
        <div class="kn_title">${lfn:message('kms-knowledge:kmsKnowledge.index.mycount')}</div>

        <div class="kn_time_list">
            <ul>
                <li class="current" data-timetype="month" onclick="renderData(this)">
                    <div>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.month')}</div>
                </li>
                <li data-timetype="season" onclick="renderData(this)">
                    <div>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.season')}</div>
                </li>
                <li data-timetype="year" onclick="renderData(this)">
                    <div>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.year')}</div>
                </li>
                <li data-timetype="total" onclick="renderData(this)">
                    <div>${lfn:message('kms-knowledge:kmsKnowledge.index.mycount.total')}</div>
                </li>
            </ul>
            <div class="range_time_box"></div>
        </div>
        <div class="clear"></div>
    </div>

    <ui:dataview id="kmsKnowledgeIndexMyDataview">
        <ui:render type="Template">
            {$
            <div class="content kmsKnowledgeIndexMy_content">
                <div class="loader" style="margin-top: 30px">
                    <div class="loader-inner ball-pulse" style="text-align: center">
                        <div></div>
                        <div></div>
                        <div></div>
                    </div>
                </div>
                <ul>
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.upload')}</div>
                            <div class="count_num">{%data.uploadCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.read')}</div>
                            <div class="count_num">{%data.readCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.introduce')}</div>
                            <div class="count_num">{%data.introCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.collect')}</div>
                            <div class="count_num">{%data.collectCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.comment')}</div>
                            <div class="count_num">{%data.commentCount%}</div>
                        </div>
                        <div class="split"></div>
                    </li>
                    <li>
                        <div class="count_data">
                            <div class="count_item">${lfn:message('kms-knowledge:kms.knowledge.mobile.type.correction')}</div>
                            <div class="count_num">{%data.correctionCount%}</div>
                        </div>
                    </li>
                </ul>
            </div>
            $}
        </ui:render>
        <ui:source type="AjaxJson">
            {url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexMyData&numtype=fdMonth;fdSeason;fdYear;fdTotal&opttype=fdUpload;fdRead;fdIntroduce;fdCollect;fdComment;fdCorrection'}
        </ui:source>
        <ui:event event="load" args="evt">
            $(".kmsKnowledgeIndexMy_content .loader").hide();
            $(".kmsKnowledgeIndexMy_content ul").show();
        </ui:event>
    </ui:dataview>
    <script type="text/javascript">
        seajs.use(['lui/jquery'], function($){
            showRangeTime("month");
            window.renderData = function (obj){
                $(obj).addClass("current").siblings().removeClass("current");
                var timetype = $(obj).attr("data-timetype");
                showRangeTime(timetype);
                var fdUrl = "/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexMyData&numtype=fdMonth;fdSeason;fdYear;fdTotal&opttype=fdUpload;fdRead;fdIntroduce;fdCollect;fdComment;fdCorrection";
                fdUrl = Com_SetUrlParameter(fdUrl, 'timetype', timetype);

                $(".kmsKnowledgeIndexMy_content .loader").show();
                $(".kmsKnowledgeIndexMy_content ul").hide();

                var dataview = LUI("kmsKnowledgeIndexMyDataview");
                dataview.source.url = fdUrl;
                dataview.source.get();
            }

            function showRangeTime(val) {
                if(val == "total") {
                    $(".range_time_box").html("");
                    return;
                }
                var dates = getRangeDate(val);
                if(dates) {
                    var startDate = dates.startDate;
                    startDate = startDate.replaceAll("-", "/");
                    var endDate = dates.endDate;
                    endDate = endDate.replaceAll("-", "/");
                    var time = startDate + " ~ " + endDate;
                    $(".range_time_box").html(time);
                }
            }
        })
    </script>
</div>