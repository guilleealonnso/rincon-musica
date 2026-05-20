<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import CancionCard from '../components/canciones/CancionCard.vue'
import ValoracionModal from '../components/valoraciones/ValoracionModal.vue'
import { cancionService } from '../services/cancionService'
import { valoracionService } from '../services/valoracionService'
import type { Cancion } from '../types/Cancion'
import type { Usuario } from '../types/Usuario'

const router = useRouter()

const canciones = ref<Cancion[]>([])
const cargando = ref(true)
const error = ref('')

const cancionSeleccionada = ref<Cancion | null>(null)
const mostrarModalValoracion = ref(false)
const publicandoValoracion = ref(false)

onMounted(async () => {
  try {
    canciones.value = await cancionService.obtenerTodas()
  } catch {
    error.value = 'No se han podido cargar las canciones.'
  } finally {
    cargando.value = false
  }
})

function abrirModalValoracion(cancion: Cancion) {
  const usuarioGuardado = localStorage.getItem('usuario')

  if (!usuarioGuardado) {
    router.push('/login')
    return
  }

  cancionSeleccionada.value = cancion
  mostrarModalValoracion.value = true
}

function cerrarModalValoracion() {
  mostrarModalValoracion.value = false
  cancionSeleccionada.value = null
}

async function publicarValoracion(puntuacion: number, comentario: string) {
  if (!cancionSeleccionada.value) {
    return
  }

  const usuarioGuardado = localStorage.getItem('usuario')

  if (!usuarioGuardado) {
    router.push('/login')
    return
  }

  const usuario = JSON.parse(usuarioGuardado) as Usuario

  try {
    publicandoValoracion.value = true

    await valoracionService.crear({
      usuarioId: usuario.id,
      cancionId: cancionSeleccionada.value.id,
      puntuacion,
      comentario,
    })

    cerrarModalValoracion()
  } catch {
    error.value = 'No se ha podido publicar la valoración.'
  } finally {
    publicandoValoracion.value = false
  }
}
</script>

<template lang="pug">
section.canciones-view
  .view-header
    p.view-label Catálogo
    h1 Canciones
    p.view-description Explora todas las canciones disponibles en la plataforma y accede a su detalle para consultar valoraciones y comentarios.

  p(v-if="cargando") Cargando canciones...
  p.error(v-else-if="error") {{ error }}

  .cards-grid(v-else)
    CancionCard(
      v-for="cancion in canciones"
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
.canciones-view {
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