define([ 
	"dojo/_base/declare", 
	"dojo/_base/array", 
	"dojo/topic", 
	"dojo/dom-construct", 
	"mui/form/Address",
	"mui/util"
	], function(declare, array, topic, domConstruct, Address, util) {
	
	return declare("sys.lbpmservice.mobile.opthandler.OptHandler", [Address], {
		type: ORG_TYPE_POSTORPERSON,
		
		text: "备选列表",
		
		//是否显示头像
		showHeadImg : false,
		
		isMul: true , 
		
		templURL:  "sys/lbpmservice/mobile/opthandler/opthandler.jsp",
		
		_cateDialogPrefix: "__opthandler__",
		
		isFormatText:true,
		
		handlerIdentity:null,
		
		optHandlerSelectType: null,
		
		fdModelName: null,
		
		fdModelId: null,
		
		deptLimit:"",
		
		dataUrl: '/sys/lbpmservice/mobile/opthanlder.do?method=handlers&deptLimit=!{deptLimit}&handlerIdentity=!{handlerIdentity}&optHandlerSelectType=!{optHandlerSelectType}&fdModelName=!{fdModelName}&fdModelId=!{fdModelId}',
		
		startup : function() {
			this.inherited(arguments);
			/*this.dataUrl = encodeURI(util.urlResolver(this.dataUrl, this));*/
			this.dataUrl = util.urlResolver(this.dataUrl, {
				handlerIdentity:this.handlerIdentity,
				optHandlerIds:encodeURIComponent(this.optHandlerIds),
				optHandlerSelectType:this.optHandlerSelectType,
				fdModelName:this.fdModelName,
				fdModelId:this.fdModelId,
				deptLimit:this.deptLimit
			});
			this.templURL ="sys/lbpmservice/mobile/opthandler/opthandler.jsp?isMul=" + this.isMul;
			this.searchUrl = this.dataUrl+'&keyword=!{keyword}';
		},
	
		//重写公式获取值
		_selectCate$1: function() {
			if(this.isFormatText){
				this.optHandlerIds = util.formatText(this.optHandlerIds);
				this.isFormatText=false;
			}
			this.inherited(arguments);
			
		}
	
	});
});