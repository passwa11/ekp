<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
    <head>
	 <style>
        /* 新增发票模态框 */
         .ld-invoice-modal{
		        height:100%;
		        width:100%;
		        position: fixed;
		        top:0;
		        left:0;
		        background: rgba(0,0,0,.5);
		        z-index:999;
	        }
	        .ld-invoice-modal-invoice{
		        position:absolute;
		        top:50%;
		        transform:translateY(-50%);
		        width:100%;
		        padding:0 0.3rem;
		        box-sizing: border-box;
	        }
	        .ld-invoice-modal-invoice  img {
	           width:100%;
	           object-fit: contain;
	        }
	        .ld-invoice-modal i{
	            height: 0.6rem;
	            width:0.6rem;
	            position: absolute;
	            right:0.3rem;
	            top:0.72rem;
	            background:url('${LUI_ContextPath}/fssc/mobile/resource/images/close.png') no-repeat center center;
	            background-size: contain;
	        }
    </style>
</head>
<div id="att_view" class="ld-invoice-modal" onclick="hideAtt();" style="display:none;">
    <div class="ld-invoice-modal-invoice">
        <img id="img_view" src="" alt="">
    </div>
</div>
