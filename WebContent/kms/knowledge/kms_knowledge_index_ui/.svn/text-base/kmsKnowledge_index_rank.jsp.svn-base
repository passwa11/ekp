<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>

<div id="kms_knowledge_index_rank_content_box">
    <ui:dataview>
        <ui:render type="Template">
            if(data.length==0||data==null){
            {$
            <div class="no_data_box">
                <div class="no_data_img"></div>
                <div class="no_data_content">${lfn:message('kms-knowledge:kms.knowledge.list.new.nocontent')}</div>
            </div>            $}
            }
            else{
            {$
            <div class="rank_list_title">
                <div class="rank_list_title_text">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.rank.title')}</div>
                <div class="rank_list_title_more" onclick="more(null,'docReadCount')">
                        ${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.more')}
                    <i class="next">&nbsp;&nbsp;&nbsp;</i>
                </div>
            </div>
            <div class="rank_list_content">
                <ul class="mod-library-rank__rank-list_current widthFull lui-knowledge-portal-rank">
                    $}
                    for(var i=0;i<data.length;i++){
                    {$
                    <li class="mod-library-rank__rank-item" jump-through="{%i+1%}">
                        $}
                        if(i>=4){
                        {$
                        <span class="mod-library-rank__rank-number mod-library-rank__rank-number_4" style="float:left">
                            <font class="fontCss">{%i+1%}</font>
                        </span>
                        <a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}{%data[i].viewUrl%}{%data[i].fdId%}" target="_blank" class="mod-library-rank__course-name" title="{%data[i].docSubject%}">
                            <div class="overHidden">{%data[i].docSubject%}</div>
                        </a>
                        $}
                        }else{
                        {$
                        <span class="mod-library-rank__rank-number mod-library-rank__rank-number_{%i+1%}" style="float:left">
                            <font class="fontCss">{%i+1%}</font>
                        </span>
                        <a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}{%data[i].viewUrl%}{%data[i].fdId%}" target="_blank" class="mod-library-rank__course-name" title="{%data[i].docSubject%}" >
                            <div class="overHidden">{%data[i].docSubject%}</div>
                        </a>
                        $}
                        }
                        {$
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
            {url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledgeBaseDocList&rowsize=8&filtertype=docReadCount'}
        </ui:source>
    </ui:dataview>

    <script type="text/javascript">
        seajs.use(['kms/knowledge/kms_knowledge_ui/js/goToMoreView.js'], function (goToMoreView) {
            window.more = function (categoryId, type) {
                goToMoreView.goToView(categoryId, 'kms/knowledge/', type, 'rowtable');
            }
        });
    </script>
</div>