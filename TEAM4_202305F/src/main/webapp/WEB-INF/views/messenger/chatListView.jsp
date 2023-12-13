<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<input type="button" id="popBtn" value="신규채팅방"/>

<div>
클라이언트가 직접 선택하는 정보 : 
누구랑 채팅할건지 !
popBtn 누르면 채팅방을 구성할 구성원 고르기

	<div>
		<!-- Checkbox -->
        <div class="col-md-6 col-12">
          <div class="card mb-md-0 mb-4">
            <h5 class="card-header">Checkboxes</h5>
            <div class="card-body">
              <div id="jstree-checkbox"></div>
            </div>
          </div>
        </div>
        <!-- /Checkbox -->
	</div>
</div>



<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/messenger/chatList.js"></script>
