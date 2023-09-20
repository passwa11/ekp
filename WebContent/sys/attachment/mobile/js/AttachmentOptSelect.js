define([
  "dojo/_base/declare",
  "mui/form/Select",
  "dojo/dom-construct",
  "sys/attachment/mobile/js/AttachmentOptDialog",
  "dojo/_base/lang",
  "dojo/topic",
  "dojo/_base/array"
], function(declare, Select, domConstruct, Dialog, lang, topic, array) {
  var _field = declare(
    "sys.attachment.mobile.js.AttachmentOptSelect",
    [Select],
    {
      pop: false,

      renderListItem: function() {
        array.forEach(
          this.values,
          function(value) {
            var node = domConstruct.create(
              "div",
              { className: "muiAttDialogButton", innerHTML: value.text },
              this.contentNode
            );

            this.connect(
              node,
              "click",
              lang.hitch(this, function() {
                topic.publish("/mui/form/valueChanged", this, {
                  value: value.value
                });
              })
            );
          },
          this
        );
      },

      _onClick: function() {
        if (this.dialog) {
          this.dialog.hide();
          this.dialog = null;
          return;
        }
        this.contentNode = domConstruct.create("div", {
          className: "muiAttDialogContent"
        });

        this.renderListItem();
        this.dialog = Dialog.element({
          element: this.contentNode,
          position: "bottom",
          showClass: "muiDialogSelect attOperationDialog",
          callback: lang.hitch(this, function() {
            topic.publish(this.SELECT_CALLBACK, this);
            this.dialog = null;
          })
        });
      }
    }
  );
  return _field;
});
