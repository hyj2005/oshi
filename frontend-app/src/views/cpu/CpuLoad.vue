<template>
  <a-space wrap>
    <!-- 16个cpu cpu-1~16 -->
    <div
      :id="`cpu-${i}`"
      style="width: 180px; height: 180px; border: 1px solid black"
      v-for="i in 16"
    ></div>
  </a-space>
</template>

<script setup>
import * as echarts from "echarts";
import { onMounted } from "vue";
import { getCpuLoadApi } from "@/api/cpuloadApi";
import { ref } from "vue";

const chartDom = ref([]);
onMounted(() => {
  //初始化图表只需要进行一次
  initChart();
  //以后每次画图
  getCpuData();
});
const initChart = () => {
  for (let i = 1; i <=16; i++) {
     var dom = document.getElementById("cpu-" + i);
    //得到一个chart对象
    var myChart = echarts.init(dom);
    chartDom.value.push(myChart);
  } 
}

// 16核 cpu的所有数据
//每一核还是一个数组，这个数组中保存的是每秒的数据
const cpuAllData = ref([]);
const getCpuData = async () => {
  //1、拿到服务器真正的响应
  let resp = await getCpuLoadApi();
  //2、返回的是16核cpu.每个核心当前的负载值
  let data = resp.data;
  for (let i = 0; i < 16; i++) {
    if (!cpuAllData.value[i]) {
      cpuAllData.value[i] = [];
    }
    //把当前请求到的这个cpu的使用率放进自己的数组中的原理
    //每次给里面放之前做一个判断
    if (cpuAllData.value[i].length >= 60) {
      //把最老的一个数据删除，放入一个最新的数据
      let arr =cpuAllData.value[i].slice(-60);//移除了
      arr.push(data[i]);//添加了
      cpuAllData.value[i] = arr;

    }else{
      cpuAllData.value[i].push(data[i]);
    }
    
    //这里会oom，这个数组最多放60个，超过60个删除最老的
    drawCpuLoad(i + 1, cpuAllData.value[i]);
  }

    await  getCpuData();
};

//1、每个图显示CPU名
//2、每个图xy轴不显示
//3、显示为面积图

const drawCpuLoad = (cpuIndex, cpuData) => {
 var myChart = chartDom.value[cpuIndex -1];
  var option;
  // textStyle. fontStyle
  option = {
    title: {
      text: "CPU" + cpuIndex,
      textStyle: { fontStyle: 14 },
    },
    grid: { left: "0", right: "0", top: "0", bottom: "5" },
    xAxis: {
      show: false, // 隐藏x轴
      type: "category",
    },
    yAxis: {
      show: false, // 隐藏y轴
      type: "value",
      min: 0,
      max:1
    },
    series: [
      {
        data: cpuData,
        type: "line",
        symbol: "none",
        smooth: true,
        areaStyle: {},
      },
    ],
  };

  myChart.setOption(option);
};
</script>

<style scoped></style>
