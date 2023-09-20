/**
 * 搜索列表Mixin
 */
define( [
	"dojo/_base/declare",
	"dojo/_base/array",
	"dojo/_base/lang",
	"dojo/json",
	"dojo/dom-construct",
	"dojox/mobile/viewRegistry",
	"mui/util",
	"mui/i18n/i18n!sys-mobile",
	"./AddressHistoryItemMixin"
	], function(declare, array, lang, JSON, domConstruct, viewRegistry, util, Msg, AddressHistoryItemMixin) {
	
	var LOCALSTORAGE_KEY = '__mui__address__history__';
	
	function unique(arr) {
	  return array.filter(arr, function(item, index, arr) {
	    return arr.indexOf(item, 0) === index;
	  });
	}
	
	function remove(arr, delItem){
		return array.filter(arr, function(item){
			return item !== delItem;
		});
	}
	
	return declare("mui.address.AddressSearchListMixin", null, {
		
		// 显示、记录搜索历史
		history: false,
		
		lazy: true,
		
		postCreate: function(){
			this.inherited(arguments);
			this.subscribe("/mui/address/search/init","_handleRenderHistory");
			this.subscribe("/mui/address/search/history/add","_handleAddHistory");
			this.subscribe("/mui/address/search/history/remove","_handleRemoveHistory");
			this.subscribe("/mui/address/search/submit","_handleSearch");
			this.subscribe("/mui/address/search/cancel","_handleCancel");
		},
		
		generateList: function(list){
			if(this.historyHeader){
				domConstruct.destroy(this.historyHeader);
				this.historyHeader = null;
			}
			this.inherited(arguments);
		},
		
		_handleRenderHistory: function(){
			if(!this.history){
				return;
			}
			if(this.tmpLoading){
				domConstruct.destroy(this.tmpLoading);
				this.tmpLoading = null;
			}
			if(this.searchCountNode){
				domConstruct.destroy(this.searchCountNode);
				this.searchCountNode = null;
			}
			if(this.historyHeader){
				domConstruct.destroy(this.historyHeader);
				this.historyHeader = null;
			}
			array.forEach(this.getChildren(), function(child){
				child.destroyRecursive();
			});
			
			var historyDatas = this.getHistoryData();
			if(historyDatas && historyDatas.length > 0){
				this.historyHeader = this._renderHistoryHeader();
				domConstruct.place(this.historyHeader, this.domNode, 'last');
				array.forEach(this.getHistoryData(), function(keyword, index){
					this.addChild(this.createHistoryItem({
						keyword : keyword
					}));
				}, this);
			}
		},
		
		_renderHistoryHeader: function(){
			var header = domConstruct.create('div',{
				className: 'muiAddressHistoryHeader'
			});
			domConstruct.create('span',{
				className: 'muiAddressHistoryHeaderTitle',
				innerHTML: Msg['mui.mobile.address.search.history']
			},header);
			var rightArea = domConstruct.create('span',{
				className: 'muiAddressHistoryHeaderRight'
			}, header);
			domConstruct.create('i',{
				className: 'mui mui-drafter_refuse_abandon',
			},rightArea);
			domConstruct.create('span',{
				className: 'muiAddressHistoryHeaderClear',
				innerHTML: Msg['mui.mobile.address.search.history.clear']
			},rightArea);
			this.connect(rightArea, 'click', '_handleClearHistory');
			return header;
		},
		
		// 添加历史记录
		_handleAddHistory: function(keyword){
			var historyData = this.getHistoryData();
			historyData.unshift(keyword);
			// 去重
			historyData = unique(historyData)
			localStorage.setItem(LOCALSTORAGE_KEY,JSON.stringify(historyData));
		},
		
		// 删除历史记录
		_handleRemoveHistory: function(srcObj, keyword){
			var historyData = this.getHistoryData();
			historyData = remove(historyData, keyword);
			localStorage.setItem(LOCALSTORAGE_KEY,JSON.stringify(historyData));
			// 重新渲染
			this._handleRenderHistory();
		},
		
		// 清空历史记录
		_handleClearHistory: function(){
			localStorage.setItem(LOCALSTORAGE_KEY,JSON.stringify([]));
			// 重新渲染
			this._handleRenderHistory();
		},
		
		// 查询回调处理
		_handleSearch: function(srcObj, evt){
			if(!evt || !evt.keyword){
				return;
			}
			if(this.isVisibleView()){
				this.url = util.formatUrl(util.urlResolver(this.searchUrl, lang.mixin(evt, this) ));
				this.showMore = false;
				this.buildLoading();
				this.reload();
				// 存储历史记录
				if(this.history && localStorage){
					this._handleAddHistory(evt.keyword);
				}
			}
		},
		
		_handleCancel: function(){
			if(this.isVisibleView()){
				this.buildLoading();
				this.url = util.urlResolver(util.formatUrl(this.dataUrl), this);
				this.showMore=true;
				this.reload();
			}
		},
		
		createHistoryItem: function(item){
			return new AddressHistoryItemMixin(item);
		},
		
		// 当前组件是否处于可视区域，可视区域组件才响应事件
		isVisibleView: function(){
			var thisView = viewRegistry.getEnclosingView(this.domNode);
			return thisView.isVisible();
		},
		
		getHistoryData: function(){
			if(!localStorage){
				return []
			}
			var data = localStorage.getItem(LOCALSTORAGE_KEY);
			return data ? JSON.parse(data) : [];
		}
	
	});
});