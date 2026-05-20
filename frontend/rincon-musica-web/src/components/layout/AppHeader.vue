<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { PhUser } from '@phosphor-icons/vue'
import type { Usuario } from '../../types/Usuario'

const router = useRouter()

const usuario = ref<Usuario | null>(obtenerUsuario())

function obtenerUsuario(): Usuario | null {
  const usuarioGuardado = localStorage.getItem('usuario')

  if (!usuarioGuardado) {
    return null
  }

  return JSON.parse(usuarioGuardado)
}

function irPerfil() {
  router.push('/perfil')
}

function cerrarSesion() {
  localStorage.removeItem('usuario')
  usuario.value = null
  router.push('/')
}
</script>

<template lang="pug">
header.app-header
  RouterLink.logo(to="/") El Rincón de la Música

  nav.nav
    RouterLink(to="/") Inicio
    RouterLink(to="/explorar") Explorar
    RouterLink(to="/canciones") Canciones
    RouterLink(to="/albumes") Álbumes
    RouterLink(to="/artistas") Artistas

  .user-zone(v-if="usuario")
    button.profile-btn(type="button" @click="irPerfil" title="Ver perfil")
      PhUser(:size="22" weight="bold")

    button.logout-btn(type="button" @click="cerrarSesion") Salir

  .auth-links(v-else)
    RouterLink(to="/login") Iniciar sesión
    RouterLink.registro-link(to="/registro") Registrarse
</template>

<style scoped>
.app-header {
  height: 72px;
  padding: 0 48px;
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  align-items: center;
  background: #111111;
  border-bottom: 1px solid #252525;
}

.logo {
  color: #f5f5f5;
  font-weight: 800;
  font-size: 22px;
  letter-spacing: .3px;
}

.nav {
  display: flex;
  justify-content: center;
  gap: 24px;
  font-size: 15px;
}

.nav a,
.auth-links a {
  color: #b8b8b8;
  transition: color .2s ease;
}

.nav a:hover,
.auth-links a:hover {
  color: #ffffff;
}

.nav a.router-link-active {
  color: #d6ff3f;
  font-weight: 800;
}

.auth-links,
.user-zone {
  justify-self: end;
  display: flex;
  align-items: center;
  gap: 14px;
}

.registro-link {
  padding: 9px 14px;
  border: 1px solid #d6ff3f;
  border-radius: 999px;
  color: #111111 !important;
  background: #d6ff3f;
  font-weight: 800;
}

.registro-link:hover {
  background: #ffffff;
  border-color: #ffffff;
}

.profile-btn {
  width: 42px;
  height: 42px;
  display: grid;
  place-items: center;
  border: 1px solid #d6ff3f;
  border-radius: 50%;
  background: #d6ff3f;
  color: #111111;
  transition: transform .2s ease, background .2s ease;
}

.profile-btn:hover {
  transform: translateY(-1px);
  background: #ffffff;
}

.logout-btn {
  padding: 9px 14px;
  border: 1px solid #3a3a3a;
  border-radius: 999px;
  background: transparent;
  color: #f5f5f5;
  font-weight: 700;
}

.logout-btn:hover {
  border-color: #d6ff3f;
  color: #d6ff3f;
}

@media (max-width: 900px) {
  .app-header {
    height: auto;
    padding: 18px 24px;
    grid-template-columns: 1fr;
    gap: 18px;
  }

  .nav {
    flex-wrap: wrap;
    justify-content: flex-start;
  }

  .auth-links,
  .user-zone {
    justify-self: start;
  }
}
</style>