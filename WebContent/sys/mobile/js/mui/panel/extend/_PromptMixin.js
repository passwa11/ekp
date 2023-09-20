define(["dojo/_base/declare","dojo/_base/lang","mui/dialog/Prompt","dojo/dom-class","dojo/dom-style","dojo/on","dojo/dom"],function(declare,lang,Prompt,domClass,domStyle,on,dom){
	
		return declare("mui.panel._PromptMixin",[Prompt],{
			
			moveNodeId:'scrollView',//默认值
			
			buildRendering:function(){
				this.inherited(arguments);
				this.elem = this.iconNode;
				
				on(document,"click",lang.hitch(this, this._hideNode));
				on(dom.byId(this.moveNodeId),"touchmove",lang.hitch(this, this._hideNode));
			},
			
			contentElementPosition:function(){
				this.inherited(arguments);
				if(domClass.contains(this.contentDom,"lui_dropdown_tooltip_menu_down")){
					domClass.remove(this.contentDom,"lui_dropdown_tooltip_menu_down");
					domClass.add(this.contentDom,"lui_dropdown_tooltip_menu_right_down");
					domClass.add(this.contentDom,"lui_dropdown_tooltip_menu_right_down_recover");
					//修改提示框内容的位置
					var height = -(this.contentDom.offsetHeight + 10);
					domStyle.set(this.contentDom,"bottom",height+"px");
				}
			},
			
			_hideNode:function(event){
				var target = event ? event.target : null;
				if(target == this.elem || target == this.domNode){
					return;
				}
				if(!this.contentShow){
					return;
				}
				var isTarget = false;
				while(target.parentNode){
					if(target == this.elem || target == this.domNode){
						isTarget = true;
						break;
					}
					target = target.parentNode;
				}
				if(isTarget)
					return;
				domStyle.set(this.contentDom,"display","none");
				this.contentShow = false;
			}
			
		});
});