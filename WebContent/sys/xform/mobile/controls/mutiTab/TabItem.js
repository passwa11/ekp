/**
 * 多页签组件 页签组件
 */
define([
	"dojo/_base/declare",
	"mui/nav/NavItem",
	"dojo/query",
	"dijit/registry",
	"dojo/dom-class"
	],function(declare,NavItem,query,registry,domClass){
	
	return declare("sys.xform.mobile.controls.MutiTab.TabItem",[NavItem],{
		
		onItemSelected: function(srcObj, evt) {
			if (!srcObj || typeof this.key == "undefined" || typeof evt == "undefined" || typeof evt.key == "undefined" || this.key != evt.key) {
				return;
			}
			var isChild = false;
			var childs = srcObj.getChildren();
			for(var i=0; i<childs.length; i++){
				if(childs[i].id != this.id) continue;
				isChild = true;
				break;
			}
			if(!isChild) return;
			this._onClick({
				target: this.domNode
			});
		},
		
		/*
		 * 覆盖原有的点击事件，原有的点击事件是进行视图的切换，但是视图切换后需要重新回到定位，
		 * 多页签和其他的导航组件事件冲突比较难处理，所以这里覆盖原有事件，点击后不太跳转视图，
		 * 而是直接进行显示或者隐藏
		 */
		_onClick:function(e){
			if (e) {
		        var target = e.target;
		        this.beingSelected(target);
		    }
			//处理视图
			var moveTo = this.moveTo;//目标视图
			var mutiTabId = this.mutiTabId;//多页签id
			var pWgt = registry.getEnclosingWidget(this.domNode.parentNode);
			if(!mutiTabId && pWgt){
				mutiTabId = pWgt.id;
			}
			if(mutiTabId && pWgt){
				query("[mutiTabId='"+mutiTabId+"']",pWgt.domNode.parentNode).forEach(function(node,index){
					domClass.replace(node,"muiFormTabViewNone","muiFormTabViewBlock");
				})
				query("[name='"+moveTo+"']",pWgt.domNode.parentNode).forEach(function(node,index){
					domClass.replace(node,"muiFormTabViewBlock","muiFormTabViewNone");
				})
			}
		}
	})
})