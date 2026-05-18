import type { Cancion } from './Cancion'
import type { Usuario } from './Usuario'

export interface Valoracion {
  id: number
  puntuacion: number
  comentario: string
  fecha: string
  usuario: Usuario
  cancion: Cancion
}

export interface ValoracionRequest {
  usuarioId: number
  cancionId: number
  puntuacion: number
  comentario: string
}