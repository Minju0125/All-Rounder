<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="${pageContext.request.contextPath }/resources/js/app/org/chartOrg.js"></script>
    
    
<!-- jQuery가 postScript에 있어서 먼저 읽지 못하고 chartOrg.js를 읽어서 오류남. 그래서 postScript가 먼저 실행하고 이 이벤트를 실행하기 위해 사용 -->
<!-- 이제는 전체 편의를 위해 jQuery를 preScript에 옮겨서 사용 X -->    

<!--  <script>
	window.addEventListener("DOMContentLoaded", function(){
		let a = document.createElement("script");
		a.src = "${pageContext.request.contextPath }/resources/js/app/org/chartOrg.js";
		document.body.appendChild(a);
	});
</script>		  -->
		
<style>
html, body {
    margin: 0px;
    padding: 0px;
    width: 100%;
    height: 100%;
    font-family: Helvetica;
    overflow: hidden;
}

#tree {
    width: 100%;
    height: 100%;
}



</style>		
		
<div style="width:100%; height:700px;" id="tree"></div>

<script>
OrgChart.templates.ana.plus = '<circle cx="15" cy="15" r="15" fill="#ffffff" stroke="#aeaeae" stroke-width="1"></circle>'
    + '<text text-anchor="middle" style="font-size: 18px;cursor:pointer;" fill="#757575" x="15" y="22">{collapsed-children-count}</text>';

OrgChart.templates.itTemplate = Object.assign({}, OrgChart.templates.ana);
OrgChart.templates.itTemplate.nodeMenuButton = "";
OrgChart.templates.itTemplate.nodeCircleMenuButton = {
    radius: 18,
    x: 250,
    y: 60,
    color: '#fff',
    stroke: '#aeaeae'
};

