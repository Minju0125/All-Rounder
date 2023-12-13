/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 10.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        	수정자       수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 13.    오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 

 		const YrModal = document.querySelector("#yrModal");
        const calendarEl = document.querySelector('#calendar');
        const mySchType = document.querySelector("#schType");
        const mySchStart = document.querySelector("#schStart");
        const mySchEnd = document.querySelector("#schEnd");
        const mySchTitle = document.querySelector("#schTitle");
        const mySchContent = document.querySelector("#schContent");
        const mySchAllday = document.querySelector("#allDay");
        const mySchAlarm = document.querySelector("#schAlarm");
//         const mySchBColor = document.querySelector("#schBColor");
        const mySchFColor = document.querySelector("#schFColor");
        const mySchCode = document.querySelector("#schCode");
        const mySchEmpCd = document.querySelector("#schEmpCd");
        const form = document.querySelector("#addNewAddressForm");
        
        
        const detailTitle = document.querySelector("#detailTitle");
        const detailContent = document.querySelector("#detailContent");
        const detailStart = document.querySelector("#detailStart");
        const detailEnd = document.querySelector("#detailEnd");
        const detailEmp = document.querySelector("#detailEmp");
        const detailAllDay = document.querySelector("#detailAllDay");
        const detailAlarm = document.querySelector("#detailAlarm");
        const detailFColor = document.querySelector("#detailFColor");
        const detailEmpCd = document.querySelector("#detailEmpCd");

        
        //캘린더 헤더 옵션
        const headerToolbar = {
            left: 'prevYear,prev,next,nextYear today',
            center: 'title',
            right: 'dayGridMonth,dayGridWeek,timeGridDay'
        }



      //jquery 소스
      var selectBoxChange = function(value){
	    console.log("값변경테스트: " + value);

		let project = document.getElementById("projectType");
		console.log("$$$",project)
	    if (value === 'P') {
	        console.log("value", value);
			if(project.value == ""){
				setTimeout(() => {	
					project.style.display = 'none';
				}, 50);
				console.log("프로젝트 X")
				console.log(project)
			//	$("#schType").find("option:eq(1)").prop("selected", true);
				$("#schType").val("I").prop("selected", true);
				console.log()
				document.getElementById("projectType").style.display="none";
				swal(
			        'Error',
			        '프로젝트가 없습니다',
			        'error'
			      )
			}else{		
				console.log("프로젝트 O")		
		        project.style.display = 'inline-block';
			}
	    }else{
			project.style.display = 'none';
		}
		
	    $.ajax({
	        method: "GET", // type을 method로 수정
	        url: "/cal/code",
	        contentType: "application/json",
	        dataType: "json", // 서버로부터 기대되는 데이터 형식
	        data: { 
	            schType: value 
	        },
	        success: function(rslt){
	            console.log(rslt.code);
	            $("#schCode").val(rslt.code);

				var firstChar = rslt.code.charAt(0);

			    if (firstChar === 'P') {
			        project.style.display = 'inline-block';
			    } else {
			        project.style.display = 'none';
			    }
	        },
	    });
	};
        
        
//         let events = [
//             {
//             	calCd : "C_231128_001",
//                 title: '테스트',
//                 start: "2023-11-28T09:20",
//                 end: "2023-11-29T09:20",
//                 backgroundColor : "#f40101",
//                 textColor : "black",
//                 type : "C",
//                 allDay : false
//             },
//             {
//             	calCd : "C_231110_001",
//                 title: '가',
//                 start: "2023-11-10T09:20",
//                 end: "2023-11-12T09:20",
//                 backgroundColor : "#f40101",
//                 textColor : "black",
//                 type : "P",
//                 allDay : false
//             },
//             {
//             	calCd : "C_231120_001",
//                 title: 'sk',
//                 start: "2023-11-20T09:20",
//                 end: "2023-11-22T09:20",
//                 backgroundColor : "#f40101",
//                 textColor : "black",
//                 type : "I",
//                 allDay : false
//             }
//         ];


        // 캘린더 생성 옵션(참고)
        const calendarOption = {
            height: '700px', // calendar 높이 설정
            expandRows: true, // 화면에 맞게 높이 재설정
            slotMinTime: '09:00', // Day 캘린더 시작 시간
            slotMaxTime: '18:00', // Day 캘린더 종료 시간
            // 맨 위 헤더 지정
            headerToolbar: headerToolbar,
            initialView: 'dayGridMonth',  // default: dayGridMonth 'dayGridWeek', 'timeGridDay', 'listWeek'
            locale: 'kr',        // 언어 설정
            selectable: true,    // 영역 선택
            selectMirror: true,  // 오직 TimeGrid view에만 적용됨, default false
            navLinks: true,      // 날짜,WeekNumber 클릭 여부, default false
            weekNumbers: true,   // WeekNumber 출력여부, default false
            editable: true,      // event(일정) 
            // 시작일 및 기간 수정가능여부
            eventStartEditable: true,
            eventDurationEditable: true,
            
            dayMaxEventRows: true,  // Row 높이보다 많으면 +숫자 more 링크 보임!
            /*
            views: {
                dayGridMonth: {
                    dayMaxEventRows: 3
                }
            },
            */
            nowIndicator: true,
            events:"/cal/list",
            /*
            eventSources: [
                './commonEvents.json'  // Ajax 요청 URL임에 유의!
//                 './KYREvents.json',
//                 './SYREvents.json'
            	
            ]
            */
        }

        // 캘린더 생성
        const calendar = new FullCalendar.Calendar(calendarEl, calendarOption);

        calendar.render();


        // 캘린더 이벤트 등록
