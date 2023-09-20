/**
 * 登录
 * 用于匿名页眉 @author 吴进 by 20191112
 * @returns
 */
function __sys_login() {
	location.href=Com_Parameter.ContextPath+'login.jsp';
}

/**
* 退出登录
* @return
*/  
function __sys_logout(){
	seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
		dialog.confirm(theaderMsg["home.logout.confirm"],function(value){
			if(value){
				location.href=Com_Parameter.ContextPath+"logout.jsp";
			}				
		});
	});
}

