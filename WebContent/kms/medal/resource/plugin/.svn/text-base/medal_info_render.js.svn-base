if( data.data == null || data.data.datas.length <= 0 ) { 
	done();
	return;
}
	var arr = data.data.datas;
	
	var size = data.data.size;
	var container = '<ul class="lui_zone_honor">';
	var content = '';
	var server = "";
	if(data.server_url) {
		server = data.server_url;
	}
	var param = "";
	if(data.isCurrent) {
		param = "isCurrent=true";
	} else{
		param = "otherUserId=" + data.orgId;
	}
	if(data.fdSex) {
		param += "&fdSex="+ data.fdSex;
	}
	for(var i = 0; i < arr.length; i ++) {
		var _more = "";
		var left = i > 0 ? ((27 * (i + 1)) + 89) : 0;
		var arrLeft = i > 0 ? (27 *(i + 1) + 108) : 0;
		var style = left ? "style='left:-" + left + "px'" : "";
		var arrStyle = left ? "style='left:" + arrLeft + "px'" : "";
		if(size > 4)
			 _more = "<div class='lui_honor_more' ><a target='_blank' " +
					"href='"+ env.fn.formatUrl(server + '/kms/medal/?') + param + "'>" + "更多</a></div>";
		content +=(['<li>',
			    '<a class="lui_zone_honor_box" style="display:block"',
			    	'target="_blank"',
			    	'href="',
			    	env.fn.formatUrl(server + "/kms/medal/kms_medal_main/kmsMedalMain.do?method=view&fdId="),arr[i].fdId,'">',
			        '<img src="',env.fn.formatUrl(server + "/kms/medal/kms_medal_main/kmsMedalMain.do?method=getPicPath&fdKey=smallMedalPic&fdId="),arr[i].fdId,'">',
			    '</a>',
			    '<div class="lui_zone_honor_dialog" ' + style +'>',
	            '<div class="lui_zone_honor_dialog_inner">',
	                '<i class="lui_zone_honor_arr lui_zone_honor_arrborder" '  + arrStyle + '>',
	                '</i><i class="lui_zone_honor_arr lui_zone_honor_arrcover" ' + arrStyle + '></i>',
	                '<div class="lui_zone_honor_iconbox">',
	                	'<img src="',env.fn.formatUrl(server + '/kms/medal/kms_medal_main/kmsMedalMain.do?method=getPicPath&fdKey=bigMedalPic&fdId='),arr[i].fdId,'">',
	                '</div>',
	                '<div class="lui_zone_honor_content">',
	                    '<h6>',
	                    	'<a target="_blank" href="',env.fn.formatUrl(server + "/kms/medal/kms_medal_main/kmsMedalMain.do?method=view&fdId="),arr[i].fdId,'">',
	                    		env.fn.formatText(arr[i].fdName),
	                    	'</a>',
	                    '</h6>',
	                    '<p>',env.fn.formatText(arr[i].fdIntroduction),'</p>',_more,
	                '</div>',
	            '</div>',
	            '</div>',
			'</li>'].join(''));
	}
container = container + content + '</ul>';

done(container);
	
var __renderId = render.parent.cid;


$("#" +  __renderId).find(".lui_zone_honor li").each(
	function() {
		var self = $(this);
		//每个勋章的show和hide定时器
		var s_timeout, h_timeout;
		self.find(".lui_zone_honor_box,.lui_zone_honor_dialog").bind("mouseover", function(e) {
				clearTimeout(h_timeout);
				s_timeout = setTimeout(function() {
					self.find(".lui_zone_honor_dialog").show();
				}, 500);
			
		}).bind("mouseout", function(e) {
				clearTimeout(s_timeout);
				h_timeout = setTimeout(function() {
					self.find(".lui_zone_honor_dialog").hide();
				}, 500);
			
		});
	}
);

