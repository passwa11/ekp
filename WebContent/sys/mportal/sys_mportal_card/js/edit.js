var lodingImg = "<img src='" + Com_Parameter.ContextPath
		+ "sys/ui/js/ajax.gif' />";

function mulValueChange(value) {
	if ('true' == value) {
		LUI.$("#panel").hide();
		LUI.$("#tabpanel").show();
	} else {
		LUI.$("#tabpanel").hide();
		LUI.$("#panel").show();
	}
}

function addTabPanelPortlet() {
	var len = LUI.$("#card_mul_config > .card_table").length;
	if(len<3){
		var xdiv = createPortletConfigTbody(len);
		LUI.$("#card_mul_config").append(xdiv);
		xdiv.find(".card_config_setting").click();
	}else{
		seajs.use(['lui/dialog','lang!sys.mportal:sysMportalCard'], function(dialog,lang) {
			dialog.alert(lang["sysMportalCard.prompting"]);
		});
	}
}

function createPortletConfigTbody(x, title) {
	var xtobdy = LUI.$("<table class='card_table card_table_tabpanel tb_normal ' id='card_table_" + x + "' ></table>");
	var xtr1 = LUI.$("<tr class='card_tr_title'></tr>");
	var xtd1 = LUI.$("<td></td>");
	var xTitle = LUI.$("<div class='card_title'>" + (title ? title : "未设置") + "</div>");
	xTitle.click(function(evt) {
		var xid = LUI.$(this).closest(".card_table").attr("id");
		var index = xid.substring(xid.lastIndexOf("_") + 1, xid.length);
		switchPorgletConfigTable(parseInt(index));
	});
	var xConfig = LUI.$("<div class='card_config'></div>");
	var xSetting = LUI.$("<span class='card_config_setting lui_icon_s lui_icon_s_icon_set_green'></span>").click(
					function(evt) {
						var xid = LUI.$(this).closest(".card_table").attr("id");
						var index = xid.substring(xid.lastIndexOf("_") + 1,
								xid.length);
						switchPorgletConfigTable(parseInt(index));
					});
	var xMoveUp = LUI.$("<span class='lui_icon_s lui_icon_s_icon_arrow_up_blue'></span>").click(
					function(evt) {
						var xid = LUI.$(this).closest(".card_table").attr("id");
						var index = xid.substring(xid.lastIndexOf("_") + 1,
								xid.length);
						moveUpPorgletConfigTable(parseInt(index));
					});
	var xMoveDown = LUI.$("<span class='lui_icon_s lui_icon_s_icon_arrow_down_blue'></span>").click(
					function(evt) {
						var xid = LUI.$(this).closest(".card_table").attr("id");
						var index = xid.substring(xid.lastIndexOf("_") + 1,
								xid.length);
						moveDownPorgletConfigTable(parseInt(index));
					});
	var xMoveDel = LUI.$("<span  class='lui_icon_s lui_icon_s_icon_close_red'></span>").click(
					function(evt) {
						var xid = LUI.$(this).closest(".card_table").attr("id");
						var index = xid.substring(xid.lastIndexOf("_") + 1,
								xid.length);
						deletePorgletConfigTable(parseInt(index));
					});
	xtd1.append(xTitle).append(
			xConfig.append(xMoveUp).append("&nbsp;").append(xMoveDown).append(
					"&nbsp;").append(xMoveDel).append("&nbsp;")
					.append(xSetting)).append(
			"<div style='clear: both;'></div>");
	xtobdy.append(xtr1.append(xtd1));

	var xtr2 = LUI.$("<tr class='card_setting_body'></tr>");
	var xtd2 = LUI.$("<td></td>").append(createPortletConfigTable().show());
	xtobdy.append(xtr2.append(xtd2).hide());
	return xtobdy;
}

function createPortletConfigTable() {
	var xtable = LUI.$("#panel .card_table").clone();
	xtable.removeAttr("id");
	xtable.find(":input").val("");
	xtable.find(":input").prop("readonly","");
	xtable.find("script").remove();
	xtable.find('.lui_mportlet_options').empty();
	xtable.find('.lui_mportlet_operations').empty();
	return xtable;
}

function deletePorgletConfigTable(x) {
	seajs.use([ 'lui/dialog', 'lui/jquery' ], function(dialog) {
		dialog.confirm("确定删除该页签？", function(val) {
			if (val == true) {
				LUI.$("#card_table_" + x).remove();
				updatePorgletConfigTableIndex();
			}
		})
	});
}

function updatePorgletConfigTableIndex() {
	LUI.$("#card_mul_config > .card_table").each(function(i, ele) {
		LUI.$(ele).attr("id", "card_table_" + i);
	});
}

