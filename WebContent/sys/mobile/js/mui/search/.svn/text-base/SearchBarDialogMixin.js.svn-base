define([
  "dojo/_base/declare",
  "dojo/dom-style",
  "dojo/dom-construct",
  "dojo/topic",
  "mui/search/SearchBar",
  "mui/search/SearchDbBar",
  "mui/util",
  "dojo/touch",
  "dojox/mobile/_css3"
], function(
  declare,
  domStyle,
  domConstruct,
  topic,
  SearchBar,
  SearchDbBar,
  util,
  touch,
  css3
) {
  return declare("mui.search.SearchBarDialogMixin", null, {
    //模块标识
    modelName: "",

    searchCancelEvt: "/mui/search/cancel",

    searchShowEvt: "/mui/searchbar/show",

    //重定向路径(均用于重定向数据库搜索)
    redirectURL: "",
    params: "",
    jumpToSearchUrl: true,
    needPrompt: true,
    showLayer: true,

    show: function(evt) {
      var created = false
      if (!this.searchNodeDiv) {
        var vars = {
          modelName: this.modelName,
          showLayer: this.showLayer,
          redirectURL: this.redirectURL,
          searchType: this.searchType,
          jumpToSearchUrl: this.jumpToSearchUrl,
          needPrompt: this.needPrompt
        }
        this.searchNodeDiv = domConstruct.create(
          "div",
          {
            className: "muiSearchBarDiv"
          },
          document.body
        )

        /**if (!isNaN(this.referOffesetTop)) {
          var tmpH = this.referOffesetTop
          domStyle.set(this.searchNodeDiv, {
            height: tmpH + "px"
          })
        }**/

        this._searchBar = this.chooseSearch(vars)
        this.searchNodeDiv.appendChild(this._searchBar.domNode)
        created = true
      }
      domStyle.set(this.searchNodeDiv, "display", "block")
      this.defer(function() {
        util.disableTouch(this.searchNodeDiv, touch.move)
        if (created) {
          this._searchBar.startup()
        }
        topic.publish(this.searchShowEvt, this)
      }, 10)
      if (!this.bindedEvent) {
        this.subscribe(this.searchCancelEvt, "hideSearchBar")
        //        this.subscribe("/mui/search/submit", "hideSearchBar")
        this.bindedEvent = true
      }
    },

    chooseSearch: function(vars) {
      if (!vars.redirectURL) {
        //全文搜索
        return new SearchBar(vars)
      } else {
        //数据库搜索
        vars.searchUrl = this.redirectURL + encodeURIComponent(this.params)
        return new SearchDbBar(vars)
      }
    },

    hideSearchBar: function(srcObj) {
      domStyle.set(this.searchNodeDiv, {
        display: "none"
      })
    }
  })
})
