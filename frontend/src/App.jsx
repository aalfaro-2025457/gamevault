import React, { useEffect, useState } from 'react';

function App() {
  const [games, setGames] = useState([]);
  const [slug, setSlug] = useState('');
  const [loading, setLoading] = useState(false);

  // 1. Obtener juegos con fetch
  const fetchGames = async () => {
    try {
      const response = await fetch('/api/v1/games?page=0&size=10');

      if (!response.ok) {
        throw new Error(`Error del servidor: ${response.status}`);
      }

      const data = await response.json();

      if (data && data.content) {
        setGames(data.content);
      }
    } catch (e) {
      console.error("Error al cargar juegos:", e.message);
    }
  };

  // 2. Importar juego con fetch (POST con parámetros)
  const handleImport = async (e) => {
    e.preventDefault();
    if (!slug) return;
    setLoading(true);

    try {
      // Importante: Los QueryParams se concatenan en la URL
      const url = `/api/v1/games/import?slug=${encodeURIComponent(slug)}&categoryName=Favoritos`;

      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        const errorData = await response.text();
        throw new Error(`Fallo al importar: ${response.status} - ${errorData}`);
      }

      setSlug('');
      fetchGames(); // Refrescar lista
    } catch (e) {
      console.error("Error al importar:", e.message);
      alert("Error: " + e.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchGames();
  }, []);

  return (
    <div className="container py-5">
      <h1 className="text-center mb-4">🎮 GameVault</h1>

      <form onSubmit={handleImport} className="mb-5 d-flex gap-2 justify-content-center">
        <input
          className="form-control w-50"
          value={slug}
          onChange={(e) => setSlug(e.target.value)}
          placeholder="Nombre del juego..."
        />
        <button className="btn btn-primary" type="submit" disabled={loading}>
          {loading ? '...' : 'Agregar'}
        </button>
      </form>

      <div className="row">
        {games.length > 0 ? (
          games.map((g) => (
            <div key={g.idGame} className="col-md-4 mb-3">
              <div className="card shadow-sm h-100 border-0">
                <img
                  src={g.imageUrlGame || 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfJyj9f32jPyJYzxFEqp8JwnX4IuMMLtO-7g&s'}
                  className="card-img-top"
                  style={{height: '200px', objectFit: 'cover'}}
                  alt={g.titleGame}
                />
                <div className="card-body bg-dark text-white rounded-bottom">
                  <h5 className="card-title mb-0 text-truncate">{g.titleGame}</h5>
                </div>
              </div>
            </div>
          ))
        ) : (
          <p className="text-center text-muted">No hay juegos. ¡Agrega uno arriba!</p>
        )}
      </div>
    </div>
  );
}

export default App;