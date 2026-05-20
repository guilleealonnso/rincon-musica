<script setup lang="ts">
import { ref } from 'vue'
// @ts-ignore
import { PhX } from '@phosphor-icons/vue'

defineProps<{
  tituloCancion: string
  cargando?: boolean
}>()

const emit = defineEmits<{
  cerrar: []
  publicar: [puntuacion: number, comentario: string]
}>()

const puntuacion = ref(0)
const comentario = ref('')
// @ts-ignore
function seleccionarPuntuacion(valor: number) {
  puntuacion.value = valor
}
// @ts-ignore
function publicarValoracion() {
  if (puntuacion.value === 0) {
    return
  }

  emit('publicar', puntuacion.value, comentario.value.trim())
}
</script>

<template lang="pug">
.modal-overlay
  .modal-card
    .modal-header
      div
        p.modal-label Valorar canción
        h2 {{ tituloCancion }}

      button.close-btn(type="button" @click="emit('cerrar')")
        PhX(:size="24" weight="bold")

    .modal-body
      .campo
        label Tu nota

        .estrellas
          button.estrella-btn(
            v-for="numero in 5"
            :key="numero"
            type="button"
            :class="{ activa: numero <= puntuacion }"
            @click="seleccionarPuntuacion(numero)"
          ) ★

      .campo
        label(for="comentario") Reseña opcional
        textarea#comentario(
          v-model="comentario"
          placeholder="Escribe tu opinión..."
          rows="4"
        )

    .modal-actions
      button.cancelar-btn(type="button" @click="emit('cerrar')") Cancelar
      button.publicar-btn(
        type="button"
        :disabled="puntuacion === 0 || cargando"
        @click="publicarValoracion"
      )
        | {{ cargando ? 'Publicando...' : 'Publicar' }}
</template>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  z-index: 1000;
  display: grid;
  place-items: center;
  padding: 24px;
  background: rgba(0, 0, 0, .72);
}

.modal-card {
  width: min(520px, 100%);
  border-radius: 22px;
  background: #181818;
  border: 1px solid #2a2a2a;
  box-shadow: 0 24px 80px rgba(0, 0, 0, .45);
  overflow: hidden;
}

.modal-header {
  padding: 24px 26px;
  display: flex;
  align-items: flex-start;
  justify-content: space-between;
  gap: 18px;
  border-bottom: 1px solid #2a2a2a;
}

.modal-label {
  margin: 0 0 8px;
  color: #b6b6b6;
  font-size: 12px;
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 2px;
}

.modal-header h2 {
  margin: 0;
  color: #ffffff;
  font-size: 28px;
  line-height: 1.1;
}

.close-btn {
  display: grid;
  place-items: center;
  border: 0;
  background: transparent;
  color: #ffffff;
}

.modal-body {
  padding: 26px;
  display: grid;
  gap: 24px;
}

.campo {
  display: grid;
  gap: 12px;
}

.campo label {
  color: #d0d0d0;
  font-size: 14px;
  font-weight: 700;
}

.estrellas {
  display: flex;
  gap: 6px;
}

.estrella-btn {
  border: 0;
  background: transparent;
  color: #3a3a3a;
  font-size: 38px;
  line-height: 1;
  transition: color .15s ease, transform .15s ease;
}

.estrella-btn:hover,
.estrella-btn.activa {
  color: #ffffff;
  transform: scale(1.05);
}

textarea {
  width: 100%;
  min-height: 120px;
  resize: vertical;
  padding: 16px;
  border: 1px solid #333333;
  border-radius: 14px;
  background: #111111;
  color: #ffffff;
  font-size: 15px;
  outline: none;
}

textarea:focus {
  border-color: #ffffff;
}

textarea::placeholder {
  color: #777777;
}

.modal-actions {
  padding: 0 26px 26px;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 14px;
}

.cancelar-btn,
.publicar-btn {
  height: 48px;
  border-radius: 999px;
  font-weight: 800;
}

.cancelar-btn {
  border: 1px solid #555555;
  background: transparent;
  color: #ffffff;
}

.cancelar-btn:hover {
  border-color: #ffffff;
}

.publicar-btn {
  border: 1px solid #ffffff;
  background: #ffffff;
  color: #111111;
}

.publicar-btn:disabled {
  opacity: .5;
  cursor: not-allowed;
}

@media (max-width: 600px) {
  .modal-card {
    width: 100%;
  }

  .modal-header h2 {
    font-size: 24px;
  }

  .estrella-btn {
    font-size: 34px;
  }

  .modal-actions {
    grid-template-columns: 1fr;
  }
}
</style>