<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>

<div id="kms_knowledge_index_new_content_box">
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
                <div class="new_list_title_text_2">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.new.title')}</div>
                <div class="new_list_title_more" onclick="more();">
                        ${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.more')}
                    <i class="next">&nbsp;&nbsp;&nbsp;</i>
                </div>
            </div>
            <div class="new_list_content">
                <ul>
                    $}
                    for(var i=0;i<data.length;i++){
                    // console.log("new list item=>", data[i])
                    var autorName = data[i].creator==""?data[i].creatorName:data[i].creator;
                    {$
                    <li class="new_list_item" onclick="openDoc('{%data[i].href%}')">
                        <div class="new_list_date_info">
                            <div class="new_list_date_day">{%data[i].created.substring(8,10)%}</div>
                            <div class="new_list_item_year">{%data[i].created.substring(0,7)%}</div>
                        </div>
                        <div class="new_list_subject_info">
                            <div class="new_list_item_subject" title="{%data[i].text%}">{%getByLength(data[i].text,42)%}</div>
                            <div class="new_list_item_info">
                                <div class="new_list_item_author" title="{%autorName%}">{%getByLength(autorName,10)%}</div>
                                <div class="new_list_item_cat">{%getByLength(data[i].catename,10)%}</div>
                            </div>
                        </div>
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
            {url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledge&dataType=col&rowsize=9&type=docPublishTime'}
        </ui:source>
    </ui:dataview>
</div>