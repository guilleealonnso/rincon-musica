import type { Album } from './Album'
import type { Artista } from './Artista'

export interface Cancion {
  id: number
  titulo: string
  anyo: number
  duracion: number
  genero: string
  popularidad: number
  imagenUrl: string
  idSpotify: string | null
  ultAct: string | null
  artista: Artista
  album: Album
}