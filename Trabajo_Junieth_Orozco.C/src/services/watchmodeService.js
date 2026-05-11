import axios from 'axios';

const API_KEY = 'YOUR_WATCHMODE_API_KEY';
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
   * @returns {Promise<Array>} List of titles.
   */
  searchTitles: async (query) => {
    const { data } = await apiClient.get('/search/', {
      params: { search_field: 'name', search_value: query },
    });
    return data.title_results || [];
  },

  /**
   * Get details for a specific title.
   * @param {number} id - The Watchmode title ID.
   * @returns {Promise<Object>} Title details.
   */
  getDetails: async (id) => {
    const { data } = await apiClient.get(`/title/${id}/details/`, {
      params: { append_to_response: 'sources' },
    });
    return data;
  },

  /**
   * Get trending/popular titles.
   * @returns {Promise<Array>} List of popular titles.
   */
  getPopular: async () => {
    const { data } = await apiClient.get('/list-titles/', {
      params: { limit: 20, sort: 'relevance_desc' },
    });
    return data.titles || [];
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
