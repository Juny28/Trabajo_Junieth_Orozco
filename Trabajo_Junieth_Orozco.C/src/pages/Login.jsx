import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { LogIn, UserPlus, Mail, Lock, Loader2, AlertCircle } from 'lucide-react';
import axios from 'axios';
import { useApp } from '../context/AppContext';

/**
 * Login page component.
 * Allows users to authenticate with the backend.
 * @returns {JSX.Element}
 */
const Login = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: ''
  });

  const { login } = useApp();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);

    try {
      const url = isLogin 
        ? 'http://localhost:8080/auth/login' 
        : 'http://localhost:8080/auth/register';
      
      const payload = isLogin 
        ? { username: formData.username, password: formData.password }
        : formData;

      const { data } = await axios.post(url, payload);
      
      if (isLogin) {
        // Mocking user role for now based on typical response
        login(data.token, { username: formData.username });
        navigate('/');
      } else {
        setIsLogin(true);
        setError('Registro exitoso. Por favor, inicia sesión.');
      }
    } catch (err) {
      setError(err.response?.data?.detail || 'Error en la autenticación');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container min-h-[80vh] flex items-center justify-center animate-fade-in">
      <div className="w-full max-w-md bg-bg-card p-10 rounded-3xl border border-white/5 shadow-2xl">
        <div className="text-center mb-10">
          <div className="w-16 h-16 bg-primary/10 rounded-2xl flex items-center justify-center mx-auto mb-6">
            <LogIn className="w-8 h-8 text-primary" />
          </div>
          <h1 className="text-3xl font-black mb-2">
            {isLogin ? '¡Bienvenido de nuevo!' : 'Crea tu cuenta'}
          </h1>
          <p className="text-text-muted">
            {isLogin 
              ? 'Accede a tus favoritos y reseñas personalizadas.' 
              : 'Únete a la comunidad de cinéfilos más grande.'}
          </p>
        </div>

        {error && (
          <div className="mb-6 p-4 bg-red-500/10 border border-red-500/20 rounded-2xl flex items-center gap-3 text-red-500 text-sm font-medium">
            <AlertCircle className="w-5 h-5 shrink-0" />
            <p>{error}</p>
          </div>
        )}

        <form onSubmit={handleSubmit} className="space-y-6">
          <div className="space-y-2">
            <label className="text-sm font-bold text-text-muted ml-1">Username</label>
            <div className="relative">
              <input 
                type="text" 
                required
                value={formData.username}
                onChange={(e) => setFormData({...formData, username: e.target.value})}
                className="w-full h-14 bg-bg-dark border border-white/5 rounded-2xl px-12 focus:ring-2 focus:ring-primary/50 outline-none transition-all"
                placeholder="tu_usuario"
              />
              <UserPlus className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-text-muted" />
            </div>
          </div>

          {!isLogin && (
            <div className="space-y-2">
              <label className="text-sm font-bold text-text-muted ml-1">Email</label>
              <div className="relative">
                <input 
                  type="email" 
                  required
                  value={formData.email}
                  onChange={(e) => setFormData({...formData, email: e.target.value})}
                  className="w-full h-14 bg-bg-dark border border-white/5 rounded-2xl px-12 focus:ring-2 focus:ring-primary/50 outline-none transition-all"
                  placeholder="usuario@ejemplo.com"
                />
                <Mail className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-text-muted" />
              </div>
            </div>
          )}

          <div className="space-y-2">
            <label className="text-sm font-bold text-text-muted ml-1">Contraseña</label>
            <div className="relative">
              <input 
                type="password" 
                required
                value={formData.password}
                onChange={(e) => setFormData({...formData, password: e.target.value})}
                className="w-full h-14 bg-bg-dark border border-white/5 rounded-2xl px-12 focus:ring-2 focus:ring-primary/50 outline-none transition-all"
                placeholder="********"
              />
              <Lock className="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-text-muted" />
            </div>
          </div>

          <button 
            type="submit"
            disabled={loading}
            className="w-full h-14 bg-primary hover:bg-primary-hover text-white rounded-2xl font-black text-lg transition-all shadow-lg shadow-primary/20 flex items-center justify-center gap-3 disabled:opacity-50 disabled:cursor-not-allowed"
          >
            {loading ? <Loader2 className="w-6 h-6 animate-spin" /> : isLogin ? 'Entrar' : 'Registrarse'}
          </button>
        </form>

        <div className="mt-8 text-center text-sm">
          <p className="text-text-muted mb-2">
            {isLogin ? '¿No tienes cuenta todavía?' : '¿Ya tienes una cuenta?'}
          </p>
          <button 
            onClick={() => {
              setIsLogin(!isLogin);
              setError(null);
            }}
            className="text-primary font-bold hover:underline"
          >
            {isLogin ? 'Crea una cuenta ahora' : 'Inicia sesión aquí'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default Login;
