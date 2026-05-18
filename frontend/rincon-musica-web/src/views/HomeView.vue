<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { RouterLink } from 'vue-router'
import CancionCard from '../components/canciones/CancionCard.vue'
import { cancionService } from '../services/cancionService'
import type { Cancion } from '../types/Cancion'

const cancionesPopulares = ref<Cancion[]>([])
const cargando = ref(true)
const error = ref('')

onMounted(async () => {
  try {
    cancionesPopulares.value = await cancionService.obtenerPopulares()
  } catch {
    error.value = 'No se han podido cargar las canciones populares.'
  } finally {
    cargando.value = false
  }
})
</script>

<template lang="pug">
section.home
  .hero
    .hero-content
      p.hero-label Comunidad musical
      h1 El Rincón de la Música
      p.hero-text Un lugar libre para opinar, valorar y descubrir canciones, álbumes y artistas.

      .hero-actions
        RouterLink.primary-btn(to="/explorar") Explorar música
        RouterLink.secondary-btn(to="/canciones") Ver canciones

  section.home-section
    .section-header
      div
        p.section-label Descubre
        h2 Canciones populares
      RouterLink.section-link(to="/canciones") Ver todas

    p(v-if="cargando") Cargando canciones...
    p.error(v-else-if="error") {{ error }}

    .cards-grid(v-else)
      CancionCard(
        v-for="cancion in cancionesPopulares"
        :key="cancion.id"
        :cancion="cancion"
      )
</template>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
  padding: 48px 24px;
}

.hero {
  min-height: 360px;
  display: flex;
  align-items: center;
  padding: 56px;
  border-radius: 28px;
  background:
    linear-gradient(135deg, rgba(255, 255, 255, .10), rgba(255, 255, 255, .02)),
    #181818;
  border: 1px solid #2a2a2a;
}

.hero-content {
  max-width: 650px;
}

.hero-label,
.section-label {
  margin: 0 0 12px;
  color: #b6b6b6;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-size: 13px;
}

.hero h1 {
  margin: 0;
  font-size: 56px;
  line-height: 1;
}

.hero-text {
  max-width: 560px;
  margin: 24px 0 0;
  color: #d6d6d6;
  font-size: 18px;
  line-height: 1.6;
}

.hero-actions {
  display: flex;
  gap: 14px;
  margin-top: 32px;
}

.primary-btn,
.secondary-btn {
  padding: 12px 18px;
  border-radius: 999px;
  border: 1px solid #ffffff;
  font-weight: 700;
}

.primary-btn {
  background: #ffffff;
  color: #111111;
}

.secondary-btn {
  background: transparent;
  color: #ffffff;
}

.home-section {
  margin-top: 56px;
}

.section-header {
  display: flex;
  align-items: end;
  justify-content: space-between;
  margin-bottom: 24px;
}

.section-header h2 {
  margin: 0;
  font-size: 32px;
}

.section-link {
  color: #cfcfcf;
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