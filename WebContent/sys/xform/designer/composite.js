/**
 * 复合组件
 */
(function(win){
	win.Designer_Config.operations['composite'] = {
		lab : "5",
		imgIndex : 73,
		title : Designer_Lang.composite,
		run : function(designer) {
			designer.toolBar.selectButton('composite');
		},
		type : 'cmd',
		order: 2.4,
		shortcut : '',
		isAdvanced: true,
		select : true,
		cursorImg : 'style/cursor/massdata.cur'
	};
	
	win.Designer_Config.controls.composite = {
		type : "composite",
		container : true,
		storeType : 'layout',
		inherit : 'base',
		onDraw : _Designer_Control_Composite_OnDraw,
		onDrawEnd : _Designer_Control_Composite_OnDrawEnd,
		drawMobile: _Designer_Control_CompositeControl_DrawMobile,
		drawXML : _Designer_Control_Composite_DrawXML,
        insertValidate: _Designer_Control_Composite_InsertValidate, //插入校验
		implementDetailsTable : true,
		attrs : {
			label : Designer_Config.attrs.label,
			help:{
				text: "帮助:</br>该控件用于把多个控件组合起来展示，目前仅支持移动端的动态下拉框和日期控件",
				type: 'help',
				align:'left',
				show: true
			}
		},
		info : {
			name : Designer_Lang.composite
		},
		resizeMode : 'no'
	};
	win.Designer_Config.buttons.tool.push("composite");
	win.Designer_Menus.tool.menu['composite'] = Designer_Config.operations['composite'];
	
	function _Designer_Control_Composite_OnDraw(parentNode, childNode){
		if(this.options.values.id == null){
			this.options.values.id = "fd_" + Designer.generateID();			
		}
		var domElement = _CreateDesignElement('div', this, parentNode, childNode);
		domElement.style.cssText = "width:100%;border:1px solid rgb(210, 210, 210)";
		var bar = document.createElement("div");
		bar.style.cssText="display:inline-block;background-color: #F5F5F5;border: 1px solid #DDDDDD;color: #9DA0A4;font-size: 12px;font-weight: bold;font-style:italic;padding: 2px 5px;height:15px;";
		bar.setAttribute("compositeBar","true");
		bar.innerHTML = Designer_Lang.composite;
		
		domElement.appendChild(bar);
	}
	
	function _Designer_Control_Composite_OnDrawEnd(){
		this.options.domElement.id = this.options.values.id;
		this.options.domElement.setAttribute("label", _Get_Designer_Control_Label(this.options.values, this));
	}
	
	function _Designer_Control_Composite_DrawXML(){
		if (this.children.length > 0) {
			var xmls = [];
			this.children = this.children.sort(Designer.SortControl);
			for (var i = 0, l = this.children.length; i < l; i ++) {
				var c = this.children[i];
				if (c.drawXML) {
					var xml = c.drawXML();
					if (xml != null)
						xmls.push(xml, '\r\n');
				}
			}
			return xmls.join('');
		}
		return '';
	}

    function _Designer_Control_Composite_InsertValidate(cell, control){
        //父控件
        var parentControl = this.parent;
        var detailsTable = Designer.getClosestDetailsTable(this);
        if(detailsTable){
            //如果父控件是明细表，则判断插入的控件是否支持明细表
            if(control && control.implementDetailsTable != true){
                if  (detailsTable.isAdvancedDetailsTable) {
                    alert(Designer_Lang.div_notSupportInsertToAdvancedListControl);
                } else {
                    alert(Designer_Lang.div_notSupportInsertToListControl);
                }
                return false;
            }
        }
        return true;
    }
	
})(window);