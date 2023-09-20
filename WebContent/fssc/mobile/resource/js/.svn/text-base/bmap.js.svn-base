    var placeList=[];
 	var currentCity;
 	var currentCityId;
 	
 	    document.write("<script language=javascript src='http://api.map.baidu.com/api?ak=O7ARrRAGAPBNBFAWIBGBHA6Y&v=2.0'></script>");

 	
    //获取城市列表
    function getDefaultPlaceInit(callback) {
 		var fdCompanyId = $("[name='fdCompanyId']").val();
 	  	 $.ajax({
	           type: 'post',
	           url:Com_Parameter.ContextPath + "fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=getCityData",
	           data: {"fdCompanyId":fdCompanyId},
	       }).success(function (data) {
	      	 var rtn = JSON.parse(data);
	      	 if(rtn.result=='success'){
	      		this.placeList=rtn.data;
	      		setDefaultPlace(this.placeList,callback);
	      	  }
	       }).error(function () {
	           console.log("获取城市失败");
	   	})
   }
   function setDefaultPlace(placeList,callback){
	   console.log("placeList:"+placeList);
 	      let geo = new BMap.Geolocation();
 	      let _ = this;
 	      geo.getCurrentPosition(function(r){
 	        if(this.getStatus()==BMAP_STATUS_SUCCESS){
 	        	//调试信息
 	        	//$("[name=fdClaimantName]").val(JSON.stringify(r.address));
 	        	console.log("定位到当前城市："+r.address.city);
 	        	if(r.address.city!=""){
 	  	          for(let i of placeList){
 	  	            if(i.name.indexOf(r.address.city)>-1){
 	  	            	currentCity=i.name;
 	  	            	currentCityId=i.id;
 	  	            	console.log("找到数据库城市："+currentCity+"-"+currentCityId);
 	 	        	  if(callback){
 		        		  callback(); 
 		        	  }
 	  	            	break;
 	  	            }
 	  	          }	
 	        	}
 	        }else{
 	    	        switch( this.getStatus() )
 	    	        {
 	    		        case 2:
 	    		        	console.log( '位置结果未知 获取位置失败.' );
 	    		        break;
 	    		        case 3:
 	    		        	console.log( '导航结果未知 获取位置失败..' );
 	    		        break;
 	    		        case 4:
 	    		        	console.log( '非法密钥 获取位置失败.' );
 	    		        break;
 	    		        case 5:
 	    		        	console.log( '对不起,非法请求位置  获取位置失败.' );
 	    		        break;
 	    		        case 6:
 	    		        	console.log( '对不起,当前 没有权限 获取位置失败.' );
 	    		        break;
 	    		        case 7:
 	    		        	console.log( '对不起,服务不可用 获取位置失败.' );
 	    		        break;
 	    		        case 8:
 	    		        	console.log( '对不起,请求超时 获取位置失败.' );
 	    		        break;
 	    		        
 	    	        }

 	        }
 	      })
 	      }