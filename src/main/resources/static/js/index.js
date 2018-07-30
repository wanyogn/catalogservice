$(function(){
    $.ajax({
        type: 'post',
        url: 'http://localhost:9005/catalog/queryDirectory',
        data: {},
        async: false,
        success: function (result) {
            var arr = JSON.parse(result);
            var _html = '';
            for(var i = 0;i < arr.length;i++){
                _html += ('<button type="button" class="btn btn-default btn_dir" data-toggle="tooltip" data-placement="top" title='
                            +arr[i].directory_name+'>'+arr[i].directory_number
                            +'</button>');
            }
            $("#dir_header").html(_html);

        },
        error:function(error){
            alert("系统繁忙。。");
        }
    });
    $.ajax({
        type: 'post',
        url: 'http://localhost:9005/catalog/queryFirst_Product',
        data: {},
        async: false,
        success: function (result) {
            contentProduct(result);
        },
        error:function(error){
            alert("系统繁忙。。");
        }
    });
    $(".content_second_main a").click(function(){
       $(this).css("color","red");
       $(this).siblings().css("color","#337ab7");
       var first_num = $(this).text().substring(0,2);
       var dir_num = $(this).parents(".content_second").find(".content_second_title").text().substring(0,2);
       var that = this;
        $.ajax({
            type: 'post',
            url: 'http://localhost:9005/catalog/queryProduct',
            data: {
                "dir_num":dir_num,
                "first_num":first_num
            },
            async: false,
            beforeSend: function () {
                layer.load(2);
            },
            complete:function(){
                layer.closeAll('loading');
            },
            success: function (result) {
                contentDetail(result,$(that).parents(".content_second").find(".detail_content"));
            },
            error:function(error){
                alert("系统繁忙。。");
            }
        });
    });
    $("#dir_header button").click(function(){
        var id = $(this).text();
        document.documentElement.scrollTop = document.body.scrollTop = $("#"+id).offset().top;
    });
    $(".search_btn").click(function(){
        var val = $(".search_input").val();
        if($.trim(val) == ''){
            layer.alert('请输入关键词！', {
                icon: 3,
                skin: 'layer-ext-moon'
            })
            return;
        }
    window.location.href="search";

    })
})
/*装配详情*/
function contentProduct(data){
    var arr = JSON.parse(data);
    for(var i = 0;i < arr.length;i++){
        $(".product_content").append($("#product_template").html());
    }
    var data_list = $(".content_second");
    for(var i = 0;i < arr.length;i++){
        var obj = arr[i];
        $(data_list[i].getElementsByClassName("content_second_title")).html(obj.name);//目录名称
        $(data_list[i]).attr("id",obj.name.substring(0,2));
        var productArr = obj.second;//二级目录的数组
        var _html = '';
        for(var j = 0;j < productArr.length;j++){
            _html +=  '<a>'+productArr[j].first_product_number+" "+productArr[j].first_product_name+'</a>'

        }
        $(data_list[i].getElementsByClassName("content_second_main")).append(_html);
    }
}
//内容的切换
function contentDetail(data,dom){
    dom.html("");
    var arr = JSON.parse(data);
    for(var i = 0;i<arr.length;i++){
        dom.append($("#detail_template").html());
    }
    var data_list = dom.find(".data-list");
    for(var i = 0;i<arr.length;i++){
        var obj = arr[i];
        if(i > 0){
            var number1 = obj.directory_number+obj.first_product_number+obj.second_product_number;
            var number2 = arr[i-1].directory_number+arr[i-1].first_product_number+arr[i-1].second_product_number;
            if(number1 == number2){
                $(data_list[i]).css("border-top","1px solid #e0e0e0");
                $(data_list[i].getElementsByClassName("product_description")).append(obj.product_description);
                $(data_list[i].getElementsByClassName("excepted_use")).append(obj.expected_use);
                $(data_list[i].getElementsByClassName("product_example")).append(obj.product_example);
            }else{
                $(data_list[i]).css("border-top","1px solid #337ab7");
                $(data_list[i].getElementsByClassName("number")).css("border","1px solid #e0e0e0");
                $(data_list[i].getElementsByClassName("product_title")).append(obj.second_product_name);
                $(data_list[i].getElementsByClassName("product_description")).append(obj.product_description);
                $(data_list[i].getElementsByClassName("excepted_use")).append(obj.expected_use);
                $(data_list[i].getElementsByClassName("product_example")).append(obj.product_example);
                $(data_list[i].getElementsByClassName("number")).append(obj.directory_number+obj.first_product_number+obj.second_product_number);
            }
        }else{
            $(data_list[i]).css("border-top","1px solid #337ab7");
            $(data_list[i].getElementsByClassName("number")).css("border","1px solid #e0e0e0");
            $(data_list[i].getElementsByClassName("product_title")).append(obj.second_product_name);
            $(data_list[i].getElementsByClassName("product_description")).append(obj.product_description);
            $(data_list[i].getElementsByClassName("excepted_use")).append(obj.expected_use);
            $(data_list[i].getElementsByClassName("product_example")).append(obj.product_example);
            $(data_list[i].getElementsByClassName("number")).append(obj.directory_number+obj.first_product_number+obj.second_product_number);
        }
    }
}
