define(["./DocView", "./NativeDocView"], function(DocView, NativeDocView) {
  return dojoConfig._native ? NativeDocView : DocView
})
