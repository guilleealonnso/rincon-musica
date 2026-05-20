<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { RouterLink } from 'vue-router'
import CancionCard from '../components/canciones/CancionCard.vue'
import ValoracionModal from '../components/valoraciones/ValoracionModal.vue'
import { useValoracionModal } from '../composables/useValoracionModal'
import { cancionService } from '../services/cancionService'
import type { Cancion } from '../types/Cancion'

const cancionesPopulares = ref<Cancion[]>([])
const cargando = ref(true)
const error = ref('')

const {
  cancionSeleccionada,
  mostrarModalValoracion,
  publicandoValoracion,
  abrirModalValoracion,
  cerrarModalValoracion,
  publicarValoracion,
} = useValoracionModal()

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
  gap: 24px;
  margin-bottom: 24px;
}

.section-header h2 {
  margin: 0;
  font-size: 32px;
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

@media (max-width: 700px) {
  .hero {
    padding: 36px 28px;
  }

  .hero h1 {
    font-size: 40px;
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