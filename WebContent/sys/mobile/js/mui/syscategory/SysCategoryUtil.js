/**
 * 全局分类工具类
 */
define([
  "mui/tabbar/CreateButton",
  "mui/syscategory/SysCategoryMixin"
], function(CreateButton, SysCategoryMixin) {
  return {
    // props:{createUrl:"",modelName:"",showFavoriteCate:true...}
    create: function(props) {
      var claz = CreateButton.createSubclass([SysCategoryMixin])
      new claz(props)._selectCate()
    }
  }
})
