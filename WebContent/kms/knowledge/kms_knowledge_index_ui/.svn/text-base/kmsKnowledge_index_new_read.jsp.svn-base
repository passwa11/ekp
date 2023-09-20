<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/sys/ui/jsp/common.jsp" %>

<div id="kms_knowledge_index_new_read_content_box">
    <ui:dataview>
        <ui:render type="Template">
            var dataInfo = data.datas;
            // console.log("new read data=>",data)
            if(dataInfo.length==0||dataInfo==null){
            {$
            <div class="no_data_box">
                <div class="no_data_img"></div>
                <div class="no_data_content">${lfn:message('kms-knowledge:kms.knowledge.list.new.nocontent')}</div>
            </div>
            $}
            }else{
            {$
            <div class="new_list_title">
                <div class="new_list_title_text">${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.new.read.title')}</div>
                <div class="new_list_title_more" onclick="newReadMore();">
                        ${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.more')}
                    <i class="next">&nbsp;&nbsp;&nbsp;</i>
                </div>
            </div>
            <div class="new_list_content">
                <div class="pic">
                    <img src="images/new_read_default.jpg">
                </div>
                <ul class="content_wrap">
                    $}
                    for(var i=0;i<dataInfo.length;i++){
                    var dataItem = dataInfo[i];
                    // console.log("dataItem", dataItem)
                    var srcDesc = getColVal(dataItem, 'fdDescription');
                    var desc = getByLength(srcDesc, 50);

                    var authorName = getColVal(dataItem, 'docAuthor.fdName');
                    // console.log("authorName", authorName);
                    // authorName = getByLength(authorName, 6);
                    var authorTitle = $(authorName).text().trim();
                    if(i == 0){
                        {$
                        <li class="wrap_item current">
                        $}
                    }else{
                        {$
                        <li class="wrap_item">
                        $}
                    }
                    {$
                        <div class="list">
                            <div class="list_title">
                                <!--
                                <div class="title_item"></div>
                                <div class="title_sep"></div>
                                -->
                                <div class="title_item" title="{%getColVal(dataItem, 'docSubject')%}">
                                    <a href="${LUI_ContextPath}{%getColVal(dataItem, 'linkStr')%}" target="_blank">
                                    {%getColVal(dataItem, 'docSubject')%}
                                    </a>
                                </div>
                            </div>
                            <div class="list_desc" title="{%srcDesc%}">{%getColVal(dataItem, 'fdDescription')==''?'${lfn:message('kms-knowledge:kms.knowledge.list.new.nodesc')}':desc%}</div>
                            <div class="list_info">
                                <div class="info_item" title="{%authorTitle%}">{%authorName%}</div>
                                <div class="info_sep"></div>
                                <div class="info_item">{%getColVal(dataItem, 'docPublishTime')%}</div>
                            </div>
                        </div>
                    </li>
                    $}
                    }
                    }
                    {$
                </ul>
            </div>
            $}
        </ui:render>
        <ui:source type="AjaxJson">
            {url:'/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDocIndex.do?method=readLogdata&orderby=fdReadTime&ordertype=down&rowsize=7'}
        </ui:source>
        <ui:event event="load" args="evt">
            $("#kms_knowledge_index_new_read_content_box .wrap_item").hover(function(){$(this).addClass("current").siblings().removeClass("current")});
        </ui:event>
    </ui:dataview>

    <script type="text/javascript">
        window.newReadMore= function () {
            Com_OpenWindow('${LUI_ContextPath}/kms/knowledge/#j_path=%2FreadInfo');
        }
    </script>
</div>