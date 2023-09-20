<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>

<script type="text/javascript">
    seajs.use("kms/knowledge/kms_knowledge_portlet_ui/index/style/portlet_index_new.css");
</script>

<div id="kms_knowledge_portlet_index_new_content_box">
    <ui:dataview>
        <ui:render type="Template">
            if(data.length==0||data==null){
            {$
            <div class="no_data_box">
                <div class="no_data_img"></div>
                <div class="no_data_content">${lfn:message('kms-knowledge:kms.knowledge.list.new.nocontent')}</div>
            </div>
            $}
            }
            else{
            {$
                <div class="new_list_title">
                    <div class="new_list_title_text">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.new.title')}</div>
                    <div class="new_list_title_more" onclick="more();">
                        ${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.more')}
                        <i class="next">&nbsp;&nbsp;&nbsp;</i>
                    </div>
                </div>
                <div class="new_list_content">
                    <ul>
                        $}
                        for(var i=0;i<data.length;i++){
                        // console.log(data[i])
                        {$
                        <li class="new_list_item" onclick="openDoc('{%data[i].href%}')">
                            <div class="new_list_item_subject">{%data[i].text%}</div>
                            <div class="new_list_item_info">
                                <div class="new_list_item_author">{%getByLength(data[i].creator,10)%}</div>
                                <div class="new_list_item_date">{%data[i].created%}</div>
                            </div>
                            <div class="clear"></div>
                        </li>
                        $}
                        }
                        {$
                    </ul>
                </div>
            $}
            }
        </ui:render>
        <ui:source type="AjaxJson">
            {url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledge&dataType=col&rowsize=${param.rowsize}&type=docPublishTime&categoryId=${param.categoryId}&scope=${param.scope}'}
        </ui:source>
    </ui:dataview>

    <script type="text/javascript">
        seajs.use(['kms/knowledge/kms_knowledge_ui/js/goToMoreView.js','kms/knowledge/resource/js/util_sea'], function (goToMoreView,Util) {
            window.more = function () {
                goToMoreView.goToView('${param.categoryId}', 'kms/knowledge/', '', 'rowtable', '${param.scope}');
            }
            window.getByLength = Util.getByLength;
            window.openDoc = function (url) {
                Com_OpenWindow('${LUI_ContextPath}' + url);
            }
        });
    </script>
</div>