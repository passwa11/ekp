if (data == null || data.length == 0) {
	done();
	return;
}

var extend = param.extend?param.extend:'';

// 变量赋值
var element = render.parent.element;
var boxStr=''

switch(extend) {
    case 'hoverPoint':
        boxStr="<div class=\"lui_tagProtlet_hot lui_tagProtlet_hot_hoverPoint\"></div>";
       break;
    case 'bg':
       boxStr="<div class=\"lui_tagProtlet_hot lui_tagProtlet_hot_bg\"></div>";
       break;
    case 'hoverBg':
       boxStr="<div class=\"lui_tagProtlet_hot lui_tagProtlet_hot_hoverBg\"></div>";
        break; 
    case '3d':
        boxStr="<div class=\"lui_tagProtlet_hot lui_tagProtlet_hot_3d\"></div>";
        break;  
    default:
       boxStr="<div class=\"lui_tagProtlet_hot lui_tagProtlet_hot_border\"></div>";
} 
element.append(boxStr)
function renderHotTagList(data){
    var hotTagListStr = '';
    if(extend=="hoverPoint"){
        for(var i=0;i<data.length;i++){
            hotTagListStr += '<a title="' + Com_HtmlEscape(data[i].fdName) +'"  target="_blank" href="'+data[i].fdUrl+'"><i></i>'+ Com_HtmlEscape(data[i].fdName) +'</a>'  
        }
    }else{
        for(var i=0;i<data.length;i++){
            hotTagListStr += '<a title="' + Com_HtmlEscape(data[i].fdName) +'"  target="_blank" href="'+data[i].fdUrl+'">'+ Com_HtmlEscape(data[i].fdName) +'</a>'  
        }
    }
    return hotTagListStr
}
$(element).find('.lui_tagProtlet_hot').html(renderHotTagList(data))
if(extend=='3d'){
    setTimeout(function(){
		$(element).find('.lui_tagProtlet_hot').css({
			width:$('.lui_tagProtlet_hot').height+'px'
		})
        var i=0;
        var oTag=null;
        
        oDiv=$(element).find('.lui_tagProtlet_hot')[0];
        
        aA=oDiv.getElementsByTagName('a');
        
        for(i=0;i<aA.length;i++)
        {
            var color=setColor()
            aA[i].style.color=color;
            oTag={};
            
            oTag.offsetWidth=aA[i].offsetWidth;
            oTag.offsetHeight=aA[i].offsetHeight;
            
            mcList.push(oTag);
        }
        
        sineCosine( 0,0,0 );
        
        positionAll();
        
        oDiv.onmouseover=function ()
        {
            active=true;
        };
        
        oDiv.onmouseout=function ()
        {
            active=false;
        };
        
        oDiv.onmousemove=function (ev)
        {
            var oEvent=window.event || ev;
            
            mouseX=oEvent.clientX-(oDiv.offsetLeft+oDiv.offsetWidth/2);
            mouseY=oEvent.clientY-(oDiv.offsetTop+oDiv.offsetHeight/2);
            
            mouseX/=5;
            mouseY/=5;
        };
        
        setInterval(update, 30);
    },100)
}
var radius = 120;
var dtr = Math.PI/180;
var d=300;
var mcList = [];
var active = false;
var lasta = 1;
var lastb = 1;
var distr = true;
var tspeed=10;
var size=250;

var mouseX=0;
var mouseY=0;

var howElliptical=1;

var aA=null;
var oDiv=null;

