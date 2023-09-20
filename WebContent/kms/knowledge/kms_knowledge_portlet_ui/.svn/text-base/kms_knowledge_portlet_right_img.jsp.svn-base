<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>
<style>
    .kms_knowledge_portlet_all{
        display: flex;
        min-width: 400px;
        padding: 16px 25px 16px 25px;
    }
    .kms_knowledge_portlet_all:hover{
        background-color: #FAFAFA;
        cursor: pointer;
    }
    .kms_knowledge_portlet_img{
        float: right;
        width: 15%;
    }
    .kms_knowledge_portlet_img img{
        border-radius: 50%;
        width: 34px;
        height: 34px;
        float: right;
    }
    .kms_knowledge_portlet_word{
        width: 85%;
        font-size: 14px;
    }
    .kms_knowledge_portlet_info{
        color: #999999;
        margin-top: 5px;
        display: flex;
    }
    .kms_knowledge_portlet_info span{
        margin-right: 5%;
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        width: 20%;
    }
    .kms_knowledge_portlet_info span img {
        margin-right: 3px;
    }
    .kms_knowledge_portlet_regular{
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
        width: 80%;
    }
    .kms_knowledge_subject:hover{
        color: #4285F4;
    }
    .kms_knowledge_portlet_iframe{
        overflow: auto;
        overflow-y: hidden;
    }
    .kms_knowledge_portlet_inner{
        width: 100%;
        overflow: hidden;
        display: inline-block;
        min-width: 450px;
    }
    .kms_knowledge_portlet_top{
        background: #4285f4;
        color: #FFF;
        border-radius: 2px;
        font-size: 10px!important;
        position: relative;
        padding: 2px 8px 1px 3px;
    }
    .kms_knowledge_portlet_top::after{
        content: "";
        width: 10px;
        height: 10px;
        position: absolute;
        background-color: #fff;
        left:30px;
        top:3px;
        transform: rotate(45deg);
    }
</style>
<ui:ajaxtext>
    <script>
        //接口数据处理
        function jsonGetValue(itemData, name){
            if(itemData.length>0){
                for (var i = 0; i < itemData.length; i++) {
                    if(itemData[i].col==name){
                        return itemData[i].value;
                    }
                }
            }
        }
    </script>
    <ui:dataview>
        <ui:source type="AjaxJson">
            {url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledgeInfos&dataType=col&rowsize=${param.rowsize}&type=${param.type}&categoryId=${param.categoryId}&dayNum=${param.dayNum}&sumSize=${param.sumSize}'}
        </ui:source>
        <ui:render type="Template">
            console.log(data.datas)
            {$<div class="kms_knowledge_portlet_iframe"><div class="kms_knowledge_portlet_inner"> $}
            for(var i=0; i< data.datas.length; i++){
            var item = data.datas[i];
            var newdata = {
            'fdId':jsonGetValue(item,'fdId'),
            'docSubject':jsonGetValue(item,'docSubject'),
            'docAuthorName':jsonGetValue(item,'docAuthorName'),
            'docAuthor.fdAuthorImageUrl':jsonGetValue(item,'docAuthor.fdAuthorImageUrl'),
            'docCategoryName':jsonGetValue(item,'docCategoryName'),
            'docReadCount':jsonGetValue(item,'docReadCount'),
            'isHot':jsonGetValue(item,'isHot'),
            'fdSetTopTime':jsonGetValue(item,'fdSetTopTime')
            }
            {$
            <div class="kms_knowledge_portlet_all" onclick="Com_OpenWindow('${LUI_ContextPath }/kms/knowledge/kms_knowledge_base_doc/kmsKnowledgeBaseDoc.do?method=view&fdId={%newdata['fdId']%}','_blank')">
                <div class="kms_knowledge_portlet_word">
                    <div class="kms_knowledge_portlet_regular kms_knowledge_subject" title="{%newdata['docSubject']%}">
                        $}
                        if(newdata['isHot'] == 'true'){
                        {$  <img src="${LUI_ContextPath }/kms/knowledge/kms_knowledge_portlet_ui/style/images/fire.svg"> $}
                        }
                        if(newdata['fdSetTopTime'] != ''){
                        {$ <span class="kms_knowledge_portlet_top"><span>置顶</span></span>$}
                        }
                        {$
                        <span style="margin-left: 5px;"> {%newdata['docSubject']%}</span>
                    </div>
                    <div class="kms_knowledge_portlet_info">
                        <span title="{%newdata['docAuthorName']%}"><img src="${LUI_ContextPath }/kms/knowledge/kms_knowledge_portlet_ui/style/images/peoper.png">{%newdata['docAuthorName']%}</span>
                        <span title="{%newdata['docCategoryName']%}">
                    <img src="${LUI_ContextPath }/kms/knowledge/kms_knowledge_portlet_ui/style/images/company.png">
                    <spanC class="kms_knowledge_portlet_regular">{%newdata['docCategoryName']%}</spanC>
                </span>
                        <span title="{%newdata['docReadCount']%}"><img src="${LUI_ContextPath }/kms/knowledge/kms_knowledge_portlet_ui/style/images/see.png">{%newdata['docReadCount']%}</span>
                    </div>
                </div>
                <div class="kms_knowledge_portlet_img">
                    $}
                    if(newdata['docAuthor.fdAuthorImageUrl'] != ''){
                    {$ <img src="${LUI_ContextPath }{%newdata['docAuthor.fdAuthorImageUrl']%}"> $}
                    }else{
                    {$ <img src="${LUI_ContextPath }/sys/person/sys_person_zone/sysPersonZone.do?method=img&fdId="> $}
                    }
                    {$
                </div>
            </div>
            $}
            }
            {$</div></div> $}
        </ui:render>
    </ui:dataview>
</ui:ajaxtext>
