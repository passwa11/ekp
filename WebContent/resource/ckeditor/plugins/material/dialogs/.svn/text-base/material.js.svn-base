(function () {
	function findDialogByName(name) {
		if (!name) {
			return $();
		}
		name = name.replace("extendDataFormInfo.","");
		return $("[class*='" + name + "_dialog']");
	}

    function materialDialog(editor) {
		var dialog;
    	var materialSelector = {
			type : 'html',
			id : 'materialSelector',
			onLoad: function (event){
				dialog = event.sender;
			},
			html: "<iframe id='myiframe' frameborder='0' src='" + Com_Parameter.ContextPath + "sys/portal/sys_portal_material_main/rtf_plugin.jsp'></iframe>",
			style: "width: 700px;height: 550px;padding: 10;"
		};

		// 点击图片
		window.selectMaterial = function(id, url) {

			// var src = fileList[i]['url'];
			var img = editor.document.createElement('img',
				{
					attributes : {
						src : url,
						'data-cke-saved-src' : url
					}
				});
			editor.insertElement(img);

			dialog.hide();  //采用下面的移除弹窗的话，调用editor.destroy()会卡死浏览器
			// 点击图片，立即写到RTF中
			// var element = new CKEDITOR.dom.element.createFromHtml('<img src="' + url + '">');
			// editor.insertElement(element);
			// 移除弹窗
			// findDialogByName(editor.name).remove();
			// $(".cke_dialog_background_cover").remove();
		};

		//点击预览图片
		window.onPreview = function (event, url, name) {
			event.stopPropagation();
			var data = {
				data : [{value : url}],
				value : url,
				valueType: 'url'
			};
			seajs.use([ 'lui/imageP/preview' ],function(preview) {
				preview({
					data : data
				});
			});
		};

        return {
            title: '选择系统图片',
            minWidth: 700,
            minHeight: 550,
            contents : [{
				id : 'tab1',
				label : '系统图片',
				title : '',
				expand : true,
				padding : 0,
				elements : [materialSelector]
			}],
            resizable: CKEDITOR.DIALOG_RESIZE_HEIGHT,
			buttons : [CKEDITOR.dialog.cancelButton]
        };
    }
   
	CKEDITOR.dialog.add('material', function (editor) {
		return materialDialog(editor);
	});
})();
