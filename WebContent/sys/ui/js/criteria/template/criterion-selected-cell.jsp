<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
{$
	<li data-criterion-key="{%data.key%}">
		<a href="javascript:;">
			<span class="text"><span class="title">{%data.title%}:</span> $}
			var valueType = data.src.valueType;
			var req = data.src.isRequired();
			var len = data.values.length;
			for (var i = 0; i < len; i ++) {
				var val = data.values[i];
				if (valueType == 'normal') {
					if (i > 0) {
						{$ 、 $}
					}
					{$ {%env.fn.formatText(val.text)%} $}
				}
				else if (valueType == 'hierarchy') {
					if (i > 0) {
						{$ &gt; $}
					}
					if ($.isArray(val)) {
						for (var j = 0 ; j < val.length; j ++) {
							var _val = val[j];
							if (j > 0) {
								{$、$}
							}
							{$ {%env.fn.formatText(_val.text)%} $}
						}
					} else {
					{$ {%env.fn.formatText(val.text)%} $}
					}
				}
				else if (valueType == 'range') {
					if(len>1){
						var value_1 = data.values[0].text;
						var value_2 = data.values[1].text;
						if (value_1==''){
							{$ ${lfn:message('sys-ui:ui.criteria.range.less')} {%value_2%} $}
						} else if(value_2==''){
							{$ ${lfn:message('sys-ui:ui.criteria.range.more')} {%value_1%} $}
						} else if(value_2==value_1){
							{$ {%value_1%} $}
						} else {
							{$ {%value_1%}~{%value_2%} $}
						}
						break;
					}
					if(len==1){
						{$ {%env.fn.formatText(val.text)%} $}	
						break;
					}					
				}
			}
			{$</span>$}
			if (!req) {
			{$<i class="cancel"></i>$}
			}
			{$
		</a>
	</li>
$}