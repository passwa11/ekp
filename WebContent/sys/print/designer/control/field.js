(function(window, undefined){
	/**
	 * 字段面板
	 */
	var fieldPanel = function(){
		
		this.designer = null;
		this.domElement = null;
		
		this.init = init;
		this.drawTitle = drawTitle;
		this.drawContent = drawContent;
		this.drawBottom = drawBottom;
		this.genContentItem = genContentItem;//生成面板记录项
		this.addDetailTableData = addDetailTableData; //添加普通表单明细表数据
		this.addXFormDetailTableData=addXFormDetailTableData;//自定义表单中明细表
		this.resetPanelPosition = resetPanelPosition;
		this.isSelected = isSelected;//字段是否已选择
		
		this._isReViewRtfMode=_isReViewRtfMode; //是否流程表单RTF模式
		this.validateDetailTableData=validateDetailTableData;//校验明细表中数据合法性
	};
	
	fieldPanel.prototype = {
		addListener : addListener,// 增加监听器
		removeListener : removeListener,// 移除监听器
		fireListener : fireListener,// 通知监听器	
		
		open: function(designer,x,y) {
			//默认在右边展示面板 
			this.init(designer);
			this.resetPanelPosition(x,y);
			this.show();
			
			this.designer.builder.sysPrintFieldPanelInstance=this;
		},
		titleMousedown:function(){
			$(this.domElement).draggable({cursor: 'move'});
		},
		mouseOver:function(event){
			$(event).addClass('field_panel_mouseover');
		},
		mouseOut:function(event){
			$(event).removeClass('field_panel_mouseover');
		},
		select:function(currObj){
			if (event.stopPropagation) { 
				event.stopPropagation();
			}else if(window.event){
				window.event.cancelBubble = true; 
			}
			
			var $currObj = $(currObj);
			var data = {"id":$currObj.attr('name'),"name":$currObj.attr('label'),"type":$currObj.attr('type'),"isformdtable":$currObj.attr('isformdtable'),"isxformdict":$currObj.attr('isxformdict')};
			
			if(this.designer.builder.$selectDomArr.length==0){
				alert(DesignerPrint_Lang.selectCelAlert);
				return;
			}
			//设置已选字段颜色
			$(currObj).addClass('field_panel_choosed');
			
			//页面位置渲染布局
			var $selectDom = this.designer.builder.$selectDomArr[0];
			//节点所在控件
			var selectDomCtrl = sysPrintUtil.getPrintDesignElement($selectDom[0]);
			//是否明细表
			var isDetailTable = $(selectDomCtrl).attr('fd_type')=='detailsTable' ? true:false;
			var next = $selectDom.next();
			//普通表单中明细表
			if(data.isformdtable=='true'){
				this.addDetailTableData(data);
				return ;
			}
			//自定义表单明细表
			if(this.addXFormDetailTableData(data,currObj)){
				return;
			}
			
			if($selectDom.hasClass('td_normal_title') && next.length>0){
				//单元格为标题栏时，附带插入名称
				var label=new sysPrintLabelControl(this.designer.builder,'label',{text:data.name});
				label.draw();
				//数据
				var field=new sysPrintDesignerFieldControl(this.designer.builder,'field',{data:data});
				field.draw(next);
			}else{
				var field=new sysPrintDesignerFieldControl(this.designer.builder,'field',{data:data});
				field.draw();
			}
			
			//支持撤消操作
			sysPrintUndoSupport.saveOperation();
		},
		close : function(event) {
			if (event.stopPropagation) { 
				event.stopPropagation();
			}else if(window.event){
				window.event.cancelBubble = true; 
			}
			this.destroy();
		},
		show : function() {
			this.domElement.style.display = 'block';
		},
		destroy:function(){
			$(this.domElement).remove();
			this.designer.builder.sysPrintFieldPanelInstance=null;
			window.sysPrintFieldPanelInstance=null;
		}
	};
	
	function init(designer) {
		this.designer = designer;
		this.domElement = document.createElement('div');
		$(this.domElement).attr('name','fieldPanel');
		$('#sysPrintdesignPanel')[0].appendChild(this.domElement);
		with(this.domElement.style) {
			position = 'absolute'; width = '253px'; height = '177px'; zIndex = '101';
		}
		
		this.drawTitle();
		this.drawContent();
		this.drawBottom();
		$(this.domElement).draggable({cursor: 'move',containment:'#sys_print_designer_draw'});
		var _domElement = this.domElement;
		Com_AddEventListener(window , 'scroll' , function() {
			if  (_domElement._clientTop != null) {
				_domElement.style.top = parseInt(_domElement._clientTop) + document.body.scrollTop + 'px';
			}
		});

	}
	function resetPanelPosition(x,y){
		// 修正 x 轴超出问题
		var p_size = 20;
		var right_x_pos = x + this.domElement.offsetWidth;
		if (right_x_pos > document.body.offsetWidth - p_size) {
			x = document.body.offsetWidth - this.domElement.offsetWidth - p_size;
		}
		this.domElement.style.left=($('#sysPrintdesignPanel').width()-235)+"px";
		this.domElement.style.top = (y ? y : 0) + 'px';
	}
	function drawTitle() {
		//this.titleBox = document.createElement('div');
		var title = [];
		title.push('<div style="cursor:move;"><div class="panel_title"><div class="panel_title_left"><div class="panel_title_right"></div>');
		title.push('<div class="panel_title_center"><div class="panel_title_text">');
		title.push('<span>' + DesignerPrint_Lang.fieldList + '</span></div></div></div><div class="panel_title_btn_bar">');
		title.push('<a href="#" onmousedown="sysPrintFieldPanelInstance.close(event)" title="' + DesignerPrint_Lang.panelCloseTitle + '" class="panel_title_close"></a>');
//		title.push('<a href="#" title="折叠" class="panel_title_fold"></a>');
//		title.push('<a href="#" title="展开" class="panel_title_expand" style="display: none;"></a>');
		title.push('</div></div><div>');
		this.domElement.appendChild($(title.join(''))[0]);
	}
	function drawContent(){
		var mainWrap = document.createElement('div');
		mainWrap.className = 'panel_main_tree';
		var mainBox = document.createElement('div');
		mainBox.className = 'panel_main_tree_box';

		mainWrap.appendChild(mainBox);
		this.domElement.appendChild(mainWrap);

		var fdKey = this.designer.fdKey;
		var modelName = this.designer.modelName;
		var _xformCloneTemplateId = $('#_xformCloneTemplateId').val();
		var baseObjs = sysPrintCommon.getDocDict(fdKey,modelName,_xformCloneTemplateId);
		var contentHTML=[];
		for(var i=0;i<baseObjs.length;i++){
			var f=baseObjs[i];
			//去除部门字段
			if(fdKey=='reviewMainDoc' && !this._isReViewRtfMode(fdKey)){
				if(f.name=='docContent' || f.name=='fdAttachment')
					continue;
			}
			//去除关联控件多余属性(若是明细表中字段则无法去除多余字段)
			if(f.businessType=='relevance' && f.name.indexOf('_config')>-1){
				continue;
			}
			if(f.subprops){
				f.isFormDTable = "true";
			}
			contentHTML.push(this.genContentItem(f));
			//明细表
			if(f.subprops){
				var _subprops = eval(f.subprops.replace(/=/ig,':'));
				for(var j=0;j < _subprops.length;j++){
					var config = _subprops[j];
					//非自定义表单明细表数据
					config.isFormDTable = "true";
					contentHTML.push(this.genContentItem(config));
				}
				
			}
		}

		mainBox.innerHTML = '<div style="position : relative">'+ contentHTML.join('')+'</div>';
		
		if (mainBox.offsetHeight > 300) {
			mainBox.style.height = '300px';
			mainBox.style.overflow = 'auto';
		}
		mainWrap.style.height = mainBox.offsetHeight + 'px';
	}
	
	function _isReViewRtfMode(fdKey){
		if(fdKey=='reviewMainDoc'){
			var fdMode = document.getElementsByName('sysFormTemplateForms.'+fdKey+'.fdMode');
			var fdModeValue = "";
			for(var i=0;i<fdMode.length;i++){
				if(fdMode[i].checked){
					fdModeValue = fdMode[i].value;
					break;
				}
			}
			if(fdModeValue=='1'){
				return true;
			}
		}
		
		return false;
	}
	
	function genContentItem(config){
		var item=[];
		var classId="field_panel_normal";
		//已选择字段用颜色区分
		if(this.isSelected(config.name)){
			classId +=" field_panel_choosed";
		}
		item.push("<div class='"+classId+"' ");
		//name label type
		item.push(" isFormDTable='"+config.isFormDTable+"'");
		item.push(" isxformdict='"+config.isXFormDict+"'");
		item.push(" name='"+config.name+"'");
		item.push(" label='"+config.label+"'");
		item.push(" type='"+config.type+"'");
		item.push(" onmouseover='sysPrintFieldPanelInstance.mouseOver(this)'");
		item.push(" onmouseout='sysPrintFieldPanelInstance.mouseOut(this)'");
		item.push(" onmousedown='sysPrintFieldPanelInstance.select(this,event)'");
		item.push(">");
		item.push("<span>");
		var labelRender = config.labelRender;
		var label = config.label;
		item.push(labelRender || label);
		item.push("</span>");

		item.push("</div>");
		return item.join("");
	}
	
	function isSelected(propId){
		//注意jquery中id的特殊字符
		var prop = $('#sys_print_designer_draw #'+propId.replace('.','\\.'));
		if(prop && prop.length>0){
			return true;
		}
		return false;
	}
	
	function validateDetailTableData(currObj,data){
		if(!(data.isxformdict=='true' && data.id.indexOf('.') > -1)){
			if(!this.isSelected(data.id)){
				$(currObj).removeClass('field_panel_choosed');
			}
			alert(DesignerPrint_Lang.detailTableIn);
			return false;
		}
		return true;
	}
	
	function addDetailTableData(data){
//		debugger;
		var $selectDom = this.designer.builder.$selectDomArr[0];
		var selectDomCtrl = sysPrintUtil.getPrintDesignElement($selectDom[0]);
		//是否明细表
		var isDetailTable = $(selectDomCtrl).attr('fd_type')=='detailsTable' ? true:false;
		
		if(data.isformdtable=='true'){
			//普通表单明细表
			if(!isDetailTable){
				var table=new sysPrintDetailsTableControl(this.designer.builder,"detailsTable");
				table.draw();
				//设置明细表名称
				table.$domElement.attr('name',data.id.split(".")[0]);
				//插入数据
				if(data.id.indexOf(".") > -1){
					var tableDom = table.$domElement[0];
					var labelCell = tableDom.rows[0].cells[1];
					var dataCell = tableDom.rows[1].cells[1];
					var label=new sysPrintLabelControl(this.designer.builder,'label',{text:data.name});
					label.draw($(labelCell));
					var field=new sysPrintDesignerFieldControl(this.designer.builder,'field',{data:data});
					field.draw($(dataCell));
					return;
				}
			}else{
				if(data.id.indexOf(".") ==-1){
					return;
				}
				if($selectDom.attr('row')=='0'){
					var tableDom = selectDomCtrl;
					var cellIndex = $selectDom[0].cellIndex;
					var labelCell = tableDom.rows[0].cells[cellIndex];
					var dataCell = tableDom.rows[1].cells[cellIndex];
					var label=new sysPrintLabelControl(this.designer.builder,'label',{text:data.name});
					label.draw($(labelCell));
					var field=new sysPrintDesignerFieldControl(this.designer.builder,'field',{data:data});
					field.draw($(dataCell));
					return;
				}else{
					var field=new sysPrintDesignerFieldControl(this.designer.builder,'field',{data:data});
					field.draw();
					return;
				}
			}
		}
	
	}
	//自定义表单明细表
	function addXFormDetailTableData(data,currObj){
		//debugger;
		var $selectDom = this.designer.builder.$selectDomArr[0];
		var $next = $selectDom.next();
		var selectDomCtrl = sysPrintUtil.getPrintDesignElement($selectDom[0]);
		//是否明细表
		var isDetailTable = $(selectDomCtrl).attr('fd_type')=='detailsTable' ? true:false;
		if(data.isxformdict=='true' && data.id.indexOf('.') > -1){
			//自定义表单中字段
			if(!isDetailTable){
				var attach = null;
				if($selectDom.hasClass('td_normal_title') && $next.length>0){
					attach = $next;
				}
				var table=new sysPrintDetailsTableControl(this.designer.builder,"detailsTable",{id:data.id.split(".")[0]});
				table.draw(attach);
				if(attach){
					//单元格为标题栏时，附带插入名称
					var label=new sysPrintLabelControl(this.designer.builder,'label',{text:data.name.split(".")[0]});
					label.draw();
				}
				//插入数据
				if(data.id.indexOf(".") > -1){
					var tableDom = table.$domElement[0];
					var labelCell = tableDom.rows[0].cells[1];
					var dataCell = tableDom.rows[1].cells[1];
					var label=new sysPrintLabelControl(this.designer.builder,'label',{text:data.name.split('.')[1]});
					label.draw($(labelCell));
					var field=new sysPrintDesignerFieldControl(this.designer.builder,'field',{data:data});
					field.draw($(dataCell));
				}
				return true;
			}else{
				if($selectDom.attr('row')=='0'){
					var tableDom = selectDomCtrl;
					var cellIndex = $selectDom[0].cellIndex;
					var labelCell = tableDom.rows[0].cells[cellIndex];
					var dataCell = tableDom.rows[1].cells[cellIndex];
					var label=new sysPrintLabelControl(this.designer.builder,'label',{text:data.name.split('.')[1]});
					label.draw($(labelCell));
					var field=new sysPrintDesignerFieldControl(this.designer.builder,'field',{data:data});
					field.draw($(dataCell));
				}else{
					var field=new sysPrintDesignerFieldControl(this.designer.builder,'field',{data:data});
					field.draw();
				}
				return true;
			}
		}else{
			if(!isDetailTable && data.isxformdict=='true'){
				//自定义表单中字段
				var xFormCtrl = sysPrintUtil.getDesignElement($('#_tmp_xform_html #'+data.id)[0]);
				var fd_type = $(xFormCtrl).attr('fd_type');
				if(fd_type=='detailsTable'){
					var attach = null;
					if($selectDom.hasClass('td_normal_title') && $next.length>0){
						attach = $next;
					}
					var table=new sysPrintDetailsTableControl(this.designer.builder,"detailsTable",{id:data.id.split(".")[0]});
					table.draw(attach);
					if(attach){
						//单元格为标题栏时，附带插入名称
						var label=new sysPrintLabelControl(this.designer.builder,'label',{text:data.name.split(".")[0]});
						label.draw();
					}
					return true;
				}
			}
			
			//非自定义表单中字段校验
			if(isDetailTable){
				if(!this.validateDetailTableData(currObj,data)){
					return true;
				}
			}
			return false;
		}
		
	}
	
	function drawBottom() {
		var bottom_bottom = document.createElement('div');
		bottom_bottom.innerHTML = '<div class="panel_bottom_left"><div class="panel_bottom_right"></div>'
			+ '<div class="panel_bottom_center"></div></div>';
		bottom_bottom.className = 'panel_bottom';
		this.domElement.appendChild(bottom_bottom);
	}
	
	//增加监听器
	function addListener(name, fun){
		var evt = this._eventListeners[name];
		if (evt == null) {
			evt = [];
			this._eventListeners[name] = evt;
		}
		evt.push(fun);
	}
	
	//移除监听器
	function removeListener(name, fun){
		var evt = this._eventListeners[name];
		if (evt != null) {
			for (var i = 0; i < evt.length; i ++) {
				if (fun === evt[i]) {
					evt.splice(i, 1);
					return;
				}
			}
		}
	}
	
	//通知监听器
	function fireListener(name) {
		var evt = this._eventListeners[name];
		if (evt != null) {
			for (var i = 0; i < evt.length; i ++) {
				evt[i](this);
			}
		}
	}
	//获取自定义表单html
	function _setPrintXFromHtm(fdKey){
		var xForm = $('#isXForm').val();
		if(xForm=='false'){
			return false;
		}

		var key = fdKey;
		var fdModeValue = sysPrintButtons.getXFormMode(key);
		
		if(fdModeValue=='2'){
			var id=document.getElementsByName('sysFormTemplateForms.'+key+'.fdCommonTemplateId')[0];
			if(!id || !id.value){
				return false;
			}
			var data = new KMSSData();
			data.AddBeanData('sysFormCommonTemplateTreeService&fdId=' + id.value);
			var html = data.GetHashMapArray()[0]['fdDesignerHtml'];
			$('#_tmp_xform_html').html(html);
			return true;
		}else if(fdModeValue=='3'){
			var method = $("#_method").val();
//			debugger;
			if(method=='add'){
				var customIframe = window.frames['IFrame_FormTemplate_'+key];
				var customIframe = window.frames['IFrame_FormTemplate_'+key].contentWindow;
				if(!customIframe){
					customIframe = window.frames['IFrame_FormTemplate_'+key];
				}
				var html = customIframe.Designer.instance.getHTML();
				$('#_tmp_xform_html').html(html);
				return true;
			}
			if(method=='edit'){
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
				$('#_tmp_xform_html').html(html);
				return true;
			}
			//兼容多打印模板
			if(method == 'editPrint' || method=='createPrint'){
				var name = "sysFormTemplateForms." + key+ ".fdDesignerHtml";
				var html = document.getElementsByName(name)[0].value;
				$('#_tmp_xform_html').html(html);
				return true;
			}
		}else{
			return false;
		}
	}
	
	
	/**
	 * 字段控件，可以取来自于表单和数据字典的字段
	 */
	var fieldControl=sysPrintDesignerControl.extend({
		
		_setPrintXFromHtm:_setPrintXFromHtm,
		_xFormFieldRender:_xFormFieldRender,//自定义表单与field控件渲染
		_formFieldRender:_formFieldRender,//普通表单与field控件渲染
		
		
		render:function(config){
			if(config && config.renderLazy) return;
			var self=this;
			if(config && config.data){
				var fdType = "input";
				if(config.data.type=='Attachment'){
					fdType = "attachment";
				}
				if(config.data.type=='RTF'){
					fdType = "rtf";
				}
				if(config.data.type=='docQRCode'){
					fdType = "docQRCode";
					config.data.isxformdict="false";
				}
				self.$domElement=$('<div printcontrol="true" style="width: auto;display:inline-block;" data_type="'+config.data.type+'" fd_type="' + fdType + '" id="'+config.data.id+'"  label="'+config.data.name+'">$'+config.data.name+'$</div>');
				//解决方案:区分这个字段来自自定义表单还是普通表单isxformdict=='true'
				if(config.data.isxformdict=='true'){
					//来自自定义表单的字段
					self.$domElement.attr('fd_extend','true');
					//根据id重新获取表单控件的配置参数
					this._xFormFieldRender(config);
				}else{
					this._formFieldRender(config);
				}
			}

		},
		bind:function(){
			var self = this;
			this.addListener('mousedown',function(event){
				if(self instanceof sysPrintDesignerFieldControl){
					//清空并重设选中控件
					self.builder.setSelectDom(self.$domElement);
					self.builder.selectControl=self;
					//设置当前选中样式
					self.$domElement.addClass("border_selected");
				}
			});
		}
		
		
	
	});
	//自定义表单与field控件渲染
	function _xFormFieldRender(config){
		//自定义表单控件转换
		if(this._setPrintXFromHtm(this.builder.owner.fdKey)){
			//自定义表单控件
			var id = config.data.id;
			if(id.indexOf('.')>-1){
				//明细表中字段
				id = id.split('.')[1];
			}
			var xFormCtrl = sysPrintUtil.getDesignElement($('#_tmp_xform_html #'+id)[0]);
			if(!xFormCtrl){
				return;
			}
			var $xFormCtrl = $(xFormCtrl);
			//自定义表单控件类型
			var fdType = $xFormCtrl.attr('fd_type');
			//审批意见配置参数
			if(fdType=='auditShow'){
				var exhibitionstyleclass = $xFormCtrl.attr('exhibitionstyleclass');
				var filternote = $xFormCtrl.attr('filternote');
				var attr_name = $xFormCtrl.attr('attr_name');
				var value = $xFormCtrl.attr('value');
				var mould = $xFormCtrl.attr('mould');
				var width = $xFormCtrl.attr('width');
				var sortnote = $xFormCtrl.attr('sortnote');
				this.$domElement.attr('fd_type','auditShow').attr('filternote',filternote).attr('attr_name',attr_name).attr('exhibitionstyleclass',exhibitionstyleclass)
							 .attr('value',value).attr('mould',mould).attr('sortnote',sortnote).attr('width',width);
			}
			//生成新控件
			if(fdType=="detailsTable"){
			}
			//单选框控件
			if(fdType=='inputRadio'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				var items = values.items;
				var alignment = values.alignment;
				this.$domElement.attr('fd_type','inputRadio').attr('items',items).attr('alignment',alignment).addClass("xform_inputRadio");
			}
			if(fdType=='inputCheckbox'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				var items = values.items;
				var alignment = values.alignment;
				this.$domElement.attr('fd_type','checkbox').attr('items',items).attr('alignment',alignment).addClass("xform_inputCheckBox");
			}
			if(fdType=='select' || fdType=='fSelect'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				var items = values.items;
				this.$domElement.attr('fd_type',fdType).attr('items',items).addClass("xform_Select");
			}
			if(fdType=='attachment'){
				this.$domElement.attr('fd_type','attachment');
			}
			if(fdType=='inputText' || fdType=='calculation'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				this.$domElement.attr('thousandShow',values.thousandShow).attr('data_type',values.dataType)
								.attr('canShow',values.canShow).attr('scale',values.scale)
								.addClass(fdType=='inputText' ? "xform_inputText" : "xform_Calculation");
			}
			if(fdType=='textarea'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				this.$domElement.attr('fd_type','textarea').attr('_width',values.width)
								.attr('_height',values.height).addClass("xform_textArea");
			}
			if(fdType=='address'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				var multiSelect = values.multiSelect;
				var orgType = values._orgType;
				this.$domElement.attr('fd_type','address').attr('multiSelect',multiSelect).attr('orgType',orgType);
			}
			if(fdType=='new_address'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				var $input = $xFormCtrl.find('input');
				this.$domElement.attr('fd_type','address').attr('multiSelect',values.multiSelect).attr('orgType',values._orgType)
						.attr('scope',$input.attr('scope'));
			}
			if(fdType=='relationSelect'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				//debugger;
				var outputParams = values.outputParams;
				var inputParams = values.inputParams;
				var funKey = values.funKey;
				var source = values.source;
				var width  = values.width;
				this.$domElement.attr('fd_type','relationSelect').attr('outputParams',outputParams).attr('inputParams',inputParams).attr('funKey',funKey)
					.attr('source',source).attr('width',width).addClass('xform_relation_select');;
			}
			if(fdType=='map'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				this.$domElement.attr('fd_type','map').attr('_width',values.width).attr('_readonly',values.readOnly);
			}
			if(fdType=='relationRadio'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				var outputParams = values.outputParams;
				var inputParams = values.inputParams;
				var funKey = values.funKey;
				var source = values.source;
				var alignment = values.alignment;
				this.$domElement.attr('fd_type','relationRadio').attr('outputParams',outputParams).attr('inputParams',inputParams).attr('funKey',funKey).attr('source',source)
								.attr('alignment',alignment).attr('defValue',values.defValue).addClass('xform_relation_radio');
			}
			if(fdType=='relationCheckbox'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				var outputParams = values.outputParams;
				var inputParams = values.inputParams;
				var funKey = values.funKey;
				var source = values.source;
				var alignment = values.alignment;
				this.$domElement.attr('fd_type','relationCheckbox').attr('outputParams',outputParams).attr('inputParams',inputParams).attr('funKey',funKey).attr('source',source)
								.attr('alignment',alignment).attr('defValue',values.defValue).addClass('xform_relation_checkbox');
			}
			if(fdType=='relationChoose'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				this.$domElement.attr('fd_type','relationChoose').attr('outputParams',values.outputParams).attr('inputParams',values.inputParams).attr('funKey',values.funKey).attr('source',values.source)
								.attr('listrule',values.listrule).attr('fillType',values.fillType).attr('fillTypeOne',values.fillTypeOne)
								.attr('_width',values.width).addClass('xform_relation_choose');
			}
			if(fdType=='formula_calculation'){
				this.$domElement.attr('fd_type','formula_calculation').attr('formula',$xFormCtrl.attr('formula')).attr('returnType',$xFormCtrl.attr('returntype')).attr('expression_mode',$xFormCtrl.attr('expression_mode'))
					.attr('loadType',$xFormCtrl.attr('loadtype')).attr('controlsId',$xFormCtrl.attr('controlIds')).addClass('xform_formula_load');
			}
			if(fdType=='qrCode'){
				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
				this.$domElement.attr('fd_type',fdType).attr("_width",values.width).attr("_height",values.height)
					.attr('mold',values.mold).attr('valType',values.valType).attr('content',values.content);
				this.$domElement.empty().append('<label class="qrCodeControl"></label>')
			}
			if(fdType=='relevance'){
				this.$domElement.attr('fd_type','relevance')
				
			}
			if (fdType=="voteNode") {
				this.$domElement.attr('fd_type','voteNode')
			}
			if (fdType=="massData") {
				this.$domElement.attr('fd_type','massData')
//				var values = eval('(' + $xFormCtrl.attr('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
//				var outputParams = values.outputParams;
//				var inputParams = values.inputParams;
//				var funKey = values.funKey;
//				var source = values.source;
//				this.$domElement.attr('fd_type','massData').attr('outputParams',outputParams).attr('inputParams',inputParams).attr('funKey',funKey).attr('source',source)
//								.attr('excelcolumns',values.excelcolumns).addClass('xform_massData');
			}
			//后续控件...
		}
	} 
	//普通表单与field控件渲染
	function _formFieldRender(config){
		//debugger;
		// #126012 打印机制无法处理自定义的对象属性，需要从model属性中转换form属性
		if(config.data.type && config.data.type.indexOf('com.landray.kmss') > -1) {
			this.$domElement.attr("modelname", __modelName__).attr('isConvert', 'true');
		}
		//...
	}
	
	
	
	window.sysPrintDesignerFieldControl=fieldControl;
	window.sysPrintFieldPanel = fieldPanel;
	
	
})(window);