define(function(require, exports, module) {

	var dialog = require("lui/dialog");
	var $ = require("lui/jquery");
	var lang = require('lang!kms-common');
	var addressImport = require('kms/common/kms_common_person_import/js/address_import.js');

	window.importingStaff = function importingStaff(tr_id,ele_id,ele_name,flag) { 
		//第一个参数tr_id为地址本锁定的一处上级元素id(可为空)，用于定位地址本,flag为是否重置该地址本框

			dialog
			.iframe(
					"/kms/common/kms_common_person_import/kmsPersonImport_edit.jsp",
					lang['ImportTheList'],
					null,
					{
						width : 684,
						height : 324,
						buttons : [ 
							{
								name : lang['person.import'],
								value : true,
								focus : true,
								fn : function(value,_dialog) {

									var _iframe = _dialog.content.iframeObj[0];
									var _doc = _iframe.contentDocument;
									if(!_doc.forms['kmsPersonImportForm']){
										_dialog.hide();
										return;
									}
									var doc = $(_doc);
									
									var _ids = doc.find("input[name='fdPersonId']")[0].value; //导入得到ids
									var _names = doc.find("input[name='fdPersonName']")[0].value;//导入得到names
									
									//调用批量操作地址本工具
									addressImport.addressImport(ele_id,ele_name,_ids,_names,flag);
									
									_dialog.hide();
									
									
								}
							},
							{
								name : lang['person.cancel'],
								styleClass : 'lui_toolbar_btn_gray',
								value : true,
								focus : true,
								fn : function(value,_dialog) {
									_dialog.hide();
								}
							}
							]
					});


	}
});