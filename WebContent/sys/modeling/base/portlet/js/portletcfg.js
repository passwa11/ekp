define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    //公式定义器
    // var viewProps_portlet = require("sys/modeling/base/portlet/js/viewProps_portlet");
    var portletCfgLink = require("sys/modeling/base/portlet/js/portletCfgLink");
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    var format=require("sys/modeling/base/portlet/js/format");
    var lang = require("lang!sys-modeling-base");

    var PortletCfg = base.Container.extend({
        //切换系统数据格式切换
        systemFormat :{
            "ekp":{
                "data.list":"sys.ui.classic",
                "sys.ui.classic":"sys.ui.classic",

                "sys.ui.listtable":"sys.ui.listtable",
                "data.table":"sys.ui.listtable",

                "sys.ui.image.desc":"sys.ui.image.desc",
                "data.person":"sys.ui.image.desc",
            },
            "cloud":{
                "sys.ui.classic":"data.list",
                "data.list":"data.list",

                "sys.ui.listtable":"data.table",
                "data.table":"data.table",

                "sys.ui.image.desc":"data.person",
                "data.person":"data.person",
            },
            "showButton":[
                "data.list",
                "sys.ui.classic",
                "sys.ui.listtable",
                "data.table",
                "sys.ui.image.desc",
                "data.person",
            ]
        },

        /**
         * 加载数据
         */
        loadFormatCfg: function () {
            var self = this;
         /*   $.ajax({
                url: "./portlet/js/format.js",
                method: 'GET',
                dataType: "json",
                async: false
            }).success(function (data) {
                self.formatCfgList = data;
                console.log("加载配置模版::", self.formatCfgList)
            });*/
            self.formatCfgList=format.getFormat();
            var url = Com_Parameter.ContextPath +
                "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + self.modelId;
            $.ajax({
                url: url,
                method: 'GET',
                async: false
            }).success(function (resultStr) {
                var result = JSON.parse(resultStr);
                self.modelWidget = result.data;
                console.debug("加载字段::", self.modelWidget)
            })
        },
        /**
         * 绑定展现方式触发事件
         */
        bindEvent: function () {
            console.debug("绑定展现方式触发事件");
            var self = this;
            //展现方式
            self.$bc.find("[data-select-btn='fdFormat']").on("click", function (e) {
                dialog.iframe("/sys/modeling/base/portlet/import/format_select.jsp" +
                    "?checked=" + self.formData.format
                    + "&fdDevice=" + self.fdDevice, lang['portal.select.type'], function (value) {
                    if (value == null)
                        return;
                    self.forSystem = "ekp";
                    if ( self.formData.format !=value){
                        self.form = {
                            fdFormat: "",
                            fdFormatMapping: {},
                            fdOperationMapping: {},
                            fdVarMapping: {}
                        }
                    }
                    console.debug("展现方式改变::", value);

                    self.draw(value);
                }, {
                    width: 540,
                    height: 540
                });
            });

            self.$sc.find("input[name='fdForSystem']").on("click", function (e) {
                var forSystem = this.value;
                //切换所属系统时，两个部件的数据格式不一致，需要清空原有配置
                if ( self.forSystem !=forSystem){
                    self.form = {
                        fdFormat: "",
                        fdFormatMapping: {},
                        fdOperationMapping: {},
                        fdVarMapping: {}
                    }
                }
                self.forSystem = forSystem;
                self.formData.format = self.systemFormat[forSystem][self.formData.format];
                self.draw(self.formData.format);
            });
        },

        initProps: function ($super, cfg) {
            $super(cfg);
            //参数传递
            console.debug("参数传递::", cfg);
            this.$bc = cfg.baseContainer;
            this.$sc = cfg.settingContainer;
            this.modelId = cfg.modelId;
            this.form = cfg.form;
            this.fdDevice = cfg.fdDevice;
            this.forSystem = cfg.form.fdForSystem?cfg.form.fdForSystem:"ekp"; //默认ekp系统
            //加载配置数据
            this.loadFormatCfg();
            //初始化公式定义器
            formulaBuilder.initFieldList(cfg.xformId);
            //初始化
            this.bindEvent();
            this.draw(cfg.form.fdFormat)

        },
        startup: function ($super, cfg) {
            $super(cfg);

        },

        draw: function (fdFormat) {
            console.debug("开始绘制 ::", fdFormat);
            if (!this.formData) {
                this.formData = {}
            }
            if (!fdFormat) {
                return
            }
            this.formData.format = fdFormat;
            var $forSystem = this.$sc.find(".forSystem");
            if(this.systemFormat["showButton"].indexOf(fdFormat)>-1){
                $forSystem.show();
                $forSystem.find("input[name=fdForSystem]:checked").prop("checked", false);
                $forSystem.find("input[value="+this.forSystem+"]").prop("checked",true);
            }else {
                $forSystem.hide();
            }
            this.formatCfg = this.formatCfgList[fdFormat];
            this.$bc.find("[mdlng-rltn-data=fdFormatText]").html(this.formatCfg.name);
            this.$bc.find("[mdlng-rltn-data=fdFormat]").val(this.formatCfg.id);

            console.debug("draw formatCfg ::", this.formatCfg);
            //预留var
            // var vm = this.formatCfg.varMapping;
            // this.buildVarsMapping(vm);

            var dm = this.formatCfg.dataMapping;
            if (this.forSystem === "ekp"){//画ekp部件数据映射
                this.buildDataMapping(dm);
            }else {//画mk部件数据映射
                this.buildMKDataMapping(dm);
            }
            var om = this.formatCfg.operationMapping;
            this.buildOperationMapping(om);
        },

        /**
         * ekp数据映射绘制 dataMapping
         * @param data
         */
        buildDataMapping: function (data) {
            console.debug("数据映射绘制 ::", data);
            var self = this;
            var dmClass = "portlet-data-mapping";

            //构建容器
            var $dmTr = self.$sc.find("[data-portlet-area=\"dataMapping\"]");
            var $valTab = $dmTr.find("[data-portlet-area=\"dataMapping-val-table\"]");
            //清空
            console.debug("原数据清空 》》》", self.$sc.find("." + dmClass).length);
            self.$sc.find("." + dmClass).remove();
            console.debug("原数据清空 《《《", self.$sc.find("." + dmClass).length);
            $.each(data, function (idx, item) {
                if (item.type == "view") {
                    //链接类型需要特殊处理
                    self.buildDataMapping_view(item)
                } else {
                    console.debug("数据映射绘制 item::", item);
                    var $tr = $("<tr />");
                    $tr.addClass(dmClass);
                    $tr.attr("data-portlet-data-key", item.key);
                    //标题
                    $("<td class='portlet-mapping-title'/>").text(item.title).appendTo($tr);
                    //值
                    if (item.key === "image"||item.key === "icon") {
                        var $dataVal = self.buildAttachmentSelect(item);
                        $("<td class='portlet-mapping-title' />").append($dataVal).appendTo($tr);
                    }else {
                        //#144719 暂时先对时间轴列表的时间控件做限制处理，只能选到日期或者日期时间的控件
                        if(self.formData.format ==="sys.ui.card" && item.key === "created") {
                            var $dataVal = self.buildCreatedSelect(item);
                            $("<td class='portlet-mapping-title' />").append($dataVal).appendTo($tr);
                        }else {
                            var $dataVal = self.buildSpliceInput(item.key, "String");
                            $("<td class='portlet-mapping-title' />").append($dataVal).appendTo($tr);
                        }
                    }
                    $valTab.append($tr);
                }
            });
            $dmTr.show();

        },
        /**
         * mk数据映射绘制 buildMKDataMapping
         * @param data
         */
        buildMKDataMapping: function (data) {
            console.debug("数据映射绘制 ::", data);
            var self = this;
            var dmClass = "portlet-data-mapping";

            //构建容器
            var $dmTr = self.$sc.find("[data-portlet-area=\"dataMapping\"]");
            var $valTab = $dmTr.find("[data-portlet-area=\"dataMapping-val-table\"]");
            //清空
            console.debug("原数据清空 》》》", self.$sc.find("." + dmClass).length);
            self.$sc.find("." + dmClass).remove();
            console.debug("原数据清空 《《《", self.$sc.find("." + dmClass).length);
            $.each(data, function (idx, item) {
                if (item.type == "view") {
                    //链接类型需要特殊处理
                    self.buildDataMapping_view(item)
                } else {
                    console.debug("数据映射绘制 item::", item);
                    var $tr = $("<tr />");
                    $tr.addClass(dmClass);
                    $tr.attr("data-portlet-data-key", item.key);
                    //标题
                    if (self.formData.format == "data.table" || self.formData.format == "data.person"){
                        var orgValue = self.form.fdFormatMapping[item.key];
                        var title = item.title;
                        if (orgValue){
                            title = orgValue.key ;
                        }
                        var inputTitle = "<div style='margin-bottom: 20px'><input type='text' name='mk-table-title' style='width: 100px' value='"+title+"'/></div>";
                        $("<td class='portlet-mapping-title'/>").html(inputTitle).appendTo($tr);
                    }else {
                        $("<td class='portlet-mapping-title'/>").text(item.title).appendTo($tr);
                    }
                    //值
                    if (item.key === "image"||item.key === "icon") {
                        var $dataVal = self.buildAttachmentSelect(item);
                        $("<td class='portlet-mapping-title' />").append($dataVal).appendTo($tr);
                    }else if (item.key === "created"){
                        //，#166216 对MK门户数据类型严格保持一致
                        var $dataVal = self.buildCreatedSelect(item);
                        $("<td class='portlet-mapping-title' />").append($dataVal).appendTo($tr);
                    }else if (item.key === "creator"){
                        //#166216 对MK门户地址本数据类型严格保持一致
                        var $dataVal = self.buildCreatorSelect(item);
                        $("<td class='portlet-mapping-title' />").append($dataVal).appendTo($tr);
                    } else {
                        var $dataVal = self.buildSpliceInput(item.key, "String");
                        $("<td class='portlet-mapping-title' />").append($dataVal).appendTo($tr);
                    }


                    $valTab.append($tr);
                }
            });
            $dmTr.show();

        },
        buildDataMapping_view: function (viewItem) {
            console.debug("数据映射[跳转视图]绘制 ::", viewItem);
            var self = this;
            self.formData.dataMapping = {};

            if (viewItem != null) {
                var viewItemData = self.form.fdFormatMapping[viewItem.key];
                var cfg = {
                    "mainContainer": self.$sc,
                    "modelId": self.modelId,
                    "viewType": "1",
                    "trClass": "portlet-data-mapping",
                    "title": viewItem.title,
                    "fdDevice": self.fdDevice
                };
                if (viewItemData) {
                    cfg.data = viewItemData.value;
                }
                self.formData.dataMapping[viewItem.key] = new portletCfgLink.CfgLink(cfg);
            }
        },
        getDataMapping: function () {
            var self = this;
            var dmClass = "portlet-data-mapping";

            //构建容器
            var $dmTr = self.$sc.find("[data-portlet-area=\"dataMapping\"]");
            var $valTab = $dmTr.find("[data-portlet-area=\"dataMapping-val-table\"]");

            var dataMapping = {};
            if (this.forSystem === "cloud" && this.formData.format == "data.table"){
                dataMapping =  self.getMkTableDataMapping($valTab,dataMapping);
            }else if (this.forSystem === "cloud" && this.formData.format == "data.person"){
                dataMapping =  self.getMkPersonDataMapping($valTab,dataMapping);
            } else {
            $.each($valTab.find("." + dmClass), function (idx, item) {
                var key = $(item).attr("data-portlet-data-key");
                if (key === "image"||key === "icon") {
                    var v = {
                        "key": key,
                        "value": $(item).find("option:selected").val()
                    };
                    dataMapping[key] = (v)
                }else if (self.forSystem === "cloud" && key === "created" ){
                    var v = {
                        "key": key,
                        "value": $(item).find("option:selected").val()
                    };
                    dataMapping[key] = (v)
                }else if (self.forSystem === "cloud" && key === "creator"){
                    var v = {
                        "key": key,
                        "value": $(item).find("option:selected").val()
                    };
                    dataMapping[key] = (v)
                }else {
                    //#144719 暂时先对时间轴列表的时间控件限制处理，只能选到日期或者日期时间的控件
                    if(self.formData.format ==="sys.ui.card" && key === "created") {
                        var v = {
                            "key": key,
                            "value": $(item).find("option:selected").val()
                        };
                        dataMapping[key] = (v)
                    }else {
                        var value = $(item).find("[name='" + key + "']").val();
                        var v = {
                            "key": key,
                            "value": encodeURIComponent(value)
                        };
                        dataMapping[key] = (v)
                    }
                }

            });
            }

            var data = self.formatCfg.dataMapping;
            $.each(data, function (idx, item) {
                if (item.type === "view") {
                    var v = {
                        "key": item.key,
                        "value": self.formData.dataMapping[item.key].getKeyData()
                    };
                    dataMapping[item.key] = (v);
                }
            });
            console.debug("getDataMapping ::", dataMapping);
            return dataMapping;
        },
        getMkPersonDataMapping:function ($valTab,dataMapping){
            $.each($valTab.find(".portlet-data-mapping"), function (idx, item) {
                var key = $(item).attr("data-portlet-data-key");
                var title =  $(item).find("[name='mk-table-title']").val();
                if (key === "image") {
                    var v = {
                        "key": title,
                        "value": $(item).find("option:selected").val()
                    };
                    dataMapping[key] = (v)
                }else {
                    var value = $(item).find("[name='" + key + "']").val();
                    var v = {
                        "key": title,
                        "value": encodeURIComponent(value)
                    };
                    dataMapping[key] = (v)
                }

            });
            return dataMapping;
        },

        getMkTableDataMapping:function ($valTab,dataMapping){
            $.each($valTab.find(".portlet-data-mapping"), function (idx, item) {
                var key = $(item).attr("data-portlet-data-key");
                var title =  $(item).find("[name='mk-table-title']").val();
                var value = $(item).find("[name='" + key + "']").val();
                var v = {
                    "key": title,
                    "value": encodeURIComponent(value)
                };
                dataMapping[key] = (v)
            });
            return dataMapping;
        },
        /**
         * 操作绘制-【更多。。。】 operationMapping
         * @param data
         */
        buildOperationMapping: function (data) {
            console.debug("操作绘制 ::", data);
            var self = this;
            var dmClass = "portlet-operation-mapping";
            self.formData.operationMapping = {};

            console.debug("原数据清空 》》》", self.$sc.find("." + dmClass).length);
            self.$sc.find("." + dmClass).remove();
            console.debug("原数据清空 《《《", self.$sc.find("." + dmClass).length);

            $.each(data, function (idx, item) {
                console.debug("操作绘制 item::", item);
                if (item.inputGenerator === "listview") {
                    var cfg = {
                        "mainContainer": self.$sc,
                        "modelId": self.modelId,
                        "viewType": "0",
                        "trClass": "portlet-operation-mapping",
                        "title": item.name,
                        "fdDevice": self.fdDevice
                    };
                    //初始化
                    var formDataItem = self.form.fdOperationMapping[item.key];
                    if (formDataItem) {
                        cfg.data = formDataItem.value;
                    }
                    self.formData.operationMapping[item.key] = new portletCfgLink.CfgLink(cfg);
                    var $more =  self.formData.operationMapping[item.key].$ele;
                    $more.append(" <td><span class=\"validateInfo-flag1\">*</span></td>>");
                }
            })

        },
        getOperationMapping: function () {
            var self = this;
            var operationMapping = {};
            if (self.formatCfg) {
                var data = self.formatCfg.operationMapping;
                $.each(data, function (idx, item) {
                    if (item.inputGenerator === "listview") {
                        var v = {
                            "key": item.key,
                            "value": self.formData.operationMapping[item.key].getKeyData()
                        };
                        operationMapping[item.key] = (v);
                    }
                });
            }
            console.debug("getOperationMapping ::", operationMapping);
            return operationMapping;
        },
        //------------------工具方法

        buildListView: function (cfg) {
            return new portletCfgLink.CfgLink(cfg);
        },
        buildAttachmentSelect: function (item) {
            console.debug("绘制附件选择 ::", item);
            var self = this;
            var $select = $("<select class='portlet_select' style=\"width:50%\"></selct>");
            $select.append("<option value=''>"+lang['relation.please.choose']+"</option>");
            var selectedOptionId = self.form.fdFormatMapping[item.key];
            for (var controlId in self.modelWidget) {
                var info = self.modelWidget[controlId];
                //#144704
                var fullLabel = info.fullLabel||info.label;
                //144597 屏蔽标题图片
                if (info.atttype ==="uploadimg"){
                    continue;
                }
                //#144717 暂时屏蔽明细表内的图片控件和附件
                if (info.atttype === "docimg" && info.name.indexOf(".") == -1) {
                    if (selectedOptionId && info.name === selectedOptionId.value) {
                        $select.append("<option title='" + fullLabel + "' selected value='" + info.name + "' data-property-type='" + info.type + "' >" + info.label + "</option>");
                    } else {
                        $select.append("<option title='" + fullLabel + "' value='" + info.name + "' data-property-type='" + info.type + "'  >" + info.label + "</option>");
                    }
                }
            }
            return $select
        },
        buildCreatedSelect: function (item) {
            console.debug("绘制时间选择 ::", item);
            var self = this;
            var $select = $("<select class='portlet_select' style=\"width:50%\"></selct>");
            $select.append("<option value=''>"+lang['relation.please.choose']+"</option>");
            var selectedOptionId = self.form.fdFormatMapping[item.key];
            for (var controlId in self.modelWidget) {
                var info = self.modelWidget[controlId];
                var fullLabel = info.fullLabel||info.label;
                if (info.name.indexOf(".") == -1){
                if (info.type === "Date" || info.type === "DateTime") {
                    if (selectedOptionId && info.name === selectedOptionId.value) {
                        $select.append("<option title='" + fullLabel + "' selected value='" + info.name + "' data-property-type='" + info.type + "' >" + info.label + "</option>");
                    } else {
                        $select.append("<option title='" + fullLabel + "' value='" + info.name + "' data-property-type='" + info.type + "'  >" + info.label + "</option>");
                    }
                }
                }
            }
            return $select
        },
        buildCreatorSelect: function (item) {
            console.debug("绘制地址本选择 ::", item);
            var self = this;
            var $select = $("<select class='portlet_select' style=\"width:50%\"></selct>");
            $select.append("<option value=''>"+lang['relation.please.choose']+"</option>");
            var selectedOptionId = self.form.fdFormatMapping[item.key];
            for (var controlId in self.modelWidget) {
                var info = self.modelWidget[controlId];
                var fullLabel = info.fullLabel||info.label;
                if (info.name.indexOf(".") == -1) {
                    if (info.type === "com.landray.kmss.sys.organization.model.SysOrgPerson" || info.type === "com.landray.kmss.sys.organization.model.SysOrgElement") {
                        if (selectedOptionId && info.name === selectedOptionId.value) {
                            $select.append("<option title='" + fullLabel + "' selected value='" + info.name + "' data-property-type='" + info.type + "' >" + info.label + "</option>");
                        } else {
                            $select.append("<option title='" + fullLabel + "' value='" + info.name + "' data-property-type='" + info.type + "'  >" + info.label + "</option>");
                        }
                    }
                }
            }
            return $select
        },
        buildSpliceInput: function (key, type) {
            var self = this;
            var $ele = $("<div class='modeling_formula modeling_splice_input'/>");
            var $val = $("<input type=\"hidden\" name=\"" + key + "\"/>");
            var $text = $("<input type=\"text\" name=\"" + key + "_name\" class=\"inputsgl\" style=\"width:50%\" readonly=\"\">");
            var $span = $("<span class=\"highLight\"><a onclick='return false' href=\"javascrip:void(0);\">"+lang['behavior.select']+"</a></span>");
            //初始化
            var orgVal = self.form.fdFormatMapping[key];
            if (orgVal) {
                var orgVal = decodeURIComponent(orgVal.value);
                $val.val(orgVal);
                $text.val(self.formatSpliceInputText(orgVal, true));
            }
            $ele.append($val);
            $ele.append($text);
            $ele.append($span);
            var data = self.deleteDetailField(self.modelWidget);
            $span.on("click", function (e) {
                var dialogParams = {
                    data: data,
                    oldData: $val.val()
                };
                dialog.iframe("/sys/modeling/base/relation/import/relation_return.jsp", lang['portal.set.para'], function (value) {
                    if (value == null)
                        return;
                    $text.val(self.formatSpliceInputText(value));
                    $val.val(JSON.stringify(value));
                }, {
                    width: 545,
                    height: 600,
                    params: dialogParams
                });
            })
            return $ele;

        },
        deleteDetailField:function (data){
            var newData ={};
            for (var key in data){
                var item = data[key];
                if(key.indexOf(".")==-1){
                   newData[key] = item;
                }
            }
            return newData;
        },
        formatSpliceInputText: function (value, isStr) {
            if (!value) {
                return "";
            }
            if (isStr) {
                value = JSON.parse(value);
            }
            var str = "";
            for (var i in value) {
                var v = value[i]
                var fullLabel = v.fullLabel||v.label;
                if (fullLabel) {
                    if (v.type === "_inputType") {
                        str += fullLabel;
                    } else {
                        str += "【" + fullLabel + "】";
                    }
                }
            }
            return str;
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
            if (this.formatCfg) {
                var keyData = {
                    varMapping: "",
                    operationMapping: JSON.stringify(this.getOperationMapping()),
                    dataMapping: JSON.stringify(this.getDataMapping())
                };
                return keyData;
            }
            return null;

        },
        initByStoreData: function ($super, storeData) {
            //没有初始化，自动初始化
        }
    });

    exports.PortletCfg = PortletCfg;
});