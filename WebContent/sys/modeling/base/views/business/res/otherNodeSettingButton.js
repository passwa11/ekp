/**
 * 其他节点设置按钮
 */

define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        topic = require('lui/topic'),
        base = require('lui/base'),
        otherNodeSetting = require('sys/modeling/base/views/business/res/otherNodeSetting'),
        otherNodeSetting4Tree = require('sys/modeling/base/views/tree/res/otherNodeSetting4Tree');
    var modelingLang = require("lang!sys-modeling-base");
    var OtherNodeSettingButton = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.container = cfg.container;
            this.itemIndex = cfg.itemIndex;
            this.parent = cfg.parent;
            this.config = cfg.config;
        },

        draw : function($super, cfg){
            $super(cfg);
            var self = this;
            this.element = $("<li class='model-edit-view-oper-content-item last-item' />").appendTo(this.container);
            var $itemContent = $("<div class='item-content'></div>");
            var $itemContentButton = $("<button type='button' class='statistics_rule_button node-content-set'><i></i>"+modelingLang['modelingTreeView.node.content.set']+"</button>");
            $itemContentButton.on("click",function () {
                //点击设置时将头部的切换隐藏
                $itemContentButton.hide();
                if(self.parent.parent.type == "treeView"){
                    $(".model-edit-right .model-edit-view-bar").hide();
                    self.drawNodeSetting4Tree($itemContent);
                }else{
                    self.drawNodeSetting($itemContent);
                }
                self.fixWidth();
            });
            $itemContent.append($itemContentButton);
            if(this.config){
                //初始化
                //this.doReShow(this.config.fdPreNode,$itemContent);
                if(self.parent.parent.type == "treeView"){
                    self.drawNodeSetting4Tree($itemContent, this.config);
                    $itemContent.find(".returnNodeCfg4Tree").trigger("click");
                }else{
                    self.drawNodeSetting($itemContent, this.config);
                    $itemContent.find(".returnNodeCfg").trigger("click");
                }
                $itemContentButton.hide();
            }
            this.element.append($itemContent);

        },
        fixWidth:function(){
            var width = $("#mindMapTableDom").width() + 19;
            var height = $("#mindMapEdit").height();
            var top = $("#mindMapTableDom").offset().top;
            $(".node-content-setting").css("width",width+"px");
            $(".node-content-setting").css("height",height+"px");

        },
        drawNodeSetting4Tree : function($itemContent,config,index){
            var self = this;
            var tableHtml = $("#nodeSettingTable").html();
            var $contentSet = $("<div class='node-content-setting' style='display: none'></div>");
            $contentSet.append(tableHtml);
            self.otherNodeSetting = new otherNodeSetting4Tree({
                container: $contentSet,
                itemIndex: self.itemIndex,
                parent : self,
                config:config,
                index:index
            });
            self.otherNodeSetting.draw();
            $itemContent.append($contentSet);
        },
        drawNodeSetting:function($itemContent,config,index){
            var self = this;
            var tableHtml = $("#nodeSettingTable").html();
            var $contentSet = $("<div class='node-content-setting' style='display: none'></div>");
            $contentSet.append(tableHtml);
            self.otherNodeSetting = new otherNodeSetting({
                container: $contentSet,
                itemIndex: self.itemIndex,
                parent : self,
                config:config,
                index:index
            });
            self.otherNodeSetting.draw();
            $itemContent.append($contentSet);
        },
        verifyLoop:function(){
            return this.parent.verifyLoop();
        },
        doReShow : function(arr,isRoot,fdTargetModelName){
            var self = this;
            $(self.container.closest(".otherItem").find(".model-edit-view-oper-head-title span")[0]).text(fdTargetModelName);
            self.container.find(".node-content-set").css("display", "none");
            var nodehtml = "<div class=\"content-through-tabs other-node-re-show\">" +
                "<div class='pre-node-title'>"+modelingLang['modelingTreeView.Superior']+"</div>"+
                "<ul class=\"content-tabs\">";
                if(isRoot == "1"){
                    nodehtml += "<li>"+modelingLang['modelingMindMap.root.node']+"</li>";
                    if(arr &&arr.length > 0){
                        nodehtml += "<li class='show-all'>"+modelingLang['modelingTreeView.total']+(arr.length+1)+modelingLang['modelingTreeView.pcs']+"</li>";
                    }
                }else{
                    nodehtml += "<li>" + arr[0].fdPreModelName+"</li>";
                    if(arr.length > 1){
                        nodehtml += "<li class='show-all'>"+modelingLang['modelingTreeView.total']+arr.length+modelingLang['modelingTreeView.pcs']+"</li>";
                    }
                }
                nodehtml += "</ul>" +
                    "<div class=\"content-through-button\" style=\"float: right\">"+modelingLang['modelingTreeView.set.up']+"</div>" +
                    "<input name=\"fdSuperNode\" type=\"hidden\"  data-update-event=\"change\" data-validate=\"required\"title=\""+modelingLang['modelingTreeView.superior.node']+"\"/>" +
                    "</div>";
                //存储数据的字段，用于初始化回显
                // nodehtml += "<input type='hidden' name='nodeConfig' value='"+JSON.stringify(this.config)+"'>";
                self.element.find(".other-node-re-show").remove();
                self.element.append(nodehtml);

                var allHtml = " <div class=\"select_view\" style=\"display: none;\">" +
                    "<div class=\"select_content_view\" style=\"\">" +
                        "<div class=\"select_search_view\">" +
                            "<ul class=\"select_result_view\">";
                for (var i = 0; i < arr.length; i++) {
                    if(isRoot){
                        allHtml +="<li>"+modelingLang['modelingMindMap.root.node']+"</li>";
                    }
                    allHtml += "<li class=\"dropDownSelectField\" field='"+arr[i].fdPreModelId+"' text='"+arr[i].fdPreModelName+"'>"+arr[i].fdPreModelName+"</li>";
                }
                allHtml+="</ul></div></div>";
                self.element.find(".other-node-re-show").append(allHtml);
                //展示共有几个
                self.element.find(".show-all").off("click").on("click",function () {
                    var $select = $(this).closest(".other-node-re-show").find(".select_view");
                    if($select.hasClass("open")){
                        $select.removeClass("open");
                        $select.css("display","none");
                    }else{
                        $select.addClass("open");
                        $select.css("display","inline-block");
                    }
                });
                //回显框的+号
                self.element.find(".content-through-button").off("click").on("click", function () {
                    var $select = $(this).closest(".other-node-re-show").find(".select_view");
                    $select.removeClass("open");
                    $select.css("display","none");
                  /*  if(self.element.find(".node-content-setting").length == 0){
                        var index = $(this).closest(".otherItem").index();
                        var config = $(this).closest(".otherItem").find("[name='nodeConfig']").val();
                        self.drawNodeSetting($itemContent,JSON.parse(config),index);
                    }else{*/
                    self.fixWidth();
                    self.element.find(".node-content-setting").css("display","inline-block");
                    // }
                })
            //新增或更新节点配置
            topic.publish("mindMap.nodeChange",{evt:this,msg:modelingLang['modelingTreeView.add.update.node.configuration']})
            self.parent.nodeIdRefresh();
        },
        doReShow4Tree : function(fdPreNode,fdTargetModelName,preNodeIndex){
            var self = this;
            // var noteText = fdTargetModelName + "(节点"+self.itemIndex+")";
            var noteText = fdTargetModelName;
            $(self.container.closest(".otherItem").find(".model-edit-view-oper-head-title span")[0]).text(noteText);
            self.container.find(".node-content-set").css("display", "none");
            var nodehtml = "<div class=\"content-through-tabs other-node-re-show\">" +
                "<div class='pre-node-title'>"+modelingLang['modelingTreeView.Superior']+"</div>"+
                "<ul class=\"content-tabs\">"+
                "<li data-index='"+preNodeIndex+"'>"+fdPreNode+"</li></ul>" +
                "<div class=\"content-through-button\" style=\"float: right\">"+modelingLang['modelingTreeView.set.up']+"</div>" +
                "<input name=\"fdSuperNode\" type=\"hidden\"  data-update-event=\"change\" data-validate=\"required\"title=\""+modelingLang['modelingTreeView.superior.node']+"\"/>" +
                "</div>";
            self.element.find(".other-node-re-show").remove();
            self.element.append(nodehtml);

            //回显框的+号
            self.element.find(".content-through-button").off("click").on("click", function () {
                //点击节点的设置，将头部切换栏隐藏
                $(".model-edit-view-bar").hide();
                var $select = $(this).closest(".other-node-re-show").find(".select_view");
                $select.removeClass("open");
                $select.css("display","none");
                self.fixWidth();
                self.element.find(".node-content-setting").css("display","inline-block");
            })
            //新增或更新节点配置
            topic.publish("mindMap.nodeChange",{evt:this,msg:modelingLang['modelingTreeView.add.update.node.configuration']});
        },
        getKeyData : function (evt){
            if(this.otherNodeSetting){
                return this.otherNodeSetting.getKeyData();
            }else{
                return {};
            }
        }
    })

    module.exports = OtherNodeSettingButton;
})