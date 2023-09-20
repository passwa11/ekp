<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
  <div class="ld-city-mask" id="ld-city-mask">
        <div class="ld-city-modal">
            <div class="ld-city-modal-head">	
                <div onclick="closeSelectCity()">${lfn:message('button.cancel')}</div>
                <div onclick="sureSelectCity()">${lfn:message('button.ok')}</div>
            </div>
            <!-- 出发地点 -->
	           <div class="currentCityType">
	                <c:if test="${param.type !='expense' }">
		                <div class="active endCity" data-source="city">消费城市</div>
		                <input id ="endCity" value="" hidden='true' >
		                <input id ="endCityId" value="" hidden='true'>
	                </c:if>
	                <c:if test="${param.type =='expense' }">
		                <div class="active startCity" data-source="city">出发城市</div>
		                <input name ="startCity" value="" hidden='true' >
		                <input name ="startCityId" value="" hidden='true'>
		                <div class="endCity">到达地点</div>
		                <input name ="endCity" value="" hidden='true' >
		                <input name ="endCityId" value="" hidden='true'>
		            <%--   </c:if>
		              <c:if test="${param.type =='expense' && param.type =='trafficTool'}"> --%>
	                	<div class="trafficTools" data-source="vehicle">交通工具</div>
	                	<input name ="trafficTools" value="" hidden='true' >
		                <input name ="trafficToolsId" value="" hidden='true'>
		              </c:if>
	            </div>
	            <div class="ld-city-modal-search">
	                <input type="search"  name="searchCity" value="">
	                <a href="javascript:" style="display:none;" class="cancel-btn">${lfn:message('button.cancel')}</a>
	                <i></i>
	            </div>
	            <div class="ld-city-modal-content">
	                <div class="ld-city-modal-tab">
	                    <div class="active" data-type="2">国内</div>
	                    <div class=""  data-type="1">国际</div>
	                </div>
                    <div class="swiper-wrapper">
                        <div class="swiper-slide">
                            <!--  -->
                            <div class="ld-city-modal-main">
                             <!--    <div class="commonlyUsed">
                                    <div class="commonlyUsed-ttle">常用</div>
                                    <ul>
                                        <li>北京</li>
                                        <li>上海</li>
                                        <li>广州</li>
                                        <li>深圳</li>
                                        <li>杭州</li>
                                        <li>成都</li>
                                    </ul>
                                </div> -->
                                <div class="ld-line20px"></div>
                                <div class="ld-city-modal-main-city-list">
                                    <div class="cityList" id="A">
                                        <p>A</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="B">
                                        <p>B</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="C">
                                        <p>C</p>
                                        <ul>
                                        </ul>
                                    </div>
                                     <div class="cityList" id="D">
                                        <p>D</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="E">
                                        <p>E</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="F">
                                        <p>F</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="G">
                                        <p>G</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="H">
                                        <p>H</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="I">
                                        <p>I</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="J">
                                        <p>J</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="K">
                                        <p>K</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="L">
                                        <p>L</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="M">
                                        <p>M</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="N">
                                        <p>N</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="O">
                                        <p>O</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="P">
                                        <p>P</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="Q">
                                        <p>Q</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="R">
                                        <p>R</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="S">
                                        <p>S</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="T">
                                        <p>T</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="U">
                                        <p>U</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="V">
                                        <p>V</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="W">
                                        <p>W</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="X">
                                        <p>X</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="Y">
                                        <p>Y</p>
                                        <ul>
                                        </ul>
                                    </div>
                                    <div class="cityList" id="Z">
                                        <p>Z</p>
                                        <ul>
                                        </ul>
                                    </div>
                                </div>
                                <div class="city-letter">
                                    <ul>
                                        <!-- <li><a href="javascript:;">常用</a></li> -->
                                        <li><a href="javascript:;">A</a></li>
                                        <li><a href="javascript:;">B</a></li>
                                        <li><a href="javascript:;">C</a></li>
                                        <li><a href="javascript:;">D</a></li>
                                        <li><a href="javascript:;">E</a></li>
                                        <li><a href="javascript:;">F</a></li>
                                        <li><a href="javascript:;">G</a></li>
                                        <li><a href="javascript:;">H</a></li>
                                        <li><a href="javascript:;">J</a></li>
                                        <li><a href="javascript:;">K</a></li>
                                        <li><a href="javascript:;">L</a></li>
                                        <li><a href="javascript:;">M</a></li>
                                        <li><a href="javascript:;">N</a></li>
                                        <li><a href="javascript:;">P</a></li>
                                        <li><a href="javascript:;">Q</a></li>
                                        <li><a href="javascript:;">R</a></li>
                                        <li><a href="javascript:;">S</a></li>
                                        <li><a href="javascript:;">T</a></li>
                                        <li><a href="javascript:;">W</a></li>
                                        <li><a href="javascript:;">X</a></li>
                                        <li><a href="javascript:;">Y</a></li>
                                        <li><a href="javascript:;">Z</a></li>
                                    </ul>
                                </div>
                            </div>
                            <!--  -->
                        </div>
                        </div>
                        <!-- 交通工具 -->
                        <div class="swiper-slide" id="trafficTools">
                            <div class="ld-city-modal-main-trafficTools">
                                <div class="ld-line20px"></div>
                                <div class="ld-city-modal-main-trafficTools-list">
                                	<div class="vehicleList" >
                                        <ul style="overflow:auto;min-height:300px;">
                                        <li>
                                            
                                        </li>
                                        </ul>
                                	</div>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/resource/jsp/edit_down.jsp" %>

