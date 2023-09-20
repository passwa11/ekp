/**
 * 左侧栏
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var modelingLang = require("lang!sys-modeling-base");
    var MaintenanceS1 = base.DataView.extend({

        startup: function ($super, cfg) {
            $super(cfg);
        },

        // 渲染完毕之后添加事件
        doRender: function ($super, html) {
            $super(html);
            var self = this;
            //--------------------------------------
            //展开配置项
            $('.form-item-title span').on('click', function () {
                if ($(this).hasClass('develop')) {
                    $(this).removeClass('develop');
                    $(this).parent().siblings('.form-item-list').css('display', 'none');
                } else {
                    $(this).addClass('develop');
                    $(this).parent().siblings('.form-item-list').css('display', 'block');
                }
            });
            //选择应用
            $('.form-item-title i').on('click', function () {
                if ($(this).hasClass('formSelected')) {
                    $(this).removeClass('formSelected');
                    $(this).parent().parent().find('.form-item-list i').removeClass('itemSelected');

                } else {
                    $(this).addClass('formSelected');
                    $(this).parent().parent().find('.form-item-list i').addClass('itemSelected');
                }

                self.checkAll();
            })
            //选择表单
            $('.form-item-list li').on('click', function () {
                var $i = $(this).find("i");
                if ($i.hasClass('itemSelected')) {
                    $i.removeClass('itemSelected');
                } else {
                    $i.addClass('itemSelected');
                }
                //判断配置项是否全选
                var checkItem = $('.form-item-list i');
                var itemCount = 0;
                for (var j = 0; j < checkItem.length; j++) {
                    if (!($(checkItem[j]).hasClass('itemSelected'))) {
                        $(this).parent().parent().find('.form-item-title i').removeClass('formSelected');
                    } else {
                        itemCount++;
                    }
                }
                ;
                if (itemCount == checkItem.length) {
                    $(this).parent().parent().find('.form-item-title i').addClass('formSelected');
                }

                self.checkAll();
            })
            // 全选
            $('.selectAll').on('click', function () {
                var $i = $(this).find("i");
                if ($i.hasClass('allSelected')) {
                    $i.removeClass('allSelected');
                    $('.form-item-title i').removeClass('formSelected');
                    $('.form-item-list i').removeClass('itemSelected')
                } else {
                    $i.addClass('allSelected');
                    $('.form-item-title i').addClass('formSelected');
                    $('.form-item-list i').addClass('itemSelected')
                }
            })
            //开始检测
            $("#doCheck").on('click', function () {
                var selected = {
                    isAll: $('.selectAll i').hasClass('allSelected'),
                };
                //应用
                var apps = $('.form-item-title i');
                selected.appIds = [];
                for (var i = 0; i < apps.length; i++) {
                    if (($(apps[i]).hasClass('formSelected'))) {
                        selected.appIds.push($(apps[i]).attr("ms1-id"))
                    }
                }
                ;
                //表单
                selected.forms = [];
                //判断配置项是否全选
                var title;
                var forms = $('.form-item-list i');
                for (var j = 0; j < forms.length; j++) {
                    if (($(forms[j]).hasClass('itemSelected'))) {
                        if (!title) {
                            title = $(forms[j]).attr("ms1-name");
                        }
                        selected.forms.push($(forms[j]).attr("ms1-id"))
                    }
                }
                ;
                if (title) {
                    title += "等(" + selected.forms.length + ")项";
                }
                self.doCheck(selected, title)

            })
        },
        //判断表单是否全选
        checkAll: function () {
            var checkForm = $('.form-item-title i');
            var formCount = 0;
            for (var i = 0; i < checkForm.length; i++) {
                if (!($(checkForm[i]).hasClass('formSelected'))) {
                    $('.selectAll i').removeClass('allSelected');
                } else {
                    formCount++;
                }
            }
            if (formCount == checkForm.length) {
                $('.selectAll i').addClass('allSelected');
            }
        },
        doCheck: function (data, title) {
            console.debug("doCheck::", data);
        },
        reRender: function () {
            this.source.get();
        },
        getModelingLang :function (){
            return modelingLang;
        }


    });

    exports.MaintenanceS1 = MaintenanceS1;
});