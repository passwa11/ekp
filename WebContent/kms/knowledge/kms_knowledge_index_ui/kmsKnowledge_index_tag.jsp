<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>

<div id="kms_knowledge_index_tag_box">
    <ui:dataview>
        <ui:render type="Template">
            // console.log("tag data=>", data)
            var dataList = data.list;
            if(dataList.length==0||dataList==null){
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
                <div class="new_list_title_text">${lfn:message('kms-knowledge:kmsKnowledge.index.tag')}</div>
            </div>
            <div class="new_list_content">
                <ul>
                    $}
                    for(var i=0;i<dataList.length;i++){
                    // console.log(dataList[i])
                    var item = dataList[i];
                    {$
                    <li class="{%i==0?'current':''%}" title="{%item.name%}"
                        onclick="goToSearchPage('{%item.name%}', '{%item.modelName%}')">
                        {%getByLength(item.name,10)%}
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
            {url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledgeHotTags&rowsize=9'}
        </ui:source>
        <ui:event event="load" args="evt">
            $("#kms_knowledge_index_tag_box ul li").hover(function(){$(this).addClass("current").siblings().removeClass("current")});
        </ui:event>
    </ui:dataview>

    <script type="text/javascript">
        window.goToSearchPage = function (name, modelName) {
            // sys/ftsearch/searchBuilder.do?method=search&searchFields=tag&newLUI=true&modelName=com.landray.kmss.kms.wiki.model.KmsWikiMain&queryString=财经
            var fdUrl = "${ LUI_ContextPath}/sys/ftsearch/searchBuilder.do?method=search&searchFields=tag&newLUI=true&queryString=" + encodeURI(name).replace("+", "%20") + "&queryType=normal"
                + "&modelName=" + modelName;
            Com_OpenWindow(fdUrl);
        }
    </script>
</div>