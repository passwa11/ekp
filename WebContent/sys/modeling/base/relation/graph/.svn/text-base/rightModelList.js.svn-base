/**
 * 页面全局对象
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    var modelingLang = require("lang!sys-modeling-base");
    var tempHtml = " <ul class=\"model-rightbar-flex\">\n" +
        "        <li class=\"model-rightbar-flex-item spread\" lui-mark-right-bar=\"relation\" style='display: none'>\n" +
        "            <div class=\"model-rightbar-flex-title\">\n" +
        "                <i></i>\n" +
        "                <p>"+modelingLang['relation.business.association']+"</p>" +
        "                   <div class=\"associated_business_item_title_right\"  onclick=\"createRelation('0')\">\n" +
        "                       <i></i>\n" +
        "                       <p>"+modelingLang['button.add']+"</p>\n" +
        "                   </div>\n" +
        "           </div>\n" +
        "            <div class=\"model-rightbar-flex-content\" style=\"display: block;\">\n" +
        "               <div class=\"model-rightbar-empty\">\n" +
        "                  <div></div>\n" +
        "                  <p>"+modelingLang['relation.no.business.association']+"</p>\n" +
        "                </div>" +
        "               <table class='tb_normal' width='100%'>\n" +
        "                   <tr><td class='td_normal_title'>"+modelingLang['modeling.model.fdName']+"</td><td class='td_normal_title'>"+modelingLang['sysModelingRelation.fdShowType']+"</td>" +
        "                       <td class='td_normal_title'>"+modelingLang['sysModelingRelation.fdWidgetName']+"</td><td class='td_normal_title'>"+modelingLang['modelingAppViewopers.fdOperation']+"</td></tr>\n" +
        "               </table>\n" +
        "            </div>\n" +
        "        </li>\n" +
        "        <li class=\"model-rightbar-flex-item\" lui-mark-right-bar=\"through\" style='display: none'>\n" +
        "            <div class=\"model-rightbar-flex-title\">\n" +
        "                <i></i>\n" +
        "                <p>"+modelingLang['relation.business.penetration']+"</p>" +
        "                   <div class=\"associated_business_item_title_right\"  onclick=\"createRelation('1')\">\n" +
        "                       <i></i>\n" +
        "                       <p>"+modelingLang['button.add']+"</p>\n" +
        "                   </div>\n" +
        "           </div>\n" +
        "            <div class=\"model-rightbar-flex-content\" >\n" +
        "               <div class=\"model-rightbar-empty\">\n" +
        "                  <div></div>\n" +
        "                  <p>"+modelingLang['relation.no.business.penetration']+"</p>\n" +
        "                </div>" +
        "               <table class='tb_normal' width='100%'>\n" +
        "                   <tr><td class='td_normal_title'>"+modelingLang['modeling.model.fdName']+"</td><td class='td_normal_title'>"+modelingLang['sysModelingRelation.fdShowType']+"</td>" +
        "                       <td class='td_normal_title'>"+modelingLang['sysModelingRelation.fdWidgetName']+"</td><td class='td_normal_title'>"+modelingLang['modelingAppViewopers.fdOperation']+"</td></tr>\n" +
        "               </table>\n" +
        "            </div>\n" +
        "        </li>\n" +
        "        <li class=\"model-rightbar-flex-item spread\" lui-mark-right-bar=\"filling\" style='display: none'>\n" +
        "            <div class=\"model-rightbar-flex-title\">\n" +
        "                <i></i>\n" +
        "                <p>"+modelingLang['relation.business.filling']+"</p>" +
        "                   <div class=\"associated_business_item_title_right\"  onclick=\"createRelation('2')\">\n" +
        "                       <i></i>\n" +
        "                       <p>"+modelingLang['button.add']+"</p>\n" +
        "                   </div>\n" +
        "            </div>\n" +
        "            <div class=\"model-rightbar-flex-content\" style=\"display: block;\">\n" +
        "               <div class=\"model-rightbar-empty\">\n" +
        "                  <div></div>\n" +
        "                  <p>"+modelingLang['relation.no.business.filling']+"</p>\n" +
        "                </div>" +
        "               <table class='tb_normal' width='100%'>\n" +
        "                   <tr><td class='td_normal_title'>"+modelingLang['modeling.model.fdName']+"</td><td class='td_normal_title'>"+modelingLang['sysModelingRelation.fdShowType']+"</td>" +
        "                       <td class='td_normal_title'>"+modelingLang['sysModelingRelation.fdWidgetName']+"</td><td class='td_normal_title'>"+modelingLang['modelingAppViewopers.fdOperation']+"</td></tr>\n" +
        "               </table>\n" +
        "            </div>\n" +
        "        </li>\n" +
        "    </ul>";
    var emptyHtml = "<div class=\"model-rightbar-create\">\n" +
        "          <div class=\"model-rightbar-create-relation\"  onclick=\"createRelation('0')\">\n" +
        "            <i></i>\n" +
        "            <p>"+modelingLang['relation.new.business.linkage']+"</p>\n" +
        "          </div>\n" +
        "          <div class=\"model-rightbar-create-penetrate\"  onclick=\"createRelation('1')\" style='display: none'>\n" +
        "            <i></i>\n" +
        "            <p>"+modelingLang['relation.new.business.penetration']+"</p>\n" +
        "          </div>\n" +
        "          <div class=\"model-rightbar-create-penetrate create-filling\"  onclick=\"createRelation('2')\" style='display: none'>\n" +
        "            <i></i>\n" +
        "            <p>新建业务填充</p>\n" +
        "          </div>\n" +
        "        </div>";
    var _showTypeTxt = {
        "0": {
            "name": modelingLang['enums.relation.show.0']
        },
        "1": {
            "name": modelingLang['enums.relation.show.1']
        },
        "2": {
            "name": modelingLang['enums.relation.show.2']
        },
        "3": {
            "name": modelingLang['enums.relation.show.3']
        },
        "4": {
            "name": modelingLang['enums.relation.show.4']
        },
        "5": {
            "name": modelingLang['enums.relation.show.5']
        },
        "6": {
            "name": modelingLang['enums.relation.show.6']
        }
    }
    var RightModelList = base.Container.extend({
        initProps: function ($super, cfg) {
            this.mainContainer = cfg.container;
            this.modelList = cfg.modelList;
            this.applicaton = cfg.applicaton;
            this.options = cfg.options;
            this.relationContentElement = {};
            this.init();
        },
        //初始化2，生成视图结构
        init: function () {
            this.element = this.build();

        },
        //视图结构生成
        build: function () {
            var self = this;
            var $ele = self.mainContainer;
            //按钮
            $ele.find(".model-rightbar-btn").on("click", function () {
                $('.model-rightbar').toggleClass('active')
                $('.model-body').toggleClass('shrink')
                var $tips = $('.model-rightbar-btn-tips');
                if ($tips.text() === modelingLang['relation.associated.template.library']) {
                    $tips.text(modelingLang["modelingApplication.putAway"])
                } else {
                    $tips.text(modelingLang['relation.associated.template.library'])
                }
            });

            self.buildModelList($ele);
            self.bindEvent($ele);
            $ele.find(".model-rightbar-btn").trigger($.Event("click"));
            return $ele
        },
        rebuildModel:function(val){
            var self = this;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/modeling.do?method=searchModelingAll";
            url = url + "&searchName=" +val;
            $.ajax({
                url: url,
                method: 'GET',
                async: true
            }).success(function (resultStr) {
                self.modelList = JSON.parse(resultStr).modelList;
                $(".model-rightbar-content ").remove();
                self.buildModelList(self.mainContainer);
                self.bindEvent(self.mainContainer);
            });
        },
        rebuild: function (id) {
            this.rebuildId = id;
            var modelList = this.element.find("[lui-right-content='modelList']");
            var relationContent = this.element.find("[lui-right-content='relation']");
            if (id === this.applicaton.modelId || !id) {
                this.element.find(".model-rightbar-header").hide();
                this.element.find(".header-modeling").show();
                this.element.find(".model-rightbar-bottom").hide();
                this.element.find(".model-rightbar-scroll").css({"top": "100px", "bottom": "0"});
                this.element.find(".model-rightbar-sub").hide();
                this.element.find(".model-rightbar-create").hide();
                modelList.show();
                relationContent.hide();
            } else {
                modelList.hide();
                this.buildRelationContent(id);
            }
        },

        //绘制右侧视图-1 - 模块列表
        buildModelList: function ($ele) {
            var self = this;
            $ele.find(".model-rightbar-header").hide();
            $ele.find(".header-modeling").show();
            $ele.find(".model-rightbar-bottom").hide();
            $ele.find(".model-rightbar-sub").hide();
            $ele.find(".model-rightbar-scroll").css({"top": "100px", "bottom": "0"});
            var $rightContent = $("<div class=\"model-rightbar-content \"></div>");
            $rightContent.attr("lui-right-content", "modelList")
            var $rightFlex = $("<ul class=\"model-rightbar-flex\"></ul>");
            var ml = self.modelList;
            for (var appId in ml) {
                var $li = $(" <li class=\"model-rightbar-flex-item\"></li>");
                var $appTitle = $("<div class=\"model-rightbar-flex-title\">\n" +
                    " <i></i> <p></p><span></span>" +
                    "</div>");
                $appTitle.find("p").text(ml[appId].name);
                if (appId === self.applicaton.appId) {
                    $appTitle.find("span").text(modelingLang['relation.current.application']);
                }
                var $appContent = $(" <div class=\"model-rightbar-flex-content\"></div>");
                self.buildModelListOfApplication($appContent, ml[appId].models);
                $li.append($appTitle);
                $li.append($appContent);
                if (appId === self.applicaton.appId) {
                    $appContent.slideToggle();
                    $li.toggleClass('spread');
                    $rightFlex.prepend($li);
                } else {
                    $rightFlex.append($li);
                }
            }
            $rightContent.append($rightFlex);

            $ele.find(".model-rightbar-scroll").append($rightContent)
        },
        //分应用绘制
        buildModelListOfApplication: function ($appContent, models) {
            var self = this;
            for (var m in models) {
                var model = models[m];
                var $item = $("<div class='dragLi model-rightbar-model-item'><i ></i><p class='textEllipsis'></p></div>");
                $item.find("p").text(model.name);
                $item.find("p").attr("title", model.name);
                $item.find("i").attr("class", model.icon + " iconfont_nav");
                $item.attr("id", m);
                $item.attr("draggable", true);
                $item.bind('dragstart', function (e) {
                    var data = e.originalEvent.dataTransfer;
                    var $dom = $(event.target);
                    var title =  $dom.find("p").attr("title");
                    var n = title
                    if (n.length > 5) {
                        n = n.substring(0, 4) + "…";
                    }
                    var text = {
                        id: $(e.target).attr("id"),
                        name: n,
                        title:title,
                        icon: $(e.target).find("i").attr("class")
                    };
                    //ie下使用text 作为key可以传递，其他不行，原因不明
                    data.setData('Text', JSON.stringify(text))
                 //   data.setData('text/plain', JSON.stringify(text))

                });
                $item.attr("lui-mark-modelId", model.id);
                $appContent.append($item);
            }
        },
        //绘制右侧视图-2 -关系列表
        buildRelationContent: function (id) {
            var self = this;
            var $ele = self.element;
            $ele.find(".model-rightbar-header").show();
            $ele.find(".header-modeling").hide();
            $ele.find(".model-rightbar-bottom").hide();
            $ele.find(".model-rightbar-sub").show();
            $ele.find(".model-rightbar-scroll").css({"top": "50px", "bottom": "50px"});
            $ele.find("[lui-right-content='relation']").remove();
            $ele.find(".model-rightbar-create").remove();
            var $rightContent = $("<div class=\"model-rightbar-content \"></div>");
            $rightContent.attr("lui-right-content", "relation");
            $rightContent.attr("lui-right-content-relation", id)
            var self = this;
            var passiveId = id === "self" ? self.applicaton.modelId : id;
            var url = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingRelation.do?method=getModelRelations";
            url = url + "&modelId=" + self.applicaton.modelId;
            url = url + "&passiveId=" + passiveId;

            $.ajax({
                url: url,
                method: 'GET',
                async: false
            }).success(function (resultStr) {
                var result;
                if(typeof(resultStr) === 'object'){
                    result = resultStr;
                }
                else{
                    result = JSON.parse(resultStr);
                }
                var sourceData = result['relation'];
                if (sourceData) {
                    self.buildRelationContentList($rightContent, sourceData);
                    self.options.mxGraph_updateNodeNumber(passiveId, result.relationNumber,result.widgetInfos);
                    //用于缓存,后续可根据时间来更新
                    //self.relationContentElement[id] = new Date();
                } else {
                    self.buildRelationEmptyContent($rightContent, passiveId);
                    self.options.mxGraph_updateNodeNumber(passiveId,"0");
                }
                self.$rightContent = $rightContent;
                $ele.find(".model-rightbar-scroll").append(self.$rightContent);
            });
        },
        //绘制右侧视图-2-1 -关系列表详情
        buildRelationContentList: function ($content, sourceData) {
            var self = this;
            // console.log("buildRelationContentList", sourceData)
            var $rightFlex = $(tempHtml);
            var $relation = $rightFlex.find("[lui-mark-right-bar=\"relation\"]").find("table");
            var $through = $rightFlex.find("[lui-mark-right-bar=\"through\"]").find("table");
            var $filling = $rightFlex.find("[lui-mark-right-bar=\"filling\"]").find("table");
            $through.hide();
            $relation.hide();
            $filling.hide();
            $rightFlex.find("[lui-mark-right-bar=\"through\"]").find(".model-rightbar-empty").show();
            $rightFlex.find("[lui-mark-right-bar=\"relation\"]").find(".model-rightbar-empty").show();
            $rightFlex.find("[lui-mark-right-bar=\"filling\"]").find(".model-rightbar-empty").show();
            for (var rid in sourceData) {
                var $item = $("<tr class='relation-right-table-tr'></tr>")
                var data = sourceData[rid];
                if (data.needModified) {
                    $("<td></td>").html("<i class='needModifiedIcon'></i>"+data.name).appendTo($item);
                }else {
                    $("<td></td>").text(data.name).appendTo($item);
                }

                var showTypeText = _showTypeTxt[data.fdShowType];
                if (showTypeText) {
                    $("<td></td>").text(showTypeText.name).appendTo($item);
                } else {
                    $("<td></td>").text("[未选定]").appendTo($item);
                }
                 var fdWidgetName = data.fdWidgetName;
                $("<td title='"+data.fdWidgetName+"'></td>").text(fdWidgetName).attr("fdWidgetId", data.fdWidgetId).appendTo($item);
                var $operationEdit = "<span lui-realtion-click='edit' lui-realtion-id='" + rid + "' lui-realtion-type='" + data.type + "'>"+modelingLang['modeling.page.edit']+"</span>";
                var $operationDel = "<span lui-realtion-click='delete' lui-realtion-id='" + rid + "' lui-realtion-type='" + data.type + "'>"+modelingLang['modeling.page.delete']+"</span>";
                var $operationTd = $("<td class='relation-operation'></td>");
                $operationTd.append($operationEdit);
                $operationTd.append($operationDel);
                $item.append($operationTd)

                if (sourceData[rid].type === "1") {
                    $through.append($item);
                    $rightFlex.find("[lui-mark-right-bar=\"through\"]").find(".model-rightbar-empty").hide();
                    $through.show();

                }else if (sourceData[rid].type === "2") {
                    $filling.append($item);
                    $rightFlex.find("[lui-mark-right-bar=\"filling\"]").find(".model-rightbar-empty").hide();
                    $filling.show();
                    $rightFlex.find("[lui-mark-right-bar=\"filling\"]").show();
                    if(self.options.getEnableCount()>0){
                        $rightFlex.find("[lui-mark-right-bar=\"relation\"]").show();
                    }
                } else {
                    $relation.append($item)
                    $rightFlex.find("[lui-mark-right-bar=\"relation\"]").find(".model-rightbar-empty").hide();
                    $relation.show();
                    $rightFlex.find("[lui-mark-right-bar=\"relation\"]").show();
                    if(self.options.getFillEnableCount()>0){
                        $rightFlex.find("[lui-mark-right-bar=\"filling\"]").show();
                    }
                }
            }

            self.$rightFlex = $rightFlex;
            self.bindEvent(self.$rightFlex);
            $content.append(self.$rightFlex);

        },
        //绘制右侧视图-2-2 -关系列表全空
        buildRelationEmptyContent: function ($content, passiveId) {
            var self = this;
            var $rightFlex = $(tempHtml);
            var $relation = $rightFlex.find("[lui-mark-right-bar=\"relation\"]").find("table");
            var $through = $rightFlex.find("[lui-mark-right-bar=\"through\"]").find("table");
            var $filling = $rightFlex.find("[lui-mark-right-bar=\"filling\"]").find("table");
            $through.hide();
            $relation.hide();
            $filling.hide();
            $rightFlex.find("[lui-mark-right-bar=\"through\"]").find(".model-rightbar-empty").show();
            $rightFlex.find("[lui-mark-right-bar=\"relation\"]").find(".model-rightbar-empty").show();
            $rightFlex.find("[lui-mark-right-bar=\"filling\"]").find(".model-rightbar-empty").show();

            if(self.options.getEnableCount()>0){
                $rightFlex.find("[lui-mark-right-bar=\"relation\"]").show();
            }

            if(self.options.getFillEnableCount()>0){
                $rightFlex.find("[lui-mark-right-bar=\"filling\"]").show();
            }

            self.$rightFlex = $rightFlex;
            self.bindEvent(self.$rightFlex);
            $content.append(self.$rightFlex);
        },
        //其他操作
        bindEvent: function ($ele) {
            var self = this;
            var pid = self.rebuildId
            var passiveId = pid === "self" ? self.applicaton.modelId : pid;
            $ele.find('.model-rightbar-flex-title').on('click', function () {
                $(this).siblings('.model-rightbar-flex-content').slideToggle();
                $(this).parent().toggleClass('spread');
                //mxGraph.refresh();
            });
            //编辑
            // console.log("--dialog--\n", dialog)
            $ele.find("[lui-realtion-click='edit']").on('click', function () {
                //subId
                var id = $(this).attr("lui-realtion-id");
                var type = $(this).attr("lui-realtion-type");
                var title = type == "2" ?modelingLang['relation.business.filling']:modelingLang['modeling.form.RelationshipSet'];
                var url = "/sys/modeling/base/sysModelingRelation.do?method=edit&fdId=" + id;
                dialog.iframe(url, title,
                    function (value) {
                        if (typeof value == "undefined") {
                            location.reload();
                        }
                    }, {
                        width: 884,
                        height: 648,
                    });
            });
            //删除
            $ele.find("[lui-realtion-click='delete']").on('click', function () {
                //subId
                var id = $(this).attr("lui-realtion-id");
                var type = $(this).attr("lui-realtion-type");
                var widgetId = $(this).closest("td").attr("fdWidgetId");
                var url = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingRelation.do?method=deleteRelation&fdId=" + id;
                dialog.confirm(modelingLang['relation.delete.current.relationship'], function (value) {
                    if (value == true) {
                        $.ajax({
                            url: url,
                            method: 'GET',
                            async: false
                        }).success(function (resultStr) {
                            var result = JSON.parse(resultStr);
                            if (result.success) {
                                dialog.success(modelingLang['modeling.baseinfo.DeleteSuccess']);
                                //业务填充控件
                                if(type == "2"){
                                    self.options.onFillingDel(passiveId, 1,widgetId);
                                }else{
                                    self.options.onRelationDel(passiveId, 1);
                                }
                                self.rebuild(pid);
                            } else {
                                if(result.status == false){
                                    dialog.failure(result.msg);
                                    //弹出框修改
                                    var url='/sys/modeling/base/listview/config/dialog_relation.jsp';
                                    dialog.iframe(url, modelingLang['modeling.form.DeleteAssociatedModule'], function(data){
                                    },{
                                        width : 600,
                                        height : 400,
                                        params : { datas : result.datas }
                                    });
                                }else{
                                    dialog.failure(result.msg);
                                }
                            }
                        });
                    }
                });
            });
        },
        createRelation: function (type) {
            var passiveId = this.rebuildId === "self" ? this.applicaton.modelId : this.rebuildId;
            this.relationAdd(type, passiveId);
        },
        relationAdd: function (type, passiveId, fdId) {
            var self = this;
            var canCreateUrl = Com_Parameter.ContextPath + "sys/modeling/base/sysModelingRelation.do" +
                "?method=canCreateRelation" +
                "&modelId=" + this.applicaton.modelId +
                "&type=" + type;
            var createUrl = ""
            if (fdId) {
                createUrl = "/sys/modeling/base/sysModelingRelation.do" +
                    "?method=add" +
                    "&fdId=" + fdId +
                    "&type=" + type;
            } else {
                createUrl = "/sys/modeling/base/sysModelingRelation.do" +
                    "?method=add" +
                    "&modelId=" + this.applicaton.modelId +
                    "&passiveId=" + passiveId +
                    "&type=" + type;
            }

            $.ajax({
                url: canCreateUrl,
                method: 'GET',
                async: false
            }).success(function (resultStr) {
                var result;
                if(typeof(resultStr) === 'object'){
                    result = resultStr;
                }
                else{
                    result = JSON.parse(resultStr);
                }
                if (result.canCreate) {
                    var title = type == "2" ?modelingLang['relation.business.filling']:modelingLang['modeling.form.RelationshipSet'];
                    dialog.iframe(createUrl, title,
                        function (value) {
                            if (passiveId) {
                                passiveId = passiveId === self.applicaton.modelId ? "self" : passiveId;
                                self.rebuild(passiveId);
                            }
                        }, {
                            width: 960,
                            height: 752
                        });
                } else {
                    alert(modelingLang['sysModelingRelation.msg.relationfull']);
                }
            });

        }
    });

    exports.RightModelList = RightModelList;

})
;
