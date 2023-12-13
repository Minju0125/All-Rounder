<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 23.      작성자명      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>    
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<div class="card-body" style="background-color:white; overflow:scroll; height:40%;">
	<div class="table-responsive text-nowrap" style="text-align:center;">
		<br>
		<div style="display:flex; justify-content: center; align-items: center; width:100%;">
			<a href="javascript:;" class="nav-btn go-prev" style="margin: auto 5px;">prev</a>
			<h2 class="year-month" style="margin: auto 5px;"></h2>
			<a href="javascript:;" class="nav-btn go-next" style="margin: auto 5px;">next</a>
		</div>
		<br>
		<table class="table table-bordered">
			<thead class="dates">
				
			</thead>
			<tbody id="attendanceListAll">
			
			</tbody>
		</table>
	</div>
</div>
<script>
$(document).ready(function() {
    calendarInit();
});
let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
let csrf = $("meta[name='_csrf']").attr("content");
/*
    달력 렌더링 할 때 필요한 정보 목록 

    현재 월(초기값 : 현재 시간)
    금월 마지막일 날짜와 요일
    전월 마지막일 날짜와 요일
*/

function calendarInit() {

    // 날짜 정보 가져오기
    var date = new Date(); // 현재 날짜(로컬 기준) 가져오기
    var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // uct 표준시 도출
    var kstGap = 9 * 60 * 60 * 1000; // 한국 kst 기준시간 더하기
    var today = new Date(utc + kstGap); // 한국 시간으로 date 객체 만들기(오늘)
  
    var thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    // 달력에서 표기하는 날짜 객체
    
    var currentYear = thisMonth.getFullYear(); // 달력에서 표기하는 연
    var currentMonth = thisMonth.getMonth(); // 달력에서 표기하는 월
    var currentDate = thisMonth.getDate(); // 달력에서 표기하는 일

    // kst 기준 현재시간
    // console.log(thisMonth);

    // 캘린더 렌더링
    renderCalender(thisMonth);

    function renderCalender(thisMonth) {

        // 렌더링을 위한 데이터 정리
        currentYear = thisMonth.getFullYear();
        currentMonth = thisMonth.getMonth();
        currentDate = thisMonth.getDate();

        // 이전 달의 마지막 날 날짜와 요일 구하기
        var startDay = new Date(currentYear, currentMonth, 0);
        var prevDate = startDay.getDate();
        var prevDay = startDay.getDay();

        // 이번 달의 마지막날 날짜와 요일 구하기
        var endDay = new Date(currentYear, currentMonth + 1, 0);
        var nextDate = endDay.getDate();
        var nextDay = endDay.getDay();

        // console.log(prevDate, prevDay, nextDate, nextDay);

        // 현재 월 표기
        $('.year-month').text(currentYear + '년 ' + (currentMonth + 1)+'월 근태');

        // 렌더링 html 요소 생성
        calendar = document.querySelector('.dates');
        calendar.innerHTML = '';
        let head='<tr><th></th>';

        let days = ['일', '월', '화', '수', '목', '금', '토'];
        // 지난달
//        for (var i = prevDate - prevDay + 1; i <= prevDate; i++) {
//            calendar.innerHTML = calendar.innerHTML + '<div class="day prev disable">' + i + '</div>'
//        }
        // 이번달
        for (let i = 1; i <= nextDate; i++) {
        	var realDay = new Date(today.getFullYear(), currentMonth, i);
        	let dayOfWeek = days[realDay.getDay()];
        	if(dayOfWeek=='일'||dayOfWeek=='토'){
            	head+='<th class="day current" style="color:#F4525F;">'+i+'/'+dayOfWeek+'</th>';
        	}else{
            	head+='<th class="day current">'+i+'/'+dayOfWeek+'</th>';
        	}
        }
    	 // 다음달
//        for (var i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay); i++) {
//            calendar.innerHTML = calendar.innerHTML + '<div class="day next disable">' + i + '</div>'
//        }
        head+='<th></th></tr>';
        calendar.innerHTML =head;
        // 오늘 날짜 표기
//        if (today.getMonth() == currentMonth) {
//            todayDate = today.getDate();
//            var currentMonthDate = document.querySelectorAll('.dates .current');
//            currentMonthDate[todayDate -1].classList.add('today');
//        }
        
        attendanceDate=currentYear+''+(currentMonth+1);
        
        let vList='';
        
        vacationList(attendanceDate);

        function vacationList(attendanceDate){
        	let dataMap={
        		"attendanceDate" : attendanceDate+'%'
        	};
        	let xhr=new XMLHttpRequest();
            xhr.open("post","/adminAttendance/vacationList",true);
            xhr.setRequestHeader("Content-Type","application/json");
            xhr.setRequestHeader("${_csrf.headerName}",csrf);
        	xhr.onreadystatechange=()=>{
        		if(xhr.readyState==4&&xhr.status==200){
        			vList=JSON.parse(xhr.responseText);
        			//console.log(vList);
        	        attendanceListAll(attendanceDate);
        		}
        	}
        	xhr.send(JSON.stringify(dataMap));
        }
        
        function attendanceListAll(attendanceDate){
			//console.log("변수 : ",attendanceDate);
            // 렌더링을 위한 데이터 정리
//            currentYear = thisMonth.getFullYear();
//            currentMonth = thisMonth.getMonth();
//            currentDate = thisMonth.getDate();
        	let xhr=new XMLHttpRequest();
            xhr.open("get","/adminAttendance/list?date="+attendanceDate,true);
        	xhr.onreadystatechange=()=>{
        		if(xhr.readyState==4&&xhr.status==200){
                    allList= document.getElementById('attendanceListAll');
                    rslt=JSON.parse(xhr.responseText);
                  /*
					list='<tr><td>'+rslt[0].empName+'<br>('+rslt[0].deptName+')</td>';
//                    console.log(JSON.parse(xhr.responseText)[0]);
        			//		console.log(rslt[0].attDate.substring(6,8));
        			let name=rslt[0].empCd;
        			//console.log("asdasdasd",rslt.length);
        			let weekend=0;
        			for(let i=0;i<nextDate;i++){
        				if(name!==rslt[i].empCd){
        					list='</tr><tr><td>'+rslt[i].empName+'<br>('+rslt[i].deptName+')</td>';
        					//i=0;
        					weekend=0;
        				}
        				let order=i+1+weekend;
        				//console.log(order);
        				if(order<10){
        					order=0+''+order;
        				}
        				console.log(order," == ",rslt[i].attDate.substring(6,8),rslt[i].attDate.substring(6,8)==order);
        				if(rslt[i].attDate.substring(6,8)==order){
        					name=rslt[i].empCd;
        					startTime=rslt[i].startTime.substring(0,2)+":"+rslt[i].startTime.substring(2,4);
        					endTime=rslt[i].endTime.substring(0,2)+":"+rslt[i].endTime.substring(2,4);
        					list+='<td>'+startTime+'<br>'+endTime+'</td>';
        				}else{
        					list+='<td> </td>';
        					weekend+=1;
        				}
        			}
        			
        			list='<tr><td>'+rslt[0].empName+'<br>('+rslt[0].deptName+')</td>';
        			let emp=rslt[0].empCd;
        			let y=0;
        			console.log(rslt.length);
        			for(let i=0;y<rslt.length;i++){
        				console.log(i,' : ',y);
        				if(emp!==rslt[y].empCd){
        					list='</tr><tr><td>'+rslt[y].empName+'<br>('+rslt[y].deptName+')</td>';
        					console.log("이름 바뀜!!");
            				console.log(emp);
            				console.log(rslt[y].empCd);
        				}else{
	        				let order=i+1;
	        				//console.log(order);
	        				if(order<10){
	        					order=0+''+order;
	        				}
	        				if(rslt[y].attDate.substring(6,8)==order){
	        					emp=rslt[y].empCd;
	        					startTime=rslt[y].startTime.substring(0,2)+":"+rslt[y].startTime.substring(2,4);
	        					endTime=rslt[y].endTime.substring(0,2)+":"+rslt[y].endTime.substring(2,4);
	        					list+='<td>'+startTime+'<br>'+endTime+'</td>';
	            				y+=1;
	        				}else{
	        					list+='<td> </td>';
	        				}
        				}
        			} */
        			list='';
        			emp='';
        			if(rslt.length>2){
	        			list='<tr data-emp-cd="'+rslt[0].empCd+'" class="monthAttendance"><td>'+rslt[0].empName+'<br>('+rslt[0].deptName+')</td>';
	        			emp=rslt[0].empCd;
        			}else{
        				list='<tr><td></td>';
        			}
        			let y=0;
        			for(let i=0;i<=nextDate;i++){
        				//console.log(rslt[y]);
						vOrder=i+1;
						vacationStatus=0;
						if(vOrder<10){
							vOrder='0'+vOrder;
						}
						for(let z=0;z<vList.length;z++){
							//console.log(vOrder);
							//console.log(vList[z]);
							if(vList[z].vflag=='N'){
								if(vList[z].empCd==emp && vList[z].vsday.substring(6,8)<=vOrder && vList[z].veday.substring(6,8) > vOrder){
									console.log("쉬는 날!!! : ",rslt[y]);
									list+=`<td style="background-color:yellow;">휴가</td>`;
									vacationStatus=2;
								}
							}
							if(vList[z].vflag=='H'){
								if(rslt[y]){
									if(vList[z].empCd==emp && vList[z].vsday.substring(6,8)<=vOrder && vList[z].vsday==rslt[y].attDate){
										//console.log("11111111반만 쉬는 날!!! : ",vList[z].empCd);
										//console.log("반만 쉬는 날!!! : ",rslt[y]);
										//console.log("반만 쉬는 날!!! : ",emp);
										//list+=`<td>반차<br>`;
										vacationStatus=1;
									}
								}
							}
						}
						//console.log("vacationStatus",vacationStatus);
						if(vacationStatus!==2){
	        				if(rslt[y]){
	            				//console.log(rslt[y].startTime);
	            				//console.log(rslt[y].endTime);
		        				if(emp!==rslt[y].empCd){
		        					/* necessaryTd=nextDate-i+1;
		        					for(let z=0;z<necessaryTd;z++){
		        						list+='<td></td>'
		        					}; */
		        					for(i;i<nextDate;i++){
		        						var realDay = new Date(today.getFullYear(), currentMonth, i+1);
				        				const dayOfWeek = realDay.getDay();
				        				if (dayOfWeek !== 0 && dayOfWeek !== 6) {
			        						list+='<td> </td>';
				        				}else{
				        					//console.log(i,"휴일",dayOfWeek);
				        					list+='<td class="holiday"> </td>';
				        				}
		        					}
		        					list+='</tr><tr data-emp-cd="'+rslt[y].empCd+'" class="monthAttendance"><td>'+rslt[y].empName+'<br>('+rslt[y].deptName+')</td>';
		        					i=-1;
		        					//console.log("이름 바뀜!!");
		        					emp=rslt[y].empCd;
		        				}else{
		    						//console.log("i : ",i);
		    						//console.log("y : ",y);
		    						//console.log("rslt[y].attDate : ",rslt[y].attDate.substring(6,8));
		    						//console.log("vOrder : ",vOrder);
			        				var realDay = new Date(today.getFullYear(), currentMonth, i+1);
			        				const dayOfWeek = realDay.getDay();
			        				if (dayOfWeek !== 0 && dayOfWeek !== 6) {
			        					if(rslt[y].attDate.substring(6,8) == vOrder){
			        						//console.log("rslt[y].attDate : ",rslt[y].attDate.substring(6,8));
			        						//console.log("vOrder : ",vOrder);
				        					//console.log(i,"평일",dayOfWeek);
				        					//console.log("길이 : ",rslt[y]);
				        					//console.log("y");
				        					if(vacationStatus!==1){
					        					if(rslt[y].startTime==null && rslt[y].endTime==null){
					        						list+='<td style="background-color:#6FADCF;">X</td>';
					        					}else if(rslt[y].startTime==null){
					        						endTime=rslt[y].endTime.substring(0,2)+":"+rslt[y].endTime.substring(2,4);
							        				list+='<td style="background-color:#6FADCF;">X<br>'+endTime+'</td>';
					        					}else if(rslt[y].endTime==null){
					        						startTime=rslt[y].startTime.substring(0,2)+":"+rslt[y].startTime.substring(2,4);
							        				list+='<td style="background-color:#6FADCF;">'+startTime+'<br>X</td>';
					        					}else{
							        				startTime=rslt[y].startTime.substring(0,2)+":"+rslt[y].startTime.substring(2,4);
							        				endTime=rslt[y].endTime.substring(0,2)+":"+rslt[y].endTime.substring(2,4);
							        				if(rslt[y].startTime>90000 || (rslt[y].endTime<180000 && rslt[y].endTime>40000)){
								        				list+='<td style="background-color:#6FADCF;">'+startTime+'<br>'+endTime+'</td>';
							        				}else{
								        				list+='<td>'+startTime+'<br>'+endTime+'</td>';
							        				}
							        				//console.log(startTime);
					        					}
				        					}else if(vacationStatus==1){
				        						if(rslt[y].startTime==null && rslt[y].endTime==null){
					        						list+='<td style="background-color:#6DFCF0;">반차</td>';
					        					}else if(rslt[y].startTime==null){
					        						endTime=rslt[y].endTime.substring(0,2)+":"+rslt[y].endTime.substring(2,4);
							        				list+='<td style="background-color:#6DFCF0;">반차<br>'+endTime+'</td>';
					        					}else if(rslt[y].endTime==null){
					        						startTime=rslt[y].startTime.substring(0,2)+":"+rslt[y].startTime.substring(2,4);
							        				list+='<td style="background-color:#6DFCF0;">'+startTime+'<br>반차</td>';
					        					}else{
							        				startTime=rslt[y].startTime.substring(0,2)+":"+rslt[y].startTime.substring(2,4);
							        				endTime=rslt[y].endTime.substring(0,2)+":"+rslt[y].endTime.substring(2,4);
							        				if(rslt[y].startTime>90000 || (rslt[y].endTime<180000 && rslt[y].endTime>40000)){
								        				list+='<td style="background-color:#6DFCF0;">'+startTime+'<br>'+endTime+'</td>';
							        				}else{
								        				list+='<td style="background-color:#6DFCF0;">'+startTime+'<br>'+endTime+'</td>';
							        				}
							        				//console.log(startTime);
					        					}
				        						//console.log(vacationStatus +' : '+rslt[y]);
				        					}
			        					}else{
			        						list+='<td> </td>';
			        					}
				           				y+=1;
			        				}else{
			        					//console.log(i,"휴일",dayOfWeek);
			        					list+='<td class="holiday"> </td>';
			        				}
		        				}
		        				/* if(i+1==nextDate){
		        					list+='<td></td>';
		        				} */
	        				}else{
	        					var realDay = new Date(today.getFullYear(), currentMonth, i+1);
		        				const dayOfWeek = realDay.getDay();
		        				if (dayOfWeek !== 0 && dayOfWeek !== 6) {
	        						list+='<td> </td>';
		        				}else{
		        					//console.log(i,"휴일",dayOfWeek);
		        					list+='<td class="holiday"> </td>';
		        				}
	        				}
        				}
        			}
        			list+='</tr>';
        	//		console.log(list);
        			allList.innerHTML=list;
        			
        			let monthAttendance=document.querySelectorAll('.monthAttendance');
					
        			for(let i=0;i<monthAttendance.length;i++){
            			monthAttendance[i].addEventListener("click",function(){
            				empCd=this.getAttribute("data-emp-cd");
            				console.log(this);
            				window.location.href = '/adminAttendance/month?empCd='+empCd+'&date='+attendanceDate;
            			});
        			}

					let holiday=document.querySelectorAll('.holiday');
					
        			for(let i=0;i<holiday.length;i++){
        				holiday[i].style.background="#CED4DA";
        			}
        		}
        	}
        	xhr.send(attendanceDate);
        }
        
    }

    // 이전달로 이동
    $('.go-prev').on('click', function() {
        thisMonth = new Date(currentYear, currentMonth - 1, 1);
        renderCalender(thisMonth);
    });

    // 다음달로 이동
    $('.go-next').on('click', function() {
        thisMonth = new Date(currentYear, currentMonth + 1, 1);
        renderCalender(thisMonth); 
    });
}

let attendanceDate='';


</script>