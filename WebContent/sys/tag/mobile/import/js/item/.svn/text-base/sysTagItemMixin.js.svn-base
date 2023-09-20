define([
    "dojo/_base/declare",
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/on",
   	"dojo/topic"
	], function(declare, array, domConstruct,domClass , domStyle , domAttr , ItemBase , _ListLinkItemMixin,on,topic) {
	var item = declare("hr.staff.mobile.resource.js.item.HrStaffTagItemMixin", [ItemBase], {

		buildRendering:function(){
			this.inherited(arguments);
			this.renderTagItem();
		},
		startup:function(){
			this.inherited(arguments);
		},
		renderTagItem:function(){
			var ischecked = false;
			/*if(window.parent.selectValue.length>0){
				if(this.fdId==window.parent.selectValue[this.index].id){
					ischecked =true;
				}
			}*/
			var itemNode = domConstruct.create("div",{className:'all-tag-item'},this.domNode);
			var checkBoxNode = domConstruct.create("div",{classNmae:'all-tag-checkbox'},itemNode);
			var checkBox = domConstruct.create("input",{classNmae:'',type:'checkbox',checked:ischecked?'checked':false},checkBoxNode);
			var fdName= domConstruct.create("div",{className:'all-tag-name',innerHTML:"<span>名称:</span><span>"+this.fdName+"</span>"},itemNode);
			var fdcategory= domConstruct.create("div",{className:'all-tag-category',innerHTML:"<span>分类:</span><span>"+this.fdCategorys+"</span>"},itemNode);
			var _this = this;
			on(checkBox,'change',function(res){
				if(res.target.checked){
					topic.publish("hr/staff/tag/list/value",{index:_this.index,value:_this.fdName,id:_this.fdId,select:true})
				}else{
					topic.publish("hr/staff/tag/list/value",{index:_this.index,select:false})
				}
			})
		},
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}

	});
	return item;
});