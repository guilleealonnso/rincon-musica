import { useApi } from '../composables/useApi'
import type { Artista } from '../types/Artista'

const api = useApi()

export const artistaService = {
  obtenerTodos(): Promise<Artista[]> {
    return api.get<Artista[]>('/artistas')
  },

  obtenerPorId(id: number): Promise<Artista> {
    return api.get<Artista>(`/artistas/${id}`)
  },

  buscarPorNombre(nombre: string): Promise<Artista[]> {
    return api.get<Artista[]>(`/artistas/buscar?nombre=${encodeURIComponent(nombre)}`)
  },
}