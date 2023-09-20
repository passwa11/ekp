define( [ 'dojo/_base/declare'], function(declare) {
	return declare('sys.person.AreaHeaderMixin', null, {
		//获取详细信息地址
		detailUrl : '/sys/person/sys_person_switchArea/sysPersonSwitchArea.do?method=getData&curId=!{curId}',
		
		resolveCateData: function(data) {
			return {
				fdId: data.id,
				label: data.name,
				parentId: data.parentId || ''
			};
			
		}
	});
});