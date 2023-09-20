define([
	"dojo/_base/declare",
	"dojox/mobile/Button",
	"mui/form/_CategoryBase",
	"mui/dialog/Tip",
	"dojo/query",
	"dojox/mobile/sniff"
	], function(declare, Button, CategoryBase, Tip, query, has) {

	return declare("hr.ratify.mobile.js.contractSelectorButton", [Button, CategoryBase], {
		
		key: '_certSelect',
		
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode.dojoClick = !has('ios');
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.eventBind();
		},
		
		_onClick : function(evt) {
			var staffId = "";
			if(this.operateType == 'change'){
				staffId = query('[name="fdChangeStaffId"]')[0].value;
			}else if(this.operateType == 'remove'){
				staffId = query('[name="fdRemoveStaffId"]')[0].value;
			}
			if(null == staffId || staffId == ""){
				Tip.fail({
					text : '请选择合同人!'
				});
				return false;
			}
			this.defer(function(){
				this._selectCate();
			}, 350);
		}
	});
});