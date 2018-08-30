var ROLE = {
    TABLE_ELEMENT: "table-page",
    PAGE_ELEMENT: "laypage",
    // 步长
    LIMIT_COUNT: 1,
    // 当前页
    CUR_NUM: 1,
    ADD_EDIT_TITLE: {'0':'编辑角色', '1':'新增角色'},
    ADD_EDIT_TIP: "点击此处返回角色列表",
    IS_SYS: {'1':'是', '0':'否'},
    OPERATE_TOOLBAR_ELEMENT: '#table-operate-toolbar',
    DELETE_CONFIRM_TITLE: '确认删除角色？',
    ROLE_SETTING_TITLE: '配置角色',
    RELOAD_BTN_ELEMENT: '.table-reload-btn .layui-btn'
};

/**
 * 添加或编辑角色
 * @param thisCode  主键code
 * @param edit
 */
ROLE.addOrEdit = function (thisCode, edit){
    var $ = layui.$;
    var index = layui.layer.open({
        title : edit ? ROLE.ADD_EDIT_TITLE[0] : ROLE.ADD_EDIT_TITLE[1],
        type : 2,
        content : edit ? URL_PAGE_OBJ.ROLE_EDIT_PAGE_URL + thisCode : URL_PAGE_OBJ.ROLE_ADD_PAGE_URL,
        success : function(layero, index){
            setTimeout(function(){
                layui.layer.tips(ROLE.ADD_EDIT_TIP, '.layui-layer-setwin .layui-layer-close', {
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

/**
 * 删除角色
 * @param thisCode
 */
ROLE.delete = function (thisCode) {
    var $ = layui.$;
    $.ajax({
        type : "POST",
        url : URL_REST_OBJ.ROLE_DEL_URL + "/" + thisCode,
        async : false,
        contentType: "application/json",
        dataType : "json",
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg(data.msg);
            } else {
                parent.layer.msg(data.msg);
            }

        }
    });
};

/**
 * 格式化系统内置
 * @param value
 * @returns {*}
 */
ROLE.formatIsSys = function (value) {
    return ROLE.IS_SYS[value];
};

/**
 * 配置角色权限
 * @param thisCode  主键code
 */
ROLE.roleSetting = function (thisCode){
    var $ = layui.$;
    var index = layui.layer.open({
        title : ROLE.ROLE_SETTING_TITLE,
        type : 2,
        content : URL_PAGE_OBJ.ROLE_MENU_PAGE_URL + thisCode,
        success : function(layero, index){
            setTimeout(function(){
                layui.layer.tips(ROLE.ADD_EDIT_TIP, '.layui-layer-setwin .layui-layer-close', {
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
}).use(['index', 'table'], function () {
    var $ = layui.$, admin = layui.admin, table = layui.table, form = layui.form;

    var listTable = table.render({
        elem: '#' + ROLE.TABLE_ELEMENT,
        url: URL_REST_OBJ.ROLE_LIST_URL,
        cols: [[
            {type:'numbers'},
            {field: 'roleName', title: '角色名称'},
            {field: 'roleSort', title: '排序'},
            {field: 'isSys', title: '系统内置', templet: '<div>{{ROLE.formatIsSys(d.isSys)}}</div>'},
            {field: '操作', width:100, title: '操作', toolbar: ROLE.OPERATE_TOOLBAR_ELEMENT}
        ]],
        page: true
    });

    table.on('tool(' + ROLE.TABLE_ELEMENT + ')', function(obj){
        var data = obj.data,      // 获得当前行数据
            layEvent = obj.event; // 获得 lay-event 对应的值
        if(layEvent == 'del'){
            layer.confirm(ROLE.DELETE_CONFIRM_TITLE, function(index){
                //obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                ROLE.delete(data.roleCode);
                table.reload(ROLE.TABLE_ELEMENT, {});
            });
        } else if(layEvent == 'edit'){
            ROLE.addOrEdit(data.roleCode, true);
        } else if (layEvent == 'setting') {
            ROLE.roleSetting(data.roleCode);
        }
    });

    var active = {
        add: function(){
            ROLE.addOrEdit("0", false);
        }
    };

    $(ROLE.RELOAD_BTN_ELEMENT).on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    form.on('submit(search)', function(data){
        listTable.reload({
            where: {
                roleName: $("#roleName").val()
            },
            page: {
                curr: 1
            }
        });
        return false;
    });

});
