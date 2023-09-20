define([
        "dojo/_base/declare",
        "mui/tabbar/TabBarButton",
        "dojo/topic",
        "dojo/dom-class",
        "dojo/dom-style",
        "dojox/mobile/sniff",
        "mui/device/device"
	], function(declare, TabBarButton, topic, domClass, domStyle, has, device) {
	
	return declare("sys.lbpmservice.mobile.workitem.OperationButton", [TabBarButton], {
		
		operationType:null,
		
		buildRendering : function() {
			this.inherited(arguments);
			if(this.tabIndex == '0'){
				//domStyle.set(this.domNode,"color","#fff");
				//domStyle.set(this.domNode,"background-color","#37AEE9");
				domClass.add(this.domNode,"selected");
			}
		},
		
		startup : function() {
			if (this._started)
				return;
			
			this.inherited(arguments);
		},
		
		_setSelectedAttr : function(selected){
			this.inherited(arguments);
			this.defer(function(){
				this.set("selected", false);
			},380);
		},
		
		onClick : function(evt) {
			if(device && device.getClientType() == 9){
				evt = evt || window.event || arguments[0];
		        if (evt.preventDefault) {
		         	evt.preventDefault();
		        }
		        if (evt.stopPropagation) {
		          	evt.stopPropagation();
		        }
		        var nowTime = new Date().getTime();
		        var clickTime = lbpm.cbtime;
		        if (clickTime != "undefined" && nowTime - clickTime < 500) {
		           return false;
		        }
		        if(!lbpm.cbtime){
		        	lbpm.firstClickStatus = true;
		        }
		        lbpm.cbtime = nowTime;
			}
			//提供给某些位置使用，注意使用完成后要注销调，比如审批要点要获取类型来判断，但是类型还没初始化
			if(lbpm){
				lbpm.operationButtonType = this.operationType;
			}else{
				window.operationButtonType = this.operationType;
			}
			topic.publish("/lbpm/operation/switch", this, {
				methodSwitch: true,
				value: this.operationType,
				label: this.label
			});
		}
	});
});