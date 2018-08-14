var MENU = {
    TREE_ELEMENT: "parentCode",
    EDIT_URL: "/rest/sys/menu/update",
    LIST_JSON_URL: "/rest/sys/menu/json"
};

layui.config({
    base: '/static/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'form', 'treeSelect'], function () {
    var $ = layui.$,
        admin = layui.admin,
        element = layui.element,
        layer = layui.layer,
        laydate = layui.laydate,
        form = layui.form,
        treeselect = layui.treeSelect;

    form.render(null, 'component-form-group');

    /* 自定义验证规则 */
    form.verify({
        menuName: function (value) {
            if (value.length > 6) {
                return '菜单名称最多6个字符';
            }
        }
    });

    /* 监听提交 */
    form.on('submit(component-form-save)', function (data) {
        $.ajax({
            type : "POST",
            url : MENU.EDIT_URL,
            data : JSON.stringify(data.field),
            async : false,
            contentType: "application/json",
            dataType : "json",
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    parent.layer.msg(data.msg);
                    window.parent.location.reload();
                    var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                    parent.layer.close(index);
                } else {
                    parent.layer.alert(data.msg)
                }

            }
        });
        return false;
    });

    /**
     * 渲染下拉树
     */

    var menuCode = $("#menuCode").val();

    treeselect.render({
        elem: "#" + MENU.TREE_ELEMENT,
        data : MENU.LIST_JSON_URL,
        method: "GET",
        value: menuCode
    });
    
    $("#form-cancle").click(function () {
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        parent.layer.close(index);
    });
    
});