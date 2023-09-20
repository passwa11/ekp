/**
 * 业务建模的占位符
 */
(function(win){
    Com_IncludeFile("control.css",Com_Parameter.ContextPath+'sys/modeling/base/form/css/','css',true);
    win.Designer_Config.operations['filling'] = {
        lab : "5",
        imgIndex : 93,
        title : Designer_Lang.filling,
        run : function(designer) {
            designer.toolBar.selectButton('filling');
        },
        type : 'cmd',
        order: 1.6,
        isAdvanced: true,
        select : true,
        cursorImg : Com_Parameter.ContextPath + 'sys/modeling/base/form/cursor/filling.cur'
    };

    win.Designer_Config.controls.filling = {
        type : "filling",
        storeType : 'none',
        inherit : 'base',
        container : false,
        onDraw : draw,
        drawMobile : _Designer_Control_Filling_DrawMobile,
        drawXML : drawXML,
        implementDetailsTable : true,
        // 在新建流程文档的时候，是否显示
        hideInMainModel : true,
        attrs : {
            label : Designer_Config.attrs.label,
            bindDom : {
                text : Designer_Lang.filling_trigger_dom,
                value : '',
                required: true,
                width:"75%",
                height:"30px",
                type : 'comText',
                operate:'_FillingEventControl',
                show : true,
                validator: [Designer_Control_Attr_Required_Validator],
                checkout: [Designer_Control_Attr_Required_Checkout]
            },
            bindCount: {
                text: Designer_Lang.filling_trigger_count,
                show: true,
                required: true,
                type: 'text',
                value: '1',
                validator: Designer_Control_Attr_Int_Validator,
                checkout: Designer_Control_Attr_Int_Checkout
            },
            help:{
                text: Designer_Lang.filling_help_text,
                type: 'help',
                align:'left',
                show: true
            },
        },
        info : {
            name : Designer_Lang.filling
        },
        resizeMode : 'no'
    };
    win.Designer_Config.buttons.tool.push("filling");
    win.Designer_Menus.tool.menu['filling'] = Designer_Config.operations['filling'];

    // 呈现
    function draw(parentNode, childNode) {
        if (this.options.values.id == null){
            this.options.values.id = "fd_" + Designer.generateID();
        }
        //不自动与文本域绑定
        this.options.values._label_bind = false;
        if(!this.options.values.label){
            this.options.values.label = this.info.name + _Designer_Index_Object.label ++;
        }
        var domElement = _CreateDesignElement('div', this, parentNode, childNode);
        domElement.style.width='25px';
        domElement.style.height='30px';
        var values = this.options.values;
        var inputDom = document.createElement('input');
        inputDom.type='hidden';
        domElement.appendChild(inputDom);
        inputDom.id = this.options.values.id;

        if (values.bindDom) {
            $(inputDom).attr("bindDom",values.bindDom);
        }
        if (values.bindCount) {
            $(inputDom).attr("bindCount",values.bindCount);
        }
        var label = document.createElement("label");
        label.style.background = "url("+Com_Parameter.ContextPath + "sys/modeling/base/form/images/filling.png) no-repeat";
        label.style.margin = '0px 0px 0px 0px';
        label.style.display = 'inline-block';
        label.style.width='24px';
        label.style.height='24px';
        domElement.appendChild(label);
    }

    /**
     * 支持属性列表
     * @returns
     */
    function Designer_Control_Filling_getCanApplyFieldLayout () {
        var builder = Designer.instance.builder;
        var controls = builder.controls.sort(Designer.SortControl);
        var objs = [];
        _Designer_Control_Filling_getCanApplyFieldLayout(controls, objs);
        return objs;
    }

    function _Designer_Control_Filling_getCanApplyFieldLayout(controls, objs) {
        for (var i = 0, l = controls.length; i < l; i ++) {
            var control = controls[i];
            if (control.storeType == 'none' && control.type != "fieldlaylout")
                continue;
            if (control.storeType == 'layout' && control.type != "fieldlaylout") {
                _Designer_Control_Filling_getCanApplyFieldLayout(control.children.sort(Designer.SortControl), objs);
                continue;
            }
            var rowDom = Designer_GetObj_GetParentDom(function(parent) {
                return (Designer.checkTagName(parent, 'tr') && parent.getAttribute('type') == 'templateRow')
            }, control.options.domElement);

            var obj = {}, isTempRow = (rowDom != null && rowDom.getAttribute('type') == 'templateRow');
            if (control.type != "fieldlaylout"){
                continue;
            }
            if (control.options.values.__dict__) {
                var dict = control.options.values.__dict__;
                for (var di = 0; di < dict.length; di ++) {

                    var _dict = dict[di];
                    objs.push({
                        name: _dict.id,
                        label: _dict.label,
                        type: _dict.type,
                        controlType: control.type,
                        isTemplateRow: isTempRow
                    });
                }
            } else {
                obj.name = control.options.values.fieldIds;
                obj.label = Designer.HtmlUnEscape(control.options.values.label);
                obj.type = control.options.values.__type;
                if (control.options.values.__type == "Integer" ||
                    control.options.values.__type == "Double" ||
                    control.options.values.__type == "BigDecimal") {
                    obj.type = "BigDecimal";
                }
                var dataType = obj.type;
                obj.controlType = control.type;
                obj.isTemplateRow = isTempRow;
                objs.push(obj);
            }
        }
    }

    function _Designer_Attr_AddAll_Filling_Controls(controls, obj,expectId) {
        for (var i = 0, l = controls.length; i < l; i ++) {
            if("filling"==controls[i].type&&expectId != controls[i].options.values.id){
                var temp={};
                temp.name=controls[i].options.values.id;
                temp.label='填充控件('+controls[i].options.values.id+')';
                temp.type='String';
                temp.controlType='filling';
                obj.push(temp);
            }
            _Designer_Attr_AddAll_Filling_Controls(controls[i].children, obj,expectId);
        }
    }
    win._FillingEventControl=function (a,name){
        var c = Designer.instance.getObj(false);
        //_Designer_Attr_AddAll_Filling_Controls(Designer.instance.builder.controls,c,Designer.instance.attrPanel.panel.control.options.values.id);
        //属性列表
        // var fieldLayout = Designer_Control_Filling_getCanApplyFieldLayout();
        // c = fieldLayout.concat(c);
        var varInfo = [];
        var except=["attachment","filling","docimg"];
        for (var i = 0; i < c.length; i++) {
            var field = c[i];
            if(except.indexOf(field.controlType)>-1){
                continue;
            }
            if(field.name && field.name.endsWith("_text")){
                continue;
            }
            var info = $.extend({},field);
            varInfo.push(info);
        }
        RelatoinFormFieldChoose(document.getElementById(name),document.getElementById(name),function(rtn){
            if(rtn&&rtn.data&&rtn.data[0].id){
                document.getElementById(name).value=rtn.data[0].id;
            }
        },varInfo);
    }

    function RelatoinFormFieldChoose(idField,nameField, action,varInfo) {
        var dialog = new KMSSDialog();
        dialog.BindingField(idField, nameField);
        if(!varInfo){
            varInfo=Designer.instance.getObj(true);
        }
        dialog.Parameters = {
            varInfo : varInfo
        };
        dialog.SetAfterShow(
            function(rtn) {
                action(rtn);
            });
        dialog.URL = Com_Parameter.ContextPath
            + "sys/xform/designer/relation/relation_formfields_tree.jsp?t="+encodeURIComponent(new Date());
        dialog.Show(window.screen.width*460/1366,window.screen.height*480/768);

    }


    function drawXML() {
        var values = this.options.values;
        var buf = [];
        //配置的模块范围
        var customElementProperties = {};
        customElementProperties.bindDom = values.bindDom;
        customElementProperties.bindCount = values.bindCount;
        customElementProperties.bindEvent = "change";
        //配置需要存储的字段
        buf.push('<extendSimpleProperty ');
        buf.push('name="', values.id, '" ');
        buf.push('label="', values.label, '" ');
        buf.push('type="', 'String', '" ');
        buf.push('length="','20','" ');
        buf.push('canDisplay="','false','" ');
        buf.push('canSearch="','false','" ');
        buf.push('canRelation="','false','" ');
        buf.push('readOnly="','true','" ');
        buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
        // 控件类型
        buf.push('businessType="', this.type, '" ');
        buf.push('/>');
        return buf.join('');
    }
    //生成数据填充控件移动端对象
    function _Designer_Control_Filling_DrawMobile() {
        _Designer_Control_hiddenChildElems(this.options.domElement);
        var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement,"");
        $(domElement).attr("removeInPreview","true");
        $(domElement).append("<div class='mui_filling_img'></div>");
    }


})(window);

