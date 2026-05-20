<script setup lang="ts">
import { ref } from 'vue'// @ts-ignore
import CancionCard from '../components/canciones/CancionCard.vue'
// @ts-ignore
import AlbumCard from '../components/albumes/AlbumCard.vue'
// @ts-ignore
import ArtistaCard from '../components/artistas/ArtistaCard.vue'
// @ts-ignore
import ValoracionModal from '../components/valoraciones/ValoracionModal.vue'
import { useValoracionModal } from '../composables/useValoracionModal'
import { cancionService } from '../services/CancionService'
import { albumService } from '../services/albumService'
import { artistaService } from '../services/artistaService'
import type { Cancion } from '../types/Cancion'
import type { Album } from '../types/Album'
import type { Artista } from '../types/Artista'

type TipoBusqueda = 'canciones' | 'albumes' | 'artistas'

const termino = ref('')
const tipoBusqueda = ref<TipoBusqueda>('canciones')
const genero = ref('')

const canciones = ref<Cancion[]>([])
const albumes = ref<Album[]>([])
const artistas = ref<Artista[]>([])

const cargando = ref(false)
const error = ref('')
const busquedaRealizada = ref(false)
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
const generos = [
  'Rock',
  'Pop',
  'Trap',
  'Reggaeton',
  'R&B',
  'Metal',
  'Electrónica',
]
// @ts-ignore
async function buscar() {
  error.value = ''
  cargando.value = true
  busquedaRealizada.value = true

  canciones.value = []
  albumes.value = []
  artistas.value = []

  try {
    if (tipoBusqueda.value === 'canciones') {
      if (genero.value) {
        canciones.value = await cancionService.buscarPorGenero(genero.value)
      } else if (termino.value.trim()) {
        canciones.value = await cancionService.buscarPorTitulo(termino.value.trim())
      } else {
        canciones.value = await cancionService.obtenerTodas()
      }
    }

    if (tipoBusqueda.value === 'albumes') {
      if (termino.value.trim()) {
        albumes.value = await albumService.buscarPorTitulo(termino.value.trim())
      } else {
        albumes.value = await albumService.obtenerTodos()
      }
    }

    if (tipoBusqueda.value === 'artistas') {
      if (termino.value.trim()) {
        artistas.value = await artistaService.buscarPorNombre(termino.value.trim())
      } else {
        artistas.value = await artistaService.obtenerTodos()
      }
    }
  } catch {
    error.value = 'No se han podido cargar los resultados.'
  } finally {
    cargando.value = false
  }
}
// @ts-ignore
function limpiarFiltros() {
  termino.value = ''
  genero.value = ''
  canciones.value = []
  albumes.value = []
  artistas.value = []
  error.value = ''
  busquedaRealizada.value = false
}
</script>

<template lang="pug">
section.explorar-view
  .view-header
    p.view-label Buscar
    h1 Explorar
    p.view-description Busca contenido dentro de la plataforma.

  .buscador
    .campo
      label(for="termino") Búsqueda
      input#termino(
        v-model="termino"
        type="text"
        placeholder="Buscar por título o nombre..."
        @keyup.enter="buscar"
      )

    .campo
      label(for="tipo") Tipo
      select#tipo(v-model="tipoBusqueda")
        option(value="canciones") Canciones
        option(value="albumes") Álbumes
        option(value="artistas") Artistas

    .campo(v-if="tipoBusqueda === 'canciones'")
      label(for="genero") Género
      select#genero(v-model="genero")
        option(value="") Todos
        option(
          v-for="opcion in generos"
          :key="opcion"
          :value="opcion"
        ) {{ opcion }}

    .acciones
      button.buscar-btn(type="button" @click="buscar") Buscar
      button.limpiar-btn(type="button" @click="limpiarFiltros") Limpiar

  p(v-if="cargando") Cargando resultados...
  p.error(v-else-if="error") {{ error }}

  section.resultados(v-else-if="busquedaRealizada")

    p.sin-resultados(
      v-if="tipoBusqueda === 'canciones' && canciones.length === 0"
    ) No se han encontrado canciones.

    p.sin-resultados(
      v-if="tipoBusqueda === 'albumes' && albumes.length === 0"
    ) No se han encontrado álbumes.

    p.sin-resultados(
      v-if="tipoBusqueda === 'artistas' && artistas.length === 0"
    ) No se han encontrado artistas.

    .cards-grid(v-if="tipoBusqueda === 'canciones' && canciones.length > 0")
      CancionCard(
        v-for="cancion in canciones"
        :key="cancion.id"
        :cancion="cancion"
        @valorar="abrirModalValoracion"
      )

    .cards-grid(v-if="tipoBusqueda === 'albumes' && albumes.length > 0")
      AlbumCard(
        v-for="album in albumes"
        :key="album.id"
        :album="album"
      )

    .cards-grid.artistas-grid(v-if="tipoBusqueda === 'artistas' && artistas.length > 0")
      ArtistaCard(
        v-for="artista in artistas"
        :key="artista.id"
        :artista="artista"
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
.explorar-view {
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

.buscador {
  display: grid;
  grid-template-columns: 1fr 180px 180px auto;
  gap: 18px;
  align-items: end;
  padding: 24px;
  border-radius: 22px;
  background: #181818;
  border: 1px solid #2a2a2a;
  margin-bottom: 36px;
}

.campo {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.campo label {
  color: #b6b6b6;
  font-size: 14px;
}

.campo input,
.campo select {
  height: 44px;
  padding: 0 14px;
  border: 1px solid #333333;
  border-radius: 12px;
  background: #111111;
  color: #ffffff;
  outline: none;
}

.campo input:focus,
.campo select:focus {
  border-color: #ffffff;
}

.acciones {
  display: flex;
  gap: 10px;
}

.buscar-btn,
.limpiar-btn {
  height: 44px;
  padding: 0 18px;
  border-radius: 999px;
  font-weight: 700;
}

.buscar-btn {
  border: 1px solid #ffffff;
  background: #ffffff;
  color: #111111;
}

.limpiar-btn {
  border: 1px solid #555555;
  background: transparent;
  color: #ffffff;
}

.resultados {
  margin-top: 32px;
}

.section-header {
  margin-bottom: 24px;
}

.section-header h2 {
  margin: 0;
  font-size: 32px;
}

.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 24px;
}

.artistas-grid {
  grid-template-columns: repeat(auto-fill, minmax(190px, 1fr));
}

.sin-resultados {
  color: #b6b6b6;
}

.error {
  color: #ff8a8a;
}

@media (max-width: 900px) {
  .buscador {
    grid-template-columns: 1fr;
  }

  .acciones {
    flex-direction: column;
  }
}
</style>