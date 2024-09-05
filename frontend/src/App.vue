<template>
  <div id="app">
    <div class="sidebar">
      <nav>

        <router-link to="/timing-types">计时类型</router-link>
        <router-link to="/timing-records">人员记时</router-link>
      </nav>
    </div>
    <div class="content">
      <router-view/>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
};
</script>

<style>
#app {
  display: flex;
}

.sidebar {
  width: 200px;
  background-color: #f8f9fa;
  padding: 20px;
}

.sidebar nav a {
  display: block;
  margin: 10px 0;
  text-decoration: none;
  color: #333;
}

.sidebar nav a.router-link-exact-active {
  font-weight: bold;
}

.content {
  flex: 1;
  padding: 20px;
}
</style>
