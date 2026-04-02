// ===== AUTHENTICATION MANAGEMENT =====
function checkAuthStatus() {
  const isLoggedIn = sessionStorage.getItem('loggedInUser') !== null;
  const logoutView = document.getElementById('logoutView');
  const loginView = document.getElementById('loginView');

  if (logoutView && loginView) {
    if (isLoggedIn) {
      logoutView.style.display = 'none';
      loginView.style.display = 'flex';
    } else {
      logoutView.style.display = 'flex';
      loginView.style.display = 'none';
    }
  }
}

function handleLogout(e) {
  e.preventDefault();
  if (confirm('Bạn chắc chắn muốn đăng xuất?')) {
    sessionStorage.removeItem('loggedInUser');
    localStorage.removeItem('userData');
    localStorage.removeItem('isLoggedIn');
    checkAuthStatus();
    alert('Đã đăng xuất thành công');
  }
}

// Initialize auth status on page load
document.addEventListener('DOMContentLoaded', function() {
  checkAuthStatus();
});

// ===== HERO SLIDER =====
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

document.addEventListener('DOMContentLoaded', function () {
    // 1. Gọi API lấy danh sách phim
    fetch('http://localhost:8080/api/phim')
        .then(response => {
            if (!response.ok) throw new Error("Không thể kết nối đến server");
            return response.json();
        })
        .then(data => {
            renderMovies(data);
        })
        .catch(error => {
            console.error('Lỗi:', error);
            document.querySelectorAll('.loading-text').forEach(el => el.innerText = "Lỗi tải dữ liệu!");
        });
});

function renderMovies(movies) {
    const nowShowingContainer = document.getElementById('now-showing-container');
    const comingSoonContainer = document.getElementById('coming-soon-container');

    // Xóa dòng "Đang tải..."
    nowShowingContainer.innerHTML = '';
    comingSoonContainer.innerHTML = '';

    const today = new Date();

    movies.forEach(movie => {
        // Phân loại phim dựa trên ngày khởi chiếu
        const releaseDate = new Date(movie.ngayKhoiChieu);

        // Tạo HTML cho thẻ phim
        const movieCard = `
            <article class="movie-card" data-movie-id="${movie.maPhim}">
                <div class="card-poster">
                    <div class="card-meta-top">
                        <span class="badge-status">${releaseDate <= today ? 'Đang chiếu' : 'Sắp chiếu'}</span>
                        <span class="badge-rating"><i class="fas fa-star"></i> 8.5</span>
                    </div>
                    <img src="${movie.poster || 'https://via.placeholder.com/300x450'}" alt="${movie.tenPhim}" />
                    <div class="button-group">
                        <a href="../movie/movie-detail.html?id=${movie.maPhim}" class="buy-btn detail-btn">Xem chi tiết</a>
                        <a href="../gia_ve/giave.html?movie_id=${movie.maPhim}" class="buy-btn">Mua vé ngay</a>
                    </div>
                </div>
                <div class="card-info">
                    <h3>${movie.tenPhim}</h3>
                    <div class="meta-line">
                        <span><i class="far fa-clock"></i> ${movie.thoiLuong} phút</span>
                        <span>•</span>
                        <span>${movie.doTuoiPhuHop}</span>
                    </div>
                    <div class="genre-tags">
                        ${movie.theLoais.map(tag => `<span>${tag}</span>`).join('')}
                    </div>
                </div>
            </article>
        ;

        // Đổ vào đúng khung (Container)
        if (releaseDate <= today) {
            nowShowingContainer.innerHTML += movieCard;
        } else {
            comingSoonContainer.innerHTML += movieCard;
        }
    });
}