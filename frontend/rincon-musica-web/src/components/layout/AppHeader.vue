

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
<script setup lang="ts">

</script>
<style scoped>
.app-header {
  height: 72px;
  padding: 0 48px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #181818;
  border-bottom: 1px solid #2a2a2a;
}

.logo {
  font-weight: 700;
  font-size: 22px;
}

.nav {
  display: flex;
  gap: 24px;
  font-size: 15px;
}

.nav a {
  color: #cfcfcf;
}

.nav a.router-link-active {
  color: #ffffff;
  font-weight: 700;
}
</style>