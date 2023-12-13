/**
 * <pre>
 * 
 * </pre>
 * @author 전수진
 * @since 2023. 11. 20.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 20.  전수진       최초작성(jsp에서 js파일 분리)
 * 2023. 11. 21.  전수진       첨부파일입력데이터 폼연결
 * 2023. 12. 04.  전수진       즐겨찾기 삭제구현
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 


// 수신자 선택을 위한 리스트 출력
$(function(){
	$.getJSON("/org/list")
		.done(function(resp) {
			let empList = resp.list;
			console.log("empList================", empList);
            let tags = makeRecpList(resp); 
			console.log(tags);
			$("#sanctnRcyer").append(tags);
		})
		.fail(function(jqxhr, textStatus, error) {
			let err = textStatus + ", " + error;
			console.error("Request Failed: " + err);
		});
		
	// 전 직원목록을 반함
	let makeRecpList = function (resp) {
	   let respList = resp.list;
	   //console.log(deptList);
	   let tags = "";
	   
	   tags += `<option value="-1">수신자 선택</option>`;
	
	   for (let i = 0; i < respList.length; i++) {
	      if (respList[i].empName != "관리자") {
	         tags += `<option data-avatar="5.png" value="${respList[i].empCd}">[${respList[i].dept.deptName}]${respList[i].empName} ${respList[i].common.commonCodeSj}</option>`;
	      }
	      // console.log(respList[i].empName);
	   }
	   return tags;
	}

	var $bookmarkList = $('#bookmarkList');
	
	$bookmarkList.on("click", function(){
		$.ajax({
		    type:"get",
		    url:"/bookmark/list",
		    dataType:"json",
		    success:function(list){
				// JSON.parse(rslt)  jQuery가 몰래해줌
				console.log("서버에서 온 값:================", list);
				console.log("서버에서 온 값:================", list[0]);
				
		      var dropdownMenu = $("#dropdownMenu");
		      
		      dropdownMenu.empty(); // 계속 추가 되는것을 막음
					
	            if (list && list.bookmarkList && list.bookmarkList.length > 0) {
	                // 리스트가 존재하고 비어있지 않은 경우
	                list.bookmarkList.forEach((item) => {
	                    let newItem = `<li><a class="dropdown-item" href="javascript:void(0);" data-bkmk-no="${item.bkmkNo}">${item.bkmkNm}</a>
										<button class="btn btn-icon btn-label-danger removeBtn" data-bkmk-no="${item.bkmkNo}" ><i class='bx bxs-trash' ></i></button>
										</li>`;
	                    dropdownMenu.append(newItem);

		                $(".removeBtn").on("click", function () {
		                    let bkmkNo = $(this).data("bkmkNo");
		                    console.log("삭제 버튼 클릭 - 삭제할 bkmkNo:", bkmkNo);
		                	$.ajax({
								type : "delete", 
								url : `/bookmark/${bkmkNo}`,
								success:function(resp) {
									console.log("서버에서 돌아온값",resp)
									if (resp == "OK") {
										Swal.fire({
								            icon: "success",
								            title: "즐겨찾기 삭제성공!",
								            text: "즐겨찾기 삭제가 성공하였습니다!",
								            showConfirmButton: false,
								            timer: 1500
								        });
																
									} else {
										Swal.fire({
								            icon: "error",
								            title: "즐겨찾기 삭제실패!",
								            text: "즐겨찾기 삭제가 실패되었습니다!",
								            showConfirmButton: false,
								            timer: 1500
								        });
									}
								},
								error:(xhr) => {
									console.log(xhr.status);
								}
							})
						});
	                });
	            } else {
	                // 리스트가 null이거나 비어있는 경우
	                let noBookmark = `<li><a class="dropdown-item">즐겨찾기 없음</a></li>`;
	                dropdownMenu.append(noBookmark);
	            }
		    },
		    error: function (request, status, error) {
		        console.log("code: " + request.status)
		        console.log("message: " + request.responseText)
		        console.log("error: " + error);
		    }
		});
	});
	
});
	

	
	$('#dropdownMenu').on("click", "li a", function() {
		var bkmkNo = $(this).data("bkmkNo");
	    console.log("클릭한 항목============: ", bkmkNo);
	    
		$.ajax({
			type : "get",
			url : "/bookmark/detail",
			data : {
				bkmkNo : bkmkNo
			},
			dataType : "json",
			success : function(list) {
				console.log("확인",list);
				$('#sanctionLine').empty(); // 기존의 값을 지우고 시작(최대 3개만 선택되도록)
				list.detailList.forEach((item) => {
					console.log(item);
					let newItem = `<p><div>${item.bookmark.emp.empName} ${item.bookmark.emp.common.commonCodeSj}<input type="hidden" value="${item.sanctner}"/></div></p>`;
					$("#sanctionLine").append(newItem);
				});
			},
		    error: function (request, status, error) {
		        console.log("code: " + request.status)
		        console.log("message: " + request.responseText)
		        console.log("error: " + error);
		    }
			
		});
	});
		
	let selectedDataArray = [];
	// 결재선의 조직도출력
	function listORG() {
	    $.ajax({
			type: "get",
			url: "/org/do",
			contentType: "application/json",
			dataType: "json",
			success: function (data) {
				let list = data.list;
				let dept = data.dept;
				let drafterDept = $("#drafterDept").val();
	
				console.log("체크1:", list);
				console.log("체크2:", dept);
	
				//일단 억지 데이터 변경 jstree에 맞춰서 쓰일수 있도록
				for (let i = 0; i < dept.length; i++) {
					dept[i].id = dept[i].deptCd;
					dept[i].parent = dept[i].udeptCd ? dept[i].udeptCd : "#";
					dept[i].text = dept[i].deptName;
					
					if(dept[i].text == "대표이사" || dept[i].text == "총괄사업본부" || dept[i].text == drafterDept ){
						dept[i].state = {'opened' : true}
					}else {
						dept[i].state = {'opened' : false}
					}
				}
	
				for(let i=0; i< list.length; i++){
					list[i].id = list[i].empCd;
					list[i].parent = list[i].deptCd;
					list[i].text = `${list[i].empName} ${list[i].common.commonCodeSj}`;
				}
	
				// 배열 합치기!  스프레드 오퍼레이터 ....
				let total = [...list,...dept];
	
				// 'admin' 조직도 제외
				total = total.filter(item => item.text != '관리자 관리자');
	
				console.log("total:",total);
				console.log("체크=================",$("#orgTreeContainer"));
				
				$("#orgTreeContainer").jstree({
					core: {
						data: total,
					}
				});
	
				// Select 했을때 값을 배열에 저장
				$('#orgTreeContainer').on("select_node.jstree", function (e, data) {
					console.log("select했을때", data);
					let drafter = $("#drafter").val();
					var id = data.node.id;
					var text = data.node.text;
					var dept = data.node.original.dept.deptName;
					console.log("select했을때 id", id);
					console.log("select했을때 text", text);
					console.log("select했을때 dept", dept);
						
					if(id == drafter) {
						data.instance.deselect_node(data.node);
						Swal.fire({
							icon: "warning",
							title: "결재자 선택 오류!",
							text: "본인은 선택할수 없습니다!",
							showConfirmButton: false,
							timer: 1500
						});
						$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
			
						return;
					}
					
					if (selectedDataArray.some(item => item.empCd == id)) {
						// 이미 선택된 경우
						data.instance.deselect_node(data.node);
						Swal.fire({
							icon: "warning",
							title: "결재자 선택 오류!",
							text: "이미 선택한 직원입니다!",
							showConfirmButton: false,
							timer: 1500
						});
						$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
						
					} else if(selectedDataArray.length < 3) {    
						// id와 text를 배열에 저장
						let selectedData = {
							empCd: id,
							empName: text,
							deptName: dept
						};
						
						// 선택된 결재자들 출력
						$('#orgTreeResult').append('<div>'+text+'<input type="hidden" value= "'+id+'"/><i class="bx bxs-x-square"></i></div>');
							selectedDataArray.push(selectedData);
							console.log("선택한 데이터:", selectedDataArray);
					} else {
						data.instance.deselect_node(data.node);
						Swal.fire({
							icon: "warning",
							title: "결재자 선택 오류!",
							text: "최대 선택인원을 초과하였습니다!",
							showConfirmButton: false,
							timer: 1500
						});
						$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
						
					}
				});
			},
		    error: function (request, status, error) {
		        console.log("code: " + request.status)
		        console.log("message: " + request.responseText)
		        console.log("error: " + error);
		    }
	    });
	}

	listORG();
	
	$('#orgTreeResult').on('click', '.bx', function() {
	    // 선택된 요소가 속한 div 요소를 찾아서 제거
	    $(this).closest('div').remove();
	
	    // 선택된 데이터 배열에서 해당 아이템 제거
	    var removedEmpCd = $(this).siblings('input').val();
	    selectedDataArray = selectedDataArray.filter(item => item.empCd != removedEmpCd);
	    console.log("선택한 데이터:", selectedDataArray);
	});
	  
	$('#remove').on("click", function(){
		$('#orgTreeResult').empty();
	    selectedDataArray = [];
		console.log("remove버튼 클릭!!!!!!");
	});
		
	$('#addLine').on('hidden.bs.modal', function(e) {
		$('#orgTreeResult').empty();
		selectedDataArray = [];
	});
	
	$('#saveBtn').on("click", function(){
	let bookmarkVal = $("#bookmark").val();
	 // 보내야 되는 값
	let sanctionArray = []; 
	
	$("#orgTreeResult > div input:hidden").each((i,itext)=>{
		console.log(itext.value);
			let boDeVO = {
				sanctner: itext.value
		};
		sanctionArray.push(boDeVO);
	});
	
		console.log("결재라인 체크:",sanctionArray);
	
		// 요즘은 다 덩어리로 보통 1개로 
		if(sanctionArray.length > 0) {
			let BookmarkVO = {};
			BookmarkVO.bkmkNm = bookmarkVal;
			BookmarkVO.detailList = sanctionArray;
		
			if(BookmarkVO.bkmkNm == '') {
				Swal.fire({
					icon: "error",
					title: "즐겨찾기 등록실패!",
					text: "즐겨찾기 이름을 입력해주세요!",
					showConfirmButton: false,
					timer: 1500
				});
				return;
			}
			
			$.ajax({
				type:"post",
				url:"/bookmark/new",
				contentType:"application/json",  // post
				data: JSON.stringify(BookmarkVO) ,
				dataType:"text",
				success:function(rslt){
					// JSON.parse(rslt)  jQuery가 몰래해줌
					console.log("서버에서 온 값:", rslt);
	
					if(rslt == "OK"){
						Swal.fire({
							icon: "success",
							title: "즐겨찾기 등록완료!",
							text: "결재선 즐겨찾기가 등록완료 되었습니다",
							showConfirmButton: false,
							timer: 1500
						});
						//console.log("체킁",$(".swal2-container.swal2-center.swal2-backdrop-show"));
						$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
					}
				},
			    error: function (request, status, error) {
			        console.log("code: " + request.status)
			        console.log("message: " + request.responseText)
			        console.log("error: " + error);
			    }
			});
		} else {
			Swal.fire({
				icon: "warning",
				title: "결재자 선택 오류!",
				text: "결재자를 1명이상 등록해주세요",
				showConfirmButton: false,
				timer: 1500
			});
			$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
		}
	});


	$('#addBtn').on("click", function(){
		$('#sanctionLine').empty(); // 기존의 값을 지우고 시작(최대 3개만 선택되도록)
	
		//console.log("pppp",selectedDataArray);
		
		if(selectedDataArray.length > 0 ){
			for(let i = 0; i < selectedDataArray.length; i++) {
				let selectedId = selectedDataArray[i].empCd;
				let selectedName = selectedDataArray[i].empName;
				console.log("수진selectedId : "+selectedId);
				//console.log("selectedName : "+selectedName);
	
			   $('#sanctionLine').append('<p><div><span>'+selectedName+'</span><input type="hidden" value="'+ selectedId+'"/></div></p>');
			}
		}
		// 모달 닫기
		$('#addLine').modal('hide');
	});
	
	$('#approvalBtn').on("click", function() {

	let formData = new FormData($("#sanctionForm")[0]);
//	let formData = new FormData();
	
    // 개별 값
	let sanctnSj = $("#sanctnSj").val();
    let drafter = $("#drafter").val();
	let sanctnRcyer = $("#sanctnRcyer").val();
	sanctnRcyer = sanctnRcyer == '-1' ? '' : sanctnRcyer;
	
    let sanctnSourc = CKEDITOR.instances.sanctnSourc.getData(); 


	// 보낼 덩어리 값
	let sanctionVO ={
		sanctnSj,
		drafter,
		sanctnRcyer,
		sanctnSourc
	}

	if(sanctnSj == '') {
		Swal.fire({
			icon: "error",
			title: "결재상신 실패!",
			text: "제목을 입력해주세요!",
			showConfirmButton: false,
			timer: 1500
		});
		return;
	}
	

	let lineList = [];

//	console.log("sanctionLine",$("#sanctionLine")[0]);
//	console.log("childRen",$("#sanctionLine").find("input"));

	$("#sanctionLine").find("input").each(function(i,elem){
		console.log("ck",i,elem);
		if(elem.value){
			let lineVO = {
				sanctner: elem.value,
				sanctnOrdr : i + 1
			}
			lineList.push(lineVO);
		}
	});
	
	
	if (lineList.length == 0) {
        Swal.fire({
            icon: "error",
            title: "결재상신 실패!",
            text: "결재선을 선택해주세요!",
            showConfirmButton: false,
            timer: 1500
        });
        return;
    }
	
	
	console.log("ck:", lineList);

	console.log(formData);

	// multi-part/form data는 바이너리 형식으로 전송됨, 그래서 변환필요
	formData.append("lineList",new Blob([JSON.stringify(lineList)],{type:"application/json;charset=utf-8"}));
	formData.append("sanctionVO",new Blob([JSON.stringify(sanctionVO)],{type:"application/json;charset=utf-8"}));
	//formData.append("lineList",JSON.stringify(lineList));
		$.ajax({
		type: "post",
		url: `/sanction/${formNo}/new`,
		data: formData,
		processData: false,  // 필수: FormData를 string으로 변환하지 않음
		contentType: false,   // 필수: 파일 업로드를 위해 false로 설정
		dataType: "json",
		success: function(resp){
			console.log(resp);
			if (resp.msg == "OK") {
				let sanctnNo = resp.sanctnNo;
				Swal.fire({
		            icon: "success",
		            title: "결재상신성공!",
		            text: "결재문서가 등록되었습니다!",
		            showConfirmButton: false,
		            timer: 1500
		        });
				location.href=`/sanction/${sanctnNo}`;
				
			} else { 
				Swal.fire({
		            icon: "error",
		            title: "결재상신 실패!",
		            text: "다시 시도 해주세요!",
		            showConfirmButton: false,
		            timer: 1500
		       	});
			}
		},
		error: (xhr) => {
			console.log(xhr.status);
			
		}
	})
});

	
