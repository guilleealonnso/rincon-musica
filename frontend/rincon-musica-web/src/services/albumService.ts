import { useApi } from '../composables/useApi'
import type { Album } from '../types/Album'

const api = useApi()

export const albumService = {
  obtenerTodos(): Promise<Album[]> {
    return api.get<Album[]>('/albumes')
  },

  obtenerPorId(id: number): Promise<Album> {
    return api.get<Album>(`/albumes/${id}`)
  },

  buscarPorTitulo(titulo: string): Promise<Album[]> {
    return api.get<Album[]>(`/albumes/buscar?titulo=${encodeURIComponent(titulo)}`)
  },
}