/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 30.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 30.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

function freeListSearch(page) {
	console.log(page)
	let data = {
		searchType:"title",
		searchWord: $("#searchFreeText").val(),
		currentPage: page,
	}
	console.log(data)
	$.ajax({
		type: "get",
		url:"/free/list",
		data: data,
		dataType: "json",
		success: function(res) {
			/*console.log("결과만 체킁:",res);
			freeList(res); */
			
			console.log(res)
			let tag = res.pagingHTML;
			let list = res.dataList;
			let page = res.pagingHTML;
			
			let searctText = `
				<input type="text" name="searchWordFree" placeholder="입력하세요" class="form-control" id="searchFreeText" />
				<input type="button" value="검색" id="searchFreeBtn" class="btn btn-primary" />
			`;
			if(list[0] != null){
				
				let freeboardList = "";
				
				list.forEach((item) => {
					console.log("리스트 결과 {} : ", item)
					let file = "D:/team4img/groupware/freeboard/";
					let day = item.bbsRgsde;
					let date = `${day[0]}-${day[1]}-${day[2]}`;
					
					let realContent = item.bbsCn;
					let content = realContent.substr(0,20);
						
					let saveFile = "/freeboardFile/"+ item.boardFileList[0].fileSavename;
					console.log(saveFile);
					let html = `	
					<div class="col-sm-6 col-lg-4">	
						<div class="card p-2 h-100 shadow-none border">
							<div class="rounded-2 text-center mb-3">
							`;
							if(saveFile == '/freeboardFile/null'){
								html +=`
									<a href="javascript:void(0);" onclick="selectFree(${item.bbsNo})"><img class="img-fluid" src="https://png.pngtree.com/png-vector/20190628/ourmid/pngtree-document-icon-for-your-project-png-image_1520484.jpg" alt="tutor image 1"></a>
								`;
							}else{
								html +=`
									<a href="javascript:void(0);" onclick="selectFree(${item.bbsNo})"><img class="img-fluid" src="${saveFile}" alt="tutor image 1"></a>
								`;
							}
							html +=`								
							</div>
							<div class="card-body p-3 pt-2">
								<div class="d-flex justify-content-between align-items-center mb-3 pe-xl-3 pe-xxl-0">
									<h6 class="d-flex align-items-center justify-content-center gap-1 mb-0">
										${item.empName} <span class="text-warning"><i class="bx bxs-star me-1"></i></span><span class="text-muted"> (${item.empCd})</span>
									</h6>
								</div>
								<a href="javascript:void(0);" onclick="selectFree(${item.bbsNo})" data-freeNo="${item.bbsNo}" id="freeBBSNo"class="h5" >${item.bbsSj}</a>
								`;
								if(realContent.length > 20){
									html +=`
										<p class="mt-2">${content} ...</p>
									`;
								}else{
									html +=`
									<p class="mt-2">${realContent}</p>
									`;
									
								}
								html+=`
								<p class="d-flex align-items-center">
									<i class="bx bx-time-five me-2">${date}</i>
									<i class="bx bxs-star me-1">조회수 ${item.bbsRdcnt}</i>
								</p>
							</div>
						</div>	
					</div>			
					`;
					freeboardList += html;		
				});
				freeboardList += `
					<div id="insertFreeBoard">
						<input type="button" value="추가" id="insertFreeBtn" class="btn btn-primary" onclick="insertFreeBtn()"/>
					</div>
				`;
				document.getElementById("freeboardList").innerHTML=freeboardList;
				//document.querySelector(".freePage").innerHTML=tag;	
				document.getElementById("searchFree").innerHTML=searctText;			
				document.getElementById("freePage").innerHTML=page;			
			}else{
				freeboardList += `
					<div><h4>정보가 없습니다</h4></div>
					<div id="insertFreeBoard">
						<input type="button" value="추가" id="insertFreeBtn" class="btn btn-primary" onclick="insertFreeBtn()"/>
					</div>
				`;
				console.log(document.getElementById("freeboardList"))
				document.getElementById("freeboardList").innerHTML=freeboardList;
				document.getElementById("serachFree").style.display="none";
			}

		},
		error: function(xhr) {
			alert("실패")
			console.log(xhr);
		}
	});

}



