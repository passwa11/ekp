/**
 * 会议审批 - 会议室审批
 */
define(['mui/createUtils',"mui/i18n/i18n!km-imeeting:kmImeeting"], function(createUtils,msg){
	
	var h = createUtils.createTemplate;
	
	// 排序（创建时间）
	var TagFilter = h('div', {
		dojoType: 'mui/property/TagFilterItem',
		dojoProps: {
			name: 'mydoc',
			isTagCount:true,
			options:[{name:msg['kmImeeting.my.approval'],value:'waitExam',prompt:true},{name:msg['kmImeeting.res.true'],value:'agree'},{name:msg['kmImeeting.res.false'],value:'reject'}],
			values:{'mydoc':'waitExam'}
		}
	});
	
	return [TagFilter].join('');
	
});