import { useAuth } from '../context/AuthContext'
export default function Profile(){const {user}=useAuth();return <div><h2>My Profile</h2><div className="card page-card p-4" style={{maxWidth:600}}><p><b>Name:</b> {user?.fullName}</p><p><b>Email:</b> {user?.email}</p><p><b>Role:</b> {user?.role}</p><p><b>Status:</b> {user?.status}</p></div></div>}
