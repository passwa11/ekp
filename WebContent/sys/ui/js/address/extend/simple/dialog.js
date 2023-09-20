(function(){
	
	Com_RegisterFile("sys/ui/js/address/extend/simple/dialog.js");
	Com_IncludeFile("dialog.js");
	Com_IncludeFile("treeview.js");
	
	window.Dialog_AddressSimple = function(mulSelect, idField, nameField, splitStr, selectType, action, URLOptions){

		var dialog = new KMSSDialog(mulSelect);
		dialog.addressBookParameter = new Array();
		
		if(selectType==null || selectType==0)
			selectType = ORG_TYPE_ALL;
		dialog.addressBookParameter.selectType = selectType;
		dialog.BindingField(idField, nameField, splitStr, null);
		dialog.SetAfterShow(action);
		
		URLOptions = URLOptions || {};
		
		var URL = URLOptions.URL;
		if(!URL){
			URL = "sys/ui/js/address/extend/simple/address.jsp";
			URL = Com_SetUrlParameter(dialog.URL,'mul',(mulSelect?1:0));
		}
		dialog.URL = URL;
		
		var nodeBeanURL = URLOptions.nodeBeanURL;
		dialog.nodeBeanURL = nodeBeanURL;
		
		if (typeof(seajs) != 'undefined' && typeof( top['seajs'] ) != 'undefined' ) {
			Com_EventPreventDefault();
			top.Com_Parameter.Dialog = dialog;
			seajs.use('lui/dialog', function(dialog) {	
				var url = '/sys/ui/js/address/extend/simple/address.jsp',
					fieldObjs = top.Com_Parameter.Dialog.fieldList;
				url =  Com_SetUrlParameter(url,'mul',(mulSelect?1:0));
				dialog.iframe(url,Data_GetResourceString('sys-organization:sysOrg.addressBook'),null,dialog.getSizeForAddress());
				DialogFunc_BlurFieldObj(fieldObjs);
			});
		}else{
			var size = getSizeForAddress();
			dialog.Show(size.width, size.height);
		}
	};
	
	
})();