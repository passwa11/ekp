
seajs.use(['lui/jquery', 'lui/util/str', 'lui/dialog','lui/topic','lui/framework/router/router-utils','lui/framework/module'],
		function($, strutil, dialog, topic,routerUtils,Module){
	
            topic.subscribe('spa.change.reset',function (evt) {
            	
            	
                if(evt.value.j_path=='/docCategory'){
                    LUI.pageOpen( evt.target.Com_Parameter.ContextPath+"dbcenter/echarts/echart_total.jsp?docCategory="+evt.value.docCategory+"#cri.q=dbEchartsTemplate:"+evt.value.docCategory,'_rIframe');
                }
            });


	var module = Module.find('dbEcharts');
	
	/**
	 * 内置参数:  $var:安装模块时传入变量；$lang:安装模块时传入多语言信息；$function:需要注册成全局的方法；$router : 全局路有对象； $ondestroy:销毁函数
	 * 内置参数的声明无顺序要求，你可以这样function($var,$function){}、或者这样function($lang,$var){}，但是参数名字必须使用$var、$lang、$function
	 */
	module.controller(function($var, $lang, $function,$router){
		//路由配置
		$router.define({
            startpath : '/echart_index',
			routes : [
                {
                    path : '/overview', //图表中心概览
                    action : function(){
                        openPage($var.$contextPath +'/dbcenter/echarts/db_echars_ui/statistics_overview.jsp?rwd=true');
                    }
                },
                {
                    path : '/echart_index', //图表中心首页
                    action : function(){
                        openPage($var.$contextPath +'/dbcenter/echarts/echart_index.jsp?rwd=true');
                    }
                },
                {
                    path : '/custom',//统计图表
                    action : function(){
                    	openPage($var.$contextPath +'/dbcenter/echarts/db_echars_ui/statistics_custom.jsp?rwd=true');
                    }
                },
			{
                path : '/index',//统计图表
                action : function(){
                	openPage($var.$contextPath +'/dbcenter/echarts/db_echars_ui/statistics_index.jsp?rwd=true');
                }
			},{
                    path : '/list',//统计图表
                    action : function(){
                    	openPage($var.$contextPath +'/dbcenter/echarts/db_echars_ui/statistics_list.jsp?rwd=true');
                    }
			},{
                    path : '/atlas',//统计图表
                    action : function(){
                    	openPage($var.$contextPath +'/dbcenter/echarts/db_echars_ui/statistics_atlas.jsp?rwd=true');
                    }
			},{
				path : '/management', // 后台管理
				action : {
					type : 'pageopen',
					options : {
						url : $var.$contextPath + '/sys/profile/moduleindex.jsp?nav=/dbcenter/echarts/tree.jsp',
						target : '_rIframe'
					}
				}
			}
			]
		});

        $function.openPreview = function(id,dic){
            if (seajs.data.env.isSpa) {
                topic.publish("nav.operation.clearStatus", null);
                
                topic.publish("spa.change.values", {
                    value : {
                        'docCategory' : id
                    }
                });
                
                if(dic){
                	LUI.pageOpen( $var.$contextPath +"/dbcenter/echarts/echart_total.jsp#cri.q=dbEchartsTemplate:"+id+"&rwd=true",'_rIframe');
           	 	}
               
                
            }
        };

	});
	
});