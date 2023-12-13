/**
 * 
 */
$(function(){
	$("#popBtn").on("click", function(){
		$.ajax(
  		 {
      type: "get",
      url: "/org/do",
      contentType: "application/json",
      dataType: "json",
      success: function(data) {
         let list = data.list;
         let dept = data.dept;

         let text = '<ul><li>All-Rounder<ul>';

         $.each(list, function(index, org) {
            if (org.common.commonCodeSj === '대표이사' || org.common.commonCodeSj === '본부장') {
               text += '<li data-value='+ org.empCd +' , data-jstree=\'{ "type" : "file" }\'>' + org.empName + ' ' + org.common.commonCodeSj + '</li>';
            }
         });

         $.each(dept, function(index, dept) {
            if (dept.deptName !== '대표이사' && dept.deptName !== '본부장') {
               text += '<li data-jstree=\'{ "selected" : true }\'><a href="javascript:;">' + dept.deptName + '</a><ul>';

               $.each(list, function(index, org) {
                  if (org.dept.deptName === dept.deptName) {
                     text += '<li data-value='+ org.empCd +', data-jstree=\'{ "type" : "file" }\'>' + org.empName + ' ' + org.common.commonCodeSj + '</li>';
                  }
               });

               text += '</ul></li>';
            }
         });

         text += '</ul></li></ul>';
         $("#kt_docs_jstree_basic").html(text);

         $('#kt_docs_jstree_basic').jstree({
               "core": {
                   "themes": {
                       "responsive": false
                   }
               },
               "types": {
                   "default": {
                       "icon": "fa fa-folder"
                   },
                   "file": {
                       "icon": "fa fa-file"
                   }
               },
               "plugins": ["types"]
           });
         
         
      },
      error: function(xhr) {
         alert(xhr);
      }
   }
)
	})
})