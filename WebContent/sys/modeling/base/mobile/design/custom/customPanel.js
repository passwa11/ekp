/**
 *
 * 部件选择面板
 */
define(function(require, exports, module) {

	var $ = require("lui/jquery");
	var base = require("lui/base");
	var topic = require("lui/topic");
	var dialog = require("lui/dialog");
	var lang = require("lang!");
	var modelingLang = require("lang!sys-modeling-base");

	var textPortlet = require('sys/modeling/base/mobile/design/custom/portlets/textPortlet');
	var imagePortlet = require('sys/modeling/base/mobile/design/custom/portlets/imagePortlet');
	var chartPortlet = require('sys/modeling/base/mobile/design/custom/portlets/chartPortlet');
	var listViewWithCountPortlet = require('sys/modeling/base/mobile/design/custom/portlets/listViewWithCountPortlet');
	var shortcutPortlet = require('sys/modeling/base/mobile/design/custom/portlets/shortcutPortlet');
	var todoPortlet = require('sys/modeling/base/mobile/design/custom/portlets/todoPortlet');
	var listViewWithIconPortlet = require('sys/modeling/base/mobile/design/custom/portlets/listViewWithIconPortlet');
	var listViewWithMultiTabPortlet = require('sys/modeling/base/mobile/design/custom/portlets/listViewWithMultiTabPortlet');
	var listViewWithMultiTabContentPortlet = require('sys/modeling/base/mobile/design/custom/portlets/listViewWithMultiTabContentPortlet');

	var CustomPanel = base.Component.extend({

		initProps: function($super,cfg) {
			$super(cfg);
			this.wgtPortlet = null;	// 记录当前绑定的wgt
			this.parent = cfg.parent;
			this.customValidation = $GetKMSSDefaultValidation(null,{
				msgInsertPoint:function(element) {
					var $optInfo = $(element).closest('.content_item_form_element');
					if($optInfo.length > 0){
						return $optInfo[0];
					}else{
						return $(element).closest('td')[0];
					}
				}
			});
			this.customValidation._doValidateElement = function(oElement, validator) {
				var _elm = oElement.element;
				var _reminder_opt = {where: this.options.msgShowWhere, insertPoint: this.options.msgInsertPoint,clickFun:this.options.onElementClickFocus};
				if(_elm.getAttribute("data-type") === "hidden"){
					var _isNotPass = false;
				}else{
					var _isNotPass = (oElement.isEnable() || _elm.getAttribute("data-type") === "validate") && !validator.test(oElement.getValue(), _elm);
				}
				var _reminder = new Reminder(_elm, this.options.getLang(validator), validator.options, _reminder_opt);
				switch(this.options.msgShowType) {
					case 'alert':
						if (_isNotPass) _reminder.alert();
						break;
					default:
						(_isNotPass) ? _reminder.show() : _reminder.hide();
						break;
				}
				// 手动渲染table表格
				Elements_redraw(_elm);
				return !_isNotPass;
			};
			this.customValidation.addValidator("arrayRequired",modelingLang["modeling.errors.required"],function(val){
				if(val === "[]"){
					return false;
				}
				return true;
			});
		},

		startup : function($super,cfg){
			$super(cfg);
		},

	    draw : function(){
	    	var self = this;
	    	var $leftBox = self.getLeftBoxHtml();
			var $demoContainerHtml = self.getDemoContainerHtml();
			this.element = $(".modelAppSpaceBox");
	    	this.element.append($leftBox).append($demoContainerHtml);
	    	this.calcWidth();
			this.bindEvent();
	    },

		calcWidth:function() {
			var docWidth = window.screen.availWidth;
			var docHeight = window.screen.availHeight;
			//设置最外层容器高度
			$(".modelAppSpaceBox").css("height", "100%");
			$(".modelAppSpaceBox").css("min-height", docHeight-204);
			$(".modelAppSpaceWidgetLeftBoxContentPanel").css("height", docHeight-180);
			$(".modelAppSpaceWidgetDemoContainer").css("height", $(".modelAppSpaceBox").height() -55);
			$(".modelAppSpaceWidgetDemoContent").css("height", $(".modelAppSpaceWidgetDemoContainer").height() -120);
			$(".modelAppSpaceMask").css("width",docWidth).css("height",docHeight);
			$("#mindMapEdit").css("height",docHeight-150);
		},

		getLeftBoxHtml:function() {
			var $box = '<div class="modelAppSpaceWidgetLeftBox" style="display: none"><i></i>\n' +
				'            <div class="modelAppSpaceWidgetLeftBoxSwitch">\n' +
				'                <ul class="modelAppSpaceWidgetLeftBoxSwitchList">\n' +
				'                    <li class="active" title="'+modelingLang["modeling.portlet.type.basic"]+'">'+modelingLang["modeling.portlet.type.basic"]+'</li>\n' +
				'                    <li title="'+modelingLang["modeling.portlet.type.list"]+'">'+modelingLang["modeling.portlet.type.list"]+'</li>\n' +
				'                    <li title="'+modelingLang["modeling.portlet.type.shortcut"]+'">'+modelingLang["modeling.portlet.type.shortcut"]+'</li>\n' +
				'                    <li title="'+modelingLang["modeling.portlet.type.calendar"]+'">'+modelingLang["modeling.portlet.type.calendar"]+'</li>\n' +
				'                </ul>\n' +
				'            </div>\n' +
				'            <div class="modelAppSpaceWidgetLeftBoxContent">\n' +

				'<div class="modelAppSpaceWidgetLeftBoxContentHeader">\n' +
				// '            <i></i>\n' +
				'        </div>'+
				'                <div class="modelAppSpaceWidgetLeftBoxContentPanel">\n' +
				'                    <div class="modelAppSpaceWidgetPanelTitles">\n' +
				'                        <strong>'+modelingLang["modeling.portlet.type.basic.title"]+'</strong>\n' +
				'                        <span>'+modelingLang["modeling.portlet.type.basic.desc"]+'</span>\n' +
				'                        <ul class="modelAppSpaceWidgetPanelList">\n' +
				'                            <li data-type="text" class="text">\n' +
				'                                <i></i>\n' +
				'                                <span>'+modelingLang["modeling.portlet.type.basic.text"]+'<div>'+modelingLang["modeling.portlet.type.basic.text"]+'</div></span>\n' +
				'                            </li>\n' +
				'                            <li data-type="chart" class="chart">\n' +
				'                                <i></i>\n' +
				'                                <span>'+modelingLang["modeling.portlet.type.basic.chart"]+'<div>'+modelingLang["modeling.portlet.type.basic.chart"]+'</div></span>\n' +
				'                            </li>\n' +
				'                            <li data-type="pic" class="pic">\n' +
				'                                <i></i>\n' +
				'                                <span>'+modelingLang["modeling.portlet.type.basic.pic"]+'<div>'+modelingLang["modeling.portlet.type.basic.pic"]+'</div></span>\n' +
				'                            </li>\n' +
				'                        </ul>\n' +
				'                    </div>\n' +
				'                    <div class="modelAppSpaceWidgetPanelTitles">\n' +
				'                        <strong>'+modelingLang["modeling.portlet.type.list.title"]+'</strong>\n' +
				'                        <span>'+modelingLang["modeling.portlet.type.list.desc"]+'</span>\n' +
				'                        <div class="modelAppSpaceWidgetListWidget clearfix">\n' +
				'                            <ul class="modelAppSpaceWidgetListPanel">\n' +
				// '                                <li data-type="listViewWithMultiTab">\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetListWidgetContent_03">\n' +
				// '                                    </div>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				// '                                        '+modelingLang["modeling.portlet.type.list.listViewWithMultiTab"]+'\n' +
				// '                                    </div>\n' +
				// '                                </li>\n' +
				'                                <li data-type="listViewWithMultiTabContent">\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetListWidgetContent_04">\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				'                                         <div class="modelAppSpaceWidgetListWidgetTip">' +
														modelingLang["modeling.portlet.type.list.listViewWithMultiTabContent"] +
				'                                    	</div><span><div>'+modelingLang["modeling.portlet.type.list.listViewWithMultiTabContent"]+'</div></span>\n' +
				'                                    </div>\n' +
				'                                </li>\n' +
				// '                                <li>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetListWidgetContent_05">\n' +
				// '                                    </div>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				// '                                        左文右图列表\n' +
				// '                                    </div>\n' +
				// '                                </li>\n' +
				'                            </ul>\n' +
				'                        </div>\n' +
				'                    </div>\n' +
				'                    <div class="modelAppSpaceWidgetPanelTitles">\n' +
				'                        <strong>'+modelingLang["modeling.portlet.type.shortcut.title"]+'</strong>\n' +
				'                        <span>'+modelingLang["modeling.portlet.type.shortcut.desc"]+'</span>\n' +
				'                        <div class="modelAppSpaceWidgetListWidget clearfix">\n' +
				'                            <ul class="modelAppSpaceWidgetListPanel">\n' +
				// '                                <li data-type="shortcutOneRowSlide" >\n' +
				'                                <li data-type="listViewWithCount">\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetListWidgetContent_01">\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				'                                        <div class="modelAppSpaceWidgetListWidgetTip">'+modelingLang["modeling.portlet.type.shortcut.listViewWithCount"]+'</div>\n' +
				'                                    	<span><div>'+modelingLang["modeling.portlet.type.shortcut.listViewWithCount"]+'</div></span>\n' +
				'                                    </div>\n' +
				'                                </li>\n' +
				'                                <li data-type="listViewWithIcon">\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetListWidgetContent_02">\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				'                                        <div class="modelAppSpaceWidgetListWidgetTip">'+modelingLang["modeling.portlet.type.shortcut.listViewWithIcon"]+'</div>\n' +
				'                                    	<span><div>'+modelingLang["modeling.portlet.type.shortcut.listViewWithIcon"]+'</div></span>\n' +
				'                                    </div>\n' +
				'                                </li>\n' +
				'                                <li data-type="shortcutOneRow" >\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetNavWidgetContent_01">\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				'                                        <div class="modelAppSpaceWidgetListWidgetTip">'+modelingLang["modeling.portlet.type.shortcut.shortcutOneRow"]+'</div>\n' +
				'                                    	<span><div>'+modelingLang["modeling.portlet.type.shortcut.shortcutOneRow"]+'</div></span>\n' +
				'                                    </div>\n' +
				'                                </li>\n' +
				'                                <li data-type="shortcutDoubleRow">\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetNavWidgetContent_02">\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				'                                        <div class="modelAppSpaceWidgetListWidgetTip">'+modelingLang["modeling.portlet.type.shortcut.shortcutDoubleRow"]+'</div>\n' +
				'                                    	<span><div>'+modelingLang["modeling.portlet.type.shortcut.shortcutDoubleRow"]+'</div></span>\n' +
				'                                    </div>\n' +
				'                                </li>\n' +
				'                                <li data-type="shortcutSlideIcon">\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetNavWidgetContent_03">\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				'                                        <div class="modelAppSpaceWidgetListWidgetTip">'+modelingLang["modeling.portlet.type.shortcut.shortcutSlideIcon"]+'</div>\n' +
				'                                    	<span><div>'+modelingLang["modeling.portlet.type.shortcut.shortcutSlideIcon"]+'</div></span>\n' +
				'                                    </div>\n' +
				'                                </li>\n' +
				// '                                <li>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetNavWidgetContent_04">\n' +
				// '                                    </div>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				// '                                        单个快捷\n' +
				// '                                    </div>\n' +
				// '                                </li>\n' +
				// '                                <li>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetNavWidgetContent_05">\n' +
				// '                                    </div>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				// '                                        宫格快捷\n' +
				// '                                    </div>\n' +
				// '                                </li>\n' +
				'                            </ul>\n' +
				'                        </div>\n' +
				'                    </div>\n' +
				'                    <div class="modelAppSpaceWidgetPanelTitles">\n' +
				'                        <strong>'+modelingLang["modeling.portlet.type.calendar.title"]+'</strong>\n' +
				'                        <span>'+modelingLang["modeling.portlet.type.calendar.desc"]+'</span>\n' +
				'                        <div class="modelAppSpaceWidgetListWidget clearfix">\n' +
				'                            <ul class="modelAppSpaceWidgetListPanel">\n' +
				'                                <li data-type="todo">\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetCalWidgetContent_01">\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				'                                        <div class="modelAppSpaceWidgetListWidgetTip">'+modelingLang["modeling.portlet.type.calendar.todo"]+'</div>\n' +
				'                                    	<span><div>'+modelingLang["modeling.portlet.type.calendar.todo"]+'</div></span>\n' +
				'                                    </div>\n' +
				'                                </li>\n' +
				// '                                <li>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetContent modelAppSpaceWidgetCalWidgetContent_02">\n' +
				// '                                    </div>\n' +
				// '                                    <div class="modelAppSpaceWidgetListWidgetInfo">\n' +
				// '                                        日历待办\n' +
				// '                                    </div>\n' +
				// '                                </li>\n' +
				'\n' +
				'                            </ul>\n' +
				'                        </div>\n' +
				'                    </div>\n' +
				'                </div>\n' +
				'            </div>\n' +
				'        </div>';
			return $box;
		},

		getDemoContainerHtml:function() {
		  	var containerHtml = '<div class="modelAppSpaceWidgetDemoContainerBox"><div class="modelAppSpaceWidgetDemoContainer"><div class="modelAppSpaceWidgetDemo">\n' +
				'                        <div class="modelAppSpaceWidgetDemoBar">\n' +
				'                            <div class="modelAppSpaceWidgetDemoBarBg"></div>\n' +
				'                        </div>\n' +
				'                        <div class="modelAppSpaceWidgetDemoTitle">\n' +
				'                             <div class="modelAppSpaceWidgetDemoBack"><i></i>'+modelingLang['sys.profile.modeling.homePage.back']+'</div>' +
				'                            <div class="modelAppSpaceWidgetDemoTitleContent">'+modelingLang["modeling.untitled.mobile.home"]+'</div>\n' +
				'                             <div class="modelAppSpaceWidgetDemoMore">'+modelingLang['modeling.more']+'</div>' +
				'                        </div>\n' +
				'                        <div class="modelAppSpaceWidgetDemoContent" id="nested">\n' +
				'                        <div class="modelAppSpaceWidgetDemoBackground">\n' +
				'                        </div>\n' +
				'                            <div class="modelAppSpaceWidgetDemoModelBtn">\n' +
				'                                <i></i><span>'+modelingLang["modeling.add.portlet"]+'</span>\n' +
				'                            </div>\n' +
				'                        </div>\n' +
				'                    </div></div></div>';
		  	return containerHtml;
		},

		chartHtml:function (){
			return '<div class="modelAppSpaceWidgetDemoModel"><span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle">标题</div><div class="modelAppSpaceWidgetDemoTypeCover"></div></div>';
		},

		textHtml:function (){
			return '<div class="modelAppSpaceWidgetDemoModel"><span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle">标题</div><div class="modelAppSpaceWidgetDemoTypeText">文本</div></div>';
		},

		picHtml:function (){
			return '<div class="modelAppSpaceWidgetDemoModel"><span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle"></div><div class="modelAppSpaceWidgetDemoTypeImg"></div></div>';
		},

		todoHtml:function (){
			return '<div class="modelAppSpaceWidgetDemoModel"><span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle"></div><div class="modelAppSpaceWidgetDemoTypeTodo">\n' +
				'                                    <i class="head_notify_icon"></i>\n' +
				'                                    <strong>'+modelingLang["modeling.processes.todo"]+'</strong>\n' +
				'                                    <span class="modelAppSpaceWidgetDemoIconLink"></span>\n' +
				'                                </div></div>';
		},

		/*带图标红色标记计数列表*/
		listViewWithCountHtml:function (){
			return '<div class="modelAppSpaceWidgetDemoModel modelAppSpaceWidgetListViewWithCount"><span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle"></div><div class="modelAppSpaceWidgetDemoTypeEnter">\n' +
				'                                    <i></i>\n' +
				'                                    <strong>快捷入口1</strong>\n' +
				'                                    <span class="modelAppSpaceWidgetDemoIconLinkN"></span>\n' +
				'                                    <span class="modelAppSpaceWidgetDemoNum">99+</span>\n' +
				'                                </div></div>';
		},

		/*带图标多个计数列表*/
		listViewWithIconHtml:function (){
			return '<div class="modelAppSpaceWidgetDemoModel modelAppSpaceWidgetListViewWithIcon"><span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle"></div><div class="modelAppSpaceWidgetDemoTypeEnter">\n' +
				'                                    <i></i>\n' +
				'                                    <strong>快捷入口1</strong>\n' +
				'                                    <span class="modelAppSpaceWidgetDemoIconLinkN"></span>\n' +
				'                                </div></div>';
		},
		/*多页纯文本列表*/
		listViewWithMultiTabContentHtml:function (){
			return '<div class="modelAppSpaceWidgetDemoModel">\n' +
				'                            <span class="del">'+lang["button.delete"]+'</span>\n' +
				'                            <div class="modelAppSpaceWidgetDemoTypeSignBox clearfix">\n' +
				'                                <div class="modelAppSpaceWidgetDemoTypeSignTitle clearfix">\n' +
				'                                    <span>最新签报</span>\n' +
				'                                    <ul>\n' +
				'                                        <li class="active">签报</li>\n' +
				'                                        <li>发文</li>\n' +
				'                                        <li>收文</li>\n' +
				'                                    </ul>\n' +
				'                                </div>\n' +
				'                                <div class="modelAppSpaceWidgetDemoTypeSignContent">\n' +
				'                                    <div class="modelAppSpaceWidgetDemoTypeSignContentItem">\n' +
				'                                        <ul>\n' +
				'                                            <li>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemTitle">\n' +
				'                                                    2019公司年中财务报告\n' +
				'                                                </div>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemInfo">\n' +
				'                                                    <i>2019-09-02</i>\n' +
				'                                                    <span>32观看</span>\n' +
				'                                                </div>\n' +
				'                                            </li>\n' +
				'                                            <li>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemTitle">\n' +
				'                                                    2019公司年中财务报告2019公司年中财务报告公司年中财务报告\n' +
				'                                                </div>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemInfo">\n' +
				'                                                    <i>2019-09-02</i>\n' +
				'                                                    <span>32观看</span>\n' +
				'                                                </div>\n' +
				'                                            </li>\n' +
				'                                            <li>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemTitle">\n' +
				'                                                    2019公司年中财务报告2019公司年中财务报告公司年中财务报告\n' +
				'                                                </div>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemInfo">\n' +
				'                                                    <i>2019-09-02</i>\n' +
				'                                                    <span>32观看</span>\n' +
				'                                                </div>\n' +
				'                                            </li>\n' +
				'                                        </ul>\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetDemoTypeSignContentItem">\n' +
				'                                        <ul>\n' +
				'                                            <li>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemTitle">\n' +
				'                                                    2019公司年中财务报告\n' +
				'                                                </div>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemInfo">\n' +
				'                                                    <i>2019-09-02</i>\n' +
				'                                                    <span>32观看</span>\n' +
				'                                                </div>\n' +
				'                                            </li>\n' +
				'                                        </ul>\n' +
				'                                    </div>\n' +
				'                                    <div class="modelAppSpaceWidgetDemoTypeSignContentItem">\n' +
				'                                        <ul>\n' +
				'                                            <li>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemTitle">\n' +
				'                                                    2019公司年中财务报告\n' +
				'                                                </div>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemInfo">\n' +
				'                                                    <i>2019-09-02</i>\n' +
				'                                                    <span>32观看</span>\n' +
				'                                                </div>\n' +
				'                                            </li>\n' +
				'                                            <li>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemTitle">\n' +
				'                                                    2019公司年中财务报告2019公司年中财务报告公司年中财务报告\n' +
				'                                                </div>\n' +
				'                                                <div class="modelAppSpaceWidgetDemoTypeSignItemInfo">\n' +
				'                                                    <i>2019-09-02</i>\n' +
				'                                                    <span>32观看</span>\n' +
				'                                                </div>\n' +
				'                                            </li>\n' +
				'                                        </ul>\n' +
				'                                    </div>\n' +
				'                                </div>\n' +
				'                                <div class="modelAppSpaceWidgetDemoTypeSignMore">\n' +
				'                                    <span>\n' +
				'                                        <i></i>\n' +
				'                                        '+modelingLang["modeling.see.more"]+'\n' +
				'                                    </span>\n' +
				'                                </div>\n' +
				'\n' +
				'                            </div>\n' +
				'                        </div>';
		},
		/*多页带标签列表*/
		listViewWithMultiTabHtml:function (){
			return '<div class="modelAppSpaceWidgetDemoModel"><span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeEnter">\n' +
				'                                    <i></i>\n' +
				'                                    <strong>快捷入口1</strong>\n' +
				'                                    <span class="modelAppSpaceWidgetDemoIconLinkN"></span>\n' +
				'                                </div></div>';
		},
		shortcutOneRowHtml:function(){
			return '<div class="modelAppSpaceWidgetDemoModel modelAppSpaceWidgetShortcutWithIcon mportal-content">' +
				'<span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle"></div>' +
				'<div class="modelingAppSpaceShortcutNumberDiv">\n' +
				'<div class="mportalList-statistics mobileBlock">\n' +
				'<div class="swiper-container mportalList-swiper">\n' +
				'<div class="swiper-wrapper"></div>\n' +
				'</div>\n' +
				'<div class="slide-pagination">\n' +
				'<div class="mportalList-pagination pagination"></div>\n' +
				'</div>\n' +
				'</div></div></div>';
		},
		shortcutDoubleRowHtml:function(){
			return '<div class="modelAppSpaceWidgetDemoModel modelAppSpaceWidgetShortcutWithIcon mportal-content">' +
				'<span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle"></div>' +
				'<div class="modelingAppSpaceShortcutNumberDiv">\n' +
				'<div class="mportal-statistics mobileBlock"></div></div></div>';
		},
		shortcutSlideIconHtml:function(){
			return '<div class="modelAppSpaceWidgetDemoModel modelAppSpaceWidgetShortcutWithIcon mportal-content">' +
				'<span class="del">'+lang["button.delete"]+'</span><div class="modelAppSpaceWidgetDemoTypeTitle"></div>' +
				'<div class="modelingAppSpaceShortcutNumberDiv">\n' +
				'<div class="mportal-iconArea mobileBlock">\n' +
				'<div class="swiper-container mportal-swiper">\n' +
				'<div class="swiper-wrapper"></div>\n' +
				'</div>\n' +
				'<div class="slide-pagination">\n' +
				'<div class="mportal-pagination pagination"></div>\n' +
				'</div>\n' +
				'</div></div></div>';
		},
		bindEvent:function (){
			var self = this;
			$(".modelAppSpacePopContentTemplateBoxLeft>ul>li").click(function(){
				if(!$(this).hasClass("active")){
					$(this).addClass("active").siblings().removeClass("active")
				}
				var idx = $(this).index();
				$(".modelAppSpacePopCOntentTemplateBoxRight").eq(idx).show().siblings().hide()
			})
			// 部件新增
			this.element.find(".modelAppSpaceWidgetListPanel>li,.modelAppSpaceWidgetPanelList>li").click(function(){
				if(!$(this).hasClass("active")){
					$(this).addClass("active").siblings().removeClass("active");
				}
				var type = $(this).data("type");
				$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").siblings().removeClass("selected");
				self.createViewPortlet(type,true);
				$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").find("span").text(modelingLang["modeling.add.portlet"]);
				$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").find("i").show();
			})

			$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").click(function (e){
				e.stopPropagation();
				if(self.wgtPortlet && !self.wgtPortlet.validate()){
					dialog.alert(modelingLang["modeling.portlet.check.not.pass"]);
					self.wgtPortlet.triggerActiveWgt();
					return;
				}
				self.leftBoxShow();
				$(this).find("span").text(modelingLang["modeling.please.select.portlet"]);
				$(this).find("i").hide();
				// $(this).siblings().removeClass("selected");
			})

			$(".modelAppSpaceWidgetLeftBox>i").click(function() {
				$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").find("span").text(modelingLang["modeling.add.portlet"]);
				$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").find("i").show();
				self.leftBoxHide();
			});

			// 锚点导航
			$('.modelAppSpaceWidgetLeftBoxSwitchList>li').click(function(e){
				if(!$(this).hasClass('active')){
					$(this).addClass('active').siblings().removeClass('active');
				}
				var idx=$(this).index();
				var targetLoc=$('.modelAppSpaceWidgetLeftBoxContentPanel>.modelAppSpaceWidgetPanelTitles').eq(idx).offset().top;
				$('.modelAppSpaceWidgetLeftBoxContentPanel').animate({scrollTop: targetLoc},300);
			});
			// 拖动列表
			var nest=document.getElementById('nested');
			nest=new Sortable(nest,{
				animation: 300,
				draggable:"div.modelAppSpaceWidgetDemoModel",
				dragClass: "droping",
				ghostClass:'dropingReplace',
				forceFallback: true, //拖拽时元素透明度
				emptyInsertThreshold: 5,
				direction: 'horizontal',
				filter:".modelingAppSpaceShortcutNumberDiv"
			});
			self.element.find(".modelAppSpaceWidgetLeftBox").on("click",function (e){
				e.stopPropagation();
			})
			// 点击外部或者按下ESC后列表隐藏
			$(document).click(function(){
				if(self.element.find(".modelAppSpaceWidgetLeftBox").css("display") === "flex"){
					self.leftBoxHide();
				}
			}).keyup(function(e){
				var key =  e.which || e.keyCode;;
				if(key == 27){
					if(self.element.find(".modelAppSpaceWidgetLeftBox").css("display") === "flex"){
						self.leftBoxHide();
					}
				}
			});
		},

		createViewPortlet : function(fdType,isActive,storeData){
			var self = this;
			var viewWgt = this.doInitChild(fdType,isActive,storeData);
			if(viewWgt == null){
				return;
			}
			var $wgtHtml = $(self[fdType+"Html"]());
			$wgtHtml.attr("data-id",viewWgt.randomName);
			$wgtHtml.find("span.del").click(function (){
				$.each(self.children,function(i,item){
					if(item === viewWgt){
						self.children.splice(i, 1);
						viewWgt.destroy();
						$wgtHtml.remove();
						$(".panel-portlet-header").show();
						return;
					}
				})
			});
			$wgtHtml.click(function() {
				if(!$(this).hasClass("selected")){
					if(self.wgtPortlet && !self.wgtPortlet.validate()){
						dialog.alert(modelingLang["modeling.portlet.check.not.pass"]);
						self.wgtPortlet.triggerActiveWgt();
						return;
					}
					for(var i = 0; i < self.children.length; i++){
						var wgt = self.children[i];
						if(wgt === viewWgt){
							self.wgtPortlet=wgt;
							self.wgtPortlet.triggerActiveWgt();
							$(this).addClass("selected").siblings().removeClass("selected");
							break;
						}
					}
					$(".panel-portlet-header").hide();
				}
				self.leftBoxHide();
			});
			$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").before($wgtHtml);
			viewWgt.draw();
			if(isActive === true){
				$wgtHtml.addClass("selected");
			}
			self.leftBoxHide();
			return viewWgt;
		},

		leftBoxHide:function (){
			$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").find("span").text(modelingLang["modeling.add.portlet"]);
			$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").find("i").show();
			this.element.find(".modelAppSpaceWidgetLeftBox").hide();
			$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").removeClass("active");
			//隐藏背景色弹框
			$(".sp-container").addClass("sp-hidden");
		},

		leftBoxShow:function() {
			this.element.find(".modelAppSpaceWidgetLeftBox").show();
			$(".modelAppSpaceWidgetDemoContainer .modelAppSpaceWidgetDemoModelBtn").addClass("active");
			//隐藏背景色弹框
			$(".sp-container").addClass("sp-hidden");
		},

		doInitChild : function(type, isActive,storeData){
			var claz = null;
			if(type === "text"){
				claz = textPortlet;
			}else if (type === "pic"){
				claz = imagePortlet;
			}else if (type === "chart"){
				claz = chartPortlet;
			}else if (type === "listViewWithCount"){
				claz = listViewWithCountPortlet;
			}else if(type.indexOf("shortcut") > -1 ){
				claz = shortcutPortlet;
			}else if(type == "todo"){
				claz = todoPortlet;
			}else if (type === "listViewWithIcon") {
				claz = listViewWithIconPortlet;
            }else if (type === "listViewWithMultiTab") {
				claz = listViewWithMultiTabPortlet;
			}else if (type === "listViewWithMultiTabContent") {
				claz = listViewWithMultiTabContentPortlet;
			}
			if(claz == null){
				return null;
			}
			var clazInstance = new claz({data:storeData,parent:this,type:type});
			clazInstance.startup();
			if(isActive === true){
				this.wgtPortlet = clazInstance;
			}

			this.addChild(clazInstance);
			return clazInstance;
		},

		validate:function (){
			var isPass= true;
			var self = this;
			$.each(this.children,function(i,item){
				if(!item.validate()){
					dialog.alert(modelingLang["modeling.portlet.check.not.pass"]);
					self.wgtPortlet = item;
					self.wgtPortlet.triggerActiveWgt();
					isPass = false;
					return false;
				}
			})
			return isPass;
		},

		getKeyData:function() {
		    var keyData = [];
		    var self = this;
		    $(".modelAppSpaceWidgetDemoContent").find(".modelAppSpaceWidgetDemoModel").each(function(idx) {
		    	var id = $(this).attr("data-id");
				$.each(self.children,function(i,item){
					if(item.randomName === id){
						var data = {};
						data.fdType = item.type;
						data.fdPortletConfig = item.getKeyData();
						data.fdOrder = idx;
						keyData.push(data);
					}
				})
			});

		    return keyData;
		},
		initByStoreData : function(storeData) {
			if (!storeData || JSON.stringify(storeData) == "[]") {
				return;
			}
			var self = this;
			if (storeData.length > 0) {
				for (var i = 0; i < storeData.length; i++) {
					var portletConfig = storeData[i];
				    self.createViewPortlet(portletConfig.fdType,false,portletConfig.fdPortletConfig);
				}
            }
		},
	});

	exports.CustomPanel = CustomPanel;
})