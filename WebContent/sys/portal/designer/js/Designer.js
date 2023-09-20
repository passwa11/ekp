define(function(require, exports, module) {	
	//require('sys/portal/designer/css/design.css');
	var $ = require("lui/jquery");
	var Class = require("lui/Class");
	var Evented = require('lui/Evented');
	var HtmlFormat = require('./HtmlFormat');
	var Toolbar = require('./Toolbar');
	var JSON = require("./JSON");
	 
	if(window.LDESIGNER==null){
		window.LDESIGNER={};
	}
	 

	var Desginer = new Class.create(Evented, {
		initialize : function(config) {
			var self = this;
			this.scene = config.scene;
			this.contextPath = config.contextPath;
			this.ref = config.ref;
			this.pageProperties = {};
			if(config.pageWidth){
				this.pageProperties.pageWidth=config.pageWidth;
			}
			this.pageTemplateList = config.pageTemplateList||[];
			this.source = $(document.getElementById(config.element));
			if($(this.source.val()).is("div[portal-type='Template']")){
				var tObj = $(this.source.val());
				this.ref = tObj.attr("ref");
				var tconfig = tObj.attr("data-config");
				if($.trim(tconfig)!=""){
					$.extend(this.pageProperties, toJSON(unescape(tconfig)));
				}
			}
			this.element = $("<div></div>"); 
			this.source.after(this.element);
			var designerOffsetTop = this.source.parent().offset().top; // 设计器纵坐标
			var pageHeight = $(window).innerHeight(); // 当前iFrame window窗口可视高度
			var iframeHeight = (pageHeight-designerOffsetTop-70)+"px";
			this.toolbar  = new Toolbar({"element":$("<div style='text-align: left;'></div>"),"desginer":this});
			this.editor = $("<iframe style='width:100%;height:"+iframeHeight+";background-color: white;' frameborder='0'></iframe>");
			if(config.style){
				var originalStyle = this.editor.attr("style");
				var newStyle = "";
				if(originalStyle.charAt(originalStyle.length-1)!=";"){
					newStyle = originalStyle + ";" + config.style;
				}else{
					newStyle = originalStyle + config.style;
				}
				this.editor.attr("style",newStyle);
			}
			this.element.append(this.toolbar.element);
			this.element.append(this.editor);
			
			this.children = [];
			this.instances = null;
			this.editWindow = null;
			this.editDocument = null;

			Com_Parameter.event["submit"].unshift(function() {
				self.source.val(self.getValue());
				return true;
			});
		},
		addChild : function(obj){
			this.children.push(obj);
		},
		start : function(){
			var self = this;
			this.editor.off().bind("load",function(){
				self.editWindow = this.contentWindow;
				self.editDocument = null;
				self.editDocument = $(self.editWindow.document);
			    /**
			     * 动态加载CSS
			     * @param HTMLDocument _document 文档对象
			     * @param {string} url 样式地址
			     */
			   var dynamicLoadCss = function(_document,url) {
			        var head = _document.getElementsByTagName('head')[0];
			        var link = _document.createElement('link');
			        link.type='text/css';
			        link.rel = 'stylesheet';
			        link.href = url;
			        head.appendChild(link);
			    };
				var design_css_url = self.contextPath+"/sys/portal/designer/css/design.css";
				//var panel_css_url = self.contextPath+"/sys/ui/extend/theme/default/style/panel.css";  #149413 页面配置选择个人中心模板，样式受旧版干扰，界面杂乱
				dynamicLoadCss(self.editWindow.document, design_css_url);
				//dynamicLoadCss(self.editWindow.document, panel_css_url);
				//加载完模板后初始可编辑区域
				self.instances = null;
				self.setValue($.trim(self.source.val()));				
			});
			var srcUrl = self.contextPath+"/sys/portal/designer/jsp/template.jsp?ref="+encodeURIComponent(this.ref)+"&v=427";
			this.editor.attr("src", srcUrl);
			
			// 在工具栏一行显示当前使用的模板名称
			var templateTitle = this.getTemplateTitle();
			this.toolbar.element.find(".current_page_template_title").text(templateTitle);
			return this;
		},
		onResize : function(){
			this.editDocument = $(this.editWindow.document);
			var h = this.editDocument.height();
			var w = this.editDocument.width();
			this.editor.height(h).width(w);
		},
		destroy : function(){
			for(var i=this.children.length-1;i>=0;i--){
				this.children[i].destroy();				
			}
		},
		setValue : function(val){
			var self = this; 
			this.children = [];
			var body = self.editDocument.find("body");
			var temp = $("<div>"+val+"</div>");
			body.append(temp.hide());
			//可编辑区域
			try{
				body.find("div[data-lui-mark='template:block']").each(function(){
					var block = $(this);
					var key = block.attr("key");
					var keyValue = temp.find("div[portal-type='./Editable'][portal-key='"+key+"']");
					if(keyValue != null && keyValue.length > 0){
						block.append(keyValue);
					}else{
						block.append("<div portal-key='"+key+"' portal-type='./Editable'></div>");
					}
				});
				body.find("div[data-lui-mark='template:aside']").each(function(){
					var aside = $(this);
					var key = aside.attr("key");
					var keyValue = temp.find("div[portal-type='./AsideEditable'][portal-key='"+key+"']");
					if(keyValue != null && keyValue.length > 0){
						aside.append(keyValue);
					}else{
						aside.append("<div portal-key='"+key+"' portal-type='./AsideEditable'></div>");
					}
				});
				body.find("div[data-lui-mark='template:header']").each(function(){
					var header = $(this);
					var key = header.attr("key");
					var keyValue = temp.find("div[portal-type='./Header'][portal-key='"+key+"']");
					if(keyValue != null && keyValue.length > 0){
						header.append(keyValue);
					}else{
						header.append("<div portal-key='"+key+"' portal-type='./Header'></div>");
					}
				});
				body.find("div[data-lui-mark='template:footer']").each(function(){
					var footer = $(this);
					var key = footer.attr("key");
					var keyValue = temp.find("div[portal-type='./Footer'][portal-key='"+key+"']");
					if(keyValue != null && keyValue.length > 0){
						footer.append(keyValue);
					}else{
						footer.append("<div portal-key='"+key+"' portal-type='./Footer'></div>");
					}
				});
				// 匿名模板去除“引导页设置”功能 @author 吴进 by 20191108
				if ("anonymous" != this.scene) {
					body.find("div[data-lui-mark='template:guide']").each(function(){
						var guide = $(this);
						var key = guide.attr("key");
						var keyValue = temp.find("div[portal-type='./Guide'][portal-key='"+key+"']");
						if(keyValue != null && keyValue.length > 0){
							guide.append(keyValue);
						}else{
							guide.append("<div portal-key='"+key+"' portal-type='./Guide'></div>");
						}
					});
				}
			}catch (e) {
				if(window.console)
					console.log(e);
			}
			temp.remove();
			delete temp;
			self.parser();
		},
		setTemplate : function(tmpl){			
			this.source.val(this.getValue());
			this.ref = tmpl.ref;
			if(this.children != null){
				for(var i=this.children.length-1;i>=0;i--){
					this.children[i].destroy();
				}
			}
			this.start();
		},
		getPageProperties : function(){
			return this.pageProperties;
		},
		setPageProperties : function(data){
			this.pageProperties = data;
		},
		setTheme : function(theme){
			
		},
		parser : function(){
			var self = this;
			var modules = [];
			var elements = [];
			var body = this.editDocument.find("body");
			body.find("[portal-type]").each(function() {
				elements.push(this);
				modules.push($(this).attr('portal-type'));
			});
			if (modules.length) {
				require.async(modules, function() {
					self.instances = {};
					for (var i = 0; i < arguments.length; i++) {
						var clz = arguments[i];
						var instance = new clz({"body":body, "element": elements[i]});
						self.instances[instance.key] = instance;
					}
					
					var key = "";
					for (key in self.instances)   {
						try {
							var instance = self.instances[key];
							var parentElement = instance.element.parents("[portal-type]");
							if (parentElement.length < 1) { 
								instance.setParent(self);
								self.addChild(instance);
							}else{
								var parentElement = $(parentElement[0]);
								var parent = self.instances[parentElement.attr('portal-key')];		
								instance.setParent(parent);
								parent.addChild(instance);
							}
						} catch(e) {
							if (window.console)
								console.error(e.stack);
						}
					}
					for (key in self.instances)   {
						var instance = self.instances[key];
						if(instance.startup)
							instance.startup();
					}
					delete modules;
					delete elements;			
					
					var iframeHeight = self.editWindow.innerHeight || self.editWindow.document.documentElement.clientHeight; // 当前iFrame 可视高度
					var defaultHeight = iframeHeight-150; // 可编辑区域默认高度（ window窗口高度减去页眉、页脚以及中间的上下两个间隙DIV的高度）
					var editable = body.find("div[portal-type='./Editable']"); // 可编辑区域DIV jQuery对象
					editable.css("min-height",defaultHeight+"px");  //重置可编辑区域最小高度（使内容高度填充满当前iFrame可视高度）
					
				});
			}
		},
		getValue : function(){
			var val = [];
			for(var i=0;i<this.children.length;i++){				
				val.push(this.getOuterHTML(this.children[i].element.get(0)));
			}
			val = val.join('\r\n');
			var template = "ref='"+this.ref+"'";
			val = "<div portal-type='Template' "+template+" data-config=\"" + escape(JSON.stringify(this.getPageProperties())) + "\">"+val+"</div>";
			val = val.replace(/jquery([0-9])+\=\"([0-9])+\"/ig,'');
			return HtmlFormat(val.replace(/^\s+/, ''), 4, ' ', 80);
		},
		getTemplateTitle : function(){
			var self = this;
			var pageTemplateList = this.pageTemplateList;
			var title = null;
			for(var i=0;i<pageTemplateList.length;i++){
				var pageTemplate = pageTemplateList[i];
				if(self.ref == pageTemplate.ref){
					title = pageTemplate.refName;
					break;
				}
			}
			return title;
		},
		getOuterHTML : function(el){
			if(document.documentMode){
				return el.outerHTML;
			}else{
				var div = document.createElement("div");
				div.appendChild(el.cloneNode(true));
				var contents = div.innerHTML;
				div = null;
				delete div;
				return contents;
			}
		}
	});
	
	var toJSON = function(str){
		try{
			return eval("("+str+")");
			//return JSON.parse(str);
			//return (new Function("return (" + str + ");"))();
		}catch(e){
			alert(str);
			alert(e);
		}
	};
	module.exports = Desginer;
});