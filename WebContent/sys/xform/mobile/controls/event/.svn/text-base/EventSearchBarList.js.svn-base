define([
  "dojo/_base/declare",
  "dijit/_Contained",
  "dijit/_Container",
  "dijit/_WidgetBase",
  "dojo/dom-construct",
  "dojo/topic",
  "mui/i18n/i18n!sys-xform-base:mui",
  "mui/search/SearchBar",
  "dojo/_base/array"
], function(
  declare,
  Contained,
  Container,
  WidgetBase,
  domConstruct,
  topic,
  Msg,
  SearchBar,
  array
) {
  return declare(
    "sys.xform.mobile.controls.event.EventSearchBarList",
    [WidgetBase, Container, Contained],
    {
      key: null,
      children: [],
      outerSearchParams: null,

      postCreate: function() {
        // 清空选项
        this.children = [];
        this.inherited(arguments);
        // 搜索触发
        this.subscribe("/mui/search/submit", "searchItem");
        // 取消搜索触发
        this.subscribe("/mui/search/cancel", "searchItem");
        // 通过晒暖器首个筛选项数据
        this.subscribe("/sys/xform/event/search/change", "searchChange");
      },

      initSearchBars: function(argu, dataSource, showItems) {
        this.argu = argu;
        this.dataSource = dataSource;
        if (showItems.length > 0) {
          this.searchBar = this.createSearchItem(showItems[0]);
          this.append(this.searchBar);
        }
      },

      searchChange: function(srcObj, obj) {
        if (srcObj.key == this.key) {
          this.searchBar.set("keyword", obj.value);
        }
      },

      createSearchItem: function(outerSearch) {
        var placeHolder =
          Msg["mui.event.search.enter"] + " " + outerSearch.sdesc;
        var item = new SearchBar({
          jumpToSearchUrl: false,
          needPrompt: false,
          searchUrl: "",
          key: this.key,
          emptySearch: true,
          placeHolder: placeHolder,
          showLayer: false,
          outerSearch: outerSearch
        });
        item.startup();
        this.children.push(item);
        return item;
      },

      append: function(item) {
        domConstruct.place(item.containerNode, this.containerNode, "last");
      },

      searchItem: function(srcObj) {
        if (srcObj.key && srcObj.key == this.key) {
          // 获取搜索的单元
          var children = this.children;
          var outerSearchParams = [];
          // allOuterSearchParams包括全部的搜索属性，包括一些不显示的属性
          var allOuterSearchParams = this.argu.outerSearchParams;
          for (var i = 0; i < children.length; i++) {
            var child = children[i];
            // 当用组概念(自定义数据的显示值和实际值)的时候，才做组处理
            if (child.outerSearch.group) {
              array.forEach(allOuterSearchParams, function(outerSearch) {
                // 同组的属性值一样
                if (
                  outerSearch.group &&
                  outerSearch.group == child.outerSearch.group
                ) {
                  outerSearch.value = child.searchNode.value;
                  outerSearchParams.push(outerSearch);
                }
              });
            } else {
              child.outerSearch.value = child.searchNode.value;
              outerSearchParams.push(child.outerSearch);
            }
          }

          this.argu.paramsJSON.outerSearchs = JSON.stringify(outerSearchParams);
          topic.publish("/sys/xform/event/search", this, {
            argu: this.argu,
            dataSource: this.dataSource
          });
        }
      }
    }
  );
});
