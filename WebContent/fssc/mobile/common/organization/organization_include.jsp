<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<script>
	var Org_JS_InitData = {
			"LUI_ContextPath":"${LUI_ContextPath}"
	};
</script>
<script src="${LUI_ContextPath}/fssc/mobile/common/organization/organization.js"></script>
 <div class="ld-right-mask" id="ld-right-mask">
        <div class="ld-right-selector">
            <div class="ld-right-selector-top">
                <div class="ld-right-selector-head">
                    <div id="parentNode"><i></i><span onclick="parentRighModal();">${lfn:message('list.lever.up')}</span></div>
                    <span class="department"></span>
                    <span onclick="closeLdRighModal()">${lfn:message('button.cancel')}</span>
                </div>
                <div class="ld-right-selector-search">
                    <div class="searchbox">
                        <i class="search"></i>
                        <input type="search" name="keyword" placeholder="${lfn:message('button.search')}">
                    </div>
                </div>
            </div>
            <div class="ld-right-selector-list">
                <div class="ld-right-selector-local">${lfn:message('fssc-mobile:fssc.mobile.dept.person')}</div>
                <ul id="daixuan">
                </ul>
                <div class="ld-right-modal-footer">
                    <div class="ld-right-modal-footer-box">
                        <ul id="yixuan">
                            
                        </ul>
                    </div>
                    <div class="ld-right-modal-footer-btn" onclick="confirmChecked();">${lfn:message('button.ok') }</div>
                </div>
            </div>
        </div>
        <input type="hidden" name="currentFeildId" />
        <input type="hidden" name="currentFeildName" />
        <input type="hidden" name="currentMulti" />
        <input type="hidden" name="currentOrgType" />
        <input type="hidden" name="currentParentId" />
    </div>