OrgChart.templates.invisibleGroup.padding = [20, 0, 0, 0];
$.ajax({
	type:"GET",
	url:"/org/list",
	contentType:"application/json",
	dataType:"json",
	success:function(rslt){
		let list = rslt.list;
		let dept = rslt.dept;
	//	console.log(dept);
		let chart = new OrgChart(document.getElementById("tree"), {
			mouseScrool: OrgChart.action.ctrlZoom,
		    template: "ula",
		    enableDragDrop: true,
		    assistantSeparation: 170,
		    nodeCircleMenu: {
		        details: {
		            icon: OrgChart.icon.details(24, 24, '#aeaeae'),
		            text: "Details",
		            color: "white"
		        },
		        edit: {
		            icon: OrgChart.icon.edit(24, 24, '#aeaeae'),
		            text: "Edit node",
		            color: "white"
		        },
		        add: {
		            icon: OrgChart.icon.add(24, 24, '#aeaeae'),
		            text: "Add node",
		            color: "white"
		        },
		        remove: {
		            icon: OrgChart.icon.remove(24, 24, '#aeaeae'),
		            text: "Remove node",
		            color: '#fff',
		        },
		        addLink: {
		            icon: OrgChart.icon.link(24, 24, '#aeaeae'),
		            text: "Add C link(drag and drop)",
		            color: '#fff',
		            draggable: true
		        }
		    },
		    menu: {
		        pdfPreview: {
		            text: "Export to PDF",
		            icon: OrgChart.icon.pdf(24, 24, '#7A7A7A'),
		            onClick: preview
		        },
		        csv: { text: "Save as CSV" }
		    },
		    nodeMenu: {
		        details: { text: "Details" },
		        edit: { text: "Edit" },
		        add: { text: "Add" },
		        remove: { text: "Remove" }
		    },
		    align: OrgChart.ORIENTATION,
		    toolbar: {
		        fullScreen: true,
		        zoom: true,
		        fit: true,
		        expandAll: true
		    },
		    nodeBinding: {
		        field_0: "이름",
		        field_1: "부서명",
		        field_2: "직책",
		        field_3: "전화번호",
		        field_4: "이메일",
		        img_0: "img"
		    },
		    tags: {
		        "대표이사": {
		            template: "invisibleGroup",
		            subTreeConfig: {
		                orientation: OrgChart.orientation.bottom,
		                collapse: {
		                    level: 1
		                }
		            }
		        },
		        "영업부": {
		            subTreeConfig: {
		                layout: OrgChart.mixed,
		                collapse: {
		                    level: 1
		                }
		            },
		        },
		        "관리부": {
		            subTreeConfig: {
		                layout: OrgChart.treeRightOffset,
		                collapse: {
		                    level: 1
		                }
		            },
		        },
		        "생산부": {
		            subTreeConfig: {
		                layout: OrgChart.treeLeftOffset,
		                collapse: {
		                    level: 1
		                }
		            },
		        },
		        "기술부": {
		            subTreeConfig: {
		                layout: OrgChart.treeLeftOffset,
		                collapse: {
		                    level: 1
		                }
		            },
		        },
		        "개발부": {
		            subTreeConfig: {
		                layout: OrgChart.treeLeftOffset,
		                collapse: {
		                    level: 1
		                }
		            },
		        },
		        "seo-menu": {
		            nodeMenu: {
		                addSharholder: { text: "Add new sharholder", icon: OrgChart.icon.add(24, 24, "#7A7A7A"), onClick: addSharholder },
		                addDepartment: { text: "Add new department", icon: OrgChart.icon.add(24, 24, "#7A7A7A"), onClick: addDepartment },
		                addAssistant: { text: "Add new assitsant", icon: OrgChart.icon.add(24, 24, "#7A7A7A"), onClick: addAssistant },
		                edit: { text: "Edit" },
		                details: { text: "Details" },
		            }
		        },
		        "menu-without-add": {
		            nodeMenu: {
		                details: { text: "Details" },
		                edit: { text: "Edit" },
		                remove: { text: "Remove" }
		            }
		        },
		        "department": {
		            template: "group",
		            nodeMenu: {
		                addManager: { text: "Add new manager", icon: OrgChart.icon.add(24, 24, "#7A7A7A"), onClick: addManager },
		                remove: { text: "Remove department" },
		                edit: { text: "Edit department" },
		                nodePdfPreview: { text: "Export department to PDF", icon: OrgChart.icon.pdf(24, 24, "#7A7A7A"), onClick: nodePdfPreview }
		            }
		        },
		        "영업부-member": {
		            template: "ula",
		        }
		    },
		    clinks: [
		        { from: 11, to: 18 }
		    ]
		});

		chart.nodeCircleMenuUI.on('click', function (sender, args) {
		    switch (args.menuItem.text) {
		        case "Details": chart.editUI.show(args.nodeId, true);
		            break;
		        case "Add node": {
		            var id = chart.generateId();
		            chart.addNode({ id: id, pid: args.nodeId, tags: ["영업부-member"] });
		        }
		            break;
		        case "Edit node": chart.editUI.show(args.nodeId);
		            break;
		        case "Remove node": chart.removeNode(args.nodeId);
		            break;
		        default:
		    };
		});

		chart.nodeCircleMenuUI.on('drop', function (sender, args) {
		    chart.addClink(args.from, args.to).draw(OrgChart.action.update);
		});

		chart.on("added", function (sender, id) {
		    sender.editUI.show(id);
		});

		chart.on('drop', function (sender, draggedNodeId, droppedNodeId) {
		    var draggedNode = sender.getNode(draggedNodeId);
		    var droppedNode = sender.getNode(droppedNodeId);
		    if (droppedNode != undefined) {
		        if (droppedNode.tags.indexOf("department") != -1 && draggedNode.tags.indexOf("department") == -1) {
		            var draggedNodeData = sender.get(draggedNode.id);
		            draggedNodeData.pid = null;
		            draggedNodeData.stpid = droppedNode.id;
		            sender.updateNode(draggedNodeData);
		            return false;
		        }
		    }

		});


		chart.on('exportstart', function (sender, args) {
		    args.styles = document.getElementById('myStyles').outerHTML;
		});

		let text = [];
			const dN1 = { 
					id : `\${dept[0].deptName}`,
					tags : [`\${dept[0].deptName}`]			
				};
			const dN2 = {
				id : `\${dept[1].deptName}`,
				pid : `\${dept[0].deptName}`,
				tags : [`\${dept[1].deptName}`, "department"],
				이름 : `\${dept[1].deptName}`
			};
			text.push(dN1, dN2);
			for(let i=2; i<dept.length; i++){
				const dN3 = {
					id : `\${dept[i].deptName}`,
					pid : `\${dept[1].deptName}`,
					tags : [`\${dept[i].deptName}`, "department"],
					이름 : `\${dept[i].deptName}`
				};
				text.push(dN3);
			}
	//		console.log(text);
			
			for(let i=1; i<3; i++){
				const dN4 = {
					id: `\${list[i].empCd}`,
					stpid : `\${list[i].dept.deptName}`,
					이름: `\${list[i].empName}`,
					부서명: `\${list[i].dept.deptName}`,
					직책 : `\${list[i].common.commonCodeSj}`,
					전화번호: `\${list[i].empTelno}`,
					이메일: `\${list[i].empMail}`,
					img: `\${list[i].empProfileImg}`	
				}
				text.push(dN4);
			}
		
			for(let i=1; i<list.length; i++){
				if(list[i].common.commonCodeCd=='RANK_03'){
					const dN5 = {
						id: `\${list[i].empCd}`,
						stpid : `\${list[i].dept.deptName}`,
						이름: `\${list[i].empName}`,
						부서명: `\${list[i].dept.deptName} \${list[i].common.commonCodeSj}`,
						직책 : `\${list[i].common.commonCodeSj}`,
						전화번호: `\${list[i].empTelno}`,
						이메일: `\${list[i].empMail}`,
						img: `\${list[i].empProfileImg}`
					};	
					text.push(dN5);
				}else if(list[i].deptCd!='DEPT_01' && list[i].deptCd!='DEPT_010'){
					const dN6 ={
						id: `\${list[i].empCd}`,
						pid:`\${list[i].empSuprr}`,
						이름: `\${list[i].empName}`, 
						부서명: `\${list[i].dept.deptName} \${list[i].common.commonCodeSj}`, 
						직책 : `\${list[i].common.commonCodeSj}`,
						전화번호: `\${list[i].empTelno}`,
						이메일: `\${list[i].empMail}`,
						img: `\${list[i].empProfileImg}`
					};
					text.push(dN6);
				};
				
//				console.log(text);
			};
			
			
			
		chart.load(text);
		
		function preview() {
			console.log("preview")
		    OrgChart.pdfPrevUI.show(chart, {
		        format: 'A4'
		    });
		}

		function nodePdfPreview(nodeId) {
			console.log("nodePdfPreview")
		    OrgChart.pdfPrevUI.show(chart, {
		        format: 'A4',
		        nodeId: nodeId
		    });
		}

		function addSharholder(nodeId) {
			console.log("addSharholder")
		    chart.addNode({ id: OrgChart.randomId(), pid: nodeId, tags: ["menu-without-add"] });
		}

		function addAssistant(nodeId) {
			console.log("addAssistant")
		    var node = chart.getNode(nodeId);
		    var data = { id: OrgChart.randomId(), pid: node.stParent.id, tags: ["assistant"] };
		    chart.addNode(data);
		}


		function addDepartment(nodeId) {
			console.log("addDepartment")
		    var node = chart.getNode(nodeId);
		    var data = { id: OrgChart.randomId(), pid: node.stParent.id, tags: ["department"] };
		    chart.addNode(data);
		}

		function addManager(nodeId) {
			console.log("addManager")
		    chart.addNode({ id: OrgChart.randomId(), stpid: nodeId });
		}
		
	},
	error:function(xhr){
		alert(xhr.status);
	}

})	



