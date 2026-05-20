import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { valoracionService } from '../services/valoracionService'
import type { Cancion } from '../types/Cancion'
import type { Usuario } from '../types/Usuario'

export function useValoracionModal(onValoracionPublicada?: () => void | Promise<void>) {
  const router = useRouter()

  const cancionSeleccionada = ref<Cancion | null>(null)
  const mostrarModalValoracion = ref(false)
  const publicandoValoracion = ref(false)
  const errorValoracion = ref('')

  function abrirModalValoracion(cancion: Cancion) {
    const usuarioGuardado = localStorage.getItem('usuario')

    if (!usuarioGuardado) {
      router.push('/login')
      return
    }

    cancionSeleccionada.value = cancion
    mostrarModalValoracion.value = true
    errorValoracion.value = ''
  }

  function cerrarModalValoracion() {
    mostrarModalValoracion.value = false
    cancionSeleccionada.value = null
  }

  async function publicarValoracion(puntuacion: number, comentario: string) {
    if (!cancionSeleccionada.value) {
      return
    }

    const usuarioGuardado = localStorage.getItem('usuario')

    if (!usuarioGuardado) {
      router.push('/login')
      return
    }

    const usuario = JSON.parse(usuarioGuardado) as Usuario

    try {
      publicandoValoracion.value = true
      errorValoracion.value = ''

      await valoracionService.crear({
        usuarioId: usuario.id,
        cancionId: cancionSeleccionada.value.id,
        puntuacion,
        comentario,
      })

      if (onValoracionPublicada) {
        await onValoracionPublicada()
      }

      cerrarModalValoracion()
    } catch {
      errorValoracion.value = 'No se ha podido publicar la valoración.'
    } finally {
      publicandoValoracion.value = false
    }
  }

  return {
    cancionSeleccionada,
    mostrarModalValoracion,
    publicandoValoracion,
    errorValoracion,
    abrirModalValoracion,
    cerrarModalValoracion,
    publicarValoracion,
  }
}