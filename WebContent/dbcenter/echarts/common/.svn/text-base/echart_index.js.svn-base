
//所有图表
function allEcharts(jsonData,parentHtml){
		
    var  echartCount=0;
    
    
    for(var i=0;i<jsonData.length;i++){

    	var allEchartCount=0;
        
    	if(jsonData[i]&&jsonData[i].dbEchartsTotalForms){
    		allEchartCount+=jsonData[i].dbEchartsTotalForms.length;
    		if(jsonData[i].echartsTemplateFormChildrens){
    			for(var j=0;j<jsonData[i].echartsTemplateFormChildrens.length;j++){
    				if(jsonData[i].echartsTemplateFormChildrens[j]&&jsonData[i].echartsTemplateFormChildrens[j].dbEchartsTotalForms){
    					allEchartCount+=jsonData[i].echartsTemplateFormChildrens[j].dbEchartsTotalForms.length;
    				}
            	}
    		}
    	}
        	
        
        //类别
        var categoryItem = $('<div class="lui-theChart-main-content-right-data-categoryItem"/>').appendTo(parentHtml);
        var categoryItem_title= $('<div class="lui-theChart-main-content-right-data-categoryItem-title"/>');
        categoryItem_title.appendTo(categoryItem);
        var title_text= $('<span class="lui-theChart-main-content-right-data-categoryItem-title-text" style="cursor:pointer;" onclick="openUrl(\''+jsonData[i].fdId+'\')">'+jsonData[i].fdName+'('+allEchartCount+')</span>');
        title_text.appendTo(categoryItem_title);
        var title_icon=$('<span class="lui-theChart-main-content-right-data-categoryItem-title-icon"/>');
        title_icon.appendTo(categoryItem_title);

        var content_dataList=$('<div class="lui-theChart-main-content-dataLsit"/>');
        content_dataList.appendTo(categoryItem);
        
        //一级类别图表
        var dataList=$('<div class="lui-theChart-main-content-right-categoryItem-dataList"/>');
        dataList.appendTo(content_dataList);
        var clearfix="";
        if(jsonData[i].dbEchartsTotalForms.length>0){
            clearfix=$('<ul class="clearfix"/>');
            clearfix.appendTo(dataList);
        }


        for(var j=0;j<jsonData[i].dbEchartsTotalForms.length;j++){
        	
            echartCount++;//图表数累加

            var li=$('<li/>');
            li.appendTo(clearfix);
            var dataList_icon=$('<span class="lui-theChart-main-content-right-category-dataList-icon"/>');
            dataList_icon.appendTo(li);
            var dataList_text=$('<span class="lui-theChart-main-content-right-category-dataList-text" title="'+jsonData[i].dbEchartsTotalForms[j].docSubject+'" onclick="getview(\''+jsonData[i].dbEchartsTotalForms[j].fdId+'\')">'+jsonData[i].dbEchartsTotalForms[j].docSubject+'</span>');
            dataList_text.appendTo(li);
        }


        //二级类别
        for(var j=0;j<jsonData[i].echartsTemplateFormChildrens.length;j++){
            var dataList_secondLevel =$('<div class="lui-theChart-main-content-right-categoryItem-dataList-secondLevel clearfix"/>');
            dataList_secondLevel.appendTo(content_dataList);
            var secondLevel_title =$('<div class="lui-theChart-main-content-right-categoryItem-dataList-secondLevel-title"/>');
            secondLevel_title.appendTo(dataList_secondLevel);
            var secondLevel_title_box =$('<div class="lui-theChart-main-content-right-categoryItem-dataList-secondLevel-title-box"/>');
            secondLevel_title_box.appendTo(secondLevel_title);
            var span=$('<span/>');
            span.appendTo(secondLevel_title_box);
            var span_click=$('<span class="lui-theChart-main-content-right-categoryItem-dataList-secondLevel-title-box-text" style="cursor:pointer;" onclick="openUrl(\''+jsonData[i].echartsTemplateFormChildrens[j].fdId+'\')">'+jsonData[i].echartsTemplateFormChildrens[j].fdName+'('+jsonData[i].echartsTemplateFormChildrens[j].dbEchartsTotalForms.length+')</span>');
            span_click.appendTo(secondLevel_title_box);

            var ul_clearfix=$('<ul class="clearfix"/>');
            ul_clearfix.appendTo(dataList_secondLevel);

            //二级类别图表
            for(var z=0;z<jsonData[i].echartsTemplateFormChildrens[j].dbEchartsTotalForms.length;z++){

                echartCount++;//图表数累加

                var secondLi=$('<li/>');
                secondLi.appendTo(ul_clearfix);
                var second_dataList_icon=$('<span class="lui-theChart-main-content-right-category-dataList-icon"/>');
                second_dataList_icon.appendTo(secondLi);
                var second_dataList_text=$('<span class="lui-theChart-main-content-right-category-dataList-text" title="'+jsonData[i].echartsTemplateFormChildrens[j].dbEchartsTotalForms[z].docSubject+'" onclick="getview(\''+jsonData[i].echartsTemplateFormChildrens[j].dbEchartsTotalForms[z].fdId+'\')">'+jsonData[i].echartsTemplateFormChildrens[j].dbEchartsTotalForms[z].docSubject+'</span>');
                second_dataList_text.appendTo(secondLi);
            }
        }
    }
    return echartCount;
}


