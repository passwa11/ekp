define(
		[ "dojo/_base/declare", "mui/util", "mui/list/JsonStoreList",
				"mui/tabfilter/TabfilterListSelection", "mui/dialog/Tip"],
		function(declare, util, JsonStoreList, TabfilterListSelection, Tip) {
			return declare(
					"sys.zone.list.FilterSearchList",
					[ JsonStoreList ],
					{
						// 数据请求URL
						dataUrl : "/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=searchPerson&rowsize=16&orderby=fdOrder&searchValue=!{_searchValue}&tagNames=!{_tagNames}",

						searchValue : "",

						tagNames : "",

						//编码后
						_searchValue : "",

						_tagNames : "",

						buildRendering : function() {
							this.url = util.urlResolver(this.dataUrl, this);
							this.inherited(arguments);
						},
						postCreate : function() {
							this.inherited(arguments);
							this.subscribe("/mui/search/submit",
									"_searchSubmit");
							this.subscribe("/mui/search/cancel",
									"_searchCancel");
							this.subscribe("/mui/tabfilter/dialog/submit",
									"_filterSubmit");
							this.subscribe("/mui/tabfilter/selChanged/delete",
									"_selDel");
						},

						_searchSubmit : function(obj, evt) {
							if (evt && evt.keyword) {
								if (evt.keyword != this.searchValue) {
									this.searchValue = evt.keyword;
									this._searchValue = encodeURIComponent(this.searchValue);
									this._searchChange();
								}
							}
						},

						_searchCancel : function(obj, evt) {
							if (!this.searchValue) {
								return;
							}
							this.searchValue = this._searchValue = "";
							this._searchChange();
						},

						_selDel : function(obj, evt) {
							if (obj.isInstanceOf(TabfilterListSelection)) {
								this._filterSubmit(null, evt);
							}
						},

						_filterSubmit : function(obj, evt) {
							if (evt) {
								if (evt.values != this.tagNames) {
									this.tagNames = evt.values;
									this._tagNames = encodeURIComponent(this.tagNames);
									this._searchChange();
								}
							}
						},

						_searchChange : function() {
							var dataUrl = util.formatUrl(this.dataUrl);
							this.url = util.urlResolver(dataUrl, this);
							this.buildLoading();
							this.reload();
						},
						
						onComplete : function(items){
							if(this.loading){
								this.loading.hide();
							}
							this.inherited(arguments);
							if(this.resultNumNode) {
								document.getElementById(this.resultNumNode).innerHTML = this.totalSize;
							}
						},
						
						buildLoading : function() {
							if(!this.loading) {
								this.loading = Tip.processing("处理中...");
							}
							this.loading.show();
						}
					});
		});