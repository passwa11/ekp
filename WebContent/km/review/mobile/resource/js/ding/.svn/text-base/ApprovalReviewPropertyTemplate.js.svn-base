/**
 * 流程头部模板，用于待我审的
 */
define(['mui/createUtils',
	"mui/util",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docCreatetime"], function(createUtils,util,msg){
	
	var h = createUtils.createTemplate;
	
	// 排序（创建时间）
	var createTimeFilter = h('div', {
		dojoType: 'mui/sort/SortItem',
		dojoProps: {
			name: 'docCreateTime',
			subject: msg['mui.kmReviewMain.docCreatetime'],
			value: 'down'
		}
	});
	
	// 属性筛选器
	var propertyFilter = h('div', {
		className: 'muiHeaderItemRight',
		dojoType: 'mui/property/FilterItem',
		dojoMixins: 'km/review/mobile/resource/js/header/ApprovalReviewPropertyMixin,km/review/mobile/resource/js/ding/DingReviewPropertyMixin'
	});
	
	return [createTimeFilter, propertyFilter].join('');
	
});