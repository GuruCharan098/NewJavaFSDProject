import { useEffect,useState } from 'react'
import api from '../services/api'
import { useAuth } from '../context/AuthContext'
export default function MyCourses(){
 const {user}=useAuth();const [items,setItems]=useState([]);const [error,setError]=useState('')
 useEffect(()=>{const url=user?.role==='FACULTY'?'/faculty/courses':'/student/courses';api.get(url).then(r=>setItems(r.data)).catch(e=>setError(e.response?.data?.error||'Unable to load courses'))},[user])
 return <div><h2>My Courses</h2>{error&&<div className="alert alert-warning">{error}</div>}<div className="row g-3">{items.map(x=>{const c=x.course;return <div className="col-md-4" key={x.id}><div className="card page-card p-3"><h5>{c?.courseName}</h5><p>{c?.courseCode} · {c?.department}</p></div></div>})}</div></div>
}
