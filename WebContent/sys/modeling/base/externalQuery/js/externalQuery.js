define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var env = require('lui/util/env');
    var dialog = require('lui/dialog');
    var topic = require("lui/topic");
    var lang = require('lang!sys-ui');
    var modelingLang = require("lang!sys-modeling-base");
    var whereRecordGenerator = require("sys/modeling/base/relation/trigger/behavior/js/whereRecordGenerator");
    var ExternalQuery= base.Component.extend({

        fieldTypes: {
            "1": {
                "name": modelingLang['modelingAppListview.enum.fix'],
                "valueDrawFun": "fixValDraw"
            },
            "3": {
                "name": modelingLang['modelingAppListview.enum.empty'],
                "valueDrawFun": "nullValDraw"
            },
            "4": {
                "name": modelingLang['modelingAppListview.enum.formula'],
                "valueDrawFun": "formulaValDraw"
            }
        },
        currentData : {},	//本表单数据
        /**
         * 初始化
         * cfg应包含：
         */
        initProps: function ($super, cfg) {
            $super(cfg);
            var self = this;
            this.container = cfg.container || null;
            this.fdConfig = cfg.fdConfig || {};
            this.channel = cfg.channel;
            this.currentData = cfg.currentData;

            this.whereWgtCollection = [];
            topic.channel("externalQuery").subscribe("soureData.load",function(rtn){
                self.formatTargetData(rtn);
            });

            this.initByStoreData(cfg);
        },

        draw: function($super) {

            var self = this;
            //外部链接
            self.drawLink();
            //链接有效期
            self.drawLinkValidityPeriod();
            //数据过滤
            self.drawDataFilter();
            //标题
            self.drawTitle()
            //结果显示项
            self.drawDisplay();
            //查询项
            self.drawSearchCondition();

            self.bindEvent();
            //初始化表单链接文本框URL内容
            updateLinkText();
        },
        /**
         * 绑定事件
         */
        bindEvent: function (cfg){
            $("[id='cfg_iframe']", parent.document).css("height","720px");

            $(".copy-icon").on('click', function () {
                if (!listviewOption.token) {
                    return;
                }
                var linkUrl =  env.fn.formatUrl("/sys/anonymous/enter/token.do?method=visitToken&token="+listviewOption.token, true);
                var flag = copyText($(this),linkUrl); //传递文本
                dialog.alert(flag ? modelingLang['modeling.baseinfo.CopySuccess'] : modelingLang['modeling.baseinfo.CopyNotSuccess'])
            })

            $(".jump-icon").on('click', function () {
                if (!listviewOption.token) {
                    return;
                }
                Com_OpenWindow( $(".link-text").text(), "_blank");
            })

            //二维码图标
            $(".qrcode-icon").on('click', function (e) {
                if (!listviewOption.token) {
                    return;
                }
                //停止冒泡触发事件
                e.stopPropagation();

                //二维码div位置
                var dom = $("#qrcodeDiv .detailQrCode")[0];

                //如果dom没有子Element==0，说明没生成二维码,则重新生成二维码
                if (dom.childElementCount == 0) {
                    //二维码标签DIV
                    var canvarsDiv = document.createElement("div");
                    canvarsDiv.setAttribute("id", "canvarsId");
                    $(dom).append(canvarsDiv);

                    //生成二维码
                    seajs.use(['lui/qrcode'], function (qrcode) {
                        var url = env.fn.formatUrl("/sys/anonymous/enter/token.do?method=visitToken&token=" + listviewOption.token, true);
                        qrcode.Qrcode({
                            text: url,
                            element: canvarsDiv,
                            render: 'canvas'
                        });
                    });

                    //下载按钮DIV
                    var downLoadDom = document.createElement("div");
                    downLoadDom.setAttribute("id", "downloadQrCodeId");
                    $(downLoadDom).css("display", "block");
                    $(downLoadDom).css("text-align", "center");
                    $(downLoadDom).css("color", "#666666");
                    $(downLoadDom).css("cursor", "pointer");

                    //图标
                    var downLoadIcon = document.createElement("i");
                    $(downLoadIcon).attr('class', 'download-icon');

                    //放置在dom元素中
                    $(dom).append(downLoadDom);
                    $(downLoadDom).append(downLoadIcon);
                    $(downLoadDom).append(lang['ui.dialog.downlaod2Dbarcodes']);

                    //绑定点击事件
                    $(downLoadDom).on("click", $.proxy(qrcodeSave, self));
                }
                //显示二维码框div
                $("#qrcodeDiv")[0].style.display = 'block';
            });

            this.renderSelectDialog();
        },

        // listviewOption在外面定义的全局变量
        getFieldInfos : function(){
            return listviewOption.baseInfo.fieldInfos;
        },

        drawLink: function (){
            var self = this;
            //外部链接
            var $trLinkDiv = $('<div class="trDiv"></div>');
            var $linkTitleDiv = $('<div class="tdDivLeft">' +
                '<div class="left-title">' +
                modelingLang['modelingExternalQuery.externalLink']+
                '</div>' +
                '</div>');
            var $linkDiv = $('<div class="tdDivRight">' +
                                '<div class="link">' +
                                    '<div class="link-text">' +
                                    '</div>' +
                                    '<i class="copy-icon"></i>' +
                                    '<i class="jump-icon"></i>' +
                                    '<i class="qrcode-icon"></i>' +
                                '</div>' +
                                '<div id="qrcodeDiv" >' +
                                    '<div class="detailQrCode">' +
                                    '</div>' +
                                '</div>'+
                            '</div>')
            $trLinkDiv.append($linkTitleDiv);
            $trLinkDiv.append($linkDiv);
            self.container.append($trLinkDiv);

            var linkUrl = env.fn.formatUrl("/sys/anonymous/enter/token.do?method=visitToken&token="+listviewOption.token, true);
            $linkDiv.find(".link-text").text(linkUrl);
        },

        drawLinkValidityPeriod: function (){
            var self = this;
            //链接有效期
            var $trValidityPeriodDiv = $('<div class="trDiv"></div>');
            var $linkValidityPeriodTitleDiv = $('<div class="tdDivLeft">' +
                '<div class="left-title">' +
                modelingLang['modelingExternalQuery.linkValidityPeriod']+
                '</div>' +
                '</div>');
            var $linkValidityPeriodDiv = $('<div class="tdDivRight">' +
                '<div class="">' +
                '<div class="inputselectsgl " style="width:200px;float: left;" onclick="xform_main_data_triggleSelectdatetime(event);">' +
                '<div class="input"><input name="endTime" type="text" validate="__datetime" value="" ></div>' +
                '<div class="inputdatetime"></div>' +
                '</div>' +
                '<span class="span-red">*</span>'+
                '</div>' +
                '</div>')
            $trValidityPeriodDiv.append($linkValidityPeriodTitleDiv);
            $trValidityPeriodDiv.append($linkValidityPeriodDiv);
            self.container.append($trValidityPeriodDiv);

            //初始化数据
            if( self.fdConfig.endTime){
                $linkValidityPeriodDiv.find("input[name*='endTime']").val(self.fdConfig.endTime);
            }

        },

        drawDataFilter: function (){
            var self = this;
            //数据过滤
            var $trDataFilterDiv = $('<div class="trDiv"></div>');
            var $dataFilterTitleDiv = $('<div class="tdDivLeft">' +
                '<div class="left-title">' +
                modelingLang['modelingExternalQuery.dataFilter']+
                '</div>' +
                '</div>');
            var $isDataFilterOff =  $('<label><input type="radio" name="isDataFilter" checked value="0"/>'+ modelingLang['enums.oper_log_method.close']+'</label>');
            var $isDataFilterOn =  $('<label><input type="radio" name="isDataFilter" value="1"/>'+ modelingLang['enums.oper_log_method.open']+'</label>');
            var $tdDivRight = $('<div class="tdDivRight"></div>');
            var $dataFilterDiv = $('<div class="dataFilterDiv"></div>');

            $dataFilterDiv.append($isDataFilterOff);
            $dataFilterDiv.append($isDataFilterOn);
            $tdDivRight.append($dataFilterDiv);
            $trDataFilterDiv.append($dataFilterTitleDiv);
            $trDataFilterDiv.append($tdDivRight);
            self.container.append($trDataFilterDiv);

            var $trDataFilterContentDiv = $('<div class="trDiv" style="display: none"></div>');
            var $tdDivLeftContent = $('<div class="tdDivLeft">' +
                '<div class="left-title">' +
                modelingLang['modelingExternalQuery.dataFilterConfig'] +
                '</div>' +
                '</div>');
            var $tdDivRightContent = $('<div class="tdDivRight" style="height: auto;width: 640px;max-height: 237px;overflow-y: auto;"></div>');
            $trDataFilterContentDiv.append($tdDivLeftContent);
            $trDataFilterContentDiv.append($tdDivRightContent);
            self.container.append($trDataFilterContentDiv);

            $isDataFilterOff.on("click",function () {
                $trDataFilterContentDiv.hide();
            })
            $isDataFilterOn.on("click",function () {
                $trDataFilterContentDiv.show();
            })

            var $dataFilterContentTypeDom = $('<div style="margin-top: 5px;margin-bottom: 10px;" class="whereTypediv">' +
                                                '<label class="whereTypeinput1"><input type="radio" value="0" name="dataFilterWhereType" checked="checked"/>'+ modelingLang['relation.meet.all.conditions'] +'</label>' +
                                                '<label class="whereTypeinput2"><input type="radio" value="1" name="dataFilterWhereType" />'+ modelingLang['relation.meet.any.conditions'] +'</label>' +
                                                '</div>');

            var $dataFilterContentDom = $('<table class="tb_normal field_table dataFilterContentWhereTable" width="100%">' +
                                            '<thead>' +
                                                '<tr>' +
                                                    '<td width="30%">'+ modelingLang['behavior.field.name'] +'</td>' +
                                                    '<td width="10%">'+ modelingLang['modelingAppViewincpara.fdOperator'] +'</td>' +
                                                    '<td width="15%">'+ modelingLang['relation.value.type'] +'</td>' +
                                                    '<td width="30%">'+ modelingLang['modelingAppViewopers.fdValue'] +'</td>' +
                                                    '<td width="15%">' +
                                                     modelingLang['modelingAppViewopers.fdOperation'] +
                                                '</td>' +
                                                '</tr>' +
                                            '' +
                                            '</thead>' +
                                        '</table>' +
                                        '<div class="model-mask-panel-table-create table_opera" style="margin-left: 0px">' +
                                        '<div class="table_opera_create"><span>'+modelingLang['button.add']+'</span></div>' +
                                        '</div>');
            $tdDivRightContent.append($dataFilterContentTypeDom);
            $tdDivRightContent.append($dataFilterContentDom);
            // 添加事件
            $tdDivRightContent.find(".table_opera").on("click", function (e) {
                e.stopPropagation();
                var whereCfg = {parent: self,isPreWhere:true,currentData:self.currentData};
                var whereWgt = new whereRecordGenerator.WhereRecordGenerator(whereCfg);
                whereWgt.fieldTypes = self.fieldTypes;
                var $whereTable = $tdDivRightContent.find(".dataFilterContentWhereTable");
                whereWgt.draw($whereTable);
            });

            //初始化数据
            if( self.fdConfig.isDataFilter){
                if('1' == self.fdConfig.isDataFilter){
                    $isDataFilterOn.find("input[name='isDataFilter']").trigger($.Event("click"));
                    //过滤类型
                    if('1' == self.fdConfig.dataFilterWhereType){
                        $dataFilterContentTypeDom.find('.whereTypeinput2').find("input[name='dataFilterWhereType']").trigger($.Event("click"));
                    }else{
                        $dataFilterContentTypeDom.find('.whereTypeinput1').find("input[name='dataFilterWhereType']").trigger($.Event("click"));
                    }
                    //过滤条件
                    if(self.fdConfig.dataFilterWhere){
                        var storeWhere = self.fdConfig.dataFilterWhere;
                        for (var i = 0; i < storeWhere.length; i++) {
                            var data = storeWhere[i];
                            var whereWgt = new whereRecordGenerator.WhereRecordGenerator({parent: self,isPreWhere:true,currentData:self.currentData});
                            whereWgt.fieldTypes = self.fieldTypes;
                            whereWgt.draw($tdDivRightContent.find(".dataFilterContentWhereTable"));
                            whereWgt.initByStoreData(data);
                        }
                    }

                }else{
                    $isDataFilterOff.find("input[name='isDataFilter']").trigger($.Event("click"));
                }
            }
        },

        drawTitle: function (){
            var self = this;
            //标题
            var $trDiv = $('<div class="trDiv"></div>');
            var $tdTitleDiv = $('<div class="tdDivLeft">' +
                '<div class="left-title">' +
                modelingLang['modelingExternalQuery.mobileTitle'] +
                '</div>' +
                '</div>');
            var $titleDiv = $('<div class="tdDivRight"></div>');
            var $titleSelectDiv =  $('<div class="titleSelect">' +
                                    '<input type="hidden" name="mobileTitleText"  value=""/></label>' +
                                    '<input type="hidden" name="mobileTitleField"  value=""/></label>' +
                                    ' </div>');

            var $downIcon = $( '<i class="down-icon"></i>');
            var $upIcon = $( '<i class="up-icon"></i>');
            $upIcon.hide()
            var $titleInputDiv =  $('<div class="titleInput"><div class="titleInputText">'+ modelingLang['modeling.page.choose']+'</div></div>');
            $titleInputDiv.append($downIcon);
            $titleInputDiv.append($upIcon)
            $titleSelectDiv.append($titleInputDiv);

            $titleSelectDiv.append($('<span class="span-red">*</span>'));

            var $titleSelectListDiv =  $('<div class="titleSelectList"></div>');
            var fieldInfos = self.getFieldInfos();
            fieldInfos = self.filterSubTableField(fieldInfos);
            for (var i = 0; i < fieldInfos.length; i++) {
                var $titleSelectItemDiv =  $('<div class="titleSelectItem" title="' + fieldInfos[i].text+ '" value="'+fieldInfos[i].field +'">' + fieldInfos[i].text+ '</div>');
                $titleSelectItemDiv.on("click",function () {
                    $titleSelectListDiv.hide();     //如果元素为显现,则将其隐藏
                    $downIcon.show();
                    $upIcon.hide();

                    $titleSelectDiv.find('[name="mobileTitleText"]').val($(this).text());
                    $titleSelectDiv.find('[name="mobileTitleField"]').val($(this).attr('value'));
                    $titleInputDiv.find('.titleInputText').text($(this).text());
                    $titleInputDiv.find('.titleInputText').attr("title",$(this).text());
                })
                $titleSelectListDiv.append($titleSelectItemDiv)
            }
            $titleSelectListDiv.hide()

            $titleSelectDiv.find('.titleInput').on('click',function (e) {
                e.preventDefault();
                e.stopPropagation();
                if($titleSelectListDiv.is(":hidden")){
                    $titleSelectListDiv.show();    //如果元素为隐藏,则将它显现
                    $downIcon.hide();
                    $upIcon.show();
                }else{
                    $titleSelectListDiv.hide();     //如果元素为显现,则将其隐藏
                    $downIcon.show();
                    $upIcon.hide();
                }
            });

            $titleDiv.append($titleSelectDiv);
            $titleDiv.append($titleSelectListDiv);
            $trDiv.append($tdTitleDiv);
            $trDiv.append($titleDiv);
            self.container.append($trDiv);

            //初始化数据
            $("[name='mobileTitleText']").val(self.fdConfig.mobileTitleText);
            $("[name='mobileTitleField']").val(self.fdConfig.mobileTitleField);
            $titleInputDiv.find('.titleInputText').text(self.fdConfig.mobileTitleText);
            $titleInputDiv.find('.titleInputText').attr("title",self.fdConfig.mobileTitleText);
        },

        drawDisplay: function (){
            var self = this;
            //结果显示项
            var $trDisplayDiv = $('<div class="trDiv"></div>');
            var $displayTitleDiv = $('<div class="tdDivLeft">' +
                                        '<div class="left-title">' +
                                           modelingLang['modelingExternalQuery.resultDisplayItem']+
                                        '</div>' +
                                    '</div>');

            var $displayTypeAll =  $('<label><input type="radio" name="displayType" checked value="0"/>'+modelingLang['modelingExternalQuery.showAll']+'</label>');
            var $displayTypeAny =  $('<label><input type="radio" name="displayType" value="1"/>'+modelingLang['modelingExternalQuery.showAny']+'</label>');
            var $tdDivRight = $('<div class="tdDivRight"></div>');
            var $displayDiv = $('<div class="displayDiv"></div>');

            $displayDiv.append($displayTypeAll);
            $displayDiv.append($displayTypeAny);
            $tdDivRight.append($displayDiv);
            $trDisplayDiv.append($displayTitleDiv);
            $trDisplayDiv.append($tdDivRight);
            self.container.append($trDisplayDiv);

            var $trDisplayContentDiv = $('<div class="trDiv" style="display: none"></div>');
            var $tdDivLeftContent = $('<div class="tdDivLeft">' +
                '<div class="left-title">' +
                '</div>' +
                '</div>');
            var $tdDivRightContent = $('<div class="tdDivRight">' +
                                            '<div class="">' +
                                                '<div class="inputselectsgl multiSelectDialog"  style="width: 554px;' +
                                                '    height: 30px;' +
                                                '    background: #FFFFFF;' +
                                                '    border: 1px solid #DDDDDD;' +
                                                '    border-radius: 2px;' +
                                                '    padding-left: 8px;">' +
                                                    '<input name="fd_displayField" value="" type="hidden">' +
                                                    '<div class="input">' +
                                                    '<input name="fd_displayText" value="" type="text" style="display:none;" />' +
                                                    '<span class="selectedItem"></span>' +
                                                '</div>' +
                                                '<div class="deleteAll"></div>' +
                                                '<div class="selectitem"></div>' +
                                            '</div>' +
                                            '<span class="span-red" style="position: relative;top: -6px;">*</span>'+
                                        '</div>');
            $trDisplayContentDiv.append($tdDivLeftContent);
            $trDisplayContentDiv.append($tdDivRightContent);
            self.container.append($trDisplayContentDiv);

            $displayDiv.find('input').on("click",function () {
                if($(this).val() == 0){
                    $trDisplayContentDiv.hide();
                }else{
                    $trDisplayContentDiv.show();
                    self.fixConditionValue();
                }
            })

            //初始化数据
            if( self.fdConfig.displayType){
                if('0' == self.fdConfig.displayType){
                    $displayTypeAll.find("input[name='displayType']").trigger($.Event("click"));
                }else{
                    $displayTypeAny.find("input[name='displayType']").trigger($.Event("click"));
                    self.renderDisplayBlock();
                }
            }

        },

        drawSearchCondition: function (){
            var self = this;
            //显示项
            var $trSearchConditionDiv = $('<div class="trDiv"></div>');
            var $searchConditionTitleDiv = $('<div class="tdDivLeft">' +
                                                '<div class="left-title">' +
                                                 modelingLang['modelingExternalQuery.conditionItem']+
                                                '</div>' +
                                            '</div>');
            var $SearchConditionDiv = $('<div class="tdDivRight">' +
                                        '<div class="">' +
                                        '<div class="inputselectsgl multiSelectDialog"  style="width: 554px;' +
                                            '    height: 30px;' +
                                            '    background: #FFFFFF;' +
                                            '    border: 1px solid #DDDDDD;' +
                                            '    border-radius: 2px;' +
                                            '    padding-left: 8px;">' +
                                            '<input name="fd_conditionField" value="" type="hidden">' +
                                            '<div class="input">' +
                                                '<input name="fd_conditionText" value="" type="text" style="display:none;" />' +
                                                '<span class="selectedItem"></span>' +
                                            '</div>' +
                                            '<div class="deleteAll"></div>' +
                                            '<div class="selectitem"></div>' +
                                            '</div>' +
                                            '<span class="span-red" style="position: relative;top: -6px;">*</span>'+
                                        '</div>')
            $trSearchConditionDiv.append($searchConditionTitleDiv);
            $trSearchConditionDiv.append($SearchConditionDiv);
            self.container.append($trSearchConditionDiv);

            //初始化数据
            self.renderConditionBlock();
        },

        drawDataFilterWhere: function (){
            var self = this;
            //数据范围类型
            var $whereType = $("<div class='open_rule_setting_where_type'></div>");
            var $whereTypeLeft = $("<div class='open_rule_setting_where_type_left'>"+modelingLang['listview.meet.following']+"</div>");
            var $whereTypeSelect = $("<div class='open_rule_setting_where_type_select'><select " +
                " class='inputsgl' style='width:100%'><option value='0'>"+modelingLang['listview.all']+"</option><option value='1'>"+modelingLang['listview.any']+"</option></select></div>");
            if(this.storeData.whereType){
                $whereTypeSelect.find('select').val(this.storeData.whereType)
            }
            var $whereTypeRight = $("<div class='open_rule_setting_where_type_right'>"+modelingLang['listview.condition']+"</div>");
            $whereType.append($whereTypeLeft);
            $whereType.append($whereTypeSelect);
            $whereType.append($whereTypeRight);

            $openRuleSetting.append($whereType);
            //数据规则
            var $whereContent = $("<div class='open_rule_setting_where_content'></div>");
            $openRuleSetting.append($whereContent);
            if(self.storeData.statisticsRule && self.storeData.statisticsRule.length){
                for (var i = 0; i < self.storeData.statisticsRule.length; i++) {
                    var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                    var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                    $whereRule.attr("index", rowIndex - 1);
                    var statisticsRuleSettingWhereWgt = new statisticsRuleSettingWhere({parent:self,container: $whereRule,storeData:self.storeData.statisticsRule[i] });
                    statisticsRuleSettingWhereWgt.draw();
                    self.statisticsRuleSettingWhereWgts.push(statisticsRuleSettingWhereWgt);
                    $whereContent.append($whereRule);
                }
            }

            var $whereCreate = $("<div class='open_rule_setting_where_create'><span>"+modelingLang['button.add']+"</span></div>");
            $whereCreate.on("click",function () {
                var $whereRule = $("<div class='open_rule_setting_where_rule'></div>");
                var rowIndex = $whereContent.find(".open_rule_setting_where_rule").length+1;
                $whereRule.attr("index", rowIndex - 1);
                var statisticsRuleSettingWhereWgt = new statisticsRuleSettingWhere({parent:self,container: $whereRule,storeData:{} });
                statisticsRuleSettingWhereWgt.draw();
                self.statisticsRuleSettingWhereWgts.push(statisticsRuleSettingWhereWgt);
                $whereContent.append($whereRule);
            });
            $openRuleSetting.append($whereCreate);

            //执行动作标题
            var $actionTitle = $("<div class='open_rule_setting_action_title'>"+modelingLang['listview.do.following']+"</div>");

            $openRuleSetting.append($actionTitle);
            //执行动作下拉框
            var $actionType = $("<div class='open_rule_setting_action_type'></div>");
            var $actionTypeSelectDiv = $("<div class='open_rule_setting_action_type_select'></div>");
            var $actionTypeSelect = $("<select class='inputsgl' style='width:100%'></select>");
            $actionTypeSelect.append($("<option value='0'>"+modelingLang['listview.count']+"</option>"))
            if(fieldObject.fieldType == "Double" || fieldObject.fieldType == "BigDecimal" ){
                $actionTypeSelect.append($("<option value='1'>"+modelingLang['listview.data.sum']+"</option>"))
                $actionTypeSelect.append($("<option value='2'>"+modelingLang['listview.data.average']+"</option>"))
                $actionTypeSelect.append($("<option value='3'>"+modelingLang['listview.find.the.minimum']+"</option>"))
                $actionTypeSelect.append($("<option value='4'>"+modelingLang['listview.find.the.maximum']+"</option>"))
            }
            if(this.storeData.statisticsType){
                $actionTypeSelect.val(this.storeData.statisticsType);
            }
            $actionTypeSelectDiv.append($actionTypeSelect);
            $actionType.append($actionTypeSelectDiv);
        },

        // type : where|target
        addWgt: function (wgt, type) {
            if (type === "preWhere") {
                this.whereWgtCollection.push(wgt);
            }
        },

        // type : where|target|detail
        deleteWgt: function (wgt, type) {
            var collect = [];
            if (type === "preWhere") {
                collect = this.whereWgtCollection;
            }
            for (var i = 0; i < collect.length; i++) {
                if (collect[i] === wgt) {
                    collect.splice(i, 1);
                    break;
                }
            }
        },

        formatTargetData : function(rtn){
            this.targetData = rtn.data;
            this.targetData.modelId = rtn.modelId;
        },

        //显示项、筛选项
        renderSelectDialog: function(){
            var self = this;

            $(".multiSelectDialog").on("click",function(){
                Com_EventStopPropagation();
                var $dom = $(this);
                var curVal = $dom.find("input[type='hidden']").val() || "";

                var fieldInfos = self.getFieldInfos();
                //拷贝并添加fdId
                var copyFieldInfos = [];
                var fdIdInfo = {
                    "field":"fdId",
                    "text":"ID",
                    "type":"String"
                }
                copyFieldInfos.push(fdIdInfo);
                for (var i = 0; i < fieldInfos.length; i++) {
                    copyFieldInfos.push(fieldInfos[i]);
                }

                fieldInfos = copyFieldInfos;
                fieldInfos = self.filterSubTableField(fieldInfos);
                if($dom.find("[name='fd_conditionField']").length >0) {
                    //筛选项，要过滤地址本
                    var keyData = self.getDisplayAndConditionData();
                    if ('0' != keyData.displayType){
                        if (!keyData || !keyData.displayField ||  keyData.displayField.length == 0) {
                            dialog.alert(modelingLang['modelingExternalQuery.please.select.display.first']);
                            return
                        }else{
                            //过滤，筛选项只能选择显示项已经选择的字段
                            var newFieldInfos = [];
                            var displayField = JSON.parse(keyData.displayField);
                            f:for (var i = 0; i < fieldInfos.length; i++) {
                                var field = fieldInfos[i].field;
                                field = field.replace("docCreator.fdName","docCreator");
                                for (var j = 0; j < displayField.length; j++) {
                                    if(field == displayField[j].field){
                                        newFieldInfos.push(fieldInfos[i]);
                                        continue f;
                                    }
                                }
                            }
                            fieldInfos = newFieldInfos;
                        }
                    }

                    self.fixConditionValue();
                }else{
                    //显示项
                }

                var url = "/sys/modeling/base/listview/config/dialog.jsp?type=normal";
                var modelDict = JSON.stringify(listviewOption.baseInfo.modelDict);
                dialog.iframe(url,modelingLang['modelingLang'],function(data){
                    if(!data){
                        return;
                    }
                    //创建者选中回显
                    data = data.replace("docCreator.fdName","docCreator");
                    data = $.parseJSON(data);
                    var selectedDatas = data.selected;
                    var textDatas = data.text;
                    // 补充text
                    if(selectedDatas.length === textDatas.length){
                        for(var i = 0;i < selectedDatas.length;i++){
                            selectedDatas[i].text = textDatas[i];
                        }
                    }

                    //回调
                    self.createSelectItem($dom, selectedDatas, textDatas);
                    self.fixConditionValue();
                },{
                    width : 720,
                    height : 530,
                    params : {
                        selected : curVal,
                        allField : fieldInfos,
                        modelDict: modelDict,
                    }
                });
            });
        },

        renderDisplayBlock: function() {
            var self = this;
            var renderData = self.fdConfig;
            //筛选项
            if (renderData.displayField && renderData.displayField.length > 0) {
                var displayField = JSON.parse(renderData.displayField);
                //回调
                var $dom = $("[name='fd_displayField']").closest(".multiSelectDialog");
                var textDatas = [];
                if(renderData.displayText) {
                    textDatas =  renderData.displayText.split(";");
                }
                this.createSelectItem($dom, displayField, textDatas);
            }
        },

        renderConditionBlock: function() {
            var self = this;
            var renderData = self.fdConfig;
            //筛选项
            if (renderData.conditionField && renderData.conditionField.length > 0) {
                var conditionField = JSON.parse(renderData.conditionField);
                //回调
                var $dom = $("[name='fd_conditionField']").closest(".multiSelectDialog");
                var textDatas = [];
                if(renderData.conditionText) {
                    textDatas =  renderData.conditionText.split(";");
                }
                this.createSelectItem($dom, conditionField, textDatas);
            }
        },

        createSelectItem : function($dom, selectedDatas, textDatas) {
            // 跟pc端的列表视图页面，保持一致
            $dom.find("input[type='hidden']").val(JSON.stringify(selectedDatas));
            $dom.find("input[type='text']").val(textDatas.join(";"));
            $dom.find(".selectedItem").empty();
            var outerWidth = $dom.outerWidth();
            for (var i = 0; i < selectedDatas.length; i++) {
                var item = selectedDatas[i];
                $dom.find(".selectedItem").append("<label class='selectedField' field='" + item.field + "'>" + item.text + "<i class='delIcon'></i></label>");
                //图标宽度：23, 共标签宽度62
                if (outerWidth  < (23 + 62 + this.calculateWidth($dom))) {
                    $dom.find("label[field='" + item.field + "']").remove();
                    break;
                }
            }
            this.createDropdownSelect($dom, $dom.find(".selectedItem")[0], selectedDatas, textDatas);
            this.bindDelClick($dom, selectedDatas, textDatas);
        },

        createDropdownSelect:  function($dom, context, selectedDatas, textDatas) {
            $(context).find(".dropdownSelectedField").remove();
            $dom.find(".select_view").remove();
            if (selectedDatas.length > 0) {
                var $total = $("<label class='dropdownSelectedField'>共" + selectedDatas.length + "</label>");
                $(context).append($total);
                var dropdownSelect = $('<div class="select_view hide">' +
                    '<div class="select_content_view" style="">' +
                    '<div class="select_search_view">' +
                    '<input class="select_keyword" name="keyword" placeholder="搜索">' +
                    '</div><ul class="select_result_view"></ul></div></div></div');
                $dom.append(dropdownSelect);
                for (var i = 0; i < selectedDatas.length; i++) {
                    var item = selectedDatas[i];
                    dropdownSelect.find(".select_result_view").append("<li class='dropDownSelectField' field='" + item.field + "' text='" + item.text + "'>" + item.text + "<i class='delIcon'></i></li>");
                }
                var self = this;
                var hideDropDownSelect = function() {
                    $(".select_view", self.element).each(function(index, obj){
                        if ($(obj).attr("class").indexOf("hide")  < 0) {
                            $(obj).addClass("hide");
                        }
                    });
                }

                //选项下拉
                $(context).find(".dropdownSelectedField").click(function(){
                    Com_EventStopPropagation();
                    var select = $dom.find(".select_view");
                    var originalClass = select.attr("class");
                    hideDropDownSelect();
                    select.attr("class", originalClass);
                    if (select.attr("class").indexOf("hide") > -1) {
                        select.removeClass("hide");
                        $total.addClass("showSelect");
                        select.css("width", $dom.width());
                    } else {
                        select.addClass("hide");
                        $total.removeClass("showSelect");
                    }
                });
                $(document).click(function (){
                    hideDropDownSelect();
                });
                $dom.find(".select_view").on("click", function(){
                    Com_EventStopPropagation();
                });
                this.bindDelClick($dom, selectedDatas, textDatas);

                this.bindSearch($dom);
            }
        },

        bindSearch : function($dom) {
            var self = this;
            $dom.find(".select_keyword").on("keydown",function(e){
                if(e.keyCode==13){
                    self.keyword = $(this).val();
                    self.search($dom);
                }
            });
        },

        calculateWidth : function($dom) {
            var width = 0;
            $dom.find(".selectedField").each(function(index, obj){
                width += $(obj).outerWidth() + 4;
            });
            return width;
        },

        bindDelClick: function($dom, selectedDatas, textDatas) {
            //移除选项
            var self = this;
            $(".delIcon", $dom).click(function(){
                if ($(this).prop("tagName") != "I") return;
                Com_EventStopPropagation();
                var removeItem = $(this).parent();
                var removeField = removeItem.attr("field");
                for (var i = 0; i < selectedDatas.length; i++) {
                    if (selectedDatas[i].field === removeField) {
                        selectedDatas.splice(i, 1);
                        textDatas.splice(i, 1);
                        $dom.find("input[type='hidden']").val(JSON.stringify(selectedDatas));
                        $dom.find("input[type='text']").val(textDatas.join(";"));
                        removeItem.remove();
                        $dom.find(".selectedItem").empty();
                        var outerWidth = $dom.outerWidth();
                        //显示项样式改变事件
                        if($dom.attr("data-lui-position") === "fdDisplay") {
                            var data = {"selected": selectedDatas, "text": textDatas};
                            topic.publish("modeling.selectDisplay.change",{'thisObj':$dom[0],'data':data});
                        }
                        if (selectedDatas.length > 0) {
                            for (var j = 0; j < selectedDatas.length; j++) {
                                var item = selectedDatas[j];
                                $dom.find(".selectedItem").append("<label class='selectedField' field='" + item.field + "'>" + item.text + "<i class='delIcon'></i></label>");
                                //图标宽度：23, 共标签宽度62
                                if (outerWidth  < (23 + 62 + self.calculateWidth($dom))) {
                                    $dom.find("label[field='" + item.field + "']").remove();
                                    break;
                                }
                            }
                            self.createDropdownSelect($dom, $dom.find(".selectedItem")[0], selectedDatas, textDatas);
                            self.fixConditionValue();
                            return;
                        } else {
                            $dom.find(".dropdownSelectedField ").remove();
                            $dom.find(".select_view ").remove();
                        }
                        break;
                    }
                }
                self.fixConditionValue();
            });
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

        //修复筛选项已选中的值
        fixConditionValue: function (){
            var self = this;
            var keyData = self.getDisplayAndConditionData();
            //筛选项重新初始化
            if (keyData.conditionField && keyData.conditionField.length > 0) {
                var conditionField = JSON.parse(keyData.conditionField);
                var conditionTextDatas = keyData.conditionText.split(";");;
                //移除不在显示项中的值
                if('1' ==  keyData.displayType){
                    //显示部分，需要移除筛选项中选中却不在显示项中选中的值
                    if(keyData.displayField){
                        //选中的显示项
                        var displayField = JSON.parse(keyData.displayField);
                        f:for (var i = conditionField.length-1; i >= 0 ; i--) {
                            for (var j = 0; j < displayField.length; j++) {
                                if(conditionField[i].field == displayField[j].field){
                                    continue f;
                                }
                            }

                            conditionField.splice(i,1);
                            conditionTextDatas.splice(i,1);
                        }
                    }else{
                        //没有选中的显示项，移除所有
                        conditionField = [];
                        conditionTextDatas = [];
                    }
                }
                //回调
                var $dom = $("[name='fd_conditionField']").closest(".multiSelectDialog");
                this.createSelectItem($dom, conditionField, conditionTextDatas);
            }
        },

        getDisplayAndConditionData: function(){
            var keyData = {};
            //结果显示项
            var displayType = $("[name='displayType']:checked").val();
            keyData.displayType = displayType;
            if('1' == displayType){
                var displayField = $("[name='fd_displayField']").val();
                keyData.displayField = displayField;
                var displayText = $("[name='fd_displayText']").val();
                keyData.displayText = displayText;
            }
            //查询项
            var conditionField = $("[name='fd_conditionField']").val();
            keyData.conditionField = conditionField;
            var conditionText = $("[name='fd_conditionText']").val();
            keyData.conditionText = conditionText;

            return keyData;
        },

        //提交到后台的数据
        getKeyData: function () {
            var keyData = {};
            //表单分享开关
            var isEnable = $("[name='isEnable']").val();
            keyData.isEnable = isEnable;
            if('1' == isEnable){
                //链接有效期
                var endTime = $("[name='endTime']").val();
                keyData.endTime = endTime;
                //数据过滤
                var isDataFilter = $("[name='isDataFilter']:checked").val();
                keyData.isDataFilter = isDataFilter;
                if('1' == isDataFilter){
                    var dataFilterWhereType = $("[name='dataFilterWhereType']:checked").val();
                    keyData.dataFilterWhereType = dataFilterWhereType;
                    keyData.dataFilterWhere = [];
                    keyData.dataFilterWhereFormat = [];
                    for (var i = 0; i < this.whereWgtCollection.length; i++) {
                        var whereWgt = this.whereWgtCollection[i];
                        var whereData = whereWgt.getKeyData();
                        if (!whereData) {
                            continue;
                        }
                        // 索引，用来进来记录排序，暂无用
                        whereData.idx = i;
                        keyData.dataFilterWhere.push(whereData);
                    }
                    for (var i = 0; i < keyData.dataFilterWhere.length; i++) {
                        var whereWgtKeyData = {};
                        var whereData = keyData.dataFilterWhere[i];
                        whereWgtKeyData.field = whereData.name.value;

                        whereWgtKeyData.fieldOperator = whereData.match;
                        switch (whereData.type.value) {
                            case '1':
                                whereWgtKeyData.fieldValue = "!{fix}";
                                break;
                            case '2':
                                whereWgtKeyData.fieldValue = "!{empty}";
                                break;
                            case '3':
                                whereWgtKeyData.fieldValue = "!{dynamic}";
                                break;
                            case '4':
                                whereWgtKeyData.fieldValue = "!{formula}";
                                break;
                            default:
                                whereWgtKeyData.fieldValue = "!{fix}";
                                break;
                        }
                        whereWgtKeyData.fieldInputValue = whereData.expression.value;
                        whereWgtKeyData.fieldValueText = whereData.expression.text;
                        keyData.dataFilterWhereFormat.push(whereWgtKeyData);
                    }
                }
                //移动端标题
                var mobileTitleText = $("[name='mobileTitleText']").val();
                var mobileTitleField = $("[name='mobileTitleField']").val();
                keyData.mobileTitleText = mobileTitleText;
                keyData.mobileTitleField = mobileTitleField;

                //结果显示项
                var displayType = $("[name='displayType']:checked").val();
                keyData.displayType = displayType;
                if('1' == displayType){
                    var displayField = $("[name='fd_displayField']").val();
                    keyData.displayField = displayField;
                    var displayText = $("[name='fd_displayText']").val();
                    keyData.displayText = displayText;
                }
                //查询项
                var conditionField = $("[name='fd_conditionField']").val();
                keyData.conditionField = conditionField;
                var conditionText = $("[name='fd_conditionText']").val();
                keyData.conditionText = conditionText;
            }

            return keyData;
        },
        //后台数据渲染方法
        initByStoreData: function (sd) {
            if(sd){
                var keyData = sd.fdConfig;
                //表单分享开关
                if("1"==keyData.isEnable){
                    $(".external-query-on").css("display","block");
                }else{
                    $(".external-query-on").css("display","none");
                }

            }
        },

    });

    window.ExternalQueryValidate = {
        validate: function (cfg) {
            if (!cfg) {
                return modelingLang['modeling.configure.first'];
            }
            var keyData = cfg;
            if("1" == keyData.isEnable) {
                if (!keyData.endTime) {
                    return modelingLang['modeling.link.validity.period.must'];
                }
                if (!keyData.mobileTitleText) {
                    return modelingLang['modelingExternalQuery.please.select.mobileTitle'];
                }
                if (!keyData.mobileTitleField) {
                    return modelingLang['modelingExternalQuery.please.select.mobileTitle'];
                }
                if('1' ==  keyData.displayType){
                    if (!keyData.displayField) {
                        return modelingLang['modelingExternalQuery.resultDisplayItem.must'];
                    }
                    if (!keyData.displayText) {
                        return modelingLang['modelingExternalQuery.resultDisplayItem.must'];
                    }
                }

                if (!keyData.conditionField) {
                    return modelingLang['modelingExternalQuery.conditionItem.must'];
                }
                if (!keyData.conditionText) {
                    return modelingLang['modelingExternalQuery.conditionItem.must'];
                }
            }
        }
    };

    //下载二维码方式
    window.qrcodeSave = function () {
        var dom = $("#qrcodeDiv .detailQrCode")[0];
        var canvas = $(dom).find("canvas");
        var name = lang['ui.dialog.2Dbarcodes'], type = "png";
        if (window.navigator.msSaveBlob) {
            window.navigator.msSaveBlob(canvas[0].msToBlob(), name);
        } else {
            var imageData = canvas[0].toDataURL(type);
            imageData = imageData.replace(_fixType(type),
                'image/octet-stream');
            var save_link = document.createElementNS(
                "http://www.w3.org/1999/xhtml", "a");
            save_link.href = imageData;
            save_link.download = name;
            var ev = document.createEvent("MouseEvents");
            ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0,
                false, false, false, false, 0, null);
            save_link.dispatchEvent(ev);
            ev = null;
            delete save_link;
        }
    };

    window.copyText = function ($this, text) {
        var $input = $("<input style='position: absolute;' />");//创建input对象
        $this.after($input);//添加元素
        $input.val(text);
        $input.select();
        try {
            var flag = document.execCommand("copy");//执行复制
        } catch (eo) {
            var flag = false;
        }
        $input.remove()//删除元素
        return flag;
    };

    window.updateLinkText = function () {
        var linkUrl = env.fn.formatUrl("/sys/anonymous/enter/token.do?method=visitToken&token="+listviewOption.token, true);
        $(".link-text").text(linkUrl);
        //是否有效token
        if (listviewOption.token) {
            //显示可使用icon图标
            $(".copy-icon").css("background-image", "");
            $(".jump-icon").css("background-image", "");
            $(".qrcode-icon").css("background-image", "");

            //默认鼠标事件反应
            $(".copy-icon").css("pointer-events","auto");
            $(".jump-icon").css("pointer-events","auto");
            $(".qrcode-icon").css("pointer-events","auto");
        } else {
            //显示不可使用icon图标
            $(".copy-icon").css("background-image", "url(./externalShare/images/copy-default.png)");
            $(".jump-icon").css("background-image", "url(./externalShare/images/jump-default.png)");
            $(".qrcode-icon").css("background-image", "url(./externalShare/images/qrcode-default.png)");

            //不对鼠标事件反应
            $(".copy-icon").css("pointer-events","none");
            $(".jump-icon").css("pointer-events","none");
            $(".qrcode-icon").css("pointer-events","none");
            //移除二维码内容，更新token之后重新生成
            $("#canvarsId").remove();
            $("#downloadQrCodeId").remove();
        }
    }

    window._fixType =function (type) {
        var r = type.match(/png|jpeg|bmp|gif/)[0];
        return 'image/' + r;
    };

    exports.ExternalQuery = ExternalQuery;
});