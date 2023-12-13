<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
    <!DOCTYPE html>

    <html>

    <head>
      <meta charset="UTF-8" />
      <meta name="viewport"
        content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

      <title>올라운더</title>
		<sec:csrfMetaTags/>

      <meta name="description" content="" />
      <meta name="keywords" content="dashboard, bootstrap 5 dashboard, bootstrap 5 design, bootstrap 5">

      <tiles:insertAttribute name="preScript" />
      <tiles:insertAttribute name="cssScript_oks" />

    </head>

    <body data-context-path="${pageContext.request.contextPath }">

   <!-- Layout wrapper -->
      <div class="layout-wrapper layout-content-navbar  ">
        <div class="layout-container">

          <!-- leftMenu -->
          <tiles:insertAttribute name="leftMenu" />
          <!-- /leftMenu -->

          <!-- Layout container -->
          <div class="layout-page">

            <tiles:insertAttribute name="headerMenu" />



            <!-- Content wrapper -->
            <div class="content-wrapper" >
            
				<div class="container-xxl flex-grow-1 container-p-y">
	                <!-- Main Content Area start -->
	                <tiles:insertAttribute name="content" />
	                <!-- Main Content Area end -->
                </div>
              </div>
              <tiles:insertAttribute name="footer" />

              <div class="content-backdrop fade"></div>
            </div>
            <!-- Content wrapper -->
          </div>
          <!-- / Layout page -->
        </div>

        <!-- Overlay -->
        <div class="layout-overlay layout-menu-toggle"></div>

      <!-- / Layout wrapper -->

      


    </body>
      <tiles:insertAttribute name="postScript" />

    </html>

    <!-- beautify ignore:end -->