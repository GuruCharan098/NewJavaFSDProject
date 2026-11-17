import { useState } from 'react'
import { Link, useNavigate, useSearchParams } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Login() {
  const { login } = useAuth()
  const navigate = useNavigate()
  const [params] = useSearchParams()
  const [email,setEmail]=useState('')
  const [password,setPassword]=useState('')
  const [error,setError]=useState('')
  const [busy,setBusy]=useState(false)

  async function submit(e) {
    e.preventDefault(); setError(''); setBusy(true)
    try {
      const user = await login(email,password)
      navigate(user.role === 'ADMIN' ? '/admin' : user.role === 'FACULTY' ? '/faculty' : '/student')
    } catch (err) {
      setError(err.response?.data?.error || 'Login failed. Check your email and password.')
    } finally { setBusy(false) }
  }

  const backend = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'
  return (
    <div className="container min-vh-100 d-flex align-items-center justify-content-center">
      <div className="card page-card p-4 p-md-5" style={{maxWidth:460,width:'100%'}}>
        <div className="text-center mb-4">
          <div className="display-6">🎓</div>
          <h2 className="brand text-primary">College CMS</h2>
          <p className="text-secondary">Sign in to your college account</p>
        </div>
        {params.get('google') === 'pending' && <div className="alert alert-info">Google account created. Please contact the administrator for activation.</div>}
        {params.get('google') === 'link-required' && <div className="alert alert-warning">An account with this email already exists. Sign in with your existing method before account linking.</div>}
        {error && <div className="alert alert-danger">{error}</div>}
        <form onSubmit={submit}>
          <label className="form-label">Email address</label>
          <input className="form-control mb-3" type="email" required value={email} onChange={e=>setEmail(e.target.value)} />
          <label className="form-label">Password</label>
          <input className="form-control mb-3" type="password" required value={password} onChange={e=>setPassword(e.target.value)} />
          <button className="btn btn-primary w-100" disabled={busy}>{busy?'Signing in...':'Login'}</button>
        </form>
        <div className="text-center my-3 text-secondary">or</div>
        <a className="btn btn-outline-dark w-100" href={`${backend}/oauth2/authorization/google`}>Continue with Google</a>
        <p className="text-center mt-4 mb-0">New student? <Link to="/register">Create account</Link></p>
      </div>
    </div>
  )
}