function freeList(page) {
	$("#freePage").css("display","block");
	$("#searchFree").css("display", "block")
		console.log("!!")
	url='';
		if(page){
			url= "/free/list?page="+page;
		}else{
			url= "/free/list";
		};
	$.ajax({
		type: "get",
		url:url,
		contentType: "application/json; charset=UTF-8",
		dataType: "json",
		success: function(res) {
			console.log(res)
			let tag = res.pagingHTML;
			let list = res.dataList;
			let page = res.pagingHTML;
			
			let searctText = `
				<input type="text" name="searchWordFree" placeholder="입력하세요" class="form-control" id="searchFreeText" />
				<input type="button" value="검색" id="searchFreeBtn" class="btn btn-primary" />
			`;
			if(list[0] != null){
				
				let freeboardList = "";
				
				list.forEach((item) => {
					console.log("리스트 결과 {} : ", item)
					let file = "D:/team4img/groupware/freeboard/";
					let day = item.bbsRgsde;
					let date = `${day[0]}-${day[1]}-${day[2]}`;
					
					let realContent = item.bbsCn;
					let content = realContent.substr(0,20);
						
					let saveFile = "/freeboardFile/"+ item.boardFileList[0].fileSavename;
					console.log(saveFile);
					let html = `	
					<div class="col-sm-6 col-lg-4">	
						<div class="card p-2 h-100 shadow-none border">
							<div class="rounded-2 text-center mb-3">
							`;
							if(saveFile == '/freeboardFile/null'){
								html +=`
									<a href="javascript:void(0);" onclick="selectFree(${item.bbsNo})"><img class="img-fluid" src="https://png.pngtree.com/png-vector/20190628/ourmid/pngtree-document-icon-for-your-project-png-image_1520484.jpg" alt="tutor image 1"></a>
								`;
							}else{
								html +=`
									<a href="javascript:void(0);" onclick="selectFree(${item.bbsNo})"><img class="img-fluid" src="${saveFile}" alt="tutor image 1"></a>
								`;
							}
							html +=`								
							</div>
							<div class="card-body p-3 pt-2">
								<div class="d-flex justify-content-between align-items-center mb-3 pe-xl-3 pe-xxl-0">
									<h6 class="d-flex align-items-center justify-content-center gap-1 mb-0">
										${item.empName} <span class="text-warning"><i class="bx bxs-star me-1"></i></span><span class="text-muted"> (${item.empCd})</span>
									</h6>
								</div>
								<a href="javascript:void(0);" onclick="selectFree(${item.bbsNo})" data-freeNo="${item.bbsNo}" id="freeBBSNo"class="h5" >${item.bbsSj}</a>
								`;
								if(realContent.length > 20){
									html +=`
										<p class="mt-2">${content} ...</p>
									`;
								}else{
									html +=`
									<p class="mt-2">${realContent}</p>
									`;
									
								}
								html+=`
								<p class="d-flex align-items-center">
									<i class="bx bx-time-five me-2">${date}</i>
									<i class="bx bxs-star me-1">조회수 ${item.bbsRdcnt}</i>
								</p>
							</div>
						</div>	
					</div>			
					`;
					freeboardList += html;		
				});
				freeboardList += `
					<div id="insertFreeBoard">
						<input type="button" value="추가" id="insertFreeBtn" class="btn btn-primary" onclick="insertFreeBtn()"/>
					</div>
				`;
				document.getElementById("freeboardList").innerHTML=freeboardList;
				//document.querySelector(".freePage").innerHTML=tag;	
				document.getElementById("searchFree").innerHTML=searctText;			
				document.getElementById("freePage").innerHTML=page;			
			}else{
				freeboardList += `					
					<div id="insertFreeBoard">
						<input type="button" value="추가" id="insertFreeBtn" class="btn btn-primary" onclick="insertFreeBtn()"/>
					</div>
				`;
				document.getElementById("freeboardList").innerHTML=freeboardList;
				document.getElementById("serachFree").style.display="none";
			}

		},
		error: function(xhr) {
			alert("실패")
			console.log(xhr);
		}
	});

}
freeList();



