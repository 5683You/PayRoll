<template>
  <div>
    <h1>工资核算结果</h1>
    <div v-if="salaryRecords.length === 0">
      <p>该月无数据</p>
    </div>
    <table v-else>
      <thead>
      <tr>
        <th>姓名</th>
        <th>技工类型</th>
        <th>小时数</th>
        <th>基数</th>
        <th>总工资</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="record in salaryRecords" :key="record.id">
        <td>{{ record.name }}</td>
        <td>{{ record.timingType ? record.timingType.type : 'N/A' }}</td>
        <td>{{ record.totalHours }}</td>
        <td>{{ record.timingType ? record.timingType.rate : 'N/A' }}</td>
        <td>{{ record.totalSalary }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      salaryRecords: []
    };
  },
  created() {
    const storedRecords = localStorage.getItem('salaryRecords');
    if (storedRecords) {
      this.salaryRecords = JSON.parse(storedRecords);
    }
  }
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
  text-align: left;
}
</style>
