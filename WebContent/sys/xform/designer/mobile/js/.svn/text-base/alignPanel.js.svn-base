/**
 * 移动端配置,控件居左居右面板
 */
Designer_AttrPanel.alignPanelInit = function(designer) {
	if (Designer_AttrPanel.alignPanel == null) {
		Designer_AttrPanel.alignPanel = new Designer_AttrPanel.Align_Panel();
	}
	// 把当前控件的状态设置到相对元素里面
	Designer_AttrPanel.alignPanel.resetSetPanelInfo(designer);
}

Designer_AttrPanel.alignPanelOpen = function(event) {
	var obj = event.target ? event.target : event.srcElement;
	var ps = Designer.absPosition(obj);
	Designer_AttrPanel.alignPanelInit();
	Designer_AttrPanel.alignPanelPanel.open(
			Designer_AttrPanel.alignPanelCallBack,
			obj.previousSibling, ps.x , ps.y + obj.offsetHeight
	);
}

function Designer_Mobile_OpenAlignPanel(designer, obj) {
	var mobileDesigner = MobileDesigner.instance;
	var designer = mobileDesigner.getMobileDesigner();
	var $mobileCheckedTr = getMobileCheckedTr();
	if ($mobileCheckedTr.length == 0) {
		alert("请先选中移动端表单!");
	}
	var mobileIFrame = getMobileIFrame();
	var ps = mobileIFrame.Designer.absPosition(obj.domElement);
	Designer = mobileIFrame.Designer;
	Designer_AttrPanel.alignPanelInit(designer);
	if (Designer_AttrPanel.alignPanel.isClose) {
		Designer_AttrPanel.alignPanel.open(
				function() {},
				designer.mobileDesigner, 
				ps.x ,
				ps.y
		);	
	} else {
		Designer_AttrPanel.alignPanel.close();
	}
	
}

Designer_AttrPanel.alignPanelCallBack = function(){
	
}

function Designer_AttrPanel_AlignPanelClose() {
	if (Designer_AttrPanel.alignPanel) {
		Designer_AttrPanel.alignPanel.close();
	}
}
function Designer_AttrPanel_AlignPanelSubmit() {
	if (Designer_AttrPanel.alignPanel) {
		Designer_AttrPanel.alignPanel.submit();
	}
}

Designer_AttrPanel.Align_Panel = function() {
	var self = this;
	self.domElement = document.createElement('div');
	$(self.domElement).addClass("align container")
	document.body.appendChild(self.domElement);
	//创建内容
	var html = "";
	html += "<table class='panel'>";
	html += '<tr class="panel_tr"><td><span class="panel_title">类型: </span></td>' + 
	'<td><span class="radio"><label style="display:inline-block;margin-right:13px;"><input type="radio" name="alignType" value="one" checked>'+'单个'+'</input></label></span>' +
	'<span class="radio"><label><input type="radio" name="alignType" value="all">'+'所有'+'</input></label></span></td></tr>';
	html += '<tr class="panel_tr"><td><span class="panel_title">对齐方式: </span></td>' + 
		'<td><span class="radio"><label><input type="radio" name="mobileControlAlign" value="left" checked>'+'左对齐'+'</input></label></span>' +
		'<span class="radio"><label><input type="radio" name="mobileControlAlign" value="right">'+'右对齐'+'</label></input></span></td></tr>';
	html += '</table>';
	html += '<div class="btns" style="text-align:center;margin-top:5px">';
	html += '<a class="btn" href="javascript:void(0)" onclick="Designer_AttrPanel_AlignPanelSubmit()">'+Data_GetResourceString("sys-xform-base:autoformat.button.ok")+'</a>';
	html += '<a class="btn" href="javascript:void(0)" onclick="Designer_AttrPanel_AlignPanelClose()">'+Data_GetResourceString("sys-xform-base:autoformat.button.cancel")+'</a>';
	html += '</div>';
	$(self.domElement).html(html);
}

Designer_AttrPanel.Align_Panel.prototype = {
		isClose : true,
		callback : function(){},
		open: function(fn, arg, x, y) {
			var self = this;
			if(!self.isClose){
				return;
			}
			// 定位
			var width = $(self.domElement).width() + 40; //40为左右边距和
			var left = x - width + 'px';
			var top = y + 28 + 'px';
			$(self.domElement).css({'left':left,'top':top});
			$(self.domElement).css("display","inline-block");
			self.isClose = false;
			self.callback = fn;
			self._arg = arg;
		},
		submit:function(){
			var obj = {};
			var alignType = $("input[name='alignType']:checked").val();
			obj.alignType = alignType;
			var mobileControlAlign = $("input[name='mobileControlAlign']:checked").val();
			obj.mobileControlAlign = mobileControlAlign;
			//校验
			if(this.validate(obj)){
				this.setAlign(obj);
				//alert("设置成功!");
				this.close();
			}
			
		},
		validate:function(obj){
			return true;
		},
		setAlign : function(obj){
			Designer_Mobile_SetAlign(obj);
		},
		close : function() {
			var self = this;
			$(self.domElement).hide();
			this.isClose = true;
		},
		resetSetPanelInfo : function(designer){
			var alignType = "one";
			var align = "left";
			if(designer.control){
				align = designer.control.options.values["mobileAlign"] || "left";
			}
			$(this.domElement).find("[name='alignType'][value='"+ alignType +"']").prop("checked", true);
			$(this.domElement).find("[name='mobileControlAlign'][value='"+ align +"']").prop("checked", true);
		}
}

function Designer_Mobile_SetAlign(obj){
	var mobileDesigner = MobileDesigner.instance;
	var align = obj.mobileControlAlign || "left";
	var alignType = obj.alignType;
	var designer = mobileDesigner.getMobileDesigner();
	if (alignType == "one") {
		if(!designer.control || designer.controls.length == 0){
			alert("请先选择控件!");
			return;
		} else {
			if (designer.control.mobileAlign) {
				var domElement = designer.control.options.domElement;
				designer.control.options.values["mobileAlign"] = align;
				Designer_Mobile_SetElementAlign(domElement, align);
				designer.builder.serializeControl(designer.control);
			}
			return;
		}
	} else {
		var controls = getControls(designer);
		for (var i = 0; i < controls.length; i++) {
			var control = controls[i];
			if (control.mobileAlign) {
				var domElement = control.options.domElement;
				control.options.values["mobileAlign"] = align;
				Designer_Mobile_SetElementAlign(domElement, align);
				designer.builder.serializeControl(control);
			}
		}
	}
	var $mobileCheckedTr = getMobileCheckedTr();
	$mobileCheckedTr.find("input[name$='fdControlAlign']").val(align);
}

function Designer_Mobile_SetElementAlign(domElement, align){
	$(domElement).attr("mobileAlign",align);
	if (align == "right"){
		$(domElement).find("[mobileEle='true']").addClass("muiFormRight");
	} else {
		$(domElement).find("[mobileEle='true']").removeClass("muiFormRight");
	}
}
