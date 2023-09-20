define(["dojo/_base/declare",
	"dijit/_WidgetBase", "dojo/dom-construct",
	"dojo/topic", "dojo/query", "mui/i18n/i18n!sys-mobile"],
	function (declare, WidgetBase, domCtr, topic, query, msg) {

		return declare("mui.view.AddBottomTipMixins", [WidgetBase], {
			//node:containerNode

			isAddBottomTip: true,
			addBottomTip: function (node) {
				this.inherited(arguments);
				var ctx = this;
				if (ctx.isAddBottomTip) {
					//监控系统预定的topic，知道页面高度变化
					this.subscribe("/dojox/mobile/viewChanged,/dojox/mobile/afterTransitionIn,mui/view/showTop", function (view) {
						var bottomTipNode = node.getElementsByClassName('addBottomTip')[0];
						var muiTop = query('.muiTop')[0];//判断top组件是否存在
						if (muiTop) {
							if (muiTop.style.display === "none") {
								domCtr.destroy(bottomTipNode);
							} else {
								topic.publish("mui/view/addBottomTip");
							}
						} else {
							domCtr.destroy(bottomTipNode);
						}

					});
					//页面内没有组件发布/dojox/mobile/viewChanged的时候,用/mui/top/viewChanged
					this.subscribe("/mui/top/viewChanged", function (digitObj) {
						var bottomTipNode = node.getElementsByClassName('addBottomTip')[0];
						var muiTop = node.getElementsByClassName('muiTop')[0];//判断top组件是否存在
						//&&!(domAttr.get(muiTop,"data-dojo-type")==="mui/top/Top")
						if (muiTop) {
							if (muiTop.style.display === "none") {
								domCtr.destroy(bottomTipNode);
							} else {
								topic.publish("mui/view/addBottomTip");
							}

						} else {
							domCtr.destroy(bottomTipNode);
						}

					});

					//通过top组件来判断是否展示
					topic.subscribe("mui/view/addBottomTip", function () {
						var bottomTipNode = node.getElementsByClassName('addBottomTip');
						var tip = msg['mui.mobile.toEnd.tip']
						if (bottomTipNode.length < 1) {
							domCtr.create("div", { className: "addBottomTip", innerHTML: "<div class='addBootomTipContent'><div class='notice'>" + tip + "</div></div>" }, node, "last");
						}
					});
				}
			}


		});
	});