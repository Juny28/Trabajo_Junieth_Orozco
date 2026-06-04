import { useState, useMemo } from 'react';
import { useQuery } from '@tanstack/react-query';

/**
 * Custom hook to abstract pagination logic.
 * It handles remote data fetching using react-query.
 * 
 * @param {string[]} queryKey - Unique key for react-query caching.
 * @param {Function} fetchFn - Function that receives a page number and returns a promise with data.
 * @param {number} [itemsPerPage=8] - Number of items to display per page (default is 8).
 * @returns {Object} Pagination state, react-query results, and navigation methods.
 */
export const usePagination = (queryKey, fetchFn, itemsPerPage = 8) => {
  const [currentPage, setCurrentPage] = useState(1);

  // Fetch data for the current page
  const { data, isLoading, error, isPlaceholderData } = useQuery({
    queryKey: [...queryKey, currentPage],
    queryFn: () => fetchFn(currentPage),
    placeholderData: (previousData) => previousData, // Maintain previous data while loading next page
  });

  // Calculate total pages if the API provides it, otherwise assume based on current items
  // Note: For Watchmode, if we don't have total_pages, we use the length check.
  const totalPages = useMemo(() => {
    if (!data) return 0;
    if (data.total_pages) return data.total_pages;
    // Fallback: If it's a fixed list, calculate from it. 
    // If it's a dynamic stream, we might only know if there's more.
    return data.results ? Math.ceil(data.total_results / itemsPerPage) : 1;
  }, [data, itemsPerPage]);

  const currentItems = useMemo(() => {
    if (!data) return [];
    return Array.isArray(data) ? data : (data.results || []);
  }, [data]);

  /**
   * Navigates to a specific page.
   * @param {number} page - The page number to go to.
   */
  const goToPage = (page) => {
    setCurrentPage(page);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  /**
   * Navigates to the next page if available.
   */
  const nextPage = () => {
    if (currentPage < totalPages || (data && currentItems.length === itemsPerPage)) {
      setCurrentPage(prev => prev + 1);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  /**
   * Navigates to the previous page if available.
   */
  const prevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(prev => prev - 1);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    }
  };

  return {
    currentPage,
    totalPages,
    currentItems,
    goToPage,
    nextPage,
    prevPage,
    isLoading,
    error,
    hasMore: currentPage < totalPages || (data && currentItems.length === itemsPerPage),
    hasLess: currentPage > 1,
    isPlaceholderData
  };
};
