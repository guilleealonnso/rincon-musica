<script setup lang="ts">
import type { Cancion } from '../../types/Cancion'

defineProps<{
  cancion: Cancion
}>()
</script>

<template lang="pug">
article.cancion-card
  RouterLink.cancion-link(:to="`/canciones/${cancion.id}`")
    img.cancion-img(:src="cancion.imagenUrl" :alt="cancion.titulo")

    .cancion-info
      h3 {{ cancion.titulo }}
      p.artista {{ cancion.artista.nombre }}
      p.detalle {{ cancion.genero }} · {{ cancion.anyo }}
      p.popularidad Popularidad: {{ cancion.popularidad }}

  button.valorar-btn(type="button") Valorar
</template>

<style scoped>
.cancion-card {
  padding: 14px;
  border-radius: 20px;
  background: #181818;
  border: 1px solid #2a2a2a;
  transition: transform .2s ease, background .2s ease;
}

.cancion-card:hover {
  transform: translateY(-4px);
  background: #202020;
}

.cancion-link {
  display: block;
}

.cancion-img {
  width: 100%;
  aspect-ratio: 1 / 1;
  object-fit: cover;
  border-radius: 16px;
  background: #333333;
}

.cancion-info {
  padding: 14px 4px 10px;
}

.cancion-info h3 {
  margin: 0 0 8px;
  font-size: 18px;
}

.artista,
.detalle,
.popularidad {
  margin: 5px 0;
  color: #bdbdbd;
  font-size: 14px;
}

.valorar-btn {
  width: 100%;
  margin-top: 8px;
  padding: 12px 18px;
  border-radius: 999px;
  border: 1px solid #ffffff;
  background: transparent;
  color: #ffffff;
  font-weight: 700;
}

.valorar-btn:hover {
  background: #ffffff;
  color: #111111;
}
</style>