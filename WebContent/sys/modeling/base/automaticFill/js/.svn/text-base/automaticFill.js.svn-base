define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var env = require('lui/util/env');
    var modelingLang = require("lang!sys-modeling-base");
    var AutomaticFill= base.Container.extend({

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
            var height =  $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 50;
            $("body", parent.document).find('#cfg_iframe').height(height);

            $('#_xform_fdNotifyType label').css('margin-right','30px');
            $('#_xform_fdNotifyType label input').css('margin-right','6px');

            $('#endTypeContent label').css('margin-right','30px');
            $('#endTypeContent label input').css('margin-right','6px');

            $('.model_automatic_td_content label').css('margin-right','30px');
            $('.model_automatic_td_content label input').css('margin-right','6px');

            $(' div.mf_container ol.mf_list li.mf_item').css('color','#333');

            $('.new_strategy_mask').css('display','none');

            $('.lui-mulit-zh-cn-html').css('overflow-x','hidden');

            $('.close_pop').on('click',  function () {
                $('.new_strategy_mask').css('display','none');
            });

        },
        //提交到后台的数据
        getKeyData: function () {
            var formData = {};
            //名称
            var fdName = $("[name='fdName']").val();
            formData.fdName = fdName;
            //启用状态
            var fdIsEnable = $("[name='fdIsEnable']").val();
            formData.fdIsEnable = fdIsEnable;
            //填报人
            var fdFillerNames = $("[name='fdFillerNames']").val();
            formData.fdFillerNames = fdFillerNames;
            var fdFlowId = $("[name='fdFlowId']").val();
            formData.fdFlowId = fdFlowId;
            //定时设置
            var fdTimeCfg = {};
            //cron
            var fdCron = $("[name='fdCron']").val();
            fdTimeCfg.cron = fdCron;
            //startTime
            var startTime = $("[name='startTime']").val();
            fdTimeCfg.startTime = startTime;
            //endType
            var endType = $("input[name='endType']:checked").val();
            fdTimeCfg.endType = endType;
            if(1 == endType){
                //endTime
                fdTimeCfg.endTime = $("[name='endTime']").val() ;
            }else if(2 == endType){
                //repeatCount
                fdTimeCfg.repeatCount =  $("[name='repeatCount']").val() ;
            }
            var fdCronTimes  = $("select[name='fdCronTimes']").val();
            if('once' == fdCronTimes){
                fdTimeCfg.endType = -1;
                delete fdTimeCfg.startTime;
                delete fdTimeCfg.endTime;
                delete fdTimeCfg.repeatCount;
            }
            //nonWorkingDayWay
            var nonWorkingDayWay = $("input[name='nonWorkingDayWay']:checked").val();
            fdTimeCfg.nonWorkingDayWay = nonWorkingDayWay ;

            $("[name='fdTimeCfg']").val(JSON.stringify(fdTimeCfg));
            formData.fdTimeCfg = fdTimeCfg;

            return formData;
        },
        //后台数据渲染方法
        initByStoreData: function (sd) {
            if (sd){
                //初始化名称
                if(sd.fdName){
                    $("[name='fdName']").val(sd.fdName);
                }
                //初始化启用状态
                // $("[name='fdIsEnable']").val(1);
                // if(sd.fdIsEnable){
                //     $("[name='fdIsEnable']").val(sd.fdIsEnable);
                // }
                //初始化消息提醒
                var method = Com_GetUrlParameter(location.href, 'method');
                if(!sd.fdNotifyType && method == 'add'){
                    $("[name='_fdNotifyType']").eq(0).attr("checked",true);
                    $("[name='fdNotifyType']").val("todo")
                }
                //初始跳过填报人
                if(sd.fdIsJumpFiller){
                }
                //初始化流程
                if(''!=sd.fdFlowId){
                    //赋值
                    $("[name='fdFlowId']").val(sd.fdFlowId);
                    $("[name='fdFlowName']").val(sd.fdFlowName);
                }

                $(':radio[name="endType"]').eq(0).attr("checked",true);
                $(':radio[name="nonWorkingDayWay"]').eq(0).attr("checked",true);
                //初始化时间设置
                if(sd.fdTimeCfg){
                    var fdTimeCfg = JSON.parse(sd.fdTimeCfg);
                    //开始时间
                    if(fdTimeCfg.startTime){
                        //赋值
                        $("[name='startTime']").val(fdTimeCfg.startTime);
                    }
                    //endType
                    if(fdTimeCfg.endType){
                        //赋值
                        $(':radio[name="endType"]').eq(fdTimeCfg.endType-1).attr("checked",true);
                        if(-1 == fdTimeCfg.endType){
                            $('#endTypeContent').css("display","none");
                            $('#endTypeTitle').css("display","none");
                            $('#endType1Setting').css("display","none");
                            $('#endType2Setting').css("display","none");
                        }else if(1 == fdTimeCfg.endType){
                            $("[id='endType1Setting']").css("display","table-row");
                            $("[id='endType2Setting']").css("display","none");
                            //endTime
                            $("[name='endTime']").val(fdTimeCfg.endTime);
                        }else if(2 == fdTimeCfg.endType){
                            $("[id='endType1Setting']").css("display","none");
                            $("[id='endType2Setting']").css("display","table-row");
                            //repeatCount
                            $("[name='repeatCount']").val(fdTimeCfg.repeatCount);
                        }else if(3 == fdTimeCfg.endType){
                            $("[id='endType1Setting']").css("display","none");
                            $("[id='endType2Setting']").css("display","none");
                        }
                    }
                    //nonWorkingDayWay
                    if(fdTimeCfg.nonWorkingDayWay){
                        //赋值
                        $(':radio[name="nonWorkingDayWay"]').eq(fdTimeCfg.nonWorkingDayWay-1).attr("checked",true);
                    }
                }

                //生成超时策略dom结构
                if(sd.autoTimeoutStrategyList && '[]' != sd.autoTimeoutStrategyList){
                    //初始化超时策略数据
                    createHasTimeoutRecordDom(sd.autoTimeoutStrategyList);
                }else{
                    createNoTimeoutRecordDom();
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
        input.val(Number(inputVal) + 1);
        var err = $(e).parent().siblings(".error");
        if (Number(input.val()) < 0) {
            err.css("display", "block");
            input.css("border", "1px solid #F25643");
        } else {
            err.css("display", "none");
            input.css("border", "1px solid #dddddd");
        }
    };

    window.cutTime = function(e) {
        var input = $(e).parent().siblings("input");
        var inputVal = input.val();
        input.val(Number(inputVal) - 1);
        var err = $(e).parent().siblings(".error");
        if (Number(input.val()) < 0) {
            err.css("display", "block");
            input.css("border", "1px solid #F25643");
        } else {
            err.css("display", "none");
            input.css("border", "1px solid #dddddd");
        }
    };

    window.appTimeoutRecordDom = function(data) {
        var table =  $('.model_automatic_table');
        //先构建标头
        if("none" != $('.noTimeoutRecordTr').css("display") ){
            $('.noTimeoutRecordTr').css("display","none");
            var htmlTitle = '<tr class="hasTimeoutRecordTr">' +
                                '<td class="model_automatic_tr_tag"><span></span>'+modelingLang['modeling.timeout.etting']+'</td>' +
                                '<td class="model_automatic_td_content">' +
                                  '<p class="model_automatic_tr_new_strategy">'+modelingLang['modeling.button.new']+'</p>' +
                                '</td>' +
                             '</tr>';
            table.append(htmlTitle);
            $('.model_automatic_tr_new_strategy').on('click',  function () {
                var cfg = {
                    isEdit:false
                }
                openTimeoutStrategyIframe(cfg);
            });
        }

        var fdActionTypeText = modelingLang['enums.action_type.0'];
        if(data.actionType && '1' == data.actionType){
            fdActionTypeText = modelingLang['enums.action_type.1'];
        }

        var fdActionJson = data.actionJson.replace(/\"/g,"'");
        var strategyIndex =  data.strategyIndex;

        var html = '<tr automatic_strategy_index='+strategyIndex+'>' +
                    '<td class="model_automatic_td_title"></td>' +
                    '<td class="model_automatic_strategy">' +
                        '<div class="model_automatic_strategy_box">' +
                            '<div class="model_automatic_strategy_title">' +
                                 '<div class="title">'+data.timeoutStrategyName+'</div>' +
                                        '<div class="operate">' +
                                             '<i class="operate_edit" onclick="editTimoutStrategy(\''+strategyIndex+'\',\''+data.fdId+'\',\''+data.timeoutStrategyName+'\',\''
                                        +data.timeoutStamp+'\',\''+data.actionType+'\','+fdActionJson+')"></i>' +
                                             '<i class="operate_del" onclick="delTimoutStrategy(\''+strategyIndex+'\')"></i>' +
                                        '</div>' +
                                 '</div>' +
                                 '<div class="model_automatic_strategy_content">' +
                                         '<div class="time">' +
                                            '<p>'+modelingLang['modeling.overtime.time']+'</p>' +
                                             '<ul>' +
                                                '<li>'+data.timeoutDay+modelingLang['automaticfill.day']+'</li>' +
                                                '<li>'+data.timeoutHour+modelingLang['automaticfill.hour']+'</li>' +
                                                '<li>'+data.timeoutMinute+modelingLang['automaticfill.minute']+'</li>' +
                                             '</ul>' +
                                        '</div>' +
                                         '<div class="action">' +
                                            '<p>'+modelingLang['automaticfill.execution.actions']+'</p>' +
                                            '<span>' +
                                               fdActionTypeText +
                                            '</span>' +
                                        '</div>' +
                                '</div>' +
                            '</div>' +
                        '</td>' +
                    '</tr>';
        table.append(html);
    };

    window.editTimeoutRecordDom = function(data) {
        var modelAutomaticStrategy =  $("[automatic_strategy_index='"+data.strategyIndex+"']");
        modelAutomaticStrategy.empty()

        var fdActionJson = data.actionJson.replace(/\"/g,"'");
        var fdActionTypeText = modelingLang['enums.action_type.0'];
        if(data.actionType && '1' == data.actionType){
            fdActionTypeText =  modelingLang['enums.action_type.1'];
        }
        var html='<td class="model_automatic_td_title"></td>' +
                 '<td class="model_automatic_strategy">' +
                    '<div class="model_automatic_strategy_box">' +
                        '<div class="model_automatic_strategy_title">' +
                            '<div class="title">'+data.timeoutStrategyName+'</div>' +
                            '<div class="operate">' +
                                '<i class="operate_edit" onclick="editTimoutStrategy(\''+data.strategyIndex+'\',\''+data.fdId+'\',\''+data.timeoutStrategyName+'\',\''
                                +data.timeoutStamp+'\',\''+data.actionType+'\','+fdActionJson+')"></i>' +
                                '<i class="operate_del" onclick="delTimoutStrategy(\''+data.strategyIndex+'\')"></i>' +
                            '</div>' +
                        '</div>' +
                        '<div class="model_automatic_strategy_content">' +
                            '<div class="time">' +
                                    '<p>'+modelingLang['modeling.overtime.time']+'</p>' +
                                    '<ul>' +
                                    '<li>'+data.timeoutDay+modelingLang['automaticfill.day']+'</li>' +
                                    '<li>'+data.timeoutHour+modelingLang['automaticfill.hour']+'</li>' +
                                    '<li>'+data.timeoutMinute+modelingLang['automaticfill.minute']+'</li>' +
                                '</ul>' +
                            '</div>' +
                            '<div class="action">' +
                                '<p>'+modelingLang['automaticfill.execution.actions']+'</p>' +
                                '<span>' +
                                    fdActionTypeText +
                                '</span>' +
                            '</div>' +
                        '</div>' +
                    '</div>' +
            '</td>';
        modelAutomaticStrategy.append(html);
    };

    window.createNoTimeoutRecordDom = function (){
        var html = '<tr class="noTimeoutRecordTr">' +
                        '<td class="model_automatic_tr_tag"><span></span>'+modelingLang['modeling.timeout.etting']+'</td>' +
                   '</tr>' +
                   '<tr class="noTimeoutRecordTr">' +
                        '<td></td>' +
                        '<td class="model_automatic_td_content">' +
                             '<div class="model_automatic_empty">' +
                                 '<img src="automaticFill/images/empty@2x.png" alt="">' +
                                 '<p>'+modelingLang['automaticfill.no.timeoout.creatnew']+'</p>' +
                                 '<div class="model_automatic_div_new_strategy">'+modelingLang['modeling.button.new']+'</p>' +
                                 '</div>' +
                            '</div>' +
                        '</td>' +
                  '</tr>';
        $('.model_automatic_table').append(html);
        $('.model_automatic_div_new_strategy').on('click',  function () {
            var cfg = {
                isEdit:false
            }
            openTimeoutStrategyIframe(cfg);
        });
    };

    window.createHasTimeoutRecordDom = function (autoTimeoutStrategyList){
        createNoTimeoutRecordDom();
        var json =  JSON.parse(autoTimeoutStrategyList);
        for(var i=0;i<json.length;i++){
            json[i].strategyIndex = i;
            json[i].timeoutDay = parseInt(json[i].timeoutStamp/(60*60*24));
            json[i].timeoutHour = parseInt(json[i].timeoutStamp%(60*60*24)/(60*60));
            json[i].timeoutMinute = parseInt(json[i].timeoutStamp%(60*60)/(60));
            appTimeoutRecordDom(json[i]);
        }
        $("[name='autoTimeoutStrategyList']").val(JSON.stringify(json));
    };

    window.AutomaticFillValidate = {
        validate: function (cfg) {
            if ( !cfg) {
                return modelingLang["modeling.configure.first"];
            }
            if (!cfg.fdName) {
                return modelingLang["modeling.name.required"];
            }
            if (!cfg.fdFillerNames) {
                return modelingLang["automaticfill.configure.reporter"];
            }
            if (!cfg.fdFlowId) {
                return modelingLang["automaticfill.configure.filling.process"];
            }
            if (!cfg.fdTimeCfg) {
                return modelingLang["automaticfill.configure.operating.frequency.first"];
            }
            var fdTimeCfg = cfg.fdTimeCfg;
            if (!fdTimeCfg.cron) {
                return modelingLang["automaticfill.configure.operating.frequency"];
            }
            if (!fdTimeCfg.endType) {
                return modelingLang["automaticfill.configure.end.situation"];
            }
            if (-1 != fdTimeCfg.endType&&!fdTimeCfg.startTime) {
                return modelingLang["automaticfill.configure.startTime"];
            }

            if (1 == fdTimeCfg.endType ) {
                if (!fdTimeCfg.endTime ) {
                    return modelingLang["automaticfill.configure.endTime"];
                }
                var start = new Date(fdTimeCfg.startTime).valueOf();
                var end = new Date(fdTimeCfg.endTime).valueOf();
                if(start>=end){
                    return modelingLang["automaticfill.configure.error0"];
                }

            }
            if (2 == fdTimeCfg.endType) {
                if( !fdTimeCfg.repeatCount ){
                    return modelingLang["automaticfill.configure.repeat.times"];
                }
                var ex = /^\d+$/;
                if (!ex.test(fdTimeCfg.repeatCount)) {
                    return modelingLang["automaticfill.configure.error1"];
                }
                if ( 0>fdTimeCfg.repeatCount ) {
                    return modelingLang["automaticfill.configure.error2"];
                }
            }

        }

    };
    exports.AutomaticFill = AutomaticFill;
});