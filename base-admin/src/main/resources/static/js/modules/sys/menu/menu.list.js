/**
 * 定义常量
 * @type {{ID: string, LIST_URL: string, TYPE: {"1": string, "2": string}, IS_SHOW: {"1": string, "0": string}}}
 */
var MENU = {
    ID: "menuTreeTable",
    LIST_URL: "/rest/sys/menu/list",
    DEL_URL: "/rest/sys/menu/delete",
    ADD_PAGE_URL: "/sys/menu/add/",
    EDIT_PAGE_URL: "/sys/menu/edit/",
    TYPE: {'1':'菜单', '2':'权限'},
    IS_SHOW: {'1':'显示', '0':'隐藏'}
};

/**
 * 格式化类型
 * @param value
 * @returns {*}
 */
MENU.formatMenuType = function (value) {
    return MENU.TYPE[value];
};

/**
 * 格式化可见
 * @param value
 * @returns {*}
 */
MENU.formatMenuShow = function (value) {
    return MENU.IS_SHOW[value];
};

/**
 * 删除菜单
 * @param menuCode
 */
MENU.deleteMenu = function (menuCode) {
    var $ = layui.$;
    $.ajax({
        type : "POST",
        url : MENU.DEL_URL + "/" + menuCode,
        async : false,
        contentType: "application/json",
        dataType : "json",
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg(data.msg);
                // var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                // parent.layer.close(index);

            } else {
                parent.layer.msg(data.msg);
            }

        }
    });
};

/**
 * 添加菜单
 * @param edit
 */
MENU.addOrEditMenu = function (menuCode, edit){
    var $ = layui.$;
    var index = layui.layer.open({
        title : edit ? "编辑菜单" : "添加菜单",
        type : 2,
        content : edit ? MENU.EDIT_PAGE_URL + menuCode : MENU.ADD_PAGE_URL + menuCode ,
        success : function(layero, index){
            setTimeout(function(){
                layui.layer.tips('点击此处返回菜单列表', '.layui-layer-setwin .layui-layer-close', {
                    tips: 3
                });
            },500);
        }
    })
    layui.layer.full(index);
    //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
    $(window).on("resize",function(){
        layui.layer.full(index);
    })
};


layui.config({
    base: '/static/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['element', 'layer', 'form', 'upload', 'treeGrid'], function () {
    var $ = layui.$,
        treeGrid = layui.treeGrid;
    treeGrid.render({
        elem: '#' + MENU.ID,
        url: MENU.LIST_URL,
        type: 'get',
        cellMinWidth: 100,
        treeId: 'menuCode',         // 树形自己ID字段名称
        treeUpId: 'parentCode',    // 树形父id字段名称
        treeShowName: 'menuName', // 以树形式显示的字段
        cols: [[
            // {type: 'checkbox'},
            {field: 'menuName', title: '菜单名称'},
            {field: 'menuHref', title: '链接'},
            {field: 'treeSort', title: '排序'},
            {field: 'menuType', title: '类型', templet: '<div>{{MENU.formatMenuType(d.menuType)}}</div>'},
            {field: 'isShow', title: '可见', templet: '<div>{{MENU.formatMenuShow(d.isShow)}}</div>'},
            {field: 'permission', title: '权限标识'},
            {field: '操作', width: 80, title: '操作', toolbar: '#table-operate-toolbar'}
        ]],
        page: false
    });

    treeGrid.on('tool(' + MENU.ID + ')', function(obj){
        var data = obj.data,      // 获得当前行数据
            layEvent = obj.event; // 获得 lay-event 对应的值
        if(layEvent == 'add_menu'){
            MENU.addOrEditMenu(data.menuCode, false);
        } else if(layEvent == 'del_menu'){
            layer.confirm('确认删除菜单？', function(index){
                //obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                MENU.deleteMenu(data.menuCode);

                treeGrid.reload(MENU.ID, {});
            });
        } else if(layEvent == 'edit_menu'){
            MENU.addOrEditMenu(data.menuCode, true);
        }
    });

    var active = {
        addMenu: function(){ //获取选中数据
            MENU.addOrEditMenu("0", false);
        }
    };

    $('.menu-table-reload-btn .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

});


