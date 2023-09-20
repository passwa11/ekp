/**
 * 头部模板
 */
define(['mui/createUtils', 'mui/i18n/i18n!km-archives:kmArchivesMain'], function(createUtils, msg){
	
	var c = createUtils.createTemplate;
	
	// 排序（归档日期）
	var Sort1 = c('div', {
		dojoType: 'mui/sort/SortItem',
		dojoProps: {
			name: 'fdFileDate',
			subject: msg['kmArchivesMain.fdFileDate'],
			value: 'down'
		}
	});
	//所属卷库
	var Sort2 = c('div', {
		dojoType: 'mui/sort/SortItem',
		dojoProps: {
			name: 'fdLibrary',
			subject: msg['kmArchivesMain.fdLibrary'],
			value: ''
		}
	});
	
	// 属性筛选器
	var propertyFilter = c('div', {
		className: 'muiHeaderItemRight',
		dojoType: 'mui/property/FilterItem',
		dojoMixins: 'km/archives/mobile/main/kStatus/js/header/AllDocPropertyMixin'
	});
	
	return [Sort1, Sort2, propertyFilter].join('');
	
});