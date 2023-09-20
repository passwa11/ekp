define(["dojo/_base/declare", "mui/i18n/i18n!sys-mobile"], function(
  declare,
  msg
) {
  return declare("mui.category.FavoriteCateMixin", null, {
    memory: [
      {
        moveTo: "favoriteCateView",
        text: msg["mui.mobile.favoriteCateView"],
        selected: true
      },
      {moveTo: "allCateView", text: msg["mui.mobile.allCateView"]}
    ]
  })
})