function update(){
	var a;
	var b;
	if(active){
		a = (-Math.min( Math.max( -mouseY, -size ), size ) / radius ) * tspeed;
		b = (Math.min( Math.max( -mouseX, -size ), size ) / radius ) * tspeed;
	}else{
		a = lasta * 0.98;
		b = lastb * 0.98;
	}
	
	lasta=a;
	lastb=b;
	
	if(Math.abs(a)<=0.01 && Math.abs(b)<=0.01){
		return;
	}
	
	var c=0;
	sineCosine(a,b,c);
	for(var j=0;j<mcList.length;j++){
		var rx1=mcList[j].cx;
		var ry1=mcList[j].cy*ca+mcList[j].cz*(-sa);
		var rz1=mcList[j].cy*sa+mcList[j].cz*ca;
		
		var rx2=rx1*cb+rz1*sb;
		var ry2=ry1;
		var rz2=rx1*(-sb)+rz1*cb;
		
		var rx3=rx2*cc+ry2*(-sc);
		var ry3=rx2*sc+ry2*cc;
		var rz3=rz2;
		
		mcList[j].cx=rx3;
		mcList[j].cy=ry3;
		mcList[j].cz=rz3;
		
		per=d/(d+rz3);
		
		mcList[j].x=(howElliptical*rx3*per)-(howElliptical*2);
		mcList[j].y=ry3*per;
		mcList[j].scale=per;
		mcList[j].alpha=per;
		
		mcList[j].alpha=(mcList[j].alpha-0.6)*(10/6);
	}
	
	doPosition();
	depthSort();
}

function depthSort()
{
	var i=0;
	var aTmp=[];
	
	for(i=0;i<aA.length;i++)
	{
		aTmp.push(aA[i]);
	}
	
	aTmp.sort
	(
		function (vItem1, vItem2)
		{
			if(vItem1.cz>vItem2.cz)
			{
				return -1;
			}
			else if(vItem1.cz<vItem2.cz)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	);
	
	for(i=0;i<aTmp.length;i++)
	{
		aTmp[i].style.zIndex=i;
	}
}

function positionAll(){
	var phi=0;
	var theta=0;
	var max=mcList.length;
	var i=0;
	var aTmp=[];
	var oFragment=document.createDocumentFragment();
	//随机排序
	for(i=0;i<aA.length;i++){
		aTmp.push(aA[i]);
	}
	
	aTmp.sort(function (){
			return Math.random()<0.5?1:-1;
		});
	
	for(i=0;i<aTmp.length;i++){
		oFragment.appendChild(aTmp[i]);
	}
	
	oDiv.appendChild(oFragment);
	for( var i=1; i<max+1; i++){
	    if( distr ){
			phi = Math.acos(-1+(2*i-1)/max);
			theta = Math.sqrt(max*Math.PI)*phi;
		}else{
			phi = Math.random()*(Math.PI);
			theta = Math.random()*(2*Math.PI);
		}
		//坐标变换
		mcList[i-1].cx = radius * Math.cos(theta)*Math.sin(phi);
		mcList[i-1].cy = radius * Math.sin(theta)*Math.sin(phi);
		mcList[i-1].cz = radius * Math.cos(phi);
		
		aA[i-1].style.left=mcList[i-1].cx+oDiv.offsetWidth/2-mcList[i-1].offsetWidth/2+'px';
		aA[i-1].style.top=mcList[i-1].cy+oDiv.offsetHeight/2-mcList[i-1].offsetHeight/2+'px';
	}
}
function doPosition(){
	var l=oDiv.offsetWidth/2;
	var t=oDiv.offsetHeight/2;
	for(var i=0;i<mcList.length;i++){
		aA[i].style.left=mcList[i].cx+l-mcList[i].offsetWidth/2+'px';
		aA[i].style.top=mcList[i].cy+t-mcList[i].offsetHeight/2+'px';
		
		aA[i].style.fontSize=Math.ceil(12*mcList[i].scale/2)+8+'px';
		
		aA[i].style.filter="alpha(opacity="+100*mcList[i].alpha+")";
		aA[i].style.opacity=mcList[i].alpha;
	}
}

function sineCosine( a, b, c){
	sa = Math.sin(a * dtr);
	ca = Math.cos(a * dtr);
	sb = Math.sin(b * dtr);
	cb = Math.cos(b * dtr);
	sc = Math.sin(c * dtr);
	cc = Math.cos(c * dtr);
}
function setColor(){
    var chars = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'];
    var colorStr='#';
    for(var i=0;i<6;i++){
        colorStr+=chars[Math.floor(Math.random()*16)];
    }
    return colorStr;
}

done()