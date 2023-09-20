define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "mui/util",
  "mui/history/listener"
], function(declare, array, WidgetBase, domConstruct, util, listener) {
  return declare("mui.property.FilterContainer", [WidgetBase], {
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

    openDialog: function() {
    	var self = this;
		listener.push({
			forwardCallback: function() {
				self.show();
			},
			backCallback: function() {
				self.hide();
			}
		})
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
				+ ' data-dojo-props=\'subject:"常用筛选",name:"_common",options:' 
				+ JSON.stringify(commonQuery)
				+ "'></div>";
		}
		
		if(this.filters && this.filters.length > 0) {
			array.forEach(this.filters, function(item, index) {
				
				if(!item.filterType) {
					return;
				}
				
				result += "<div data-dojo-type='mui/property/filter/" + item.filterType + "'"
					+ " data-dojo-props='_props:" 
					+ JSON.stringify(item) 
					+ "'></div>";
			});
		}
		
		return result;
    }
  })
})
