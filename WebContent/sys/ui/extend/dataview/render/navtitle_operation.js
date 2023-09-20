var element = render.parent.element;
if(data==null || data.length==0){
	done();
	return;
}
var showCount = 0;
var moreOperationNode;

element.html('');
var operationListDiv = $('<ul>').attr('class', 'lui-navtitle-card-operation').appendTo(element);
buildOperation();
seajs.use(['lui/topic'],function(topic){
	topic.subscribe('nav.operation.clearStatus', function(evt) {
		$(".lui-navtitle-card-operation li").removeClass("selected");
	});
});
done();


//构造第一个菜单，同时进行计算
function buildOperation(){
	showCount = data.length;
	
	for(var i = 0;i< showCount;i++ ){
		buildOneOperation(data[i]).appendTo(operationListDiv);
	}
}

//构造一个菜单
function buildOneOperation(oneData,isMore){
	var topNode = $("<li/>");
	if(oneData.selected){ //有selected选项强制选中
		topNode.attr('class', 'selected');
	}else if(oneData.router){ //如果route与当前route一致则选中
		seajs.use(['lui/framework/router/router-utils'],function(routerUtils){
			if(routerUtils.equalPath(oneData.href) 
					|| routerUtils.isParentPath(oneData.href)){
				topNode.attr('class', 'selected');
			}
		});
	}
	
	var bodyNode = $("<a/>").appendTo(topNode);
	var html = "";
	if(oneData.icon){
		if(oneData.icon.indexOf('&#') > -1){ //后续废弃
			html+= '<i class="fontmui">'+oneData.icon+'</i>';
		}else if(oneData.icon.indexOf('lui_iconfont') > -1){
			html+= '<i class="iconfont '+ oneData.icon +'"></i>';
		}else{
			html+= '<i class="'+oneData.icon+'"></i>';
		}
	}
	seajs.use(['lui/util/str'],function(strutil){
		html+= strutil.decodeHTML(oneData.text);
		bodyNode.html(html);
		topNode.attr('title',strutil.decodeHTML(oneData.text));
	});
	
	if(oneData.href!=null){
		topNode.click(function(){
			
			//移除导航选中状态
			LUI.$("[data-lui-type*=AccordionPanel] li").removeClass("lui_list_nav_selected");
			
			$('.lui-navtitle-card-operation li').removeClass('selected');
			
			seajs.use(['lui/topic'],function(topic){
				topic.publish("nav.operation.clearStatus", null);
			});
			
			var _self = this;
			setTimeout(function(){
				$(_self).addClass('selected');
			},1);
			
			if(oneData.router){
				seajs.use(['lui/framework/router/router-utils'],
						function(routerUtils){
					var $router = routerUtils.getRouter();
					$router.push(oneData.href, oneData.params || {});
				});
			}else{
				if(oneData.href.toLowerCase().indexOf("javascript:")>-1){
					location.href = oneData.href;
				}else{
					LUI.pageOpen(oneData.href, oneData.target ||'_blank');
				}
			}
		});
	}
	return topNode;
}

//构造更多操作
function buildMoreNode(){
	var bodyNode = $("<div/>").attr({'class':'lui-operation-more-wrap'});
	var textNode = $("<a/>").attr({'class':'lui-operation-more expand'}).appendTo(bodyNode);
	textNode.html('<i class="arrow"></i>');
	bodyNode.attr('title','更多操作');
	return bodyNode;
}