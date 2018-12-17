var springvm={};
var springUI=new SpringUI({auto:true});

function SpringUI(optionsobj){
	var options={auto:true};
	options=Object.assign(options,optionsobj);
	this.From=SpringUIForm;
	this.List=SpringUIList;
	if(options.auto && $("#inputForm").length==1){
		this.From(obj);
	}
	if(options.auto && $("#searchForm").length==1){
		this.List(obj);
	}
}

function SpringUIForm(obj){
	var options={key:"id",formId:"#inputForm",formUrl:"action",saveUrl:"",getJsonUrl:""};
	options=Object.assign(options,obj);
	if($("#inputForm").attr("getJsonUrl")){
		options.getJsonUrl=$("#inputForm").attr("getJsonUrl");
	}
	if($("#inputForm").attr("saveUrl")){
		options.saveUrl=$("#inputForm").attr("saveUrl");
	}	
	if(options.saveUrl==""){
		options.saveUrl=$(options.formId).attr(options.formUrl)+"/../savejson";
	}
	if(options.getJsonUrl==""){
		options.getJsonUrl=$(options.formId).attr(options.formUrl)+"/../getjson";
	}	
	if(url(options.key)==null){
		   $("[v-model]").removeAttr("v-model");
	}

	springvm = new Vue({
		el:options.formId,
		data:{formData:options.formData},
		methods: {
			save: function () {
				if(validateForm!=null){
                    if(!validateForm.form()){
                        return false;
                    }
				}
				$.ajax({  
				     url : $(options.formId).attr(options.formUrl),  type : "POST",  
				     data : $(options.formId).serialize(),  
				     async:false,
				     success:function(data) {
				     	if(top.getActiveTab()[0])
                            top.getActiveTab()[0].contentWindow.springvm.search();
					 },
				     error:function(data) {
				     	if(top.getActiveTab()[0])
                            top.getActiveTab()[0].contentWindow.springvm.search();
				     }
				});
				return true;
			}
		}
	});		

	if(url(options.key)!=null && url(options.key).length>0){
		$.ajax({  
		     url : options.getJsonUrl,type:"POST",data:options.key+"="+url(options.key),  async:false,
		     success:function(data) {springvm.formData=data;},
		     error:function(data) {alert("读取失败");}
		});
	}
}

function SpringUIList(obj){
	var options={key:"id",formId:"#searchForm",formUrl:"action",saveUrl:"./savejson",getJsonUrl:"./getjson",};
	options=Object.assign(options,obj);
	var fromUrl=window.location.href.replace("List.html","Form.html");
	springvm= new Vue({
		el:'#rrapp',
		data:{message:{},page:{},result:{},list:{}},
		methods: {
			addForm:function(){
				//openDialog("新增"+'请假单',fromUrl,"800px", "500px","");
				window.location.href=fromUrl;
			},
			editFormSelect:function(){
			    //openDialog("修改"+'请假单',fromUrl+"?id="+getId(),"800px", "500px","");
                window.location.href=fromUrl;
			},view:function(id){
				//openDialogView("查看请假单",fromUrl+"?id="+id,"800px","500px")
                window.location.href=fromUrl;
			},editform:function(id){
				//openDialog("修改请假单",fromUrl+"?id="+id,"800px","500px");
                window.location.href=fromUrl;
			},
			search: function(){
				$.ajax({  
				     url : $("#searchForm").attr("action"),type:"POST",data:$("#searchForm").serialize(),  
				     success : function(data) {
                         if(data.list!=undefined)
                             springvm.page=data.list;
                         else{
                             springvm.page=data.RESULT.list;
                             springvm.result=data.RESULT;
                             springvm.list=data.RESULT.list;
                             springvm.message=data;
                         }
					 },
				     error : function(data) {
                         console.log(data);
					 }
				});
			},
			deletex: function (id) {
			    //if(!confirmx("确认要删除该请假单吗？", window.location.href)) return;
				$.ajax({  
				     url:$("#searchForm").attr("action")+"/../deletejson",type:"POST",  
				     data : "id="+id,  
				     success : function(data) {springvm.search();},
				     error : function(data) {springvm.search();}
				});
			}
		}
	});		

	$.ajax({  
	     url : $("#searchForm").attr("action"),  
	     type : "POST",  
	     data : $("#searchForm").serialize(),  
	     success : function(data){
	     	if(data.list!=undefined)
				springvm.page=data.list;
			else{
                springvm.page=data.RESULT.list;
                springvm.result=data.RESULT;
                springvm.list=data.RESULT.list;
                springvm.message=data;
			}
		 },
	     error : function(data){
             console.log(data);
		 }
	});
}

function getId(){
	 var str="";var ids="";
	  var size = $("#contentTable tbody tr td input.i-checks:checked").size();
	  if(size == 0 ){
			top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
			return;
	  }
	  if(size > 1 ){
			top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
			return;
	   }
	  return $("#contentTable tbody tr td input.i-checks:checkbox:checked").attr("id");
}
