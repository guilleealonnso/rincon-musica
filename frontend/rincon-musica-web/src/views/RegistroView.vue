<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { usuarioService } from '../services/usuarioService'

const router = useRouter()

const username = ref('')
const email = ref('')
const password = ref('')
const confirmarPassword = ref('')

const error = ref('')
const cargando = ref(false)

async function registrarse() {
  error.value = ''

  if (!username.value.trim() || !email.value.trim() || !password.value.trim()) {
    error.value = 'Completa todos los campos.'
    return
  }

  if (password.value !== confirmarPassword.value) {
    error.value = 'Las contraseñas no coinciden.'
    return
  }

  try {
    cargando.value = true

    const usuario = await usuarioService.registrar({
      username: username.value.trim(),
      email: email.value.trim(),
      password: password.value,
    })

    localStorage.setItem('usuario', JSON.stringify(usuario))
    router.push('/')
  } catch {
    error.value = 'No se ha podido crear la cuenta.'
  } finally {
    cargando.value = false
  }
}
</script>

<template lang="pug">
section.auth-view
  .auth-card
    h1 Registrarse
    p.auth-text Crea una cuenta para poder valorar canciones y guardar tu actividad.

    form.auth-form(@submit.prevent="registrarse")
      .campo
        label(for="username") Nombre de usuario
        input#username(
          v-model="username"
          type="text"
          placeholder="Tu nombre de usuario"
        )

      .campo
        label(for="email") Email
        input#email(
          v-model="email"
          type="email"
          placeholder="tu@email.com"
        )

      .campo
        label(for="password") Contraseña
        input#password(
          v-model="password"
          type="password"
          placeholder="Contraseña"
        )

      .campo
        label(for="confirmarPassword") Confirmar contraseña
        input#confirmarPassword(
          v-model="confirmarPassword"
          type="password"
          placeholder="Repite la contraseña"
        )

      p.error(v-if="error") {{ error }}

      button.auth-btn(type="submit" :disabled="cargando")
        | {{ cargando ? 'Creando cuenta...' : 'Registrarse' }}

    p.auth-link
      | ¿Ya tienes cuenta?
      RouterLink(to="/login")  Inicia sesión
</template>

<style scoped>
.auth-view {
  max-width: 520px;
  margin: 0 auto;
  padding: 64px 24px;
}

.auth-card {
  padding: 34px;
  border-radius: 24px;
  background: #181818;
  border: 1px solid #2a2a2a;
}

.auth-label {
  margin: 0 0 12px;
  color: #b6b6b6;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-size: 13px;
}

.auth-card h1 {
  margin: 0;
  font-size: 42px;
}

.auth-text {
  margin: 14px 0 28px;
  color: #cfcfcf;
  line-height: 1.5;
}

.auth-form {
  display: grid;
  gap: 18px;
}

.campo {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.campo label {
  color: #d0d0d0;
  font-size: 14px;
}

.campo input {
  height: 46px;
  padding: 0 14px;
  border: 1px solid #333333;
  border-radius: 12px;
  background: #111111;
  color: #ffffff;
  outline: none;
}

.campo input:focus {
  border-color: #ffffff;
}

.auth-btn {
  height: 46px;
  margin-top: 8px;
  border: 1px solid #ffffff;
  border-radius: 999px;
  background: #ffffff;
  color: #111111;
  font-weight: 700;
}

.auth-btn:disabled {
  opacity: .6;
  cursor: not-allowed;
}

.auth-link {
  margin: 24px 0 0;
  color: #b6b6b6;
  text-align: center;
}

.auth-link a {
  color: #ffffff;
  font-weight: 700;
}

.error {
  margin: 0;
  color: #ff8a8a;
}
</style>