define([
    "dojo/_base/declare",
	"dojo/topic",
	"mui/i18n/i18n!sys-mobile",
	"./_ViewScrollCommonMixin",
	"dojox/mobile/viewRegistry"
	], function(declare, topic, Msg, _ViewScrollCommonMixin, viewRegistry) {
	
	return declare("mui.list._ViewDownReloadMixin", [_ViewScrollCommonMixin], {
		
		startPoint : null,
		lastPoint : null,
		
		constructor : function() {
			this.optDown = {
				  use: true, // 是否启用下拉刷新; 默认true
			      autoShowLoading: false, // 如果设置auto=true(在初始化完毕之后自动执行下拉刷新的回调),那么是否显示下拉刷新的进度; 默认false
			      isLock: false, // 是否锁定下拉刷新,默认false;
			      isBoth: false, // 下拉刷新时,如果滑动到列表底部是否可以同时触发上拉加载;默认false,两者不可同时触发;
			      offset: 80, // 在列表顶部,下拉大于80px,松手即可触发下拉刷新的回调
			      inOffsetRate: 1, // 在列表顶部,下拉的距离小于offset时,改变下拉区域高度比例;值小于1且越接近0,高度变化越小,表现为越往下越难拉
			      outOffsetRate: 0.2, // 在列表顶部,下拉的距离大于offset时,改变下拉区域高度比例;值小于1且越接近0,高度变化越小,表现为越往下越难拉
			      bottomOffset: 20, // 当手指touchmove位置在距离body底部20px范围内的时候结束上拉刷新,避免Webview嵌套导致touchend事件不执行
			      minAngle: 45, // 向下滑动最少偏移的角度,取值区间  [0,90];默认45度,即向下滑动的角度大于45度则触发下拉;而小于45度,将不触发下拉,避免与左右滑动的轮播等组件冲突;
			      hardwareClass: 'mui-mescroll-hardware', // 硬件加速样式,解决部分手机闪屏或动画不流畅的问题
			      mustToTop: false, // 是否滚动条必须在顶部,才可以下拉刷新.默认false. 当您发现下拉刷新会闪白屏时,设置true即可修复.
			      warpId: null, // 可配置下拉刷新的布局添加到指定id的div;默认不配置,默认添加到mescrollId
			      warpClass: 'mui-mescroll-downwarp', // 下拉刷新的布局容器样式,参见mescroll.css
			      resetClass: 'mui-mescroll-downwarp-reset', // 下拉刷新高度重置的动画,参见mescroll.css
			      textInOffset: Msg['mui.list.pull.reload'], // 下拉的距离在offset范围内的提示文本，下拉刷新
			      textOutOffset: Msg['mui.list.pull.release'], // 下拉的距离大于offset范围的提示文本，释放更新
			      textLoading: Msg['mui.list.pull.loading'], // 加载中的提示文本
			    //htmlContent: '<p class="downwarp-progress"></p><p class="downwarp-tip"></p>', // 布局内容
			      htmlContent: '<span class="mui-downwarp-progress"><i class="fontmuis muis-stick"></i><span class="downwarp-tip"></span></span>',
			      inited: function (mescroll, downwarp) {
			        // 下拉刷新初始化完毕的回调
			        mescroll.downTipDom = downwarp.getElementsByClassName('downwarp-tip')[0];
			        mescroll.downIconDom = downwarp.getElementsByClassName('fontmuis')[0];
			      //  mescroll.downProgressDom = downwarp.getElementsByClassName('downwarp-progress')[0];
			      },
			      inOffset: function (mescroll) {
			        // 下拉的距离进入offset范围内那一刻的回调，下拉刷新
			        if (mescroll.downTipDom) mescroll.downTipDom.innerHTML = mescroll.optDown.textInOffset;
			        if (mescroll.downIconDom) mescroll.downIconDom.className = "fontmuis muis-stick";
			      },
			      outOffset: function (mescroll) {
			        // 下拉的距离大于offset那一刻的回调，释放更新
			        if (mescroll.downTipDom) mescroll.downTipDom.innerHTML = mescroll.optDown.textOutOffset;
			        if (mescroll.downIconDom) mescroll.downIconDom.className = "mui-rotate-180 fontmuis muis-stick pullToUp";
			      },
			      onMoving: function (mescroll, rate, downHight) {},
			      beforeLoading: function (mescroll, downwarp) {
			        // 准备触发下拉刷新的回调
			        return false; // 如果return true,将不触发showLoading和callback回调; 常用来完全自定义下拉刷新, 参考案例【淘宝 v6.8.0】
			      },
			      showLoading: function (mescroll) {
			        // 显示下拉刷新进度的回调，加载中
			        if (mescroll.downTipDom) mescroll.downTipDom.innerHTML = mescroll.optDown.textLoading;
			        if (mescroll.downIconDom) mescroll.downIconDom.className = "fontmuis muis-tips-loading mui-spin";
			      },
			      afterLoading: function (mescroll) {
			        // 准备结束下拉的回调. 返回结束下拉的延时执行时间,默认0ms; 常用于结束下拉之前再显示另外一小段动画,才去隐藏下拉刷新的场景, 参考案例【dotJump】
			        return 0; 
			      }
			}
		},
		
		//下拉刷新回调
		downCallback : function() {
			this.onPull(this);
		},
		
		//外部触发
		reload : function() {
			this.triggerDownScroll();
		},
		
		onReload: function(widget, handle) {
			topic.publish('/mui/list/onReload', this, handle);
		},
		
		onPull: function(widget, handle) {
			topic.publish("/mui/list/onPull", this, handle);
		},
		
		//下拉刷新数据抓取完毕
		endDownSuccess: function(obj) {
			 if (this.isDownScrolling) {
				 this.endDownScroll();
			 }
		},
		
		//mescroll
		startup : function(){
			this.inherited(arguments);
			
			if(this.scrollDom) {
				this.initDownScroll();
			}
		},
		
		
		buildRendering : function() {
			this.inherited(arguments);
			
			if(!this.scrollDom) return;
			
			if (this.optDown.use) {
			      this.downwarp = document.createElement('div');
			      this.downwarp.className = this.optDown.warpClass;
			      this.downwarp.innerHTML = '<div class="downwarp-content">' + this.optDown.htmlContent + '</div>';
			      var downparent = this.optDown.warpId ? this.getDomById(this.optDown.warpId) : this.scrollDom;
			      if (this.optDown.warpId && downparent) {
			        downparent.appendChild(this.downwarp);
			      } else {
			        if (!downparent) downparent = this.scrollDom;
			        downparent.insertBefore(this.downwarp, this.scrollDom.firstChild);
			      }
			      // 初始化完毕的回调
			      var me = this;
			      me.optDown.inited(me, me.downwarp);
//			      setTimeout(function () { // 待主线程执行完毕再执行,避免new MeScroll未初始化,在回调获取不到mescroll的实例
//			    	  me.optDown.inited(me, me.downwarp);
//			      }, 0)
			}
			
		},
		
		_initLoading : function(obj, evt) {
			if(this.isListInScrollView(obj.domNode)) {
				this.showDownScroll(); // 下拉刷新中...
			}
		},
		
		 /* 触发下拉刷新 */
		triggerDownScroll : function () {
		    if (!this.optDown.beforeLoading(this, this.downwarp)) { // 准备触发下拉的回调,return
		      this.showDownScroll(); // 下拉刷新中...
		      this.optDown.callback && this.optDown.callback(this); // 执行回调,联网加载数据
		      this.downCallback && this.downCallback(this);
		    }
		},

		  /* 显示下拉进度布局 */
		showDownScroll : function () {
		    this.isDownScrolling = true; // 标记下拉中
		    this.optDown.showLoading(this); // 下拉刷新中...
		    this.downHight = this.optDown.offset; // 更新下拉区域高度
		    this.downwarp.classList.add(this.optDown.resetClass); // 加入高度重置的动画,过渡平滑
		    this.downwarp.style.height = this.optDown.offset + 'px'; // 调整下拉区域高度
		},
		
		/* 结束下拉刷新 */
		endDownScroll : function () {
		    var me = this;
		    // 结束下拉刷新的方法
		    var endScroll = function () {
		    	me.downHight = 0;
		    	me.downwarp.style.height = 0;
		    	me.isDownScrolling = false;
		      if (me.downProgressDom) me.downProgressDom.classList.remove('mescroll-rotate');
		    }
		    // 结束下拉刷新时的回调
		    var delay = this.optDown.afterLoading(me); // 结束下拉刷新的延时,单位ms
		    if (typeof delay === 'number' && delay > 0) {
		      setTimeout(endScroll, delay);
		    } else {
		      endScroll();
		    }
		},
		
		lockDownScroll : function (isLock) {
		    if (isLock == null) isLock = true;
		    this.optDown.isLock = isLock;
		},
		
		touchstartEvent : function(e) {
			 if (this.isScrollTo) this.preventDefault(e); // 如果列表执行滑动事件,则阻止事件,优先执行scrollTo方法

		     this.startPoint = this.getPoint(e); // 记录起点
		     this.lastPoint = this.startPoint; // 重置上次move的点

		     this.maxTouchmoveY = this.getBodyHeight() - this.optDown.bottomOffset; // 手指触摸的最大范围(写在touchstart避免body获取高度为0的情况)
		     this.inTouchend = false; // 标记不是touchend
		     var scrollTop = this.getScrollTop();// 滚动条的位置
		     this.isKeepTop = scrollTop === 0; // 标记滚动条起点为0
		},
		
		touchmoveEvent : function(e) {
			 if (!this.startPoint) {
				 return;
			 }

		      var scrollTop = this.getScrollTop(); // 当前滚动条的距离
		      if (scrollTop > 0) this.isKeepTop = false; // 在移动过程中,只要滚动条有一次大于0,则标记false
		      var curPoint = this.getPoint(e); // 当前点

		      var moveY = curPoint.y - this.startPoint.y; // 和起点比,移动的距离,大于0向下拉,小于0向上拉
		      // 向下拉
		      if (moveY > 0) {

		        // 在顶部
		        if (scrollTop <= 0) {

		          this.preventDefault(e); // 阻止浏览器默认的滚动,避免触发bounce
		          // 可下拉的条件
		          if (this.optDown.use 
		        		  && !this.inTouchend 
		        		  && !this.isDownScrolling 
		        		  && !this.optDown.isLock 
		        		  && (!this.isUpScrolling || (this.isUpScrolling && this.optUp.isBoth))) {
		            if (this.optDown.mustToTop && !this.isKeepTop) return; // 是否配置了必须在顶部才可以下拉

		            // 下拉的角度是否在配置的范围内
		            var x = Math.abs(this.lastPoint.x - curPoint.x);
		            var y = Math.abs(this.lastPoint.y - curPoint.y);
		            var z = Math.sqrt(x * x + y * y);
		            if (z !== 0) {
		              var angle = Math.asin(y / z) / Math.PI * 180; // 两点之间的角度,区间 [0,90]
		              if (angle < this.optDown.minAngle) return; // 如果小于配置的角度,则不往下执行下拉刷新
		            }
								
		            // 如果手指的位置超过配置的距离,则提前结束下拉,避免Webview嵌套导致touchend无法触发
		            if (this.maxTouchmoveY > 0 && curPoint.y >= this.maxTouchmoveY) {
		              this.inTouchend = true; // 标记执行touchend
		              this.touchendEvent(); // 提前触发touchend
		              return;
		            }

		            var diff = curPoint.y - this.lastPoint.y; // 和上次比,移动的距离 (大于0向下,小于0向上)
		            if (!this.downHight) this.downHight = 0; // 下拉区域的高度
		            // 下拉距离  < 指定距离
		            if (this.downHight < this.optDown.offset) {
		              if (this.movetype !== 1) {
		                this.movetype = 1; // 加入标记,保证只执行一次
		                this.optDown.inOffset(this); // 进入指定距离范围内那一刻的回调,只执行一次
		                this.downwarp.classList.remove(this.optDown.resetClass); // 移除高度重置的动画
		                this.isMoveDown = true; // 标记下拉区域高度改变,在touchend重置回来
		                if (this.os.ios && !this.isKeepTop) { // 下拉过程中,滚动条一直在顶部的,则不必取消回弹,否则会闪白屏
		                  this.scrollDom.classList.add(this.optDown.hardwareClass); // 开启硬件加速,解决iOS下拉因隐藏进度条而闪屏的问题
		                  this.scrollDom.style.webkitOverflowScrolling = 'auto'; // 取消列表回弹效果,避免与下面this.downwarp.style.height混合,而导致界面抖动闪屏
		                  this.isSetScrollAuto = true; // 标记设置了webkitOverflowScrolling为auto
		                }
		              }
		              this.downHight += diff * this.optDown.inOffsetRate; // 越往下,高度变化越小

		              // 指定距离  <= 下拉距离
		            } else {
		              if (this.movetype !== 2) {
		                this.movetype = 2; // 加入标记,保证只执行一次
		                this.optDown.outOffset(this); // 下拉超过指定距离那一刻的回调,只执行一次
		                this.downwarp.classList.remove(this.optDown.resetClass); // 移除高度重置的动画
		                this.isMoveDown = true; // 标记下拉区域高度改变,在touchend重置回来
		                if (this.os.ios && !this.isKeepTop) { // 下拉过程中,滚动条一直在顶部的,则不必取消回弹,否则会闪白屏
		                  this.scrollDom.classList.add(this.optDown.hardwareClass); // 开启硬件加速,解决iOS下拉因隐藏进度条而闪屏的问题
		                  this.scrollDom.style.webkitOverflowScrolling = 'auto'; // 取消列表回弹效果,避免与下面this.downwarp.style.height混合,而导致界面抖动闪屏
		                  this.isSetScrollAuto = true; // 标记设置了webkitOverflowScrolling为auto
		                }
		              }
		              if (diff > 0) { // 向下拉
		                this.downHight += diff * this.optDown.outOffsetRate; // 越往下,高度变化越小
		              } else { // 向上收
		                this.downHight += diff; // 向上收回高度,则向上滑多少收多少高度
		              }
		            }

		            this.downwarp.style.height = this.downHight + 'px'; // 实时更新下拉区域高度
		            var rate = this.downHight / this.optDown.offset; // 下拉区域当前高度与指定距离的比值
		            this.optDown.onMoving(this, rate, this.downHight); // 下拉过程中的回调,一直在执行
		          }
		        }

		        // 向上拉
		      } else if (moveY < 0) {
		        var scrollHeight = this.getScrollHeight(); // 滚动内容的高度
		        var clientHeight = this.getClientHeight(); // 滚动容器的高度
		        var toBottom = scrollHeight - clientHeight - scrollTop; // 滚动条距离底部的距离

		        // 如果在底部,则阻止浏览器默认事件
		        if (this.optUp && !this.optUp.isBounce && toBottom <= 0) this.preventDefault(e);

		        // 如果不满屏或者已经在底部,无法触发scroll事件,此时需主动触发上拉回调
//		        if (this.optUp.use && !this.optUp.isLock && this.optUp.hasNext && !this.isUpScrolling && (!this.isDownScrolling || (this.isDownScrolling && this.optDown.isBoth)) && (clientHeight + this.optUp.offset >= scrollHeight || toBottom <= 0)) {
//		          this.triggerUpScroll();
//		        }
		      }

		      this.lastPoint = curPoint; // 记录本次移动的点
		},
		
		
		touchendEvent : function () {
		      // 如果下拉区域高度已改变,则需重置回来
		      if (this.optDown.use && this.isMoveDown) {
		        if (this.downHight >= this.optDown.offset) {
		          // 符合触发刷新的条件
		          this.triggerDownScroll();
		        } else {
		          // 不符合的话 则重置
		          this.downwarp.classList.add(this.optDown.resetClass); // 加入高度重置的动画,过渡平滑
		          this.downHight = 0;
		          this.downwarp.style.height = 0;
		        }
		        if (this.isSetScrollAuto) {
		          this.scrollDom.style.webkitOverflowScrolling = 'touch';
		          this.scrollDom.classList.remove(this.optDown.hardwareClass);
		          this.isSetScrollAuto = false;
		        }
		        this.movetype = 0;
		        this.isMoveDown = false;
		     }
		},
		
		//初始化下拉刷新
		initDownScroll : function() {
			 this.scrollDom.addEventListener('touchstart', this.touchstartEvent.bind(this));
			  // 移动端手指的滑动事件
			 this.scrollDom.addEventListener('touchmove', this.touchmoveEvent.bind(this), {
			      passive: false
			 });
			 this.scrollDom.addEventListener('touchend', this.touchendEvent.bind(this)); // 移动端手指事件
			 this.scrollDom.addEventListener('touchcancel', this.touchendEvent.bind(this)); // 移动端系统停止跟踪触摸
		}
		
	});
});