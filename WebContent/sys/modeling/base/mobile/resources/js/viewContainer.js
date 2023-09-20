/**
 * 移动列表视图的视图组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery'),
		base = require('lui/base'),
		view = require('sys/modeling/base/mobile/resources/js/view'),
        pcView = require('sys/modeling/base/mobile/resources/js/pcView'),
        mobileView = require('sys/modeling/base/mobile/resources/js/mobileView'),
		pcBoardView = require('sys/modeling/base/mobile/resources/js/collection/board/pcBoardView');
	var mobileBoardView = require('sys/modeling/base/mobile/resources/js/collection/board/mobileBoardView'),
        pcCardView = require('sys/modeling/base/mobile/resources/js/collection/card/pcCardView'),
        mobileCardView = require('sys/modeling/base/mobile/resources/js/collection/card/mobileCardView');
	var topic = require("lui/topic");
	var dialog = require("lui/dialog");
	
	var ViewContainer = base.Container.extend({
		
		transforms3d : (window.Modernizr && Modernizr.csstransforms3d === true) || (function () {
            'use strict';
            var div = document.createElement('div').style;
            return ('webkitPerspective' in div || 'MozPerspective' in div || 'OPerspective' in div || 'MsPerspective' in div || 'perspective' in div);
        })(),
		
		viewDatas : [],	// 页面视图的数据集合
		
		initProps: function($super, cfg) {
			$super(cfg);

			//默认true
			this.hasHead = true;
			if (typeof this.config.isMultiTab != "undefined") {
			    this.hasHead = this.config.isMultiTab;
            }
			if (this.hasHead) {
                this.headerContainer = $("." + this.config.headerClass, this.element);
            }
            this.views = [],
			this.viewContainer = $("." + this.config.mainClass, this.element);
			this.mode = this.config.mode;
			if(cfg.storeData){
				this.viewDatas = cfg.storeData.views || [];
			}
			if (this.mode) {
                this.viewDatas = [cfg.storeData];
            }
			this.fdType = cfg.fdType || 0;
			this.sysModelingOperationJson = cfg.sysModelingOperationJson || {};
		},
		
		startup : function($super, cfg) {
			$super(cfg);
		},
		
		draw : function($super, cfg){
			// 画导航
			var self = this;
			// 多页签才绘制页签行
			if (this.hasHead) {
                this.drawHeader(this.headerContainer);
            }

			if(this.viewDatas.length === 0){
				this.createChildView({});
			}else{
				for(var i = 0;i < this.viewDatas.length;i++){
					this.createChildView(this.viewDatas[i]);
				}
			}
			
			this.element.show();
			
			//改变item的样式
			this._updateHeaderItemWrapWidth();
			// 默认选中第一个
            if (this.hasHead) {
                this.switchView(this.views[0].id);
            }
		},
		
		/****************** 头部 start **********************/
		drawHeader : function(container){
			var headerHtml = this.getHeaderHtml();
			var $header = $(headerHtml).appendTo(container);
			
			this.headerContainer = $header.find(".swiper-container");
			this.headerItemWrap = $header.find(".swiper-wrapper"); 
			var self = this;
			/** ********** 添加事件 start *********** */
			// 滑动插件
			/*this.headerSwiper = new Swiper('.swiper-container', {
				resizeReInit : true,
				slidesPerView : "auto",
				slideClass : "header-slide-item",
				cssWidthAndHeight : 'height',
				grabCursor : true,
				noSwiping : true
			});*/
			
			// 添加视图
			$header.find(".model-tab-table-slide-create").on("click",function() {
				self.createChildView({},true);
			});
			
			// 切换视图
			$header.find('.swiper-tab-next').on('click', function() {
				self.swipeNext();
			})
			$header.find('.swiper-tab-prev').on('click', function() {
				self.swipePrev();
			})
			
			this.numTotalDom = $header.find(".swiper-tab-num");
			/** ********** 添加事件 end *********** */
			
		},
		
		swipeNext : function(){
			var $headerItem = this.headerItemWrap.find(".active");
			var nextItem = $headerItem.next();
			if(nextItem.length > 0){
				this.switchView(null, nextItem);
			}
		},
		
		swipePrev : function(){
			var $headerItem = this.headerItemWrap.find(".active");
			var preItem = $headerItem.prev();
			if(preItem.length > 0){
				this.switchView(null, preItem);
			}
		},
		
		getHeaderHtml : function(){
			var html = "<div class='model-tab-table-slide clearfix'>";
			// 页签区
			html = '<div class="model-tab-table-slide-wrap">';
			html += '<div class="model-tab-table-slide-tag"><div class="swiper-container" >';
			html += '<div class="swiper-wrapper" style="height:40px;line-height:40px;"></div></div></div>';
			html += '<div class="model-tab-table-slide-create"></div>';
			html += '</div>';
			
			// 切换功能
			html += '<div class="model-tab-table-slide-change clearfix"><div class="swiper-tab-prev"><i class="swiper-tab-prev-icon"></i>';
			html += '<div class="swiper-tab-prev-desc">前一个</div>'
			html += '</div>';
			
			// 记录索引
			html += '<div class="swiper-tab-num">';
			html += '</div>';
			
			html += '<div class="swiper-tab-next">';
			html += '<i class="swiper-tab-prev-icon"></i>';
			html += '<div class="swiper-tab-next-desc">后一个</div>';
			html += '</div></div>';
			
			html += "</div>";
			
			return html;
		},
		
		// 添加页签
		createHeaderItem : function(viewId, title){
			var $el = $("<div class='header-slide-item'><div class='model-tab-table-slide-tag-item'><p title='"+title+"'>"+ title +"</p><i></i></div></div>").appendTo(this.headerItemWrap);
			$el.attr("data-wgt-id",viewId);
			this._updateHeaderItemWrapWidth();
			return $el;
		},
		
		// 更新宽度
		_updateHeaderItemWrapWidth : function(){
		    if (this.hasHead) {
                var itemLength = this.headerItemWrap.find(".header-slide-item").length;
                var $header = this.headerItemWrap.parents(".panel-tab-header").eq(0);
                var maxWidth = $header.width();
                var reWidth = maxWidth - $header.find(".model-tab-table-slide-create").width() - $header.find(".model-tab-table-slide-change").width();
                if(reWidth > parseInt(itemLength * 112 + 10)){//剩余的宽度大于头部项的宽度+10，则改变宽度
                    $header.find(".swiper-container").css("max-width",parseInt(itemLength * 112)+"px");
                }else{
                    if(reWidth > 10){
                        //计算能显示的个数
                        var minNum = parseInt((reWidth - 10)/112);
                        $header.find(".swiper-container").css("max-width",parseInt(minNum * 112)+"px");
                    }
                }
                this.headerItemWrap.width(parseInt(itemLength * 112));
            }
		},
		
		// 待完善
		_transform : function(activeDom){
			var xAxis = activeDom.index() * 112;
			var maxWidth = activeDom.parents(".swiper-container").width();
			var defiHori = xAxis - maxWidth + 112;
			if(defiHori >= 0){
				this._setWrapperTranslate(-defiHori, 0, 0);
			}else if(defiHori < 0){
				this._setWrapperTranslate(0, 0, 0);
			}
		},
		
		_setWrapperTranslate : function(x,y,z){
			if (this.headerItemWrap.length<=0)
				return;
			var es = this.headerItemWrap[0].style,
            coords = {x: 0, y: 0, z: 0},
            translate;

	        if (arguments.length === 3) {
	            coords.x = x;
	            coords.y = y;
	            coords.z = z;
	        }else {
	        	if (typeof y === 'undefined') {
	                y = 'x';
	            }
	            coords[y] = x;
	        }

	        if (this.transforms3d) {
	            translate = this.transforms3d ? 'translate3d(' + coords.x + 'px, ' + coords.y + 'px, ' + coords.z + 'px)' : 'translate(' + coords.x + 'px, ' + coords.y + 'px)';
	            es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = translate;
	        }
	        else {
	            es.left = coords.x + 'px';
	            es.top  = coords.y + 'px';
	        }
		},
		
		/****************** 头部 end **********************/
		
		// isSwitchToChild:是否切换到当前视图
		createChildView : function(viewData,isSwitchToChild){
			// 设计基础，标题页面和视图组件通过data-wgt-id进行绑定
            if (this.mode === "pc") {
                var pcViewData = viewData.fdPcCfg;
				if(this.fdType == 0){
					var viewWgt = new pcView["PcView"]({data:pcViewData,parent:this,container:this.viewContainer,fdType:this.fdType,sysModelingOperationJson:this.sysModelingOperationJson});
				}else if(this.fdType == 1){
					var viewWgt = new pcCardView["PcCardView"]({data:pcViewData,parent:this,container:this.viewContainer,fdType:this.fdType,sysModelingOperationJson:this.sysModelingOperationJson});
				}else if(this.fdType == 2){
					var viewWgt = new pcBoardView["PcBoardView"]({data:pcViewData,parent:this,container:this.viewContainer,fdType:this.fdType,sysModelingOperationJson:this.sysModelingOperationJson});
				}
            } else if (this.mode === "mobile") {
                var mobileViewData = viewData.fdMobileCfg
				if(this.fdType == 0){
					var viewWgt = new mobileView["MobileView"]({data:mobileViewData,parent:this,container:this.viewContainer});
				}else if(this.fdType == 1){
					var viewWgt = new mobileCardView["MobileCardView"]({data:mobileViewData,parent:this,container:this.viewContainer});
				}else if(this.fdType == 2){
					var viewWgt = new mobileBoardView["MobileBoardView"]({data:mobileViewData,parent:this,container:this.viewContainer});
				}
            } else {
                var viewWgt = new view["View"]({data:viewData,parent:this,container:this.viewContainer});
            }
			viewWgt.startup();
            this.views.push(viewWgt);
            viewWgt.draw();

			if (this.hasHead) {
                var title = viewData.fdName || "未定义";
                var $headerItem = this.createHeaderItem(viewWgt.id, title);
                // 添加点击事件
                var self = this;
                $headerItem.on("click",function(){
                    self.switchView(null,$(this));
                });
                $headerItem.find("i").on("click",function(){
                    //删除视图前先提示
                    var $items = self.headerItemWrap.find(".header-slide-item");
                    if($items && $items.length == 1){
                        //只有一个，不让删除
                        dialog.alert("请至少保留一项！");
                    }else{
                        var pass = true;
                        var url = Com_Parameter.ContextPath + "sys/modeling/base/mobile/modelingAppMobileListView.do?method=checkTabRelation&tabId="+viewWgt.id;
                        $.ajax({
                            url: url,
                            type: "post",
                            async : false,
                            success: function (rtn) {
                                if(rtn != null && rtn.hasOwnProperty("length") && rtn.length > 0 && typeof(rtn[0].navs) != "undefined" && rtn[0].navs.length != 0){
                                    pass = false;
                                    var url='/sys/modeling/base/listview/config/dialog_relation.jsp';
                                    dialog.iframe(url, "删除关联模块", function(data){
                                    },{
                                        width : 600,
                                        height : 400,
                                        params : { datas : rtn }
                                    });
                                }
                            },
                            error : function(){
                                dialog.alert(rtn.errmsg || "删除失败");
                            }
                        });
                        if(pass){
                            dialog.confirm(Data_GetResourceString("sys-modeling-base:view.del.confirm"), function(value) {
                                if(value == true) {
                                    dialog.result({"status" : true, "title" : "你的操作已成功！"});
                                    self.delChildView(viewWgt);
                                }else{
                                    //dialog.result({"status" : false, "title" : "操作失败"});
                                }
                            });
                        }
                    }
                });

                if(isSwitchToChild){
                    $headerItem.trigger($.Event("click"));
                }
            }
			return viewWgt;
		},
		
		delChildView : function(viewWgt){
			// 删除视图
			for(var i = 0;i < this.views.length;i++){
				if(this.views[i] === viewWgt){
					this.views.splice(i, 1);
					break;
				}
			}
			viewWgt.destroy();
			
			// 删除头部标题
			var $delHeaderItem = this.headerItemWrap.find("[data-wgt-id='"+ viewWgt.id +"']");
			if($delHeaderItem.hasClass("active")){
				var $nextDom = $delHeaderItem.next();
				$delHeaderItem.remove();
				// 删除当前页面，默认跳转到下一个页签
				if($nextDom.length > 0){
					this.switchView(null, $nextDom);
				}else{
					// 若没有下一个页签，则取最后一个页签
					var lastItem = this.headerItemWrap.find(".header-slide-item").last();
					if(lastItem.length > 0){
						this.switchView(null, lastItem);
					}else{
						// 所有页签没有，则默认新建一个新的
						this.createChildView({},true);
					}
				}
			}else{
				$delHeaderItem.remove();
				this._transform(this.headerItemWrap.find(".active"));
			}
			this._updateHeaderItemWrapWidth();
		},

		// wgtId和titleDom，必须要有一个
		switchView : function(wgtId, titleDom){
			if(!titleDom){
				titleDom = this.element.find("[data-wgt-id='"+ wgtId +"']");
			}
			if(!wgtId){
				wgtId = titleDom.attr("data-wgt-id");
			}
			titleDom.siblings().removeClass("active");
			titleDom.addClass("active");
			this._transform(titleDom);
			var currrentView = null;
			for(var i = 0;i < this.views.length;i++){
				this.views[i].element.hide();
				if(this.views[i].id === wgtId){
					currrentView = this.views[i];
				}
			}
			if(currrentView){
				currrentView.element.show();
				topic.publish("switchView_finish",{'wgtId':wgtId,'titleDom':titleDom,'currentView':currrentView});
			}else{
				console.error("找不到当前视图(视图ID为:"+ wgtId +")！");
			}
			// 更新索引提示
			this.updateNum(currrentView);
		},
		
		updateNum : function(currrentView){
			var numHtml = "";
			numHtml += "<p>";
			numHtml += "<span>" + (currrentView.element.index() + 1) + "</span>";
			numHtml +=  "/";
			numHtml +=  "<span class='total-num'>" + this.views.length + "</span>"; 
			numHtml += "</p>";
			this.numTotalDom.html(numHtml);
		},
		
		getKeyData : function(){
			var keyData = {};
			keyData.views = [];
			for(var i = 0;i < this.views.length;i++){
				var viewWgt = this.views[i];
				keyData.views.push(viewWgt.getKeyData());
			}
			return keyData;
		},

        validate : function() {
            var isPass = true;
            for(var i = 0;i < this.views.length;i++){
                var viewWgt = this.views[i];
                 if (!viewWgt.validate()) {
                     isPass = false;
                 }
            }
            return isPass;
        }
	});
	
	exports.ViewContainer = ViewContainer;
})