//我关注的图表
function getMyAttentionEcharts(jsonData,parentHtml){

    var  echartCount=0;

        //类别
        var categoryItem = $('<div class="lui-theChart-main-content-right-data-categoryItem"/>').appendTo(parentHtml);
        var categoryItem_title= $('<div class="lui-theChart-main-content-right-data-categoryItem-title"/>');
        categoryItem_title.appendTo(categoryItem);
        var title_text= $('<span class="lui-theChart-main-content-right-data-categoryItem-title-text">我关注的图表('+jsonData.length+')</span>');
        title_text.appendTo(categoryItem_title);
        var title_icon=$('<span class="lui-theChart-main-content-right-data-categoryItem-title-icon"/>');
        title_icon.appendTo(categoryItem_title);

        if(jsonData&&jsonData.length>0){
        	 var content_dataList=$('<div class="lui-theChart-main-content-dataLsit"/>');
             content_dataList.appendTo(categoryItem);
             
             //一级类别图表
             var dataList=$('<div class="lui-theChart-main-content-right-categoryItem-dataList"/>');
             dataList.appendTo(content_dataList);
             var clearfix="";
             if(jsonData.length>0){
                 clearfix=$('<ul class="clearfix"/>');
                 clearfix.appendTo(dataList);
             }

             for(var j=0;j<jsonData.length;j++){
                 echartCount++;
                 var li=$('<li/>');
                 li.appendTo(clearfix);
                 var dataList_icon=$('<span class="lui-theChart-main-content-right-category-dataList-icon"/>');
                 dataList_icon.appendTo(li);
                 var dataList_text=$('<span class="lui-theChart-main-content-right-category-dataList-text" title="'+jsonData[j].docSubject+'" onclick="getview(\''+jsonData[j].fdId+'\')">'+jsonData[j].docSubject+'</span>');
                 dataList_text.appendTo(li);
             }
        }else{
        	 var content_dataList=$('<div class="emptyStyle"/>');
             content_dataList.appendTo(categoryItem);
             var content_emptyIcon=$('<div class="emptyIcon"/>');
             content_emptyIcon.appendTo(content_dataList);
             var emptyText=$('<span class="emptyText">暂无数据</span>');
             emptyText.appendTo(content_dataList);
             
             var dataListHeight=$('.lui-theChart-main').height();
	     	    var emptyMarginTop=(dataListHeight-230)/2;
	 		   $('.emptyStyle').eq(0).css({
	 		     'margin-left':emptyMarginTop+'px',
	 		     'margin-top':'120px '
	 		   })
     		   
        }
       
        return echartCount;
}


//获取我创建的图表
function getMyCreateEcharts(jsonData,parentHtml){


    	var  echartCount=0;
        //类别
        var categoryItem = $('<div class="lui-theChart-main-content-right-data-categoryItem"/>').appendTo(parentHtml);
        var categoryItem_title= $('<div class="lui-theChart-main-content-right-data-categoryItem-title"/>');
        categoryItem_title.appendTo(categoryItem);
        var title_text= $('<span class="lui-theChart-main-content-right-data-categoryItem-title-text">我创建的图表('+jsonData.length+')</span>');
        title_text.appendTo(categoryItem_title);
        var title_icon=$('<span class="lui-theChart-main-content-right-data-categoryItem-title-icon"/>');
        title_icon.appendTo(categoryItem_title);
        
        if(jsonData&&jsonData.length>0){
        	 var content_dataList=$('<div class="lui-theChart-main-content-dataLsit"/>');
             content_dataList.appendTo(categoryItem);
             
             //一级类别图表
             var dataList=$('<div class="lui-theChart-main-content-right-categoryItem-dataList"/>');
             dataList.appendTo(content_dataList);
             var clearfix="";
             if(jsonData.length>0){
                 clearfix=$('<ul class="clearfix"/>');
                 clearfix.appendTo(dataList);
             }

             for(var j=0;j<jsonData.length;j++){
                 echartCount++;
                 var li=$('<li/>');
                 li.appendTo(clearfix);
                 var dataList_icon=$('<span class="lui-theChart-main-content-right-category-dataList-icon"/>');
                 dataList_icon.appendTo(li);
                 var dataList_text=$('<span class="lui-theChart-main-content-right-category-dataList-text" title="'+jsonData[j].docSubject+'" onclick="getview(\''+jsonData[j].fdId+'\')">'+jsonData[j].docSubject+'</span>');
                 dataList_text.appendTo(li);
             }
        }else{
        	var content_dataList=$('<div class="emptyStyle"/>');
            content_dataList.appendTo(categoryItem);
            var content_emptyIcon=$('<div class="emptyIcon"/>');
            content_emptyIcon.appendTo(content_dataList);
            var emptyText=$('<span class="emptyText">暂无数据</span>');
            emptyText.appendTo(content_dataList);
            
            var dataListHeight=$('.lui-theChart-main').height();
	     	    var emptyMarginTop=(dataListHeight-230)/2;
	 		   $('.emptyStyle').eq(0).css({
	 		     'margin-left':emptyMarginTop+'px',
	 		     'margin-top':'120px '
	 		   })
        }
       
        
        return echartCount;

}