function selectFree(bbsNo){
	$("#freeboardSelect").css("display","block");
	$("#freePage").css("display","none");
	$("#searchFree").css("display","none");
	console.log("1")
	console.log(document.getElementById("freeBBSNo"))
	let url = "/free/"+bbsNo;
	console.log(url)
	let empCd = document.getElementById("empCd");
	let jsondata = {
		"bbsNo":bbsNo
		}
console.log(jsondata)		
	$.ajax({
		type:"post",
		url:url,
		contentType: "application/json; charset=UTF-8",
		data:JSON.stringify(jsondata),
		dataType:"json",
		success:function(res){
			console.log(res)
			document.getElementById("freeboardList").style.display="none";
			document.getElementById("serachFree").style.display="none";
			
			let day = res.bbsRgsde;
			let date = `${day[0]}-${day[1]}-${day[2]}`;	
			
			let saveFile = "/freeboardFile/"+ res.boardFileList[0].fileSavename;
			console.log(saveFile);
							
			let html = `

				    <!-- 수정, 삭제 기능 -->
				    <div id="editDelete">
				        <button id="editBtn" class="btn btn-primary">수정</button>
				        <button id="deleteBtn" class="btn btn-primary">삭제</button>
						<button id="returnList" class="btn btn-primary" onclick="returnList()">목록</button>
				    </div>


				<div class="d-flex justify-content-between align-items-center flex-wrap mb-2 gap-1">
					<div class="me-1" id="freeSelectNo">
						<h5 class="mb-1">${res.bbsSj}</h5>
						<p class="mb-1" data-freeSelectNo="${res.bbsNo}"><span class="fw-medium"></span></p>
					</div>
					<div class="d-flex align-items-center">
						<i class="bx bx-share-alt bx-sm mx-4"></i>
						<i class="bx bx-bookmarks bx-sm"></i>
					</div>
				</div>

				<div class="card academy-content shadow-none border">
					<div class="p-2">
						<div class="cursor-pointer">
							<div class="plyr plyr--full-ui plyr--video plyr--html5 plyr--fullscreen-enabled plyr--paused plyr--stopped plyr--pip-supported plyr__poster-enabled" style="border-radius: 7px;">
								<div class="plyr__video-wrapper">
								`;
								if(saveFile == '/freeboardFile/null'){
									html += `
										<img class="w-100" src="https://png.pngtree.com/png-vector/20190628/ourmid/pngtree-document-icon-for-your-project-png-image_1520484.jpg" alt="tutor image 1">
									`;
								}else{
									html += `
										<img class="w-100" src="${saveFile}" alt="tutor image 1">
									`;
								}
								html +=`
								</div>
							</div>
						</div>
					</div>
					<div class="card-body">
						<h5 class="mb-2">제목</h5>
						<p class="mb-0 pt-1">${res.bbsSj}</p>
				
						<hr class="mb-4 mt-2">
						<h5>내용</h5>
						<p class="mb-4">${res.bbsCn}</p>
						<hr class="my-4">
						<h5>정보</h5>
						<div class="d-flex flex-wrap">
							<div class="me-5">
								<p class="text-nowrap">
									<i class="bx bx-user bx-sm me-2"></i>작성자 ${res.empName}
								</p>
								<p class="text-nowrap">
									<i class="bx bx-pencil bx-sm me-2"></i>작성자사번 ${res.empCd}
								</p>
								<p class="text-nowrap">
									<i class="bx bxs-watch bx-sm me-2"></i>작성날짜 ${date}
								</p>			
							</div>
							<div>
							</div>
						</div>
						<hr class="my-4">
						<h5>댓글</h5>
						<textarea id="answerReply" placeholder="댓글을 입력하세요"></textarea>
						<input type="button" value="추가" id="insertReply" onclick="insertReply()">
			
				`;
				let anser = res.anserList;
				console.log(anser)
				anser.forEach((v)=>{
					console.log(v.answerWrter)
					console.log(v.answerCn)
					console.log(v.answerRgsde)
					console.log(v.answerWrterName)
					console.log(v.answerCode)
					console.log(v.answerUpcode)
					console.log(v)
					let writer = v.answerWrter;
					console.log("----------------")				
					
					if(v.answerCode == null){
						html += ` <hr class="my-4">
							<div id="comments" class="comments">
							댓글이 없습니다.
							</div>
						`;
					}
					
					else if(v.answerUpcode == null ){
						html += `
						<hr class="my-4">
						<div id="comments" class="comments">
						    <small class="text-nowrap" data-replyNo="${v.answerCode}">
								<i class="bx bx-user bx-sm me-2"></i> (작성코드) ${v.answerCode} , (작성자) ${v.answerWrterName}, (작성자사번) ${v.answerWrter}
							</small> 
							<small class="text-muted">(작성날짜) ${v.answerRgsde}
							</small>
							`;
							if(writer == empCd.value){
								html+= `
									<input type="button" value="삭제" onclick="answerDelete(this)" id="answerDelete">
									<input type="button" value="수정" onclick="answerUpdate(this)" id="answerUpdate">
								`;								
							}						
							html +=`
							<br />				
							<div id="textUpdate">		   
						    	<i class="bx bx-file bx-sm me-2"><p id="contentAnswer"> ${v.answerCn} </p> 							
								</i> 
							</div><br /><br />
						    <textarea id="commentInput" placeholder="댓글을 입력하세요"></textarea>
						    <button onclick="postComment(this)" value="${v.answerCode}" id="reAnswer">댓글 작성</button>
						    
						
						

						`;	

			            anser.forEach((reply) => {
							let replyWriter = reply.answerWrter;
							if(reply.answerUpcode != null && v.answerCode == reply.answerUpcode){
								html += `
									<!-- 대댓글 입력 양식 -->
									<ul id="commentList">
										<div class="replyArea">
											<i class="bx bx-check-double bx-sm me-2" ></i>
											<span class="text-muted" data-pCode="${reply.answerCode}">작성자 ${reply.answerWrterName} (${reply.answerWrter}) / 작성일 : ${reply.answerRgsde}
											`;
											if(replyWriter == empCd.value){
												html+=`
													<input type="button" value="삭제" onclick="replyDelete(this)" id="replyDelete">		
													<input type="button" value="수정" onclick="replyUpdate(this)" id="replyUpdate">
													`;											
											}	
											html+=`													
											</span>										
												<li data-answerCode="${reply.answerUpcode}">내용 : ${reply.answerCn}</li>
												<div id="textReplyUpdate">
													
												</div>	
										</div>
									</ul>
								`;
							}
			            });
						
						html += `
					
					</div>
				`;
					}
					
						
				})
				html += `
					
					</div>
				`;

			document.getElementById("freeboardSelect").innerHTML = html;
		},
		error:function(xhr){
			alert("2")
			console.log(xhr)
		}
		
	});
}

