define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"./item/NotifyItemMixin", "dojo/_base/array", "dijit/registry","dojo/dom-style", "mui/util" ,"mui/i18n/i18n!sys-notify:mportal.sysNotify","mui/device/adapter" ], function(declare,
		_TemplateItemListMixin, NotifyItemMixin, array, registry, domStyle, util ,Msg,adapter) {

	return declare("sys.notify.mportal.NotifyListMixin", [ _TemplateItemListMixin ], {
		
		itemRenderer : NotifyItemMixin,
		
		type : '',
		
		startup : function(){
			this.inherited(arguments);
			// 监听window对象的 pageshow 事件（为了浏览器回退的时候能够强制刷新页面）
			this.connect(window,'pageshow','_pageshow');

			//添加钉钉resume监听，页面重现可见时刷新，解决待办不刷新的问题
			adapter.resume(function (){
				window.location.reload();
			});
		},
		_pageshow : function(evt){
			/* 注：经验证，只有第一次浏览器回退的时候persisted才会为true，所以必须使用页面刷新的方式来显示新的列表数据以及角标
			 * 不可以手动去发事件去更新列表和角标，因为只有强制刷新整个页面后，才会使得每次浏览器回退时persisted都为true
			*/ 
			if(evt.persisted){	
				if(registry.byNode(this.domNode) && this._isVisible(true)){
					window.location.reload();
				}
			}
		},
		
		 _isVisible: function(checkAncestors) {
				var visible = function(node){
					return domStyle.get(node, "display") !== "none";
				};
				if(checkAncestors){
					for(var n = this.domNode; n&&n.tagName !== "BODY"; n = n.parentNode){
						if(!visible(n)){ return false; }
					}
					return true;
				}else{
					return visible(this.domNode);
				}
		},
		
		buildRendering: function(){
			this.inherited(arguments);
			if(this.type){
				if(this.type == 1 || this.type == 2){
					this.url = util.setUrlParameter(this.url,'oprType','doing');
					if(this.type == 1){
						this.url = util.setUrlParameter(this.url,'fdType','13');
						//没有待审批申请
						this.nodataText = Msg['mportal.sysNotify.doing13.nodata'];
					}else{
						this.url = util.setUrlParameter(this.url,'fdType','2');
						//没有待阅读申请
						this.nodataText = Msg['mportal.sysNotify.doing2.nodata'];
					}
				}else if(this.type == 3){
					this.url = util.setUrlParameter(this.url,'oprType','done');
					//没有已办数据
					this.nodataText = Msg['mportal.sysNotify.done.nodata'];
				}
			}
		}
		
		
	});
});