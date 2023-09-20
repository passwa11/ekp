/**
 * 上级节点设置
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        headGenerator = require('sys/modeling/base/mobile/resources/js/headGenerator'),
        dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    var PreNodeSetting = headGenerator.extend({
        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container;
            this.parent = cfg.parent;
            this.config = cfg.config;
            this.preNodeSettingCollection = cfg.preNodeSettingCollection;
            this.targetModelData = cfg.targetModelData;
            this.otherNodeIdCollection = cfg.otherNodeIdCollection;
        },
        draw : function($super, cfg){
            var rowIndex = this.container.find(".preNodeItem").length+1;
            var $otherItem = $("<div class='item preNodeItem sortItem'></div>").appendTo(this.container);
            $otherItem.attr("index", rowIndex - 1);
            this.element = $otherItem;
            var self = this;
            var html = ""
            this.content = $("<div class='model-edit-view-oper' data-lui-position='fdParentNode-"+(rowIndex-1)+"'></div>");
            $otherItem.append(this.content);
            var text = modelingLang['modelingTreeView.Superior']+rowIndex;
            $super(cfg);
            this.textEle.text(text);
            //内容
            html += "<div class='model-edit-view-oper-content'>";
            html += "<ul class='list-content'>";
            html += "<div class='item-content' />";
            html += "</ul>";
            html += "</div>";
            this.content.append(html);
            $fieldTd = this.content.find(".item-content");
            self.drawPreModel($fieldTd);
            self.buildPreModel();
            self.drawSelect($fieldTd);
            if(this.config){
                this.initByStoreData(this.config);
            }
        },
        drawPreModel : function($fieldTd){
            var formHtml = "";
            formHtml += "<div class=\"parent-node-title\">" +
                modelingLang['modelingTreeView.parent.form'] +
                "</div>" +
                "<div class=\"target-form-content\">" +
                "<div class=\"inputselectsgl multiSelectDialog parentModel\"  data-lui-position='fdParentModel' style=\"width:100%;height: 28px!important;\">" +
                "<input name=\"fdPreModelModelId\" value='' type=\"hidden\">" +
                "<input name=\"preNodeSettingId\" value='' type=\"hidden\">" +
                "<div class=\"input\">" +
                "<input name=\"fdPreModelName\" value=\"\" type=\"text\" style=\"display:none;\" />" +
                "<span class=\"preModelItem\"></span>" +
                "</div>\n" +
                "<div class=\"deleteAll\"></div>" +
                "<div class=\"selectitem\"></div>" +
                "</div>" +
                "</div>";
            $fieldTd.append(formHtml);
        },
        buildPreModel : function(){
            var self = this;
            this.container.find(".parentModel").off("click").on("click",function(){
                var $dom = $(this);
                var appId = "";
                var index = $dom.closest(".preNodeItem").index();
                var preNodeIds= [];
                var nodeSettingIds=[];
                var nodeDatas=self.parent.parent.parent.getKeyData();
                for (var j = 0; j < nodeDatas.length; j++) {
                        preNodeIds.push(nodeDatas[j].fdTargetModelId);
                        nodeSettingIds.push(nodeDatas[j].nodeSettingId);
                }
                dialog.iframe("/sys/modeling/base/views/business/mindMap/model_select.jsp?preNodeId="+preNodeIds+"&nodeSettingId="+nodeSettingIds, modelingLang['modelingTreeView.select.superior.node.form'],
                    function (value) {
                        if (value) {
                            $dom.find(".preModelItem").html("<span class=\"com_subject\">"+value.fdName+"</span>");
                            $dom.find("[name='fdPreModelModelId']").val(value.fdId);
                            $dom.find("[name='preNodeSettingId']").val(value.nodeSettingId);

                            var text = $dom.find(".preModelItem").find(".com_subject").text();
                            $dom.find("[name='fdPreModelName']").val(text);
                            //更新上级节点的名称
                            $dom.closest(".preNodeItem").find(".model-edit-view-oper-head-title span").text(value.fdName);
                            self.initPreNodeModelInfo(value.fdId,index);
                        }
                    }, {
                        width: 1010,
                        height: 600
                    });
            });
        },
        initPreNodeModelInfo : function (modelId,index) {
            var self = this;
            var url =  Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=getModelWidget&modelId=" + modelId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data, status) {
                    if (data) {
                        self.drawPreModelField(JSON.parse(data).data,index);
                    }
                }
            });
        },
        drawSelect : function ($fieldTd) {
            var self = this;
            var formHtml = "";
            formHtml += "<div class=\"parent-node-title\">"+modelingLang['modelingTreeView.matching.relationship']+"</div>" +
                "            <div class=\"match-relation\">\n" +
                "                <select class='cur-model-field-option no-cur-field-model' disabled='disabled'><option class='disable-option'>"+modelingLang['modelingTreeView.target.form.field']+"</option></select>" +
                "                <div class=\"macth-relation-type-div\"><span class=\"match-relation-type\">=</span></div>" +
                "                <select class='target-model-field-option no-target-field-model' disabled='disabled'><option>"+modelingLang['modelingTreeView.superior.node.form.field']+"</option></select>" +
                "            </div>";
            $fieldTd.append(formHtml);
            //目标表单字段
            var data =  self.parent.targetModelData;
            if(!$.isEmptyObject(data)){
                $fieldTd.find(".cur-model-field-option").removeAttr("disabled");
                $fieldTd.find(".cur-model-field-option").removeClass("no-cur-field-model");
                $fieldTd.find(".cur-model-field-option").html("");
                var html = "<option value=''>"+modelingLang['modeling.page.choose']+"</option>";
                for(var key in data){
                    html += "<option value='"+key+"'>"+data[key].label+"</option>";
                }
                $fieldTd.find(".cur-model-field-option").append(html);

            }

            //目标表单的切换更新下拉框字段
            topic.channel("modelingMindMapOtherNode").subscribe("targetSoureData.load",function(rtn){
                var data = rtn.data.data;
                var index = rtn.index;
                var otherItem = self.container.closest(".otherItem");
                for(var i = 0;i < otherItem.length;i++){
                    if($(otherItem[i]).attr("index") == index){
                        $fieldTd.find(".cur-model-field-option").removeAttr("disabled");
                        $fieldTd.find(".cur-model-field-option").removeClass("no-cur-field-model");
                        $fieldTd.find(".cur-model-field-option").html("<option value=''>"+modelingLang['modeling.page.choose']+"</option>");
                        var html = "";
                        for(var key in data){
                            html += "<option value='"+key+"'>"+data[key].label+"</option>";
                        }
                        $fieldTd.find(".cur-model-field-option").append(html);
                    }
                }
            })

        },
        drawPreModelField:function(predata,index){
            var self = this;
            var nodeItem = self.container.find(".preNodeItem");
            for(var i = 0;i < nodeItem.length;i++){
                if($(nodeItem[i]).attr("index") == index){
                    $(nodeItem[i]).find(".target-model-field-option").removeAttr("disabled");
                    $(nodeItem[i]).find(".target-model-field-option").removeClass("no-target-field-model");
                    $(nodeItem[i]).find(".target-model-field-option").html("<option value=''>"+modelingLang['modeling.page.choose']+"</option>");
                    var preHtml = "";
                    for(var key in predata){
                        preHtml += "<option value='"+key+"'>"+predata[key].label+"</option>";
                    }
                    $(nodeItem[i]).find(".target-model-field-option").append(preHtml);
                }
            }
        },
        delItem : function (dom) {
            var $item = $(dom).closest(".item");
            var curIndex = $item.attr("index");
            var wgt = this.parent.preNodeSettingCollection[curIndex];
            this.parent.preNodeSettingCollection.splice(curIndex,1);
            wgt.destroy();
            return;
        },
        getKeyData : function (evt){
            var preNodeSettingId = this.element.find("[name='preNodeSettingId']").val();
            var fdPreModelId = this.element.find("[name='fdPreModelModelId']").val();
            var fdPreModelName = this.element.find("[name='fdPreModelName']").val();
            var fdCurModelField = this.element.find(".cur-model-field-option option:checked").val();
            var fdCurModelFieldName = this.element.find(".cur-model-field-option option:checked").text();
            var fdTargetModelField = this.element.find(".target-model-field-option option:checked").val();
            var fdTargetModelFieldName = this.element.find(".target-model-field-option option:checked").text();
            var fdMatchType = "=";
            return {
                preNodeSettingId:preNodeSettingId,
                fdPreModelId:fdPreModelId,
                fdPreModelName:fdPreModelName,
                fdCurModelField:fdCurModelField,
                fdCurModelFieldName:fdCurModelFieldName,
                fdTargetModelField:fdTargetModelField,
                fdTargetModelFieldName:fdTargetModelFieldName,
                fdMatchType:fdMatchType
            }
        },
        initByStoreData: function (sd) {
            var self = this;
            //上级表单
            this.element.find("[name='fdPreModelModelId']").val(sd.fdPreModelId);
            if(sd.preNodeSettingId){
                this.element.find("[name='preNodeSettingId']").val(sd.preNodeSettingId);
            }else{
                this.element.find("[name='preNodeSettingId']").val(sd.fdPreModelId);
            }

            var index = this.element.index();
            if(sd.fdPreModelId){
                self.initPreNodeModelInfo(sd.fdPreModelId,index);
            }
            this.element.find("[name='fdPreModelName']").val(sd.fdPreModelName);
            this.element.find(".model-edit-view-oper-head-title span").text(sd.fdPreModelName);
            var preHtml = "<span class=\"com_subject\">"+sd.fdPreModelName+"</span>";
            self.element.find(".target-form-content .preModelItem").html(preHtml);
            //目标表单字段
            var curHtml = "option[value='"+sd.fdCurModelField+"']";
            this.element.find(".cur-model-field-option").find(curHtml).attr("selected",true);
            //上级表单字段
            var tarHtml = "option[value='"+sd.fdTargetModelField+"']";
            this.element.find(".target-model-field-option").find(tarHtml).attr("selected",true);
        }
    })
    exports.PreNodeSetting = PreNodeSetting;
})