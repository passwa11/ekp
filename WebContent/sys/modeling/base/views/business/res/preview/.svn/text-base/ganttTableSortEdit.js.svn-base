/**
 * 内数据的数据信息 start
 */
function Insystem_Context(){

    this.dictData = [];

    // 权限标志
    this.auth = {};

    // 业务过滤的集合
    this.filterDictData = [];

    // 字符串的集合
    this.strDictData = [];

    this.idProperty = null;

    this.nameProperty = null;

    this.modelName = null;

    // 内部
    this._init = _Insystem__Init;

    // 共用
    this.initialize = Insystem_Initialize;
    this.clear = Insystem_Clear;
    this.hasDictData = Insystem_HasDictData;

    this._init();
}

function _Insystem__Init(){

}

function Insystem_Initialize(dictData){
    if(dictData){
        for(var i = 0;i < dictData.length;i++){
            var pro = new Insystem_Property();
            var data = dictData[i];
            pro.initialize(data);
            if(data.isIdProperty && data.isIdProperty == 'true'){
                this.idProperty = pro;
            }
            if(data.isNameProperty && data.isNameProperty == 'true' && this.idProperty != pro){
                this.nameProperty = pro;
            }
            if(!Insystem_FilterPro(pro)){
                this.filterDictData.push(pro);
            }
            if(data.fieldType.toLowerCase() == "string"){
                this.strDictData.push(pro);
            }
            this.dictData.push(pro);
        }
    }
    return this;
}

function Insystem_Clear(){
    this.dictData = [];
    // isAuth
    this.auth = {};
    this.filterDictData = [];
    this.strDictData = [];
    this.modelName = null;
    this.idProperty = null;
    this.nameProperty = null;
}

// 是否含有数据
function Insystem_HasDictData(){
    var flag = true;
    if(this.dictData.length == 0){
        flag = false;
    }
    return flag;
}

function Insystem_Property_IsModel(type){
    var flag = false;
    if(type.indexOf("com.landray.kmss") > -1){
        flag = true;
    }
    return flag;
}

// 业务过滤
function Insystem_FilterPro(pro){
    var flag = false;
    /**************列表对象属性过滤******************/
    if(pro.isListProperty){
        flag = true;
    }
    return flag;
}

function Insystem_Property(){
    this.field = '';
    this.fieldType = '';
    this.message = '';
    this.isModel = false;
    this.enumType = '';
    this.enumValues = [];
    this.isListProperty = false;
    this.parent = null;
    this.isIdProperty = null;
    this.orgType = null;
    this.businessType = null;

    this.initialize = Insystem_Property_Initialize;
}

function Insystem_Property_Initialize(data){
    this.field = data.field;
    this.fieldType = data.fieldType;
    this.message = data.fieldText;
    this.isModel = Insystem_Property_IsModel(this.fieldType);
    if(data.enumType != null){
        this.enumType = data.enumType;
        this.enumValues = data.enumValues;
    }
    if(data.isListProperty == 'true'){
        this.isListProperty = true;
    }
    if(data.isIdProperty){
        this.isIdProperty = data.isIdProperty;
    }
    if(data.orgType){
        this.orgType = data.orgType;
    }
    if(data.isNotNull){
        this.isNotNull = data.isNotNull;
    }
    if(data.isMutiValue){
        this.isMutiValue = data.isMutiValue;
    }
    if(data.isSubTableField){
        this.isSubTableField = data.isSubTableField;
    }
    if(data.businessType){
        this.businessType = data.businessType;
    }
}
/**
 * 内数据的数据信息 end
 */

