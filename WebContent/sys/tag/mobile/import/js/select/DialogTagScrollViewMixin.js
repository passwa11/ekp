define([
    "dojo/_base/declare",
    "dojo/dom-geometry",
    "dojo/dom",
    "dojo/dom-style",
], function (declare, geometry, dom, domStyle) {

    return declare("mui.tag.DialogTagScrollViewMixin", [], {

        isMul: true,

        resize : function() {

            this.inherited(arguments);

            // moved from init() to support dynamically added fixed bars
            this._appFooterHeight = (this._fixedAppFooter) ? this._fixedAppFooter.offsetHeight : 0;

            // var top = geometry.position(this.domNode, true);
            var top = this.domNode.getBoundingClientRect().top;

            var	h,
                screenHeight = this.getScreenSize().h,
                dh = screenHeight  - top - this._appFooterHeight; // default height
            if(this.height) {
                h = this.height;
            }
            if(!h) {
                h = dh + "px";
            }
            if(h.charAt(0) !== "-" && h !== "default"){
                var height = h.substring(0, h.length - 2);
                if(this.isMul) {
                    height = Number(height) - 64;
                }
                this.domNode.style.height = height + "px";
            }
        },
    });
});