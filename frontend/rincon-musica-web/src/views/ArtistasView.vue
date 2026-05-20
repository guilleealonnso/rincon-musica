<script setup lang="ts">
import { onMounted, ref } from 'vue'
// @ts-ignore
import ArtistaCard from '../components/artistas/ArtistaCard.vue'
import { artistaService } from '../services/artistaService'
import type { Artista } from '../types/Artista'

const artistas = ref<Artista[]>([])
const cargando = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    artistas.value = await artistaService.obtenerTodos()
  } catch {
    error.value = 'No se han podido cargar los artistas.'
  } finally {
    cargando.value = false
  }
})
</script>

<template lang="pug">
section.artistas-view
  .view-header
    p.view-label Catálogo
    h1 Artistas
    p.view-description Explora los artistas disponibles en la plataforma y accede a su información detallada.

  p(v-if="cargando") Cargando artistas...
  p.error(v-else-if="error") {{ error }}

  .cards-grid(v-else)
    ArtistaCard(
      v-for="artista in artistas"
      :key="artista.id"
      :artista="artista"
    )
</template>

<style scoped>
.artistas-view {
  max-width: 1200px;
  margin: 0 auto;
  padding: 48px 24px;
}

.view-header {
  margin-bottom: 32px;
}

.view-label {
  margin: 0 0 12px;
  color: #b6b6b6;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-size: 13px;
}

.view-header h1 {
  margin: 0;
  font-size: 44px;
}

.view-description {
  max-width: 720px;
  margin: 16px 0 0;
  color: #cfcfcf;
  line-height: 1.6;
}

.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(190px, 1fr));
  gap: 24px;
}

.error {
  color: #ff8a8a;
}
</style>