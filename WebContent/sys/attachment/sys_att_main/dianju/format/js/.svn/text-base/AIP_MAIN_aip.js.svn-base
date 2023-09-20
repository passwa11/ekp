/****************************************************************************************************
全局变量

httpaddr    印章服务器地址，第一个参数为印章服务器的ip+端口+路径，一般只需要修改ip+端口即可。

*****************************************************************************************************/

//var httpaddr="http://127.0.0.1:8080/Seal/general/interface/";
var httpaddr="http://59.110.230.54:8092/ESS/api/serviceForAip/";
/****************************************************************************************************
方法名：OpenFile					打开文档
参  数：url							可以是服务器http路径：http://127.0.0.1/test.pdf
									也可以是本地文件路径：c://test.pdf
									也可以是文件流：http://127.0.0.1/GetFile.aspx


*****************************************************************************************************/
//windows环境初始化
function initAipProperties(){
	var ocx = document.getElementById("HWPostil1");
	//隐藏菜单栏
	ocx.ShowDefMenu = 0;
	//隐藏打开及保存按钮
	ocx.HideMenuItem(3);
}


//国产化下初始化方法
function NotifyCtrlReady(){
	//alert("国产化下初始化方法");
	var downloadUrl = document.getElementsByName('downloadUrl')[0].value;
	var openType = document.getElementsByName('openType');

	var ocx = document.getElementById("HWPostil1");
	// 打开
	ocx.setCompositeVisible("file_open",false);
	//保存
	ocx.setCompositeVisible("file_save",false);
	//视图
	ocx.setViewPreference("navigator","thumbnail");

	if(openType != '' && openType != undefined && openType[0].value == 'online') {
		openFile(downloadUrl);
	} else {
		openIncludeFile(downloadUrl);
	}

}


// 打开文件
function openFile(downloadUrl) {
	var ocx = document.getElementById("HWPostil1");
	var result = ocx.LoadFile(downloadUrl);
	//显示缩略图及文档窗体
	ocx.ShowView = 	136;

	if (result == 1) {
		fangDaCustomize(200);
		//addseal(1);
	} else {
		alert("加载文件失败");
	}

}

function openIncludeFile(dianjuDownloadUrl) {
		var ocx = document.getElementById("HWPostil1");
		var result = ocx.LoadFile(dianjuDownloadUrl);
		//显示缩略图及文档窗体
		ocx.ShowView = 	136;
		if (result == 1) {
			fangDaCustomize(200);
		} else {
			alert("加载文件失败");
		}

}

function loadFile(){
	var ocx = document.getElementById("HWPostil1");
	var IsOpen = ocx.LoadFile("");
	if (IsOpen != 1) {
		alert("打开文档失败！");
	}
}



/************************************登录****************************************
usertype    用户类型：0 测试用户，1 本地key用户，2 服务器key用户，3 服务器口令用户
other      预留参数：
					当usertype为1,2时，值为用户真实姓名，可以为空获则取证书用户名。
					当usertype为3时，值为口令内容。
*********************************************************************************/
function login(usertype,other){
	var ocx = document.getElementById("HWPostil1");
	if(usertype == 0){
		var islogin=ocx.Login("HWSEALDEMO",4,65535,"DEMO","");
	}else if(usertype == 1){
		var islogin = ocx.Login(other,1,65535,"","");
	}else if(usertype == 2){
		var islogin = ocx.Login(other,3,65535,"",httpaddr);
	}else if(usertype == 3){
		var stri = "Use-RemotePfx-Login:"+other;
		var strj = stri.length+1;
		var islogin = ocx.LoginEx(httpaddr, stri, strj);
	}else{
		alert("用户类型参数错误，以测试用户身份登录");
		var islogin = ocx.Login("HWSEALDEMO**",4,65535,"DEMO","");
	}
}

