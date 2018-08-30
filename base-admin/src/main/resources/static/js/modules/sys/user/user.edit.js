layui.config({
    base: '/static/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'form', 'treeSelect'], function () {
    var $ = layui.$,
        form = layui.form;

    form.render(null, 'user-form-group');

    /* 监听提交 */
    form.on('submit(user-form-edit)', function (data) {
        $.ajax({
            type : "POST",
            url : URL_REST_OBJ.USER_UPDATE_URL,
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