function insertReply(){
	console.log("%%%%");
	let empCd = document.getElementById("empCd").value;
	console.log(empCd);
	
	let text = document.getElementById("answerReply");
	console.log(text.value)

	let freeSelect = document.getElementById("freeSelectNo");
	let select = freeSelect.querySelector("p")
	let no = select.getAttribute("data-freeselectno");	
	console.log(no)
	
	let jsondata = {
		"answerCode" : no,
		"answerWrter" : empCd,
		"answerCn" : text.value,
		"bbsNo" : no
	}
	
	$.ajax({
		type:"post",
		url:"/free/answerInsert",
		contentType: "application/json; charset=UTF-8",
		data:JSON.stringify(jsondata),
		dataType:"text",
		success:function(res){
			console.log(res)
			
			selectFree(no);
		},
		error:function(xhr){
			alert("실패")
			console.log(xhr)
		}
	});
}



function submitCommentBtnRe(answerCode){
	document.getElementById("reAnswer").style.display="none";
	document.getElementById("replyForm").style.display="block";
	console.log(answerCode.value)

}

function submitReplyBtn(answerCode){
	let empCd = document.getElementById("empCd").value;
	console.log(empCd);
	console.log(answerCode.value)
	let commentForm = document.getElementById("replyForm");
	var textarea = commentForm.querySelector("textarea")
	var text = textarea.value
	console.log(text)
	let txtBox = document.querySelector(".txt-box");
	var h2Element = txtBox.querySelector("h2");
	var no = h2Element.getAttribute("data-bbscd");
	console.log(no)
	
	
	
	let jsondata = {
		"answerWrter":empCd,
		"answerCn" : text,
		"bbsNo" : no,
		"answerUpcode" : answerCode.value
		
	}
	console.log(jsondata)
	$.ajax({
		type:"post",
		url:"/free/replyInsert",
		contentType: "application/json; charset=UTF-8",
		data:JSON.stringify(jsondata),
		dataType:"json",
		success:function(res){
			console.log(res)
		},
		error:function(xhr){
			alert("실패")
			console.log(xhr)
		}
	});
}

