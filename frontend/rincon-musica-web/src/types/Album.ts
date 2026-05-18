import type { Artista } from './Artista'

export interface Album {
  id: number
  titulo: string
  anyo: number
  imagenUrl: string
  numeroCanciones: number
  artista: Artista
}