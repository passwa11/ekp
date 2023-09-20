define(["dojo/_base/declare",
"dojo/dom-construct",
"dojo/dom-style",
"dojo/dom-attr",
"mui/util",
"dojo/on",
"mui/dialog/Tip",
"mui/list/item/TextItemMixin",
"mui/i18n/i18n!km-archives:mobile"],
function(declare, domConstruct, domStyle, domAttr, util, on, Tip, TextItemMixin,msg) {
	var item = declare("kms.lecturer.TextItemMixin", [TextItemMixin], {
		
		buildTag : function (domNode) {
			if(this.status && domNode) {
				if(this.status) {
					if('1' == this.status) {
						this.status = msg['mobile.enums.borrow_status.1'];
						this.statusClass = 'muiProcessExamine';
					} else if('2' == this.status) {
						this.status = msg['mobile.enums.borrow_status.2'];
						this.statusClass = 'muiProcessPublish';
					} else if('3' == this.status) {
						this.status = msg['mobile.enums.borrow_status.3'];
						this.statusClass = 'muiProcessRefuse';
					} 
				}
				domConstruct.create('span', {
					className : this.statusClass ? this.tagClass+' '+this.statusClass : this.tagClass,
					innerHTML : this.status
				}, domNode);
			}
		}
	});
	return item;
});