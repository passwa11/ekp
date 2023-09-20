/**
 * 筛选
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/topic",
  "mui/i18n/i18n!sys-xform-base:mui",
  "mui/header/HeaderItem",
  "mui/folder/_Folder",
  "mui/dialog/Dialog",
  "dojo/_base/lang",
  "dojo/_base/array",
  "mui/i18n/i18n!sys-mobile:mui"
], function(
  declare,
  domConstruct,
  topic,
  Msg,
  HeaderItem,
  _Folder,
  Dialog,
  lang,
  array,
  MuiMsg
) {
  return declare(
    "sys.xform.mobile.controls.event.MoreSearchLayout",
    [HeaderItem, _Folder],
    {
      baseClass: "muiHeaderItem muiHeaderItemIcon",
      icon: "fontmuis muis-menu",

      searchItems: [],
      searchInputs: [],

      show: function() {
    	  if(!this.searchContentNode)
    		  this.searchContentNode =  this.buildSearchContent();
        var self = this;
        var buttons = [
          {
            title: MuiMsg["mui.button.cancel"],
            fn: function(dialog) {
              dialog.hide();
            }
          },
          {
            title: Msg["mui.event.search.ok"],
            fn: function(dialog) {
              self.ok();
              dialog.hide();
            }
          },
          {
            title: Msg["mui.event.search.reset"],
            fn: function() {
              self.reset();
            }
          }
        ];

        this.dialog = Dialog.element({
          element: self.searchContentNode,
          buttons: buttons,
          position: "bottom",
          showClass: this.showClass ? this.showClass : "muiDialogSelect",
          callback: lang.hitch(this, function() {
            topic.publish(this.SELECT_CALLBACK, this);
            this.dialog = null;
          })
        });
      },

      initFilter: function(argu, dataSource, showItems) {
        this.argu = argu;
        this.dataSource = dataSource;
        this.searchItems = showItems;
      },

      buildSearchContent: function() {
        var contentNode = domConstruct.create("div", {
          className: "searchWrap"
        });
        for (var i = 0; i < this.searchItems.length; i++) {
          this.buildSearchItem(this.searchItems[i], contentNode);
          if (i === 0) {
            this.connect(
              this.searchInputs[0].dom,
              "change",
              "_searchValChange"
            );
          }
        }
        return contentNode;
      },

      _searchValChange: function(event) {
        var value = event.target.value;
        topic.publish("/sys/xform/event/search", this, { value: value });
      },

      buildSearchItem: function(item, container) {
        var searchItem = domConstruct.create(
          "div",
          { className: "searchItem" },
          container
        );
        domConstruct.create(
          "span",
          { innerHTML: item.sdesc + "：" },
          searchItem
        );
        var input = domConstruct.create("input", { type: "text" }, searchItem);
        this.searchInputs.push({ item: item, dom: input });
      },

      // 仿EventSearchBarList的searchItem方法
      searchValues: function() {
        var allOuterSearchParams = this.argu.outerSearchParams;
        var outerSearchParams = [];
        for (var i = 0; i < this.searchInputs.length; i++) {
          var searchItem = this.searchInputs[i];
          if (searchItem.item.group) {
            array.forEach(allOuterSearchParams, function(outerSearch) {
              // 同组的属性值一样
              if (outerSearch.group == searchItem.item.group) {
                outerSearch.value = searchItem.dom.value;
                outerSearchParams.push(outerSearch);
              }
            });
          } else {
            searchItem.item.value = searchItem.dom.value;
            outerSearchParams.push(searchItem.item);
          }
        }
        this.argu.paramsJSON.outerSearchs = JSON.stringify(outerSearchParams);
        topic.publish("/sys/xform/event/search", this, {
          argu: this.argu,
          dataSource: this.dataSource
        });
      },

      ok: function() {
        this.searchValues();
      },

      reset: function() {
        for (var i = 0; i < this.searchInputs.length; i++) {
          this.searchInputs[i].dom.value = "";
        }
      }
    }
  );
});
