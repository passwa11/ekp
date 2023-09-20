define(["dojo/_base/declare",
	"sys/mportal/module/mobile/containers/header/CardMixin",
	"mui/i18n/i18n!sys-person:sysPersonMlink"], 
		function(declare, CardMixin, msg) {
	return declare("", [CardMixin], {
		datas : {
			title : {
				text : ''+msg['sysPersonMlink.myMsg']+''
			},
			menus : [
			     [
			      	{
			      		text: ''+msg['sysPersonMlink.pending']+'',
			      		countUrl: '/sys/person/sys_person_module_data/sysPersonModuleData.do?method=getModuleData',
			      		href : '/sys/notify/mobile/index.jsp#path=0',
			      		countPath : 'todo1'
			      	},
			      	{
			      		text: ''+msg['sysPersonMlink.toBeRead']+'',
			      		countUrl: '/sys/person/sys_person_module_data/sysPersonModuleData.do?method=getModuleData',
			      		href : '/sys/notify/mobile/index.jsp#path=1',
		      			countPath : 'todo2'
			      	},
			      	{
			      		text: ''+msg['sysPersonMlink.done']+'',
			      		countUrl: '/sys/person/sys_person_module_data/sysPersonModuleData.do?method=getModuleData',
			      		href : '/sys/notify/mobile/index.jsp#path=2',
		      			countPath : 'todoDone'
			      	},
			     ]
			]
		}
	});
})
