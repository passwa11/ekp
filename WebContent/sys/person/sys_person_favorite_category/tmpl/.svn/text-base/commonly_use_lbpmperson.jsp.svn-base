<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var data = render.parent.data;
var config = render.parent.config;
if(data.length > 0){
{$
<div class="lui_summary_dataview_header">
    <div class="lui_summary_dataview_header_icon"></div>
    <div class="lui_summary_dataview_header_text">${lfn:message("sys-person:ui.commonly.use")}</div>
</div>
<div class="lui_summary_dataview_content">
    <div class="lui_summary_list ">
        <ul>
            $}
            for(var i=0; i<data.length; i++){
            var item = data[i];
            //进行转码
            item.text = $("<div></div>").text(item.text).html();
            item.cateName = $("<div></div>").text(item.cateName).html();
            {$
            <li class="lui_summary_item" data-item-fdId="{%item.value%}" data-item-fdName="{%item.text%}" data-item-modelName="{%item.modelName%}" data-href="{%Com_Parameter.ContextPath%}{%item.addUrl%}" onclick="Com_OpenNewWindow(this)">
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
                    <div class="lui_summary_item_title" title="{%item.text%}">{%item.text%}</div>
                    <div class="lui_summary_item_parent" title="{%item.cateName%}">{%item.cateName%}</div>
                </div>
                <div class="lui_summary_item_btn">
                    <i class="lui_summary_item_btn_custom"></i>
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