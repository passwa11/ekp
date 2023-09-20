<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!-- 筛选器-主面板 start -->
<div class="fy-filter-container" id="fy-filter-container">
    <c:if test="${industryList !=null && industryList.size() > 0}">
        <div class="fy-filter-row">
            <div class="fy-filter-title">${lfn:message('third-mall:kmReuseCommon.fdIndustry')}：</div>
            <div class="fy-filter-comp">
                <div class="fy-filter-select-collapse">
                    <div class="fy-filter-comp-btn-wrap">
                        <span class="single-btn choice-btn"
                              onclick="singleandMultiple(this,'industry')">${lfn:message('third-mall:kmReuseCommon.manyChoose')}</span>
                        <span
                                class="collapse-btn industry-collapse"> ${lfn:message('third-mall:kmReuseCommon.more')} <i
                                class="fy-icon fy-icon-arrow-down"></i> <i
                                class="fy-icon fy-icon-arrow-down active"></i>
                                            </span>
                    </div>

                    <ul class="fy-filter-select-list single">
                        <li class="fy-filter-select-item industry" id="unlimited"
                            onclick="commonSelect(this,'industry');"><a
                                href="javascript:;"
                                class="all selected"> ${lfn:message('third-mall:kmReuseCommon.noLimit')} </a></li>
                        <c:forEach items="${industryList}" var="industry">
                            <li class="fy-filter-select-item industry"
                                id="${industry.fdId}" onclick="commonSelect(this,'industry');">
                                <a href="javascript:;" onfocus="this.blur();"> <i
                                        class="fy-icon fy-icon-checkbox"></i> <c:out value="${industry.text}"/>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                    <div class="fy-filter-select-btn industry-div"
                         style="display: none">
                        <button class="confirm-btn"
                                onclick="confirmButton('industry');">${lfn:message('third-mall:kmReuseCommon.comfirm')}</button>
                        <button class="cancel-btn"
                                onclick="cancelButton('industry');">${lfn:message('third-mall:kmReuseCommon.cancel')}</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${areaList !=null && areaList.size() > 0}">
        <div class="fy-filter-row">
            <div class="fy-filter-title">${lfn:message('third-mall:kmReuseCommon.fdArea')}：</div>
            <div class="fy-filter-comp">
                <div class="fy-filter-select-collapse">
                    <div class="fy-filter-comp-btn-wrap">
                        <span class="single-btn choice-btn"
                              onclick="singleandMultiple(this,'area')">${lfn:message('third-mall:kmReuseCommon.manyChoose')}</span>
                        <span
                                class="collapse-btn area-collapse"> ${lfn:message('third-mall:kmReuseCommon.more')} <i
                                class="fy-icon fy-icon-arrow-down"></i> <i
                                class="fy-icon fy-icon-arrow-down active"></i>
											</span>
                    </div>

                    <ul class="fy-filter-select-list single">
                        <li class="fy-filter-select-item area" id="unlimited"
                            onclick="commonSelect(this,'area');"><a
                                href="javascript:;"
                                class="all selected"> ${lfn:message('third-mall:kmReuseCommon.noLimit')} </a></li>
                        <c:forEach items="${areaList}" var="area">
                            <li class="fy-filter-select-item area"
                                id="${area.fdId}" onclick="commonSelect(this,'area');">
                                <a href="javascript:;" onfocus="this.blur();"> <i
                                        class="fy-icon fy-icon-checkbox"></i> <c:out value="${area.text}"/>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                    <div class="fy-filter-select-btn area-div"
                         style="display: none">
                        <button class="confirm-btn"
                                onclick="confirmButton('area');">${lfn:message('third-mall:kmReuseCommon.comfirm')}</button>
                        <button class="cancel-btn"
                                onclick="cancelButton('area');">${lfn:message('third-mall:kmReuseCommon.cancel')}</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${formatList !=null && formatList.size() > 0}">
        <div class="fy-filter-row">
            <div class="fy-filter-title">${lfn:message('third-mall:kmReuseCommon.fdFormat')}：</div>
            <div class="fy-filter-comp">
                <div class="fy-filter-select-collapse">
                    <div class="fy-filter-comp-btn-wrap">
						<span class="single-btn choice-btn"
                              onclick="singleandMultiple(this,'format')">${lfn:message('third-mall:kmReuseCommon.manyChoose')}</span>
                        <span class="collapse-btn format-collapse">
							${lfn:message('third-mall:kmReuseCommon.more')} <i
                                class="fy-icon fy-icon-arrow-down"></i> <i
                                class="fy-icon fy-icon-arrow-down active"></i>
						</span>
                    </div>
                    <ul class="fy-filter-select-list single">
                        <li class="fy-filter-select-item format" id="unlimited"
                            onclick="commonSelect(this,'format');"><a
                                href="javascript:;" class="all selected">
                                ${lfn:message('third-mall:kmReuseCommon.noLimit')} </a></li>
                        <c:forEach items="${formatList}" var="format">
                            <li class="fy-filter-select-item format" id="${format.fdId}"
                                onclick="commonSelect(this,'format');"><a
                                    href="javascript:;" onfocus="this.blur();"> <i
                                    class="fy-icon fy-icon-checkbox"></i> <c:out
                                    value="${format.text}"/>
                            </a></li>
                        </c:forEach>
                    </ul>
                    <div class="fy-filter-select-btn format-div"
                         style="display: none">
                        <button class="confirm-btn"
                                onclick="confirmButton('format');">${lfn:message('third-mall:kmReuseCommon.comfirm')}</button>
                        <button class="cancel-btn"
                                onclick="cancelButton('format');">${lfn:message('third-mall:kmReuseCommon.cancel')}</button>
                    </div>
                </div>
            </div>
        </div>

    </c:if>
    <c:if test="${cateList !=null && cateList.size() > 0}">
        <!-- 主题分类 -->
        <div class="fy-filter-row">
            <div class="fy-filter-title">${lfn:message('third-mall:kmReuseCommon.fdMainCate')}：</div>
            <div class="fy-filter-comp">
                <div class="fy-filter-select-collapse">
                    <div class="fy-filter-comp-btn-wrap">
                            <span class="single-btn choice-btn"
                                  onclick="singleandMultiple(this,'cate')">${lfn:message('third-mall:kmReuseCommon.manyChoose')}</span>
                        <span class="collapse-btn cate-collapse">
                                ${lfn:message('third-mall:kmReuseCommon.more')} <i
                                class="fy-icon fy-icon-arrow-down"></i> <i
                                class="fy-icon fy-icon-arrow-down active"></i>
                            </span>
                    </div>
                    <ul class="fy-filter-select-list single">
                        <li class="fy-filter-select-item cate" id="unlimited"
                            onclick="commonSelect(this,'cate');"><a
                                href="javascript:;" class="all selected">
                                ${lfn:message('third-mall:kmReuseCommon.noLimit')} </a></li>
                        <c:forEach items="${cateList}" var="cate">
                            <li class="fy-filter-select-item cate" id="${cate.fdId}"
                                onclick="commonSelect(this,'cate');"><a
                                    href="javascript:;" onfocus="this.blur();"> <i
                                    class="fy-icon fy-icon-checkbox"></i> <c:out
                                    value="${cate.text}"/>
                            </a></li>
                        </c:forEach>
                    </ul>
                    <div class="fy-filter-select-btn cate-div" style="display: none">
                        <button class="confirm-btn"
                                onclick="confirmButton('cate');">${lfn:message('third-mall:kmReuseCommon.comfirm')}</button>
                        <button class="cancel-btn"
                                onclick="cancelButton('cate');">${lfn:message('third-mall:kmReuseCommon.cancel')}</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${fdType=='component'}">
        <!-- 部件呈现、部件外观、页眉、页脚、页面模板 -->
        <div class="fy-filter-row">
            <div class="fy-filter-title">${lfn:message('third-mall:kmReuseThemeSet.component.type')}：</div>
            <div class="fy-filter-comp">
                <div class="fy-filter-select-collapse">
                    <div class="fy-filter-comp-btn-wrap">
						<span class="single-btn choice-btn"
                              onclick="singleandMultiple(this,'componentType')">${lfn:message('third-mall:kmReuseCommon.manyChoose')}</span>
                        <span class="collapse-btn componentType-collapse">
							${lfn:message('third-mall:kmReuseCommon.more')} <i
                                class="fy-icon fy-icon-arrow-down"></i> <i
                                class="fy-icon fy-icon-arrow-down active"></i>
						</span>
                    </div>
                    <ul class="fy-filter-select-list single">
                        <li class="fy-filter-select-item componentType" id="unlimited"
                            onclick="commonSelect(this,'componentType');"><a
                                href="javascript:;" class="all selected">
                                ${lfn:message('third-mall:kmReuseCommon.noLimit')} </a></li>

                        <li class="fy-filter-select-item componentType" id="render"
                            onclick="commonSelect(this,'componentType');"><a
                                href="javascript:;" onfocus="this.blur();"> <i
                                class="fy-icon fy-icon-checkbox"></i> ${ lfn:message('third-mall:kmReuseThemeSet.component.layout') }
                        </a></li>
                        <li class="fy-filter-select-item componentType" id="panel"
                            onclick="commonSelect(this,'componentType');"><a
                                href="javascript:;" onfocus="this.blur();"> <i
                                class="fy-icon fy-icon-checkbox"></i> ${ lfn:message('third-mall:kmReuseThemeSet.component.ui') }
                        </a></li>
                        <li class="fy-filter-select-item componentType" id="header"
                            onclick="commonSelect(this,'componentType');"><a
                                href="javascript:;" onfocus="this.blur();"> <i
                                class="fy-icon fy-icon-checkbox"></i> ${ lfn:message('third-mall:kmReuseThemeSet.component.header') }
                        </a></li>
                        <li class="fy-filter-select-item componentType" id="footer"
                            onclick="commonSelect(this,'componentType');"><a
                                href="javascript:;" onfocus="this.blur();"> <i
                                class="fy-icon fy-icon-checkbox"></i> ${ lfn:message('third-mall:kmReuseThemeSet.component.footer') }
                        </a></li>
                        <li class="fy-filter-select-item componentType" id="template"
                            onclick="commonSelect(this,'componentType');"><a
                                href="javascript:;" onfocus="this.blur();"> <i
                                class="fy-icon fy-icon-checkbox"></i> ${ lfn:message('third-mall:kmReuseThemeSet.component.page') }
                        </a></li>
                    </ul>
                    <div class="fy-filter-select-btn componentType-div"
                         style="display: none">
                        <button class="confirm-btn"
                                onclick="confirmButton('componentType');">${lfn:message('third-mall:kmReuseCommon.comfirm')}</button>
                        <button class="cancel-btn"
                                onclick="cancelButton('componentType');">${lfn:message('third-mall:kmReuseCommon.cancel')}</button>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

</div>