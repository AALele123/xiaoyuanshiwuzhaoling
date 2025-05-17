<template>
  <div class="content">
    <div style="width: 40%;height: 10%">
      <div class="text main-text">欢迎使用 {{this.$project.projectName}}</div>
    </div>
    <!-- 添加图表容器 -->
    <div style="width: 100%; height: 80%; display: flex; margin-top: 20px;" v-if="showCharts">
      <div style="width: 50%; height: 100%; padding: 10px;">
        <div class="chart-title"></div>
        <div id="statistic1" style="width:100%;height:90%;"></div>
      </div>
      <div style="width: 50%; height: 100%; padding: 10px;">
        <div class="chart-title">认领状态统计</div>
        <div id="statistic2" style="width:100%;height:90%;"></div>
      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router/router-static'
import * as echarts from 'echarts'
import 'echarts/theme/macarons' // 引入主题

export default {
  data() {
    return {
      sessionTable: "", // 登录账户所在表名
      role: "", // 权限
      userId: "", // 当前登录人的id
      chart1: null, // 图表1实例
      chart2: null, // 图表2实例
      showCharts: true // 控制图表显示
    };
  },
  mounted() {
    // 获取当前登录用户的信息
    this.sessionTable = this.$storage.get("sessionTable");// 获取用户所在表名
    this.role = this.$storage.get("role");
    this.userId = this.$storage.get("userId");
    // 初始化图表（创建 ECharts 实例并绑定到 DOM 元素）
    this.initCharts();
    // 加载图表数据（从后端接口获取数据并渲染图表）
    this.loadChartData();
  },
  beforeDestroy() {
    // 组件销毁前执行（页面关闭或路由跳转时调用）
    // 销毁图表实例，释放内存和事件监听
    if (this.chart1) {
      this.chart1.dispose(); // 销毁第一个图表实例
    }
    if (this.chart2) {
      this.chart2.dispose(); // 销毁第二个图表实例
    }
  },
  methods: {
    // 1.初始化图表
    initCharts() {
      this.$nextTick(() => {
        // 使用 this.$nextTick 确保代码在 DOM 更新后执行
        // 初始化图表1 - 失物类型分布
        this.chart1 = echarts.init(document.getElementById("statistic1"), 'macarons');
        // 初始化图表2 - 认领状态统计
        this.chart2 = echarts.init(document.getElementById("statistic2"), 'macarons');

        // 添加窗口大小变化监听
        window.addEventListener('resize', this.handleResize);
      });
    },

    // 2.加载图表数据
    loadChartData() {
      // 获取失物类型统计数据
      this.$http({
        url: "shiwuzhaoling/groupByType",
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          const typeData = data.data.map(item => ({
            name: item.name, // 假设返回数据中有name字段表示类型名称
            value: item.value // 假设返回数据中有value字段表示数量
          }));
          this.setPieChart(this.chart1, '失物类型分布', typeData);
        }
      });

      // 获取认领状态统计数据
      this.$http({
        url: "shiwuzhaoling/groupByStatus", // 请求后端接口地址，获取按认领状态分组的数据
        method: "get"
      }).then(({ data }) => {
        if (data && data.code === 0) {
          // 遍历后端返回的数据数组将后端返回的数据映射为 ECharts 需要的格式
          const statusData = data.data.map(item => ({
            name: item.name, // 认领状态名称
            value: item.value // 各状态数量
          }));
          // 调用设置饼图的方法，传入图表实例、标题和处理后的数据
          this.setPieChart(this.chart2, '认领状态统计', statusData);
        }
      });
    },

    // 3.响应式调整
    handleResize() {
      if (this.chart1) {
        this.chart1.resize();
      }
      if (this.chart2) {
        this.chart2.resize();
      }
    },

    // 4.图表配置
    setPieChart(chartInstance, title, data) {
      const option = {
        title: {
          text: title,
          left: 'center'
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}: {c} ({d}%)'
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: data.map(item => item.name)
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
              show: true,
              formatter: '{b}: {c} ({d}%)'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '18',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: true
            },
            data: data
          }
        ]
      };

      chartInstance.setOption(option, true);
    },

    init() {
      if (this.$storage.get('Token')) {
        this.$http({
          url: `${this.$storage.get('sessionTable')}/session`,
          method: "get"
        }).then(({ data }) => {
          if (data && data.code != 0) {
            router.push({ name: 'login' })
          }
        });
      } else {
        router.push({ name: 'login' })
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.content {
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
  min-height: 500px;
  text-align: center;

  .main-text {
    font-size: 38px;
    font-weight: bold;
    margin-top: 15%;
  }

  .text {
    font-size: 24px;
    font-weight: bold;
    color: #333;
  }

  .chart-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 10px;
    color: #333;
  }
}
</style>