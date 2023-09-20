define([
  "dojo/_base/declare",
  "mui/category/CategorySelection",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/request",
  "mui/util",
  "dojo/_base/lang",
  "dojo/topic",
  "mui/i18n/i18n!sys-modeling-main"
], function(
  declare,
  CategorySelection,
  domConstruct,
  domClass,
  request,
  util,
  lang,
  topic,
  modelingLang
) {
  var selection = declare(
    "sys.modeling.main.resources.js.mobile.listView.flowListSelection",
    [CategorySelection],
    {
      modelName: null,

      // 只显示末尾三级
      max: 3,

      buildIcon: function() {
        return null
      },

      _buildSelItem: function() {
        var item = this.inherited(arguments)
        domClass.add(item, "muiCategorySItem")
        return item
      },

      startup: function() {
    	  this.inherited(arguments)
	      domClass.add(this.leftArea, "muiCateSecBtnDis")
	      this.connect(this.buttonNode, "click", "_subSelItem")
	
	      this.connect(this.clearButtonNode, "click", "_cancel")
	      this.clearButtonNode.innerHTML = modelingLang['mui.modeling.cancel']
          this.clearButtonNode.className = "muiCateClearBtn muiModelingCancelBtn"
      },
      
      // 取消
      _cancel : function(){
    	  this._clearSelItem(this);
    	  topic.publish("/mui/category/cancel",this)
      },
      
      _subSelItem : function(){
    	  // 当没有选择一条记录时，不允许确定
    	  if(this.cateSelArr.length === 0){
    		  return;
    	  }
    	  this.inherited(arguments)
      },

      // 更新样式
      _resizeSelection: function() {
          var items = this.cateSelArr
          var html = ""
          if (items.length > 0) {
            domClass.remove(this.buttonNode, "muiCateSecBtnDis")
            html = items[0].label;
            html = modelingLang['mui.modeling.chosen'] + html;
          } else {
        	  domClass.add(this.buttonNode, "muiCateSecBtnDis");
          }

          this.leftArea.innerHTML = html
      }

    }
  )
  return selection
})