var insystemContext = new Insystem_Context();
var _xform_main_data_insystem_split = "|";
Com_AddEventListener(window, 'load', xform_main_data_init);
//初始化数据，主要用于edit编辑页面
function xform_main_data_init(){
    //触发业务模块的下拉框变动事件
    // $("select[name='fdModelId']").each(function() {
    //     modelChange(this.value, this, true);
    // });
    //初始化数据字典变量
    var dictData = ganttOption.modelingGanttForm.modelDict;
    dictData = dictData.replace(/&quot;/g,"\"");
    if(dictData){
        var dictJSON = $.parseJSON(getValidJSONArray(dictData));
        insystemContext.clear();
        insystemContext.initialize(dictJSON);
        // xform_main_data_setGlobal(dictJSON);
        //处理预定义查询
        // var whereData = ganttOption.modelingGanttForm.fdWhereBlock;
        // var whereDataJsonArray = $.parseJSON(getValidJSONArray(whereData));
        // //遍历预定义查询数据
        // // 把查询条件按类型做分组
        // // 按照分组调用新增行方法
        // var predefineArr = [];
        // var sysArr = [];
        // var $predefineTable = $(".model-query-content-cust").find(".model-edit-view-oper-content-table");
        // var $sysTable = $(".model-query-content-sys").find(".model-edit-view-oper-content-table");
        // for(var i = 0;i < whereDataJsonArray.length;i++){
        //     if($.isEmptyObject(whereDataJsonArray[i])){
        //         continue;
        //     }
        //     var whereType = whereDataJsonArray[i].whereType;
        //     if(whereType == '0'){
        //         predefineArr.push(whereDataJsonArray[i]);
        //     }else if(whereType == '1'){
        //         sysArr.push(whereDataJsonArray[i]);
        //     }
        //     //xform_main_data_addWhereItem(whereDataJsonArray[i], true);
        // }
        // for(var i = 0;i < predefineArr.length;i++){
        //     xform_main_data_addWhereItem(predefineArr[i], $($predefineTable),'0');
        // }
        //
        // for(var i = 0;i < sysArr.length;i++){
        //     xform_main_data_addWhereItem(sysArr[i], $($sysTable),'1');
        // }
        // //处理排序设置
        // var orderbyData = listviewOption.modelingAppListviewForm.fdOrderBy;
        // var orderbyDataJsonArray = $.parseJSON(getValidJSONArray(orderbyData));
        // for(var i = 0;i < orderbyDataJsonArray.length;i++){
        //     if($.isEmptyObject(orderbyDataJsonArray[i])){
        //         continue;
        //     }
        //     xform_main_data_addOrderbyItem(orderbyDataJsonArray[i], true);
        // }
    }
    // xform_main_data_custom_enumChangeEvent("xform_main_data_returnValueTable");
    // xform_main_data_custom_enumChangeEvent("xform_main_data_searchTable");
}

function getValidJSONArray(arr){
    /*if(!arr || !arr.startsWith("[") || !arr.endsWith("]")){
        return "[]";
    }*/
    var startStr = "\\[";
    var endStr = "\\]";
    var startReg =new RegExp("^"+startStr);
    var endReg = new RegExp(endStr+"$");
    if(!arr || !startReg.test(arr) || !endReg.test(arr)){
        return "[]";
    }
    return arr;
}


