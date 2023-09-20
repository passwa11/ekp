define([
    "dojo/_base/declare",
    "mui/search/SearchBar",
    "mui/i18n/i18n!sys-mobile",
    "mui/util",
    "dojo/topic",
    "dojo/dom",
    "dojo/dom-style",
], function (declare, SearchBar, Msg, util, topic, dom, domStyle) {

    return declare("mui.tag.DialogSearchBar", [SearchBar], {

        //搜索请求地址
        searchUrl: "",

        //搜索结果直接挑转至searchURL界面
        jumpToSearchUrl: false,

        //搜索关键字
        keyword: "",

        //例外类别id
        exceptValue: '',

        //提示文字
        placeHolder: Msg['mui.search.search'],

        //是否需要输入提醒
        needPrompt: false,

        orgType: null,

        _onSearch: function(evt) {
            this._eventStop(evt)
            this.set("keyword", this.searchNode.value)
            if (this.keyword) {
                var t = {}
                if (!this.needPrompt) {
                    if (this.searchUrl) {
                        var url = this.searchUrl;
                        url = util.urlResolver(url, this)
                        var cv = window.TAG_CATEGORY_VALUE;
                        var cl = window.TAG_CATEGORY_LEVEL;
                        var parent = cv? (cv[cl]? cv[cl] : "") : "";
                        var parentId = parent.fdId? parent.fdId : "";
                        if(parentId) {
                            url += "&parentId=" + parentId;
                        }
                        url = util.formatUrl(url);
                        t.url = url
                    }
                    t.keyword = decodeURIComponent(this.keyword)
                    t.modelName = this.modelName
                    t.searchFields = this.searchFields
                    t.docStatus = this.docStatus
                }
                var categorySelect = dom.byId("tag_DialogCategorySelect");
                domStyle.set(categorySelect, "display", "none");
                domStyle.set(this.domNode, "width", "100%");
                topic.publish("/mui/search/submit", this, t)
            }

            return false
        },

        _onCancel: function () {
            this.inherited(arguments)
        }

    });
});
