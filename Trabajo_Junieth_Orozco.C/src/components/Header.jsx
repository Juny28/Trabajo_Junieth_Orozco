import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import { Heart, Search, Smartphone, List } from 'lucide-react';
import { useApp } from '../context/AppContext';

/**
 * Header component with navigation and favorites counter.
 * @returns {JSX.Element}
 */
const Header = () => {
  const { favorites, auth, logout, isAuthenticated } = useApp();

  return (
    <header className="glass-morphism fixed top-0 w-full z-50">
      <div className="container h-20 flex items-center justify-between">
        <Link to="/" className="text-2xl font-extrabold tracking-tighter hover:scale-105 transition-transform">
          FILM<span className="gradient-text">API</span>
        </Link>

        <nav className="hidden md:flex items-center gap-8 text-sm font-black uppercase tracking-tighter">
          <NavLink to="/" className={({ isActive }) => isActive ? 'text-primary' : 'text-text-muted hover:text-text-main transition-colors'}>
            Inicio
          </NavLink>
          <NavLink to="/all" className={({ isActive }) => isActive ? 'text-primary' : 'text-text-muted hover:text-text-main transition-colors'}>
            Catálogo
          </NavLink>
          <NavLink to="/myplatforms" className={({ isActive }) => isActive ? 'text-primary' : 'text-text-muted hover:text-text-main transition-colors'}>
            Plataformas
          </NavLink>
        </nav>

        <div className="flex items-center gap-6">
          <Link to="/favorites" className="relative group p-2">
            <Heart className={`w-6 h-6 transition-all duration-300 ${favorites.length > 0 ? 'text-secondary fill-current' : 'text-text-muted group-hover:text-secondary'}`} />
            {favorites.length > 0 && (
              <span className="absolute -top-1 -right-1 bg-secondary text-white text-[10px] font-black w-5 h-5 flex items-center justify-center rounded-full shadow-lg shadow-secondary/30 animate-scale-in">
                {favorites.length}
              </span>
            )}
          </Link>
          
          <div className="h-6 w-px bg-white/10 mx-2 hidden sm:block" />

          {isAuthenticated ? (
            <div className="flex items-center gap-4">
              <span className="text-sm font-bold text-text-main hidden lg:block">Hola, {auth.user?.username}</span>
              <button 
                onClick={logout}
                className="px-5 py-2 bg-white/5 hover:bg-red-500/10 hover:text-red-500 border border-white/10 hover:border-red-500/50 rounded-xl text-xs font-black uppercase tracking-widest transition-all"
              >
                Salir
              </button>
            </div>
          ) : (
            <Link 
              to="/login"
              className="px-6 py-2 bg-primary hover:bg-primary-hover text-white rounded-xl text-xs font-black uppercase tracking-widest transition-all shadow-lg shadow-primary/20"
            >
              Entrar
            </Link>
          )}

          <div className="md:hidden">
            <List className="w-6 h-6 text-text-muted" />
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
