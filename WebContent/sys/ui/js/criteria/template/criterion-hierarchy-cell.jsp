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
{$
<li class="criterion-hierarchy-parent" style="display:none;">
<a href="javascript:;" title=""><span class="text"></span><i class="up"></i></a>
</li>
$}
var ds = (Object.isArray(data)) ? data : ((data) ? (data.datas || []) : []);
for (var i = 0, ln = ds.length; i < ln; i ++) {
{$
<li><a href="javascript:;" title="{%env.fn.formatText(ds[i].text)%}" 
	data-lui-val="{%ds[i].value%}">
	<i class="checkbox"></i><span class="text">{%env.fn.formatText(ds[i].text)%}</span>
	<div class="next lui_icon_s lui_icon_s_icon_circle_arrow_right"></div></a></li>
$}
}