import { useApi } from "../composables/useApi";
import type { Valoracion, ValoracionRequest } from "../types/Valoracion";

const api = useApi();

export const valoracionService = {
  obtenerPorCancion(cancionId: number): Promise<Valoracion[]> {
    return api.get<Valoracion[]>(`/valoraciones/cancion/${cancionId}`);
  },
  
  obtenerPorUsuario(usuarioId: number): Promise<Valoracion[]> {
    return api.get<Valoracion[]>(`/valoraciones/usuario/${usuarioId}`);
  },

  obtenerMediaPorCancion(cancionId: number): Promise<number> {
    return api.get<number>(`/valoraciones/cancion/${cancionId}/media`);
  },

  crear(valoracion: ValoracionRequest): Promise<Valoracion> {
    return api.post<Valoracion>("/valoraciones", valoracion);
  },
};
