$(() => {
    $("#addPage").click(() => {
        layer.open({
            title: '添加一级页面'
            , skin: 'layui-layer-molv'
            , content: '<input id="pageName" type="text" name="title" required lay-verify="required" placeholder="请输入页面名称" autocomplete="off" class="layui-input">'
            , yes: index => {
                request("/management/page", "post", { name: $("#pageName").val() }, _ => { location.reload() })
                layer.close(index); //如果设定了yes回调，需进行手工关闭
            }
        });
    })
    //页面点击方法
    $(".layui-nav-item").click(e => {
        if ($(e.target).attr("role") === "page") {
            getPageInfo($(e.target).prop("id"))
        }
    })
})
function updatePage() {
    let id = $("#pagePanel").attr('role')
    layer.open({
        title: '修改页面名'
        , skin: 'layui-layer-molv'
        , content: '<input id="pageName" type="text" name="title" required lay-verify="required" placeholder="请输入页面名称" autocomplete="off" class="layui-input">'
        , yes: index => {
            request("/management/page"
                , "put"
                , { name: $("#pageName").val(), id: id }
                , _ => { location.reload() })
            layer.close(index); //如果设定了yes回调，需进行手工关闭
        }
    });
}
function getLastStr(str) {
    let index = str.lastIndexOf("/");
    return str.substring(index + 1, str.length);
}
function getPageInfo(id) {
    request("/management/page", "get", { id: id }, res => {
        let page = res.data
        let subPages = page.subPageList
        $("#quote").text("【" + page.name + "】页面共有" + subPages.length + "个子页面")
        if (page.imgUrl !== null) {
            $("#pagePicture").prop('src', page.imgUrl)
        } else {
            $("#pagePicture").prop('src', '')
        }
        let html = ''
        for (index in subPages) {
            html += '<tr><td>' + subPages[index].name + '<button type="button" class="layui-btn" id="addSubPage" onclick="updateSubPage(' + subPages[index].id + ')">修改子页面名称</button></td></tr>';
        }
        $("#subPageTable").html(html)
    })
}
function addSubPage() {
    let id = $("#pagePanel").attr('role')
    layer.open({
        title: '添加二级页面'
        , skin: 'layui-layer-molv'
        , content: '<input id="subPageName" type="text" name="title" required lay-verify="required" placeholder="请输入子页面名称" autocomplete="off" class="layui-input">'
        , yes: index => {
            request("/management/subPage"
                , "post"
                , { name: $("#subPageName").val(), pageId: id }
                , _ => { location.reload() })
            layer.close(index); //如果设定了yes回调，需进行手工关闭
        }
    });
}
function getSubPage(id, func) {
    request("/management/subPage/" + id, "get", null, res => { func(res.data.content) })
}
function updateSubPage(id) {
    layer.open({
        title: '修改二级页面名称'
        , skin: 'layui-layer-molv'
        , content: '<input id="subPageName" type="text" name="title" required lay-verify="required" placeholder="请输入子页面名称" autocomplete="off" class="layui-input">'
        , yes: index => {
            request("/management/subPage"
                , "put"
                , { name: $("#subPageName").val(), id: id }
                , _ => { location.reload() })
            layer.close(index); //如果设定了yes回调，需进行手工关闭
        }
    });
}