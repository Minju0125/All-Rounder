
/**
 * <pre>
 * 채팅 기능에서 사용하는 자바스크립트
 * </pre>
 * @author 박민주
 * @since 2023. 11. 12.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 18. 박민주		최초작성
 * 2023. 11. 19. 박민주     채팅방 목록 조회
 * 2023. 11. 19. 박민주     채팅방 개설
 * 2023. 11. 19. 박민주     채팅방 삭제
 * 2023. 11. 19. 박민주     채팅방 이름 수정
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
const initSelect2 = () => { //수산자 리스트에 사원의 이미지를 렌더링함 !
    let receiversCd = $('#receiversCd'); //수신자를 선택하는 select 태그
    if (receiversCd.length) {
        function renderContactsAvatar(option) { //수신자 리스트에 사원의 이미지를 렌더링하는 함수
            if (!option.id) {
                return option.text;
            }
            var dataAvatar = $(option.element).data('avatar');
            let $avatar =
                "<div class='d-flex flex-wrap align-items-center'>" +
                "<div class='avatar avatar-xs me-2 w-px-20 h-px-20'>" +
                `<img src="${dataAvatar}" alt='avatar' class='rounded-circle'/>` +
                '</div>' +
                option.text +
                '</div>';
            return $avatar;
        }
        receiversCd.wrap('<div class="position-relative"></div>').select2({ //위의 함수 랩핑, 호출
            placeholder: '선택',
            dropdownParent: receiversCd.parent(),
            closeOnSelect: false,
            templateResult: renderContactsAvatar,
            templateSelection: renderContactsAvatar,
            escapeMarkup: function (es) {
                return es;
            }
        });
    }
}

/*전역변수*/
let $chatRoomCd = ""; //채팅방 번호
let chatType = ""; //클릭한 채팅방 유형

