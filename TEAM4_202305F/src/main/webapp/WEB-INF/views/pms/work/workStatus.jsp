<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* Nov 20, 2023      송석원      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<c:if test="${not empty paging.dataList}">
    <div class="col-md">
        <div class="accordion mt-3 accordion-header-primary" id="accordionStyle1">
            <table class="table">
                <thead>
                    <tr>
                        <th>프로젝트</th>
                        <th>일감명</th>
                        <th>작성자</th>
                        <th>진행도</th>
                        <th>담당자</th>
                        <th>상태</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <c:forEach items="${paging.dataList}" var="work">
                        <c:forEach items="${work.pjobList}" var="workjob">
                            <tr>
                                <td>${work.proNm}</td>
                                <td>${workjob.jobSj}</td>
                                <td>${workjob.jobWriter}</td>
                                <td>${workjob.jobProgrs}</td>
                                <c:forEach items="${workjob.chargerList}" var="workcharger">
                                    <td>${workcharger.emp.empName}</td>
                                </c:forEach>
                                <td>${workjob.jobStcd}</td>
                            </tr>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</c:if>



		</tbody>
		<tfoot>
			<tr>
				<td colspan="6">${paging.pagingHTML }
					<div id="searchUI" class="row g-3 d-flex justify-content-center">
						<div class="col-auto">
							<form:select path="simpleCondition.searchType"
								class="form-select">
								<form:option label="전체" value="" />
								<form:option label="제목" value="title" />
							</form:select>
						</div>
						<div class="col-auto">
							<form:input path="simpleCondition.searchWord" placeholder="검색키워드"
								class="form-control" />
						</div>
						<div class="col-auto">
							<input type="button" value="검색" id="searchBtn"
								class="btn btn-primary" />
						</div>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
</div>
