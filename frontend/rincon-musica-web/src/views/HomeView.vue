<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
// @ts-ignore
import { RouterLink } from 'vue-router'
// @ts-ignore
import CancionCard from '../components/canciones/CancionCard.vue'
// @ts-ignore
import ValoracionModal from '../components/valoraciones/ValoracionModal.vue'
import { useValoracionModal } from '../composables/useValoracionModal'
import { cancionService } from '../services/CancionService'
import type { Cancion } from '../types/Cancion'

const cancionesPopulares = ref<Cancion[]>([])
const cargando = ref(true)
const error = ref('')
// @ts-ignore
const {
  cancionSeleccionada,
  mostrarModalValoracion,
  publicandoValoracion,
  abrirModalValoracion,
  cerrarModalValoracion,
  publicarValoracion,
} = useValoracionModal()
// @ts-ignore
const textoActualizacionPopulares = computed(() => {
  const fechas = cancionesPopulares.value
    .map((cancion) => cancion.ultAct)
    .filter((fecha): fecha is string => Boolean(fecha))

  if (fechas.length === 0) {
    return 'Actualización no disponible'
  }

  const ultimaFecha = fechas.sort(
    (a, b) => new Date(b).getTime() - new Date(a).getTime()
  )[0]

  return obtenerTextoActualizacion(ultimaFecha)
})

onMounted(async () => {
  try {
    cancionesPopulares.value = await cancionService.obtenerPopulares()
  } catch {
    error.value = 'No se han podido cargar las canciones populares.'
  } finally {
    cargando.value = false
  }
})

function obtenerTextoActualizacion(fecha: string | null): string {
  if (!fecha) {
    return 'Actualización no disponible'
  }

  const fechaActualizacion = new Date(fecha)
  const ahora = new Date()

  const diferenciaMs = ahora.getTime() - fechaActualizacion.getTime()
  const diferenciaMinutos = Math.floor(diferenciaMs / 1000 / 60)
  const diferenciaHoras = Math.floor(diferenciaMinutos / 60)
  const diferenciaDias = Math.floor(diferenciaHoras / 24)

  if (diferenciaMinutos < 1) {
    return 'Actualizado hace unos segundos'
  }

  if (diferenciaMinutos < 60) {
    return `Actualizado hace ${diferenciaMinutos} min`
  }

  if (diferenciaHoras < 24) {
    return `Actualizado hace ${diferenciaHoras} h`
  }

  if (diferenciaDias === 1) {
    return 'Actualizado ayer'
  }

  return `Actualizado hace ${diferenciaDias} días`
}
</script>

<template lang="pug">
section.home
  .hero
    .hero-content
      //- p.hero-label Para los que nos gusta la música...
      h1 El Rincón de la Música
      p.hero-text Hecho para gente que sabe de música...

      .hero-actions
        RouterLink.primary-btn(to="/explorar") Explorar música
        RouterLink.secondary-btn(to="/canciones") Ver canciones

    .hero-image-card
      img.hero-image(src="/assets/home-music-answer.jpeg" alt="Music is the answer")

  section.home-section
    .section-header
      div
        p.section-label Descubre
        h2 Canciones populares

      .section-actions
        span.actualizacion {{ textoActualizacionPopulares }}
        RouterLink.section-link(to="/canciones") Ver todas

    p(v-if="cargando") Cargando canciones...
    p.error(v-else-if="error") {{ error }}

    .cards-grid(v-else)
      CancionCard(
        v-for="cancion in cancionesPopulares"
        :key="cancion.id"
        :cancion="cancion"
        @valorar="abrirModalValoracion"
      )

  ValoracionModal(
    v-if="mostrarModalValoracion && cancionSeleccionada"
    :titulo-cancion="cancionSeleccionada.titulo"
    :cargando="publicandoValoracion"
    @cerrar="cerrarModalValoracion"
    @publicar="publicarValoracion"
  )
</template>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
  padding: 60px 24px 48px;
}

.hero {
  min-height: 430px;
  display: grid;
  grid-template-columns: minmax(0, 1.15fr) 360px;
  align-items: center;
  gap: 48px;
  padding: 58px;
  border-radius: 34px;
  background:
    radial-gradient(circle at top left, rgba(255, 255, 255, .12), transparent 34%),
    linear-gradient(135deg, #242424, #151515);
  border: 1px solid #303030;
  box-shadow: 0 24px 80px rgba(0, 0, 0, .32);
  overflow: hidden;
}

.hero-content {
  max-width: 680px;
}

.hero-label,
.section-label {
  margin: 0 0 14px;
  color: #b6b6b6;
  text-transform: uppercase;
  letter-spacing: 2px;
  font-size: 13px;
  font-weight: 700;
}

.hero h1 {
  margin: 0;
  font-size: clamp(48px, 6vw, 76px);
  line-height: .95;
  letter-spacing: -2px;
}

.hero-text {
  max-width: 610px;
  margin: 28px 0 0;
  color: #d6d6d6;
  font-size: 20px;
  line-height: 1.55;
}

.hero-actions {
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
  margin-top: 36px;
}

.primary-btn,
.secondary-btn {
  padding: 13px 22px;
  border-radius: 999px;
  border: 1px solid #ffffff;
  font-weight: 800;
  transition: transform .2s ease, background .2s ease, color .2s ease;
}

.primary-btn {
  background: #ffffff;
  color: #111111;
}

.secondary-btn {
  background: transparent;
  color: #ffffff;
}

.primary-btn:hover,
.secondary-btn:hover {
  transform: translateY(-2px);
}

.secondary-btn:hover {
  background: #ffffff;
  color: #111111;
}

.hero-image-card {
  position: relative;
  padding: 14px;
  border-radius: 28px;
  background: #0b0b0b;
  border: 1px solid #3a3a3a;
  box-shadow: 0 20px 60px rgba(0, 0, 0, .45);
}

.hero-image-card::before {
  content: "";
  position: absolute;
  inset: -1px;
  z-index: 0;
  border-radius: 28px;
  background: linear-gradient(135deg, rgba(255, 255, 255, .28), transparent 38%, rgba(255, 255, 255, .10));
  opacity: .55;
}

.hero-image {
  position: relative;
  z-index: 1;
  width: 100%;
  aspect-ratio: 3 / 4;
  display: block;
  object-fit: cover;
  border-radius: 20px;
  filter: contrast(1.05);
}

.home-section {
  margin-top: 64px;
}

.section-header {
  display: flex;
  align-items: end;
  justify-content: space-between;
  gap: 24px;
  margin-bottom: 24px;
}

.section-header h2 {
  margin: 0;
  font-size: 34px;
}

.section-actions {
  display: flex;
  align-items: center;
  gap: 18px;
}

.actualizacion {
  color: #9b9b9b;
  font-size: 14px;
  white-space: nowrap;
}

.section-link {
  color: #cfcfcf;
  white-space: nowrap;
}

.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 24px;
}

.error {
  color: #ff8a8a;
}

@media (max-width: 950px) {
  .hero {
    grid-template-columns: 1fr;
  }

  .hero-image-card {
    max-width: 360px;
  }
}

@media (max-width: 700px) {
  .home {
    padding-top: 36px;
  }

  .hero {
    padding: 34px 26px;
    border-radius: 26px;
  }

  .hero h1 {
    font-size: 42px;
  }

  .hero-text {
    font-size: 17px;
  }

  .section-header {
    align-items: flex-start;
    flex-direction: column;
  }

  .section-actions {
    align-items: flex-start;
    flex-direction: column;
    gap: 8px;
  }
}
</style>