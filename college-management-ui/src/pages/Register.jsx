import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Register() {
  const { register }=useAuth(); const navigate=useNavigate()
  const [form,setForm]=useState({fullName:'',email:'',password:'',rollNumber:'',department:'',semester:''})
  const [error,setError]=useState(''); const [message,setMessage]=useState('')
  function update(e){setForm({...form,[e.target.name]:e.target.value})}
  async function submit(e){
    e.preventDefault();setError('');setMessage('')
    try { const r=await register(form);setMessage(r.data.message);setTimeout(()=>navigate('/login'),1200) }
    catch(err){setError(err.response?.data?.error||'Registration failed')}
  }
  return <div className="container py-5" style={{maxWidth:650}}>
    <div className="card page-card p-4">
      <h2 className="text-primary">Student Registration</h2>
      <p className="text-secondary">Create a student account.</p>
      {error&&<div className="alert alert-danger">{error}</div>}
      {message&&<div className="alert alert-success">{message}</div>}
      <form onSubmit={submit} className="row g-3">
        {[['fullName','Full name'],['email','Email'],['password','Password'],['rollNumber','Roll number'],['department','Department'],['semester','Semester']].map(([name,label])=>
          <div className="col-md-6" key={name}><label className="form-label">{label}</label><input className="form-control" name={name} type={name==='password'?'password':name==='email'?'email':'text'} required minLength={name==='password'?8:undefined} value={form[name]} onChange={update}/></div>
        )}
        <div className="col-12"><button className="btn btn-primary">Register</button> <Link className="btn btn-link" to="/login">Back to login</Link></div>
      </form>
    </div>
  </div>
}
