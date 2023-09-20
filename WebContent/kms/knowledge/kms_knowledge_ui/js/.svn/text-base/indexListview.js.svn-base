define(function(require, exports, module) {
    var $ = null;
    if (window.$) {
        $ = window.$;
    } else {
        $ = require('lui/jquery');
    }
    var dialog = require('lui/dialog');
    var topic = require('lui/topic');
    var Template = require('lui/view/Template');
	var criteria = require('lui/criteria/base');
    var Spa = require('lui/spa/Spa');
	var env = require('lui/util/env');
	var lang = require('lang!kms-knowledge');
	var SpaConst = require('lui/spa/const');
    
    var markLock = false;
    var allLock = false;
    var createLock = false;
    var evalLock = false;
    var draftLock = false;
    var approveLock = false;
    var approvedLock = false;
	var readLock = false;
    
    // 根据筛选值变化显示不同排序
    window.initIndexSort = function(criVal, path) {
    	criVal = criVal? criVal : "";
		path = path? path : "";
		// 去除listview的sorts参数,防止sourceUrl保留原排序值
		try {
			showSortBtn();
			LUI("listview").sorts=[];
			if(criVal.indexOf("myBookmark") > -1) {
				// 防止重复点击同一 Nav
				if(markLock) return;
				initNavClickLock();
				markLock = true;
				// 初始化排序显示下标
				LUI("mark_sort").poolIndex=0;
				// 去除原排序小箭头样式
				LUI("mark_sort").setDefaultStyle();
				// 选中id为 mark_sort 的这个排序
				LUI("knowledge_sort_groud").setCurrent(LUI("mark_sort"));
				showSortBtn('mark_sort', true);
			} else if (criVal.indexOf("create") > -1 && criVal.indexOf("docStatus:10") == -1) {
				if(createLock) return;
				initNavClickLock();
				createLock = true;
				LUI("create_sort").poolIndex=0;
				LUI("create_sort").setDefaultStyle();
				LUI("knowledge_sort_groud").setCurrent(LUI("create_sort"));
				showSortBtn('create_sort', true);
			} else if (criVal.indexOf("myEval") > -1) {
				if(evalLock) return;
				initNavClickLock();
				evalLock = true;
				LUI("eval_sort").poolIndex=0;
				LUI("eval_sort").setDefaultStyle();
				LUI("knowledge_sort_groud").setCurrent(LUI("eval_sort"));
				showSortBtn('eval_sort', true);
			} else if (criVal.indexOf("create") > -1 && criVal.indexOf("docStatus:10") > -1) {
				if(draftLock) return;
				initNavClickLock();
				draftLock = true;
				LUI("draft_sort").poolIndex=0;
				LUI("draft_sort").setDefaultStyle();
				LUI("knowledge_sort_groud").setCurrent(LUI("draft_sort"));
				showSortBtn('draft_sort', true);
			} else if (criVal.indexOf("approval") > -1) {
				if(approveLock) return;
				initNavClickLock();
				approveLock = true;
				LUI("create_sort").poolIndex=0;
				LUI("create_sort").setDefaultStyle();
				LUI("knowledge_sort_groud").setCurrent(LUI("create_sort"));
				showSortBtn('create_sort', true);
			} else if (criVal.indexOf("approved") > -1) {
				if(approvedLock) return;
				initNavClickLock();
				approvedLock = true;
				LUI("approve_sort").poolIndex=0;
				LUI("approve_sort").setDefaultStyle();
				LUI("knowledge_sort_groud").setCurrent(LUI("approve_sort"));
				showSortBtn('approve_sort', true);
			} else if (path.indexOf("hot") > -1) {
				if(readLock) return;
				initNavClickLock();
				readLock = true;
				LUI("read_sort").poolIndex=1;
				LUI("read_sort").setDefaultStyle();
				LUI("knowledge_sort_groud").setCurrent(LUI("read_sort"));
				showSortBtn('read_sort', true);
			} else {
				if(allLock) {
					LUI("pubTime_sort").poolIndex=1;
					LUI("pubTime_sort").setDefaultStyle();
					LUI("knowledge_sort_groud").setCurrent(LUI("pubTime_sort"));
					showSortBtn('pubTime_sort', true);
                    // 显示小箭头样式
					// 默认倒序
					$(".lui_icon_s_default_filter").addClass("lui_icon_s_on_filter");
					$(".lui_widget_btn_icon").css({display:"inline-block"});
					return;
				}
				initNavClickLock();
				allLock = true;
				LUI("pubTime_sort").poolIndex=1;
				LUI("pubTime_sort").setDefaultStyle();
				LUI("knowledge_sort_groud").setCurrent(LUI("pubTime_sort"));
				showSortBtn('pubTime_sort', true);
			}

			// 显示小箭头样式
			// 默认倒序
			$(".lui_icon_s_default_filter").addClass("lui_icon_s_on_filter");
			$(".lui_widget_btn_icon").css({display:"inline-block"});
		} catch (e) {

			console.warn("initIndexSort.js error:" + e);
		}
    }
    
    window.checkHashUrlIndexOf = function(str) {
    	var hashUrl = decodeURIComponent(location.hash);
    	return hashUrl.indexOf(str) > -1;
    }
    
    function initNavClickLock() {
    	markLock = false;
        createLock = false;
        evalLock = false;
        draftLock = false;
        approveLock = false;
        approvedLock = false;
        allLock = false;
		readLock=false;
    }
    
    function bindNavClickEvent() {
		$(".lui_knowledge_sum_card_item_wiki").mousedown(initNavClickLock);
		$(".lui_knowledge_sum_card_item_atom").mousedown(initNavClickLock);
		$(".lui_knowledge_sum_card_item_document").mousedown(initNavClickLock);
		$("[data-path='/all']").mousedown(initNavClickLock);
		$("[data-path='/readInfo']").mousedown(initNavClickLock);
		$("[data-path='/approval']").mousedown(initNavClickLock);
		$("[data-path='/draftBox']").mousedown(initNavClickLock);
	}
	
    
	function changeNav(param, title, showFrame) {
		if(!showFrame || showFrame == "") showFrame = true 
	
		showFrame && LUI.pageHide("_rIframe");
		topic.publish(criteria.CRITERIA_UPDATE, param);
		// seajs的topic与筛选项对应
		seajs.use('lui/topic', function(topic) { 
		    topic.publish(criteria.CRITERIA_UPDATE, param);
		});
		
		changeNavTitle(title)
	}
	
	function changeNavTitle(title) {
		// 保证颜色跟随主题
		var titleHtml = getPanelTitle(title);
		LUI("kms_index_panel").props(0, { title: titleHtml });
	}
	
	function getPanelTitle(val) {
		var title = '<span class="lui_panel_title_main lui_tabpanel_navs_item_title">'+val+'</span>';
		return title;
	}
	
	function resetPanelTitle(bol) {
		changeNavTitle(lang['kmsKnowledge.index.all.know']);
		$(".lui_knowledge_sum_card_item_wiki").removeClass("selected");
		$(".lui_knowledge_sum_card_item_atom").removeClass("selected");
		$(".lui_knowledge_sum_card_item_document").removeClass("selected");

		$("[data-path='/readInfo']").removeClass("lui_list_nav_selected");
		$("[data-path='/approval']").removeClass("lui_list_nav_selected");
		$("[data-path='/draftBox']").removeClass("lui_list_nav_selected");
		if(!bol) {
			$("[data-path='/all']").addClass("lui_list_nav_selected");
			Spa.spa.setValue('j_path', "/all");
		}
		$(".approve_box").html("");
		$("#kms_index_panel .lui_panel_navs_l").hide();
		$("#kms_index_panel .criteria").css("margin-top","0");
	}


	window.changeApprove = function(num) {

		topic.publish('list.select.all.checked', {
			checked : false
		});
		var selectAllLable = $('#lui_list_operation_knowledge_btn_selectall [type=checkbox]');
		for(var i = 0;i<selectAllLable.length;i++){
			selectAllLable[i].checked = false;

		}
		if(num == "0") {
			var param = {
					operation: 'add', 
					key: 'mydoc', 
					value: 'approval'
				};   
			changeNav(param, lang['kmsKnowledge.index.my.approved1']);
		} else {
			var param = {
					operation: 'add', 
					key: 'mydoc', 
					value: 'approved'
				};   
			changeNav(param, lang['kmsKnowledge.index.my.approved1']);
		}
		showApproveHtml(num);
	}
	
	function showApproveHtml(num) {
		var html =   "<span onclick=\"changeApprove('0')\" class=\"approve_box_span "+(num == '0'? 'lui_tabpanel_navs_item_selected' : '')+"\">" +
					 "	<span class=\"approve_box_span_span lui_tabpanel_navs_item_c\">" +
					 "		<span class=\"approve_box_span_span_span "+ (num == '0'? 'lui_panel_title_main' : '') +" lui_tabpanel_navs_item_title\">" +
					 	lang['kmsKnowledge.index.my.approved2'] +
					 "		</span>" +
					 "	</span>" +
					 "</span>" +
					 "<span onclick=\"changeApprove('1')\" class=\"approve_box_span "+ (num == '1'? 'lui_tabpanel_navs_item_selected' : '') +"\">" +
					 "	<span class=\"approve_box_span_span lui_tabpanel_navs_item_c\">" +
					 "		<span class=\"approve_box_span_span_span "+ (num == '1'? 'lui_panel_title_main' : '') +" lui_tabpanel_navs_item_title\">" +
					 	lang['kmsKnowledge.index.my.approved3']+
					 "		</span>" +
					 "	</span>" +
					 "</span>" +
					 "</div>";
		
		$(".approve_box").html(html);
		$(".approve_box").show();
		$("#kms_index_panel .lui_panel_navs_l").show();
		$("#kms_index_panel .criteria").css("margin-top","56px");
	}
	
	function changeApproveEvent(etv) {
		var path = getEtvValue(etv, 'j_path');
        var criVal = getEtvValue(etv, 'cri.q');
        var spaCri = window.top.location.hash;
        if(!criVal || (spaCri && spaCri.indexOf("approval") == -1 && spaCri.indexOf("approved") == -1)) {
        	$(".approve_box").html("");
			$("#kms_index_panel .lui_panel_navs_l").hide();
        	return;
        }

        if(criVal.indexOf("mydoc:approved") > -1 || 
    		criVal.indexOf("mydoc:approval") > -1 || 
    		path == "/approval") {
        	changeApprove("0");
        }
	}

	function getEtvValue(etv, key) {
    	if(etv && etv.value) {
    		return etv.value[key]
		}
    	return "";
	}
	
	// 订阅路由值改变事件
    topic.subscribe(SpaConst.SPA_CHANGE_RESET, function(etv){

    	changeApproveEvent(etv);
    })
    
    var loaded = false;
    
    // 订阅Spa改变事件
    // 根据Spa变化动态改变panelTitle、sort、Nav
	topic.subscribe(SpaConst.SPA_CHANGE_VALUES, function(etv){

		var criVal = getEtvValue(etv, 'cri.q');

		var path = getEtvValue(etv, 'j_path');

		if(criVal == undefined || criVal == "") {
			resetPanelTitle(true);
			criVal = "";
		}

		var spaJPath = window.top.location.hash;

		if(spaJPath.indexOf("j_path=%2Feval") > -1) {
			if(criVal.indexOf("myEval") == -1 || criVal == "") {
				resetPanelTitle();
			}
		}

		if(spaJPath.indexOf("j_path=%2Fbookmark") > -1) {
			if(criVal.indexOf("myBookmark") == -1 || criVal == "") {
				resetPanelTitle();
			}
		}
		
		if(createLock) {
			if(criVal.indexOf("create") == -1) {
				resetPanelTitle();
			}
		}
		
		if(draftLock) {
			if(criVal.indexOf("mydoc:create;docStatus:10") == -1) {
				resetPanelTitle();
			}
		}

		if(approvedLock || approveLock) {
			var spaCri = window.top.location.hash;

			if(criVal.indexOf("approval") > -1 && spaCri.indexOf("j_path=%2Fapproval") > -1) {
				showApproveHtml("0");
			}

			if(criVal.indexOf("approved") > -1 && spaCri.indexOf("j_path=%2Fapproval") > -1) {
				showApproveHtml("1");
			}

			if(criVal.indexOf("approval") == -1 && criVal.indexOf("approved") == -1) {
				resetPanelTitle();
			}
		}

		if(path && path.indexOf("/index") > -1) {
			setTimeout(function() {
				showSortBtn();
			}, 250);
		} else {
			initIndexSort(criVal, path);
		}

		var docCategory = getEtvValue(etv, 'docCategory');
		ajaxCheckBorrowOpen(criVal, docCategory)
		if(loaded) return;
		loaded = true;
		
		// 绑定nav点击初始化事件
		bindNavClickEvent();
		changeApproveEvent(etv);
    })


	function showSortBtn(id, isShow) {
		if(id) {
			if(isShow) {
				$("#"+id).show();
			} else {
				$("#"+id).hide();
			}
		} else {
			$("#create_sort").hide();
			$("#mark_sort").hide();
			$("#eval_sort").hide();
			$("#approve_sort").hide();
			$("#draft_sort").hide();
		}
	}

	function ajaxCheckBorrowOpen(criVal, docCategory) {

		var el = $(".docIsBorrow_span");
		if(!el) return;
		if(criVal && (criVal.indexOf("approved") > -1 ||
			criVal.indexOf("approval") > -1 ||
			criVal.indexOf("create") > -1)) {
			el.hide();
			return;
		}
		var url = "/kms/knowledge/kms_knowledge_borrow/kmsKnowledgeBorrow.do?method=checkBorrowOpen";
		if(docCategory) {
			url += "&docCategory=" + docCategory;
		}
		$.ajax({
			url: env.fn.formatUrl(url),
			type: 'POST',
			dataType: 'json',
			success: function (data) {
				if(data == true) {
					el.show();
				} else {
					el.hide();
				}
			},
			error: function (e) {
				el.hide();
			}
		})
	}
    
})
