define( [ "dojo/_base/declare","dojo/_base/lang"],
		function(declare,lang) {
			var dialogSelect = declare("mui.commondialog.DialogSelectMixin", null, {
				type : "1" ,
				
				
				modelName:null,
				
				isMul: false , 
				
				dataURL:null,
				
				dataUrl:null,
				
				templURL : "/sys/mobile/js/mui/commondialog/template/dialog_sgl.jsp?modelName=!{modelName}",
				
				_setIsMulAttr:function(mul){
					this._set('isMul' , mul);
					if(this.isMul){
						this.templURL =  "/sys/mobile/js/mui/commondialog/template/dialog_mul.jsp?modelName=!{modelName}";
					}else{
						this.templURL =  "/sys/mobile/js/mui/commondialog/template/dialog_sgl.jsp?modelName=!{modelName}";
					}
				},
				_selectCate: function() {
					if(lang.isFunction(this.dataURL)){
						this.dataUrl = this.dataURL.apply(this);
					}else{
						this.dataUrl = this.dataURL;
					}
					if(this.dataUrl!=null){
						this.inherited(arguments);
					}
				}
			});
			return dialogSelect;
	});