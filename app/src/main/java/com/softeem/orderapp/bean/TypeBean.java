package com.softeem.orderapp.bean;

public class TypeBean {
    private int typeId = -1;
    private String typeName;
    private int typeParentId;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeParentId() {
        return typeParentId;
    }

    public void setTypeParentId(int typeParentId) {
        this.typeParentId = typeParentId;
    }

    @Override
    public String toString() {
        return "TypeBean [typeId=" + typeId + ", typeName=" + typeName + ", typeParentId=" + typeParentId + "]";
    }

    public TypeBean(int typeId, String typeName, int typeParentId) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeParentId = typeParentId;
    }

    public TypeBean() {
    }
}
