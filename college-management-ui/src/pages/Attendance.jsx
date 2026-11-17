import { useEffect,useState } from 'react'
import api from '../services/api'
import { useAuth } from '../context/AuthContext'
export default function Attendance(){
 const {user}=useAuth();const [items,setItems]=useState([]);const [error,setError]=useState('')
 useEffect(()=>{const url=user?.role==='FACULTY'?'/faculty/attendance/self':'/student/attendance';api.get(url).then(r=>setItems(r.data)).catch(e=>setError(e.response?.data?.error||'Unable to load attendance'))},[user])
 return <div><h2>My Attendance</h2><p className="text-secondary">Saturday and Sunday are non-working days.</p>{error&&<div className="alert alert-warning">{error}</div>}
 <div className="table-responsive bg-white rounded p-3"><table className="table"><thead><tr><th>Date</th><th>Status</th></tr></thead><tbody>{items.map(x=><tr key={x.id}><td>{x.attendanceDate}</td><td>{x.status}</td></tr>)}</tbody></table></div></div>
}
