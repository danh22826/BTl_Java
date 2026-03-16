document.addEventListener("DOMContentLoaded", function () {
     const urlParams = new URLSearchParams(window.location.search);
     const phimId = urlParams.get("id");

     console.log("JavaScript đang chạy");

     if (!phimId) {
         alert("Không tìm thấy mã phim trên URL");
         return;
     }

     fetch(`http://localhost:8080/api/phim/${phimId}`)
         .then(response => {
             if (!response.ok) throw new Error("Lỗi API: " + response.status);
             return response.json();
         })
         .then(phim => renderPhim(phim))
         .catch(error => {
             console.error("Lỗi:", error);
             alert("Lỗi khi tải dữ liệu phim: " + error.message);
         });
 });

 function renderPhim(phim) {
     document.title = phim.tenPhim || "Chi tiết phim";

     document.getElementById("movie-title").innerText = phim.tenPhim || "";
     document.getElementById("movie-age").innerText = phim.doTuoiPhuHop || "Chưa cập nhật";
     document.getElementById("movie-release").innerText = formatDate(phim.ngayKhoiChieu);
     document.getElementById("movie-summary-text").innerText = phim.moTa || "Chưa có tóm tắt";

     document.getElementById("movie-language").innerText = phim.ngonNgu || "Chưa cập nhật";

     document.getElementById("movie-basic").innerText = phim.thoiLuong ? `${phim.thoiLuong} phút` : "Chưa cập nhật";

     document.getElementById("movie-format").innerText =
         phim.theLoais?.length > 0
             ? phim.theLoais.map(tl => tl.tenTheLoai).join(" | ")
             : "Chưa cập nhật";



     const posterEl = document.getElementById("movie-poster");
     posterEl.onerror = function () {
         this.src = "http://localhost:8080/poster/default.jpg";
     };

     if (phim.poster) {
         const fileName = phim.poster.split("/").pop();
         posterEl.src = `http://localhost:8080/poster/${fileName}`;
     } else {
         posterEl.src = "http://localhost:8080/poster/default.jpg";
     }

     const bookBtn = document.querySelector(".book-btn");
     if (bookBtn) {
         bookBtn.onclick = function () {
             window.location.href = `../html/datve.html?id=${phim.maPhim}`;
         };
     }
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