//페이지 로딩 후
document.addEventListener('DOMContentLoaded', function () {
    let empCd = document.querySelector("#empCd").value; //로그인된 유저의 사번
    // 1. 페이지 로딩 직후, 전직원 정보를 직원검색모달에 미리 세팅, 로그인한 사원VO, 소속된 채팅방 정보 가져온다.

    //메세지 전송버튼 클릭 시 전송할 JSON 데이터 (아래에서 더 추가될 예정)
    let sendData = {};
    setChatType('G');

    // 1-1. 로그인한 사원의 기본 정보 가져오는 ajax
    $.ajax({
        type: "GET",
        url: `/org/${empCd}`,
        contentType: "application/json; charset=utf-8",
        success: function (resp) {
            console.log("성공.emp ===> ", resp.emp);
            //가져온 데이터를 JSON 객체(sendData)에 추가
            sendData.senderEmpCd = empCd; /* 로그인된 직원 사번*/
            sendData.senderEmpName = resp.emp.empName; /* 이름 */
            sendData.senderDeptName = resp.emp.dept.deptName; /* 부서이름 */
            sendData.senderCommonCodeSj = resp.emp.common.commonCodeSj; /* 직급 */
            sendData.senderEmpProfileImg = resp.emp.empProfileImg; /* 본인의 프로필 이미지 */
            $("#myProfile").attr("src", sendData.senderEmpProfileImg);
        },
        error: function (xhr) {
            console.log("실패 ===> ", xhr);
        }
    });

    //1-3. 전직원 정보를 검색 모달에 미리 세팅해놓는다.
    $.ajax({
        type: "GET",
        url: "/org/list",
        contentType: "application/json; charset=utf-8",
        success: function (resp) {
            let respList = resp.list;
            //console.log(deptList);
            let tags = "";
            for (let i = 0; i < respList.length; i++) {
                if (respList[i].empName != "관리자" && respList[i].empCd != $("#empCd").val()) {
                    tags += `<option data-avatar="${respList[i].empProfileImg}" value="${respList[i].empCd}">[${respList[i].dept.deptName}]${respList[i].empName} ${respList[i].common.commonCodeSj}</option>`;
                }
            }
            initSelect2();
            $("#receiversCd").append(tags);
        },
        error: function (xhr) {
            console.log("Error: " + xhr);
        }
    })

    //2. 채팅방 삭제
    $(document).on("click", "#chatRoomDeleteBtn", function () {
        let chatRoomCd = $(this).attr("data-value");
        chatRoomDelete(chatRoomCd);
    });

    const chatRoomDelete = (chatRoomCd) => {
        Swal.fire({
            title: '정말 삭제하시겠습니까?',
            icon: 'warning',
            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
            confirmButtonColor: '#696cff', // confrim 버튼 색깔 지정
            cancelButtonColor: '#939393', // cancel 버튼 색깔 지정
            confirmButtonText: '삭제', // confirm 버튼 텍스트 지정
            cancelButtonText: '취소', // cancel 버튼 텍스트 지정
        }).then(result => {
            // 만약 Promise리턴을 받으면,
            if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
                $.ajax({
                    type: "DELETE",
                    url: `/messenger/${chatRoomCd}`,
                    contentType: "application/json; charset=utf-8",
                    success: function (resp) { //삭제
                        //브라우저 화면에서 삭제 (chatRoomCd 와 같은 value 를 가지고 있는 div를 remove)
                        Swal.fire('삭제되었습니다.', '', 'success');
                        $("#chat-list").empty();
                        getChatList();
                    },
                    error: function (xhr) {
                        console.log("삭제 실패 ====> ", xhr);
                    }
                })
            }
        });
    }

    // //3-1. 신규채팅방 개설을 위한 모달(1) 열기
    // $(document).on("click", "#addEmpBtn", function () {
    //     //모달 열기
    //     $("#smallModal").removeClass("modal fade");
    //     $("#smallModal").addClass("modal fade show");
    //     $("#smallModal").attr("aria-modal", "true");
    //     $("#smallModal").attr("role", "dialog");
    //     $("#smallModal").css("display", "block");
    //     $(".select2-selection__rendered").css("border-bottom", "1px solid #696cff");
    //     $(".modal-footer").css("justify-content", "center");
    // })

     //3-1. 신규채팅방 개설을 위한 모달(1) 열기
    $("#addEmpBtn").on("click", function () {
        //모달 열기
        $("#smallModal").removeClass("modal fade");
        $("#smallModal").addClass("modal fade show");
        $("#smallModal").attr("aria-modal", "true");
        $("#smallModal").attr("role", "dialog");
        $("#smallModal").css("display", "block");
        $(".select2-selection__rendered").css("border-bottom", "1px solid #696cff");
        $(".modal-footer").css("justify-content", "center");
    })
    //3-2. 신규채팅방 개설을 위한 모달(1) 닫기
    $(document).on("click", "#addCloseBtn", function () {
        //모달 닫기
        $("#smallModal").removeClass("modal fade show");
        $("#smallModal").addClass("modal fade");
        $("#smallModal").attr("aria-modal", "false");
        $("#smallModal").removeAttr("role");
        $("#smallModal").css("display", "none");
    })

    //3-2. 신규채팅방 개설
    $("#makeChatBtn").on("click", function () {
        let selectedUsers = $("#receiversCd").val();
        $.ajax({
            type: "POST",
            url: `/messenger/${chatType}`,
            data: JSON.stringify(selectedUsers),
            dataType: "json",
            contentType: "application/json",
            success: function (resp) {
                console.log("성공 ===> ", resp);
                //성공하면, alert 개설되었습니다.
                Swal.fire('채팅방이 개설되었습니다.', "", 'success')
                    .then(function () {
                        $("#chat-list").empty();
                        getChatList();
                    })
            },
            error: function (xhr) {
                console.log("실패 ===> ", xhr);
            }
        });
    });

    //4. 채팅 내용 불러오기
    let chatRoomImg = "";
    $(document).on("click", ".chat-contact-list-item", function (e) {
        $('.chat-contact-list').find('.chat-contact-name').css('color', 'grey');
        $('.chat-contact-list').find('i').css('color', 'grey');
        $(".chat-contact-list-item.active").removeClass("active");
        $(this).removeClass("unclicked-chatroom");
        $chatRoomCd = $(this).data("value");
        //console.log("$chatRoomCd ==? " + $chatRoomCd);
        generateRightSideTitle($chatRoomCd);

        //------------------------------------------------//------------------------------------------------//------------------------------------------------

        $("#historyUl").empty(); //메시지 이력 영역 비우기

        let roomTitle = $(this).find(".chat-contact-name").text();
        //$("#chatRoomTitle1").text(roomTitle);

        $("#chatRoomTitle1").text(roomTitle); // 나중에는 여기에 ajax 로 해당방번호에 해당하는 room 이름 가져와서 컬럼에 값이 존재한다면 출력해주고, 존재하지 않는다면 !!!!! 지금 상태 저대로 띄워줌 
        $("#chatRoomTitle2").text(roomTitle);

        let chatHistory = JSON.parse(localStorage.getItem($chatRoomCd));
        //console.log("chatHis" + JSON.stringify(chatHistory));

        let liTags = "";
        if (chatHistory) { //채팅이력이 존재한다면
            for (var i = 0; i < chatHistory.length; i++) {
                var startLiTags = "";
                let cssStyle = "";
                if (chatHistory[i].senderEmpCd == empCd) { //본인이면 오른쪽에 출력
                    startLiTags = '<li class="chat-message chat-message-right">';
                    cssStyle = "style='color:white;'";
                } else { //아니면 왼쪽에 출력
                    startLiTags = '<li class="chat-message">';
                    cssStyle = "style='color:grey;'";
                }
                liTags += `
                ${startLiTags}
                <div class="d-flex overflow-hidden">
                    <div class="chat-message-wrapper flex-grow-1">
                            <div class="chat-message-text">
                                <p class="mb-0" ${cssStyle}>${chatHistory[i].sendContent}</p>
                            </div>
                            <div class="text-end text-muted mt-1">
                                <i class="bx bx-check-double text-success"></i>
                                <small>${chatHistory[i].sendTime}</small>
                            </div>
                            </div>
                                <div class="user-avatar flex-shrink-0 ms-3">
                                    <div class="avatar avatar-sm">
                                        <img src="${chatHistory[i].senderEmpProfileImg}" alt="Avatar"
                                        class="rounded-circle" />
                            </div>
							<div>${chatHistory[i].senderEmpName}</div>
                    </div>
                </div>
               </li >
                        `;
            }
        }
        $("#historyUl").append(liTags);
        if (!$(this).hasClass('chat-contact-list-item-title')) {
            $(this).addClass('active');
            $(this).find('.chat-contact-name').css('color', 'white');
            $(this).find('i').css('color', 'white');
        }
    })

    //우측 타이틀2 만드는 함수! 명단으로만 구성됨.
    const generateRightSideTitle = (chatRoomCd) => {
        $.ajax({
            type: "get",
            url: `/messenger/all/${chatRoomCd}`,
            contentType: "application/json",
            success: function (resp) {
                let title = generateChatRoomTitle(resp);
                $("#chatRoomTitle2").text(title);
            },
            error: function (xhr) {
                console.log("못가져옴 ==> ", xhr);
            }
        });
    }

    //5. 채팅 메시지 전송
    $("#msgSendBtn").on("click", function () {
        let crc = $chatRoomCd;
        console.log("send 에서 ==> " + $chatRoomCd);
        console.log($("#historyUl").has("#noChatMsg"));
        var currentDate = new Date();
        var formattedDate = currentDate.toISOString().split('T')[0]; // YYYY-MM-DD
        var formattedTime = new Intl.DateTimeFormat('en-US', { hour: '2-digit', minute: '2-digit' }).format(currentDate);
        sendData.sendDate = formattedDate;
        sendData.sendTime = formattedTime;
        sendData.chatRoomCd = crc;
        sendData.sendContent = $("#inputText").val();

        $("#inputText").val(""); //입력란 초기화
        // console.log("보내기전 마지막 ! sendData ===> ", sendData);

        webSocketChat.send(JSON.stringify(sendData));

        $(".chat-message").find('p').css('color', 'grey');
        $(".chat-message-right").find('p').css('color', 'white');
    });

    //채팅방 리스트 클릭 후 오른쪽 메뉴에서 드롭다운 메뉴 클릭시
    //채팅방 번호는 클릭 시에 $chatRoomCd 에 들어있음
    $("#modifyTitleBtn").on('click', function () {
        Swal.fire({
            title: '이름 변경',
            text: "채팅방 이름을 입력하세요.",
            input: 'text',
            inputPlaceholder: '입력..',
            showCancelButton: true,
            confirmButtonText: "확인",
            cancelButtonColor: '#939393'
        }).then(function (result) {
            if (result.isConfirmed) {
                let newTitle = result.value;
                $.ajax({
                    type: "PUT",
                    url: `/messenger/${$chatRoomCd}`,
                    data: newTitle, // 데이터를 JSON 형식으로 전달
                    dataType: "json",
                    contentType: "application/json",
                    success: function (resp) {
                        console.log("결과 ==>", resp);
                        Swal.fire("변경되었습니다.");
                    },
                    error: function (xhr) {
                        Swal.fire({
                            icon: "error",
                            title: "변경에 실패하였습니다.",
                            text: "다시 시도해주세요.",
                        });
                    }
                });
            }
        });
    });

    $("#delBtn").on('click', function () {
        chatRoomDelete($chatRoomCd);
    });
    $("#syncBtn").on('click', function (e) {
        location.reload();
    });
});

