define(["dojo/_base/declare", "mui/tabbar/TabBarButton", "dojo/dom-class", "dijit/registry", 
        "dojo/topic", "mui/form/_FormBase", "dojo/dom"], function(
       	declare, TabBarButton, domClass, registry, topic, FormBase, dom) {

		return declare("mui.tabbar.SaveDraftButton", [TabBarButton], {
				
				//生成校验器的节点Id
				validateDomId : 'scrollView',
				
				//需要校验的字段Id, 可校验多个字段, 中间以;分隔
				validateElementId : '',

				buildRendering:function(){
					this.inherited(arguments);
					
					domClass.add(this.domNode,'muiBarSaveDraftButton');
				},
				
				docStatus: '',
				
				saveDraft : false,
				
				onClick : function() {
					this.defer(function(){
						var _validator = registry.byId(this.validateDomId);
						var validateElementId = $.trim(this.validateElementId);
						if (!_validator) {
							return;
						}
						if (validateElementId) {
							if (validateElementId.substring(validateElementId.length - 1) == ";") {
								validateElementId = validateElementId.substring(0, validateElementId.length-1);
							}
						}
						var canSubmit = false;
						var idArray = [];
						if (validateElementId) {
							idArray = validateElementId.split(";");
						} else{
							canSubmit = true;
						}

						var elems = _validator.getValidateElements();
						for (var id = 0; id < idArray.length; id++) {
							var n = registry.byNode(dom.byId(idArray[id]));
							var index = elems.indexOf(n);
						    if (index > -1) {
						    	elems.splice(index, 1);
						    }
						}
						_validator.removeElementValidate(elems,'required');
						this.saveDraft = true;
						lbpm.globals.saveDraft = true;
						if (_validator.validate()) {
							canSubmit = true;
						} else {
							canSubmit = false;
						}
						this.saveDraft = false;
						_validator.resetElementValidate(_validator.getValidateElements());

						var method = Com_GetUrlParameter(location.href,'method');
						var docStatus = this.docStatus;
						if (canSubmit) {
							if(method=='add'){
								Com_Submit(document.forms[0], 'saveDraft', null, { saveDraft : true });
							} else{
								if (docStatus == '11') {
									Com_Submit(document.forms[0], 'updateDraft', null, { saveDraft : true });
								} else {
									Com_Submit(document.forms[0], 'update', null, { saveDraft : true });
								}
							}
						}
					}, 350);
				}

		});
});