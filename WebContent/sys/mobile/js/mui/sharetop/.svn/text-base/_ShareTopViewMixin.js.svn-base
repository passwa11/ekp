define( [ "dojo/_base/declare", 'dojo/topic', 'dojo/dom-style',
		"dojo/_base/lang" ], function(declare, topic, domStyle, lang) {
	return declare("mui.list._TopViewMixin", null, {
		adjustDestination : '/mui/list/adjustDestination',

		listTop : '/mui/list/toTop',

		toCreate : function(evt) {
			var url = dojoConfig.baseUrl ? dojoConfig.baseUrl : '/';
			url += "third/ywork/ywork_doc/yworkDoc.do?method=findDocURL";
			url += "&ismb=1&fdModelId="+this.fdModelId+"&fdModelName="+this.fdModelName+"&fdTemplateId="+this.fdTemplateId+"&fdAllPath="+encodeURI(this.fdAllPath);
			$.post(url,function(data){
				status = data.status;
				if(status=="1"){
					location.href = data.codeUrl;
				}else if(status=="2"){
					ywork.toast(data.msg);
				}else{
					ywork.toast("Error");
				}
			},"json");
		},

		connectToggle : function() {
			
		}
	});
});