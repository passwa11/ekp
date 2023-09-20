define(['dojo/_base/declare',
        'dojo/_base/lang',
        './BaseNoDataMixin'
        ], function(declare, lang, BaseNoDataMixin) {

		return declare("sys.mportal._ListNoDataMixin", [ BaseNoDataMixin ], {
			
			startup : function() {
				if (this._started) {
					return;
				}
				this.inherited(arguments);
				this.subscribe("/mui/mportal/card/loaded",lang.hitch(this,function(evts){
						if(evts!=null && evts==this){
							this.buildNoDataItem(evts);
						}
					}));
			},
			
			buildNoDataItem : function(widget){
				if(this.tempItem){
					if(widget.removeChild)
						widget.removeChild(this.tempItem);
					this.tempItem.destroy();
					this.tempItem = null;
				}
				if(widget.totalSize==0){
					
					this.buildTemplate(widget);
					
				}
			}
		});
});