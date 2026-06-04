import React, { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { Search, TrendingUp, Sparkles, AlertCircle, Loader2 } from 'lucide-react';
import { watchmodeService } from '../services/watchmodeService';
import GetMediaCard from '../components/GetMediaCard';

/**
 * Home component that serves as the landing page of the application.
 * It features a hero section with a search bar and a display of 
 * trending/popular titles using react-query for data fetching.
 * 
 * @returns {JSX.Element} The rendered Home page.
 */
const Home = () => {
  const [searchQuery, setSearchQuery] = useState('');
  const [isSearching, setIsSearching] = useState(false);

  // Fetch popular titles
  const { data: popularTitles, isLoading: loadingPopular, error: errorPopular } = useQuery({
    queryKey: ['popular'],
    queryFn: () => watchmodeService.getPopular(1),
  });

  // Fetch search results
  const { data: searchResults, isLoading: loadingSearch, error: errorSearch } = useQuery({
    queryKey: ['search', searchQuery],
    queryFn: () => watchmodeService.searchTitles(searchQuery, 1),
    enabled: searchQuery.length > 2,
  });

  /**
   * Handles the search form submission.
   * @param {React.FormEvent} e - The form event.
   */
  const handleSearch = (e) => {
    e.preventDefault();
    if (searchQuery.length > 2) {
      setIsSearching(true);
    }
  };

  return (
    <div className="container animate-fade-in">
      {/* Hero Section */}
      <section className="py-16 text-center">
        <h1 className="text-5xl md:text-7xl font-extrabold mb-6 tracking-tight">
          Encuentra tu próxima <br />
          <span className="gradient-text">gran aventura</span>
        </h1>
        <p className="text-text-muted text-lg max-w-2xl mx-auto mb-10">
          Explora miles de películas y series, descubre dónde verlas y guarda tus favoritas en un solo lugar.
        </p>

        {/* Search Bar */}
        <form onSubmit={handleSearch} className="max-w-2xl mx-auto relative">
          <input 
            type="text" 
            placeholder="Busca por título (ej. Inception, Breaking Bad...)"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full h-16 bg-bg-card border border-white/10 rounded-2xl px-14 text-lg focus:outline-none focus:ring-2 focus:ring-primary/50 transition-all shadow-2xl"
          />
          <Search className="absolute left-5 top-1/2 -translate-y-1/2 text-text-muted w-6 h-6" />
          <button 
            type="submit"
            className="absolute right-3 top-1/2 -translate-y-1/2 bg-primary hover:bg-primary-hover text-white px-6 py-2 rounded-xl font-bold transition-colors shadow-lg"
          >
            Buscar
          </button>
        </form>
      </section>

      {/* Results or Trending */}
      <section className="py-12">
        <div className="flex items-center gap-3 mb-10">
          {searchQuery.length > 2 ? (
            <>
              <Search className="w-6 h-6 text-primary" />
              <h2 className="text-2xl font-bold">Resultados para "{searchQuery}"</h2>
            </>
          ) : (
            <>
              <TrendingUp className="w-6 h-6 text-primary" />
              <h2 className="text-2xl font-bold">Títulos en Tendencia</h2>
            </>
          )}
        </div>

        {(loadingPopular || (loadingSearch && searchQuery.length > 2)) ? (
          <div className="flex flex-col items-center justify-center py-20 gap-4">
            <Loader2 className="w-12 h-12 text-primary animate-spin" />
            <p className="text-text-muted font-medium">Cargando increíbles historias...</p>
          </div>
        ) : (errorPopular || errorSearch) ? (
          <div className="flex flex-col items-center justify-center py-20 text-center bg-red-500/5 rounded-3xl border border-red-500/10">
            <AlertCircle className="w-12 h-12 text-red-500 mb-4" />
            <h3 className="text-xl font-bold mb-2">¡Oops! Algo salió mal</h3>
            <p className="text-text-muted">No pudimos conectar con la base de datos de películas. Por favor, intenta de nuevo.</p>
          </div>
        ) : (
          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
            {(searchQuery.length > 2 ? searchResults : popularTitles)?.map((title) => (
              <GetMediaCard key={title.id} title={title} />
            ))}
          </div>
        )}

        {searchQuery.length > 2 && searchResults?.length === 0 && (
          <div className="text-center py-20">
            <Sparkles className="w-12 h-12 text-text-muted mx-auto mb-4 opacity-20" />
            <p className="text-text-muted text-lg">No encontramos resultados para tu búsqueda.</p>
          </div>
        )}
      </section>
    </div>
  );
};

export default Home;