$(document).on('click', '.node', function(e) {
	let empCd = $(this).attr('data-n-id');
	console.log("사번 : ", empCd)
	
//	console.log(this)
//	console.log("★★★★★★★★★",e.target);
	
	let tag = $('.boc-edit-form-fields-inner').find('input');
//	console.log(tag)
	for(let i =0; i<tag.length; i++){
		let result = tag[i];
	//	console.log(result)
		//let mail = result.getAttribute("data-binding").value;
	//console.log(mail)
		console.log(result.getAttribute('data-binding'));
		if(result.getAttribute("data-binding") == '이메일'){
			console.log(result.getAttribute("data-binding"))
			console.log(result.value)
			console.log(result)
			result.setAttribute("onmouseover" , "changeColor(this, 'blue', 'pointer')")
			result.setAttribute("onmouseout" , "changeColor(this, '#757575', 'wait')")
			result.setAttribute("onclick" , "emailClick()")
			break;
		}
	//	console.log(result.getAttribute("data-binding"))
	//	console.log(result.getAttribute("data-binding").value)
	//	console.log(result.value)
	}

});

function emailClick(){
	console.log("1")
	location.href="/mail/mailForm";
}

function changeColor(element, color, cursorStyle) {
    element.style.color = color; // 변경하고 싶은 색상으로 수정
    element.style.cursor = cursorStyle;
}
    </script> 