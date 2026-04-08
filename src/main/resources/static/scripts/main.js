// Handler para el clic
const handleEditClick = (button) => {
    console.log("¡Botón presionado!"); // Si ves esto en la consola, el JS cargó bien

    const id = button.getAttribute('data-id');
    const title = button.getAttribute('data-title');
    const category = button.getAttribute('data-category');
    const image = button.getAttribute('data-image');

    console.log("Datos capturados:", {id, title, category, image});

    prepareEdit(id, title, category, image);
};

const prepareEdit = (id, title, category, imageUrl) => {
    const formTitle = document.getElementById('form-title');
    const submitBtn = document.getElementById('submit-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const gameForm = document.getElementById('game-form');

    formTitle.innerHTML = `<span class="text-blue-500">📝</span> Editando: ${title}`;
    submitBtn.innerText = 'Guardar Cambios';

    // Cambiamos clases de color
    submitBtn.classList.remove('bg-red-600', 'hover:bg-red-700');
    submitBtn.classList.add('bg-blue-600', 'hover:bg-blue-700');

    cancelBtn.classList.remove('hidden');

    // Llenamos los inputs
    document.getElementById('game-id').value = id;
    document.getElementById('input-title').value = title;
    document.getElementById('input-category').value = category;
    document.getElementById('input-image').value = imageUrl;

    gameForm.action = `/admin/games/update/${id}`;
    window.scrollTo({ top: 0, behavior: 'smooth' });
};

const resetForm = () => {
    const formTitle = document.getElementById('form-title');
    const submitBtn = document.getElementById('submit-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const gameForm = document.getElementById('game-form');

    formTitle.innerHTML = `<span class="text-red-500">➕</span> Añadir Nuevo Título`;
    submitBtn.innerText = 'Añadir';

    submitBtn.classList.remove('bg-blue-600', 'hover:bg-blue-700');
    submitBtn.classList.add('bg-red-600', 'hover:bg-red-700');

    cancelBtn.classList.add('hidden');
    gameForm.reset();
    document.getElementById('game-id').value = '';
    gameForm.action = '/admin/games/add';
};