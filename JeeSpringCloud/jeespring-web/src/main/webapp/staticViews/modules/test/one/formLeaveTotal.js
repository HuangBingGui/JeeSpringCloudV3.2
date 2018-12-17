$(document).ready(function() {
	var name = setInterval(
		function(){
			if($("#run").attr('checked')){
				location.reload();
			}
		}   ,
		//每20秒执行一次
		20000
	);

});

function sumColumnIndex(id,index){
	var sum=0;
	$("table tbody tr").each(function(){
		sum=sum+Number($(this).find("td:eq("+(index-1)+")").html());
	});
	$("#"+id).html(sum);
}

function sumColumn(id,columnName){
	if(columnName==null)
		columnName=id;
	var sum=0;
	$("."+columnName).each(function(){
		sum=sum+Number($(this).html());
	});
	$("#"+id).html(sum);
}