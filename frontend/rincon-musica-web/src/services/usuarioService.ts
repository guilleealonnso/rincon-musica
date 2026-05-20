import { useApi } from '../composables/useApi'
import type { Usuario } from '../types/Usuario'

const api = useApi()

interface LoginRequest {
  email: string
  password: string
}

interface RegistroRequest {
  username: string
  email: string
  password: string
}

export const usuarioService = {
  registrar(datos: RegistroRequest): Promise<Usuario> {
    return api.post<Usuario>('/usuarios/registro', datos)
  },

  login(datos: LoginRequest): Promise<Usuario> {
    return api.post<Usuario>('/usuarios/login', datos)
  },

  obtenerPorId(id: number): Promise<Usuario> {
    return api.get<Usuario>(`/usuarios/${id}`)
  },
}