/**
 * 公式定义器构造器
 */


define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    require("resource/js/xml.js");
    require("resource/js/data.js");
    require("resource/js/formula.js");
    require("resource/js/dialog.js");
    require("resource/js/address.js");
    require("resource/js/treeview.js");
    require("resource/js/calendar.js");
    require("resource/js/jquery-ui/jquery.ui");

    var lang = require("lang!sys-modeling-base");

    if (!window.Calendar_Lang)
        window.Calendar_Lang = {
            format: {
                //date : window.seajs ? seajs.data.env.pattern.date : Data_GetResourceString("date.format.date.2y"),
                //time : window.seajs ? seajs.data.env.pattern.time : Data_GetResourceString("date.format.time"),
                //dataTime : window.seajs ? seajs.data.env.pattern.datetime : Data_GetResourceString("date.format.datetime.2y")
                date: Data_GetResourceString("date.format.date.2y").toLowerCase(),
                time: Data_GetResourceString("date.format.time"),
                dataTime: Data_GetResourceString("date.format.datetime.2y").toLowerCase()
            }
        };


    var fieldList = null;
    var _ModelingAppModelName = "com.landray.kmss.sys.modeling.base.model.ModelingAppModel";
    var FormulaBuilder = base.Component.extend({

        initFieldList: function (xformId) {

            fieldList = this.XForm_getXFormDesignerObj(xformId);
        },
        getFieldList:function(){
        	return fieldList;
        },
        /*
        <div class="inputselectsgl" onclick="__rpClick(this,'tableCondition');">
        <input name="tableCondition" value="" type="hidden">
        <div class="input">
        <input placeholder="请选择" data-lui-mark="dialogText" name="tableConditionText" value="" type="text" readonly="">
        </div><div class="selectitem"></div>
        </div>
         */
        // 返回公式定义器
       get_style2: function (name, type, formulaParameter, formualUrl, filterField) {
            var self = this;
            var html_str = "<div class=\"inputselectsgl modeling_formula\" >\n" +
                "        <input value=\"\" type=\"hidden\">\n" +
                "        <div class=\"input\">\n" +
                "        <input placeholder='"+lang['modeling.page.choose']+"'  value=\"\" type=\"text\" width='' readonly>\n" +
                "        </div><div class=\"selectitem\"></div>\n" +
                "        </div>";
            var $html = $(html_str);
            $html.find("input[type='hidden']").attr("name", name);
            $html.find("input[type='text']").attr("name", name + "_name");
            var fieldList_list = fieldList;
            if (filterField) {
                fieldList_list = filterField(fieldList);
            }

            $html.on("click", function (e) {
                if (type === "Attachment") {
                    self.Formula_Attachment_Dialog(name, name + "_name", fieldList_list || "");
                } else {
                    Formula_Dialog(name, name + "_name", fieldList_list || "", type || "Object", null, null, _ModelingAppModelName, formulaParameter, formualUrl);
                }

            });
            return $html;
        },
        get: function (name, type, formulaParameter, formualUrl, filterField) {
            var self = this;
            var html = $("<div class='modeling_formula'/>");
            html.append("<input type='hidden' name='" + name + "' />");
            html.append("<input type='text' name='" + name + "_name' class='inputsgl' style='width:150px' readonly/>");
            var $formula = $("<span class='highLight'><a href='javascrip:void(0);'>选择</a></span>");
            var fieldList_list = fieldList;
            if (filterField) {
                fieldList_list = filterField(fieldList);
            }

            if (formulaParameter && formulaParameter.supportDetailAtt) {
                //支持明细表选择
                this.mergeFieldListForDetailAtt(formulaParameter, fieldList_list)
            }
            $formula.on("click", function (e) {
                if (type.indexOf("Attachment") > -1) {
                    self.Formula_Attachment_Dialog(name, name + "_name", fieldList_list || "");
                } else {
                    Formula_Dialog(name, name + "_name", fieldList_list || "", type || "Object", null, null, _ModelingAppModelName, formulaParameter, formualUrl);
                }

            });
            html.append($formula);
            return html;
        },
        mergeFieldListForDetailAtt: function (formulaParameter, fieldList_list) {
            if (formulaParameter.sourceFormList && fieldList_list) {
                var sourceFormList = formulaParameter.sourceFormList;
                for (var key in sourceFormList) {
                    if (sourceFormList[key].type == "Attachment") {
                        var item = [];
                        item["name"] = sourceFormList[key].name
                        item["label"] = sourceFormList[key].fullLabel||sourceFormList[key].label
                        item["businessType"] = sourceFormList[key].businessType
                        item["type"] = sourceFormList[key].type;
                        fieldList_list.push(item)
                    }
                }
            }
        },
        get_style1: function (name, type, formulaParameter, formualUrl, xformId) {
            var self = this;
            var html = $("<div _xform_type=\"text\"/>");
            html.append("<input type='hidden' name='" + name + "' />");
            html.append("<input type='hidden' name='" + name + "' />");
            var $formula = $("<div  class=\"model-mask-panel-table-show highLight\"\>")
            $formula.append("<input type='text' name='" + name + "_name' class='inputsgl' style='width:150px' readonly/>");
            $formula.on("click", function (e) {
                var targetFieldList = fieldList;
                if (xformId) {
                    targetFieldList = self.getXFormFieldList(xformId);
                }
                if (type === "Attachment") {
                    self.Formula_Attachment_Dialog(name, name + "_name", targetFieldList || "");
                } else {
                    Formula_Dialog(name, name + "_name", targetFieldList || "", type || "Object", null, null, _ModelingAppModelName, formulaParameter, formualUrl);
                }

            });
            html.append($formula);
            return html;
        },
        Formula_Attachment_Dialog: function (idField, nameField, fieldList) {
            var data = new KMSSData();
            for (var i = 0; i < fieldList.length; i++) {
                var field = fieldList[i];
                if (!(field.type == "Attachment" || field.type == "Attachment[]")) {
                    continue;
                }
                var label =  field.label;
                if(field.fullLabel){
                    label = field.fullLabel;
                }
                var pp = {name: field.name, label: label};
                if (field.type == "Attachment[]") {
                    pp.name = "$" + field.name + "$";
                    pp.label = "$" + label + "$";
                }
                data.AddHashMap({id: pp.name, name: pp.label});
            }
            var dialog = new KMSSDialog(false, true);
            dialog.winTitle = "title";
            dialog.AddDefaultOption(data);
            dialog.BindingField(idField, nameField, ";");
            dialog.Show();
        },
        // 返回地址本
        getOrgAddress: function (isMulti, name, type) {
            var html = $("<div class='modeling_org_address'/>");
            html.append("<input type='hidden' name='" + name + "' />");
            html.append("<input type='text' name='" + name + "_name' class='inputsgl' style='width:150px' readonly/>");
            var $address = $("<span class='highLight'><a href='javascrip:void(0);'>选择</a></span>");
            $address.on("click", function (e) {
                Dialog_Address(isMulti, name, name + "_name", ";", type || ORG_TYPE_PERSON);
            });
            html.append($address);
            return html;
        },
        getOrgAddress_style1: function (isMulti, name, type) {
            var html = $("<div _xform_type=\"text\"/>");
            html.append("<input type='hidden' name='" + name + "' />");
            var $address = $("<div  class=\"model-mask-panel-table-show highLight\"\>")
            $address.append("<input type='text' name='" + name + "_name' class='inputsgl' readonly/>");
            $address.on("click", function (e) {
                Dialog_Address(isMulti, name, name + "_name", ";", type || ORG_TYPE_PERSON);
            });
            html.append($address);
            return html;
        },
        // 返回时间选择
        getDate: function (isMulti, name, type) {

            var html = $("<div class='modeling_data_select inputselectsgl'/>");
            var $input = $(" <div class=\"input\"  style='width:85%;min-height:20px;float: left;' />")
            $input.append("<input type='text' name='" + name + "' style='width:150px' readonly/>");

            html.append($input);

            var icon = $("<div class='inputdatetime'/>");
            //区分日期，时间、时间日期
            if (type === "Date") {
                html.on("click", function (e) {
                    selectDate(name, null, function (c) {
                        __CallXFormDateTimeOnValueChange(c);
                    });
                });
            } else if (type === "DateTime") {
                html.on("click", function (e) {
                    selectDateTime(name, null, function (c) {
                        __CallXFormDateTimeOnValueChange(c);
                    });
                });
            } else if (type === "Time") {
                icon = $("<div class='inputtime'/>");
                html.on("click", function (e) {
                    selectTime(name, null, function (c) {
                        __CallXFormDateTimeOnValueChange(c);
                    });
                });
            }
            html.append(icon);
            return html;
        },

        // 给指定元素添加公式定义器
        addFormulaEventToElement: function (dom, event, name, type, xformId,detailId) {
            var self = this;
            $(dom).on(event, function () {
                if (xformId) {
                    var targetFieldList = self.getXFormFieldList(xformId,detailId);
                    Formula_Dialog(name, name + "_name", targetFieldList, type || "Object",null, null, _ModelingAppModelName);
                } else {
                    Formula_Dialog(name, name + "_name", fieldList, type || "Object",null, null, _ModelingAppModelName);
                }

            });
        },

        getXFormFieldList: function (xformId,detailId) {
            return this.XForm_getXFormDesignerObj(xformId,detailId);
        },

        XForm_getXFormDesignerObj: function (xformId,detailId) {
            var obj = [];

            var sysObj = this._XForm_GetSysDictObj("com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain");
            if (sysObj != null) {
                //获取有无流程标识
                var getIsFlowUrl = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingBehavior.do?method=findModelIsFlowByXformId&xformId=" + xformId;
                $.ajax({
                    url: getIsFlowUrl,
                    type: "get",
                    async: false,
                    success: function (data) {
                        if(!data){
                            for (var i = 0; i <sysObj.length ; i++) {
                                var sysItem  = sysObj[i];
                                if(sysItem && sysItem.name=="fdProcessEndTime"){
                                    //无流程表单去掉流程结束时间
                                    sysObj.splice(i,1);
                                }
                            }
                        }else{
                            //#166825
                            var fdHandlerObj = [];
                            fdHandlerObj.name="LbpmExpecterLog_fdHandler";
                            fdHandlerObj.label=lang['modeling.lbpm.expecterLog_fdHandler'];
                            fdHandlerObj.type="com.landray.kmss.sys.organization.model.SysOrgPerson";
                            sysObj.push(fdHandlerObj);
                            var fdNodeObj = [];
                            fdNodeObj.name="LbpmExpecterLog_fdNode";
                            fdNodeObj.label=lang['modeling.lbpm.expecterLog.fdNode'];
                            fdNodeObj.type="String";
                            sysObj.push(fdNodeObj);
                        }
                    }
                });
            }
            var extObj = null;

            extObj = this._XForm_GetTempExtDictObj(xformId);
            return this.XForm_Util_UnitArray(obj, sysObj, extObj,detailId);
        },

        // 查询modelName的属性信息
        _XForm_GetSysDictObj: function (modelName) {
            return Formula_GetVarInfoByModelName(modelName);
        },

        // 查找自定义表单的数据字典
        _XForm_GetTempExtDictObj: function (tempId) {
            return new KMSSData().AddBeanData("sysFormDictVarTree&tempType=template&tempId=" + tempId).GetHashMapArray();
        },

        XForm_Util_UnitArray: function (array, sysArray, extArray,detailId) {
            array = array.concat(sysArray);
            if (extArray != null) {
                for (var i = 0; i <extArray.length ; i++) {
                    //#127245 过滤关联文档 ,关联文档控件有controlId，controlId_config,暂时屏蔽controlId_config
                    var extItem  = extArray[i];
                    if (extItem && extItem.controlType =="relevance") {
                        if (extItem.name && extItem.name.endsWith("_config")) {
                            continue;
                        }
                    }
                    //过滤业务填充控件/#171077 过滤审批意见控件
                    if(extItem && (extItem.controlType == "filling" || extItem.controlType == "auditShow")){
                        continue;
                    }
                    //过滤没选择的明细表字段
                    if (detailId!=null){
                    if ( extItem.controlType =="detailsTable"||extItem.name.indexOf(".")!= -1 ) {
                        if (extItem.name.indexOf(detailId)== -1) {
                            continue;
                        }
                    }
                }
                    array.push(extArray[i])
                    
                }
                // array = array.concat(extArray);
            }
            return array;
        }

    });

    module.exports = new FormulaBuilder();
});
