/**
 * 表单Item
 */
seajs.use(['lui/jquery', 'lui/dialog','lang!sys-xform-base'], function($, dialog,lang) {
	window.buildNode = function(data){
		var node = $('<div/>').attr("class","cards");
		node.attr("data-formid",data.value);
		$left = $("<span class='left'/>").appendTo(node);
		//更新样式
		var $center = $("<div class='center'>").appendTo(node);
		$center.append('<p>' + data.name + '</p>');
		var $view = $('<a href="javascript:void(0);">' + lang["mui.xform.common.view"] + '</a>');
		$center.append($view);
		//view
		$view.on('click',function(){
			var url = Com_Parameter.ContextPath + "sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=view"
					+ "&fdId=" + data.value + "&fdModelName=" + modelInfo.fdModelName + "&fdMainModelName="
					+ modelInfo.fdMainModelName + "&fdKey=" + modelInfo.fdKey
			top.open(url,"url");
		});
		//遮罩
		var $right = $('<div class="right">' + lang["mui.xform.common.use"] + '</div>');
		node.append($right);
		
		$right.on('click',function(){
			var context = window.parent.document;
			var url = Com_Parameter.ContextPath + $("[name='add_url']",context).val();
			url += "&sourceFrom=common&sourceKey=Common&type=1&mode=2&fdCommonTemplateId=" + data.value;
			if(Com_Parameter.dingXForm === "true") {
				//因为钉钉审批高级版后台页面最外层是moduleindex，与\sys\profile\index.jsp不同，frameWin[funcName]能找到对应方法，会导致新建模板页面在viewFrame中打开
				Com_OpenWindow(url, "_blank");
			} else {
				Com_OpenWindow(url);
			}
			hide();
		});
		return node;
	};
	
	var element = render.parent.element;
	$(element).html("");
	var formInfos = data.data;
	if(formInfos == null || formInfos.length == 0){
		done();
		noRecode($(element));
	}else{
		var context = $('<div>').attr('class', 'cm-common-template').appendTo(element);
		var wrap = $('<div>').attr('class', 'common-content').appendTo(context[0]);
		for(var i = 0; i < formInfos.length; i++){
			var formObj = formInfos[i];
			var formInfo = {"name":formObj.fdName,"value":formObj.fdId};
			var node = buildNode(formInfo);
			node.appendTo(wrap);
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