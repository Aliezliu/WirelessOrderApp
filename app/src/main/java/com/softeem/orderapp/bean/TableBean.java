package com.softeem.orderapp.bean;

/**
 * Created by Edward on 2017/6/23.
 */

public class TableBean {
    // 桌子编号
    private int id;

    private int personNum;

    private boolean isSmoke;

    private String shape;

    private boolean status;

    private int site;

    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public boolean isSmoke() {
        return isSmoke;
    }

    public void setSmoke(boolean smoke) {
        isSmoke = smoke;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TableBean{" +
                "id=" + id +
                ", personNum=" + personNum +
                ", isSmoke=" + isSmoke +
                ", shape=" + shape + '\'' +
                ",status="+status+
                ",site="+site+
                ",remark"+remark+
                '}';
    }
}
