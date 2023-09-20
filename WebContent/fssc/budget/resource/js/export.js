define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var dialog = require('lui/dialog');
	var lang = require('lang!');

	var encodeHTML = function(str){ 
			return str.replace(/&/g,"&amp;")
				.replace(/</g,"&lt;")
				.replace(/>/g,"&gt;")
				.replace(/\"/g,"\"")
				.replace(/\'/g,"&#39;")
				.replace(/¹/g, "&sup1;")
				.replace(/²/g, "&sup2;")
				.replace(/³/g, "&sup3;");
		};
	
	//如果最后一列是操作类型，如删除/添加，需要去掉最后一列的话，请调用：listExportExOperation
	var listExport = function(url, id){
		window.export_load = dialog.loading();
 			var json = new Array();
 			var values;
 			var prefix = "";
 			if(id){
				prefix = "#" + id + " ";
			}
 			var ths = LUI.$(prefix+".lui_listview_columntable_table").find("th");
 			var thsValues=null;
 			var index=0;
 			LUI.$(prefix + "input[name='List_Selected']:checked").each(function(j){
			 var tds = LUI.$(this).parent().parent().find("td");
			 var data = new Array();
			 for (var i = 1; i < ths.length; i++) {
				 var input=LUI.$(ths[i]).find("input[type='checkbox'][name='List_Tongle']");
					if(LUI.$(input).length==0){
						if(tds[i].innerText=='' && LUI.$(tds[i]).children("img").length>0){
							if(LUI.$(tds[i]).children("img").eq(0).attr("title")){
							data.push([ths[i].innerText,LUI.$(tds[i]).children("img").eq(0).attr("title")]);
							}else{
							data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
							}
						}else{
							data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
						}
					} 
					}
			 json.push(["json"+j,data]);
			 index=j;
			});
 			if(json.length!=0){
 				for (var i = 1; i < ths.length; i++) {
 					if(thsValues==null){
 						thsValues=ths[i].innerText;
 					}else{
 						thsValues=thsValues+","+ths[i].innerText;
 					}
 			}
 			 openWindowWithPost(url,"post","json",encodeURI(LUI.stringify(json)),"ths",encodeURI(thsValues));
 			  if(window.export_load!=null){
					window.export_load.hide(); 
				}
 			}else{
 				 if(window.export_load!=null){
 					window.export_load.hide(); 
 				}
 				dialog.alert(lang["page.noSelect"]);
 				return;
 			}
	};
	
	//如果最后一列是操作类型，如删除/添加，则导出时去掉最后一列
	var listExportExOperation = function(url,id){
		window.export_load = dialog.loading();
 			var json = new Array();
 			var values;
			var prefix = "";
			if(id){
				prefix = "#" + id + " ";
			}
 			var ths = LUI.$(prefix + ".lui_listview_columntable_table").find("th");
 			var thsValues=null;
 			var index=0;
 			LUI.$(prefix + "input[name='List_Selected']:checked").each(function(j){
			 var tds = LUI.$(this).parent().parent().find("td");
			 var data = new Array();
			 for (var i = 1; i < ths.length - 1; i++) {
				 var input=LUI.$(ths[i]).find("input[type='checkbox'][name='List_Tongle']");
					if(LUI.$(input).length==0){
						if(tds[i].innerText=='' && LUI.$(tds[i]).children("img").length>0){
							if(LUI.$(tds[i]).children("img").eq(0).attr("title")){
							data.push([ths[i].innerText,LUI.$(tds[i]).children("img").eq(0).attr("title")]);
							}else{
							data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
							}
						}else{
							data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
						}
					} 
					}
			 json.push(["json"+j,data]);
			 index=j;
			});
 			if(json.length!=0){
 				for (var i = 1; i < ths.length - 1; i++) {
 					if(thsValues==null){
 						thsValues=ths[i].innerText;
 					}else{
 						thsValues=thsValues+","+ths[i].innerText;
 					}
 			}
 			 openWindowWithPost(url,"post","json",encodeURI(LUI.stringify(json)),"ths",encodeURI(thsValues));
 			  if(window.export_load!=null){
					window.export_load.hide(); 
				}
 			}else{
 				 if(window.export_load!=null){
 					window.export_load.hide(); 
 				}
 				dialog.alert(lang["page.noSelect"]);
 				return;
 			}
	};
	
	var openWindowWithPost = function(url,name,key,value,thkey,thvalue){
	    var newWindow = window.open(name);  
	    if (!newWindow)  
	        return false;  
	    var html = "";  
	    html += "<html><head></head><body><form id='formid' method='post' action='" + url + "'>";  
	    if (key && value)  
	    {  
	       html += "<input id='"+key+"' type='hidden' name='" + key + "' value='" +value+ "'/>";
	      
	    }
	    if(thkey && thvalue){
	       html += "<input id='"+thkey+"' type='hidden' name='" + thkey + "' value='" +thvalue+ "'/>";
	    }  
	    html += "</form><script type='text/javascript'>document.getElementById('formid').submit();";  
	    html += "<\/script></body></html>".toString().replace(/^.+?\*|\\(?=\/)|\*.+?$/gi, "");   
	    newWindow.document.write(html);  
	    return newWindow; 
	};
	
	var hideExport = function(value){
		setTimeout(function(){
			if("columntable"==value&&LUI.$("#export").length>0){
				LUI.$("#export").attr("style","display:block;");
			}else{
				LUI.$("#export").attr("style","display:none;");
			}
		},1);
		
	}
	
	
	exports.listExport = listExport;
	exports.listExportExOperation = listExportExOperation;
	exports.openWindowWithPost = openWindowWithPost;
	exports.hideExport = hideExport;
	
});