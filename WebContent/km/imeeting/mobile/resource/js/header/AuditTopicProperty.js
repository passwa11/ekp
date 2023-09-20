/**
 * 会议审批 - 会议安排审批
 */
define(['mui/createUtils',"mui/i18n/i18n!km-imeeting:kmImeeting"], function(createUtils,msg){
	
	var h = createUtils.createTemplate;
	
	// 排序（创建时间）
	var TagFilter = h('div', {
		dojoType: 'mui/property/TagFilterItem',
		dojoProps: {
			name: 'mytopic',
			isTagCount:true,
			options:[{name:msg['kmImeeting.my.approval'],value:'myApproval',prompt:true},{name:msg['kmImeeting.my.approved'],value:'myApproved'}],
			values:{'mytopic':'myApproval'}
		}
	});
	
	return [TagFilter].join('');
	
});