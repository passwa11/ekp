
function stopTouch(e) {
    //e.preventDefault();
}
function addTouchListener(){
   document.body.addEventListener("touchmove", stopTouch, {
     passive: false,
     capture: true
   });
}
function removeTouchListener(){
   document.body.removeEventListener("touchmove", stopTouch, {
     capture: true
   });
}
function isdingding() {
    var ua = navigator.userAgent.toLowerCase();
    return ua.indexOf("dingtalk") >= 0;
  }
