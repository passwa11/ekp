define([ 'dojo/_base/declare', 'dojo/_base/array', 'dojo/_base/lang' , 'dojo/topic', 'dojo/request', 
         './item/MeetingMapItem', 'mui/util', 'dojo/date', 'dojo/dom-class', 'mhui/list/feature/LoadMore'],
	function(declare, array, lang, topic, request, MeetingMapItem, util, date, domClass, LoadMore) {
		return declare('km.imeeting.maxhub.MeetingMapItemListMixin', [LoadMore] ,{

			className: 'mhuiMeetingMapList',
			tagName: 'div',
			
			itemRenderer: MeetingMapItem,
			
			url: 'km/imeeting/km_imeeting_main/kmImeetingMain.do?' + 
				'method=listChildren&categoryId={categoryId}&' + 
				'q.role={role}&q.fdPlace={fdPlace}&q.fdTemplate={fdTemplate}&' + 
				'q.fdName={fdName}&pageno={pageno}&rowsize={rowsize}&orderby=fdHoldDate&' + 
				'q.meetingStatus={meetingStatus}&ordertype=down&s_ajax=true&{fdHoldDate}&' + 
				'q.meetingCondition={meetingCondition}',
			query: {
				pageno: 1,
				rowsize: 15,
				categoryId: '',
				fdTemplate: '',
				fdPlace: '',
				fdName: '',
				role: '',
				fdPlace: '',
				meetingStatus: '',
				fdHoldDate: '',
				meetingCondition: '0'
			},
			
			_hasMore: true,
			
			reload: function(query) {
				
				var ctx = this;
				
				ctx.domNode.scrollTop = 0;
				ctx._hasMore = true;
				ctx.query = lang.mixin({}, ctx.query, query, {
					pageno: 1
				});
				
				// 显示取消状态的会议
				if(ctx.query.meetingStatus == '41') {
					ctx.query.meetingCondition = '41';
				} else {
					ctx.query.meetingCondition = '30&q.meetingCondition=41';
				}
				
				ctx.getList(function(data) {
					
					if(data.length == 0) {
						ctx.renderList([{isEmpty: true}]);
					} else {
						ctx.renderList([{isFirst: true}].concat(ctx.convertData(data)));
					}
					
				});
			},
			
			onLoadMore: function(done) {
				
				var ctx = this;
				
				if(!ctx._hasMore) {
					done();
					return;
				}
				
				domClass.add(ctx.domNode, 'loading');
				
				ctx.query = lang.mixin({}, ctx.query, {
					pageno: ctx.query.pageno + 1
				});
				
				ctx.getList(function(data, res){
					if(data.length > 0 && ctx.query.pageno == res.page.currentPage) {
						ctx.renderList(ctx.convertData(data), true);
					} else {
						ctx._hasMore = false;
					}
					domClass.remove(ctx.domNode, 'loading');
					done();
				});
			},
			
			getList: function(cb) {
				
				request(dojoConfig.baseUrl + lang.replace(this.url, this.query), {
					method: 'get',
					handleAs: 'json'
				}).then(function(res){
					cb(res.datas, res);
				}, function(err) {
					console.error(err);
				});
				
			},
			
			convertData: function(data) {
				
				var res = [];
				
				data = data || [];
				
				var i, l = data.length;
				for(i = 0; i < l; i++) {
					
					var row = data[i] || [];
					var j, _l = row.length;
					var t = {};
					for(j = 0; j < _l; j++) {
						t[row[j].col] = row[j].value;
					}
					
					res.push(t);
					
				}
				
				return res;
				
			}
			
		});
	}
);