//         calendar.on("eventAdd", info => console.log("Add:", info));
//         calendar.on("eventChange", info => console.log("Change:", info));
//         calendar.on("eventRemove", info => console.log("Remove:", info));
//         calendar.on("eventClick", info => {
//             console.log("eClick:", info);
//             console.log('Event: ', info.event.extendedProps);
//             console.log('Coordinates: ', info.jsEvent);
//             console.log('View: ', info.view);

//             detailOpenModal();
            
//             info.el.style.borderColor = 'purple';
//         });
//         calendar.on("eventMouseEnter", info => console.log("eEnter:", info));
//         calendar.on("eventMouseLeave", info => console.log("eLeave:", info));
//         calendar.on("dateClick", info => console.log("dateClick:", info));
//         calendar.on("select", info => {
//             console.log("체크:", info);

//             mySchStart.value = info.startStr;
//             mySchEnd.value = info.endStr;

//             YrModal.style.display = "block";
//         });

        calendar.on("eventClick", info => {
			
         let scheduleCd = info.event.extendedProps.calCd;
		console.log("결과 : ", scheduleCd);     
		
			$.ajax({
				type:"get",
				url:"/cal/detail",
				contentType: "application/json",
				dataType: "json",
				data:{
					"scheduleCd":scheduleCd
				},
				success:function(rslt){
					
					
					let detail = rslt.detailCal;
					detailTitle.value=detail.scheduleSj;
					detailContent.value=detail.scheduleCn;
					detailStart.value=detail.scheduleBgnDt;
					detailEnd.value=detail.scheduleEndDt;
					detailEmp.value=detail.empCd;
					detailAllDay.value=detail.scheduleDayYn;
					detailAlarm.value=detail.scheduleAlarmYn;
					detailFColor.value=detail.scheduleFcolor;
					detailEmpCd.value=scheduleCd;
					
					console.log("++++++++++++++++++++++++++++++",detailStart.value)
					
					
					console.log(detail)
					console.log(detailTitle.value)
					console.log(detailContent.value)
					console.log(detailStart.value)
					console.log(detailEnd.value)
					console.log(detailEmp.value)
					console.log(detailAllDay.value)
					console.log(detailAlarm.value)
					console.log(detailFColor.value)
					console.log(detailEmpCd.value)
					
	/*				if(detailAllDay.value == 'Y'){
						document.getElementById("detailAllDay").setAttribute("checked",true);						
					}else{
						document.getElementById("detailAllDay").setAttribute("checked",false);						
					}*/										
					
					detailOpenModal();

				},
            	error: function (request, status, error) {
                    console.log("code: " + request.status)
                    console.log("message: " + request.responseText)
                    console.log("error: " + error);
                }
			})

        });

		calendar.on('eventDrop', function (info) {
			let event = info.event;
			console.log("!!!!", event);
						
			
		    let scheduleBgnDt = event.startStr;
		    let scheduleEndDt = event.endStr;
			let BgnDt ="";
			let EndDt ="";						
			
			
			let dayAll = document.getElementById("detailAllDay").value;
			if(dayAll == 'Y'){
				console.log(dayAll);
				console.log("$$$$$$$$$$$$$$$$$$");
				BgnDt = scheduleBgnDt+"T08:00";
				EndDt = scheduleBgnDt+"T22:00";
			}else{
				console.log(dayAll);
				console.log("^^^^^^^^^^^^^^^^^^^");
				BgnDt = (scheduleBgnDt+"").substr(0, 16);
				EndDt = (scheduleEndDt+"").substr(0, 16);	
			}
			
									
		    let updatedEvent = {
		        scheduleCd: event.extendedProps.calCd,
		        scheduleBgnDt: BgnDt,
		        scheduleEndDt: EndDt
		    };
		
		    console.log("%%%%", updatedEvent);
			
			
        	$.ajax({
        		url:"/cal/drag",
        		type:"PUT",
        		contentType: "application/json;charset=UTF-8",
        		data : JSON.stringify(updatedEvent),
        		dataType : 'text',
        		success : function(res) {
					console.log(res)
					calendar.refetchEvents();
        		},
        		error: function (request, status, error) {
                    console.log("code: " + request.status)
                    console.log("message: " + request.responseText)
                    console.log("error: " + error);
                }
        	})
			
			 calendar.refetchEvents();
		});
        

        // 일정(이벤트) 추가
        function fCalAdd() {
            if (!mySchType.value) {
                alert("범위를 선택하세요")
                mySchType.focus();
                return;
            }
            if (!mySchTitle.value) {
                alert("제목을 입력하세요")
                mySchTitle.focus();
                return;
            }
			if( !mySchStart.value){
				alert("시작일을 선택하세요")
				return;
			}
//             let bColor = mySchBColor.value;
            let fColor = mySchFColor.value;
//             if (fColor == bColor) {
//                 bColor = "black";
//                 fColor = "white";
//             }
	

			let schType= document.getElementById("schType").value;
			let scheduleSharer= document.getElementById("projectType").value;
			let schDeptName = document.getElementById("schDeptName").value;
			
			
			let start = mySchStart.value;
			let end = mySchEnd.value;
			
			console.log(end)
			let startDay = "";
			let endDay = "";

			console.log(mySchAllday.checked)
						
			if(mySchAllday.checked == "true"){
				startDay = start.substr(0,11)+"08:00";
				endDay = start.substr(0,11)+"22:00";				
			}else{
				startDay=start;
				endDay=end;
			}
			
				console.log(startDay)
				console.log(endDay)
			
			console.log("+++++++++++++++++++++++++++++++++++")

			
			let sharer = "";
			if(schType == 'C'){
				sharer = "COMMON";
			}else if(schType == "P"){
				sharer = scheduleSharer;
			}else if(schType == "I"){
				sharer = mySchEmpCd.value;
			}else if(schType == "D"){
				sharer = schDeptName;
			}

            let event = {
                "searchCd": mySchType.value,
                "scheduleBgnDt": mySchStart.value,
                "scheduleEndDt": mySchEnd.value,
                "scheduleCn": mySchContent.value,
                "scheduleSj": mySchTitle.value,
                "scheduleDayYn": mySchAllday.checked?'Y':'N',
                "empCd": mySchEmpCd.value,
//                 backgroundColor: bColor,
                "scheduleFcolor": fColor,
                "scheduleCd":mySchCode.value,
				"scheduleSharer":sharer
            };
            console.log(event);
            
            $.ajax({
            	type:"post",
            	url:"/cal",
            	data:JSON.stringify(event),
            	contentType: "application/json;charset=UTF-8",
            	dataType:"json",
            	success:function(rslt){
//             		calendar.addEvent(event);
//             		calendarOption();
            		calendar.refetchEvents();
					
					// 입력 필드 초기화
				    mySchType.value = "";
				    mySchStart.value = "";
				    mySchEnd.value = "";
				    mySchTitle.value = "";
				    mySchContent.value = "";
				    mySchAllday.checked = false;
//				    mySchAlarm.checked = false;

            		fMClose();
            	},
            	error: function (request, status, error) {
                    console.log("code: " + request.status)
                    console.log("message: " + request.responseText)
                    console.log("error: " + error);
                }
            })
            
            
//             calendar.addEvent(event);
//             fMClose();

        }

        // 모달 열기 함수
        function insertOpenModal() {
            // 모달을 나타나게 설정
            YrModal.style.display = "block";

			// 체크박스 엘리먼트 가져오기
			let allDayCheckbox = document.getElementById("allDay");
			let div = document.getElementById('dateStratEnd');
			
			console.log(div)
			
			// 체크박스에 이벤트 리스너 추가
			allDayCheckbox.addEventListener("change", function() {
			    // 이 함수는 체크박스 상태가 변경될 때 호출됩니다.
			    if (allDayCheckbox.checked) {
			        console.log("선택");
					div.style.display = 'none'						
			    } else {
			        console.log("선택 해제");
					div.style.display = 'block'
			    }
			});
        }
        
        // 모달 열기 함수
        function detailOpenModal() {      
			let day = document.getElementById("detailAllDay");
			console.log(day)
			
            // 모달을 나타나게 설정
            detailModal.style.display = "block";

			let dayAll = document.querySelector(".detailAllDay");
			let div = document.getElementById('detailStratEnd');

			console.log(div)
			// 값이 올바르게 가져와지는지 확인
			console.log(detailAllDay.value);

			// checked 속성을 직접 설정
			dayAll.setAttribute("checked", dayAll.checked);
			
			// detailAllDay.value 값에 따라 checkbox 체크 여부 결정
			if (detailAllDay.value === 'Y') {
				console.log("YYY")
				dayAll.checked = true;
				div.style.display = 'none'
			} else {
				console.log("NNN")
				dayAll.checked = false;
				div.style.display = 'block'
			}
			
			dayAll.addEventListener("change", function() {
			    // 이 함수는 체크박스 상태가 변경될 때 호출됩니다.
			    if (dayAll.checked) {
			        console.log("선택");
					div.style.display = 'none'						
			    } else {
			        console.log("선택 해제");
					div.style.display = 'block'
			    }
			});


		}

        // 모달 닫기 함수
        function fMClose() {
            // 모달을 숨기는 설정
            YrModal.style.display = "none";
            detailModal.style.display = "none";
        }
        
        //이벤트 업데이트!, 이벤트소스 다시 가져와서 다시 그리깅
        function fCalUpdate() {
            let jsondata  = {
            	"scheduleCd":detailEmpCd.value,
     			"scheduleSj" : detailTitle.value,
     			"scheduleCn" : detailContent.value,
     			"scheduleBgnDt" : detailStart.value,
     			"scheduleEndDt" : detailEnd.value,
     			"empCd": mySchEmpCd.value,
     			"scheduleDayYn" : detailAllDay.checked?'Y':'N',
     			"scheduleFcolor" : detailFColor.value
            }
            $.ajax({
        		url : "/cal",
        		type : "put",
        		contentType: "application/json;charset=UTF-8",
        		data : JSON.stringify(jsondata),
        		success : function(res) {
        			console.log(res);
					calendar.removeAllEvents();												  
				    calendar.addEventSource(res);
        			calendar.refetchEvents();
            		fMClose();
        		},
        		error: function (request, status, error) {
                    console.log("code: " + request.status)
                    console.log("message: " + request.responseText)
                    console.log("error: " + error);
                },
        		dataType : 'json'
        	})
        	
            calendar.refetchEvents();
        }
        
        function fCalDelete(){
        	
            Swal.fire({
                title: '정말 삭제 하시겠습니까?',
                text: "다시 되돌릴 수 없습니다.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '승인',
                cancelButtonText: '취소',
                reverseButtons: false, // 버튼 순서 거꾸로
                
              }).then(result => {
                if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
                
	        	$.ajax({
	        		url:"/cal",
	        		type:"DELETE",
	        		contentType: "application/json;charset=UTF-8",
	        		data : detailEmpCd.value,
	        		dataType : 'json',
	        		success : function(res) {
	        			calendar.refetchEvents();
	            		fMClose();
	        		},
	        		error: function (request, status, error) {
	                    console.log("code: " + request.status)
	                    console.log("message: " + request.responseText)
	                    console.log("error: " + error);
	                }
	        	})
                   Swal.fire('삭제되었습니다.', '','success');
                }
             });
        	
        }
        

        	let check = document.querySelectorAll('.searchCd');
        	
        	check.forEach(function (checkbox) {
        	    checkbox.addEventListener('change', function () {					
	
        	        // 체크박스가 변경될 때마다 이벤트 발생
        	        console.log("Checkbox value changed: " + checkbox.value);
	
					let check = document.querySelectorAll('input[name="searchCd"]:checked')
					
					console.log(check)
					var result = "";
					
					for(i=0; i<check.length; i++){
					   result+=(check[i].getAttribute('data-cd'));
					}
					console.log(result)
					
					if(result.includes('P')){
						console.log("!!")
						let zero = document.getElementById("projectType").value;
						console.log(zero)
						if(zero == ""){
							console.log(this.parentNode)
							let tag = this.parentNode.parentNode;
							console.log(tag)
							let checkbox = document.getElementById('type3');

							let selectBar = document.getElementById("projectType");
							selectBar.style.display="none";
							// 체크 해제
							checkbox.checked = false;
						//	tag.classList.remove('checked');
							
							console.log("없음")
							 swal(
						        'Error',
						        '프로젝트가 없습니다',
						        'error'
						      )
							
						}else{
							console.log("있음")
						}
					}
						
					
	
        	        $.ajax({
        	        	url:"/cal/list",
        	        	type:"get",
        	        	contentType: "application/json;charset=UTF-8",
        	        	dataType : 'json',
        	        	data : {
        	        		"searchCd" : result
        	        	},
        	        	success : function(res){
							console.log(res);											
							
						    calendar.removeAllEvents();						
						  
						    calendar.addEventSource(res);											   

        	        	},
        	        	error: function (request, status, error) {
		                    console.log("code: " + request.status)
		                    console.log("message: " + request.responseText)
		                    console.log("error: " + error);
		                }
        	        });
        	    });
        	});
        	

        