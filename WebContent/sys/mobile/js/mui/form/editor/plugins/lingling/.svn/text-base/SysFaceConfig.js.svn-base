define([ "dojo/_base/declare","mui/util","mui/i18n/i18n!sys-evaluation"],
		function(declare,util,Msg) {
			var claz = declare(
					"sys.mui.form.editor.plugins.lingling.SysFaceConfig",
					null,
					{
						//表情配置
						_faceConfig: {
							"face":{
								"activeClass":'eval_bq_active',
								"tabId":"eval_bq_system",
								"bqName":"系统表情",
								"width":24,
								"height":24,
								"container":"eval_bg_system_container",
								"row":5,
								"col":17,
								"start":0,
								"max":79,
								"type":"face",
								"suffix":".gif",
								"path":util.formatUrl("/sys/evaluation/import/resource/images/bq/")
							},"dnyling":{
								"tabId":"eval_bq_dny_ling",
								"bqName":"动态凌凌",
								"width":60,
								"height":60,
								"container":"eval_bg_dny_ling_container",
								"isHide":true,
								"row":3,
								"col":6,
								"start":1,
								"max":32,
								"type":"dnyling",
								"suffix":".gif",
								"path":util.formatUrl("/sys/ui/resource/images/phiz/dnyling/")
							},"lingling":{
								"tabId":"eval_bq_ling_ling",
								"bqName":"凌凌表情",
								"width":60,
								"height":60,
								"container":"eval_bg_ling_ling_container",
								"isHide":true,
								"row":3,
								"col":6,
								"start":1,
								"max":33,
								"type":"lingling",
								"suffix":".png",
								"path": util.formatUrl("/sys/ui/resource/images/phiz/lingling/")
							}
						},
						//初始化表情配置信息
						_initSysFaceConfig: function(){
							for(var key in this._faceConfig){
								var faceCfg = this._faceConfig[key];
								faceCfg["configs"]=[];
								for(var i= faceCfg.start;i<=faceCfg.max;i++){
									faceCfg['configs'][i] ="["+faceCfg.type+i+"]";
								}
							}
						},
						//获取对应类型配置信息
						getByType:function(type){
							 return this._faceConfig[type];
						},
						//表情替换
						replaceFace: function(content){
							if(!content || content.length == 0){
								return '';
							}
							for(var key in this._faceConfig){
								var faceCfg = this._faceConfig[key];
								for(var i = faceCfg.start; i<=faceCfg.max;i++){
									var faceItem = faceCfg["configs"][i];
									while (content.indexOf(faceItem) != -1){
										content = content.replace(faceCfg["configs"][i], "<img width='"+faceCfg.width+"' src='"+faceCfg.path+i+faceCfg.suffix +"' type='"+faceCfg.type+"' />")
									}
								}
								content = content.replace(/&amp;nbsp;/g, '');
								content = content.replace(/&lt;div&gt;/g, '<br>');
								content = content.replace(/&lt;\/div&gt;/g, '');
							}
							return content;
						},
						//表情替换成空白字符
						replaceFaceWithBlank: function(content){
							if(!content || content.length == 0){
								return '';
							}
							for(var key in this._faceConfig){
								if('face' == key) continue;
								var faceCfg = this._faceConfig[key];
								for(var i = faceCfg.start; i<=faceCfg.max;i++){
									var faceItem = faceCfg["configs"][i];
									while (content.indexOf(faceItem) != -1){
										content = content.replace(faceCfg["configs"][i], "")
									}
								}
								content = content.replace(/&amp;nbsp;/g, '');
								content = content.replace(/&lt;div&gt;/g, '<br>');
								content = content.replace(/&lt;\/div&gt;/g, '');
							}
							return content;
						},
						startup : function() {
							this.inherited(arguments);
							this._initSysFaceConfig();
						}
					});
			var obj = new claz();
			obj.startup();
			return obj;
		});