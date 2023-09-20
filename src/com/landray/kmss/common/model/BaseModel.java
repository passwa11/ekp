package com.landray.kmss.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.HibernateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectUtil;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;
import org.hibernate.mapping.PersistentClass;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 域模型的基类，建议大部分的域模型继承，只有域模型继承了该类，后面的Dao、Service、Action等才能继承通用的相应的实现。<br>
 * 使用限制条件：<br>
 * 1、对应数据库表必须为单主键，主键的数据类型为long类型，名称必须为f_id；<br>
 * 2、域模型中对主键的映射为fdId（全系统内置，继承后不需要在域模型中添加该域的声明）
 *
 * @author 叶中奇
 * @version 1.0 2006-04-02
 */
public abstract class BaseModel implements IBaseModel, Serializable {

    protected transient PersistentAttributeInterceptor interceptor;

    /**
     * 自定义属性
     */
    private CustomPropList<Map<String, Object>> customPropList = new CustomPropList<Map<String, Object>>();

    /**
     * 自定义属性
     */
    @SuppressWarnings("unused")
    private Map<String, Object> customPropMap = customPropList.getCustomPropMap();

    protected SysDictModel sysDictModel;

    protected String fdId;

    @Override
    public String getFdId() {
        if (fdId == null) {
            fdId = IDGenerator.generateID();
        }
        return fdId;
    }

    @Override
    public void setFdId(String id) {
        this.fdId = id;
    }

    @Override
    public void recalculateFields() {

    }

    @Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        return new ModelToFormPropertyMap();
    }

    /**
     * 覆盖toString方法，使用列出域模型中的所有get方法返回的值（不获取返回值类型为非java.lang.*的值）
     *
     * @see Object#toString()
     */
    @Override
    public String toString() {
        try {
            PersistentClass persistentClass = HibernateUtil.getPersistentClass(this.getClass());
            Method[] methodList = this.getClass().getMethods();
            ToStringBuilder rtnVal = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
            for (int i = 0; i < methodList.length; i++) {
                String methodName = methodList[i].getName();
                if (methodList[i].getParameterTypes().length > 0 || !methodName.startsWith("get")
                        || "getClass".equals(methodName)) {
                    continue;
                }
                methodName = methodList[i].getReturnType().toString();
                if ((methodName.startsWith("class") || methodName.startsWith("interface"))
                        && !(methodName.startsWith("class java.lang.")
                        || methodName.startsWith("interface java.lang."))) {
                    continue;
                }
                methodName = methodList[i].getName();
                // 优化#153816，遍历model对象属性时，跳过懒加载,大字段等属性，以提升性能，如果想获取这些信息，业务上应该显示的调用getXXX()
                String propertyName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                if (persistentClass.hasProperty(propertyName)
                        && persistentClass.getProperty(propertyName).isLazy()) {
                    continue;
                }
                try {
                    rtnVal.append(methodList[i].getName().substring(3), methodList[i].invoke(this, null));
                } catch (Exception e) {
                }
            }
            return rtnVal.toString().replaceAll("@[^\\[]+\\[\\r\\n", "[\r\n");
        } catch (Exception e) {
            return super.toString();
        }
    }

    /**
     * 覆盖equals方法，仅比较类型是否相等以及关键字是否相等
     *
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (!ModelUtil.getModelClassName(object).equals(ModelUtil.getModelClassName(this))) {
            return false;
        }
        BaseModel objModel = (BaseModel) object;
        return ObjectUtil.equals(objModel.getFdId(), this.getFdId(), false);
    }

    /**
     * 覆盖hashCode方法，通过模型中类名和ID构建哈希值
     *
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        HashCodeBuilder rtnVal = new HashCodeBuilder(-426830461, 631494429);
        rtnVal.append(ModelUtil.getModelClassName(this));
        rtnVal.append(getFdId());
        return rtnVal.toHashCode();
    }

    @SuppressWarnings("unchecked")
    protected Object readLazyField(String fieldName, Object oldValue) {
        if (null != interceptor) {
            oldValue = interceptor.readObject(this, fieldName, oldValue);
        }
        return oldValue;
    }

    protected Object writeLazyField(String fieldName, Object oldValue, Object newValue) {
        if (null == interceptor) {
            return newValue;
        }

        return interceptor.writeObject(this, fieldName, oldValue, newValue);
    }

    @JsonIgnore
    public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
        return interceptor;
    }

    public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public SysDictModel getSysDictModel() {
        return sysDictModel;
    }

    @Override
    public void setSysDictModel(SysDictModel sysDictModel) {
        this.sysDictModel = sysDictModel;
    }

    public Map<String, String> dynamicMap = new HashMap<String, String>();

    @Override
    public Map<String, String> getDynamicMap() {
        return dynamicMap;
    }

    public void setDynamicMap(Map<String, String> dynamicMap) {
        this.dynamicMap = dynamicMap;
    }

    /**
     * please use  getCustomPropList
     *
     * @return
     */
    @Override
    public Map<String, Object> getCustomPropMap() {
        return this.customPropList.getCustomPropMap();

    }

    /**
     * please use  setCustomPropList
     *
     * @param customPropMap
     */
    public void setCustomPropMap(Map<String, Object> customPropMap) {
        this.customPropMap = customPropMap;
        this.customPropList.setCustomPropMap(customPropMap);
    }

    public CustomPropList<Map<String, Object>> getCustomPropList() {
        return this.customPropList;
    }

    public void setCustomPropList(CustomPropList<Map<String, Object>> customPropList) {
        this.customPropList = customPropList;
    }

    /**
     * 机制类
     */
    private Map<String, Object> mechanismMap = new HashMap<>();

    @Override
    public Map<String, Object> getMechanismMap() {
        return mechanismMap;
    }

    @Override
    public void setMechanismMap(Map<String, Object> mechanismMap) {
        this.mechanismMap = mechanismMap;
    }

    @JsonIgnore
    private transient Map<String, Object> transientInfoMap = new HashMap<>();

    /**
     * 用于在复杂业务中存储一些<b>不需要持久化</b>的相关信息
     * @return
     */
    @Override
    public Map<String, Object> getTransientInfoMap(){
        return transientInfoMap;
    }

    @Override
    public void setTransientInfoMap(Map<String, Object> transientInfoMap){
        this.transientInfoMap = transientInfoMap;
    }
}
