define( [ "dojo/_base/declare", "dojo/_base/lang", "mui/category/CategoryList","mui/category/CategoryAllSelectMixin"], function(declare, lang,
		CategoryList,CategoryAllSelectMixin) {
	return declare("mui.selectdialog.DialogList", [ CategoryList,CategoryAllSelectMixin ], {
		//数据请求URL
		//dataUrl : '/sys/organization/mobile/address.do?method=addressList&parentId=!{parentId}&orgType=!{selType}',
		dataUrl :null,
		//不能参与选择的id
		exceptValue : "",
		//字段参数
		fieldParam:null,
		
		 postCreate: function() {
		      this.inherited(arguments)
	  		  this.subscribe("/mui/search/submit","_cateChange");
	  	      this.subscribe("/mui/search/cancel","_cateReback");
		 },
		 
		 startup : function() {
			if (this._started) {
				return;
			}
			this.inherited(arguments);
			this.reload();
		},
		
		buildQuery:function(){
			var params = this.inherited(arguments);
			var exParams = {exceptValue : this.exceptValue};
			if(this.fieldParam!=null&&this.fieldParam!=""){
				var fieldParams =  this.fieldParam.split(",");
				for(var i=0;i<fieldParams.length;i++){
					var idx = fieldParams[i].indexOf(":");
					if(idx>0){
						var fieldObj = document.getElementsByName(fieldParams[i].substring(idx+1));
						if(fieldObj.length>0){
							var fieldVal = "";
							if(fieldObj[0].type=="radio"||fieldObj[0].type=="checkbox"){
								for(var x=0;x<fieldObj.length;x++){
									if(fieldObj[x].checked) fieldVal += (""==fieldVal?"":";")+fieldObj[x].value;
								}
							}else if(fieldObj[0].tagName=="SELECT"){
								for(var x=0;x<fieldObj[0].options.length;x++){
									if(fieldObj[0].options[x].selected) fieldVal = fieldObj[0].options[x].value;
								}
							}else{
									fieldVal = fieldObj[0].value;
							}
							if(fieldVal!=""){
								exParams[fieldParams[i].substring(0,idx)] = fieldVal;
							}
						}
					}
				}				
			}			
			return lang.mixin(params , exParams);
		}
	});
});