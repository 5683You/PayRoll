<template>
  <div>
    <h1>计时类型</h1>
    <table>
      <thead>
      <tr>
        <th>类型</th>
        <th>基数</th>
        <th>启用</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="type in timingTypes" :key="type.id">
        <td>{{ type.type }}</td>
        <td>{{ type.rate }}</td>
        <td>{{ type.enabled ? '是' : '否' }}</td>
        <td>
          <button @click="toggleEnabled(type)">{{ type.enabled ? '禁用' : '启用' }}</button>
        </td>
      </tr>
      </tbody>
    </table>
    <button @click="showModal = true">添加</button>

    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <span class="close" @click="showModal = false">×</span>
        <form @submit.prevent="addType">
          <div class="form-group">
            <label for="type">类型</label>
            <input id="type" v-model="newType.type" placeholder="类型" required>
          </div>
          <div class="form-group">
            <label for="rate">基数</label>
            <input id="rate" v-model="newType.rate" placeholder="基数" required>
          </div>
          <button type="submit">添加</button>
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
      timingTypes: [],
      newType: {
        type: '',
        rate: '',
        enabled: true
      },
      showModal: false
    };
  },
  created() {
    this.fetchTimingTypes();
  },
  methods: {
    fetchTimingTypes() {
      axios.get('http://localhost:8080/api/timing-types')
          .then(response => {
            this.timingTypes = response.data;
          });
    },
    addType() {
      if (!this.newType.type || !this.newType.rate) {
        alert('所有字段均不能为空');
        return;
      }
      axios.post('http://localhost:8080/api/timing-types', this.newType)
          .then(() => {
            this.fetchTimingTypes();
            this.newType = { type: '', rate: '', enabled: true };
            this.showModal = false;
          });
    },
    toggleEnabled(type) {
      const updatedType = { ...type, enabled: !type.enabled };
      axios.put(`http://localhost:8080/api/timing-types/${type.id}`, updatedType)
          .then(() => {
            this.fetchTimingTypes();
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

.form-group input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
</style>
