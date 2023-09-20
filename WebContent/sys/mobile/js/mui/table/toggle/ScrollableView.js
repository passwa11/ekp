define(["./View", "./NativeView"], function(
  View,
  NativeView
) {
  return dojoConfig._native ? NativeView : View
})

