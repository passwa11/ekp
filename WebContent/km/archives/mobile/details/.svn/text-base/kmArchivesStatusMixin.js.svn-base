define(['dojo/_base/declare', 'dojo/dom-construct', 'mui/i18n/i18n!km-archives:enums.borrow_status'], function(declare, domConstruct, msg) {
	var item = declare("km.archives.TextItem.statusMixin", null, {
		
		buildTag : function () {

			if(this.status) {
				
				if(this.status == '0') {
					
					this.statusTagClass = ' muiProcessDraft';
					this.statusTagText = msg['enums.borrow_status.0'];
					
				} else if(this.status == '1') {
					
					this.statusTagClass = ' muiProcessExamine';
					this.statusTagText = msg['enums.borrow_status.1'];
					
				} else if(this.status == '2') {
					
					this.statusTagClass = ' muiProcessPublish';
					this.statusTagText = msg['enums.borrow_status.2'];
					
				} else if(this.status == '3') {
					
					this.statusTagClass = ' muiProcessDiscard';
					this.statusTagText = msg['enums.borrow_status.3'];
					
				}
				
				domConstruct.create('span', {
					className : this.tagClass + this.statusTagClass,
					innerHTML : this.statusTagText
				}, this.muiTextItemTitle);
				
			}
			
		},
		
	});
	return item;
});