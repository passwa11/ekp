define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/topic",
  "mui/iconUtils",
  "mui/category/CommonCategoryItemMixin"
], function(
  declare,
  array,
  domConstruct,
  domClass,
  domStyle,
  topic,
  iconUtils,
  CommonCategoryItemMixin
) {
  var item = declare(
    "mui.simplecategory.SimpleCommonCategoryItemMixin",
    [CommonCategoryItemMixin],
    {
      buildRendering: function() {
        this.fdId = this.value
        this.label = this.text
        this.icon = ""
        this.type = window.SIMPLE_CATEGORY_TYPE_CATEGORY
        this.inherited(arguments)
      },

      postCreate: function() {
        this.inherited(arguments)
        this.subscribe("/mui/category/cate_selected", "selected")
        this.subscribe("/mui/category/cate_unselected", "unselected")
      },

      selected: function(srcObj, evt) {
        if (srcObj.key == this.key) {
          if (evt && evt.fdId) {
            if (evt.fdId == this.fdId) {
              domClass.add(this.selectNode, "muiCateSeled")
              if (!this.checkedIcon) {
                this.checkedIcon = domConstruct.create(
                  "i",
                  {
                    className: "mui mui-checked muiCateSelected"
                  },
                  this.selectNode
                )
              }
            }
          }
        }
      },
      unselected: function(srcObj, evt) {
        if (srcObj.key == this.key) {
          if (evt && evt.fdId) {
            if (evt.fdId.indexOf(this.fdId) > -1) {
              if (this.checkedIcon) {
                domClass.remove(this.selectNode, "muiCateSeled")
                domConstruct.destroy(this.checkedIcon)
                this.checkedIcon = null
              }
            }
          }
        }
      },

      _setSelected: function(srcObj, evt) {
        if (srcObj.key == this.key) {
          if (evt && evt.fdId) {
            if (evt.fdId == this.fdId) {
              domClass.add(this.selectNode, "muiCateSeled")
              if (!this.checkedIcon) {
                this.checkedIcon = domConstruct.create(
                  "i",
                  {
                    className: "mui mui-checked muiCateSelected"
                  },
                  this.selectNode
                )
              }

              var evts = this.getEvts()
              topic.publish("/mui/category/selected", this, evts)
              topic.publish("/mui/category/commonSelected", this, evts)
            }
          }
        }
      },

      getEvts: function() {
        return {
          label: this.label,
          fdId: this.fdId,
          icon: this.icon,
          type: this.type
        }
      },

      _cancelSelected: function(srcObj, evt) {
        if (srcObj.key == this.key) {
          if (evt && evt.fdId) {
            if (evt.fdId.indexOf(this.fdId) > -1) {
              if (this.checkedIcon) {
                domClass.remove(this.selectNode, "muiCateSeled")
                domConstruct.destroy(this.checkedIcon)
                this.checkedIcon = null
                var evts = this.getEvts()
                topic.publish("/mui/category/unselected", this, evts)
                topic.publish("/mui/category/commonUnSelected", this, evts)
              }
            }
          }
        }
      },

      //是否显示往下一级
      showMore: function() {
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
          if (array.indexOf(arrs, this.fdId) > -1) return true
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
