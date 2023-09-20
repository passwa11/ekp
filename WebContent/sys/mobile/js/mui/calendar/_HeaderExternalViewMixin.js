define([ "dojo/_base/declare", "dojo/date", "dojo/topic", "dojo/dom-style","dojo/_base/array","dojo/dom-construct",
         "dojo/_base/lang","dojo/dom-class","mui/iconUtils","dojox/mobile/TransitionEvent", "mui/util","./item/CardItem"], function(declare,
		dateClaz, topic, domStyle,array,domConstruct, lang,domClass,iconUtils,TransitionEvent, util,CardItem) {
	var claz = declare("mui.calendar._HeaderExternalViewMixin", null, {

		startup : function() {
			this.inherited(arguments);
			this.connect(this.rightNode, 'click', 'onRightClick');
			this.connect(this.leftNode, 'click', 'onLeftClick');
			this.subscribe("/mui/calendar/rightNodeDialogClose", "onClose");
		},

		onTransition : function(opts) {
			new TransitionEvent(this.domNode, opts).dispatch();
		},

		opts : {
			transition : 'scaleOut'
		},

		onLeftClick : function(evt) {
			if (this.left.href) {
				location.href = util.formatUrl(encodeURI(this.left.href));
				return false;
			} else if (this.left.moveTo) {
				this.opts.moveTo = this.left.moveTo;
				this.onTransition(this.opts);
			}
		},

		onRightClick : function(evt) {
			if(this.right.showDialog == 'true'){
				if(this.right.cards){
					this.buildDialog(this.right.cards)
				}
			}else{
				if (this.right.href) {
					location.href = util.formatUrl(encodeURI(this.right.href));
					return false;
				} else if (this.right.moveTo) {
					this.opts.moveTo = this.right.moveTo;
					this.onTransition(this.opts);
				}
			}
		},
		
		 buildDialog: function(datas) {
		      if (!this.dialogNode) {
		        this.dialogNode = domConstruct.create(
		          "div",
		          {className: "muiCalendarRightNodeDialog"},
		          document.body
		        )
		        this.buildCover()
		        this.buildCard(datas)
		        this.buildClose()
		      }

		      domStyle.set(this.dialogNode, "display", "block")
		      this.defer(function() {
		        domClass.add(this.dialogNode, "muiCalendarRightNodeDialogShow")
		      }, 1)
		    },

		    buildCard: function(datas) {
		      if (datas.length == 0) {
		        return
		      }
		      var menuNode = domConstruct.create(
		        "div",
		        {className: "muiCalendarRightNodeMenu"},
		        this.dialogNode
		      )

		      var length = datas.length
		      if (length > this.maxColumn) {
		        length = 4
		      }

		      var width = 100 / length + "%"

		      array.forEach(
		        datas,
		        function(data) {
		          lang.mixin(data, {width: width})
		          var cardItem = new CardItem(data)
		          domConstruct.place(cardItem.domNode, menuNode)
		        },
		        this
		      )
		    },

		    buildClose: function() {
		      var closeNode = domConstruct.create(
		        "div",
		        {className: "muiCalendarRightNodeClose"},
		        this.dialogNode
		      )
		      var iconNode = iconUtils.createIcon(
		        "muis-pop-close",
		        null,
		        null,
		        null,
		        closeNode
		      )

		      this.connect(iconNode, "click", "onClose")
		    },

		    onClose: function() {
		      domClass.remove(this.dialogNode, "muiCalendarRightNodeDialogShow")
		      this.defer(function() {
		        domStyle.set(this.dialogNode, "display", "none")
		      }, 500)
		    },

		    buildCover: function() {
		      domConstruct.create(
		        "div",
		        {className: "muiCalendarRightNodeCover"},
		        this.dialogNode
		      )
		    }
	});
	return claz;
});