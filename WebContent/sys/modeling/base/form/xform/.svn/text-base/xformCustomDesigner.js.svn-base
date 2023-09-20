/**
 * 业务建模对designer做的定制
 */
(function(){
	
	// 初始化多语言['业务建模']
	var _ModelingLang = [];
	_ModelingLang.push("sys-modeling-base:xform.relaitonSource.modeling");
	_ModelingLang = Data_GetResourceString(_ModelingLang.join(";"));
	
	function ModelingCustomDesigner(designer,win){
		// 对跟流程节点有关的表单控件移除相关配置
		CutDesignerInstan(designer,win);
	}
	
	function CutDesignerInstan(designer,win){
		// 隐藏权限区段、显示权限控件、阶段图、投票结果、传阅意见
		designer.toolBar.hideGroupButton("right");
		designer.toolBar.hideGroupButton("rightTree");
		designer.toolBar.hideGroupButton("stageDiagram");
		designer.toolBar.hideGroupButton("voteNode");
		designer.toolBar.hideGroupButton("fieldlaylout");
		designer.toolBar.hideGroupButton("fieldlist");
		designer.toolBar.hideGroupButton("ocr");
		if(window.isFlow != 'true'){//流程的才有
			designer.toolBar.hideGroupButton("auditShow");
			designer.toolBar.hideGroupButton("auditNote");
			designer.toolBar.hideGroupButton("newAuditNote");
            designer.toolBar.hideGroupButton("showHandlerNode");
            designer.toolBar.hideButtonNextEle("showHanqdlerNode");
            //#161189 无流程屏蔽E签宝控件
            designer.toolBar.hideGroupButton("eqb");
		}
		designer.toolBar.hideGroupButton("circulationOpinionShow");
		
		/*
		// 移除审批意见展示控件的按节点展示类型
		var opts = designer.controlDefinition['auditShow']['attrs']['mould']['opts'];
		designer.controlDefinition['auditShow']['attrs']['mould']['opts'] = opts.filter(function(opt){
			return opt.value != '21' && opt.value != '22' && opt.value != '31';
		});
		
		// 移除审批操作控件的按节点展示类型
		opts = designer.controlDefinition['auditNote']['attrs']['mould']['opts'];
		designer.controlDefinition['auditNote']['attrs']['mould']['opts'] = opts.filter(function(opt){
			return opt.value != '21';
		});
		
		// 移除审批操作控件的按节点展示类型
		opts = designer.controlDefinition['newAuditNote']['attrs']['mould']['opts'];
		designer.controlDefinition['newAuditNote']['attrs']['mould']['opts'] = opts.filter(function(opt){
			return opt.value != '21';
		});
		* */
	}
	
	window.ModelingCustomDesigner = ModelingCustomDesigner;
	
})()
