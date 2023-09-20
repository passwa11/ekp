<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

{$
	<div class="lui_matrix_card_item_inner">
        $}
        if(grid['edit_auth'] == 'true') {
       	{$
        	<input type="checkbox" class="lui_matrix_card_select_input" data-lui-mark="table.content.checkbox" name="List_Selected" value="{%grid['fdId']%}">
        $}
        }
        if(grid['isAvailable'] == 'true') {
        {$
        <span class="card_status status_start"></span>
        $}
        } else {
        {$
        <span class="card_status status_disable"></span>
        $}
        }
        {$
        <h4 class="card_title">
            <a class="card_title_item" data-href="<c:url value="/sys/organization/sys_org_matrix/sysOrgMatrix.do"/>?method=view&fdId={%grid['fdId']%}" target="_blank" onclick="Com_OpenNewWindow(this)" title="{%grid['fdName']%}">{%grid['fdName']%}</a>
        $}
		if(grid['_matrixType']=='1') {
       	{$
            <span class="matrixType_label">系统</span>
        $}
        }
        {$
        	<p class="card_summary" title="{%grid['fdDesc']%}">{%grid['fdDesc']%}</p>
        </h4>

        <p class="card_info"><span class="card_info_item" title="{%grid['fdGroupCate']%}"><em>${lfn:message('sys-organization:sysOrgMatrix.fdCategory')}：</em>{%grid['fdGroupCate']%}</span></p>
        $}
       	if(grid['edit_auth'] == 'true') {
       	{$
        <p class="card_tip">
            <span class="card_tip_item" onclick="edit('{%grid['fdId']%}', 0);">
                <i class="card_icon icon_baseinfo"></i>
                <span class="card_tip_txt"><i class="card_tip_arrow"></i>
                <i class="card_tip_txt_inner">${lfn:message('sys-organization:sysOrgMatrix.base')}</i></span>
            </span>
            <span class="card_tip_item" onclick="edit('{%grid['fdId']%}', 1);">
                <i class="card_icon icon_field"></i>
                <span class="card_tip_txt"><i class="card_tip_arrow"></i>
                <i class="card_tip_txt_inner">${lfn:message('sys-organization:sysOrgMatrix.field')}</i></span>
            </span>
            <span class="card_tip_item" onclick="divideEdit('{%grid['fdId']%}');">
                <i class="card_icon icon_data"></i>
                <span class="card_tip_txt"><i class="card_tip_arrow"></i>
                <i class="card_tip_txt_inner">${lfn:message('sys-organization:sysOrgMatrix.fdContent')}</i></span>
            </span>
            $}
        	if(grid['isAvailable'] == 'true') {
        	{$
            <span class="card_tip_item" onclick="invalidated('{%grid['fdId']%}', false);">
                <i class="card_icon icon_disable"></i>
                <span class="card_tip_txt"><i class="card_tip_arrow"></i>
                <i class="card_tip_txt_inner">${lfn:message('sys-organization:sys.org.available.false')}</i></span>
            </span>
            $}
        	} else {
        	{$
        	<span class="card_tip_item" onclick="invalidated('{%grid['fdId']%}', true);">
                <i class="card_icon icon_start"></i>
                <span class="card_tip_txt"><i class="card_tip_arrow"></i>
                <i class="card_tip_txt_inner">${lfn:message('sys-organization:sys.org.available.true')}</i></span>
            </span>
        	$}
        	}
        	{$
        </p>
        $}
       	} else if(grid['data_cate_auth'] == 'true') {
       	{$
        <p class="card_tip">
            <span class="card_tip_item" style="width: 100%;" onclick="dataCate('{%grid['fdId']%}');">
                <i class="card_icon icon_baseinfo"></i>
                <span class="card_tip_txt"><i class="card_tip_arrow"></i>
                <i class="card_tip_txt_inner">${lfn:message('sys-organization:sysOrgMatrix.fdContent')}</i></span>
            </span>
        </p>
        $}
       	}
       	{$
    </div>
$}