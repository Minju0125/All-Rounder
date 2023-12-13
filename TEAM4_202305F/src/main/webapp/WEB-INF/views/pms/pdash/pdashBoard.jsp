<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<style>
	.test{
		border: 1px solid black;
		width: 650px;
	}
	
	#onebox {
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: space-around;
}
	.card-body {
    height: 250px;
    }
	
	
</style>




<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">
    <!-- Layout container -->
    <div class="layout-page">

      <!-- Content wrapper -->
      <div>
        <!-- Content -->
        <h3 class="pb-1 mb-4 text-muted">${emp}님 안녕하세요</h3>

	
        <div class="row mb-5">
         	<div align = "center">
		<!-- <table border="1" width = "400" height = "400" align = "center"> -->
		<table border="1" style="width: 400px; height: 400px;">
			<tr>
				<!-- td>내용에 따라 크기가 자동으로 조절됩니다.</td> -->
				<td width = "30%" height = "100px">
				1-1
				<div id="onebox" class="test"  >
          <div class="col-md-6 col-lg-4">
            <h6 class="mt-2 text-muted">일정</h6>
            <div class="card mb-4">
            <div class="card-body">
                <!--이분에서 작업-->
              </div>
              
            </div>
            
          </div>
          <div class="col-md-6 col-lg-4">
            <h6 class="mt-2 text-muted">오늘의 일정</h6>
            <div class="card mb-4">
              <div class="card-body">
                <!--이분에서 작업-->
              </div>
            </div>
          </div>
          <!--test  -->
          </div>
				</td>
				<td>
				1-2
				<div id="twobox" class="test">
          <div class="col-md-6 col-lg-4">
            <h6 class="mt-2 text-muted">메모장</h6>
            <div class="card mb-4">
              <div class="card-body" id="memoSelect">
                <!--이분에서 작업-->
              </div>
            </div>
          </div>
          </div>
				</td>
			</tr>
			<tr>
				<td>2-1
				<div class="col-md-6 col-lg-4">
            <h6 class="mt-2 text-muted">내일감</h6>
            <div class="card mb-4">
              <div class="card-body">
                <!--이분에서 작업-->
              </div>
            </div>
          </div>
				</td>
				<td>2-2
				  <h6 class="mt-2 text-muted">내 일감 현황</h6>
            <div class="card mb-4">
              <div class="card-body">
                <!--이분에서 작업-->
              </div>
            </div>
            <h6 class="mt-2 text-muted">내 이슈 현황</h6>
            <div class="card mb-4">
              <div class="card-body">
                <!--이분에서 작업-->
              </div>
            </div></td>
			</tr>
		</table>
	</div>
         
         
         
         
         
         
        </div>
        
        


        <!-- /Content -->
      </div>
      <!-- /Content wrapper -->
    </div>
    <!-- /Layout container -->
  </div>
  <!-- /Layout wrapper -->
</div>