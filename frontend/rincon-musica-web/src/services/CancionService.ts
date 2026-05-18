import { useApi } from '../composables/useApi'
import type { Cancion } from '../types/Cancion'

const api = useApi()

export const cancionService = {
  obtenerTodas(): Promise<Cancion[]> {
    return api.get<Cancion[]>('/canciones')
  },

  obtenerPorId(id: number): Promise<Cancion> {
    return api.get<Cancion>(`/canciones/${id}`)
  },

  obtenerPopulares(): Promise<Cancion[]> {
    return api.get<Cancion[]>('/canciones/populares')
  },

  buscarPorTitulo(titulo: string): Promise<Cancion[]> {
    return api.get<Cancion[]>(`/canciones/buscar?titulo=${encodeURIComponent(titulo)}`)
  },

  buscarPorGenero(genero: string): Promise<Cancion[]> {
    return api.get<Cancion[]>(`/canciones/genero/${encodeURIComponent(genero)}`)
  },
}