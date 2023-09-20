<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var data = render.parent.data;
var list = data.list;
var config = render.parent.config;
if(list.length > 0){
{$
<div class="lui_summary_dataview_header">
    <div class="lui_summary_dataview_header_icon"></div>
    <div class="lui_summary_dataview_header_text">${lfn:message("sys-lbpmperson:ui.recently.use")}</div>
</div>
<div class="lui_summary_dataview_content">
    <div class="lui_summary_list">
        <ul>
            $}
            for(var i=0; i<list.length; i++){
            var item = list[i];
            //进行转码
            item.templateName = $("<div></div>").text(item.templateName).html();
            item.cateName = $("<div></div>").text(item.cateName).html();
            var isFavorite = item['isFavorite'] || false;
            var btnCustomClass = isFavorite ? "active" : "";
            {$
            <li class="lui_summary_item" data-item-fdId="{%item.fdTemplateId%}" data-item-fdName="{%item.templateName%}" data-item-modelName="{%item.templateModelName%}" data-href="{%Com_Parameter.ContextPath%}{%item.addUrl%}" onclick="Com_OpenNewWindow(this)">
                <div class="lui_summary_item_icon">
                    $}
                    if(item.fdIcon){
                        if(item.fdIcon.indexOf("${LUI_ContextPath}")==0 && "${LUI_ContextPath}" != "/"){
                            {$
                            <img src="{%item.fdIcon%}" class=""></img>
                            $}
                        }else{
                            if("${LUI_ContextPath}" == "/"){
                                {$
                                <img src="{%item.fdIcon%}" class=""></img>
                                $}
                            }else{
                                {$
                                <img src="${LUI_ContextPath}{%item.fdIcon%}" class=""></img>
                                $}
                            }
                        }
                    }else{
                    {$
                    <img src="${LUI_ContextPath}/sys/ui/resource/images/icon_office.png">
                    $}
                    }
                    {$
                </div>
                <div class="lui_summary_item_text">
                    <div class="lui_summary_item_title" title="{%item.templateName%}">{%item.templateName%}</div>
                    <div class="lui_summary_item_parent" title="{%item.cateName%}">{%item.cateName%}</div>
                </div>
                <div class="lui_summary_item_btn {%btnCustomClass%}">
                    <i title="${lfn:message("sys-lbpmperson:ui.recently.add")}" class="lui_summary_item_btn_custom"></i>
                </div>
            </li>
            $}
            }
            {$
        </ul>
        <div class="lui_more_btn"></div>
    </div>
</div>
$}
}
