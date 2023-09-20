/**
 *
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var env = require("lui/util/env");
    var topic = require("lui/topic");
    var dialog = require('lui/dialog');
    var popup = require("sys/modeling/base/resources/js/popup");
    var modelingLang = require("lang!sys-modeling-base");

    var ListView = base.DataView.extend({

        tmpMethod: {
            "allApps": "getAllAppInfos",
            "myApps": "getMyAppInfos",
            "editorApps": "getMyEditorAppInfos",
            "installedApps": "getInstalledAppInfos"
        },

        // 应用不同状态下对应的属性
        tmpStatus: {
            "00": {
                "class": "green_backgroud_color",
                "colorClass":"green",
                "text": listOption.lang.published,
                "showOper": ["cancelPublish", "endApp", "export"]
            },
            "01": {
                "class": "blue_backgroud_color",
                "colorClass":"orange",
                "text": listOption.lang.toPublish,
                "showOper": ["publish", "endApp", "export"]
            },
            /*已停用  这个位置需要后面修改，停用，并且主表数据没有数据情况下是可以删除的*/
            "02": {
                "class": "black_backgroud_color",
                "colorClass":"red",
                "text": listOption.lang.terminated,
                //已经停用的app应用，可以执行的操作类型有启用应用，导出应用，新增删除应用
                "showOper": ["startApp", "export","deleteByAjax"]
            },
            "03": {
                "class": "black_backgroud_color",
                "colorClass":"default",
                "text": listOption.lang.draft,
                "showOper": ["publish", "deleteByAjax", "deleteData", "export"]
            },
            "04": {
                "class": "black_backgroud_color",
                "colorClass":"red",
                "text": modelingLang['modelingLicense.disabled'],
                "showOper": ["deleteByAjax"]
            }
        },
