// Control de pestañas
function switchTab(tab) {
    const loginSec = document.getElementById('loginSection');
    const regSec = document.getElementById('registerSection');
    const tabLog = document.getElementById('tabLogin');
    const tabReg = document.getElementById('tabRegister');
    const msg = document.getElementById('responseMessage');

    msg.classList.add('hidden');

    if (tab === 'login') {
        loginSec.classList.remove('hidden');
        regSec.classList.add('hidden');
        tabLog.className = "tab-btn active-tab";
        tabReg.className = "tab-btn";
    } else {
        regSec.classList.remove('hidden');
        loginSec.classList.add('hidden');
        tabReg.className = "tab-btn active-tab";
        tabLog.className = "tab-btn";
    }
}

const messageDiv = document.getElementById('responseMessage');

// Lógica de Registro
document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('/api/users/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, email, password })
        });

        const data = await response.json();
        messageDiv.classList.remove('hidden');

        if (response.ok) {
            messageDiv.className = "alert alert-success";
            messageDiv.textContent = `¡Registro exitoso! Ya puedes iniciar sesión.`;
            document.getElementById('registerForm').reset();
            setTimeout(() => switchTab('login'), 2000);
        } else {
            messageDiv.className = "alert alert-error";
            messageDiv.textContent = data.message || "Ocurrió un error al registrar el usuario.";
        }
    } catch (error) {
        messageDiv.classList.remove('hidden');
        messageDiv.className = "alert alert-error";
        messageDiv.textContent = "No se pudo conectar con el servidor backend.";
    }
});

// Lógica de Login (Con redirección al puerto 8080 y paso de parámetro userId)
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch('/api/users/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        const data = await response.json();
        messageDiv.classList.remove('hidden');

        if (response.ok) {
            messageDiv.className = "alert alert-success";
            messageDiv.textContent = "¡Login exitoso! Redirigiendo al almacén...";

            // 1. Guardamos el ID del usuario en localStorage
            localStorage.setItem('userId', data.id);

            // 2. Redirigimos al microservicio de productos pasando el parámetro en la URL
            setTimeout(() => {
                window.location.href = `http://localhost:8080/productos.html?userId=${data.id}`;
            }, 1000);
        } else {
            messageDiv.className = "alert alert-error";
            messageDiv.textContent = data.message || "Credenciales incorrectas.";
        }
    } catch (error) {
        messageDiv.classList.remove('hidden');
        messageDiv.className = "alert alert-error";
        messageDiv.textContent = "Error al intentar iniciar sesión.";
    }
});