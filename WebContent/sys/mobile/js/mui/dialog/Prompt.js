define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase","dojo/dom-attr","dojo/dom-class",
        "dojo/query","dojo/touch","dojo/topic","dojox/mobile/viewRegistry","dijit/registry",
        "dojo/dom-construct","mui/util","dojo/on","dojo/dom","dojo/_base/array","dojo/dom-style","dojo/ready"],function(declare,lang,WidgetBase,domAttr,domClass,query,touch,
        		topic,viewRegistry,registry,domConstruct,util,on,dom,array,domStyle,ready){
	
		return declare("mui.dialog.Prompt",[WidgetBase],{
			
			content : "", //提示内容
			
			font : "", //字体
			
			text:"",
			
			elem:"",
			
			fontSize : "1.4rem", //字体大小
			
			fontColor : "#FFF", //字体颜色
		
			contentShow : false,
			
			buildRendering:function(){
				this.inherited(arguments);
				this.buildDefaultRender();	
			},
			
			postCreate: function() {
				this.inherited(arguments);
				if(this.elem){
					on(this.elem,"click",lang.hitch(this, this.promptLabelClick));
				}else{
					on(this.domNode,"click",lang.hitch(this, this.promptLabelClick));
				}
			},
			
			buildDefaultRender:function(){
				
				domClass.add(this.domNode,' muiFormEleWrap muiFormStatusEdit lui_prompt_tooltip lui_mobile_tooltip muiFormLeft');
				if(this.content){
					this.content = this.content;
				}else{
					if(this.domNode.innerHTML)
					this.contentNameDom = query("[name='" + this.fdId + "_content']",this.domNode)[0];
					this.content = this.contentNameDom.innerHTML;
				}
				
				this.iconNode = domConstruct.create('span',{
					className : 'mui-PromptInfo',
					innerHTML : "<a href='javascript:;' style='color:#C4C6CF'>?</a>"
				},this.domNode)
				
				this.contentDom = domConstruct.create('div',{
					style : 'display:none',
					className : 'lui_dropdown_tooltip_menu lui_tool_tip_ding'
				},this.domNode)
				
				//this.setStyle(this.contentDom);
			},
			

			
			promptLabelClick:function(event){
				event = event || window.event;
				event.cancelBubble = true;
				if (event.preventDefault){
					event.preventDefault();
				}
				if (event.stopPropagation) {
					event.stopPropagation();
				}
				//连续点击不能超过一秒,不知道为啥ios只点了一次会进来两次这个事件
				var nowTime = new Date().getTime();
			    var clickTime = this.ctime;
			    if( clickTime != 'undefined' && (nowTime - clickTime < 500)){
			        return false;
			     }
			    this.ctime = nowTime;
				if (this.contentShow){
					domStyle.set(this.contentDom,"display","none");
					this.contentShow = false;
				}else{
					var unContent = this.htmlUnEscape(this.content);
					//判断是否含有html标签
					var hasTag = /<(\w+)\s*.+\/?>(?:<\/\1>)?/.test(unContent);
					if (hasTag) {
						this.contentDom.innerHTML = unContent;
					} else {
						this.contentDom.innerText = unContent;
					}
					
					var length = this.contentWidth(this.contentDom.innerText);
					domAttr.set(this.contentDom,"style","width:"+length*0.7+"rem");
					
					
					var aObj = query('a',this.contentDom);
					var url = "";
					var eventJs = "window.open('url','_self')";
					var target = "_blank";
					aObj.forEach(function(dom,index){
						url = domAttr.get(dom,"href");
						if(domAttr.get(dom,"target")){
							target = domAttr.get(dom,"target");
						}
						domAttr.set(dom,"onclick",eventJs.replace("url", url).replace("target", target));
						domAttr.set(dom,"href","javascript:void(0);");
					})
					domStyle.set(this.contentDom,"zIndex","2000");
					domStyle.set(this.contentDom,"display","inline-block");
					this.contentElementPosition();
					this.contentShow = true;
				}
			},
			
			contentElementPosition : function(){
				//图标x轴坐标
				var iconx=this.iconNode.offsetLeft+document.body.scrollLeft;
				//图标y轴坐标
				var icony=this.iconNode.offsetTop;
				
				//图标x轴坐标
				var x=this.domNode.offsetLeft+document.body.scrollLeft;
				//图标y轴坐标
				var y=this.domNode.offsetTop;
				//scrollWidth 对象的实际内容的宽度
				var scrollWidth = document.body.scrollWidth;
				//scrollHeight 对象的实际内容的高度
				var scrollHeight = document.body.scrollHeight;
				//提示框的宽度
				var divWidth = domStyle.get(this.contentDom,"width");
				//提示框的高度
				var divHeight = domStyle.get(this.contentDom,"height");
				offset = {};
				//图标X坐标是否大于屏幕的一半
				domClass.remove(this.contentDom,"lui_dropdown_tooltip_menu");
				if(x<(scrollWidth/2)){
					if(this.contentNameDom){
						domClass.add(this.contentDom,"lui_dropdown_tooltip_menu")
					}else{
						if(divHeight<y){
							domClass.add(this.contentDom,"lui_dropdown_tooltip_menu");//右上方
						}else{
							domClass.add(this.contentDom,"lui_dropdown_tooltip_menu_down");
						}
					}

					
				}else{
					if(this.contentNameDom){
						domClass.add(this.contentDom,"lui_dropdown_tooltip_menu_right");
					}else{
						if(divHeight<y){
							domClass.add(this.contentDom,"lui_dropdown_tooltip_menu_right");//左上方
						}else{
							domClass.add(this.contentDom,"lui_dropdown_tooltip_menu_right_down");
						}
					}
				}
				var scrollViewWgt = this.findDetailTableScrollView();
				if (scrollViewWgt) {
					domStyle.set(scrollViewWgt.domNode,"overflow","");
					var tableAddNode = query(".muiDetailTableAdd",query(".detailTableContent",scrollViewWgt.domNode)[0])[0];
					if (tableAddNode) {
						var plusNode = query(".mui-plus",tableAddNode)[0];
						if (plusNode) {
							domAttr.set(plusNode,"style","position:static");
						}
					}
				}
			},
			
			setStyle : function (dom){
				//将样式设置在span元素上
				if(this.font)	
					domStyle.set(dom,"fontFamily",this.font);
				if(this.size){
					this.fontSize = this.size
				}
					domStyle.set(dom,"fontSize",this.fontSize);
				if(this.color){
					this.fontColor = this.color;
				}
					domStyle.set(dom,"color",this.fontColor);
				if(this.b=="true") 
					domStyle.set(dom,"fontWeight","bold");
				if(this.i=="true") 
					domStyle.set(dom,"fontStyle","bold","italic");
				if(this.underline=="true") 
					domStyle.set(dom,"textDecoration","underline");
				domStyle.set(dom,"background-color","#4285f4");
			},
			
			findDetailTableScrollView : function(){
				var tableNormalNode = this.findTableNormal();
				if (tableNormalNode) {
					var scrollViewNode = query(this.domNode).closest(".mblScrollableView")[0];
					if (scrollViewNode) {
						return registry.byNode(scrollViewNode);
					}
				} 
				return null;
			},
			
			findTableNormal : function(){
				var domNode = this.domNode;
				if(domNode){
					var parent = domNode.parentNode;
					while(parent){
						if(domClass.contains(parent,"detailTableNormal") || domClass.contains(parent,"detailTableSingleRow")){
							return parent;
						}
						parent = parent.parentNode;
					}
				}
				return null;
			},
			
			
			
			htmlUnEscape : function(s){
				if (s == null || s ==' ') return '';
				s = s.replace(/&amp;/g, "&");
				s = s.replace(/&quot;/g, "\"");
				s = s.replace(/&lt;/g, "<");
				s = s.replace(/&nbsp;/g," ");
				return s.replace(/&gt;/g, ">");
			},
			
			contentWidth : function(s){
			
				var len = 0;
				for (var i=0; i<s.length; i++) { 
					var c = s.charCodeAt(i); 
					 if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
					       len++;
					     }  else {
					         len+=2;
					     } 
				}
				return len;
			},
			
			startup:function(){
				this.inherited(arguments);
				if (this.hidden === "true"){
					domStyle.set(this.domNode,"display","none");
				}
				domStyle.set(this.domNode,"min-width","1rem");
			}
		});
});