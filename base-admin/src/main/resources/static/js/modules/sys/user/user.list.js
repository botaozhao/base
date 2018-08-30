var USER = {
    TABLE_ELEMENT: "user-table-page",
    PAGE_ELEMENT: "laypage",
    // 步长
    LIMIT_COUNT: 1,
    // 当前页
    CUR_NUM: 1,
    TYPE: {'1':'超级管理员', '2':'普通管理员'},
    STATUS: {'0':'正常', '1':'删除', '2':'停用'}
};

/**
 * 添加或编辑用户
 * @param edit
 */
USER.addOrEditUser = function (userCode, edit){
    var $ = layui.$;
    var index = layui.layer.open({
        title : edit ? "编辑用户" : "新增用户",
        type : 2,
        content : edit ? URL_PAGE_OBJ.USER_EDIT_PAGE_URL + userCode : URL_PAGE_OBJ.USER_ADD_PAGE_URL,
        success : function(layero, index){
            setTimeout(function(){
                layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
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
 * 格式化用户类型
 * @param value
 * @returns {*}
 */
USER.formatUserType = function (value) {
    return USER.TYPE[value];
};

/**
 * 格式化用户状态
 * @param value
 * @returns {*}
 */
USER.formatUserStatus = function (value) {
    return USER.STATUS[value];
};

/**
 * 删除用户
 * @param userCode
 */
USER.deleteUser = function (userCode) {
    var $ = layui.$;
    $.ajax({
        type : "POST",
        url : URL_REST_OBJ.USER_DEL_URL + "/" + userCode,
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
 * 角色配置
 * @param userCode
 */
USER.configRole = function (userCode) {
    var $ = layui.$;
    var index = layui.layer.open({
        title : "角色分配",
        type : 2,
        content : "/sys/user/role/list/" + userCode,
        success : function(layero, index){
            setTimeout(function(){
                layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
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
    var $ = layui.$, admin = layui.admin, table = layui.table, laypage = layui.laypage, form = layui.form;

    var userListTable = table.render({
        elem: '#' + USER.TABLE_ELEMENT,
        url: URL_REST_OBJ.USER_LIST_URL,
        cols: [[
            {type:'numbers'},
            {field: 'loginCode', title: '登录账号'},
            {field: 'userName', title: '用户姓名'},
            {field: 'email', title: '邮箱'},
            {field: 'mobile', title: '手机'},
            {field: 'status', title: '状态', templet: '<div>{{USER.formatUserStatus(d.status)}}</div>'},
            {field: 'userType', title: '用户类型', templet: '<div>{{USER.formatUserType(d.userType)}}</div>'},
            {field: '操作', width:100, title: '操作', toolbar: '#table-operate-toolbar'}
        ]],
        page: true
    });

    table.on('tool(' + USER.TABLE_ELEMENT + ')', function(obj){
        var data = obj.data,      // 获得当前行数据
            layEvent = obj.event; // 获得 lay-event 对应的值
        if(layEvent == 'del_user'){
            layer.confirm('确认删除用户？', function(index){
                //obj.del(); //删除对应行（tr）的DOM结构
                layer.close(index);
                //向服务端发送删除指令
                USER.deleteUser(data.userCode);
                table.reload(USER.TABLE_ELEMENT, {});
            });
        } else if(layEvent == 'edit_user'){
            USER.addOrEditUser(data.userCode, true);
        } else if (layEvent == 'setting') {
            USER.configRole(data.userCode);
        }
    });

    var active = {
        addUser: function(){
            USER.addOrEditUser("0", false);
        }
    };

    $('.user-table-reload-btn .layui-btn').on('click', function(){
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });

    form.on('submit(search)', function(data){
        userListTable.reload({
            where: {
                loginCode: $("#loginCode").val(),
                userName: $("#userName").val(),
                email: $("#email").val(),
                mobile: $("#mobile").val()
            },
            page: {
                curr: 1
            }
        });
        return false;
    });

});
