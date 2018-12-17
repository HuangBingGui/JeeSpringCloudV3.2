//页面操作基础方法代码
$(document).ready(function() {
    orderBy();
    checks();
    message();
    validateForm();
});
//导出excel
$("#btnExport").click(function(){
    top.layer.confirm('确认要导出Excel吗?', {icon: 3, title:'系统提示'}, function(index){
        //do something
        //导出之前备份
        var url =  $("#searchForm").attr("action");
        var pageNo =  $("#pageNo").val();
        var pageSize = $("#pageSize").val();
        //导出excel
        $("#searchForm").attr("action",url);
        $("#pageNo").val(-1);
        $("#pageSize").val(-1);
        $("#searchForm").submit();

        //导出excel之后还原
        $("#searchForm").attr("action",url);
        $("#pageNo").val(pageNo);
        $("#pageSize").val(pageSize);
        //top.layer.close(index);
    });
});
//导入excel
$("#btnImport").click(function(){
    if($(this).data("events")["click"]){return;}
    var url=$(this).attr("url");
    layer.open({
        type: 1,
        area: [500, 300],
        title:"导入数据",
        content:$("#importBox").html(),
        btn: ['下载模板','确定', '关闭'],
        btn1: function(index, layero){
            window.location.href=url+'/template';
        },
        btn2: function(index, layero){
            $("#importBox").remove();
            $("#importForm").submit();
            //top.layer.close(index);
        },

        btn3: function(index){
            //top.layer.close(index);
        }
    });
});
//筛选
$("#btnSearchView").click(function(){
    $("#searchForm").toggle();
    /*if($("#searchForm").is(':visible')){
        $("#btnSearchView").html("<i class=\"fa fa-filter\"></i>隐藏");
    }else{
        $("#btnSearchView").html("<i class=\"fa fa-filter\"></i>查询");
    }*/
});
//统计信息展示隐藏
$("#btnTotalView").click(function(){
    $('#total').toggle();
    //$('.fa-chevron').toggle();
});
//查询
$("#btnSearch").click(function(){
    sortOrRefresh();
});
//重置
$("#btnReset").click(function(){
    return reset();
});
//新增
$("#btnAdd").click(function(){
    return btnAEV(this);
});
//查看
$(".btnView").click(function(){
    return btnAEV(this);
});
//编辑
$(".btnEdit").click(function(){
    return btnAEV(this);
});
//删除
$(".btnDelete").click(function(){
    return confirmx('确认要删除该通知吗？', this.href);
});
//删除全部
$("#btnDeleteAll").click(function(){
    var id=$(this).attr("data-id");
    var url = $(this).attr('data-url');
    if(!id) id="contentTable";
    if(!url) url=$(this).attr("href");
    var str="";
    var ids="";
    $("#"+id+" tbody tr td input.i-checks:checkbox").each(function(){
        if(true == $(this).is(':checked')){
            str+=$(this).attr("id")+",";
        }
    });
    if(str.substr(str.length-1)== ','){
        ids = str.substr(0,str.length-1);
    }
    if(ids == ""){
        warning("请至少选择一条数据!");
        //top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
        return false;
    }
    top.layer.confirm('确认要彻底删除数据吗?', {icon: 3, title:'系统提示'}, function(index){
        window.location = url+"?ids="+ids;
        //top.layer.close(index);
    });
    if($(this).attr("href")) return false;
});
//刷新
$("#btnRefresh").click(function(){
    sortOrRefresh();
});
//保存
$("#btnSubmit").click(function(){
    return formSubmit();
});
//返回
$("#btnBack").click(function(){
    if(jeeSpring.addTab=="true")
        top.closeCurrentTab();
    else
        history.back(-1);
});

var jeeSpring = {
    addTab:"false",
    inputForm:{}
};
if(get("addTab")=="true")
    jeeSpring.addTab= get("addTab");

function get(name) {
    if (typeof (Storage) !== 'undefined') {
        return localStorage.getItem(name)
    } else {
        window.alert('Please use a modern browser to properly view this template!')
    }
}

function store(name, val) {
    if (typeof (Storage) !== 'undefined') {
        localStorage.setItem(name, val)
    } else {
        window.alert('Please use a modern browser to properly view this template!')
    }
}

