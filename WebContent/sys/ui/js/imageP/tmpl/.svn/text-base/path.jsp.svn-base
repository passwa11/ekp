<%@ page language="java" pageEncoding="UTF-8"%>
var data = layout.parent.data;
{$
<div class="lui_imageP_path">
	<ul class="lui_imageP_path_content clearfloat">
$}

for(var i=0;i<data.length;i++){
	var className = 'lui_imageP_path_n';
	if(i==0)
		className="lui_imageP_path_f";
	if(i==data.length-1)
		className="lui_imageP_path_l";
		{$
			<li class="{%className%}">
		$}
			if(i==0){
				{$<div class="lui_icon_s lui_icon_s_home"></div>$}
			}
			var hasHref = true
			if(!data[i].href)
				hasHref = false;
				
		{$
				<a href="$}if(hasHref){ {$ {%data[i].href%} $} }else{ {$ javascript:; $} }{$" style="$} if(!hasHref){ {$ cursor:none $} } {$">{% data[i].text %}</a>
			</li>
		$}
		
		if(i!=data.length-1){
		{$
			<li class="lui_imageP_path_arrow"><a href="javascript:;">></a></li>
		$}
		}
}
{$
	</ul>
</div>
$}

