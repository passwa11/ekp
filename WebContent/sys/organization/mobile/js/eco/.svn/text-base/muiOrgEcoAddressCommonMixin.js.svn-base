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
			parentId: '{categroy.fdId}',
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
		props.isEco = true;
		props.isEcoPath = true;
		return h('div',{
			dojoType: 'mui/address/AddressCategoryPath',
			dojoMixins: params.mixins || [],
			dojoProps: props
		});
	}
	
	// 列表模板
	function tmplList(params){
		var mixins = ['sys/organization/mobile/js/eco/muiOrgEcoAddressItemListMixin'];
		if(params.mixins){
			mixins = mixins.concat(params.mixins);
		}
		mixins = mixins.join(',');
		var html = 
			'<ul class="muiOrgEcoList" data-dojo-type="mui/address/AddressList" ' + 
				'data-dojo-mixins="'+ mixins +'" ' + 
				"data-dojo-props=\"fdExternal:true,parentId:'{categroy.fdId}',deptLimit:'{categroy.deptLimit}',beforeSelectCateHistoryId: '{categroy.beforeSelectCateHistoryId}',isMul:{categroy.isMul},key:'{categroy.key}'," + "channel:'" + params.channel +"',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'\">" +
			'</ul>';
		return html;
	}
	
	function tmplLoad(params) {
		var mul = params.mul;
		
		var search = h('div', {
			className: 'muiCateSearchArea muiAddressSearchArea'
		}, h('div', {
			id: '_address_search_{categroy.key}',
			dojoType: 'sys/organization/mobile/js/eco/muiOrgEcoAddressSearchBar',
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
		
		// 顶部导航
		var header = '';/*h('div',{
			dojoType: 'mui/header/Header',
			dojoProps: {
				height: '4rem'
			}
		}, h('div',{
			id: 'categoryNavBar',
			dojoType: 'mui/nav/StaticNavBar',
			dojoMixins: 'mui/address/AddressNavBarMixin',
			dojoProps: { key: '{categroy.key}' }
		}));*/
		
		// 我的部门入口
		var deptMemberEntry = '';/*h('div',{
			dojoType: 'mui/address/AddressDeptMember',
			dojoProps: { key: '{categroy.key}', selType: '$${categroy.type}$$' }
		});*/
		
		// 我的下属入口
		var subordinateEntry = '';/*h('div',{
			dojoType: 'mui/address/AddressSubordinate',
			dojoProps: { key: '{categroy.key}', selType: '$${categroy.type}$$' }
		});*/
		
		
		// 常用视图
		var usualScrollView = '';/* h('div',{
			dojoType: 'dojox/mobile/ScrollableView',
			dojoMixins: ['mui/address/AddressViewScrollResizeMixin', 'mui/category/AppBarsMixin'],
			dojoProps: {
				key: '{categroy.key}',
				scrollBar: false,
				threshold: 100,
				channel: 'usual'
			}
		}, [
			tmplPath({ channel: 'usual', visible: false }),
			tmplList({ channel: 'usual', mixins : ['mui/address/AddressRecentItemListMixin'] })
		]);*/
		
		var usualView = '';/*h('div', {
			id: 'usualCateView_{categroy.key}',
			dojoType: 'dojox/mobile/View'
		},[ 
			deptMemberEntry, 
			subordinateEntry, 
			h('div',{
				className: 'muiAddressItemTitle muiUsualRecentTitle'
			}, Msg['mui.mobile.address.recently']),
			usualScrollView
		]);*/
		
		// 公司卡片
		var card = h('div', {
			dojoType: 'sys/organization/mobile/js/eco/muiOrgEcoCard'
		});
		
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
		}, [card,
			tmplList({ key:'{categroy.key}', channel: 'all' })
		]);
		
		var allView = h('div', {
			id: 'allCateView_{categroy.key}',
			dojoType: 'dojox/mobile/View'
		}, [
			tmplPath({ key:'{categroy.key}', channel: 'all', height: '3rem', titleNode : "生态组织" ,visible: true, mixins: 'mui/address/AddressOrgCategoryPathMixin'  }),
			allScrollView
		]);
		
		// 群组入口
		var groupEntry = '';/*h('div', {
			id: 'groupView_{categroy.key}',
			dojoType: 'dojox/mobile/View',
			dojoMixins: ['mui/address/AddressGroupViewMixin'],
			dojoProps: {
				key: '{categroy.key}'
			}
		});*/
		// 公共群组视图
		var commonGroup = '';/*h('div',{
			id: 'commonGroupView_{categroy.key}',
			dojoType: 'dojox/mobile/View'
		}, [
			h('div',{
				dojoType: 'mui/address/AddressCommonGroupCategoryPath',
				dojoProps: {
					key: '{categroy.key}',
					channel: 'common_group',
					height: '3rem'
				}
			}),
			h('div',{
				dojoType: 'dojox/mobile/ScrollableView',
				dojoMixins: ['mui/address/AddressViewScrollResizeMixin', 'mui/category/AppBarsMixin'],
				dojoProps: {
					key:'{categroy.key}',
					channel: 'common_group',
					scrollBar: false,
					threshold: 100
				}
			},[
				'<ul data-dojo-type="mui/address/AddressList" ' +
					'data-dojo-mixins="mui/address/AddressCommonGroupListMixin" ' + 
					"data-dojo-props=\"deptLimit:'{categroy.deptLimit}',beforeSelectCateHistoryId: '{categroy.beforeSelectCateHistoryId}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'common_group',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}',maxPageSize:{categroy.maxPageSize}\">" + 
				'</ul>'
			])
		]);*/
		// 我的群组
		var myGroup = '';/*h('div', {
			id: 'myGroupView_{categroy.key}',
			dojoType: 'dojox/mobile/View'
		}, [
			h('div', {
				dojoType: 'mui/address/AddressMyGroupCategoryPath',
				dojoProps: {
					key: '{categroy.key}',
					channel: 'my_group',
					height: '3rem'
				}
			}),
			h('div',{
				dojoType: 'dojox/mobile/ScrollableView',
				dojoMixins: ['mui/address/AddressViewScrollResizeMixin', 'mui/category/AppBarsMixin'],
				dojoProps: {
					key:'{categroy.key}',
					channel: 'my_group',
					scrollBar: false,
					threshold: 100
				}
			},[
				'<ul data-dojo-type="mui/address/AddressList" ' +
					'data-dojo-mixins="mui/address/AddressMyGroupListMixin" ' + 
					"data-dojo-props=\"deptLimit:'{categroy.deptLimit}',beforeSelectCateHistoryId: '{categroy.beforeSelectCateHistoryId}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'my_group',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}',maxPageSize:{categroy.maxPageSize}\">" + 
				'</ul>'
			])
		]);*/
		
		var groupView = '';/*h('div', {
			dojoType: 'dojox/mobile/View',
			dojoProps: {
				key: '{categroy.key}',
				channel: 'group'
			}
		}, [ groupEntry, commonGroup, myGroup ]);*/
		
		//主面板
		html += h('div', {
			id: 'defaultView_{categroy.key}',
			dojoType: 'dojox/mobile/View'
		}, [ header, usualView, allView, groupView ]);
		
		// 我的部门面板
		/*html += h('div', {
			id: 'deptMemberView_{categroy.key}',
			dojoType: 'dojox/mobile/ScrollableView',
			dojoMixins: 'mui/address/AddressViewScrollResizeMixin',
			dojoProps: {
				key: '{categroy.key}',
				channel: 'deptMember',
				scrollBar: false,
				threshold: 100
			}
		}, [
			tmplPath({ channel: 'deptMember', visible: false }),
			tmplList({ channel: 'deptMember', mixins: ['mui/address/AddressDeptMemberListMixin','mui/address/AddressSearchListMixin'] })
		]);*/
		// 我的下属面板
		/*html += h('div', {
			id: 'subordinateView_{categroy.key}',
			className : 'muiSubordinateView',
			dojoType: 'dojox/mobile/ScrollableView',
			dojoMixins: 'mui/address/AddressViewScrollResizeMixin',
			dojoProps: {
				key: '{categroy.key}',
				channel: 'subordinate',
				scrollBar: false,
				threshold: 100
			}
		}, [
			tmplPath({
				channel: 'subordinate', 
				visible: true, 
				className: ['muiAddressSubordinatePath'], 
				titleNode: Msg['mui.mobile.address.subordinate'],
				mixins: ['mui/address/AddressSubordinatePathMixin']
			}),
			tmplList({ channel: 'subordinate', mixins: ['mui/address/AddressSubordinateListMixin','mui/address/AddressSearchListMixin'] })
		]);*/
		
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
				'data-dojo-mixins="sys/organization/mobile/js/eco/muiOrgEcoAddressItemListMixin,mui/address/AddressSearchListMixin" ' +
				"data-dojo-props=\"isSearch:true,fdExternal:true,parentId:'{categroy.fdId}',history:true,deptLimit:'{categroy.deptLimit}',beforeSelectCateHistoryId: '{categroy.beforeSelectCateHistoryId}',isMul:{categroy.isMul},key:'{categroy.key}',channel:'all',curIds:'{categroy.curIds}',curNames:'{categroy.curNames}',selType:{categroy.type},orgType:{categroy.type},exceptValue:'{categroy.exceptValue}',scope:'{categroy.scope}'\">" +
			'</ul>' 
		]);
		
		// 底部按钮
		html += '<div class="muiOrgEcoAddBtnGroup" data-dojo-type="sys/organization/mobile/js/eco/muiOrgEcoBtn" data-dojo-props="fdOrgType:\'{categroy.fdOrgType}\',parentId:\'{categroy.fdId}\'"></div>';
		
		// 多选,已选区域
		/*if(mul){
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
		}*/
		
		return html;
	}
	
	 return {html: tmplLoad}
});