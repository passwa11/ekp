CKEDITOR.dialog.add(
				'kemlink',
				function(editor) {
					var config = editor.config;
					var dialog;
					var date = new Date().getTime();
					var html = 
					"<table id='citeKemDialog'>"
						+"<tr>"
							+"<td>"+editor.lang.kemlink.citeName+"</td><td><input id='rtf_dialog_citeName_"+date+"' class='citeNameCss' style='border-bottom:0.5px solid lightgrey;' type='text' name='citeName'/></td>"
						+"</tr>"
						+"<tr>"
							+"<td>"+editor.lang.kemlink.citeKem+"</td>"
							+"<td>"
								+"<input id='rtf_dialog_citeKem_"+date+"' class='citeKemCss' style='border-bottom:0.5px solid lightgrey;' type='text' name='citeKem' disabled='disabled'/>"
								+"<a href='javascript:void;' onclick='top.testDialog("+date+");' class='citeButtonCss'>"+editor.lang.kemlink.cite+"</a>"
							+"</td>"
						+"</tr>"
					+"</table>"
					+"<div style='margin-top:20px;padding-top:10px;text-align:center;color:red;'>"+editor.lang.kemlink.description+"</div>"

					var kemlinkSelector = {
						type : 'html',
						id : 'kemlinkSelector',
						html : html,
						onLoad : function(event) {
							dialog = event.sender;
						},
						style : 'width: 100%; border-collapse: separate;'
					};

					return {
						title : editor.lang.kemlink.title,
						minWidth : 300,
						minHeight : 180,
						contents : [ {
							id : 'tab1',
							label : '',
							title : '',
							expand : true,
							padding : 0,
							elements : [ kemlinkSelector ]
						} ],

						// 确定后插入到编辑器中
						onOk : function() {
							var citeName = document.getElementById("rtf_dialog_citeName_"+date).value;
							var citeKem = document.getElementById("rtf_dialog_citeKem_"+date).value;
							if(citeKem == "" || citeKem == null){
								alert(editor.lang.kemlink.emptyCiteKemTip);
								return false;
							}
							if(citeName == "" || citeName == null){
								var flag = confirm(editor.lang.kemlink.emptyCiteNameTip);
								if(!flag){
									return false;
								}
								window.KEM_DATA.citeName = window.KEM_DATA.docSubject;
							}else{
								window.KEM_DATA.citeName = citeName;
							}

							var appendHtml = "<a href='"+Com_Parameter.ContextPath.substring(0,Com_Parameter.ContextPath.length-1)+window.KEM_DATA.linkStr+"' target='_blank'>"+window.KEM_DATA.citeName+"</a>";
							var element = new CKEDITOR.dom.element.createFromHtml(appendHtml);
							editor.insertElement(element);
						},

						onShow : function() {
							document.getElementById("rtf_dialog_citeName_"+date).value = "";
							document.getElementById("rtf_dialog_citeKem_"+date).value = "";
						},

						onLoad : function() {
						}
					};
				});

