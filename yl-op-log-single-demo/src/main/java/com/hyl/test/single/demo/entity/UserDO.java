package com.hyl.test.single.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hyl.op.log.annotations.OpLogDbField;

/**
 * @author huayuanlin
 * @date 2021/08/09 23:31
 * @desc the class desc
 */
@TableName("sy_user")
public class UserDO {


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 如果你的字段与数据库的下划线格式不对应，
     * 需要添加以下注解在新增时才会记录到，无需记录的字段忽视。
     */
    @OpLogDbField(value = "name")
    private String name;

    @TableLogic(value = "true",delval = "false")
    private Boolean isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
