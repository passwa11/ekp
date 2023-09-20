/**
 * 显示项样式设置生成器
 */
define(function(require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require('lui/topic');

    var DisplayCssSetBoard = base.Component.extend({
        initProps : function($super, cfg) {
            $super(cfg);
            this.container = cfg.container || null;
            this.renderContentField();
            this.renderContentPosition();
            this.renderPreview();
        },

        renderContentField : function() {
            this.contentField = this.container.find(".display_css_content_property");
            var contentFieldHtml='<span>摘要项：</span> ' +
                '       <div class="display_css_content_field"> ' +
                '        <div> ' +
                '         <div style="display:inline-block;" class="fontColorSelect"> ' +
                '          <span>字体：</span> ' +
                '          <div class="colorColorDiv" name="font_color"> ' +
                '           <div data-lui-mark="colorColor"></div> ' +
                '          </div> ' +
                '         </div> ' +
                '         <div style="display:inline-block;" class="backgroundColorSelect"> ' +
                '          <span>底色：</span> ' +
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
            var contentPositionHtml='<span class="font-position">所在卡片： ' +
                '               <span class="font-position-hover">可设置满足条件的所在卡片的显示样式</span></span> ' +
                '               <div class="font-position-content"> ' +
                '                <div style="display:inline-block;" class="backgroundColorItemSelect" > ' +
                '                 <span>底色：</span> ' +
                '                 <div name="background_color_item" class="colorColorDiv"> ' +
                '                <div data-lui-mark="colorColor"></div> ' +
                '               </div> ' +
                '                </div> ' +
                '       </div>';
            this.contentPosition.html(contentPositionHtml);
        },

        renderPreview : function() {
            this.contengPreview = this.container.find(".displayCss-pre");
            var contentPreviewHtml = '<span>预览</span><div class="model-mask-panel">' +
                '<div class="cardClassifyContent">' +
                '            <div class="cardClassifyHeader clearfix">' +
                '                <div class="cardClassifyHeaderText">分类</div>' +
                '                <div class="cardClassifyHeaderCount">2</div>' +
                '                <span class="cardClassifyHeaderBtn"></span>' +
                '            </div>' +
                '            <div class="cardClassifyDetails fieldItem">' +
                '                <div class="cardClassifyDetailsCover fieldItem"></div>' +
                '                <div class="cardClassifyDetailsText fieldItem">' +
                '                    <div class="cardClassifyDetailsTitle panelShowText">' +
                '                        标题' +
                '                    </div>' +
                '                    <div class="cardClassifyAbstract">' +
                '                        <div class="cardClassifyAbstractTitle">' +
                '                            摘要项' +
                '                        </div>' +
                '                        <div class="cardClassifyAbstractContent statify satisfy_condition_content">' +
                '                            内容' +
                '                        </div>' +
                '                    </div>' +
                '                    <div class="cardClassifyAbstract">' +
                '                        <div class="cardClassifyAbstractTitle">' +
                '                            其他摘要项' +
                '                        </div>' +
                '                        <div class="cardClassifyAbstractContent">' +
                '                            不满足条件内容' +
                '                        </div>' +
                '                    </div>' +
                '                </div>' +
                '                ' +
                '            </div>' +
                '        </div>' +
                '</div>'
            this.contengPreview.html(contentPreviewHtml);
        },

        getKeyData : function() {
            // 显示项样式对象
            var keyData = {};
            keyData.field={};
            //摘要项
            keyData.field.fontColor = $("[name='font_color']").find("input[type='hidden']").val();
            keyData.field.backgroundColor = $("[name='background_color']").find("input[type='hidden']").val();
            keyData.field.tab={};
            keyData.fieldItem={};
            //所在卡片
            keyData.fieldItem.ItemBackgroundColor= $("[name='background_color_item']").find("input[type='hidden']").val();

            return keyData;
        }
    });

    exports.DisplayCssSetBoard = DisplayCssSetBoard;
});
