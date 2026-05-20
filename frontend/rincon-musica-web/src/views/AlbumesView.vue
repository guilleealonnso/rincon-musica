<script setup lang="ts">
import { onMounted, ref } from 'vue'
import AlbumCard from '../components/albumes/AlbumCard.vue'
import { albumService } from '../services/albumService'
import type { Album } from '../types/Album'

const albumes = ref<Album[]>([])
const cargando = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    albumes.value = await albumService.obtenerTodos()
  } catch {
    error.value = 'No se han podido cargar los álbumes.'
  } finally {
    cargando.value = false
  }
})
</script>

<template lang="pug">
section.albumes-view
  .view-header
    p.view-label Catálogo
    h1 Álbumes
    p.view-description Explora los álbumes disponibles en la plataforma y accede a su información detallada.

  p(v-if="cargando") Cargando álbumes...
  p.error(v-else-if="error") {{ error }}

  .cards-grid(v-else)
    AlbumCard(
      v-for="album in albumes"
      :key="album.id"
      :album="album"
    )
</template>

<style scoped>
.albumes-view {
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
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 24px;
}

.error {
  color: #ff8a8a;
}
</style>