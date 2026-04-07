import React, { useEffect, useState } from 'react';

function App() {
  const [games, setGames] = useState([]);
  const [loading, setLoading] = useState(false);

  const [formData, setFormData] = useState({
    title: '',
    category: '',
    imageUrl: ''
  });

  const fetchGames = async () => {
    try {
      const response = await fetch('/api/v1/games?page=0&size=12');
      if (!response.ok) throw new Error('Error al cargar');
      const data = await response.json();
      if (data && data.content) {
        setGames(data.content);
      }
    } catch (e) {
      console.error("Error visualizando juegos:", e);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const { title, category, imageUrl } = formData;

    if (!title || !category || !imageUrl) {
      alert("Por favor, rellena todos los campos.");
      return;
    }

    setLoading(true);

    try {

      const url = `/api/v1/games/import?slug=${encodeURIComponent(title)}&categoryName=${encodeURIComponent(category)}&imageUrl=${encodeURIComponent(imageUrl)}`;

      console.log(imageUrl);

      const response = await fetch(url, { method: 'POST' });

      if (!response.ok) {
        throw new Error('Fallo al guardar el juego');
      }

      setFormData({ title: '', category: '', imageUrl: '' });
      fetchGames();
    } catch (e) {
      console.error("Error guardando:", e);
      alert("No se pudo guardar el juego. Revisa la consola.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchGames();
  }, []);

  return (
    <div className="min-h-screen">
      {/* Barra de navegación elegante */}
      <nav class="bg-black/80 backdrop-blur-sm sticky top-0 z-50 border-b border-gray-800">
        <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4 flex items-center justify-between">
          <div className="flex items-center gap-2">
             <span className="text-3xl">🎮</span>
             <h1 className="text-3xl font-bold text-white tracking-tighter">
                Game<span className="text-red-600">Vault</span>
             </h1>
          </div>
          <span className="text-gray-400 text-sm">Tu Colección Personal</span>
        </div>
      </nav>

      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">

        {/* Sección del Formulario - Estilo Tarjeta Oscura */}
        <section className="bg-card p-8 rounded-2xl border border-gray-800 shadow-xl mb-12">
          <h2 className="text-2xl font-semibold text-white mb-6 flex items-center gap-2">
            <span className="text-red-500">➕</span> Añadir Nuevo Título
          </h2>

          <form onSubmit={handleSubmit} className="grid grid-cols-1 md:grid-cols-4 gap-4 items-end">
            <div>
              <label className="block text-sm font-medium text-gray-400 mb-1">Título del Juego</label>
              <input
                name="title"
                value={formData.title}
                onChange={handleInputChange}
                className="w-full bg-vdark border border-gray-700 rounded-lg p-3 text-white placeholder-gray-500 focus:ring-2 focus:ring-red-600 focus:border-red-600"
                placeholder="ej: Elden Ring"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-400 mb-1">Categoría</label>
              <input
                name="category"
                value={formData.category}
                onChange={handleInputChange}
                className="w-full bg-vdark border border-gray-700 rounded-lg p-3 text-white placeholder-gray-500 focus:ring-2 focus:ring-red-600 focus:border-red-600"
                placeholder="ej: RPG"
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-400 mb-1">URL de la Imagen</label>
              <input
                name="imageUrl"
                value={formData.imageUrl}
                onChange={handleInputChange}
                className="w-full bg-vdark border border-gray-700 rounded-lg p-3 text-white placeholder-gray-500 focus:ring-2 focus:ring-red-600 focus:border-red-600"
                placeholder="https://link-a-la-portada.com/imagen.jpg"
              />
            </div>
            <div>
              <button
                type="submit"
                disabled={loading}
                className="w-full bg-red-600 hover:bg-red-700 text-white font-bold py-3 px-6 rounded-lg transition duration-150 disabled:opacity-50 flex items-center justify-center gap-2"
              >
                {loading ? (
                    <span className="animate-spin inline-block w-4 h-4 border-2 border-white border-t-transparent rounded-full"></span>
                ) : 'Añadir a la Bóveda'}
              </button>
            </div>
          </form>
        </section>

        {/* Sección de la Galería - Estilo Póster de Película */}
        <section>
          <h2 className="text-3xl font-bold text-white mb-8 tracking-tight">Mi Bóveda de Juegos</h2>

          <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 xl:grid-cols-6 gap-6">
            {games.length > 0 ? (
              games.map((g) => (
                <div key={g.idGame} className="game-card group relative bg-card rounded-lg overflow-hidden shadow-md hover:shadow-2xl transition-all duration-300 border border-gray-800">
                  <div className="aspect-[2/3] overflow-hidden">
                    <img
                      src={g.imageUrlGame || 'https://via.placeholder.com/300x450?text=Sin+Portada'}
                      className="w-full h-full object-cover"
                      alt={g.titleGame}
                    />
                  </div>

                  <div className="absolute inset-0 bg-gradient-to-t from-black via-black/70 to-transparent p-4 flex flex-col justify-end opacity-0 group-hover:opacity-100 transition-opacity duration-300">
                    <h3 className="text-lg font-bold text-white leading-tight mb-1">{g.titleGame}</h3>
                    <div className="flex items-center gap-2">
                        <span className="bg-red-600 text-xs text-white font-bold px-2 py-1 rounded">
                            {g.category?.name || 'General'}
                        </span>
                    </div>
                  </div>

                  <div className="p-3 bg-card group-hover:bg-vdark transition-colors">
                    <p className="text-sm font-semibold text-gray-100 text-truncate">{g.titleGame}</p>
                  </div>
                </div>
              ))
            ) : (
              <div className="col-span-full text-center py-20 bg-card rounded-xl border border-gray-800">
                <span className="text-6xl mb-4 block">👻</span>
                <p className="text-xl text-gray-500">Tu bóveda está vacía. ¡Añade tu primer juego arriba!</p>
              </div>
            )}
          </div>
        </section>
      </main>

      <footer className="border-t border-gray-800 mt-20 bg-black/50 py-6 text-center text-gray-600 text-sm">
        GameVault v0.1 | Desarrollado por Angel Alfaro en Kinal
      </footer>
    </div>
  );
}

export default App;