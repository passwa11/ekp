;(function() {
	var Dropdown = function(obj) {
		//定义属性用this 查找元素 obj
		var self = this;
		this.obj= obj;
		this.t = null; //定时器
		this.wrap = this.obj.parents(".conf_show_more_w");
		this.conf_drop_op = this.obj.siblings(".conf_drop_op");

		var w_wrap = this.wrap.width();
		this.conf_drop_op.width(w_wrap);

		//this.toggle(); //切换显示与隐藏
		this.obj.mouseenter(function(e) {
			self.toggle();
		});

		this.obj.mouseleave(function(e) {
			self.removeMenu();
		});

		this.conf_drop_op.mouseenter(function(e) {
			self.toggle();
		});

		this.conf_drop_op.mouseleave(function(e) {
			self.removeMenu();
		});

		this.conf_drop_op.find("li").click(function(e) {
			e.stopPropagation();
			self.wrap.removeClass("current");
		});
	}

	Dropdown.prototype = {
		toggle: function() {
			clearTimeout(this.t);
			this.wrap.addClass("current");
		},
		removeMenu: function() {
			var that = this;
			that.t = setTimeout(function() {
				that.wrap.removeClass("current");
			}, 200);
		}
	}

	//初始化
	Dropdown.init = function() {
		var btns = $(".conf_btn_edit");
		for ( var j = 0; j < btns.length; j++) {
			var btn = $(btns[j]);
			var childrens = btn.children();
			// 当按钮超过3个时，显示“更多”按钮
			if (childrens.length > 3) {
				btn.append($('<a class="conf_btn_more J_btn_more" href="javascript:void(0)"><i class="caret"></i></a>'));
				var _div = $('<div class="mod_drop_op conf_drop_op"></div>');
				var _ul = $('<ul class="bor2"></ul>');
				for ( var i = 2; i < childrens.length; i++) {
					var children = childrens[i];
					var li = $('<li></li>');
					li.append(children);
					_ul.append(li);
				}
				_div.append(_ul);
				btn.append(_div);
			}
		}

		var _this_ = this;
		$(".J_btn_more").each( function() {
			new _this_($(this));
		});
	};

	window["Dropdown"] = Dropdown;
})(jQuery);