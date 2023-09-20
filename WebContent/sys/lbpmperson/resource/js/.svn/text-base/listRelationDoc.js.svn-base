var _sameTemplateObj = {};
function listRelationDocument(fdTemplateId,fdMainModelName,url,searchTxt){
	_sameTemplateObj.fdTemplateId = fdTemplateId;
	_sameTemplateObj.fdMainModelName = fdMainModelName;
	_sameTemplateObj.url = url;
	var srcElement = event.srcElement;
	var doc = window.document;
	if (typeof originHeight === "undefined") {
		originHeight = doc.documentElement.scrollHeight || doc.body.scrollHeight;
	}
	var $relationDiv = $(srcElement).closest("li").find("div[name='relationDocument']");
	if ($relationDiv.is(':hidden')){
		$relationDiv.show();
		$(srcElement).removeClass("unlistImg");
		$(srcElement).addClass("listImg");
	}else{
		if (typeof searchTxt == "undefined"){
			$relationDiv.hide();
			$(srcElement).removeClass("listImg");
			$(srcElement).addClass("unlistImg");
			return ;
		}
	}
	$relationDiv.find("ul[name='relations']").remove();
	var param = {};
	param.fdTemplateId = fdTemplateId;
	param.fdMainModelName = fdMainModelName;
	
	if (searchTxt){
		param.searchTxt = searchTxt;
	}
	
	$.ajax({
		url: Com_Parameter.ContextPath + "sys/lbpmperson/SysLbpmPersonCreate.do?method=listUsualByTemplateId",
		type: 'POST',
		async: false,
		dataType: 'json',
		data: param,
		success:function(data){
			var html = [];
			html.push("<div style='margin-top:50px;text-align:left;'>");
			html.push("<ul name='relations'>");
			for (var i = 0; i < data.length; i++){
				var obj = data[i];
				html.push("<li>");
				html.push("<a class='fileIcon'></a>");
				var copyUrl  = url + "&fdCopyId=" + obj.fdId
				html.push("<a class='textEllipsis' style='display:inline-block;width:90%;' href='" + copyUrl + "' target='_blank' title='" + obj.docSubject + "'>");
				html.push(obj.docSubject);
				html.push("</a>");
				html.push("</li>");
			}
			html.push("</ul>")
			html.push("</div>");
			$relationDiv.append(html.join(''));
			resizeBodyHeight();
		}
	});
}

function isAllRelationDivHide() {
	var isAllHide = true;
	$(document).find("div[name='relationDocument']").each(function(index,obj){
		if (!$(obj).is(':hidden')) {
			isAllHide = false;
			return false;
		}
	});
	return isAllHide;
}

function resizeBodyHeight(height) {
	var doc = window.document;
	var _height = doc.documentElement.scrollHeight || doc.body.scrollHeight;
	var isEdge = navigator.userAgent.indexOf("Edge") > -1;//判断是否IE的Edge浏览器
	if(isEdge){
		_height = doc.documentElement.scrollHeight;
	}
	if (typeof height !== "undefined") {
		_height = height;
	}
	if($(doc.body).css("height") != (_height + "px")){
		$(doc.body).css("height",_height);
		doc.documentElement.scrollHeight = _height;
	}
}

function relationSearch(evt){
	var event = evt ||  Com_GetEventObject();
	if (event && event.keyCode == 13){
		QRelationsearch(event);
	}
}

function QRelationsearch(event){
	var event = event || Com_GetEventObject();
	var srcDom = event.srcElement||event.target;
	var $contextObj = $(srcDom).closest("div[name='relationDocument']");
	srcDom.blur();
	var searchTxt = $("#relationSearchTxt",$contextObj).val();
	if(searchTxt !="" && $.trim(searchTxt) != ""){
		var fdTemplateId = _sameTemplateObj.fdTemplateId;
		var fdMainModelName = _sameTemplateObj.fdMainModelName;
		var url = _sameTemplateObj.url;
		listRelationDocument(fdTemplateId,fdMainModelName,url,searchTxt);
	}else{
		 seajs.use(['lui/dialog'], function(dialog) {
			dialog.alert(enterKeywordFirst);
		 });
		return;
	}
}