function moveUpPorgletConfigTable(x) {
	if (x <= 0) {
		return;
	}
	var y = x - 1;
	LUI.$("#card_table_" + y).before(LUI.$("#card_table_" + x));
	updatePorgletConfigTableIndex();
}

function moveDownPorgletConfigTable(x) {
	var len = LUI.$("#card_mul_config > .card_table").length;
	if (x >= len) {
		return;
	}
	var y = x + 1;
	LUI.$("#card_table_" + y).after(LUI.$("#card_table_" + x));
	updatePorgletConfigTableIndex();
}

function switchPorgletConfigTable(x) {
	LUI.$(".card_table .card_setting_body").filter(function(index) {
		var xid = LUI.$(this).closest(".card_table").attr("id");
		var index = xid.substring(xid.lastIndexOf("_") + 1, xid.length);
		if (parseInt(index) == x) {
			return false;
		} else {
			return true;
		}
	}).hide();
	var currentTable = LUI.$("#card_table_" + x).find(".card_setting_body");
	if (currentTable.is(":visible")) {
		currentTable.hide();
	} else {
		currentTable.show();
	}
}

function selectPortlet(ele) {

	seajs.use([ 'lui/dialog', 'lui/jquery','lang!sys-mportal' ],
					function(dialog, $,lang) {
                        var $table = $(ele).closest(".card_table");
						dialog.iframe(
										"/sys/mportal/sysMportalMportlet_dialog.jsp?portletId="+$table.find("[name='fdPortletId']").val(),
										lang['sysMportalCard.dialog.select'],
										function(val) {
											if (!val) {
												return;
											} else {
												$table.find("[name='fdPortletId']").val(val.fdPortletId);
												$table.find("[name='fdPortletName']").val(val.fdPortletName);
												if(val.description){
													$table.find("[name='descriptionContent']").show();
													$table.find("[name='description']").text(val.description);			
												}else{
													$table.find("[name='descriptionContent']").hide();
												}
												$table.parents('.card_table').find(".card_title").html(val.fdPortletName);

												var __jsname = val.uuid+ "_mportlet";

												$table.find(".lui_mportlet_options").attr('id', __jsname)
														.html(lodingImg)
														.show()
														.load(
																Com_Parameter.ContextPath + "sys/mportal/mportlet_vars.jsp?x=" + (new Date().getTime()),
																{
																	"fdId" : val.fdPortletId,
																	"jsname" : __jsname
														});
												$table.find(".lui_mportlet_operations")
														.html(lodingImg)
														.show()
														.load(
																Com_Parameter.ContextPath + "sys/mportal/mportlet_operations.jsp?x=" + (new Date().getTime()),
																{
																	"fdId" : val.fdPortletId
														});
											}

										}, {
											width : 750,
											height : 550
										});
					});
}

// 转换多页签配置参数
function parsePanelConfig(configs, container) {

	container.each(function(indx, item) {

		var config = {
			operations : {},
			vars : {}
		};

		var $item = $(item);

		// 封装id和name
		config.portletName = $item.find('input[name="fdPortletName"]').val();

		config.portletId = $item.find('input[name="fdPortletId"]').val();

		// 封装高级设置
		$item.find('input[name="___operation"]').each(function(i, o) {

			var $o = $(o);
			var key = $o.val();

			config.operations[key] = $o.is(':checked');

		});

		// 封装参数设置
		var jsname = $item.find('.lui_mportlet_options').attr('id');

		if (window[jsname] && window[jsname].VarSet) {

			var _varset = window[jsname].VarSet;
			var length = _varset.length;

			for (var i = 0; i < length; i++) {
				config.vars[_varset[i].name] = _varset[i].getter();
			}
		}

		configs.push(config);

	});

}

// 转换配置参数
function parseConfigs() {

	var selected = $('input[name="fdType"]:checked').val();

	var configs = [];

	if ('false' == selected)
		// 单页签
		parsePanelConfig(configs, $("#panel").find('.card_table'));
	else
		// 多页签
		parsePanelConfig(configs, $('#tabpanel').find('.card_table_tabpanel'));

	var configsString = JSON.stringify(configs);

	if (configsString)
		$("[name='fdPortletConfig']").val(configsString);

}

// 参数设置校验
function optionsValidation($panel) {

	var jsname = $panel.find('.lui_mportlet_options').attr('id');

	if (window[jsname]) {

		if (!window[jsname].validation()) {
			return false;
		}
	}

	return true
}

