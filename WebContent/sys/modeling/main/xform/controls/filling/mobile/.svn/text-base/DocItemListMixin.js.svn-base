define([
    "dojo/_base/declare",
    'dojox/mobile/viewRegistry',
	"mui/list/_TemplateItemListMixin",
	"sys/modeling/main/xform/controls/filling/mobile/DocItemMixin",
	"dojo/_base/lang",
	'dojo/when',
	'dojo/topic',
	'mui/util',
	'mui/store/JsonRest','mui/store/JsonpRest',
	], function(declare, viewRegistry, _TemplateItemListMixin, DocItemMixin,lang,when, topic,
				util,JsonStore, JsonpStore) {
	
	return declare("sys.modeling.main.xform.controls.filling.mobile.DocItemListMixin", [_TemplateItemListMixin], {
		itemTemplateString : null,
		itemRenderer: DocItemMixin,
		//单选|多选
		isMul: false,
		rowsize:15,
		//查询条件
		search: {},
		//排序
		sort: {},
		//入参
		ins: {},
		//业务填充控件列表key
		fsKey:null,
		curIds:[],
		startup : function(){
			console.log("start")
			this.inherited(arguments);
			/* 监听window对象的 pageshow 事件
			     （注：因iPhone IOS和部分Android机，在使用history.back()返回列表页面时，页面不会自动刷新，导致新建返回列表页后无法实时看到最新数据，为了浏览器回退的时候能够强制刷新页面，所以监听pageshow事件）*/
			this.connect(window,'pageshow','_pageshow');
			this.parent = this.getParent();
			if(this.parent){
				this.isMul = this.parent.rel.isMul != "0";
				this.relationId = this.parent.rel.relationId;
			}
			// 监听筛选器变化
			this.subscribe('/mui/property/filter', 'onFilter');
		},
		_pageshow : function(evt){
			/* 注：经验证，只有第一次浏览器回退的时候persisted才会为true，所以必须使用页面刷新的方式来显示新的列表数据
			 * 不可以手动去发事件去更新列表，因为只有强制刷新整个页面后，才会使得每次浏览器回退时persisted都为true
			*/
			if(evt.persisted){
				var viewObj = viewRegistry.getEnclosingView(this.domNode);
				if(viewObj && viewObj.isVisible()){
					window.location.reload();
				}
			}
		},
		createListItem: function(/*Object*/item){
			item["fsKey"] = this.fsKey;
			item["isMul"] = this.isMul;
			return new this.itemRenderer(this._createItemProperties(item));
		},
		// 构建
		buildQuery : function() {
			return lang.mixin({} , {
				pageno : this.pageno,
				rowsize : this.rowsize,
				relationId: this.relationId,
				ins:this.ins,
				search:JSON.stringify(this.search),
				sort:JSON.stringify(this.sort)
			});
		},
		doLoad: function(handle, append) {
			if (this.busy) {
				return;
			}
			if (handle)
				handle.work(this);

			this.busy = true;
			this.append = !!append;

			if (this.append && this._loadOver) {
				if (handle)
					handle.done(this);
				this.busy = false;
				return;
			}

			var promise = null;
			var queryOptions = {
			    method:"POST",
			}
			if (this.store) {
				this.store.target = this.url;
				promise = this.setQuery(this.buildQuery(), queryOptions);
			} else {
				if(this.dataType == 'jsonp') {
					promise = this.setStore(new JsonpStore(
						{idProperty: 'fdId', target: util.urlResolver(this.url, this)}),
						this.buildQuery(), {});
				} else {
					promise = this.setStore(new JsonStore(
						{idProperty: 'fdId', target: util.urlResolver(this.url, this)}),
						this.buildQuery(), {});
				}
			}
			var self = this;
			if (handle) {
				when(promise,
					function() {handle.done(self);},
					function() {handle.error(self);});
			}
			return promise;
		},

		onFilter:function (obj, values){
			if(!this.isSameChannel(obj.key) || !values){
				return;
			}
			// 滚动置顶
			topic.publish("/mui/list/toTop", this);
			this.sort={};
			if(values.hasOwnProperty("orderby") && values.hasOwnProperty("ordertype")){
				for(var key in values){
					if("orderby" === key){
						this.sort["orderBy"] = values[key].value;
					}
					if("ordertype" === key && values[key].value){
						this.sort["orderType"] = values[key].value == "up" ? "asc" : "desc";
					}
				}
			}else{
				this.search = {};
				for(var key in values){
					this.search[key] = values[key];
				}
			}

			this.reload();
		}
	});
});