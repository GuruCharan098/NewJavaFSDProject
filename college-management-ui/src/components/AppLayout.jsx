import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function AppLayout({ children }) {

  const { user, logout } = useAuth()

  const navigate = useNavigate()

  const links = user?.role === 'ADMIN'
    ? [['/','Dashboard'],['/admin/users','Users'],['/admin/courses','Courses']]
    : user?.role === 'FACULTY'
      ? [['/','Dashboard'],['/faculty/courses','My Courses'],['/faculty/attendance','Attendance']]
      : [['/','Dashboard'],['/student/courses','My Courses'],['/student/attendance','Attendance']]
      
  async function signOut() { await logout(); navigate('/login') }
  return (
    <>
      <nav className="navbar navbar-dark bg-primary px-3">
        <Link className="navbar-brand brand" to="/">College CMS</Link>
        <div className="d-flex align-items-center gap-3 text-white">
          <span>{user?.fullName} · {user?.role}</span>
          <button className="btn btn-sm btn-light" onClick={signOut}>Logout</button>
        </div>
      </nav>
      <div className="container-fluid">
        <div className="row">
          <aside className="col-md-2 sidebar p-3">
            {links.map(([to,label]) => <Link key={to+label} to={to}>{label}</Link>)}
            <Link to="/profile">My Profile</Link>
          </aside>
          <main className="col-md-10 p-4">{children}</main>
        </div>
      </div>
    </>
  )
}
