define(["dojo/_base/declare","mui/dialog/Prompt","dojo/dom-style","dojo/dom-construct","dojo/dom-class"],function(declare,prompt,domStyle,domConstruct,domClass){
	return declare("sys.lbpmservice.mobile.lbpmSummaryApproval.LbpmSummaryPrompt",[prompt],{
		
		buildDefaultRender:function(){
			this.inherited(arguments);
			//隐藏默认的图标展示
			domStyle.set(this.iconNode,"display","none");
			//建立数据内容展示
			this.targetNode = domConstruct.create("div",{
				className:'value',
				innerHTML:this.content
			},this.domNode);
		},
		
		contentElementPosition:function(){
			this.inherited(arguments);
			if(domClass.contains(this.contentDom,"lui_dropdown_tooltip_menu_down")){
				//修改提示框内容的位置
				var height = -(this.contentDom.offsetHeight + 10);
				domStyle.set(this.contentDom,"bottom",height+"px");
			}
		}
	});
});