/* 선택한 채팅방 유형 (chatType)에 따른 목록 출력 */
//좌측 상단의 일반 / 프로젝트 탭 클릭시 실행하는 함수
function setChatType(type) {
    chatType = type;
    if (chatType == "P") { //프로젝트 선택시
        $("#chatTypeInput").val(chatType);
        $("#addEmpBtn").attr("style", "display:none;"); //추가 버튼 없애기
        // $(".chatRoomDeleteBtn").attr("style", "display:none; background-color:red;"); //채팅 리스트 삭제 버튼 숨기기
        // $(".chatRoomDeleteBtn")c.attr("style", "display:none;"); //채팅 리스트 삭제 버튼 숨기기
        $("#delBtn").attr("style", "display:none;"); //채팅방 내에서 삭제 버튼 숨기기
        $("#project-chat-list").empty();
    } else { //버튼들 초기화
        $("#chatTypeInput").val(chatType);
        $("#addEmpBtn").attr("style", "")
        $(".chatRoomDeleteBtn").attr("style", "")
        $("#delBtn").attr("style", "")
        $("#chat-list").empty();
    }
    //탭 누를때마다 채팅방 목록 가져오기
    getChatList();
}

// 소속된 채팅방 list와 참여자 list 가져오는 ajax
function getChatList (){
    $(function () {
        $.ajax({
            type: "GET",
            url: "/messenger",
            contentType: "application/json; charset=utf-8",
            success: handleChatListSuccess,
            error: function (xhr) {
                console.log("채팅방 정보를 못가져옴 ! ==> " + xhr.responseText);
            }
        });
    });
}

