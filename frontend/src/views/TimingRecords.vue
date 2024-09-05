<template>
  <div>
    <h1>人员记时</h1>
    <input v-model="searchName" placeholder="按姓名搜索">
    <button @click="fetchTimingRecords">搜索</button>
    <button @click="showSalaryModal = true" class="calculate-salary-button">核算工资</button>
    <table>
      <thead>
      <tr>
        <th>姓名</th>
        <th>小时数</th>
        <th>计时类型</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="record in timingRecords" :key="record.id">
        <td>{{ record.name }}</td>
        <td>{{ record.hours }}</td>
        <td>{{ record.timingType.type }}</td>
        <td>
          <button @click="editRecord(record)">编辑</button>
          <button @click="confirmDelete(record.id)">删除</button>
        </td>
      </tr>
      </tbody>
    </table>
    <button @click="showModal = true">添加</button>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showModal = false">×</span>
        <form @submit.prevent="addRecord">
          <div class="form-group">
            <label for="name">姓名</label>
            <input id="name" v-model="newRecord.name" placeholder="姓名" required>
          </div>
          <div class="form-group">
            <label for="hours">小时数</label>
            <input id="hours" v-model="newRecord.hours" placeholder="小时数" required>
          </div>
          <div class="form-group">
            <label for="timingType">计时类型</label>
            <select id="timingType" v-model="newRecord.timingType.id" required>
              <option v-for="type in enabledTimingTypes" :value="type.id">{{ type.type }}</option>
            </select>
          </div>
          <button type="submit">添加</button>
        </form>
      </div>
    </div>

    <div v-if="showEditModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showEditModal = false">×</span>
        <form @submit.prevent="updateRecord">
          <div class="form-group">
            <label for="editName">姓名</label>
            <input id="editName" v-model="editRecordData.name" placeholder="姓名" required>
          </div>
          <div class="form-group">
            <label for="editHours">小时数</label>
            <input id="editHours" v-model="editRecordData.hours" placeholder="小时数" required>
          </div>
          <div class="form-group">
            <label for="editTimingType">计时类型</label>
            <select id="editTimingType" v-model="editRecordData.timingType.id" required>
              <option v-for="type in enabledTimingTypes" :value="type.id">{{ type.type }}</option>
            </select>
          </div>
          <button type="submit">更新</button>
        </form>
      </div>
    </div>

    <div v-if="showSalaryModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showSalaryModal = false">×</span>
        <form @submit.prevent="calculateSalary">
          <div class="form-group">
            <label for="month">选择月份</label>
            <input type="month" id="month" v-model="selectedMonth" required>
          </div>
          <button type="submit">计算工资</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      timingRecords: [],
      timingTypes: [],
      searchName: '',
      newRecord: {
        name: '',
        hours: '',
        timingType: {
          id: ''
        }
      },
      editRecordData: {
        id: '',
        name: '',
        hours: '',
        timingType: {
          id: ''
        }
      },
      selectedMonth: '',
      salaryRecords: [],
      showModal: false,
      showEditModal: false,
      showSalaryModal: false
    };
  },
  created() {
    this.fetchTimingRecords();
    this.fetchTimingTypes();
  },
  computed: {
    enabledTimingTypes() {
      return this.timingTypes.filter(type => type.enabled);
    }
  },
  methods: {
    fetchTimingRecords() {
      let url = 'http://localhost:8080/api/timing-records';
      if (this.searchName) {
        url += `?name=${this.searchName}`;
      }
      axios.get(url)
          .then(response => {
            this.timingRecords = response.data;
          });
    },
    fetchTimingTypes() {
      axios.get('http://localhost:8080/api/timing-types')
          .then(response => {
            this.timingTypes = response.data;
          });
    },
    addRecord() {
      if (!this.newRecord.name || !this.newRecord.hours || !this.newRecord.timingType.id) {
        alert('所有字段均不能为空');
        return;
      }
      axios.post('http://localhost:8080/api/timing-records', this.newRecord)
          .then(() => {
            this.fetchTimingRecords();
            this.newRecord = { name: '', hours: '', timingType: { id: '' } };
            this.showModal = false;
          });
    },
    editRecord(record) {
      this.editRecordData = { ...record, timingType: { id: record.timingType.id } };
      this.showEditModal = true;
    },
    updateRecord() {
      if (!this.editRecordData.name || !this.editRecordData.hours || !this.editRecordData.timingType.id) {
        alert('所有字段均不能为空');
        return;
      }
      axios.put(`http://localhost:8080/api/timing-records/${this.editRecordData.id}`, this.editRecordData)
          .then(() => {
            this.fetchTimingRecords();
            this.showEditModal = false;
          });
    },
    confirmDelete(id) {
      if (confirm('确定要删除这条记录吗？')) {
        this.deleteRecord(id);
      }
    },
    deleteRecord(id) {
      axios.delete(`http://localhost:8080/api/timing-records/${id}`)
          .then(() => {
            this.fetchTimingRecords();
          });
    },
    calculateSalary() {
      if (!this.selectedMonth) {
        alert('请选择月份');
        return;
      }
      axios.post('http://localhost:8080/api/salary-calculations', null, { params: { month: this.selectedMonth } })
          .then(response => {
            this.salaryRecords = response.data;
            this.showSalaryModal = false;
            localStorage.setItem('salaryRecords', JSON.stringify(this.salaryRecords)); // 存储到 localStorage
            this.$router.push({ name: 'SalaryPage' });
          })
          .catch(error => {
            console.error("计算工资时出错！", error);
          });
    }
  }
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f2f2f2;
  text-align: left;
}

button {
  margin-top: 10px;
}

.calculate-salary-button {
  margin-bottom: 20px;
  background-color: #4CAF50;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
}

.calculate-salary-button:hover {
  background-color: #45a049;
}

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgb(0,0,0);
  background-color: rgba(0,0,0,0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
</style>