var modelingLang;
seajs.use([ 'sys/ui/js/dialog' ,'lui/topic','lang!sys-modeling-base'],function(dialog,topic,lang) {
    modelingLang=lang;
    //增加排序设置
    window.xform_main_data_addOrderbyItem = function(selectedItem){
        if(!insystemContext.hasDictData()){
            alert(ganttOption.lang.chooseModuleFirst);
            return;
        }
        var dictData = window.filterSubTableField(insystemContext.filterDictData);
        dictData = window.filterOrderByField(dictData);
        var $selectTable = $("#xform_main_data_orderbyTable");
        var rowIndex = $selectTable.find("tr.orderbyTr").length+1;
        var trObj = $("<tr class='orderbyTr'>");
        var html = "";
        html += "<td><div class='model-edit-view-oper' data-lui-position='fdOrderBy-"+(rowIndex-1)+"' onclick=switchSelectPosition(this,'right')>";
        //头部
        html += "<div class='model-edit-view-oper-head'>";
        html += "<div class='model-edit-view-oper-head-title'><div onclick='changeToOpenOrClose(this)'><i class='open'></i></div><span>"+ganttOption.lang.sortItem+"<span class='title-index'>"+(rowIndex-1)+"</span></span></div>";
        html += "<div class='model-edit-view-oper-head-item'><div class='del' onclick='updateRowAttr(0,null,this);xform_main_data_delTrItem(this);updateRowAttr()'><i></i></div><div class='down' onclick='xform_main_data_moveTr_new(1,this);updateRowAttr(1,null,this)'><i></i></div><div class='up' onclick='xform_main_data_moveTr_new(-1,this);updateRowAttr(-1,null,this)'><i></i></div></div>";
        html += "</div>";
        //内容
        html += "<div class='model-edit-view-oper-content'>";
        html += "<ul>";
        //字段
        html += "<li class='model-edit-view-oper-content-item first-item'><div class='item-title'>"+ganttOption.lang.field+"</div>";
        html += "<div class='item-content'>";
        var field = selectedItem == null ? null : selectedItem.field;
        if (field && field.indexOf("|") !== -1) {
            field = field.substring(0, field.indexOf("|"));
        }
        html += window.xform_main_data_getFieldOptionHtml(dictData,'fdAttrField', 'true', field,selectedItem == null ? null : selectedItem,true);
        html += "</div></li>";
        //排序
        var ascSelected;
        var descSelected;
        if(selectedItem && selectedItem.hasOwnProperty('orderType') && selectedItem.orderType == 'desc'){
            descSelected = "selected";
        }else{
            ascSelected = "selected";
        }
        html += "<li class='model-edit-view-oper-content-item last-item'><div class='item-title'>"+ganttOption.lang.fdOutSort+"</div>";
        html += "<div class='item-content'><select name='fdOrderType' type='checkbox' class='inputsgl' style='margin:0 4px;width:100%'>";
        html += "<option value='asc' " + ascSelected + ">" + ganttOption.lang.asc + "</option>";
        html += "<option value='desc'" + descSelected + ">" + ganttOption.lang.desc + "</option>";
        html += "</select></div></li>";
        html += "</ul>";
        html += "</div></div></td>";
        trObj.append(html);
        $selectTable.append(trObj);
        //更新角标
        var index = $selectTable.find("> tbody > tr").last().find(".title-index").text();
        $selectTable.find("> tbody > tr").last().find(".title-index").text(parseInt(index)+1);
        //更新向下的图标
        $selectTable.find("> tbody > tr").last().prev("tr").find("div.down").show();
        $(trObj).find("div.down").hide();
        //修改默认标题
        var fdAttrFieldId = $(trObj).find("[name='fdAttrField']").val();
        var fdAttrFieldText = $(trObj).find("[name='fdAttrField']").find("option[value='"+fdAttrFieldId+"']").text();
        $selectTable.find(">tbody>tr").eq(index).find(".model-edit-view-oper-head-title span").html(fdAttrFieldText);
        //刷新预览
        topic.publish("preview.refresh");
        //渲染之后出发点击
        $(trObj).find(".model-edit-view-oper").click();
    }
    /*过滤排序的字段*/
    window.filterOrderByField = function(fields){
        var allField = fields || [];
        if(typeof allField === 'string'){
            allField = $.parseJSON(fields);
        }
        var newAllField = [];
        for(var i=0; i<allField.length; i++){
            var field = allField[i];
            //组织架构字段过滤（不包含创建者）
            if(field.fieldType && field.fieldType.indexOf("com.landray.kmss.sys.organization.model") !== -1 && field.field !== "docCreator") {
                //只可选择不为空的字段
                if(field.isNotNull === undefined || field.isNotNull === null || field.isNotNull === false){
                    console.log("非必填：");
                    console.log(field);
                    continue;
                }
                //不可选择多选的字段
                if(field.isMutiValue !== undefined && field.isMutiValue != null && field.isMutiValue === true){
                    console.log("多选：");
                    console.log(field);
                    continue;
                }
            }
            newAllField.push(field);
        }
        return newAllField;
    }
    /*过滤明细表的字段*/
    window.filterSubTableField =  function(fields){
        var allField = fields || [];
        if(typeof allField === 'string'){
            allField = $.parseJSON(fields);
        }
        var newAllField = [];
        for(var i=0; i<allField.length; i++){
            var field = allField[i];
            if(field.isSubTableField){
                continue;
            }
            newAllField.push(field);
        }
        return newAllField;
    }

    //获取属性列表，返回下拉菜单
    window.xform_main_data_getFieldOptionHtml =  function(dataArray,fdName,noOnchangeEvent,value,fieldJSON,nonSubField){
        dataArray = window.filterSubTableField(dataArray);
        var html = "";
        var selectValue = value;
        //属性可能是对象类型，数据含有|即为对象类型
        if(selectValue != null && selectValue.indexOf(_xform_main_data_insystem_split) > -1){
            // 重新编辑
            // 属性对象，用于存储上下文，主要用于列表对象
            function MainDataFields(){
                this.childrent = [];
            }
            var currentModelName = $("input[name='fdModelName']").val();
            var fieldType = currentModelName + _xform_main_data_insystem_split + fieldJSON.fieldType;// 加上当前modelname
            var selectArray = selectValue.split(_xform_main_data_insystem_split);
            var fieldTypeArray = fieldType.split(_xform_main_data_insystem_split);
            var mainDataFields = new MainDataFields();
            for(var i = 0;i < selectArray.length;i++){
                /********************* 构建临时对象 start *********************/
                var mainDataField = new Insystem_Property();
                mainDataField.fieldName = selectArray[i];
                mainDataField.fieldType = fieldTypeArray[i];
                mainDataFields.childrent.push(mainDataField);
                if(i != 0){
                    mainDataField.parent = mainDataFields.childrent[i-1];
                }
                /********************* 构建临时对象 end *********************/
                    //一个个属性遍历，通过ajax查询返回对象的数据字典
                var params = {'modelName' : fieldTypeArray[i],'isListProperty':'false'};
                // 上一个属性是否是列表属性对象，如果是则做一些业务处理
                if(mainDataField.parent && mainDataField.parent.isListProperty == true){
                    params.isListProperty = 'true';
                }
                xform_main_data_getDictAttrByModelName(params,function(data){
                    if(data){
                        var datas = $.parseJSON(data);
                        var arr = [];
                        // 封装数组
                        for(var j = 0;j < datas.length;j++){
                            var pro = new Insystem_Property();
                            var data = datas[j];
                            pro.initialize(data);
                            /********************* 构建临时对象 start *********************/
                            if(data.field == selectArray[i] && pro.isListProperty){
                                mainDataField.isListProperty = true;
                            }
                            /********************* 构建临时对象 end *********************/
                            arr.push(pro);
                        }
                        html += window.xform_main_data_getFieldOptionHtml(arr,fdName,null,selectArray[i]);
                        return html;
                    }
                });
            }
        }else{
            if(!nonSubField) nonSubField=false;
            if(noOnchangeEvent != null && noOnchangeEvent == 'true'){
                html += "<select name='" + fdName + "' onchange='xform_main_data_trrigleFieldAttr(this,false," + nonSubField + ");' class='inputsgl floatLeft' style='margin:0 4px;width:100%;'>";
            }else{
                html += "<select name='" + fdName + "' onchange='xform_main_data_trrigleFieldAttr(this,true," + nonSubField + ");' class='inputsgl floatLeft' style='margin:0 4px; width:100%'>";
            }

            for(var i = 0;i < dataArray.length;i++){
                var data = dataArray[i];
                html += "<option value='" + data.field + "' data-type='" + data.fieldType + "'";
                // 选中已选有的值
                if(selectValue != null && selectValue == data.field){
                    html += " selected='selected'";
                }
                /************** 业务参数 start****************/
                // 添加枚举值
                if(data.fieldType == 'enum'){
                    html += " data-enumtype='" + data.enumType + "'";
                }
                // 是否是列表对象属性
                if(data.isListProperty){
                    html += " data-isListProperty='true'";
                }
                // 是否是ID属性
                if(data.isIdProperty){
                    html += " data-isIdProperty='true'";
                }
                // 组织架构的类型
                if(data.orgType){
                    html += " data-orgType='" + data.orgType + "'";
                }else{
                    if(data.fieldType == "com.landray.kmss.sys.organization.model.SysOrgElement"){
                        html += " data-orgType='ORG_TYPE_ORG|ORG_TYPE_DEPT|ORG_TYPE_POST'";
                    }
                }
                /*************** end ***************/
                html += ">" + data.message + "</option>";
            }
            html += "</select>"
        }
        return html;
    }
    //属性列表值改变
    //changeEvent true|false ，是否需要改变其他dom
    window.xform_main_data_trrigleFieldAttr = function(dom , changeEvent, nonSubField){
        var selectDom = dom;
        var type = $(selectDom).find("option:selected").attr('data-type');
        var field = $(selectDom).find("option:selected").val();
        var typeChange = changeEvent;
        //刷新预览
        topic.publish("preview.refresh");
        // 如果属性是对象类型
        if(type.indexOf("com.landray.kmss") > -1 && !nonSubField){
            //删除当前元素后面的同级元素
            $(selectDom).nextAll().remove();
            var params = {'modelName' : type,'isListProperty':'false'};
            var isListProperty = $(selectDom).find("option:selected").attr('data-isListProperty');
            if(isListProperty && isListProperty == 'true'){
                params.isListProperty = 'true';
            }
            xform_main_data_getDictAttrByModelName(params,function(data){
                if(data){
                    var datas = $.parseJSON(data);
                    var arr = [];
                    // 封装数组
                    for(var j = 0;j < datas.length;j++){
                        var pro = new Insystem_Property();
                        var data = datas[j];
                        pro.initialize(data);
                        arr.push(pro);
                    }
                    var selectHtml = xform_main_data_getFieldOptionHtml(arr,"fdAttrField");
                    $(selectDom).after(selectHtml);
                    typeChange = false;
                    //$(selectHtml).change();
                    //不能用上面这种方式触发，因为此时的$(selectHtml)并不存在于dom结构里面，会导致下面的获取不到父元素
                    $(selectDom).next().change();
                }
            });
        }else{
            //删除当前元素后面的同级元素
            $(selectDom).nextAll().remove();
        }
        if(typeChange){
            //当前选的是ID
            var isIdProperty = $(selectDom).find("option:selected").attr('data-isIdProperty');
            if(isIdProperty){
                //这个ID属于组织架构 人员=ORG_TYPE_PERSON 部门=ORG_TYPE_ORG|ORG_TYPE_DEPT
                var orgType = getFieldOrgType(selectDom);
                if(orgType) {
                    //部门类型
                    if (orgType === "ORG_TYPE_ORG|ORG_TYPE_DEPT") {
                        //运算符位置显示'包含子部门'选项
                        $(selectDom).closest("tr").find(".xform_main_data_operatorWrap").html('<label><input type="checkbox" name="isIncludeSub" value="true"/>包含子部门</label>');
                    } else {
                        //其它组织架构类型
                        //运算符只显示'等于'
                        var operatorArray = xform_main_data_getOperatorByType(type.toLowerCase());
                        operatorArray = [operatorArray[0],operatorArray[1]];
                        $(selectDom).closest("tr").find(".xform_main_data_operatorWrap").html(xform_main_data_buildOperatorSelectHtml(operatorArray));
                    }
                    xform_main_data_changeFieldValue(selectDom);
                    return;
                }
            }
            //改变运算符
            var operatorArray = xform_main_data_getOperatorByType(type.toLowerCase());
            $(selectDom).closest("tr").find(".xform_main_data_operatorWrap").html(xform_main_data_buildOperatorSelectHtml(operatorArray,null,type));
            //运算符title
            $(selectDom).closest("tr").find(".xform_main_data_operatorWrap").parent().prev().html(ganttOption.lang.fdOperator);
            //改变值展示方式
            xform_main_data_changeFieldValue(selectDom);
        }
        //更新标题
        changeUlTitle($(selectDom).closest('li').find('select[name="fdAttrField"]'));
    }

    //业务操作弹框
    window.xform_main_data_operationDialog = function(){
        var index = getElemIndex();
        //业务操作弹框回调
        var action = function(rtnData){
            var listOperationIdLasts = $("[name='listOperationIds_last']").val() || "";
            var listOperationNameLasts = $("[name='listOperationNames_last']").val() || "";
            var oprIdArr = listOperationIdLasts.split(";");
            var oprNameArr = listOperationNameLasts.split(";");
            var selectedOprLastIndex = oprIdArr.length - 1;
            var fdId = rtnData[0].fdId;
            var fdName = rtnData[0].fdName;
            var fdOperationScenario =rtnData[0].fdOperationScenario;
            var fdDefType = rtnData[0].fdDefType;
            if(fdId && listOperationIdLasts.indexOf(fdId) == -1 ){
                if(index > selectedOprLastIndex){
                    if(listOperationIdLasts.lastIndexOf(";") != listOperationIdLasts.length-1){
                        listOperationIdLasts += ";" + fdId + ";";
                        listOperationNameLasts += ";" + fdName + ";";
                    }else{
                        listOperationIdLasts += fdId + ";";
                        listOperationNameLasts += fdName + ";";
                    }
                }else{
                    oprIdArr[index] = fdId;
                    oprNameArr[index] = fdName;
                    listOperationIdLasts = oprIdArr.join(";");
                    listOperationNameLasts = oprNameArr.join(";");
                }
                //处理显示内容
                $("p.listOperationName").eq(index).text(fdName);
                $("[name='listOperationIdArr["+index+"]']").val(fdId);
                $("[name='listOperationNameArr["+index+"]']").val(fdName);

                //显示机制开启提示
                tipsShow(index,fdDefType, fdOperationScenario);
            }
            $("[name='listOperationIds']").val(listOperationIdLasts);
            $("[name='listOperationNames']").val(listOperationNameLasts);
            $("[name='listOperationIds_last']").val(listOperationIdLasts);
            $("[name='listOperationNames_last']").val(listOperationNameLasts);
            //更新标题
            $("#operationTable").find(">tbody>tr").eq(index).find(".model-edit-view-oper-head-title span").html(fdName);
            //刷新预览
            topic.publish("preview.refresh");
        }
        var exceptValue = $("[name='listOperationIds_last']").val() || "";
        var currentValue = $("[name='listOperationIdArr["+index+"]']").val();
        if(currentValue){
            exceptValue = exceptValue.replace(currentValue+";","");
            exceptValue = exceptValue.replace(currentValue,"");
        }
        dialogSelect(false,'sys_modeling_operation_selectListviewOperation','listOperationIds','listOperationNames', action, exceptValue);
    }

});

