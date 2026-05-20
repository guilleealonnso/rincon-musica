<script setup lang="ts">
// @ts-ignore
import { RouterView } from 'vue-router'
// @ts-ignore
import AppHeader from './components/layout/AppHeader.vue'
// @ts-ignore
import AppFooter from './components/layout/AppFooter.vue'
</script>

<template lang="pug">
.app
  AppHeader

  main.app-main
    RouterView

  AppFooter
</template>

<style scoped>
.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.app-main {
  flex: 1;
}
</style>