/**
 * 指令统一请求<br>
 */
define(function(require, exports, module) {

	var topic = require('lui/topic');
	var SpaConst = require('./const');
	var env = require('lui/util/env');
	var str = require('lui/util/str');

	var direction = {

		// 是否需要权限校验
		authEnable : true,

		directions : [],

		authCheckUrl : '/sys/authorization/SysAuthUrlCheckAction.do?method=checkUrlAuth',

		startup : function($super) {

			$super();

			if (!this.authEnable)
				return;

			topic.subscribe(SpaConst.SPA_CHANGE_VALUES, this.onDirection, this);
		},

		/**
		 * 注册<br>
		 * 每个指令初始化化过程中需要手动调用这个方法<br>
		 * Spa.spa.regist(this)
		 * 
		 */
		regist : function(obj) {

			this.directions.push(obj);
		},

		/**
		 * 执行指令<br>
		 * 获取所有注册过的指令，封装权限数据包，统一做权限校验，再发事件让具体指令做下一步校验
		 */
		onDirection : function(evt) {

			if (!evt)
				return;

			var datas = [];

			// 封装所有指令中涉及权限的数据
			for (var i = 0; i < this.directions.length; i++) {

				var direction = this.directions[i];

				var obj = $.extend({}, direction, evt.value);
				var url = str.variableResolver(direction.cfgSpaAuth, obj);

				var data = [];
				data.push(direction.id);
				data.push(url);

				datas.push(data);

			}

			if (datas.length == 0) {

				topic.publish(SpaConst.SPA_DIRECTION_DONE, {
					auth : [],
					values : evt.value
				});

				return;
			}

			// 统一做异步权限校验
			$.ajax({

				url : env.fn.formatUrl(this.authCheckUrl),
				dataType : 'json',
				type : 'post',
				data : {
					data : JSON.stringify(datas)
				},
				success : function(datas, textStatus) {

					topic.publish(SpaConst.SPA_DIRECTION_DONE, {
						auth : datas,
						value : evt.value
					});

				}
			});

		}
	};

	module.exports = direction;

});