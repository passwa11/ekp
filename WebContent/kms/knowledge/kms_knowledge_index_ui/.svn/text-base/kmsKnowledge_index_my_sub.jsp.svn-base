<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>

<div id="kms_knowledge_index_my_sub_box">
    <ui:dataview>
        <ui:render type="Template">
            var dataInfo = data.datas;
            // console.log("sub data=>", dataInfo);
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
                <div class="new_list_title_text">${lfn:message('kms-knowledge:list.mySub')}</div>
                <div class="new_list_title_more" onclick="subMore();">
                        ${lfn:message('kms-knowledge:kmsKnowledge.portlet.knowledge.index.more')}
                    <i class="next">&nbsp;&nbsp;&nbsp;</i>
                </div>
            </div>
            <div class="new_list_content">
                <ul>
                    $}
                    for(var i=0;i<dataInfo.length;i++){
                        var dataItem = dataInfo[i];
                        console.log("dataItem", dataItem)
                        var href = getColVal(dataItem, 'href');
                        var subject = getColVal(dataItem, 'docSubject');
                        var docAuthor = getColVal(dataItem, 'docAuthor');
                        {$
                        <li class="new_list_item" onclick="openDoc('{%href%}')">
                            <div class="new_list_date_info">
                                <div class="img">
                                    <img src="./images/default_first.jpg"/>
                                </div>
                            </div>
                            <div class="new_list_subject_info">
                                <div class="new_list_item_subject" title="{%subject%}">{%getByLength(subject,45)%}</div>
                                <div class="new_list_item_info">
                                    <div class="new_list_item_author" title="{%docAuthor%}">{%getByLength(docAuthor,12)%}</div>
                                    <div class="new_list_item_date">{%getColVal(dataItem, 'docCreateTime')%}</div>
                                </div>
                            </div>
                            <div class="clear"></div>
                        </li>
                        $}
                    }
                    {$
            </div>
            $}
            }
        </ui:render>
        <ui:source type="AjaxJson">
            {url:'/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated.do?method=listPerson&rowsize=16&q.followtype=category_modelName_com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc&q.s_raq=0.7226546250451906&pageno=1&pagingtype=default&orderby=sysFollowPersonDocRelated.followDoc.docCreateTime&ordertype=down'}
        </ui:source>
    </ui:dataview>

    <script type="text/javascript">
        window.subMore= function () {
            Com_OpenWindow('${LUI_ContextPath}/sys/follow/sys_follow_person_doc_related/sysFollowPersonDocRelated_person.jsp#cri.q=followtype:category_modelName_com.landray.kmss.kms.knowledge.model.KmsKnowledgeBaseDoc');
        }
    </script>
</div>