define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var env = require('lui/util/env');
    var modelingLang = require("lang!sys-modeling-base");
    var TimeoutStrategy= base.Container.extend({

        /**
         * 初始化
         * cfg应包含：
         */
        initProps: function ($super, cfg) {
            this.bindEvent(cfg);
            this.initByStoreData(cfg);
        },
        doRender: function ($super, cfg) {
        },
        /**
         * 绑定事件
         */
        bindEvent: function (cfg){
            $('.model_automatic_strategy_content label').css('margin-right','30px');
            $('.model_automatic_strategy_content label input').css('margin-right','6px');

            $('.lui-mulit-zh-cn-html').css('overflow','hidden');

            $(".cycle_input").click(function (event) {
                event.stopPropagation();
                if ($(".cycle_option_list").css("display") == "block") {
                    $(".cycle_option_list").css("display", "none");
                } else {
                    $(".cycle_option_list").css("display", "block");
                    var showText = $("#repeatCycleType").val();
                    console.log(showText)
                    $(".cycle_option_list .cycle_option_list_item").each(function () {
                        $(this).removeAttr("style");
                        if(showText == $(this).text()){
                            $(this).css("color","#4285F4")
                            $(this).css("background","#ecf2fd")
                        }
                    });
                }
            });

            $(".cycle_select").click(function (event) {
                event.stopPropagation();
                if ($(".cycle_option_list").css("display") == "block") {
                    $(".cycle_option_list").css("display", "none");
                } else {
                    $(".cycle_option_list").css("display", "block");
                    var showText = $("#repeatCycleType").val();
                    $(".cycle_option_list .cycle_option_list_item").each(function () {
                        $(this).removeAttr("style");
                        if(showText == $(this).text()){
                            $(this).css("color","#4285F4")
                            $(this).css("background","#ecf2fd")
                        }
                    });
                }
            });

            $(".cycle_option_list").on('click', '.cycle_option_list_item', function (event) {
                var showText = $(this).text();
                $(".cycle_input").val(showText);
            });
            // 点击其他地方关闭下拉列表
            $(document).click(function () {
                if ($(".cycle_option_list").css("display") == "block") {
                    $(".cycle_option_list").css("display", "none");
                }
            });

            $('#reminderNotice').click(function(){
                var text = $('#reminderNotice').val();
                if(text.length == 0){
                    $('#reminderNotice').val(modelingLang['automaticfill.timeout.tips']);
                }

            });

            if("0"==$("[name='isRepeatReminder']").val()){
                $(".repeatCycle").css("display","none");
                $(".repeatCount").css("display","none");
            }

            $('#timeoutStrategyName').blur(function (){
                if(0 == $(this).val().length){
                    $('.timeout_strategy_name_item .error').text(modelingLang["automaticfill.name.cannot.null"]);
                    $('.timeout_strategy_name_item .error').css("display","block");
                    $(this).css("border", "1px solid #F25643");
                    return;
                }
               if(200 <$(this).val().length){
                   $('.timeout_strategy_name_item .error').text(modelingLang["automaticfill.name.exceed.200.characters"]);
                   $('.timeout_strategy_name_item .error').css("display","block");
                   $(this).css("border", "1px solid #F25643");
                   return;
               }
                $('.timeout_strategy_name_item .error').css("display","none");
                $(this).css("border", "1px solid #dddddd");
            });

            $('#reminderNotice').blur(function (){
                if(0 == $(this).val().length){
                    $('.reminder_notice_item .error').css("display","block");
                    $(this).css("border", "1px solid #F25643");
                    return;
                }
                $('.reminder_notice_item .error').css("display","none");
                $(this).css("border", "1px solid #dddddd");
            });

            $('#timeoutDay').blur(function (e){
                inputTime(this)
            });

            $('#timeoutHour').blur(function (e){
                inputTime(this)
            });

            $('#timeoutMinute').blur(function (e){
                inputTime(this)
            });

            $('#repeatCycleCount').blur(function (e){
                inputTime(this)
            });

            $('#timeoutRepeatCount').blur(function (e){
                inputTime(this)
            });

        },
        //提交到后台的数据
        getKeyData: function () {
            var formData = {};


            return formData;
        },
        //后台数据渲染方法
        initByStoreData: function (cfg) {
            $(':radio[name="actionType"]').eq(0).attr("checked",true);
            $("[id='timeoutDay']").val(0);
            $("[id='timeoutHour']").val(0);
            $("[id='timeoutMinute']").val(1);
            $("[id='isEdit']").val(cfg.isEdit);
            $("[id='strategyIndex']").val(cfg.strategyIndex);

            if (cfg.isEdit){
                $("[id='fdId']").val(cfg.fdId);
                $("[id='timeoutStrategyName']").val(cfg.fdName);
                $("[id='timeoutDay']").val(parseInt(cfg.fdTimeoutStamp/(60*60*24)));
                $("[id='timeoutHour']").val(parseInt(cfg.fdTimeoutStamp%(60*60*24)/(60*60)));
                $("[id='timeoutMinute']").val(parseInt(cfg.fdTimeoutStamp%(60*60)/(60)));

                $(':radio[name="actionType"]').eq(cfg.fdActionType).attr("checked",true);
                if(0 == cfg.fdActionType){
                    $("[id='actionType1Setting']").css("display","table-row");
                }else if(1 == cfg.fdActionType){
                    $("[id='actionType1Setting']").css("display","none");
                    //自适应弹窗高度
                    $(".lui_dialog_content",window.parent.document).css("height",230);
                }
                if(cfg.fdActionJson){
                    var actionJson = cfg.fdActionJson;
                    $("[id='reminderNotice']").val(actionJson.reminderNotice);

                    if(actionJson.isRepeatReminder && '1' == actionJson.isRepeatReminder){
                        $("[id='repeatCycleCount']").val(actionJson.repeatCycleCount);
                        $("[id='repeatCycleType']").val(actionJson.repeatCycleType);
                        $("[id='timeoutRepeatCount']").val(actionJson.timeoutRepeatCount);
                        //自适应弹窗高度
                        if(0 == cfg.fdActionType){
                            $(".lui_dialog_content",window.parent.document).css("height",520);
                        }
                    }
                }

            }
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },

    });
    window.addTime = function(e) {
        var input = $(e).parent().siblings("input");
        var inputVal = input.val();
        var err = $(e).parent().siblings(".error");

        var maxNum = 0;
        var minNum = 0;
        var isLimitMax = true;
        var isLimitMin = true;
        var text = modelingLang["automaticfill.configure.error3"];
        switch (input.attr("id")) {
            case 'timeoutDay':
                isLimitMax = false;
                isLimitMin = false;
                break;
            case 'timeoutHour':
                text = modelingLang["automaticfill.invalid.hour.entered"];
                maxNum = 23;
                isLimitMax = true;
                isLimitMin = false;
                break;
            case 'timeoutMinute':
                text = modelingLang["automaticfill.invalid.minute.entered"];
                maxNum = 59;
                isLimitMax = true;
                isLimitMin = false;
                break;
            case 'repeatCycleCount':
                minNum = 1;
                text = modelingLang["automaticfill.configure.error6"];
                isLimitMax = false;
                isLimitMin = true;
                break;
            case 'timeoutRepeatCount':
                minNum = 1;
                text = modelingLang["automaticfill.configure.error6"];
                isLimitMax = false;
                isLimitMin = true;
                break;
        }
        //判断是否是整数
        if(!isNum(inputVal)){
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        }
        input.val(Number(inputVal) + 1);
        var number = Number(input.val())
        if (number < 0) {
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
        } else if(isLimitMax && number > maxNum) {
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        } else if(isLimitMin && number < minNum) {
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        } else {
            err.css("display", "none");
            input.css("border", "1px solid #dddddd");
        }
    };

    window.cutTime = function(e) {
        var input = $(e).parent().siblings("input");
        var inputVal = input.val();
        var err = $(e).parent().siblings(".error");
        var maxNum = 0;
        var minNum = 0;
        var isLimitMax = true;
        var isLimitMin = true;
        var text = modelingLang["automaticfill.configure.error3"];
        switch (input.attr("id")) {
            case 'timeoutDay':
                isLimitMax = false;
                isLimitMin = false;
                break;
            case 'timeoutHour':
                text = modelingLang["automaticfill.invalid.hour.entered"];
                maxNum = 23;
                isLimitMax = true;
                isLimitMin = false;
                break;
            case 'timeoutMinute':
                text = modelingLang["automaticfill.invalid.minute.entered"];
                maxNum = 59;
                isLimitMax = true;
                isLimitMin = false;
                break;
            case 'repeatCycleCount':
                minNum = 1;
                text = modelingLang["automaticfill.configure.error6"];
                isLimitMax = false;
                isLimitMin = true;
                break;
            case 'timeoutRepeatCount':
                minNum = 1;
                text = modelingLang["automaticfill.configure.error6"];
                isLimitMax = false;
                isLimitMin = true;
                break;
        }
        //判断是否是整数
        if(!isNum(inputVal)){
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        }
        input.val(Number(inputVal) - 1);
        var number = Number(input.val())
        if (number < 0) {
            err.css("display", "block");
            input.css("border", "1px solid #F25643");
            return;
        } else if(isLimitMax && number > maxNum) {
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        } else if(isLimitMin && number < minNum) {
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        } else {
            err.css("display", "none");
            input.css("border", "1px solid #dddddd");
            return;
        }
    };

    window.inputTime = function(e) {
        var input = $(e);
        var err = $(e).siblings(".error");
        var number = Number(input.val());
        var maxNum = 0;
        var minNum = 0;
        var isLimitMax = true;
        var isLimitMin = true;
        var text = modelingLang["automaticfill.configure.error3"];
        switch (input.attr("id")) {
            case 'timeoutDay':
                 isLimitMax = false;
                 isLimitMin = false;
                break;
            case 'timeoutHour':
                text = modelingLang["automaticfill.invalid.hour.entered"];
                maxNum = 23;
                isLimitMax = true;
                isLimitMin = false;
                break;
            case 'timeoutMinute':
                text = modelingLang["automaticfill.invalid.minute.entered"];
                maxNum = 59;
                isLimitMax = true;
                isLimitMin = false;
                break;
            case 'repeatCycleCount':
                minNum = 1;
                text = modelingLang["automaticfill.configure.error6"];
                isLimitMax = false;
                isLimitMin = true;
                break;
            case 'timeoutRepeatCount':
                minNum = 1;
                text = modelingLang["automaticfill.configure.error6"];
                isLimitMax = false;
                isLimitMin = true;
                break;
        }
        //判断是否是整数
        if(!isNum(number)){
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        }
        if (number < 0) {
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        } else if(isLimitMax && number > maxNum) {
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        } else if(isLimitMin && number < minNum) {
            err.css("display", "block");
            err.text(text);
            input.css("border", "1px solid #F25643");
            return;
        } else {
            err.css("display", "none");
            input.css("border", "1px solid #dddddd");
            return;
        }
    };


    window.isRepeatReminderChange = function(e) {
        if("0"==$("[name='isRepeatReminder']").val()){
            $(".repeatCycle").css("display","none");
            $(".repeatCount").css("display","none");
            //自适应弹窗高度
            $(".lui_dialog_content",window.parent.document).css("height",420);
        }else{
            $(".repeatCycle").css("display","");
            $(".repeatCount").css("display","");
            $("[id='repeatCycleCount']").val(1);
            $("[id='repeatCycleType']").val(modelingLang['automaticfill.day']);
            $("[id='timeoutRepeatCount']").val(1);
            //自适应弹窗高度
            $(".lui_dialog_content",window.parent.document).css("height",520);
        }

    };

    window.isNum = function(val){
        //#138944 自动填报催办的重复周期和次数，设置小于1后，再按往上，没有反应
        if(typeof val != 'number' && val.substr(0,1) == '-' && val.length>1){
            val = val.substr(1,val.length)
        }
        var ex = /^\d+$/;
        return ex.test(val);
    }

    //后续可以整理校验逻辑
    window.AutoTimeoutStrategyValidate = {
        validate:function (cfg){
            if (!cfg) {
                return modelingLang["modeling.configure.first"];
            }
            if(!cfg.timeoutStrategyName){
                $('.timeout_strategy_name_item .error').text(modelingLang["automaticfill.name.cannot.null"]);
                $('.timeout_strategy_name_item .error').css("display","block");
                $(this).css("border", "1px solid #F25643");

                if(cfg.actionType == 0) {
                    var actionJson = cfg.actionJson;
                    if (!actionJson.reminderNotice) {
                        $('.reminder_notice_item .error').css("display", "block");
                        $(this).css("border", "1px solid #F25643");
                        return modelingLang["automaticfill.remindernotification.cannot.null"];
                    }
                }
                return modelingLang["automaticfill.name.cannot.null"];
            }
            if(200<cfg.timeoutStrategyName.length){
                $('.timeout_strategy_name_item .error').text(modelingLang["automaticfill.name.exceed.200.characters"]);
                $('.timeout_strategy_name_item .error').css("display","block");
                $(this).css("border", "1px solid #F25643");
                return modelingLang["automaticfill.name.exceed.200.characters"];
            }
            if(!cfg.timeoutDay){
                cfg.timeoutDay=0;
            }
            if(!isNum(cfg.timeoutDay)){
                return  modelingLang["automaticfill.days.must.integer"];
            }
            if(cfg.timeoutDay<0){
                return  modelingLang["automaticfill.days.cannot.less.0"];
            }
            if(!cfg.timeoutHour){
                cfg.timeoutHour=0;
            }
            if(!isNum(cfg.timeoutHour)){
                return  modelingLang["automaticfill.hour.must.integer"];
            }
            if(0>cfg.timeoutHour || 24 <= cfg.timeoutHour){
                return modelingLang["automaticfill.invalid.hour.entered"];
            }
            if(!cfg.timeoutMinute){
                cfg.timeoutMinute=0;
            }
            if(!isNum(cfg.timeoutMinute)){
                return  modelingLang["automaticfill.minute.must.integer"];
            }
            if(0>cfg.timeoutMinute || 60<=cfg.timeoutMinute){
                return modelingLang["automaticfill.invalid.minute.entered"];
            }

            if(cfg.timeoutDay ==0 && cfg.timeoutHour == 0 && cfg.timeoutMinute == 0){
                var input = $('#timeoutMinute').siblings("input");
                var err = $('#timeoutMinute').siblings(".error");
                err.css("display", "block");
                err.text(modelingLang["automaticfill.configure.error6"]);
                $('#timeoutMinute').css("border", "1px solid #F25643");
                return modelingLang["automaticfill.timeout.cannot.0"];
            }
            cfg.timeoutStamp = cfg.timeoutDay*60*60*24 + cfg.timeoutHour*60*60+ cfg.timeoutMinute*60;
            if(!cfg.actionType){
                return modelingLang["automaticfill.action.type.cannot.empty"];
            }
            //动作类型为催办填报人
            if(cfg.actionType == 0){
                var actionJson = cfg.actionJson;
                if(!actionJson.reminderNotice){
                    $('.reminder_notice_item .error').css("display","block");
                    $(this).css("border", "1px solid #F25643");
                    return modelingLang["automaticfill.remindernotification.cannot.null"];
                }
                if(1 == actionJson.isRepeatReminder){
                    if(!actionJson.repeatCycleCount){
                        return modelingLang["automaticfill.repetition.period.cannot.empty"];
                    }
                    if(!isNum(actionJson.repeatCycleCount)){
                        return  modelingLang["automaticfill.repetition.period.must.integer"];
                    }
                    if( actionJson.repeatCycleCount <= 0){
                        return modelingLang["automaticfill.repetition.period.cannot.less.0"];
                    }
                    if(!actionJson.timeoutRepeatCount){
                        return modelingLang["automaticfill.repetition.number.cannot.empty"];
                    }
                    if(!isNum(actionJson.timeoutRepeatCount)){
                        return  modelingLang["automaticfill.repetition.number.must.integer"];
                    }
                    if( actionJson.timeoutRepeatCount <= 0){
                        return modelingLang["automaticfill.repetition.number.cannot.less.0"];
                    }
                }
            }
        }

    };
    exports.TimeoutStrategy = TimeoutStrategy;
});