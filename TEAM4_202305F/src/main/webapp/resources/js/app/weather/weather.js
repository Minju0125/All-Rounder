/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 13.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 13. 김보영        최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 

/*날씨api*/
const xhr = new XMLHttpRequest();
const apiURI ="https://api.openweathermap.org/data/2.5/weather?lat=36.3226&lon=127.405922222222&appid=a31e895730e8dba45e1d6612f6602b7a&units=metric";
xhr.open('GET', apiURI, true); // 비동기적으로 요청을 열기
xhr.onload = function() {
    if (xhr.status === 200) {
        const resp = JSON.parse(xhr.responseText); // 성공적으로 데이터를 가져왔을 때 수행할 동작
        console.log(resp);
        console.log("현재온도 : "+ (resp.main.temp) );
        console.log("현재습도 : "+ resp.main.humidity);
        console.log("날씨 : "+ resp.weather[0].main );
        console.log("상세날씨설명 : "+ resp.weather[0].description );
        console.log("날씨 이미지 : "+ resp.weather[0].icon );
        console.log("바람   : "+ resp.wind.speed );
        console.log("나라   : "+ resp.sys.country );
        console.log("도시이름  : "+ resp.name );
        console.log("구름  : "+ (resp.clouds.all) +"%" );                 
		document.getElementById("weather").innerHTML = resp.name +","+resp.sys.country+"<br>";
		document.getElementById("ondo").innerHTML += "　" + resp.main.temp+"°C<br>";
//		document.getElementById("weather").innerHTML += resp.weather[0].description +"<br>";
//		document.getElementById("weather").innerHTML += "바람 : " + resp.wind.speed+"<br>";
//		document.getElementById("weather").innerHTML += "날씨이미지 추후예정";
		$("#pnIcon").html("　<img src='http://openweathermap.org/img/w/"+resp.weather[0].icon+".png' style='width:50px;'/>");
    } else {
        console.log('요청에 문제가 발생했습니다.');
    }
};
xhr.onerror = function() {
    console.log('네트워크 에러가 발생했습니다.');
};
xhr.send(); // 요청 보내기


/*
$.ajax({
	    url: apiURI,
	    //dataType: "json",
	    //type: "GET",
	    async: "false",
	    success: function(resp) {
	        console.log(resp);
	        console.log("현재온도 : "+ (resp.main.temp) );
	        console.log("현재습도 : "+ resp.main.humidity);
	        console.log("날씨 : "+ resp.weather[0].main );
	        console.log("상세날씨설명 : "+ resp.weather[0].description );
	        console.log("날씨 이미지 : "+ resp.weather[0].icon );
	        console.log("바람   : "+ resp.wind.speed );
	        console.log("나라   : "+ resp.sys.country );
	        console.log("도시이름  : "+ "대전 오류동" );
	        console.log("구름  : "+ (resp.clouds.all) +"%" );                 
			document.getElementById("weatherLoc").innerHTML = "대전 오류동";
			document.getElementById("ddddd").innerHTML += "현재온도 : " + resp.main.temp+"도<br>";
			document.getElementById("weather").innerHTML += resp.weather[0].description +"<br>";
			document.getElementById("weather").innerHTML += "바람 : " + resp.wind.speed+"<br>";
			document.getElementById("weather").innerHTML += "날씨이미지 추후예정";
	    }
	})
*/
/*
<p>var imgURL = "http://openweathermap.org/img/w/" + resp.weather[0].icon + ".png";
 $("html컴포넌트").attr("src", imgURL);
</p>
*/



