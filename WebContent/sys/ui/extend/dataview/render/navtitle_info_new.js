var element = render.parent.element;
if(data==null || data.length==0){
	done();
	return;
}


element.html('');
var infoListDiv = $('<ul>').attr('class', 'lui_knowledge_sum_card_item').appendTo(element);
buildInfo();

seajs.use(['lui/topic'],function(topic){
	topic.subscribe('nav.operation.clearStatus', function(evt) {
		
		$(".lui_knowledge_sum_card_item li").removeClass("selected");
		
	});
});

done();


//构造第一个菜单，同时进行计算
function buildInfo(){
	
	for(var i = 0;i< data.length;i++ ){
		buildOneInfo(data[i],i).appendTo(infoListDiv);
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
function buildOneInfo(oneData,inum){
	var divClass='lui_knowledge_sum_card_item_document';
	if(inum==1||inum==4||inum==7||inum==10){
		divClass='lui_knowledge_sum_card_item_wiki';
	}
	if(inum==2||inum==5||inum==8||inum==11){
		divClass='lui_knowledge_sum_card_item_atom';
	}
	var topNode = $("<li/>").attr('class', divClass);
	var divNode = $("<div/>").attr('class', 'lui_knowledge_sum_card_item_num').appendTo(topNode);
	var bNode = $("<b/>").appendTo(divNode);
	var spanNode= $("<span/>").appendTo(bNode);
	var aNode = $("<a/>").attr('class', 'num').appendTo(spanNode);
	if(oneData.value){
		aNode.html(oneData.value);
	}
	if(oneData.count_url){
		getNum(oneData.count_url).then(function(num){
			if(parseInt(num) > 9999){
				var text = "9999";
				aNode.html(text);
				spanNode.after('<i>+</i>')
			}else{
				aNode.text(num);
			}
			aNode.attr('title',num);
			
			if(num<100&&num>0){
				topNode.addClass('percent10');
		      }else if(num>=100&&num<500){
		    	  topNode.addClass('percent25');
		      }else if(num>=500&&num<1000){
		    	  topNode.addClass('percent50');
		      }else if(num>=1000){
		    	  topNode.addClass('percent75');
		      }
			
		});
	}
	var brNode= $("<br/>").appendTo(topNode);
	var textNode= $("<span/>").appendTo(topNode);
	textNode.text(oneData.text);
	
//	var bodyNode = $("<a/>").appendTo(topNode);
//	var numberNode = $("<span/>").attr('class', 'num').appendTo(bodyNode);
//	var textNode = $("<span/>").attr('class', 'txt').appendTo(bodyNode);
//	if(oneData.value){
//		if(parseInt(oneData.value) > 999){
//			var text = "999<i class='num_plus'>+</i>";
//			numberNode.html(text);
//		}else{
//			numberNode.text(oneData.value);
//		}
//	}else if(oneData.count_url){
//		getNum(oneData.count_url).then(function(num){
//			if(parseInt(num) > 999){
//				var text = "999<i class='num_plus'>+</i>";
//				numberNode.html(text);
//			}else{
//				numberNode.text(num);
//			}
//			numberNode.attr('title',num);
//		});
//	}
//	
//	textNode.text(oneData.text);
	
	if(oneData.href!=null){
		topNode.click(function(){
			
			//移除导航选中状态
			LUI.$("[data-lui-type*=AccordionPanel] li").removeClass("lui_list_nav_selected");
			
			$('.lui_knowledge_sum_card_item li').removeClass('selected');
			
			seajs.use(['lui/topic'],function(topic){
				topic.publish("nav.operation.clearStatus",null);
			});
			
			var _self = this;
			setTimeout(function(){
				$(_self).addClass('selected');
			},2);
			
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

