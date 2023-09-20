<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

var sv = render.parent.selectedValues.get();

var fs = render.parent.favouriteSource;

//子分类收藏事件后需要重新刷新favouriteData数据
fs.favouriteData = fs.rebuild(render.parent);

var showFavorite = render.parent.parent.parent.showFavorite;

var isInit = render.parent.isInit || false;
var exceptValue = render.parent.parent.parent.exceptValue;

if(isInit){
	var max = render.parent.max;
	if(data.length > max)
		render.parent.slot = data.length - max;
}else{
	// 用于内部搜索渲染block的结构
	if(data.length == 0)
		data = [[]];
	
}


for(var k = 0;k<data.length;k++) {
	var index = 0;
	var display = true;
	if(!isInit){
		index = render.parent.index 
	}else{
		index = k+1;
		if(k < render.parent.slot){
			display = false;
		}
	}
{$
	 <li class="lui_category_frame_item" data-lui-mark="select.block.content" data-lui-block-index="{% index %}" $}if(!display){{$ style="display:none" $}} {$>
	 	<ul data-lui-mark="select-item" class="lui_category_content">
$}
			var d = data[k];
			
			if(exceptValue) {
				var cpdata = d;
				d = [];
			
				for(var jj = 0; jj < cpdata.length; jj ++) {
					if(exceptValue.indexOf(cpdata[jj].value) <= -1) {
						d.push(cpdata[jj]);
					}
				}
			}
			
		 	for(var i=0;i<d.length;i++){
		 		var selected = false;
		 		for(var j = 0;j<sv.length;j++){
		 			for(var e = 0;e<sv[j].data.length;e++){
			 			if(sv[j].data[e]['value']==d[i].value){
			 				selected = true;
			 				break;
			 			}
		 			}
		 		}
			{$
				<li data-lui-item-id="{%d[i].value%}" data-lui-item-padmin="{% d[i].pAdmin %}" class="clearfloat $}if(selected){{$ lui_category_li_on $}}{$" data-lui-mark="select.item.li">
			$}
				if(d[i].nodeType){
					if (d[i].nodeType=='template') {
					{$
						<a class="lui_category_item_template" href="javascript:;" >​</a>
					$}
					} else if(d[i].nodeType=='category') {
					{$
						<a class="lui_category_item_category" href="javascript:;" >​</a>
					$}
					}
				}	
			{$
					<a class="lui_category_itemLink_single $}if(selected){{$lui_category_itemLink_on $}}{$ " $} if(d[i].nodeType){ {$ style="width:85%" $} } {$ href="javascript:;" data-lui-mark="selected.item.pointer">
						<label class="textEllipsis" title="{%env.fn.formatText(d[i].text)%}">{%env.fn.formatText(d[i].text)%}</label>
					</a>
			$}
				 if(showFavorite){
					if(d[i].nodeType == '' || d[i].nodeType !='category'){
						if (fs.exist(d[i].value)) {
							{$
								<a  data-lui-mark="select.item.favourite" href="javascript:;" title="${lfn:message('sys-ui:ui.category.gcategory.cancel') }" >​
									<i class="star-icon star-icon-solid"></i>
								</a>
							$}
						}else{
							{$
								<a   data-lui-mark="select.item.favourite" href="javascript:;"  title="${lfn:message('sys-ui:ui.category.gcategory.add') }">​
									<i class="star-icon star-icon-line"></i>
								</a>
							$}
						}
					}
				}	
			{$
				</li>
			$}
			}
{$
	 	</ul>
	</li>
$}
}