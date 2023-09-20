/**
 * 
 */
Com_IncludeFile("spectrum.js",Com_Parameter.ContextPath+'resource/js/colorpicker/','js',true);
Com_IncludeFile("spectrum.css",Com_Parameter.ContextPath+'resource/js/colorpicker/css/','css',true);
Designer_AttrPanel.colorDraw = function(name, attr, value, form, attrs, values) {
	var buff = [];
	buff.push('<input type="text" disabled style="width:78%;border:solid 1px #000000;background-color:', value ? value : attr&&attr.value?attr.value:'#000','">');
	buff.push('<input type="hidden" name="', name, '" value="', value ? value : attr&&attr.value?attr.value:'#000','" >');
	buff.push('<input type="button" class="btnopt" value="..." onclick="Designer_AttrPanel.colorPanelOpen(event);Designer_AttrPanel.resetPosition(event);">');
	return Designer_AttrPanel.wrapTitle(name, attr, value, buff.join(''));
}
Designer_AttrPanel.colorPanel = null;
Designer_AttrPanel.colorPanelInit = function() {
	if (Designer_AttrPanel.colorPanel == null) {
		Designer_AttrPanel.colorPanel = new Designer_AttrPanel.Color_Panel();
	}
}

function Designer_OptionRun_OpenFontColor(designer, obj) {
	if (designer.control == null) {
		Designer_OptionRun_Alert("请选择要操作的文字（按住CTRL键可进行多选）！");
		return;
	}
	var mobileIFrame = getMobileIFrame();
	var ps = mobileIFrame.Designer.absPosition(obj.domElement);
	var color = designer.control.getColor ? designer.control.getColor() : false;
	Designer_AttrPanel.colorPanelInit();
	Designer_AttrPanel.colorPanel.open(
			(color ? color : '#000'),
			function(spectrumColor,args) {
				if(args && args.type == 'choose'){
					Designer_OptionRun_CallFunction(designer, function(control) {
						if (control.setColor) {
							control.setColor(spectrumColor.toHexString());
							return true;
						}
						return false;
					},config.lang.selectOpt);
				}
			},
			designer, 
			ps.x , ps.y
	);	
	
}