/*********************************
*********判断当前是否登录**********
*********************************/
function isLogin(){
	var ocx = document.getElementById("HWPostil1");
	var isLogincode = ocx.IsLogin();
	if(isLogincode == 0){
		alert("未登录");
	}else{
		alert("已登录");
	}
}
/**********保存文件*********
url         保存的文件路径
savetype    保存类型
filecode    0 保存不关闭
		    1 保存且关闭
***************************/
function saveto(url,savetype,filecode){
	var ocx = document.getElementById("HWPostil1");
	//var issave = ocx.SaveTo(url, savetype, filecode);
	alert(requestUrl);
	var uploadUrl = "http://10.10.3.144:8080/ekp/sys/attachment/sys_att_main/sysAttMain.do?method=updateCloudRelation";
	var issave = ocx.SaveTo(requestUrl, "ofd", 0);
	debugger;
	alert(issave);
	if (issave == 0) {
		alert("保存失败!");
	}
}
function getHost(){
	var host = location.protocol.toLowerCase()+"//" + location.hostname;
	if(location.port!='' && location.port!='80'){
		host = host+ ":" + location.port;
	}
	return host;
}
/**********上传文件**********
filepath    上传服务器路径
filecode    上传的字符串变量
***************************/
function saveFileServer(filepath,fileName){
	var ocx = document.getElementById("HWPostil1");
	ocx.SetValue("ADD_FORCETYPE_VALUE2","32768");//保存成ofd、pdf
	//ocx.SetValue("SET_CURRENT_COOKIE","COOKIE:LOGIN_TEST=somevalue;KEY=asdasd\r\n");  //设置cookie
	ocx.HttpInit();  //初始化HTTP引擎。
	ocx.HttpAddPostString("FileName", fileName);
	ocx.HttpAddPostCurrFile("FileBlod");  //设置上传当前文件,文件标识为FileBlod。
	var ispost = ocx.HttpPost(encodeURI(getHost() + filepath));  //上传数据。
	if (ispost != 'success') {
		alert("文档上传失败！错误代码：" + ispost);
	} else {
		alert("上传成功");
	}
}

/*****************打印文件****************
printflag    0 不打印节点信息
			 1 打印节点信息
showdlg      0 不显示打印对话框，直接打印
			 1 显示打印对话框
*****************************************/
function printdoc(printflag,showdlg){
	var ocx = document.getElementById("HWPostil1");
	var isprint = ocx.PrintDoc(printflag,showdlg);
}



/******************************关闭文件****************************
type        0 关闭文档，不保存当前修改
			1 关闭文档，保存当前修改
			2 如果文档已经被修改，显示保存文档对话框，否则直接关闭
			其他 关闭文档，保存当前修改
******************************************************************/


function closedoc(type){
	var ocx = document.getElementById("HWPostil1");
	var isclose = ocx.CloseDoc(type);
	if (isclose == 0) {
		alert("关闭文档失败失败!");
	}
}



