const heroSlides = [...document.querySelectorAll('.hero-slide')];
const heroDots = [...document.querySelectorAll('.hero-dot')];
const prevBtn = document.querySelector('.hero-prev');
const nextBtn = document.querySelector('.hero-next');
let heroIndex = 0;

function showHeroSlide(index) {
  heroIndex = (index + heroSlides.length) % heroSlides.length;
  heroSlides.forEach((slide, i) => slide.classList.toggle('active', i === heroIndex));
  heroDots.forEach((dot, i) => dot.classList.toggle('active', i === heroIndex));
}

prevBtn?.addEventListener('click', () => showHeroSlide(heroIndex - 1));
nextBtn?.addEventListener('click', () => showHeroSlide(heroIndex + 1));
heroDots.forEach((dot, i) => dot.addEventListener('click', () => showHeroSlide(i)));

setInterval(() => showHeroSlide(heroIndex + 1), 5000);

const API_PHIM = "http://localhost:8080/api/phim";

const SO_PHIM_MOI_TRANG = 12;
let danhSachPhim = [];
let trangHienTai = 1;
let daMoPhanTrang = false;

function tongSoTrang() {
  return Math.ceil(danhSachPhim.length / SO_PHIM_MOI_TRANG);
}

function layPhimTheoTrang(trang) {
  const start = (trang - 1) * SO_PHIM_MOI_TRANG;
  const end = start + SO_PHIM_MOI_TRANG;
  return danhSachPhim.slice(start, end);
}

function xemChiTiet(maPhim) {
  window.location.href = `/html/chitietphim.html?id=${maPhim}`;
}

function datVe(maPhim) {
  window.location.href = `/html/chitietphim.html?id=${maPhim}`;
}

function taoCard(phim) {
  const poster = `/poster/${phim.poster?.split("/").pop() || "default.jpg"}`;

  return `
    <article class="movie-card">
      <div class="card-poster">


        <img src="${poster}" alt="${phim.tenPhim}"
             onerror="this.src='/poster/default.jpg'" />

        <div class="button-group" onclick="event.stopPropagation()">
          <button class="buy-btn" onclick="datVe('${phim.maPhim}')">Mua vé ngay</button>
          <button class="detail-btn" onclick="xemChiTiet('${phim.maPhim}')">Chi tiết</button>
        </div>
      </div>

      <div class="card-info">
        <h3>${phim.tenPhim || "Chưa có tên phim"}</h3>
        <div class="meta-line">
          <span><i class="far fa-clock"></i> ${phim.thoiLuong || 0} phút</span>
          <span>•</span>
          <span>${phim.doTuoiPhuHop || "K"}</span>
        </div>
        <div class="genre-tags">
          ${
            phim.theLoais?.length > 0
              ? phim.theLoais.map(tl => `<span>${tl.tenTheLoai}</span>`).join("")
              : "<span>Chưa cập nhật</span>"
          }
        </div>
      </div>
    </article>
  `;
}

function hienThiPhim() {
  const grid = document.getElementById("movie-grid");
  const dsPhimTrang = layPhimTheoTrang(trangHienTai);

  if (dsPhimTrang.length === 0) {
    grid.innerHTML = `
      <div class="movie-loading">
        <p>Không có dữ liệu phim</p>
      </div>
    `;
    return;
  }

  grid.innerHTML = dsPhimTrang.map(taoCard).join("");
}

function hienThiPhanTrang() {
  const phanTrangEl = document.getElementById("phan-trang");
  const total = tongSoTrang();

  let html = "";

  for (let i = 1; i <= total; i++) {
    html += `
      <button class="${i === trangHienTai ? "active" : ""}" onclick="chuyenTrang(${i})">
        ${i}
      </button>
    `;
  }

  phanTrangEl.innerHTML = html;
}

function chuyenTrang(trang) {
  trangHienTai = trang;
  hienThiPhim();
  hienThiPhanTrang();

  document.getElementById("movie-grid")?.scrollIntoView({
    behavior: "smooth",
    block: "start"
  });
}

window.chuyenTrang = chuyenTrang;
window.xemChiTiet = xemChiTiet;
window.datVe = datVe;

function khoiTaoDanhSachPhim(data) {
  danhSachPhim = data;
  trangHienTai = 1;

  document.getElementById("movie-count").innerText =
    `${danhSachPhim.length} phim • ${tongSoTrang()} trang`;

  hienThiPhim();
  hienThiPhanTrang();

  const btnXemThem = document.getElementById("btn-xem-them");
  const phanTrangEl = document.getElementById("phan-trang");

  btnXemThem.addEventListener("click", function () {
    daMoPhanTrang = !daMoPhanTrang;
    phanTrangEl.style.display = daMoPhanTrang ? "flex" : "none";
    btnXemThem.innerText = daMoPhanTrang ? "Ẩn bớt" : "Xem thêm";
  });
}

fetch(API_PHIM)
  .then(response => {
    if (!response.ok) throw new Error("Không thể tải danh sách phim");
    return response.json();
  })
  .then(data => {
    khoiTaoDanhSachPhim(data);
  })
  .catch(error => {
    console.error(error);
    document.getElementById("movie-grid").innerHTML = `
      <div class="movie-loading">
        <p style="color:#e50914">Không tải được dữ liệu phim</p>
      </div>
    `;
    document.getElementById("movie-count").innerText = "";
    document.getElementById("btn-xem-them").style.display = "none";
  });