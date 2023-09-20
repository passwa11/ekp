$(function () {
  // 左侧栏面板列表上下折叠
  // $('.beh-aside-accordion-body').first().slideDown();
  $('.beh-aside-accordion-heading>a').click(function() {
    $('.beh-aside-accordion-heading>a').addClass('beh-collapsed');
    $(this).removeClass('beh-collapsed');
    $('.beh-aside-accordion-body').slideUp();
    $(this).parent().next('dd').stop().slideDown();
  })
  $('.beh-aside-accordion-body>a').click(function() {
    $('.beh-aside-accordion-body>a').removeClass('active');
    $(this).addClass('active');
  })
})