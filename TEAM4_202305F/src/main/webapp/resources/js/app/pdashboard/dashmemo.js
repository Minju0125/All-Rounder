/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 18.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 18.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 

function memoSelect(){
	
	

	
	$.ajax({
		type:"GET",
		url:"/memo",
		contentType: "application/json",
		dataType: "json",
		data : JSON.stringify(jsondata),
		success:function(data){
			console.log(data);
			
			let memoSelect="";
			
			
			document.getElementById('memoSelect').value = memoSelect;
		},
		error: function (request, status, error) {
            console.log("code: " + request.status)
            console.log("message: " + request.responseText)
            console.log("error: " + error);
        }
		
	});
	
	
}