<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 27.      작성자명      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<div class="col-md-6 col-lg-4 col-xl-4 mb-4"
	style="width: 100%; overflow: scroll; height: 60%;">
	<div class="card">
		<div class="card-header d-flex justify-content-between">
			<h5 class="card-header" id="attendanceEmpCd"
				data-emp-cd="${emp.empCd}">${emp.deptCd}의 ${emp.empName} 님</h5>
			<div class="card-header"
				style="text-align: center; padding-bottom: 0;">
				<div id="searchUI" class="row g-3 d-flex justify-content-center">
					<div class="col-auto">
						<select id="searchDept" name="searchType" class="form-select">
							<option value="">부서</option>
							<c:forEach items="${dept }" var="d">
								<option value="${d.deptCd }">${d.deptName }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-auto">
						<select id="searchRank" name="searchType" class="form-select">
							<option value="">직급</option>
							<c:forEach items="${rankList }" var="r">
								<option value="${r.COMMON_CODE_CD }">${r.COMMON_CODE_SJ }</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-auto">
						<select id="searchEmp" name="searchType" class="form-select">
							<option value="">사원</option>
						</select>
					</div>
					<div class="col-auto">
						<input type="button" value="검색" id="searchBtn"
							class="btn btn-primary">
					</div>
				</div>
			</div>
			<div class="dropdown">
				<button class="btn btn-sm btn-label-primary dropdown-toggle"
					type="button" id="year" data-bs-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false"></button>
				<div class="dropdown-menu dropdown-menu-end"
					aria-labelledby="salesAnalyticsId" id="yearList" style="">
					<a class="dropdown-item changeYear" href="javascript:void(0);">2023</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2022</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2021</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2020</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2019</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2018</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2017</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2016</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2015</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2014</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2013</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2012</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2011</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2010</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2009</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2008</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2007</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2006</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2005</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2004</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2003</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2002</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2001</a>
					<a class="dropdown-item changeYear" href="javascript:void(0);">2000</a>
				</div>
				<button class="btn btn-sm btn-label-primary dropdown-toggle"
					type="button" id="month" data-bs-toggle="dropdown"
					aria-haspopup="true" aria-expanded="false"></button>
				<div class="dropdown-menu dropdown-menu-end"
					aria-labelledby="salesAnalyticsId" id="monthList" style="">
					<a class="dropdown-item changeMonth" href="javascript:void(0);">01</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">02</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">03</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">04</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">05</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">06</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">07</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">08</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">09</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">10</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">11</a>
					<a class="dropdown-item changeMonth" href="javascript:void(0);">12</a>
				</div>
				<a href="/adminAttendance" class="btn btn-sm btn-label-primary">목록으로</a>
			</div>
		</div>
		<div class="table-responsive text-nowrap">
			<table class="table table-bordered" style="text-align: center;">
				<thead>
					<tr>
						<td>근무날짜</td>
						<td>요일</td>
						<td>근무구분</td>
						<td>기준시업</td>
						<td>기준종업</td>
						<td>근로시간</td>
						<td>시업</td>
						<td>종업</td>
						<td>지각</td>
						<td>조퇴</td>
						<td>외출</td>
						<td>근로시간</td>
					</tr>
				</thead>
				<tbody id="monthAttendanceList">

				</tbody>
			</table>
		</div>
	</div>
</div>
<script>
$(document).ready(function() {
    calendarInit();
});

let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
let csrf = $("meta[name='_csrf']").attr("content");

