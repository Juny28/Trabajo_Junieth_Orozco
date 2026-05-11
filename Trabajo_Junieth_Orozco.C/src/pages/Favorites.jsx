import React from 'react';
import { Heart, Ghost, MoveRight } from 'lucide-react';
import { Link } from 'react-router-dom';
import { useApp } from '../context/AppContext';
import GetMediaCard from '../components/GetMediaCard';

/**
 * Favorites page displaying the list of user's saved titles.
 * @returns {JSX.Element}
 */
const Favorites = () => {
  const { favorites } = useApp();

  return (
    <div className="container animate-fade-in">
      <div className="mb-12">
        <h1 className="text-4xl font-extrabold mb-2">Mis Favoritos</h1>
        <p className="text-text-muted">Todos los títulos que has guardado para ver más tarde.</p>
      </div>

      {favorites.length === 0 ? (
        <div className="flex flex-col items-center justify-center py-24 text-center">
          <div className="w-24 h-24 bg-bg-card rounded-full flex items-center justify-center mb-6 border border-white/5">
            <Ghost className="w-12 h-12 text-text-muted opacity-30" />
          </div>
          <h2 className="text-2xl font-bold mb-4">Tu lista está vacía</h2>
          <p className="text-text-muted max-w-md mx-auto mb-10">
            Parece que aún no has añadido ningún título a tus favoritos. ¡Explora el catálogo y guarda lo que más te guste!
          </p>
          <Link 
            to="/all" 
            className="flex items-center gap-2 bg-primary hover:bg-primary-hover text-white px-8 py-3 rounded-xl font-bold transition-all shadow-xl shadow-primary/20 group"
          >
            Explorar Catálogo <MoveRight className="w-5 h-5 group-hover:translate-x-1 transition-transform" />
          </Link>
        </div>
      ) : (
        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
          {favorites.map((title) => (
            <GetMediaCard key={title.id} title={title} />
          ))}
        </div>
      )}
    </div>
  );
};

export default Favorites;
