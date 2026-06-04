import axios from 'axios';

const API_KEY = import.meta.env.VITE_WATCHMODE_API_KEY || 'YOUR_WATCHMODE_API_KEY';
const BASE_URL = 'https://api.watchmode.com/v1';

const apiClient = axios.create({
  baseURL: BASE_URL,
  params: {
    apiKey: API_KEY,
  },
});

/**
 * Service to fetch data from Watchmode API.
 */
export const watchmodeService = {
  /**
   * Search for titles by name.
   * @param {string} query - The search query.
   * @param {number} [page=1] - Page number.
   * @returns {Promise<Array>} List of titles.
   */
  searchTitles: async (query, page = 1) => {
    const { data } = await axios.get(`http://localhost:8080/titles/search?name=${query}&page=${page}&limit=8`);
    return data;
  },

  /**
   * Get details for a specific title.
   * @param {number} id - The Watchmode title ID.
   * @returns {Promise<Object>} Title details.
   */
  getDetails: async (id) => {
    // We can still call Watchmode directly for details to reduce backend load if preferred,
    // or call backend if we want to sync with DB.
    // The requirement says: (GET) /titles/{watchmodeId}/reviews (Public)
    // and (GET) /titles/search?name=... (Private)
    // I'll keep the direct API call for details for speed, but use backend for search.
    const { data } = await apiClient.get(`/title/${id}/details/`, {
      params: { append_to_response: 'sources' },
    });
    return data;
  },

  /**
   * Get trending/popular titles.
   * @param {number} [page=1] - Page number.
   * @returns {Promise<Array>} List of popular titles.
   */
  getPopular: async (page = 1) => {
    const { data } = await axios.get(`http://localhost:8080/titles/search?name=&page=${page}&limit=8`);
    return data;
  },

  /**
   * Get list of all platforms.
   * @returns {Promise<Array>} List of platforms.
   */
  getPlatforms: async () => {
    const { data } = await apiClient.get('/sources/');
    return data;
  }
};
