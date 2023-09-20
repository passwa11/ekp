/**
 * 全选按钮
 */
define([
  "dojo/_base/declare",
  "mui/category/CategoryItemMixin",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/_base/array",
  "dojo/topic",
  "dojo/dom-construct"
], function(declare, CategoryItemMixin, domStyle, domClass, array, topic, domConstruct) {
  return declare("mui.category.CategoryAllSelectItem", [CategoryItemMixin], {
    
	buildRendering: function(){
		this.inherited(arguments);
		domClass.add(this.domNode, 'muiCateAllSelectItem');
	},
	  
	postCreate: function() {
      this.inherited(arguments)
      this.subscribe("/mui/category/unselected", "unselected")
      this.subscribe("/mui/category/selected", "selected")
    },

    show: function() {
      domStyle.set(this.domNode, {display: "block"})
    },

    hide: function() {
      domStyle.set(this.domNode, {display: "none"})
    },

    startup: function() {
      this.inherited(arguments)
      this.hide()
      // 恶心，后面改
      this.defer(function() {
        this.initALlSelecte()
      })
    },

    buildIcon: function() {
      domStyle.set(this.iconNode, {display: "none"})
    },

    // 选中统计
    buildCount: function() {
      this.countNode = domConstruct.create(
        "div",
        {innerHTML: this.getCount(), className: "muiCateSelect"},
        this.cateContainer
      )
    },

    setCount: function() {
      if (this.countNode) {
        this.countNode.innerHTML = this.getCount()
      }
    },

    getCount: function() {
      var children = this.getParent().getChildren()
      var total = 0

      var selectedCount = 0

      array.forEach(children, function(child, index) {
        if (index == 0) {
          return
        }

        if (child.showSelect && child.showSelect()) {
          total++
        }
        if (child.isSelected && child.isSelected()) {
          selectedCount++
        }
      })

      if (total == 0) {
        this.hide()
      } else {
        this.show()
      }

      return selectedCount + "/" + total
    },

    // 是否同个父节点
    samParent: function(obj) {
      return obj.getParent() == this.getParent()
    },

    // 初始化全选按钮
    initALlSelecte: function() {
      this.selected(this)
      this.buildCount()
    },

    // 同级节点取消选中触发取消选中”全选按钮“
    unselected: function(obj) {
      if (this.samParent(obj)) {
        this.setCount()
        this._cancelSelected(this, {fdId: this.fdId, trigger: true})
      }
    },

    // 同级所有节点选中触发选中”全选按钮“
    selected: function(obj) {
      if (this.samParent(obj)) {
        this.setCount()
        if (this.isAllSelected()) {
          this._setSelected(this, {fdId: this.fdId, trigger: true})
        } else {
          this._cancelSelected(this, {fdId: this.fdId, trigger: true})
        }
      }
    },

    // 给同级节点发送是否选中事件
    topicEvt: function(select) {
      var event = "/mui/category/cancelSelected"
      if (select) {
        event = "/mui/category/setSelected"
      }

      var list = this.getParent(),
        children = list.getChildren()

      array.forEach(
        children,
        function(child, index) {
          if (index == 0) {
            return
          }
          topic.publish(event, this, {
            fdId: child.fdId
          })
        },
        this
      )
    },

    // 是否所有同级节点都已经选中
    isAllSelected: function() {
      var children = this.getParent().getChildren()

      var all = true

      for (var i = 0; i < children.length; i++) {
        if (i == 0) {
          continue
        }
        var child = children[i]

        if (child.showSelect && child.showSelect() && !child.isSelected()) {
          all = false
          break
        }
      }

      return all
    },

    _cancelSelectedTrigger: function(evt) {
      if (evt.trigger) {
        return
      }
      this.topicEvt(false)
    },

    _setSelectedTrigger: function(evt) {
      if (evt.trigger) {
        return
      }
      this.topicEvt(true)
    },
    showMore: function() {
      return false
    },
    isSelected: function() {
      return false
    }
  })
})
