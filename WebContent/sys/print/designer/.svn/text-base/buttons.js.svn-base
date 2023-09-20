(function(window, undefined){
	/**
	 * 业务类
	 */
	var sysPrintButtons={};
	 
	 //判断打印模板是否包含其它控件
	 sysPrintButtons.isPrintTempContainCtrls=function(parentNode){
		var sysPrintCtrls = $(parentNode).html().match(/printcontrol/g);
		if(sysPrintCtrls && sysPrintCtrls.length>1){
			return true;
		}
		return false;
	 }
	
	 //判断是否可以一键导入
	 sysPrintButtons.isCanImportXFormTemp=function(designer){
	 	if(!sysPrintButtons.isXFormSupport()) return false;
		var key = designer.fdKey;
		var fdModeValue = sysPrintButtons.getXFormMode(key);
		if(fdModeValue=='2'){
			var id=document.getElementsByName('sysFormTemplateForms.'+key+'.fdCommonTemplateId')[0];
			if(!id || !id.value){
				return false;
			}
			return true;
		}else if(fdModeValue=='3' || fdModeValue=='4'){
			return true;
		}else{
			return false;
		}
	 };
	 //是否支持自定义表单
	 sysPrintButtons.isXFormSupport = function(){
		 var xForm = $('#isXForm').val();
		 if(xForm=='true'){
			return true;
		 }
		 return false;
	 };
	 //自定义表单引用方式
	 sysPrintButtons.getXFormMode = function(key){
		 if(PRINT_OPER_TYPE=='templateHistory'){
			 //历史模板操作
			 return '-1';
		 }
		if(!sysPrintButtons.isXFormSupport()) return null;
		var fdMode = document.getElementsByName('sysFormTemplateForms.'+key+'.fdMode');
		var fdModeValue = fdMode[0].value;
		return fdModeValue;
	 };
	 
	//一键导入自定义表单
	sysPrintButtons.importXFormTemp = function(designer,isTip) {
		if(!sysPrintButtons.isXFormSupport()){
			alert(DesignerPrint_Lang.moduleNoXFormAlert);
			return;
		}
		var key = designer.fdKey;
		var fdModeValue = sysPrintButtons.getXFormMode(key);
		if(fdModeValue=='2'){
			var id=document.getElementsByName('sysFormTemplateForms.'+key+'.fdCommonTemplateId')[0];
			if(!id || !id.value){
				alert(DesignerPrint_Lang.unUsedXFormTemplateAlert);
				return;
			}
			if(isTip){
				if(!confirm(DesignerPrint_Lang.overrideCurrentTemptByImportAlert)){
					return;
				}
			}
			var data = new KMSSData();
			data.AddBeanData('sysFormCommonTemplateTreeService&fdId=' + id.value);
			var html = data.GetHashMapArray()[0]['fdDesignerHtml'];
			$('#_tmp_xform_html').html(html);
			//控件解释转换
			designer.builder.parseCtrl($('#_tmp_xform_html')[0]);
			designer.builder.setHTML($('#_tmp_xform_html').html());
			//增加对撤销功能的支持
			if(typeof (sysPrintUndoSupport)  != 'undefined'){
				sysPrintUndoSupport.saveOperation();
			}
		}else if(fdModeValue=='3' || fdModeValue=='4'){
			if(isTip){
				if(!confirm(DesignerPrint_Lang.overrideCurrentTemptByImportAlert)){
					return;
				}
			}
			var method = $("#_method").val();
			var customIframe = window.frames['IFrame_FormTemplate_'+key];
			var customIframe = window.frames['IFrame_FormTemplate_'+key].contentWindow;
			if(!customIframe){
				customIframe = window.frames['IFrame_FormTemplate_'+key];
			}
			var html = "";
			if(customIframe.Designer){
				html = customIframe.Designer.instance.getHTML();
			}else{
				var name = "sysFormTemplateForms." + key+ ".fdDesignerHtml";
				html = document.getElementsByName(name)[0].value;
			}
			if(method=='add' || method=='edit' || method=='clone'){
				if(html){
					$('#_tmp_xform_html').html(html);
					designer.builder.parseCtrl($('#_tmp_xform_html')[0]);
					designer.builder.setHTML($('#_tmp_xform_html').html());
				}
				//增加对撤销功能的支持
				if(typeof (sysPrintUndoSupport)  != 'undefined'){
					sysPrintUndoSupport.saveOperation();
				}
			}
		}else if(fdModeValue=='-1'){
			//控件解释转换
			designer.builder.parseCtrl($('#_tmp_history_xform_html')[0]);
			designer.builder.setHTML($('#_tmp_history_xform_html').html());
			//增加对撤销功能的支持
			if(typeof (sysPrintUndoSupport)  != 'undefined'){
				sysPrintUndoSupport.saveOperation();
			}
		}else{
			alert(DesignerPrint_Lang.onlyXFormImportAlert);
		}
	};
	
	//生成数据控件jq dom
	sysPrintButtons.genDataCtrlDom = function(data) {
		var ctrHtm = [];
		ctrHtm.push('<div printcontrol="true" style="width: auto;display: inline" fd_type="');
		ctrHtm.push(data.fdType + '"');
		ctrHtm.push(' data_type="');
		ctrHtm.push(data.type + '"');
		if(data.dimension){
			ctrHtm.push(' data_dimension="');
			ctrHtm.push(data.dimension + '"');
		}
		ctrHtm.push(' id="');
		ctrHtm.push(data.name + '"');
		ctrHtm.push(' label="');
		var label = data.label||data.lable;
		ctrHtm.push(label + '"');
		if(data.isXFormDict){
			//来自自定义表单的字段
			ctrHtm.push(' fd_extend="true"');
		}
		ctrHtm.push('>');
		ctrHtm.push('$'+ label +'$</div>');
		var $newDom = $(ctrHtm.join(''));
		return $newDom;
	}
	//工具栏操作
	sysPrintButtons.createControl = function(controlType,designer){
		switch (controlType) {
			case 'table':
				var table=new sysPrintDesignerTableControl(designer.builder,"table");
				table.draw();
				break;
			case 'label':
				var label=new sysPrintLabelControl(designer.builder,'label');
				label.draw();
				break;
			case 'proccess':
				var selectDom = designer.builder.$selectDomArr[0];
				if(!selectDom){
					var proc=new sysPrintProcControl(designer.builder,'proccess');
					proc.draw();
					return;
				}
				var next = selectDom.next();
				if(selectDom.hasClass('td_normal_title') && next.length>0){
					//单元格为标题栏时，附带插入名称
					var label=new sysPrintLabelControl(designer.builder,'label',{text:DesignerPrint_Lang.approvalRecord});
					label.draw();
					//数据
					var proc=new sysPrintProcControl(designer.builder,'proccess');
					proc.draw(next);
				}else{
					var proc=new sysPrintProcControl(designer.builder,'proccess');
					proc.draw();
				}
				break;
			case 'page':
				var page=new sysPrintPageControl(designer.builder,'page');
				page.draw();
				break;
			case 'uploadimg':
				var uploadimg=new sysPrintUploadimgControl(designer.builder,'uploadimg');
				uploadimg.draw();
				break;
		}
	}
	//添加控件
	sysPrintButtons.addToolBarControl = function(owner){
		var button = sysPrintUtil.eventButton(event);
		//若点击鼠标左键，则创建控件；否则是取消创建状态
		if(button == 1) {
			var tAction = owner.toolBarAction;
			if (tAction != '') owner.builder.createControl(tAction,owner);
			//取消工具栏选中状态
			owner.toolBar.cancelSelect();
			//恢复鼠标样式
			owner.builderDomElement.style.cursor = 'default';
			return;
		} else if(button == 2) {
			//取消工具栏选中状态
			owner.toolBar.cancelSelect();
			//恢复鼠标样式
			owner.builderDomElement.style.cursor = 'default';
		}
	}
	
	window.sysPrintButtons=sysPrintButtons;//打印机制通用工具
	
})(window);