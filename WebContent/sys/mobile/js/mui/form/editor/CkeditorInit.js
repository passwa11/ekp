/**
 * Ckeditor 初始化工具
 */
define(["dojo/topic","dijit/registry"], function (topic,registry) {
  return {
    init: function (name) {
		topic.subscribe('parser/done',function(){
			 window.CKEDITOR_BASEPATH = dojoConfig.baseUrl + "sys/mobile/js/lib/ckeditor/";
		      require(["lib/ckeditor/ckeditor"], function () {
		        var dom = document.querySelector("[name='" + name + "']");
		        dom.dojoClick = false;
		        CKEDITOR.replace(dom);
		        
		        /********绑定事件start**********/
		        //监听编辑器初始化完成事件
		        CKEDITOR.on('instanceReady', function (e) {
		        	//给所有的实例绑定失焦事件
					for(instance in CKEDITOR.instances){
						CKEDITOR.instances[instance].on('blur', function(event){
							//获取实例对象
							var sender = event && event.sender;
							if (sender) {
								//更新值到文本域对象
								sender.updateElement();
								//触发校验
								var srcElement = sender.element.$;
								var srcWgt = registry.getEnclosingWidget(srcElement);
								if(srcWgt && srcWgt.validation)
									srcWgt.validation.validateElement(srcWgt);
							}
						});
					}
		        });
		        /********绑定事件end**********/
		        
		        Com_Parameter.event["confirm"].push(function () {
		          if (Com_Submit.ajaxSubmit) {
		            CKEDITOR.instances[name].updateElement();
		          }
		          return true;
		        });
		      });	
		});
    },
  };
});
