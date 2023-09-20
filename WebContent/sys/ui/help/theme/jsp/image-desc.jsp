<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.portal.util.*"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!-- 图文摘要 -->
<table class="lux-portal-layout-table">
    <tr>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.default" toggle="false"  height="240"  scroll="false">
                <ui:content title="图文切换-摘要收展视图">
                    <portal:portlet title="图文切换-摘要收展视图" subtitle="" titleicon="" var-scope="no" var-rowsize="6"
                        var-cateid="">
                        <ui:dataview format="sys.ui.image.desc">
                            <ui:source ref="sys.news.image.desc.source" var-scope="no" var-rowsize="6" var-cateid="">
                            </ui:source>
                            <ui:render ref="sys.ui.image.desc.silder.default" var-position="1" var-rate="45"
                                var-stretch="true" var-showCreator="true" var-showCreated="true" var-showCate="true"
                                var-cateSize="0" var-newDay="0"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.light" toggle="false" height="240" scroll="false">
                <ui:content title="图文摘要列表">
                    <portal:portlet title="图文摘要列表" subtitle="" titleicon="" var-scope="no" var-rowsize="6"
                        var-cateid="">
                        <ui:dataview format="sys.ui.image.desc">
                            <ui:source ref="sys.news.image.desc.source" var-scope="no" var-rowsize="6" var-cateid="">
                            </ui:source>
                            <ui:render ref="sys.ui.image.desc.list.default" var-position="0" var-rate="15"
                                var-stretch="true" var-showCreator="true" var-showCreated="true" var-showCate="true"
                                var-cateSize="0" var-newDay="0"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height: 10px;"></div>
        </td>
    </tr>
    <tr>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.default" toggle="false"  height="240"  scroll="false">
                <ui:content title="图文切换-摘要平铺视图">
                    <portal:portlet title="图文切换-摘要平铺视图" subtitle="" titleicon="" var-scope="no" var-rowsize="6"
                        var-cateid="">
                        <ui:dataview format="sys.ui.image.desc">
                            <ui:source ref="sys.news.image.desc.source" var-scope="no" var-rowsize="6" var-cateid="">
                            </ui:source>
                            <ui:render ref="sys.ui.image.desc.silder.knMap" var-position="1" var-rate="45"
                                var-stretch="true" var-showCreator="true" var-showCreated="true" var-showCate="true"
                                var-cateSize="0" var-newDay="0"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.light" toggle="false" height="240" scroll="false">
                <ui:content title="摘要新闻-图片文字">
                    <portal:portlet title="摘要新闻-图片文字" subtitle="" titleicon="" var-scope="no" var-rowsize="3"
                        var-cateid="" var-width="200" var-height="140" var-showCreator="true" var-showCreated="true"
                        var-sumSize="0">
                        <ui:dataview format="sys.ui.classic.imageDesc">
                            <ui:source ref="sys.news.main.imgAndDescription.source" var-scope="no" var-rowsize="3"
                                var-cateid="" var-width="200" var-height="140" var-showCreator="true"
                                var-showCreated="true" var-sumSize="0"></ui:source>
                            <ui:render ref="sys.ui.classic.imageDesc.level"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
    </tr>
    <tr>
        <td>
            <div style="height:10px;"></div>
        </td>
    </tr>
    <tr>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.default" toggle="false"  height="240"  scroll="false">
                <ui:content title="图文摘要-首行图片卡片列表">
                    <portal:portlet title="图文摘要-首行图片卡片列表" subtitle="" titleicon="" var-scope="no" var-rowsize="6"
                        var-cateid="">
                        <ui:dataview format="sys.ui.image.desc">
                            <ui:source ref="sys.news.image.desc.source" var-scope="no" var-rowsize="6" var-cateid="">
                            </ui:source>
                            <ui:render ref="sys.ui.image.desc.card.imgFirst" var-position="0" var-rate="55"
                                var-stretch="true" var-showCreator="true" var-showCreated="true" var-showCate="true"
                                var-cateSize="0" var-newDay="0"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
        <td style="width:10px"></td>
        <td style="width:49%">
            <ui:panel layout="sys.ui.panel.default" toggle="false"  height="240"  scroll="false">
                <ui:content title="上图下文列表">
                    <portal:portlet title="上图下文列表" subtitle="" titleicon="" var-scope="no" var-rowsize="2"
                        var-cateid="">
                        <ui:dataview format="sys.ui.image.desc">
                            <ui:source ref="sys.news.image.desc.source" var-scope="no" var-rowsize="2" var-cateid="">
                            </ui:source>
                            <ui:render ref="sys.ui.imageUp.descDown" var-position="0" var-imgHeight="200"
                                var-stretch="true" var-showCreator="true" var-showCreated="true" var-showCate="true"
                                var-cateSize="0" var-newDay="0"></ui:render>
                            <ui:var name="showNoDataTip" value="true"></ui:var>
                            <ui:var name="showErrorTip" value="true"></ui:var>
                        </ui:dataview>
                    </portal:portlet>
                    <ui:operation name="更多" type="more" href="#" />
                    <ui:operation name="新建" type="create" href="#" />
                </ui:content>
            </ui:panel>
        </td>
    </tr>

</table>