var dataView = render.parent;
if (render.preDrawing) { // 预加载执行
    sendMessage();
    return;
}
if (data == null || data.length == 0) {
    done();
    return;
}

var con_div = $('<div/>').attr('class', 'lui-person-n-slide').appendTo(dataView.element);

var con_div_1 = $('<div/>')
        .attr('class',
                'lui-person-swiper-container lui-person-swiper-container-horizontal lui-person-swiper-container-wp8-horizontal')
        .appendTo(con_div);
var con_ul = $('<ul/>').attr('class', 'lui-person-swiper-wrapper lui-person-n-slide-wrap').appendTo(con_div_1);
    con_ul.attr('style','list-style:none;transition: all 0.5s ease-in-out;left:0');
     
    for (var i = 0; i < data.length; i++) {
        buildDetail(data[i],con_ul,i);
    }
    
    var prevbtn = $('<div/>').attr('class', 'lui-person-swiper-button-prev lui-person-swiper-button-disabled').appendTo(con_div);
    var nextbtn = $('<div/>').attr('class', 'lui-person-swiper-button-next').appendTo(con_div);
   
setTimeout(function(){
  //滑动控制start
    var scrollWidth = con_div_1.width();
    var liNum = data.length;
     
    var liWidth = 150;
    var showNum = parseInt(scrollWidth/liWidth);
    var hidNum = liNum - showNum;
    var moveItem = 0;
    if(hidNum<=0){
        nextbtn.addClass('lui-person-swiper-button-disabled');
    }
    
    prevbtn.click(function(){
        if(moveItem>0){
            moveItem--
            con_ul.css("left",-(liWidth*moveItem));
            if(moveItem==0){
                prevbtn.addClass('lui-person-swiper-button-disabled');
            }
        } 
        if(moveItem<hidNum){
            nextbtn.removeClass('lui-person-swiper-button-disabled');
        }
        
    });
    
    nextbtn.click(function(){
        if(moveItem<hidNum){
            moveItem++
            con_ul.css("left",-(liWidth*moveItem));
            if(moveItem==hidNum){
                nextbtn.addClass('lui-person-swiper-button-disabled');
            }
        } 
        if(moveItem>0){
            prevbtn.removeClass('lui-person-swiper-button-disabled');
        }
    });
//滑动控制
},1000);

    
    
function buildDetail(item,ulCon,i){
    var con_li   = $('<li/>').attr('class','lui-person-swiper-slide').appendTo(ulCon);
        if(i==0){
            con_li.addClass('lui-person-swiper-slide-active');
        }else if(i==1){
            con_li.addClass('lui-person-swiper-slide-next');
        }
    var con_a    = $('<a/>').attr('class','lui-person-n-slide-item').attr('target','_blank').attr('class','lui-person-n-slide-item').appendTo(con_li);
    var con_div  = $('<div/>').attr('class','lui-person-n-slide-item-wrap').appendTo(con_a);
    var img_div  = $('<div/>').attr('class','lui-person-n-slide-item-img').appendTo(con_div);
                   $('<img/>').attr('src', item['imgurl']).appendTo(img_div);
                   $('<p/>').attr('class','lui-person-n-slide-item-name').text(item['name']).appendTo(con_div);
                   $('<p/>').attr('class','lui-person-n-slide-item-part').attr('style','height:20px;').text(item['deptinfo']).appendTo(con_div);

   if(item['showstar'] && item['showstar'] == "false") {
	   
   } else {
	   var star_div = $('<div/>').attr('class','lui-person-n-slide-item-stars').appendTo(con_div);
       $('<i/>').attr('class','lui-person-nsis-item').appendTo(star_div);
       $('<i/>').attr('class','lui-person-nsis-item').appendTo(star_div);
       $('<i/>').attr('class','lui-person-nsis-item').appendTo(star_div);
   }
   
        
        if(item['fdcontentlink']){
            con_li.attr('onclick', "gotoPersonPage('" + item['fdcontentlink']
            + "',event)");
        }           
       
}

//打开个人页面
window.gotoPersonPage = function (fdLink, event) {
    if (!fdLink || fdLink == null || fdLink.trim() == "") {
        return;
    }
    var goalConClass = event.target.className;
    if (goalConClass != null
            && (goalConClass.indexOf('lui-person-n-phone-item-slide') > -1 || goalConClass
                    .indexOf('lui-person-n-phone-item-icon') > -1)) {
        return;
    }
    Com_OpenWindow(fdLink, '_blank');
}


function sendMessage() {
    if (data != null && data.length > 0 && newDay > 0) {
        var more = false;
        for (var i = 0; i < data.length; i++) {
            if (showNewIcon(data[i])) {
                more = true;
                break;
            }
        }
        if (more) {
            if (dataView && dataView.parent) {
                var countInfo = {};
                countInfo.more = true;
                dataView.parent.emit('count', countInfo);
            }
        }
    }
}

done();
