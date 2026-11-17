import { useEffect,useState } from 'react'
import api from '../services/api'
export default function AdminCourses(){
 const [courses,setCourses]=useState([]);const [form,setForm]=useState({courseCode:'',courseName:'',department:'',credits:3});const [message,setMessage]=useState('');const [error,setError]=useState('')
 const load=()=>api.get('/courses').then(r=>setCourses(r.data))
 useEffect(()=>{load().catch(e=>setError(e.response?.data?.error||'Load failed'))},[])
 async function submit(e){e.preventDefault();setMessage('');setError('');try{await api.post('/admin/courses',{...form,credits:Number(form.credits)});setMessage('Course created');setForm({courseCode:'',courseName:'',department:'',credits:3});load()}catch(e){setError(e.response?.data?.error||'Create failed')}}
 return <div><h2>Manage Courses</h2>{message&&<div className="alert alert-success">{message}</div>}{error&&<div className="alert alert-danger">{error}</div>}
 <form className="card page-card p-3 mb-4 row g-2" onSubmit={submit}><div className="col-md-3"><input className="form-control" placeholder="Course code" required value={form.courseCode} onChange={e=>setForm({...form,courseCode:e.target.value})}/></div><div className="col-md-3"><input className="form-control" placeholder="Course name" required value={form.courseName} onChange={e=>setForm({...form,courseName:e.target.value})}/></div><div className="col-md-3"><input className="form-control" placeholder="Department" value={form.department} onChange={e=>setForm({...form,department:e.target.value})}/></div><div className="col-md-1"><input className="form-control" type="number" min="1" value={form.credits} onChange={e=>setForm({...form,credits:e.target.value})}/></div><div className="col-md-2"><button className="btn btn-primary w-100">Create</button></div></form>
 <div className="bg-white rounded p-3"><table className="table"><thead><tr><th>Code</th><th>Course</th><th>Department</th><th>Credits</th></tr></thead><tbody>{courses.map(c=><tr key={c.id}><td>{c.courseCode}</td><td>{c.courseName}</td><td>{c.department}</td><td>{c.credits}</td></tr>)}</tbody></table></div></div>
}
