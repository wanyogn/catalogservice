$(function(){
    $.ajax({
        type: 'post',
        url: 'http://localhost:9005/catalog/queryDirectory',
        data: {},
        async: false,
        success: function (result) {
            var arr = JSON.parse(result);
            var _html = "<option value='-1'>---请选择要上传的内容目录---</option>";
            for(var i = 0; i < arr.length; i++){
                var data = arr[i];
                _html += ("<option value="+ data.directory_number +">"+data.directory_name+"</option>");
            }

            $(".selectParent").html(_html);
        },
        error:function(error){
            alert("系统繁忙。。");
        }
    });
})
function startUpload(){
    var selected = $(".selectParent").val();
    var content = UE.getEditor('editor').getContent();
    if(selected == '-1'){
        alert("选择操作目录")
        $(".selectParent").focus();
        return;
    }
    $.ajax({
        type: 'post',
        url: 'http://localhost:9005/catalog/introductionUpload',
        data: {
            "selected":selected,
            "content":content
        },
        async: false,
        success: function (result) {
            alert(result);
        },
        error:function(error){
            alert("系统繁忙。。");
        }
    });

}