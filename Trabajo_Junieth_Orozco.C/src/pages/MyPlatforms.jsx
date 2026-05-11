import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { Smartphone, CheckCircle2, Plus, Loader2 } from 'lucide-react';
import { watchmodeService } from '../services/watchmodeService';
import { useApp } from '../context/AppContext';

/**
 * MyPlatforms page to select and manage user's subscribed platforms.
 * @returns {JSX.Element}
 */
const MyPlatforms = () => {
  const { platforms, updatePlatforms } = useApp();

  const { data: allPlatforms, isLoading, error } = useQuery({
    queryKey: ['platforms'],
    queryFn: watchmodeService.getPlatforms,
  });

  const togglePlatform = (platformId) => {
    if (platforms.includes(platformId)) {
      updatePlatforms(platforms.filter(id => id !== platformId));
    } else {
      updatePlatforms([...platforms, platformId]);
    }
  };

  // Filter some popular platforms for the UI if too many are returned
  const popularSourceIds = [203, 157, 26, 387, 372, 371, 444];
  const displayPlatforms = allPlatforms?.filter(p => popularSourceIds.includes(p.id)) || [];

  if (isLoading) {
    return (
      <div className="flex flex-col items-center justify-center min-h-[60vh] gap-4">
        <Loader2 className="w-12 h-12 text-primary animate-spin" />
        <p className="text-text-muted">Cargando plataformas disponibles...</p>
      </div>
    );
  }

  return (
    <div className="container animate-fade-in">
      <div className="max-w-4xl mx-auto">
        <div className="text-center mb-16">
          <div className="w-20 h-20 bg-primary/10 rounded-3xl flex items-center justify-center mx-auto mb-6">
            <Smartphone className="w-10 h-10 text-primary" />
          </div>
          <h1 className="text-4xl md:text-5xl font-black mb-4 tracking-tight">Mis Plataformas</h1>
          <p className="text-text-muted text-lg">
            Selecciona los servicios a los que estás suscrito para personalizar tu experiencia.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {displayPlatforms.map((platform) => {
            const isSelected = platforms.includes(platform.id);
            return (
              <button
                key={platform.id}
                onClick={() => togglePlatform(platform.id)}
                className={`flex items-center justify-between p-6 rounded-3xl border-2 transition-all duration-300 ${
                  isSelected 
                    ? 'bg-primary/10 border-primary shadow-lg shadow-primary/10' 
                    : 'bg-bg-card border-white/5 hover:border-white/20'
                }`}
              >
                <div className="flex items-center gap-4">
                  <div className={`w-12 h-12 rounded-2xl flex items-center justify-center font-black text-sm ${
                    isSelected ? 'bg-primary text-white' : 'bg-white/5 text-text-muted'
                  }`}>
                    {platform.name.substring(0, 2).toUpperCase()}
                  </div>
                  <div className="text-left">
                    <p className="font-bold text-lg">{platform.name}</p>
                    <p className="text-xs text-text-muted uppercase font-black tracking-widest">
                      {platform.type}
                    </p>
                  </div>
                </div>

                <div className={`w-8 h-8 rounded-full flex items-center justify-center transition-all ${
                  isSelected ? 'bg-primary text-white scale-110' : 'bg-white/5 text-text-muted'
                }`}>
                  {isSelected ? <CheckCircle2 className="w-5 h-5" /> : <Plus className="w-5 h-5" />}
                </div>
              </button>
            );
          })}
        </div>

        {platforms.length > 0 && (
          <div className="mt-16 p-8 bg-gradient-to-br from-primary/20 to-accent/20 rounded-3xl border border-white/10 text-center animate-fade-in">
            <h3 className="text-xl font-bold mb-2">¡Suscripciones configuradas!</h3>
            <p className="text-text-muted">
              Tienes {platforms.length} plataformas seleccionadas. Ahora priorizaremos estos servicios en tus búsquedas.
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default MyPlatforms;
