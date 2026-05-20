<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { valoracionService } from '../services/valoracionService'
import type { Usuario } from '../types/Usuario'
import type { Valoracion } from '../types/Valoracion'

const router = useRouter()

const usuario = ref<Usuario | null>(null)
const valoraciones = ref<Valoracion[]>([])
const cargandoValoraciones = ref(false)
const error = ref('')

onMounted(async () => {
  const usuarioGuardado = localStorage.getItem('usuario')

  if (!usuarioGuardado) {
    router.push('/login')
    return
  }

  usuario.value = JSON.parse(usuarioGuardado)

  await cargarValoraciones()
})

async function cargarValoraciones() {
  if (!usuario.value) {
    return
  }

  try {
    cargandoValoraciones.value = true
    valoraciones.value = await valoracionService.obtenerPorUsuario(usuario.value.id)
  } catch {
    error.value = 'No se han podido cargar tus valoraciones.'
  } finally {
    cargandoValoraciones.value = false
  }
}

function cerrarSesion() {
  localStorage.removeItem('usuario')
  router.push('/')
}

function irCancion(cancionId: number) {
  router.push(`/canciones/${cancionId}`)
}
</script>

<template lang="pug">
section.perfil-view
  template(v-if="usuario")
    .perfil-card
      .perfil-header
        .avatar
          span {{ usuario.username.charAt(0).toUpperCase() }}

        .perfil-info
          p.perfil-label Perfil de usuario
          h1 {{ usuario.username }}
          p {{ usuario.email }}

      .datos  
        p
          strong Fecha de registro:
          |  {{ usuario.fechaRegistro }}

      .acciones
        button.cerrar-btn(type="button" @click="cerrarSesion") Cerrar sesión

    section.valoraciones-section
      .section-header
        div
          h2 Mis valoraciones
          p Consulta las canciones que has valorado.

        span.total {{ valoraciones.length }} valoraciones

      p(v-if="cargandoValoraciones") Cargando valoraciones...
      p.error(v-else-if="error") {{ error }}
      p.sin-valoraciones(v-else-if="valoraciones.length === 0") Todavía no has valorado ninguna canción.

      .valoraciones-list(v-else)
        article.valoracion-card(
          v-for="valoracion in valoraciones"
          :key="valoracion.id"
        )
          img.valoracion-img(
            :src="valoracion.cancion.imagenUrl"
            :alt="valoracion.cancion.titulo"
          )

          .valoracion-info
            .valoracion-header
              div
                h3 {{ valoracion.cancion.titulo }}
                p {{ valoracion.cancion.artista.nombre }}

              span.puntuacion {{ valoracion.puntuacion }}/5

            p.comentario {{ valoracion.comentario || 'Sin comentario.' }}

            button.ver-cancion-btn(
              type="button"
              @click="irCancion(valoracion.cancion.id)"
            ) Ver canción
</template>

<style scoped>
.perfil-view {
  max-width: 980px;
  margin: 0 auto;
  padding: 56px 24px;
}

.perfil-card {
  padding: 32px;
  border-radius: 24px;
  background: #181818;
  border: 1px solid #2a2a2a;
}

.perfil-header {
  display: flex;
  align-items: center;
  gap: 24px;
}

.avatar {
  width: 96px;
  height: 96px;
  display: grid;
  place-items: center;
  border-radius: 50%;
  background: #ffffff;
  color: #111111;
  font-size: 42px;
  font-weight: 800;
}

.perfil-label {
  margin: 0 0 10px;
  color: #b6b6b6;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-size: 13px;
}

.perfil-info h1 {
  margin: 0;
  font-size: 42px;
}

.perfil-info p:last-child {
  margin: 10px 0 0;
  color: #cfcfcf;
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

.acciones {
  margin-top: 30px;
}

.cerrar-btn {
  padding: 12px 18px;
  border: 1px solid #ffffff;
  border-radius: 999px;
  background: transparent;
  color: #ffffff;
  font-weight: 700;
}

.cerrar-btn:hover {
  background: #ffffff;
  color: #111111;
}

.valoraciones-section {
  margin-top: 42px;
  padding: 28px;
  border-radius: 24px;
  background: #181818;
  border: 1px solid #2a2a2a;
}

.section-header {
  display: flex;
  justify-content: space-between;
  gap: 24px;
  margin-bottom: 24px;
}

.section-header h2 {
  margin: 0;
  font-size: 30px;
}

.section-header p,
.sin-valoraciones {
  margin: 8px 0 0;
  color: #b6b6b6;
}

.total {
  color: #b6b6b6;
  white-space: nowrap;
}

.valoraciones-list {
  display: grid;
  gap: 16px;
}

.valoracion-card {
  display: grid;
  grid-template-columns: 92px 1fr;
  gap: 18px;
  padding: 16px;
  border-radius: 18px;
  background: #111111;
  border: 1px solid #2a2a2a;
}

.valoracion-img {
  width: 92px;
  height: 92px;
  object-fit: cover;
  border-radius: 14px;
  background: #252525;
}

.valoracion-info {
  min-width: 0;
}

.valoracion-header {
  display: flex;
  justify-content: space-between;
  gap: 18px;
}

.valoracion-header h3 {
  margin: 0;
  font-size: 20px;
}

.valoracion-header p {
  margin: 6px 0 0;
  color: #b6b6b6;
}

.puntuacion {
  font-weight: 800;
  color: #ffffff;
  white-space: nowrap;
}

.comentario {
  margin: 14px 0 0;
  color: #d0d0d0;
  line-height: 1.5;
}

.ver-cancion-btn {
  margin-top: 16px;
  padding: 9px 14px;
  border: 1px solid #555555;
  border-radius: 999px;
  background: transparent;
  color: #ffffff;
  font-weight: 700;
}

.ver-cancion-btn:hover {
  border-color: #ffffff;
}

.error {
  color: #ff8a8a;
}

@media (max-width: 700px) {
  .perfil-header {
    align-items: flex-start;
    flex-direction: column;
  }

  .section-header {
    flex-direction: column;
  }

  .valoracion-card {
    grid-template-columns: 1fr;
  }

  .valoracion-img {
    width: 100%;
    height: auto;
    aspect-ratio: 1 / 1;
  }
}
</style>