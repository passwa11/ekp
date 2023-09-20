CKEDITOR.dialog.add( 'autoformat', function(editor)
		{

	
	return {
		title :'',
		minWidth : 350,
		minHeight : 180,
		contents : [
		            {
		            	id : editor.id + '_myModal',
		            	label : editor.lang.autoformat? editor.lang.autoformat.immediately:"一键排版",
		            	title : editor.lang.autoformat? editor.lang.autoformat.immediately:"一键排版",
		            	elements :
		            		[
		            		{
		            			type: "html",
	                            html: "<iframe id='" + editor.id + "_myiframe' width='100%' height='100%' frameborder='0' src='" + Com_Parameter.ContextPath + "resource/ckeditor/plugins/autoformat/dialogs/autoformat.jsp"+"'></iframe>",
	                            style: "width:350px;height:180px;padding:0;"
			            	}
		            		]

		            }
		            ],
		            // 弹出框加载完后执行
		            onLoad: function () {
						var diaPanel = document.querySelector("div.cke_reset_all." + editor.id);

		            	var cke_dialog_close_button=document.querySelector("a.cke_dialog_close_button");
		            	
		            	//var cke_dialog_close_buttons=document.querySelector("div.cke_dialog_title");
						var cke_dialog_close_buttons=document.getElementsByName("cke_dialog_title_" + editor.id)[0];
		            	
		            	//var cke_dialog_body=document.querySelector("div.cke_dialog_body");
						var cke_dialog_body=document.getElementById("cke_dialog_body_"+editor.id);
		            	
		            	cke_dialog_body.removeChild(cke_dialog_close_buttons);
		            	
		            	
		            },
		            resizable: CKEDITOR.DIALOG_RESIZE_NONE ,
		            onOk : function()
		            {
		            	
		            	
		            	formatText(editor);
		            	
		            	//格式化
		            	function formatText(editor) {
							var myIfaramContent = document.getElementById(editor.id + "_myiframe").contentDocument;
		            		var pBlank = myIfaramContent.getElementById('pBlank').checked? "　　":"";
		            		var imgBorder = 0;// myIfaramContent.getElementById('imgBorder').checked? 1:0;
		            		var imgCenter =  myIfaramContent.getElementById('imgCenter').checked? 1:0;
		            		var imgCenterLeft = imgCenter ? "<p style=\"text-align:center;\">" : "<p>" + pBlank, imgCenterRight = "</p>";
		            		var tableHolder = myIfaramContent.getElementById('tableHolder').checked? 1:0;
		            		var strongHolder = myIfaramContent.getElementById('strongHolder').checked? 1:0;
		            		var linkHolder = myIfaramContent.getElementById('linkHolder').checked? 1:0;
		            		var qhzw = 0;//myIfaramContent.getElementById('qhzw').checked? 1:0;
		            		var mark = "yijianpaiban_mark";
		            		var editorHtml = $("#" + editor.id + "_contents" + ".cke_contents.cke_reset iframe:eq(0)");
		            		
		            		
		            		var body = editorHtml.contents().find("body");
		            		body.html(clearHtml(editor.getData()));
		            		var imgs = new Array();
		            		var tables = new Array();
		            		var strongs = new Array();
		            		var links = new Array();
		            		var styles = new Array();
		            		var i = 0;
		            		var j = 0;
		            		var k = 0;
		            		var z = 0;
		            		var v=0;
		            		if(tableHolder){
		            			$.each(body.find("table"),function(){
		            				tables[tables.length] = $(this).prop("outerHTML");
		            				$(this).replaceWith("\n#"+mark+"_table" + j + "#\n");
		            				j = j + 1;
		            			})
		            		}
		            		if(strongHolder){
		            			$.each(body.find("b"),function(){
		            				$(this).replaceWith("<strong>" + $(this).html() + "</strong>");
		            			})
		            			$.each(body.find("strong"),function(){
		            				strongs[strongs.length] = "<strong>" + $(this).text() + "</strong>";
		            				$(this).replaceWith("#strong" + k + "#");
		            				k = k + 1;
		            			})
		            		}
		            		
		            		if(linkHolder){
		            			
		            			$.each(body.find("a"),function(){
		            				links[links.length] = "<a target="+$(this).attr("target")+" href="+$(this).attr("href")+">" + $(this).text() + "</a>";
		            				$(this).replaceWith("#link" + z + "#");
		            				z = z + 1;
		            			})
		            		}
		            		
		            		
		            		$.each(body.find("style"),function(){
		            			styles[styles.length] = "<style>" + $(this).text() + "</style>";
	            				$(this).replaceWith("#style" + v + "#");
	            				v = v + 1;
	            			})
	            			
	            			var a=0;
	            			var ols = new Array();
		            		$.each(body.find("ol"),function(){
		            			ols[ols.length] = $(this).prop("outerHTML");
	            				$(this).replaceWith("#"+mark+"_ol" + a + "#");
	            				a = a + 1;
	            			})

                            var p=0;
                            var uls = new Array();
                            $.each(body.find("ul"),function(){
                                uls[uls.length] = $(this).prop("outerHTML");
                                $(this).replaceWith("#"+mark+"_ul" + p + "#");
                                p = p + 1;
                            })

                            var q=0;
                            var lis = new Array();
                            $.each(body.find("li"),function(){
                                lis[lis.length] = $(this).prop("outerHTML");
                                $(this).replaceWith("#"+mark+"_li" + q + "#");
                                q = q + 1;
                            })

	            			var f=0;
		            		var videos = new Array();
		            		$.each(body.find("video"),function(){
		            			videos[videos.length] = $(this).prop("outerHTML");
                                $(this).replaceWith("#"+mark+"_video" + q + "#");
                                f = f + 1;
                            })
		            		
		            		$.each(body.find("img"),function(){
		            			imgs[imgs.length] = $(this).attr("src");
		            			$(this).replaceWith("\n#"+mark+"_img" + i + "#\n");
		            			i = i + 1;
		            		})
		            		var text = body.text().replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/	/g, "");
		            		var tmps = text.split("\n");
		            		var newhtml = "";
		            		for (var k = 0; k < tmps.length; k++) {
		            			var tmp = $.trim(tmps[k]);
		            			if (tmp!="" && tmp.indexOf(mark)==-1) {
		            				newhtml += "<p>" + pBlank + tmp + "</p>\n";
		            			}else{
		            				newhtml += tmp;
		            			}
		            		}
		            		
		            		for (k = 0; k < strongs.length; k++) {
		            			newhtml = newhtml.replace("#strong" + k + "#", strongs[k]);
		            		}
		            		for (z = 0; z < links.length; z++) {
		            			newhtml = newhtml.replace("#link" + z + "#", links[z]);
		            		}
		            		for (j = 0; j < tables.length; j++) {
		            			newhtml = newhtml.replace("#"+mark+"_table" + j + "#", tables[j]);
		            		}
		            		for (i = 0; i < imgs.length; i++) {
		            			newhtml = newhtml.replace("#"+mark+"_img" + i + "#", imgCenterLeft+"<img src=\"" + imgs[i] + "\" border=\""+imgBorder+"\">"+imgCenterRight);
		            		}
		            		
		            		for (v = 0; v < styles.length; v++) {
		            			newhtml = newhtml.replace("#style" + v + "#", styles[v]);
		            		}
		            		
		            		for (a = 0; a< ols.length; a++) {
		            			newhtml = newhtml.replace("#"+mark+"_ol" + a + "#", ols[a]);
		            		}

                            for (p = 0; p< uls.length; p++) {
                                newhtml = newhtml.replace("#"+mark+"_ul" + p + "#", uls[q]);
                            }

                            for (q = 0; q< lis.length; q++) {
                                newhtml = newhtml.replace("#"+mark+"_li" + q + "#", lis[q]);
                            }
                            
                            for (f = 0; f< videos.length; f++) {
                                newhtml = newhtml.replace("#"+mark+"_video" + f + "#", videos[f]);
                            }
		            		
		            		if(qhzw){
		            			
		            			newhtml=newhtml.split(',').join("，");
		            			newhtml=newhtml.split('.').join("。");
		            			newhtml=newhtml.split('!').join("！");
		            			newhtml=newhtml.split('?').join("？");
		            			newhtml=newhtml.split(':').join("：");
		            			newhtml=newhtml.split('(').join("（");
		            			newhtml=newhtml.split(')').join("）");
		            			newhtml=newhtml.split('@').join("@");
		            			newhtml=newhtml.split(';').join("；");
		            			 // 把字符串按照双引号截成数组  
		            	        var  newStr = newhtml.split("\"");  
		            	        if(newStr!=""){
		            	        	 var yhStr="";
		            	        	 
				            	        for (var i = 1; i <= newStr.length; i++) { 
				            	        		if (i % 2 == 0) {  
					            	            	yhStr += newStr[i - 1] + "”";  
					            	            	console.log(yhStr);
					            	            } else {  
					            	            	
					            	            	yhStr += newStr[i - 1] + "“"; 
					            	            	console.log(yhStr);
					            	            }  
				            	        }  
				            	        newhtml=yhStr.substring(0, yhStr.length-1);
		            	        }
		            	        
		            		}
		            		
		            		
		            		body.html(newhtml);
		            		$.each(body.find("p"),function(){
		            			if($(this).is(":empty")){
		            				$(this).remove();
		            			}
		            		})

		            		
		            	}
		            	
		            	
		            	
		            	function clearHtml(text){
		            		return text.replace(/\n/g, "").replace(/p>/g, "p>\n").replace(/br>/g, "br>\n").replace(/&nbsp;/g, "").replace(/h1>/g, "h1>\n").replace(/h2>/g, "h2>\n").replace(/h3>/g, "h3>\n").replace(/h4>/g, "h4>\n").replace(/h5>/g, "h5>\n").replace(/h6>/g, "h6>\n");
		            	}
		            	
		            },
		            buttons: [ CKEDITOR.dialog.okButton.override( { label :(editor.lang.autoformat? editor.lang.autoformat.typesetting:'立即排版'),style : 'width: 95px;height:35px;padding: 8px 0px;background: #4184F5;border: 0px;'} ) ],
		           
		            
		            

	}
});