/*********************************************************************************************************

方法名：AddSeal						盖章或手写
参  数：number					1手动盖章，2 坐标盖章，3 文字盖章,4骑缝章章,5手写


**********************************************************************************************************/
function addseal(number) {
	var ocx = document.getElementById("HWPostil1");
	if(String(navigator.platform).indexOf("Linux") > -1){
		if(number == 1){
			// 检察是否使用UK
			UkeyIsLogin();
			ocx.Login("", 1, 65535, "", "");
			ocx.CurrAction = 2568;
		}else if(number == 2){
			//页面1-3页盖章 
			var a = ocx.sealSignBySetting("{\"range\":\"0-2\",\"xpos\":\"100\",\"ypos\":180}");
		}else if(number == 3){
			//1-3页对文字“1”定位盖章
			//key：关键字 string
			//keyindex:关键字序号，从1开始，负序号表示逆序， 即-1 表示最后一个，-2表示倒数第二个,关键字不为空时有效 int
			var a = ocx.sealSignBySetting("{\"startpage\":\"0\",\"endpage\":\"2\",\"key\":\"1\"}");
		}else if(number == 4){
			//页面2-6该骑缝章  
			//firstpagepercent：首页印章百分比，int  
			//pagesealcoordinates：骑缝章坐标，int（1表示1/5，2表示2/5，3表示3/5，4表示4/5）
			//coordinates:骑缝章坐标，float（百分比）
			//pagesealdirection：骑缝位置 string right 右骑缝，bottom 下骑缝, left 左骑缝，top 上骑缝
			//pagesealtype:骑缝类型 string multi 多页，double 双页
			var a = ocx.sealSignBySetting("{\"startpage\":\"1\",\"endpage\":\"5\",\"firstpagepercent \":\"30\",\"pagesealcoordinates\":2,\"sealsigntype\":\"3\"}");
		}
		else if(number == 5){
			ocx.CurrAction = 264;
		}
	}else{
		if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
			
		}else{
			var searchstring="10000:10000:0";
			var str=searchstring.split(":");
			var num=ocx.PageCount;
			if(number == 1){
				// 检察是否使用UK
				UkeyIsLogin();
				//远程签章
				//var islogin = ocx.Login("", 3, 65535, "", httpaddr);
				//本地签章
				var islogin = ocx.Login("", 1, 65535, "", "");
				ocx.CurrAction = 2568;
			}else if(number == 2){
				//坐标盖章 
				var a = ocx.AddQifengSeal(0,0+","+str[0]+",0,"+str[1]+",50,"+str[2],"","AUTO_ADD_SEAL_FROM_PATH");
			}else if(number == 3){
				var searchstring="罪";
				ocx.AddQifengSeal(0,"AUTO_ADD:0,"+num+",0,0,1,"+searchstring+")|(0,","","AUTO_ADD_SEAL_FROM_PATH");
			}else if(number == 4){
				var page="";
				for(i=1;i<num;i++){
					page+=i+",";
				}
				if(num>1){
					var bl=100/(num);
					var isseal=ocx.AddQifengSeal(0,0+",25000,1,3,"+bl+","+page,"","AUTO_ADD_SEAL_FROM_PATH");
					if(isseal!=1){
						return "-100";
					}else{
						return "1";
					}
				}else{
					return "-100";
				}
				
			}else if(number == 5){
				ocx.CurrAction = 264;
			}
		}
		
	}
	
}
//判断是否插入Ukey
function UkeyIsLogin(){
	var ocx = document.getElementById("HWPostil1");
	var strNum = ocx.GetSerialNumber();
	if(!strNum) {
		alert("请插入UKey");
	}
	// }else{
	// 	alert("已插入UKey");
	// }
}
/******************图片水印*************
参数:xmlParam：表示要添加水印的属性(type：类型取值范围text为文字水印，image为图片水印，
patterntype：位置类型tile(平铺)，
imagefile：图片地址，content：文字内容，
rotate：角度，
opacity不透明度，
fontname：字体名称，
fontsize：字体大小，
fontcolor：字体颜色，
zoom：缩放百分比，
offsetx：X偏移，
offsety:Y偏移,)。


*/
function addTrackInfoImage(){
	var ocx = document.getElementById("HWPostil1");
	if(String(navigator.platform).indexOf("Linux") > -1){
		var xmlstr="<trackinfo type=\"image\" patterntype=\"center\" imagefile=\"/opt/dianju/test.bmp\" rotate=\"0\" opacity=\"100\"  fontname=\"simsun\"  fontsize=\"10.5\" fontcolor=\"#00ff00\" zoom=\"1\" offsetx=\"0\" offsety=\"0\"/>";
		ocx.addTrackInfo(xmlstr);
	}else{
		if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
			
		}else{
			ocx.Login("sys_admin", 5, 65535, "", "");
			ocx.WaterMarkAlpha=57;
			ocx.WaterMarkAngle=-3900;
			ocx.WaterMarkMode=6;
			ocx.WaterMarkPosX=0;
			ocx.WaterMarkPosY=-7000;
			ocx.WaterMarkTextColor=0;
			ocx.WaterMarkTextOrPath="c:\\水印.jpg"
			ocx.WaterMarkTxtHOrImgZoom=120;
		}
		
	}
}

