import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import { useAuth } from './context/AuthContext'
import ProtectedRoute from './components/ProtectedRoute'
import AppLayout from './components/AppLayout'
import Login from './pages/Login'
import Register from './pages/Register'
import Dashboard from './pages/Dashboard'
import Users from './pages/Users'
import Courses from './pages/Courses'
import MyCourses from './pages/MyCourses'
import Attendance from './pages/Attendance'
import Profile from './pages/Profile'
import AdminCourses from './pages/AdminCourses'

function HomeRedirect(){
 const {user,loading}=useAuth()
 if(loading)return <div className="text-center p-5">Loading...</div>
 if(!user)return <Navigate to="/login" replace/>
 return <Navigate to={user.role==='ADMIN'?'/admin':user.role==='FACULTY'?'/faculty':'/student'} replace/>
}
function ProtectedPage({roles,children}){return <ProtectedRoute roles={roles}><AppLayout>{children}</AppLayout></ProtectedRoute>}

export default function App(){
 return <BrowserRouter><Routes>
  <Route path="/" element={<HomeRedirect/>}/>
  <Route path="/login" element={<Login/>}/>
  <Route path="/register" element={<Register/>}/>
  <Route path="/admin" element={<ProtectedPage roles={['ADMIN']}><Dashboard/></ProtectedPage>}/>
  <Route path="/admin/users" element={<ProtectedPage roles={['ADMIN']}><Users/></ProtectedPage>}/>
  <Route path="/admin/courses" element={<ProtectedPage roles={['ADMIN']}><AdminCourses/></ProtectedPage>}/>
  <Route path="/student" element={<ProtectedPage roles={['STUDENT']}><Dashboard/></ProtectedPage>}/>
  <Route path="/student/courses" element={<ProtectedPage roles={['STUDENT']}><Courses/></ProtectedPage>}/>
  <Route path="/student/my-courses" element={<ProtectedPage roles={['STUDENT']}><MyCourses/></ProtectedPage>}/>
  <Route path="/student/attendance" element={<ProtectedPage roles={['STUDENT']}><Attendance/></ProtectedPage>}/>
  <Route path="/faculty" element={<ProtectedPage roles={['FACULTY']}><Dashboard/></ProtectedPage>}/>
  <Route path="/faculty/courses" element={<ProtectedPage roles={['FACULTY']}><MyCourses/></ProtectedPage>}/>
  <Route path="/faculty/attendance" element={<ProtectedPage roles={['FACULTY']}><Attendance/></ProtectedPage>}/>
  <Route path="/profile" element={<ProtectedPage roles={['ADMIN','STUDENT','FACULTY']}><Profile/></ProtectedPage>}/>
  <Route path="*" element={<Navigate to="/" replace/>}/>
 </Routes></BrowserRouter>
}
