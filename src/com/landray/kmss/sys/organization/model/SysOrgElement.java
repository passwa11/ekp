package com.landray.kmss.sys.organization.model;

import com.landray.kmss.common.convertor.ModelConvertor_Common;
import com.landray.kmss.common.convertor.ModelConvertor_ModelToForm;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.constant.BaseTreeConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.interfaces.ISysOrgElement;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang.BooleanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 组织架构元素
 *
 * @author 叶中奇
 */
@SuppressWarnings("unchecked")
public class SysOrgElement extends BaseModel implements SysOrgConstant,
        ISysOrgElement {
    private static final long serialVersionUID = -8078238937733505633L;

    static {
        ModelConvertor_Common.addCacheClass(SysOrgElement.class);
    }

    /*
     * 类型
     */
    private Integer fdOrgType = 0;

    @Override
    public Integer getFdOrgType() {
        return fdOrgType;
    }

    public void setFdOrgType(Integer fdOrgType) {
        this.fdOrgType = fdOrgType;
    }

    /*
     * 层级ID
     */
    protected String fdHierarchyId;

    public String getFdHierarchyId() {
        if (fdHierarchyId == null) {
            fdHierarchyId = BaseTreeConstant.HIERARCHY_ID_SPLIT + getFdId()
                    + BaseTreeConstant.HIERARCHY_ID_SPLIT;
        }
        return fdHierarchyId;
    }

    public void setFdHierarchyId(String fdHierarchyId) {
        this.fdHierarchyId = fdHierarchyId;
    }

    /*
     * 名称
     */
    private String fdName;

    public String getFdNameOri() {
        return fdName;
        // return fdName;
    }

    @Override
    public String getFdName() {
        return SysLangUtil.getPropValue(this, "fdName", fdName);
        // return fdName;
    }

    public String getFdName(String lang) {
        Locale locale = ResourceUtil.getLocale(lang);
        if (locale != null) {
            return getFdName(locale);
        } else {
            return this.fdName;
        }
    }

    public String getFdName(Locale locale) {
        if (!SysLangUtil.isLangEnabled()) {
            return this.fdName;
        }
        if (locale == null) {
            return this.fdName;
        }
        String localeCountry = SysLangUtil.getLocaleShortName(locale);
        String value_lang = this.getDynamicMap().get("fdName" + localeCountry);
        if (StringUtil.isNull(value_lang)) {
            return this.fdName;
        }
        return value_lang;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
        SysLangUtil.setPropValue(this, "fdName", fdName);
    }

    /*
     * 名称拼音
     */
    private String fdNamePinYin;

    /*
     * 编号
     */
    private String fdNo;

    @Override
    public String getFdNo() {
        return fdNo;
    }

    public void setFdNo(String fdNo) {
        this.fdNo = fdNo;
    }

    /*
     * 排序号
     */
    private Integer fdOrder;

    @Override
    public Integer getFdOrder() {
        return fdOrder;
    }

    public void setFdOrder(Integer fdOrder) {
        this.fdOrder = fdOrder;
    }

    /*
     * 关键字
     */
    private String fdKeyword;

    @Override
    public String getFdKeyword() {
        return fdKeyword;
    }

    public void setFdKeyword(String fdKeyword) {
        this.fdKeyword = fdKeyword;
    }

    /*
     * 是否有效
     */
    private Boolean fdIsAvailable;

    @Override
    public Boolean getFdIsAvailable() {
        if (fdIsAvailable == null) {
            fdIsAvailable = Boolean.TRUE;
        }
        return fdIsAvailable;
    }

    public void setFdIsAvailable(Boolean fdIsAvailable) {
        this.fdIsAvailable = fdIsAvailable;
    }

    /*
     * 是否业务相关
     */
    private Boolean fdIsBusiness;

    @Override
    public Boolean getFdIsBusiness() {
        if (fdIsBusiness == null) {
            fdIsBusiness = Boolean.TRUE;
        }
        return fdIsBusiness;
    }

    public void setFdIsBusiness(Boolean fdIsBusiness) {
        this.fdIsBusiness = fdIsBusiness;
    }

    /*
     * 导入的数据的对应键值 update by wubing date:2006-12-14
     */
    private String fdImportInfo;

    public String getFdImportInfo() {
        return fdImportInfo;
    }

    public void setFdImportInfo(String fdImportInfo) {
        this.fdImportInfo = fdImportInfo;
    }

    /*
     * OMS导入时使用的字段，业务扩展时使用
     */
    private String fdLdapDN;

    public String getFdLdapDN() {
        return fdLdapDN;
    }

    public void setFdLdapDN(String fdLdapDN) {
        this.fdLdapDN = fdLdapDN;
    }

    /*
     * OMS导入时使用的字段，业务上务任何意义
     */
    private Boolean fdFlagDeleted;

    public Boolean getFdFlagDeleted() {
        return fdFlagDeleted;
    }

    public void setFdFlagDeleted(Boolean fdFlagDeleted) {
        this.fdFlagDeleted = fdFlagDeleted;
    }

    /*
     * 描述
     */
    private String fdMemo;

    @Override
    public String getFdMemo() {
        return fdMemo;
    }

    public void setFdMemo(String fdMemo) {
        this.fdMemo = fdMemo;
    }

    /*
     * 创建时间
     */
    private Date fdCreateTime = new Date();

    public Date getFdCreateTime() {
        return fdCreateTime;
    }

    public void setFdCreateTime(Date fdCreateTime) {
        this.fdCreateTime = fdCreateTime;
    }

    /*
     * 最后修改时间
     */
    private Date fdAlterTime = new Date();

    public Date getFdAlterTime() {
        return fdAlterTime;
    }

    public void setFdAlterTime(Date fdAlterTime) {
        this.fdAlterTime = fdAlterTime;
    }

    /*
     * 本级领导
     */
    private SysOrgElement fdThisLeader;

    public SysOrgElement getHbmThisLeader() {
        return fdThisLeader;
    }

    public void setHbmThisLeader(SysOrgElement fdThisLeader) {
        this.fdThisLeader = fdThisLeader;
    }

    private List fdThisLeaderChildren;

    public List getHbmThisLeaderChildren() {
        return fdThisLeaderChildren;
    }

    public void setHbmThisLeaderChildren(List fdThisLeaderChildren) {
        this.fdThisLeaderChildren = fdThisLeaderChildren;
    }

    /*
     * 上级领导
     */
    private SysOrgElement fdSuperLeader;

    public SysOrgElement getHbmSuperLeader() {
        return fdSuperLeader;
    }

    public void setHbmSuperLeader(SysOrgElement fdSuperLeader) {
        this.fdSuperLeader = fdSuperLeader;
    }

    private List fdSuperLeaderChildren;

    public List getHbmSuperLeaderChildren() {
        return fdSuperLeaderChildren;
    }

    public void setHbmSuperLeaderChildren(List fdSuperLeaderChildren) {
        this.fdSuperLeaderChildren = fdSuperLeaderChildren;
    }

    @Override
    public SysOrgElement getLeader(int level) throws Exception {
        List<SysOrgElement> leaders = getAllLeader(level);
        if (level < 0) {
            level = leaders.size() + level;
        }
        if (level < 0 || level >= leaders.size()) {
            return null;
        }
        return leaders.get(level);
    }

    /**
     * #25441：获取领导线角色逻辑，由原先的遍历出所有领导线成员 优化为 获取上一级仅取上一级，而不去循环计算。
     * 有效防止抛出死循环错误。
     * <p>
     * 如果level > 0，则由提交人向上获取领导，假设“3级直线领导”有死循环，而此时只需要获取2级直线领导，此方法只会返回2级直线领导，而不会抛出错误。
     * <p>
     * 如果level < 0，则由组织机构从上往下获取领导，此时必须要获取所有领导，无法避免死循环错误
     * <p>
     * 如果level = null，则获取所有领导，无法避免死循环错误
     */
    public List<SysOrgElement> getAllLeader(Integer level) throws Exception {
        // 以下代码由Domino的领导获取代码翻译而成，代码逻辑有些混乱，修改时需要跟domino的逻辑同步，否则很麻烦
        List<SysOrgElement> rtnVal = new ArrayList<SysOrgElement>();
        List<String> idList = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();
        SysOrgElement curElem = this; // 当前的组织架构元素
        SysOrgElement calElem = this; // 实际参与计算的组织架构元素
        SysOrgElement lastSuperLeader = null; // 上次循环使用的上级领导
        for (; curElem != null && calElem != null; ) {
            // 若参与计算的组织架构是个人或已未定义领导的岗位，则计算的实体为所在的部门
            boolean isPost = false;
            int curOrgType = calElem.getFdOrgType();
            if (curOrgType == ORG_TYPE_PERSON || curOrgType == ORG_TYPE_POST
                    && calElem.getHbmThisLeader() == null) {
                calElem = calElem.getFdParent();
                if (calElem == null) {
                    break;
                }
            }
            // 死循环校验
            nameList.add(calElem.getFdName());
            if (idList.contains(calElem.getFdId())) {
                throw new RuntimeException(ResourceUtil.getString(
                        "sysOrgElement.leader.cycle", "sys-organization", null,
                        nameList.toString().replaceAll(",", " =>")));
            }
            idList.add(calElem.getFdId());
            // 获取直接领导，若直接领导为空或跟上次循环使用的上级领导重叠了，则忽略
            SysOrgElement thisLeader = calElem.getHbmThisLeader();
            if (!(thisLeader == null || thisLeader.equals(lastSuperLeader))) {
                if (thisLeader.getFdOrgType().intValue() == ORG_TYPE_POST) {
                    isPost = true;
                } else {
                    isPost = false;
                }
                rtnVal.add(thisLeader);
            }
            if (calElem.getFdOrgType().intValue() == ORG_TYPE_ORG
                    || calElem.getFdOrgType().intValue() == ORG_TYPE_DEPT) {
                // 若计算对象为机构或部门，则获取上级领导，并添加到返回值列表中
                // 若上级领导不为空，则下次循环采用上级领导，否则采用上级部门
                lastSuperLeader = calElem.getHbmSuperLeader();
                if (lastSuperLeader != null) {
                    rtnVal.add(lastSuperLeader);
                    curElem = lastSuperLeader;
                } else {
                    curElem = calElem.getFdParent();
                }
                calElem = curElem;
            } else {
                // 注意：这里计算对象只剩下岗位了
                // 若计算对象为岗位，将持续获取岗位领导，不再获取部门（机构）
                if (thisLeader == null) {
                    break;
                }
                // 设定下一轮循环的对象，将岗位领导设置为当前对象。
                calElem = thisLeader;
                if (curOrgType == ORG_TYPE_POST) {
                    curElem = calElem.getHbmThisLeader();
                } else {
                    curElem = calElem.getFdParent();
                }
                lastSuperLeader = null;
            }

            // 如果是由提交人往上获取领导，可以避免死循环
            if (level != null && level >= 0) {
                // 当获取到需要的级别领导时，不再往获取领导，而是返回已经获取到的领导
                if (rtnVal.size() >= level + 1 && isPost == false) {
                    return rtnVal;
                }
            }
        }
        return rtnVal;
    }

    @Override
    public List<SysOrgElement> getAllLeader() throws Exception {
        // 以下代码由Domino的领导获取代码翻译而成，代码逻辑有些混乱，修改时需要跟domino的逻辑同步，否则很麻烦
        List<SysOrgElement> rtnVal = new ArrayList<SysOrgElement>();
        List<String> idList = new ArrayList<String>();
        List<String> nameList = new ArrayList<String>();
        SysOrgElement curElem = this; // 当前的组织架构元素
        SysOrgElement calElem = this; // 实际参与计算的组织架构元素
        SysOrgElement lastSuperLeader = null; // 上次循环使用的上级领导
        for (; curElem != null && calElem != null; ) {
            // 若参与计算的组织架构是个人或已未定义领导的岗位，则计算的实体为所在的部门
            int curOrgType = calElem.getFdOrgType();
            if (curOrgType == ORG_TYPE_PERSON || curOrgType == ORG_TYPE_POST
                    && calElem.getHbmThisLeader() == null) {
                calElem = calElem.getFdParent();
                if (calElem == null) {
                    break;
                }
            }
            // 死循环校验
            nameList.add(calElem.getFdName());
            if (idList.contains(calElem.getFdId())) {
                throw new RuntimeException(ResourceUtil.getString(
                        "sysOrgElement.leader.cycle", "sys-organization", null,
                        nameList.toString().replaceAll(",", " =>")));
            }
            idList.add(calElem.getFdId());
            // 获取直接领导，若直接领导为空或跟上次循环使用的上级领导重叠了，则忽略
            SysOrgElement thisLeader = calElem.getHbmThisLeader();
            if (!(thisLeader == null || thisLeader.equals(lastSuperLeader))) {
                rtnVal.add(thisLeader);
            }
            if (calElem.getFdOrgType().intValue() == ORG_TYPE_ORG
                    || calElem.getFdOrgType().intValue() == ORG_TYPE_DEPT) {
                // 若计算对象为机构或部门，则获取上级领导，并添加到返回值列表中
                // 若上级领导不为空，则下次循环采用上级领导，否则采用上级部门
                lastSuperLeader = calElem.getHbmSuperLeader();
                if (lastSuperLeader != null) {
                    rtnVal.add(lastSuperLeader);
                    curElem = lastSuperLeader;
                } else {
                    curElem = calElem.getFdParent();
                }
                calElem = curElem;
            } else {
                // 注意：这里计算对象只剩下岗位了
                // 若计算对象为岗位，将持续获取岗位领导，不再获取部门（机构）
                if (thisLeader == null) {
                    break;
                }
                // 设定下一轮循环的对象，将岗位领导设置为当前对象。
                calElem = thisLeader;
                if (curOrgType == ORG_TYPE_POST) {
                    curElem = calElem.getHbmThisLeader();
                } else {
                    curElem = calElem.getFdParent();
                }
                lastSuperLeader = null;
            }
        }
        return rtnVal;
    }

    @Override
    public SysOrgElement getMyLeader(int level) throws Exception {
        List<SysOrgElement> leaders = getAllMyLeader(level);
        if (level < 0) {
            level = leaders.size() + level;
        }
        if (level < 0 || level >= leaders.size()) {
            return null;
        }
        return leaders.get(level);
    }

    public List<SysOrgElement> getAllMyLeader(int level) throws Exception {
        List<SysOrgElement> rtnList = getAllLeader(level);
        if (getFdOrgType().intValue() == ORG_TYPE_POST) {
            rtnList.remove(this);
        } else if (getFdOrgType().intValue() == ORG_TYPE_PERSON) {
            rtnList.remove(this);
            rtnList.removeAll(getFdPosts());
        }
        return rtnList;
    }

    @Override
    public List<SysOrgElement> getAllMyLeader() throws Exception {
        List<SysOrgElement> rtnList = getAllLeader();
        if (getFdOrgType().intValue() == ORG_TYPE_POST) {
            rtnList.remove(this);
        } else if (getFdOrgType().intValue() == ORG_TYPE_PERSON) {
            rtnList.remove(this);
            rtnList.removeAll(getFdPosts());
        }
        return rtnList;
    }

    private SysOrgElement fdParentOrg;

    @Override
    public SysOrgElement getFdParentOrg() {
        return fdParentOrg;
    }

    public SysOrgElement getHbmParentOrg() {
        return fdParentOrg;
    }

    public void setHbmParentOrg(SysOrgElement parentOrg) {
        this.fdParentOrg = parentOrg;
    }

    /*
     * 父
     */
    private SysOrgElement fdParent;

    @Override
    public SysOrgElement getFdParent() {
        return fdParent;
    }

    public void setFdParent(SysOrgElement parent) {
        this.fdParent = parent;
    }

    public SysOrgElement getHbmParent() {
        return fdParent;
    }

    public void setHbmParent(SysOrgElement parent) {
        this.fdParent = parent;
    }

    public String getFdParentsName() {
        return getFdParentsName("_");
    }

    /**
     * 根据当前语言取层级名称
     *
     * @param splitStr
     * @return
     */
    public String getFdParentsName(String splitStr) {
        return getFdParentsName(splitStr, false);
    }

    /**
     * 根据语言取层级名称
     *
     * @param splitStr,lang
     * @return
     */
    public String getFdParentsName(String splitStr, String lang) {
        String fdParentsName = "";
        List list = new ArrayList();
        if (fdParent != null) {
            try {
                SysOrgElement parent = fdParent;
                while (parent != null) {
                    if(list.size()>100) {//避免脏数据导致死循环，跳出循环
                        break;
                    }
                    list.add(parent);
                    parent = parent.getFdParent();
                }
            } catch (Exception ex) {
            }
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            fdParentsName += ((SysOrgElement) list.get(i)).getFdName(lang);
            if (i > 0) {
                fdParentsName += splitStr;
            }
        }
        return fdParentsName;
    }

    /**
     * 取官方语言的层级名称
     *
     * @param splitStr
     * @return
     */
    public String getFdParentsNameOri(String splitStr) {
        return getFdParentsName(splitStr, true);
    }

    /**
     * 取层级名称
     *
     * @param splitStr
     * @param isOfficialLang 是否取官方语言（当为false时，取当前语言的层级名称）
     * @return
     */
    private String getFdParentsName(String splitStr, boolean isOfficialLang) {
        String fdParentsName = "";
        List list = new ArrayList();
        if (fdParent != null) {
            try {
                SysOrgElement parent = fdParent;
                while (parent != null) {
                    if(list.size()>100) {//避免脏数据导致死循环，跳出循环
                        break;
                    }
                    list.add(parent);
                    parent = parent.getFdParent();
                }
            } catch (Exception ex) {
            }
        }
        for (int i = list.size() - 1; i >= 0; i--) {
            if (isOfficialLang) {
                fdParentsName += ((SysOrgElement) list.get(i)).getFdNameOri();
            } else {
                fdParentsName += ((SysOrgElement) list.get(i)).getFdName();
            }
            if (i > 0) {
                fdParentsName += splitStr;
            }
        }
        return fdParentsName;
    }

    /*
     * 子（此关系由父维护，不提供set方法）
     */
    private List fdChildren;

    public List getFdChildren() {
        List rtnVal = new ArrayList();
        List children = getHbmChildren();
        if (children != null) {
            rtnVal.addAll(children);
        }
        return rtnVal;
    }

    public List getHbmChildren() {
        return fdChildren;
    }

    public void setHbmChildren(List children) {
        this.fdChildren = children;
    }

    /*
     * 所属群组
     */
    private List fdGroups;

    @Override
    public List getFdGroups() {
        List rtnVal = new ArrayList();
        List groups = getHbmGroups();
        if (groups != null) {
            rtnVal.addAll(groups);
        }
        return rtnVal;
    }

    public void setFdGroups(List groups) {
        new KmssCache(SysOrgElement.class).clear();
        if (this.fdGroups == groups) {
            return;
        }
        if (this.fdGroups == null) {
            this.fdGroups = new ArrayList();
        } else {
            this.fdGroups.clear();
        }
        if (groups != null) {
            this.fdGroups.addAll(groups);
        }
    }

    public List getHbmGroups() {
        return fdGroups;
    }

    public void setHbmGroups(List groups) {
        this.fdGroups = groups;
    }

    private List fdViews = null;

    public List getFdViews() {
        List rtnVal = new ArrayList();
        List views = getHbmViews();
        if (views != null) {
            rtnVal.addAll(views);
        }
        return rtnVal;
    }

    public void setFdViews(List views) {
        if (this.fdViews == views) {
            return;
        }
        if (this.fdViews == null) {
            this.fdViews = new ArrayList();
        } else {
            this.fdViews.clear();
        }
        if (views != null) {
            this.fdViews.addAll(views);
        }
    }

    public List getHbmViews() {
        return fdViews;
    }

    public void setHbmViews(List views) {
        this.fdViews = views;
    }

    /*
     * 岗位列表（个人使用）
     */
    private List fdPosts = null;

    public List getFdPosts() {
        List rtnVal = new ArrayList();
        List posts = getHbmPosts();
        if (posts != null) {
            rtnVal.addAll(posts);
        }
        return rtnVal;
    }

    public void setFdPosts(List posts) {
        if (this.fdPosts == posts) {
            return;
        }
        if (this.fdPosts == null) {
            this.fdPosts = new ArrayList();
        } else {
            this.fdPosts.clear();
        }
        if (posts != null) {
            this.fdPosts.addAll(posts);
        }
    }

    public List getHbmPosts() {
        return fdPosts;
    }

    public void setHbmPosts(List posts) {
        this.fdPosts = posts;
    }

    /*
     * 个人列表（岗位使用）
     */
    public List fdPersons = null;

    public List getFdPersons() {
        List rtnVal = new ArrayList();
        List persons = getHbmPersons();
        if (persons != null) {
            rtnVal.addAll(persons);
        }
        return rtnVal;
    }

    public void setFdPersons(List persons) {
        if (this.fdPersons == persons) {
            return;
        }
        if (this.fdPersons == null) {
            this.fdPersons = new ArrayList();
        } else {
            this.fdPersons.clear();
        }
        if (persons != null) {
            this.fdPersons.addAll(persons);
        }
    }

    public List getHbmPersons() {
        return fdPersons;
    }

    public void setHbmPersons(List persons) {
        this.fdPersons = persons;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!(object instanceof SysOrgElement)) {
            return false;
        }
        BaseModel objModel = (BaseModel) object;
        return ObjectUtil.equals(objModel.getFdId(), this.getFdId(), false);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    private static ModelToFormPropertyMap toFormPropertyMap;

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.put("fdParent.fdId", "fdParentId");
            toFormPropertyMap.put("fdParent.fdName", "fdParentName");
            toFormPropertyMap.put("hbmThisLeader.fdId", "fdThisLeaderId");
            toFormPropertyMap.put("hbmThisLeader.fdName", "fdThisLeaderName");
            toFormPropertyMap.put("docCreator.fdId", "docCreatorId");

            toFormPropertyMap.put("fdExternal", new ModelConvertor_ModelToForm("fdExternal"));
            toFormPropertyMap.put("fdRange", new ModelConvertor_ModelToForm("fdRange"));
            toFormPropertyMap.put("fdHideRange", new ModelConvertor_ModelToForm("fdHideRange"));
        }
        return toFormPropertyMap;
    }

    @Override
    public Class getFormClass() {
        return null;
    }

    /*
     * 是否已废弃,true表示禁用
     */
    protected Boolean fdIsAbandon = new Boolean(false);

    public Boolean getFdIsAbandon() {
        return fdIsAbandon;
    }

    public void setFdIsAbandon(Boolean fdIsAbandon) {
        this.fdIsAbandon = fdIsAbandon;
    }

    /**
     * @return fdNamePinYin
     */
    public String getFdNamePinYin() {
        return fdNamePinYin;
    }

    /**
     * @param fdNamePinYin 要设置的 fdNamePinYin
     */
    public void setFdNamePinYin(String fdNamePinYin) {
        this.fdNamePinYin = fdNamePinYin;
    }

    /**
     * 管理员 (部门与机构使用)
     */
    protected List<SysOrgElement> authElementAdmins;

    /**
     * @return 管理员
     */
    public List<SysOrgElement> getAuthElementAdmins() {
        return authElementAdmins;
    }

    /**
     * @param authElementAdmins 管理员
     */
    public void setAuthElementAdmins(List<SysOrgElement> authElementAdmins) {
        this.authElementAdmins = authElementAdmins;
    }

    @Override
    public void recalculateFields() {
        ModelUtil.checkTreeCycle(this, fdParent, "fdParent");

        if (fdParent == null || getFdOrgType().intValue() == ORG_TYPE_ORG) {
            this.fdParentOrg = null;
        } else {
            if (fdParent.getFdOrgType().intValue() == ORG_TYPE_ORG) {
                this.fdParentOrg = fdParent;
            } else {
                this.fdParentOrg = fdParent.getFdParentOrg();
            }
        }
    }

    public List<SysOrgElement> getAllParent(boolean excludeMe) throws Exception {
        List<SysOrgElement> rtnVal = new ArrayList<SysOrgElement>();
        SysOrgElement curElem = this; // 当前的组织架构元素

        while (curElem != null) {
            if (SysOrgConstant.ORG_TYPE_ORG == curElem.getFdOrgType()
                    || SysOrgConstant.ORG_TYPE_DEPT == curElem.getFdOrgType()) {
                rtnVal.add(curElem);
            }

            curElem = curElem.getFdParent();
        }

        if (rtnVal.size() > 0 && excludeMe) {
            rtnVal.remove(0);
        }

        return rtnVal;
    }

    public void setFdOrgEmail(String fdOrgEmail) {
        this.fdOrgEmail = fdOrgEmail;
    }

    public String getFdOrgEmail() {
        return fdOrgEmail;
    }

    private String fdOrgEmail;

    /**
     * 部门/机构人员总数
     */
    private Integer fdPersonsNumber;

    public Integer getFdPersonsNumber() {
        return fdPersonsNumber;
    }

    public void setFdPersonsNumber(Integer fdPersonsNumber) {
        this.fdPersonsNumber = fdPersonsNumber;
    }

    /**
     * 名称简拼
     */
    private String fdNameSimplePinyin;

    public String getFdNameSimplePinyin() {
        return fdNameSimplePinyin;
    }

    public void setFdNameSimplePinyin(String fdNameSimplePinyin) {
        this.fdNameSimplePinyin = fdNameSimplePinyin;
    }

    /*
     * 部门层级显示名称
     */
    private String deptLevelNames;

    /**
     * 根据后台配置取部门层级名称
     *
     * @return
     */
    public String getDeptLevelNames() {
        SysOrganizationConfig config = null;
        try {
            // 取后台配置信息
            config = new SysOrganizationConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果类型不是部门，或者取配置信息失败，就直接返回fdName
        if ((getFdOrgType()
                | SysOrgConstant.ORG_TYPE_ORGORDEPT) != SysOrgConstant.ORG_TYPE_ORGORDEPT
                || config == null) {
            return getFdName();
        }

        switch (config.getKmssOrgDeptLevelDisplay()) {
            case SysOrganizationConfig.DEPT_LEVEL_ALL: { // 部门全路径
                deptLevelNames = this.getFdParentsName();
                if (StringUtil.isNotNull(deptLevelNames)) {
                    deptLevelNames += "_";
                }
                deptLevelNames += getFdName();
                break;
            }
            case SysOrganizationConfig.DEPT_LEVEL_ONLY_LAST: { // 仅末级部门
                deptLevelNames = getFdName();
                break;
            }
            case SysOrganizationConfig.DEPT_LEVEL_LATELY: { // 最近的X级
                deptLevelNames = getLatelyNames(
                        config.getKmssOrgDeptLevelDisplayLength());
                break;
            }
            default:
                deptLevelNames = getFdName();
                break;
        }
        return deptLevelNames;
    }

    /**
     * @param display       显示类别
     * @param displayLength 最近的X级
     */
    public String getDeptLevelNames(int display, int displayLength) {
        String dlns = "";
        // 如果类型不是部门，或者取配置信息失败，就直接返回fdName
        if ((getFdOrgType()
                | SysOrgConstant.ORG_TYPE_ORGORDEPT) != SysOrgConstant.ORG_TYPE_ORGORDEPT) {
            return getFdName();
        }

        switch (display) {
            case SysOrganizationConfig.DEPT_LEVEL_ALL: { // 部门全路径
                dlns = this.getFdParentsName();
                if (StringUtil.isNotNull(dlns)) {
                    dlns += "_";
                }
                dlns += getFdName();
                break;
            }
            case SysOrganizationConfig.DEPT_LEVEL_ONLY_LAST: { // 仅末级部门
                dlns = getFdName();
                break;
            }
            case SysOrganizationConfig.DEPT_LEVEL_LATELY: { // 最近的X级
                dlns = getLatelyNames(displayLength);
                break;
            }
            default:
                dlns = getFdName();
                break;
        }
        return dlns;
    }

    /**
     * 根据后台配置的层级数来取部门名称
     *
     * @param length
     * @return
     */
    public String getLatelyNames(int length) {
        StringBuffer latelyNames = new StringBuffer();
        SysOrgElement parent = this.fdParent;
        try {
            int count = 1;
            while (parent != null) {
                if (++count > length) {
                    break;
                }
                latelyNames.insert(0, "_" + parent.getFdName());
                parent = parent.getFdParent();
            }
        } catch (Exception ex) {
        }
        latelyNames.append("_").append(getFdName());
        latelyNames.deleteCharAt(0);
        return latelyNames.toString();
    }

    private String fdPreDeptId;

    private String fdPrePostIds;

    public String getFdPreDeptId() {
        return fdPreDeptId;
    }

    public void setFdPreDeptId(String fdPreDeptId) {
        this.fdPreDeptId = fdPreDeptId;
    }

    public String[] getFdPrePostIdsArr() {
        String[] ids;
        if (StringUtil.isNotNull(fdPrePostIds)) {
            ids = fdPrePostIds.split(";");
        } else {
            ids = null;
        }

        return ids;
    }

    public void setFdPrePostIds(String[] fdPrePostIds) {
        String ids = "";
        if (fdPrePostIds != null) {
            for (int i = 0; i < fdPrePostIds.length; i++) {
                if (i == 0) {
                    ids = fdPrePostIds[i];
                } else {
                    ids += ";" + fdPrePostIds[i];
                }
            }
        }
        this.fdPrePostIds = ids;
    }

    public String getFdPrePostIds() {
        return fdPrePostIds;
    }

    public void setFdPrePostIds(String fdPrePostIds) {
        this.fdPrePostIds = fdPrePostIds;
    }

    /**
     * 是否外部组织
     */
    protected Boolean fdIsExternal;

    public Boolean getFdIsExternal() {
        // 默认是内部组织
        if (fdIsExternal == null) {
            fdIsExternal = false;
        }
        return fdIsExternal;
    }

    public void setFdIsExternal(Boolean fdIsExternal) {
        this.fdIsExternal = fdIsExternal;
    }

    /**
     * 允许查看组织范围映射
     */
    private SysOrgElementRange fdRange;

    public SysOrgElementRange getFdRange() {
        if (BooleanUtils.isTrue(fdIsExternal) && (this.fdOrgType == ORG_TYPE_ORG || this.fdOrgType == ORG_TYPE_DEPT)) {
            if (fdRange == null) {
                fdRange = new SysOrgElementRange();
                fdRange.setFdIsOpenLimit(Boolean.TRUE);
                fdRange.setFdViewType(1);
            }else if(BooleanUtils.isNotTrue(fdRange.getFdIsOpenLimit())){
                fdRange.setFdIsOpenLimit(Boolean.TRUE);
                fdRange.setFdViewType(1);
            }
        }
        return fdRange;
    }

    public void setFdRange(SysOrgElementRange fdRange) {
        this.fdRange = fdRange;
    }

    public SysOrgElementRange getHbmRange() {
        return this.fdRange;
    }

    public void setHbmRange(SysOrgElementRange fdRange) {
        this.fdRange = fdRange;
    }

    /**
     * 隐藏组织范围
     */
    private SysOrgElementHideRange fdHideRange;

    public SysOrgElementHideRange getFdHideRange() {
        if (BooleanUtils.isTrue(fdIsExternal) && (this.fdOrgType == ORG_TYPE_ORG || this.fdOrgType == ORG_TYPE_DEPT)) {
            // 隐藏属性在生态中，默认开启。但是也支持关闭
            if (fdHideRange == null) {
                fdHideRange = new SysOrgElementHideRange();
                fdHideRange.setFdIsOpenLimit(Boolean.TRUE);
                fdHideRange.setFdViewType(0);
            }
        }
        return fdHideRange;
    }

    public void setFdHideRange(SysOrgElementHideRange fdHideRange){
        this.fdHideRange = fdHideRange;
    }

    public SysOrgElementHideRange getHbmHideRange() {
        return this.fdHideRange;
    }

    public void setHbmHideRange(SysOrgElementHideRange fdHideRange) {
        this.fdHideRange = fdHideRange;
    }

    /**
     * 外部组织扩展
     */
    protected SysOrgElementExternal fdExternal;

    public SysOrgElementExternal getFdExternal() {
        return fdExternal;
    }

    public void setFdExternal(SysOrgElementExternal fdExternal) {
        this.fdExternal = fdExternal;
    }

    public SysOrgElementExternal getHbmExternal() {
        return this.fdExternal;
    }

    public void setHbmExternal(SysOrgElementExternal fdExternal) {
        this.fdExternal = fdExternal;
    }

    /**
     * 创建者
     */
    private SysOrgPerson docCreator;

    /**
     * @return 创建者
     */
    public SysOrgPerson getDocCreator() {
        return this.docCreator;
    }

    /**
     * @param docCreator 创建者
     */
    public void setDocCreator(SysOrgPerson docCreator) {
        this.docCreator = docCreator;
    }

}
