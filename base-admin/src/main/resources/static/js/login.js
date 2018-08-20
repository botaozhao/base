layui.config({
    base: '/static/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'user'], function(){
    var $ = layui.$
        ,setter = layui.setter
        ,admin = layui.admin
        ,form = layui.form
        ,router = layui.router()
        ,search = router.search;

    form.render();

    //提交
    form.on('submit(submit)', function(obj){

        console.log(JSON.stringify(obj.field));
        console.log(obj.field);

        $.ajax({
            type : "POST",
            url : "/rest/login/doLogin",
            data : JSON.stringify(obj.field),
            async : false,
            contentType: "application/json",
            dataType : "json",
            error : function(request) {
                parent.layer.alert("Connection error");
            },
            success : function(data) {
                if (data.code == 0) {
                    parent.layer.msg(data.msg);
                    location.href = "/index";
                } else {
                    parent.layer.alert(data.msg)
                }

            }
        });
        return false;
    });

});