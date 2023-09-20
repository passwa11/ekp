<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
var data = render.parent.data;
var list = data.list;
var config = render.parent.config;
if(list.length > 0){
{$
<div class="lui_summary_dataview_content">
    <div class="lui_summary_list">
        <ul>
            $}
            for(var i=0; i<list.length; i++){
                var item = list[i];
            {$
            <li class="lui_summary_item" data-item-fdId="{%item.value%}" data-item-fdName="{%item.text%}">
                <div class="lui_summary_item_icon">
                    $}
                    if(item.fdIcon){
                    {$
                    <img src="{%item.fdIcon%}" class=""></img>
                    $}
                    }else{
                    {$
                    <img src="${LUI_ContextPath}/sys/ui/resource/images/icon_office.png">
                    $}
                    }
                    {$
                </div>
                <div class="lui_summary_item_text">
                    <div class="lui_summary_item_title">{%item.text%}</div>
                    <div class="lui_summary_item_parent">{%item.parent%}</div>
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