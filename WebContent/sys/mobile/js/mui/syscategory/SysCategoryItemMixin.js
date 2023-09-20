define([
	"dojo/_base/declare",
	"dojo/_base/array",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/topic",
	"dojo/dom-style",
	"mui/iconUtils",
	"mui/category/CategoryItemMixin"
  ], function(declare, array, domConstruct, domClass, topic, domStyle, iconUtils, CategoryItemMixin) {
	var item = declare(
	  "mui.syscategory.SysCategoryItemMixin",
	  [CategoryItemMixin],
	  {
		buildRendering: function() {
		  this.fdId = this.value
		  this.label = this.text
		  this.canSelect = this.isShowCheckBox
		  this.icon = ""
		  this.type =
			this.nodeType == "CATEGORY"
			  ? window.SYS_CATEGORY_TYPE_CATEGORY
			  : window.SYS_CATEGORY_TYPE_TEMPLATE
		  this.inherited(arguments)
		  this.subscribe("/mui/category/path", "_pathChange")
		  if (window.pathItem) {
			this._pathChange(this, window.pathItem)
		  }
		},
  
		postCreate : function() {
		  this.inherited(arguments);
			this.subscribe("/mui/category/cancelSelected", "_cancelSelected");
			this.subscribe("/mui/category/setSelected", "_setSelected");
			this.subscribe("/mui/category/commonSelected", "commonSelected");
			this.subscribe("/mui/category/commonUnSelected", "commonUnSelected");
  
			// 监听本类发布的“选中”和“取消”事件，当监听到此消息时，仅修改选中状态，不再发布事件
			this.subscribe("/mui/category/unselected", "_unselected");
			this.subscribe("/mui/category/selected", "_selected");
		},
  
		  _cancelSelectedTrigger: function(evt) {
			topic.publish("/mui/category/unselected", this, {
			  label: this.label,
			  fdId: this.fdId,
			  icon: this.icon,
			  type: this.type
			})
			topic.publish("/mui/category/cate_unselected", this, {
			  label: this.label,
			  fdId: this.fdId,
			  icon: this.icon,
			  type: this.type
			})
		  },
	  
		  _cancelSelected: function(srcObj, evt) {
			if (srcObj.key == this.key) {
			  if (evt && evt.fdId) {
				if (evt.fdId.indexOf(this.fdId) > -1) {
				  window._curIds = "-";
				  if (this.checkedIcon) {
					domClass.remove(this.selectNode, "muiCateSeled")
					domConstruct.destroy(this.checkedIcon)
					this.checkedIcon = null
					this._cancelSelectedTrigger(evt)
				  }
				}
			  }
			}
		  },
  
		  _unselected: function(srcObj, evt) {
			if (srcObj.key == this.key) {
			  if (evt && evt.fdId) {
				if (evt.fdId.indexOf(this.fdId) > -1) {
				  window._curIds = "-";
				  if (this.checkedIcon) {
					domClass.remove(this.selectNode, "muiCateSeled")
					domConstruct.destroy(this.checkedIcon)
					this.checkedIcon = null
				  }
				}
			  }
			}
		  },
	  
		  commonUnSelected: function(srcObj, evt) {
			if (srcObj.key == this.key) {
			  if (evt && evt.fdId) {
				if (evt.fdId.indexOf(this.fdId) > -1) {
				  window._curIds = "-";
				  if (this.checkedIcon) {
					domClass.remove(this.selectNode, "muiCateSeled")
					domConstruct.destroy(this.checkedIcon)
					this.checkedIcon = null
				  }
				}
			  }
			}
		  },
	  
		  commonSelected: function(srcObj, evt) {
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
	  
		  _setSelectedTrigger: function() {
			topic.publish("/mui/category/selected", this, {
			  label: this.label,
			  fdId: this.fdId,
			  icon: this.icon,
			  type: this.type
			})
			topic.publish("/mui/category/cate_selected", this, {
			  label: this.label,
			  fdId: this.fdId,
			  icon: this.icon,
			  type: this.type
			})
		  },
	  
		  _setSelected: function(srcObj, evt) {
			if (srcObj.key == this.key) {
			  if (evt && evt.fdId) {
				if (evt.fdId == this.fdId) {
				  window._curIds = this.fdId;
				  if (this.checkedIcon) {
					domConstruct.destroy(this.checkedIcon)
					this.checkedIcon = null
				  }
	  
				  if (!this.selectNode) {
					return
				  }
	  
				  if (!domClass.contains(this.selectNode, "muiCateSeled")) {
					domClass.add(this.selectNode, "muiCateSeled")
				  }
	  
				  this.checkedIcon = domConstruct.create(
					"i",
					{
					  className: "mui mui-checked muiCateSelected"
					},
					this.selectNode
				  )
				  this._setSelectedTrigger(evt)
				}
			  }
			}
		  },
  
		  _selected: function(srcObj, evt) {
			if (srcObj.key == this.key) {
			  if (evt && evt.fdId) {
				if (evt.fdId == this.fdId) {
				  window._curIds = this.fdId;
				  if (this.checkedIcon) {
					domConstruct.destroy(this.checkedIcon)
					this.checkedIcon = null
				  }
	  
				  if (!this.selectNode) {
					return
				  }
	  
				  if (!domClass.contains(this.selectNode, "muiCateSeled")) {
					domClass.add(this.selectNode, "muiCateSeled")
				  }
	  
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
		  },
  
		_pathChange: function(obj, items) {
		  if (items.indexOf(this.fdId) >= 0) {
			this.set("entered", true)
		  } else {
			this.set("entered", false)
		  }
		},
  
		getTitle: function() {
		  return this.label
		},
  
		_setEnteredAttr: function(entered) {
		  if (entered) {
			domClass.add(this.domNode, "muiCategoryEntered")
		  } else {
			domClass.remove(this.domNode, "muiCategoryEntered")
		  }
		},
  
		//是否显示往下一级
		showMore: function() {
		  var pWeiget = this.getParent()
		  //仅显示分类并且下一级不为空
		  var isShowMore = (pWeiget && pWeiget.isOnlyShowCate == 1 
							  && typeof this.beanName != "undefined" && this.hasChildren == "1") 
							  || (pWeiget && typeof pWeiget.isOnlyShowCate == "undefined");
		  if (this.type == window.SYS_CATEGORY_TYPE_CATEGORY 
				  && isShowMore) {
			return true
		  }
		  return false
		},
  
		//是否显示选择框
		showSelect: function() {
		  var pWeiget = this.getParent()
		 //仅显示分类并且下一级不为空
		  var _isShowSelect = (pWeiget && pWeiget.isOnlyShowCate == 1 
							  && this.canSelect != "0") 
							  || (pWeiget && typeof pWeiget.isOnlyShowCate == "undefined");
		  if ((pWeiget.selType | this.type) == this.type && _isShowSelect) return true
		  return false
		},
  
		//是否选中
		isSelected: function() {
		  if (window._curIds) {
			// 查看window._curIds是否存在选中值
			if (this.fdId == window._curIds){
			  return true;
			}
		  } else {
			var pWeiget = this.getParent()
			if (pWeiget && pWeiget.curIds) {
			  var arrs = pWeiget.curIds.split(";")
			  if (array.indexOf(arrs, this.fdId) > -1) {
				return true
			  }
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
  