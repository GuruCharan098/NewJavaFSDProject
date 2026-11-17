import { useEffect,useState } from 'react'
import api from '../services/api'
import { useAuth } from '../context/AuthContext'
export default function Courses(){
 const {user}=useAuth();const [courses,setCourses]=useState([]);const [error,setError]=useState('');const [message,setMessage]=useState('')
 const load=()=>api.get('/courses').then(r=>setCourses(r.data)).catch(e=>setError(e.response?.data?.error||'Unable to load courses'))
 useEffect(()=>{load()},[])
 async function enroll(id){try{await api.post(`/student/courses/${id}/enroll`);setMessage('Enrolment successful')}catch(e){setError(e.response?.data?.error||'Enrolment failed')}}
 return <div><h2>{user?.role==='STUDENT'?'Available Courses':'Courses'}</h2>{error&&<div className="alert alert-warning">{error}</div>}{message&&<div className="alert alert-success">{message}</div>}
 <div className="row g-3">{courses.map(c=><div className="col-md-4" key={c.id}><div className="card page-card p-3 h-100"><h5>{c.courseName}</h5><div className="text-secondary">{c.courseCode} · {c.department}</div><p className="mt-2">Credits: {c.credits}</p>{user?.role==='STUDENT'&&<button className="btn btn-primary" onClick={()=>enroll(c.id)}>Enrol</button>}</div></div>)}</div></div>
}
