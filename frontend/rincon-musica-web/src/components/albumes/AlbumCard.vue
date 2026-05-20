<script setup lang="ts">
import type { Album } from '../../types/Album'

defineProps<{
  album: Album
}>()
</script>

<template lang="pug">
article.album-card
  RouterLink.album-link(:to="`/albumes/${album.id}`")
    img.album-img(:src="album.imagenUrl" :alt="album.titulo")

    .album-info
      h3 {{ album.titulo }}
      p.artista {{ album.artista.nombre }}
      p.detalle {{ album.anyo }} · {{ album.numeroCanciones }} canciones
</template>

<style scoped>
.album-card {
  padding: 14px;
  border-radius: 20px;
  background: #181818;
  border: 1px solid #2a2a2a;
  transition: transform .2s ease, background .2s ease;
}

.album-card:hover {
  transform: translateY(-4px);
  background: #202020;
}

.album-link {
  display: block;
}

.album-img {
  width: 100%;
  aspect-ratio: 1 / 1;
  object-fit: cover;
  border-radius: 16px;
  background: #333333;
}

.album-info {
  padding: 14px 4px 6px;
}

.album-info h3 {
  margin: 0 0 8px;
  font-size: 18px;
}

.artista,
.detalle {
  margin: 5px 0;
  color: #bdbdbd;
  font-size: 14px;
}
</style>