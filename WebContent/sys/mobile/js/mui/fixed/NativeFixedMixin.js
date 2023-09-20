/**
 * 原生吸顶插件
 **/
define([
  "dojo/_base/declare",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/dom-construct",
  "dojo/query",
  "dojo/window",
  "dojo/dom-geometry",
  "dijit/registry"
], function(declare, domClass, domStyle, domConstruct, query, win, domGeometry, registry) {
  return declare("mui.fixed.NativeFixedMixin", [], {
	  
	baseClass: "muiNativeFixedComponent",  
	  
    fixedClass: "muiFixed",
    
    /* Fixed组件定位排序号（适用于一个页面有多个Fixed组件时，top定位根据排序号依次从上至下排序）    */
    fixedOrder: 0,

    startup: function() {
      this.inherited(arguments);
      window.addEventListener("scroll", this.scrollEvent.bind(this));
    },

    scrollEvent: function(evt) {
        if (this.noSrcoll) {
            return;
        }
      if (!this.offsetTop) {
        this.offsetTop = domGeometry.getContentBox(this.domNode).t;
      }

      var doc = win.getBox()
      
      var fixedTop = this.calculateFixedTop();
      
      if (doc.t + fixedTop > this.offsetTop) {
        this.addPlaceholder();
        domClass.add(this.domNode, "muiFixed");
        if(fixedTop!=0) domStyle.set(this.domNode, "top", fixedTop+"px");
      } else {
        this.removePlaceholder();
        domClass.remove(this.domNode, "muiFixed");
        domStyle.set(this.domNode, "top", "");
      }

    },
    
    
	/**
	* 计算当前fixed组件应该定位的top值（适用于一个页面有多个Fixed组件时，top定位根据排序号依次从上至下排序）
	* 计算逻辑：根据排序号，累加当前fixed组件之前可见的其它fixed组件的高度
	* @return 返回计算得出的top值
	*/	
    calculateFixedTop: function(){
    	var fixedTop = 0;
    	var nativeFixedNodes = query(".muiNativeFixedComponent");
    	if(nativeFixedNodes.length>0){
        	var beforeFixedComponents = [];
        	for(var i=0;i<nativeFixedNodes.length;i++){
        		var fixedNode = nativeFixedNodes[i];
        		if(this._isVisible(fixedNode)){
            		var component = registry.byNode(fixedNode);
            		if(component && component.fixedOrder<this.fixedOrder){
            			beforeFixedComponents.push(component);
            		}
        		}
        	}
        	
        	beforeFixedComponents = beforeFixedComponents.sort(this.orderAscCompare);
        	
        	for(var i=0;i<beforeFixedComponents.length;i++){
        		var component = beforeFixedComponents[i];
        		fixedTop+=domGeometry.getContentBox(component.domNode).h;
        	}
    	}
    	return fixedTop;
    },
    

	/**
	* 排序规则函数（升序）
	* @param obj1 排序号一
	* @param obj2 排序号二
	* @return 1 or -1 or 0
	*/	
    orderAscCompare : function(obj1,obj2){
        var val1 = obj1.fixedOrder;
        var val2 = obj2.fixedOrder;
        if(val1 > val2){
            return 1;
        }else if(val1 < val2){
            return -1;
        }else{
            return 0;
        }
    },
    
	/**
	* 判断DOM元素是否可见
	* @param node DOM元素
	* @return boolean
	*/			
	_isVisible: function(node){
		var visible = function(dom){
			return domStyle.get(dom, "display") !== "none";
		};
		for(var n = node; n.tagName !== "BODY"; n = n.parentNode){
			if(!visible(n)){ return false; }
		}
		return true;
	},

    removePlaceholder: function() {
      if (this.hoderNode) {
        domConstruct.destroy(this.hoderNode);
        this.hoderNode = null;
      }
    },

    // 占位符
    addPlaceholder: function() {
      if (this.hoderNode) {
        return;
      }
      var width = this.domNode.offsetWidth,
        height = this.domNode.offsetHeight;

      this.hoderNode = domConstruct.create("div", {}, this.domNode, "after");
      domStyle.set(this.hoderNode, {
        width: width + "px",
        height: height + "px",
        visibility: "hidden"
      });
    }
  })
})
