var element = render.parent.element;
if(data==null || data.length==0){
	done();
	return;
}


element.html('');
var infoListDiv = $('<ul>').attr('class', 'lui-navtitle-card-info').appendTo(element);
buildInfo();

seajs.use(['lui/topic'],function(topic){
	topic.subscribe('nav.operation.clearStatus', function(evt) {
		
		$(".lui-navtitle-card-info li").removeClass("selected");
		
	});
});

done();


//构造第一个菜单，同时进行计算
function buildInfo(){
	
	for(var i = 0;i< data.length;i++ ){
		buildOneInfo(data[i]).appendTo(infoListDiv);
	}
	
}

function getNum(url){
	var defer = $.Deferred();
	var num = "0";
	 $.ajax({    
	     type:"post",   
	     url:env.fn.formatUrl(url),     
	     success:function(data){
		    if(data['count']!=null){
		    	defer.resolve(data['count']);
		    }
		}
	 });
	return defer;
}

//构造一个菜单
function buildOneInfo(oneData){
	var topNode = $("<li/>");
	var bodyNode = $("<a/>").appendTo(topNode);
	var numberNode = $("<span/>").attr('class', 'num').appendTo(bodyNode);
	var textNode = $("<span/>").attr('class', 'txt').appendTo(bodyNode);
	if(oneData.value){
		if(parseInt(oneData.value) > 999){
			var text = "999<i class='num_plus'>+</i>";
			numberNode.html(text);
		}else{
			numberNode.text(oneData.value);
		}
	}else if(oneData.count_url){
		getNum(oneData.count_url).then(function(num){
			if(parseInt(num) > 999){
				var text = "999<i class='num_plus'>+</i>";
				numberNode.html(text);
			}else{
				numberNode.text(num);
			}
			numberNode.attr('title',num);
		});
	}
	
	textNode.text(oneData.text);
	
	if(oneData.href!=null){
		topNode.click(function(){
			
			//移除导航选中状态
			LUI.$("[data-lui-type*=AccordionPanel] li").removeClass("lui_list_nav_selected");
			
			
			$('.lui-navtitle-card-info li').removeClass('selected');
			
			seajs.use(['lui/topic'],function(topic){
				topic.publish("nav.operation.clearStatus",null);
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
					LUI.pageOpen(oneData.href,  oneData.target  || '_blank');
				}
			}
		});
	}
	return topNode;
}

