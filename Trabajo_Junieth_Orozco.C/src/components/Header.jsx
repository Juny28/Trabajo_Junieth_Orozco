import React from 'react';
import { Link, NavLink } from 'react-router-dom';
import { Heart, Search, Smartphone, List } from 'lucide-react';
import { useApp } from '../context/AppContext';

/**
 * Header component with navigation and favorites counter.
 * @returns {JSX.Element}
 */
const Header = () => {
  const { favorites } = useApp();

  return (
    <header className="glass-morphism fixed top-0 w-full z-50">
      <div className="container h-20 flex items-center justify-between">
        <Link to="/" className="text-2xl font-extrabold tracking-tighter">
          FILM<span className="gradient-text">API</span>
        </Link>

        <nav className="hidden md:flex items-center gap-8 text-sm font-medium">
          <NavLink to="/" className={({ isActive }) => isActive ? 'text-primary' : 'text-text-muted hover:text-text-main'}>
            Inicio
          </NavLink>
          <NavLink to="/all" className={({ isActive }) => isActive ? 'text-primary' : 'text-text-muted hover:text-text-main'}>
            Catálogo
          </NavLink>
          <NavLink to="/myplatforms" className={({ isActive }) => isActive ? 'text-primary' : 'text-text-muted hover:text-text-main'}>
            Plataformas
          </NavLink>
        </nav>

        <div className="flex items-center gap-6">
          <Link to="/favorites" className="relative group">
            <Heart className="w-6 h-6 text-text-muted group-hover:text-secondary transition-colors" />
            {favorites.length > 0 && (
              <span className="absolute -top-2 -right-2 bg-secondary text-white text-[10px] font-bold w-5 h-5 flex items-center justify-center rounded-full animate-fade-in">
                {favorites.length}
              </span>
            )}
          </Link>
          <div className="md:hidden">
            <List className="w-6 h-6 text-text-muted" />
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
