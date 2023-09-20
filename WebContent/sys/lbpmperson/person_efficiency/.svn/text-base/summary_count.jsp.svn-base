<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript">
		Com_IncludeFile("jquery.js");
</script>
<script type="text/javascript">
function loadSummaryCount(){
	$.ajax({     
	     type:"post",    
	     url:"${LUI_ContextPath}/sys/lbpmperson/SysLbpmPersonSummary.do?method=listAllCountSummary",     
	     async:true,
	     dataType : 'json',
	     success:function(data){
	    	 $("#summary_draft").html(data.draftCount);
	    	 $("#summary_create").html(data.createCount);
	    	 $("#summary_approval").html(data.approvalCount);
	    	 $("#summary_approved").html(data.approvedCount);
	    	 $("#summary_abandon").html(data.abandonCount);
	    	 
	  	 }
    });
}
function clickHref(id){
	window.parent.setPersonMainIframe(id);
}
</script>
<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/lbpmperson/style/css/docreater.css" />
<style>
.lui_form_button {
  border: none;
  color: #fff;
  cursor: pointer;
  text-decoration: none;
  background-color: #51B5EB;
}

	.head_div_wrap{padding: 0px 10px }
	.head_div_wrap:after{
		content: '';
		display: table;
		visibility: hidden;
		clear: both;
	}
	.head_div_box{
		width:20%;
		float:left;
	}
	.head_div_box > .item{
		margin-right: 10px;
		height:80px;
		padding: 10px 0px;
		text-align: center;
		border: 1px solid rgba(0,0,0,.15);
		background-color: transparent;
		transition-duration: .3s;
		background-color: #fff;
		cursor: pointer;
	}
	.head_div_box > .item:hover{
		border-color: #e0e0e0;
		background-color: #f8f8f8;
		-webkit-box-shadow: 0 1px 5px rgba(0,0,0,.15);
			 -moz-box-shadow: 0 1px 5px rgba(0,0,0,.15);
				-ms-box-shadow: 0 1px 5px rgba(0,0,0,.15);
				 -o-box-shadow: 0 1px 5px rgba(0,0,0,.15);
						box-shadow: 0 1px 5px rgba(0,0,0,.15);
	}
	.head_div_wrap > .head_div_box:last-child > .item,
	.head_div_wrap > .head_div_box.last-child > .item{ margin-right: 0; }
	.head_div_box_number{
		margin-top: 15px;
		font-size: 28px;
		display: block;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	.head_div_wrap > .head_div_box:nth-child(5n+1) > .item{
	border-color: #4285f4;
}
.head_div_wrap > .head_div_box:nth-child(5n+2) > .item{
	border-color: #9e6ce5;
}
.head_div_wrap > .head_div_box:nth-child(5n+3) > .item{
	border-color: #14cfbc;
}
.head_div_wrap > .head_div_box:nth-child(5n+4) > .item{
	border-color: #fa6f00;
}
.head_div_wrap > .head_div_box:nth-child(5n+5) > .item{
	border-color: #fb6e91;
}

.head_div_wrap > .head_div_box:nth-child(5n+1) > .item:hover{
	background-color: rgba(66,133,244,.05);
	-webkit-box-shadow: 0 1px 8px rgba(66,133,244,.35);
		 -moz-box-shadow: 0 1px 8px rgba(66,133,244,.35);
			-ms-box-shadow: 0 1px 8px rgba(66,133,244,.35);
			 -o-box-shadow: 0 1px 8px rgba(66,133,244,.35);
					box-shadow: 0 1px 8px rgba(66,133,244,.35);
}
.head_div_wrap > .head_div_box:nth-child(5n+2) > .item:hover{
	background-color: rgba(158,108,229,.05);
	-webkit-box-shadow: 0 1px 8px rgba(158,108,229,.35);
		 -moz-box-shadow: 0 1px 8px rgba(158,108,229,.35);
			-ms-box-shadow: 0 1px 8px rgba(158,108,229,.35);
			 -o-box-shadow: 0 1px 8px rgba(158,108,229,.35);
					box-shadow: 0 1px 8px rgba(158,108,229,.35);
}
.head_div_wrap > .head_div_box:nth-child(5n+3) > .item:hover{
	background-color: rgba(20,207,188,.05);
	-webkit-box-shadow: 0 1px 8px rgba(20,207,188,.35);
		 -moz-box-shadow: 0 1px 8px rgba(20,207,188,.35);
			-ms-box-shadow: 0 1px 8px rgba(20,207,188,.35);
			 -o-box-shadow: 0 1px 8px rgba(20,207,188,.35);
					box-shadow: 0 1px 8px rgba(20,207,188,.35);
}
.head_div_wrap > .head_div_box:nth-child(5n+4) > .item:hover{
	background-color: rgba(250,111,0,.05);
	-webkit-box-shadow: 0 1px 8px rgba(250,111,0,.35);
		 -moz-box-shadow: 0 1px 8px rgba(250,111,0,.35);
			-ms-box-shadow: 0 1px 8px rgba(250,111,0,.35);
			 -o-box-shadow: 0 1px 8px rgba(250,111,0,.35);
					box-shadow: 0 1px 8px rgba(250,111,0,.35);
}
.head_div_wrap > .head_div_box:nth-child(5n+5) > .item:hover{
	background-color: rgba(251,110,145,.05);
	-webkit-box-shadow: 0 1px 8px rgba(251,110,145,.35);
		 -moz-box-shadow: 0 1px 8px rgba(251,110,145,.35);
			-ms-box-shadow: 0 1px 8px rgba(251,110,145,.35);
			 -o-box-shadow: 0 1px 8px rgba(251,110,145,.35);
					box-shadow: 0 1px 8px rgba(251,110,145,.35);
}
.head_div_wrap > .head_div_box:nth-child(5n+1) .head_div_box_number{ color: #4285f4;}
.head_div_wrap > .head_div_box:nth-child(5n+2) .head_div_box_number{ color: #9e6ce5;}
.head_div_wrap > .head_div_box:nth-child(5n+3) .head_div_box_number{ color: #14cfbc;}
.head_div_wrap > .head_div_box:nth-child(5n+4) .head_div_box_number{ color: #fa6f00;}
.head_div_wrap > .head_div_box:nth-child(5n+5) .head_div_box_number{ color: #fb6e91;}

	.head_div_box_text{
		margin-top: 2px;
		padding: 0 5px;
		text-align: center;
		color: #999;
		font-size: 12px;
		display: block;
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
@media \0screen {
	.head_div_box > .item{
		border: 1px solid #4285f4;
	}
}
</style>
			<div class="head_div_wrap">
				<div class="head_div_box" onclick="clickHref('/draft');">
					<div class="item">
						<span class="head_div_box_number" id="summary_draft">0</span>
						<span class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.draft')}</span>
					</div>
				</div>
				<div class="head_div_box" onclick="clickHref('/create');">
					<div class="item">
						<span class="head_div_box_number" id="summary_create">0</span>
						<span class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.create')}</span>
					</div>
				</div>
				<div class="head_div_box" onclick="clickHref('/approve');">
					<div class="item">
						<span class="head_div_box_number" id="summary_approval">0</span>
						<span class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.approval')}</span>
					</div>
				</div>
				<div class="head_div_box" onclick="clickHref('/approved');">
					<div class="item">
						<span class="head_div_box_number" id="summary_approved">0</span>
						<span class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.approved')}</span>
					</div>
				</div>
				<div class="head_div_box last-child" onclick="clickHref('/abandon');">
					<div class="item">
						<span class="head_div_box_number" id="summary_abandon">0</span>
						<span class="head_div_box_text">${lfn:message('sys-lbpmperson:lbpmperson.chars.head.abandon')}</span>
					</div>
				</div>
			</div>
			
		<script>
		Com_AddEventListener(window,'load',function(){
			loadSummaryCount();
		});
		</script>

		