define([
    "dojo/_base/declare",
    "dojo/topic",
	"dojox/mobile/EdgeToEdgeStoreList",
	'mui/store/JsonRest',
	'mui/store/JsonpRest',
	'mui/util',
	'mui/calendar/CalendarUtil',
	'./_ListNoDataMixin'
	], function(declare, topic,EdgeToEdgeStoreList,JsonStore, JsonpStore,util,cutil,_ListNoDataMixin) {
	
	return declare("sys.mportal.JsonStoreCardList", 
			[EdgeToEdgeStoreList, _ListNoDataMixin], {
		
		nodataText : '暂无日程',
		
		// 支持URL
		url: '',
		
		busy: false,
		
		//时间范围,today:今天、less3:最近三天、less7:最近一周、more3:未来三天、more7:未来一周
		scopeType : 'today',
		
		dataType : "json",
		
		//是否马上请求数据
		lazy: false,
		
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
	
		doLoad : function(evt){
			if (this.busy) {
				return;
			}
			this.busy = true;
			this._setDate();
			var promise = null;
			if (this.store) {
				this.store.target = this.url;
				promise = this.setQuery({}, {});
			} else {
				if(this.dataType == 'jsonp') {
					promise = this.setStore(new JsonpStore(
							{
							 idProperty: 'id', 
							 target: util.urlResolver(this.url, this)
							 }), {}, {});
				} else {
					promise = this.setStore(new JsonStore( {
										idProperty : 'id',
										target : util.urlResolver(this.url,
												this)
									}), {}, {});
				}
			}
			var self = this;
			return promise;
		},
		
		onComplete: function(items) {
			this.busy = false;
			this.totalSize = items.length;
			this.generateList(items);
			topic.publish('/mui/mportal/card/loaded', this, items);
		},
		
		//less3:最近三天、less7:最近一周、more3:未来三天、more7:未来一周
		_setDate : function(){
			var start = this._clearTime(new Date()),
				end = new Date(start.getTime() + 24 * 3600 * 1000);
			if(this.scopeType && this.scopeType != 'today'){
				var now = this._clearTime(new Date()).getTime();
				switch(this.scopeType){
					case 'less3':
						start = new Date(now - 24 *  3600 * 1000 * 2 );
						break;
					case 'less7':
						start = new Date(now - 24 *  3600 * 1000 * 6 );
						break;
					case 'more3':
						end =  new Date(now + 24 *  3600 * 1000 * 3 );
						break;
					case 'more7':
						end = new Date(now + 24 *  3600 * 1000 * 7);
						break;
					default :
						break;
				}
			}
			this.start = cutil.formatDate(this._clearTime(start));
			this.end = cutil.formatDate(this._clearTime(end));
		},
		
		_clearTime : function(date){
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			return date;
		},
		
		_fullTime : function(date){
			date.setHours(23);
			date.setMinutes(59);
			date.setSeconds(59);
			return date;
		}
		
	});
});