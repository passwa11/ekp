define([ "dojo/_base/declare", "dojo/dom","dojo/dom-construct", "mui/rtf/_ImageResizeMixin",
		"mui/rtf/_TableResizeMixin", "mui/rtf/_VideoResizeMixin" ,"mui/util","dojo/_base/array",
		"dojo/_base/lang",
		"dojo/dom-attr",
		"dojo/query",
		"dojo/on",
		"dojo/dom-style",
		"dojo/topic",
		"dojo/ready"], function(
		declare, dom, domConstruct, _ImageResizeMixin, _TableResizeMixin, _VideoResizeMixin, util,array,
		lang,
		domAttr,
		query,
		on,
		domStyle,
		topic,
		ready) {

	var claz = declare("mui.rtf.RtfResize", [ _ImageResizeMixin,
			_TableResizeMixin, _VideoResizeMixin ], {

		// 包含编辑器内容dom对象
		containerNode : null,

		// RTF名称
		name : null,

		formatContent : function(domNode) {
			this.inherited(arguments);
			ready(function(){
				//对domNode做个最大宽度的限制，避免外出溢出页面，导致里层滚动失效
				var clientX = domNode.getBoundingClientRect().x || domNode.getBoundingClientRect().left || 0;//相对窗口的横向位置
				var clientWidth = document.documentElement.clientWidth;//窗口大小
				if(clientWidth && domNode.getBoundingClientRect().width > clientWidth){
					domStyle.set(domNode,{
						"max-width":(clientWidth - clientX - 12) + 'px'
					})
				}
			})
		},
		
		renderTitle : function(domNode){
			if(dojoConfig.newMui && this.label){
				this.tipNode = domConstruct.create("div",{'className':'muiFormEleTip'},domNode,'first');
				this.titleNode = domConstruct.create("span", {
					'className' : 'muiFormEleTitle',
					'innerHTML' : util.formatText(this.label)
				},this.tipNode);
			}
		},

		setName : function(name) {
			this.name = name;
		},
		
		setLabel : function(label) {
			this.label = label;
		},

		// 构造函数
		constructor : function(name,label) {
			this.inherited(arguments);
			if (typeof (name) == "object") {
				this.setName(name['name']);
				this.containerNode = name['containerNode'];
			} else {
				this.setName(name);
			}
			this.setLabel(label);
			this.load();
		},

		load : function() {
			var _container = null;
			if (this.name != null) {
				_container = '_____rtf_____' + this.name;
				_container = dom.byId(_container);
			} else {
				_container = this.containerNode;
			}
			this.renderTitle(_container);
			this.formatContent(_container);
			array.forEach(query('a', _container), lang.hitch(this, function(
					item) {

				var href = item.href;

				domAttr.set(item, 'href', 'javascript:;');

				on(item, 'click',
						function() {

							if (href
									&& (href.indexOf('http') == 0 || href
											.indexOf('/') == 0)) {

								window.open(href, '_self');

							} else {

								new Function(href)();

							}

						})

			}));
			
		}
	});
	return claz;
});