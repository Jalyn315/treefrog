package com.shuwa.treefrog.entity;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 分类-实体
 */
@Component
public class Type {
    private Integer id; //类型 id
    private String typeName; // 类型名称
    private Date createTime; //创建类型时间

    public Type() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", typeName='" + typeName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
