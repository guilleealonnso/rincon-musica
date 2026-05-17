<script setup lang="ts">
import { RouterView } from 'vue-router'
import AppHeader from './components/layout/AppHeader.vue'
import AppFooter from './components/layout/AppFooter.vue'
</script>

<template lang="pug">
.app
  AppHeader

  main.app-main
    RouterView

  AppFooter
</template>