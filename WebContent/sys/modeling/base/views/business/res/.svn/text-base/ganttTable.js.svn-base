/**
 * gantt类
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var dialog = require('lui/dialog');
    var topic = require("lui/topic");
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var ganttBase = require("sys/modeling/base/views/business/res/ganttBase");
    var modelingLang = require("lang!sys-modeling-base");
    var GanttTable = ganttBase.GanttBase.extend({

        /**
         * 初始化
         * cfg应包含参考引用方
         */
        initProps: function ($super, cfg) {
            this.cst = {}
            this.cst.rpClickFun = {
                "tableCondition": "_conditionDialogClick",
                "fdDisplay": "_fdDisplayDialogClick",
                "tableWhere": "_whereItemAddClickTable",
                "tableNameOnClick":"_tableNameOnClick",
                "tableNameOnValueChange":"_tableNameOnValueChange",
                "viewContent": "_fdViewContent"
            };
            if (cfg.flowInfo) {
                this.fdEnableFlow = cfg.flowInfo.fdEnableFlow;
            }
            $super(cfg);
        },
        /**
         * 实际绘制显示设置区域
         *
         */
        build: function () {
            var self = this;
            //#1  图表标题
            //去除变量
            var tableTitle = formulaBuilder.get_style2("cfgTableTitle", "String", null, null, function () {
                return [];
            });
            self.$c.find("#_resPanel_table_title").append(tableTitle);

            var ganttStartTime = this.getTimeSelectHtml('ganttStartTime',ganttOption.modelingGanttForm.allField);
            self.$c.find("#_gantt_start_time").append(ganttStartTime);

            var ganttEndTime = this.getTimeSelectHtml('ganttEndTime',ganttOption.modelingGanttForm.allField);
            self.$c.find("#_gantt_end_time").append(ganttEndTime);
            var ganttProgress = formulaBuilder.get_style2("cfgGanttProgress", "String", null, null, null);
            self.$c.find("#_gantt_progress").append(ganttProgress);
            self.$c.find("#_gantt_progress").append("<div title='"+modelingLang['Gantt.progress.range.0~1']+"' class='progress_range'><label>\n" +
                "<img src='"+Com_Parameter.ContextPath+"sys/xform/designer/prompt/icon-prompt@2x.png'>\n" +
                "</label></div>");
            var ganttShowField = formulaBuilder.get_style2("cfgGanttShowField", "String", null, null, null);
            self.$c.find("#_gantt_show_Field").append(ganttShowField);
            //# 行列改变事件
            self.$c.find("[rp-table-mark=\"category\" ]").on("change", function () {
                self.categoryChange($(this))
            }).trigger($.Event("change"));
            self.element = self.$c;
        },
        //
        _conditionDialogClick: function (thisObj) {
            this.switchPosition(thisObj,'right','fdCondition');
            var modelingAllFild=ganttOption.modelingGanttForm.allField;
            //转义换行符
            modelingAllFild = modelingAllFild.replace(/\n/g,"\\\\n");
            //转义mac \r\n换行符
            modelingAllFild = modelingAllFild.replace(/\r/g,"\\\\r");
            //过滤明细表的属性
            modelingAllFild = JSON.stringify(window.filterSubTableField(modelingAllFild));
            //过滤当前节点，当前处理人属性
            modelingAllFild = JSON.stringify(this.filterLbpmField(modelingAllFild));
            var selected = $(thisObj).find("input[type='hidden']").val();
            var url = '/sys/modeling/base/listview/config/dialog.jsp?type=condition';
            dialog.iframe(url, modelingLang['modelingAppListview.fdCondition'], function (data) {
                if (!data) {
                    return;
                }
                data = $.parseJSON(data);
                $(thisObj).find("[data-lui-mark='dialogText']").val(data.text.join(";"));
                $(thisObj).find("input[type='hidden']").val(JSON.stringify(data.selected));
                ganttOption.modelingGanttForm.fdCondition = JSON.stringify(data.selected);
                //更新预览
                //明细表字段处理
                // var field = cfg["field"];
                // cfg["field"] = field.substring(field.lastIndexOf(".")+1);
                topic.publish("preview.refresh");
            }, {
                width: 720,
                height: 530,
                params: {
                    selected: selected,
                    allField: modelingAllFild,
                    modelDict : ganttOption.modelingGanttForm.modelDict
                }
            });
        },
        _fdViewContent: function (thisObj) {
            this.switchPosition(thisObj,'right','viewContent');
        },
        _fdDisplayDialogClick: function (thisObj) {
            //Com_EventStopPropagation();
            //切换样式
            this.switchPosition(thisObj,'right','fdDisplay');
            topic.publish("switch_select_status",{'position':'fdConditions'});
            var url='/sys/modeling/base/views/business/gantt/dialog.jsp?type=display';
            var modelingAllFild=ganttOption.modelingGanttForm.allField;
            //转义换行符
            modelingAllFild = modelingAllFild.replace(/\n/g,"\\\\n");
            //转义mac \r\n换行符
            modelingAllFild = modelingAllFild.replace(/\r/g,"\\\\r");
            //过滤明细表的属性
            modelingAllFild = JSON.stringify(this.filterSubTableField(modelingAllFild));
            //modelingAllFild=modelingAllFild.replace("docCreator.fdName","docCreator");
            //过滤当前节点，当前处理人属性
            modelingAllFild = JSON.stringify(this.filterLbpmField(modelingAllFild));

            dialog.iframe(url,ganttOption.lang.selectDisplay,function(data){
                if(!data){
                    return;
                }
                data=data.replace("docCreator.fdName","docCreator");
                data = $.parseJSON(data);
                //回调
                if(JSON.stringify(data.selected).length >= 3000){
                    dialog.confirm(modelingLang['Gantt.displayItem.many.selec.again']);
                }else{
                    ganttOption.modelingGanttForm.fdDisplay = JSON.stringify(data.selected);
                    $("input[name='fdDisplayText']").val(data.text.join(";"));
                    //#138154 关闭dialog时会刷新预览，若预览卡片处于预览状态会自动被刷新，所以将预览按钮重置
                    $(".model-source-table-main .gantt-view-block-text").first().removeClass("source-dialog-preview");
                    $("#dialogDetailPreview").text(modelingLang['sys.profile.modeling.preview']);
                    //显示项样式改变事件
                    topic.publish("modeling.selectDisplay.change",{'thisObj':thisObj,'data':data});

                    //更新预览
                    topic.publish("preview.refresh");
                }

            },{
                width : 900,
                height : 500,
                params : {
                    selected : ganttOption.modelingGanttForm.fdDisplay,
                    allField : modelingAllFild,
                    modelDict : ganttOption.modelingGanttForm.modelDict
                }
            });
        },
        _tableNameOnClick: function (thisObj) {
            this.switchPosition(thisObj,'right','tableName');
        },
        _tableNameOnValueChange: function (thisObj) {
            var text =   $("#tableNameText").val();
            if(text){
                $("#tableName").text(text);
            }

        },
        /*过滤明细表的字段*/
        filterSubTableField: function(fields){
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
        },
        /*过滤当前处理人和当前节点的字段*/
        filterLbpmField: function(fields){
            var allField = fields || [];
            if(typeof allField === 'string'){
                allField = $.parseJSON(fields);
            }
            var newAllField = [];
            for(var i=0; i<allField.length; i++){
                var field = allField[i];
                if(field.field == 'LbpmExpecterLog_fdHandler' || field.field == 'LbpmExpecterLog_fdNode'){
                    continue;
                }
                newAllField.push(field);
            }
            return newAllField;
        },
        _whereItemAddClickTable: function (thisObj) {
            var self = this;
            //这里相当于自己查询自己，没有当前主文档的概念，需要屏蔽掉公式定义器
            var whereCfg = {
                parent: self,
                widgets: self.widgets,
                formulaWidgets: self.widgets,
                fdEnableFlow: self.fdEnableFlow
            };
            this._whereItemAddClick(thisObj, whereCfg);
        },
        getKeyData: function () {
            var self = this;
            var table = {};
            //名称
            var tableName =  self.element.find("[id= tableNameText]").val();
            table.tableName = tableName;
            //筛选项
            var condition = self.element.find("[name=tableCondition]").val();
            table.condition = [];
            if (condition || condition.length > 0) {
                table.condition = JSON.parse(condition);
                table.conditionText = self.element.find("[name= tableConditionText]").val();
            }
            //显示项
            table.fdDisplay = ganttOption.modelingGanttForm.fdDisplay;
            table.fdDisplayText = self.element.find("[name= fdDisplayText]").val();

            //配色方案
            var colorProgrammeSelect = self.element.find("[id= colorProgrammeSelect]").val();
            table.colorProgramme = colorProgrammeSelect;
            //时间维度
            var timeDimensionSelect = self.element.find("[id= timeDimensionSelect]").val();
            table.timeDimension = timeDimensionSelect;
            //开始时间
            var startTimeField = self.element.find("[id= ganttStartTime]").val();
            if(modelingLang['modeling.page.choose'] == startTimeField){
                table.endTimeField = undefined;
            }else{
                table.startTimeField=startTimeField;
            }

            //结束时间
            var endTimeField = self.element.find("[id= ganttEndTime]").val();
            if(modelingLang['modeling.page.choose'] == startTimeField){
                table.endTimeField = undefined;
            }else {
                table.endTimeField = endTimeField;
            }
            //进度
            var progressValue =  self.element.find("[name= cfgGanttProgress]").val();
            var progressText =  self.element.find("[name= cfgGanttProgress_name]").val();
            var progress = {};
            progress.text = progressText;
            progress.value = progressValue;
            table.progress=progress;
            //显示字段
            var showFieldValue =  self.element.find("[name= cfgGanttShowField]").val();
            var showFieldText =  self.element.find("[name= cfgGanttShowField_name]").val();
            var showField = {};
            showField.text = showFieldText;
            showField.value = showFieldValue;
            table.showField=showField;
            //处理查询条件
            var $selectTr = $(".model-query-content").find("tr:not(.xform_main_data_tableTitle)");
            var selectArray = [];
            for(let i = 0;i < $selectTr.length; i++){
                let tr = $selectTr[i];
                selectArray.push(xform_main_data_detailSelectWhere(tr));
            }
            //处理排序设置
            $selectTr = $("#xform_main_data_orderbyTable").find("tr:not(.xform_main_data_tableTitle)");
            var orderbyArray = [];
            for(let i = 0;i < $selectTr.length; i++){
                let tr = $selectTr[i];
                orderbyArray.push(xform_main_data_detailOrderbyWhere(tr));
            }
            //保存时，"筛选项"将过滤"预定义查询"中已使用的字段
            if (selectArray && selectArray.length > 0) {
                let fdCondition = ganttOption.modelingGanttForm.fdCondition;
                var startStr = "\\[";
                var endStr = "\\]";
                var startReg =new RegExp("^"+startStr);
                var endReg = new RegExp(endStr+"$");
                if (fdCondition && startReg.test(fdCondition) && endReg.test(fdCondition)) {
                    let resultFdConditionArray = [];
                    let fdConditionArray = JSON.parse(fdCondition);
                    for (let i in fdConditionArray) {
                        let condition = fdConditionArray[i];
                        let isInWhereBlock = false;
                        for (let j in selectArray) {
                            let where = selectArray[j];
                            if(where.whereType === '1')
                                continue;
                            //根据字段名判断,WhereBlock中格式为"field":"fd_382c69377ea860|fdId"，condition中格式为"field":"fd_382c69377ea860"
                            if (where.field.indexOf(condition.field) !== -1) {
                                isInWhereBlock = true;
                                break;
                            }
                        }
                        if (!isInWhereBlock) {
                            resultFdConditionArray.push(condition);
                        }
                    }
                    //提示
                    if(fdConditionArray.length !== resultFdConditionArray.length){
                        seajs.use(["lui/dialog"], function(dialog) {
                            dialog.confirm(modelingLang['listview.filter.item.used.query'], function (value) {
                                if (value === true) {
                                    ganttOption.modelingGanttForm.fdCondition = JSON.stringify(resultFdConditionArray);
                                    table.where = JSON.stringify(selectArray);
                                }
                            });
                        });
                        return false;
                    }
                }
            }
            table.fdWhere = JSON.stringify(selectArray);
            var fdCustomWhereType= self.element.find("input[name='fdCustomWhereType']:checked").val();
            if (fdCustomWhereType) {
                table.fdCustomWhereType = fdCustomWhereType;
            } else {
                table.fdCustomWhereType = '0';
            }
            table.fdOrderBy = orderbyArray;
            //是否字段穿透
            var fdViewFlag =  self.element.find("[name= fdViewFlag]").val();
            table.fdViewFlag = fdViewFlag;
            var fdViewId =  self.element.find("[name= fdViewId]").val();
            table.fdViewId = fdViewId;
            //是否开启权限过滤
            var fdAuthEnabled =  self.element.find("[name= fdAuthEnabled]").val();
            table.fdAuthEnabled = fdAuthEnabled;
            //处理业务操作
            var listOperationIdArr = "";
            $("input[name^='listOperationIdArr[']").each(function () {
                listOperationIdArr += $(this).val() + ";";
            });
            $("input[name='listOperationIds']").val(listOperationIdArr);
            var listOperationNameArr = "";
            $("input[name^='listOperationNameArr[']").each(function () {
                listOperationNameArr += $(this).val() + ";";
            });
            $("input[name='listOperationNames']").val(listOperationNameArr);
            return table;
        },
        initByStoreData: function (table) {
            console.debug("initByStoreData - table::", table);
            var self = this;
            if (table) {
                try {
                    if (table.title) {
                        self.element.find("[name='cfgTableTitle_name']").val(table.title.text)
                        self.element.find("[name='cfgTableTitle']").val(table.title.value);
                    }
                    //
                    if (table.condition) {
                        self.element.find("[name=fdCondition]").val()
                    }
                    var condition = table.condition;
                    if (condition || condition.length > 0) {
                        self.element.find("[name=tableCondition]").val(JSON.stringify(condition));
                        if (table.conditionText) {
                            self.element.find("[name= tableConditionText]").val(table.conditionText);
                        }
                    }
                    var fdDisplay = table.fdDisplay;
                    self.element.find("[name= fdDisplayText]").css(
                        {
                            "overflow": "hidden",
                            "white-space": "nowrap",
                            "text-overflow": "ellipsis"
                        }
                    );
                    if (fdDisplay || fdDisplay.length > 0) {
                        if (table.fdDisplayText) {
                            self.element.find("[name= fdDisplayText]").val(table.fdDisplayText);
                        }
                    }
                    //处理预定义查询
                    var whereData = table.fdWhere;
                    var whereDataJsonArray = $.parseJSON(getValidJSONArray(whereData));
                    //遍历预定义查询数据
                    // 把查询条件按类型做分组
                    // 按照分组调用新增行方法
                    var predefineArr = [];
                    var sysArr = [];
                    var $predefineTable = $(".model-query-content-cust").find(".model-edit-view-oper-content-table");
                    var $sysTable = $(".model-query-content-sys").find(".model-edit-view-oper-content-table");
                    for(var i = 0;i < whereDataJsonArray.length;i++){
                        if($.isEmptyObject(whereDataJsonArray[i])){
                            continue;
                        }
                        var whereType = whereDataJsonArray[i].whereType;
                        if(whereType == '0'){
                            predefineArr.push(whereDataJsonArray[i]);
                        }else if(whereType == '1'){
                            sysArr.push(whereDataJsonArray[i]);
                        }
                        //xform_main_data_addWhereItem(whereDataJsonArray[i], true);
                    }
                    for(var i = 0;i < predefineArr.length;i++){
                        xform_main_data_addWhereItem(predefineArr[i], $($predefineTable),'0');
                    }

                    for(var i = 0;i < sysArr.length;i++){
                        xform_main_data_addWhereItem(sysArr[i], $($sysTable),'1');
                    }
                    //初始化开始时间
                    if(table.startTimeField) {
                        self.element.find("[id= ganttStartTime]").val(table.startTimeField);
                        $("#ganttEndTime option[value='"+table.startTimeField+"']").css("display","none");
                    }
                    //初始化结束维度
                    if(table.endTimeField) {
                        self.element.find("[id= ganttEndTime]").val(table.endTimeField);
                        $("#ganttStartTime option[value='"+table.endTimeField+"']").css("display","none");
                    }
                    //初始化配色方案
                    if(table.colorProgramme){
                        self.element.find("[id= colorProgrammeSelect]").val(table.colorProgramme);
                    }
                    //初始化时间维度
                    if(table.timeDimension) {
                        self.element.find("[id= timeDimensionSelect]").val(table.timeDimension);
                    }
                    //初始化名称
                    if(table.tableName){
                        self.element.find("[id= tableName]").text(table.tableName);
                    }
                    //初始化排序项
                    if(table.fdOrderBy){
                        self.element.find("[name= fdOrderBy]").val(table.fdOrderBy);
                    }
                    //初始化进度
                    if(table.progress){
                        self.element.find("[name= cfgGanttProgress]").val(table.progress.value);
                        self.element.find("[name= cfgGanttProgress_name]").val(table.progress.text);
                    }
                    //初始化显示字段
                    if(table.showField){
                        self.element.find("[name= cfgGanttShowField]").val(table.showField.value);
                        self.element.find("[name= cfgGanttShowField_name]").val(table.showField.text);
                    }
                    //是否字段穿透
                    self.element.find("[name= fdViewFlag]").val(0);
                    if(table.fdViewFlag){
                        self.element.find("[name= fdViewFlag]").val(table.fdViewFlag);
                        if(table.fdViewId){
                            self.element.find("[name= fdViewId]").val(table.fdViewId);
                        }
                    }
                    //是否开启权限过滤
                    if(table.fdAuthEnabled){
                        self.element.find("[name= fdAuthEnabled]").val(table.fdAuthEnabled);
                    }
                } catch (e) {
                    console.log("初始化失败", e)
                }


            }
        },
        __getRowAndColType: function (type) {
            try {
                var sourceKeyData = this.getKeyData();
                var data = sourceKeyData[type];
                return data.type;
            } catch (e) {
                console.warn("未获取到面板行列字段属性", e)
            }
        },
        switchPosition: function (obj,direct,toPosition){
            Com_EventStopPropagation();
            $("[data-lui-position]").removeClass('active');
            // $("[data-lui-local]").removeClass('localFocus');
            $(".model-edit-view-content-top").removeClass("active");
            $(".model-edit-view-content-bottom").removeClass("active");
            $("[data-lui-position='fdCondition']").parents("div[onclick]").eq(0).removeClass("active");
            $("[data-lui-position='fdDisplay']").parents("div[onclick]").eq(0).removeClass("active");
            $("[data-lui-position='fdOrderBy']").parents("div[onclick]").eq(0).removeClass("active");
            $("[data-lui-position='tableName']").parents("div[onclick]").eq(0).removeClass("active");
            $("[data-lui-position='viewContent']").parents("div[onclick]").eq(0).removeClass("active");
            $(".dots.active").removeClass("active");
            $("#moreList").hide();
            var position = $(obj).attr("data-lui-position");
            if(!position){
                $(obj).addClass("active");
                $('[data-lui-position="'+toPosition+'"]').addClass("active");
            }else{
                $("[data-lui-position='"+position+"']").addClass("active");
                //$("td[data-lui-position='"+position.split("-")[0]+"']").addClass("active");
                $("[data-lui-position='"+position.split("-")[0]+"']").addClass("active");
                $("[data-lui-position='"+position+"']").parents(".model-edit-view-content-top").addClass("active");
                $("[data-lui-position='"+position+"']").parents(".model-edit-view-content-bottom").addClass("active");
                $("[data-lui-position='"+position+"']").parents(".model-edit-view-content-center-wrap").addClass("active");
            }
            //进行特殊处理
            if(position && position.split("-")[0] == 'fdCondition'){
                //搜索项
                $("[data-lui-position='"+position.split("-")[0]+"']").parents("div[onclick]").eq(0).addClass("active");
            }else if(position && position.split("-")[0] == 'fdDisplay'){
                //显示项
                $("[data-lui-position='"+position.split("-")[0]+"']").parents("div[onclick]").eq(0).addClass("active");
            }else if(position && position == 'viewContent'){
                //图表设置
                // $("[data-lui-position='"+position+"']").addClass("localFocus");
            }
            //进行滚轮处理
            if(direct=='left' && position){
                var panel = $("[data-lui-position='"+position+"']").parents(".model-edit-view-content").eq(0);
                var target = $(".model-edit-right").find("[data-lui-position='"+position+"']").eq(0);
                var scrollTop = target.offset().top - panel.offset().top + panel.scrollTop() - 50;
                panel.scrollTop(scrollTop)
            }
            lastSelectPostionObj = obj;
            lastSelectPostionDirect = direct;
            lastSelectPosition = toPosition;
            return false;
        },
        // 返回开始时间和结束时间下拉框
        getTimeSelectHtml: function (id,allField) {
            var modelingAllFild=allField;
            //转义换行符
            modelingAllFild = modelingAllFild.replace(/\n/g,"\\\\n");
            //转义mac \r\n换行符
            modelingAllFild = modelingAllFild.replace(/\r/g,"\\\\r");
            //过滤明细表的属性
            modelingAllFild = JSON.stringify(this.filterSubTableField(modelingAllFild));
            var allFieldJson =  $.parseJSON(modelingAllFild);
            var len = allFieldJson.length;
            var obj;

            var html_str = " <select id="+id+" class=\"inputsgl width45Pe\" style='width: 100%' onchange=selectOnChange(\'"+id+"\');>\n" ;
            html_str =   html_str + "         <option>"+modelingLang['modeling.page.choose']+"</option>\n"
                    for(var i=0;i<len;i++){
                        obj = allFieldJson[i];
                        if(obj.field && ('Date' == obj.type || 'DateTime' == obj.type || 'Time' == obj.type)){
                            html_str =   html_str + "         <option value="+obj.field+">"+obj.text+"</option>\n"
                        }
                    }
            html_str =   html_str + "          </select>";
            html_str =   html_str + "<p style=\"display: inline-block;color: #FF0000;font-weight: normal;margin-left: 5px;\">*</p>";
            var $html = $(html_str);

            return $html;
        },

});

    exports.GanttTable = GanttTable;
});
