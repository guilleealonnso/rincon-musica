<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { albumService } from '../services/albumService'
import { cancionService } from '../services/cancionService'
import CancionCard from '../components/canciones/CancionCard.vue'
import ValoracionModal from '../components/valoraciones/ValoracionModal.vue'
import BackButton from '../components/layout/BackButton.vue'
import { useValoracionModal } from '../composables/useValoracionModal'
import type { Album } from '../types/Album'
import type { Cancion } from '../types/Cancion'

const route = useRoute()

const album = ref<Album | null>(null)
const cancionesAlbum = ref<Cancion[]>([])
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

onMounted(async () => {
  try {
    const id = Number(route.params.id)

    album.value = await albumService.obtenerPorId(id)

    const canciones = await cancionService.obtenerTodas()
    cancionesAlbum.value = canciones.filter(
      (cancion) => cancion.album?.id === id,
    )
  } catch {
    error.value = 'No se ha podido cargar el detalle del álbum.'
  } finally {
    cargando.value = false
  }
})
</script>

<template lang="pug">
section.album-detalle-view
  p(v-if="cargando") Cargando álbum...
  p.error(v-else-if="error") {{ error }}

  template(v-else-if="album")
    BackButton(to="/albumes" label="Volver a álbumes")

    .detalle
      img.detalle-img(:src="album.imagenUrl" :alt="album.titulo")

      .detalle-info
        p.detalle-label Álbum
        h1 {{ album.titulo }}
        p.artista {{ album.artista.nombre }}

        .datos
          p
            strong Año:
            |  {{ album.anyo }}

          p
            strong Canciones disponibles:
            |  {{ cancionesAlbum.length }}

    section.canciones-section
      .section-header
        h2 Canciones del álbum
        p {{ cancionesAlbum.length }} canciones

      p.sin-canciones(v-if="cancionesAlbum.length === 0") No hay canciones en el catálogo para este álbum.

      .cards-grid(v-else)
        CancionCard(
          v-for="cancion in cancionesAlbum"
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
.album-detalle-view {
  max-width: 1200px;
  margin: 0 auto;
  padding: 48px 24px;
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

.canciones-section {
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

.section-header p,
.sin-canciones {
  margin: 0;
  color: #b6b6b6;
}

.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 24px;
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