/***************文字水印****************
参数:xmlParam：表示要添加水印的属性(type：类型取值范围text为文字水印，image为图片水印，
patterntype：位置类型tile(平铺)，
imagefile：图片地址，content：文字内容，
rotate：角度，
opacity不透明度，
fontname：字体名称，
fontsize：字体大小，
fontcolor：字体颜色，
zoom：缩放百分比，
offsetx：X偏移，
offsety:Y偏移,)。


*/
function addTrackInfoText(){
	var ocx = document.getElementById("HWPostil1");
	if(String(navigator.platform).indexOf("Linux") > -1){
		var xmlstr="<trackinfo type=\"text\" patterntype=\"center\" content=\"AAAAAA\" rotate=\"0\" opacity=\"100\"    fontsize=\"10.5\" fontcolor=\"#00ff00\" zoom=\"1\" offsetx=\"0\" offsety=\"0\"/>";
		ocxocx.addTrackInfo(xmlstr);
	}else{
		if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
			
		}else{
			ocx.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
			ocx.WaterMarkAlpha=50;//透明度值范围：1到63，愈大愈透明
			ocx.WaterMarkAngle=-450;//旋转角度，此处角度单位：0.1度
			ocx.WaterMarkMode=2;//显示模式：1：居中 (文字)2：平铺 (文字)3：居中带阴影(文字)4：平铺带阴影(文字)7：指定像素值
			ocx.WaterMarkPosX=80;//水印横坐标
			ocx.WaterMarkPosY=80;//水印纵坐标
			ocx.WaterMarkTextColor=0;//水印颜色
			ocx.WaterMarkTextOrPath="文档水印";//水印内容
			//document.all.HWPostil1.WaterMarkTextOrPath="C:/11.bmp";
			ocx.WaterMarkTxtHOrImgZoom=100;//文字大小或图片缩放比例
		}
		
	}
	
}

/***************档案水印****************
参数:
nodename：节点名称，
filename：文件名称或路径，
pageindex：页码，
xpos：X偏移，
ypos:Y偏移)。


*/
function addTrackInfoFiles(){
	var ocx = document.getElementById("HWPostil1");
	if(String(navigator.platform).indexOf("Linux") > -1){
			ocx.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
			ocx.SetDocProperty("a1","时是时时时时室");
			ocx.SetDocProperty("a2","[0001]第2345号");
			ocx.SetDocProperty("a3","1月16日11时58分收到");
			ocx.SetDocProperty("a4","1月16日分收到");
			var num=ocx.PageCount;
			for(var i=0;i<num;i++){
				var ret = ocx.AddDocNumSeal("{\"nodename\":\"bbb\",\"filename\":\"/home/dj/多行.sel\",\"pageindex\":\""+i+"\",\"xpos\":\"40000\",\"ypos\":\"5000\"}");
			}
			
	}else{
		if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
			
		}else{
			ocx.Login("HWSEALDEMO**", 4, 65535, "DEMO", "");
		//	ocx.DocProperty("a1")="111222222222"; //客户端添加标记印章信息
		//	ocx.DocProperty("a2")="2021"; //客户端添加标记印章信息
		//	ocx.DocProperty("a3")="05"; //客户端添加标记印章信息
		//	ocx.DocProperty("a4")="05ddd"; //客户端添加标记印章信息
			var num=ocx.PageCount;
			var searchstring='';
		    for(var i=1; i<=num; i++){ 
			   searchstring+=i+",";
			}
			var str = searchstring.match(/(\S*),/)[1];
			var seal=document.all.HWPostil1.AddQifengSeal(0,"0,40000,9,5,5000,"+str+",C:\\Users\\ThinkPad\\Desktop\\多行.sel","NO_SIGN_SEAL","AUTO_ADD_SEAL_FROM_PATH");
			alert(seal);
		}
		
	}
	
}
/******************插入编号******************
pcName			节点或区域的名字(不可以重复)
lPageIndex   	在第几页添加(从0开始)
lType			1 链接

				2 手写区域
				3 编辑区域
				4 自动扩充区域

				5 自动扩充区但不扩充页面
lPosx			坐标X的数值，单位基于当前坐标系
lPosy			坐标Y的数值，单位基于当前坐标系
lWidth			节点宽度

lHeight			节点高度
**********************************************/
function insertNo(pcName,lPageIndex,lType,lPosx,lPosy,lWidth,lHeight){
	var ocx = document.getElementById("HWPostil1");
	var islogin = ocx.Login("HWSEALDEMO**",4,65535,"DEMO","");
	ocx.InsertNote(pcName,lPageIndex,lType,lPosx,lPosy,lWidth,lHeight);
	ocx.SetValue(pcName, ":PROP:FRONTCOLOR:0");//设置文字颜色
	ocx.SetValue(pcName,":PROP::LABEL:2");//只读,
	ocx.SetValue(pcName,":PROP:FONTSIZE:25");//设置文字大小
	ocx.SetValue(pcName, "000312");
	
	
	//
}
//删除水印
function clearTrackInfo(){
	var ocx = document.getElementById("HWPostil1");
	
	if(String(navigator.platform).indexOf("Linux") > -1){
		ocx.clearTrackInfo();
	}else{
		if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
			
		}else{
			ocx.Login("HWSEALDEMO",4,65535,"DEMO","");
			ocx.WaterMarkTextOrPath="";
		}
		
	}
	
}


