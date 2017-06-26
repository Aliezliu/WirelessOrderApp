package com.softeem.orderapp.constant;

/**
 * Created by Edward on 2017/6/20.
 */

public class ServerUrl {
    public static String SERVER_PATH = "http://wos.chinacloudsites.cn/api";
    public static String GET_ALL_TYPE = SERVER_PATH + "/Types";

    public static String GET_ALL_MENU = SERVER_PATH + "/Menus";

    public static String GET_MENU_BY_TYPE = SERVER_PATH + "/menuController/getMenuByType.action?typeId=";
    public static String GET_MENU_BY_ID = SERVER_PATH + "/menuController/getMenuByMenuId.action?menuId=";
    public static String SEARCH_BY_NAME = SERVER_PATH + "/menuController/searchByName.action?shortName=";

    public static String GET_TYPE_BY_MENU_ID = SERVER_PATH + "/menuController/getTypeByMenuId.action?menuId=";

    // 提交订单的接口
    public static String PUT_ORDER = SERVER_PATH + "/orderController/createOrder.action";

    //更新桌子信息接口
    public static String UPDATE_TABLE_STATE = SERVER_PATH + "/tableController/updateTable.action";

    //获取所有桌子编号接口
    public static String GET_ALL_TABLE = SERVER_PATH + "/tableController/listTable.action";
    //获取所有桌子信息的接口
    public static String GET_ALL_TABLE_NUMBERS = SERVER_PATH + "/tableController//listTableNumbers.action";
}
