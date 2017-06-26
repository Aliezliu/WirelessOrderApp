package com.softeem.orderapp.bean;

/**
 * Created by Edward on 2017/6/23.
 */

public class TableBean {
    // 桌子编号
    private int tableId;
    private int tableNumber;

    // 人数
    private int personNumber;

    // 状态
    private boolean status;

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(int personNumber) {
        this.personNumber = personNumber;
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
                "tableId=" + tableId +
                ", tableNumber=" + tableNumber +
                ", personNumber=" + personNumber +
                ", status='" + status + '\'' +
                '}';
    }
}
