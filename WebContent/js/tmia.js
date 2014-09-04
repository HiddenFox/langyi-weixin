$(document).ready(function(){
	
	$("#amount").blur(function(){
		$("#control-group-amount").removeClass("error");
		if(!$(this).val().match(/^[0-9]+(\.[0-9]{0,2})?$/)){
			$("#control-group-amount").addClass("error");
		}
	});
	
	$("#comment").blur(function(){
		$("#control-group-comment").removeClass("error");
		if($(this).val().length > 30){
			$("#control-group-comment").addClass("error");
		}
	});
	
	$("form").submit(function(){
		$("#amount").blur();
		$("#comment").blur();
		if ($("form .error").size() > 0) {
			return false;
		}
	});
	
	/*$(".btn-edit").click(function(){
	
		var modal = $.scojs_modal({
		  keyboard: true
		});
		modal.show();
	});*/
	
	$(".btn-delete").click(function(){
		var btn = $(this);
		var confirm = $.scojs_confirm({
			content: "您确定要删除这条发票信息？",
			action: function(){
				$.ajax({
					url: btn.attr("data-link"),
					success: function(data, textStatus){
						if(data == "true"){
							btn.parents("tr").remove();
						}else{
							alert("内部错误，删除失败。");
						}
					},
					async: false
				});
				this.destroy();
			}
		});
		confirm.show();
	});
	
});