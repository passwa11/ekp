define(["dojo/_base/declare", "mui/category/ScrollableCategoryPath", "dojo/dom-construct"], function(
  declare,
  CategoryPath,
  domConstruct
) {
  var header = declare(
    "mui.listcategory.ListCategoryPath",
    [CategoryPath],
    {
      modelName: null,

      //获取详细信息地址
      detailUrl:
        "/sys/category/mobile/sysSimpleCategory.do?method=pathList&cateId=!{curId}&modelName=!{modelName}",
        
      _createTitleNode: function() {
            var itemTag = domConstruct.create("div", { className: 'muiCatePathTitleItem' }, this.containerNode)
            var textNode = domConstruct.create(
              "div",
              {
                className: ""
              },
              itemTag
            )
            var labelNode = domConstruct.create(
              "span",
              {
                className: "muiListCatePathTitle"
              },
              textNode
            )
            labelNode.innerHTML = this.titleNode

            this.connect(itemTag, "onclick", function() {
              this._chgHeaderInfo(this, {})
              topic.publish("/mui/category/changed", this, {parentId: ""})
            })
            return itemTag
          },
    	  
    }
  )
  return header
})
