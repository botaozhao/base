var ROLE = {
    FORM_RENDER_ELEMENT: "form-group",
    FORM_SUBMIT_ELEMENT: "form-edit",
    FORM_MENU_ELEMENT: "menu_div"
};

layui.config({
    base: '/static/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['index', 'form', 'treeSelect'], function () {
    var $ = layui.$,
        form = layui.form;

    form.render(null, ROLE.FORM_RENDER_ELEMENT);

    var roleCode = $("#roleCode").val();

    /* 监听提交 */
    form.on('submit(' + ROLE.FORM_SUBMIT_ELEMENT + ')', function (data) {
        var oCks = menuTree.GetChecked(); //这是方法
        var selectMenuCode = new Array();
        for (var i = 0; i < oCks.length; i++) {
            selectMenuCode.push(oCks[i].value);
        }
        var dataArray = {roleCode: roleCode,menuCode: selectMenuCode.join(",")};
        $.ajax({
            type : "POST",
            url : URL_REST_OBJ.ROLE_MENU_ADD_URL,
            // data : JSON.stringify(data.field),
            data : JSON.stringify(dataArray),
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

    var menuTree = new layuiXtree({
        elem: ROLE.FORM_MENU_ELEMENT,
        form: form,
        data: URL_REST_OBJ.ROLE_MENU_JSON_URL + roleCode
    });

    $("#form-cancle").click(function () {
        var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
        parent.layer.close(index);
    });

});