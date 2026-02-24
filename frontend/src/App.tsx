import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import MatchList from './pages/MatchList';
import CreateMatch from './pages/CreateMatch';
import MatchDetail from './pages/MatchDetail';
import Notifications from './pages/Notifications';

function ProtectedRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated } = useAuth();
  return isAuthenticated ? <>{children}</> : <Navigate to="/login" />;
}

export default function App() {
  const { isAuthenticated } = useAuth();

  return (
    <div className="min-h-screen bg-gray-50">
      {isAuthenticated && <Navbar />}
      <main className={isAuthenticated ? 'pt-16' : ''}>
        <Routes>
          <Route path="/login" element={isAuthenticated ? <Navigate to="/" /> : <Login />} />
          <Route path="/register" element={isAuthenticated ? <Navigate to="/" /> : <Register />} />
          <Route path="/" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
          <Route path="/partidos" element={<ProtectedRoute><MatchList /></ProtectedRoute>} />
          <Route path="/partidos/nuevo" element={<ProtectedRoute><CreateMatch /></ProtectedRoute>} />
          <Route path="/partidos/:id" element={<ProtectedRoute><MatchDetail /></ProtectedRoute>} />
          <Route path="/notificaciones" element={<ProtectedRoute><Notifications /></ProtectedRoute>} />
        </Routes>
      </main>
    </div>
  );
}
