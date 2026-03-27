const API_BASE = "http://localhost:8080/api";

// DOM
const khuVucThanhPho = document.getElementById("khu-vuc-thanh-pho");
const khuVucRap = document.getElementById("khu-vuc-danh-sach-rap");
const tomTatRap = document.getElementById("tom-tat-rap");
const rapYeuThich = document.getElementById("rap-yeu-thich");

// State
let dsThanhPho = [];
let dsRap = [];

let maThanhPhoDangChon = null;
let maRapDangChon = null;

// INIT
document.addEventListener("DOMContentLoaded", async () => {
    await loadThanhPho();
});


// LOAD THÀNH PHỐ
async function loadThanhPho() {
    try {
        khuVucThanhPho.innerHTML = `<div class="loading-text">Đang tải thành phố...</div>`;

        const res = await fetch(`${API_BASE}/thanh-pho`);
        if (!res.ok) throw new Error("Lỗi load thành phố");

        dsThanhPho = await res.json();

        if (dsThanhPho.length === 0) {
            khuVucThanhPho.innerHTML = `<div class="empty-state">Không có thành phố</div>`;
            return;
        }

        // chọn mặc định thành phố đầu tiên
        maThanhPhoDangChon = dsThanhPho[0].maThanhPho;

        renderThanhPho();

        await loadRap(maThanhPhoDangChon);

    } catch (err) {
        console.error(err);
        khuVucThanhPho.innerHTML = `<div class="empty-state">Lỗi tải dữ liệu</div>`;
    }
}


// RENDER THÀNH PHỐ
function renderThanhPho() {
    khuVucThanhPho.innerHTML = dsThanhPho.map(tp => {
        const active = tp.maThanhPho === maThanhPhoDangChon ? "city-item--active" : "";

        return `
            <button class="city-item ${active}" onclick="chonThanhPho('${tp.maThanhPho}')">
                ${tp.tenThanhPho}
            </button>
        `;
    }).join("");
}


// CLICK THÀNH PHỐ
async function chonThanhPho(maThanhPho) {
    maThanhPhoDangChon = maThanhPho;
    maRapDangChon = null;

    renderThanhPho();

    await loadRap(maThanhPho);
}


// LOAD RẠP
async function loadRap(maThanhPho) {
    try {
        khuVucRap.innerHTML = `<div class="loading-text">Đang tải rạp...</div>`;

        const res = await fetch(`${API_BASE}/rap?maThanhPho=${maThanhPho}`);
        if (!res.ok) throw new Error("Lỗi load rạp");

        dsRap = await res.json();

        if (dsRap.length === 0) {
            khuVucRap.innerHTML = `<div class="empty-state">Không có rạp</div>`;
            return;
        }

        // chọn mặc định rạp đầu tiên
        maRapDangChon = dsRap[0].maRap;

        renderRap();

        updateSummary();

    } catch (err) {
        console.error(err);
        khuVucRap.innerHTML = `<div class="empty-state">Lỗi tải rạp</div>`;
    }
}



// RENDER RẠP

function renderRap() {
    khuVucRap.innerHTML = dsRap.map(r => {
        const active = r.maRap === maRapDangChon ? "cinema-item--active" : "";

        return `
            <button class="cinema-item ${active}" onclick="chonRap('${r.maRap}')">
                ${r.tenRap}
            </button>
        `;
    }).join("");
}



// CLICK RẠP

function chonRap(maRap) {
    maRapDangChon = maRap;

    renderRap();
    updateSummary();
}



// UPDATE THANH DƯỚI

function updateSummary() {
    const rap = dsRap.find(r => r.maRap === maRapDangChon);

    if (rap) {
        tomTatRap.textContent = rap.tenRap;
        rapYeuThich.textContent = rap.tenRap;
    } else {
        tomTatRap.textContent = "Vui lòng chọn rạp";
        rapYeuThich.textContent = "--";
    }
}