/**
 * 明细表汇总控件
 *
 * @author HermiteZhang
 */
//-------------------- 控件定义
var t_value={
    "sum":Designer_Lang.detailSummary_calc_sum,
    "max":Designer_Lang.detailSummary_calc_max,
    "min":Designer_Lang.detailSummary_calc_min,
    "avg":Designer_Lang.detailSummary_calc_avg
};

//工具栏绘制
Designer_Config.operations['detailSummary'] = {
    lab: "5",
    imgIndex: 75,
    title: Designer_Lang.detailSummary_title,
    run: function (designer) {
        designer.toolBar.selectButton('detailSummary');
    },
    type: 'cmd',
    order: 9.4,
    isAdvanced: true,
    select: true,
    isShow: _Designer_Control_DetailSummary_IsShow,
    cursorImg: 'style/cursor/massdata.cur'
};
//明细表汇总控件定义
Designer_Config.controls.detailSummary = {
    type: "detailSummary",
    storeType: 'field',
    inherit: 'base',
    container: false,
    //绘制控件、xml、移动端
    onDraw: Designer_DetailSummary_Control_Draw,
    drawXML: Designer_DetailSummary_Control_Draw_Xml,
    drawMobile: Designer_DetailSummary_Control_Draw_Mobile,
    //是否支持明细表
    implementDetailsTable: true,
    //严格限制此控件在明细表内
    beforeDrawValidate: Designer_DetailSummary_Control_InsertValidate,
    //属性面板成功关闭时设置值
    onAttrSuccess: Designer_DetailSummary_Control_AttrSuccessAttr,
    onAttrApply: Designer_DetailSummary_Control_AttrSuccessAttr,
    mobileAlign: "right",
    //表单参数：
    attrs: {
        label: Designer_Config.attrs.label,
        //明细表映射
        detailPointer: {
            text: Designer_Lang.detailSummary_detailPointer,
            value: "",
            type: 'self',
            show: true,
            validator: Designer_Control_Attr_Required_Validator,
            checkout: Designer_Control_Attr_Required_Checkout,
            draw: Designer_DetailSummary_Control_Draw_DetailPointer,
            getVal: detailPointer_getVal
        },
        //关系映射
        relation: {
            text: Designer_Lang.detailSummary_relation,
            value: "",
            type: 'self',
            show: true,
            validator: Designer_Control_Attr_Required_Validator,
            checkout: Designer_Control_Attr_Required_Checkout,
            draw: Designer_DetailSummary_Control_Draw_Relation,
            getVal: relation_getVal,
            translator: relation_translator,
            compareChange: relation_compareChange
        },
        //字段汇总
        fieldCalc: {
            text: Designer_Lang.detailSummary_fieldCalc,
            value: "",
            type: 'self',
            show: true,
            validator: Designer_Control_Attr_Required_Validator,
            checkout: Designer_Control_Attr_Required_Checkout,
            draw: Designer_DetailSummary_Control_Draw_FieldCalc,
            getVal: fieldCalc_getVal,
            translator: fieldCalc_translator,
            compareChange: fieldCalc_compareChange
        },
        //计算方式：自动|0，手动|1
        autoCalc: {
            text: Designer_Lang.detailSummary_auto,
            value: "0",
            show: true,
            type: 'radio',
            opts: [{text: Designer_Lang.detailSummary_auto_0, value: "0"},
                {text: Designer_Lang.detailSummary_auto_1, value: "1"}],
        },
        //以下为表单控件基本参数
        isMark: {
            text: Designer_Lang.controlAttrIsMark,
            value: "false",
            type: 'checkbox',
            checked: false,
            show: true
        },
        summary: {
            text: Designer_Lang.controlAttrSummary,
            value: "false",
            type: 'checkbox',
            checked: false,
            show: true
        },
        encrypt: Designer_Config.attrs.encrypt,
        width: {
            text: Designer_Lang.controlAttrWidth,
            value: "",
            type: 'text',
            show: true,
            validator: Designer_Control_Attr_Width_Validator,
            checkout: Designer_Control_Attr_Width_Checkout
        },
        dataType: {
            text: Designer_Lang.controlAttrDataType,
            value: "Double",
            opts: [{text: Designer_Lang.controlAttrDataTypeNumber, value: "Double"},
                {text: Designer_Lang.controlAttrDataTypeBigNumber, value: "BigDecimal"},
                {text: Designer_Lang.controlAttrDataTypeMoney, value: "BigDecimal_Money"}],
            show: true,
            synchronous: true,
            type: 'select'
        },
        decimal: {
            text: Designer_Lang.controlInputTextAttrDecimal,
            show: false,
            validator: [Designer_Control_Attr_Required_Validator,
                Designer_Control_Attr_Int_Validator],
            checkout: Designer_Control_Attr_Int_Checkout
        },
        scale: {
            text: Designer_Lang.controlInputTextAttrDecimal,
            value: 0,
            show: true,
            type: 'text',
            validator: Designer_Control_Attr_Int_Validator,
            checkout: Designer_Control_Attr_Int_Checkout
        },
        thousandShow: {
            text: Designer_Lang.controlAttrThousandShow,
            value: "false",
            type: 'checkbox',
            checked: false,
            show: true
        },
        help: {
            text: Designer_Lang.detailSummary_help,
            type: 'help',
            align: 'left',
            show: true
        }
    },
    info: {
        name: Designer_Lang.detailSummary_title
    },
    resizeMode: 'no'
};
//注册
Designer_Config.buttons.form.push("detailSummary");
Designer_Menus.tool.menu['detailSummary'] = Designer_Config.operations['detailSummary'];

