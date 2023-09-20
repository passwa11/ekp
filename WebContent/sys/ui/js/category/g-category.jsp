<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

var exceptValue = render.parent.parent.exceptValue;
var selectCommonText = render.parent.parent.selectCommonText;

{$
	<div class="lui_category_gcategory_select_box">$}
		{$<select class="lui_category_gcategory_select_favorite">$}
		if(selectCommonText){
			{$<option value="">{%selectCommonText%}</option>$}
		}else{
			{$<option value="">${lfn:message('sys-ui:ui.category.gcategory.msg') }</option>$}
		}
		for (var i = 0; i < data.length; i ++) {
			if(exceptValue && exceptValue.indexOf(data[i].value) > -1) {
				continue;
			}
			{$<option value="{%data[i].value%}">{%env.fn.formatText(data[i].text)%}</option>$}
		}
		{$</select>$}
	{$</div>
$}
