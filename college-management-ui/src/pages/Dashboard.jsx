import { useEffect, useState } from 'react'
import api from '../services/api'
import { useAuth } from '../context/AuthContext'

export default function Dashboard() {
  const { user } = useAuth();

  const [data, setData] = useState(null); const [error, setError] = useState('')


  useEffect(() => {
    api.get('/dashboard')
      .then(r => setData(r.data))
      .catch(e => setError(e.response?.data?.error || 'Unable to load dashboard'))
  }, [])
  return <div>
    <h2 className="mb-1">{user?.role} Dashboard</h2>
    <p className="text-secondary">Welcome, {user?.fullName}</p>
    {error && <div className="alert alert-warning">{error}</div>}
    <div className="row g-3 mt-2">
      <div className="col-md-4"><div className="card stat-card p-4"><div className="text-secondary">Your role</div><h3>{user?.role}</h3></div></div>
      <div className="col-md-4"><div className="card stat-card p-4"><div className="text-secondary">Account status</div><h3>{user?.status}</h3></div></div>
      <div className="col-md-4"><div className="card stat-card p-4"><div className="text-secondary">Dashboard data</div><h3>{data ? 'Loaded' : 'Loading'}</h3></div></div>
    </div>
    {data && <pre className="bg-white rounded p-3 mt-4">{JSON.stringify(data, null, 2)}</pre>}
  </div>
}
