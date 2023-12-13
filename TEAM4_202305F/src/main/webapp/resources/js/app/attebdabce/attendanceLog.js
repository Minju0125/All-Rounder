/**
 * <pre>
 * 
 * </pre>
 * @author 권도윤
 * @since 2023. 11. 16.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 16.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 

(()=>{
	logListAjax();
	weeklyWork(0);
	progress();
	annualStatus();
	annualList();
})();

function logListAjax(){
	let xhr=new XMLHttpRequest();
    xhr.open("get","/attendance/work",true);
	xhr.onreadystatechange=()=>{
		if(xhr.readyState==4&&xhr.status==200){
				console.log(xhr.responseText);
			logList(JSON.parse(xhr.responseText));
			selectLogList();
		}
    }
    xhr.send();
};

let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
let csrf = $("meta[name='_csrf']").attr("content");

function selectLogList(){
//	console.log("여기");
	let xhr=new XMLHttpRequest();
    xhr.open("get","/attendance/logList",true);
//    xhr.setRequestHeader("Content-Type","application/json");
//    xhr.setRequestHeader("X-CSRF-TOKEN",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
//			console.log(xhr.responseText);
			if(xhr.responseText){
				attendanceLogList(JSON.parse(xhr.responseText));
//				attendanceLogList(xhr.responseText);
			}
		}
	}
	xhr.send();
}

function updLog(button){
	let value=button.value;
	
	let alVO={
		attLog:value
	}
	
	let xhr=new XMLHttpRequest();
    xhr.open("post","/attendance",true);
    xhr.setRequestHeader("Content-Type","application/json");
    xhr.setRequestHeader("X-CSRF-TOKEN",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
        	console.log(value,"로 근태 변경");
			selectLogList();
//			console.log(JSON.parse(xhr.responseText));
//			attendanceLogList(JSON.parse(xhr.responseText));
        }
    }
	xhr.send(JSON.stringify(alVO));
};

let attendanceLog=document.getElementById("attendanceLog");

function attendanceLogList(rslt){
	let list=sStartTime;
	for(let i=0;i<rslt.length;i++){
		list+=`<div>● ${rslt[i].ltime}`;
		if(rslt[i].attLog=='N'){
			list+=` 근무`;
		}
		if(rslt[i].attLog=='O'){
			list+=` 외출`;
		}
		if(rslt[i].attLog=='E'){
			list+=` 외근`;
		}
		if(rslt[i].attLog=='B'){
			list+=` 출장`;
		}
		if(rslt[i].attLog=='M'){
			list+=` 회의`;
		}
	}
	attendanceLog.innerHTML=list;
}

let attendanceStart=document.querySelector("#attendanceStart");

let sStartTime=``;

function logList(rslt){
	let newDiv=document.createElement("div");
	sStartTime+=`● ${rslt.stime} 출근`;
	newDiv.textContent=sStartTime;
	
	// 있던 내용물들 싹 무시하고 제일 처음에 넣기
//	attendanceStart.insertBefore(newDiv,attendanceStart.firstChild);
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
			if(percent>100){
				percent=100;
			}
			progressbar.style.width = percent+'%';
		}
    }
    xhr.send();
}

let week=0;
	
function weeklyWork(a){
	let xhr=new XMLHttpRequest();
    xhr.open("get","/attendance/weekly?week="+a,true);
	xhr.onreadystatechange=()=>{
		if(xhr.readyState==4&&xhr.status==200){
			workTimeTable(JSON.parse(xhr.responseText));
		}
	}
	xhr.send(a);
}

let workTimeTbody=document.getElementById('workTime');

var workTimeTable=function(rslt){
	let list=`<tr>
			<td style="background-image: linear-gradient(to left bottom, transparent calc(50% - 1px), #D9DEE3, transparent calc(50% + 1px)); background-size: 120% 120%; background-position: center;"></td>
			<td>월</td>
			<td>화</td>
			<td>수</td>
			<td>목</td>
			<td>금</td>
		</tr>
		<tr>
			<td>출근</td>`;
	for(let i=0;i<5;i++){
	console.log(rslt[i]);
		if(rslt[i]!==undefined){
			if(rslt[i].attDate!==null){
				let stime=rslt[i].startTime.substring(0,2)+":"+rslt[i].startTime.substring(2,4);
				list +=`<td>${stime}</td>`;
			}else{
				list +=`<td> </td>`;
			}
		}else{
			list +=`<td> </td>`;
		}
	}
	list+=`</tr><tr><td>퇴근</td>`;
	for(let i=0;i<5;i++){
		if(rslt[i]!==undefined){
			if(rslt[i].attDate!==null){
				if(rslt[i].endTime!==null){
					let eTime=rslt[i].endTime.substring(0,2)+":"+rslt[i].endTime.substring(2,4);
					list+=`<td>${eTime}</td>`;
				}else{
					list +=`<td> </td>`;
				}
			}else{
				list +=`<td> </td>`;
			}
		}else{
			list +=`<td> </td>`;
		}
	}
	sumWeekAttTime=0;
	list+=`</tr><tr><td>근무시간</td>`;
	for(let i=0;i<5;i++){
		if(rslt[i]!==undefined){
			if(rslt[i].attDate!==null){
				if(rslt[i].endTime!==null){
					let workTime=parseInt(rslt[i].endTime)-parseInt(rslt[i].startTime);
					if(parseInt(rslt[i].startTime)<120000){
						workTime=workTime-10000;
					}
		//			console.log(workTime);
					if(rslt[i].endTime<rslt[i].startTime){
						workTime=workTime+240000;
					}
					sumWeekAttTime+=workTime;
					workTime+="";
		//			console.log(workTime.length);
					realTime=workTime.substring(0,2)+"시간";
					if(workTime.length==5){
						realTime=workTime.substring(0,1)+"시간";
					}
					list+= `<td>${realTime}</td>`;
				}else{
					list +=`<td> </td>`;
				}
			}else{
				list +=`<td> </td>`;
			}
		}else{
			list +=`<td> </td>`;
		}
	}
	list+=`</tr>`;
	workTimeTbody.innerHTML=list;
	
	startDate=rslt[0].startOfWeek.substring(0,4)+'.'+rslt[0].startOfWeek.substring(4,6)+'.'+rslt[0].startOfWeek.substring(6);
	endDate=rslt[0].endOfWeek.substring(0,4)+'.'+rslt[0].endOfWeek.substring(4,6)+'.'+rslt[0].endOfWeek.substring(6);
	
	$('.year-month').text(startDate+' ~ '+endDate);
	
	weekAttTimeList(sumWeekAttTime);
};

let weekAttTime=document.getElementById('weekAttTime');

function weekAttTimeList(time){
	time=''+time;
	sumWeekAttTime=time.substring(0,2);
	if(time.length==5){
		sumWeekAttTime=time.substring(0,1);
	}
	leftOver=40-sumWeekAttTime;
	if(leftOver<0){
		leftOver='충족';
	}
	list=`<tr><td>기준 시간</td><td>40</td></tr>`;
	list+=`<tr><td>주 근무시간</td><td>${sumWeekAttTime}</td></tr>`;
	list+=`<tr><td>잔여 근무시간</td><td>${leftOver}</td></tr>`;
	
	weekAttTime.innerHTML=list;
}

let allAnnual=0;
let canAnnual=0;
let useAnnual=0;
let awardAnnual=0;

function annualStatus(){
	let xhr=new XMLHttpRequest();
	xhr.open("get","/attendance/annual",true);
	xhr.onreadystatechange=()=>{
		if(xhr.readyState==4&xhr.status==200){
			let annual=JSON.parse(xhr.responseText);
			allAnnual=annual[0];
			awardAnnual=annual[1];
			useAnnual=annual[2];
			annualStatusBar();
		}
	}
	xhr.send();
}

function annualStatusBar(){
	let as=document.getElementById('annualStatus');
	let list=`
			<tr>
				<td>총 연차 개수</td>
				<td>${allAnnual+awardAnnual}</td>
			</tr>
			<tr>
				<td>사용 연차 개수</td>
				<td>${useAnnual}</td>
			</tr>
			<tr>
				<td>잔여 연차 개수</td>
				<td>${allAnnual+awardAnnual-useAnnual}</td>
			</tr>`;
	as.innerHTML=list;
	

	new Chart(document.getElementById("pie-chart"), {
	    type: 'pie',
	    data: {
	      labels: ["잔여", "사용","포상"],
	      datasets: [{
	        label: "Population (millions)",
	        backgroundColor: ["#3e95cd", "#8e5ea2","#5DF5F0"],
	        data: [allAnnual+awardAnnual-useAnnual,useAnnual,awardAnnual]
	      }]
	    },
	    options: {
	      title: {
	        display: true,
	        text: '연차 사용 현황'
	      }
	    }
	});
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
};

$('.go-prev').on('click', function() {
    week+=1;
    weeklyWork(week);
});

// 다음달로 이동
$('.go-next').on('click', function() {
    week-=1;
    weeklyWork(week);
});




