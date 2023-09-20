/**
 * 多选的列表视图(属性面板)
 *
 * Statistics和ListView都继承该类
 */
define(function(require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require("lui/dialog");
    var modelingLang = require("lang!sys-modeling-base");
    var MultiListTab =  base.Component.extend({

        tempHtml:"<div class=\"content_wrap\" data-lui-position='fdTag-!{index}'>"+
            "<div class='content_tab'>"+
            "<p class=\"form_tab_title\" title='"+modelingLang['modeling.bookmark']+"'>"+modelingLang['modeling.bookmark']+"</p>"+
            "<i class=\"form_tab_delete\"></i>"+
            "</div>"+
            "<div class=\"content_item_box\">"+
            "<div class=\"content_item_form_element content_input\">"+
            "<div class=\"content_body_title\">"+modelingLang['modeling.page.name']+
            "<div style=\"float: right\">\n" +
            "</div></div>"+
            "<div class=\"content_opt\">"+
            "<input class=\"content_tab_name\" name='{%data.uuId%}_formTabTitle' title='"+modelingLang['modeling.bookmark']+"' type=\"text\" placeholder=\""+modelingLang["modeling.please.enter"]+"\"  validate=\"required maxLength(36)\"  data-type=\"validate\"/>" +
            "<span class=\"txtstrong\">*</span>"+
            "</div>"+
            "<p class=\"content_opt_info\"></p>"+
            "</div>"+
            "<div class=\"content_item_form_element content_choose\">"+
            "<p class=\"content_body_title\">"+modelingLang['modeling.Page.content']+"</p>"+
            "<input name=\"listView\" title=\""+modelingLang["table.modelingCollectionView"]+"\"  type=\"hidden\"  validate=\"required\" data-type=\"validate\"/>"+
            "<input name=\"listViewText\"  type=\"hidden\" />"+
            "<input name=\"listViewAppId\"  type=\"hidden\" />"+
            "<input name=\"nodeType\"  type=\"hidden\" />"+
            "<div class=\"content_opt listVieElement\">"+
            "<p class=\"listViewText\">"+modelingLang['modeling.page.choose']+"</p>" +
            "<span class=\"txtstrong\">*</span>"+
            "<i></i>"+
            "</div>"+
            "<p class=\"content_opt_info\"></p>"+
            "</div>"+
            "</div>"+
            "</div>",

        initProps: function ($super, cfg) {
            $super(cfg);
            this.parent = cfg.parent;
            //初始化1
            this.element =$(this.tempHtml);
            this.area = cfg.area;
            this.widgetKey = cfg.widgetKey;
            this.bindEvent();
            this.area.append(this.element);
        },


        /**
         * 事件绑定
         */
        bindEvent: function () {
            var uuId = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            var $formItem = this.element;
            var self = this;
            // 点击页签，收起和展开内容
            this.element.find(".content_tab").on("click",function(){
                var $itemMain = $formItem.find(".content_item_box");

                $itemMain.slideToggle("normal",function(){
                    //#123054 移动端首页功能区收缩后，间距太多
                    if($itemMain.find(".content_item_box").css("display") == 'none'){
                        $itemMain.find(".content_item_box").css({"display":"none"});
                    }else{
                        $itemMain.find(".content_item_box").removeAttr("style");
                    }
                    $itemMain.toggleClass('multi_shrink');
                });
                $formItem.toggleClass('shrink');
            });
            //删除页签
            this.element.find(".form_tab_delete").on("click",function(){
                $(this).closest(".content_wrap").remove();
                var num = $(self.area).find(".content_item_box");
                if (num.length==1){
                    $(self.area).find(".form_tab_delete").hide();
                }else {
                    $formItem.find(".form_tab_delete:first").show();
                }
            });
            // 页签名字失焦时，同步页签
            this.element.find("[name*='formTabTitle']").on("input",function(){
                var val = $(this).val();
                val = val || modelingLang['modeling.Undefined'];
                $formItem.find(".form_tab_title").html(val);
                $formItem.find(".form_tab_title").attr("title",val);
            });

            var self = this;
            // 添加列表视图点击事件
            this.element.find(".listVieElement").on("click",function(){
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
            if(this.parent && this.parent.parent && this.parent.parent.addValidateElements){
                //标题绑定必填校验
                this.parent.parent.addValidateElements($formItem.find("[name*='formTabTitle']")[0],"required");
                //标签选择绑定必填校验
                this.parent.parent.addValidateElements($formItem.find("[name='listView']")[0],"required");
                //标签内容绑定值改变事件
                $formItem.find("[name='listView']").off("change").on("change",function() {
                    self.parent.parent.validateElement(this);
                });
            }
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
                area.find("[name*='lvCollection']").val(JSON.stringify(views));
            }else{
                area.find(".lvCountWrap").html(this.createCustomSelect(views,value, uuId));
            }
        },
        // 把字符串数组转换为json格式的字符串
        transStrToJson : function(str){
            str = str || "{}";
            return JSON.parse(str);
        },

        setValuesAfterListViewDialog : function(uuId, rtn, $formItem){
            if(this.isCount){
                var views = this.transStrToJson(rtn.data.viewsjson);
                views = this.formatViews(views);
                this.updateCountBlock(views, "", uuId, $formItem);
            }

            $formItem.find(".listViewText").html(rtn.data.text);
            $formItem.find(".listViewText").attr("title",rtn.data.text);
            $formItem.find("[name ='listViewText']").val(rtn.data.text);
            $formItem.find("[name ='listViewText']").attr("title",rtn.data.text);
            $formItem.find("[name ='listView']").val(rtn.data.value);
            $formItem.find("[name='listViewAppId']").val(rtn.appId || ___appId);
            $formItem.find("[name='nodeType']").val(rtn.data.nodetype || rtn.data.nodeType);
            // 此处添加主动触发，是为了更新data
            $formItem.find("[name ='listView']").val(rtn.data.value).trigger($.Event("change"));
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
    })
    exports.MultiListTab = MultiListTab;
})