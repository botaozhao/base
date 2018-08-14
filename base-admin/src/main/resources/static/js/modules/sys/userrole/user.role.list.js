var ROLE = {
    TABLE_ELEMENT: "table-page",
    PAGE_ELEMENT: "laypage",
    DATA_LIST_URL: "/rest/sys/user/role/list",
    // 步长
    LIMIT_COUNT: 1,
    // 当前页
    CUR_NUM: 1,
    IS_SYS: {'1':'是', '0':'否'},
    SAVE_USER_ROLE: "/rest/sys/user/saveUserRole"
};

/**
 * 格式化系统内置
 * @param value
 * @returns {*}
 */
ROLE.formatIsSys = function (value) {
    return ROLE.IS_SYS[value];
};

layui.config({
    base: '/static/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'table'], function () {
    var $ = layui.$, table = layui.table, form = layui.form;

    var userCode = $("#userCode").val();

    table.render({
        elem: '#' + ROLE.TABLE_ELEMENT,
        url: ROLE.DATA_LIST_URL + "?userCode=" + userCode,
        cols: [[
            {type:'checkbox', fixed: 'left'},
            {field: 'roleName', title: '角色名称'},
            {field: 'isSys', title: '系统内置', templet: '<div>{{ROLE.formatIsSys(d.isSys)}}</div>'}
        ]],
        page: true
    });
    
    $("#table-save").click(function () {
        var checkStatus = table.checkStatus(ROLE.TABLE_ELEMENT)
            ,data = checkStatus.data, selectRoleCode = new Array();
        if (data.length > 0) {
            for (i in data) {
                selectRoleCode.push(data[i].roleCode);
            }

            var dataArray = {userCode: userCode, roleCode: selectRoleCode.join(",")};
            $.ajax({
                type : "POST",
                url : ROLE.SAVE_USER_ROLE,
                data : JSON.stringify(dataArray),
                async : false,
                contentType: "application/json",
                dataType : "json",
                error : function(request) {
                    parent.layer.alert("Connection error");
                },
                success : function(data) {
                    if (data.code == 0) {
                        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                        parent.layer.close(index);
                    } else {
                        parent.layer.alert(data.msg)
                    }

                }
            });
        }
    });
    
    $("#table-cancle").click(function () {
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        parent.layer.close(index);
    });

});