// 댓글 작성 함수
function postComment(data) {
	console.log(data.value)
	console.log(data)
	console.log(this)
	
	let comments = document.getElementById("comments");
	console.log(comments)
	
	 // 해당 버튼의 부모 요소에서 textarea 찾기
    let textarea = data.parentNode.querySelector('textarea');
    
    // textarea의 값을 가져와서 출력 또는 활용
    let textareaValue = textarea.value;
    console.log(textareaValue);

	let empCd = document.getElementById("empCd").value;
	console.log(empCd);
		

	let freeSelect = document.getElementById("freeSelectNo");
	let select = freeSelect.querySelector("p")
	let no = select.getAttribute("data-freeselectno");	
	console.log(no)




	let jsondata = {
		"answerWrter":empCd,
		"answerCn":textareaValue,
		"bbsNo":no,
		"answerUpcode":data.value
	}
	
	console.log(jsondata)
	$.ajax({
		type:"post",
		url:"/free/replyInsert",
		contentType: "application/json; charset=UTF-8",
		data:JSON.stringify(jsondata),
		dataType:"text",
		success:function(res){
			console.log(res)
			selectFree(no);
		},
		error:function(xhr){
			alert("실패")
			console.log(xhr)
		}
	});

}

// 댓글 및 대댓글을 화면에 표시하는 함수
/*function displayComments() {
    const commentList = document.getElementById('commentList');
    commentList.innerHTML = '';

    comments.forEach((comment, index) => {
        const commentItem = document.createElement('li');
        commentItem.textContent = `${index + 1}. ${comment.text}`;

        if (comment.replies.length > 0) {
            const replyList = document.createElement('ul');
            comment.replies.forEach((reply, replyIndex) => {
                const replyItem = document.createElement('li');
                replyItem.textContent = `${index + 1}.${replyIndex + 1}. ${reply}`;
                replyList.appendChild(replyItem);
            });
            commentItem.appendChild(replyList);
        }

        commentList.appendChild(commentItem);
    });
}*/

function answerDelete(data){
	console.log(data)
	console.log(data.parentNode)
	let value = data.parentNode.querySelector('small');
	let answerCode = value.getAttribute("data-replyNo")
	console.log(value)
	console.log(answerCode)
	
	let empCd = document.getElementById("empCd");
	console.log(empCd.value)
	
	let freeSelect = document.getElementById("freeSelectNo");
	let select = freeSelect.querySelector("p")
	let no = select.getAttribute("data-freeselectno");	
	console.log(no)
	
	let jsondata = {
		"answerCode" : answerCode,
		"answerWrter" : empCd.value
	}
	console.log(jsondata)
	
	Swal.fire({
      title: '정말로 삭제 하시겠습니까?',
      text: "다시 되돌릴 수 없습니다.",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: '확인',
      cancelButtonText: '취소',
      reverseButtons: false, // 버튼 순서 거꾸로
      
    }).then((result) => {
      if (result.isConfirmed) {
	
		$.ajax({
		type:"DELETE",
		url:"/free/answerDelete",
		contentType: "application/json; charset=UTF-8",
		data:JSON.stringify(jsondata),
		dataType:"text",
		success:function(res){
			Swal.fire(
	          '삭제이 완료되었습니다.',
	          '화끈하시네요~!',
	          'success'
	        )
				console.log(res)
				selectFree(no);
			},
			error:function(xhr){
				alert("실패")
				console.log(xhr)
			}
		});
 
      }
    })
	
	
}

