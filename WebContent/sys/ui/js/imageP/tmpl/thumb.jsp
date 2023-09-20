<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
var parent = layout.parent;
var _data = parent.data;
var data = _data.get('data'); 
var index = _data.get('index');
{$
<div class="lui_imageP_thumb_container">
	<div class="lui_imageP_thumb_toggle">
		<span class="lui_imageP_thumb_btn" data-lui-toggle="${lfn:message('sys-ui:imageP.thumbnail.collapse')}">
			${lfn:message('sys-ui:imageP.thumbnail.expand')}
		</span>
		<div class="lui_imageP_thumb_num">
			<strong class="lui_imageP_thumb_curr_num">{%index+1%}</strong>/{%_data.get('length')%}
		</div>
		<div class="lui_imageP_thumb_arrow">
			<a href="javascript:;" class="retract"></a>
		</div>
	</div>
	
	<div class="lui_imageP_thumb_content">
		<div class="lui_imageP_thumb_prev">
			<a href="javascript:;"></a>
		</div>
		<div class="lui_imageP_thumb_list">
			<ul>$}
			
			for(var i=0;i<data.length;i++){
				{$
					<li>
						<a data-lui-thumb-id="{%data[i].value%}" href="javascript:;" $} if(index==i){ {$class="on"$} } {$>
							<img data-src="{%parent.formatSrc(data[i].value)%}" src="">
						</a>
					</li>
				$}
			}
			
			{$</ul>
		</div>
		<div class="lui_imageP_thumb_next">
			<a href="javascript:;"></a>
		</div>
	</div>
	
</div>
$}
