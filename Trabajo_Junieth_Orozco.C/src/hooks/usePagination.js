import { useState } from 'react';

/**
 * Custom hook to abstract pagination logic.
 * @param {Array} items - The full list of items to paginate.
 * @param {number} itemsPerPage - Number of items to show per page.
 * @returns {Object} Pagination state and methods.
 */
export const usePagination = (items = [], itemsPerPage = 12) => {
  const [currentPage, setCurrentPage] = useState(1);

  const totalPages = Math.ceil(items.length / itemsPerPage);
  
  const currentItems = items.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  /**
   * Navigates to a specific page.
   * @param {number} page - The page number to go to.
   */
  const goToPage = (page) => {
    if (page >= 1 && page <= totalPages) {
      setCurrentPage(page);
    }
  };

  /**
   * Navigates to the next page.
   */
  const nextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(prev => prev + 1);
    }
  };

  /**
   * Navigates to the previous page.
   */
  const prevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(prev => prev - 1);
    }
  };

  return {
    currentPage,
    totalPages,
    currentItems,
    goToPage,
    nextPage,
    prevPage,
    hasMore: currentPage < totalPages,
    hasLess: currentPage > 1
  };
};
