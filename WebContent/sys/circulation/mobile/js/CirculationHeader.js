define([ "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dojo/dom-class",
         "dojo/request",
         "mui/util",
         "mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile"
         ],
         function(declare, WidgetBase, domClass, request, util, Msg){
	var button = declare("sys.circulation.CirculationHeader", [ WidgetBase ], {
		
		postCreate: function() {
			this.inherited(arguments);
		},
		startup: function() {
			this.inherited(arguments);
		},
		buildRendering : function() {
			this.inherited(arguments);
			domClass.add(this.domNode,'muiCirculationHeader');
			this.domNode.innerText=Msg['sysCirculationMain.mobile.lastCirculation'];
			var self = this;
			var url = util.formatUrl('/sys/circulation/sys_circulation_main/sysCirculationMain.do?method=getCirculationInfo');
			url = util.setUrlParameter(url, "fdModelId", this.modelId);
            url = util.setUrlParameter(url, "fdModelName", this.modelName);
			request.get(url, {handleAs:'json',sync:true}).then(function(json) {
				if(json && json.count){
					self.domNode.innerText=Msg['sysCirculationMain.mobile.lastCirculation']+"("+json.count+")";
				}
			});
		}
	});
	return button;
});
