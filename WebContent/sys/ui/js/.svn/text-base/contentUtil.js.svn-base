
define(function(require, exports, module) {
	
	var setContentCount = function(contentId,count){
		if(contentId){
			var panel = LUI(contentId).parent;
			var idx = $.inArray(LUI(contentId),panel.contents);
			if(idx>-1){
				if(panel.titlesNode[idx]!=null){
					var title=panel.contents[idx].title;
					panel.__setTitle(idx,{title:title,force:true,count:count});
				}
				/*
				if(panel.titlesNode[idx]!=null){
					var titleNode = panel.titlesNode[idx];
					console.log(titleNode);
					
					var html = "<span class='count'>("+count+ ")</span>";
					
					var title=panel.contents[idx].title;
					var extendType;
					if(panel.layout.config && panel.layout.config.param){
						extendType = panel.layout.config.param.extend;
						if(extendType == "multiCollapse" && title.indexOf("/") >-1){
							var titleArr = title.split("/");
							title = titleArr[1];
						}
					}
					var titleiconHtml = panel.contents[idx].titleicon?self.buildTitleIcon(panel.contents[idx].titleicon):"";
					var subtitleHtml = panel.contents[idx].subtitle?self.buildSubtitle(panel.contents[idx].subtitle):"";
					
				
					var scriptHtml='<script>var h = $(".lui_panel_vertical_navs_item_c .lui_panel_hasCount_frame").html();</script>'
					var nodeHtml = titleiconHtml+'<span class="lui_tabpanel_navs_item_title">' + title +html + '</span>' +subtitleHtml;
					
					titleNode.html(nodeHtml);
				}
				*/
			}
		}
	};
	
	
	
	exports.setContentCount = setContentCount;
});