/******************查找文字******************
searchtext		要查的文字
				"" 弹出查找对话框开始新的查找
MatchCase   	0 区分大小写
				1 不区分大小写
FindNext		0 从头查找
				1 查找下一个
				
*********************************************/
function searchText(searchtext,MatchCase,FindNext){
	var ocx = document.getElementById("HWPostil1");
	ocx.SearchText(searchtext,MatchCase,FindNext);
}

/******************插入编辑区******************
pcName			节点或区域的名字(不可以重复)
lPageIndex   	在第几页添加(从0开始)
lType			1 链接
				2 手写区域
				3 编辑区域
				4 自动扩充区域
				5 自动扩充区但不扩充页面
lPosx			坐标X的数值，单位基于当前坐标系
lPosy			坐标Y的数值，单位基于当前坐标系
lWidth			节点宽度
lHeight			节点高度
**********************************************/
function insertbjq(pcName,lPageIndex,lType,lPosx,lPosy,lWidth,lHeight){
	var ocx = document.getElementById("HWPostil1");
	var islogin = ocx.Login("HWSEALDEMO**",4,65535,"DEMO","");
	ocx.InsertNote(pcName,lPageIndex,lType,lPosx,lPosy,lWidth,lHeight);
	ocx.SetValue(pcName, "<bold>主送：</>刘磊<br/><bold>抄送：</>胡兵 、张磊、刘平");
	//
}

//编辑区插入内容
function insertText(pcName){
	var ocx = document.getElementById("HWPostil1");
	var islogin = ocx.Login("HWSEALDEMO**",4,65535,"DEMO","");
	ocx.SetValue(pcName, "");
	ocx.SetValue(pcName, "222");
	
}

//获取编辑内容
function GetValueEx(pcName){
	var ocx = document.getElementById("HWPostil1");
	var a=ocx.GetValueEx(pcName,2,"",0,"");//获取编辑区文本值
    alert(a);
}




/***********************插入附件***********************
lPageStartIndex		添加页的起始位置,0,1,2,..
					= -1 弹出选择页码的的对话框
					大于等于当前页数的数字,添加到尾部
pcFileName			null 弹出文件选择对话框
					文件名称 可以是Http服务器上面的文件 
					"STRDATA:ss" ss为AIP文件的base64值 
******************************************************/
function mergeFile(lPageStartIndex,pcFileName){
	var ocx = document.getElementById("HWPostil1");
	var islogin = ocx.Login("HWSEALDEMO**",4,65535,"DEMO","");
	ocx.MergeFile(lPageStartIndex,pcFileName);
}

//设置只读
function setRedonly(){
	var ocx = document.getElementById("HWPostil1");
	ocx.SetValue("rcvBgsNbyj",":PROP::LABEL:3");
	
}

