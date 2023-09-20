define(["dojo/_base/declare", "./Knowledge"], function(
  declare,
  Knowledge
) {
  return declare("kms.common.module.ModuleMixin", null, {
    views: [Knowledge]
  })
})
