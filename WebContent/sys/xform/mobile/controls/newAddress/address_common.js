define([
	"mui/util",
	"mui/createUtils",
	"mui/i18n/i18n!sys-mobile"
	], function(util, createUtils ,Msg) {
	
	var h = createUtils.createTemplate;
	
	// 面包屑
	function tmplPath(params){
		var className = [];
		if(params.className){
			className = className.concat(params.className);
		}
		var props =  {
			key: '{categroy.key}',
			height: '3rem',
			channel: params.channel,
			visible: params.visible ? true: false
		};
		if(params.titleNode){
			props.titleNode = params.titleNode;
		}
		if(params.detailUrl){
			props.detailUrl = params.detailUrl;
		}
		return h('div',{
			dojoType: 'mui/address/AddressCategoryPath',
			dojoMixins: params.mixins || [],
			dojoProps: props
		});
	}
	
	// 列表模板
	function tmplList(params){
		var mixins = ['mui/address/AddressItemListMixin'];
		if(params.mixins){
			mixins = mixins.concat(params.mixins);
		}
		mixins = mixins.join(',');
		var html = 
			'<ul data-dojo-type="mui/address/AddressList" ' + 
				'data-dojo-mixins="'+ mixins +'" ' + 
				"data-dojo-props=\"dataUrl:'{categroy.dataUrl}',deptLimit:'{categroy.deptLimit}',beforeSelectCateHistoryId: '{categroy.beforeSelectCateHistoryId}',isMul:{categroy.isMul},key:'{categroy.key}'," + "channel:'" + params.channel +"',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'\">" +
			'</ul>';
		return html;
	}
	
	function tmplLoad(params) {
		var mul = params.mul;
		
		var search = h('div', {
			className: 'muiCateSearchArea muiAddressSearchArea'
		}, h('div', {
			id: '_address_search_{categroy.key}',
			dojoType: 'mui/address/AddressSearchBar',
			dojoProps: {
				key: '{categroy.key}',
				searchUrl: '{categroy.searchUrl}',
				orgType: '$${categroy.type}$$',
				exceptValue: '{categroy.exceptValue}',
				height: '4rem'
			}
		}));
		
		// 搜索框
		var html = search;
		
		// 组织架构视图
		var allScrollView = h('div', {
			dojoType: 'dojox/mobile/ScrollableView',
			dojoMixins: ['mui/address/AddressViewScrollResizeMixin', 'mui/category/AppBarsMixin'],
			dojoProps: {
				key:'{categroy.key}',
				channel: 'all',
				scrollBar: false,
				threshold: 100
			}
		}, [
			tmplList({ key:'{categroy.key}', channel: 'all' })
		]);
		
		var allView = h('div', {
			id: 'allCateView_{categroy.key}',
			dojoType: 'dojox/mobile/View'
		}, [ 
			tmplPath({ key:'{categroy.key}', channel: 'all', height: '3rem', titleNode : Msg['mui.mobile.address.allCate'] ,visible: false, mixins: 'mui/address/AddressOrgCategoryPathMixin'  }),
			allScrollView
		]);
		
		//主面板
		html += h('div', {
			id: 'defaultView_{categroy.key}',
			dojoType: 'dojox/mobile/View'
		}, [ allView ]);
		
		// 搜索面板
		html += h('div', {
			id: 'searchView_{categroy.key}',
			className: 'muiAddressView',
			dojoType: 'dojox/mobile/ScrollableView',
			dojoMixins: 'mui/address/AddressViewScrollResizeMixin',
			dojoProps: {
				key: '{categroy.key}',
				channel: 'search',
				scrollBar: false,
				threshold: 100
			}
		}, [
			'<ul data-dojo-type="mui/address/AddressList" ' +
				'data-dojo-mixins="mui/address/AddressItemListMixin,mui/address/AddressSearchListMixin" ' +
				"data-dojo-props=\"searchUrl:'{categroy.searchUrl}',dataUrl:'{categroy.dataUrl}',history:true,deptLimit:'{categroy.deptLimit}',beforeSelectCateHistoryId: '{categroy.beforeSelectCateHistoryId}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'search',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},orgType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'\">" +
			'</ul>' 
		]);
		
		// 多选,已选区域
		if(mul){
			html += h('div', {
				dojoType: 'mui/address/AddressSelection',
				dojoProps: {
					key: '{categroy.key}',
					beforeSelectCateHistoryId: '{categroy.beforeSelectCateHistoryId}',
					curIds: '{categroy.curIds}',
					curNames: '{categroy.curNames}',
					fixed: 'bottom'
				}
			});
		}
		
		return html;
	}
	
	 return {html: tmplLoad}
});