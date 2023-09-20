define("mui/fixed/Fixed", ["./ScrollableFixed", "./NativeFixed"], function(
  ScrollableFixed,
  NativeFixed
) {
  return dojoConfig._native ? NativeFixed : ScrollableFixed
})
