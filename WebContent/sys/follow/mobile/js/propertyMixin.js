define(["dojo/_base/declare"], function(declare) {
  return declare("sys.follow.propertyMixin", null, {
    modelName: "com.landray.kmss.sys.follow.model.SysFollowDoc",
    filters: [
      {
        filterType: "FilterSearch",
        name: "keyword",
        subject: "标题："
      }
    ]
  })
})
