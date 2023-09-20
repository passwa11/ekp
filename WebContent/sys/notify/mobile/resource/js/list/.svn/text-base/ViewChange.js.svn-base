define(["dojo/topic", "dijit/registry"], function(topic, registry) {
  // 订阅监听页签切换事件
  topic.subscribe("/dojox/mobile/viewChanged", function(view) {
    setTimeout(function() {
      // 获取当前页签索引
      var index = view.getIndexInParent()
      // 获取“快速审批”按钮所在的tabBar DOM对象
      var fastDom = document.getElementById("fastReviewTodo")
      if (fastDom) {
        // 只有当前页签为“待办”的时候才需要显示“快速审批”按钮
        fastDom.style.display = index == 0 ? "block" : "none"
        // 重置“快速审批”按钮宽高
        registry.byId("fastReviewTodo").resize()
        // 重置列表滑动区域宽高
        registry.byId("scrollView").resize()
      }
    }, 1)
  })
})
