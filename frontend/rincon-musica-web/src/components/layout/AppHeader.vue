<script setup lang="ts">
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
    RouterLink(to="/login") Iniciar sesión
</template>