package com.softeem.orderapp.bean;

/**
 * Created by Edward on 2017/6/23.
 */

public class TableBean {
    // 桌子编号
    private int id;
    private int tableNumber;

    // 人数
    private int personNum;

    // 状态
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return id;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TableBean{" +
                "tableId=" + id +
                ", tableNumber=" + tableNumber +
                //", personNumber=" + personNumber +
                ", status='" + status + '\'' +
                '}';
    }
}