Com_AddEventListener(window, 'load', xform_main_data_initVar);



//显示开启机制提示信息
function tipsShow(rowIndex,fdDefType,fdOperationScenario){
    //fdDefType:8-打印、 9-导入、 10-批量打印、  11-归档  支付场景: fdDefType = null fdOperationScenario-1
    if("8"===fdDefType|| "9" ===fdDefType|| "10"===fdDefType ||"11"===fdDefType ||
        ((fdDefType == null || fdDefType == undefined || fdDefType == "") && "1" === fdOperationScenario)){
        this.tips = $("#listOperationTipsArr\\["+rowIndex+"\\]");
        this.divTips = $("<div class='operateTips' />");
        var html =  modelingLang['modeling.model.mechanism.tips'];
        this.divTips.html(html);
        this.tips.html(this.divTips);
    }else {
        //如果存在旧的tips，则删除掉
        this.tips = $("#listOperationTipsArr\\["+rowIndex+"\\]");
        if(this.tips){
            this.tips.html("");
        }
    }
}

//初始化数据，主要用于edit编辑页面
function xform_main_data_initVar(){
    //触发业务模块的下拉框变动事件
   /* $("select[name='fdModelId']").each(function() {
        modelChange(this.value, this, true);
    });*/

    //初始化数据字典变量
    var dictData = ganttOption.modelingGanttForm.modelDict;
    dictData = dictData.replace(/&quot;/g,"\"");
    if(dictData){
        var dictJSON = $.parseJSON(getValidJSONArray(dictData));
        xform_main_data_setGlobal(dictJSON);
        //处理排序设置
        var orderbyData = ganttOption.modelingGanttForm.fdOrderBy;
        var orderbyDataJsonArray = $.parseJSON(getValidJSONArray(orderbyData));
        for(var i = 0;i < orderbyDataJsonArray.length;i++){
            if($.isEmptyObject(orderbyDataJsonArray[i])){
                continue;
            }
            xform_main_data_addOrderbyItem(orderbyDataJsonArray[i], true);
        }
    }
    // xform_main_data_custom_enumChangeEvent("xform_main_data_returnValueTable");
    // xform_main_data_custom_enumChangeEvent("xform_main_data_searchTable");
}

