<script setup lang="ts">
import type { Artista } from '../../types/Artista'

defineProps<{
  artista: Artista
}>()
</script>

<template lang="pug">
article.artista-card
  RouterLink.artista-link(:to="`/artistas/${artista.id}`")
    img.artista-img(:src="artista.imagenUrl" :alt="artista.nombre")

    .artista-info
      h3 {{ artista.nombre }}
      p Ver perfil del artista
</template>

<style scoped>
.artista-card {
  padding: 14px;
  border-radius: 20px;
  background: #181818;
  border: 1px solid #2a2a2a;
  transition: transform .2s ease, background .2s ease;
}

.artista-card:hover {
  transform: translateY(-4px);
  background: #202020;
}

.artista-link {
  display: block;
}

.artista-img {
  width: 100%;
  aspect-ratio: 1 / 1;
  object-fit: cover;
  border-radius: 50%;
  background: #333333;
}

.artista-info {
  padding: 16px 4px 6px;
  text-align: center;
}

.artista-info h3 {
  margin: 0 0 8px;
  font-size: 18px;
}

.artista-info p {
  margin: 0;
  color: #bdbdbd;
  font-size: 14px;
}
</style>