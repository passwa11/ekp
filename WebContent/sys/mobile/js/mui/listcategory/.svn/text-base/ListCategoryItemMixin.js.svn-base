define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dojo/dom-class",
  "dojo/dom-style",
  "mui/iconUtils",
  "mui/category/CategoryItemMixin",
  "dojo/_base/array"
], function(
  declare,
  array,
  domClass,
  domStyle,
  iconUtils,
  CategoryItemMixin,
  array
) {
  var item = declare(
    "mui.listcategory.ListCategoryItemMixin",
    [CategoryItemMixin],
    {
      buildRendering: function() {
        this.fdId = this.value ? this.value : this.fdId;
        this.label = this.text ? this.text : (this.docSubject?this.docSubject:this.fdName);
        this.icon = ""
        this.type = window.SIMPLE_CATEGORY_TYPE_CATEGORY
        this.inherited(arguments)
        this.subscribe("/mui/category/path", "_pathChange")
        if (window.pathItem) {
          this._pathChange(this, window.pathItem)
        }
      },

      _pathChange: function(obj, items) {
        if (obj.key != this.key) {
          return
        }
        if (items.indexOf(this.fdId) >= 0) {
          this.set("entered", true)
        } else {
          this.set("entered", false)
        }
      },

      _cateChange: function(obj, evt) {},

      _setEnteredAttr: function(entered) {
        if (entered) {
          domClass.add(this.domNode, "muiCategoryEntered")
        } else {
          domClass.remove(this.domNode, "muiCategoryEntered")
        }
      },

      //是否显示往下一级
      showMore: function() {
        if (!this.child || this.child.length <= 0) {
          return false
        }
        var more = false
        var pWeiget = this.getParent()
        if (pWeiget.authCateIds) {
          if (pWeiget.authCateIds.indexOf(this.fdId) > -1) {
            more = true
          }
        } else more = true
        if (this.type == window.SIMPLE_CATEGORY_TYPE_CATEGORY && more) {
          return true
        }

        return false
      },

      //是否显示选择框
      showSelect: function() {
        var pWeiget = this.getParent()
        if (pWeiget.authType == "03") {
          //数据筛选时
          if (pWeiget && pWeiget.selType == this.type) {
            return true
          }
        } else {
          if (
            pWeiget &&
            pWeiget.selType == this.type &&
            this.isShowCheckBox != "0"
          ) {
            return true
          }
        }
        return false
      },

      //是否选中
      isSelected: function() {
        var pWeiget = this.getParent()
        if (pWeiget && pWeiget.curIds) {
          var arrs = pWeiget.curIds.split(";")
          if (array.indexOf(arrs, this.fdId) > -1) {
        	pWeiget.curNames = this.label;
            return true
          }
        }

        return false
      },

      buildIcon: function(iconNode) {
        if (this.icon) {
          iconUtils.setIcon(this.icon, null, this._headerIcon, null, iconNode)
        } else {
          domStyle.set(iconNode, {display: "none"})
        }
      }
    }
  )
  return item
})
