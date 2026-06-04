import React from 'react';
import { Link } from 'react-router-dom';
import { Heart, Star, Calendar, Monitor } from 'lucide-react';
import { useApp } from '../context/AppContext';

/**
 * Component to display a media card (Movie/TV Show).
 * @param {Object} props - Component props.
 * @param {Object} props.title - Title data (id, name/title, type, year, poster).
 * @returns {JSX.Element}
 */
const GetMediaCard = ({ title }) => {
  const { toggleFavorite, isFavorite } = useApp();
  const favorite = isFavorite(title.id);

  // Fallback for different API response naming conventions
  const name = title.name || title.title;
  const year = title.year || 'N/A';
  const type = title.type === 'movie' ? 'Película' : (title.type === 'tv_series' ? 'Serie' : title.type);

  return (
    <div className="bg-bg-card rounded-2xl overflow-hidden group hover:scale-[1.02] transition-transform duration-300 border border-white/5 shadow-xl">
      <div className="relative aspect-[2/3] overflow-hidden">
        <img 
          src={title.poster || `https://placehold.co/400x600/1e293b/a855f7?text=${encodeURIComponent(name)}`} 
          alt={name}
          className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-500"
          onError={(e) => {
            e.target.onerror = null; 
            e.target.src=`https://placehold.co/400x600/1e293b/a855f7?text=${encodeURIComponent(name)}`;
          }}
        />
        <div className="absolute inset-0 bg-gradient-to-t from-bg-dark/90 via-transparent to-transparent opacity-0 group-hover:opacity-100 transition-opacity duration-300" />
        
        <button 
          onClick={(e) => {
            e.preventDefault();
            toggleFavorite(title);
          }}
          className={`absolute top-4 right-4 p-3 rounded-full backdrop-blur-md transition-all duration-300 ${
            favorite ? 'bg-secondary text-white scale-110' : 'bg-black/40 text-white hover:bg-black/60'
          }`}
        >
          <Heart className={`w-5 h-5 ${favorite ? 'fill-current' : ''}`} />
        </button>
      </div>

      <div className="p-5">
        <div className="flex items-center gap-3 mb-2">
          <span className="text-[10px] font-bold uppercase tracking-widest text-primary bg-primary/10 px-2 py-1 rounded">
            {type}
          </span>
          <span className="flex items-center gap-1 text-[10px] text-text-muted">
            <Calendar className="w-3 h-3" /> {year}
          </span>
        </div>
        
        <Link to={`/details/${title.id}`}>
          <h3 className="text-lg font-bold text-text-main line-clamp-1 hover:text-primary transition-colors cursor-pointer">
            {name}
          </h3>
        </Link>
        
        <p className="text-sm text-text-muted mt-2 line-clamp-2 min-h-[40px]">
          {title.tmdb_type ? `Visto en ${title.tmdb_type}` : 'Disponible para streaming'}
        </p>

        <Link 
          to={`/details/${title.id}`}
          className="mt-4 flex items-center justify-center gap-2 w-full py-3 bg-white/5 hover:bg-white/10 rounded-xl text-sm font-semibold transition-colors"
        >
          Ver Detalles
        </Link>
      </div>
    </div>
  );
};

export default GetMediaCard;
