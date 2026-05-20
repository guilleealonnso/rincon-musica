<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { artistaService } from '../services/artistaService'
import { albumService } from '../services/albumService'
import { cancionService } from '../services/cancionService'
import AlbumCard from '../components/albumes/AlbumCard.vue'
import CancionCard from '../components/canciones/CancionCard.vue'
import ValoracionModal from '../components/valoraciones/ValoracionModal.vue'
import BackButton from '../components/layout/BackButton.vue'
import { useValoracionModal } from '../composables/useValoracionModal'
import type { Artista } from '../types/Artista'
import type { Album } from '../types/Album'
import type { Cancion } from '../types/Cancion'

const route = useRoute()

const artista = ref<Artista | null>(null)
const albumesArtista = ref<Album[]>([])
const cancionesArtista = ref<Cancion[]>([])

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

    artista.value = await artistaService.obtenerPorId(id)

    const albumes = await albumService.obtenerTodos()
    albumesArtista.value = albumes.filter((album) => album.artista?.id === id)

    const canciones = await cancionService.obtenerTodas()
    cancionesArtista.value = canciones.filter((cancion) => cancion.artista?.id === id)
  } catch {
    error.value = 'No se ha podido cargar el detalle del artista.'
  } finally {
    cargando.value = false
  }
})
</script>

<template lang="pug">
section.artista-detalle-view
  p(v-if="cargando") Cargando artista...
  p.error(v-else-if="error") {{ error }}

  template(v-else-if="artista")
    BackButton(to="/artistas" label="Volver a artistas")

    .detalle
      img.detalle-img(:src="artista.imagenUrl" :alt="artista.nombre")

      .detalle-info
        p.detalle-label Artista
        h1 {{ artista.nombre }}

        .datos
          p
            strong Álbumes registrados:
            |  {{ albumesArtista.length }}
          p
            strong Canciones registradas:
            |  {{ cancionesArtista.length }}

    section.contenido-section
      .section-header
        h2 Álbumes
        p {{ albumesArtista.length }} álbumes

      p.sin-contenido(v-if="albumesArtista.length === 0") No hay álbumes registrados para este artista.

      .cards-grid(v-else)
        AlbumCard(
          v-for="album in albumesArtista"
          :key="album.id"
          :album="album"
        )

    section.contenido-section
      .section-header
        h2 Canciones
        p {{ cancionesArtista.length }} canciones

      p.sin-contenido(v-if="cancionesArtista.length === 0") No hay canciones registradas para este artista.

      .cards-grid(v-else)
        CancionCard(
          v-for="cancion in cancionesArtista"
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
.artista-detalle-view {
  max-width: 1200px;
  margin: 0 auto;
  padding: 48px 24px;
}


.detalle {
  display: grid;
  grid-template-columns: 360px 1fr;
  gap: 42px;
  align-items: center;
}

.detalle-img {
  width: 100%;
  aspect-ratio: 1 / 1;
  object-fit: cover;
  border-radius: 50%;
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
  font-size: 56px;
  line-height: 1;
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

.contenido-section {
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
.sin-contenido {
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
    font-size: 42px;
  }
}
</style>