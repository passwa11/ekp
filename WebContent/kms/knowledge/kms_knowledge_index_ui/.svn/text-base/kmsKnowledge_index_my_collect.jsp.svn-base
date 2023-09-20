<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>

<div id="kms_knowledge_index_my_collect_box">
    <ui:dataview>
        <ui:render type="Template">
            var dataInfo = data.datas;
            // console.log("collect data=>", dataInfo);
            if(dataInfo.length==0||dataInfo==null){
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
                <div class="new_list_title_text">${lfn:message('kms-knowledge:list.myBookmark')}</div>
                <div class="new_list_title_more" onclick="collectReadMore();">
                        ${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.more')}
                    <i class="next">&nbsp;&nbsp;&nbsp;</i>
                </div>
            </div>
            <div class="new_list_content">
                <ul>
                    $}
                    for(var i=0;i<dataInfo.length;i++){
                        var dataItem = dataInfo[i];
                        var fdId =  getColVal(dataItem, "fdId");
                        // console.log("dataItem", dataItem)
                        var srcSubject = getColVal(dataItem, 'docSubject');
                        var subject = getByLength(srcSubject,45);
                        var docAuthor = getColVal(dataItem, 'docAuthorName');
                        {$
                        <li class="new_list_item" onclick="openBaseDoc('{%fdId%}')">
                            <div class="new_list_item_subject" title="{%srcSubject%}">{%subject%}</div>
                            <div class="new_list_item_info">
                                <div class="new_list_item_author" title="{%docAuthor%}">{%getByLength(docAuthor,12)%}</div>
                                <div class="new_list_item_org">{%getColVal(dataItem, 'docCategoryName')%}</div>
                                <div class="new_list_item_date">{%getColVal(dataItem, 'docPublishTime')%}</div>
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
            {url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=listChildren&dataType=des&orderby=kmsKnowledgeBaseDoc.docPublishTime&ordertype=down&q.type=myBookmark&pageno=1&rowsize=15'}
        </ui:source>
    </ui:dataview>

    <script type="text/javascript">
        window.collectReadMore= function () {
            Com_OpenWindow('${LUI_ContextPath}/kms/knowledge/#j_path=%2Fbookmark&cri.q=type%3AmyBookmark');
        }
    </script>
</div>