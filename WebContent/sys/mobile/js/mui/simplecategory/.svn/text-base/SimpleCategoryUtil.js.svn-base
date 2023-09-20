/**
 * 简单分类工具类
 */
define([
  "mui/tabbar/CreateButton",
  "mui/simplecategory/SimpleCategoryMixin"
], function(CreateButton, SimpleCategoryMixin) {
  return {
    // props:{createUrl:"",modelName:"",showFavoriteCate:true...}
    create: function(props) {
      var claz = CreateButton.createSubclass([SimpleCategoryMixin])
      new claz(props)._selectCate()
    }
  }
})
