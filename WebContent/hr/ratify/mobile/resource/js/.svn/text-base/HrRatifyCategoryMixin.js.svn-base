define( [ "dojo/_base/declare","dojo/request","dojo/topic","mui/device/adapter","mui/util"],
		function(declare,request,topic,adapter,util) {			
			var sysCategory = declare("hr.hrratifycategory.SysCategoryMixin", null, {
			
			tempKey:"",
			
/*			startup:function(){
				this.inherited(arguments);
				this.templURL = "/sys/mobile/js/mui/syscategory/syscategory_sgl.jsp?modelName=!{modelName}&mainModelName=!{mainModelName}&authType=!{authType}&showCommonCate=!{showCommonCate}&extendPara=key:!{key}&fdTmepKey="+this.tempKey+"&s_time=" + new Date().getTime();
			},*/
			
			afterSelectCate:function(evt){
				topic.publish("hr/ratify/afterSelectCate");
				// #68063 部分机型下Loading在后退后仍存在，具体原因不明，所以暂不显示Loading(对体验影响很小，因为浏览器上方本身也有Loading)
				// process.show();

				var _this = this;
				this.defer(function(){
					if(_this.mobileCate){
						topic.publish("hr/ratify/afterSelectCate",this.curIds);
						this.curIds = "";
						this.curNames = "";
						/*if(process){
							process.hide();
						}*/
					}else{
						adapter.open(util.formatUrl(util.urlResolver(this.createUrl, evt)),"_self");
						this.curIds = "";
						this.curNames = "";
						/*if(process){
							process.hide();
						} */
					}
				},300);
			}
				
			});
			return sysCategory;
	});
