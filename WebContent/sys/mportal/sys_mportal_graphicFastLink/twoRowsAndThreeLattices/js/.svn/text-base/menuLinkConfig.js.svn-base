/**
 * 打开系统链接选择框
 * @return
 */
function openSelectSystemLinkDialog(nameField,linkField,jsp,title){
	var _dialogWin = window.parent || dialogWin || window;
	seajs.use(['lui/dialog','lui/jquery'],function(dialog){
		dialog.iframe(jsp, title, function(val){
			if(!val){
				return;
			}
			var title = "";
			var link = "";
			if(val.length>0){
				title = val[0].name;
				link = val[0].link;
			}
			$("#"+nameField).val(title);
			$("#"+linkField).val(link);
		}, {width:750,height:580,"topWin":_dialogWin});
	});
}

/**
 * 打开图标选择框
 * @return
 */
function openSelectIconDialog(iconField,jsp,title){
	var _dialogWin = window.parent || dialogWin || window;
	seajs.use(['lui/dialog','lui/jquery'],function(dialog){
		dialog.iframe(jsp, title, function(returnData){
			if(!returnData){
				return;
			}
			var iconType = returnData.iconType || returnData.type; // 1、图片图标      2、字体图标（素材图片）     3、文字
			var val = "";
			if(iconType == 1){  // 图片图标
				val = returnData.className;
			}else if(iconType == 2){        // 字体图标 ||（素材图片）
				val = returnData.className;
				if(typeof val == "undefined" || !val){
					val = returnData.url;
					if(!!val){
						iconType = 'sc00';
					}
				}
			}else if(iconType == 3){  // 文字
				val = returnData.text;
			}
			setIconContentNew(iconField,iconType,val);
		}, {width:750,height:580,"topWin":_dialogWin});
	});
}

/**
 * 新的设置内容区图片，兼容素材图片回显
 * @param iconField
 * @param iconType
 * @param val
 */
function setIconContentNew(iconField,iconType,val){
	var claz2 = val;
	var $iconPanel = $("#"+iconField+"_panel");
	var claz1 = $iconPanel.attr('claz');
	$iconPanel.attr('claz',claz2);
	$iconPanel.removeClass(claz1);
	if(claz2.indexOf('mui')>=0){ // 图标
		$iconPanel.text("");
		$iconPanel.addClass(claz2);
	}else{ // 文字
		$iconPanel.text(claz2);
	}
	$("#"+iconField).val(claz2);
	//素材展示逻辑
	if(iconType == 'sc00'){
		//素材有些是透明色，需要置换背景色
		//val-src-已经携带“/”开头
		$iconPanel.closest(".icon").css("background-color","white");
		$iconPanel.html($("<img src='" + Com_Parameter.ContextPath.substr(0,Com_Parameter.ContextPath.length-1) + val + "' width=\"50\" height=\"50\" alt=\"\">"));
	}else{
		$iconPanel.closest(".icon").css("background-color","#1d9d74");
	}
}

/**
 * 旧的内容区图标设置逻辑
 * @param iconField
 * @param val
 */
function setIconContent(iconField,val){
	var claz2 = val;
	var $iconPanel = $("#"+iconField+"_panel");
	var claz1 = $iconPanel.attr('claz');
	$iconPanel.attr('claz',claz2);
	$iconPanel.removeClass(claz1);
	if(claz2.indexOf('mui')>=0){ // 图标
		$iconPanel.text("");
		$iconPanel.addClass(claz2);
	}else{ // 文字
		$iconPanel.text(claz2);
	}
	$("#"+iconField).val(claz2);
	//素材图片展示逻辑
	if(val.indexOf('/') > -1){
		//素材有些是透明色，需要置换背景色
		$iconPanel.closest(".icon").css("background-color","white");
		$iconPanel.html($("<img src='" + Com_Parameter.ContextPath.substr(0,Com_Parameter.ContextPath.length-1) + val + "' width=\"50\" height=\"50\" alt=\"\">"));
	}else{
		$iconPanel.closest(".icon").css("background-color","#1d9d74");
	}
}