function submit(method) {

	// 校验
	var selected = $('input[name="fdType"]:checked').val();

	if ('false' == selected) {
		// 单页签
		if (!optionsValidation($("#panel")))
			return;

	} else {

		var valed = true;
		// 多页签
		$('#tabpanel').find('.card_table_tabpanel').each(function(index, item) {

			if (!optionsValidation($(item))) {

				valed = false;
				return false;

			}
		})

		if (!valed)
			return;
	}
	 
	/*if('true' == selected){
		if(!checkNameLength()){
			seajs.use(['lui/dialog'], function(dialog){
				dialog.alert(nameLengthError);
			});
			return;
		}
	}*/

	parseConfigs();
	Com_Submit(document.sysMportalCardForm, method);
}

function checkNameLength(){
	
	var container = $('#tabpanel').find('.card_table_tabpanel');
	
	var result = true;

	var fdName = $('input[name="fdName"]').val();
	
	if(fdName.length > 5)
		result = false;
	
	if(result){
		container.each(function(indx, item) {
			
			var $item = $(item);
			
			// 封装id和name
			var portalName = $item.find('input[name="fdPortletName"]').val();
			
			if(portalName.length > 3) {
				
				result = false;
				
				return false;
				
			}
			
		});
	}
	
	return result;
}

function setOperations(operations, elem) {
	elem.find('input[name="___operation"]').each(function(index, item) {
		var $item = $(item);
		if (operations[$item.val()]) {
			$item.prop('checked', true);
		} else {
			$item.prop('checked', false);
		}
	});
}

function setPanelConfig($panel, index, config) {

	var mid = config.portletId;

	var jsname = "_mportlet_" + index;

	if (!mid)
		return;

	$panel.find('input[name="fdPortletName"]').val(config.portletName);
	$panel.find('input[name="fdPortletId"]').val(config.portletId);
	 $.ajax({    
	     type:"post",   
	     dataType:'json',
	     url: Com_Parameter.ContextPath+"sys/mportal/sys_mportal_card/sysMportalCard.do?method=getPortletById&portletId="+config.portletId,     
	     success:function(data){
		    if(data.description){
		    	$panel.find('div[name="descriptionContent"]').show();
		    	$panel.find('span[name="description"]').text(data.description);
		    }
		}
	 });

	$panel
			.find('.lui_mportlet_options')
			.html(lodingImg)
			.show()
			.load(
					Com_Parameter.ContextPath
							+ "sys/mportal/mportlet_vars.jsp?x="
							+ (new Date().getTime()),
					{
						"fdId" : mid,
						"jsname" : jsname
					},
					function() {
						if (window[jsname]) {

							window[jsname].setValue(config.vars);

							$panel.find('.lui_mportlet_options').attr('id',
									jsname);

							if (window[jsname].VarSet) {
								for (var i = 0; i < window[jsname].VarSet.length; i++) {
									var item = window[jsname].VarSet[i];
									if (item.name == "menuId") {
										// 验证是否被删除
										$
												.ajax(
														Com_Parameter.ContextPath
																+ "sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=isExist",
														{
															type : "get",
															data : {
																fdId : item
																		.getter().menuId
															},
															async : false,
															success : function(
																	data) {
																// if
																// (!data.isExist)
																// {
																// $(
																// "[value='"
																// + item
																// .getter().menuId
																// + "']")
																// .closest(
																// "td")
																// .append(
																// "<div
																// style='color:#d02300'
																// >(该快捷方式已经被删除)</div>");
																// }
															}
														});
										break;
									}
								}
							}
						}

					});

	$panel.find(".lui_mportlet_operations").html(lodingImg).show().load(
			Com_Parameter.ContextPath
					+ "sys/mportal/mportlet_operations.jsp?x="
					+ (new Date().getTime()), {
				"fdId" : mid
			}, function() {
				setOperations(config.operations, $panel);
			});
}

function editInit() {

	seajs.use([ 'lui/jquery' ], function($) {
		$(function() {

			var configs = $("[name='fdPortletConfig']").val();

			if (configs) {
				configs = JSON.parse(configs);
			}
				
			var len = configs.length;
			if (len == 0) {
				return;
			}
			
			var fdType = $("input[name='fdType']:checked").val();
			var tabMulFlag = false;
			if (len == 1) {
				if (fdType == 'true') {
					tabMulFlag = true;
				}
			} else {
				tabMulFlag = true;
			}
			
			// 单页签	
			if (!tabMulFlag) {
				setPanelConfig($('#panel'), 0, configs[0]);
			} else {// 多页签

				// $('#tabpanle');

				seajs.use([ 'lui/util/env' ], function(env) {
					for (var i = 0; i < configs.length; i++) {

						var xtable = createPortletConfigTbody(i, env.fn
								.formatText(configs[i].portletName));

						LUI.$("#card_mul_config").append(xtable);

						setPanelConfig(xtable, i, configs[i]);
					}
				});

			}

		});
	});

}
