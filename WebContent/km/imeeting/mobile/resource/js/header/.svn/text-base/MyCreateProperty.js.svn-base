/**
 * 会议审批 - 会议安排审批
 */
define(['mui/createUtils',"mui/i18n/i18n!km-imeeting:mobile"], function(createUtils,msg){
	
	var h = createUtils.createTemplate;
	
	// 排序（创建时间）
	var TagFilter = h('div', {
		dojoType: 'mui/property/TagFilterItem',
		dojoProps: {
			name: 'createdatemeeting',
			options:[{name:msg['mobile.my.status.month'],value:'month'},{name:msg['mobile.my.status.threeMonth'],value:'threeMonth'},{name:msg['mobile.my.status.halfAYear'],value:'halfYear'}],
			values:{'createdatemeeting':'month'}
		}
	});
	
	return [TagFilter].join('');
	
});