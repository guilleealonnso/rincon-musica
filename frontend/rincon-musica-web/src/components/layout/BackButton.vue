<script setup lang="ts">
import { PhArrowLeft } from '@phosphor-icons/vue'

defineProps<{
  to: string
  label: string
}>()
</script>

<template lang="pug">
RouterLink.back-button(:to="to")
  PhArrowLeft(:size="20" weight="bold")
  span {{ label }}
</template>

<style scoped>
.back-button {
  width: fit-content;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 28px;
  padding: 10px 14px;
  border: 1px solid #333333;
  border-radius: 999px;
  background: #181818;
  color: #ffffff;
  font-size: 14px;
  font-weight: 700;
  transition: background .2s ease, border-color .2s ease, transform .2s ease;
}

.back-button:hover {
  border-color: #ffffff;
  background: #202020;
  transform: translateX(-2px);
}
</style>