import axios from 'axios'

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api',
  withCredentials: true,
  headers: { 'Content-Type': 'application/json' }
})

let csrfToken = null

export async function refreshCsrf() {
  const response = await api.get('/auth/csrf')
  csrfToken = response.data.token
  return csrfToken
}

api.interceptors.request.use(async config => {
  const method = (config.method || 'get').toLowerCase()
  if (['post', 'put', 'patch', 'delete'].includes(method)) {
    if (!csrfToken) await refreshCsrf()
    config.headers['X-XSRF-TOKEN'] = csrfToken
  }
  return config
})

export default api
