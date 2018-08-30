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

    getCode();

    $("#getVercode").click(function () {
        getCode();
    });

    //提交
    form.on('submit(submit)', function(obj){
        $.ajax({
            type : "POST",
            url : URL_REST_OBJ.LOGIN_URL,
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
                    parent.layer.alert(data.msg);
                }

            }
        });
        return false;
    });

});

/**
 * 获取验证码
 */
var getCode = function () {
    var $ = layui.$;
    $.ajax({
        type : "get",
        url : URL_REST_OBJ.GET_CODE_URL,
        async : false,
        contentType: "application/json",
        dataType : "json",
        error : function(request) {
            parent.layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                var tokenCode = data.tokenCode;
                var base64Img = data.verifyCode;
                $("#getVercode").attr("src", "data:image/gif;base64," + base64Img);
                $("#tokenCode").val(tokenCode);
            } else {
                parent.layer.alert(data.msg);
            }
        }
    });
};