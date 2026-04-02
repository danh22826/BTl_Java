document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const phimId = urlParams.get("id");

    if (!phimId) {
        fillFallbackContent();
        return;
    }

    fetch(`http://localhost:8080/api/phim/${phimId}`)
        .then(response => {
            if (!response.ok) throw new Error("Loi API: " + response.status);
            return response.json();
        })
        .then(phim => renderPhim(phim))
        .catch(error => {
            console.error("Loi:", error);
            fillFallbackContent();
        });
});

function renderPhim(phim) {
    document.title = phim.tenPhim || "Chi tiet phim";

    const title = phim.tenPhim || "Dang cap nhat";
    const posterUrl = resolvePosterUrl(phim.poster);
    const genres = Array.isArray(phim.theLoais) && phim.theLoais.length > 0
        ? phim.theLoais.join(" | ")
        : "Chua cap nhat";
    const durationText = phim.thoiLuong ? `${phim.thoiLuong} phut` : "Chua cap nhat";
    const releaseText = formatDate(phim.ngayKhoiChieu) || "Chua cap nhat";
    const ageText = phim.doTuoiPhuHop || "Chua cap nhat";
    const languageText = phim.ngonNgu || "Chua cap nhat";
    const summaryText = phim.moTa || "Noi dung phim se duoc cap nhat sau.";
    const posterFile = phim.poster ? phim.poster.split("/").pop() : "default.jpg";

    setText("movie-title", title);
    setText("movie-title-hero", title);
    setText("movie-tagline", summaryText);
    setText("movie-age", ageText);
    setText("movie-release", releaseText);
    setText("movie-summary-text", summaryText);
    setText("movie-language", languageText);
    setText("movie-basic", `${languageText} (${durationText})`);
    setText("movie-format", genres);
    setText("movie-code", phim.maPhim || "---");
    setText("movie-duration", durationText);
    setText("movie-poster-file", posterFile);
    setText("movie-score", calcScore(phim));

    bindImage("movie-poster", posterUrl);
    bindImage("movie-hero", posterUrl);
    bindImage("movie-side-banner", posterUrl);
    bindImage("movie-thumb-1", posterUrl);
    bindImage("movie-thumb-2", posterUrl);
    bindImage("movie-thumb-3", posterUrl);

    const bookBtn = document.querySelector(".book-btn");
    if (bookBtn) {
        bookBtn.onclick = function () {
            window.location.href = `../html/datve.html?id=${phim.maPhim}`;
        };
    }

    const urlBtn = document.querySelector(".share-btn.url");
    if (urlBtn) {
        urlBtn.href = window.location.href;
    }
}

function setText(id, value) {
    const element = document.getElementById(id);
    if (element) {
        element.innerText = value;
    }
}

function bindImage(id, src) {
    const image = document.getElementById(id);
    if (!image) return;

    image.onerror = function () {
        this.src = "http://localhost:8080/poster/default.jpg";
    };
    image.src = src;
}

function resolvePosterUrl(poster) {
    if (!poster) return "http://localhost:8080/poster/default.jpg";
    const fileName = poster.split("/").pop();
    return `http://localhost:8080/poster/${fileName}`;
}

function calcScore(phim) {
    const base = phim.thoiLuong ? Math.min(10, 8 + phim.thoiLuong / 100) : 9.0;
    return base.toFixed(1);
}

function fillFallbackContent() {
    renderPhim({
        maPhim: "PHIM-DEMO",
        tenPhim: "Khung du lieu phim",
        doTuoiPhuHop: "P",
        ngayKhoiChieu: "2026-03-13",
        moTa: "Trang nay da co san cac khung de nhung du lieu tu API phim, poster, tom tat, thumbnail, banner va cac thong tin metadata.",
        ngonNgu: "Long tieng, Phu de",
        thoiLuong: 105,
        theLoais: ["Animation", "Phieu luu", "Gia dinh"],
        poster: null
    });
}

function formatDate(dateString) {
    if (!dateString) return "";

    const date = new Date(dateString);
    if (isNaN(date.getTime())) return dateString;

    const day = String(date.getDate()).padStart(2, "0");
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const year = date.getFullYear();

    return `${day}/${month}/${year}`;
}
