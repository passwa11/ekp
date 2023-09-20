define([
    "dojo/_base/declare",
    'dijit/_WidgetBase',
    "dijit/registry",
    'dojox/mobile/viewRegistry',
    'dojo/dom-construct',
    'dojo/dom-style',
    'dojo/dom-attr',
    'dojo/touch',
    'mui/util',
    './_IframePushAppendMixin',
    'resource/js/domain'
	], function(declare, _WidgetBase, registry, viewRegistry, domConstruct, domStyle, domAttr, touch, util, _IframePushAppendMixin) {
	
	/**
	 * 该组件对嵌入页面有如下要求:
	 * 1、嵌入页面需要加载如下js:  /resource/js/domain.js
	 * 
	 * 2、高度自适应问题:
	 * 		为了解决高度自适应问题，嵌入页面在渲染完成后需利用domain.call发送`$ekpIframeListLoaded`事件给到父窗口，具体代码如下:
	   		
	   		domain.call(parent, "$ekpIframeListLoaded",[{
				target : domain.getParam(window.location.href,'LUIID'), //写死，接受事件的目标组件ID
				geometry : {
					height : domain.getBodySize().height, //嵌入页面的高度
					width :  domain.getBodySize().width //嵌入页面的宽度
				},
				data : {
					loadOver : self._loadOver //是否存在更多数据，分页时起作用；否则设置为true
				}
			}]);
	
	 * 
	 * 3、分页问题:
	 * 		组件在滚动到页面底部时会往内部发送`$ekpIframePush`的事件；
	 * 		所以如果你的嵌入页面有分页需求，可订阅此事件并往自己的页面中添加下一页内容，当然渲染完成后记得再次发送`$ekpIframeListLoaded`事件完成高度自适应，具体代码如下:
	 * 
	  	domain.register('$ekpIframePush',function(event){
			//处理下一页内容渲染逻辑
			//渲染完成发送`$ekpIframeListLoaded`事件
		});
	 
	 * 
	 * 4、如果页面中存在打开链接的需求，请以'_top'形式打开。
	 * 
	 * 5、参考文件：/mui/list/iframe/_IframePushAppendMixin(`$ekpIframePush`事件的运用)、 /mui/list/iframe/_IframeItemListMixin(`$ekpIframeListLoaded`事件的运用)
	 */
	
	var clz =  declare("mui.list.iframe.Iframe", [ _WidgetBase, _IframePushAppendMixin ], {
		
		url: '',
		
		//是否马上加载
		lazy: true,
		
		_setUrlAttr: function(url){
			this.url = util.formatUrl(url);
		},
		
		startup : function() {
			if(this._started){ return; }
			this.inherited(arguments);
			if(!this.lazy){
				this.doLoad();
			}
		},
		
		doLoad : function(handle, append){
			var self = this;
			if (this.busy) {
				return;
			}
			this.busy = true;
			if (this.append && this._loadOver) {
				this.busy = false;
				return;
			}
			if(!this.iframeNode){
				var ___url = this.url;
				___url = util.setUrlParameter(___url, 'LUIID', this.id);
				var iframeNode = this.iframeNode = domConstruct.create('iframe',{
					src : ___url,
					scrolling : 'no'
				},this.domNode, 'first');
				domStyle.set(iframeNode,{ border :'0', width : '100%','-webkit-overflow-scrolling': 'touch' });
				iframeNode.onload = function(){
					self.busy = false;
				};
			}
		},
		
		reload: function(handle) {
			this.doLoad(handle, false);
		}
		
	});
	
	return clz;
	
});