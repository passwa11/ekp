<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.dialog">
    <template:replace name="head">
    	<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/dialog.css" rel="stylesheet">
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/boardTemplate.css?s_cache=${LUI_Cache}" />
		<link type="text/css" rel="stylesheet" href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}" />
		<link type="text/css" rel="stylesheet"
	              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
	    <link type="text/css" rel="stylesheet"
	              href="${KMSS_Parameter_ContextPath}sys/modeling/base/relation/trigger/behavior/css/behavior.css"/>
	    <link type="text/css" rel="stylesheet"
	              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>
	    <link type="text/css" rel="stylesheet"
	              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modelTable.css?s_cache=${LUI_Cache}"/>
	              <link type="text/css" rel="stylesheet"
	              href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/showFilters.css?s_cache=${LUI_Cache}"/>
		<link type="text/css" rel="stylesheet"
			  href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/display.css?s_cache=${LUI_Cache}"/>
		<style>
	       
	        
	    </style>
	</template:replace>
    <template:replace name="content">
    	<div class="lui_custom_list_container" style="display:none;background-color: white">
		<div class="display_dialog">
			<div class="model-mask-panel">
                <table class="where_tmp_html" style="width:100%;">
                    <tr class="main_where">
                        <td width="95%">
                       		<div class="display_dialog_where_set">${lfn:message('sys-modeling-base:listview.condition.setting') }</div>
                        	<div class="display_dialog_where_rule">${lfn:message('sys-modeling-base:listview.query.rules') }：</div>
	                         <div class="WhereTypediv">
								<label class="WhereTypeinput1"><input type="radio" value="0" name="fdDisplayWhereType" checked /><span>${lfn:message('sys-modeling-base:relation.meet.all.conditions') }</span></label>
								<label class="WhereTypeinput2"><input type="radio" value="1" name="fdDisplayWhereType"  /><span>${lfn:message('sys-modeling-base:relation.meet.any.conditions') }</span></label>
								<label class="WhereTypeinput3" style="margin-left:10px;"><input type="radio" value="2" name="fdDisplayWhereType" /><span>${lfn:message('sys-modeling-base:listview.unconditional') }</span></label>
							</div>
                            <div class="model-mask-panel-table-base view_field_where_div">
                                <table class="tb_normal field_table view_field_where_table" width="100%">
                                    <thead>
	                                    <tr>
	                                    	<td width="15%"></td>
	                                        <td width="15%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperator') }</td>
	                                        <td width="55%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdValue') }</td>
	                                        <td width="15%">${lfn:message('sys-modeling-base:modelingAppViewopers.fdOperation') }</td>
	                                    </tr>
                                    </thead> 
                                    <tbody class="model-table-right where_view" style="display:none;">
                                         <%--<tr class="where_tr">
                                         	<td width="15%" class="display_item">${lfn:message('sys-modeling-base:modelingAppListview.fdDisplay') }</td>
                               				<td colspan="3" class="display_item_td" style="padding:0px;">
                               					<table class="where_html_temp model-mask-panel-table">
                               					</table>
                               				</td>
                                         </tr>   --%>
            						</tbody>
                                </table>
                                
                                <div class="model-mask-panel-table-create table_opera">
                                    <div>${lfn:message('sys-modeling-base:button.add') }</div>
                                </div>
                            </div>
                        </td>
                    </tr>
                </table>
          </div>
          <div class="display_css">
          		<div class="display_css_title">
          			<span>${lfn:message('sys-modeling-base:modelingAppListview.fdDisplayCssSet') }</span>
          			<samp>${lfn:message('sys-modeling-base:listview.metCondition.perform.set') }</samp>
          		</div>
          		<div class="display_css_content">
          			<div>
						<div class="display_css_content_property">
							<span>${lfn:message('sys-modeling-base:relation.field') }：</span>
							<div class="display_css_content_field">
								<div>
									<div style="display:inline-block;" class="fontColorSelect">
										<span>${lfn:message('sys-modeling-base:listview.font') }：</span>
										<div class="colorColorDiv" name="font_color">
											<div data-lui-mark="colorColor"></div>
										</div>
									</div>
									<div style="display:inline-block;" class="backgroundColorSelect">
										<span>${lfn:message('sys-modeling-base:listview.background.color') }：</span>
										<div class="colorColorDiv"  name="background_color">
											<div data-lui-mark="colorColor"></div>
										</div>
									</div>
									<div class="add_tab_switch">
										<ui:switch property="addTab" checkVal="1" unCheckVal="0" text="${lfn:message('sys-modeling-base:listview.add.tag') }：" onValueChange="IsAddTab(this)"></ui:switch>
									</div>
									<div class="tab_temp_html">
										<div>
											<div>
												<span>${lfn:message('sys-modeling-base:listview.display.position') }：</span>
												<span class="tab_temp_html_cont"><input type="radio" name="showPosition" value="0" checked/>${lfn:message('sys-modeling-base:listview.before.field.content') }</span>
												<span class="tab_temp_html_cont"><input type="radio" name="showPosition" value="1"/>${lfn:message('sys-modeling-base:listview.after.field.content') }</span>
											</div>
											<div>
												<span>${lfn:message('sys-modeling-base:listview.mark.type') }：</span>
												<span class="tab_temp_html_cont"><input type="radio" name="tabStyle" value="0" checked/>${lfn:message('sys-modeling-base:listview.custom.content') }</span>
												<span class="tab_temp_html_cont"><input type="radio" name="tabStyle" value="1"/>${lfn:message('sys-modeling-base:listview.icon') }</span>
											</div>
											<div class="custom_content" >
												<div>
													<span>${lfn:message('sys-modeling-base:listview.mark.content') }：</span>
													<input type="text" name="tabContent" class="tabContent"/>
												</div>
												<div>
													<span>${lfn:message('sys-modeling-base:listview.font.size') }：</span>
													<div style="display:inline-block;width:80%;" class="defaultValueItem">
														<select name="whereFieldValueSelect" class="whereFieldValueSelect">
															<option name="whereFieldValue" value="12">12px</option>
															<option name="whereFieldValue" value="14">14px</option>
															<option name="whereFieldValue" value="16">16px</option>
															<option name="whereFieldValue" value="18">18px</option>
															<option name="whereFieldValue" value="20">20px</option>
															<option name="whereFieldValue" value="22">22px</option>
														</select>
													</div>
												</div>
												<div class="fontColorTabSelect">
													<span>${lfn:message('sys-modeling-base:listview.font.color') }：</span>
													<div name="font_color_tab" class="colorColorDiv">
														<div data-lui-mark="colorColor"></div>
													</div>
												</div>
												<div class="backgroundColorTabSelect">
													<span>${lfn:message('sys-modeling-base:listview.background.fill') }：</span>
													<div name="background_color_tab"  class="colorColorDiv">
														<div data-lui-mark="colorColor"></div>
													</div>
												</div>
											</div>
											<div class="icon_content" style="display:none;">
												<div>
													<span>${lfn:message('sys-modeling-base:listview.default.icon') }：</span>
													<div class="model-view-panel-table-td">
														<div class="appMenu_main_icon" onclick="selectIcon();"><i class="iconfont_nav"></i></div>
														<input name="fdIcon" type="hidden" value="iconfont_nav"/>
													</div>
												</div>
												<div>
													<span>${lfn:message('sys-modeling-base:listview.icon.size') }：</span>
													<div style="display:inline-block;width:80%;" class="iconSizeItem">
														<select name="iconSelect" class="iconSelect">
															<option name="whereiconFieldValue" value="10">10 x 10 px</option>
															<option name="whereiconFieldValue" value="14">12 x 12 px</option>
															<option name="whereiconFieldValue" value="16">14 x 14 px</option>
															<option name="whereiconFieldValue" value="18">18 x 18 px</option>
															<option name="whereiconFieldValue" value="20">20 x 20 px</option>
															<option name="whereiconFieldValue" value="22">22 x 22 px</option>
														</select>
													</div>
												</div>
											</div>
										</div>
									</div>
          						</div>
							</div>
						</div>
					</div>
          			<div>
          				<div class="display_css_content_position">
          					<span class="font-position">${lfn:message('sys-modeling-base:listview.field.row') }：
          					<span class="font-position-hover">${lfn:message('sys-modeling-base:listview.metCondition.display.style') }</span></span>
          					<div class="font-position-content">
          					<div style="display:inline-block;" class="fontColorItemSelect">
	          						<span>${lfn:message('sys-modeling-base:listview.font') }：</span>
	          						<div name="font_color_item"  class="colorColorDiv">
	       								<div data-lui-mark="colorColor"></div>
	       							</div>
	          					</div>
	          					<div style="display:inline-block;" class="backgroundColorItemSelect" >
	          						<span>${lfn:message('sys-modeling-base:listview.background.color') }：</span>
	          						<div name="background_color_item" class="colorColorDiv">
	       								<div data-lui-mark="colorColor"></div>
	       							</div>
	          					</div>
							</div>
          				</div>
          			</div>
          			<div>
          				<div class="displayCss-pre">
          					<span>${lfn:message('sys-modeling-base:sysform.preview') }</span>
          					<div class="model-mask-panel">
          						<table class="tb_normal field_table view_field_css_table">
          							<thead>
          								<tr>
	                                    	<td width="15%" class="display_view_item">${lfn:message('sys-modeling-base:modelingAppListview.fdDisplay') }</td>
	                                        <td width="20%">${lfn:message('sys-modeling-base:listview.other.display.items') }</td>
	                                    </tr>
          							</thead>
          							<tbody>
          								<tr class="fieldItem">
	                                    	<td width="15%" class="satisfy_condition_content">
	                                    		<span class="preTab">
							                        <i class="iconfont_nav"></i>
						                        </span>
						                        <span class="statify">${lfn:message('sys-modeling-base:modeling.form.Content') }</span>
						                        <span class="lastTab"></span>
						                     </td>
	                                        <td width="20%">${lfn:message('sys-modeling-base:listview.other') }</td>
	                                    </tr>
	                                    <tr>
	                                    	<td width="15%">${lfn:message('sys-modeling-base:listview.unsatisfactory.content') }</td>
	                                        <td width="20%">${lfn:message('sys-modeling-base:listview.other') }</td>
	                                    </tr>
          							</tbody>
          						</table>
          					</div>
          				</div>
          			</div>
          		</div>
          </div>
          </div>
		 <div class="lui_custom_list_box_content_col_btn">
          <a class="lui_custom_list_box_content_blue_btn" href="javascript:void(0)" onclick="ok()">${lfn:message('button.ok') }</a>
          <a class="lui_custom_list_box_content_whith_btn" href="javascript:void(0)" onclick="cancle()">${lfn:message('button.cancel') }</a>
     	</div>
	 </div>
	  <script>
    	var type = '${param.type}';
    	
    	window.colorChooserHintInfo = {
            cancelText: '取消',
            chooseText: '确定'
        };
    	Com_IncludeFile("jquery.js");
    	Com_IncludeFile("xform.js");
        Com_IncludeFile("spectrum.js", Com_Parameter.ContextPath + 'resource/js/colorpicker/', 'js', true);
        Com_IncludeFile("spectrum.css", Com_Parameter.ContextPath + 'resource/js/colorpicker/css/', 'css', true);
        Com_IncludeFile("spectrumColorPicker.js", Com_Parameter.ContextPath + 'sys/modeling/base/views/business/res/', 'js', true);
        
	    var interval = setInterval(beginInit, "50");
		function beginInit(){
			if(!window['$dialog'])
				return;
			clearInterval(interval);
			var selected = $dialog.___params.selected;
			var text = $dialog.___params.text;
			var displayType = $dialog.___params.displayType;
			var showMode = $dialog.___params.showMode;
			var allField = $dialog.___params.allField;
			var modelDict = $dialog.___params.modelDict;
			var data = {}
			if(type == "update"){
				data = $dialog.___params.data;
			}
			seajs.use([ 'sys/ui/js/dialog' ,'lui/topic','sys/modeling/base/resources/js/listview_display_dialog',
						'sys/modeling/base/listview/config/js/displayCssSetContainer'],
					function(dialog,topic,DisplayDialog,displayCssSetContainer) {
						window.ok = function(){
							var displayCssSet = {};
							displayCssSet.whereType = {};
							displayCssSet.where = [];		//查询规则
							displayCssSet.field = {};		//字段
							displayCssSet.fieldItem = {};	//字段所在行
							displayCssSet.fieldKey = "";
							displayCssSet.fieldText = "";
							displayCssSet.selected = {};

							//where条件
							displayCssSet.whereType.whereTypeValue = $("[name='fdDisplayWhereType']:checked").val();
							displayCssSet.whereType.whereTypeText = $("[name='fdDisplayWhereType']:checked +span").text();
							var whereData = window.DisplayDialogInstance.getKeyData();
							displayCssSet.where = whereData.where;
							displayCssSet.fieldKey = whereData.fieldKey.split(".")[0];
							displayCssSet.fieldText = whereData.fieldText;
							displayCssSet.fieldType = whereData.fieldType;
							displayCssSet.selected = whereData.selected;

							if(displayType === "board"){
								var keyData = window.displayCssSetContainerInstance.getKeyData();
								displayCssSet.field = keyData.field || {};
								displayCssSet.fieldItem = keyData.fieldItem || {};
								displayCssSet.field.tab = {};
							}else{
								//字段
								var tab = {};
								tab.showPosition = $("[name='showPosition']:checked").val();
								tab.tabType = $("[name='tabStyle']:checked").val();
								tab.tabContent = $(".tabContent").val();
								tab.tabFontSize = $("[name='whereFieldValue']:checked").val();
								tab.tabFontColor = $("[name='font_color_tab']").find("input[type='hidden']").val();
								tab.tabBackgroundColor = $("[name='background_color_tab']").find("input[type='hidden']").val();
								tab.defaultIcon = $("input[name='fdIcon']").val();
								tab.iconSize = $("[name='whereiconFieldValue']:checked").val();
								tab.isShow = $("[name='addTab']").val();

								displayCssSet.field.fontColor = $("[name='font_color']").find("input[type='hidden']").val();
								displayCssSet.field.backgroundColor = $("[name='background_color']").find("input[type='hidden']").val();
								displayCssSet.field.tab = tab;

								//字段所在行
								displayCssSet.fieldItem.ItemColor = $("[name='font_color_item']").find("input[type='hidden']").val();
								displayCssSet.fieldItem.ItemBackgroundColor = $("[name='background_color_item']").find("input[type='hidden']").val();
							}

							$dialog.hide(JSON.stringify(displayCssSet));
						}
						window.cancle = function(){
							$dialog.hide();
						}

						window.getRGBA = function(colorData,a){
							var color = (colorData.charAt(0)=="#") ? colorData.substring(1,7):colorData;
							var R = parseInt(color.substring(0,2),16);
							var G = parseInt(color.substring(2,4),16);
							var B = parseInt(color.substring(4,6),16);
							return 'rgb('+ R + ','+ G +','+ B +',' + a + ')';
						}
						window.updatePreview = function(){
							//字段
							var fontcolor = $("[name='font_color']").find("input[type='hidden']").val();
							var background_color = $("[name='background_color']").find("input[type='hidden']").val();
							//字段所在行
							var font_color_item_color = $("[name='font_color_item']").find("input[type='hidden']").val();
							var background_color_item_color = $("[name='background_color_item']").find("input[type='hidden']").val();

							//字段
							$(".statify").css({"color":fontcolor});
							$(".satisfy_condition_content").css({"background-color":background_color});
							if(displayType==='calendar'){
								$(".satisfy_condition_title").css({"background-color":window.getRGBA(background_color,'20%')});
							}
							//字段所在行
							$(".fieldItem").css({"color":font_color_item_color});
							$(".fieldItem").css({"background-color":background_color_item_color});

							//标记
							var tabText = $(".tabContent").val();
							var fontSize = $("[name='whereFieldValue']:checked").val();
							var font_color_tab = $("[name='font_color_tab']").find("input[type='hidden']").val();
							var background_color_tab = $("[name='background_color_tab']").find("input[type='hidden']").val();
							var position = $("[name='showPosition']:checked").val();
							var typeStyle = $("[name='tabStyle']:checked").val();
							var iconSize = $("[name='whereiconFieldValue']:checked").val();
							if(typeStyle == '0'){
								//自定义
								if(position == '0'){
									$(".lastTab").text("");
									$(".lastTab").removeAttr("style");
									$(".preTab").text(tabText);
									$(".preTab").css({"color":font_color_tab});
									$(".preTab").css({"background-color":background_color_tab});
									$(".preTab").css({"font-size":fontSize+"px"});
								}else{
									$(".preTab").text("");
									$(".preTab").removeAttr("style");
									$(".lastTab").text(tabText);
									$(".lastTab").css({"color":font_color_tab});
									$(".lastTab").css({"background-color":background_color_tab});
									$(".lastTab").css({"font-size":fontSize+"px"});
								}

							}else{
								//图标
								$(".lastTab").text("");
								$(".preTab").text("");
								$(".preTab").removeAttr("style");
								$(".lastTab").removeAttr("style");
								var html = '<i></i>';
								if(position == '0'){
									$(".preTab").append(html);
									$(".preTab i").attr("class","iconfont_nav "+$("[name='fdIcon']").val());
									$(".preTab i").css({"font-size":iconSize+"px"});
								}else{
									$(".lastTab").append(html);
									$(".lastTab i").attr("class","iconfont_nav "+$("[name='fdIcon']").val());
									$(".lastTab i").css({"font-size":iconSize+"px"});
								}
							}
						}

						//更新预览
						topic.subscribe("modeling.SpectrumColorPicker.change",function(obj){
							window.updatePreview();
						});

						topic.subscribe("modeling.SpectrumColorPicker.init",function(obj){
							window.updatePreview();
						})

						 window.displayCssSetContainerInstance = new displayCssSetContainer.DisplayCssSetContainer({
                              "container": $(".display_css_content"),
                             "type": displayType,
							 "showMode":showMode
                         });

						window.DisplayDialogInstance = new DisplayDialog.DisplayDialog({
							"$tmpEle": $(".view_field_where_div"),
							"viewContainer": $(".model-table-right"),
							"$cssEle": $(".display_css_content"),
							"selected": selected,
							"text": text,
							"data":data,
							"displayType":displayType,
							"allField":allField,
							"modelDict":modelDict,
						});

						window.selectIcon = function () {
							var url = "/sys/modeling/base/resources/iconfont.jsp?type=module";
							dialog.iframe(url, "${lfn:message('sys-modeling-base:modeling.app.selectIcon')}", changeIcon, {
								width: 750,
								height: 500
							})
						}

						window.changeIcon = function (className) {
							if (className) {
								$("i.iconfont_nav").removeClass().addClass(className);
								//#155440 在列表视图添加在字体前显示图标的显示样式，在前台列表页面不显示图标
								$("input[name='fdIcon']").val(className);
							}
						}
						//添加标记
						window.IsAddTab = function(dom){
							var value = $(dom).closest(".lui-component").find("[name='addTab']").val();
							if(value == '1'){	//开关打开
								//添加标记展开和隐藏
								$(".tab_temp_html").css("display","block");
							}else{
								//清空标记
								$("[name='tabStyle'][value='0']").trigger("click");
								$("[name='showPosition'][value='0']").trigger("click");
								$("[name='tabContent']").val("");
								console.log($(".whereFieldValueSelect").find("option[value='12']"))
								$(".whereFieldValueSelect").val("12");
								$(".iconSelect").val("10");
								window.SpectrumColorPicker.setColor("fontColorTabSelectValue","#FF8000");
								window.SpectrumColorPicker.setColor("backgroundColorTabSelectValue","#ffffff");
								$(".appMenu_main_icon i").attr("class","iconfont_nav");
								$(".tab_temp_html").css("display","none");
								window.updatePreview();
							}
						}



					})

		}

		Com_AddEventListener(window,"load",function(){
			// “自定义内容”或“图标”切换
			$("input[name='tabStyle']").on("click",function(){
				if($(this).val() == "0"){
					$(".custom_content").css("display","block");
					$(".icon_content").css("display","none");
					window.updatePreview();
				}else{
					$(".custom_content").css("display","none");
					$(".icon_content").css("display","block");
					window.updatePreview();
				}
			})
			// "添加标记"开关,默认打开
			$("[name='addTab']").next().find("input[type='checkbox']").attr("checked","checked");
			$("[name='addTab']").val("1");
			// "查询规则"
			$(document).on("click","[name='fdDisplayWhereType']",function(){
				if($(this).val() == '2') {
					$(".view_field_where_div").css("display","none");
				}else{
					$(".view_field_where_div").css("display","block");
				}
			})
			
			//预览
			$(".iconSelect").change(function(){
				window.updatePreview();
			})
			$(".tabContent").bind('input propertychange', function() {
				window.updatePreview();
			});
			$(".whereFieldValueSelect").change(function(){
				window.updatePreview();
			})
			$("[name='showPosition']").change(function(){
				window.updatePreview();
			})
			
		})
    </script>
    </template:replace>
</template:include>