//--------------------  控件定义结束
//120419
function _Designer_Control_DetailSummary_IsShow(){
    if (Com_Parameter.dingXForm === "true") {
        return false;
    }
    return true;
}
//其他业务函数
function fieldCalc_compareChange(name, attr, oldValue, newValue) {
    var oldText = oldValue.field + "#" + oldValue.calc + "#" + oldValue.fieldCalc;
    var newText = newValue.field + "#" + newValue.calc + "#" + newValue.fieldCalc;
    if (oldText == newText) {
        return null;
    }
    var change = {
        status: 1,
        name: "fieldCalc",
        before: oldValue,
        after:newValue
    };
    return JSON.stringify(change);
}

function relation_compareChange(name, attr, oldValue, newValue) {
    var oldText = oldValue.pointer + "#" + oldValue.self;
    var newText = newValue.pointer + "#" + newValue.self;
    if (oldText == newText) {
        return null;
    }
    var change = {
        status: 1,
        name: "relation",
        before: oldValue,
        after: newValue
    };
    return JSON.stringify(change);
}


function fieldCalc_translator(change) {
    if (!change) {
        return "";
    }
    var change = JSON.parse(change);

    var html = [];
    var before = change.before;
    var after = change.after;
    html.push("<span>" + Designer_Lang.from);

    html.push("(" + before.fieldCalc + "#" + t_value[before.calc] + ")\&nbsp;\&nbsp;\&nbsp;" + Designer_Lang.to + "\&nbsp;\&nbsp;\&nbsp;(" + after.fieldCalc + "#" + t_value[after.calc] + ")");

    html.push("</span>");
    return html.join("");
}

function relation_translator(change) {
    if (!change) {
        return "";
    }
    var change = JSON.parse(change);
    var html = [];
    var before = change.before;
    var after = change.after;
    html.push("<span>" + Designer_Lang.from);
    html.push("(" + before.self + "#" + before.pointer + ")\&nbsp;\&nbsp;\&nbsp;" + Designer_Lang.to + "\&nbsp;\&nbsp;\&nbsp;(" + after.self + "#" + after.pointer + ")");
    html.push("</span>");
    return html.join("");
}

//工具类方法

/**
 * 明细表映射 取值
 * @param name
 * @param attr
 * @param value
 * @param controlValue
 */
function detailPointer_getVal(name, attr, value, controlValue) {
    controlValue[name] = getDetailTableLabel(value || "");
}

/**
 * 关系映射 取值
 * @param name
 * @param attr
 * @param value
 * @param controlValue
 */
