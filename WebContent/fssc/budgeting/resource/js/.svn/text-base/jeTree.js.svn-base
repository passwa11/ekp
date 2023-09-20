
(function(root, factory) {
    //amd
    if (typeof define === "function" && define.amd) {
        define([ "jquery" ], factory);
    } else if (typeof exports === "object") {
        //umd
        module.exports = factory();
    } else {
        root.jeTree = factory(window.$ || $);
    }
})(this, function($) {
    $.fn.jeTree = function(options) {
        return this.each(function() {
            return new jeTree($(this), options || {});
        });
    };
    $.extend({
        jeTree:function(elem, options) {
            return $(elem).each(function() {
                return new jeTree($(this), options || {});
            });
        }
    });
    var jeTree = function(elem, opts) {
        var config = {
            skin:"je-tree",
            datas:function () {
                return {url:"",type:"GET",dataType:"json",async:true};
            },
            target:"_blank",                         //是否新选项卡打开（比如节点返回href才有效）
            itemfun:function(val) {},                //点击当前的回调，val：选中的数据
            success:function(val) {}                             //加载成功后的回调
        };
        this.opts = $.extend(config, opts || {});
        this.elCell = elem;
        this.init();
    };
    var jefn = jeTree.prototype;
    jefn.init = function () {
        var _this = this, opts = _this.opts, datas = "";
        //加载数据
        if (typeof opts.ajaxdatasTree == "function") {
            //通过Ajax方式加载数据
            $.ajax(opts.datas()).done(function(json) {
                datas = json;
            });
        } else if (typeof opts.datas == "object") {
            //直接加载数据
            datas = opts.datas;
        }
        //通过数据加载树列表
        var showlist = $("<ul>",{"class":opts.skin+" lui_list_nav_list"});
        _this.datatree(datas, showlist);
      //modify  by xiexx 不需要拼接原来的，直接覆盖值
        _this.elCell.html(showlist);
        _this.elCell.parent().attr("data-lui-type","lui/base!DataView");
       // _this.elCell.append(showlist);
      /*  $(".je-bg-navy").find("a").css("color",$(".lui_accordionpanel_nav_text").css("color"));
	  	 $(".je-bg-navy").find("a").on("mouseover",function(){
	  			$(this).css("color","rgb(66, 133, 244)")
	  		}).on("mouseout",function(){
 				$(this).css("color",$(".lui_accordionpanel_nav_text").css("color"))
 			}); */
        //加载成功后的回调
        if ($.isFunction(opts.success) || opts.success != ("" || null)) {
            opts.success && opts.success();
        }
    };
    jefn.datatree = function (data, parent) {
        var _this = this;
        $.each(data,function (i,val) {
            //如果有子节点，则遍历该子节点
            if (val.childlist.length > 0) {
                //创建一个子节点li
                var liCls = $("<li treeid='"+val.tid+"'></li>");
                //将li的文本设置好，并马上添加一个空白的ul子节点，并且将这个li添加到父亲节点中
                var ulShow = val.open ? "<ul style='display:block;'></ul>" : "<ul></ul>",
                    shde = val.open ? "folderhide foldershow" : "folderhide";
                //liCls.append("<a href='javascript:;' class='"+shde+"'>"+val.name+"</a>").append(ulShow).appendTo(parent);
                var urls = (val.url == "" || val.url == undefined) ? "javascript:;" : val.url;
                liCls.append("<div style='display:flex; '><span class='"+shde+"'></span><a style='padding-top:0;padding-bottom:0;line-height:26px;padding-left:0px;' href='#' onclick=\"clearTimeout(100); var url='"+urls+"';url = Com_SetUrlParameter(url,'navTitle',encodeURIComponent('报销')); url+='&j_iframe=true&j_aside=false&j_aside=false' ;LUI.pageQuickOpen(url,'_rIframe',{subscribeIframe:false})\"  title='"+val.name+"' >"+val.name+"</a></div>").append(ulShow).appendTo(parent);
                //将空白的ul作为下一个递归遍历的父亲节点传入
                _this.datatree(val.childlist, liCls.children("ul"));
            }else {
                //创建一个子节点li
                var liCls = $("<li treeid='"+val.tid+"'></li>");
                //没有子节点的情况
                var urls = (val.url == "" || val.url == undefined) ? "javascript:;" : val.url;
                liCls.append("<a style='padding-top:0;padding-bottom:0;line-height:26px;padding-left:24px;' href='#' onclick=\"clearTimeout(100); var url='"+urls+"';url = Com_SetUrlParameter(url,'navTitle',encodeURIComponent('报销')); url+='&j_iframe=true&j_aside=false&j_aside=false' ;LUI.pageQuickOpen(url,'_rIframe',{subscribeIframe:false})\" class='folderleaf' title='"+val.name+"' >"+val.name+"</a>").appendTo(parent);
            }
            _this.clicktree(liCls,val);
        });
    };
    jefn.clicktree = function (licell,items) {
        var _this = this, opts = _this.opts;
        licell.children("div").children("span").on("click",function(){
            var that = $(this), nextCls = that.parent().next(), show = "foldershow";
            //判断点击的是否为展开或收起状态
            if(that.hasClass(show)){
                that.removeClass(show);
                nextCls.animate({height:0},200,function(){
                    $(this).css({height:"auto",display:"none"});
                });
            }else {
                that.addClass(show);
                //计算当前下一个元素的高度
                var openHeight = nextCls.innerHeight();
                nextCls.css({display:"block",height:"0"}).animate({height:openHeight},200,function(){
                    $(this).css({height:"auto"});
                });
            }
            //加载成功后的回调
            if ($.isFunction(opts.itemfun) || opts.itemfun != ("" || null)) {
                opts.itemfun && opts.itemfun(items);
            }
        })
    };
    return jeTree;
});


