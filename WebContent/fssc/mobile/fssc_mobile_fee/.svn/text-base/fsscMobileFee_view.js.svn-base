$(document).ready(function(){
 		$('.txtstrong').remove();
 		$('.inputsgl').removeClass();
 	});

    function closeLdRighModal() {
        $('.ld-right-selector').removeClass('ld-right-selector-show')
        var timer = setTimeout(function() {
            $('.ld-right-mask').removeClass('ld-right-mask-show')
            clearInterval(timer)
        }, 500)
        removeTouchListener()
    }
    $('.ld-right-mask').click(function(e) {
        if ((e.target || e.srcElement).id == "ld-right-mask") {
            closeLdRighModal()
        }
    })
    if (isdingding()) {
        dd.ready(function() {
            dd.ui.webViewBounce.disable();
        });
    }
    // 禁用body滚动
    function forbiddenScroll() {
        $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
    }
    // 启用滚动
    function ableScroll() {
        $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
    }
    // 弹出新建行程弹窗
    $('.ld-newApplicationForm-travelInfo-btn').click(function() {
        $('.ld-travel-detail-body').addClass('ld-travel-detail-body-show')
            // $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
        forbiddenScroll()
    })
    $('.ld-save-btn').click(function() {
            $('.ld-travel-detail-body').removeClass('ld-travel-detail-body-show')
                // $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
            ableScroll()
        })
        // 费用明细
    $('.ld-newApplicationForm-costInfo-btn').click(function() {
        $('.ld-entertain-main-body').addClass('ld-entertain-main-body-show')
            // $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
        forbiddenScroll()
    })
    $('.ld-entertain-detail-btn').click(function() {
            $('.ld-entertain-main-body').removeClass('ld-entertain-main-body-show')
                // $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
            ableScroll()
        })
        // 新增账户
    $('.ld-newApplicationForm-account-btn').click(function() {
        $('.ld-addAccount-body').addClass('ld-addAccount-body-show')
            // $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
        forbiddenScroll()
    })
    $('.ld-addAccount-btn').click(function() {
            $('.ld-addAccount-body').removeClass('ld-addAccount-body-show')
                // $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
            ableScroll()
        })
        // 新增行程
    $('.ld-newApplicationForm-trip-btn').click(function() {
        $('.ld-addTravel-body').addClass('ld-addTravel-body-show')
            // $('.ld-newApplicationForm').addClass('ld-newApplicationForm-hidden')
        forbiddenScroll()
    })
    $('.ld-addTrip-btn').click(function() {
        $('.ld-addTravel-body').removeClass('ld-addTravel-body-show')
            // $('.ld-newApplicationForm').removeClass('ld-newApplicationForm-hidden')
        ableScroll()
    })

   /*************************************************************************
   * 查看明细
   *************************************************************************/
  function viewDetail(id,index){
	  var detail_id='TABLE_DL_'+id;
      $("div[id='div"+index+"_"+id+"'][class='ld-entertain-main-body']").addClass('ld-entertain-main-body-show');
      forbiddenScroll();
  }
    /*************************************************************************
     * 返回主表
     *************************************************************************/
    function rtnMain(id,index){
    	$('#div'+index+'_'+id).attr('class','ld-entertain-main-body');
        ableScroll();
    }

  