// 소속된 채팅방 정보 가져오기 성공 시 처리하는 함수 (좌측 리스트 출력)
function handleChatListSuccess(resp){
    //상단 (chatlist, 추가버튼)
    let listTag = "";
    if (!resp.chatList) { //대화내역이 없는 경우
        listTag += "대화내역 없음";
    } else {
        listTag += makeChatRoomList(resp); //메신저 모달의 좌측에 표시될 채팅방 리스트를 만드는 함수 (바로 아래)
    }
    // console.log("완성된 채팅방 목록 태그 : " + listTag);
    console.log("현재 선택되어있는 chatType: " + chatType);
    if (chatType == "G") { //일반 채팅이 선택되어있는 경우
        $("#chat-list").append(listTag);
    } else { //프로젝트 채팅이 선택되어 있는 경우
        $("#project-chat-list").append(listTag);
    }
}

// 응답을 통해 채팅방 리스트를 만드는 함수
function makeChatRoomList(resp) {
    let chatList = resp.chatList; //채팅방 리스트
    let empList = resp.empList; //채팅방 별 참여자 리스트
    let listTag ="";
    for (var i = 0; i < chatList.length; i++) { //개설된 채팅방이 존재하는 경우
        let chatRoomCd = chatList[i].chatRoomCd; //채팅방 번호
        // listTage 에 추가해야하는 경우의 수
        // 1. 프로젝트 채팅방 :  현재 선택된 채팅방이 프로젝트 채팅방이면서, 채팅방 이름이 P로 시작하는 경우
        // 2. 일반 채팅방 : 현재 선택된 채팅방이 일반 채팅방이면서, 채팅방 이름이 P로 시작하지 않는 경우
        console.log("chatRoomCd : " + chatList[i].chatRoomCd.includes("P"));
        if((chatType=="P") && (chatList[i].chatRoomCd.includes("P"))){ 
            let chatHistory = JSON.parse(localStorage.getItem(chatRoomCd)); //대화내역 가져오기
            if (!resp.chatList[i].chatRoomNm) { //채팅방 제목이 설정되지 않은 경우
                resp.chatList[i].chatRoomNm = generateChatRoomTitle(empList[chatRoomCd]); //1)참여자 이름을 나열하여 출력함
            }
            let { lastContent, lastDateTime } = getLastChatInfo(chatHistory); //2)마지막 대화내역 출력
            listTag += generateChatListItem(resp.chatList[i], lastContent, lastDateTime, empList[chatRoomCd]); //3)완성된 lsitTag 목록 만들기
        }else if((chatType=="G") &&!(chatList[i].chatRoomCd.includes("P"))){
            let chatHistory = JSON.parse(localStorage.getItem(chatRoomCd)); //대화내역 가져오기
            if (!resp.chatList[i].chatRoomNm) { //채팅방 제목이 설정되지 않은 경우
                resp.chatList[i].chatRoomNm = generateChatRoomTitle(empList[chatRoomCd]); //1)참여자 이름을 나열하여 출력함
            }
            let { lastContent, lastDateTime } = getLastChatInfo(chatHistory); //2)마지막 대화내역 출력
            listTag += generateChatListItem(resp.chatList[i], lastContent, lastDateTime, empList[chatRoomCd]); //3)완성된 lsitTag 목록 만들기
        }else{
            //나머지는 처리 안함
        }
    }
    return listTag;
}

