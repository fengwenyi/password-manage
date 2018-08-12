// ajax
function ajax(urlObj, method, dataObj, func) {
    let token = getToken();
    let loadingObj;
    if (!token) {
        // 需要重新登录
        layer.msg('token已失效，请重新登录');
    } else {
        //
        $.ajax({
            headers : {
                'token' : token
            },
            url : urlObj,
            type : method, 
            async : true,   
            data : dataObj,
            timeout : 3000,   
            dataType : 'json', 
            beforeSend : function(xhr){
                loadingObj = layer.load(0, {shade: false});
            },
            success : function(data, textStatus, jqXHR){
                layer.close(loadingObj)
                let code = data.code
                if (code === 0) {
                    func(data)
                } else {
                    let msg = data.msg
                    layer.msg(msg);
                }
            },
            error : function(xhr, textStatus){
                layer.close(loadingObj)
                layer.msg('提交出错···');
            },
            complete : function(){

            }
        })
    }
}

// get
function ajaxGet(url, func) {
    ajax(url, "GET", '123', func)
}

// post
function ajaxPost(url, data, func) {
    ajax(url, "POST", data, func)
}

// delete
function ajaxDelete(url, func) {
    ajax(url, "DELETE", "", func)
}