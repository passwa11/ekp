<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

var isRequired = render.parent.isRequired();
if (!isRequired) {
{$
<li class="criterion-all">
	<a href="javascript:;" title="${lfn:message('sys-ui:ui.criteria.all')}" class="selected"><span class="text">${lfn:message('sys-ui:ui.criteria.all')}</span></a>
</li>
$}
}
for (var i = 0, ln = data.length; i < ln; i ++) {
var title = data[i].title ? data[i].title : data[i].text;
{$
<li class="lui-property-criterion-li"><a href="javascript:;" title="{%env.fn.formatText(title)%}"
	data-lui-val="{%data[i].value%}"><i class="checkbox"></i><span class="text">
$}	
	if(data[i].text.length>30){
		{$ 
			{%env.fn.formatText(data[i].text.substring(0,30))%}...
		 $}
		}else{
		{$ 
			{%env.fn.formatText(data[i].text)%}
		 $}
	}
	
	
{$	
	</span>
	$}
	if(data[i].hasChild && (data[i].hasChild == 'true' || data[i].hasChild == true)) {
	{$<div data-lui-val="{%data[i].value%}" title="展开子级" class="lui-property-has-child-icon"></div> $}
	}
	{$
</a>
</li>
$}
}