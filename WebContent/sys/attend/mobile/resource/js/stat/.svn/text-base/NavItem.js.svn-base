define(['dojo/_base/declare','mui/nav/NavItem','mui/util','dojo/request','dojo/dom-construct',
        'dijit/registry','dojo/date/locale','mui/i18n/i18n!sys-mobile','dojo/query'], 
		function(declare,NavItem,util,request,domConstruct,registry,locale,muiMsg,query ) {

	return declare('sys.attend.mobile.NavItem', [NavItem], {
		
		buildRendering : function() {
			this.inherited(arguments);
			this.subscribe("/mui/navItem/reset/num",'statNavCount');
		},
		startup : function() {
			this.inherited(arguments);
			this.connect(this.domNode, "onclick", '_onClick');
		},
		statNavCount:function(){
			if (this.badge == 0 && this.url != '' && this.url != null) {
				var _self = this;
				if(this.isNavCount==true){
					var countUrl = util.formatUrl(_self.url + "&rowsize=1");
					var id = this.statType=='2' ? "mStatList_statDate" :"statList_statDate";
					var statListDateObj = registry.byId(id);
					var statDate = locale.parse(statListDateObj.get('value')+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
					var fdDeptObj = registry.byNode(query('.muiStatCriterion .muiAddressForm .muiCategory')[0]);
					if(this.statType=='2'){
						countUrl = util.setUrlParameter(countUrl,"fdMonth",statDate.getTime());
					}else{
						countUrl = util.setUrlParameter(countUrl,"fdDate",statDate.getTime());
					}
					countUrl = util.setUrlParameter(countUrl,"fdDeptId",fdDeptObj.get('value'));
			
					request.post(countUrl, {
						handleAs : 'json'
					}).then(function(data) {
						var numNodes = query(".muiTitleNum",_self.domNode);
						if(numNodes.length==0){
							var titleNumNode = domConstruct.create('span',{innerHTML:data.page.totalSize,className:'muiTitleNum'},_self.domNode,'first');
						}else{
							numNodes[0].innerHTML=data.page.totalSize;
						}
						
					}, function(data) {

					});
				}
			}
		}

	});
});