function answerUpdate(data){
	

	let tag = data.parentNode.querySelector('p');
	console.log(data.parentNode)
	console.log(tag)
	
	let content = tag.innerHTML;
		
	let iTag = data.parentNode.querySelector('i');
	console.log(iTag)
	let html = `
		<textarea id="updateShow" placeholder="${content}"></textarea>
		<input type="button" value="수정" onclick="updateAnswer(this)" id="updateAnswer" />
		<input type="button" value="취소" onclick="updateAnswerCancel(this)" id="updateAnswerCancel" />
	`;
	let textUpdate = data.parentNode.querySelector('div');
	console.log(textUpdate)
	textUpdate.innerHTML = html;
	
	let buttonUpdate = data.parentNode.querySelector('#answerUpdate');
	buttonUpdate.style.display="none";
	let buttonDelete = data.parentNode.querySelector('#answerDelete');
	buttonDelete.style.display="none";
	
}

function updateAnswer(data){
	let text = data.parentNode.querySelector("#updateShow").value;
	console.log(text)
	let empCd = document.getElementById("empCd");
	
	console.log(data.parentNode)
	console.log(data.parentNode.parentNode)
	let parent = data.parentNode.parentNode;
	let tag = parent.querySelector("small");
	console.log(tag.getAttribute("data-replyNo"))
	let answerUpcode = tag.getAttribute("data-replyNo");
	
	let freeSelect = document.getElementById("freeSelectNo");
	let select = freeSelect.querySelector("p")
	let no = select.getAttribute("data-freeselectno");	
	console.log(no)
	
	let jsondata = {
		"answerCn" : text,
		"answerWrter" : empCd.value,
		"answerCode" : answerUpcode
	}
	console.log(jsondata)
	
	$.ajax({
		type:"PUT",
		url:"/free/answerUpdate",
		contentType: "application/json; charset=UTF-8",
		data:JSON.stringify(jsondata),
		dataType:"text",
		success:function(res){
			console.log(res)
			selectFree(no);
		},
		error:function(xhr){
			alert("실패")
			console.log(xhr)
		}
	});
}

function replyUpdate(data){
	console.log(data)
	console.log(data.parentNode)
	let tag = data.parentNode.parentNode;
	console.log(tag)
	console.log(tag.querySelector('li'))
	let text = tag.querySelector('li').innerHTML;
	console.log(text)
	tag.querySelector('li').style.display="none";
	
	
	let divArea = data.parentNode.parentNode.querySelector('div');
	console.log(divArea)
	let show = document.getElementById("textReplyUpdate");
	console.log(show)
	console.log(divArea.style.display="block")
	
	let html = `
		<textarea id="replyShow" placeholder="${text}"></textarea>
		<input type="button" value="수정" onclick="replyAnswer(this)" id="replyAnswer" />
		<input type="button" value="취소" onclick="replyAnswerCancel(this)" id="replyAnswerCancel" />
	`;
	
	divArea.innerHTML = html;
	

	
	let buttonUpdate = data.parentNode.querySelector('#replyUpdate');
	buttonUpdate.style.display="none";
	let buttonDelete = data.parentNode.querySelector('#replyDelete');
	buttonDelete.style.display="none";
}

function replyDelete(data) {
	let tag = data.parentNode;
	console.log(tag)
	let code = tag.getAttribute("data-pCode");
	console.log(code)

	let empCd = document.getElementById("empCd").value;

	let freeSelect = document.getElementById("freeSelectNo");
	let select = freeSelect.querySelector("p")
	let no = select.getAttribute("data-freeselectno");
	console.log(no)

	let jsondata = {
		"answerWrter": empCd,
		"answerCode": code
	}

	Swal.fire({
		title: '정말로 삭제 하시겠습니까?',
		text: "다시 되돌릴 수 없습니다.",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '확인',
		cancelButtonText: '취소',
		reverseButtons: false, // 버튼 순서 거꾸로

	}).then((result) => {
		if (result.isConfirmed) {
 
			$.ajax({
				type: "DELETE",
				url: "/free/answerDelete",
				contentType: "application/json; charset=UTF-8",
				data: JSON.stringify(jsondata),
				dataType: "text",
				success: function(res) {
					Swal.fire(
						'삭제가 완료되었습니다.',
						'화끈하시네요~!',
						'success'
					)
					console.log(res)
					selectFree(no);
				},
				error: function(xhr) {
					alert("실패")
					console.log(xhr)
				}
			})


		}
	});
}

