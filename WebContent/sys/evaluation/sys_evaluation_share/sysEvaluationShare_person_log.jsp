<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/evaluation/resource/evaluation/share/share_log.css" />

<div class="share_log_main">
    <div class="share_log_title_div" id="share_log_title_div">
        <span class="share_log_title">${lfn:message('sys-evaluation:table.sysEvaluationShare')}</span>
        <span class="share_log_count" id="share_person_log_count"></span>
    </div>
    <div class="share_log_content">
        <list:listview id="personListview">
            <ui:source type="AjaxJson">
                {"url":"/sys/evaluation/sys_evaluation_share/sysEvaluationShare.do?method=viewAll&orderby=fdShareTime&ordertype=down&rowsize=8&fdShareMode=${param.fdShareMode}&fdModelId=${param.fdModelId}&fdModelName=${param.fdModelName}"}
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
										<span class="share_p_to_p">${lfn:message('sys-evaluation:sysEvaluationShare.shareTo')}</span>
										<span style="color: #F19703;font-size: 14px;">{%row['goalPersonNames']%}：</span>
									</span>
                                <span style="color:black;">{%row['fdShareReason']%}</span>
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
            <ui:event topic="list.loaded" args="vt">
                // 只接收圈子的topic
                if(vt.listview.id=="personListview"){
                    var shareCount = vt.listview._data.page.totalSize;
                    $("#share_person_log_count").html("（"+shareCount+"）");
                }
            </ui:event>
        </list:listview>
    </div>
</div>

