var instances;
var heig = render.parent.element.parent().height();
var dom = $("<div class='lui_dataview_html_div' style='min-height:"+heig+"px;overflow-y:hidden;'>"+data.html+"</div>");
dom.find("[data-lui-mark='panel.dataview.height']").css("min-height",heig);
done(dom); 
seajs.use(['lui/parser', "resource/ckeditor/videoResize"],function(parser,rtfVideoResize){	
	//修复rtf H5视频播放的问题
	if(rtfVideoResize){
		rtfVideoResize.fixVideoResize(dom);
	}
	parser.parse(render.parent.element,function(_instances){
		instances = _instances;
		// 处理继承父DataView无数据时显示“暂无相关数据”公用tip提醒标识属性
		for (id in instances){ 
           var dataView = instances[id]; 
           if(dataView.parent == null){
        	   var parentDataView = LUI(render.parent.element.attr("data-lui-cid"));
        	   var parentShowNoDataTip = (parentDataView.config.vars && parentDataView.config.vars.showNoDataTip)||false; // 是否要显示暂无数据公共提醒
        	   var parentShowErrorTip = (parentDataView.config.vars && parentDataView.config.vars.showErrorTip)||false;   // 是否要显示程序发生异常公共提醒
        	   if(dataView.config.vars){
        		   dataView.config.vars["showNoDataTip"] = parentShowNoDataTip.toString();
        		   dataView.config.vars["showErrorTip"] = parentShowErrorTip.toString();
        	   }else{
        		   dataView.config.vars = { "showNoDataTip":parentShowNoDataTip.toString(), "showErrorTip":parentShowErrorTip.toString() };
        	   }
           }
		}
	});
});
render.parent.onErase(function(){
	for (var i in instances){
		instances[i].destroy();
	}		
});