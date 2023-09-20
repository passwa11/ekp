define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var env = require('lui/util/env');
    var dialog = require('lui/dialog');
    var lang = require('lang!sys-ui');
    var modelingLang = require("lang!sys-modeling-base");
    var ExternalShare= base.Container.extend({

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
            $("[id='cfg_iframe']", parent.document).css("height","670px");
            //复制链接
            $(".copy-icon").on('click', function () {
                if (options.token) {
                    var linkUrl = env.fn.formatUrl("/sys/anonymous/enter/token.do?method=visitToken&token=" + options.token, true);
                    var flag = copyText($(this), linkUrl); //传递文本
                    dialog.alert(flag ? modelingLang['modeling.baseinfo.CopySuccess'] : modelingLang['modeling.baseinfo.CopyNotSuccess']);
                }
            });
            //悬浮提示
            $(".copy-icon").hover(function () {
                popTips(this, modelingLang['modelingExternalShare.icon.copy']);
            });
            $(".copy-icon").mouseout(function () {
                $(".externalShareIconPop").css("display","none");
            });

            //跳转链接
            $(".jump-icon").on('click', function () {
                if (options.token) {
                    Com_OpenWindow($(".link-text").text(), "_blank");
                }
            });
            $(".jump-icon").hover(function () {
                popTips(this, modelingLang['modelingExternalShare.icon.open.link']);
            });
            $(".jump-icon").mouseout(function () {
                $(".externalShareIconPop").css("display","none");
            });

            //二维码图标
            $(".qrcode-icon").on('click', function (e) {
                if (!options.token) {
                    return;
                }
                //停止冒泡触发事件
                e.stopPropagation();

                //二维码div位置
                var dom = $("#qrcodeDiv .detailQrCode")[0];

                //如果dom没有子Element==0，说明没生成二维码,则重新生成二维码
                if (dom.childElementCount == 0) {
                    //二维码标签DIV
                    var canvarsDiv = document.createElement("div");
                    canvarsDiv.setAttribute("id", "canvarsId");
                    $(dom).append(canvarsDiv);

                    //生成二维码
                    seajs.use(['lui/qrcode'], function (qrcode) {
                        var url = env.fn.formatUrl("/sys/anonymous/enter/token.do?method=visitToken&token=" + options.token, true);
                        qrcode.Qrcode({
                            text: url,
                            element: canvarsDiv,
                            render: 'canvas'
                        });
                    });

                    //下载按钮DIV
                    var downLoadDom = document.createElement("div");
                    downLoadDom.setAttribute("id", "downloadQrCodeId");
                    $(downLoadDom).css("display", "block");
                    $(downLoadDom).css("text-align", "center");
                    $(downLoadDom).css("color", "#666666");
                    $(downLoadDom).css("cursor", "pointer");

                    //图标
                    var downLoadIcon = document.createElement("i");
                    $(downLoadIcon).attr('class', 'download-icon');

                    //放置在dom元素中
                    $(dom).append(downLoadDom);
                    $(downLoadDom).append(downLoadIcon);
                    $(downLoadDom).append(lang['ui.dialog.downlaod2Dbarcodes']);

                    //绑定点击事件
                    $(downLoadDom).on("click", $.proxy(qrcodeSave, self));
                }
                //显示二维码框div
                $("#qrcodeDiv")[0].style.display = 'block';
            });
            //悬浮提示
            $(".qrcode-icon").hover(function () {
                popTips(this, lang['ui.dialog.downlaod2Dbarcodes']);
            });
            $(".qrcode-icon").mouseout(function () {
                $(".externalShareIconPop").css("display","none");
            });

        },

        //提交到后台的数据
        getKeyData: function () {
            var keyData = {};
            var formConfig = {};
            //表单分享开关
            var isEnable = $("[name='isEnable']").val();
            formConfig.isEnable = isEnable;
            if('1' == isEnable){
                //表单结束时间
                var endTime = $("[name='endTime']").val();
                formConfig.endTime = endTime;
                //表单提交总次数
                var commitTotalCount = $("[name='commitTotalCount']").val();
                formConfig.commitTotalCount = commitTotalCount;
                //表单结束提示
                var endNotification = $("[name='endNotification']").val();
                formConfig.endNotification = endNotification;
                //单人提交限制开关
                var isCommitLimitEnable = $("[name='isCommitLimitEnable']:checked").val();
                formConfig.isCommitLimitEnable = isCommitLimitEnable;
                if('1' == isCommitLimitEnable){
                    //单人允许提交的最大次数
                    var everyOneCommitCount = $("[name='everyOneCommitCount']").val();
                    formConfig.everyOneCommitCount = everyOneCommitCount;
                }
                //是否允许执行触发开关
                var isRunBehavior = $("[name='isRunBehavior']").val();
                formConfig.isRunBehavior = isRunBehavior;

                //附件上传个数
                var fileLimitCount = $("[name='fileLimitCount']").val();
                formConfig.fileLimitCount = fileLimitCount;

                //单个附件大小限制
                var singleFileSize = $("[name='singleFileSize']").val();
                formConfig.singleFileSize = singleFileSize;
                //整体附件大小限制
                var fileMaxSize = $("[name='fileMaxSize']").val();
                formConfig.fileMaxSize = fileMaxSize;
            }

            keyData.formConfig = formConfig;
            return keyData;
        },
        //后台数据渲染方法
        initByStoreData: function (sd) {
            //初始化表单链接文本框URL内容
            updateLinkText();

            if(sd){
                var formConfig = sd.formConfig;
                if(formConfig){
                    //表单分享开关
                    if("0"==formConfig.isEnable){
                        $(".external-share-on").css("display","none");
                        $(".external-share-off").css("display","block");
                    }else{
                        $(".external-share-on").css("display","block");
                        $(".external-share-off").css("display","none");
                    }
                    //表单结束时间
                    if(formConfig.endTime){
                        $("[name='endTime']").val(formConfig.endTime);
                    }
                    //表单提交总次数
                    if(formConfig.commitTotalCount){
                        $("[name='commitTotalCount']").val(formConfig.commitTotalCount);
                    }
                    //表单结束提示
                    if(formConfig.endNotification){
                        $("[name='endNotification']").val(formConfig.endNotification);
                    }
                    //单人提交限制开关
                    var isCommitLimitEnable = formConfig.isCommitLimitEnable;
                    if("0" ==isCommitLimitEnable || typeof(isCommitLimitEnable) =="undefined"){
                        $(".everyCommitCountTr").css("display","none");
                        $("[name='isCommitLimitEnable'][value='0']").prop("checked","true");
                    }else{
                        $(".everyCommitCountTr").css("display","block");
                        $("[name='isCommitLimitEnable'][value='1']").prop("checked","true");
                        //单人允许提交的最大次数
                        if(formConfig.everyOneCommitCount){
                            $("[name='everyOneCommitCount']").val(formConfig.everyOneCommitCount);
                        }
                    }
                    //附件上传个数
                    if(formConfig.fileLimitCount){
                        $("[name='fileLimitCount']").val(formConfig.fileLimitCount);
                    }

                    //单个附件大小限制
                    if(formConfig.singleFileSize){
                        $("[name='singleFileSize']").val(formConfig.singleFileSize);
                    }

                    //整体附件大小限制
                    if(formConfig.fileMaxSize){
                        $("[name='fileMaxSize']").val(formConfig.fileMaxSize);
                    }
                }

            }
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },

    });

    window.ExternalShareValidate = {
        validate: function (cfg) {
            if (!cfg) {
                return modelingLang['modeling.configure.first'];
            }
            if(!cfg.formConfig){
                return modelingLang['modeling.configure.first'];
            }
            var formConfig = cfg.formConfig;
            if("1" == formConfig.isEnable) {
                if (!formConfig.endTime) {
                    return modelingLang['modeling.Deadline.form.must'];
                }
                if (!formConfig.commitTotalCount) {
                    return modelingLang['modeling.submit.number.error0 '];
                }
                if (/^-?\d+$/.test(formConfig.commitTotalCount)) {
                    if(formConfig.commitTotalCount.indexOf('-') >-1){
                        return modelingLang['modeling.submit.number.error1'];
                    }
                }else{
                    return modelingLang['modeling.submit.number.error2'];
                }
                //单人提交限制开关
                if("1" == formConfig.isCommitLimitEnable) {
                    if (!formConfig.everyOneCommitCount) {
                        return modelingLang['modeling.single.submit.maxnum.error0'];
                    }
                    //单人允许提交的最大次数
                    if (/^-?\d+$/.test(formConfig.everyOneCommitCount)) {
                        if(formConfig.everyOneCommitCount.indexOf('-') >-1){
                            return modelingLang['modeling.single.submit.maxnum.error1'];
                        }
                        if(formConfig.everyOneCommitCount==0){
                            return modelingLang['modeling.single.submit.maxnum.error2'];
                        }
                    }else{
                        return modelingLang['modeling.single.submit.maxnum.error3'];
                    }
                }
            }
        }
    };

    window.copyText = function ($this, text) {
        var $input = $("<input style='position: absolute;' />");//创建input对象
        $this.after($input);//添加元素
        $input.val(text);
        $input.select();
        try {
            var flag = document.execCommand("copy");//执行复制
        } catch (eo) {
            var flag = false;
        }
        $input.remove()//删除元素
        return flag;
    };

    //token变更修改文本框URL链接
    window.updateLinkText = function () {
        var linkUrl = env.fn.formatUrl("/sys/anonymous/enter/token.do?method=visitToken&token="+options.token, true);
        //url赋值
        $(".link-text").text(linkUrl);
        //是否有效token
        if (options.token) {
            //显示可使用icon图标
            $(".copy-icon").css("background-image", "");
            $(".jump-icon").css("background-image", "");
            $(".qrcode-icon").css("background-image", "");

            //默认鼠标事件反应
            $(".copy-icon").css("pointer-events","auto");
            $(".jump-icon").css("pointer-events","auto");
            $(".qrcode-icon").css("pointer-events","auto");
        } else {
            //显示不可使用icon图标
            $(".copy-icon").css("background-image", "url(./externalShare/images/copy-default.png)");
            $(".jump-icon").css("background-image", "url(./externalShare/images/jump-default.png)");
            $(".qrcode-icon").css("background-image", "url(./externalShare/images/qrcode-default.png)");

            //不对鼠标事件反应
            $(".copy-icon").css("pointer-events","none");
            $(".jump-icon").css("pointer-events","none");
            $(".qrcode-icon").css("pointer-events","none");
            //移除二维码内容，更新token之后重新生成
            $("#canvarsId").remove();
            $("#downloadQrCodeId").remove();
        }
    };

    //下载二维码方式
    window.qrcodeSave = function () {
        var dom = $("#qrcodeDiv .detailQrCode")[0];
        var canvas = $(dom).find("canvas");
        var name = lang['ui.dialog.2Dbarcodes'], type = "png";
        if (window.navigator.msSaveBlob) {
            window.navigator.msSaveBlob(canvas[0].msToBlob(), name);
        } else {
            var imageData = canvas[0].toDataURL(type);
            imageData = imageData.replace(_fixType(type),
                'image/octet-stream');
            var save_link = document.createElementNS(
                "http://www.w3.org/1999/xhtml", "a");
            save_link.href = imageData;
            save_link.download = name;
            var ev = document.createEvent("MouseEvents");
            ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0,
                false, false, false, false, 0, null);
            save_link.dispatchEvent(ev);
            ev = null;
            delete save_link;
        }
    };

    //图标悬浮显示信息
    window.popTips = function (object,context) {
        var obj = object.getBoundingClientRect();
        var x = (-6) + (8 * context.length);
        $(".externalShareIconPop").css("top",obj.top - 42);
        $(".externalShareIconPop").css("left",obj.left - x);
        $(".externalShareIconPop").text(context);
        $(".externalShareIconPop").css("display","block");
    };

    window._fixType =function (type) {
        var r = type.match(/png|jpeg|bmp|gif/)[0];
        return 'image/' + r;
    };

    exports.ExternalShare = ExternalShare;
});