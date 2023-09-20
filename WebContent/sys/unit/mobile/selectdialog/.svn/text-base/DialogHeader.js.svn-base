define( [ "dojo/_base/declare", "mui/category/CategoryHeader","./DialogChannelMixin","mui/i18n/i18n!sys-mobile"], 
		function(declare,CategoryHeader,DialogChannelMixin,Msg) {
		var header = declare("mui.selectdialog.DialogHeader", [ CategoryHeader,DialogChannelMixin], {
				//获取详细信息地址
				//detailUrl : '/sys/organization/mobile/address.do?method=detailList&orgIds=!{curId}'
				title: Msg['mui.category.select'],
				
				headerUrl:null,
				
				//加载
				startup : function() {
					this.inherited(arguments);
					this.detailUrl = this.headerUrl;
				},
				
				_chgHeaderInfo : function(srcObj, evt) {
					if(this.isSameChannel(srcObj)){
						this.inherited(arguments);
					}
				},
				
				buildRendering : function() {
					if (!this.title || this.title==''){ //有可能没有传标题进来
						this.title = Msg['mui.category.select'];
					}					
			        this.inherited(arguments);
				}
			});
			return header;
});