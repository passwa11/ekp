define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "mui/device/device",
    "dojo/query",
    "dojo/ready"
], function(declare,_WidgetBase, device, query, ready) {
    return declare("mui.tabbar.TabBarAdapter", [_WidgetBase], {

        startup:function(){
            this.inherited(arguments);
            ready(function () {
                var u = navigator.userAgent;
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);
                var deviceType = device.getClientType();
                if (!isiOS || deviceType == 0) {//非ios和浏览器不设置底部
                    query(".muiTabBarBottomAdaptive").removeClass("muiTabBarBottomAdaptive");
                }
            });
        }
    });
});