define([
    "dojo/_base/declare","dojo/_base/lang",
    "dojo/dom-construct",
    "dojox/mobile/_ItemBase",
   	"mui/list/item/_ListLinkItemMixin","mui/i18n/i18n!sys-attend",'mui/util'
	], function(declare, lang, domConstruct, ItemBase, _ListLinkItemMixin, Msg,Util) {
	
	var item = declare("sys.attend.AttendStatLogMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		
		baseClass:"",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);

			this.domNode = this.containerNode= this.srcNodeRef || domConstruct.create(this.tag,{className:''});
			var contentNode = domConstruct.create('div',{className : 'content'},this.domNode);

			// 显示打卡时间
			var dateNode = domConstruct.create('div',{className : 'muiSignDateBox'},contentNode);
			// domConstruct.create('span',{className : '',innerHTML:Msg['mui.time'] + '：'}, dateNode);
			domConstruct.create('span',{className : 'timeInfo',innerHTML:this.created}, dateNode);

			let typeUrl =Util.formatUrl('/sys/attend/mobile/resource/image/map.png');
			if(this.type=="2"){
				typeUrl =Util.formatUrl('/sys/attend/mobile/resource/image/wifi.png');
			}
			// 显示打卡位置
			var locationNode = domConstruct.create('div',{className : 'muiSignDateBox'},contentNode);
			domConstruct.create('img',{src : typeUrl ,className: 'addressImage'}, locationNode);
			domConstruct.create('span',{className : 'addressInfo',innerHTML:this.location}, locationNode);
		},

		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},

		_setLabelAttr: function(text){
			if(text){
				this._set("label", text);
			}
		},
		makeUrl:function(){
			if(!this.href){
				return '';
			}
			return this.inherited(arguments);
		}
	});
	return item;
});