import React from 'react';
import { Smartphone, CheckCircle2, Plus } from 'lucide-react';
import { useApp } from '../context/AppContext';

// Static list of popular streaming platforms
const PLATFORMS = [
  { id: 203, name: 'Netflix', type: 'subscription', color: '#E50914' },
  { id: 26,  name: 'Amazon Prime Video', type: 'subscription', color: '#00A8E0' },
  { id: 387, name: 'Disney+', type: 'subscription', color: '#113CCF' },
  { id: 372, name: 'Apple TV+', type: 'subscription', color: '#555555' },
  { id: 444, name: 'HBO Max', type: 'subscription', color: '#5822B4' },
]

/**
 * MyPlatforms page to select and manage user's subscribed platforms.
 * @returns {JSX.Element}
 */
const MyPlatforms = () => {
  const { platforms, updatePlatforms } = useApp();

  /**
   * Toggles the selection of a streaming platform.
   * Adds the platform ID to the global context if not present, otherwise removes it.
   * @param {number} platformId - The unique ID of the platform (Watchmode source_id).
   */
  const togglePlatform = (platformId) => {
    if (platforms.includes(platformId)) {
      updatePlatforms(platforms.filter(id => id !== platformId));
    } else {
      updatePlatforms([...platforms, platformId]);
    }
  };

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
          {PLATFORMS.map((platform) => {
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
                  <div
                    className="w-12 h-12 rounded-2xl flex items-center justify-center font-black text-sm text-white"
                    style={{ backgroundColor: platform.color }}
                  >
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


