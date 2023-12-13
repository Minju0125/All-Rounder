const options = {
    container: document.getElementById('myChart'),
    title: {
        text: "참여자 진행도",
    },
   
    data: getData(),
    series: [
        {
            type: 'bar',
            xKey: 'quarter',
            yKey: '진행',
            yName: '진행',
            stacked: true,
			fill: 'rgb(231, 231, 255)', 
        },
        {
            type: 'bar',
            xKey: 'quarter',
            yKey: '요청',
            yName: '요청',
            stacked: true,
			fill: 'rgb(232, 250, 223)',
        },
        {
            type: 'bar',
            xKey: 'quarter',
            yKey: '피드백',
            yName: '피드백',
            stacked: true,
			fill: 'rgb(255, 224, 219)',
        },
        {
            type: 'bar',
            xKey: 'quarter',
            yKey: '보류',
            yName: '보류',
            stacked: true,
			fill: 'rgb(255, 242, 214)',
        },
        {
            type: 'bar',
            xKey: 'quarter',
            yKey: '완료',
            yName: '완료',
            stacked: true, 
			fill: 'rgb(215, 245, 252)',
        },
    ],
};

const chartAll = agCharts.AgCharts.create(options);


