import React, { createContext, useContext, useState, useEffect } from 'react';

const AppContext = createContext();

/**
 * AppProvider component to manage global state (favorites and platforms).
 * @param {Object} props - Component props.
 * @param {React.ReactNode} props.children - Child components.
 * @returns {JSX.Element}
 */
export const AppProvider = ({ children }) => {
  // Load auth from localStorage
  const [auth, setAuth] = useState(() => {
    const saved = localStorage.getItem('filmapi_auth');
    return saved ? JSON.parse(saved) : { token: null, user: null };
  });

  // Load favorites from localStorage (anonymous fallback)
  const [favorites, setFavorites] = useState(() => {
    const saved = localStorage.getItem('filmapi_favorites');
    return saved ? JSON.parse(saved) : [];
  });

  // Load platforms from localStorage
  const [platforms, setPlatforms] = useState(() => {
    const saved = localStorage.getItem('filmapi_platforms');
    return saved ? JSON.parse(saved) : [];
  });

  // Persist auth
  useEffect(() => {
    localStorage.setItem('filmapi_auth', JSON.stringify(auth));
    if (auth.token) {
      import('axios').then(({ default: axios }) => {
        axios.defaults.headers.common['Authorization'] = `Bearer ${auth.token}`;
      });
    }
  }, [auth]);

  // Persist favorites
  useEffect(() => {
    localStorage.setItem('filmapi_favorites', JSON.stringify(favorites));
  }, [favorites]);

  // Persist platforms
  useEffect(() => {
    localStorage.setItem('filmapi_platforms', JSON.stringify(platforms));
  }, [platforms]);

  /**
   * Performs login and updates auth state.
   * @param {string} token - JWT token.
   * @param {Object} user - User information.
   */
  const login = (token, user) => {
    setAuth({ token, user });
  };

  /**
   * Performs logout and clears auth state.
   */
  const logout = () => {
    setAuth({ token: null, user: null });
    import('axios').then(({ default: axios }) => {
      delete axios.defaults.headers.common['Authorization'];
    });
  };

  /**
   * Toggles a title in favorites.
   * @param {Object} title - The title object to add or remove.
   */
  const toggleFavorite = async (title) => {
    // If logged in, we should ideally sync with backend.
    // For now, keeping local dominance as per frontend requirements.
    setFavorites(prev => {
      const exists = prev.find(f => f.id === title.id || f.watchmodeId === title.id);
      if (exists) {
        return prev.filter(f => f.id !== title.id && f.watchmodeId !== title.id);
      }
      return [...prev, title];
    });
  };

  /**
   * Updates the user's selected streaming platforms.
   * @param {number[]} newPlatforms - Array of platform IDs.
   */
  const updatePlatforms = (newPlatforms) => {
    setPlatforms(newPlatforms);
  };

  const value = {
    favorites,
    toggleFavorite,
    isFavorite: (id) => favorites.some(f => f.id === id || f.watchmodeId === id),
    platforms,
    updatePlatforms,
    auth,
    login,
    logout,
    isAuthenticated: !!auth.token
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
