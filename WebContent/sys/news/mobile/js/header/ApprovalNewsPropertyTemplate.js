/**
 * 新闻头部模板，用于“待我审的”页签
 */
define(['mui/createUtils', 'mui/i18n/i18n!sys-news:mobile'], function(createUtils, msg){
	
	var h = createUtils.createTemplate;
	
	// 排序（浏览次数）
	var readCountFilter = h('div', {
		dojoType: 'mui/sort/SortItem',
		dojoProps: {
			name: 'docReadCount',
			subject: msg['mobile.sysNews.views'],
			value: ''
		}
	});
	
	// 属性筛选器
	var propertyFilter = h('div', {
		className: 'muiHeaderItemRight',
		dojoType: 'mui/property/FilterItem',
		dojoMixins: 'sys/news/mobile/js/header/ApprovalNewsPropertyMixin'
	});
	
	// 分类筛选器
	var categoryFilter = h('div', {
		className: 'muiHeaderItemRight',
		dojoType: 'mui/catefilter/FilterItem',
		dojoMixins: 'mui/simplecategory/SimpleCategoryMixin',
		dojoProps: {
			modelName: 'com.landray.kmss.sys.news.model.SysNewsTemplate',
			catekey: 'categoryId',
			prefix:''
		}
	});
	
	return [readCountFilter, propertyFilter, categoryFilter].join('');
	
});