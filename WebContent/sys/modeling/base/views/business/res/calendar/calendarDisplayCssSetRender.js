/**
 * 显示项样式设置生成器
 */
define(function(require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require('lui/topic');

    var lang = require("lang!sys-modeling-base");

    var CalendarDisplayCssSetRender = base.Component.extend({
        initProps : function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.showMode = cfg.showMode || "";
            this.renderContentField();
            this.renderContentPosition();
            if(this.showMode == "1"){
                this.renderMobileMonthPreview();
            }else if(this.showMode == "0"){
                this.renderMobileDayPreview();
            }else{
                this.renderPreview();
            }
        },

        renderContentField : function() {
            this.contentField = this.container.find(".display_css_content_property");
            var contentFieldHtml='<span>'+lang["relation.field"]+'：</span> ' +
                '       <div class="display_css_content_field"> ' +
                '        <div> ' +
                '         <div style="display:inline-block;" class="fontColorSelect"> ' +
                '          <span>'+lang["listview.font"]+'：</span> ' +
                '          <div class="colorColorDiv" name="font_color"> ' +
                '           <div data-lui-mark="colorColor"></div> ' +
                '          </div> ' +
                '         </div> ' +
                '         <div style="display:inline-block;" class="backgroundColorSelect"> ' +
                '          <span>'+lang["listview.background.color"]+'：</span> ' +
                '          <div class="colorColorDiv"  name="background_color"> ' +
                '           <div data-lui-mark="colorColor"></div> ' +
                '          </div> ' +
                '         </div> ' +
                '        </div> ' +
                '       </div>';
            this.contentField.html(contentFieldHtml);
        },

        renderContentPosition : function() {
            this.contentPosition = this.container.find(".display_css_content_position");
            this.contentPosition.html("");
        },

        renderPreview : function() {
            this.contengPreview = this.container.find(".displayCss-pre");
            var contentPreviewHtml = '<span>'+lang["sys.profile.modeling.preview"]+'</span><div class="model-mask-panel">' +
                '<ul class="calendar-table-content">'+
                '<li>' +
                '    <div class="model-source-table-day">31</div>' +
                '    <div class="model-source-table-day-lunar">廿一</div>' +
                '</li>'+
                '<li>' +
                ' <div class="model-source-data-day-content">' +
                '    <div class="model-source-table-day">1</div>' +
                '    <div class="model-source-table-day-lunar">廿二</div>' +
                '    <div class="model-source-table-day-line satisfy_condition_content"></div>' +
                '    <div class="model-source-table-day-title fieldItem statify satisfy_condition_title">'+lang["calendar.display.field"]+'</div>' +
                ' </div>' +
                '</li>'+
                '<li>' +
                '    <div class="model-source-table-day">2</div>' +
                '    <div class="model-source-table-day-lunar">廿三</div>' +
                '</li>'+
                '</ul>'
                '</div>';


            this.contengPreview.html(contentPreviewHtml);
        },

        renderMobileMonthPreview : function() {
            this.contengPreview = this.container.find(".displayCss-pre");
            var contentPreviewHtml = '<span>'+lang["sys.profile.modeling.preview"]+'</span><div class="model-mask-panel">' +
                '<ul class="calendar-table-year-content">'+
                '<li>' +
                ' <div class="model-source-year-day-content">' +
                '    <div class="model-source-table-year-day">30</div>' +
                '    <div class="model-source-table-year-lunar">十九</div>' +
                ' </div>' +
                '</li>'+
                '<li>' +
                ' <div class="model-source-year-day-content">' +
                '    <div class="model-source-table-year-day">31</div>' +
                '    <div class="model-source-table-year-lunar">二十</div>' +
                ' </div>' +
                '</li>'+
                '<li>' +
                ' <div class="model-source-year-day-content">' +
                '    <div class="model-source-table-year-day">1</div>' +
                '    <div class="model-source-table-year-lunar">廿一</div>' +
                ' </div>' +
                '</li>'+
                '<li>' +
                ' <div class="model-source-year-day-content">' +
                '    <div class="model-source-table-year-day">2</div>' +
                '    <div class="model-source-table-year-lunar">廿二</div>' +
                '    <div class="model-source-table-year-day-title fieldItem statify satisfy_condition_content">'+lang["calendar.display.field"]+'</div>' +
                ' </div>' +
                '</li>'+
                '<li>' +
                ' <div class="model-source-year-day-content">' +
                '    <div class="model-source-table-year-day">3</div>' +
                '    <div class="model-source-table-year-lunar">廿三</div>' +
                ' </div>' +
                '</li>'+
                '<li>' +
                ' <div class="model-source-year-day-content">' +
                '    <div class="model-source-table-year-day">4</div>' +
                '    <div class="model-source-table-year-lunar">廿四</div>' +
                ' </div>' +
                '</li>'+
                '<li>' +
                ' <div class="model-source-year-day-content">' +
                '    <div class="model-source-table-year-day">5</div>' +
                '    <div class="model-source-table-year-lunar">廿五</div>' +
                ' </div>' +
                '</li>'+
                '</ul>'
            '</div>';


            this.contengPreview.html(contentPreviewHtml);
        },

        renderMobileDayPreview : function() {
            this.contengPreview = this.container.find(".displayCss-pre");
            var contentPreviewHtml = '<span>'+lang["sys.profile.modeling.preview"]+'</span><div class="model-mask-panel">' +
                '<ul class="calendar-table-day-content">'+
                '<li>' +
                ' <div class="model-day-display-css-content">' +
                '    <div class="model-day-display-css-top">' +
                '       <div class="model-day-display-css-line satisfy_condition_content"></div>' +
                '       <div class="model-day-display-css-subject fieldItem statify">'+lang["calendar.display.field"]+'</div>' +
                '    </div>' +
                '    <div class="model-day-display-css-left">' +
                '       <ul class="model-day-display-css-table">' +
                '           <li>' +
                '               <div class="model-day-display-css-title">'+lang["calendar.time"]+'</div>' +
                '               <div class="model-day-display-css-summary">'+lang["calendar.summary.content"]+'</div>' +
                '           </li>'+
                '           <li>' +
                '               <div class="model-day-display-css-title">'+lang["calendar.content.csummary'"]+'</div>' +
                '               <div class="model-day-display-css-summary">'+lang["calendar.summary.content"]+'</div>' +
                '           </li>'+
                '           <li>' +
                '               <div class="model-day-display-css-title">'+lang["calendar.initiator"]+'</div>' +
                '               <div class="model-day-display-css-summary">'+lang["calendar.summary.content"]+'</div>' +
                '           </li>'+
                '           <li>' +
                '               <div class="model-day-display-css-title">'+lang["calendar.participants"]+'</div>' +
                '               <div class="model-day-display-css-summary">'+lang["calendar.summary.content"]+'</div>' +
                '           </li>'+
                '           <li>' +
                '               <div class="model-day-display-css-title">'+lang["calendar.place"]+'</div>' +
                '               <div class="model-day-display-css-summary">'+lang["calendar.summary.content"]+'</div>' +
                '           </li>'+
                '       </ul>' +
                '    </div>' +
                '    <div class="model-day-display-css-right">' +
                '       <i class="model-day-display-css-link"></i>' +
                '    </div>' +
                ' </div>' +
                '</li>'+
                '</ul>'
            '</div>';


            this.contengPreview.html(contentPreviewHtml);
        },

        getKeyData : function() {
            // 显示项样式对象
            var keyData = {};
            //摘要项
            keyData.field.fontColor = $("[name='font_color']").find("input[type='hidden']").val();
            keyData.field.backgroundColor = $("[name='background_color']").find("input[type='hidden']").val();

            //所在卡片
            keyData.fieldItem.ItemBackgroundColor= $("[name='background_color_item']").find("input[type='hidden']").val();
            return keyData;
        }
    });

    exports.CalendarDisplayCssSetRender = CalendarDisplayCssSetRender;
});
