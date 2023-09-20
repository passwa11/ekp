/**
 * 表单Item
 */
seajs.use(['lui/jquery', 'lui/dialog','lang!third-ding'], function($, dialog,lang) {
	window.buildNode = function(data){
		var node = $('<div/>').attr("class","cm-items");
		node.attr("data-formid",data.name);
		var context = window.parent.document;
		var createUrl = $("[name='add_url']",context).val();
		var url = "";
		//内容
		var $item = $("<div class='item-box'>").appendTo(node);
		if (data.previewUrl != "") {
			$item.append('<img src="' + Com_Parameter.ContextPath + data.previewUrl + '"></img>');
		} else {
			$item.append('<span class="icon-pic"></span>');
		}
		var $content = $('<div class="content">');
		$item.append($content);
		var html = [];
		html.push("<p class='cm-form-box-item-desc'>" + data.text + "</p>");
		html.push("<div class='cm-form-box-item-title'>");
		html.push("</div>");
	    $content.append(html.join(""));
	    $item.on('click',function(){
			//新建模板
			url = Com_Parameter.ContextPath + createUrl + "&sourceFrom=ding&type=2&sourceKey=ding&template=" + data.name;
			if(Com_Parameter.dingXForm === "true") {
				//因为钉钉审批高级版后台页面最外层是moduleindex，与\sys\profile\index.jsp不同，frameWin[funcName]能找到对应方法，会导致新建模板页面在viewFrame中打开
				Com_OpenWindow(url, "_blank");
			} else {
				Com_OpenWindow(url);
			}
			window.parent.$dialog.hide();
		});
		return node;
	};
	var element = render.parent.element;
	$(element).html("");
	var formInfos = data;
	if(formInfos == null || formInfos.length == 0){
		done();
		noRecode($(element));
	}else{
		var ul = $(element);
		for(var i = 0; i < formInfos.length; i++){
			var formObj = formInfos[i];
			var node = buildNode(formObj);
			node.appendTo(ul);
		}
	}
});

function hide() {
	var context = window.top.document;
	seajs.use(['lui/jquery'],function($){
		$("[data-lui-mark='dialog.nav.close']",context).trigger("click");
	});
}



function  noRecode(context) {
	seajs.use(['lui/util/env','theme!listview'],function(env,listview){
		var	__url = '/resource/jsp/listview_norecord.jsp?_='+new Date().getTime();
		$.ajax({
					url : env.fn.formatUrl(__url),
					dataType : 'text',
					success : function(data, textStatus) {
									context.html(data);
							  }
		});
	})
};