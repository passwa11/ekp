define(['dojo/_base/declare', "dojo/_base/lang", 'dojo/topic', "mui/i18n/i18n!sys-mobile:mui.button.cancel"],
    function (declare, lang, topic, cancelMsg) {
        return declare('km.calendar.mobile.resource.js.label.LabelCategorySelectionMixin', null, {
            startup: function () {
                this.inherited(arguments);
                // 修改按钮文字
                this.clearButtonNode.innerText = cancelMsg['mui.button.cancel'];
                this.clearButtonNode.innerHTML = this.clearButtonNode.innerText;
                // 监听点击事件
                this.connect(this.clearButtonNode, "click",
                    lang.hitch(this, function () {
                        // 发布关闭弹窗事件
                        topic.publish("km/calendar/LabelSelect/closeDialog");
                    })
                )
            }
        });
    });