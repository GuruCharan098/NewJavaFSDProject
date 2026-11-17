import { createContext, useContext, useEffect, useState } from 'react'
import api, { refreshCsrf } from '../services/api'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  async function loadUser() {
    try {
      await refreshCsrf()
      const response = await api.get('/auth/me')
      setUser(response.data)
    } catch {
      setUser(null)
    } finally {
      setLoading(false)
    }
  }

  async function login(email, password) {
    await refreshCsrf()
    const response = await api.post('/auth/login', { email, password })
    setUser(response.data)
    await refreshCsrf()
    return response.data
  }

  async function register(payload) {
    await refreshCsrf()
    return api.post('/auth/register', payload)
  }

  async function logout() {
    await refreshCsrf()
    await api.post('/auth/logout')
    setUser(null)
    await refreshCsrf()
  }

  useEffect(() => { loadUser() }, [])

  return (
    <AuthContext.Provider value={{ user, loading, login, register, logout, reloadUser: loadUser }}>
      {children}
    </AuthContext.Provider>
  )
}

export const useAuth = () => useContext(AuthContext)