function calendarInit() {

    // 날짜 정보 가져오기
    var date = new Date(); // 현재 날짜(로컬 기준) 가져오기
    var utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // uct 표준시 도출
    var kstGap = 9 * 60 * 60 * 1000; // 한국 kst 기준시간 더하기
    var today = new Date(utc + kstGap); // 한국 시간으로 date 객체 만들기(오늘)
  
    var thisMonth = new Date(${year}, ${month}-1, today.getDate());
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
        $('#year').text(currentYear);
        $('#month').text(currentMonth + 1);
        
        dataMonth=currentMonth+1;
        if(dataMonth<10){
        	dataMonth='0'+dataMonth;
        }
        
        attendanceDate=currentYear+''+dataMonth;
        
        vacationList(attendanceDate);
        
        let searchBtn=document.getElementById('searchBtn');
        
        searchBtn.addEventListener("click",function(){
        	let empCd=document.getElementById('searchEmp');
        	var selectEmp=empCd.selectedIndex;
        	empName = empCd.options[selectEmp].text;
        	document.getElementById('attendanceEmpCd').setAttribute('data-emp-cd',empCd.value);
        	vacationList(attendanceDate);
        	
            let searchDept= document.getElementById('searchDept');
        	document.getElementById('attendanceEmpCd').textContent=deptName+' 의 '+empName+' 님';
        });

        function vacationList(attendanceDate){
        	let empCd= document.getElementById('attendanceEmpCd').getAttribute('data-emp-cd');
        	let dataMap={
        		"attendanceDate" : attendanceDate+'%',
        		"empCd" : empCd
        	};
        	let xhr=new XMLHttpRequest();
            xhr.open("post","/adminAttendance/vacationList",true);
            xhr.setRequestHeader("Content-Type","application/json");
            xhr.setRequestHeader("${_csrf.headerName}",csrf);
        	xhr.onreadystatechange=()=>{
        		if(xhr.readyState==4&&xhr.status==200){
        			vList=JSON.parse(xhr.responseText);
        			monthAttendance(attendanceDate);
        		}
        	}
        	xhr.send(JSON.stringify(dataMap));
        }
        
        function monthAttendance(attendanceDate){
        	let empCd= document.getElementById('attendanceEmpCd').getAttribute('data-emp-cd');
        	let dataMap={
        		"attendanceDate" : attendanceDate+'%',
        		"empCd" : empCd,
        	};
        	let xhr=new XMLHttpRequest();
            xhr.open("post","/adminAttendance/monthList",true);
            xhr.setRequestHeader("Content-Type","application/json");
            xhr.setRequestHeader("${_csrf.headerName}",csrf);
        	xhr.onreadystatechange=()=>{
        		if(xhr.readyState==4&&xhr.status==200){
        			//console.log(xhr.responseText);
        			rslt=JSON.parse(xhr.responseText);
        			if(rslt.length>2){
	        			document.getElementById('attendanceEmpCd').setAttribute('data-emp-cd',rslt[0].empCd);
        			}
        			let monthAttendanceList=document.getElementById('monthAttendanceList');
        			list=``;
        			y=0;
        			
        			let days = ['일', '월', '화', '수', '목', '금', '토'];
        			
        			for(let i=1;i<=nextDate;i++){
        	        	var realDay = new Date(today.getFullYear(), currentMonth, i);
        	        	let dayOfWeek = days[realDay.getDay()];
        	        	let weekStandard=realDay.getDay();
        	        	//console.log(dayOfWeek);
        	        	//console.log(rslt[y])
        	        	if(dayOfWeek=='일'||dayOfWeek=='토'){
	        	        	if(rslt[y]){
	        				list+=`<tr data-ad="\${rslt[y].attDate}"><td style="color:#F4525F;">\${dataMonth}월\${i}일</td>
	        						<td style="color:#F4525F;">\${dayOfWeek}</td>`;
	        	        	}else{
	            				list+=`<tr><td style="color:#F4525F;">\${dataMonth}월\${i}일</td>
	            						<td style="color:#F4525F;">\${dayOfWeek}</td>`;
	        	        	}
        	        	}else{
        	        		if(rslt[y]){	
    	        				list+=`<tr data-ad="\${rslt[y].attDate}"><td>\${dataMonth}월\${i}일</td>
    	        						<td>\${dayOfWeek}</td>`;
    	        	        	}else{
    	            				list+=`<tr><td>\${dataMonth}월\${i}일</td>
    	            						<td>\${dayOfWeek}</td>`;
    	        	        	}
        	        	}
						vOrder=i;
						vacationStatus=0;
						if(vOrder<10){
							vOrder='0'+vOrder;
						}
						for(let z=0;z<vList.length;z++){
							if(rslt[y]){
								if(vList[z].vflag=='N'){
									if(vList[z].vsday.substring(6,8)<=vOrder && vList[z].veday.substring(6,8) > vOrder){
										list+=`<td>주간</td><td>09:00</td><td>18:00</td><td>8h</td><td class="vac">휴가</td><td></td><td></td><td></td><td></td><td></td></tr><tr>`;
										vacationStatus+=2;
									}
								}
								if(vList[z].vflag=='H'){
									if(vList[z].vsday.substring(6,8)<=vOrder && vList[z].vsday==rslt[y].attDate){
										vacationStatus+=1;
									}
								}
							}
						}
						if(vacationStatus!==2){
			        		if (weekStandard !== 0 && weekStandard !== 6) {
		        				if(rslt[y]){
									list+=`<td>주간</td><td>09:00</td><td>18:00</td><td>8h</td>`;
									startTime='';
									endTime='';
									if(rslt[y].startTime==null && rslt[y].endTime==null){
		        						list+='<td>X</td><td>X</td>';
		        					}else if(rslt[y].startTime==null){
		        						endTime=rslt[y].endTime.substring(0,2)+":"+rslt[y].endTime.substring(2,4);
				        				list+='<td>X</td><td>'+endTime+'</td><td></td>';
		        					}else if(rslt[y].endTime==null){
		        						startTime=rslt[y].startTime.substring(0,2)+":"+rslt[y].startTime.substring(2,4);
				        				list+='<td>'+startTime+'</td><td>X</td>';
		        						if(rslt[y].startTime>90000){
		        							if(vacationStatus==1){
			        							list+=`<td style="color:red;">○</td>`;
		        							}else{
			        							list+=`<td style="color:red;">○</td>`;
		        							}
		        						}else{
		        							list+=`<td></td>`;
		        						}
		        					}else{
				        				startTime=rslt[y].startTime.substring(0,2)+":"+rslt[y].startTime.substring(2,4);
				        				endTime=rslt[y].endTime.substring(0,2)+":"+rslt[y].endTime.substring(2,4);
				        				list+='<td>'+startTime+'</td><td>'+endTime+'</td>';
		        						if(rslt[y].startTime>90000){
		        							if(vacationStatus==1){
			        							list+=`<td style="color:#FF8A8A;">반차</td>`;
		        							}else{
			        							list+=`<td style="color:#6DFCF0;">○</td>`;
		        							}
		        						}else{
		        							list+=`<td></td>`;
		        						}
		        					}
									list+=`<td></td><td></td>`;
									if(rslt[y].startTime!==null && rslt[y].endTime!==null){
										let workingHours=rslt[y].endTime-rslt[y].startTime-10000;
										if(workingHours<1){
											workingHours+=240000;
										}
										if((workingHours/100000)<1){
											workingHours='0'+workingHours;
										}
										workingHours+='';
										endTime=workingHours.substring(0,2)*1;
										//console.log("workingHours",workingHours);
										if(workingHours.substring(2,4)>=60){
											endTime+=1;
										}
										endTime+='h';
										//console.log("endTime",endTime);
										list+=`<td>\${endTime}</td>`;
									}
			           				y+=1;
		        				}else{
		        					list+=`<td></td><td></td><td></td><td></td><td></td><td></td>
		        							<td></td><td></td><td></td><td></td>`;
		        				}
			        		}else{
	        					list+=`<td></td><td></td><td></td><td></td><td></td><td></td>
	    								<td></td><td></td><td></td><td></td>`;
	    					}
	            			list+=`</tr>`;
						}
        			}
        			monthAttendanceList.innerHTML=list;
        			
        			let vac=document.querySelectorAll('.vac');
        			for(let i=0;i<vac.length;i++){
            			let vacParent = vac[i].parentNode;
            			vacParent.style.backgroundColor = 'yellow';
        			}
        			grantClass(monthAttendanceList);
        		}
        	}
        	xhr.send(JSON.stringify(dataMap));
        }

        function grantClass(tbody){
        	var rows = tbody.getElementsByTagName('tr');

        	for (var i = 0; i < rows.length; i++) {
        	    var cells = rows[i].getElementsByTagName('td');
        	    if (cells.length > 5) {
        		    cells[6].classList.add('sTime');
        		    cells[7].classList.add('eTime');
        		    
        		    cells[6].addEventListener("dblclick",function(){
        				let sTime=this.textContent;
        				//console.log(sTime);
        				inputAttTime=document.querySelector('.inputElement');
        				if(inputAttTime ==null){
        					if(sTime.length>0 && sTime!=='휴가'){
        						var inputElement = document.createElement('input');
        		                inputElement.type = 'text';
        		                inputElement.value=sTime;
        		                inputElement.style.width="50px";
        		                inputElement.classList.add('inputElement');
        		                inputElement.setAttribute('tabindex', '1');
        		                this.innerHTML='';
        		                this.appendChild(inputElement);
        		                inputElement.focus();
        		                timeType='s';
        		                let tr=this.parentNode;
        		                attDate=tr.getAttribute('data-ad');
        		                inputElement.addEventListener('keydown', handleKeyEvents);
        					}
        				}else{
        					alert("수정을 마무리 후 가능합니다.");
        				}
        			});
        		    
        			cells[7].addEventListener("dblclick",function(){
        				let eTime=this.textContent;
        				//console.log(eTime);
        				inputAttTime=document.querySelector('.inputElement');
        				if(inputAttTime ==null){
        					if(eTime.length>0 && eTime!=='휴가'){
        						var inputElement = document.createElement('input');
        		                inputElement.type = 'text';
        		                inputElement.value=eTime;
        		                inputElement.style.width="50px";
        		                inputElement.classList.add('inputElement');
        		                inputElement.setAttribute('tabindex', '1');
        		                this.innerHTML='';
        		                this.appendChild(inputElement);
        		                inputElement.focus();
        		                timeType='e';
        		                let tr=this.parentNode;
        		                attDate=tr.getAttribute('data-ad');
        		                inputElement.addEventListener('keydown', handleKeyEvents);
        					}
        				}else{
        					alert("수정을 마무리 후 가능합니다.")
        				}
        			});
        	    }
        	}
        }

        function handleKeyEvents(e){
        	if(event.keyCode==13){
        		inputAttTime=document.querySelector('.inputElement');
        		time=inputAttTime.value.replace(':', '')+'00';
        		//console.log("time : ",time*1);
        		//console.log("typeof time : ",typeof time*1 == 'number');
        		//console.log("time : ",Number.isNaN(Number(time)));
        		//timeCheck=time*1;
        		//console.log("timeCheck : ",Number.isNaN(Number(timeCheck)));
        		if(!Number.isNaN(Number(time)) && time.substring(0,2)<25 && time.substring(2,4)<60){
	        		let empCd=document.getElementById('attendanceEmpCd').getAttribute('data-emp-cd');
	        		data={
	        			"type":timeType,
	        			"time":time,
	        			"empCd":empCd,
	        			"attDate":attDate
	        		}
	        		//console.log(data);
	        		let xhr=new XMLHttpRequest();
	        	    xhr.open("post","/adminAttendance/timeUpdate",true);
	        	    xhr.setRequestHeader("Content-Type","application/json");
	        	    xhr.setRequestHeader("${_csrf.headerName}",csrf);
	        	    xhr.onreadystatechange=()=>{
	        	        if(xhr.readyState==4 && xhr.status==200){
	                		monthAttendance(attendanceDate);
	        	        }
	        	    }
	        	    xhr.send(JSON.stringify(data));
        		}else{
        			alert("올바르지 않은 입력입니다.");
        		}
        	}
        	if(event.keyCode==27){
        		monthAttendance(attendanceDate);
        	}
        }
    }

    var changeyears=document.querySelectorAll(".changeYear");

    // 날짜 변경
    for(let i=0; i<changeyears.length; i++){
    	changeyears[i].addEventListener("click", function(event){
        	event.preventDefault();
            year = this.textContent;
            thisMonth = new Date(year, 0, 1);
            //console.log(thisMonth);
            renderCalender(thisMonth);
    	});
    }

    var changeMonths=document.querySelectorAll(".changeMonth");

    // 날짜 변경
    for(let i=0; i<changeMonths.length; i++){
    	changeMonths[i].addEventListener("click", function(event){
        	event.preventDefault();
            month = this.textContent;
            thisMonth = new Date(currentYear, month-1, 1);
            //console.log(currentYear-1);
            //console.log(currentYear+1);
            renderCalender(thisMonth);
    	});
    }

    let searchDept= document.getElementById('searchDept');

    searchDept.addEventListener("change",function(){
    	dept=searchDept.value;
    	data={
    		"deptCd":dept
    	}
    	selectDeptEmp(data);
    });

    let searchRank = document.getElementById('searchRank');

    searchRank.addEventListener("change",function(){
    	dept=searchDept.value;
    	rank=searchRank.value;
    	data={
    		"deptCd":dept,
    		"searchRank":rank
    	}
    	selectDeptEmp(data);
    });

    function selectDeptEmp(data){
    	let xhr=new XMLHttpRequest();
        xhr.open("post","/adminAttendance/deptEmp",true);
        xhr.setRequestHeader("Content-Type","application/json");
        xhr.setRequestHeader("${_csrf.headerName}",csrf);
    	xhr.onreadystatechange=()=>{
    		if(xhr.readyState==4&&xhr.status==200){
    			let rslt=JSON.parse(xhr.responseText);
    			let searchEmp=document.getElementById('searchEmp');
    			list=``;
    			if(rslt?.length >0){
	    			for(let i=0;i<rslt.length;i++){
	    				list+=`<option value="\${rslt[i].empCd}" data-emp-name="\${rslt[i].empName}">\${rslt[i].empName}</option>`;
	    			}
        			deptName=rslt[0].deptCd;
    			}else{
    				list+=`<option>없음</option>`;
    			}
    			//console.log(deptName);
    			searchEmp.innerHTML=list;
    		}
    	}
    	xhr.send(JSON.stringify(data));
    }
}

let deptName='';

let empName='';

let vList='';

let inputAttTime='';

let time='';

let timeType='';

let attDate='';

let searchEmp=document.getElementById('searchEmp');

searchEmp.addEventListener("change",function(){
	let empCd=document.getElementById('searchEmp');
	document.getElementById('attendanceEmpCd').setAttribute('data-emp-cd',empCd.value);
})
</script>