define([
    "dojo/_base/declare",
	"dojo/topic",
	"mui/i18n/i18n!sys-mobile",
	"dojo/dom-construct",
	"./_ViewScrollCommonMixin",
	"dojo/dom-style",
	], function(declare, topic, Msg, domConstruct, _ViewScrollCommonMixin, domStyle) {
	
	return declare("mui.list._ViewUpAppendMixin", [_ViewScrollCommonMixin], {
		
		isNeedTopBtn : true, //是否需要top按钮
		
		constructor : function() {
			this.optUp = {
					  use: true, // 是否启用上拉加载; 默认true
				      auto: true, // 是否在初始化完毕之后自动执行上拉加载的回调; 默认true
				      isLock: false, // 是否锁定上拉加载,默认false;
				      isBoth: false, // 上拉加载时,如果滑动到列表顶部是否可以同时触发下拉刷新;默认false,两者不可同时触发;
				      isBounce: true, // 是否允许ios的bounce回弹;默认true,允许; 如果设置为false,则除了mescroll, mescroll-touch, mescroll-touch-x, mescroll-touch-y能够接收touchmove事件,其他部分均无法滑动,能够有效禁止bounce
				      callback: null, // 上拉加载的回调;function(page,mescroll){ }
				      noMoreSize: 5, // 如果列表已无数据,可设置列表的总数量要大于等于5条才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看
				      offset: 150, // 列表滚动到距离底部小于100px,即可触发上拉加载的回调
				      hardwareClass: 'mui-mescroll-hardware', // 硬件加速样式,使上拉动画流畅
				      warpId: null, // 可配置上拉加载的布局添加到指定id的div;默认不配置,默认添加到mescrollId
				      warpClass: 'mui-mescroll-upwarp', // 上拉加载的布局容器样式
				      htmlLoading: '<span><i class="fontmuis muis-tips-loading mui-spin"></i>' + Msg["mui.list.push.more"]+'</span>',
				      htmlNodata: '<p class="mui-upwarp-nodata">-- ' + Msg["mui.list.msg.nomore"] + ' --</p>', // 无数据的布局
				      inited: function (mescroll, upwarp) {
				        // 初始化完毕的回调,可缓存dom 比如 mescroll.upProgressDom = upwarp.getElementsByClassName("upwarp-progress")[0];
				      },
				      showLoading: function (mescroll, upwarp) {
				        // 上拉加载中.. mescroll.upProgressDom.style.display = "block" 不通过此方式显示,因为ios快速滑动到底部,进度条会无法及时渲染
				        upwarp.innerHTML = mescroll.optUp.htmlLoading;
				      },
				      showNoMore: function (mescroll, upwarp) {
				        // 无更多数据
				        upwarp.innerHTML = mescroll.optUp.htmlNodata;
				      },
				      onScroll: null, // 列表滑动监听,默认null; 例如 onScroll: function(mescroll, y, isUp){ }; //y为列表当前滚动条的位置; isUp=true向上滑,isUp=false向下滑
				      hasNext : true
				}
		},
		
		buildRendering : function() {
			this.inherited(arguments);
			this.scrollDom = this.domNode;
						
		    // 在页面中加入上拉布局
		    this.upwarp = document.createElement('div');
		    this.upwarp.className = this.optUp.warpClass;
		    this.scrollDom.appendChild(this.upwarp);
			
		    this.initUpScroll();
		    
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe('/mui/list/toTop', 'toTop');
		},
		
		//mescroll
		startup : function(){
			this.inherited(arguments);
		},
		
		
		//获取下一页数据
		upCallback : function() {
			topic.publish("/mui/list/onPush", this);
		},
		

		endUpSuccess : function(obj) {
			if(obj._loadOver) { //加载完了
				if(obj.totalSize <= 0) {
					this.endUpScroll(false, true);
				} else {
					this.endUpScroll(true);
				}
				this.optUp.hasNext = false; // 无更多数据
			} else { //还有数据
				this.optUp.hasNext = true;
				this.endUpScroll(false);
				this.loadFull();
			}
		},
		
		scrollEvent : function() {
			 // 列表内容顶部卷去的高度(含列表边框)
		      var scrollTop = this.getScrollTop();

		      // 向上滑还是向下滑动
		      var isUp = scrollTop - this.preScrollY > 0;
		      this.preScrollY = scrollTop;
		      // 如果没有在加载中
		      if (isUp && !this.isUpScrolling 
		    		  && this.optUp.hasNext
		    		  && !this.optUp.isLock 
		    		  && (!this.isDownScrolling || (this.isDownScrolling && this.optDown.isBoth))) {
		        // offsetheight 列表高度(内容+内边距+边框),滚动条在边框之内,所以使用clientHeight即可
		        // clientHeight 列表高度(内容+内边距),不含列表边框
		        // scrollHeight 列表内容撑开的高度
				var toBottom = this.getScrollHeight() - this.getClientHeight() - scrollTop; // 滚动条距离底部的距离
				if (toBottom <= this.optUp.offset) {
					// 如果滚动条距离底部指定范围内且向上滑,则执行上拉加载回调
					this.triggerUpScroll();
				}
		      }

		      // 顶部按钮的显示隐藏
		      if(this.isNeedTopBtn) {
			      if (scrollTop >= 50) {
			          this.showTopBtn();
			      } else {
			          this.hideTopBtn();
			      }
		      }

		      // 滑动监听
		      this.optUp.onScroll && this.optUp.onScroll(me, scrollTop, isUp);
		},
		
		initUpScroll : function() {
			var me = this;
		 
		    // 不允许ios的bounce时,需禁止webview的touchmove事件
		    if (!this.optUp.isBounce) this.setBounce(false);

		    if (this.optUp.use === false) return; // 配置不使用上拉加载时,则不初始化上拉布局
		    
		    this.optUp.hasNext = true; // 如果使用上拉,则默认有下一页

		    // 滚动监听
		    this.preScrollY = 0;
		   
		    this.scrollDom.addEventListener('scroll', this.scrollEvent.bind(this));

		    // 初始化完毕的回调
		    setTimeout(function () { // 待主线程执行完毕再执行,避免new MeScroll未初始化,在回调获取不到mescroll的实例
		    	me.optUp.inited(me, me.upwarp);
		    }, 0);
		},
		
		setBounce : function (isBounce) {
		    if (!this.os.ios) return; // 不支持非ios设备
		    if (isBounce === false) {
		      this.optUp.isBounce = false; // 禁止
		      window.addEventListener('touchmove', this.bounceTouchmove, {
		        passive: false
		      });
		    } else {
		      this.optUp.isBounce = true; // 允许
		      window.removeEventListener('touchmove', this.bounceTouchmove);
		    }
		},
		
		bounceTouchmove : function (e) {
		    var me = this;
		    var el = e.target;
		    // 当前touch的元素及父元素是否要拦截touchmove事件
		    var isPrevent = true;
		    while (el !== document.body && el !== document) {
		      var cls = el.classList;
		      if (cls) {
		        if (cls.contains('mescroll') || cls.contains('mescroll-touch')) {
		          isPrevent = false; // 如果是指定条件的元素,则无需拦截touchmove事件
		          break;
		        } else if (cls.contains('mescroll-touch-x') || cls.contains('mescroll-touch-y')) {
		          // 如果配置了水平或者垂直滑动
		          var curX = e.touches ? e.touches[0].pageX : e.clientX; // 当前第一个手指距离列表顶部的距离x
		          var curY = e.touches ? e.touches[0].pageY : e.clientY; // 当前第一个手指距离列表顶部的距离y

		          if (!this.preWinX) this.preWinX = curX; // 设置上次移动的距离x
		          if (!this.preWinY) this.preWinY = curY; // 设置上次移动的距离y

		          // 计算两点之间的角度
		          var x = Math.abs(this.preWinX - curX);
		          var y = Math.abs(this.preWinY - curY);
		          var z = Math.sqrt(x * x + y * y);

		          this.preWinX = curX; // 记录本次curX的值
		          this.preWinY = curY; // 记录本次curY的值

		          if (z !== 0) {
		            var angle = Math.asin(y / z) / Math.PI * 180; // 角度区间 [0,90]
		            if ((angle <= 45 && cls.contains('mescroll-touch-x')) || (angle > 45 && cls.contains('mescroll-touch-y'))) {
		              isPrevent = false; // 水平滑动或者垂直滑动,不拦截touchmove事件
		              break;
		            }
		          }
		        }
		      }
		      el = el.parentNode; // 继续检查其父元素
		    }
		   
		    // 拦截touchmove事件:是否可以被禁用&&是否已经被禁用 (这里不使用this.preventDefault(e)的方法,因为某些情况下会报找不到方法的异常)
		    if (isPrevent && e.cancelable && !e.defaultPrevented && typeof e.preventDefault === "function") e.preventDefault();
		  },
		  
		  /* 触发上拉加载 */
		  triggerUpScroll : function () {
		    if (!this.isUpScrolling) {
		      this.showUpScroll(); // 上拉加载中...
		      //this.optUp.page.num++; // 预先加一页,如果失败则减回
		      this.isUpAutoLoad = true; // 标记上拉已经自动执行过,避免初始化时多次触发上拉回调
		      this.upCallback && this.upCallback(this);
		    }
		  },

		  /* 显示上拉加载中 */
		  showUpScroll : function () {
			this.isUpScrolling = true;
		     // 标记上拉加载中
		    this.upwarp.classList.add(this.optUp.hardwareClass); // 添加硬件加速样式,使动画更流畅
		    this.upwarp.style.visibility = 'visible'; // 显示上拉加载区域
		    this.upwarp.style.display = 'block'; // 显示上拉加载区域
		    this.optUp.showLoading(this, this.upwarp); // 加载中...
		  },

		  /* 显示上拉无更多数据 */
		  showNoMore : function () {
			if (this.getClientHeight() > this.getBodyHeight()) {
				this.upwarp.style.visibility = 'visible'; // 显示上拉加载区域
			    this.upwarp.style.display = 'block'; // 显示上拉加载区域
			    this.optUp.showNoMore(this, this.upwarp); // 无更多数据
			} else {
				this.upwarp.style.display = 'none';
			}
		  },

		  /* 隐藏上拉区域. displayAble: 是否通过display:none隐藏, 默认false通过visibility:hidden的方式隐藏**/
		  hideUpScroll : function (displayAble) {
		    if (displayAble) {
		      this.upwarp.style.display = 'none'; // 通过display:none隐藏: 优点隐藏后不占位,缺点列表快速滑动到底部不能及时显示加载中
		    } else {
		      this.upwarp.style.visibility = 'hidden'; // 通过visibility:hidden的方式隐藏,优点当列表快速滑动到底部能及时显示加载中,缺点隐藏后会占位
		    }
		    this.upwarp.classList.remove(this.optUp.hardwareClass); // 移除硬件加速样式
		    var upProgressDom = this.upwarp.getElementsByClassName('upwarp-progress')[0];
		    if (upProgressDom) upProgressDom.classList.remove('mescroll-rotate');
		  },

		  /* 结束上拉加载 */
		  endUpScroll : function (isShowNoMore, displayAble) {
		    if (isShowNoMore != null) { // isShowNoMore=null,不处理下拉状态,下拉刷新的时候调用
		      if (isShowNoMore) {
		        this.showNoMore(); // isShowNoMore=true,显示无更多数据
		      } else {
		        this.hideUpScroll(displayAble); // isShowNoMore=false,隐藏上拉加载
		      }
		    }
		    this.isUpScrolling = false; // 标记结束上拉加载
		  },
		
		  //不满整个滚动屏的时候，触发加载数据，进而铺满
		  loadFull : function () {
			    var me = this;
			    if (!this.optUp.isLock && this.optUp.hasNext) {
			      setTimeout(function () {
			        // 延时之后,还需再判断一下高度,因为可能有些图片在延时期间加载完毕撑开高度
			        if (me.getScrollHeight() <= me.getClientHeight()) me.triggerUpScroll();
			      }, 500);
			    }
		 },
		 
		 lockUpScroll : function (isLock) {
			  if (isLock == null) isLock = true;
			  this.optUp.isLock = isLock;
		 },
		 
		 //滑动到顶部
		 toTop : function(obj, evt) {
			if(this._isVisible()) {
				var y = 0, t = 300;
				if(evt) {
					y = evt.y || 0;
					t = evt.t >= 0 ? evt.t : 300;
				}
				this.scrollTo(y, t);
			}
		 },
		 
		 showTopBtn : function() {
			 this.isTopBtnDisplay = true;
			 topic.publish('mui/list/showTop', this);
		 },
		 hideTopBtn : function() {
			 this.isTopBtnDisplay = false;
			 topic.publish('mui/list/hideTop', this);
		 },
		 
		 scrollTo : function (y, t) {
			    var me = this;
			    var star = this.getScrollTop();
			    var end = y;
			    if (end > 0) {
			      var maxY = this.getScrollHeight() - this.getClientHeight(); // y的最大值
			      if (end > maxY) end = maxY; // 不可超过最大值
			    } else {
			      end = 0; // 不可小于0
			    }
			    this.isScrollTo = true; // 标记在滑动中,阻止列表的触摸事件
			    this.scrollDom.style.webkitOverflowScrolling = 'auto';
			    this.getStep(star, end, function (step) {
			      me.setScrollTop(step);
			      if (step === end) {
			        me.scrollDom.style.webkitOverflowScrolling = 'touch';
			        me.isScrollTo = false;
			      }
			    }, t);
		 },
		 
		 getStep : function (star, end, callback, t, rate) {
			    var diff = end - star; // 差值
			    if (t === 0 || diff === 0) {
			      callback && callback(end);
			      return;
			    }
			    t = t || 300; // 时长 300ms
			    rate = rate || 30; // 周期 30ms
			    var count = t / rate; // 次数
			    var step = diff / count; // 步长
			    var i = 0; // 计数
			    var timer = window.setInterval(function () {
			      if (i < count - 1) {
			        star += step;
			        callback && callback(star, timer);
			        i++;
			      } else {
			        callback && callback(end, timer); // 最后一次直接设置end,避免计算误差
			        window.clearInterval(timer);
			      }
			    }, rate);
		 },
		 
		 _isVisible: function(checkAncestors) {
				var visible = function(node){
					return domStyle.get(node, "display") !== "none";
				};
				if(checkAncestors){
					for(var n = this.domNode; n.tagName !== "BODY"; n = n.parentNode){
						if(!visible(n)){ return false; }
					}
					return true;
				}else{
					return visible(this.domNode);
				}
		},
		
		isShowTop : function() {
			return this.isTopBtnDisplay || false;
		}
	});
});