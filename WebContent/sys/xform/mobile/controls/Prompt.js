define([ "dojo/_base/declare", 
         "dojo/dom-construct", 
         "dojo/dom-prop", 
         "sys/xform/mobile/controls/xformUtil", 
         "dijit/registry", 
         "mui/form/_FormBase", 
         "dojo/query","dojo/dom", 
         "dojo/dom-class",
         "dojo/dom-attr",
         "dojo/dom-style",
         "dojo/on",
         "dojo/_base/lang",
         "dojo/topic"
         ], function(declare, domConstruct, domProp, xUtil, registry,
        		 _FormBase, query,dom,domClass,domAttr,domStyle,on,lang,topic) {
	var claz = declare("sys.xform.mobile.controls.Prompt", [ _FormBase], {
		
		content : "",
		
		contentShow : false,
		
		
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode,"lui_prompt_tooltip");
			this.iconDom = query("[name='" + this.fdId + "']",this.domNode)[0];
			domClass.add(this.domNode,"lui_mobile_tooltip");
			domClass.add(this.iconDom,"mui_prompt_tooltip_drop");
			this.contentDom = query("[name='" + this.fdId + "_content']",this.domNode)[0];
			this.setStyle(this.contentDom);
			this.content = this.contentDom.innerHTML;
		},
		
		postCreate: function() {
			this.inherited(arguments);
			on(this.domNode,"click",lang.hitch(this, this.promptLabelClick));
		},
		
		startup:function(){
			this.inherited(arguments);
			if (this.hidden === "true"){
				domStyle.set(this.domNode,"display","none");
			}
			domStyle.set(this.domNode,"min-width","1rem");
		},
		
		setStyle : function (dom){
			//将样式设置在span元素上
			if(this.font)	
				domStyle.set(dom,"fontFamily",this.font);
			if(this.size)
				domStyle.set(dom,"fontSize",this.size);
			if(this.color)
				domStyle.set(dom,"color",this.color);
			if(this.b=="true") 
				domStyle.set(dom,"fontWeight","bold");
			if(this.i=="true") 
				domStyle.set(dom,"fontStyle","bold","italic");
			if(this.underline=="true") 
				domStyle.set(dom,"textDecoration","underline");
		},
		
		/**
		 * 控件鼠标悬停事件,显示提示元素
		 * @param event
		 * @returns
		 */
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
				var content = this.htmlUnEscape(this.content)
				this.contentDom.innerHTML = content;
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
		
		/**
		 * 提示元素定位
		 * @returns
		 */
		contentElementPosition : function(){
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
			var inIndetail = xUtil.isInDetail(this);
			if(x > 220){
				domStyle.set(this.contentDom,"marginLeft","-250px");
			}else{
				//图标x坐标大于提示框的宽度的一半,如果x+提示框宽度+图标宽度 大于 屏幕宽度
				if (x + 240 + 31 > scrollWidth) {
					domStyle.set(this.contentDom,"marginLeft",-x/2+"px");
					domStyle.set(this.contentDom,"bottom","30px");
				} else {
					domStyle.set(this.contentDom,"marginLeft","15px");
				}
			}
			if((y - divHeight) < 0){
				domStyle.set(this.contentDom,"bottom",-(divHeight+10)+'px');
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
	});
	return claz;
});