function btnAEV(object){
    var href = $(object).attr('href');
    var dataAddTab = $(object).attr('data-addTab');//value:true,false
    var dataTitle = $(object).attr('data-title');
    if(!dataTitle) dataTitle = $(object).attr('title');
    if(dataAddTab!="false") dataAddTab=jeeSpring.addTab;
    if(dataAddTab!="true" || !top) return;
    if(typeof(top.addTabs) == "undefined") return;
    top.addTabs({id:dataTitle,title: dataTitle, url: href,open:"new"});
    if(dataAddTab=="true" && href) return false;
    return true;
}

function formSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
    if (jeeSpring.inputForm == 'undefined' || jeeSpring.inputForm == null) {
        return true;
    }
    if(jeeSpring.inputForm.form()){
        $("#inputForm").submit();
        return true;
    }else{
        message();
    }
    return false;
}

function validateForm() {
    if(typeof(validateFormPage) != "undefined"){
        //页面自定义validateForm
        validateFormPage();
        return;
    }
    if($("#inputForm").length==0) return;
    //$("#name").focus();
    jeeSpring.inputForm = $("#inputForm").validate({
        submitHandler: function (form) {
            //loading('正在提交，请稍等...');
            form.submit();
            /*top.closeCurrentTab()
            $.ajax({
                url:$("#inputForm").attr("action"),type:"POST",data:$("#inputForm").serialize(),async:false,
                success:function(data){},
                error:function(data){}
           });*/
        },
        errorContainer: "#messageBox",
        errorPlacement: function (error, element) {
            $("#messageBox").text("输入有误，请先更正。");
            if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                error.appendTo(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        }
    });
}

function checks(){
    $('#contentTable thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定
        $('#contentTable tbody tr td input.i-checks').iCheck('check');
    });

    $('#contentTable thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定
        $('#contentTable tbody tr td input.i-checks').iCheck('uncheck');
    });
}

function orderBy(){
    if($("#orderBy").length==0) return;
    var orderBy = $("#orderBy").val().split(" ");
    $(".sort-column").each(function(){
        $(this).html($(this).html()+" <i class=\"fa fa-sort\"></i>");
    });
    $(".sort-column").each(function(){
        if ($(this).hasClass(orderBy[0])){
            orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
            $(this).find("i").remove();
            $(this).html($(this).html()+" <i class=\"fa fa-sort-"+orderBy[1]+"\"></i>");
        }
    });
    $(".sort-column").click(function(){
        var order = $(this).attr("class").split(" ");
        var sort = $("#orderBy").val().split(" ");
        for(var i=0; i<order.length; i++){
            if (order[i] == "sort-column"){order = order[i+1]; break;}
        }
        if (order == sort[0]){
            sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
            $("#orderBy").val(order+" DESC"!=order+" "+sort?"":order+" "+sort);
        }else{
            $("#orderBy").val(order+" ASC");
        }
        sortOrRefresh();
    });
}

function sortOrRefresh(){//刷新或者排序，页码不清零
    $("#searchForm").submit();
    return false;
}

function reset(){//重置，页码清零
    $("#pageNo").val(0);
    $("#searchForm div.form-group input").val("");
    $("#searchForm div.form-group select").val("");
    //$("#searchForm").submit();
    return false;
}

function page(n,s){//翻页
    $("#pageNo").val(n);
    $("#pageSize").val(s);
    $("#searchForm").submit();
    $("span.page-size").text(s);
    return false;
}

function edit(){
    var id=$(this).attr("id");
    // var url = $(this).attr('data-url');
    var str="";
    var ids="";
    var size = $("#"+id+" tbody tr td input.i-checks:checked").size();
    if(size == 0 ){
        top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
        return;
    }

    if(size > 1 ){
        top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
        return;
    }
    id =  $("#"+id+" tbody tr td input.i-checks:checkbox:checked").attr("id");
    var title=$(this).attr("title");
    var url=$(this).attr("url");
    var width=$(this).attr("width");
    var height=$(this).attr("height");
    var target=$(this).attr("target");
    openDialog("修改"+title,url+"?id="+id,width==null?'800px':width, height==null?'500px':height,target);
}

