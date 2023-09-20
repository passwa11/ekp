<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

var zoneUrl = env.fn.formatUrl("/sys/person/sys_person_zone/sysPersonZone.do?method=view&fdId=") + grid["docElement.fdId"];
{$
<div style="text-align: center;" class="luiMedalPersonNode">
					<a target="_blank" href="{%zoneUrl%}"> 
						<img style="width: 60px; height: 60px;" src="{%env.fn.formatUrl(grid['imgUrl'])%}">
						<span title="{%grid['docElement.fdName']%}" data-fdId="__{%grid['docElement.fdId']%}" class="personspan">
						{%grid['docElement.fdName']%}</span>
					</a>
					<kmss:auth requestURL="/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=deleteByUserId&medalId=${kmsMedalMainForm.fdId}">
					<div class="luiRemoveIcon" data-id="{%grid['docElement.fdId']%}"></div>
					</kmss:auth>
				</div>
$}
