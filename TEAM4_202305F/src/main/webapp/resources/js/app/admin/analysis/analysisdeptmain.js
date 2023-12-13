function formatNumber(value) {
    
    return `${Math.floor(value)} 명`;
}

const options = {
    container: document.getElementById('deptChart'), 
    data: getData(),
    title: {
        text: '부서별 인원',
    },
    
    series: [
        {
            type: 'bar',
            xKey: 'dept',
            yKey: 'memC',
            label: { 
                formatter: ({ value }) => formatNumber(value),
            },
            tooltip: {
                renderer: ({ datum, xKey, yKey }) => {
                    return { title: datum[xKey], content: formatNumber(datum[yKey]) };
                },
            },
        },
    ] 
};

const chart = agCharts.AgCharts.create(options);