// ,"eq.fdValid": "true"
        env: {view: '', criteria: {"like.fdAppName": ""}, "curTriggerPopup": null},	// 记录当前视图，当前筛选项

        initProps: function ($super, cfg) {
            $super(cfg);
            var self = this;
            // 订阅左侧导航栏切换
            topic.channel("modelingAppList").subscribe('app.type.change', function (ctx) {
                self.reRender(ctx.type);
            });
        },

        load: function () {
            this.source.get();
        },

        reRender: function (type) {
            window.app_type_load = dialog.loading();

            this.element.append(this.loading);
            if (!type) {
                type = this.env.view;
            } else {
                this.env.view = type;
            }
            var url = "/sys/modeling/base/modelingApplication.do?method=" + this.tmpMethod[type];

            for (var key in this.env.criteria) {
                url += "&c." + key + "=" + this.env.criteria[key];
            }
            this.source.url = url;
            if (this.popupWgt) {
                this.popupWgt.destroy();
            }
            this.load();
        },

        updatePage: function () {
            topic.channel("modelingAppList").publish("app.update");
            topic.channel("modelingAppList").publish("app.lincense");
            topic.channel("modelingAppList").publish("window.reload");
        },

        // 渲染完毕之后添加事件
        doRender: function ($super, cfg) {
            $super(cfg);
            var self = this;
            // 新建
            this.element.find("[data-applist-boxtype='create']").each(function (index, dom) {
                $(dom).on("click", function () {
                    if (licenseInfo.licenseMode === 'runner' && licenseInfo.licenseCount === 0 ){
                        var html = "<p style='text-align: left;font-weight:bold;'>"+modelingLang['modelingLicense.license.cannot.add.tip1']+"</p><p style='text-align: left;'>"+modelingLang['modelingLicense.license.cannot.add.tip2']+"</p>";
                        var param = {"title":modelingLang['modelingLicense.license.tipTitle'],"html":html}
                        dialog.alert(param)
                        return;
                    }else {
                        var url = "/sys/modeling/base/modelingApplication.do?method=add";
                        dialog.iframe(url, listOption.lang.createApp, function (rt) {
                            return self.closeDialog.call(self, rt);
                        }, {width: 540, height: 317, params: {formWindow: window}});
                    }
                });
            });
            // 编辑
            this.element.find("[data-applist-boxtype='edit']").each(function (index, dom) {
                $(dom).on("click", function () {
                    var url = Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=appIndex&fdId=" + $(this).attr("data-applist-id");
                    Com_OpenWindow(url, "_blank");
                });
            });
            // 跳转首页
            this.element.find("[data-home-href]").each(function (index, dom) {
                $(dom).on("click", function (event) {
                    self.stopBubble(event);
                    window.open(env.fn.formatUrl($(dom).attr("data-home-href")), "_blank");
                });
            });
            // 设置点击
            var self = this;
            this.element.find("[data-script-click]").each(function (index, dom) {
                var type = $(dom).attr("data-script-click");
                $(dom).on("click", function (event) {
                    self.stopBubble(event);
                    self.element.find(".form_popup_expand").removeClass("form_popup_expand");
                    if (type === "export") {
                        var appId = $(dom).closest("li[data-applist-id]").attr("data-applist-id");
                        var appName = $(dom).closest("li[data-applist-id]").find(".appMenu_titleName").attr("title");
                        self.exportFunc(appId, appName);
                    } else if (type === "version") {
                        var appId = $(dom).closest("li[data-applist-id]").attr("data-applist-id");
                        var appName = $(dom).closest("li[data-applist-id]").find(".appMenu_titleName").attr("title");
                        self.versionFunc(appId, appName);
                    }else if (type === "more"){
                      var text =   $(dom).find(".modeling_app_operation_alert li").val();
                        if (text !=undefined) {
                            $(dom).toggleClass("form_popup_expand")
                        }
                    }else if (type === "actionCode"){
                        var appId = $(dom).closest("li[data-applist-id]").attr("data-applist-id");
                        var appName = $(dom).closest("li[data-applist-id]").find(".appMenu_titleName").attr("title");
                        self.actionCodeFunc(appId, appName);
                    }else if (type === "selfBuildApp"){
                        var appId = $(dom).closest("li[data-applist-id]").attr("data-applist-id");
                        var status = $("li[data-applist-id="+appId+"]").find(".modeling_app_operation").attr("data-oper-code");
                        if (licenseInfo.licenseMode === 'runner' && licenseInfo.licenseCount === 0 ){
                            var html = "<p style='text-align: left;font-weight:bold;'>"+modelingLang['modelingLicense.license.cannot.add.tip1']+"</p><p style='text-align: left;'>"+modelingLang['modelingLicense.license.cannot.add.tip2']+"</p>";
                            var param = {"title":modelingLang['modelingLicense.license.tipTitle'],"html":html}
                            dialog.alert(param);
                        }else if (licenseInfo.licenseMode === 'runner' && licenseInfo.remainBuild === 0 && status === "00"){
                            dialog.alert(modelingLang['modelingLicense.number.less.tips'])
                        }else {
                            dialog.confirm(modelingLang['modelingLicense.confirm.switch.self-built.app'],function(flag){
                                if (flag){
                                    self.converSelfBuild(appId);
                                }
                            });
                        }
                    }
                });
            });
            this.element.find(".maturity_tips").each(function (index, dom) {
                $(dom).hover(function () {
                    $(dom).parents().children(".end_time_tips").css("visibility", "visible");
                    $(dom).parents().children(".end_time_tips").css("display", "block");
                })

            });
            $(".maturity_tips").mouseout(function () {
                $(".end_time_tips").css("visibility", "hidden");
            });
            this.element.find(".listTableMaturityTip").each(function (index, dom) {
                $(dom).hover(function () {
                    var offset = $(dom).offset();
                    $(".end_time_table_tips").css("left", offset.left -22);
                    $(".end_time_table_tips").css("top", offset.top + 25);
                    $(".end_time_table_tips").css("visibility", "visible");
                    $(".end_time_table_tips").css("display", "block");
                });
            });
            $(".listTableMaturityTip").mouseout(function () {
                $(".end_time_table_tips").css("visibility", "hidden");
            });
            // 设置分类伸缩
            this.element.find(".appList_category_head").each(function (index, dom) {
                $(dom).find("span").on("click", function (event) {
                    self.stopBubble(event);
                    var $arrow = $(dom).find(".appList_category_head_arrow");
                    var $txt = $(dom).find(".appList_category_head_display");
                    if ($arrow.hasClass("arrow_invert")) {
                        $txt.html(listOption.lang.putAway)
                        $arrow.removeClass("arrow_invert");
                        $(dom).closest(".appList_category").find(".appList_category_main").slideDown();
                    } else {
                        $txt.html(listOption.lang.unfold);
                        $arrow.addClass("arrow_invert");
                        $(dom).closest(".appList_category").find(".appList_category_main").slideUp();
                    }

                });
            });
            this.element.on("click", function () {
               self.element.find(".form_popup_expand").removeClass("form_popup_expand")
            });
            
            /************ 设置弹出层 start ****************/
            this.element.find(".modeling_app_operation").each(function (index, dom) {
                var $ul = self.getPopupContent();
                var code = $(dom).attr("data-oper-code");
                var appId = $(dom).closest("li[data-applist-id]").attr("data-applist-id");
                var showOpers = self.tmpStatus[code]["showOper"];
                var $lis = $ul.find("li");
                $lis.hide();
                $lis.each(function (index, d) {
                    if ($.inArray($(d).attr("data-form-oper-method"), showOpers) > -1) {
                        $(d).show();
                        $(d).on("click", function () {
                            var method = $(this).attr("data-form-oper-method");
                            self.runPopupEvent(appId, method);
                            self.hidePopup();
                        });
                    }
                });

                var $popup = $("<div class='form_popup'/>");
                $popup.append($ul);
                $(dom).append($popup);
                $(dom).on("click",  function (event) {
                    self.stopBubble(event);
                    self.element.find(".modeling_app_btn_more ").removeClass("form_popup_expand");
                    $(dom).toggleClass("form_popup_expand");
                    $(dom).find(".form_popup").toggleClass("form_popup_expand");
                });
                $(dom).parent().parent().on("click", function (event) {
                    self.stopBubble(event);
                    $(dom).find(".form_popup").removeClass("form_popup_expand");
                });
                $(dom).parent().parent().on("click",  function (event) {
                    self.stopBubble(event);
                    $(dom).find(".form_popup").removeClass("form_popup_expand");
                });
            });
            if (window.app_type_load){
                window.app_type_load.hide();
            }
            /************ 设置弹出层 end ****************/
            //列表视图时更多按钮显示规则
            this.element.find(".modeling_app_table_operation").each(function (index, dom) {
                var code = $(dom).attr("data-oper-code");
                $(dom).on("click",  function (event) {
                    $('.app_table_more_button').hide();
                    $(dom).find(".app_table_more_button").show();
                    $(document).one('click',function(){
                        $('.app_table_more_button').hide();
                    })
                    event.stopPropagation();
                    $(".app_table_more_button").on("click", function(e){
                        e.stopPropagation();
                    });
                });
                var $lis = $(dom).find(".buttonOptionList").children("li");
                $lis.each(function (index, d) {
                    $(d).on("click", function () {
                        var method= $(d).attr("data-table-oper-method");
                        var appId= $(d).attr("data-applist-id");
                        var appName= $(d).attr("data-applist-name");
                        var status =  $(d).attr("data-oper-code");
                        if (method === "export") {
                            self.exportFunc(appId, appName);
                        }else if (method === "version") {
                            self.versionFunc(appId, appName);
                        }else if (method === "selfBuildApp"){
                            if (licenseInfo.licenseMode === 'runner' && licenseInfo.licenseCount === 0 ){
                                var html = "<p style='text-align: left;font-weight:bold;'>"+modelingLang['modelingLicense.license.cannot.add.tip1']+"</p><p style='text-align: left;'>"+modelingLang['modelingLicense.license.cannot.add.tip2']+"</p>";
                                var param = {"title":modelingLang['modelingLicense.license.tipTitle'],"html":html}
                                dialog.alert(param);
                            }else if (licenseInfo.licenseMode === 'runner' && licenseInfo.remainBuild === 0 && status === "00"){
                                dialog.alert(modelingLang['modelingLicense.number.less.tips'])
                            }else {
                                dialog.confirm(modelingLang['modelingLicense.confirm.switch.self-built.app'],function(flag){
                                    if (flag){
                                        self.converSelfBuild(appId);
                                    }
                                });
                            }
                        }else {
                            self.runPopupEvent(appId, method);
                        }
                        $('.app_table_more_button').hide();
                    });
                });
            });
            //列表激活操作
            this.element.find("[data-table-script-click]").each(function (index, dom) {
                var type = $(dom).attr("data-table-script-click");
                var appId = $(dom).attr("data-applist-id");
                var appName  = $(dom).attr("data-applist-name");
                $(dom).on("click", function (event) {
                    self.stopBubble(event);
                    if (type === "actionCode"){
                        self.actionCodeFunc(appId, appName);
                    }
                });
            });

            // 设置自定义title
            this.rendTitleTip();
        },

        // 设置自定义title
        rendTitleTip : function(){
        	var self = this;
        	// 偏移量
			var fixOffSet = {
				x : 10,
				y : 20
			};
			self.element.find("[data-r-title]").on("mouseover", function(e) {
				// 如果是【更多】按钮，会有弹出的悬浮框
				if($(this).hasClass("form_popup_expand")){
					return;
				}
				var title = $(this).attr("data-r-title");
				var $tip = $("<div class='title-tip' ></div>").appendTo(document.body);
				$tip.html("<span>" + title + "</span>");
				$tip.css({
					"top" : (e.pageY + fixOffSet.y) + "px",
					"left" : (e.pageX + fixOffSet.x) + "px"
				}).show("fast");
			}).on("mouseout click", function(e) {
				$(".title-tip").remove();
			}).on("mousemove", function(e) {
				$(".title-tip").css({
					"top" : (e.pageY + fixOffSet.y) + "px",
					"left" : (e.pageX + fixOffSet.x) + "px"
				});
			});
        },

        alertMore:function(){

        },
        runPopupEvent: function (appId, method) {
            var self = this;
            if (method === "deleteByAjax") {
                dialog.confirm(listOption.lang.DeleteTips, function (value) {
                    if (value === true) {
                        self.ajaxReq(appId, method);
                    }
                });
            } else if (method === "deleteData") {
                dialog.confirm(listOption.lang.DeleteTips, function (value) {
                    if (value === true) {
                        self.ajaxReq(appId, method);
                    }
                });
            }else {
                self.ajaxReq(appId, method);
            }
        },

        ajaxReq: function (appId, method) {
            var requestType = "get";
            var self = this;
            $.ajax({
                url: Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=" + method + "&fdId=" + appId,
                type: requestType,
                jsonp: "jsonpcallback",
                success: function (rs) {
                    if (rs.status === "00") {
                        dialog.success(listOption.lang.OperateSuccess);
                        topic.channel("modelingAppList").publish("app.update");
                    } else if (rs.status === "02") {
                        //弹框提示
                        self.relatedHandler(rs.data.dialog);
                    } else {
                        dialog.failure(rs.errmsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    //dialog.failure(rs.errmsg);status
                    if (XMLHttpRequest.status === 403) {
                        dialog.failure(listOption.lang.OperateTips);
                    } else {
                        dialog.failure(textStatus);
                    }
                }
            });
        },

        /**
         * 关联弹框
         */
        relatedHandler: function (datas) {
            var url = '/sys/modeling/base/resources/jsp/dialog_relation.jsp';
            dialog.iframe(url, listOption.lang.DeleteAssociatedModule, function (data) {
            }, {
                width: 600,
                height: 400,
                params: {relatedDatas: datas, delObjType: 'app'}
            });
        },

        /**
         * 应用导出
         */
        exportFunc: function (appId, appName) {
            var expprt_load  =dialog.loading();
            $.ajax({
                url: Com_Parameter.ContextPath + "sys/modeling/base/modelingDatainitMain.do?method=checkRelated&fdAppId=" + appId,
                type: "get",
                jsonp: "jsonpcallback",
                success: function (rs) {
                    if (rs.status === "00") {
                        if (rs.allDependApp) {
                            //有依赖
                            var url = '/sys/modeling/datainit/export/dialog_relation.jsp';
                            dialog.iframe(url, listOption.lang.export + appName + listOption.lang.fdApplication + rs.baselineVersion, function (data) {
                            }, {
                                width: 540+5,
                                height: 398,
                                params: {relatedDatas: rs}
                            });
                        }
                        expprt_load.hide();
                    } else {
                        expprt_load.hide();
                        dialog.failure(rs.errmsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    expprt_load.hide();
                    if (XMLHttpRequest.status === 403) {
                        dialog.failure(listOption.lang.OperateTips);
                    } else {
                        dialog.failure(textStatus);
                    }
                }
            });
        },

        /**
         * 版本管理
         */
        versionFunc: function(fdAppId, appName){
            var self = this;
            var currentLang = getSysLangInfo()
            var url = '/sys/modeling/base/appVersion/dialog_index.jsp?fdAppId=' + fdAppId+'&currentLang='+currentLang;
            dialog.iframe(url, listOption.lang.fdApplication, function (flag) {
                self.updatePage();
            }, {
                width: 570,
                height: 564,
                params : { appName : appName }
            });
        },
        converSelfBuild: function (appId) {
            var requestType = "get";
            var self = this;
            $.ajax({
                url: Com_Parameter.ContextPath + "sys/modeling/base/modelingApplication.do?method=converSelfBuild&fdId=" + appId,
                type: requestType,
                jsonp: "jsonpcallback",
                success: function (rs) {
                    if (rs.status === "00") {
                        dialog.success(listOption.lang.OperateSuccess);
                        self.updatePage();
                    } else if (rs.status === "02") {
                        //弹框提示
                        self.relatedHandler(rs.data.dialog);
                    } else {
                        dialog.failure(rs.errmsg);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    //dialog.failure(rs.errmsg);status
                    if (XMLHttpRequest.status === 403) {
                        dialog.failure(listOption.lang.OperateTips);
                    } else {
                        dialog.failure(textStatus);
                    }
                }
            });
        },

        actionCodeFunc:function (fdAppId){
            var self = this;
            var url = '/sys/modeling/base/modelingApplication.do?method=activateAppPage&fdId ='+fdAppId;
            dialog.iframe(url, modelingLang['modelingLicense.activate.app'],function (flag) {
               if (flag === "true"){
                   self.updatePage();
               }
            }, {
                width: 570,
                height: 400,
                params : { fdAppId : fdAppId }
            });
        },

        hidePopup: function () {
            this.env.curTriggerPopup = null;
            topic.channel("modelingPopup").publish("app.popup.hide");
        },

        closeDialog: function (rt) {
            if (rt && rt.type === "success") {
                this.updatePage();
            }
        },

        // popupWgt调用（由主控件控制是否弹出弹出层）
        isExpandPopup: function (triggerObject) {
            // 如果触发的是当前弹出层，则不展示，同时把对象置空
            if (this.env.curTriggerPopup === triggerObject) {
                this.env.curTriggerPopup = null;
                return false;
            } else {
                this.env.curTriggerPopup = triggerObject;
                return true;
            }
        },

        // 弹出层表单内容
        getPopupContent: function () {
            var $ul = $("<ul class='modeling_app_operation_alert' />");
            $ul.append("<li data-form-oper-method='publish'>"+listOption.lang.pubApp+"</li>");
            $ul.append("<li data-form-oper-method='cancelPublish'>"+listOption.lang.unpublish+"</li>");
            $ul.append("<li data-form-oper-method='startApp'>"+listOption.lang.enableApp+"</li>");
            $ul.append("<li data-form-oper-method='endApp'>"+listOption.lang.disableApp+"</li>");
            $ul.append("<li data-form-oper-method='deleteByAjax'>"+listOption.lang.deleteApp+"</li>");
            $ul.append("<li data-form-oper-method='deleteData'>"+listOption.lang.deleteData+"</li>");
            // $ul.append("<li data-form-oper-method='export'>导出应用</li>");
            return $ul;
        },

        // 选择性的显示某些按钮操作 params:{dom:xxx,popup:wgt}
        updateContent: function (params) {
            var code = $(params.dom).attr("data-oper-code");
            var showOpers = this.tmpStatus[code]["showOper"];
            var $lis = params.popup.contentElement.find("li");
            $lis.hide();
            $lis.each(function (index, dom) {
                if ($.inArray($(dom).attr("data-form-oper-method"), showOpers) > -1) {
                    $(dom).show();
                }
            });
        },

        // 设置筛选条件
        setCriteria: function (key, value) {
            this.env.criteria[key] = value;
        },
        clearCriteria: function (key, value) {
            var appName = this.env.criteria["like.fdAppName"];
            this.env.criteria = {"like.fdAppName": appName}
        },
        // 对应用数据，进行分类处理
        classifyAppInfos: function (appInfos) {
            var rs = {};
            rs.noCategorys = [];
            rs.categorys = {};	//{"xxx(categoryId)" : {"name":"xxx","order":"xxx","apps":[{...},{...}]} }
            for (var i = 0; i < appInfos.length; i++) {
                var appInfo = appInfos[i];
                if (appInfo.categoryId) {
                    if (!rs.categorys.hasOwnProperty(appInfo.categoryId)) {
                        rs.categorys[appInfo.categoryId] = {};
                        rs.categorys[appInfo.categoryId]["apps"] = [];
                    }
                    rs.categorys[appInfo.categoryId].name = appInfo.categoryName;
                    rs.categorys[appInfo.categoryId].order = appInfo.categoryOrder;
                    rs.categorys[appInfo.categoryId].time = appInfo.categoryTime;
                    rs.categorys[appInfo.categoryId].isNull= appInfo.categoryOrderIsNull;
                    rs.categorys[appInfo.categoryId].apps.push(appInfo);
                } else {
                    rs.noCategorys.push(appInfo);
                }
            }
            return rs;
        },
        listTableAppInfos: function (appInfos) {
            var rs = {};
            rs.tableApps = [];
            for (var i = 0; i < appInfos.length; i++) {
                var appInfo = appInfos[i];
                rs.tableApps.push(appInfo);
            }
            return rs;
        },
        tableAddButtonShow: function (authCreate,licenseCreate,viewType) {
            var self = this;
            if (viewType === "1"){
                $("#head_newapp_table").hide();
            }else {
                if(authCreate && licenseCreate){
                    $("#head_newapp_table").show();
                }else{
                    $("#head_newapp_table").hide();
                }
            }
        },
        stopBubble:function (e) {
        //如果提供了事件对象，则这是一个非IE浏览器
        if ( e && e.stopPropagation )
        //因此它支持W3C的stopPropagation()方法
            e.stopPropagation();
        else
        //否则，我们需要使用IE的方式来取消事件冒泡
            window.event.cancelBubble = true;
    },
        getModelingLang :function (){
            return modelingLang;
        },

    })

    exports.ListView = ListView;
})