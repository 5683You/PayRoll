
import { createRouter, createWebHistory } from 'vue-router';
import TimingTypes from '../views/TimingTypes.vue';
import TimingRecords from '../views/TimingRecords.vue';
import SalaryPage from '../views/SalaryPage.vue'; // 导入 SalaryPage 组件

const routes = [
    { path: '/', redirect: '/timing-types' },
    { path: '/timing-types', component: TimingTypes },
    { path: '/timing-records', component: TimingRecords },
    { path: '/salary', name: 'SalaryPage', component: SalaryPage, props: route => ({ salaryRecords: route.params.salaryRecords }) } // 添加 SalaryPage 路由
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