// 1)채팅방 타이틀 생성 함수 (모든 참여자 나열)
function generateChatRoomTitle(allParticipantArray){
    let chatRoomTitle = "";
    if (allParticipantArray.length > 2) {
        for (var i = 0; i < allParticipantArray.length; i++) {
            if (allParticipantArray[i].chatEmpCd != $("#empCd").val()) {
                chatRoomTitle += `${allParticipantArray[i].empName}(${allParticipantArray[i].deptName}), `;
            }
        }
    } else {
        for (var i = 0; i < allParticipantArray.length; i++) {
            if (allParticipantArray[i].chatEmpCd !== $("#empCd").val()) {
                chatRoomTitle += `${allParticipantArray[i].empName}(${allParticipantArray[i].deptName})`;
            }
        }
    }
    return chatRoomTitle;
}

// 2)마지막 채팅 정보 가져오는 함수
function getLastChatInfo(chatHistory){
    let lastContent = " ";
    let lastDateTime = " ";

    if (chatHistory == null) {
        // 대화 내용이 존재하지 않는 경우
    } else if ($(chatHistory).length == 1) {
        // 대화가 한건인 경우
        if (compareToToday(chatHistory[0].sendDate)) {
            lastDateTime = chatHistory[0].sendTime;
        } else {
            lastDateTime = chatHistory[0].sendDate;
        }
        lastContent = chatHistory[0].sendContent;
    } else {
        // 대화가 두건 이상일 경우
        let index = $(chatHistory).length - 1;
        let lastDate = chatHistory[index].sendDate;
        let lastTime = chatHistory[index].sendTime;
        lastContent = chatHistory[index].sendContent;
        if (compareToToday(lastDate)) {
            lastDateTime = lastTime;
        } else {
            lastDateTime = lastDate;
        }
    }
    return { lastContent, lastDateTime };
}

// 3)채팅 리스트 아이템 생성 함수
function generateChatListItem (chatListItem, lastContent, lastDateTime, empList){
    //해당 채팅방의 인원이 여러명인 경우 사람들 다 출력
    let chatRoomCd = chatListItem.chatRoomCd; //채팅방 번호
    let memberCount = empList.length; //인원수
    let avatarTag = '<div class="d-flex align-items-center avatar-group my-3">';
    avatarTag += '';

    if(memberCount<=3){ //세명까지는 그냥 표시함!
        for (var i = 0; i < memberCount; i++) {
            let profileImg = empList[i].empProfileImg;
            avatarTag += `
                <div class="avatar avatar-md">
                    <img src="${profileImg}" alt="Avatar" class="rounded-circle pull-up">
                </div>
                `;
        }
    }else{ //네명부터는 생략해서 표시함
        for (var i = 0; i < 2; i++) {
            let profileImg = empList[i].empProfileImg;
            avatarTag += `
            <div class="avatar avatar-md">
            <img src="${profileImg}" alt="Avatar" class="rounded-circle pull-up">
            </div>
            `;
        }
        let leftMemberCount = memberCount-3;
        avatarTag += `
            <div class="avatar">
                <span class="avatar-initial rounded-circle pull-up bg-secondary" data-bs-toggle="tooltip" data-bs-placement="bottom" data-bs-original-title="3 more">\+${leftMemberCount}</span>
            </div>
        `;
    }
    avatarTag += '</div>';

    //일반 채팅방은 삭제 버튼 안만듦
    let deleteIcon = "";
    if(chatType=="G"){
        deleteIcon += `
            <div id="chatRoomDeleteBtn" class="chatRoomDeleteBtn" data-value="${chatRoomCd}">
                <i class='bx bx-xs bxs-minus-circle'></i>
            </div>
        `;
    }

    let returnTag = "";
    returnTag += `<li class="chat-contact-list-item" data-value="${chatRoomCd}">
                ${deleteIcon}
                <a class="d-flex align-items-center">
                     ${avatarTag}
                    <div class="chat-contact-info flex-grow-1 ms-3">
                        <h6 class="chat-contact-name text-truncate m-0">${chatListItem.chatRoomNm}</h6>
                        <p class="chat-contact-status text-truncate mb-0 text-muted">
                            ${lastContent}
                        </p>
                    </div> <small class="text-muted mb-auto">${lastDateTime}</small>
                </a>
            </li>`;
    return returnTag;
}