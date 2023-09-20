<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
if(data.length > 0){
{$
<div class="lui_imeeting_criterions">
	<div class="lui_imeeting_criterion_item">
        <div class="lui_imeeting_criterion_title">相关议题：</div>
        <div class="lui_imeeting_criterion_selectBox">
        	<ul>
$}
	for(var i = 0; i < data.length; i++){
		var value = data[i].value;
		if(i == 0){
			{$
	            <li class="lui_imeeting_criterion_selectItem sclpitl_item_active lui_text_primary" id="firstBallot" onclick="ballotChange('{%value%}',this)">
	                <span>{% data[i].text %}</span>
	            </li>
			$}
		}else{
			{$
				<li class="lui_imeeting_criterion_selectItem" onclick="ballotChange('{%value%}',this)">
	                <span>{% data[i].text %}</span>
	            </li>
			$}
		}
	}
{$
			</ul>
		</div>
	</div>
</div>
$}
}

