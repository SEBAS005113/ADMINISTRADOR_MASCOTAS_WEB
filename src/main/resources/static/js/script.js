let chartPerroSaludInstance = null;
let chartPerroRazaInstance = null;
let chartGatoSaludInstance = null;
let chartGatoRazaInstance = null;

function renderizarEstadisticas() {
    let perroSalud = { "Estable": 0, "Enfermo": 0, "Crítico": 0 };
    let perroRazas = {};
    let gatoSalud = { "Estable": 0, "Enfermo": 0, "Crítico": 0 };
    let gatoRazas = {};

    if (window.mascotasData) {
        window.mascotasData.forEach(m => {
            const isPerro = m.tipo && m.tipo.toLowerCase().includes('perro');
            const isGato = m.tipo && m.tipo.toLowerCase().includes('gato');

            if (isPerro) {
                if (perroSalud[m.healthStatus] !== undefined) {
                    perroSalud[m.healthStatus]++;
                }
                if (m.breed) {
                    perroRazas[m.breed] = (perroRazas[m.breed] || 0) + 1;
                }
            } else if (isGato) {
                if (gatoSalud[m.healthStatus] !== undefined) {
                    gatoSalud[m.healthStatus]++;
                }
                if (m.breed) {
                    gatoRazas[m.breed] = (gatoRazas[m.breed] || 0) + 1;
                }
            }
        });
    }

    // Destruir gráficos anteriores para evitar duplicados o errores en canvas
    if (chartPerroSaludInstance) chartPerroSaludInstance.destroy();
    if (chartPerroRazaInstance) chartPerroRazaInstance.destroy();
    if (chartGatoSaludInstance) chartGatoSaludInstance.destroy();
    if (chartGatoRazaInstance) chartGatoRazaInstance.destroy();

    // 1. Perros - Estado de Salud (Gráfico de Barras)
    const ctxPerroSalud = document.getElementById('chartPerroSalud').getContext('2d');
    chartPerroSaludInstance = new Chart(ctxPerroSalud, {
        type: 'bar',
        data: {
            labels: Object.keys(perroSalud),
            datasets: [{
                label: 'Cantidad',
                data: Object.values(perroSalud),
                backgroundColor: ['#10B981', '#F59E0B', '#EF4444'],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } }
        }
    });

    // 2. Perros - Razas (Gráfico de Pastel)
    const ctxPerroRaza = document.getElementById('chartPerroRaza').getContext('2d');
    chartPerroRazaInstance = new Chart(ctxPerroRaza, {
        type: 'pie',
        data: {
            labels: Object.keys(perroRazas),
            datasets: [{
                data: Object.values(perroRazas),
                backgroundColor: ['#0284C7', '#38BDF8', '#0369A1', '#7DD3FC', '#BAE6FD'],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

    // 3. Gatos - Estado de Salud (Gráfico de Barras)
    const ctxGatoSalud = document.getElementById('chartGatoSalud').getContext('2d');
    chartGatoSaludInstance = new Chart(ctxGatoSalud, {
        type: 'bar',
        data: {
            labels: Object.keys(gatoSalud),
            datasets: [{
                label: 'Cantidad',
                data: Object.values(gatoSalud),
                backgroundColor: ['#10B981', '#F59E0B', '#EF4444'],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } }
        }
    });

    // 4. Gatos - Razas (Gráfico de Pastel)
    const ctxGatoRaza = document.getElementById('chartGatoRaza').getContext('2d');
    chartGatoRazaInstance = new Chart(ctxGatoRaza, {
        type: 'pie',
        data: {
            labels: Object.keys(gatoRazas),
            datasets: [{
                data: Object.values(gatoRazas),
                backgroundColor: ['#EC4899', '#F472B6', '#DB2777', '#FBCFE8'],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
}

function cambiarTab(tipo) {
    const formPerro = document.getElementById('form-perro');
    const formGato = document.getElementById('form-gato');
    const seccionListar = document.getElementById('seccion-listar');
    const seccionEstadisticas = document.getElementById('seccion-estadisticas');
    
    const btnPerro = document.getElementById('tab-btn-perro');
    const btnGato = document.getElementById('tab-btn-gato');
    const btnListar = document.getElementById('tab-btn-listar');
    const btnEstadisticas = document.getElementById('tab-btn-estadisticas');

    if (!formPerro || !formGato || !seccionListar || !seccionEstadisticas) return;

    formPerro.classList.add('hidden');
    formGato.classList.add('hidden');
    seccionListar.classList.add('hidden');
    seccionEstadisticas.classList.add('hidden');
    
    const inactiveClass = "px-4 py-2 text-center font-semibold text-slate-600 bg-slate-50 rounded-t-lg border-t border-x border-transparent hover:bg-slate-100 transition";
    const activeClass = "px-4 py-2 text-center font-semibold text-sky-800 bg-sky-100 rounded-t-lg border-t border-x border-sky-300 transition";

    btnPerro.className = inactiveClass;
    btnGato.className = inactiveClass;
    btnListar.className = inactiveClass;
    btnEstadisticas.className = inactiveClass;

    if (tipo === 'perro') {
        formPerro.classList.remove('hidden');
        btnPerro.className = activeClass;
    } else if (tipo === 'gato') {
        formGato.classList.remove('hidden');
        btnGato.className = activeClass;
    } else if (tipo === 'listar') {
        seccionListar.classList.remove('hidden');
        btnListar.className = activeClass;
    } else if (tipo === 'estadisticas') {
        seccionEstadisticas.classList.remove('hidden');
        btnEstadisticas.className = activeClass;
        renderizarEstadisticas();
    }
}

window.addEventListener('DOMContentLoaded', () => {
    const tabActiva = window.tabActivaServidor || 'perro';
    cambiarTab(tabActiva);
});