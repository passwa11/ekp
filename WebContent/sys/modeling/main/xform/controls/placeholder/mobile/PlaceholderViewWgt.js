/**
 * 
 */
define([ "dojo/_base/declare", "dojo/_base/lang", "dijit/_WidgetBase","dojo/request", "mui/util", "dojo/topic", "dojo/dom-construct"], 
		function(declare, lang, WidgetBase, request, util, topic, domConstruct) {
	
	var claz = declare("sys.modeling.main.xform.controls.placeholder.mobile.PlaceholderViewWgt", [ WidgetBase ], {
		
		buildRendering : function(){
			this.inherited(arguments);
			this.createViewDom();
		},
		
		createViewDom : function(){
			var through = this.envInfo.through || {};
			if(through.isThrough){
				var values = this.value || "";
				var texts = this.text || "";
				this.buildDrillingDoms(values, texts, through);
			}else{
				this.domNode.innerHTML = this.text;
			}
		},
		
		buildDrillingDoms : function(values, texts, info){
			var valArr = values.split(";");
			var txtArr = texts.indexOf("&&")>-1 ? texts.split("&&") : texts.split("&amp;&amp;");
			if(valArr.length === txtArr.length){
				for(var i = 0;i < valArr.length;i++){
					var val = valArr[i];
					var txt = txtArr[i];
					if(val){
						this.buildDrilling(txt, val, info);
					}
				}
			}else{
				this.domNode.innerHTML = texts;
				console.error("【业务关联控件】实际值长度和显示值长度不一致,没法穿透!");
			}
		},
		
		buildDrilling : function(txt, val, info){
			var targetUrl = this.getDrillingUrl(info.url, val);
			var drillingDom = domConstruct.create("div",{
				style:{
					'height':'1.8rem',
					'line-height':'1.8rem',
					'padding':'4px 0px',
					'font-size': '1.5rem',
					'color': '#57A7DA'
				}
			}, this.domNode);
			// 图标
			domConstruct.create("i", {
				className : 'mui mui-associated-doc',
				style:{
					'float': 'left',
					'margin-right':'5px',
					'color':'#57A7DA',
					'margin-top':'3.5px'
				}
			}, drillingDom);
			// 标题
			var txtDom = domConstruct.create("p",{
				style:{
					'font-size': '1.5rem'
				}
			},drillingDom);
			txtDom.innerHTML = txt;
			
			this.connect(drillingDom, "click" , function(){
				Com_OpenWindow(targetUrl, '_self');
			});
		},
		
		// 获取穿透链接
		getDrillingUrl : function(tmpUrl, value){
			var url = "";
			if(value && tmpUrl){
				url = util.formatUrl(tmpUrl.replace(/\:fdId/g, value));
			}else{
				console.error("【业务关联控件】获取穿透跳转链接失败!跳转的模板链接:" + tmpUrl + ";fdId:" + idInfo.value);
			}
			return url;
		}
	});
	
	return claz;
});