var APP_URL_EXT = "http://localhost:8898/api";
var PAGE_URL_EXT = "http://localhost:8899";

// rest接口URL
var URL_REST_OBJ = {
    // 登录
    LOGIN_URL: APP_URL_EXT + "/rest/login/doLogin",
    GET_CODE_URL: APP_URL_EXT + "/rest/code/getCode",

    // 菜单
    MENU_JSON_URL: APP_URL_EXT + "/rest/sys/menu/json",
    MENU_LIST_URL: APP_URL_EXT + "/rest/sys/menu/list",
    MENU_DEL_URL: APP_URL_EXT + "/rest/sys/menu/delete",
    MENU_ADD_URL: APP_URL_EXT + "/rest/sys/menu/save",
    MENU_UPDATE_URL: APP_URL_EXT + "/rest/sys/menu/update",

    // 权限
    ROLE_LIST_URL: APP_URL_EXT + "/rest/sys/role/list",
    ROLE_ADD_URL: APP_URL_EXT + "/rest/sys/role/save",
    ROLE_UPDATE_URL: APP_URL_EXT + "/rest/sys/role/update",
    ROLE_DEL_URL: APP_URL_EXT + "/rest/sys/role/delete/",

    // 用户
    USER_LIST_URL: APP_URL_EXT + "/rest/sys/user/list",
    USER_ADD_URL: APP_URL_EXT + "/rest/sys/user/save",
    USER_UPDATE_URL: APP_URL_EXT + "/rest/sys/user/update",
    USER_DEL_URL: APP_URL_EXT + "/rest/sys/user/delete/",

    // 权限菜单
    ROLE_MENU_ADD_URL: APP_URL_EXT + "/rest/sys/role/saveRoleMenu",
    ROLE_MENU_JSON_URL: APP_URL_EXT + "/rest/sys/role/json/",

    // 用户角色
    USER_ROLE_LIST_URL: APP_URL_EXT + "/rest/sys/user/role/list",
    USER_ROLE_SAVE_URL: APP_URL_EXT + "/rest/sys/user/saveUserRole"

};

// page_url
var URL_PAGE_OBJ = {
    // 菜单
    MENU_ADD_PAGE_URL: PAGE_URL_EXT + "/sys/menu/add/",
    MENU_EDIT_PAGE_URL: PAGE_URL_EXT + "/sys/menu/edit/",

    // 权限
    ROLE_ADD_PAGE_URL: PAGE_URL_EXT + "/sys/role/add",
    ROLE_EDIT_PAGE_URL: PAGE_URL_EXT + "/sys/role/edit/",

    // 权限菜单
    ROLE_MENU_PAGE_URL: PAGE_URL_EXT + "/sys/role/rolemenu/",

    // 用户
    USER_ADD_PAGE_URL: "/sys/user/add",
    USER_EDIT_PAGE_URL: "/sys/user/edit/"
}