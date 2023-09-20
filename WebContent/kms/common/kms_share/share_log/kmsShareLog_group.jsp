<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/kms/common/kms_share/share_log/style/shareLog.css" />

<div class="share_log_main">
    <div class="share_log_title_div" id="share_log_title_div">
        <span class="share_log_title">${lfn:message('kms-common:kmsShareMain.share')}</span>
        <span class="share_log_count" id="share_group_log_count"></span>
    </div>
    <div class="share_log_content">
        <list:listview id="groupListview">
            <ui:source type="AjaxJson">
                {"url":"/kms/common/kms_share/kmsShareMain.do?method=viewAll&orderby=fdShareTime&ordertype=down&rowsize=8&fdModelId=${param.fdModelId}&fdModelName=${param.fdModelName}&fdShareMode=3"}
            </ui:source>
            <list:rowTable channel="rowtable" isDefault="true" target="_blank" cfg-norecodeLayout="simple">
                <ui:layout type="Template">
                    {$<div data-lui-mark='table.content.inside'>
                    </div>$}
                </ui:layout>
                <list:row-template>
                    {$
                    <ul>
                        <li class="share_li">
                            <img class="share_person" src="{%env.fn.formatUrl(row['imgUrl'])%}">
                            <p class="share_info">
									<span class="share_to_person">
										{%row['fdSharePerson']%}
                                        <span class="share_to_group_p">{%row['docCreator']%}</span>
										<span class="share_p_to_g">${lfn:message('kms-common:kmsShareMain.shareToGroup')}
                                            <a style="font-size: 16px;" target='_blank' href='${ LUI_ContextPath}/sns/group/sns_group_main/snsGroupMain.do?method=loadIndexData&appName=topic&fdGroupId={%row['fdGroupId']%}'>{%row['fdGroupName']%}</a>
                                        </span>
									</span>
                            </p>
                            <p class="share_time">
                                {%row['fdShareTime']%}
                            </p>
                        </li>
                    </ul>
                    $}
                </list:row-template>
            </list:rowTable>
            <list:paging layout="sys.ui.paging.default"></list:paging>
            <ui:event topic="list.loaded"  args="vt">
                // 只接收圈子的topic
                if(vt.listview.id=="groupListview"){
                    var shareCount = vt.listview._data.page.totalSize;
                    $("#share_group_log_count").html("（"+shareCount+"）");
                }
            </ui:event>
        </list:listview>
    </div>
</div>