/**
 * 会议审批 - 会议安排审批
 */
define(['mui/createUtils',"mui/i18n/i18n!km-imeeting:mobile"], function(createUtils,msg){
	
	var h = createUtils.createTemplate;
	
	// 排序（创建时间）
	var TagFilter = h('div', {
		dojoType: 'mui/property/TagFilterItem',
		dojoProps: {
			name: 'datemeeting',
			options:[{name:msg['mobile.my.status.all'],value:'all'},{name:msg['mobile.my.status.today'],value:'today'},{name:msg['mobile.my.status.tomorrow'],value:'tomorrow'},{name:msg['mobile.my.status.week'],value:'week'}],
			values:{'datemeeting':'all'},
			isTagCount:true
		}
	});
	
	return [TagFilter].join('');
	
});