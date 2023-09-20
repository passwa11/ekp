/**
 * 视图预览
 */
define(function(require, exports, module) {
	
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
    var dialog = require("lui/dialog");
	var datamngLang = require("lang!sys-datamng");
	var Preview = base.DataView.extend({
		
		startup : function($super,cfg){
			$super(cfg);
			var self = this;
		},
		
		initProps: function ($super, cfg) {
            $super(cfg);
            var self = this;
        },
		
		// 渲染完毕之后添加事件
		doRender : function($super,html){
			$super(html);
			var self = this;
			topic.publish("preview_load_finish");
			this.postscript();
		},
		
		reRender : function(){
			this.source.get();
		},

        postscript : function(){
		    if (this.id === "view_preview_mobile") {
                $(".model-import-pc").click(function(){
                    if (!$(".model-import-pc-tip").hasClass("active")) {
                        $(".model-import-pc-tip").addClass("active");
                    }
                });
                $(".model-import-pc-btn-ok").click(function(){
                    Com_EventStopPropagation();
                    if($(this).attr("name") === "view"){
                    	//查看视图的导入
						topic.publish("preview_view_import");
					}else{
						var pcData = LUI("pcViewContainer").getKeyData()["views"][0];
						LUI("mobileViewContainer").views[0].reDrawByPc(pcData);
						dialog.success('导入成功!');
						$(".model-import-pc-tip").removeClass("active");
					}
                });
                $(".model-import-pc-btn-cancel").click(function(){
                    Com_EventStopPropagation();
                    $(".model-import-pc-tip").removeClass("active");
                });
            }
        },

		setSourceData: function(value,type){//根据类型提供获取数据的方式，可以是直接数据也可以是获取数据的方法
			var self = this;
			var datas = [];
			if(type == 'data'){
				datas = value;
			}else{
				if(typeof(value) == "string"){
					datas = eval(value+"()");
				}else{
					datas = value();
				}
			}
			self.source.config.datas = datas;
		},
		//获取多语言资源
		getDatamngLang :function (){
			return datamngLang;
		}
	})
	
	exports.Preview = Preview;
})