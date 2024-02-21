$(function () {
    //添加page
    let addPagTipIndex
    $("#addPage").hover(() => {
        addPagTipIndex = layer.tips('添加一级页面', '#addPage')
    }, () => {
        layer.close(addPagTipIndex)
    })
    $(".layui-nav-item a").click(e => {
        //边框变亮
        $(e.target).addClass("layui-this")
        //面板切换
        hidePanel()
        if ($(e.target).attr("role") === "page") {
            $("#pagePanel").show()
        }
        else if ($(e.target).attr("role") === "subPage") {
            $("#subPagePanel").show()
        }
        else if ($(e.target).attr("role") === "main") {
            $("#mainPanel").show()
        }
        else if ($(e.target).attr("role") === "aboutUs") {
            $("#aboutUsPanel").show()
        }
        $("#pagePanel").attr('role', $(e.target).prop('id'))
    })
    $(".layui-nav-child a").click(e => {
        $("#subPagePanel .layui-btn").attr('onclick', 'saveSubPage(' + $(e.target).prop('id') + ')')
        getSubPage($(e.target).prop('id'), content => { layedit.setContent(subPageEditor, content) })
    })
    //边框失焦
    $(".layui-nav-item a").on("focus blur", e => {
        $(e.target).removeClass("layui-this")
        $(e.target).parent().removeClass("layui-this")
    })
    $(".layui-nav-child a").on("focus blur", e => {
        $(e.target).parent().removeClass("layui-this")
    })
    indexCss()
    loadBanner()
    loadTitle()
})
$(window).on("resize", () => {
    indexCss()
})
$(document).ready(function () {
})
function indexCss() {
    //index中主题画面
    let left = ($(".layui-body").width() - 400) / 2
    let height = ($(".layui-body").height() - 250) / 2
    $("#indexImg").css("margin-left", left)
    $("#indexImg").css("margin-top", height)
}
function hidePanel() {
    $("#indexPanel").hide()
    $("#mainPanel").hide()
    $("#pagePanel").hide()
    $("#subPagePanel").hide()
    $("#aboutUsPanel").hide()
}
//首页title
upload('/management/upload/picture/title', 'uploadTitle', url => { $("#title").attr('src', url) })
function loadTitle() {
    request('/management/title', 'get', null, res => {
        let url = res.data
        if (url !== null) {
            $("#title").attr('src', url)
        }
    })
}

//首页轮播
upload('/management/upload/picture/banner', 'uploadBanner', () => { loadBanner() })
var ins
var options = {
    elem: '#banner'
    , arrow: 'always' //始终显示箭头
    , autoplay: false
    //,anim: 'updown' //切换动画方式
}
layui.use('carousel', function () {
    //建造实例
    ins = layui.carousel.render(options)
})
function loadBanner() {
    request("/management/allBanners", "get", null, res => {
        let bannerList = res.data
        let html = ''
        for (let index in bannerList) {
            html += '<div id="' + bannerList[index].id + '"><img src="' + bannerList[index].imgUrl + '" style="height: 100%; "/></div>'
        }
        $("#banner [carousel-item]").html(html)
        ins.reload(options)
    })
}
function deleteBanner() {
    for (index in ins.elemItem) {
        let jqObj = $(ins.elemItem[index])
        if (jqObj.hasClass('layui-this')) {
            request("management/banner/" + jqObj.prop('id'), "delete", null, res => { loadBanner() })
            break
        }
    }
}
//页面头图
upload('/management/upload/picture', 'uploadPagePicture', picture => {
    request("/management/page", "put", { id: $("#pagePanel").attr('role'), imgId: picture.id }, res => {
        $("#pagePicture").attr('src', picture.url)
    })
})

//富文本编辑器
var subPageEditor
var layedit
layui.use('layedit', () => {
    layedit = layui.layedit
    let height = $(window).height()
    subPageEditor = layedit.build('subPageEditor', {
        height: height * 0.8 //设置编辑器高度
        , uploadImage: {
            url: '/management/upload/picture/content' //接口url
            , type: 'post'
        }
        , tool: ['strong' //加粗
            , 'italic' //斜体
            , 'underline' //下划线
            , 'del' //删除线
            , '|' //分割线
            , 'left' //左对齐
            , 'center' //居中对齐
            , 'right' //右对齐
            , 'link' //超链接
            , 'unlink' //清除链接
            , 'image' //插入图片
        ]
    })
})
function saveSubPage(id) {
    request("/management/subPage", "put", { id: id, content: layui.layedit.getContent(subPageEditor) }, _ => { layer.msg('保存成功') })
}