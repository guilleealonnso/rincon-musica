import { createRouter, createWebHistory } from 'vue-router'

import HomeView from '../views/HomeView.vue'
import ExplorarView from '../views/ExplorarView.vue'
import CancionesView from '../views/CancionesView.vue'
import CancionDetalleView from '../views/CancionDetalleView.vue'
import AlbumesView from '../views/AlbumesView.vue'
import AlbumDetalleView from '../views/AlbumDetalleView.vue'
import ArtistasView from '../views/ArtistasView.vue'
import ArtistaDetalleView from '../views/ArtistaDetalleView.vue'
import LoginView from '../views/LoginView.vue'
import RegistroView from '../views/RegistroView.vue'
import PerfilView from '../views/PerfilView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/explorar',
      name: 'explorar',
      component: ExplorarView,
    },
    {
      path: '/canciones',
      name: 'canciones',
      component: CancionesView,
    },
    {
      path: '/canciones/:id',
      name: 'cancion-detalle',
      component: CancionDetalleView,
    },
    {
      path: '/albumes',
      name: 'albumes',
      component: AlbumesView,
    },
    {
      path: '/albumes/:id',
      name: 'album-detalle',
      component: AlbumDetalleView,
    },
    {
      path: '/artistas',
      name: 'artistas',
      component: ArtistasView,
    },
    {
      path: '/artistas/:id',
      name: 'artista-detalle',
      component: ArtistaDetalleView,
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/registro',
      name: 'registro',
      component: RegistroView,
    },
    {
      path: '/perfil',
      name: 'perfil',
      component: PerfilView,
    },
  ],
})

export default router