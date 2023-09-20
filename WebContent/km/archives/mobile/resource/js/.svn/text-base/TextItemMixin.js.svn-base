define(["dojo/_base/declare",
"dojo/dom-construct",
"dojo/dom-style",
"dojo/dom-attr",
"mui/util",
"dojo/on",
"mui/dialog/Tip",
"mui/list/item/TextItemMixin",
"mui/i18n/i18n!km-archives:mobile"],
function(declare, domConstruct, domStyle, domAttr, util, on, Tip, TextItemMixin, msg) {
	var item = declare("kms.lecturer.TextItemMixin", [TextItemMixin], {
		buildInternalRender : function() {
			if (this.docSubject) {
								
				this.muiTextItemTitle = domConstruct.create('div', {className : 'muiTextItemTitle muiFontSizeM'}, this.domNode);

				this.buildTag(this.muiTextItemTitle);
								
				this.muiTextItemTitle.innerHTML = this.muiTextItemTitle.innerHTML + this.docSubject;
			}

			this.messageDomNode = domConstruct.create('ul', {
				className : 'muiTextItemInfo'
			});

			if(this["docCreator.fdName"]){
				
				domConstruct.create('li', {
					innerHTML : this["docCreator.fdName"]
				}, this.messageDomNode);
				
				this.buildMessage = true;
			}
			if(this.docCreateTime){
				
				domConstruct.create('li', {
					innerHTML : this.docCreateTime
				}, this.messageDomNode);

				this.buildMessage = true;
			}else if(this.fdFileDate){
				
				domConstruct.create('li', {
					innerHTML : this.fdFileDate
				}, this.messageDomNode);

				this.buildMessage = true;
			}

			domConstruct.place(this.messageDomNode, this.domNode, 'last');
			
			if (!this.href) {
				this.makeLockLinkTip(this.domNode);
			}
		},

		makeLockLinkTip : function(linkNode){
			this.href='javascript:void(0);';
			on(linkNode,'click',function(evt){
				Tip.tip({icon:'mui mui-warn', text:msg['mobile.tips.viewOnPhone']});
			});
		},
		
		buildTag : function (domNode) {
			this.inherited(arguments);
			if(this.docStatus && domNode) {
				this.formatStatusData();
				domConstruct.create('span', {
					className : this.tagClass + this.statusClass,
					innerHTML : this.docStatus
				}, domNode);
			}
		},
		
		formatStatusData : function () {
			if(this.docStatus) {
				if('00' == this.docStatus) {
					this.docStatus = msg['mobile.enums.doc_status.00'];
					this.statusClass = ' muiProcessDiscard';
				} else if('10' == this.docStatus) {
					this.docStatus = msg['mobile.enums.doc_status.10'];
					this.statusClass = ' muiProcessDraft';
				} else if('11' == this.docStatus) {
					this.docStatus = msg['mobile.enums.doc_status.11'];
					this.statusClass = ' muiProcessRefuse';
				} else if('20' == this.docStatus) {
					this.docStatus = msg['mobile.enums.doc_status.20'];
					this.statusClass = ' muiProcessExamine';
				} else if('30' == this.docStatus) {
					this.docStatus = msg['mobile.enums.doc_status.30'];
					this.statusClass = ' muiProcessPublish';
				} else if('31' == this.docStatus) {
					this.docStatus = msg['mobile.enums.doc_status.31'];
					this.statusClass = ' muiProcessPublish';
				} 
			}
		}
	});
	return item;
});