const API_URL = 'http://localhost:8080/api'

export function useApi() {
  async function get<T>(ruta: string): Promise<T> {
    const respuesta = await fetch(`${API_URL}${ruta}`)

    if (!respuesta.ok) {
      throw new Error('Error al obtener datos')
    }

    return respuesta.json()
  }

  async function post<T>(ruta: string, datos: unknown): Promise<T> {
    const respuesta = await fetch(`${API_URL}${ruta}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(datos),
    })

    if (!respuesta.ok) {
      throw new Error('Error al enviar datos')
    }

    return respuesta.json()
  }

  return {
    get,
    post,
  }
}