Designer_AttrPanel.colorPanelOpen = function(event) {
	var obj = event.target ? event.target : event.srcElement;
	var ps = Designer.absPosition(obj);
	Designer_AttrPanel.colorPanelInit();
	Designer_AttrPanel.colorPanel.open(
			obj.previousSibling.value,
			Designer_AttrPanel.colorPanelCallBack,
			obj.previousSibling, ps.x , ps.y + obj.offsetHeight
	);
}
Designer_AttrPanel.colorPanelClose = function() {
	if (Designer_AttrPanel.colorPanel) {
		Designer_AttrPanel.colorPanel.close();
	}
}
Designer_AttrPanel.colorPanelCallBack = function(spectrumColor,args) {
	if(args && args.type == 'choose'){
		var colorDom = this._arg;
		if (colorDom.form && colorDom.previousSibling){
			colorDom.value = spectrumColor.toHexString();
			colorDom.previousSibling.style.backgroundColor = spectrumColor.toHexString();
			Designer_AttrPanel.showButtons(colorDom);
		}
	}
}
//重置颜色面板的定位，目前再属性面板打开颜色面板时top定位不正确，进行重置
Designer_AttrPanel.resetPosition = function(event){
	var obj = event.target ? event.target : event.srcElement;
	var ps = Designer.absPosition(obj);
	Designer_AttrPanel.colorPanel.resetPosition(ps.x,ps.y + obj.offsetHeight);
}
Designer_AttrPanel.Color_Panel = function() {
	var self = this;
	self.domElement = document.createElement('div');
	document.body.appendChild(self.domElement);
	with(self.domElement.style) {
		position = 'fixed'; display='none';
	}
	self.spectrum = $(self.domElement).spectrum({
        clickoutFiresChange: false,
        showInput: true,
        className: "full-spectrum",
        showInitial: true,
        showPalette: true,
        showSelectionPalette: true,
        maxPaletteSize: 10,
        preferredFormat: "hex",
        localStorageKey: "spectrum.xform",
        move: function (color) {
        },
        show: function () {

        },
        beforeShow: function () {

        },
        hide: function (color,args) {
        	self.callback.call(self,color,args);
        },

        palette: [
            ["rgb(0, 0, 0)", "rgb(67, 67, 67)", "rgb(102, 102, 102)", /*"rgb(153, 153, 153)","rgb(183, 183, 183)",*/
            "rgb(204, 204, 204)", "rgb(217, 217, 217)", /*"rgb(239, 239, 239)", "rgb(243, 243, 243)",*/ "rgb(255, 255, 255)"],
            ["rgb(152, 0, 0)", "rgb(255, 0, 0)", "rgb(255, 153, 0)", "rgb(255, 255, 0)", "rgb(0, 255, 0)",
            "rgb(0, 255, 255)", "rgb(74, 134, 232)", "rgb(0, 0, 255)", "rgb(153, 0, 255)", "rgb(255, 0, 255)"],
            ["rgb(230, 184, 175)", "rgb(244, 204, 204)", "rgb(252, 229, 205)", "rgb(255, 242, 204)", "rgb(217, 234, 211)",
            "rgb(208, 224, 227)", "rgb(201, 218, 248)", "rgb(207, 226, 243)", "rgb(217, 210, 233)", "rgb(234, 209, 220)",
            "rgb(221, 126, 107)", "rgb(234, 153, 153)", "rgb(249, 203, 156)", "rgb(255, 229, 153)", "rgb(182, 215, 168)",
            "rgb(162, 196, 201)", "rgb(164, 194, 244)", "rgb(159, 197, 232)", "rgb(180, 167, 214)", "rgb(213, 166, 189)",
            "rgb(204, 65, 37)", "rgb(224, 102, 102)", "rgb(246, 178, 107)", "rgb(255, 217, 102)", "rgb(147, 196, 125)",
            "rgb(118, 165, 175)", "rgb(109, 158, 235)", "rgb(111, 168, 220)", "rgb(142, 124, 195)", "rgb(194, 123, 160)",
            "rgb(166, 28, 0)", "rgb(204, 0, 0)", "rgb(230, 145, 56)", "rgb(241, 194, 50)", "rgb(106, 168, 79)",
            "rgb(69, 129, 142)", "rgb(60, 120, 216)", "rgb(61, 133, 198)", "rgb(103, 78, 167)", "rgb(166, 77, 121)",
            /*"rgb(133, 32, 12)", "rgb(153, 0, 0)", "rgb(180, 95, 6)", "rgb(191, 144, 0)", "rgb(56, 118, 29)",
            "rgb(19, 79, 92)", "rgb(17, 85, 204)", "rgb(11, 83, 148)", "rgb(53, 28, 117)", "rgb(116, 27, 71)",*/
            "rgb(91, 15, 0)", "rgb(102, 0, 0)", "rgb(120, 63, 4)", "rgb(127, 96, 0)", "rgb(39, 78, 19)",
            "rgb(12, 52, 61)", "rgb(28, 69, 135)", "rgb(7, 55, 99)", "rgb(32, 18, 77)", "rgb(76, 17, 48)"]
        ]		    
	});
	self.spectrumContainer = $(self.domElement).spectrum('container')[0];
	self.spectrumContainer.style.position = "absolute";
	self.spectrumContainer.style['z-index'] = "200";

}
Designer_AttrPanel.Color_Panel.prototype = {
	canClose : true,
	callback : function(){},
	open: function(defValue, fn, arg, x, y) {
		var self = this;
		$(self.domElement).spectrum('set',defValue);
		// 定位
		$(self.domElement).spectrum('show');
		var _width = (self.spectrumContainer.offsetWidth||436);
		var _height = self.spectrumContainer.offsetHeight;
		var left = x - _width + 'px';
		var top = y + 28 + 'px';
		$(self.spectrumContainer).css({'left':left,'top':top});
		self.callback = fn;
		self._arg = arg;
	},
	resetPosition: function(x,y){
		var self = this;
		// 定位
		var _width = (self.spectrumContainer.offsetWidth||436);
		var _height = self.spectrumContainer.offsetHeight;
		var left = x - _width + 'px';
		var top = y + 28 + 'px';
		$(self.spectrumContainer).css({'left':left,'top':top});
	},
	close : function() {
		var self = this;
		$(self.domElement).spectrum('hide');
	}
}