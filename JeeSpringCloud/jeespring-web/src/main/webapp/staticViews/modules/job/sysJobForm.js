var validateForm;
function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
  if(validateForm.form()){
	  $("#inputForm").submit();
	  return true;
  }
  return false;
}

$(document).ready(function() {
	validateForm = $("#inputForm").validate({
		submitHandler: function(form){
			loading('正在提交，请稍等...');
			form.submit();
			/*top.closeSelectTabs()*/
			 /*$.ajax({
				 url:$("#inputForm").attr("action"),type:"POST",data:$("#inputForm").serialize(),async:false,
				 success:function(data){},
				 error:function(data){}
			});*/
		},
		errorContainer: "#messageBox",
		errorPlacement: function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});

	});

