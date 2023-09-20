		var curList=[{"name":"WpsOAAssist","addonType":"wps","online":"true","url":Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/wps/oaassist/WpsOAAssist/"},{"name":"EtOAAssist","addonType":"et","online":"true","url":Com_Parameter.serverPrefix+"/sys/attachment/sys_att_main/wps/oaassist/EtOAAssist/"}]//在线模式配置参考
        var localList=[];
        var publishIndex=0;
        var publishUnIndex=0;
        /*获取用户本地全部加载项的接口是必须要的，这个接口做了判断，
        ** 如果58890端口未启动，会先去启动这个端口
        */
        //加载项安装函数
        function installWpsAddin(){
            WpsAddonMgr.getAllConfig(function(e){
                if(!e.response||e.response.indexOf("null")>=0){//本地没有加载项，直接安装
                    if(curList.length>0){
                        installWpsAddinOne();
                    } 
                }else{//本地有加载项，先卸载原有加载项，然后再安装
                    localList=JSON.parse(e.response)
                    unInstallWpsAddinOne()
                }
                   
            })
        }
        //安装单个加载项
        function installWpsAddinOne(){
            WpsAddonMgr.enable(curList[publishIndex],function(e){
                if(e.status){
                    console.log(e.msg)
                }else{
                    console.log("安装成功")
                }
                publishIndex++;
                if(publishIndex<curList.length){
                    installWpsAddinOne();
                }
            })
        }
        //卸载单个加载项
        function unInstallWpsAddinOne(){
            WpsAddonMgr.disable(localList[publishUnIndex],function(e){
                if(e.status){
                    console.log(e.msg)
                }else{
                    console.log("卸载成功")
                }
                publishUnIndex++;
                if(publishUnIndex<localList.length){
                    unInstallWpsAddinOne();
                }else{
                    if(curList.length>0){
                        installWpsAddinOne();
                    } 
                }
            })
        }
        //复制结束，
        //将复制的这部分内容放到业务系统用户调起WPS前必须访问的一个页面中。
        //页面加载完成后，自动安装
