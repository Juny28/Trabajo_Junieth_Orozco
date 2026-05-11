import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { AppProvider } from './context/AppContext';
import Header from './components/Header';
import Home from './pages/Home';
import AllTitles from './pages/AllTitles';
import Favorites from './pages/Favorites';
import Details from './pages/Details';
import MyPlatforms from './pages/MyPlatforms';

/**
 * Main App component with routing and global context.
 * @returns {JSX.Element}
 */
function App() {
  return (
    <AppProvider>
      <Router>
        <div className="min-h-screen bg-bg-dark text-text-main">
          <Header />
          <main className="pt-24 pb-12">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/all" element={<AllTitles />} />
              <Route path="/favorites" element={<Favorites />} />
              <Route path="/details/:id" element={<Details />} />
              <Route path="/myplatforms" element={<MyPlatforms />} />
            </Routes>
          </main>
        </div>
      </Router>
    </AppProvider>
  );
}

export default App;
