package com.landray.kmss.third.ding.dto;


import java.util.List;

/**
 * 查询用户待办列表 参数对象
 *
 * 查询用户待办列表 api 接口
 * https://developers.dingtalk.com/document/app/query-a-user-to-do-list?spm=a2q3p.21071111.0.0.709d65eeK5SBcc
 */
public class DingToDoList {

    //分页游标
    private String nextToken;
    /**  排序字段。默认值为：dueTime
     *
     * created：创建时间
     * modified：更新时间
     * finished：完成时间
     * startTime：开始时间
     * dueTime：截止时间
     * priority：优先级
     */
    private String orderBy;
    //排序方向。默认值：asc
    private String orderDirection;
    //查询目标用户角色类型。
    //待办完成状态
    private Boolean isDone;
    //查询从计划完成时间开始。
    private List<List<String>>  roleTypes;
    //查询从计划完成时间开始。
    private Long fromDueTime;
    //查询到计划完成时间结束
    private Long toDueTime;
    //所属分类
    private String category;
    //待办回收状态
    private Boolean isRecycled;


    public String getNextToken() {
        return nextToken;
    }

    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public List<List<String>> getRoleTypes() {
        return roleTypes;
    }

    public void setRoleTypes(List<List<String>> roleTypes) {
        this.roleTypes = roleTypes;
    }

    public Long getFromDueTime() {
        return fromDueTime;
    }

    public void setFromDueTime(Long fromDueTime) {
        this.fromDueTime = fromDueTime;
    }

    public Long getToDueTime() {
        return toDueTime;
    }

    public void setToDueTime(Long toDueTime) {
        this.toDueTime = toDueTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getRecycled() {
        return isRecycled;
    }

    public void setRecycled(Boolean recycled) {
        isRecycled = recycled;
    }
}