//设置权限js变量的数据字典变量和权限变量
function xform_main_data_setGlobal(data){
    insystemContext.clear();
    insystemContext.initialize(data);
}


//删除一行
function xform_main_data_delTrItem(aDom,callback){
    $(aDom).closest("tr").remove();
    if(callback){
        callback();
    }
}
//更新标题
function changeUlTitle(selectDom) {
    $(selectDom).parents(".model-edit-view-oper").eq(0).find(".model-edit-view-oper-head-title span").text($(selectDom).find("option:selected").text());
}

//移动 -1：上移       1：下移
function xform_main_data_moveTr_new(direct,dom){
    var tb = $(dom).closest("table")[0];
    var $tr = $(dom).closest("tr");
    var curIndex = $tr.index();
    var lastIndex = tb.rows.length - 1;
    if(direct == 1){
        if(curIndex >= lastIndex){
            alert(listviewOption.lang.alreadyToDown);
            return;
        }
        $tr.next().after($tr);
    }else{
        if(curIndex < 1){
            alert(listviewOption.lang.alreadyToUp);
            return;
        }
        $tr.prev().before($tr);
    }
}


//切换状态
function changeFlag(obj, name, value, relatedShowName){

    var curVal = $("[name='"+name+"']").val();
    if(curVal == value){
        return;
    }
    var radioObj = $(obj).parents(".view_flag_radio")[0];
    if(value == 0){
        if(relatedShowName) {
            $("select[name='" + relatedShowName + "']").hide();
            $("select[name='" + relatedShowName + "']").val("");
        }
        $(radioObj).find(".view_flag_radio_no i").addClass("view_flag_yes");
        $(radioObj).find(".view_flag_radio_yes i").removeClass("view_flag_yes");
    }else{
        if(relatedShowName)
            $("select[name='" + relatedShowName + "']").show();
        $(radioObj).find(".view_flag_radio_no i").removeClass("view_flag_yes");
        $(radioObj).find(".view_flag_radio_yes i").addClass("view_flag_yes");
    }
    $("[name='"+name+"']").val(value);
}

//提交时处理搜索设置
function xform_main_data_detailOrderbyWhere(tr){
    var valueJSON = {};
    //处理查询属性
    var attrOptions = $(tr).find("select[name='fdAttrField']");
    var fieldVal = "";
    var fieldType = "";
    for(var i = 0;i < attrOptions.length;i++){
        var fieldOption = $(attrOptions[i]).find("option:selected");
        var ty = fieldOption.attr('data-type');
        fieldVal += fieldOption.val() + _xform_main_data_insystem_split;
        fieldType += ty + _xform_main_data_insystem_split;
    }
    valueJSON.field = fieldVal.substring(0,fieldVal.length - 1);
    valueJSON.fieldType = fieldType.substring(0,fieldType.length - 1);
    // 处理显示值
    var show = $(tr).find("select[name='fdOrderType']");
    if(show.length > 0){
        valueJSON.orderType = show.val();
    }
    return valueJSON;
}
