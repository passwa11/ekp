 var totlePeson = 0; //添加数量
    var orgIDs = [];  //已经存在的人员ID
    var htmls = [];   //需要添加的人员——HTML信息
   
    //删除人员
    function onRemovePerson(xmfid)
    {
    	var child = document.getElementById(xmfid); //获取子标签
    	var xmfids = xmfid.split("_"); //分隔
    	var fid = xmfids[1];  //获取id
    	
    	if(child != '' && child != undefined)
    	{
    		var parent = child.parentNode;
    		
    		if(parent != '' && parent != undefined)
    		{
    			parent.removeChild(child); 
    			document.getElementById('xmfv_'+fid).setAttribute('value',''); //ID为空
    			document.getElementById('xmfn_'+fid).setAttribute('value',''); //名称为空
    		}
    	}
    }
    //地址本选择后，统计选取人员
    function afterAddress()
    {
    	htmls = [];  //清楚
    	totlePeson = 0;  //清楚
    	
    	var orgName = document.getElementsByName("cateRelationsFdOrgName")[0].value; //选取的人员名称
    	var orgId = document.getElementsByName("cateRelationsFdOrgIds")[0].value;   //人员ID
    	var orgNames = [];
    	var orgIds = [];
    	
    	if(orgName != '' && orgId != '')
    	{
    		orgNames = orgName.split(';');
    		orgIds = orgId.split(';');
    	}
    	
    	
    	//添加表格中不存在的用户
    	for(var i = 0; i < orgIds.length; i++)
    	{
    		var id = orgIds[i];
    		if(orgIDs.indexOf(id) < 0)
    		{
    			var li = document.createElement('li');
    			var xmfid = "'"+"xmf_"+id+"'";
    			var fid = "'"+id+"'";
    			li.setAttribute('class','mf_item');
			    li.setAttribute('role','option');
			     li.setAttribute('aria-selected','false');
			     li.setAttribute('id',"xmf_"+id);
	             var liHtml = 
  			              orgNames[i] +'<a class="xmf_value" href="javascript:void(0);" onclick="onRemovePerson('+xmfid+');">X</a>'
					 		//disabled 使内容不随form一起提交，BeanUtils包从1.6升级到1.9.2后会对表单字段进行检查，出现[]中无下标的问题
  			             + '<input name="undefined_values[]" disabled class="mf_value" type="hidden" value="'+id+'"></input>' ;
    			li.innerHTML = liHtml;
    			htmls.push(li);
   			    totlePeson++;
    		}
    	}
    	
    }
    
    //批量添加
   function addMultiPerson()
   {
	   afterAddress();
	   
      //地址本中选择的人员加入
	  for(var i = 0; i < totlePeson; i++)
	 {
		var newrow =  DocList_AddRow('personsTable'); //加入行
	    var table = document.getElementById("personsTable");//获取表格
	    
	    //以下是：获取已经存在的表格中的人员ID放入orgIDs中
        if(table != '' && table != undefined)
        {
        	var tbody =  table.getElementsByTagName("tbody")[0]; //获取tbody
        	if(tbody != '' && tbody != undefined)
        	{
        		var tr = tbody.getElementsByTagName("tr");  //获取行
        		
        		if(tr != '' && tr != undefined)
        		{
        			var trLength = tr.length;  //行数
        			var td = tr[trLength-1].getElementsByTagName('td')[1]; //第二列
        			var ol = td.getElementsByTagName('ol')[0]; //第二列
        			var input = td.getElementsByClassName('input')[0];
        			var  inputselectsgl = td.getElementsByClassName('inputselectsgl')[0]; //获取inputselectsgl 
        			
        			//以下设置为了可以校验，否则会提示字段不能为空信息
        			if(inputselectsgl != '' && inputselectsgl != 'undefined')
        			{
        				//设置ID值
        				var selInput = inputselectsgl.getElementsByTagName('input')[0];
        				if(selInput != '' && selInput != undefined)
        				{
        					selInput.setAttribute('value',htmls[i].getElementsByTagName('input')[0].value);
        					selInput.setAttribute('id',"xmfv_"+htmls[i].getElementsByTagName('input')[0].value); //用于删除
        				}
        				
        				//设置值，可以随意放入，为了通过校验
        				var selClassInput = inputselectsgl.getElementsByClassName('input')[0];
            			if(selClassInput != '' && selClassInput != undefined)
            			{
            				var selValueTag = selClassInput.getElementsByTagName('input')[0];
            				if(selValueTag != '' && selValueTag != undefined)
            				{
            					selValueTag.setAttribute('value','Landray');
            					selValueTag.setAttribute('id',"xmfn_"+htmls[i].getElementsByTagName('input')[0].value); //用于删除
            				}
            			}
        			}
        			
        			
        			if(ol != '' && ol != undefined)
        			{
        				ol.appendChild(htmls[i]);//行，加入人员信息
        			}
        			

        		}

        	}
        }
	  }
    	
      totlePeson = 0;
      closeBox();
   }
    
    //计算已经存在表格中的人员
    function existPerson()
    {
        orgIDs = [];
        var table = document.getElementById("personsTable");//获取表格
        
        //以下是：获取已经存在的表格中的人员ID放入orgIDs中
        if(table != '' && table != undefined)
        {
        	var tbody =  table.getElementsByTagName("tbody")[0]; //获取tbody
        	if(tbody != '' && tbody != undefined)
        	{
        		var tr = tbody.getElementsByTagName("tr");  //获取行
        		if(tr != '' && tr != undefined)
        		{
        			var trLength = tr.length;  //行数
        			
        			for(var i = 1; i < trLength; i++ )
        			{
        				var td = tr[i].getElementsByTagName('td')[1]; //第二列
        				var mfValue = td.getElementsByClassName('mf_value'); //人员ID
        				if(mfValue != '' && mfValue != undefined)
        				{
        					var orgId = mfValue[0].value;
        					orgIDs.push(orgId);
        				}
        			}

        		}

        	}
        }
    }
    
    //批量添加弹出框
   function popBox() {
        var popBox = document.getElementById("popBox");
        var popLayer = document.getElementById("popLayer");
        var poBoxTable = document.getElementById("popBoxTable");
        popBox.style.display = "block";
        popLayer.style.display = "block";
        poBoxTable.style.display = "block";
    	//document.getElementById('multiTabel').style.display='block';
        existPerson();

   }
   
    //关闭批量添加框
   function closeBox() 
    {
       var popBox = document.getElementById("popBox");
       var popLayer = document.getElementById("popLayer");
       var poBoxTable = document.getElementById("popBoxTable");
       popBox.style.display = "none";
       popLayer.style.display = "none";
       poBoxTable.style.display = "none";
    //   document.getElementById('multiTabel').style.display='none';
    }
    