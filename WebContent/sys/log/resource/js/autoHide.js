/**
 * 自动隐藏过高的内容
 * 1.引入autoHide.css
 * 2.使用<div class="pre_hide"></div>包裹页面中需要隐藏的内容
 * 3.调用init方法，传入隐藏高度
 */
define(function(require, exports, module) {
	var $ = require("lui/jquery");
	var limitHeight = 100;
	
	function init(height){
		if(height){
			limitHeight = height;
		}
		$(".pre_hide").each(function(){
			if($(this).height() > limitHeight){
				//展开按钮
				var showBtn = "<div class=\"mask\" style=\"display: block;\">"
							+"<div class=\"showbtn\" onClick=\"maskOff(this)\">"
							+"展开"
							+"<span class=\"arrowdown\"></span>"
							+"</div>"
							+"</div>";
				//收起按钮
				var hideBtn = "<span class=\"hidebtn\" style=\"display: none;\" onClick=\"maskOn(this)\">"
							+ "收起"
					 		+ "<i class=\"arrowup\"></i></span>";
				//高度
				$(this).css("height", limitHeight);
				//添加按钮
				$(this).append(showBtn).append(hideBtn);
				//添加隐藏样式
				$(this).addClass("hide");
			}
			$(this).removeClass("pre_hide");
		});
	}

	/*
	 * 展开
	 */
	function maskOff(obj){
		//展开内容
		$(obj).parent().parent().removeClass('hide').addClass("show");
		//高度
		$(obj).parent().parent().css("height", "100%");
		//隐藏展开按钮
		$(obj).parent().css("display", "none");
		//显示收起按钮
		$(obj).parent().parent().find('.hidebtn').show();
	}
	
	/*
	 * 收起
	 */
	function maskOn(obj){
		//收起内容
		$(obj).parent().removeClass('show').addClass("hide");
		//高度
		$(obj).parent().css("height", limitHeight);
		//显示展开按钮
		$(obj).parent().find(".mask").css("display", "block");
		//隐藏收起按钮
		$(obj).hide();
	}
	
	module.exports.init = init;
	window.maskOff = maskOff;
	window.maskOn = maskOn;
	
});