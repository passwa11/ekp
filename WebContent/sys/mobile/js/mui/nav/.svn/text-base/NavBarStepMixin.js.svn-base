define("mui/nav/NavBarStepMixin", [
  "dojo/dom-construct",
  "dojo/_base/declare",
  "dojo/dom-class",
  "dojo/topic",
  "dojo/_base/array",
  "mui/nav/_ShareNavBarMixin",
  "dojo/_base/lang",
], function (
  domConstruct,
  declare,
  domClass,
  topic,
  array,
  _ShareNavBarMixin,
  lang
) {
  var cls = declare("mui.nav.NavBarStepMixin", null, {
    height: "3.4rem",
    buildRendering: function () {
      this.inherited(arguments);
      domClass.remove(this.containerNode, "muiNavbarContainer");
      var stepNavBarContainer = domConstruct.create("div", {
        className: "muiStepNavBarContainer",
      });
      domConstruct.place(stepNavBarContainer, this.domNode, "before");
      domConstruct.place(this.domNode, stepNavBarContainer, "first");
      domClass.add(this.domNode, "muiStepNavBar");
      domClass.add(this.containerNode, "muiStepNav");
      var _self = this;
      var changeNav = function (view) {
        var wgt = _self;
        for (var i = 0; i < wgt.getChildren().length; i++) {
          var tmpChild = wgt.getChildren()[i];
          if (view.id == tmpChild.moveTo) {
            tmpChild.beingSelected(tmpChild.domNode);
            return;
          }
        }
      };
      topic.subscribe("mui/form/validateFail", function (view) {
        changeNav(view);
      });
      topic.subscribe("mui/view/currentView", function (view) {
        changeNav(view);
      });
    },
    generateList: function (items) {
      array.forEach(
        items,
        function (item, index) {
          if (index == 0) {
            this.addFirstChild(
              this.createListItem(
                lang.mixin(item, { tabIndex: index, order: index + 1 })
              )
            );
            return;
          }
          this.addChild(
            this.createListItem(
              lang.mixin(item, { tabIndex: index, order: index + 1 })
            )
          );
          if (item[this.childrenProperty]) {
            array.forEach(
              item[this.childrenProperty],
              function (child, index) {
                this.addChild(
                  this.createListItem(
                    lang.mixin(item, { tabIndex: index, order: index + 1 })
                  )
                );
              },
              this
            );
          }
        },
        this
      );
    },
  });
  return cls;
});
