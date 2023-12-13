const data = getData1();
const numFormatter = new Intl.NumberFormat('en-US'); 
const total = data.reduce((sum, d) => sum + d['count'], 0);

const options1 = { 
    container: document.getElementById('rankChart'),  
    data,
    title: {
        text: '사원 집계',
    },
    series: [
        {
            type: 'pie',
            calloutLabelKey: 'type',
            angleKey: 'count',
            sectorLabelKey: 'count',
            calloutLabel: {
                enabled: false,
            },
            sectorLabel: {
                formatter: ({ datum, sectorLabelKey }) => {
                    const value = datum[sectorLabelKey];
                    return numFormatter.format(value);
                },
            },
            //title: {
                //text: ' ',
            // },
            innerRadiusRatio: 0.7,
            innerLabels: [
                {
                    text: numFormatter.format(total),
                    fontSize: 24,
                },
                {
                    text: 'Total',
                    fontSize: 16,
                    margin: 10,
                },
            ],
            tooltip: {
                renderer: ({ datum, calloutLabelKey, title, sectorLabelKey }) => {
                    return {
                        title,
                        content: `${datum[calloutLabelKey]}: ${numFormatter.format(datum[sectorLabelKey])}`,
                    };
                },
            },
            strokeWidth: 3,
        },
    ],
};

const chart1 = agCharts.AgCharts.create(options1);


