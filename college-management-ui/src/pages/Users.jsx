import { useEffect,useState } from 'react'
import api from '../services/api'
export default function Users(){
 const [users,setUsers]=useState([]);const [error,setError]=useState('')
 useEffect(()=>{api.get('/admin/users').then(r=>setUsers(r.data)).catch(e=>setError(e.response?.data?.error||'Unable to load users'))},[])
 return <div><h2>Manage Users</h2>{error&&<div className="alert alert-warning">{error}</div>}
 <div className="table-responsive bg-white rounded p-3"><table className="table"><thead><tr><th>Name</th><th>Email</th><th>Role</th><th>Status</th></tr></thead><tbody>{users.map(u=><tr key={u.id}><td>{u.fullName}</td><td>{u.email}</td><td>{u.role}</td><td>{u.status}</td></tr>)}</tbody></table></div></div>
}
