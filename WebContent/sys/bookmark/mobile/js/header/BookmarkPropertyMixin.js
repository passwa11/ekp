/**
 * 筛选静态数据源
 */
define(["dojo/_base/declare"], function(declare) {
  return declare("sys.bookmark.BookmarkPropertyMixin", null, {
	  modelName: "com.landray.kmss.sys.bookmark.model.SysBookmarkCategory",  
    filters: [           
      {
          filterType: "FilterDatetime",
          type: "date",
          name: "docCreateTime",
          subject: "收藏时间"
      }      
    ]
  })
})