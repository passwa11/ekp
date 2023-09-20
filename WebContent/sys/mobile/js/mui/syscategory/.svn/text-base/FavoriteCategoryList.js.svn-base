define(["dojo/_base/declare", "mui/category/CommonCategoryList"], function(
  declare,
  CategoryList
) {
  return declare("mui.syscategory.FavoriteCategoryList", [CategoryList], {
    modelName: null,

    //数据请求URL
    dataUrl:
      "/sys/person/sys_person_favorite_category/sysPersonFavoriteCategory.do?method=favorite&modelName=!{modelName}"
  })
})
