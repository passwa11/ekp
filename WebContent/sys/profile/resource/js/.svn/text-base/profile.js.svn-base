/****************************************
 *			后台配置菜单搜索定位
 ****************************************/
var searchTimer;
seajs.use(['lui/jquery', 'theme!profile'], function($) {
	window.searchMenu = function(val, evt) {
		if (evt.keyCode == 13 || evt.keyCode == 38 || evt.keyCode == 40) {
			keySelection(evt.keyCode);
			if(evt.keyCode != 13)
				return;
		}
		val = val.replace(/(^\s*)|(\s*$)/g, ""); // 去空格
		if (searchTimer) {
			clearTimeout(searchTimer);
		}
		searchTimer = setTimeout(function() {
			___search(val);
			searchTimer = null;
		}, 500);
	};
	window.___search = function(val) {
		if(val.length < 1) {
			val = $(".location_input input").val().replace(/(^\s*)|(\s*$)/g, "");
			if(val.length < 1) {
				$("#selection_menus").empty();
				$(".location_input_selection").hide();
				return;
			}
		}
		var menus = []; // 保存菜单HTML
		$.each(menuDatas, function(i, n) {
			if(n.text.toUpperCase().indexOf(val.toUpperCase()) > -1 
				|| n.pinYin.toUpperCase().indexOf(val.toUpperCase()) > -1 ) {
				var selection = '';
				if(menus.length == 0) {
					selection = ' class="status_hover"';
				}
				menus.push('<li data-url="' + n.url + '"' + selection + '>' + n.text + '</li>');
			}
		});
		if(menus.length < 1) {
			menus.push('<li class="search_nodata">' + searchNodataMsg + '</li>');
		}
		$(".location_input_selection").show();
		$("#selection_menus").empty().append(menus.join(""));
	};
	// 键盘上下移动(上:38, 下:40, 确定:13)
	window.keySelection = function(keyCode) {
		var hoverNode = $("#selection_menus>li.status_hover");
		// 没有选择，默认选择第一项
		var selectedIndex = 0;
		if(hoverNode.length > 0) {
			selectedIndex = hoverNode.index() + 1;
		}
		if(keyCode == 13) { // 回车
			if(hoverNode.length > 0)
				hoverNode.click();
			return;
		}
		
		var selectionMenus = $("#selection_menus>li");
		var allCount = selectionMenus.length;
		if(keyCode == 40) { // 下移动
			if(selectedIndex >= allCount)
				selectedIndex = 1;
			else
				selectedIndex++;
		}
		if(keyCode == 38) { // 上移动
			if(selectedIndex <= 0)
				selectedIndex = allCount;
			else
				selectedIndex--;
		}
		selectionMenus.removeClass("status_hover");
		selectionMenus.eq((selectedIndex - 1)).addClass("status_hover");
	};
	
	$(function() {
		// 搜索按钮点击，显示搜索输入框
		var locationInput = $(".location_input"); // 搜索区域
		var navLeftMask = $(".lui_profile_navLeft_mask"); // 遮罩区域
		$(".lui_portal_header_location").click(function(event) {
			event.stopPropagation();
			if(locationInput.is(":hidden")) {
				locationInput.show();
				navLeftMask.show();
				locationInput.find("input").focus();
			} else {
				locationInput.hide();
				navLeftMask.hide();
			}
		});
		// 点击非搜索区域，隐藏搜索输入框
		$(document).not(locationInput).click(function() {
			locationInput.hide();
			navLeftMask.hide();
	    });
		locationInput.click(function(event) {
			event.stopPropagation();
		});
		
		// 搜索出来的菜单点击事件
		$("#selection_menus").on("click", "li", function() {
			var dataUrl = $(this).attr("data-url");
			if(dataUrl) {
				var url = document.location.href.split("#");
				location.href = url[0]+ "#" + dataUrl;
				location.reload();
			}
		}).on("mouseover", "li", function() {
			$("#selection_menus>li").removeClass("status_hover");
			$(this).addClass("status_hover");
		});
		
		// 输入框获得焦点
		$(".location_input input").focus(function() {
			$(".location_input").addClass("status_select");
		});
		
		// 输入框失去焦点
		$(".location_input input").blur(function() {
			if($("#selection_menus>li").length < 1)
			 $(".location_input").removeClass("status_select");
		});
		
		$(".icon_cancel").click(function() {
			$(".location_input input").val("");
			$("#selection_menus").empty();
			$(".location_input_selection").hide();
		});
	});
});