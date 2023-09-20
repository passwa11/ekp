(function () {
	Com_IncludeFile("jquery.js|data.js");
	var tools = {
			success : function(msg) {
				window.alert(msg);
			},
			failure : function(msg) {
				window.alert(msg);
			},
			loading : function() {
				
			}
	};
	if(window.seajs) {
		window.seajs.use(['lui/dialog'],function(dialog) {
			tools.success = dialog.success;
			tools.failure = dialog.failure;
			tools.loading = dialog.loading;
		});
	}
	
	function importwordDialog(editor) { 
    	var _rtfImportWordForm ='_rtfImportWordForm_'+editor.name; 
    	var html = [];
    	var cansubmit = true;
    	var config = editor.config;
    	config.initClass = "lui_attachment_upload_btn_init";
    	var action = Com_Parameter.ContextPath+"sys/common/import.do?method=importWord";
    	html.push('<div class="custom_popupBox">');
    	html.push('<form action="'+action+'" target="importWordInnerFrame" enctype="multipart/form-data" name="'+_rtfImportWordForm+'" method="post" onsubmit="return rtf_importWord_checkForm();">');
    	html.push('<input name="file" accept=".doc,.docx" type="file" class="upload" />');
    	html.push('</form>');
    	html.push('<iframe name="importWordInnerFrame" style="display:none"></iframe>');
    	html.push('</div>');
    	
    	var importwordSelector = {
			type : 'html',
			id : 'importwordSelector',
			html : html.join(''),
			onLoad : function(event) {
				dialog = event.sender;
			},
			validate: function() {
				return cansubmit;
            }
		};
    	window.rtf_importWord_checkForm = function() {
    		return false;
    	};
    	
    	window.rtf_importWord_callback = function(data) {
    		if(data != null && data != '') {
    			//获得HTML字符串
    			$.get(Com_Parameter.ContextPath+"resource/ckeditor/images/"+data+"/doc.html",function(htmlStr) {
    				// #65731 有的word多次上传之后由于某些标签会让editor.insertHtml报错
    				// 这里用jQuery一个个合并
    				var html = $(htmlStr);
    				var temp = '<div>';
    				html.each(function() {
    					temp += $(this).prop("outerHTML");
    				});
    				temp += "</div>";
    				// 用jquery处理之后对于2003的word，会出现很多undefined字符串，这里剔除
    				temp = temp.replace(/undefined/ig,"");
    				editor.insertHtml(temp);
    			}).success(function() {
    				if(window.import_word_load != null) {
    					window.import_word_load.hide();
    				}
    				tools.success(Com_Parameter.OptSuccessInfo);
    			}).error(function() {
    				if(window.import_word_load != null) {
    					window.import_word_load.hide();
    				}
    				tools.failure(Com_Parameter.OptFailureInfo);
    			}).complete(function() {
    				//删除生成的html文件
    				$.post(Com_Parameter.ContextPath+"sys/common/import.do?method=deleteFile&folder="+data);
    			});
    		} else {
    			if(window.import_word_load != null) {
					window.import_word_load.hide();
				}
    			tools.failure(Com_Parameter.OptFailureInfo);
    		}
    	};
    	
        return {
            title: editor.lang.importword.label,
            minWidth: 300,
            minHeight: 200,
            contents : [{
				id : 'tab1',
				label : '主数据',
				title : '',
				expand : true,
				padding : 0,
				elements : [importwordSelector]
			}],
            buttons: [
            CKEDITOR.dialog.okButton,
            CKEDITOR.dialog.cancelButton],
            
            onLoad: function () {
                //console.log('onLoad');
            },
            onShow: function () { 
            	document[_rtfImportWordForm].file.value = '';
            },
            onHide: function () { 
            },
            onOk: function () {
            	
            	if(document[_rtfImportWordForm].file.value != null && document[_rtfImportWordForm].file.value != '') {
            		document[_rtfImportWordForm].submit();
            		window.import_word_load = tools.loading();
            	}
            		
            },
            onCancel: function () {
            	//console.log('onCancel');
            },
            resizable: CKEDITOR.DIALOG_RESIZE_HEIGHT
        };
    }
   
    CKEDITOR.dialog.add('importword', function (editor) {
        return importwordDialog(editor);
    });
})();
