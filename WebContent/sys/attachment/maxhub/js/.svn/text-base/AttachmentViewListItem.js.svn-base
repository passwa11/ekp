define(["dojo/_base/declare","dojo/dom-construct","mui/util","dojo/_base/lang",
		"sys/attachment/mobile/js/_AttachmentItem"],
		function(declare, domConstruct, util, lang, _AttachmentItem){
	
	//普通附件项展示类（MaxHub端）
	return declare("sys.attachment.maxhub.js.AttachmentViewListItem",[_AttachmentItem],{
		
		baseClass : 'mhuiAttachmentItem',
		
		//-1准备上传,	0上传出错,	1上传中,		2上传成功 ,  3 表示阅读状态
		status : 3,
		
		buildItem : function(){
			var itemL = domConstruct.create("div", {
				className : "mhuiAttachmentItemL " +
				this.getAttContainerType()
			}, this.containerNode);
			this.attItemIcon = domConstruct.create("div",{ className: "mhuiAttachmentItemIcon" }, itemL);
			if(this.getType() == 'img'){
				domConstruct.create("img",{
					align:"middle",
					src: util.formatUrl(this.href)}, this.attItemIcon);
			}else{
				var iconClass = this.getAttTypeClass();
				if (this.icon != null && this.icon != '') {
					iconClass = this.icon;
				}
				domConstruct.create("i", { className :  iconClass }, this.attItemIcon);
			}
			var itemC = domConstruct.create("div", { className : "mhuiAttachmentItemC" ,innerHTML : '' }, this.containerNode);
			domConstruct.create("span", {
				className : "mhuiAttachmentItemName",
				innerHTML : this.name
			}, itemC);
			if (this.href) {
				this.connect(itemC, "click",lang.hitch(this._onItemClick));
				itemC.dojoClick = true;
			}
		},
		
		_onItemClick : function(){
			console.log(util.formatUrl(this.href));
			window.location.href = util.formatUrl(this.href);
		}
		
	});
	
});