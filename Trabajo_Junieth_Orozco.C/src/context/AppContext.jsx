import React, { createContext, useContext, useState, useEffect } from 'react';

const AppContext = createContext();

/**
 * AppProvider component to manage global state (favorites and platforms).
 * @param {Object} props - Component props.
 * @param {React.ReactNode} props.children - Child components.
 * @returns {JSX.Element}
 */
export const AppProvider = ({ children }) => {
  // Load favorites from localStorage
  const [favorites, setFavorites] = useState(() => {
    const saved = localStorage.getItem('filmapi_favorites');
    return saved ? JSON.parse(saved) : [];
  });

  // Load platforms from localStorage
  const [platforms, setPlatforms] = useState(() => {
    const saved = localStorage.getItem('filmapi_platforms');
    return saved ? JSON.parse(saved) : [];
  });

  // Persist favorites
  useEffect(() => {
    localStorage.setItem('filmapi_favorites', JSON.stringify(favorites));
  }, [favorites]);

  // Persist platforms
  useEffect(() => {
    localStorage.setItem('filmapi_platforms', JSON.stringify(platforms));
  }, [platforms]);

  /**
   * Toggles a title in favorites.
   * @param {Object} title - The title object to add or remove.
   */
  const toggleFavorite = (title) => {
    setFavorites(prev => {
      const exists = prev.find(f => f.id === title.id);
      if (exists) {
        return prev.filter(f => f.id !== title.id);
      }
      return [...prev, title];
    });
  };

  /**
   * Updates the subscribed platforms.
   * @param {Array} newPlatforms - The new list of platform IDs.
   */
  const updatePlatforms = (newPlatforms) => {
    setPlatforms(newPlatforms);
  };

  const value = {
    favorites,
    toggleFavorite,
    isFavorite: (id) => favorites.some(f => f.id === id),
    platforms,
    updatePlatforms
  };

  return <AppContext.Provider value={value}>{children}</AppContext.Provider>;
};

/**
 * Custom hook to use the AppContext.
 * @returns {Object} Context values.
 */
export const useApp = () => {
  const context = useContext(AppContext);
  if (!context) {
    throw new Error('useApp must be used within an AppProvider');
  }
  return context;
};
