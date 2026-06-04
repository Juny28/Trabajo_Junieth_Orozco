import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { 
  ArrowLeft, Star, Clock, Calendar, 
  PlayCircle, ExternalLink, Heart,
  Tv, Film, Loader2, AlertCircle
} from 'lucide-react';
import { watchmodeService } from '../services/watchmodeService';
import { useApp } from '../context/AppContext';

/**
 * Componente que muestra la información detallada de una película o serie.
 * @param {Object} props - Propiedades del componente.
 * @param {string} props.id - El ID de Watchmode del título.
 * @returns {JSX.Element}
 */
const GetDetails = ({ id }) => {
  const { toggleFavorite, isFavorite } = useApp();
  const favorite = isFavorite(parseInt(id));

  const { data: details, isLoading, error } = useQuery({
    queryKey: ['details', id],
    queryFn: () => watchmodeService.getDetails(id),
  });

  if (isLoading) {
    return (
      <div className="flex flex-col items-center justify-center min-h-[60vh] gap-4">
        <Loader2 className="w-12 h-12 text-primary animate-spin" />
        <p className="text-text-muted">Obteniendo detalles de la historia...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex flex-col items-center justify-center py-20 text-center">
        <AlertCircle className="w-12 h-12 text-red-500 mb-4" />
        <h3 className="text-xl font-bold mb-2">No pudimos encontrar los detalles</h3>
        <p className="text-text-muted">Hubo un problema al conectar con el servidor.</p>
      </div>
    );
  }

  return (
    <div className="animate-fade-in">
      {/* Backdrop Hero */}
      <div className="relative w-full h-[50vh] md:h-[70vh] rounded-3xl overflow-hidden mb-12 shadow-2xl">
        <img 
          src={details.backdrop || details.poster} 
          alt={details.title}
          className="w-full h-full object-cover"
        />
        <div className="absolute inset-0 bg-gradient-to-t from-bg-dark via-bg-dark/40 to-transparent" />
        
        <div className="absolute bottom-10 left-10 right-10">
          <div className="flex flex-col md:flex-row md:items-end justify-between gap-8">
            <div className="flex-1">
              <div className="flex items-center gap-3 mb-4">
                <span className="bg-primary text-white text-xs font-black px-3 py-1 rounded uppercase tracking-tighter">
                  {details.type === 'movie' ? 'Película' : 'Serie'}
                </span>
                <span className="flex items-center gap-1 text-white/80 text-sm font-bold">
                  <Star className="w-4 h-4 text-yellow-500 fill-current" /> {details.user_rating || 'N/A'}
                </span>
                <span className="text-white/60 text-sm font-medium">
                  {details.year} • {details.runtime_minutes ? `${details.runtime_minutes} min` : 'Duración variable'}
                </span>
              </div>
              <h1 className="text-4xl md:text-6xl font-black text-white mb-4 tracking-tighter">{details.title}</h1>
              <p className="text-white/80 text-lg max-w-3xl line-clamp-3 md:line-clamp-none font-medium leading-relaxed">
                {details.plot_overview}
              </p>
            </div>

            <div className="flex items-center gap-4">
              <button 
                onClick={() => toggleFavorite(details)}
                className={`flex items-center gap-3 px-8 py-4 rounded-2xl font-bold transition-all shadow-xl ${
                  favorite 
                    ? 'bg-secondary text-white' 
                    : 'bg-white/10 text-white backdrop-blur-md hover:bg-white/20'
                }`}
              >
                <Heart className={`w-6 h-6 ${favorite ? 'fill-current' : ''}`} />
                {favorite ? 'En Favoritos' : 'Guardar'}
              </button>
              
              {details.trailer && (
                <a 
                  href={details.trailer} 
                  target="_blank" 
                  rel="noopener noreferrer"
                  className="p-4 bg-white text-bg-dark rounded-2xl hover:scale-110 transition-transform shadow-xl"
                >
                  <PlayCircle className="w-8 h-8 fill-current" />
                </a>
              )}
            </div>
          </div>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-12">
        {/* Info Sidebar */}
        <div className="space-y-10">
          <div>
            <h3 className="text-lg font-bold mb-4 flex items-center gap-2">
              <ExternalLink className="w-5 h-5 text-primary" /> Dónde ver
            </h3>
            <div className="grid grid-cols-1 gap-3">
              {details.sources?.length > 0 ? details.sources.slice(0, 5).map((source) => (
                <div key={source.source_id} className="flex items-center justify-between p-4 bg-bg-card rounded-2xl border border-white/5">
                  <div className="flex items-center gap-3">
                    <div className="w-10 h-10 bg-white/5 rounded-lg flex items-center justify-center font-bold text-xs uppercase">
                      {source.name.substring(0, 2)}
                    </div>
                    <span className="font-bold">{source.name}</span>
                  </div>
                  <span className="text-xs font-bold px-2 py-1 bg-primary/10 text-primary rounded">
                    {source.type}
                  </span>
                </div>
              )) : (
                <p className="text-text-muted italic">No hay servicios de streaming disponibles.</p>
              )}
            </div>
          </div>

          <div>
            <h3 className="text-lg font-bold mb-4">Géneros</h3>
            <div className="flex flex-wrap gap-2">
              {details.genre_names?.map(genre => (
                <span key={genre} className="px-4 py-2 bg-bg-card rounded-xl border border-white/5 text-sm font-semibold hover:border-primary transition-colors cursor-default">
                  {genre}
                </span>
              ))}
            </div>
          </div>
        </div>

        {/* Extended Details */}
        <div className="lg:col-span-2 space-y-10">
          <div className="bg-bg-card p-8 rounded-3xl border border-white/5 shadow-2xl">
            <h2 className="text-2xl font-black mb-6">Sinopsis Completa</h2>
            <p className="text-text-muted text-lg leading-relaxed font-medium">
              {details.plot_overview || 'No hay sinopsis disponible para este título.'}
            </p>
          </div>

          <div className="grid grid-cols-2 md:grid-cols-4 gap-6">
            <div className="bg-bg-card p-6 rounded-2xl border border-white/5 text-center">
              <Calendar className="w-6 h-6 text-primary mx-auto mb-3" />
              <p className="text-xs text-text-muted font-bold uppercase mb-1">Año</p>
              <p className="font-black text-lg">{details.year}</p>
            </div>
            <div className="bg-bg-card p-6 rounded-2xl border border-white/5 text-center">
              <Star className="w-6 h-6 text-yellow-500 mx-auto mb-3" />
              <p className="text-xs text-text-muted font-bold uppercase mb-1">Rating</p>
              <p className="font-black text-lg">{details.user_rating || 'N/A'}</p>
            </div>
            <div className="bg-bg-card p-6 rounded-2xl border border-white/5 text-center">
              <Clock className="w-6 h-6 text-accent mx-auto mb-3" />
              <p className="text-xs text-text-muted font-bold uppercase mb-1">Duración</p>
              <p className="font-black text-lg">{details.runtime_minutes ? `${details.runtime_minutes}m` : 'N/A'}</p>
            </div>
            <div className="bg-bg-card p-6 rounded-2xl border border-white/5 text-center">
              {details.type === 'movie' ? <Film className="w-6 h-6 text-secondary mx-auto mb-3" /> : <Tv className="w-6 h-6 text-secondary mx-auto mb-3" />}
              <p className="text-xs text-text-muted font-bold uppercase mb-1">Formato</p>
              <p className="font-black text-lg uppercase">{details.type}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

/**
 * Details Page wrapper.
 * @returns {JSX.Element}
 */
const Details = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  return (
    <div className="container">
      <button 
        onClick={() => navigate(-1)}
        className="flex items-center gap-2 text-text-muted hover:text-text-main font-bold mb-8 transition-colors group"
      >
        <ArrowLeft className="w-5 h-5 group-hover:-translate-x-1 transition-transform" /> Volver
      </button>
      <GetDetails id={id} />
    </div>
  );
};

export default Details;
