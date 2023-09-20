/**
 列表视图基础设置
 **/
define(function (require, exports, module) {
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var collectionDataFilter =   require('sys/modeling/base/resources/js/views/collection/collectionDataFilter');

    var ListViewEditCommon = base.Container.extend({
        initProps : function($super, cfg) {
            $super(cfg);
            this.contentContainer = cfg.contentContainer;
            this.contentContent = cfg.contentContent;
            this.collectionViewType = cfg.collectionViewType;
            this.modeligWhereBlock = cfg.modeligWhereBlock;
            this.fdType = cfg.fdType;
            this.fdConfig = cfg.fdConfig;
            this.config = {};
            this.whereTemp = cfg.whereContentTemp.prop("outerHTML");
            this.valueName = "fd_"+ parseInt(((new Date().getTime() + Math.random()) * 10000)).toString(16);
            var typeFlag = ".modeling-pam-content-"+this.collectionViewType+"-right";
            this.draw(this.contentContainer.find(typeFlag).find(".model-edit-view-content-common"));
            this.initByStoreData(this.fdType,this.fdConfig);
        },
        draw:function(contentContainer){
            var self = this;
            this.contentContent.css("display","block");
            contentContainer.append(this.contentContent);
            this.buildEvent(this.contentContent);
        },
        buildEvent: function(contentContent){
            //事件绑定——视图类型
            this.buildEvent4ViewType();
            //事件绑定——可访问者
            this.buildEvent4Reader();
            //查询条件
            this.drawWhereSetting(this.contentContent.find(".common-data-filter"),this.fdConfig);
        },
        buildEvent4Reader:function(){
            var self = this;
            $(document).on("change","[name='auth-filter']",function(){
                if($(this).val() === "0"){
                    $(this).siblings().removeAttr("checked");
                    $(this).prop("checked",true);
                    self.contentContent.find(".common-auth-type-unify").css("display","block");
                    self.contentContent.find(".common-auth-type-alone").css("display","none");
                    //初始化时都是有值的，切换后清空地址本
                    self.contentContent.find(".common-auth-type-alone").find('.mf_container ol li').remove();
                    self.contentContent.find(".common-auth-type-alone").find('input[name=pcAuthReaderIds]').val("");
                    self.contentContent.find(".common-auth-type-alone").find('input[name=mobileAuthReaderIds]').val("");
                    Address_QuickSelection("pcAuthReaderIds","pcAuthReaderNames",";",ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,true,null,null,null,"","false");
                    Address_QuickSelection("mobileAuthReaderIds","mobileAuthReaderNames",";",ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,true,null,null,null,"","false");
                }else{
                    $(this).siblings().removeAttr("checked");
                    $(this).prop("checked",true);
                    self.contentContent.find(".common-auth-type-unify").css("display","none");
                    self.contentContent.find(".common-auth-type-alone").css("display","block");
                    //初始化时都是有值的，切换后清空地址本
                    self.contentContent.find(".common-auth-type-unify").find('.mf_container ol li').remove();
                    self.contentContent.find(".common-auth-type-unify").find('input[name=authReaderIds]').val("");
                    Address_QuickSelection("authReaderIds","authReaderNames",";",ORG_TYPE_ALL|ORG_FLAG_BUSINESSYES,true,null,null,null,"","false");
                }
            })
            //初始化
            var authSetting = self.contentContent.find("[name='auth-filter']:checked").val() || "0";
            if(self.fdConfig){
                authSetting = $.parseJSON(self.fdConfig).fdCommon.authSettingType;
            }
            $("input[name='auth-filter'][value='"+authSetting+"']").trigger("change");
        },
        buildEvent4ViewType:function(){
            var self = this;
            this.contentContent.find(".model-edit-view-content-common-type").find(".type-content").on("click",function () {
                self.contentContent.find(".type-selectBoxs").css("display","block");
                self.contentContent.find(".type-selectBoxs ul").find("li").each(function () {
                    if($(this).text() === self.contentContent.find(".type-container-text").text()){
                        $(this).addClass("active");
                    }
                })
            });
            $(document).on("mouseenter", ".type-selectBoxs li",function() {
                $(".type-selectBoxs li").removeClass("active");
                $(this).addClass("active");
            })
            $(document).on("mouseleave", ".type-selectBoxs li",function() {
                $(this).removeClass("active");
            })
            $(document).on("click", ".type-selectBoxs li",function() {
                $(".type-container-text").text($(this).text());
                $("[name='fdType']").val($(this).val());
                $(".type-selectBoxs").css("display","none");
            })
            if(!this.fdType){
                this.contentContent.find(".type-selectBoxs li").eq(0).trigger("click");
            }
        },
        drawWhereSetting:function(whereContent,fdConfig){
            var fdWhereBlock = {};
            if(fdConfig){
                fdWhereBlock = $.parseJSON(fdConfig).fdCommon.fdWhereBlock;
            }
            var collectionDataFilter_cfg = {
                whereContent: whereContent,
                whereTemp:this.whereTemp,
                fdWhereBlock:fdWhereBlock,
                modeligWhereBlock:this.modeligWhereBlock
            };
            this.collectionDataFilterInc = new collectionDataFilter.CollectionDataFilter(collectionDataFilter_cfg);
            this.collectionDataFilterInc.startup();
        },
        delWhereTr:function($dom){
            this.collectionDataFilterInc.delTr($dom);
        },
        moveWgt:function($dom,type, curIndex, targetIndex){
            this.collectionDataFilterInc.moveWgt($dom,type, curIndex, targetIndex);
        },
        getKeyData : function() {
            var self = this;
            var fdAuthEnabled = this.contentContainer.find("[name='fdAuthEnabled']").val();
            var authSettingType = this.contentContainer.find("[name='auth-filter']:checked").val();
            this.config["authSettingType"] = authSettingType;
            this.config["fdAuthEnabled"] = fdAuthEnabled;
            this.config["fdWhereBlock"] = this.collectionDataFilterInc.getKeyData();
            return this.config;
        },
        initByStoreData : function(fdType,fdConfig) {
            if(fdType){
                this.contentContainer.find(".type-selectBoxs li").each(function () {
                    if($(this).val() == fdType){
                        $(this).trigger("click");
                    }
                });
            }
            if(fdConfig){
                fdConfig = $.parseJSON(fdConfig);
                var auth = fdConfig.fdCommon.fdAuthEnabled;
                var value = this.contentContent.find("[name='fdAuthEnabled']").val();
                if(auth != value){
                    this.contentContent.find(".common-auth-value").find("input[type='checkbox']").trigger("click");
                }
            }
        },
        startup : function ($super, cfg) {
            $super(cfg);
        }

    });
    exports.ListViewEditCommon = ListViewEditCommon;
})