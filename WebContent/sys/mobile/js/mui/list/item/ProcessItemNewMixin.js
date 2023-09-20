define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "mui/util",
    "mui/list/item/ProcessItemMixin"
	], function(declare, domConstruct,util,ProcessItemMixin) {
	var item = declare("mui.list.item.ProcessItemNewMixin", [ProcessItemMixin], {
		buildInternalRender : function() {
			var rightArea = domConstruct.create("div",{className:"muiProcessRight"},this.contentNode);
			domConstruct.create("span", { className: "muiProcessImg",style:{background:'url(' + util.formatUrl(this.icon) +') center center no-repeat',backgroundSize:'cover',display:'inline-block'}}, rightArea);
			domConstruct.create("a", { className: "muiProcessCreator muiAuthor",innerHTML:this.creator}, rightArea);
			domConstruct.create("span", { className: "muiProcessCreated muiListSummary", 
				innerHTML:this.created}, rightArea);
			
			var leftArea = domConstruct.create("a",{className:"muiProcessLeft"},this.contentNode);
			var title = domConstruct.create("h3",{className:"muiProcessTitle muiSubject"},leftArea);
			if(this.status){
				var statusHTML = this.status;
				if(this.statusIdx){
					if(this.statusIdx=='00'){
						statusHTML = '<span class="muiProcessStatusBorder muiProcessDiscard">' + this.status + '</span>';
					}else if(this.statusIdx=='10'){
						statusHTML = '<span class="muiProcessStatusBorder muiProcessDraft">' + this.status + '</span>';
					}else if(this.statusIdx=='11'){
						statusHTML = '<span class="muiProcessStatusBorder muiProcessRefuse">' + this.status + '</span>';
					}else if(this.statusIdx=='20'){
						statusHTML = '<span class="muiProcessStatusBorder muiProcessExamine">' + this.status + '</span>';
					}else if(this.statusIdx=='30'){
						statusHTML = '<span class="muiProcessStatusBorder muiProcessPublish">' + this.status + '</span>';
					}else{
						statusHTML = '<span class="muiProcessStatusBorder muiProcessPublish">' + this.status + '</span>';
					}
				}
				title.appendChild(domConstruct.toDom(statusHTML));
			}
			if(this.label){
				title.appendChild(domConstruct.toDom(this.label));
			}
			if(this.summary){
				var summary = domConstruct.create("p",{className:"muiProcessSummary muiListSummary",innerHTML:this.summary},leftArea);
				domConstruct.create("i",{className:"muiProcessSign mui mui-flowlist"},summary,"first");
			}
			
			if(this.href){
				this.proxyClick(leftArea, this.href, '_blank');
			}
		}		
	});
	return item;
});