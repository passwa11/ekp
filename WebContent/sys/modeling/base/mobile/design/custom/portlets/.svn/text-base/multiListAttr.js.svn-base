/**
 * 多选的列表视图(属性面板)
 *
 * Statistics和ListView都继承该类
 */
define(function(require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var env = require("lui/util/env");
    var render = require("lui/view/render");
    var dialog = require("lui/dialog");
    var topic = require('lui/topic');
    var modelingLang = require("lang!sys-modeling-base");
    var multiListTab = require("sys/modeling/base/mobile/design/custom/portlets/multiListTab");
    var MultiListAttr =  base.DataView.extend({

        renderHtml : "/sys/modeling/base/mobile/design/custom/portlets/multiListAttrRender.html#",

        initProps: function($super,cfg) {
            $super(cfg);
            this.isCount = cfg.isCount || false;	// 是否需要设置总计页签
            this.widgetKey = cfg.widgetKey || "";
            this.data = cfg.data || {};
            this.area = cfg.area || null;
            this.isIcon = cfg.isIcon || false;
            this.parent = cfg.parent || null;
            this.isMulti = cfg.isMulti || false;
            var uuId = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.data.uuId = uuId;
        },

        startup : function($super,cfg){
            this.setRender(new render.Template({
                src : this.renderHtml,
                parent : this
            }));
            this.render.startup();
            $super(cfg);
        },

        doRender: function($super, cfg){
            $super(cfg);
            this.area.append(this.element);
            this.addEvent();
            this.parent.addWgt(this);
        },

        draw : function($super, cfg){
            $super(cfg);
            this.render.get(this.data);
        },

        /****************** 属性配置 start ************************/

        addEvent : function(){
            var self = this;
            var $formItem = this.element;

            /************* 添加事件 start *****************/
            // 点击标题，收起和展开内容
            $formItem.find(".content_name").off("click").on("click",function(){
                var $itemMain = $formItem.find(".field_item_content");
                $itemMain.slideToggle("normal",function(){
                    if($formItem.find(".field_item_body").css("display") == 'none'){
                        $formItem.find(".field_item_body").css({"display":"none"});
                    }else{
                        $formItem.find(".field_item_body").removeAttr("style");
                    }
                    $formItem.find(".field_item_value").toggleClass('multi_shrink');
                });
                $formItem.find(".field_item_value").toggleClass('shrink');
            });

            // 删除该条记录
            $formItem.find(".form_item_delete").off("click").on("click",function(event){
                event.stopPropagation();
                event.preventDefault();
                var num = $(self.area).find(".field_item_value");
                if (num.length<=1){
                    dialog.alert(modelingLang['modeling.keep.lastone']);
                }else{
                    $formItem.remove();
                    self.parent.deleteWgt(self);
                }
            });

            // 标题失焦时，同步标题
            $formItem.find("[name*='formItemTitle']").on("input",function(){
                var val = $(this).val();
                val = val || modelingLang['modeling.Undefined'] ;
                $formItem.find(".form_item_title").html(val);
                $formItem.find(".form_item_title").attr("title",val);
                self.parent.setLeftShow();
            });

            //图标选择
            $formItem.find(".content-icon-choose").on("click", function(){
                var iconDom = this;
                var url = "/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=icon&iconTypeRange=2";
                dialog.iframe(url, modelingLang["modeling.app.selectIcon"], function(rtn){
                    if(rtn){
                        var icon = rtn.className;
                        $(iconDom).find("i").attr("class","mui " + rtn.className);
                        if(rtn.url){
                            var url = env.fn.formatUrl(rtn.url);
                            $(iconDom).find("i").css("background","url(" + url + ")  no-repeat center")
                                .css("height","24px").css("width","24px").css("background-size","contain");
                            $formItem.find("[name$='iconType']").val("4");//素材库
                            icon = rtn.url;
                        }else{
                            $formItem.find("[name$='iconType']").val("2");//图标
                        }
                        $formItem.find("[name$='icon']").val(icon).trigger($.Event("change"));
                        self.parent.setLeftShow();
                    }
                } ,{
                    width: 800,
                    height: 500,
                    close: true
                });
            });

            if(this.isMulti){
                this.multiTabEvent($formItem);
            }else{
                this.singleTabEvent($formItem);
            }
            if(this.parent && this.parent.addValidateElements){
                //标题绑定必填校验
                this.parent.addValidateElements($formItem.find("[name*='formItemTitle']")[0],"required");
                //图标绑定必填校验
                this.parent.addValidateElements($formItem.find("[name*='icon']")[0],"required");
            }
            /************* 添加事件 end *****************/
            if(this.isCount){
                var dataValue = this.data || {};
                self.updateCountBlock(dataValue.lvCollection, dataValue.countLv, dataValue.uuId, $formItem);
            }
            return $formItem;
        },

        //多标签事件绑定
        multiTabEvent : function($formItem){
            var self = this;
            // 点击视图穿透设置，收起和展开内容
            $formItem.find(".content-through-button").on("click",function(){
                //$formItem.find(".content-through").css({"display":"none"});
                self.rebuildTabItem();
                $formItem.find(".field_item_listTabs").css({"display":"block"});
                if ($formItem.find(".content_item_box").length==0){
                    $formItem.find(".field-item-throuth-add").trigger("click")
                }if($formItem.find(".content_item_box").length==1){
                    $formItem.find(".form_tab_delete").hide();
                }
            });

            //点击新增，增加页签
            $formItem.find(".field-item-throuth-add").on("click",function(){
                var $TitemTabs =$($formItem.find(".field_item_listTabs"))
                var tab = new multiListTab.MultiListTab({parent: self,widgetKey:self.widgetKey,area:$TitemTabs});
                var TitemTab =tab.element;
                if ($formItem.find(".content_item_box").length==0){
                    $(TitemTab).find(".form_tab_delete").hide();
                }else {
                    $formItem.find(".form_tab_delete:first").show();
                }
                $TitemTabs.append(TitemTab);
            });
            //点击确定事件
            $formItem.find(".contentBackOk").on("click",function(){
                $formItem.find(".content_tab_name").trigger("change");
                $formItem.find("input[name *='listView']").trigger("change");
                var contentWraps = $formItem.find(".field_item_listTabs").find(".content_wrap");
                var pass = true;
                //必填校验
                for (var i=0;i<contentWraps.length;i++){
                    var contentOptInfo = $(contentWraps[i]);
                    pass = self.parent.parent.customValidation.validateElement(contentOptInfo.find("[name*='formTabTitle']")[0]);
                    if (!self.parent.parent.customValidation.validateElement(contentOptInfo.find("[name='listView']")[0]) || !pass){
                        pass = false;
                    }
                }
                if (pass){
                    $formItem.find(".field_item_listTabs").css({"display":"none"});
                    $formItem.find(".addTabButton").css({"display":"none"});
                    var lvcondition = [];
                    var tabs = $formItem.find(".content_wrap");
                    $formItem.find(".content-tabs").find("li").remove();
                    //为跳转页面内容赋值
                    for (var i=0;i<tabs.length;i++){
                        var tab = $(tabs[i]);
                        var tabText = tab.find(".content_tab_name").val();
                        var listviewValue =  tab.find("input[name *='listView']").val();
                        var listviewText =  tab.find(".listViewText").html();
                        var appId = tab.find("[name='listViewAppId']").val();
                        var nodeType = tab.find("[name='nodeType']").val();
                        lvcondition.push({tab : tabText, value : listviewValue,text:listviewText,appId:appId,nodeType:nodeType});
                        $formItem.find(".content-tabs").append("<li title='"+ tabText+"'>"+tabText+"<i class='delIcon'></i></li>")
                    }
                    //重新计算宽度，防止页面布局混乱
                    $formItem.width($(".model-edit-right").width() - 50);
                    $formItem.find("input[name *='listviews']").val(JSON.stringify(lvcondition));
                    $formItem.find(".content-through-tabs").css({"display":"block"});
                    self.parent.setLeftShow();
                    //为标签绑定删除事件
                    $formItem.find(".content-tabs>li>i").off("click").on("click",function(){
                        var idx = $(this).closest("li").index();
                        $(this).closest("li").remove();
                        lvcondition.splice(idx,1);
                        $formItem.find("input[name *='listviews']").val(JSON.stringify(lvcondition));
                        $formItem.find("input[name *='listviews']").trigger($.Event("change"));
                        self.parent.setLeftShow();
                    })
                    //清空标签html
                    tabs.remove();
                    //触发跳转页面内容值改变事件
                    $formItem.find("input[name *='listviews']").trigger($.Event("change"));
                }
            });

            //点击取消事件
            $formItem.find(".contentBackCancel").on("click",function(){
                var listviews = $formItem.find("input[name *='listviews']").val();
                //判断是否为空
                if(listviews && listviews !== "[]"){
                    //不为空，则隐藏“设置”按钮
                    $formItem.find(".field_item_listTabs").css({"display":"none"});
                    $formItem.find(".addTabButton").css({"display":"none"});
                    $formItem.find(".content-through-tabs").css({"display":"block"});
                }else{
                    //为空则显示“设置”按钮
                    $formItem.find(".field_item_listTabs").css({"display":"none"});
                    $formItem.find(".addTabButton").css({"display":"block"});
                    $formItem.find(".content-through-tabs").css({"display":"none"});
                }
                $formItem.find(".content_wrap").remove();
            });
            //构建跳转页面内容
            self.buildTabItem($formItem);
            //跳转页面内容绑定值改变事件
            $formItem.find("input[name *='listviews']").off("change").on("change",function() {
                self.parent.validateElement(this);
            });
            //跳转页面内容绑定必填校验
            this.parent.addValidateElements($formItem.find("input[name *='listviews']")[0],"arrayRequired");
        },
        //单标签事件绑定
        singleTabEvent:function($formItem) {
            var self = this;
            var uuId = this.data.uuId;
            // 添加列表视图点击事件
            $formItem.find(".listVieElement").on("click",function(){
                // ___appId：在edit.jsp定义的全局变量
                var url = "/sys/modeling/base/resources/js/dialog/leftNav/leftNavDialog.jsp?isTodo=false";
                var appId = $formItem.find("[name*='listViewAppId']").val();
                dialog.iframe(url,modelingLang["table.modelingAppMobileListView"],function(rtn){
                    if(rtn && rtn.data){
                        self.setValuesAfterListViewDialog(uuId, rtn, $formItem);
                    }
                },{
                    width : 800,
                    height : 500,
                    params : {
                        "cateBean" : "modelingAppModelService",
                        "fdAppId" : appId ||___appId,
                        "dataBean" : "modelingAppMobileListViewService&modelId=!{value}",
                        "isShowCalendar":self.widgetKey === "listView"
                    }
                });
            });
            //跳转页面内容绑定值改变事件
            $formItem.find("[name='"+ uuId +"_listView']").off("change").on("change",function() {
                self.parent.validateElement(this);
            });
            this.parent.addValidateElements($formItem.find("[name='"+ uuId +"_listView']")[0],"required");
        },

        setValuesAfterListViewDialog : function(uuId, rtn, $formItem){
            if(this.isCount){
                var views = this.transStrToJson(rtn.data.viewsjson);
                views = this.formatViews(views);
                this.updateCountBlock(views, "", uuId, $formItem);
            }
            $formItem.find(".listViewText").html(rtn.data.text);
            $formItem.find(".listViewText").attr("title",rtn.data.text);
            $formItem.find("[name $='listViewText']").val(rtn.data.text);
            $formItem.find("[name $='listViewText']").attr("title",rtn.data.text);
            $formItem.find("[name $='listView']").val(rtn.data.value);
            $formItem.find("[name $='listViewAppId']").val(rtn.appId || ___appId);
            $formItem.find("[name $='nodeType']").val(rtn.data.nodetype || rtn.data.nodeType);
            // 此处添加主动触发，是为了更新data
            $formItem.find("[name $='listView']").val(rtn.data.value).trigger($.Event("change"));
        },

        //重新构建标签
        rebuildTabItem :function() {
            this.element.find(".content_wrap").remove();
            var newlistviews = JSON.parse(this.element.find("input[name*='listviews']").val() || "[]");
            for (var i = 0; i < newlistviews.length; i++) {
                var listview = newlistviews[i];
                var $TitemTabs = this.element.find(".field_item_listTabs");
                var tab = new multiListTab.MultiListTab({parent: this,widgetKey:this.widgetKey,area:$TitemTabs});
                $(tab.element).find(".content_tab_name").val(listview.tab);
                $(tab.element).find("input[name *='listView']").val(listview.value);
                $(tab.element).find(".listViewText").html(listview.text);
                $(tab.element).find(".listViewText").attr("title",listview.text);
                $(tab.element).find(".form_tab_title").html(listview.tab);
                $(tab.element).find(".form_tab_title").attr("title",listview.tab);
                $(tab.element).find("[name='listViewAppId']").val(listview.appId);
                $(tab.element).find("[name='nodeType']").val(listview.nodeType);
                $(tab.element).find(".field_item_listTabs").css({"display":"block"});
            }
        },

        //初始化标签
        buildTabItem:function($formItem){
            var self = this;
            var formData = this.data;
            var newlistviews = formData.newlistView;
            if (newlistviews instanceof Array) {
                if (newlistviews.length > 0) {
                    $formItem.find(".field_item_listTabs").css({"display":"none"});
                    $formItem.find(".addTabButton").css({"display":"none"});
                    var lvcondition = [];
                    $formItem.find(".content-tabs").find("li").remove();
                    for (var i = 0; i < newlistviews.length; i++) {
                        var listview = newlistviews[i];
                        var tabText = listview.tab;
                        var listviewValue =  listview.value;
                        var listviewText =  listview.text;
                        var appId = listview.appId;
                        var nodeType = listview.nodeType;
                        lvcondition.push({tab : tabText, value : listviewValue,text:listviewText,appId:appId,nodeType:nodeType});
                        $formItem.find(".content-tabs").append("<li title='"+ tabText+"'>"+tabText+"<i class='delIcon'></i></li>")
                    }
                    //重新计算宽度，防止页面布局混乱
                    $formItem.width($(".model-edit-right").width() - 50);
                    $formItem.find("input[name $='listviews']").val(JSON.stringify(lvcondition));
                    $formItem.find(".content-through-tabs").css({"display":"block"});
                    $formItem.find(".content-tabs>li>i").off("click").on("click",function(){
                        var idx = $(this).closest("li").index();
                        $(this).closest("li").remove();
                        lvcondition.splice(idx,1);
                        $formItem.find("input[name $='listviews']").val(JSON.stringify(lvcondition));
                        $formItem.find("input[name *='listviews']").trigger($.Event("change"));
                        self.parent.setLeftShow();
                    })
                }
            }
        },

        // 把字符串数组转换为json格式的字符串
        transStrToJson : function(str){
            str = str || "{}";
            return JSON.parse(str);
        },

        // 去除views多余的字段属性
        formatViews : function(views){
            var rs = [];
            for(var i = 0;i < views.length;i++){
                var view = views[i];
                rs.push({text : view.text, value : view.value});
            }
            return rs;
        },

        // 设置总计页签对应哪个页签
        updateCountBlock : function(views, value, uuId, area){
            views = views || [];
            if(views.length > 0){
                area.find(".lvCountWrap").html(this.createCustomSelect(views,value, uuId));
                area.find("[name$='lvCollection']").val(JSON.stringify(views));
            }else{
                area.find(".lvCountWrap").html(this.createCustomSelect(views,value, uuId));
            }
        },

        createCustomSelect : function(views,value, uuId){
            var text = "===请选择===";
            var lvCountHtml = "";
            lvCountHtml += "<div class='model-mask-panel-table-select' style='margin-left:0px'>";
            lvCountHtml += "<p class='model-mask-panel-table-select-val'></p>";
            lvCountHtml += "<div class='model-mask-panel-table-option'>";
            for(var i = 0;i < views.length;i++){
                var lvTab = views[i];
                lvCountHtml += "<div option-value='"+ lvTab.value +"'";
                if(lvTab.value === value){
                    text = lvTab.text;
                }
                lvCountHtml += ">"+ lvTab.text +"</div>";
            }
            lvCountHtml += "</div>";

            lvCountHtml += "<input name='"+ uuId +"_countLv' data-update-event='change' type='hidden' value='"+ value +"' data-validate='countLvRequired'/>";

            lvCountHtml += "</div>";
            var $select = $(lvCountHtml);
            $select.find(".model-mask-panel-table-select-val").html(text);
            // 添加事件
            $select.on("click", function(event) {
                event.stopPropagation();
                $(this).toggleClass("active")
            });
            $select.find(".model-mask-panel-table-option div").on("click", function () {
                var $tableSelect = $(this).closest(".lvCountWrap");
                var $p = $tableSelect.find(".model-mask-panel-table-select-val");
                $p.html($(this).html());

                var selectVal = $(this).attr("option-value");
                $tableSelect.closest(".content_item_form_element").find("[name*='countLv']").val(selectVal).trigger($.Event("change"));
            });
            return $select;
        },
        /****************** 属性配置 end ************************/

        /************** 校验函数 end *******************/
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        },

        formatUrl :function (url){
            if(url){
                return env.fn.formatUrl(url);
            }
            return "";
        },

        getKeyData : function(){
            var itemInfo = {};
            itemInfo.title = this.element.find("input[name*='formItemTitle']").val();
            if(this.isIcon){
                itemInfo.icon = this.element.find("input[name$='icon']").val();
                itemInfo.iconType = this.element.find("input[name*='iconType']").val();
            }
            itemInfo.listViewAppId = this.element.find("[name*='listViewAppId']").val();
            itemInfo.listViewText = this.element.find("[name*='listViewText']").val();
            if(this.isMulti){
                itemInfo.newlistView = JSON.parse(this.element.find("input[name*='listviews']").val() || "[]");
                //为了统计第一个tab的数据总数，存第一个listviewId
                if(itemInfo.newlistView.length > 0){
                    itemInfo.listView = itemInfo.newlistView[0].value;
                    itemInfo.nodeType = itemInfo.newlistView[0].nodeType;
                }
            }else{
                itemInfo.listView = this.element.find("[name$='listView']").val();
                itemInfo.nodeType = this.element.find("[name$='nodeType']").val();
            }

            if(this.isCount){
                itemInfo.countLv = this.element.find("input[name $='listView']").val() || "";
                itemInfo.lvCollection = JSON.parse(this.element.find("[name$='lvCollection']").val() || "[]");
            }
            return itemInfo;
        },
        destroy : function($super) {
            $super();
        }
    });

    exports.MultiListAttr = MultiListAttr;
})