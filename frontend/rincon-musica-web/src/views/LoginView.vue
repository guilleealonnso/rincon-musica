<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { usuarioService } from '../services/usuarioService'

const router = useRouter()

const email = ref('')
const password = ref('')

const error = ref('')
const cargando = ref(false)
// @ts-ignore
async function iniciarSesion() {
  error.value = ''

  if (!email.value.trim() || !password.value.trim()) {
    error.value = 'Completa todos los campos.'
    return
  }

  try {
    cargando.value = true

    const usuario = await usuarioService.login({
      email: email.value.trim(),
      password: password.value,
    })

    if (!usuario) {
      error.value = 'Email o contraseña incorrectos.'
      return
    }

    localStorage.setItem('usuario', JSON.stringify(usuario))
    router.push('/')
  } catch {
    error.value = 'No se ha podido iniciar sesión.'
  } finally {
    cargando.value = false
  }
}
</script>

<template lang="pug">
section.login-view
  .login-image-card
    img.login-image(src="/assets/login-bg.jpeg" alt="Look mom I can fly")

  .login-content
    .login-card
      p.auth-label Acceso
      h1 Iniciar sesión
      p.auth-text Entra con tu cuenta para valorar canciones y consultar tu perfil.

      form.auth-form(@submit.prevent="iniciarSesion")
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

        p.error(v-if="error") {{ error }}

        button.auth-btn(type="submit" :disabled="cargando")
          | {{ cargando ? 'Entrando...' : 'Iniciar sesión' }}

      p.auth-link
        | ¿No tienes cuenta?
        RouterLink(to="/registro")  Regístrate
</template>

<style scoped>
.login-view {
  min-height: calc(100vh - 72px);
  max-width: 1200px;
  margin: 0 auto;
  padding: 64px 24px;
  display: grid;
  grid-template-columns: 1fr 390px;
  align-items: center;
  gap: 70px;
}

.login-image-card {
  justify-self: center;
  width: min(420px, 100%);
  padding: 14px;
  border-radius: 28px;
  background: #181818;
  border: 1px solid #2a2a2a;
  box-shadow: 0 24px 80px rgba(0, 0, 0, .35);
}

.login-image {
  width: 100%;
  aspect-ratio: 1 / 1;
  display: block;
  object-fit: cover;
  border-radius: 20px;
}

.login-content {
  width: 100%;
}

.login-card {
  padding: 26px;
  border-radius: 22px;
  background: #181818;
  border: 1px solid #2a2a2a;
  box-shadow: 0 24px 80px rgba(0, 0, 0, .28);
}

.auth-label {
  margin: 0 0 10px;
  color: #b6b6b6;
  text-transform: uppercase;
  letter-spacing: 2px;
  font-size: 12px;
  font-weight: 700;
}

.login-card h1 {
  margin: 0;
  font-size: 34px;
}

.auth-text {
  margin: 12px 0 22px;
  color: #cfcfcf;
  line-height: 1.5;
  font-size: 15px;
}

.auth-form {
  display: grid;
  gap: 14px;
}

.campo {
  display: flex;
  flex-direction: column;
  gap: 7px;
}

.campo label {
  color: #d0d0d0;
  font-size: 14px;
}

.campo input {
  height: 42px;
  padding: 0 13px;
  border: 1px solid #333333;
  border-radius: 12px;
  background: #0d0d0d;
  color: #ffffff;
  outline: none;
}

.campo input:focus {
  border-color: #ffffff;
}

.auth-btn {
  height: 42px;
  margin-top: 6px;
  border: 1px solid #ffffff;
  border-radius: 999px;
  background: #ffffff;
  color: #111111;
  font-weight: 800;
}

.auth-btn:disabled {
  opacity: .6;
  cursor: not-allowed;
}

.auth-link {
  margin: 18px 0 0;
  color: #b6b6b6;
  text-align: center;
  font-size: 14px;
}

.auth-link a {
  color: #ffffff;
  font-weight: 800;
}

.error {
  margin: 0;
  color: #ff8a8a;
  font-size: 14px;
}

@media (max-width: 850px) {
  .login-view {
    grid-template-columns: 1fr;
    gap: 32px;
    padding: 42px 24px;
  }

  .login-image-card {
    width: min(320px, 100%);
  }
}
</style>