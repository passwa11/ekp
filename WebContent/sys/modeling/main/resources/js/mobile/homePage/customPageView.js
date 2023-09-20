/**
 * 
 */
define(['dojo/_base/declare','dojo/_base/lang',
		'dojo/_base/array',
		'dojo/dom-class',
		'dojo/dom-attr',
		'dojo/dom-style',
		'dojo/html',
		'mui/view/DocScrollableView',
		"dojo/request", "mui/util", "dojo/topic",
		"sys/modeling/main/resources/js/mobile/homePage/custom/ModelingCustomPageUtil",
		"dojo/query",
		"dojo/dom-construct"],
		function(declare, lang, array, domClass, domAttr, domStyle, html, DocScrollableView, request, util, topic,ModelingCustomPageUtil,query,domConstruct){
	
	return declare('sys.modeling.main.resources.js.mobile.homePage.customPageView', [DocScrollableView], {
		
		url : "/sys/modeling/main/mobile/modelingAppMainMobile.do?method=getDefaultModeCfg&fdId=!{fdAppId}&fdMobileId=!{fdMobileId}",
		
		scrollDir : "v",
		
		fdAppId : "",
		fdMobileId:"",
		
		startup : function() {
			if (this._started)
				return;
			this.doLoad();
			// 物理按键点击返回时，需要重新加载
			this.connect(window,'pageshow','_pageshow');
			this.inherited(arguments);
		},
		
		_pageshow : function(evt){
			/* 注：经验证，只有第一次浏览器回退的时候persisted才会为true，所以必须使用页面刷新的方式来显示新的列表数据
			 * 不可以手动去发事件去更新列表，因为只有强制刷新整个页面后，才会使得每次浏览器回退时persisted都为true
			*/ 
			if(evt.persisted){	
				window.location.reload();
			}
		},
		
		doLoad : function(){
			console.log("doLoad");
			// 请求获取nav的信息
			var url = util.urlResolver(this.url, {fdAppId: this.fdAppId,fdMobileId: this.fdMobileId});
			var self = this;
			request.get(util.formatUrl(url),{handleAs : 'json'}).then(function(json){
				// 处理请求返回的数据
				if(json && json.status === "00"){
					self.formatInfo(json.data);
				}
			});
		},

		// 格式化数据
		formatInfo : function(data){
			var portletInfos = data.fdPortlets;
			var portletsHtml = this.portletsHtmlDraw(portletInfos);
			var headDom = this.createItem(data.fdBackground);
			var options={
				container:headDom
			}
			this.renderPortletsWithTemplate(portletsHtml,options);
			return data;
		},

		renderPortletsWithTemplate: function(tmplContent, options){
			if(typeof(tmplContent)=='string'){
				tmplContent = tmplContent.trim();
			}
			if(!tmplContent || tmplContent.length==0){
				return;
			}

			topic.publish('/mui/navView/resize',this)

			var self = this;
			var dhs = new html._ContentSetter({
				node: options.container,
				parseContent: true,
				cleanContent: true,
				setContent: function(){
					this.inherited("setContent",arguments);
					// 所有的子组件绑定key，避免不同视图下组件通信混乱
					// array.forEach(this.node.childNodes, function(element){
					// 	if(element.nodeType === 1){
					// 		domAttr.set(element, 'key', options.key);
					// 	}
					// });
				}
			});
			var node = dhs.set(tmplContent);
			dhs.parseDeferred.then(lang.hitch(this, function(widgetList){
				array.forEach(widgetList, function(child){
					if(child.resize){
						child.resize();
					}
				},this);
			}));
			dhs.tearDown();
		},

		portletsHtmlDraw : function(portletInfos){
			var items = [];
			for(var i = 0;i < portletInfos.length;i++){
				var portletInfo = portletInfos[i];
				items.push(ModelingCustomPageUtil.getPortletHtml(portletInfo.fdType,this.fdMobileId,portletInfo));
			}
			return items.join("");
		},

		onComplete : function(data){
			topic.publish('/sys/modeling/mobile/index/load', data);
		},
		
		getDim: function(){
			// summary:
			//		Returns various internal dimensional information needed for calculation.

			var d = {};
			// content width/height
			// 当前页面特殊，高度应使用scrollHeight
			d.c = {h:this.containerNode.scrollHeight, w:this.containerNode.offsetWidth};

			// view width/height
			d.v = {h:this.domNode.offsetHeight + this._appFooterHeight, w:this.domNode.offsetWidth};

			// display width/height
			d.d = {h:d.v.h - this.fixedHeaderHeight - this.fixedFooterHeight - this._appFooterHeight, w:d.v.w};

			// overflowed width/height
			d.o = {h:d.c.h - d.v.h + this.fixedHeaderHeight + this.fixedFooterHeight + this._appFooterHeight, w:d.c.w - d.v.w};
			return d;
		},
		createItem : function(value){
			var boxDom = domConstruct.create("div", {
				className : "mui_personnel_process_box"
			}, this.containerNode);
			var contentDom = domConstruct.create("div", {
				className : "mui_personnel_process_content"
			}, boxDom);
			var className = "mui_personnel_process_head";
			if(value === "1"){
				className += " blueBg";
			}else if(value === "2"){
				className += " collapseBg";
			}
			var headDom = domConstruct.create("div", {
				className : className
			}, contentDom);
			return headDom;
		}
		
	});
});