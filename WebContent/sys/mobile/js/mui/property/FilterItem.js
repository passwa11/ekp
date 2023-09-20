define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "dojo/_base/array",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/dom-class",
  "mui/util",
  "mui/property/PropertyDialogMixin",
  "mui/property/FilterDialogButtonMixin",
  "mui/property/HashMixin",
  "mui/history/listener"
], function(declare, lang, array, WidgetBase, domConstruct, domClass, util, PropertyDialogMixin, FilterDialogButtonMixin, HashMixin, listener) {
  return declare("mui.property.FilterItem", [ WidgetBase, PropertyDialogMixin, FilterDialogButtonMixin, HashMixin ], {
    commonQuery: "",

    baseClass: "muiHeaderItem muiHeaderItemIcon",
    icon: "fontmuis muis-filter",

    buildRendering: function() {
      this.inherited(arguments)
      this.labelNode = domConstruct.create(
        "div",
        {
          className: "muiHeaderItemLable",
          innerHTML: util.formatText(this.label)
        },
        this.domNode
      )

      domConstruct.create(
        "i",
        {
          className: this.icon
        },
        this.labelNode
      )

      
      this.connect(this.domNode, "onclick", "openDialog")
    },
    
    startup: function(){
    	this.inherited(arguments);
    	domClass.toggle(this.domNode, 'selected', this.hasValue());
   },
    
    openDialog: function() {
    	var self = this;
		var result = listener.push({
			forwardCallback: function() {
				self.show();
			},
			backCallback: function() {
				self.hide();
			}
		});
		this.previousHistoryId = result.previousId;
    },

    getStaticFilters: function() {
		var result = '';

		if (this.commonQuery && this.commonQuery.length > 0) {
			var commonQuery = [];
			array.forEach(this.commonQuery, function(item, index) {
				commonQuery.push({
					name: item.text,
					value: item.dataURL
				});
			});
			result += "<div data-dojo-type='mui/property/filter/FilterRadio'"
				+ ' data-dojo-props=\'key:"'+ this.key +'",subject:"常用筛选",name:"_common",options:' 
				+ JSON.stringify(commonQuery)
				+ "'></div>";
		}
		if(this.filters && this.filters.length > 0) {
			array.forEach(this.filters, function(item, index) {
				
				if(!item.filterType) {
					return;
				}
				// filterType是否为全路径
				isTypePathFull = item.isTypePathFull === true ? true : false;
				if(isTypePathFull){
					result += "<div data-dojo-type='" + item.filterType + "'";
				}else{
					result += "<div data-dojo-type='mui/property/filter/" + item.filterType + "'";
				}
				result += " data-dojo-props='_props:" 
					+ JSON.stringify(item) 
					+ ",key:\""+ this.key +"\"'></div>";
			}, this);
		}
		
		return result;
    },
    
    hasValue: function(){
    	if(!this.values){
    		return false;
    	}
    	for(var key in this.values){
    		var value = this.values[key];
    		if(lang.isString(value) && value){
    			return true;
    		}
    		if(lang.isArray(value) && value.length > 0) {
    			for(var i=0;i < value.length; i++){
    				if(value[i] != ''){
    					return true;
    				}
    			}
    		}
    		if(!lang.isArray(value) && lang.isObject(value)){
    			var realValue = value.value;
    			if(lang.isArray(realValue) && realValue.length > 0 
    					|| lang.isString(realValue) && realValue){
        			return true;
        		}
    		}
    	}
    	return false;
    }
    
  })
})
