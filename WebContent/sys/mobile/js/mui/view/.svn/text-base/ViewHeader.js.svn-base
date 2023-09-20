define(["dojo/_base/declare", "dojo/_base/lang", "dojo/cache", "dijit/_WidgetBase","dijit/_TemplatedMixin","mui/util"],
		function(declare, lang, cache, WidgetBase, TemplatedMixin, util){
	
		return declare("mui.view.ViewHeader",[WidgetBase, TemplatedMixin],{
			mode:'default',
			
			tempPath:null,
			
			context:{},
			
			templReplace:function(text,mapping){
				var tmpStr = text.replace(/<(script)[\s|\S]*?<\/\1>/ig,function(scriptHTML){//模板不支持外部网站script的嵌入
					var src = $(scriptHTML).attr("src");
					if(src){
						src = $.trim(src);
						if (src.substring(0, 1)=='/') {
							return scriptHTML;
						}
					}else{
						return scriptHTML;
					}
					return "";
				}).replace(/{\$(\s|\S)*?\$}/g,function(s){	
							return s.replace(/("|\\)/g,"\\$1")
											.replace("{$",'_s.push("')
											.replace("$}",'");')
											.replace(/{\%([\s\S]*?)\%}/g, '",$1,"').replace(/(\r\n|\n)/g,"\\$1");
				});
				var _a = []; 
				var _v = []; 
				var i;
				for(i in mapping){
					_a.push(i);
					_v.push(mapping[i]);
				}
				var fn = new Function(_a,"var _s=[];"+ tmpStr +" return _s.join('');");
				return fn.apply(window,_v);
			},
			
			buildRendering:function(){
				var tmplStr = ""
				if(this.tempPath != null && this.tempPath != ""){
					tmplStr = cache(util.formatUrl(this.tempPath), {sanitize: true});
				}else{
					 if(this.mode != null && this.mode != ""){
						tmplStr = cache(util.formatUrl("/sys/mobile/js/mui/view/templ/" + this.mode + ".tmpl"), {sanitize: true});
					}
				}
				if(tmplStr==""){
					window.console.warn("组件属性model和tempPath都为空，组件无用。");
					this.templateString = null;
				}else{
					this.templateString = this.templReplace(tmplStr, {util:util,data:this.context});
				}
				this.inherited(arguments);
			},
		
			startup:function(){
				this.inherited(arguments);
			}
		});
});