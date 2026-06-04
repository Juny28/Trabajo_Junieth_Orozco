import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { ChevronLeft, ChevronRight, ListFilter, Loader2 } from 'lucide-react';
import { watchmodeService } from '../services/watchmodeService';
import { usePagination } from '../hooks/usePagination';
import GetMediaCard from '../components/GetMediaCard';

/**
 * AllTitles page with paginated list of all media titles.
 * @returns {JSX.Element}
 */
const AllTitles = () => {
  const [filter, setFilter] = React.useState('all');

  const {
    currentItems,
    currentPage,
    totalPages,
    nextPage,
    prevPage,
    goToPage,
    hasMore,
    hasLess,
    isLoading,
    error
  } = usePagination(
    ['all-titles', filter], 
    (page) => watchmodeService.getPopular(page), 
    8
  );

  // Still need filtering if the API doesn't support it, but the requirement is to usePagination for the fetch
  // Since we unified search to the backend, we can assume the hook handles the page state.

  if (isLoading) {
    return (
      <div className="flex flex-col items-center justify-center min-h-[60vh] gap-4">
        <Loader2 className="w-12 h-12 text-primary animate-spin" />
        <p className="text-text-muted">Cargando catálogo completo...</p>
      </div>
    );
  }

  if (error) {
    return <div className="container py-20 text-center text-red-500">Error al cargar el catálogo.</div>;
  }

  return (
    <div className="container animate-fade-in">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-6 mb-12">
        <div>
          <h1 className="text-4xl font-extrabold mb-2">Catálogo Completo</h1>
          <p className="text-text-muted">Explora nuestra colección completa de películas y series.</p>
        </div>
        
        <div className="flex items-center gap-2 bg-bg-card p-1 rounded-xl border border-white/5">
          <button 
            onClick={() => setFilter('all')}
            className={`px-4 py-2 ${filter === 'all' ? 'bg-primary text-white shadow-lg' : 'text-text-muted hover:text-text-main'} rounded-lg text-sm font-bold transition-colors`}
          >Todos</button>
          <button 
            onClick={() => setFilter('movie')}
            className={`px-4 py-2 ${filter === 'movie' ? 'bg-primary text-white shadow-lg' : 'text-text-muted hover:text-text-main'} rounded-lg text-sm font-bold transition-colors`}
          >Películas</button>
          <button 
            onClick={() => setFilter('tv')}
            className={`px-4 py-2 ${filter === 'tv' ? 'bg-primary text-white shadow-lg' : 'text-text-muted hover:text-text-main'} rounded-lg text-sm font-bold transition-colors`}
          >Series</button>
        </div>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
        {currentItems.map((title) => (
          <GetMediaCard key={title.id} title={title} />
        ))}
      </div>

      {/* Pagination Controls */}
      <div className="mt-16 flex items-center justify-center gap-4">
        <button 
          onClick={prevPage}
          disabled={!hasLess}
          className={`p-3 rounded-xl border border-white/10 transition-all ${
            hasLess ? 'bg-bg-card text-text-main hover:bg-white/10' : 'text-text-muted opacity-50 cursor-not-allowed'
          }`}
        >
          <ChevronLeft className="w-6 h-6" />
        </button>

        <div className="flex items-center gap-2">
          {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
            <button
              key={page}
              onClick={() => goToPage(page)}
              className={`w-10 h-10 rounded-xl font-bold transition-all ${
                currentPage === page 
                  ? 'bg-primary text-white shadow-lg shadow-primary/30' 
                  : 'bg-bg-card text-text-muted hover:text-text-main border border-white/5'
              }`}
            >
              {page}
            </button>
          ))}
        </div>

        <button 
          onClick={nextPage}
          disabled={!hasMore}
          className={`p-3 rounded-xl border border-white/10 transition-all ${
            hasMore ? 'bg-bg-card text-text-main hover:bg-white/10' : 'text-text-muted opacity-50 cursor-not-allowed'
          }`}
        >
          <ChevronRight className="w-6 h-6" />
        </button>
      </div>
    </div>
  );
};

export default AllTitles;
