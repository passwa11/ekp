define(["dojo/_base/declare", "mui/NativeView"], function(declare, NativeView) {
  return declare("mui.table.NativeView", [NativeView], {
    scrollDir: "h", //默认为h，可以切为v

    buildRendering : function() {
      this.inherited(arguments);
      //如果是新的表单桌面端，则设置为v
      if(this.isXform == true && this.isDesktopLayoutForm == true && window._xform_isNewDesktopLayout == "true"){
        this.scrollDir = 'v';
      }
    }
  })
})