function relation_getVal(name, attr, value, controlValue) {
    var val = value || "";
    val = val.replace(/quot;/g, "\"").replace(/'/g, "\"");
    if (val === "") {
        return;
    }
    val = JSON.parse(val);
    //关系映射-目标
    val.pointer = getLabelByObj(val.pointer);
    //关系映射-自身
    val.self = getLabelByObj(val.self);
    controlValue[name] = val;
}

/**
 * 字段计算 取值
 * @param name
 * @param attr
 * @param value
 * @param controlValue
 */
function fieldCalc_getVal(name, attr, value, controlValue) {
    var val = value || "";
    val = val.replace(/quot;/g, "\"").replace(/'/g, "\"")
    if (val === "") {
        return;
    }
    val = JSON.parse(val);
    val.fieldCalc = getLabelByObj(val.field);
    controlValue[name] = val;
}


/**
 * 【工具方法】 获取明细表的lable
 * @param tableId
 * @returns {*}
 */
function getDetailTableLabel(tableId) {
    var var1 = Designer.instance.builder.controls;
    for (var i = 0; i < var1.length; i++) {
        var type = var1[i].type;
        if (type == "standardTable") {
            var var2 = var1[i].children;
            for (var i2 = 0; i2 < var2.length; i2++) {
                var type2 = var2[i2].type;
                if (type2 == "standardTable") {
                    continue;
                }
                if (tableId != var2[i2].options.values.id) {
                    continue;
                }
                var tablelabel = var2[i2].options.values.label;
                return tablelabel;

            }
        }
    }
}

/**
 * 【工具方法】 获取明细表中组件的lable
 * @param obj
 * @returns {string}
 */
function getLabelByObj(obj) {
    if (obj && obj.indexOf(".") > -1) {
        return getLabel(obj.split(".")[0], obj.split(".")[1])
    } else {
        return ""
    }
}

/**
 * 【工具方法】 获取明细表中组件的lable
 * @param tableId
 * @param fieldId
 * @returns {string}
 */
function getLabel(tableId, fieldId) {
    var var1 = Designer.instance.builder.controls;
    for (var i = 0; i < var1.length; i++) {
        var type = var1[i].type;
        if (type == "standardTable") {
            var var2 = var1[i].children;
            for (var i2 = 0; i2 < var2.length; i2++) {
                var type2 = var2[i2].type;
                if (type2 == "standardTable") {
                    continue;
                }
                if (tableId != var2[i2].options.values.id) {
                    continue;
                }
                var tablelabel = var2[i2].options.values.label;
                var var3 = var2[i2].children;
                for (var i3 = 0; i3 < var3.length; i3++) {
                    var type3 = var3[i3].type;
                    var values = var3[i3].options.values;
                    var label = values.label;
                    var id = values.id;
                    if (id == fieldId) {
                        var fieldCalc = tablelabel + "." + label;
                        return fieldCalc
                    }

                }

            }
        }
    }
}


//-------------------- 面板
/**
 * 明细表映射值改变方法，赋值并重绘关系映射与字段汇总属性,绑定在select控件上
 * @param t
 * @constructor
 */
function Designer_DetailSummary_Control_Change_DetailPointer(t) {
    var control = Designer.instance.control;
    control.options.values.detailPointer = $(t).val();
    Designer_DetailSummary_Control_Draw_Relation("", "", "", "", "", "", control, $(t).val());
    Designer_DetailSummary_Control_Draw_FieldCalc("", "", "", "", "", "", control, $(t).val());
}

/**
 * 绘制明细表关系映射，列出该表除了自己以外的所有明细表，值改变时赋值
 * selected_id="DetailSummary_Ctr_DetailPointer_" + control._p.key;
 * @param name
 * @param attr
 * @param value
 * @param form
 * @param attrs
 * @param values
 * @param control
 * @returns {*}
 * @constructor
 */
function Designer_DetailSummary_Control_Draw_DetailPointer(name, attr, value,
                                                           form, attrs, values, control) {
    Designer_DetailSummary_Control_FUN_Init_P(control);
    var c = Designer_DetailSummary_Control_FUN_GetAllField(Designer.instance, function (ci) {
        return ci.name != control._p.pid && Designer.IsDetailsTableControlObj(ci);
    });
    //初始化值
    if (!value && c.length > 0) {
        value = c[0].name;
        control.options.values.detailPointer = c[0].name;
    }
    var id = "DetailSummary_Ctr_DetailPointer_" + control._p.key;
    //绑定下拉框值改变事件Designer_DetailSummary_Control_Change_DetailPointer，
    var html = Designer_DetailSummary_Control_FUN_DrawSelect(c, value, Designer_Lang.detailSummary_tips_detailPointer,
        " id='" + id + "' name=\"detailPointer\" onchange='Designer_DetailSummary_Control_Change_DetailPointer(this)'");
    return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

/**
 * 绘制关系映射，列出自己的明细表字段与“DetailPointer”所选择明细表字段
 * 提交时赋值，参数detailPointer不为空值可重绘
 * @param name
 * @param attr
 * @param value
 * @param form
 * @param attrs
 * @param values
 * @param control
 * @param detailPointer
 * @returns {*}
 * @constructor
 */
function Designer_DetailSummary_Control_Draw_Relation(name, attr, value,
                                                      form, attrs, values, control, detailPointer) {
    Designer_DetailSummary_Control_FUN_Init_P(control);
    var v_relation = {"self": undefined, "pointer": undefined};
    try {
        v_relation = value ? JSON.parse(value.replace(/'/g, "\"")) : v_relation;
    } catch (e) {
        console.warn("映射关系值解析失败", value, e)
    }
    var id = "DetailSummary_Ctr_Relation_" + control._p.key;
    var html = "<div style='margin: 5px 0px;' id='" + id + "'>";
    var cl = Designer_DetailSummary_Control_FUN_GetAllField(Designer.instance, function (ci) {
        //自己明细表的属性
        if (ci.name.indexOf(".") > -1) {
            var nameArr = ci.name.split(".");
            if (nameArr[0] === control._p.pid)
                return true;
        }
        return false;

    });

    var sl = Designer_DetailSummary_Control_FUN_DrawSelect(cl, v_relation.self,
        Designer_Lang.detailSummary_tips_nofield_0, " name='relation' detail_summary_ctr_relation='self' ");
    html += sl;
    html += "<div style='height: 3px; width: 100%'></div>";
    var cr = Designer_DetailSummary_Control_FUN_GetAllField(Designer.instance, function (ci) {
        //关联明细表的属性
        if (ci.name.indexOf(".") > -1) {
            var nameArr = ci.name.split(".");
            if (detailPointer) {
                //重绘指定
                if (nameArr[0] === detailPointer)
                    return true;
            } else {
                //初始化指定
                if (nameArr[0] == values.detailPointer)
                    return true;
            }
        }
        return false;
    });
    //初始化值
    if (!v_relation.pointer && cr.length > 0) {
        v_relation.pointer = cr[0].name;
        control.options.values.relation = JSON.stringify(v_relation).replace(/"/g, "'");
    }

    var sr = Designer_DetailSummary_Control_FUN_DrawSelect(cr, v_relation.pointer,
        Designer_Lang.detailSummary_tips_nofield_1, " name='relation' detail_summary_ctr_relation='pointer' ");
    html += sr;
    html += "</div>";
    if (detailPointer) {
        //重绘指定
        $("#"+id).html(html);
    } else {
        //初始化指定
        return Designer_AttrPanel.wrapTitle(name, attr, value, html);
    }
}

/**
 * 绘制字段，列出“DetailPointer”所选择明细表字段（仅数字，金额类型）
 * 提交时赋值，参数detailPointer不为空值可重绘
 * @param name
 * @param attr
 * @param value
 * @param form
 * @param attrs
 * @param values
 * @param control
 * @param detailPointer
 * @returns {*}
 * @constructor
 */
function Designer_DetailSummary_Control_Draw_FieldCalc(name, attr, value,
                                                       form, attrs, values, control, detailPointer) {
    Designer_DetailSummary_Control_FUN_Init_P(control);
    var v_fieldCalc = {"field": undefined, "calc": undefined};
    try {
        v_fieldCalc = value ? JSON.parse(value.replace(/'/g, "\"")) : v_fieldCalc;
    } catch (e) {
        console.warn("字段汇总值解析失败", value, e)
    }
    // if(!detailPointer)
    //     detailPointer = Designer_DetailSummary_Control_FUN_GetAtt(control,"detailPointer");
    var id = "DetailSummary_Ctr_FieldCalc_" + control._p.key;
    var html = "<div style='margin: 5px 0px;'  id='" + id + "'>";
    var typeAllow = ["BigDecimal_Money", "BigDecimal", "Double"];
    var cl = Designer_DetailSummary_Control_FUN_GetAllField(Designer.instance, function (ci) {
        //关联明细表的属性 (仅数字，金额)
        if (ci.name.indexOf(".") > -1) {
            var type = ci.type.replace("[", "").replace("]", "");
            if (typeAllow.indexOf(type) == -1) {
                return false;
            }
            var nameArr = ci.name.split(".");
            //关联明细表的属性
            if (detailPointer) {
                //重绘指定
                if (nameArr[0] === detailPointer)
                    return true;
            } else {
                //初始化指定
                if (nameArr[0] == values.detailPointer)
                    return true;
            }

        }
        return false;

    });
    //初始化值
    if (!v_fieldCalc.field && cl.length > 0) {
        v_fieldCalc.field = cl[0].name;
        control.options.values.fieldCalc = JSON.stringify(v_fieldCalc).replace(/"/g, "'");
    }
    var sl = Designer_DetailSummary_Control_FUN_DrawSelect(cl, v_fieldCalc.field,
        Designer_Lang.detailSummary_tips_nofield_1, " name='fieldCalc' detail_summary_ctr_fieldCalc='field' ");
    html += sl;
    html += "<div style='height: 3px; width: 100%'></div>";
    //计算方式
    var cr = [{"name": "sum", "label": Designer_Lang.detailSummary_calc_sum},
        {"name": "max", "label": Designer_Lang.detailSummary_calc_max},
        {"name": "min", "label": Designer_Lang.detailSummary_calc_min},
        {"name": "avg", "label": Designer_Lang.detailSummary_calc_avg}]
    //初始化值
    if (!v_fieldCalc.calc && cr.length > 0) {
        v_fieldCalc.calc = cr[0].name;
        control.options.values.fieldCalc = JSON.stringify(v_fieldCalc).replace(/"/g, "'");
    }
    var sr = Designer_DetailSummary_Control_FUN_DrawSelect(cr, v_fieldCalc.calc, "", " detail_summary_ctr_fieldCalc='calc' ");
    html += sr;
    html += "</div>";
    if (detailPointer) {
        //重绘指定
        $("#"+id).html(html);
    } else {
        //初始化指定
        return Designer_AttrPanel.wrapTitle(name, attr, value, html);
    }
}

/**
 * 【校验】，严格限制此控件在明细表内
 * @param parentNode
 * @param childNode
 * @returns {boolean}
 * @constructor
 */
function Designer_DetailSummary_Control_InsertValidate(parentNode, childNode) {
    //父控件判断
    if (Designer.IsDetailsTableControl(parentNode) || Designer_DetailSummary_Control_RightInsertValidate(parentNode)) {
        return true;
    }
    alert(Designer_Lang.detailSummary_tips_place);
    return false;
}

//汇总控件添加权限区段时，校验 严格限制此控件在明细表内
function Designer_DetailSummary_Control_RightInsertValidate(parentNode){
    return parentNode && parentNode.parent && parentNode.parent.isDetailsTable;
}

//-------------------- 控件生成方法
/**
 * 生成表单控件
 * @param parentNode 父节点
 * @param childNode 子节点
 * @constructor
 */
function Designer_DetailSummary_Control_Draw(parentNode, childNode) {
    if($(parentNode).parents("tr:first").attr("type")==="statisticRow"){
        //alert("汇总控件不能放在汇总行!");
        alert(Designer_Lang.detailSummary_tips_statisticRow);
        return;
    }
    if (!Designer_DetailSummary_Control_InsertValidate(this.parent,childNode)){
        //拖拽后若是非明细表内数据保留原来的数据
        if ( this._p.isInitialized == "initialized") {
            parentNode= this._p.parentNode ;
            childNode=this._p.childNode ;
            this.parent =  this._p.parent;
            this._p.isInitialized = "back"
        }else{
            return;
        }
    }
    Designer_DetailSummary_Control_FUN_Init_P(this);
    Designer_DetailSummary_Control_FUN_SetDefVal(this.options.values, this);
    if (this.options.values.id == null) {
        this.options.values.id = "fd_" + Designer.generateID();
    }
    var domElement = _CreateDesignElement('div', this, parentNode, childNode);
    domElement.id = this.options.values.id;
    domElement.style.display = "inline-block";
    var _length = 12;
    if (this.options.values.width) {
        if (this.options.values.width.toString().indexOf('%') > -1) {
            domElement.style.width = this.options.values.width;
        } else {
            //+5是为了防止设置必填的时候“*”号被换行
            domElement.style.width = (parseInt(this.options.values.width) + _length) + 'px';
        }
    } else {
        this.options.values.width = 166;
        domElement.style.width = (parseInt(this.options.values.width) + _length) + 'px';
    }

    //设置默认与左边文字域绑定
    domElement.label = _Get_Designer_Control_Label(this.options.values, this);
    var values = this.options.values;
    var inputDom = document.createElement('input');
    inputDom.className = "inputsgl";
    $(inputDom).attr("readonly", true);
    inputDom.style.width = ($(domElement).width() - 35) + "px";
    domElement.appendChild(inputDom);
    var a = document.createElement("a");
    a.innerText = Designer_Lang.detailSummary_calc;
    domElement.appendChild(a);
    if (this.options.values.required == "true") {
        $(domElement).append("<span class=txtstrong>*</span>");
    }

    //this._p.isInitialized = "initialized";
    this._p.parentNode = parentNode;
    this._p.childNode = childNode;
    this._p.parent =  this.parent;

}

/**
 * 生成框移动端dom对象
 * @returns {HTMLDivElement}
 * @constructor
 */
function Designer_DetailSummary_Control_Draw_Mobile() {
    _Designer_Control_ModifyWidth(this.options.domElement);
    _Designer_Control_hiddenChildElems(this.options.domElement);
    var domElement = _Designer_Control_CreateMobileWrapElement(this.options.domElement, "oldMui muiFormEleWrap");
    $(domElement).append("<div class='muiDetailSummary'></div>");
    $(domElement).append("<div class='muiSelInputRight'><span class='mui mui-forward'></span></div>");
    return domElement;
}

/**
 * 生成xml
 * @returns {string}
 * @constructor
 */
function Designer_DetailSummary_Control_Draw_Xml() {
    var values = this.options.values;
    var buf = [];
    //配置前端需要存储的字段
    buf.push('<extendSimpleProperty ');
    buf.push('name="', values.id, '" ');
    buf.push('label="', values.label, '" ');

    //是否留痕
    if (values.isMark == "true") {
        buf.push(' isMark="true" ');
    } else {
        buf.push(' isMark="false" ');
    }
    //摘要汇总
    if (values.summary == 'true') {
        buf.push(' summary="true" ');
    }
    // 字段是否需要加密
    if (values.encrypt == 'true') {
        buf.push('encrypt="true" ');
        buf.push('encryptionMethod="AES" ');
    }
    //只读
    if (values.readOnly && values.readOnly == 'true') {
        buf.push(' readOnly="true" ');
    }
    //数据类型
    buf.push('type="', values.dataType ? (values.dataType.indexOf('BigDecimal') > -1 ? 'BigDecimal' : values.dataType) : 'Double', '" ');
    //小数位
    buf.push('scale="', values.scale, '" ');
    //千分位
    if (values.thousandShow == 'false') {
        buf.push('thousandShow="', 'false', '" ');
    } else {
        buf.push('thousandShow="', 'true', '" ');
    }
    //其他属性
    if (!values.relation) {
        values.relation = "{'self':'','pointer':''}";
    }
    if (!values.fieldCalc) {
        values.fieldCalc = "{'field':'','calc':''}";
    }
    try {
        var customElementProperties = {
            "detailPointer": values.detailPointer,
            "relation": JSON.parse(values.relation.replace(/'/g, "\"")),
            "fieldCalc": JSON.parse(values.fieldCalc.replace(/'/g, "\"")),
            "autoCalc": values.autoCalc
        };
    } catch (e) {
        console.warn("Designer_DetailSummary_Control_Draw_Xml", e)
    }

    buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
    // 控件类型
    buf.push('businessType="', this.type, '" ');
    buf.push('/>');
    return buf.join('');
}

/**
 * 属性面板成功关闭时设置值
 * @constructor
 */
function Designer_DetailSummary_Control_AttrSuccessAttr() {
    this.options.values.relation = JSON.stringify(Designer_DetailSummary_Control_FUN_GetAtt(this, "relation")).replace(/"/g, "'");
    this.options.values.fieldCalc = JSON.stringify(Designer_DetailSummary_Control_FUN_GetAtt(this, "fieldCalc")).replace(/"/g, "'");
}

/**
 * 初始化一些全局变量参数，用与各控件间交互
 * _p:
 * 子节点：childNode: null
 * 父节点：parentNode: td
 * 父对象：parent
 * 控件状态:isInitialized: "ready"-初始化准备/"back"-移出到明细表外重绘/"initialized"-已初始化
 * 节点唯一标识符：key: "dsc_key39396105fcd7e4"
 * 父节点Id：pid: "fd_393928d00bab1e"
 * @param control or this 指向当前控件
 * @constructor
 *
 */
function Designer_DetailSummary_Control_FUN_Init_P(control) {
    if (!control._p) {
        control._p = {};
        var _key = "dsc_key" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16)
        control._p.key = _key;
        control._p.pid = control.parent.options.values.id;
        control._p.isInitialized = "ready";
        console.debug("control._p",control._p);
    }else{
        if ( control._p.isInitialized != "back"){
            //重绘不做处理
            control._p.pid = control.parent.options.values.id;
        }
    }


}

/**
 * 【工具方法】，获取当前表单所有字段，
 * @param _instance
 * @param filterFun 自定义筛选器
 * @returns {Array}
 * @constructor
 */
function Designer_DetailSummary_Control_FUN_GetAllField(_instance, filterFun) {
    var all_fields = [];
    var defFilter = ["inputText", "inputRadio", "select", "new_address", "datetime", "address", "detailsTable", "seniorDetailsTable"]
    var c = _instance.getObj(false);
    for (var i = 0; i < c.length; i++) {
        //默认过滤
        if (defFilter.indexOf(c[i].controlType) == -1) {
            continue
        }
        if (c[i].controlType.indexOf("address") > -1) {
            c[i].name = c[i].name + ".id";
        }
        //自定义过滤
        if (!filterFun || filterFun(c[i])) {
            all_fields.push(c[i])
        }
    }
    return all_fields;
}

/**
 * 【工具方法】，绘制select
 * @param optionArr
 * @param value
 * @param emptyTips
 * @param selectattr
 * @returns {string}
 * @constructor
 */
function Designer_DetailSummary_Control_FUN_DrawSelect(optionArr, value, emptyTips, selectattr) {
    var html = "";
    if (optionArr.length > 0) {
        if (!selectattr)
            selectattr = "";
        var select = "<select " + selectattr + " >";

        for (var i = 0; i < optionArr.length; i++) {
            if (!value) {
                value = optionArr[0].name
            }
            if (optionArr[i].name === value) {
                select += "<option value='" + optionArr[i].name + "' selected>" + optionArr[i].label + "</option>";
            } else {
                select += "<option value='" + optionArr[i].name + "'>" + optionArr[i].label + "</option>";
            }
        }
        select += "</select>";
        html = select;
    } else {
        var p = "<p style='color: #f00e5a'> <input type='hidden'" + selectattr + "> " + emptyTips + "</p>"
        html = p;
    }
    return html;
}

/**
 * 设置必填属性的初始化值
 * @param values
 * @param control
 * @constructor
 */
function Designer_DetailSummary_Control_FUN_SetDefVal(values, control) {
    //初始化值-detailPointer
    var allField = Designer_DetailSummary_Control_FUN_GetAllField(Designer.instance, function (ci) {
        return ci.name != control._p.pid && Designer.IsDetailsTableControlObj(ci);
    });
    //重绘需要重新计算
    var isRebuild =control._p.isInitialized == "initialized";
    if (isRebuild ||(!values.detailPointer && allField.length > 0)) {
        values.detailPointer = allField[0].name;
    } else {
        return;
    }
    //初始化值-relation
    if (isRebuild || !values.relation) {
        //自己明细表的属性
        var cl = Designer_DetailSummary_Control_FUN_GetAllField(Designer.instance, function (ci) {
            if (ci.name.indexOf(".") > -1) {
                var nameArr = ci.name.split(".");
                if (nameArr[0] === control._p.pid)
                    return true;
            }
            return false;
        });
        if (cl.length <= 0)
            return;
        var cr = Designer_DetailSummary_Control_FUN_GetAllField(Designer.instance, function (ci) {
            //关联明细表的属性
            if (ci.name.indexOf(".") > -1) {
                var nameArr = ci.name.split(".");
                return nameArr[0] === values.detailPointer
            }
            return false;
        });
        if (cr.length <= 0)
            return;
        var v_relation = {"self": cl[0].name, "pointer": cr[0].name};
        values.relation = JSON.stringify(v_relation).replace(/"/g, "'");
    }
    //初始化值-fieldCalc
    if (isRebuild || !values.fieldCalc) {
        var typeAllow = ["BigDecimal_Money", "BigDecimal", "Double"];
        var cl = Designer_DetailSummary_Control_FUN_GetAllField(Designer.instance, function (ci) {
            //关联明细表的属性 (仅数字，金额)
            if (ci.name.indexOf(".") > -1) {
                var type = ci.type.replace("[", "").replace("]", "");
                if (typeAllow.indexOf(type) == -1) {
                    return false;
                }
                var nameArr = ci.name.split(".");
                //关联明细表的属性
                return nameArr[0] === values.detailPointer;
            }
            return false;
        });
        var cr = [{"name": "sum", "label": Designer_Lang.detailSummary_calc_sum},
            {"name": "max", "label": Designer_Lang.detailSummary_calc_max},
            {"name": "min", "label": Designer_Lang.detailSummary_calc_min},
            {"name": "avg", "label": Designer_Lang.detailSummary_calc_avg}];
        var v_fieldCalc = {"field": null, "calc": cr[0].name};
        if (cl.length>0){
            v_fieldCalc.field=cl[0].name
        }
        values.fieldCalc = JSON.stringify(v_fieldCalc).replace(/"/g, "'");
    }
    //设置非必填属性的初始化值
    if (!values.autoCalc) {
        values.autoCalc = "0";
    }
    if (!values.isMark) {
        values.isMark = "false";
    }
    if (!values.summary) {
        values.summary = "false";
    }
    if (!values.dataType) {
        values.dataType = "Double";
    }
    if (!values.scale) {
        values.scale = 0;
    }
    if (!values.thousandShow) {
        values.thousandShow = "false";
    }

}

function Designer_DetailSummary_Control_FUN_GetAtt(control, attName) {
    Designer_DetailSummary_Control_FUN_Init_P(control);
    if (attName === "detailPointer") {
        return $("#DetailSummary_Ctr_DetailPointer_" + control._p.key).val();
    }
    if (attName === "relation") {
        var $att = $("#DetailSummary_Ctr_Relation_" + control._p.key);
        return {
            'self': $att.find("[detail_summary_ctr_relation='self']").val(),
            'pointer': $att.find("[detail_summary_ctr_relation='pointer']").val()
        }
    }
    if (attName === "fieldCalc") {
        var $att = $("#DetailSummary_Ctr_FieldCalc_" + control._p.key);
        return {
            'field': $att.find("[detail_summary_ctr_fieldCalc='field']").val(),
            'calc': $att.find("[detail_summary_ctr_fieldCalc='calc']").val()
        }
    }

}
