<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/sys/ui/jsp/common.jsp"%>
<style type="text/css">
.mod-library-rank__rank-number {display: inline-block;vertical-align: middle;width: 18px;height: 18px;text-align: center;line-height: 18px;margin-right: 10px; font-size: 0; margin-top: 4px;}
.mod-library-rank__rank-number {background: url(${LUI_ContextPath}/kms/knowledge/kms_knowledge_portlet_ui/style/images/portlet_list_number.png) no-repeat;}
.mod-library-rank__rank-number_1 { background-position: 0 -10px;vertical-align: top;margin-top:3px;}
.mod-library-rank__rank-number_2 { background-position: -18px -10px;}
.mod-library-rank__rank-number_3 { background-position: -36px -10px;}
.mod-library-rank__rank-number_4 { background-position: -54px -10px;}
.mod-library-rank__rank-number_5 { background-position: -72px -10px;}
.mod-library-rank__rank-number_6 { background-position: -90px -10px;}
.mod-library-rank__rank-number_7 { background-position: -108px -10px;}
.mod-library-rank__rank-number_8 { background-position: -126px -10px;}
.mod-library-rank__rank-number_9 { background-position: -144px -10px;}
.mod-library-rank__rank-number_10 { background-position: -162px -10px;}
.mod-library-rank__rank-number_11 { background-position: -180px -10px;}
.mod-library-rank__rank-number_12 { background-position: -198px -10px;}
.mod-library-rank__rank-number_13 { background-position: -216px -10px;}
.mod-library-rank__course-name {vertical-align: middle; width: 205px; color: #333; transition: .3s; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; word-wrap: normal;font-size:14px;}
.mod-library-rank__rank-item {width: 100%;padding: 0 6px;height: 34px;line-height: 26px;transition: .3s; margin: 0;}
.mod-library-rank__rank-item_sn {width: 20px;padding: 0 px;height: 32px;line-height: 26px;transition: .3s; margin: 0;}
.mod-library-rank__rank-list_current {display:inline-block;  margin: 0; padding: 0;list-style: none;}
.mod-library-rank__rank-item_first {height: 116px;padding: 10px 6px;}
.mod-library-rank__link-img {width: 140px; height: 88px;}
.mod-library-rank__link-img .mod-library-rank__course-img {display: inline-block;vertical-align: middle;width: 140px;height: 88px;margin: 5px 0 0 0;    border: 1px #eee solid;}
.mod-library-rank__content {padding-top: 10px;}
.mod-library-rank__rank-item:hover {background: #E5E5E5;}
.mod-library-rank__rank-content {display: inline-block;vertical-align: middle;width: 184px;vertical-align: top;margin-top: -3px;}
.mod-library-rank__course-price {color: #e85308;}
.mod-library-rank__agency-name {color: #999;}
.mod-library-rank__course-price, .mod-library-rank__agency-name, .mod-library-rank__resign-number {display: inline-block; vertical-align: middle;width: 80px;}
.mod-library-rank__course-des {display: inline-block;vertical-align: middle;width: 76px;padding-left: 8px;}
.fontCss{text-align:center;font-weight:bold;color:white;font-size:13px;}
.widthFull{width:100%;}
a:hover{text-decoration:none;}
.noDate{}
.overHidden{overflow: hidden; white-space: nowrap; text-overflow: ellipsis;}
.lui-knowledge-portal-rank  a.mod-library-rank__course-name {display:inline;}
.lui-knowledge-portal-rank span.mod-library-rank__rank-number{padding:0;}
</style>
<ui:dataview>
	<ui:render type="Template">
		if(data.length==0||data==null){
			{$<div class="noDate">${lfn:message('kms-learn:kms.learn.list.null')}</div>$}
		}else{
		data[0].imgUrl = '${LUI_ContextPath}'+data[0].imgUrl;
		{$
			<ul class="mod-library-rank__rank-list_current widthFull lui-knowledge-portal-rank">
		$}
			for(var i=0;i<data.length;i++){
				if(i==0){
		{$
					<li class="mod-library-rank__rank-item mod-library-rank__rank-item_first">
						<span style="float:left;" class="mod-library-rank__rank-number mod-library-rank__rank-number_{%i+1%}">
							<font class="fontCss">{%i+1%}</font>
						</span>
						<div>
							<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}{%data[i].viewUrl%}{%data[i].fdId%}" target="_blank" class="mod-library-rank__course-name" title="{%data[i].docSubject%}">
								<div class="overHidden">{%data[i].docSubject%}</div>
							</a>
						</div>
						<div class="mod-library-rank__rank-content">
							<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}{%data[i].viewUrl%}{%data[i].fdId%}" target="_blank" class="mod-library-rank__link-img" title="{%data[i].docSubject%}" >
								<img alt="{%data[i].docSubject%}" title="{%data[i].docSubject%}" class="mod-library-rank__course-img" src="{%data[i].imgUrl%}">
							</a>
						</div>
					</li>
		$}	
				}else{
		{$
				   <li class="mod-library-rank__rank-item" jump-through="{%i+1%}">
		$}
					if(i>=4){ 
		{$
				   		<span class="mod-library-rank__rank-number mod-library-rank__rank-number_4" style="float:left">
				   			<font class="fontCss">{%i+1%}</font>
			   			</span>
		   				<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}{%data[i].viewUrl%}{%data[i].fdId%}" target="_blank" class="mod-library-rank__course-name" title="{%data[i].docSubject%}">
		   					<div class="overHidden">{%data[i].docSubject%}</div>
			   			</a>
		$}
					}else{
		{$
						<span class="mod-library-rank__rank-number mod-library-rank__rank-number_{%i+1%}" style="float:left">
							<font class="fontCss">{%i+1%}</font>
						</span>
						<a onclick="Com_OpenNewWindow(this)" data-href="${LUI_ContextPath}{%data[i].viewUrl%}{%data[i].fdId%}" target="_blank" class="mod-library-rank__course-name" title="{%data[i].docSubject%}" >
							<div class="overHidden">{%data[i].docSubject%}</div>
						</a>
		$}
					}
		{$
				   </li>
		$}
				}
			}
		{$	
			</ul>
		$}
		}
	</ui:render>
	<ui:source type="AjaxJson">
		{url:'/kms/knowledge/kms_knowledge_portlet/kmsKnowledgePortlet.do?method=getKnowledgeBaseDocList&rowsize=${JsParam.rowsize}&categoryId=${JsParam.categoryId}&type=${JsParam.type}&scope=${JsParam.scope}&filtertype=docReadCount'}
	</ui:source>
</ui:dataview>

