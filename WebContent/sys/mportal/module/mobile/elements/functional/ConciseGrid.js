/**
 * 简版格子布局
 */
define(["dojo/_base/declare", "./Grid", "./item/ConciseGridItem"], function(
  declare,
  Grid,
  ConciseGridItem
) {
  return declare("", [Grid], {
    itemClass: ConciseGridItem
  })
})
