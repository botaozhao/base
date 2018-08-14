var USER = {
    FORM_ELEMENT: "user-form-group",
    FORM_SUBMIT_ELEMENT: "user-form-save",
    ADD_URL: "/rest/sys/user/save"
};

layui.config({
    base: '/static/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'form'], function () {
    var $ = layui.$,
        form = layui.form;

    form.render(null, USER.FORM_ELEMENT);

    /* 监听提交 */
    form.on('submit(' + USER.FORM_SUBMIT_ELEMENT + ')', function (data) {
        $.ajax({
            type : "POST",
            url : USER.ADD_URL,
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
                    parent.layer.alert(data.msg);
                }
            }
        });
        return false;
    });

});