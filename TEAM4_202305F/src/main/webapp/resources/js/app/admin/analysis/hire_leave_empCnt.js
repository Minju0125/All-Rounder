/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 12. 5.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 12. 5.  김보영     입퇴사자,부서별 일 근무시간
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

function formatter2(value) {
    
    return `${value}`;
}

const optionsBY2 = {
    container: document.getElementById('deftWorkTime'),
	data : getBY2Data(),
    autoSize: true,
    title: {
        text: '평균근무시간',
    },
    subtitle: {
        text: '부서별 일 평균 근무시간',
    },
    series: [
        {
            type: 'bar',
            direction: 'horizontal',
            xKey: 'deftName',
            yKey: 'workTime',
            label: {
                formatter: ({ value }) => `${value} 시간`,
            },
            formatter: ({ datum, yKey }) => ({
				fill: getBarColor(datum[yKey]),
                fillOpacity: getOpacity(datum[yKey], yKey, 0.4, 1),
            }),
        },
    ],
    axes: [
        {
            type: 'category',
            position: 'left',
        },
        {
            type: 'number',
            position: 'bottom',
            title: {
                enabled: true,
                text: '평균 근무 시간 (시)',
            },
        },
    ],
};

// 추가된 함수: 데이터에 따라 막대의 색상을 동적으로 결정
function getBarColor(workTime) {
    // 예제: 근무 시간이 8시간 이상인 경우 녹색, 미만인 경우 빨강색 반환
    return workTime >= 8 ? 'green' : 'red';
}

function getOpacity(value, key, minOpacity, maxOpacity) {
    const [min, max] = getDomain(key);
    let alpha = Math.round(((value - min) / (max - min)) * 10) / 10;
    return map2(alpha, 0, 1, minOpacity, maxOpacity);
}

function getDomain(key) {
    const min = Math.min(...deftWorkTime.map((d) => d[key])); // Fix: Use workTimeData instead of data
    const max = Math.max(...deftWorkTime.map((d) => d[key])); // Fix: Use workTimeData instead of data
    return [min, max];
}

const map2 = (value, start1, end1, start2, end2) => {
    return ((value - start1) / (end1 - start1)) * (end2 - start2) + start2;
};

const BY2chart = agCharts.AgCharts.create(optionsBY2);






//------------------------------------------------------------------------------------------------------------------------------
//입사자 퇴사자 

const selectMax = getBYData(); 
const maxLeftAxisValue = Math.max(...selectMax['입사자'].map(item => item.cnt), ...selectMax['퇴사자'].map(item => item.cnt));
const halfMaxLeftAxisValue = maxLeftAxisValue / 2;

function formatter(value) {
    
    return `${value}`;
}

const optionsBY = {
    autoSize: true,
    container: document.getElementById('HLEmpCnt'),
    title: {
        text: '올해 입퇴사자 현황',
    },
    subtitle: {
        text: '(2023년)',
    },
    series: Object.entries(getBYData()).map(([ageGroup, data]) => ({
        data,
        type: 'line',
        xKey: 'year',
        yKey: 'cnt',
        yName: ageGroup,
        label: {
            enabled: true,
            formatter: ({ value }) => formatter(value),
        },
        marker: {
            size: 10,
        },
		tooltip: {
            renderer: ({ datum, xKey, yKey }) => {
                return { title: datum[xKey], content: formatter(datum[yKey])+'명' };
            },
        },
    })),
    axes: [
        {
            position: 'bottom',
            type: 'category',
            label: {
                enabled: false,
            },
			line: {
                enabled: false,
            },
            crosshair: {
                enabled: true,
            },
        },
        {
            position: 'left',
            type: 'number',
            tick: {
                size: 10,
                interval: 5,
            },
			gridLine: {
                enabled: false,
            },	
            line: {
                width: 1,
            },
            label: {
                formatter: ({ value }) => formatter(value),
            },
            crossLines: [
                {
                    type: 'line',
                    value: halfMaxLeftAxisValue,
                    strokeOpacity: 0.5,
                    lineDash: [6, 4],
                    label: {
                        fontSize: 13,
                        padding: 10,
                        position: 'right',
                    },
                },
            ],
        },
    ],
    seriesArea: {
        padding: {
            left: 10,
            bottom: 10,
            top:30,
        },
    },
};

const BYchart = agCharts.AgCharts.create(optionsBY);