//设置编辑 
function setBJ(){
	var ocx = document.getElementById("HWPostil1");
	ocx.SetValue("rcvBgsNbyj",":PROP::LABEL:0");
	
}


//设置全屏
function quanPing(){
	var ocx = document.getElementById("HWPostil1");
	ocx.ShowFullScreen=1;
	
}

//退出全屏
function closeQuanPing(){
	var ocx = document.getElementById("HWPostil1");
	ocx.ShowFullScreen=0;
	
}

//放大
function fangDa(){
	var ocx = document.getElementById("HWPostil1");
	ocx.SetPageMode(1,150);
	
}

//放大
function fangDaCustomize(size){
	var ocx = document.getElementById("HWPostil1");
	ocx.SetPageMode(1,size);

}

//缩小
function suoXiao(){
	var ocx = document.getElementById("HWPostil1");
	ocx.SetPageMode(1,90);
	
}



/****************************************************************************************************

方法名：ShowToolBar						设置工具栏
参  数：SetLog		

*****************************************************************************************************/
function ShowToolBar(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.ShowToolBar=SetLog;
}
/****************************************************************************************************

方法名：ShowDefMenu						设置菜单
参  数：SetLog							设置状态：0 隐藏，1 显示

*****************************************************************************************************/
function ShowDefMenu(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.ShowDefMenu=SetLog;
}
/****************************************************************************************************

方法名：ShowScrollBarButton				设置滚动条
参  数：SetLog							设置状态：2 隐藏滚动条，1 隐藏滚动条的工具栏，0 显示滚动条

*****************************************************************************************************/
function ShowScrollBarButton(SetLog) {
	var AipObj = document.getElementById("HWPostil1");
	AipObj.ShowScrollBarButton=SetLog;
}

function showDHL() {
	var ocx = document.getElementById("HWPostil1");
	if(String(navigator.platform).indexOf("Linux") > -1){
		ocx.setCompositeVisible("navigator",true);
	}else{
		if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
			
		}else{
			ocx.SetValue("SET_TEMPFLAG_MODE_DEL","256");//不显示左侧导航窗口（两段代码一块用）
		}
		
	}
	
	//document.getElementById("HWPostil1").SetValue("TEMP_FLAG_NOLEFTVIEW","256");//不显示左侧导航窗口（两段代码一块用）
	//document.getElementById("HWPostil1").SetValue("SET_TEMPFLAG_MODE_DEL","256");//不显示左侧导航窗口
}
function noneDHL() {
	var ocx = document.getElementById("HWPostil1");
	if(String(navigator.platform).indexOf("Linux") > -1){
		ocx.setCompositeVisible("navigator",false);
	}else{
		if(navigator.userAgent.indexOf("Firefox")>0 || navigator.userAgent.indexOf("Chrome")>0){
			
		}else{
			ocx.SetValue("TEMP_FLAG_NOLEFTVIEW","256");//不显示左侧导航窗口（两段代码一块用）
			ocx.SetValue("SET_TEMPFLAG_MODE_ADD","256");//不显示左侧导航窗口
		}
		
	}
	
}


function InsertYwm(){
   var ocx = document.getElementById("HWPostil1");
   var islogin = ocx.Login("HWSEALDEMO**",4,65535,"DEMO","");
   ocx.InsertPicture("id001","BARCODEDATA:12121212121",0,0,0,13172836);
}

//添加二维码
function InsertPdf417(){
   var ocx = document.getElementById("HWPostil1");
   var islogin = ocx.Login("HWSEALDEMO**",4,65535,"DEMO","");
   ocx.InsertPicture("id001","BARCODEDATA:12121212121",0,5000,5000,200);
}

//添加方形码
function InsertQr(){
   var ocx = document.getElementById("HWPostil1");
   var islogin = ocx.Login("HWSEALDEMO**",4,65535,"DEMO","");
   ocx.InsertPicture("id001","BARCODEDATA:12121212121",0,15000,5000,13107500);
}
