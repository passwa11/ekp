<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>

<div id="kms_knowledge_index_contribute_box">
    <ui:dataview>
        <ui:render type="Template">
            var dataList = data.list;
            // console.log("Contribuate data",data.list)
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
                <div class="new_list_title_text">${lfn:message('kms-knowledge:kmsKnowledge.index.contribuate.rank')}</div>
            </div>
            <div class="new_list_content">
                <ul>
                    <li class="head">
                        <div class="rank">
                            ${lfn:message('kms-knowledge:kmsKnowledge.index.rank01')}
                        </div>
                        <div class="author">
                            ${lfn:message('kms-knowledge:kmsKnowledge.index.rank02')}
                        </div>
                        <div class="count">
                            ${lfn:message('kms-knowledge:kmsKnowledge.index.rank03')}
                        </div>
                    </li>
                    $}
                    for(var i=0;i<dataList.length;i++){
                    // console.log(dataList[i])
                    var item = dataList[i];
                    {$
                    <li>
                        <div class="rank">
                            <span class="num num_{%i+1%}">
                                {% i + 1 %}
                            </span>
                        </div>
                        <div class="author">
                            <span><img class="img" src="${ LUI_ContextPath}/sys/person/image.jsp?personId={%item.id%}&size=m"/></span>
                            <span class="name" title="{%item.isOuter?item.outerName:item.name%}">
                                {%getPersonLink(item.id, getByLength(item.name,5), item.isOuter, getByLength(item.outerName,5))%}
                            </span>
                        </div>
                        <div class="count count_{%i+1%}">
                            {%item.count%}
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
            {url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getIndexContribuateData&rowsize=6'}
        </ui:source>
        <ui:event event="load" args="evt">
            $("#kms_knowledge_index_contribute_box .com_outer_author").each(function(index,item){
                var ptitle =  $(this).parent().attr("title");
                // console.log("ptitle=>", ptitle);
                $(this).attr("title", ptitle);
            });
        </ui:event>
    </ui:dataview>
</div>