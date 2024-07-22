<template>
    <div class="view-container">
        <lay-row :space="30">
            <lay-col :md=8>
                <lay-panel shadow="hover">
                    <div ref="pieChart" style="width:100%;height:30vh"></div>
                </lay-panel>
            </lay-col>
            <lay-col :md="8">
                <lay-panel shadow="hover">
                    <div ref="radarChart" style="width:100%;height:30vh"></div>
                </lay-panel>
            </lay-col>
            <lay-col :md="8">
                <lay-panel shadow="hover">
                    <div ref="barChart" style="width:100%;height:30vh"></div>
                </lay-panel>
            </lay-col>
            <lay-col :md="24">
                <lay-panel shadow="hover">
                    <div ref="lineChart" style="width:100%;height:30vh"></div>
                </lay-panel>
            </lay-col>
        </lay-row>

    </div>
</template>

<script>
import * as echarts from 'echarts';
import {onMounted} from 'vue';

export default {
    setup() {
        const pieChart = ref();
        const lineChart = ref();
        const radarChart = ref();
        const barChart = ref();

        function initPieChart() {
            const pie = echarts.init(pieChart.value);
            const option = {
                grid: {
                    top: '10%',
                    bottom: '10%'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 20,
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: [
                            {value: 1048, name: 'TestA'},
                            {value: 735, name: 'TestB'},
                            {value: 580, name: 'TestC'},
                        ]
                    }
                ]
            };
            pie.setOption(option)
        }

        function initLineChart() {
            const line = echarts.init(lineChart.value);
            const option = {
                title: {
                    text: 'ExampleLineOption'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['ExampleA', 'ExampleB', 'ExampleC', 'ExampleD', 'ExampleE']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['ExampleX1', 'ExampleX2', 'ExampleX3', 'ExampleX4', 'ExampleX5', 'ExampleX6', 'ExampleX7']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: 'ExampleA',
                        type: 'line',
                        stack: 'Total',
                        data: [120, 132, 101, 134, 90, 230, 210]
                    },
                    {
                        name: 'ExampleB',
                        type: 'line',
                        stack: 'Total',
                        data: [220, 182, 191, 234, 290, 330, 310]
                    },
                    {
                        name: 'ExampleC',
                        type: 'line',
                        stack: 'Total',
                        data: [150, 232, 201, 154, 190, 330, 410]
                    },
                    {
                        name: 'ExampleD',
                        type: 'line',
                        stack: 'Total',
                        data: [320, 332, 301, 334, 390, 330, 320]
                    },
                    {
                        name: 'ExampleE',
                        type: 'line',
                        stack: 'Total',
                        data: [820, 932, 901, 934, 1290, 1330, 1320]
                    }
                ]
            };
            line.setOption(option)
        }

        function initRadarChart(){
            const radar = echarts.init(radarChart.value)
            const option = {
                radar: {
                    indicator: [
                        { name: '数据1', max: 6500 },
                        { name: '数据2', max: 16000 },
                        { name: '数据3', max: 30000 },
                        { name: '数据4', max: 38000 },
                        { name: '数据5', max: 52000 },
                        { name: '数据6', max: 25000 }
                    ]
                },
                series: [
                    {
                        type: 'radar',
                        data: [
                            {
                                value: [4200, 3000, 20000, 35000, 50000, 18000],
                                name: '小明'
                            },
                            {
                                value: [5000, 14000, 28000, 26000, 42000, 21000],
                                name: '李华'
                            }
                        ]
                    }
                ]
            };
            radar.setOption(option)
        }

        function initBarChart(){
            const bar = echarts.init(barChart.value)
            const option = {
                title: {
                    text: 'Bar Chart'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                legend: {},
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value',
                    boundaryGap: [0, 0.01]
                },
                yAxis: {
                    type: 'category',
                    data: ['语文', '数学', '英语', '物理', '化学', '生物']
                },
                series: [
                    {
                        name: '上次',
                        type: 'bar',
                        data: [98, 80, 70, 85, 88, 92]
                    },
                    {
                        name: '本次',
                        type: 'bar',
                        data: [90, 88, 75, 82, 85, 89]
                    }
                ]
            };
            bar.setOption(option)
        }

        onMounted(() => {
            initLineChart()
            initPieChart()
            initRadarChart()
            initBarChart()
        })
        return {
            pieChart,
            lineChart,
            radarChart,
            barChart
        }
    }
}
</script>