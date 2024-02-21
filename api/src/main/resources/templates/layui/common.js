function request(url, type, data, func) {
    let index = layui.layer.load({ shade: [0.8, '#393D49'], area: 'window' })
    let contentType = null
    if (type === 'post' || type === 'put') {
        contentType = "application/json"
        data = JSON.stringify(data)
    }
    $.ajax({
        url: url,
        method: type,
        contentType: contentType,
        data: data
    }).done(res => {
        func(res)
        layui.layer.close(index)
    })
}
function upload(url, id, func) {
    layui.use('upload', () => {
        let index
        //执行实例
        layui.upload.render({
            elem: '#' + id //绑定元素
            , url: url //上传接口
            , field: 'picture'
            , size: 5120
            , before: () => {
                index = layui.layer.load({ shade: [0.8, '#393D49'], area: 'window' })
            }
            , done: res => {
                //上传完毕回调
                func(res.data)
                layui.layer.close(index)
            }
        })
    })
}