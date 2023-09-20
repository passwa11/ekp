define([
	"dojo/_base/declare","dijit/registry","dojo/query"
	], function(declare,registry,query) {
	return declare("sys.news.edit", null,{
		init: function(data){
			var self = this
			var fdContentType =data.fdContentType;
			self.sysNews_changeType(fdContentType);
			window.review_submit = function(status) {
				self.review_submit(status);
			}
			window.sysNews_changeAut = function(e) {
				self.sysNews_changeAut(e);
			}
			//切换正文模式
			window.sysNews_changeType = function(e) {
				self.sysNews_changeType(e);
			}
		},
		review_submit: function(status){
			var _validator = registry.byId('scrollView');
			var doc_status = document.getElementsByName("docStatus")[0].value;
			if(!_validator.validate()){
				return;
			}
			this.sysNews_clearAut();
			var method = Com_GetUrlParameter(location.href,'method');
			if (method=='add') {
				document.getElementsByName("docStatus")[0].value = status;
				if(status == '10') {
					Com_Submit(document.forms[0], 'save', null, { saveDraft : true });
				} else {
					Com_Submit(document.forms[0], 'save');
				}
			} else {
				if(status=='10'||status=='11'||doc_status>='30'){
					Com_Submit(document.forms[0], 'update', null, { saveDraft : true });
				}else{
					document.getElementsByName("docStatus")[0].value = status;
					Com_Submit(document.forms[0], 'update');
				}
			}
		},
		sysNews_changeAut: function(e){
			if(e=='true'){
				document.getElementById("innerAuthor").style.display = "none";
				document.getElementById("outerAuthor").style.display = "block";
				query("#outerAuthor div[data-dojo-type='mui/form/Input']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: true,
					});
				});
				query("#innerAuthor div[data-dojo-type='mui/form/Address']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: false,
					});
				});
				// 隐藏部门
				document.getElementById("innerDept").style.display = "none";
				query("#innerDept div[data-dojo-type='mui/form/Address']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: false,
					});
				});
			}else if (e=='false'){
				document.getElementById("innerAuthor").style.display = "block";
				document.getElementById("outerAuthor").style.display = "none";
				query("#outerAuthor div[data-dojo-type='mui/form/Input']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: false,
					});
				});
				query("#innerAuthor div[data-dojo-type='mui/form/Address']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: true,
					});
				});
				// 显示部门
				document.getElementById("innerDept").style.display = "";
				query("#innerDept div[data-dojo-type='mui/form/Address']").every(function(item){
					var widgetObj =  registry.byId(item.id);
					widgetObj.set({
						required: true,
					});
				});
			}
		},
		sysNews_changeType: function(e){
			var mode=document.getElementsByName("fdContentType")[0];
			mode.value = "rtf";
			if(e=='att'){
				mode.value = "att";
				//附件上传模式
				document.getElementById("attEdit").style.display = "";
				document.getElementById("rtfEdit").style.display = "none";
			}else{
				document.getElementById("attEdit").style.display = "none";
				document.getElementById("rtfEdit").style.display = "";
			}
		},
		sysNews_clearAut: function(){
			var _type = document.getElementsByName("fdIsWriter")[0].value;
			if(_type == "true"){
				document.getElementsByName("fdIsWriter")[0].value = "on";
				document.getElementsByName("fdAuthorId")[0].value = "";
				document.getElementsByName("fdAuthorName")[0].value = "";
			}else{
				document.getElementsByName("fdIsWriter")[0].value = "";
				document.getElementsByName("fdWriter")[0].value = "";
			}
		}
	
	});
});