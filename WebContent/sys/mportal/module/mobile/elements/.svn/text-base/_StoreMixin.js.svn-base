define([
  "dojo/_base/declare",
  "mui/util",
  "mui/store/JsonRest",
  "dojox/mobile/_StoreListMixin",
  "dojo/store/Memory",
  "./_CacheMixin"
], function(declare, util, JsonRest, _StoreListMixin, Memory, _CacheMixin) {
  return declare(
    "sys.mportal.module._StoreMixin",
    [_StoreListMixin, _CacheMixin],
    {
      datas: null,

      buildRendering: function() {
        if (this.datas) {
          this.store = new Memory({data: this.datas})
        }
        if (this.url) {
          this.store = new JsonRest({
            idProperty: "fdId",
            target: util.formatUrl(this.url)
          })
        }

        this.inherited(arguments)
      },

      generateList: function() {},

      onComplete: function(datas) {
        if (!this.datas) {
          this.datas = datas
        }

        this.inherited(arguments)
      }
    }
  )
})
