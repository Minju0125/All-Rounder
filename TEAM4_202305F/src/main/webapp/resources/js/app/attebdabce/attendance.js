/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 20.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 20.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 

(()=>{
	currentStatus();
	progress();
	annualStatus();
	annualList();
})();

let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
let csrf = $("meta[name='_csrf']").attr("content");

let commuteBtn=document.getElementById('commute');

function commute(){
	let xhr=new XMLHttpRequest();
    xhr.open("post","/attendance/commute",true);
    xhr.setRequestHeader("Content-Type","application/json");
    xhr.setRequestHeader("X-CSRF-TOKEN",csrf);
	xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
			currentStatus();
		}
	}
	xhr.send();
}

function currentStatus(){
	let xhr=new XMLHttpRequest();
    xhr.open("get","/attendance/work",true);
	xhr.onreadystatechange=()=>{
		if(xhr.readyState==4&&xhr.status==200){
//			console.log("응답 데이따 : ",xhr.responseText);
//			console.log("길이 : ",xhr.responseText.length);
			if(xhr.responseText.length===2 || xhr.responseText.length===0){
				commuteBtn.textContent=``;
				commuteBtn.innerHTML=`<span class="tf-icons bx bx-pie-chart-alt"></span> 출근`;
			}else{
				commuteBtn.textContent=``;
				commuteBtn.innerHTML=`<span class="tf-icons bx bx-pie-chart-alt"></span> 퇴근`;
				progress();
			}
		}
    }
    xhr.send();
}

function progress(){
	let progressbar=document.getElementById('progressbar');
	let xhr=new XMLHttpRequest();
    xhr.open("get","/attendance/week",true);
	xhr.onreadystatechange=()=>{
		if(xhr.readyState==4&&xhr.status==200){
//			console.log("응답 따블 : ",xhr.responseText);
//			console.log("progressbar : ",progressbar);
			let percent=xhr.responseText;
			/*if(percent>100){
				percent=100;
			}*/
			progressbar.style.width = percent+'%';
		}
    }
    xhr.send();
}

function annualStatus(){
	let annualGauge=document.getElementById('annualGauge');
	let xhr=new XMLHttpRequest();
	xhr.open("get","/attendance/annual",true);
	xhr.onreadystatechange=()=>{
		if(xhr.readyState==4&xhr.status==200){
			console.log("연차 : ",xhr.responseText);
			let annual=JSON.parse(xhr.responseText);
			let list=`<p>잔여연차 : ${annual[0]+annual[1]-annual[2]}　 　사용연차 : ${annual[2]}</p>`;
			annualGauge.innerHTML=list;
		}
	}
	xhr.send();
}

function annualList(){
	let xhr=new XMLHttpRequest();
	xhr.open("get","/attendance/annualList",true);
	xhr.onreadystatechange=()=>{
		if(xhr.readyState==4&xhr.status==200){
			makeAnnualList(JSON.parse(xhr.responseText));
		}
	}
	xhr.send();
}

function makeAnnualList(rslt){
	let al=document.getElementById('annualList');
	let list=`<tr>`;
	if(rslt.length==0){
		console.log("길이1",rslt.length);
		list+=`<td colspan='3'>연차자가 없습니다.</td>`;
	}else{
		console.log("길이2",rslt.length);
		for(let i=0;i<rslt.length;i++){
			list+=`<td>${rslt[i].deptName}</td>
					<td>${rslt[i].rankNm}</td>
					<td>${rslt[i].empName}</td>`;
		}
	}
	list+=`</tr>`;
	al.innerHTML=list;
}