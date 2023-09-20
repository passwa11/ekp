define( function(require, exports, module) {
	var base = require('lui/base');
	var $ = require("lui/jquery");

	var Switch = base.Component.extend( {
		initialize : function($super, config) {
			$super(config);
		},
		startup : function() {
			var self = this;
			/* 引入CSS文件 */
			if (self.supportCss3()) {
				self.loadingCSS(Com_Parameter.ContextPath + "sys/ui/extend/theme/default/style/switch.css");
			}
		},
		draw : function($super) {
			if (this.isDrawed)
				return;

			var self = this;

			if (this.config.showText && this.config.text) {
				this.element.append(this.config.text + " ");
			}

			/* 生成隐藏字段 */
			this.property = $('<input type="hidden" name="' + this.config.property + '" value="' + (this.config.checked == "true" ? this.config.checkVal : this.config.unCheckVal) + '">');
			/* 生成按钮开关 */
			this.label = $('<label class="weui_switch"></label>');
			this.element.append(this.property);
			this.element.append(this.label);

			/* 设置按钮状态（开启或关闭） */
			var checked = '';
			if (this.config.checked == "true") {
				checked = 'checked';
			}

			/*设置按钮是否可用 */
			var disabled = '';
			var cursor = '';
			if ('show' == this.config.showType) {
				disabled = 'disabled';
				cursor = ' style=\'cursor: default;\'';
			}
			this.checkbox = $('<input type="checkbox" ' + checked + ' ' + disabled + '/>');
			
			var parent_span = $('<span class="weui_switch_bd"/>');
			parent_span.append(this.checkbox);
			parent_span.append('<span' + cursor + '></span>');
			parent_span.append('<small' + cursor + '></small>');
			
			this.label.append(parent_span);
			this.label.append('<span name="switchText"></span>');

			/* 绑定按钮点击事件 */
			this.checkbox.click( function() {
				var status = $(this).is(':checked');
				// 处理需要转义的字符
				var _property = self.config.property.replace(/\(/g, "\\\(").replace(/\)/g, "\\\)").replace(/\./g, "\\\.");
				if(status)
					$("input[name='"+ _property +"']").val(self.config.checkVal);
				else
					$("input[name='"+ _property +"']").val(self.config.unCheckVal);
				self.setText(status);
				// 内容修改事件
				if(self.config.onValueChange) {
					eval(self.config.onValueChange);
				}
			});

			this.element.show();
			this.isDrawed = true;
			self.setText(this.config.checked == "true");

			return this;
		},
		/* 设置按钮显示文字 */
		setText : function(status) {
			if (!this.config.showText) {
				return;
			}

			var switchText = this.element.find("span[name=switchText]");
			if (status) {
				if (!this.config.text && this.config.enabledText)
					switchText.text(' ' + this.config.enabledText);
			} else {
				if (!this.config.text && this.config.disabledText)
					switchText.text(' ' + this.config.disabledText);
			}
		},

		/* 判断浏览器是否支持CSS3 */
		supportCss3 : function() {
			var style = 'animation-play-state';
			var prefix = [ 'webkit', 'Moz', 'ms', 'o' ], i, humpString = [], htmlStyle = document.documentElement.style, _toHumb = function(string) {
				return string.replace(/-(\w)/g, function($0, $1) {
					return $1.toUpperCase();
				});
			};
			for (i in prefix)
				humpString.push(_toHumb(prefix[i] + '-' + style));
			humpString.push(_toHumb(style));
			for (i in humpString)
				if (humpString[i] in htmlStyle)
					return true;
			return false;
		},

		/* 动态加载CSS文件 */
		loadingCSS : function(path) {
			var head = document.getElementsByTagName('head')[0];
			var link = document.createElement('link');
			link.href = path;
			link.rel = 'stylesheet';
			link.type = 'text/css';
			head.appendChild(link);
		}
	});

	exports.Switch = Switch;
});