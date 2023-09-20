define("mui/nav/NavItem", [
  "dojo/_base/declare",
  "dojox/mobile/TransitionEvent",
  "dojox/mobile/_ItemBase",
  "dojox/mobile/Badge",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/request",
  "dojo/topic",
  "mui/util",
  "./NavItemPromptMixin"
], function(
  declare,
  TransitionEvent,
  ItemBase,
  Badge,
  domConstruct,
  domStyle,
  domClass,
  request,
  topic,
  util,
  NavItemPromptMixin
) {
  var NavItem = declare("mui.nav.NavItem", [ItemBase, NavItemPromptMixin], {
	 tag: "li",

    // 选中class
    _selClass: "muiNavitemSelected ",
    
    // 选中事件
    topicType: "/mui/navitem/_selected",
    
    // 对外暴露监听选择事件，提供js调用模仿点击的能力
    _topicType: "/mui/navitem/selected",
    
    // 修改选中状态事件（仅修改页签选中状态样式，不会发出事件通知其它组件）
    changedTopicType: "/mui/navitem/changedSelected",

    // 角标数字
    badge: 0,

    buildRendering: function() {
    
      this.domNode = this.containerNode = this.srcNodeRef || domConstruct.create("li", {  className: "muiNavitem" });

      this.inherited(arguments);
      
      if (this.text) {
    	// 页签文本内容DOM
        this.textNode = domConstruct.create("span", {
            className: !this.overflowHide? "muiNavitemSpan" : "textEllipsis muiNavitemSpan",
            innerHTML: this.order?("<p>"+this.order+"</p>"+this.text):this.text
        },this.domNode);
        // 获取并赋值角标数字
        this.statNavCount();
        // 选中项的下划线
		if(!this.order)
        this.bottomNode = domConstruct.create("div", {className: "muiNavitemBottom"}, this.domNode);
      }

      // 监听页签选中事件
      this.subscribe(this.topicType, "handleItemSelected");

      // 对外暴露监听选择事件，提供js调用模仿点击的能力
      this.subscribe(this._topicType, "onItemSelected");
      
      // 监听修改选中状态事件（仅修改页签选中状态样式，不会发出事件通知其它组件）
      this.subscribe(this.changedTopicType, "onChangedItemSelected")
      
      // 监听列表下拉动作，触发更新角标数字
      this.subscribe("/mui/list/onPull", "updateNavCount");
      
      // 监听更新角标数字事件
      this.subscribe("/mui/list/onRefreshNavCount", "updateNavCount");
      
      // 当页签需要显示角标时，添加与角标相关的Class类名标记（展示角标的页签padding值会预留出展示角标数字的空间来）
      if (this.isNavCount == true) {
        domClass.add(this.domNode, "muiNavitemBadge");
      }
    },
    
    startup: function() {
        if (this._started) return;
        // 绑定页签点击事件
        this.connect(this.domNode, "onclick", "_onClick");
        this.inherited(arguments);
    },


	/**
	* 页签选中事件的响应函数
	* @param srcObj 调用方来源的NavItem组件对象
	*/
    handleItemSelected: function(srcObj) {
       if ( this!=srcObj && this.getParent() == srcObj.getParent() ) {
    	  // 将同一父级下的所有页签设置为未选中的页签样式
    	  this._handleItemSelectedClass(false);
       }else if(this==srcObj){
    	  // 已选中的页签样式处理
    	  this._handleItemSelectedClass(true);
       }
    },

    
	/**
	* 页签选中或非选中状态的样式处理
	* @param selected 是否选中（boolean） 
	*/
    _handleItemSelectedClass: function(selected){
    	if(selected){
    		domClass.add(this.domNode, this._selClass);
    	}else{
    		domClass.remove(this.domNode, this._selClass);
    	}
    },
    
	/**
	* JS模仿click点击来选中页签事件的响应函数
	* @param srcObj 调用方来源的NavItem组件对象
	* @param evt    参数对象
	*/
    onItemSelected: function(srcObj, evt) {
      if (typeof this.key == "undefined" || typeof evt == "undefined" || typeof evt.key == "undefined" || this.key != evt.key) {
        return;
      }
      this._onClick({
        target: this.domNode
      });
    },

    
	/**
	* 修改选中状态事件的响应函数（仅修改页签选中状态样式，不会发出事件通知其它组件）
	* @param srcObj 调用方来源的NavItem组件对象
	* @param evt    参数对象
	*/
    onChangedItemSelected: function(srcObj, evt){
    	 // 注：此处使用this.defer进行延迟是为了避免页面初始化时，选中状态被setSelected函数覆盖
		 this.defer(function() {
	        if ((typeof this.key == "undefined" || typeof evt == "undefined" || typeof evt.key == "undefined" || this.key != evt.key) && this.getParent() == srcObj.getParent()) {
	        	// 移除选中样式
	        	this._handleItemSelectedClass(false);
	        }else if(this==srcObj){
	        	// 添加选中样式
	        	this._handleItemSelectedClass(true);
	        	// 页签栏滑动显示出当前选中的页签
	        	this.slideBarToCurrentNav(this.domNode);
	        	// 默认click事件
	            this.defaultClickAction({ target: this.domNode });
	        }
		 }, 1);
    } ,    
    
    
	/**
	* 更新角标数字
	* @param srcObj 调用方来源的NavItem组件对象
	*/
    updateNavCount: function(srcObj) {
      if (this.url && srcObj.rel == this) {
        this.statNavCount();
      }
    },
    
    
	/**
	* 发起后台请求获取最新的角标数字，并调用设置角标显示
	*/
    statNavCount: function() {
      if (this.url) {
        var _self = this;
        if (this.isNavCount) {
          // countUrl为请求角标数字的URL，发起异步请求获取角标数字，回调函数中处理角标的显示更新
          var countUrl = util.formatUrl(_self.url + "&rowsize=1");
          request.post(countUrl, {handleAs: "json"}).then(function(data) {
            var badgeNum = 0;
            if (data.page && data.page.totalSize) {
               badgeNum = parseInt(data.page.totalSize);
            }
             _self.modifyNavCount(badgeNum);
          });
        }
      }
    },

    
	/**
	* 修改设置角标的显示值
	* @param badgeNum 角标数字
	*/
    modifyNavCount: function(badgeNum) {
      this.badge = badgeNum;
      if (badgeNum > 99) {
        this.set("badge", "99+"); // 当角标数字大于99时，显示“99+”
      } else if (badgeNum > 0) {
        this.set("badge", badgeNum); // 当角标数字大于0时，显示实际角标值
      } else { 
    	// 当角标数字等于0或异常情况时，移除角标显示
        if (this.badgeObj && this.domNode === this.badgeObj.domNode.parentNode) {
          this.domNode.removeChild(this.badgeObj.domNode);
        }
      }
    },

    
	/**
	* 手动设置页签选中
	*/
    setSelected: function() {
      this.defer(function() {
        this.beingSelected(this.textNode);
      }, 1);
    },

    
	/**
	* 处理页签被选中
	* @param target 页签下被点击的DOM
	*/
    beingSelected: function(target) {
      if (target) {
          // 页签栏滑动显示出当前选中的页签
          this.slideBarToCurrentNav(target);

          // 发布页签被选中的事件通知
          topic.publish(this.topicType, this, {
              target: this,
              url: this.url,
              text: this.text,
              index: this.tabIndex
          });
      }
    },
    
    
	/**
	* 页签栏滑动显示出当前选中的页签
	* @param target 页签下被点击的DOM
	*/    
    slideBarToCurrentNav: function(target){
        // 通过DOM向上查找，一直查找到页签domNode
        while (target) {
          if (domClass.contains(target, "muiNavitem")) break;
          target = target.parentNode;
        }
        
        var left, width;
        if (!target.offsetParent) (left = 0), (width = 0);
        var style = domStyle.getComputedStyle(target),
            marginLeft = domStyle.toPixelValue(target, style.marginLeft),
            marginRight = domStyle.toPixelValue(target, style.marginRight);
        
        // 发布页签栏滑动定位事件（页签栏滑动显示出当前选中的页签 ）
        topic.publish("/mui/navbar/slideBar", this, {
          width: width == 0 ? 0 : target.offsetWidth + marginRight + marginLeft,
          left: left == 0 ? left : target.offsetLeft + target.offsetParent.offsetLeft - marginLeft,
          target: this,
          index: this.tabIndex
        });
    },

    
    _onClick: function(e) {
      if (e) {
        var target = e.target;
        this.beingSelected(target);
      }
      // 默认click事件，触发列表数据刷新
      this.defaultClickAction(e);
    },

    
    /**
     * 此处重写了dojox.mobile._ItemBase的 userClickAction 函数 
     */
    userClickAction: function() {
      if (this.moveTo) {
        return true;
      }
      return false; // 修复出现view跳转问题
    },
    

    /**
     * 此处重写了dojox/mobile/_ItemBase的 makeTransition 函数 
     */
    makeTransition: function(e) {
      if (this.back && history) {
        history.back();
        return;
      }
      if (this.href && this.hrefTarget && this.hrefTarget != "_self") {
        win.global.open(this.href, this.hrefTarget || "_blank");
        this._onNewWindowOpened(e);
        return;
      }
      var opts = this.getTransOpts();
      var doTransition = !!(
        opts.moveTo ||
        opts.href ||
        opts.url ||
        opts.target ||
        opts.scene
      );

      if (this.getParent() && this.getParent().key) {
        opts.moveTo = opts.moveTo + "_" + this.getParent().key;
      }

      if (this._prepareForTransition(e, doTransition ? opts : null) === false) {
        return;
      }
      if (doTransition) {
        this.setTransitionPos(e);
        new TransitionEvent(this.domNode, opts, e).dispatch();
      }
    },

    
	/**
	* 调用DOJO原生set方法来设置角标值
	* 调用示例：this.set("badge",1);
	* @param value 角标数字值
	* @return
	*/
    _setBadgeAttr: function(value) {
      if (!this.badgeObj) {
        this.badgeObj = new Badge();
        domStyle.set(this.badgeObj.domNode, {
          position: "absolute",
          left: "100%",
          top: "0.2rem"
        });
      }
      this.badgeObj.setValue(value);
      if (value) {
         this.domNode.appendChild(this.badgeObj.domNode);
         domClass.add(this.domNode, "muiNavitemBadge");
      } else {
         if (this.domNode === this.badgeObj.domNode.parentNode) {
            this.domNode.removeChild(this.badgeObj.domNode);
            domClass.remove(this.domNode, "muiNavitemBadge");
         }
      }
    }
    
    
  });

  return NavItem;
})
