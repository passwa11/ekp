var element = render.parent.element;
if(data==null || data.length==0){
	done();
	return;
}


element.html('');
getNum().then(function(num){
	var font=0;
	if(num>0&&num<100){
		font=10;
      }else if(num>=100&&num<500){
    	  font=25;
      }else if(num>=500&&num<1000){
    	  font=50;
      }else if(num>=1000){
    	  font=75;
      }
	var htmlString="<div id='processingbar' class='processingbar'><font>"+font+"</font><div>"+num+"</div></div>";
	element.html(htmlString);
	cycleTotal();
});

done();


//构造第一个菜单，同时进行计算
function buildInfo(){
	buildOneInfo().appendTo(infoListDiv);
}

function getNum(){
	var defer = $.Deferred();
	var num = 0;
	for(var i = 0;i< data.length;i++ ){
		$.ajax({    
		     type:"post", 
		     async:false,
		     url:env.fn.formatUrl(data[i].count_url),     
		     success:function(data){
			    if(data['count']!=null){
			    	num=num+data['count'];
			    }
			}
		 });
	}
	 
	 defer.resolve(num);
	return defer;
}

//构造一个菜单
function buildOneInfo(){
	var topNode = $("<div/>");
	getNum().then(function(num){
			topNode.text(num);
			topNode.attr('title',num);
	});
	
	return topNode;
}

