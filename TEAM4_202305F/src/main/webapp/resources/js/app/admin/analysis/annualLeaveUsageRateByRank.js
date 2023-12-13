/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 12. 6.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 12. 6.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 

let ALURBRlabels=[];
let usevac=[];

function ALURBR(){
	let xhr=new XMLHttpRequest();
	xhr.open("get","/attendance/annualLeaveUsageByRank",true);
	xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
			rslt=JSON.parse(xhr.responseText);
			console.log("여기야 여기!! : ",rslt);
			for(let i=0;i<rslt.length;i++){
				ALURBRlabels.push(rslt[i].COMMON_CODE_SJ)
				usevac.push(rslt[i].USEVAC);
			}
			console.log("labels : ",ALURBRlabels);
			console.log("usevac : ",usevac);

			new Chart(document.getElementById("ALURBRchart"), {
			    type: 'polarArea',
			    data: {
			      labels: ALURBRlabels,
			      datasets: [
			        {
			          label: "Population (millions)",
			          backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#FFEE58"],
			          data: usevac
			        }
			      ]
			    },
			    options: {
			      title: {
			        display: true,
			        text: '직급별 연차(2023) 사용률(%)'
			      }
			    }
			});
		}
	}
	xhr.send();
}

ALURBR();