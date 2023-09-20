define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic',
         'dojo/dom', 'dojo/dom-construct', 'dojo/dom-style','dojo/dom-class',
         'dojo/request', 'dojo/request/xhr', 'mui/util', './AttendPersonItem', 'mhui/list/SwiperItem'],
	function(declare, array, lang, topic, dom, domCtr, domStyle, domClass, 
			request, xhr, util, AttendPersonItem, SwiperItem) {
		return declare('sys.attend.maxhub.AttendPersonListMixin', null ,{
			
			itemRenderer: AttendPersonItem,
			
			reloadTimer: null,
			reloadDuration: 4000,
			
			pageno: 1,
			rowsize: 6,
			totalno: -1,
			
			cancel: false,
			
			startUpdateAttendPerson: function(appId, cateId) {
				this.appId = appId;
				this.cateId = cateId;
				this.reload();
				this.initReloadTimer();
			},
			
			cancelUpdateAttendPerson: function() {
				this.stopReloadTimer();
				this.cancel = true;
			},
			
			initReloadTimer: function() {
				var ctx = this;
				
				if(this.cancel) {
					return;
				}
				
				ctx.stopReloadTimer();
				
				ctx.reloadTimer = setInterval(function() {
					ctx.reload();
				}, ctx.reloadDuration);
				
			},
			
			reinitReloadTimer: function() {
				this.initReloadTimer();
			},
			
			stopReloadTimer: function() {
				if(this.reloadTimer) {
					clearInterval(this.reloadTimer);
					this.reloadTimer = null;
				}
			},
			
			reload: function() {
				this.pageno = 1;
				this.inherited(arguments);
			},

			resolveUrl: function(pageno, rowsize) {
				var _url = dojoConfig.baseUrl + 'sys/attend/sys_attend_main/sysAttendMain.do?method=list&categoryType=custom&rowsize=!{rowsize}&operType=!{operType}&appId=!{appId}&fdCategoryId=!{cateId}&pageno=!{pageno}';
				var url = util.urlResolver(_url, {
					appId: this.appId,
					cateId: this.cateId,
					operType: this.filter == 'attend' ? 1 : 0,
					rowsize: rowsize,
					pageno: pageno
				});
				
				return url;
			},

			getList: function(cb) {
				
				var ctx = this;
				
				xhr.get(ctx.resolveUrl(ctx.pageno, ctx.rowsize), {
					handleAs: 'json',
				}).then(function(res) {
					
					var data = res.datas;
					var page = res.page;
					
					if(page.totalSize % page.pageSize == 0) {
						ctx.totalno = page.totalSize / page.pageSize;
					} else {
						ctx.totalno = (page.totalSize / page.pageSize) + 1;
					}
					
					if(page.currentPage * page.pageSize < page.totalSize) {

						xhr.get(ctx.resolveUrl(ctx.pageno + 1, ctx.rowsize), {
							handleAs: 'json',
						}).then(function(_res) {
							ctx.pageno = ctx.pageno + 1;
							data = data.concat(_res.datas);
							cb(data || []);
						});
						
					} else {
						cb(data || []);
					}
					
				});
				
			},
			
			convertData: function(data) {
				
				var resData = [], i = 0, l = (data || []).length;
				for(i; i < l; i++) {
					
					var d = data[i];
					
					var t = {};
					var j = 0, _l = (d || []).length;
					for(j; j < _l; j++) {
						t[d[j].col] = d[j].value;
					}
					
					resData.push(t);
					
				}
				
				return resData;
			},
			
			loadMore: function(pageno) {
				var ctx = this;
				
				xhr.get(ctx.resolveUrl(pageno, ctx.rowsize), {
					handleAs: 'json',
				}).then(function(res) {

					var data = ctx.convertData(res.datas || []);
					
					array.forEach(data || [], function(item) {
						
						var swiperItem = new SwiperItem();
						
						if(ctx.itemRenderer) {
							swiperItem.addChild(new ctx.itemRenderer(item));
							ctx.addChild(swiperItem.domNode);
						}
						
					});
					
				});
				
			},
			
			generateOptions: function(data) {
				
				var ctx = this;
				
				return {
					width: screen.width * 0.316666667,
					slidesPerView: ctx.rowsize || 6,
					slidesPerGroup: ctx.rowsize || 6,
					on:{
						touchStart: function(){
							ctx.stopReloadTimer();
						},
						touchEnd: function() {
							ctx.initReloadTimer();
						},
						slideChange: function() {
							ctx.reinitReloadTimer();
							// 加载下一页数据
							var pageno = ctx.swiper.activeIndex + 2;
							if(pageno > ctx.pageno && pageno <= ctx.totalno) {
								ctx.pageno = pageno;
								ctx.loadMore(pageno);
							}
						}
					}
				};
			}
			
			
		});
	}
);