/**
 * 更新视图
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var createView = require("sys/modeling/base/relation/trigger/behavior/js/view/createView");
    var creatorGenerator = require("sys/modeling/base/relation/trigger/behavior/js/creatorGenerator");

    var UpdateView = createView.CreateView.extend({

        startup: function ($super, cfg) {
            $super(cfg);
        },

        build: function () {
            this.prefix = "update";
        	if($("[mdlng-bhvr-data='precfg']").find(".behavior_update_preview").length != 1){
        		this.buildPreWhereView();
        	}
            if($("[mdlng-bhvr-data='precfg']").find(".behavior_detail_query_preview").length != 1){
                this.buildDetailQueryView();
            }
            var $element = $("<div class='behavior_update_view'/>");
            var $table = $("<table class='tb_simple modeling_form_table view_table' width='100%'/>");
            this.updateOrCreateFlag = "0";
            //明细表
            this.buildDetailRule($table, this.parent.detailRuleTmpStr,this.parent.detailTmpStr);
            //消息
            this.buildMsg($table,"remove");
            this.creatorConfig = new creatorGenerator.CreatorGenerator({$table:$table,parent:this});
            this.bindUpdateOrCreateEvent($table);
            //查询
            this.buildWhere($table, this.parent.whereTmpStr);
            //查询条件类型
            $table.find(".WhereTypeinput4").hide();
            //查询条件切换事件
            $table.find(".WhereTypediv input[type='radio']").on("change",function(){
            	 var $whereBlockDom = $table.find(".view_field_where_div");
            	 if(this.value === "2"){
            		 $(".mainModelWhereTip").remove();
            		 $whereBlockDom.hide();
            	 }else{
            		 $whereBlockDom.show();
            	 }
            });
            var whereType = $table.find(".WhereTypediv input[type='radio']:checked").val();
            if(whereType === "3"){
                $table.find(".WhereTypediv input[type='radio'][value='0']").prop("checked","checked");
            }
            $table.find(".WhereTypediv input[type='radio']").filter(':checked').trigger($.Event("change"));
            $table.find(".view_fdId_where_table").remove();

            //前置查询
            this.buildPreQuery($table,this.parent.preQueryTmpStr);
            //目标
            var authProperty = this.parent.targetData ? this.parent.targetData.authProperty : null;
            this.buildTarget($table, this.parent.targetTmpStr, authProperty);
            $element.append($table);

            this.config.viewContainer.append($element);
            return $element;
        },
        show : function($super){
            $super();
            // this.creatorConfig.show();
            this.initUpdateOrCreateEvent();
        },

        hide : function($super){
            $super();
            this.creatorConfig.hide();
            this.initUpdateOrCreateEvent();
        },
        initUpdateOrCreateEvent:function($table){
            var self= this;
            var detailRule = this.detailRule;
            if ( self.parent.config.$fdUpdateTypeTypeEle &&  self.parent.config.$fdUpdateTypeTypeEle.length>0){
                //初始化
                self.parent.config.$fdUpdateTypeTypeEle.each(function (idx, dom) {
                    var checked = $(dom).prop("checked");
                    if (checked) {
                        self.updateOrCreateFlag=this.value;
                        if (this.value == "0") {
                            self.creatorConfig.hide();
                        } else {
                            if (!detailRule || detailRule != "2") {
                                self.creatorConfig.show();
                            }
                        }
                    }
                });
            }
        },
        bindUpdateOrCreateEvent:function($table){
            var self= this;
            if ( self.parent.config.$fdUpdateTypeTypeEle &&  self.parent.config.$fdUpdateTypeTypeEle.length>0){
                //监听
                self.parent.config.$fdUpdateTypeTypeEle.on("change", function () {
                    self.updateOrCreateFlag=this.value;
                    if (this.value == "0") {
                        self.creatorConfig.hide();
                    } else {
                        self.creatorConfig.show();
                    }
                });
                //初始化
                self.initUpdateOrCreateEvent();
            }
        },
        getKeyData: function ($super, cfg) {
            var keyData = $super(cfg);
            return keyData;
        },

        initByStoreData: function ($super, storeData) {
            $super(storeData);
        }


    });

    exports.UpdateView = UpdateView;
});