function updateAnswerCancel(data){
	console.log(data.parentNode.parentNode);
	let tag = data.parentNode.parentNode;
	let tagTextarea = tag.querySelector("textarea");
	console.log(tagTextarea)
	let content = tagTextarea.getAttribute("placeholder");
	
	let html = `
			<i class="bx bx-file bx-sm me-2"><p id="contentAnswer">${content} </p>
		`;
	
	console.log(html)
	
	
	let tagId = tag.querySelector("#textUpdate");
	console.log(tagId)
	
	tagId.innerHTML = html;
	
	let answerUpdate = tag.querySelector("#answerUpdate");
	let answerDelete = tag.querySelector("#answerDelete");
	console.log(answerUpdate)
	console.log(answerUpdate)
	answerUpdate.style.display = "block";
	answerDelete.style.display = "block";
	
}

function replyAnswer(data){
	console.log(data)
	console.log(data.parentNode.parentNode);
	let tag = data.parentNode.parentNode;
	let tagId = tag.querySelector("div");
	console.log(tag)
	console.log(tagId)
	
	let liTag = tag.querySelector("li")
	console.log(liTag)
	
	let tagValue = liTag.getAttribute("data-answercode");
	console.log(tagValue)	
	
	let empCd = document.getElementById("empCd").value;
	
	let textarea = tagId.querySelector("textarea").value
	
	let code = tag.querySelector("span")
	let pCode = code.getAttribute("data-pCode")
	console.log(pCode)
	
	let freeSelect = document.getElementById("freeSelectNo");
	let select = freeSelect.querySelector("p")
	let no = select.getAttribute("data-freeselectno");	
	console.log(no)
	
	let jsondata = {
		"answerWrter" : empCd,
		"answerCn" : textarea,
		"answerUpcode" : tagValue,
		"answerCode" : pCode,
	}
	console.log(jsondata)
	
		$.ajax({
		type:"PUT",
		url:"/free/answerUpdate",
		contentType: "application/json; charset=UTF-8",
		data:JSON.stringify(jsondata),
		dataType:"text",
		success:function(res){
			console.log(res)
			selectFree(no);
		},
		error:function(xhr){
			alert("실패")
			console.log(xhr)
		}
	});

}

function replyAnswerCancel(data){
	console.log(data.parentNode.parentNode);
	let tag = data.parentNode.parentNode;
	let tagId = tag.querySelector("div");
	console.log(tag)
	console.log(tagId)
	tagId.style.display="none";
	
	let show = tag.querySelector("li")
	console.log(show)
	show.style.display="block";
	
	let answerUpdate = tag.querySelector("#replyUpdate");
	let answerDelete = tag.querySelector("#replyDelete");
	console.log(answerUpdate)
	console.log(answerUpdate)
	answerUpdate.style.display = "block";
	answerDelete.style.display = "block";
}

function returnList(){
	$("#freeboardList").css("display","flex");
	$("#searchFree").css("display","block");
	console.log("2")
	let freeboardList = document.getElementById("freeboardList");
	let freeboardSelect = document.getElementById("freeboardSelect");
	let replyFree = document.getElementById("replyFree");
	
	console.log(freeboardList)
	console.log(freeboardSelect)
	console.log(replyFree)
	
	freeList();
	
	freeboardSelect.style.display="none";
	//freeboardList.style.display="block";
}

function fileAttachmentSelect(data){
	console.log(data)
	console.log("파일",data.files[0]);
	let file = data.files[0];	
	
	let html = `
		<label for="postContent">글 내용:</label>
		<img src="" alt="user-avatar" class="d-block rounded" height="100"width="100" id="fileFreeUpload">
	`;
	
	let filePlus = document.getElementById("filePlus");
	filePlus.innerHTML=html;
	
	clearImage();

    // 이미지 표시
    displayImage(file);
}

