define([ 
	"dojo/_base/declare", 
	"dojo/_base/array", 
	"dojo/topic", 
	"dojo/dom-construct", 
	"mui/form/Address",
	"mui/util"
	], function(declare, array, topic, domConstruct, Address, util) {
	
	return declare("sys.circulation.CirculationOtherScope", [Address], {
		
		text: "传阅人员",
		
		//是否显示头像
		showHeadImg : true,
		
		isMul: true , 
		
		templURL:  "sys/circulation/mobile/js/CirculationOtherScope.jsp?isMul=true",
		
		_cateDialogPrefix: "__cirhandler__",
		
		isFormatText:true,
		
		fdModelName: null,
		
		deptLimit:"",
		
		scope:'44',
		
		dataUrl: '/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=handlers&fdModelName=!{fdModelName}',
		
		startup : function() {
			this.inherited(arguments);
			this.dataUrl = util.urlResolver(this.dataUrl, {
				fdModelName:this.fdModelName
			});
			this.searchUrl = this.dataUrl+'&keyword=!{keyword}';
		}
	});
});