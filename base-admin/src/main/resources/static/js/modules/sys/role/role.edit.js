var ROLE = {
    EDIT_URL: "/rest/sys/role/update",
    FORM_RENDER_ELEMENT: "form-group",
    FORM_SUBMIT_ELEMENT: "form-edit"
};

layui.config({
    base: '/static/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'form', 'treeSelect'], function () {
    var $ = layui.$,
        form = layui.form;

    form.render(null, ROLE.FORM_RENDER_ELEMENT);

    /* 监听提交 */
    form.on('submit(' + ROLE.FORM_SUBMIT_ELEMENT + ')', function (data) {
        $.ajax({
            type : "POST",
            url : URL_REST_OBJ.ROLE_UPDATE_URL,
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

    $("#form-cancle").click(function () {
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        parent.layer.close(index);
    });

});