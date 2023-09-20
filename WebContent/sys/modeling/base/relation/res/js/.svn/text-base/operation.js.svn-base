/**
 * 页面全局对象
 */
define(function (require, exports, module) {
    //系统组件
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require('lui/dialog');
    //公式定义器
    var formulaBuilder = require("sys/modeling/base/relation/trigger/behavior/js/formulaBuilder");
    //自定义参数
    var operationTrigger = require("sys/modeling/base/relation/res/js/operationTrigger");
    var operationView = require("sys/modeling/base/relation/res/js/operationView");
    var operationGenerator = require("sys/modeling/base/relation/res/js/operationGenerator");
    var operationPayuser = require("sys/modeling/base/relation/res/js/operationPayuser");
    var operationPaymoney=require("sys/modeling/base/relation/res/js/operationPaymoney");
    var operationPayMerchants=require("sys/modeling/base/relation/res/js/operationPayMerchant");

    var Operation = base.Container.extend({
        /*
            cfg ：
                nameLabel : name,text,
                xformId,
        */
        initProps: function ($super, cfg) {
            $super(cfg);
            //初始化
            formulaBuilder.initFieldList(cfg.xformId);
            var self = this;
            this.element = cfg.container;
            var viewCfg = {
                container: this.element.find("[mdlng-prtn-property=\"fdView\"]"),
                pcfg: cfg
            };
            var triggerCfg = {
                container: this.element.find("[mdlng-prtn-property=\"fdTrigger\"]"),
                pcfg: cfg
            };
            this.triggerEle = new operationTrigger.OperationTrigger(triggerCfg);
            this.formFdType = cfg.formFdType;
            if (cfg.formFdType === "0") {

                this.viewEle = null;
                this.element.find("[mdlng-prtn-property=\"fdView\"]").hide();
            } else {
                this.viewEle = new operationView.OperationView(viewCfg);
            }
            this.typeContainer = this.element.find("[mdlng-prtn-property=\"fdType\"]");
            //收款商户
            var fdPayeeMerchantEle={
                fieldName: "fdPayeeMerchant",
                pcfg: this.config
            };
            this.fdPayeeMerchantEle = new operationPayMerchants.OperationPayMerchant(fdPayeeMerchantEle);
            //支付用户
            var fdPayUserEle ={
                fieldName: "fdPayUser",
                pcfg: this.config
            };
            this.fdPayUserEle = new operationPayuser.OperationPayuser(fdPayUserEle);

            //支付金额
            var fdPayMoneyEle ={
                fieldName: "fdPayMoney",
                pcfg: this.config
            };
            this.fdPayMoneyEle = new operationPaymoney.OperationPaymoney(fdPayMoneyEle);
            //返回值
            var fdOutParamEle ={
                fieldName: "fdOutParam",
                pcfg: this.config
            };
            this.fdOutParamEle = new operationGenerator.OperationGenerator(fdOutParamEle);
            this.bindEvent();
            this.initByStoreData();
            //end-----
        },
        fdTypeChange: function (v, n) {
            if (!this.viewEle) {
                return;
            }
            if (v === "1") {
                this.viewEle.show();
            } else {
                this.viewEle.hide();
            }
        },
        showOrHideAddType:function (){
            this.viewEle.showOrHideAddType();
        },
        bindEvent: function () {
            //通用事件绑定
            var self = this;
            this.element.on("click", function () {
                var isActive =  self.element.find(".model-mask-panel-table-select").hasClass("active");
                if (isActive) {
                    $('.model-mask-panel').height($('.model-mask-panel')[0].scrollHeight - 50);
                }
                self.element.find(".model-mask-panel-table-select").removeClass("active");
            });
            this.element.find(".model-mask-panel-table-select").on("click", function () {
                event.stopPropagation();
                var isActive =  $(this).hasClass("active");
                self.element.find(".model-mask-panel-table-select").removeClass("active");
                if (!isActive) {
                    $('.model-mask-panel').height($('.model-mask-panel')[0].scrollHeight + 50);
                    $(this).addClass("active")
                }else{
                    $('.model-mask-panel').height($('.model-mask-panel')[0].scrollHeight - 50);
                }

            });
            this.element.find(".model-mask-panel-table-option div").on("click", function () {
                var $select = $(this).parent().parent();
                var $p = $select.find(".model-mask-panel-table-select-val");
                var val = $(this).attr("option-value");
                $p.attr("table-select-val", val);
                $p.html($(this).html());
            });
        },
        startup: function ($super, cfg) {
            $super(cfg);
        },
        //获取传到后台去的参数方法
        getKeyData: function () {
            //入参设置
            this.triggerEle.getKeyData();
            var triggerInc = this.triggerEle.getTriggerInc();
            var fdInParamJson = {};
            fdInParamJson.trigger = triggerInc;
            if (this.viewEle) {
                this.viewEle.getKeyData();
                fdInParamJson = this.viewEle.getInc(fdInParamJson);
            }
            var fdInParamStr = JSON.stringify(fdInParamJson);
            this.element.find("[name='fdInParam']").val(fdInParamStr);

            var fdPayeeMerchantData = this.fdPayeeMerchantEle.getKeyData();
            var fdpmer = JSON.stringify(fdPayeeMerchantData);
            this.element.find("[mdlng-rltn-data=\"fdPayeeMerchant\"]").val(fdpmer);

            var fdPayUserData = this.fdPayUserEle.getKeyData();
            var fdpu = JSON.stringify(fdPayUserData);
            this.element.find("[mdlng-rltn-data=\"fdPayUser\"]").val(fdpu);

            var fdPayMoneyData = this.fdPayMoneyEle.getKeyData();
            var fdpm = JSON.stringify(fdPayMoneyData);
            this.element.find("[mdlng-rltn-data=\"fdPayMoney\"]").val(fdpm);

            var fdOutParamData = this.fdOutParamEle.getKeyData();
            var fopd = JSON.stringify(fdOutParamData);
            this.element.find("[mdlng-rltn-data=\"fdOutParam\"]").val(fopd);

        }, initByStoreData: function () {
            var self = this;
            self.triggerEle.initByStoreData();
            if (this.viewEle) {
                self.fdTypeChange(self.formFdType);
                if(self.formFdType === "1"){
                    self.viewEle.initByStoreData();
                }
            } else {
                var fdType = self.typeContainer.find("[name=\"fdType\"]:checked").val();
                self.fdTypeChange(fdType);
            }
            //收款商户设置
            var fdPayeeMerchant=self.element.find("[mdlng-rltn-data=\"fdPayeeMerchant\"]").val();
            if (fdPayeeMerchant && fdPayeeMerchant.length > 0) {
                var value = JSON.parse(fdPayeeMerchant);
                this.fdPayeeMerchantEle.initByStoreData(value);
            }
            //支付用户设置
            var fdPayuser=self.element.find("[mdlng-rltn-data=\"fdPayUser\"]").val();
            if (fdPayuser && fdPayuser.length > 0) {
                var value = JSON.parse(fdPayuser);
                this.fdPayUserEle.initByStoreData(value);
            }
            //支付金额设置
            var fdPayMoney=self.element.find("[mdlng-rltn-data=\"fdPayMoney\"]").val();
            if (fdPayMoney && fdPayMoney.length > 0) {
                var value = JSON.parse(fdPayMoney);
                this.fdPayMoneyEle.initByStoreData(value);
            }
            //返回值设置
            var $ele = this.element;
            var vs = $ele.find("[mdlng-rltn-data=\"fdOutParam\"]").val();
            if (vs && vs.length > 0) {
                var value = JSON.parse(vs);
                this.fdOutParamEle.initByStoreData(value);
            }
        },

    });

    exports.Operation = Operation;
});
