<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--    <script src="/resources/assets/js/app-email.js"></script> -->
<!--    <script src="/resources/assets/vendor/libs/quill/quill.js"></script> -->
<script src="/resources/js/ckeditor/ckeditor.js"></script>
<script src="/resources/assets/vendor/libs/block-ui/block-ui.js"></script>
<script src="/resources/assets/vendor/libs/hammer/hammer.js"></script>
<script src="/resources/assets/vendor/js/template-customizer.js"></script>
<script src="/resources/assets/vendor/libs/quill/katex.js"></script>

<!-- Content -->
<div class="container-xxl flex-grow-1 container-p-y">
	<div class="app-email card">
		<div class="border-0">
			<div class="row g-0">
				<!-- Email Sidebar -->
				<div class="col app-email-sidebar border-end flex-grow-0"
					id="app-email-sidebar">
					<div class="btn-compost-wrapper d-grid">
						<button class="btn btn-primary btn-compose" data-bs-toggle="modal"
							data-bs-target="#emailComposeSidebar"
							id="emailComposeSidebarLabel">메일 작성</button>
					</div>

					<!-- 메일함 분류-->
					<div class="email-filters py-2">
						<!-- Email Filters: Folder -->
						<ul class="email-filter-folders list-unstyled pb-1">
							<li class="active d-flex justify-content-between"
								data-target="inbox"><a href="javascript:void(0);"
								class="d-flex flex-wrap align-items-center"> <i
									class="bx bx-envelope"></i> <span class="align-middle ms-2"
									id="receptionMailBox">받은메일함</span>
							</a>
								<div class="badge bg-label-primary rounded-pill"
									id="receptionMailBoxCount"></div></li>
							<li class="d-flex" data-target="sent"><a
								href="javascript:void(0);"
								class="d-flex flex-wrap align-items-center"> <i
									class="bx bx-send"></i> <span class="align-middle ms-2"
									id="sentMailBox">보낸메일함</span>
							</a></li>
							<li class="d-flex justify-content-between" data-target="draft">
								<a href="javascript:void(0);"
								class="d-flex flex-wrap align-items-center"> <i
									class="bx bx-edit"></i> <span class="align-middle ms-2">임시저장함</span>
							</a>
								<div class="badge bg-label-warning rounded-pill">8</div>
							</li>
							<li class="d-flex" data-target="starred"><a
								href="javascript:void(0);"
								class="d-flex flex-wrap align-items-center"> <i
									class="bx bx-star"></i> <span class="align-middle ms-2"
									id="importantMailBox">중요메일함</span>
							</a></li>
							<li class="d-flex align-items-center" data-target="trash"><a
								href="javascript:void(0);"
								class="d-flex flex-wrap align-items-center"> <i
									class="bx bx-trash-alt"></i> <span class="align-middle ms-2"
									id="deletedMailBox">휴지통</span>
							</a></li>
						</ul>
					</div>
				</div>
				<!--/ 메일함 분류 -->

				<!-- Emails List -->
				<div class="col app-emails-list">
					<div class="card shadow-none border-0">
						<div class="card-body emails-list-header p-3 py-lg-3 py-2">
							<!-- Email List: Search -->
							<div class="d-flex justify-content-between align-items-center">
								<div class="d-flex align-items-center w-100">
									<i
										class="bx bx-menu bx-sm cursor-pointer d-block d-lg-none me-3"
										data-bs-toggle="sidebar" data-target="#app-email-sidebar"
										data-overlay></i>
									<div class="mb-0 mb-lg-2 w-100">
										<div class="input-group input-group-merge shadow-none">
											<span class="input-group-text border-0 ps-0 py-0"
												id="email-search"> <i
												class="bx bx-search fs-4 text-muted"></i>
											</span> <input type="text"
												class="form-control email-search-input border-0 py-0"
												placeholder="Search mail" aria-label="Search..."
												aria-describedby="email-search" />
										</div>
									</div>
								</div>
								<div class="d-flex align-items-center mb-0 mb-md-2">
									<i
										class="bx bx-refresh scaleX-n1-rtl cursor-pointer email-refresh me-2 bx-sm text-muted"></i>
									<div class="dropdown">
										<button class="btn p-0" type="button" id="emailsActions"
											data-bs-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">
											<i class="bx bx-dots-vertical-rounded bx-sm text-muted"></i>
										</button>
										<div class="dropdown-menu dropdown-menu-end"
											aria-labelledby="emailsActions">
											<a class="dropdown-item" href="javascript:void(0)">읽은메일만</a>
											<a class="dropdown-item" href="javascript:void(0)">안읽은메일만</a>
											<a class="dropdown-item" href="javascript:void(0)">Delete</a>
											<a class="dropdown-item" href="javascript:void(0)">Archive</a>
										</div>
									</div>
								</div>
							</div>
							<hr class="mx-n3 emails-list-header-hr" />
							<!-- Email List: Actions -->
							<div class="d-flex justify-content-between align-items-center">
								<div class="d-flex align-items-center">
									<div class="form-check me-2">
										<input class="form-check-input" type="checkbox" id="email-select-all" />
										<label class="form-check-label" for="email-select-all"></label>
									</div>
								</div>
									<!-- pagination 영역 -->
									<div id="paginationArea" class="d-flex align-items-center"></div>
							</div>
						</div>
						<hr class="container-m-nx m-0" />
						<!-- Email List: Items -->
						<div class="email-list pt-0">
							<ul class="list-unstyled m-0" id="mailUl">
								<!-- 여기에 append -->
							</ul>
							<ul class="list-unstyled m-0">
								<li class="email-list-empty text-center d-none">No items
									found.</li>
							</ul>
						</div>
					</div>
					<div class="app-overlay"></div>
				</div>
				<!-- /Emails List -->

				<!-- Email View -->
				<!-- 여기 메일 내용!!! -->
				<div class="col app-email-view flex-grow-0 bg-body"
					id="app-email-view">
					<div class="app-email-view-header p-3 py-md-3 py-2 rounded-0">
						<!-- Email View : Title  bar-->
						<div class="d-flex justify-content-between align-items-center">
							<div class="d-flex align-items-center overflow-hidden" id="backBtn">
								<i class="bx bx-chevron-left bx-sm cursor-pointer me-2"
									data-bs-toggle="sidebar" data-target="#app-email-view"></i>
							</div>
							<!-- Email View : Action  bar-->
							<div class="d-flex">
								<div class="dropdown ms-3">
									<button class="btn p-0" type="button" id="dropdownMoreOptions"
										data-bs-toggle="dropdown" aria-haspopup="true"
										aria-expanded="false">
										<i class="bx bx-dots-vertical-rounded fs-4"></i>
									</button>
									<div class="dropdown-menu dropdown-menu-end"
										aria-labelledby="dropdownMoreOptions">
										<a class="dropdown-item" href="javascript:void(0)"> <i
											class="bx bx-envelope-open bx-xs me-1"></i> <span
											class="align-middle">Mark as unread</span>
										</a> <a class="dropdown-item" href="javascript:void(0)"> <i
											class="bx bx-envelope-open bx-xs me-1"></i> <span
											class="align-middle">Mark as unread</span>
										</a> <a class="dropdown-item" href="javascript:void(0)"> <i
											class="bx bx-star bx-xs me-1"></i> <span class="align-middle">Add
												star</span>
										</a>
									</div>
								</div>
							</div>
						</div>
						<hr class="app-email-view-hr mx-n3 mb-2" />
						<div class="d-flex justify-content-between align-items-center">
							<div class="d-flex align-items-center">
								<i class="bx bx-trash-alt cursor-pointer me-3 fs-4"
									data-bs-toggle="sidebar" data-target="#app-email-view"></i> <i
									class="bx bx-envelope fs-4 cursor-pointer me-3"></i>
								<div class="dropdown">
									<button class="btn p-0" type="button"
										id="dropdownMenuFolderTwo" data-bs-toggle="dropdown"
										aria-haspopup="true" aria-expanded="false">
										<i class="bx bx-folder fs-4 me-3"></i>
									</button>
									<div class="dropdown-menu dropdown-menu-end"
										aria-labelledby="dropdownMenuFolderTwo">
										<a class="dropdown-item" href="javascript:void(0)"> <i
											class="bx bx-error-circle me-1"></i> <span
											class="align-middle">Spam</span>
										</a> <a class="dropdown-item" href="javascript:void(0)"> <i
											class="bx bx-edit me-1"></i> <span class="align-middle">Draft</span>
										</a> <a class="dropdown-item" href="javascript:void(0)"> <i
											class="bx bx-trash-alt me-1"></i> <span class="align-middle">Trash</span>
										</a>
									</div>
								</div>
								<div class="dropdown">
									<button class="btn p-0" type="button" id="dropdownLabelTwo"
										data-bs-toggle="dropdown" aria-haspopup="true"
										aria-expanded="false">
										<i class="bx bx-label fs-4 me-3"></i>
									</button>
									<div class="dropdown-menu dropdown-menu-end"
										aria-labelledby="dropdownLabelTwo">
										<a class="dropdown-item" href="javascript:void(0)"> <i
											class="badge badge-dot bg-success me-1"></i> <span
											class="align-middle">Workshop</span>
										</a> <a class="dropdown-item" href="javascript:void(0)"> <i
											class="badge badge-dot bg-primary me-1"></i> <span
											class="align-middle">Company</span>
										</a> <a class="dropdown-item" href="javascript:void(0)"> <i
											class="badge badge-dot bg-warning me-1"></i> <span
											class="align-middle">Important</span>
										</a>
									</div>
								</div>
							</div>
							<div
								class="d-flex align-items-center flex-wrap justify-content-end">
								<span class="d-sm-block d-none mx-3 text-muted">1-10 of
									653</span> <i
									class="bx bx-chevron-left scaleX-n1-rtl cursor-pointer text-muted me-4 fs-4"></i>
								<i class="bx bx-chevron-right scaleX-n1-rtl cursor-pointer fs-4"></i>
							</div>
						</div>
					</div>
					<hr class="m-0" />
					<!-- Email View : Content-->
					<div class="app-email-view-content py-4">
						<!---------------------------------------- 현재 메일 내용 -------------------------------->
						<div class="card email-card-last mx-sm-4 mx-3 mt-4 border">
							<div
								class="card-header d-flex justify-content-between align-items-center flex-wrap border-bottom">
								<div class="d-flex align-items-center mb-sm-0 mb-3" id="emailViewImgDiv">
									
								</div>
								<div class="d-flex align-items-center">
									<small class="mb-0 me-3 text-muted" id="emailViewDateTime"></small>
									<i class="bx bx-paperclip cursor-pointer me-2 bx-sm"></i> <i
										class="email-list-item-bookmark bx bx-star cursor-pointer me-2 bx-sm"></i>
									<div class="dropdown me-3">
										<button class="btn p-0" type="button" id="dropdownEmailTwo"
											data-bs-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">
											<i class="bx bx-dots-vertical-rounded bx-sm"></i>
										</button>
										<div class="dropdown-menu dropdown-menu-end"
											aria-labelledby="dropdownEmailTwo">
											<a class="dropdown-item scroll-to-reply"
												href="javascript:void(0)"> <i class="bx bx-share me-1"></i>
												<span class="align-middle">Reply</span>
											</a> <a class="dropdown-item" href="javascript:void(0)"> <i
												class="bx bx-share me-1 scaleX-n1 scaleX-n1-rtl"></i> <span
												class="align-middle">Forward</span>
											</a> <a class="dropdown-item" href="javascript:void(0)"> <i
												class="bx bx-info-circle me-1"></i> <span
												class="align-middle">Report</span>
											</a>
										</div>
									</div>
								</div>
							</div>
							<div class="card-body pt-3" id="emailViewCardBody">
								<hr />
								<p class="mb-2">Attachments</p>
								<div class="cursor-pointer" id="emailViewAttaches"></div>
							</div>

						</div>
					</div>
				</div>
				<!-- Email View -->
			</div>
		</div>

		<!-- Compose Email -->
		<div class="app-email-compose modal" id="emailComposeSidebar"
			tabindex="-1" aria-labelledby="emailComposeSidebarLabel"
			aria-hidden="true">
			<div class="modal-dialog m-0 me-md-4 mb-4 modal-lg">
				<div class="modal-content p-0">
					<div class="modal-header py-3 bg-body">
						<h5 class="modal-title fs-5">Compose Mail</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body flex-grow-1 pb-sm-0 p-4 py-2">
						<!-- form -->
						<form class="email-compose-form" action="/mail" method="POST" enctype="multipart/form-data" id="sendForm">
							<div 	class="email-compose-to d-flex justify-content-between align-items-center">
								<label class="form-label mb-2" for="receiversCd">To:</label>
								<div 	class="select2-primary border-0 shadow-none flex-grow-1 mx-2">
									<select class="select2 select-email-contacts form-select" id="receiversCd" name="mailReceivers" multiple>
									</select>
								</div>
							</div>

							<hr class="mx-n4 my-0" />
							<div class="email-compose-subject d-flex align-items-center my-1">
								<label for="email-subject" class="form-label mb-0">Subject:</label>
								<input type="text"
									class="form-control border-0 shadow-none flex-grow-1 mx-2"
									id="mailSj" name="mailSj" />
							</div>
							<div class="email-compose-message mx-n4">
								<div class="d-flex justify-content-end">
									<div class="email-editor-toolbar border-0 w-100 border-top">
										<textarea id="mailCn" name="mailCn"></textarea>
									</div>
								</div>
								<div class="email-editor border-0 border-top"></div>
							</div>
							<hr class="mx-n4 mt-0 mb-2" />
							<div class="email-compose-actions d-flex justify-content-between align-items-center my-2 py-1">
								<div class="d-flex align-items-center">
									<div class="btn-group">
										<input type="button" value="Send" id="sendBtn"
											class="btn btn-primary" data-bs-dismiss="modal"
											aria-label="Close" />
										<button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">
											<span class="visually-hidden">Toggle Dropdown</span>
										</button>
										<ul class="dropdown-menu">
											<li><a class="dropdown-item" href="javascript:void(0);" id="draftBtn">임시저장</a></li>
										</ul>
									</div>
									<input type="file" name="files" class="attachments form-control" id="attachments" multiple style="margin-left:15px" />
								</div>
								<div class="d-flex align-items-center">
									<div class="dropdown">
										<button class="btn p-0" type="button" id="dropdownMoreActions"
											data-bs-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false">
											<i class="bx bx-dots-vertical-rounded"></i>
										</button>
										<ul class="dropdown-menu"
											aria-labelledby="dropdownMoreActions">
											<li><button type="button" class="dropdown-item">Add Label</button></li>
											<li><button type="button" class="dropdown-item">Plain text mode</button></li>
											<li>
												<hr class="dropdown-divider" />
											</li>
											<li><button type="button" class="dropdown-item">Print</button></li>
											<li><button type="button" class="dropdown-item">Check Spelling</button></li>
										</ul>
									</div>
									<button type="reset" class="btn" data-bs-dismiss="modal"
										aria-label="Close">
										<i class="bx bx-trash-alt"></i>
									</button>
								</div>
							</div>
							<sec:csrfInput />
						</form>
					</div>
				</div>
			</div>
		</div>
		<!-- /Compose Email -->
	</div>
</div>
<div class="content-backdrop fade"></div>



<script src="/resources/js/app/mail/mail.js"></script>
