define([ "dojo/_base/declare", 
         "dojo/dom-construct",
         "dijit/_WidgetBase",
         "mui/util", 
         "mui/device/adapter",
         "dojo/request"],
		function(declare, domConstruct,widgetBase, util, adapter, request) {
			return declare('hr.staff.mobile.js.SysTagEditModal',[ widgetBase ],{
						modelId : "",			
						baseClass : "modal_edit_label_box",
						buildRendering : function() {
							this.inherited(arguments);
							var content = domConstruct.create("div" , {className : 'modal_edit_label_content'} , this.domNode);
							var url = Com_Parameter.ContextPath + 'hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=getTagsByModelId&modelId=' + this.modelId;
							request.get(url, {handleAs : 'json',headers: {"accept": "application/json"}})
							.response.then(function(datas) {
								if(datas.status == 200){
									var data = datas.data;
									for(var i = 0; i < data.length;i++){
										var item = domConstruct.create("div" , {className : 'mel_c_item'} , content);
										domConstruct.create("span" , {id : data[i].id,innerHTML : util.formatText(data[i].value)} , item);
										domConstruct.create("i", {className : 'mel_c_close_icon'}, item);
									}
									var button = domConstruct.create("div", {className : 'mel_c_item mel_c_item_edit_button'}, content);
									domConstruct.create("i", {className : 'mel_c_add_icon'}, button);
								}
							});
						},
						
						startup : function() {
							this.inherited(arguments);
							this.connect(this.domNode, "onclick", '_onClick');
						},
						_onClick : function(evt) {
							var target = evt.target;
						}
					})
		});