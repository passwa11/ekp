/**
 * 简版公文头部模板，用于待我审的
 */
define(['mui/createUtils', 'mui/i18n/i18n!km-smissive:mobile'], function(createUtils, msg){
	
	var h = createUtils.createTemplate;
	
	// 排序（拟稿日期）
	var createTimeFilter = h('div', {
		dojoType: 'mui/sort/SortItem',
		dojoProps: {
			name: 'docCreateTime',
			subject: msg['mobile.kmSmissive.draftDate'],
			value: 'down'
		}
	});
	
	// 属性筛选器
	var propertyFilter = h('div', {
		className: 'muiHeaderItemRight',
		dojoType: 'mui/property/FilterItem',
		dojoMixins: 'km/smissive/mobile/resource/js/header/ApprovalSmissivePropertyMixin'
	});
	
	// 分类筛选器
	var categoryFilter = h('div', {
		className: 'muiHeaderItemRight',
		dojoType: 'mui/catefilter/FilterItem',
		dojoMixins: 'mui/simplecategory/SimpleCategoryMixin',
		dojoProps: {
			modelName: 'com.landray.kmss.km.smissive.model.KmSmissiveTemplate',
			catekey: 'categoryId',
			prefix:''
		}
	});

	return [createTimeFilter, propertyFilter, categoryFilter].join('');
	
});