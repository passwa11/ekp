define([
    "dojo/_base/declare",
    "mui/dialog/Dialog",
    "dojo/dom-construct"
], function(declare, Dialog, domConstruct) {
    var claz = declare("sys.tag.CategoryDialog", [Dialog.claz],  {
        buildRendering : function() {
            this.inherited(arguments);
            this.canenlBoxNode = domConstruct.create("div", {
                className: "muiTagDialogContainer"
            }, this.containerNode);

            this.cancelBtn = domConstruct.create("div", {
                className: "muiTagDialogBottom",
                innerHTML: "取消"
            }, this.canenlBoxNode);

            this.connect(this.cancelBtn, "click", "_onClose");
        },
        _onClose: function(evt) {
            if (this.callback) this.callback(window, this);
            this.hide();
        }
    });

    return {
        element: function(options) {
            var obj = new claz(options);
            return obj.show();
        }
    };
});