function clearImage() {
    var img = document.getElementById("fileFreeUpload");
    img.src = "";
}

function displayImage(file){

	var fReader = new FileReader();

    fReader.readAsDataURL(file);

    fReader.onloadend = function (event) {
        var img = document.getElementById("fileFreeUpload");
        img.src = event.target.result;

        console.log("path: ", event.target.result);
    };

}

function submitBoard(data){
	let formData = new FormData();
	
	console.log(data)
	let empCd = document.getElementById("empCd").value;
	console.log(empCd)
	
	let all = data.parentNode;
	console.log(all)
	let file = all.querySelector("#fileAttachment").files[0];
	console.log(file)
	let postTitle = all.querySelector("#postTitle");
	console.log(postTitle.value)
	let editor = all.querySelector("#editor");
	console.log(editor.innerHTML)
	
	if(file){
		formData.append("freeboardImgFile",file);
	}
	formData.append("bbsSj", postTitle.value);
	formData.append("bbsCn", editor.innerHTML);
	formData.append("empCd", empCd);
	
	console.log(formData)
	
	$.ajax({
		type:"post",
		url:"/free/freeInsert",
	//	contentType: "application/json; charset=UTF-8",
		contentType:false,
		processData: false,
		data:formData,
	//	dataType:"text",
		success:function(res){
			console.log(res)
			let insertFree = document.getElementById("insertFree");
			insertFree.style.display="none";
			freeList();
			let freeboardList = document.getElementById("freeboardList");
			freeboardList.style.display="flex";
		},
		error:function(xhr){
			alert("실패")
			console.log(xhr)
		}
	});
}

function insertFreeBtn(){
	$("#insertFree").css("display","block");
	$("#freeboardList").css("display","none");
	$("#freePage").css("display","none");
	$("#searchFree").css("display","none");
	let empCd = document.getElementById("empCd").value;
	console.log(empCd)
	
	let html = `
		<div class="container mt-5">
		    <h2>자유 게시판 작성</h2>
		    <form id="boardForm">
		        <div class="form-group">
		            <label for="writerName">작성자사번:</label>
		            <input type="text" class="form-control" id="writerName" name="writerName" required value="${empCd}" disabled>
		        </div> <br />
		        <div class="form-group">
		            <label for="postTitle">글 제목:</label>
		            <input type="text" class="form-control" id="postTitle" name="postTitle" required>
		        </div> <br />
		        <div class="form-group">
		            <label for="fileAttachment">파일 첨부:</label>
		            <input type="file" class="form-control-file" id="fileAttachment" name="fileAttachment"  onchange="fileAttachmentSelect(this)">
		        </div> <br /> <br />
		        <div class="form-group">
					<div id="filePlus">
		            	<label for="postContent">글 내용:</label>
					</div>
					<div id="editor" contenteditable="true" class="form-control">
					</div>
		        </div> <br /> 
		        <!-- 필요에 따라 더 많은 필드를 추가할 수 있습니다 -->
		        <br />
				<button type="button" class="btn btn-primary" onclick="submitBoard(this)">글 작성</button>
		        <button type="button" class="btn btn-primary" onclick="submitBoardCancel()">취소</button>
		    </form>
		</div>
	`;
	
	let insertFreeTag = document.getElementById("insertFree");
	console.log(insertFreeTag)
	insertFreeTag.innerHTML=html;	
}

function submitBoardCancel(){
	$("#insertFree").css("display","none");
	$("#freeboardList").css("display","flex");
	$("#freePage").css("display","block");
	$("#searchFree").css("display","block");
}

var currentPage;

function fn_paging(page) {
	console.log("1")
	console.log(page)
	currentPage = page;
	freeList(page);
//	freeListSearch(page);
}

//검색 버튼 클릭
$(document).on("click", "#searchFreeBtn", function(){
	var searchValue = $('#searchFreeText').val()
	console.log(searchValue)
	//freeList(searchValue);
	freeListSearch(currentPage);
});