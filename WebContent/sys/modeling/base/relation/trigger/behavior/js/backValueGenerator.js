/**
 * 前置查询返回值
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");

    var TEMP_HTML="<div class=\"muiPerformanceDropdownBox\">\n" +
        "                <div class=\"input\">\n" +
        "                    <div class=\"selectNone\">\n" +
                                 modelingLang['relation.please.choose']+
        "                    </div>\n" +
        "                    <span class=\"selectedItem\" style='display: none'></span>\n" +
        "                </div>\n" +
        "<i class=\"muiPerformanceDropdownBoxIcon\"></i>" +
        "<div class=\"select_view\">" +
        "</div>" +
        "            </div>";

    var BackValueGenerator = base.Component.extend({

        initProps: function ($super, cfg) {
            $super(cfg);
            this.parent = cfg.parent;
            this.$table = cfg.$table;
            this.valueName = "fd_" + parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            this.sourceData = cfg.sourceData;
            this.currentDatas = [];
            this.selectedDatas = {};
        },
        draw: function () {
            if(this.sourceData && this.sourceData.data){
                for (var key in this.sourceData.data) {
                    var item = this.sourceData.data[key];
                    var type = item.type.toLowerCase();
                    if(item.name.indexOf(".")>-1){
                        continue;
                    }
                    //只支持数值类型
                    if(type == "long" || type == "integer"
                        || type == "double" || type == "bigdecimal" || type == "number"){
                        var filed = {};
                        filed["value"]=item.name;
                        filed["type"]=item.type;
                        filed["text"]=item.label;
                        filed["fullLabel"]=item.fullLabel || item.label;
                        this.currentDatas.push(filed);
                    }
                }
            }
            this.$countHtml = $("<div><input type='checkbox' name='count_"+this.valueName+"' value='1'>"+modelingLang['modeling.behavior.preQuery.totalRows']+"</div>");
            this.$table.append(this.$countHtml);
            this.$firstRowHtml = $("<div><input type='checkbox' name='firstRow_"+this.valueName+"' value='2'>"+modelingLang['modeling.behavior.preQuery.firstRow']+"</div>");
            this.$table.append(this.$firstRowHtml);

            this.$sumHtml = $("<div></div>");
            var sumCheckBoxHtml = $("<div class='behaviorCheckBox'><input type='checkbox' name='sum_"+this.valueName+"' value='3'>"+modelingLang['modeling.behavior.preQuery.sum']+"</div>");
            this.$sumHtml.append(sumCheckBoxHtml);
            this.$sumHtml.append(TEMP_HTML);
            this.buildDropDown(this.$sumHtml,"sum");
            this.$table.append(this.$sumHtml);

            this.$maxHtml = $("<div></div>");
            var maxCheckBoxHtml = $("<div class='behaviorCheckBox'><input type='checkbox' name='max_"+this.valueName+"' value='4'>"+modelingLang['modeling.behavior.preQuery.max']+"</div>");
            this.$maxHtml.append(maxCheckBoxHtml);
            this.$maxHtml.append(TEMP_HTML);
            this.buildDropDown(this.$maxHtml,"max");
            this.$table.append(this.$maxHtml);

            this.$minHtml = $("<div></div>");
            var minCheckBoxHtml = $("<div class='behaviorCheckBox'><input type='checkbox' name='min_"+this.valueName+"' value='5'>"+modelingLang['modeling.behavior.preQuery.min']+"</div>");
            this.$minHtml.append(minCheckBoxHtml);
            this.$minHtml.append(TEMP_HTML);
            this.buildDropDown(this.$minHtml,"min");
            this.$table.append(this.$minHtml);

            this.$avgHtml = $("<div></div>");
            var avgCheckBoxHtml =  $("<div class='behaviorCheckBox'><input type='checkbox' name='avg_"+this.valueName+"' value='6'>"+modelingLang['modeling.behavior.preQuery.avg']+"</div>");
            this.$avgHtml.append(avgCheckBoxHtml);
            this.$avgHtml.append(TEMP_HTML);
            this.buildDropDown(this.$avgHtml,"avg");
            this.$table.append(this.$avgHtml);
        },
        buildDropDown: function ($dom,type) {
            var self = this;
            //回调
            self.createSelectItem($dom,type);
        },

        createSelectItem : function($dom,type) {
            // 创建已选择字段
            this.createSelectedField($dom,type);
            //创建下拉选项
            this.createDropdownSelect($dom,type);
        },

        calculateWidth : function($dom) {
            var width = 0;
            $dom.find(".selectedField").each(function(index, obj){
                width += $(obj).outerWidth() + 4;
            });
            return width;
        },

        destroy : function($super) {
            this.$table.empty();
            $super();
        },

        createSelectedField: function ($dom,type) {
            var self = this;
            $dom.find(".selectedItem").empty();
            var outerWidth = $dom.find(".muiPerformanceDropdownBox").outerWidth();
            if (self.selectedDatas[type] && self.selectedDatas[type].length > 0) {
                for (var j = 0; j < self.selectedDatas[type].length; j++) {
                    var item = self.selectedDatas[type][j];
                    $dom.find(".selectedItem").append("<label class='selectedField muiHrArchivesPreviewBoxLabel' data-field='" + item.value + "'>" + item.text + "<i class='delIcon'></i></label>");
                    //图标宽度：23, 共标签宽度62
                    if (outerWidth < (23 + 62 + self.calculateWidth($dom))) {
                        $dom.find("label[data-field='" + item.value + "']").remove();
                        break;
                    }
                }
                var $total = $("<label class='dropdownSelectedField'>共" + self.selectedDatas[type].length + "</label>");
                $($dom.find(".selectedItem")[0]).append($total);
                self.bindDelClick($dom,type);
                $dom.find("input[name='"+type+"_"+this.valueName+"']").prop("checked","checked");

                $dom.find(".selectedItem").css("display","");
                $dom.find(".selectNone").css("display","none");
            }else{
                $dom.find(".selectedItem").css("display","none");
                $dom.find(".selectNone").css("display","");
            }
        },
        bindDelClick: function($dom,type) {
            //移除选项
            var self = this;
            $(".delIcon", $dom).click(function(){
                if ($(this).prop("tagName") != "I") return;
                Com_EventStopPropagation();
                var removeItem = $(this).parent();
                var removeField = removeItem.data("field");
                if(self.selectedDatas[type]){
                    for (var i = 0; i < self.selectedDatas[type].length; i++) {
                        if (self.selectedDatas[type][i].value === removeField) {
                            removeItem.remove();
                            self.selectedDatas[type].splice(i, 1);
                            self.createSelectedField($dom,type);
                            $dom.find(".select_result_view>li input").each(function(index,obj){
                                var selectItem = $(this).closest("li");
                                var selectField = selectItem.data("field");
                                if(removeField === selectField){
                                    $(obj).prop("checked",false);
                                    return;
                                }
                            });
                            break;
                        }
                    }
                }
            });
        },

        isExistInSelected: function (value,type) {
            var isExist = false;
            if(this.selectedDatas[type]){
                for (var j = 0; j < this.selectedDatas[type].length; j++) {
                    var select = this.selectedDatas[type][j];
                    if (value === select.value) {
                        isExist = true;
                        break;
                    }
                }
            }
            return isExist;
        },
        createDropdownSelect:  function($dom,type) {
            var self = this;
            if(this.currentDatas.length > 0){
                var context = $dom.find(".muiPerformanceDropdownBox")[0];
                $(context).find(".select_view").empty();
                var dropdownSelect = $('<div class="select_content_view" style="">' +
                    '<div class="select_search_view">' +
                    '<input class="select_keyword" name="keyword" placeholder="搜索">' +
                    '</div><ul class="select_result_view"></ul></div></div>');
                for (var i = 0; i < this.currentDatas.length; i++) {
                    var item = this.currentDatas[i];
                    var isExist = self.isExistInSelected(item.value,type);
                    if (isExist) {
                        dropdownSelect.find(".select_result_view").append("<li class='dropDownSelectField' data-field='" + item.value + "' text='" + item.text + "'><label class='muiPerformanceManagementCheckbox'><input type=\"checkbox\" checked=\"checked\" ><span>" + item.text + "</span></label></li>");
                    }else{
                        dropdownSelect.find(".select_result_view").append("<li class='dropDownSelectField' data-field='" + item.value + "' text='" + item.text + "'><label class='muiPerformanceManagementCheckbox'><input type=\"checkbox\" ><span>" + item.text + "</span></label></li>");
                    }
                }
                $(context).find(".select_view").append(dropdownSelect);

                // $(context).find(".select_content_view").off("mouseleave").on("mouseleave",function (e){
                //     self.hideDropDownSelect();
                // });
                //触发下拉显示/隐藏
                $dom.find('.muiPerformanceDropdownBox').off("click").on("click",function(e){
                    e?e.stopPropagation():event.cancelBubble = true;
                    self.hideDropDownSelect();
                    if($(this).find('.select_view').css('display') == 'none'){
                        $(this).find('.select_view').show();
                        $(".model-mask-panel").css("padding-bottom","272px");
                    }else{
                        $(this).find('.select_view').hide();
                        $(".model-mask-panel").css("padding-bottom","72px");
                    }
                })

                // 下拉列表点击外部或者按下ESC后列表隐藏
                $(document).click(function(){
                    self.hideDropDownSelect();
                }).keyup(function(e){
                    var key =  e.which || e.keyCode;;
                    if(key == 27){
                        self.hideDropDownSelect();
                    }
                });

                $dom.find(".select_view").off("click").on("click", function(){
                    Com_EventStopPropagation();
                });

                $dom.find(".select_result_view span").off("click").on("click", function(){
                    Com_EventStopPropagation();
                });

                //选择/删除字段
                $dom.find(".select_result_view>li").off("click").on("click", function(e){
                    var selectItem = $(this);
                    selectItem.find("input[type=checkbox]").trigger($.Event("click"));
                    Com_EventStopPropagation();
                });
                $dom.find(".select_result_view>li input").off("click").on("click", function(e){
                    var selectItem = $(this).closest("li");
                    var selectField = selectItem.data("field");
                    var isExist = self.isExistInSelected(selectField,type);

                    if (!isExist) {
                        //如果已选字段中不存在，则添加
                        if (!self.selectedDatas[type]){
                            self.selectedDatas[type] =[];
                        }
                        for (var i = 0; i < self.currentDatas.length; i++) {
                            var item = self.currentDatas[i];
                            if(item.value === selectField){
                                self.selectedDatas[type].push(item);
                                break;
                            }
                        }
                        self.createSelectedField($dom,type);
                    }else{
                        var isDeleted = false;
                        //如果已选字段中已存在，则删除
                        $dom.find(".selectedField").each(function(index, obj){
                            if ($(obj).data("field") === selectField) {
                                $(obj).find("i.delIcon").trigger($.Event("click"));
                                isDeleted = true;
                                return;
                            }
                        });
                        if (!isDeleted && self.selectedDatas[type]) {
                            for (var i = 0; i < self.selectedDatas[type].length; i++) {
                                if (self.selectedDatas[type][i].value === selectField) {
                                    self.selectedDatas[type].splice(i, 1);
                                    self.createSelectedField($dom,type);
                                    break;
                                }
                            }
                        }
                    }
                    Com_EventStopPropagation();
                });
                this.bindSearch($dom);
            }
        },

        hideDropDownSelect: function() {
            $('.select_view').hide();
            $('.muiPerformanceDropdownBox').removeClass('active');
            $(".model-mask-panel").css("padding-bottom","72px");
            this.$table.find("input[name=keyword]").val("");
            this.$table.find("li.dropDownSelectField").each(function(index, obj){
                $(obj).show();
            });
        },

        bindSearch : function($dom) {
            var self = this;
            $dom.find(".select_keyword").off("keydown").on("keydown",function(e){
                if(e.keyCode==13){
                    self.keyword = $(this).val();
                    self.search($dom);
                }
            });
        },

        search : function ($dom) {
            let self = this;
            if(self.keyword){
                $dom.find("li.dropDownSelectField").each(function(index, obj){
                    var fieldText = $(obj).attr("text").toUpperCase();
                    var keywordText = self.keyword.toUpperCase();
                    if (fieldText.indexOf(keywordText) > -1) {
                        $(obj).show();
                    } else {
                        $(obj).hide();
                    }
                });
            } else {
                $dom.find("li.dropDownSelectField").each(function(index, obj){
                    $(obj).show();
                });
            }
            $dom.find('.select_result_view').scrollTop(0);
        },
        getKeyData: function () {
            var self = this;
            var keyData = {};
            keyData.count="";
            keyData.firstRow = "";
            keyData.sum=[];
            keyData.max=[];
            keyData.min=[];
            keyData.avg=[];

            var count = this.$table.find("input[name='count_"+this.valueName+"']:checked").val();
            if (count){
                keyData.count = "1";
            }
            var firstRow = this.$table.find("input[name='firstRow_"+this.valueName+"']:checked").val();
            if (firstRow){
                keyData.firstRow = "1";
            }
            var sum = this.$table.find("input[name='sum_"+this.valueName+"']:checked").val();
            if (sum){
                keyData.sum = self.selectedDatas["sum"];
            }
            var max = this.$table.find("input[name='max_"+this.valueName+"']:checked").val();
            if (max){
                keyData.max = self.selectedDatas["max"];
            }
            var min = this.$table.find("input[name='min_"+this.valueName+"']:checked").val();
            if (min){
                keyData.min = self.selectedDatas["min"];
            }
            var avg = this.$table.find("input[name='avg_"+this.valueName+"']:checked").val();
            if (avg){
                keyData.avg = self.selectedDatas["avg"];
            }
            return keyData;
        },

        initByStoreData: function (storeData) {
            if(!storeData){
                return;
            }
            var self = this;
            if (storeData.hasOwnProperty("count")){
                var count = storeData["count"];
                if (count){
                    self.$countHtml.find("[name='count_"+self.valueName+"']").prop("checked","checked");
                }
            }
            if (storeData.hasOwnProperty("firstRow")){
                var firstRow = storeData["firstRow"];
                if (firstRow){
                    self.$firstRowHtml.find("[name='firstRow_"+self.valueName+"']").prop("checked","checked");
                }
            }
            if (storeData.hasOwnProperty("sum")){
                var sum = storeData["sum"];
                if (sum.length>0){
                    self.$sumHtml.find("[name='sum_"+self.valueName+"']").prop("checked","checked");
                    self.selectedDatas["sum"] = sum;
                    self.createSelectedField(self.$sumHtml,"sum");
                    self.createDropdownSelect(self.$sumHtml, "sum");
                }
            }
            if (storeData.hasOwnProperty("max")){
                var max = storeData["max"];
                if (max.length>0){
                    self.$maxHtml.find("[name='max_"+self.valueName+"']").prop("checked","checked");
                    self.selectedDatas["max"] = max;
                    self.createSelectedField(self.$maxHtml,"max");
                    self.createDropdownSelect(self.$maxHtml, "max");
                }
            }
            if (storeData.hasOwnProperty("min")){
                var min = storeData["min"];
                if (min.length>0){
                    self.$minHtml.find("[name='min_"+self.valueName+"']").prop("checked","checked");
                    self.selectedDatas["min"] = min;
                    self.createSelectedField(self.$minHtml,"min");
                    self.createDropdownSelect(self.$minHtml, "min");
                }
            }
            if (storeData.hasOwnProperty("avg")){
                var avg = storeData["avg"];
                if (avg.length>0){
                    self.$avgHtml.find("[name='avg_"+self.valueName+"']").prop("checked","checked");
                    self.selectedDatas["avg"] = avg;
                    self.createSelectedField(self.$avgHtml,"avg");
                    self.createDropdownSelect(self.$avgHtml, "avg");
                }
            }
        },

    })
    exports.BackValueGenerator = BackValueGenerator;

})
