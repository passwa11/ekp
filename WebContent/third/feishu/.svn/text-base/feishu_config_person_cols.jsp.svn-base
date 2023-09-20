<div>
    <span style="color:red"><bean:message key="third.feishu.org.person.tip" bundle="third-feishu"/></span>
    <table style="text-align: center" class="tb_normal" width=100%>
        <!--表头-->
        <tr>
            <td class="td_normal_title"><bean:message key="third.feishu.org.col" bundle="third-feishu"/></td>
            <td class="td_normal_title"><bean:message key="third.feishu.org.synWay" bundle="third-feishu"/></td>
            <td class="td_normal_title"><bean:message key="third.feishu.org.from.ekp" bundle="third-feishu"/></td>
        </tr>
        <!--姓名-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.name" bundle="third-feishu"/> <span class="txtstrong">*</span>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.name.synWay)" className="selectsgl" subject="姓名"
                              showStatus="edit" showPleaseSelect="false">
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <xform:select className="selectsgl" showStatus="view" value="fdName" property="value(org2Feishu.name)"
                              subject="${lfn:message('third-feishu:third.feishu.org.name')}"
                              showPleaseSelect="false" htmlElementProperties="disabled=disabled" style="width:45%;">
                    <xform:simpleDataSource value="fdName">
                        <bean:message key="third.feishu.org.name" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
        </tr>
        <!--手机号-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.mobile" bundle="third-feishu"/> <span class="txtstrong">*</span>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.mobile.synWay)" className="selectsgl"
                              subject="${lfn:message('third-feishu:third.feishu.org.mobile')}"
                              showStatus="edit" showPleaseSelect="false">
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn"  bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <xform:select property="value(org2Feishu.mobile)" showStatus="view" value="fdMobileNo"
                              className="selectsgl" subject="${lfn:message('third-feishu:third.feishu.org.mobile')}"
                              showPleaseSelect="false" style="width:45%">
                    <xform:simpleDataSource value="fdMobileNo">
                        <bean:message key="third.feishu.org.mobile" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
        </tr>
        <!--员工UserId-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.userId" bundle="third-feishu"/> <span class="txtstrong">*</span>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.userid.synWay)" className="selectsgl"
                              subject="${lfn:message('third-feishu:third.feishu.org.addSyn')}"
                              showStatus="edit" showPleaseSelect="false">
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <xform:select property="value(org2Feishu.userid)" subject="" className="selectsgl" showStatus="edit"
                              showPleaseSelect="false" style="width:45%">
                    <xform:simpleDataSource value="fdLoginName">
                        <bean:message key="third.feishu.org.loginName" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="fdId">fdId</xform:simpleDataSource>
                </xform:select>
                <span style="color:red"><bean:message key="third.feishu.org.userId.tip" bundle="third-feishu"/></span>
            </td>
        </tr>
        <!--部门-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.dept" bundle="third-feishu"/> <span class="txtstrong">*</span>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.department.synWay)" subject="" className="selectsgl"
                              showStatus="edit" showPleaseSelect="false">
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <xform:select property="value(org2Feishu.department)" subject="" className="selectsgl" showStatus="edit"
                              showPleaseSelect="false" style="width:45%">
                    <xform:simpleDataSource value="fdDept">
                        <bean:message key="third.feishu.org.dept.sigle" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="fdMuilDept">
                        <bean:message key="third.feishu.org.dept.mutil" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
        </tr>
        <!--排序号-->
        <tr>
            <td class="td_normal_title" width="15%"><
                bean:message key="third.feishu.org.order" bundle="third-feishu"/>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.orderInDepts.synWay)" htmlElementProperties="type='synWay'"
                              onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.orderInDepts)" subject="" className="selectsgl"
                                  showStatus="edit" showPleaseSelect="false" style="width:45%">
                        <xform:simpleDataSource value="asc">
                            <bean:message key="third.feishu.org.order.asc" bundle="third-feishu"/>
                        </xform:simpleDataSource>
                        <xform:simpleDataSource value="desc">
                            <bean:message key="third.feishu.org.order.desc" bundle="third-feishu"/>
                        </xform:simpleDataSource>
                    </xform:select>
                    <span style="color:red"><bean:message key="third.feishu.org.order.tip" bundle="third-feishu"/></span>
                </div>
            </td>
        </tr>
        <!--别名-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.alias" bundle="third-feishu"/>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.alias.synWay)" className="selectsgl" subject=""
                              htmlElementProperties="type='synWay'" onValueChange="checkSyn(this)" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.alias)" subject="" className="selectsgl"
                                  showStatus="edit" showPleaseSelect="false" style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--英文名-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.english.name" bundle="third-feishu"/>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.english.name.synWay)" className="selectsgl" subject=""
                              htmlElementProperties="type='synWay'" onValueChange="checkSyn(this)" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.english.name)" subject="" className="selectsgl"
                                  showStatus="edit" showPleaseSelect="false" style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--工号-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.jobNum" bundle="third-feishu"/>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.jobnumber.synWay)" className="selectsgl" subject=""
                              htmlElementProperties="type='synWay'" onValueChange="checkSyn(this)" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.jobnumber)" subject="" className="selectsgl"
                                  showStatus="edit" showPleaseSelect="false" style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--职务-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.post" bundle="third-feishu"/></td>
            <td width="25%">
                <xform:select property="value(org2Feishu.position.synWay)" className="selectsgl" subject=""
                              htmlElementProperties="type='synWay'" onValueChange="checkSyn(this)" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.position)" onValueChange="checkPositionSyn(this)" subject=""
                                  className="selectsgl" showStatus="edit" showPleaseSelect="false" style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--邮箱-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.email" bundle="third-feishu"/>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.email.synWay)" className="selectsgl" subject=""
                              htmlElementProperties="type='synWay'" onValueChange="checkSyn(this)" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.email.name)" subject="" className="selectsgl"
                                  showStatus="edit" showPleaseSelect="false" style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--性别-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.sex" bundle="third-feishu"/>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.sex.synWay)" className="selectsgl" subject=""
                              htmlElementProperties="type='synWay'" onValueChange="checkSyn(this)" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.sex.name)" subject="" className="selectsgl"
                                  showStatus="edit" showPleaseSelect="false" style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--办公地点-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.workplace" bundle="third-feishu"/></td>
            <td width="25%">
                <xform:select property="value(org2Feishu.workPlace.synWay)" htmlElementProperties="type='synWay'"
                              onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.workPlace)" subject="" className="selectsgl"
                                  showStatus="edit" htmlElementProperties="iscustom='true'" showPleaseSelect="false"
                                  style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--入职时间-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.hiredate" bundle="third-feishu"/></td>
            <td width="25%">
                <xform:select property="value(org2Feishu.hiredDate.synWay)" htmlElementProperties="type='synWay'"
                              onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.hiredDate)" subject="" className="selectsgl"
                                  htmlElementProperties="iscustom='true'" showStatus="edit" showPleaseSelect="false"
                                  style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--企业邮箱-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.orgMail" bundle="third-feishu"/>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.orgEmail.synWay)" htmlElementProperties="type='synWay'"
                              onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:select property="value(org2Feishu.orgEmail)" subject="" className="selectsgl"
                                  htmlElementProperties="iscustom='true'" showStatus="edit" showPleaseSelect="false"
                                  style="width:45%">
                        <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                    </xform:select>
                </div>
            </td>
        </tr>
        <!--号码隐藏-->
        <tr>
            <td class="td_normal_title" width="15%">
                <bean:message key="third.feishu.org.mobile.hide" bundle="third-feishu"/>
            </td>
            <td width="25%">
                <xform:select property="value(org2Feishu.isHide.synWay)" htmlElementProperties="type='synWay'"
                              onValueChange="checkSyn(this)" className="selectsgl" subject="" showStatus="edit"
                              showPleaseSelect="false">
                    <xform:simpleDataSource value="noSyn">
                        <bean:message key="third.feishu.org.noSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="syn">
                        <bean:message key="third.feishu.org.syn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                    <xform:simpleDataSource value="addSyn">
                        <bean:message key="third.feishu.org.addSyn" bundle="third-feishu"/>
                    </xform:simpleDataSource>
                </xform:select>
            </td>
            <td width="55%" align="left">
                <div class="sys_property" style="display: block">
                    <xform:radio property="value(org2Feishu.isHide.all)" showStatus="edit"
                                 htmlElementProperties="onclick='switchMobileHide(this.value)'">
                        <xform:simpleDataSource value="true" textKey="third.feishu.config.mobile.isHide"
                                                bundle="third-feishu"></xform:simpleDataSource>
                        <xform:simpleDataSource value="false" textKey="third.feishu.config.from"
                                                bundle="third-feishu"></xform:simpleDataSource>
                    </xform:radio>
                    <div style="display: block" name="org2dingMobileIsHide">
                        <xform:select property="value(org2Feishu.isHide)" subject="" className="selectsgl"
                                      htmlElementProperties="iscustom='true'" showPleaseSelect="false"
                                      style="width:45%">
                            <xform:simpleDataSource value="isContactPrivate">
                                <bean:message key="third.feishu.org.mobile.from.zone" bundle="third-feishu"/>
                            </xform:simpleDataSource>
                            <xform:customizeDataSource className="com.landray.kmss.third.feishu.datasource.ThirdFeishuPersonCustomData"/>
                        </xform:select>
                        <p style="color:red">
                            <bean:message key="third.feishu.org.mobile.hide.tip" bundle="third-feishu"/>
                            <a href="${LUI_ContextPath }/sys/zone/sys_zone_private_config/sysZonePrivateConfig.do?method=edit&modelName=com.landray.kmss.sys.zone.model.SysZonePrivateConfig&s_path=应用配置%E3%80%80>%E3%80%80员工黄页%E3%80%80>%E3%80%80隐私设置&s_css=default"
                               target="_blank"
                               style="color: blue"
                            ><bean:message key="third.feishu.org.mobile.zone" bundle="third-feishu"/></a></p>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    function checkSyn(target){
        var val=target.value;
        var $tr = target.closest("tr");
        if(val == "noSyn" || val == "false"){
            $($tr).find(".sys_property").css("display","none");
        }else{
            $($tr).find(".sys_property").css("display","block");
        }
    }
    window.checkPositionSyn=function(target){
        var val = $('[name="value(org2Feishu.position)"]').val();
        if(val == "hbmPosts"){
            $("#org2ding_position_order").css("display","");
        }else{
            $("#org2ding_position_order").css("display","none");
        }
    }
    function switchMobileHide(val){
        if("true" == val){
            $("div[name='org2dingMobileIsHide']").css("display","none");
        }else{
            $("div[name='org2dingMobileIsHide']").css("display","block");
        }
    }
    seajs.use(['lui/jquery','lui/dialog'],function($, dialog){
        $("select[name^='value(org2Feishu.'][name$='.synWay)']").filter(function (index){
            return this.value === 'noSyn' && this.onchange;
        }).each(function(index, docEle){
            checkSyn(docEle);
        });
    });
</script>