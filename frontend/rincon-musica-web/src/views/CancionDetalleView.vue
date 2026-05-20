<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { cancionService } from '../services/cancionService'
import { valoracionService } from '../services/valoracionService'
import ValoracionModal from '../components/valoraciones/ValoracionModal.vue'
import type { Cancion } from '../types/Cancion'
import type { Valoracion } from '../types/Valoracion'
import type { Usuario } from '../types/Usuario'

const route = useRoute()
const router = useRouter()

const cancion = ref<Cancion | null>(null)
const valoraciones = ref<Valoracion[]>([])
const media = ref<number | null>(null)

const cargando = ref(true)
const error = ref('')

const mostrarModalValoracion = ref(false)
const publicandoValoracion = ref(false)

onMounted(async () => {
  await cargarDatosCancion()
})

async function cargarDatosCancion() {
  try {
    const id = Number(route.params.id)

    cancion.value = await cancionService.obtenerPorId(id)
    valoraciones.value = await valoracionService.obtenerPorCancion(id)
    media.value = await valoracionService.obtenerMediaPorCancion(id)
  } catch {
    error.value = 'No se ha podido cargar el detalle de la canción.'
  } finally {
    cargando.value = false
  }
}

function abrirModalValoracion() {
  const usuarioGuardado = localStorage.getItem('usuario')

  if (!usuarioGuardado) {
    router.push('/login')
    return
  }

  mostrarModalValoracion.value = true
}

function cerrarModalValoracion() {
  mostrarModalValoracion.value = false
}

async function publicarValoracion(puntuacion: number, comentario: string) {
  if (!cancion.value) {
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
      cancionId: cancion.value.id,
      puntuacion,
      comentario,
    })

    valoraciones.value = await valoracionService.obtenerPorCancion(cancion.value.id)
    media.value = await valoracionService.obtenerMediaPorCancion(cancion.value.id)

    mostrarModalValoracion.value = false
  } catch {
    error.value = 'No se ha podido publicar la valoración.'
  } finally {
    publicandoValoracion.value = false
  }
}
</script>

<template lang="pug">
section.detalle-view
  p(v-if="cargando") Cargando canción...
  p.error(v-else-if="error") {{ error }}

  template(v-else-if="cancion")
    RouterLink.volver(to="/canciones") ← Volver a canciones

    .detalle
      img.detalle-img(:src="cancion.imagenUrl" :alt="cancion.titulo")

      .detalle-info
        p.detalle-label Canción
        h1 {{ cancion.titulo }}

        p.artista {{ cancion.artista.nombre }}

        .datos
          p
            strong Álbum:
            |  {{ cancion.album?.titulo || 'Sin álbum' }}
          p
            strong Género:
            |  {{ cancion.genero }}
          p
            strong Año:
            |  {{ cancion.anyo }}
          p
            strong Popularidad:
            |  {{ cancion.popularidad }}
          p
            strong Media:
            |  {{ media !== null ? media.toFixed(1) : 'Sin valoraciones' }}

        button.valorar-btn(type="button" @click="abrirModalValoracion") Valorar canción

    section.comentarios
      .section-header
        h2 Comentarios
        p {{ valoraciones.length }} valoraciones

      p.sin-comentarios(v-if="valoraciones.length === 0") Todavía no hay comentarios para esta canción.

      article.valoracion(v-for="valoracion in valoraciones" :key="valoracion.id")
        .valoracion-header
          strong {{ valoracion.usuario.username }}
          span {{ valoracion.puntuacion }}/5
        p {{ valoracion.comentario || 'Sin comentario.' }}

    ValoracionModal(
      v-if="mostrarModalValoracion"
      :titulo-cancion="cancion.titulo"
      :cargando="publicandoValoracion"
      @cerrar="cerrarModalValoracion"
      @publicar="publicarValoracion"
    )
</template>

<style scoped>
.detalle-view {
  max-width: 1200px;
  margin: 0 auto;
  padding: 48px 24px;
}

.volver {
  display: inline-block;
  margin-bottom: 28px;
  color: #cfcfcf;
}

.detalle {
  display: grid;
  grid-template-columns: 360px 1fr;
  gap: 42px;
  align-items: start;
}

.detalle-img {
  width: 100%;
  aspect-ratio: 1 / 1;
  object-fit: cover;
  border-radius: 24px;
  background: #252525;
}

.detalle-info {
  padding: 24px 0;
}

.detalle-label {
  margin: 0 0 12px;
  color: #b6b6b6;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-size: 13px;
}

.detalle-info h1 {
  margin: 0;
  font-size: 52px;
  line-height: 1;
}

.artista {
  margin: 18px 0 0;
  color: #d6d6d6;
  font-size: 22px;
}

.datos {
  margin-top: 32px;
  display: grid;
  gap: 12px;
  color: #d0d0d0;
}

.datos p {
  margin: 0;
}

.valorar-btn {
  margin-top: 32px;
  padding: 12px 20px;
  border: 1px solid #ffffff;
  border-radius: 999px;
  background: #ffffff;
  color: #111111;
  font-weight: 700;
}

.comentarios {
  margin-top: 56px;
}

.section-header {
  display: flex;
  align-items: end;
  justify-content: space-between;
  margin-bottom: 20px;
}

.section-header h2 {
  margin: 0;
  font-size: 32px;
}

.section-header p {
  margin: 0;
  color: #b6b6b6;
}

.sin-comentarios {
  color: #b6b6b6;
}

.valoracion {
  padding: 18px;
  margin-bottom: 14px;
  border: 1px solid #2a2a2a;
  border-radius: 18px;
  background: #181818;
}

.valoracion-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.valoracion-header span {
  color: #ffffff;
  font-weight: 700;
}

.valoracion p {
  margin: 0;
  color: #d0d0d0;
}

.error {
  color: #ff8a8a;
}

@media (max-width: 800px) {
  .detalle {
    grid-template-columns: 1fr;
  }

  .detalle-info h1 {
    font-size: 40px;
  }
}
</style>